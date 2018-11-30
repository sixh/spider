package com.sixh.spider.core.context;

import lombok.Data;

/**
 * RequestContext.
 * <p>
 * 请求上下文的实现处理.
 * <p>
 * 18-11-30上午11:38
 *
 * @author chenbin sixh
 */
@Data
public class RequestContext {

    /**
     * 请求体.
     */
    private String body;

    /**
     * 请求的IP地址
     */
    private String requestIp;

    /**
     * 请求头
     */
    private Header header;

    /**
     * 请求的服务KEY,方法KEY
     */
    private String serviceKey;

    /**
     * 用于直连路由
     */
    private String url;

    /**
     * 服务器校验
     */
    private String tokenId;

    /**
     * 业务ID;
     */
    private String mid;

    /**
     * 唯一请求的通道ID
     */
    private String channelId;

    /**
     * 请求头
     */
    @Data
    public static class Header {
        private String service;

        private String method;

        private String domain;

        private String version;
    }
}
