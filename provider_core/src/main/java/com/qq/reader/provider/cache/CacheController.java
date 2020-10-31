package com.qq.reader.provider.cache;

import com.qq.reader.provider.DataProviderConfig;
import com.qq.reader.provider.cache.core.DiskLruCache;
import com.qq.reader.provider.cache.core.IoUtils;
import com.qq.reader.provider.utils.MD5Utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * 缓存控制器 对 DiskLruCache 的一层封装
 */
public class CacheController {
    private static final String DISK_CACHE_DIR = DataProviderConfig.getApplication().getExternalCacheDir() + "/" + "data_provider";
    private static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;//10M
    public static final int DEFAULT_BUFFER_SIZE = 32 * 1024; // 32 Kb

    private DiskLruCache cache;

    public static volatile CacheController instance = null;

    public static CacheController getInstance() {
        if (instance == null) {
            synchronized (CacheController.class) {
                if (instance == null) {
                    instance = new CacheController();
                }
            }
        }
        return instance;
    }

    private CacheController() {
        initCache();
    }

    private void initCache() {
        File cacheFile = new File(DISK_CACHE_DIR);
        try {
            //1. 缓存地址 2. 版本号  3. 指定同一个key可以对应多少个缓存文件 4. 指定最多可以缓存多少字节的数据
            cache = DiskLruCache.open(cacheFile, DataProviderConfig.getAppVersion(), 1, MAX_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**保存文件*/
    public boolean save(String originKey, InputStream inputStream) throws IOException {
        if (cache == null) {
            return false;
        }
        DiskLruCache.Editor editor = cache.edit(generateKey(originKey));
        if (editor == null) {
            return false;
        }
        OutputStream ops = editor.newOutputStream(0);
        OutputStream os = new BufferedOutputStream(ops, DEFAULT_BUFFER_SIZE);
        boolean copied = false;
        try {
            copied = IoUtils.copyStream(inputStream, os, null, DEFAULT_BUFFER_SIZE);
        } finally {
            if (copied) {
                editor.commit();
                cache.flush();
            } else {
                editor.abort();//有内鬼，终止交易
            }
            IoUtils.closeQuietly(os);
            IoUtils.closeQuietly(ops);
        }
        return copied;
    }

    private String generateKey(String originKey) {
        return MD5Utils.getSHA256(originKey);
    }

    /**获取缓存快照，需要 close 哟*/
    public InputStream get(String originKey) {
        try {
            DiskLruCache.Snapshot snapshot = cache.get(generateKey(originKey));
            if (snapshot != null) {
                return snapshot.getInputStream(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**移除缓存*/
    public boolean remove(String originKey) {
        if (cache == null) {
            return false;
        }
        try {
            return cache.remove(generateKey(originKey));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**全部清除*/
    public void clear() {
        if (cache == null) {
            return;
        }
        try {
            cache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            initCache();
        }
    }
}
