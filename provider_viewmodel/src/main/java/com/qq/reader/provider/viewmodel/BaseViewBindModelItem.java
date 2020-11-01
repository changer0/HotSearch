package com.qq.reader.provider.viewmodel;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.qq.reader.provider.BaseViewBindItem;
import com.qq.reader.provider.bean.BaseDataBean;
import com.qq.reader.provider.log.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhanglulu on 2020/10/23.
 * for 支持使用 IModel IView 形式构建 ViewBindItem
 */
@SuppressWarnings("rawtypes")
public abstract class BaseViewBindModelItem
        <Bean extends BaseDataBean>
        extends BaseViewBindItem<Bean, BaseViewHolder> {

    private static final String TAG = "BaseViewBindModelItem";

    @NonNull
    private final Map<Integer, IViewModel> viewModelMap = new ConcurrentHashMap<>();

    /**设置数据时触发*/
    @Override
    public void setData(Bean dataBean) {
        super.setData(dataBean);
        try {
            onBindViewModel(dataBean, viewModelMap);
        } catch (Exception e) {
            Logger.e("BaseViewBindModelItem", "onBindViewModel 失败：" + e);
        }
    }

    @Override
    public boolean bindView(@NonNull BaseViewHolder holder, @NonNull Activity activity) {

        for (Map.Entry<Integer, IViewModel> viewModelEntry : viewModelMap.entrySet()) {
            View view = holder.getView(viewModelEntry.getKey());
            if (!(view instanceof IView)) {
                Logger.e(TAG, "资源文件中的 View，必须实现 IView 接口！！！");
                continue;
            }
            IViewModel value = viewModelEntry.getValue();
            if (value == null) {
                Logger.e(TAG, "当前 ViewModel 为空：" + value);
                continue;
            }
            ((IView) view).setModel(viewModelEntry.getValue());
        }
        return true;
    }

    /**初始化 Model*/
    public abstract void onBindViewModel(Bean data, @NonNull Map<Integer, IViewModel> viewModelMap);
}
