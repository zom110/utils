package com.wodan.platform.foundation.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * MapWrapper
 * @ClassName: MapWrapper 
 * @author Administrator
 * @date 2015-1-17 上午11:17:42 
 * @history 
 * 
 * @param <K>
 * @param <V>
 */
public class MapWrapper<K, V> {
	/**
	 * map
	 */
	private final Map<K, V> map = new HashMap<K, V>();

	/**
	 * put
	 * @Description:
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {
		Assert.notNull(key, "key is  null");

		if (value == null) {
			return;
		}
		
		map.put(key, value);
	}
	
	/**
	 * getMap
	 * @Description:
	 * @return
	 */
	public Map<K, V> getMap() {
		return map;
	}
}
