package com.sixh.spider.core.network;

/**
 * 通道处理的handler.
 * CreateDate: 2017/1/17 9:25
 * @author :  chenbin
 */
public interface ChannelHandler {
    /**
     * 增加通道监听事件.
     *
     * @param listener 监听处理
     */
    void addListener(ChannelHandlerListener listener);
}
