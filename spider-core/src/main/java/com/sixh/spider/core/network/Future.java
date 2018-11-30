package com.sixh.spider.core.network;

/**
 * 通道回调future.
 * CreateDate: 2017/1/17 9:29
 * @author : chenbin
 */
public interface Future {
    /**
     * 是否发送数据成功.
     * @return true 是，false 否
     */
    boolean isSuccessfully();

    /**
     * 发送异常.
     * @return 异常信息
     */
    Throwable cause();

    /**
     * 获取当前通道.
     * @return channel
     */
    Channel getChannel();
}
