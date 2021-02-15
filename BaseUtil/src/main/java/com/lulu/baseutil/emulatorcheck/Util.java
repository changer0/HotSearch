package com.lulu.baseutil.emulatorcheck;

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by weilai1 on 2017/5/15.
 */

public class Util {

    public static String readFile(String str) {
        File file = new File(str);
        StringBuilder sb = new StringBuilder();
        if (file.exists()) {
            try {
                String line = null;
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                fileInputStream.close();
                return sb.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String convertSize(int i) {
        int size = i / 1000;
        if (size > 360 && size < 440) {
            return "400M";
        }
        if (size > 460 && size < 540) {
            return "500M";
        }
        if (size > 560 && size < 640) {
            return "600M";
        }
        if (size > 660 && size < 740) {
            return "700M";
        }
        if (size > 760 && size < 840) {
            return "800M";
        }
        if (size > 860 && size < 940) {
            return "900M";
        }
        if (size > 960 && size < 1040) {
            return "1G";
        }
        if (size < 1000) {
            return String.format("%dM", new Object[]{Integer.valueOf(size)});
        }
        return String.format("%.1fG", new Object[]{Float.valueOf(((float) size) / 1000.0f)});
    }

    public static String tempToStr(float temp, int tempSetting) {
        if (temp <= 0.0f) {
            return "";
        }
        if (tempSetting == 2) {
            return String.format("%.1f°F", new Object[]{Float.valueOf(((9.0f * temp) + 160.0f) / 5.0f)});
        }
        return String.format("%.1f°C", new Object[]{Float.valueOf(temp)});
    }

    public static String getCommandReturn(String cmd) throws Throwable {

        Process p = Runtime.getRuntime().exec(cmd);
        String data = null;
        BufferedReader ie = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String error = null;
        while ((error = ie.readLine()) != null
                && !error.equals("null")) {
            data += error + "\n";
        }
        String line = null;
        while ((line = in.readLine()) != null
                && !line.equals("null")) {
            data += line + "\n";
        }
        return data;
    }

    public static int antiFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int antiProperty(String propertyName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propertyName);
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (line != null) {
            line = line.trim();
        }
        if (!TextUtils.isEmpty(line)) {
            return 1;
        }
        return 0;
    }

    public static int antiPropertyValueContains(String propertyName, String value) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propertyName);
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (line != null) {
            line = line.trim();
        }
        if (!TextUtils.isEmpty(line)) {
            if (line.contains(value)) {
                return 1;
            }
        }
        return 0;
    }
}
