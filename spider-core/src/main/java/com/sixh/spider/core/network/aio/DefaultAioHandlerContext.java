/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sixh.spider.core.network.aio;

import com.sixh.spider.core.network.MFuture;

/**
 * DefaultAioHandlerContext.
 * <p>
 *
 * <p>
 * 18-12-6上午11:42
 *
 * @author chenbin sixh
 */
public class DefaultAioHandlerContext implements AioHandlerContext {

    private final boolean in;

    private final boolean out;

    private AioPipeline pipeline;

    private String name;

    private AioHandler handler;

    /**
     * The Head.
     */
    DefaultAioHandlerContext next;

    /**
     * The Last.
     */
    DefaultAioHandlerContext prev;

    /**
     * Instantiates a new Default aio handler context.
     *
     * @param in       the in
     * @param out      the out
     * @param pipeline the pipeline
     * @param name     the name
     * @param handler  the handler
     */
    public DefaultAioHandlerContext(boolean in,
                                    boolean out,
                                    AioPipeline pipeline,
                                    String name,
                                    AioHandler handler) {
        this.in = in;
        this.out = out;
        this.pipeline = pipeline;
        this.name = name;
        this.handler = handler;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public AioChannel channel() {
        return pipeline.channel();
    }

    @Override
    public AioHandler handler() {
        return handler;
    }

    @Override
    public String name() {
        return name;
    }


    @Override
    public AioPipeline pipeline() {
        return pipeline;
    }

    @Override
    public AioInboundInvoker read(Object msg) {
        return null;
    }

    @Override
    public MFuture write(Object message) {
        DefaultAioHandlerContext outbound = findContextOutbound();
        return outbound.invokeWrite(message);
    }

    @Override
    public MFuture send(Object message) {
        return write(message);
    }

    private MFuture invokeWrite(Object message) {
        try {
            ((AioOutHandler) handler()).write(this, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AioFuture(channel());
    }

    private DefaultAioHandlerContext findContextOutbound() {
        DefaultAioHandlerContext ctx = this;
        do {
            ctx = ctx.prev;
        } while (!ctx.out);
        return ctx;
    }
}
