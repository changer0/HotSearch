package com.qq.reader.bookstore;

import android.view.View;

import androidx.annotation.IdRes;

import com.qq.reader.zebra.utils.CastUtils;

/**
 * 书城工具方法
 * @author zhanglulu
 */
public class BookStoreUtil {

    /**
     * 带有空判断的 findViewById
     * @return
     */
    public static <T> T findViewByIdCheckNull(View view, @IdRes int resId, Class<T> placeHolder) {
        View viewById = view.findViewById(resId);
        if (viewById != null) {
            return CastUtils.cast(viewById);
        }
        return null;
    }

}
