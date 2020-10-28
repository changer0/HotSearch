package com.qq.reader.provider.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

/**
 * Created by zhanglulu on 2019/10/13.
 * for
 */
public class ProviderUtility {

    /**
     * 根据当前context获取Activity, 主要处理Dialog相获取不到的问题
     * 未来放到 CommonUtility
     * @param cont
     * @return
     */
    public static Activity getActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity) cont;
        else if (cont instanceof ContextWrapper)
            return getActivity(((ContextWrapper) cont).getBaseContext());

        return null;
    }

    public static ThreadFactory threadFactory(final String name) {
        return runnable -> new Thread(runnable, name);
    }
}
