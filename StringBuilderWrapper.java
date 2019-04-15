package com.wodan.platform.foundation.util;

public class StringBuilderWrapper {
	private final StringBuilder sb = new StringBuilder();

	public void append(Object obj) {
		if(obj == null) {
			return;
		}
		sb.append(obj);
	}
	
	@Override
	public String toString() {
		return sb.toString();
	}
}
