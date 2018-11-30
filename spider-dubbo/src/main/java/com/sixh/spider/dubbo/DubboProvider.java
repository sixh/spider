package com.sixh.spider.dubbo;


import com.sixh.spider.common.URL;
import lombok.Data;

@Data
public class DubboProvider {

    /**
     * 提供者;
     */
    private String key;
    /**
     * DubboURL;
     */
    private URL url;
    /**
     * 方法提供者
     */
    private Integer port;
    /**
     * 地址;
     */
    private String address;


    public DubboProvider(String key, String address, Integer port) {
        this.key = key;
        this.port = port;
        this.address = address;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DubboProvider{");
        sb.append("key='").append(key).append('\'');
        sb.append(", url=").append(url);
        sb.append(", port=").append(port);
        sb.append(", address='").append(address).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
