package com.qq.reader.provider.viewmodel;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.chad.library.adapter.base.BaseViewHolder;
import com.qq.reader.provider.BaseViewBindItem;
import com.qq.reader.provider.bean.BaseDataBean;
import com.qq.reader.provider.log.Logger;
import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * @author zhanglulu on 2020/10/23.
 * for 支持使用 IModel IView 形式构建 ViewBindItem
 */
public abstract class BaseViewBindModelItem
        <Bean extends BaseDataBean>
        extends BaseViewBindItem<Bean, BaseViewHolder> {

    private static final String TAG = "BaseViewBindModelItem";

    @Nullable
    private IGetViewModelMapInter getViewModelMapInter;

    /**设置数据时触发*/
    @Override
    public void setData(Bean bean) {
        super.setData(bean);
        try {
            onInitModel(bean);
            findBindClass();
        } catch (Exception e) {
            Logger.e("BaseViewBindModelItem", "setData 失败：" + e);
        }

    }

    private void findBindClass() {
        Class<? extends BaseViewBindModelItem> clazz = getClass();
        String clazzName = clazz.getName();
        try {
            //参考 butterknife
            Class<?> bindingClass = clazz.getClassLoader().loadClass(clazzName + "_ProviderViewBindModel");
            Constructor<?> constructor = bindingClass.getConstructor(getClass());
            getViewModelMapInter = (IGetViewModelMapInter) constructor.newInstance(this);
        } catch (Exception e) {
            Logger.e("BaseViewBindModelItem", "查找创建 " + clazzName + " 对应的映射类出错！！！");
            e.printStackTrace();
        }

    }

    @Override
    public boolean bindView(@NonNull BaseViewHolder holder, @NonNull Activity activity) throws Exception {
        if (getViewModelMapInter == null) {
            return false;
        }
        Map<Integer, IModel> viewModelMap = getViewModelMapInter.getViewModelMap();
        if (viewModelMap == null) {
            return false;
        }
        for (Map.Entry<Integer, IModel> viewModelEntry : viewModelMap.entrySet()) {
            View view = holder.getView(viewModelEntry.getKey());
            if (!(view instanceof IView)) {
                Logger.e(TAG, "资源文件中的 View，必须实现 IView 接口！！！");
                continue;
            }
            IModel value = viewModelEntry.getValue();
            if (value == null) {
                Logger.e(TAG, "当前 Model 为空：" + value + " 对应的生成类中已经做了非空判断，理论上不应该出现为空的情况！！！");
                continue;
            }
            ((IView) view).setModel(viewModelEntry.getValue());
        }
        return true;
    }

    /**初始化 Model*/
    public abstract void onInitModel(Bean data);
}
