package com.sixh.spider.core.network.netty;

import com.sixh.spider.core.network.MChannelHandler;
import com.sixh.spider.core.network.MChannel;
import com.sixh.spider.core.network.MFuture;
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
public class NettyChannel implements MChannel {

    private final Channel channel;

    /**
     * Instantiates a new Netty channel.
     *
     * @param channel the channel
     */
    public NettyChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public SocketAddress remoteAddress() {
        return channel.remoteAddress();
    }

    @Override
    public SocketAddress localAddress() {
        return channel.localAddress();
    }

    @Override
    public boolean isConnected() {
        return channel.isActive();
    }

    @Override
    public boolean isOpened() {
        return channel.isOpen();
    }

    @Override
    public boolean isClose() {
        return !isOpened();
    }

    @Override
    public MFuture close() {
        return new NettyFuture(channel.close());
    }

    @Override
    public MFuture send(Object message) {
        return new NettyFuture(channel.writeAndFlush(message));
    }
}
