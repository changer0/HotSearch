package com.lulu.baseutil;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Collection of methods for operate memory.
 * 获取当前内存状态工具类
 */
public class MemoryUtil {

    static final int ERROR = -1;

    private static final long RESERVED_SIZE = 2097152;

    /**
     * sdcard 是否内存不足
     *
     * @return
     */

    static public boolean externalMemoryAvailable() {
        return android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState());
    }

    /**
     * 获取可用内部存储
     *
     * @return
     */

    static public long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取内部储存总大小
     *
     * @return
     */
    static public long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    /**
     * 获取外部储存可用大小
     *
     * @param context
     * @return
     */
    static public long getAvailableExternalMemorySize(Context context) {
        if (externalMemoryAvailable()) {
            try {
                File path = FileUtil.getStorageFileDir(context);
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getAvailableBlocks();
                return availableBlocks * blockSize;
            } catch (Exception e) {
                return ERROR;
            }
        } else {
            return ERROR;
        }
    }

    /**
     * 获取外部储存总大小
     *
     * @param context
     * @return
     */
    static public long getTotalExternalMemorySize(Context context) {
        if (externalMemoryAvailable()) {
            File path = FileUtil.getStorageFileDir(context);
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSize();
            long totalBlocks = stat.getBlockCount();
            return totalBlocks * blockSize;
        } else {
            return ERROR;
        }
    }

    /**
     * byte 转换mb
     *
     * @param size
     * @return
     */
    static public String formatSize(long size) {
        String suffix = "B";

        if (size >= 1024) {
            suffix = "KiB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MiB";
                size /= 1024;
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffix != null) {
            resultBuffer.append(suffix);
        }
        return resultBuffer.toString();
    }

    /**
     * 外部储存是否可用
     *
     * @param size
     * @param context
     * @return
     */
    static public boolean isExternalMemoryAvailable(long size, Context context) {
        long availableMemory = getAvailableExternalMemorySize(context);
        return !(size > availableMemory);
    }

    /**
     * 内部储存是否可用
     *
     * @param size
     * @return
     */

    static public boolean isInternalMemoryAvailable(long size) {
        long availableMemory = getAvailableInternalMemorySize();
        return !(size > availableMemory);
    }

    /**
     * 内存是否可用
     *
     * @param size
     * @param context
     * @return
     */
    static public boolean isMemoryAvailable(long size, Context context) {
        size += RESERVED_SIZE;
        if (externalMemoryAvailable()) {
            return isExternalMemoryAvailable(size, context);
        }
        return isInternalMemoryAvailable(size);
    }

    /**
     * 该路径内存可用值
     *
     * @param path
     * @return
     */
    static public long getSpecificMemoryAvaliable(String path) {
        StatFs stat = new StatFs(path);
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 该路径下是否有内存可用
     * @param size
     * @param path
     * @return
     */
    static public boolean isSpecificMemoryAvailable(long size, String path) {
        long availableMemory = getSpecificMemoryAvaliable(path);
        return !(size > availableMemory);
    }
}
