package com.sixh.spider.core.network.netty;

import com.sixh.spider.core.network.MChannel;
import com.sixh.spider.core.network.MChannelHandler;
import com.sixh.spider.core.network.ChannelHandlerListener;
import com.sixh.spider.core.network.MFuture;
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
public class NettyChannelHandler implements MChannelHandler {


    private ChannelFuture channelFuture;

    /**
     * Instantiates a new Netty channel handler.
     *
     * @param future the future
     */
    public NettyChannelHandler(ChannelFuture future) {
        this.channelFuture = future;
    }

    public void addListener(ChannelHandlerListener listener) {
        channelFuture.addListener((ChannelFutureListener) future -> listener.operationComplete(new MFuture() {
            @Override
            public boolean isSuccessfully() {
                return future.isSuccess();
            }

            @Override
            public Throwable cause() {
                return future.cause();
            }

            @Override
            public MChannel getChannel() {
                return new NettyChannel(future.channel());
            }
        }));
    }

    @Override
    public void connected() {

    }

    @Override
    public void disconnected() {

    }

    @Override
    public void caught() {

    }
}
