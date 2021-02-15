package com.lulu.baseutil;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 压缩工具集
 */
public class ZipUtil {
    private static final String TAG = "YWZipUtil";
    /**
     * 压缩文件
     *
     * @param dirPath     压缩源文件路径
     * @param zipFileName 压缩目标文件路径
     */
    public static boolean zipCompress(String dirPath, String zipFileName) {

        try {
            File dirFile = new File(dirPath);
            if (!dirFile.exists()) {
                return false;
            }
            List<File> fileList = FileUtil.getAllFile(dirFile);

            byte[] buffer = new byte[FileUtil.BUFFER];
            ZipEntry zipEntry = null;
            int readLength = 0;     //每次读取出来的长度

            // 对输出文件做CRC32校验
            CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(
                    zipFileName), new CRC32());
            ZipOutputStream zos = new ZipOutputStream(cos);

            for (File file : fileList) {

                if (file.isFile()) {   //若是文件，则压缩文件

                    zipEntry = new ZipEntry(getRelativePath(dirPath, file));  //
                    zipEntry.setSize(file.length());
                    zipEntry.setTime(file.lastModified());
                    zos.putNextEntry(zipEntry);

                    InputStream is = new BufferedInputStream(new FileInputStream(file));

                    while ((readLength = is.read(buffer, 0, FileUtil.BUFFER)) != -1) {
                        zos.write(buffer, 0, readLength);
                    }
                    is.close();
                } else {     //若是空目录，则写入zip条目中

                    zipEntry = new ZipEntry(getRelativePath(dirPath, file));
                    zos.putNextEntry(zipEntry);
                }
            }
            zos.close();  //最后得关闭流，不然压缩最后一个文件会出错
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * zip文件解压
     *
     * @param inputFile   待解压文件夹/文件
     * @param destDirPath 解压路径
     */
    public static void zipUncompress(String inputFile, String destDirPath) throws IllegalArgumentException, IOException {
        File srcFile = new File(inputFile);//获取当前压缩文件
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new IllegalArgumentException(srcFile.getPath() + "所指文件不存在");
        }
        ZipFile zipFile = new ZipFile(srcFile);//创建压缩文件对象
        //开始解压
        Enumeration<?> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            Log.i(TAG, "entry.getName():" + entry.getName() + ",entry.isDirectory():" + entry.isDirectory());
            // 如果是文件夹，就创建个文件夹
            if (entry.isDirectory()) {
                String dirPath = destDirPath + "/" + entry.getName();
                File targetFile = new File(dirPath);
                // 保证这个文件的父文件夹必须要存在
                if (!targetFile.getParentFile().exists()) {
                    targetFile.getParentFile().mkdirs();
                }
                targetFile.mkdirs();
            } else {
                // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                File targetFile = new File(destDirPath + "/" + entry.getName());
                // 保证这个文件的父文件夹必须要存在
                if (!targetFile.getParentFile().exists()) {
                    targetFile.getParentFile().mkdirs();
                }
                targetFile.createNewFile();
                // 将压缩文件内容写入到这个文件中
                InputStream is = zipFile.getInputStream(entry);
                FileOutputStream fos = new FileOutputStream(targetFile);
                int len;
                byte[] buf = new byte[1024];
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                // 关流顺序，先打开的后关闭
                fos.close();
                is.close();
            }
        }
    }


    /**
     * 获取相对路径
     *
     * @param dirPath 源文件路径
     * @param file    准备压缩的单个文件
     */
    private static String getRelativePath(String dirPath, File file) {
        File dirFile = new File(dirPath);
        String relativePath = file.getName();

        while (true) {
            file = file.getParentFile();
            if (file == null) break;
            if (file.equals(dirFile)) {
                break;
            } else {
                relativePath = file.getName() + "/" + relativePath;
            }
        }
        return relativePath;
    }
}
