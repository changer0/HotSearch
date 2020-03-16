package com.qq.reader.view.lottie;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.MainThread;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieListener;
import com.tencent.mars.xlog.Log;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zhanglulu on 2019/10/23.
 * for Lottie 文件加载类
 */
public class LottieDataLoader {
    private static final String TAG = "LottieDataLoader";

    private CopyOnWriteArrayList<LottieLoaderBean> mListQueue = new CopyOnWriteArrayList<>();
    private AtomicBoolean isRunning = new AtomicBoolean(false);

    private LottieDataLoader() {
    }

    private static volatile LottieDataLoader instance;

    public static LottieDataLoader getInstance() {
        if (instance == null) {
            synchronized (LottieDataLoader.class) {
                if (instance == null) {
                    instance = new LottieDataLoader();
                }
            }
        }
        return instance;
    }


    @MainThread
    public synchronized void fromAssert(LottieLoaderBean lottieLoaderBean) {
        if (lottieLoaderBean == null) {
            return;
        }
        if (isRunning.get()) {
            mListQueue.add(lottieLoaderBean);
            return;
        }
        if (mListQueue.isEmpty()) {
            addLoaderTask(lottieLoaderBean);
        }
    }

    /**
     * @param lottieLoaderBean
     */
    private void addLoaderTask(LottieLoaderBean lottieLoaderBean) {
        if (lottieLoaderBean == null) {
            return;
        }
        isRunning.set(true);
        Context context = lottieLoaderBean.getContext();
        String fileName = lottieLoaderBean.getFileName();
        LottieListener<LottieComposition> listener = lottieLoaderBean.getListener();
        if (context == null || TextUtils.isEmpty(fileName) || listener == null) {
            return;
        }

        LottieCompositionFactory.fromAsset(context, fileName)
                .addListener(result -> {
                    listener.onResult(result);
                    Log.d(TAG, "addLoaderTask: fileName: " + fileName);
                    notifyNext();
                })
                .addFailureListener(result -> {
                    listener.onResult(null);
                    Log.e(TAG, "addLoaderTask: fileName: " + fileName);
                    notifyNext();
                });
    }

    private void notifyNext() {
        if (mListQueue.isEmpty()) {
            isRunning.set(false);
            return;
        }
        LottieLoaderBean bean = mListQueue.remove(0);
        addLoaderTask(bean);
    }


}
