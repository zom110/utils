package com.wodan.platform.foundation.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

/**
 * 不区分大小写的STRING
 * 
 * @ClassName: CaseInsensitiveString
 * @author Administrator
 * @date 2015-11-9 下午12:01:10
 * @history
 * 
 */
public final class CaseInsensitiveString implements Comparable<CaseInsensitiveString>{
	private final String origString;

	private final String lowercaseString;

	public CaseInsensitiveString(String str) {
		Assert.notNull(str);

		this.origString = str;
		this.lowercaseString = str.toLowerCase();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		return StringUtils.equalsIgnoreCase(obj.toString(), lowercaseString);
	}

	@Override
	public int hashCode() {
		return lowercaseString.hashCode();
	}

	@Override
	public String toString() {
		return origString;
	}

	public static CaseInsensitiveString valueOf(String str) {
		if (str == null) {
			return null;
		}
		
		return new CaseInsensitiveString(str);
	}
	
	@Override
	public int compareTo(CaseInsensitiveString o) {
		return this.lowercaseString.compareTo(o.lowercaseString);
	}
}
