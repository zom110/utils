package com.wodan.platform.foundation.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 通用反射类，继承自spring的反射类
 * 
 * @ClassName: ReflectionUtils
 * @author Administrator
 * @date 2015-11-5 下午3:44:42
 * @history
 * 
 */
public abstract class ReflectionUtils extends org.springframework.util.ReflectionUtils {
	/**
	 * 类方法的集合
	 */
	private static final Map<Class<?>, Set<Method>> CLASS_METHOD_SET_CACHE = Maps.newHashMap();

	/**
	 * 判断类中是否有对应方法
	 * 
	 * @Description:
	 * @param clazz
	 * @param method
	 * @return
	 */
	public static boolean isClassHasMethod(Class<?> clazz, Method method) {
		Assert.notNull(clazz);
		Assert.notNull(method);

		Set<Method> methodSet = CLASS_METHOD_SET_CACHE.get(clazz);
		if (methodSet == null) {
			methodSet = Sets.newHashSet(getAllDeclaredMethods(clazz));
		}
		return methodSet.contains(method);
	}

	/**
	 * 获取接口的泛型类型
	 */
	public static Class<?> getInterfaceGenericType(Class<?> clazz, int typeIndex) {
		Assert.notNull(clazz);
		Assert.isTrue(typeIndex >= 0);

		Type[] genericInterfaces = clazz.getGenericInterfaces();
		if (ArrayUtils.isEmpty(genericInterfaces)) {
			return null;
		}

		ParameterizedType pType = (ParameterizedType) genericInterfaces[0];
		Type[] actualTypeArguments = pType.getActualTypeArguments();
		if (ArrayUtils.isEmpty(actualTypeArguments)) {
			return null;
		}

		return (Class<?>) actualTypeArguments[typeIndex];
	}

	/**
	 * 获取类的泛型类型
	 * 
	 * @Description:
	 * @param clazz
	 * @param typeIndex
	 * @return
	 */
	public static Class<?> getClassGenericType(Class<?> clazz, int typeIndex) {
		Assert.notNull(clazz);
		Assert.isTrue(typeIndex >= 0);

		return getClassGenericType(clazz, typeIndex, 0);
	}

	public static Type getSuperClassType(Class<?> clazz, int typeIndex) {
		Assert.notNull(clazz);
		Assert.isTrue(typeIndex >= 0);
		
		ParameterizedType genericSuperclass = (ParameterizedType) clazz.getGenericSuperclass();
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		
		return actualTypeArguments[typeIndex];
	}

	/**
	 * 获取类的泛型类型
	 * 
	 * @Description:
	 * @param clazz
	 * @param typeIndex
	 * @return
	 */
	public static Class<?> getClassGenericType(Class<?> clazz, int typeIndex, int deps) {
		Assert.notNull(clazz);
		Assert.isTrue(typeIndex >= 0);
		Assert.isTrue(deps >= 0);

		ParameterizedType genericSuperclass = (ParameterizedType) clazz.getGenericSuperclass();
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();

		Type actualType = actualTypeArguments[typeIndex];
		if (actualType instanceof Class) {
			return (Class<?>) actualType;
		}

		for (int i = 0; i < deps; i++) {
			if (actualType instanceof Class) {
				return (Class<?>) actualType;
			} else {
				ParameterizedType pType = (ParameterizedType) actualType;
				actualType = pType.getActualTypeArguments()[0];
			}
		}

		return (Class<?>) ((ParameterizedType) actualType).getRawType();
	}

	/**
	 * 获取字段的泛型类型
	 */
	public static Class<?> getFieldGenericType(Class<?> clazz, String fieldName) {
		Assert.notNull(clazz);
		Assert.hasText(fieldName);

		Field field = findField(clazz, fieldName);
		if (field == null) {
			throw new NullPointerException("类[" + clazz + "]中不存在字段[" + fieldName + "]");
		}

		ParameterizedType genericType = (ParameterizedType) field.getGenericType();
		return (Class<?>) genericType.getRawType();
	}

	public static void doWithMethodParameterAnnotations(Method method, MethodParameterAnnotationCallback callback) {
		Assert.notNull(method);

		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < parameterAnnotations.length; i++) {
			Annotation[] annotations = parameterAnnotations[i];
			callback.doWith(annotations, i);
		}
	}

	public static interface MethodParameterAnnotationCallback {
		public void doWith(Annotation[] annotations, int paramIndex);
	}

	public static boolean isSubclassOf(Class<?> mapperInterface, Class<?> superClass) {
		Assert.notNull(mapperInterface);
		Assert.notNull(superClass);

		if (superClass.isAssignableFrom(mapperInterface)) {
			return true;
		} else {
			return false;
		}
	}
}
