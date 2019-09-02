package com.honor.common.base.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.StringTokenizer;

public final class IPUtils {

	/**
	 * 获取客户端IP地址
	 *
	 * @param request
	 * @return
	 */
	public static String getIp(final HttpServletRequest request) {
		String ip = request.getHeader( "HTTP_X_FORWARDED_FOR" );
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip )) {
			ip = request.getHeader( "x-forwarded-for" );
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip )) {
			ip = request.getHeader( "Proxy-Client-IP" );
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip )) {
			ip = request.getHeader( "WL-Proxy-Client-IP" );
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip )) {
			ip = request.getRemoteAddr();
		}
		// 多级反向代理
		if (null != ip && !"".equals( ip.trim() )) {
			StringTokenizer st = new StringTokenizer( ip, "," );
			String ipTmp = "";
			if (st.countTokens() > 1) {
				while (st.hasMoreTokens()) {
					ipTmp = st.nextToken();
					if (ipTmp != null && ipTmp.length() != 0 && !"unknown".equalsIgnoreCase( ipTmp )) {
						ip = ipTmp;
						break;
					}
				}
			}
		}
		return ip;
	}

	/**
	 *
	 * 方法用途: <br>
	 * 获取本地IP地址 实现步骤: <br>
	 *
	 * @return
	 */
	public static String getLocalIP() {
		String sIP = "";
		InetAddress ip = null;
		try {
			// 如果是Windows操作系统
			if (isWindowsOS()) {
				ip = InetAddress.getLocalHost();
			}
			// 如果是Linux操作系统
			else {
				boolean bFindIP = false;
				Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
				while (netInterfaces.hasMoreElements()) {
					if (bFindIP) {
						break;
					}
					NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
					// ----------特定情况，可以考虑用ni.getName判断
					// 遍历所有ip
					Enumeration<InetAddress> ips = ni.getInetAddresses();
					while (ips.hasMoreElements()) {
						ip = (InetAddress) ips.nextElement();
						if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
								&& !ip.getHostAddress().contains(":")) {
							bFindIP = true;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != ip) {
			sIP = ip.getHostAddress();
		}
		return sIP;
	}

	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty( "os.name" );
		if (osName.toLowerCase().indexOf( "windows" ) > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	public static long ipToLong(String ipAddress) {

		long result = 0;

		String[] ipAddressInArray = ipAddress.split( "\\." );

		for (int i = 3; i >= 0; i--) {
			long ip = Long.parseLong( ipAddressInArray[ 3 - i ] );
			result |= ip << (i * 8);
		}
		return result;
	}

	public String longToIp(long ip) {

		return ((ip >> 24) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + (ip & 0xFF);
	}
}
