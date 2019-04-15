package com.wodan.platform.foundation.util;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.util.Assert;

/**
 * 数组构建器
 * 
 * @ClassName: ArrayBuilder
 * @author Administrator
 * @date 2015-11-6 下午2:17:46
 * @history
 * 
 * @param <T>
 */
public class ArrayBuilder<T> {
	private final T[] array;

	private final AtomicInteger index = new AtomicInteger(-1);

	@SuppressWarnings("unchecked")
	public ArrayBuilder(Class<T> clazz, int size) {
		Assert.notNull(clazz);
		Assert.isTrue(size > 0);

		this.array = (T[]) java.lang.reflect.Array.newInstance(clazz, size);
	}

	public void append(T element) {
		Assert.notNull(element);

		array[index.incrementAndGet()] = element;
	}
	
	public T[] build() {
		return array;
	}
}
