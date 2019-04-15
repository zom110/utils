package com.wodan.platform.foundation.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wodan.platform.foundation.http.URLConnectionUtil;

public class IdCardNoVerifyUtils {
	
//	private static Logger LOGGER = LoggerFactory.getLogger(IdCardNoVerifyUtils.class);
	
	private static final String SERVICE_URL = "http://api.id98.cn/api/idcard";
	private static final String APP_KEY = "cf132e9866a3f9f0d478519aafac7c7c";
	private static final String OUPUT = "json";// 序列号
	
	/**
	 * 工具类，不可实例化
	 */
	private IdCardNoVerifyUtils() {
	    throw new IllegalAccessError("Utility class, 请不要实例化我");
	} 

	public static JSONObject check(String idCardNo, String name) {

		String str = "";
		try {
			Map<String, String> appParamsMap = new HashMap<>();
			appParamsMap.put("cardno", idCardNo);
			appParamsMap.put("output", OUPUT);
			appParamsMap.put("appkey", APP_KEY);
			appParamsMap.put("name", name);

			str = URLConnectionUtil.call(SERVICE_URL, RequestMethod.POST, appParamsMap);

			return JSON.parseObject(str);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean isFamale(String id) {
		String sex = id.substring(16, 17);
		return Integer.parseInt(sex) % 2 == 0;
	}

	public static String getBirtyday(String id) {
		StringBuilder sb = new StringBuilder();
		sb.append(id.subSequence(6, 10)).append("-").append(id.substring(10, 12)).append("-")
				.append(id.substring(12, 14));
		return sb.toString();
	}

//	public static void main(String[] args) throws Exception {
//		JSONObject r = JSON
//				.parseObject("{\"data\":{\"birthday\":\"1990-09-20\",\"sex\":\"M\",\"address\":\"福建省泉州市安溪县\",\"err\":0},\"code\":1,\"isok\":1}");
//		Integer code = (Integer) r.get("code");
//		LOGGER.error(code.toString());
//	}

}
