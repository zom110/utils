package com.wodan.platform.foundation.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * 当出现异常后重试的工具框架
 * 
 * @ClassName: RetryWhenErrorUrils
 * @author Administrator
 * @date 2015-9-22 上午9:28:31
 * @history
 * 
 */
public class RetryWhenErrorUrils {
	private static final Logger LOGGER = LoggerFactory.getLogger(RetryWhenErrorUrils.class);

	/**
	 * 执行需要重试的操作
	 * 
	 * @Description:
	 * @param callback
	 */
	public static <T> T execute(final int retryCount, final long sleepInterval, final RetryCallback<T> callback) {
		Assert.notNull(callback);

		RuntimeException lastException = null;
		for (int i = 0; i < retryCount; i++) {
			try {
				return callback.doInExecute();
			} catch (RuntimeException ex) {
				LOGGER.error("执行redis请求时抛出异常", ex);
				lastException = ex;
				
				try {
					Thread.sleep(sleepInterval);
				} catch (InterruptedException e) {
				}
			}
		}

		throw lastException;
	}

	public static interface RetryCallback<T> {
		public T doInExecute();
	}
}
