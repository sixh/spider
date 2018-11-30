package com.sixh.spider.core.network;

import java.net.SocketAddress;

/**
 * Channel.
 * <p>
 * network channel A unified definition.
 * <p>
 * 18-11-30下午2:44
 *
 * @author chenbin sixh
 */
public interface Channel extends Transport{

    /**
     * SocketAddress.
     *
     * @return socketAddress. socket address
     */
    SocketAddress remoteAddress();

    /**
     * SocketAddress.
     *
     * @return SocketAddress socket address
     */
    SocketAddress localAddress();

    /**
     * Is connected boolean.
     *
     * @return the boolean
     */
    boolean isConnected();

    /**
     * Is opened boolean.
     *
     * @return the boolean
     */
    boolean isOpened();

    /**
     * Is close boolean.
     *
     * @return the boolean
     */
    boolean isClose();

    /**
     * Close channel handler.
     *
     * @return the channel handler
     */
    ChannelHandler close();
}
