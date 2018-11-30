package com.sixh.spider.dubbo;

import com.sixh.spider.core.context.RequestContext;

import java.util.List;

/**
 * 引用：dubbo
 *
 * @author chenbin sixh
 */
public interface ChooserFactory {

    /**
     * 定义一个选择器;
     *
     * @param providers 操作对象;
     * @param context   访问信息
     * @return 返回一个已知的选择器;
     */
    DubboProvider select(List<DubboProvider> providers, RequestContext context);
}
