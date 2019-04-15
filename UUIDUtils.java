package com.wodan.platform.foundation.util;

import java.util.UUID;

public class UUIDUtils {

	public static final String create() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 去除uuid中的横线
	 * 
	 * @Description:
	 * @return
	 */
	public static final String replaceLine(String uuid) {
		return uuid.replace("-", "");
	}

	/**
	 * 恢复uuid中的横线
	 * 
	 * @Description:
	 * @param uuid
	 * @return
	 */
	public static final String addLine(String uuid) {

		StringBuilder newUUID = new StringBuilder();
		newUUID.append(uuid.substring(0, 8));
		newUUID.append("-");
		newUUID.append(uuid.substring(8, 12));
		newUUID.append("-");
		newUUID.append(uuid.substring(12, 16));
		newUUID.append("-");
		newUUID.append(uuid.substring(16, 20));
		newUUID.append("-");
		newUUID.append(uuid.substring(20, 32));
		return newUUID.toString();

	}

	// public static void main(String[] args) {
	// String uuid = "7e348474-ede5-4c0e-bb8d-ed1057f70691";
	// String uuid2 = replaceLine(uuid);
	//
	// System.out.println(uuid);
	// System.out.println(uuid2);
	// System.out.println(addLine(uuid2));
	// }

}
