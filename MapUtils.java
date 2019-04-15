package com.wodan.platform.foundation.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @ClassName: MapUtils
 * @Description: map处理工具类
 * @author sutf
 * @date 2016-7-8 上午10:27:10 
 * @history 
 *
 */
public class MapUtils {
	
	/**
	 * 工具类，不可实例化
	 */
	private MapUtils(){
		throw new IllegalAccessError("Utility class, 请不要实例化我");
	}
    
    /**
     * 过来map值为空的参数
     * @Description:
     * @param formParams
     * @return
     */
    public static Map<String, Object> filterMap(Map<String, String> formParams) {
        
        Map<String, Object> queryMap = new HashMap<>();
        
        // 过滤下空条件
        for (Entry<String, String> entry : formParams.entrySet()) {
            if(StringUtils.isNotBlank(entry.getValue())){
                queryMap.put(entry.getKey(), entry.getValue());
            }
        }
        
        return queryMap;
    }
    
    
    
    
}
