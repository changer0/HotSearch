package com.yuewen.reader.zebra;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.yuewen.reader.zebra.inter.INetQuestParams;

import java.io.InputStream;

/**
 * Zebra 配置 类
 */
public class ZebraConfig {

    private static Application application = null;

    private static NetQuestAdapter netQuestAdapter = null;

    public static boolean isDebug = false;

    /**缓存目录*/
    private static String cacheDir;

    public static String getCacheDir() {
        if (cacheDir == null) {
            throw new RuntimeException("使用前 请在 Application 中执行 ZebraConfig.init");
        }
        return cacheDir;
    }

    public static Application getApplication() {
        if (application == null) {
            throw new RuntimeException("使用前 请在 Application 中执行 ZebraConfig.init");
        }
        return application;
    }

    public static NetQuestAdapter getNetQuestAdapter() {
        if (netQuestAdapter == null) {
            throw new RuntimeException("使用前 请在 Application 中执行 ZebraConfig.init");
        }
        return netQuestAdapter;
    }

    /**初始化*/
    public static void init(Builder builder) {
        ZebraConfig.application = builder.application;
        ZebraConfig.netQuestAdapter = builder.netQuestAdapter;
        ZebraConfig.isDebug = builder.isDebug;
        ZebraConfig.cacheDir = builder.cacheDir;
    }

    /**获取获取版本号*/
    public static int getAppVersion() {
        try {
            PackageInfo info = ZebraConfig.application.getPackageManager()
                    .getPackageInfo(ZebraConfig.application.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**网络请求适配器*/
    public interface NetQuestAdapter {
        @WorkerThread
        @Nullable
        InputStream syncRequest(@NonNull INetQuestParams params) throws Exception;
    }

    /**
     * 构建类
     */
    public static class Builder {
        private Application application;
        private NetQuestAdapter netQuestAdapter;
        private boolean isDebug = false;
        private String cacheDir;

        public Builder(Application application, NetQuestAdapter netQuestAdapter) {
            this.application = application;
            this.netQuestAdapter = netQuestAdapter;
            this.cacheDir = application.getExternalCacheDir() + "/" + "data_provider";
        }

        public void setDebug(boolean debug) {
            isDebug = debug;
        }

        public void setCacheDir(String cacheDir) {
            this.cacheDir = cacheDir;
        }
    }

}
