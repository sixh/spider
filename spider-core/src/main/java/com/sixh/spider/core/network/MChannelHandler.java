package com.sixh.spider.core.network;

/**
 * 通道处理的handler.
 * CreateDate: 2017/1/17 9:25
 *
 * @author :  chenbin
 */
@SuppressWarnings("all")
public interface MChannelHandler {
    /**
     * Connected.
     */
    void connected();

    /**
     * Disconnected.
     */
    void disconnected();

    /**
     * Caught.
     */
    void caught();
}
