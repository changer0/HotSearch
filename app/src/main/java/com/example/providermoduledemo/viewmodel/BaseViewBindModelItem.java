package com.example.providermoduledemo.viewmodel;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.qq.reader.provider.BaseViewBindItem;
import com.qq.reader.provider.bean.BaseDataBean;
import com.qq.reader.provider.log.Logger;
import com.qq.reader.provider.viewmodel.BindUIView;
import com.qq.reader.provider.viewmodel.IModel;
import com.qq.reader.provider.viewmodel.IView;
import com.qq.reader.widget.recyclerview.base.BaseViewHolder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhanglulu on 2020/10/23.
 * for 支持使用 IModel IView 形式构建 ViewBindItem
 */
public abstract class BaseViewBindModelItem
        <Bean extends BaseDataBean>
        extends BaseViewBindItem<Bean, BaseViewHolder> {

    private final List<Integer> viewIds = new ArrayList<>();

    private final List<IModel> models = new ArrayList<>();

    /**设置数据时触发*/
    @Override
    public void setData(Bean bean) {
        super.setData(bean);
        try {
            onCreateModel(bean);
            parseViewModel();
        } catch (Exception e) {
            Logger.e("BaseViewBindModelItem", "setData 失败：" + e);
        }

    }

    /**解析 ViewModel*/
    private void parseViewModel() {
        if (viewIds.size() > 0) {
            viewIds.clear();
        }
        if (models.size() > 0) {
            models.clear();
        }
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(BindUIView.class)) {
                continue;
            }
            field.setAccessible(true);
            IModel model = null;
            try {
                model = (IModel) field.get(this);
            } catch (IllegalAccessException e) {
                continue;
            }
            if (model == null) {
                continue;
            }
            BindUIView fieldAnnotation = field.getAnnotation(BindUIView.class);
            viewIds.add(fieldAnnotation.value());
            models.add(model);
        }
    }

    @Override
    public boolean bindView(@NonNull BaseViewHolder holder, @NonNull Activity activity) throws Exception {
        if (viewIds.size() != models.size()) {
            return false;
        }
        for (int i = 0; i < viewIds.size(); i++) {
            ((IView) holder.getView(viewIds.get(i))).setModel(models.get(i));
        }
        return true;
    }

    /**初始化 Model*/
    public abstract void onCreateModel(Bean data);
}
