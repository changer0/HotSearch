package com.qq.reader.provider.viewmodel;

import java.util.Map;

/**
 * @author zhanglulu on 2020/10/28.
 * for 用于获取 View-Model 对应关系的接口，有自动生成类实现
 */
public interface IGetViewModelMapInter {
    Map<Integer, IViewModel> getViewModelMap();
}
