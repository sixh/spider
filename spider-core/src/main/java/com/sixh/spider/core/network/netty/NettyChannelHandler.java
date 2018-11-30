package com.sixh.spider.core.network.netty;

import com.sixh.spider.core.network.Channel;
import com.sixh.spider.core.network.ChannelHandler;
import com.sixh.spider.core.network.ChannelHandlerListener;
import com.sixh.spider.core.network.Future;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;

/**
 * NettyChannelHandler.
 * <p>
 * <p>
 * 18-11-30下午3:02
 *
 * @author chenbin sixh
 */
public class NettyChannelHandler implements ChannelHandler {


    private ChannelFuture channelFuture;

    /**
     * Instantiates a new Netty channel handler.
     *
     * @param future the future
     */
    public NettyChannelHandler(ChannelFuture future) {
        this.channelFuture = future;
    }

    @Override
    public void addListener(ChannelHandlerListener listener) {
        channelFuture.addListener((ChannelFutureListener) future -> listener.operationComplete(new Future() {
            @Override
            public boolean isSuccessfully() {
                return future.isSuccess();
            }

            @Override
            public Throwable cause() {
                return future.cause();
            }

            @Override
            public Channel getChannel() {
                return new NettyChannel(future.channel());
            }
        }));
    }
}
