package com.sixh.spider.core.network;

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
     */
    ChannelHandler send(Object message);
}
