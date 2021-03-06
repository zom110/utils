package com.wodan.platform.foundation.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wodan.platform.foundation.http.URLConnectionUtil;


/**
 * 根据ip获取城市信息
 * @ClassName: GetCityUtil 
 * @Description: 
 * @author Administrator
 * @date 2015-3-23 上午10:39:25 
 * @history 
 *
 */
public class GetCityUtils extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(GetCityUtils.class);
	
	/**
	 * 获取ip所在城市信息地址
	 */
	private static final String GET_CITY_BY_IP_URL = "http://www.ip138.com/ips138.asp";
	private static final String ENCODE = "gb2312";
	
	
	/**
	 * 根据网址：http://www.ip138.com/获取城市信息
	 * @param ip
	 * @return
	 */
	public static String getCityInfoByIp138(String ip) {
		
		String url = GET_CITY_BY_IP_URL+"?ip="+ip+"&action=2";
		System.out.println(url);
		try {
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:36.0) Gecko/20100101 Firefox/36.0");
			headers.put("Referer", "http://www.ip138.com/");
			
			Map<String, String> params = new HashMap<String, String>();
			String result = URLConnectionUtil.call(url,RequestMethod.GET, headers, params,ENCODE);
			
			Document jsoup = Jsoup.parse(result);
			Element select = jsoup.select("ul.ul1").select("li").first();
			if(select==null) {
				logger.error("根据ip获取城市信息网址出错，请求URL：{}, 返回内容：{}", null, result);
				return null;
			}
			String city = select.text();
			System.out.println(city);
			if(StringUtils.isNotBlank(city)) {
				int index = city.indexOf('市');
				if(index == -1) {
					return "";
				}
				int startIndex = index > 2 ? index - 2 : 0;
				city = city.substring(startIndex, index);
			}
			return city;
			
		} catch (IOException ex) {
			logger.error("获取城市信息出现异常", ex.getMessage());
			return null;
		}
	}
	
	
	/**
	 * 根据新浪ip接口获取城市信息
	 * @Description:
	 * @param ip
	 * @return
	 */
	public static String getCityInfoBySina(String ip) {
		String url = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip="+ip;
		// System.out.println(url);
		try {
			Map<String, String> headers = new HashMap<String, String>();
			Map<String, String> params = new HashMap<String, String>();
			
			String result = URLConnectionUtil.call(url,RequestMethod.GET, headers, params,ENCODE);
			// System.out.println(result);
			JSONObject cityJsonObj;
			try {
				cityJsonObj = JSON.parseObject(result);
			} catch (Exception e) {
				logger.error("从新浪获取该IP城市信息失败, IP为:" + ip);
				return null;
			}
			Integer ret = cityJsonObj.getInteger("ret");
			if(ret != 1) {
				logger.error("从新浪获取该IP城市信息失败, IP为:" + cityJsonObj.getString("ip"));
				return null;
			}
			String city = cityJsonObj.getString("city");
			System.out.println(city);
			if(city.indexOf('市') == -1) {
				return "";
			}
			city = city.substring(0, city.indexOf('市'));
			return city;
		} catch (IOException ex) {
			logger.error("从新浪获取城市信息出现异常", ex.getMessage());
			return null;
		}
	}

	
	/**
	 * 根据淘宝ip接口获取城市信息
	 * @Description:
	 * @param ip
	 * @return
	 */
	public static String getCityInfoByTaobao(String ip) {
		String url = "http://ip.taobao.com/service/getIpInfo.php?ip="+ip;
		try {
			Map<String, String> headers = new HashMap<String, String>();
			Map<String, String> params = new HashMap<String, String>();
			
			String result = URLConnectionUtil.call(url,RequestMethod.GET, headers, params,ENCODE);
			JSONObject cityJsonObj = JSON.parseObject(result);
			Integer code = cityJsonObj.getInteger("code");
			if(code != 0) {
				logger.error("从淘宝获取该IP城市信息失败, IP为:" + cityJsonObj.getString("ip"));
				return null;
			}
			String city = cityJsonObj.getJSONObject("data").getString("city");
			if(city.indexOf('市') == -1) {
				return "";
			}
			city = city.substring(0, city.indexOf('市'));
			return city;
		} catch (Exception ex) {
			logger.error("从淘宝获取该IP城市信息出现异常", ex.getMessage());
			return null;
		}
	}

	
	/**
	 * 根据ip获取城市信息
	 * @Description:
	 * @param ip
	 * @return
	 */
	public static String getCityInfoByIp(String ip) {
		String city = getCityInfoByIp138(ip);
		
		if(StringUtils.isNotBlank(city)) {
			return city;
		}
		
		city = getCityInfoBySina(ip);
		
		if(StringUtils.isNotBlank(city)) {
			return city;
		}
		
		city = getCityInfoByTaobao(ip);
		return city;
	}
	
	public static void main(String[] args) {
		//System.out.println(getCityInfoByphp("27.151.193.166")); 
		//System.out.println(getCityInfo("202.99.240.0")); //27.152.193.166
//		System.out.println(getCityInfoBySina("27.152.193.166")); //27.152.193.166
//		System.out.println(getCityInfoByIp("101.226.12.223")); //27.152.193.166
		
//		System.out.println(getCityInfoByTaobao("101.226.12.223")); //27.152.193.166
		
//		new GetCityUtil(1, "27.152.193.166").start();
//		new GetCityUtil(2, "27.152.193.166").start();
//		new GetCityUtils(3, "27.152.193.166").start();
		
	}
	
	int type;
	String ip;
	
	String[] ipArray = { "1.179.133.66", "1.227.56.236", "1.255.53.81", "1.9.21.21", "101.1.16.123", "101.226.12.223",
			"101.226.249.237", "101.227.252.130", "101.255.64.22", "101.255.74.58", "101.255.82.66", "101.4.136.100",
			"101.4.136.101", "101.4.136.103", "101.4.136.104", "101.4.136.2", "101.4.136.3", "101.4.136.5",
			"101.4.136.6", "101.4.136.66", "101.4.136.66", "101.4.136.66", "101.4.136.66", "101.4.60.43",
			"101.4.60.46", "101.4.60.47", "101.69.199.99", "101.71.27.120", "103.12.163.250", "103.17.36.33",
			"103.225.174.159", "103.239.54.190", "103.245.196.185", "103.249.100.18", "103.25.166.107",
			"103.25.179.12", "103.30.84.90", "103.30.90.206", "103.9.185.57", "105.235.193.113", "106.37.177.251",
			"106.38.251.62", "107.150.96.210", "107.182.17.149", "107.182.17.149", "108.165.33.3", "109.104.207.120",
			"109.123.99.100", "109.196.127.35", "109.73.185.16", "110.173.0.58", "110.173.0.58", "110.74.195.123",
			"110.75.120.170", "111.1.36.163", "111.1.36.164", "111.1.36.166", "111.1.36.167", "111.1.36.6",
			"111.11.255.11", "111.12.251.199", "111.13.109.52", "111.13.136.58", "111.13.136.58", "111.13.136.59",
			"111.161.126.100", "111.161.126.101", "111.161.126.98", "111.161.126.99", "111.206.1.194",
			"111.206.133.16", "111.206.133.16", "111.206.87.5", "111.40.196.68", "111.40.196.68", "111.47.92.226",
			"111.8.9.165", "111.85.83.61", "112.112.11.82", "112.126.72.101", "112.126.80.177", "112.150.156.223",
			"112.250.70.50", "112.5.254.172", "112.84.130.11", "112.84.130.14", "112.84.130.18", "112.84.130.2",
			"112.84.130.31", "112.90.146.76", "112.91.208.78", "112.95.244.57", "113.105.224.66", "113.105.224.86",
			"113.105.224.87", "113.105.224.95", "113.105.4.93", "113.106.93.42", "113.107.56.252", "113.107.56.253",
			"113.107.56.97", "113.107.57.76", "113.107.57.76", "113.108.141.98", "113.140.25.4", "113.142.37.248",
			"113.176.61.11", "113.252.184.101", "113.253.44.78", "113.53.230.154", "114.112.91.97", "114.113.31.4",
			"114.141.47.173", "114.215.100.170", "114.215.108.155", "114.215.150.13", "114.215.151.133",
			"114.242.166.216", "114.255.183.163", "114.255.183.164", "114.255.183.173", "114.255.183.174",
			"114.255.183.189", "114.32.114.10", "114.32.160.30", "114.32.160.30", "114.80.182.132", "115.231.188.113",
			"115.231.96.120", "115.238.225.26", "115.238.55.91", "115.28.11.130", "115.28.137.189", "115.28.137.244",
			"115.28.198.131", "115.28.23.36", "115.28.236.172", "115.28.85.240", "115.29.151.135", "115.68.30.221",
			"116.113.17.6", "116.197.135.58", "116.212.129.38", "116.228.55.217", "116.236.216.116", "116.51.12.146",
			"117.102.82.67", "117.117.139.4", "117.135.244.45", "117.146.116.2", "117.158.1.210", "117.177.240.43",
			"117.177.243.100", "117.177.243.102", "117.177.243.103", "117.177.243.104", "117.177.243.105",
			"117.177.243.108", "117.177.243.109", "117.177.243.110", "117.177.243.14", "117.177.243.2",
			"117.177.243.3", "117.177.243.30", "117.177.243.35", "117.177.243.36", "117.177.243.37", "117.177.243.38",
			"117.177.243.39", "117.177.243.4", "117.177.243.40", "117.177.243.41", "117.177.243.43", "117.177.243.46",
			"117.177.243.47", "117.177.243.53", "117.177.243.54", "117.177.243.55", "117.177.243.56", "117.177.243.57",
			"117.177.243.58", "117.177.243.67", "117.177.243.68", "117.177.243.70", "117.177.243.71", "117.177.243.72",
			"117.177.243.73", "117.177.243.74", "117.177.243.75", "117.177.243.76", "117.177.243.77", "117.177.243.79",
			"117.41.182.188", "117.74.121.187", "118.193.11.38", "118.193.146.138", "118.69.226.241", "118.70.13.161",
			"118.97.255.106", "119.110.73.130", "119.18.153.246", "119.187.113.218", "119.188.94.145", "119.90.127.2",
			"120.131.128.211", "120.131.128.213", "120.132.52.84", "120.192.86.254", "120.192.92.98", "120.192.92.99",
			"120.193.146.93", "120.193.146.93", "120.193.146.93", "120.197.234.166", "120.202.249.230",
			"120.202.53.50", "120.203.214.206", "120.236.148.113", "120.236.33.10", "120.25.202.216", "120.25.213.94",
			"120.26.71.96", "121.10.252.139", "121.101.214.160", "121.14.138.56", "121.14.4.111", "121.199.30.110",
			"121.199.60.143", "121.40.181.123", "121.40.92.146", "121.41.20.229", "121.42.29.118", "121.8.69.162",
			"122.136.46.151", "122.136.46.151", "122.141.243.131", "122.225.106.35", "122.225.106.36",
			"122.225.106.40", "122.225.117.26", "122.228.92.103", "122.228.92.103", "122.228.92.73", "122.228.92.73",
			"122.228.92.73", "122.72.124.42", "122.72.22.199", "122.72.33.237", "122.72.4.215", "122.72.62.60",
			"122.96.59.106", "122.96.59.106", "122.96.59.106", "122.96.59.106", "122.96.59.106", "123.124.168.149",
			"123.125.1.52", "123.125.104.240", "123.200.24.78", "123.30.95.175", "123.57.68.233", "123.58.129.48",
			"123.59.52.241", "124.118.87.156", "124.126.126.101", "124.126.126.103", "124.127.123.48",
			"124.158.74.156", "124.161.94.8", "124.202.168.26", "124.202.169.50", "124.202.175.70", "124.202.177.26",
			"124.202.179.150", "124.202.179.154", "124.202.181.146", "124.202.182.42", "124.248.205.25",
			"124.248.205.25", "125.39.66.149", "125.39.66.150", "125.39.66.68", "125.62.22.47", "125.88.75.151",
			"130.94.148.99", "138.94.96.3", "139.255.41.130", "14.139.172.170", "14.140.151.30", "14.140.222.220",
			"14.161.15.23", "14.29.80.34", "14.63.165.60", "150.107.137.60", "150.241.228.5", "157.7.48.92",
			"162.208.49.45", "162.208.49.45", "162.248.53.68", "165.138.124.4", "166.70.97.138", "173.237.197.138",
			"175.196.24.120", "175.43.16.177", "176.31.21.245", "177.126.89.27", "177.55.253.36", "177.55.253.68",
			"177.69.56.130", "177.71.49.25", "177.86.24.194", "180.210.48.130", "180.240.233.50", "181.110.248.53",
			"181.177.234.206", "181.52.246.30", "181.88.177.145", "182.140.132.108", "182.140.237.82",
			"182.253.33.174", "182.253.73.115", "182.74.40.46", "182.92.148.71", "182.92.240.197", "182.92.64.30",
			"183.131.144.204", "183.131.144.204", "183.136.135.153", "183.203.208.163", "183.203.208.164",
			"183.203.208.165", "183.203.208.168", "183.203.208.173", "183.203.208.177", "183.207.224.13",
			"183.207.224.14", "183.207.224.15", "183.207.224.42", "183.207.224.43", "183.207.224.44", "183.207.224.45",
			"183.207.224.50", "183.207.224.50", "183.207.224.51", "183.207.224.51", "183.207.227.16",
			"183.207.228.115", "183.207.228.116", "183.207.228.118", "183.207.228.119", "183.207.228.22",
			"183.207.228.22", "183.207.228.22", "183.207.228.41", "183.207.228.42", "183.207.228.43", "183.207.228.44",
			"183.207.228.51", "183.207.228.56", "183.207.228.57", "183.207.228.60", "183.207.229.200",
			"183.207.232.119", "183.207.232.193", "183.207.232.194", "183.218.63.168", "183.218.63.179",
			"183.224.1.30", "183.224.12.81", "183.224.12.82", "183.230.53.169", "183.238.133.43", "183.60.126.65",
			"183.60.187.55", "183.62.255.58", "183.62.58.250", "183.62.60.100", "183.63.116.54", "183.63.81.66",
			"185.28.193.95", "185.33.33.133", "185.37.226.106", "186.208.232.98", "186.224.64.222", "186.232.160.11",
			"186.250.117.26", "186.46.162.246", "187.217.191.162", "187.6.252.146", "188.165.223.173",
			"188.165.223.173", "188.165.223.173", "188.247.43.30", "189.112.179.98", "189.21.214.78", "189.39.115.54",
			"189.58.248.110", "190.0.33.18", "190.121.158.54", "190.128.238.38", "190.131.225.123", "190.204.244.196",
			"190.206.85.91", "190.208.45.221", "190.221.29.214", "194.213.60.227", "195.154.231.43", "195.186.81.204",
			"195.186.81.92", "195.186.81.94", "195.186.81.96", "195.191.78.1", "195.8.126.22", "197.234.32.158",
			"198.35.28.3", "199.115.114.237", "199.189.84.217", "199.200.120.36", "199.200.120.36", "199.200.120.37",
			"199.200.120.37", "2.232.249.21", "200.169.142.73", "200.178.118.136", "200.192.211.14", "200.192.253.151",
			"200.203.146.69", "200.217.7.170", "200.25.236.128", "200.55.254.187", "201.20.182.35", "201.211.202.196",
			"201.211.202.92", "201.211.208.132", "201.221.133.86", "201.59.174.194", "201.72.98.244", "202.101.96.154",
			"202.102.22.182", "202.106.16.36", "202.106.169.142", "202.107.233.85", "202.114.144.15", "202.119.25.227",
			"202.119.25.228", "202.119.25.69", "202.119.25.70", "202.119.25.71", "202.119.25.73", "202.134.6.227",
			"202.145.3.242", "202.150.157.34", "202.153.130.214", "202.154.190.150", "202.171.253.102",
			"202.28.120.220", "202.75.214.11", "202.75.216.235", "202.96.247.113", "202.99.16.28", "203.110.160.14",
			"203.114.104.82", "203.192.12.214", "204.27.58.202", "206.17.20.75", "209.66.192.149", "209.66.192.149",
			"210.101.131.231", "210.101.131.232", "210.209.84.155", "210.39.18.236", "211.138.121.38",
			"211.138.121.38", "211.138.121.38", "211.141.130.108", "211.141.130.109", "211.141.130.254",
			"211.144.72.153", "211.144.76.58", "211.144.81.68", "211.144.81.69", "211.22.230.8", "212.13.49.186",
			"212.56.208.154", "218.104.144.248", "218.161.34.107", "218.240.131.12", "218.240.156.82", "218.25.37.108",
			"218.28.96.39", "218.4.236.117", "218.71.136.39", "218.75.205.58", "218.78.210.190", "218.90.174.167",
			"219.133.31.120", "219.137.229.146", "219.142.192.196","219.217.227.93",
			"219.223.190.90", "220.181.143.14", "220.248.41.106", "221.10.102.199",  "221.10.102.203", 
			 "221.176.14.72", "221.181.6.52", "221.181.73.45", "221.182.134.20", "221.229.125.86",
			"221.5.69.51", "221.5.69.51", "222.124.219.215", "222.124.29.244", "222.222.51.133", "222.45.18.130",
			"222.45.194.122", "222.45.195.34", "222.45.196.17", "222.45.212.130", "222.45.85.210", "222.59.246.38",
			"222.92.141.250", "223.68.6.10", "223.87.3.145", "223.99.254.99", "23.110.53.93", "31.15.48.12",
			"31.217.218.36", "36.250.69.4", "36.55.231.3", "36.85.91.95", "37.187.145.80", "37.187.53.74",
			"37.216.235.242", "37.247.111.113", "37.49.137.243", "37.49.137.243", "37.59.101.237", "41.184.34.98",
			"41.191.235.86", "41.205.231.202", "41.72.105.38", "41.75.201.146", "41.78.91.203", "41.79.207.255",
			"41.89.96.36", "42.121.105.155", "42.96.162.252", "45.64.136.154", "45.64.9.126", "46.165.239.102",
			"46.243.114.226", "46.245.59.111", "46.32.10.98", "46.32.4.138", "5.135.176.41", "5.135.193.216",
			"5.135.193.216", "5.149.106.196", "5.160.31.155", "5.56.60.234", "5.56.61.185", "5.56.61.185",
			"5.56.61.185", "5.56.61.185", "5.56.61.186", "5.56.61.186", "5.56.61.186", "5.56.61.186", "5.56.61.186",
			"5.56.61.186", "5.56.62.137", "54.246.92.203", "58.132.109.186", "58.152.34.72", "58.213.48.108",
			"58.214.5.229", "58.215.187.2", "58.220.2.130", "58.220.2.133", "58.220.2.134", "58.220.2.135",
			"58.220.2.136", "58.220.2.137", "58.220.2.138", "58.220.2.139", "58.220.2.140", "58.220.2.141",
			"58.220.2.142", "58.220.2.145", "58.220.2.148", "58.220.2.153", "58.220.2.156", "58.221.85.182",
			"58.240.227.204", "58.240.65.57", "58.246.199.122", "58.251.132.181", "58.251.78.71", "58.252.167.103",
			"58.252.69.194", "58.252.72.179", "58.253.238.242", "58.253.238.243", "58.52.195.3", "58.57.73.142",
			"58.59.68.91", "58.64.200.58", "58.67.159.50", "58.96.174.204", "58.96.177.117", "58.96.179.244",
			"58.96.179.83", "59.108.4.32", "59.127.178.95", "59.39.177.199", "59.41.239.13", "59.44.152.110",
			"59.48.101.158", "59.56.75.20", "59.58.162.141", "59.61.79.124", "60.12.8.8", "60.18.147.42",
			"60.182.137.76", "60.195.3.180", "60.209.20.90", "60.29.248.142", "61.106.209.114", "61.133.51.6",
			"61.136.213.3", "61.149.182.102", "61.152.102.40", "61.152.102.40", "61.152.102.40", "61.153.21.219",
			"61.154.127.136", "61.156.3.166", "61.184.192.42", "61.19.30.198", "61.237.162.119", "62.182.109.5",
			"62.201.200.5", "62.201.200.5", "62.205.34.88", "62.209.225.107", "62.210.71.217", "64.186.47.179",
			"64.186.47.179", "72.10.15.121", "72.159.148.20", "74.94.168.113", "77.78.31.3", "77.88.210.116",
			"78.153.4.24", "80.11.120.105", "80.87.84.186", "81.163.88.18", "81.30.200.40", "82.117.163.74",
			"82.151.117.162", "82.223.148.124", "82.223.148.124", "82.223.148.124", "83.244.121.250", "84.11.252.150",
			"84.22.35.37", "84.236.134.2", "84.253.75.6", "85.194.75.18", "85.194.85.122", "85.70.179.49",
			"86.101.99.153", "88.225.232.158", "88.82.172.237", "89.191.131.243", "89.218.144.4", "89.23.196.60",
			"89.234.199.60", "89.46.101.178", "90.183.169.77", "91.108.158.129", "91.118.154.14", "91.204.173.237",
			"91.204.173.237", "91.75.84.28", "92.222.14.156", "92.222.46.207", "92.87.49.1", "93.57.87.142",
			"94.23.205.32", "94.23.23.60", "94.23.59.45", "95.0.124.58", "95.168.217.24", "95.178.106.99",
			"99.185.0.108" };
	
	public GetCityUtils(int type, String ip) {
		this.type = type;
		this.ip = ip;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("type=" + type + ".线程开始执行");
		long currentTimeMillis = System.currentTimeMillis();
		for (int i = 0; i < ipArray.length; i++) {
			try {
				if(type == 1) {
					getCityInfoByTaobao(ipArray[i]);
				} else if(type == 2) {
					getCityInfoBySina(ipArray[i]);
				} else if(type == 3) {
					getCityInfoByIp138(ipArray[i]);
				}
//				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		long time = System.currentTimeMillis() - currentTimeMillis;
		System.out.println("type=" + type + "执行时长:" + time);
	}
}
