package com.qq.reader.provider;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.WorkerThread;

import com.qq.reader.provider.inter.INetQuestParams;

import java.io.InputStream;

/**
 * DataProvider 配置 类
 */
public class DataProviderConfig {

    private static Application application = null;

    private static NetQuestAdapter netQuestAdapter = null;

    public static boolean isDebug = false;

    /**缓存目录*/
    public static String cacheDir;

    public static Application getApplication() {
        if (application == null) {
            throw new RuntimeException("使用前 请在 Application 中执行 DataProviderConfig.init");
        }
        return application;
    }

    public static NetQuestAdapter getNetQuestAdapter() {
        if (netQuestAdapter == null) {
            throw new RuntimeException("使用前 请在 Application 中执行 DataProviderConfig.init");
        }
        return netQuestAdapter;
    }

    /**初始化*/
    public static void init(Builder builder) {
        DataProviderConfig.application = builder.application;
        DataProviderConfig.netQuestAdapter = builder.netQuestAdapter;
        DataProviderConfig.isDebug = builder.isDebug;
        DataProviderConfig.cacheDir = builder.cacheDir;
    }

    /**获取获取版本号*/
    public static int getAppVersion() {
        try {
            PackageInfo info = DataProviderConfig.application.getPackageManager()
                    .getPackageInfo(DataProviderConfig.application.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**网络请求适配器*/
    public interface NetQuestAdapter {
        @WorkerThread
        InputStream syncRequest(INetQuestParams params) throws Exception;
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
