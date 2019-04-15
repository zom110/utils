package com.wodan.platform.foundation.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * IP地址的工具类
 * 
 * @ClassName: IpUtils
 * @author Administrator
 * @date 2015-3-3 下午2:28:24
 * @history
 * 
 */
public class IpUtils {
	
	public static Logger log = LoggerFactory.getLogger(IpUtils.class);
	
	
	/**
	 * 获取请求 客户端的真实ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		
		if(ip.contains(",")) {
			return ip.split(",")[0];
		} else {
			return ip;
		}
	}

	/**
	 * getHostIp
	 * 
	 * @Description:
	 * @return
	 */
	public static String getHostIp() {
		// 根据网卡取本机配置的IP
		Enumeration<NetworkInterface> netInterfaces = null;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = netInterfaces.nextElement();
				if (ni.isLoopback() || ni.isVirtual() || !ni.isUp()) {
					continue;
				}

				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					return ips.nextElement().getHostAddress();
				}
			}

			return "127.0.0.1";
		} catch (Exception e) {
			return "127.0.0.1";
		}
	}
}
