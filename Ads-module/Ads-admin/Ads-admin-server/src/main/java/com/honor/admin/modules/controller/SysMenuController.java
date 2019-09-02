/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.honor.admin.modules.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.honor.admin.common.annotation.SysLog;
import com.honor.admin.common.constant.RedisKeyConstant;
import com.honor.admin.common.exception.RRException;
import com.honor.admin.common.utils.Constant;
import com.honor.admin.common.utils.R;
import com.honor.admin.modules.service.SysUserService;
import com.honor.common.base.dto.CommonResultDto;
import com.honor.common.base.dto.UserDto;
import com.honor.common.redis.service.ICacheService;
import com.honor.admin.modules.entity.SysMenuEntity;
import com.honor.admin.modules.service.ShiroService;
import com.honor.admin.modules.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 系统菜单
 *
 * @author Mark sunlightcs@gmail.com
 */
@Slf4j
@Api("系统菜单")
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private ShiroService shiroService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ICacheService cacheService;

    /**
     * 导航菜单
     */
    @GetMapping("/nav")
    public R nav(UserDto userDto) {
        //先从缓存中获取
        List<SysMenuEntity> menuList = JSONObject.parseArray(cacheService.get(RedisKeyConstant.SESSION_USER_MENU_LIST + userDto.getUserId()), SysMenuEntity.class);
        if (CollectionUtils.isEmpty(menuList)) {
            menuList = sysMenuService.getUserMenuList(userDto.getUserId());
            cacheService.add(RedisKeyConstant.SESSION_USER_MENU_LIST + userDto.getUserId(), JSON.toJSONString(menuList));
        }
        List<Object> permissionsList = JSONObject.parseArray(cacheService.get(RedisKeyConstant.SESSION_USER_PERMISSIONS_LIST + userDto.getUserId()));
        if (CollectionUtils.isEmpty(permissionsList)) {
            Set<String> permissions = shiroService.getUserPermissions(userDto.getUserId());
            cacheService.add(RedisKeyConstant.SESSION_USER_PERMISSIONS_LIST + userDto.getUserId(), JSON.toJSONString(permissions));
            return R.ok().put("menuList", menuList).put("permissions", permissions);
        } else {
            Stream<Object> distinctPermissionsList = permissionsList.stream().distinct();
            return R.ok().put("menuList", menuList).put("permissions", distinctPermissionsList);
        }
    }

    /**
     * 所有菜单列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public List<SysMenuEntity> list() {
        List<SysMenuEntity> menuList = sysMenuService.list();
        for (SysMenuEntity sysMenuEntity : menuList) {
            SysMenuEntity parentMenuEntity = sysMenuService.getById(sysMenuEntity.getParentId());
            if (parentMenuEntity != null) {
                sysMenuEntity.setParentName(parentMenuEntity.getName());
            }
        }

        return menuList;
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public R select() {
        //查询列表数据
        List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

        //添加顶级菜单
        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);

        return R.ok().put("menuList", menuList);
    }

    /**
     * 菜单信息
     */
    @GetMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public R info(@PathVariable("menuId") Long menuId) {
        SysMenuEntity menu = sysMenuService.getById(menuId);
        return R.ok().put("menu", menu);
    }

    /**
     * 保存
     */
    @SysLog("保存菜单")
    @PostMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public R save(@RequestBody SysMenuEntity menu,UserDto userDto) {
        //数据校验
        verifyForm(menu);

        sysMenuService.save(menu);
        reloadRedisMenuAndPermissions(userDto);
        return R.ok();
    }

    //刷新redis中的用户权限
    private void reloadRedisMenuAndPermissions(UserDto userDto){
        //刷新用户列表
        List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(userDto.getUserId());
        cacheService.add(RedisKeyConstant.SESSION_USER_MENU_LIST + userDto.getUserId(), JSON.toJSONString(menuList));
        //刷新用户权限
        Set<String> permissions = shiroService.getUserPermissions(userDto.getUserId());
        cacheService.add(RedisKeyConstant.SESSION_USER_PERMISSIONS_LIST + userDto.getUserId(), JSON.toJSONString(permissions));
    }
    /**
     * 修改
     */
    @SysLog("修改菜单")
    @PostMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public R update(@RequestBody SysMenuEntity menu,UserDto userDto) {
        //数据校验
        verifyForm(menu);

        sysMenuService.updateById(menu);
        reloadRedisMenuAndPermissions(userDto);
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除菜单")
    @PostMapping("/delete/{menuId}")
    @RequiresPermissions("sys:menu:delete")
    public R delete(@PathVariable("menuId") long menuId) {
        if (menuId <= 31) {
            return R.error("系统菜单，不能删除");
        }

        //判断是否有子菜单或按钮
        List<SysMenuEntity> menuList = sysMenuService.queryListParentId(menuId);
        if (menuList.size() > 0) {
            return R.error("请先删除子菜单或按钮");
        }

        sysMenuService.delete(menuId);

        return R.ok();
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(SysMenuEntity menu) {
        if (StringUtils.isBlank(menu.getName())) {
            throw new RRException("菜单名称不能为空");
        }

        if (menu.getParentId() == null) {
            throw new RRException("上级菜单不能为空");
        }

        //菜单
        if (menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new RRException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenuEntity parentMenu = sysMenuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if (menu.getType() == Constant.MenuType.CATALOG.getValue() ||
                menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                throw new RRException("上级菜单只能为目录类型");
            }
            return;
        }

        //按钮
        if (menu.getType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                throw new RRException("上级菜单只能为菜单类型");
            }
            return;
        }
    }

    @PostMapping("/queryMenuListByParentId")
    @ApiOperation("queryMenuListByParentId")
    public CommonResultDto queryMenuListByParentId(@RequestBody Map<String,Object> params, UserDto userDto){
        log.info("入参：{}",JSON.toJSONString(params));
        SysMenuEntity menuEntity = this.sysMenuService.getOne(new QueryWrapper<>(SysMenuEntity.builder().menuId(Long.parseLong(params.get("name").toString())).build()));
        if (Objects.isNull(menuEntity)){
            return CommonResultDto.SUCCESS();
        }
        Map<String,Object> map = new HashMap<>();
        //查询用户权限
        Set<String> permissions = shiroService.getUserPermissions(userDto.getUserId());
        map.put("permissions", permissions);
        //获取下级菜单
        List<SysMenuEntity> queryMenuListByParentId = this.sysMenuService.queryMenuListByParentId(menuEntity.getMenuId());
        //如果是系统管理员则显示所有列表
        if(Constant.SUPER_ADMIN == userDto.getUserId()){
            map.put("menuList", queryMenuListByParentId);
            return CommonResultDto.SUCCESS(map);
        }
        //获取用户下的所有菜单ID
        List<Long> menuIdList = sysUserService.queryAllMenuId(userDto.getUserId());
        //根据权限过滤菜单
        List<SysMenuEntity> filterMenuEntityList = queryMenuListByParentId.stream()
                .filter(sysMenuEntity -> menuIdList.contains(sysMenuEntity.getMenuId())).collect(Collectors.toList());
        map.put("menuList", filterMenuEntityList);

        return CommonResultDto.SUCCESS(map);
    }


    public static String getMenuName(String name){
        switch (name){
            case "social":
                return "社会信息采集";
        }
        return name;
    }
}
