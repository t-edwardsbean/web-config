package com.baidu.testframework.core;

import java.util.List;
import java.util.Random;

/**
 * 随机获取一个待测试的方法
 * Created by edwardsbean on 14-10-15.
 */
public class RandomMethodSelector implements MethodSelector {
    private Random random;

    public RandomMethodSelector() {
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public MethodParam getMethod(List<MethodParam> methods) {
        if (methods != null && !methods.isEmpty()) {
            int radomIndex = random.nextInt(methods.size());
            return methods.get(radomIndex);

        }
        return null;
    }
}
