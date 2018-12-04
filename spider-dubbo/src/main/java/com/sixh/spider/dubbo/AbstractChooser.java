package com.sixh.spider.dubbo;

import com.sixh.spider.common.Const;
import com.sixh.spider.core.context.RequestContext;

import java.util.List;

/**
 * @author p
 */
public abstract class AbstractChooser implements ChooserFactory {

    static int calculateWarmupWeight(int uptime, int warmup, int weight) {
        int ww = (int) ((float) uptime / ((float) warmup / (float) weight));
        return ww < 1 ? 1 : (ww > weight ? weight : ww);
    }

    /**
     * 真实执行选择处理的信息;
     *
     * @param providers 处理器;
     *
     * @return 处理；
     */
    abstract DubboProvider doSelect(List<DubboProvider> providers, RequestContext context);


    protected int getWeight(DubboProvider provider, RequestContext context) {
        int weight = provider.getUrl().getMethodParameter(
                context.getHeader().getMethod(),
                Const.WEIGHT_KEY,
                Const.DEFAULT_WEIGHT);
        if (weight > 0) {
            long timestamp = provider.getUrl().getParameter(Const.TIMESTAMP_KEY, 0L);
            if (timestamp > 0L) {
                int uptime = (int) (System.currentTimeMillis() - timestamp);
                int warmup = provider.getUrl().getParameter(Const.WARMUP_KEY, Const.DEFAULT_WARMUP);
                if (uptime > 0 && uptime < warmup) {
                    weight = calculateWarmupWeight(uptime, warmup, weight);
                }
            }
        }
        return weight;
    }

    @Override
    public DubboProvider select(List<DubboProvider> providers, RequestContext context) {
        if (providers == null || providers.size() == 0) {
            return null;
        }
        if (providers.size() == 1) {
            return providers.get(0);
        }
        return doSelect(providers, context);
    }
}