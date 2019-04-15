package com.wodan.platform.foundation.util;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author fzdxstf
 * @ClassName: SerialNumberUtil
 * @Description: 创建订单号
 * @date 2015-9-15 下午5:02:45
 * @history
 */
public class SerialNumberUtil {
    // 1. 声明全局的counter
    private static AtomicLong counter = new AtomicLong(0);

    public static String createOrder() {

        // 2. 获取当前时间
        long currentTime = System.currentTimeMillis();

        // 3. 构造buffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.putLong(currentTime); // 前8个字节为当前时间
        buffer.putLong(counter.incrementAndGet()); // 后8个字节为订单序列号

        return HexUtil.bytesToHexString(buffer.array());
    }

    public static String createShortOrder() {
        // 2. 获取当前时间
        int currentTime = (int) (System.currentTimeMillis() / 1000);

        // 3. 构造buffer
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putInt(currentTime); // 前8个字节为当前时间
        buffer.putInt((int) counter.incrementAndGet()); // 后8个字节为订单序列号

        return HexUtil.bytesToHexString(buffer.array());
    }

    /**
     * 创建8位数订单号.
     *
     * @return 8 位数订单流水号
     */
    public static String createShort8Order() {
        // 构造buffer
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt((int) counter.incrementAndGet()); // 后8个字节为订单序列号

        return HexUtil.bytesToHexString(buffer.array());
    }

    public static void main(String[] args) {
        System.out.println(createShortOrder());
    }

}
