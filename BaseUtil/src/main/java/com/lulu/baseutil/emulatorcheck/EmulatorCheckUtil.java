package com.lulu.baseutil.emulatorcheck;

import android.content.Context;
import android.text.TextUtils;


/**
 * Android 模拟器检测相关工具类
 */

public class EmulatorCheckUtil {


    private static final int FEATURE_CHECK_COUNT = 2;

    private static final String TAG = "EmulatorCheckUtil";

    public static boolean isEmulator(Context context) {
        try {
            int featuresCount = getEmuFeatures(context);
            if (featuresCount >= FEATURE_CHECK_COUNT) {
                return true;
            }
            return false;
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * 获取特征值集合
     */
    private static int getEmuFeatures(Context context) {
        String cpu = JniAnti.getCpuinfo();
        String cpuFreq = Util.convertSize(Check.getCpuMaxFrequency());
        String kernel = JniAnti.getKernelVersion();
        boolean gravity = Check.checkGravity(context);
        String temp = Check.getBatteryTemp(context);
        String volt = Check.getBatteryVolt(context);
        int check = JniAnti.checkAntiFile();
        boolean gps = Check.hasGPSDevice(context);

//        StringBuilder sb = new StringBuilder();
        int code = 0;
        if (cpu.contains("Genuine Intel(R)") || cpu.contains("Intel(R) Core(TM)") || cpu.contains("Intel(R) Pentium(R)") || cpu.contains("Intel(R) Xeon(R)") || cpu.contains("AMD")) {
//            sb.append("特征一：" + cpu + "\n");
//            code |= FEATURE_EMULATOR_CPU_MATCH;
            code++;
        }
        if (kernel.contains("qemu+") || kernel.contains("tencent") || kernel.contains("virtualbox")) {
//            sb.append("特征二：" + kernel + "\n");
//            code |= FEATURE_EMULATOR_KERNEL_MATACH;
            code++;
        }
        if (gravity == false) {
//            sb.append("特征三：" + "无重力感应器\n");
//            code |= FEATURE_NO_GRAVITY_SENSOR;
            code++;
        }
        if (TextUtils.isEmpty(temp)) {
//            sb.append("特征四：" + "无电池温度\n");
//            code |= FEATURE_NO_BATTARY_TEMPERATRUE;
            code++;
        }
        if (TextUtils.isEmpty(volt)) {
//            sb.append("特征五：" + "无电池电压\n");
//            code |= FEATURE_NO_BATTARY_VOLTAGE;
            code++;
        }
        if (check > 0) {
//            sb.append("特征六：" + "模拟器特征文件\n");
//            code += FEATURE_EMULATOR_SPECIFIC_FEATURE_MATCH;
            code++;
        }
        if (gps == false) {
//            sb.append("特征七：" + "无gps\n");
//            code |= FEATURE_NO_GPS;
            code++;
        }
        if (cpuFreq.equals("0M")) {
//            sb.append("特征八：" + "cpu无频率\n");
//            code |= FEATURE_NO_CPU_FREQUENCY;
            code++;
        }
        return code;
    }
}
