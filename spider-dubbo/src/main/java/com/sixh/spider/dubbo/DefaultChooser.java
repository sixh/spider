package com.sixh.spider.dubbo;

import com.sixh.spider.core.context.RequestContext;

import java.util.List;
import java.util.Random;

/**
 * All rights Reserved, Designed By HQYG
 * <pre>
 * Copyright:  Copyright(C) 2017
 * Company:    HQYG.
 * Author:     chenbin
 * CreateDate: 2018-02-02
 * </pre>
 * <p>
 * 默认的一个选择器;
 * </p>
 *
 * @author: chenbin
 */
public class DefaultChooser extends AbstractChooser {

    private final Random random = new Random();


    @Override
    DubboProvider doSelect(List<DubboProvider> providers, RequestContext context) {
        // 总个数
        int length = providers.size();
        // 总权重
        int totalWeight = 0;
        // 权重是否都一样
        boolean sameWeight = true;
        for (int i = 0; i < length; i++) {
            int weight = getWeight(providers.get(i), context);
            // 累计总权重
            totalWeight += weight;
            if (sameWeight && i > 0
                    && weight != getWeight(providers.get(i - 1), context)) {
                // 计算所有权重是否一样
                sameWeight = false;
            }
        }
        if (totalWeight > 0 && !sameWeight) {
            // 如果权重不相同且权重大于0则按总权重数随机
            int offset = random.nextInt(totalWeight);
            // 并确定随机值落在哪个片断上
            for (int i = 0; i < length; i++) {
                offset -= getWeight(providers.get(i), context);
                if (offset < 0) {
                    return providers.get(i);
                }
            }
        }
        // 如果权重相同或权重为0则均等随机
        return providers.get(random.nextInt(length));
    }
}
