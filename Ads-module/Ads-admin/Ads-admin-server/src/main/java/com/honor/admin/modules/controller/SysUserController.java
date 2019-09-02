/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.honor.admin.modules.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.honor.common.sso.util.LoginUtils;
import com.honor.admin.common.annotation.SysLog;
import com.honor.admin.common.utils.Constant;
import com.honor.admin.common.utils.PageUtils;
import com.honor.admin.common.utils.R;
import com.honor.admin.common.validator.Assert;
import com.honor.admin.common.validator.ValidatorUtils;
import com.honor.admin.common.validator.group.AddGroup;
import com.honor.admin.common.validator.group.UpdateGroup;
import com.honor.admin.modules.entity.SysUserEntity;
import com.honor.admin.modules.form.PasswordForm;
import com.honor.admin.modules.service.SysUserRoleService;
import com.honor.admin.modules.service.SysUserService;
import com.honor.common.base.dto.UserDto;
import com.honor.common.base.utils.ValidatorHelperUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;


    /**
     * 所有用户列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params, UserDto userDto) {
        //只有超级管理员，才能查看所有管理员列表
        if (userDto.getUserId() != Constant.SUPER_ADMIN) {
            params.put("createUserId", userDto.getUserId());
        }
        PageUtils page = sysUserService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取登录的用户信息
     */
    @GetMapping("/info")
    public R info(UserDto userDto) {
        return R.ok().put("user", userDto);
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @PostMapping("/password")
    public R password(@RequestBody PasswordForm form, UserDto userDto) {
        Assert.isBlank(form.getNewPassword(), "新密码不为能空");
        SysUserEntity userEntity = sysUserService.getById(userDto.getUserId());
        ValidatorHelperUtils.isNullThrow(userEntity, "用户信息不存在");
        //sha256加密
        String password = new Sha256Hash(form.getPassword(), userEntity.getSalt()).toHex();
        //sha256加密
        String newPassword = new Sha256Hash(form.getNewPassword(), userEntity.getSalt()).toHex();
        userEntity.setPassword(newPassword);
        ValidatorHelperUtils.isFalseThrow(
        		this.sysUserService.update(userEntity,
						new QueryWrapper<>(SysUserEntity.builder().userId(userDto.getUserId()).password(password).build())),
				"密码修改失败");
        return R.ok();
    }

    /**
     * 用户信息
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.getById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @PostMapping("/save")
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody SysUserEntity user, UserDto userDto) {
        ValidatorUtils.validateEntity(user, AddGroup.class);

        user.setCreateUserId(userDto.getUserId());
        sysUserService.saveUser(user);

        return R.ok();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @PostMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody SysUserEntity user, UserDto userDto) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);

        user.setCreateUserId(userDto.getUserId());
        sysUserService.update(user);

        return R.ok();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody Long[] userIds, UserDto userDto) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, userDto.getUserId())) {
            return R.error("当前用户不能删除");
        }

        sysUserService.deleteBatch(userIds);

        return R.ok();
    }

    /**
     * 退出
     */
    @PostMapping("/logout")
    public R logout(@ApiIgnore UserDto userDto) {
        LoginUtils.logout(userDto);
        return R.ok();
    }
}
