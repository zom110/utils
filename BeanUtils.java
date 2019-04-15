package com.wodan.platform.foundation.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.wodan.platform.foundation.bytesconverter.ConverterUtils;

/**
 * 自带的BeanUtils，继承自spring
 * 
 * @ClassName: BeanUtils
 * @author Administrator
 * @date 2015-1-10 上午11:39:28
 * @history
 * 
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {
	/**
	 * 将bean转换为map
	 * 
	 * @Description:
	 * @param bean
	 * @return
	 */
	public static Map<String, String> bean2Map(Object bean) {
		Assert.notNull(bean, "bean is null");

		try {
			Map<String, String> resultMap = new HashMap<String, String>();

			Class<? extends Object> clazz = bean.getClass();
			PropertyDescriptor[] propertyDescriptors = getPropertyDiscriptors(clazz);
			for (PropertyDescriptor property : propertyDescriptors) {
				String name = property.getName();
				if ("class".equals(name)) {
					continue;
				}

				Field field = clazz.getDeclaredField(name);
				if (Modifier.isTransient(field.getModifiers())) {
					continue;
				}

				Method readMethod = property.getReadMethod();

				Object valueObj = readMethod.invoke(bean);
				if (valueObj == null) {
					continue;
				}

				resultMap.put(name, ConverterUtils.objectToString(valueObj));
			}

			return resultMap;

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * getPropertyDiscriptors
	 * 
	 * @Description:
	 * @param clazz
	 * @return
	 * @throws IntrospectionException
	 */
	private static PropertyDescriptor[] getPropertyDiscriptors(Class<?> clazz) throws IntrospectionException {
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		return propertyDescriptors;
	}

	/**
	 * map 转换为bean
	 * 
	 * @Description:
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static <T> T map2Bean(Map<String, String> map, Class<T> clazz) {
		try {
			T instance = clazz.newInstance();

			PropertyDescriptor[] propertyDiscriptors = getPropertyDiscriptors(clazz);
			for (PropertyDescriptor property : propertyDiscriptors) {
				String name = property.getName();
				if ("class".equals(name)) {
					continue;
				}

				Field field = clazz.getDeclaredField(name);
				if (Modifier.isTransient(field.getModifiers())) {
					continue;
				}

				Method writeMethod = property.getWriteMethod();

				String str = map.get(name);
				if (str == null) {
					continue;
				}

				Object valueObj = ConverterUtils.stringToObject(str, property.getPropertyType());
				writeMethod.invoke(instance, valueObj);
			}

			return instance;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 检查字段是否为空
	 * 
	 * @Description:
	 * @param bean
	 * @param notNullFields
	 * @return
	 */
	public static List<String> checkNotNull(Object bean, String... notNullFields) {
		Class<? extends Object> clazz = bean.getClass();
		PropertyDescriptor[] propertyDiscriptors;
		try {
			propertyDiscriptors = getPropertyDiscriptors(clazz);
			for (PropertyDescriptor property : propertyDiscriptors) {
				String name = property.getName();
				if ("class".equals(name)) {
					continue;
				}

				if (ArrayUtils.contains(notNullFields, name)) {
					Method readMethod = property.getReadMethod();
					Object value = readMethod.invoke(bean);
					if (ConverterUtils.isNullOrEmpty(value)) {
						List<String> result = new ArrayList<String>();
						result.add(name);
						return result;
					}
				}
			}

			return Collections.emptyList();
		} catch (IntrospectionException ex) {
			throw new RuntimeException(ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 用反射方式从bean中抽取id字段
	 * 
	 * @Description:
	 * @param beanList
	 * @param attrName
	 * @return
	 */
	public static final List<Long> extractIdFromBeanList(Collection<?> beanList, String... fieldNames) {
		return extractFieldFromBeanList(beanList, fieldNames);
	}

	/**
	 * 用反射方式抽取string字段
	 * 
	 * @Description:
	 * @param beanList
	 * @param fieldNames
	 * @return
	 */
	public static final List<String> extractStringFromBeanList(Collection<?> beanList, String... fieldNames) {
		return extractFieldFromBeanList(beanList, fieldNames);
	}

	/**
	 * 用反射方式从bean中抽取id字段
	 * 
	 * @Description:
	 * @param beanList
	 * @param attrName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static final <T> List<T> extractFieldFromBeanList(Collection<?> beanList, String... fieldNames) {
		if (CollectionUtils.isEmpty(beanList)) {
			return Collections.emptyList();
		}

		try {
			Set<Method> readMethodList = new HashSet<Method>();
			for (String fieldName : fieldNames) {
				PropertyDescriptor propertyDescriptor = getPropertyDescriptor(beanList.iterator().next().getClass(),
						fieldName);
				Method readMethod = propertyDescriptor.getReadMethod();

				readMethodList.add(readMethod);
			}

			Set<T> resultList = new HashSet<T>();
			for (Object obj : beanList) {
				for (Method method : readMethodList) {
					Object value = method.invoke(obj);
					if (value != null) {
						resultList.add((T) value);
					}
				}
			}

			return new ArrayList<T>(resultList);
		} catch (SecurityException ex) {
			throw new RuntimeException(ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		} catch (IllegalArgumentException ex) {
			throw new RuntimeException(ex);
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 根据key将list转换为map
	 * 
	 * @Description:
	 * @param beanList
	 * @param keyFieldName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> list2Map(List<V> beanList, String keyFieldName) {
		if (CollectionUtils.isEmpty(beanList)) {
			return Maps.newHashMap();
		}
		
		try {
			PropertyDescriptor propertyDescriptor = getPropertyDescriptor(beanList.get(0).getClass(), keyFieldName);
			Method readMethod = propertyDescriptor.getReadMethod();

			Map<K, V> map = new HashMap<K, V>();
			for (V bean : beanList) {
				Object key = readMethod.invoke(bean);

				map.put((K) key, bean);
			}

			return map;
		} catch (SecurityException ex) {
			throw new RuntimeException(ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		} catch (IllegalArgumentException ex) {
			throw new RuntimeException(ex);
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 根据key将list转换为map
	 * 
	 * @Description:
	 * @param beanList
	 * @param keyFieldName
	 * @return
	 */
	public static <K extends Comparable<K>, V, E> Map<K, V> list2Map(List<E> beanList, List2MapFieldExtractor<K, V, E> extractor) {
		if (CollectionUtils.isEmpty(beanList)) {
			return Collections.emptyMap();
		}
		Assert.notNull(extractor);

		Map<K, V> resultMap = Maps.newTreeMap();
		for (E bean : beanList) {
			K key = extractor.getKey(bean);
			if (key == null) {
				continue;
			}
			V value = extractor.getValue(bean);
			if (value == null) {
				continue;
			}
			resultMap.put(key, value);
		}
		return resultMap;
	}

	/**
	 * 根据key将list转换为map
	 * 
	 * @Description:
	 * @param beanList
	 * @param keyFieldName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K extends Comparable<K>, V> Map<K, List<V>> list2ListMap(List<V> beanList, String keyFieldName) {
		if (CollectionUtils.isEmpty(beanList)) {
			return Collections.emptyMap();
		}
		try {
			PropertyDescriptor propertyDescriptor = getPropertyDescriptor(beanList.get(0).getClass(), keyFieldName);
			Method readMethod = propertyDescriptor.getReadMethod();

			Map<K, List<V>> map = Maps.newTreeMap();
			for (V bean : beanList) {
				Object key = readMethod.invoke(bean);
				List<V> valueList = map.get(key);
				if (valueList == null) {
					valueList = new ArrayList<V>();
					map.put((K) key, valueList);
				}

				valueList.add(bean);
			}

			return map;
		} catch (SecurityException ex) {
			throw new RuntimeException(ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		} catch (IllegalArgumentException ex) {
			throw new RuntimeException(ex);
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 根据key将list转换为map
	 * 
	 * @Description:
	 * @param beanList
	 * @param keyFieldName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, Long> list2CountMap(List<V> beanList, String keyFieldName) {
		if (CollectionUtils.isEmpty(beanList)) {
			return Collections.emptyMap();
		}
		try {
			PropertyDescriptor propertyDescriptor = getPropertyDescriptor(beanList.get(0).getClass(), keyFieldName);
			Method readMethod = propertyDescriptor.getReadMethod();

			Map<K, Long> map = Maps.newHashMap();
			for (V bean : beanList) {
				Object key = readMethod.invoke(bean);
				Long count = map.get(key);
				if (count == null) {
					map.put((K) key, Long.valueOf(1));
				} else {
					map.put((K) key, Long.valueOf(count.longValue() + 1));
				}
			}

			return map;
		} catch (SecurityException ex) {
			throw new RuntimeException(ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		} catch (IllegalArgumentException ex) {
			throw new RuntimeException(ex);
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 合并bean中的非空字段
	 * 
	 * @Description:
	 * @param src
	 * @param target
	 */
	public static void mergeBeanIfNotNull(Object src, Object target) {
		Assert.notNull(src);
		Assert.notNull(target);
		Assert.isTrue(src.getClass().equals(target.getClass()));

		try {
			PropertyDescriptor[] propertyDescriptors = getPropertyDescriptors(src.getClass());
			for (PropertyDescriptor pd : propertyDescriptors) {
				Method readMethod = pd.getReadMethod();

				Object targetValue = readMethod.invoke(target);
				if (targetValue != null) {
					continue;
				}

				Object srcValue = readMethod.invoke(src);
				if (srcValue == null) {
					continue;
				}

				Method writeMethod = pd.getWriteMethod();
				writeMethod.invoke(target, new Object[] { srcValue });

			}
		} catch (InvocationTargetException ex) {
			throw new RuntimeException(ex);
		} catch (IllegalAccessException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * list转成map的字段抽取器
	 * 
	 * @ClassName: List2MapExtractor
	 * @Description: 
	 * @author Administrator
	 * @date 2015-8-15 上午11:11:17
	 * @history
	 * 
	 */
	public static interface List2MapFieldExtractor<K, V, E> {
		/**
		 * 抽取key
		 * 
		 * @Description:
		 * @return
		 */
		K getKey(E bean);

		/**
		 * 抽取value
		 * 
		 * @Description:
		 * @return
		 */
		V getValue(E bean);
	}
}
