package com.qq.reader.provider.viewmodel.compiler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhanglulu on 2020/10/28.
 * for
 */
class ClazzBuilderInfo {
    String packageName;
    String simpleClassName;
    Map<Integer, String> filedMap = new HashMap<>();

    public ClazzBuilderInfo(String packageName, String simpleClassName) {
        this.packageName = packageName;
        this.simpleClassName = simpleClassName;
    }
}
