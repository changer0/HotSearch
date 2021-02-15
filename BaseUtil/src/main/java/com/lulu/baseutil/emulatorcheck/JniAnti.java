package com.lulu.baseutil.emulatorcheck;

import android.text.TextUtils;

import static com.lulu.baseutil.emulatorcheck.Util.antiFile;
import static com.lulu.baseutil.emulatorcheck.Util.antiProperty;
import static com.lulu.baseutil.emulatorcheck.Util.antiPropertyValueContains;

/**
 * Created by weilai1 on 2017/5/15.
 */

public class JniAnti {

    public static String getCpuinfo() {
        try {
            String cpuData = Util.getCommandReturn("cat /proc/cpuinfo");
            if (cpuData != null) {
                cpuData = cpuData.replace("\n", "\0").replace("\r", "\0");
                String[] lines = cpuData.split("\0");
                for (String line : lines) {
                    if (!TextUtils.isEmpty(line) && (line.contains("Hardware")
                            || line.contains("model name"))) {
                        String[] params = line.split(":");
                        if (params != null && params.length == 2) {
                            String result = params[1];
                            if (!TextUtils.isEmpty(result)) {
                                return result.trim();
                            }
                        }
                    }
                }
            }
        } catch (Throwable e) { }

        return "";
    }

    /**
     * 获取内核版本号
     * */
    public static String getKernelVersion() {
        try {
            String kernelVersion = Util.getCommandReturn("cat /proc/version");
            kernelVersion = kernelVersion.replace("\r", "\0").replace("\n", "\0");
            if (!TextUtils.isEmpty(kernelVersion)) {
                kernelVersion = kernelVersion.trim();
            }
            return kernelVersion;

        } catch (Throwable e) {}

        return "";
    }


    /**获取模拟器特征文件数量*/
    public static int checkAntiFile() {
        int i = 0;
        i += antiFile("/system/bin/qemu_props"); //检测原生模拟器
        // antiFile("/system/bin/qemud");  //小米会检测出此项
        i += antiFile("/system/bin/androVM-prop");
        i += antiFile("/system/bin/microvirt-prop");//逍遥
        i += antiFile("/system/lib/libdroid4x.so"); //海马玩
        i += antiFile("/system/bin/windroyed");//文卓爷
        i += antiFile("/system/bin/microvirtd");//逍遥
        i += antiFile("/system/bin/nox-prop"); //夜神
        i += antiFile("/system/bin/ttVM-prop"); //天天
        i += antiFile("/system/bin/droid4x-prop"); //海马玩
        i += antiFile("/data/.bluestacks.prop");//bluestacks
        i += antiProperty("init.svc.vbox86-setup"); //基于vitrualbox
        i += antiProperty("init.svc.droid4x"); //海马玩
        i += antiProperty("init.svc.qemud");
        i += antiProperty("init.svc.su_kpbs_daemon");
        i += antiProperty("init.svc.noxd"); //夜神
        i += antiProperty("init.svc.ttVM_x86-setup"); //天天
        i += antiProperty("init.svc.xxkmsg");
        i += antiProperty("init.svc.microvirtd");//逍遥
//    antiProperty("ro.secure");   //检测selinux是否被关闭，一般手机均开启此选项
        i += antiProperty("ro.kernel.android.qemud");
        //  antiProperty("ro.kernel.qemu.gles"); //三星SM-G5500误报此项
        i += antiProperty("androVM.vbox_dpi");
        i += antiProperty("androVM.vbox_graph_mode");
        i += antiPropertyValueContains("ro.product.manufacturer",
                "Genymotion");

        return i;
    }
}
