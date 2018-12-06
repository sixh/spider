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
 * DefaultAioPipeline.
 * <p>
 * <p>
 * 18-12-6下午2:47
 *
 * @author chenbin sixh
 */
public class DefaultAioPipeline implements AioPipeline {

    /**
     * The Head.
     */
    DefaultAioHandlerContext head;

    /**
     * The Last.
     */
    DefaultAioHandlerContext last;

    private AioChannel channel;

    @Override
    public AioInboundInvoker read(Object msg) {
        return null;
    }

    @Override
    public MFuture send(Object message) {
        return last.send(message);
    }

    @Override
    public MFuture write(Object message) {
        return last.write(message);
    }

    /**
     * Instantiates a new Default aio pipeline.
     */
    public DefaultAioPipeline(AioChannel channel) {
        head = new HeadContext(this);
        last = new LastContext(this);
        head.next = last;
        last.prev = head;
        this.channel = channel;
    }

    @Override
    public AioChannel channel() {
        return channel;
    }

    @Override
    public AioPipeline addLast(String name, AioHandler handler) {
        DefaultAioHandlerContext context = newContext(name, handler);
        addLast0(context);
        return this;
    }

    private void addLast0(DefaultAioHandlerContext context) {
        DefaultAioHandlerContext prev = last.prev;
        context.prev = prev;
        context.next = last;
        prev.next = context;
        last.prev = context;
    }

    private DefaultAioHandlerContext newContext(String name, AioHandler handler) {
        boolean in = handler instanceof AioDecode;
        boolean out = handler instanceof AioEncode;
        return new DefaultAioHandlerContext(in, out, this, name, handler);
    }

    /**
     * The type Head context.
     */
    class HeadContext extends DefaultAioHandlerContext implements AioOutHandler {

        /**
         * Instantiates a new Default aio handler context.
         *
         * @param pipeline the pipeline
         */
        public HeadContext(AioPipeline pipeline) {
            super(false, true, pipeline, HeadContext.class.getSimpleName(), null);
        }

        @Override
        public AioHandler handler() {
            return this;
        }

        @Override
        public void write(AioHandlerContext ctx, Object msg) {
            pipeline().channel().write(msg);
        }
    }

    /**
     * The type Last countext.
     */
    class LastContext extends DefaultAioHandlerContext {

        /**
         * Instantiates a new Default aio handler context.
         *
         * @param pipeline the pipeline
         */
        public LastContext(AioPipeline pipeline) {
            super(true, true, pipeline, LastContext.class.getSimpleName(), null);
        }
    }
}
