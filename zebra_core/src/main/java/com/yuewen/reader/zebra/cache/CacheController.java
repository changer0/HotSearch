package com.yuewen.reader.zebra.cache;

import android.text.TextUtils;

import com.yuewen.reader.zebra.ZebraConfig;
import com.yuewen.reader.zebra.cache.core.DiskLruCache;
import com.yuewen.reader.zebra.cache.core.IoUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


/**
 * 缓存控制器 对 DiskLruCache 的一层封装
 */
public class CacheController {
    private static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;//10M
    private static final int DEFAULT_BUFFER_SIZE = 32 * 1024; // 32 Kb

    private DiskLruCache cache;

    private static volatile CacheController instance = null;

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
        File cacheFile = new File(ZebraConfig.getCacheDir());
        try {
            //1. 缓存地址 2. 版本号  3. 指定同一个key可以对应多少个缓存文件 4. 指定最多可以缓存多少字节的数据
            cache = DiskLruCache.open(cacheFile, ZebraConfig.getAppVersion(), 1, MAX_CACHE_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**保存文件 String*/
    public boolean save(String originKey, String str) throws IOException {
        ByteArrayOutputStream baos = null;
        ByteArrayInputStream bais = null;
        try {
            baos = new ByteArrayOutputStream();
            if (!TextUtils.isEmpty(str)) {
                baos.write(str.getBytes(StandardCharsets.UTF_8));
            }
            bais = new ByteArrayInputStream(baos.toByteArray());
            //保存缓存
            return CacheController.getInstance().save(originKey, bais);
        } finally {
            if (bais != null) {
                bais.close();
            }
            if (baos != null) {
                baos.close();
            }
        }
    }


    /**保存文件 Stream*/
    public boolean save(String originKey, InputStream inputStream) throws IOException {
        if (cache == null) {
            return false;
        }
        DiskLruCache.Editor editor = cache.edit(originKey);
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

    /**获取缓存快照，需要 close 哟*/
    public InputStream get(String originKey) {
        try {
            DiskLruCache.Snapshot snapshot = cache.get(originKey);
            if (snapshot != null) {
                return snapshot.getInputStream(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**获取缓存String */
    public String getString(String originKey) throws IOException {
        InputStream is = get(originKey);
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            IoUtils.copyStream(bis, baos, null);
            return new String(baos.toByteArray(), StandardCharsets.UTF_8);
        } finally {
            try {
                bis.close();
                baos.close();
                //不要忘记关掉快照
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**移除缓存*/
    public boolean remove(String originKey) {
        if (cache == null) {
            return false;
        }
        try {
            return cache.remove(originKey);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** 判断当前快照是否有缓存数据*/
    public boolean hasCache(String originKey) {
        InputStream cacheInputStream = get(originKey);
        if (cacheInputStream == null) {
            return false;
        }
        try {
            if (cacheInputStream.available() <= 0) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
