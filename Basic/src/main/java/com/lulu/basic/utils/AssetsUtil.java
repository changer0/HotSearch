package com.lulu.basic.utils;

import com.lulu.baseutil.Init;
import com.yuewen.reader.zebra.cache.core.IoUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Author: zhanglulu
 * Time: 2021/2/16
 */
public class AssetsUtil {
    public static String getAssetsFileToString(String fileName){
        String ret = "";
        InputStream is = null;
        try {
            is = Init.app.getResources().getAssets().open(fileName);
            ret = IoUtils.getString(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }
}
