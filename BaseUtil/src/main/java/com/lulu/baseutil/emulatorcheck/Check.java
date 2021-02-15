package com.lulu.baseutil.emulatorcheck;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import static android.content.Context.SENSOR_SERVICE;
import static android.hardware.Sensor.TYPE_GRAVITY;
import static com.lulu.baseutil.emulatorcheck.Util.tempToStr;

/**
 * Created by weilai1 on 2017/5/15.
 */

public class Check {
    private final static String CPUFREQ_CPUINFO_MAX_FREQ = "/cpufreq/cpuinfo_max_freq";
    private final static String CPUFREQ_CPUINFO_MIN_FREQ = "/cpufreq/cpuinfo_min_freq";
    private final static String CPUFREQ_SCALING_CUR_FREQ = "/cpufreq/scaling_cur_freq";


    /**
     * 获取重力传感器
     * */
    public static boolean checkGravity(Context context) {
        boolean z = false;
        List<Sensor> defaultSensor = ((SensorManager) context.getSystemService(SENSOR_SERVICE)).getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : defaultSensor) {
            if (sensor.getType() == TYPE_GRAVITY) { //不能使用getName去判断是否存在重力感应器，应交与系统判断
                z = true;
                break;
            }
        }
        return z;
    }

    public static boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    /**
     * 获取CPU最大频率
     * */
    public static int getCpuMaxFrequency() {
        File file = new File("/sys/devices/system/cpu");
        if (!file.exists()) {
            return 0;
        }
        File[] listFiles = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return Pattern.matches("cpu[0-9]", pathname.getName());
            }
        });
        if (listFiles == null || listFiles.length <= 0) {
            return 0;
        }
        List arrayList = new ArrayList();
        for (File absolutePath : listFiles) {
            String path = absolutePath.getAbsolutePath();
            try {
                int max = Math.max(Math.max(Integer.parseInt(Util.readFile(path + CPUFREQ_CPUINFO_MAX_FREQ)), Integer.parseInt(Util.readFile(path + CPUFREQ_SCALING_CUR_FREQ))), Integer.parseInt(Util.readFile(path + CPUFREQ_CPUINFO_MIN_FREQ)));
                if (max > 0) {
                    arrayList.add(Integer.valueOf(max));
                }
            } catch (Throwable th) {
            }
        }
        if (arrayList.isEmpty()) {
            return 0;
        }
        Collections.sort(arrayList);
        return ((Integer) arrayList.get(arrayList.size() - 1)).intValue();
    }

    /**
     * 获取电池温度
     * */
    public static String getBatteryTemp(Context act) {
        if (act == null) {
            return null;
        }
        Intent batteryStatus = act.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (batteryStatus == null) {
            return null;
        }
        int temp = batteryStatus.getIntExtra("temperature", -1);
        if (temp > 0) {
            return tempToStr(((float) temp) / 10.0f, 1);
        }
        return null;
    }

    /**
     * 获取电池电压
     * */
    public static String getBatteryVolt(Context act) {
        if (act == null) {
            return null;
        }
        Intent batteryStatus = act.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (batteryStatus == null) {
            return null;
        }
        int volt = batteryStatus.getIntExtra("voltage", -1);
        if (volt > 0) {
            return String.valueOf(volt);
        }
        return null;
    }

}
