package com.sixh.spider.core.network;

import com.sixh.spider.common.exception.RemotingException;

/**
 * Transport.
 * <p>
 * 数据传输点．
 * <p>
 * 18-11-30下午2:55
 *
 * @author chenbin sixh
 */
public interface Transport {

    /**
     * Send.
     *
     * @param message the message
     * @return the channel handler
     * @throws RemotingException 发送消息失败或消息格式不正确的时候.
     */
    MFuture send(Object message) throws RemotingException;
}
