package com.sixh.spider.core.network;

import java.util.EventListener;

/**
 * 通道监听事件处理器.
 * CreateDate: 2017/1/17 9:29
 * @author :  chenbin
 */
public interface ChannelHandlerListener extends EventListener {
    /**
     * 事件处理器.
     *
     * @param future 通道结果信息
     * @throws Exception exception;
     */
    void operationComplete(MFuture future) throws Exception;
}
