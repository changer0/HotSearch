package com.qq.reader.view.lottie;

import android.content.Context;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieListener;


/**
 * @author zhanglulu on 2019/10/23.
 * for
 */
public class LottieLoaderBean {
    private Context context;
    private String fileName;
    private LottieListener<LottieComposition> listener;

    public LottieLoaderBean(Context context, String fileName, LottieListener<LottieComposition> listener) {
        this.context = context;
        this.fileName = fileName;
        this.listener = listener;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LottieListener<LottieComposition> getListener() {
        return listener;
    }

    public void setListener(LottieListener<LottieComposition> listener) {
        this.listener = listener;
    }
}
