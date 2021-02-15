package com.lulu.baseutil;

import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.WorkerThread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    /**
     * 压缩得到的文件的后缀名
     */
    public static final String ZIP_SUFFIX = ".zip";
    /**
     * 缓冲器大小
     */
    public static final int BUFFER = 4 * 1024;

    public static final String TAG = FileUtil.class.getSimpleName();

    /**
     * 判断 SD 卡是否可用，这个判断包含了判断 writable 和 Readable
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取SD卡应用专属文件目录/storage/emulated/0/Android/data/app_package_name/files
     * 这个目录在android 4.4及以上系统不需要申请SD卡读写权限
     * 因此也不用考虑6.0系统动态申请SD卡读写权限问题，切随应用被卸载后自动清空 不会污染用户存储空间
     *
     * @param context 上下文
     * @return 缓存文件夹 如果没有SD卡或SD卡有问题则返回内部存储目录，/data/data/app_package_name/files
     * 否则优先返回SD卡缓存目录
     */
    public static File getStorageFileDir(Context context) {
        return getStorageFileDir(context, null);
    }


    /**
     * 获取SD卡应用专属文件目录/storage/emulated/0/Android/data/app_package_name/files
     * 这个目录在android 4.4及以上系统不需要申请SD卡读写权限
     * 因此也不用考虑6.0系统动态申请SD卡读写权限问题，切随应用被卸载后自动清空 不会污染用户存储空间
     *
     * @param context 上下文
     * @param type    文件夹类型 可以为空，为空则返回API得到的一级目录
     * @return 缓存文件夹 如果没有SD卡或SD卡有问题则返回内部存储目录，/data/data/app_package_name/files
     * 否则优先返回SD卡缓存目录
     */
    public static File getStorageFileDir(Context context, String type) {
        File appCacheDir = getExternalFileDirectory(context, type);
        if (appCacheDir == null) {
            appCacheDir = getInternalFileDirectory(context, type);
        }

        if (appCacheDir == null) {
            Log.e(TAG, "getStorageFileDir fail , ExternalFile and InternalFile both unavailable ");
        } else {
            if (!appCacheDir.exists() && !appCacheDir.mkdirs()) {
                Log.e(TAG, "getStorageFileDir fail ,the reason is make directory fail !");
            }
        }
        return appCacheDir;
    }

    /**
     * 获取SD卡缓存目录
     *
     * @param context 上下文
     * @param type    文件夹类型 如果为空则返回 /storage/emulated/0/Android/data/app_package_name/files
     *                否则返回对应类型的文件夹如Environment.DIRECTORY_PICTURES 对应的文件夹为 .../data/app_package_name/files/Pictures
     *                {@link android.os.Environment#DIRECTORY_MUSIC},
     *                {@link android.os.Environment#DIRECTORY_PODCASTS},
     *                {@link android.os.Environment#DIRECTORY_RINGTONES},
     *                {@link android.os.Environment#DIRECTORY_ALARMS},
     *                {@link android.os.Environment#DIRECTORY_NOTIFICATIONS},
     *                {@link android.os.Environment#DIRECTORY_PICTURES}, or
     *                {@link android.os.Environment#DIRECTORY_MOVIES}.or 自定义文件夹名称
     * @return 缓存目录文件夹 或 null（无SD卡或SD卡挂载失败）
     */
    public static File getExternalFileDirectory(Context context, String type) {
        File appFileDir = null;
        if (isSDCardEnable()) {
            if (TextUtils.isEmpty(type)) {
                appFileDir = context.getExternalFilesDir(null);
            } else {
                appFileDir = context.getExternalFilesDir(type);
            }

            if (appFileDir == null) {// 有些手机需要通过自定义目录
                appFileDir = new File(Environment.getExternalStorageDirectory(),
                        "Android/data/" + context.getPackageName() + "/files/" + type);
            }

            if (!appFileDir.exists() && !appFileDir.mkdirs()) {
                Log.e(TAG, "getExternalFileDirectory fail ,the reason is make directory fail ");
            }
        } else {
            Log.e(TAG, "getExternalFileDirectory fail ,the reason is sdCard unMounted ");
        }
        return appFileDir;
    }

    /**
     * 获取内存缓存目录 /data/data/app_package_name/files
     *
     * @param type 子目录，可以为空，为空直接返回一级目录
     * @return 缓存目录文件夹 或 null（创建目录文件失败）
     * 注：该方法获取的目录是能供当前应用自己使用，外部应用没有读写权限，如 系统相机应用
     */
    public static File getInternalFileDirectory(Context context, String type) {
        File appFileDir = null;
        if (TextUtils.isEmpty(type)) {
            appFileDir = context.getFilesDir();// /data/data/app_package_name/files
        } else {
            appFileDir = new File(context.getFilesDir(), type);// /data/data/app_package_name/files/type
        }

        if (!appFileDir.exists() && !appFileDir.mkdirs()) {
            Log.e(TAG, "getInternalFileDirectory fail ,the reason is make directory fail !");
        }
        return appFileDir;
    }

    /**
     * 得到源文件路径的所有文件
     * eg:
     * 输入：
     * h1/h2/h3/34.txt
     * h1/h22
     * 结果：34.txt h22
     *
     * @param dirFile 源文件路径
     */
    public static List<File> getAllFile(File dirFile) {

        List<File> fileList = new ArrayList<File>();

        File[] files = dirFile.listFiles();
        for (File file : files) {//文件
            if (file.isFile()) {
                fileList.add(file);
                System.out.println("add file:" + file.getName());
            } else {//目录
                if (file.listFiles().length != 0) {//非空目录
                    fileList.addAll(getAllFile(file));//把递归文件加到fileList中
                } else {//空目录
                    fileList.add(file);
                }
            }
        }
        return fileList;
    }

    /**
     * 遍历所有文件/文件夹
     * 该工具与 getAllFile 有重合的地方
     *
     * @param file    源文件路径
     * @param files   返回集合
     * @param withDir 是否包含目录
     *                eg:
     *                输入：
     *                h1/h2/h3/34.txt
     *                h1/h22
     *                结果：h1 h2 h3 34.txt h22
     */
    public static void flatFileWithDir(File file, List<File> files, boolean withDir) {
        try {
            if (file == null || !file.exists()) {
                return;
            }
            if (withDir) {
                files.add(file);
            } else if (file.isFile()) {
                files.add(file);
            }
            if (!file.isFile()) {
                File[] children = file.listFiles();
                if (children != null) {
                    for (File aChildren : children) {
                        flatFileWithDir(aChildren, files, withDir);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制 srcFile 到 destFile
     *
     * @param srcFile  来源文件
     * @param destFile 目标文件
     * @return 是否成功
     */
    public static boolean copyFile(File srcFile, File destFile) {
        return copyFile(srcFile, destFile, false);
    }

    /**
     * 复制 srcFile 到 destFile
     *
     * @param srcFile   来源文件
     * @param destFile  目标文件
     * @param overwrite 是否覆盖
     * @return 是否成功
     */
    public static boolean copyFile(File srcFile, File destFile, boolean overwrite) {
        try {
            if (!srcFile.exists()) {
                return false;
            }
            if (destFile.exists()) {
                if (overwrite) {
                    destFile.delete();
                } else {
                    return true;
                }
            } else {
                if (mkdirsIfNotExit(destFile.getParentFile())) {
                    destFile.createNewFile();
                }
            }

            FileInputStream input = null;
            FileOutputStream fos = null;
            try {
                input = new FileInputStream(srcFile);
                fos = new FileOutputStream(destFile);
                byte[] block = new byte[1024 * 50];
                int readNumber = -1;
                while ((readNumber = input.read(block)) != -1) {
                    fos.write(block, 0, readNumber);
                }
                fos.flush();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                try {
                    if (input != null) {
                        input.close();
                        input = null;

                    }
                    if (fos != null) {
                        fos.close();
                        fos = null;
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 不存在的情况下创建 File
     *
     * @param file 文件
     * @return 是否成功
     */
    public static boolean mkdirsIfNotExit(File file) {
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            synchronized (FileUtil.class) {
                return file.mkdirs();
            }
        }
        return true;
    }

    /**
     * 强制删除文件，若失败则延迟 200ms 重试，重试次数 10 次
     * 【注】务必在子线程中调用
     *
     * @param file 待删除文件
     * @return 是否成功
     */
    @WorkerThread
    public static boolean forceDeleteFile(File file) {
        if (file.exists()) {
            boolean result = false;
            int tryCount = 0;
            while (!result && tryCount++ < 10) {
                result = file.delete();
                if (!result) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        Log.e("forceDeleteFile", e.getMessage());
                    }
                }
            }
            return result;
        } else {
            return true;
        }
    }

    /**
     * 清空文件（夹）
     * 【注】务必在子线程中调用
     *
     * @param file 待删除文件
     * @return 是否成功
     */
    @WorkerThread
    public static boolean clear(File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            if (!file.isDirectory()) {
                return FileUtil.forceDeleteFile(file);
            } else {
                boolean ret = true;
                try {
                    File[] files = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        Thread.sleep(1);
                        if (files[i].isDirectory()) {
                            if (!clear(files[i])) {
                                // 只要失败就return false
                                return false;
                            }
                        } else {
                            if (!FileUtil.forceDeleteFile(files[i])) {
                                ret = false;
                                break;
                            }
                        }
                    }
                    final File to = new File(file.getAbsolutePath()
                            + System.currentTimeMillis());
                    file.renameTo(to);
                    FileUtil.forceDeleteFile(to);// 删除空文件夹
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
                return ret;
            }
        } else {
            return true;
        }
    }

    public static boolean writeStream(File file, InputStream is) {
        FileOutputStream os = null;
        try {
            if (file == null || is == null) {
                return false;
            }
            if (file != null) {
                File parentFile = file.getParentFile();
                if (!parentFile.exists()) {
                    boolean mkdirs = parentFile.mkdirs();
                    if (!mkdirs) {
                        return false;
                    }
                }
                if (!file.exists()) {
                    boolean newFile = file.createNewFile();
                    if (!newFile) {
                        return false;
                    }
                }
                os = new FileOutputStream(file);
                int bytesWritten = 0;
                int byteCount;
                byte[] bytes = new byte[1024];
                while ((byteCount = is.read(bytes)) != -1) {
                    os.write(bytes, bytesWritten, byteCount);
                    bytesWritten += byteCount;
                }
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is!=null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os!=null){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
