package com.sixh.spider.core.network.netty;

import com.sixh.spider.core.network.ChannelHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.net.SocketAddress;

/**
 * NettyChannel.
 * <p>
 * netty channel.
 * <p>
 * 18-11-30下午2:58
 *
 * @author chenbin sixh
 */
public class NettyChannel implements com.sixh.spider.core.network.Channel {

    private final Channel channel;

    public NettyChannel(ChannelHandlerContext channelHandlerContext) {
        channel = channelHandlerContext.channel();
    }

    public NettyChannel(Channel channel) {
        this.channel = channel;
    }

    public SocketAddress remoteAddress() {
        return channel.remoteAddress();
    }

    public SocketAddress localAddress() {
        return channel.localAddress();
    }

    public boolean isConnected() {
        return channel.isActive();
    }

    public boolean isOpened() {
        return channel.isOpen();
    }

    public boolean isClose() {
        return !isOpened();
    }

    public ChannelHandler close() {
        return new NettyChannelHandler();
    }

    public ChannelHandler send(Object message) {
        return null;
    }
}
