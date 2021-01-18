package com.qq.reader.provider.page.compiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * @author zhanglulu
 * 2021/1/18 7:19 PM
 */
public class OtherModuleNameSaver {

    public static final String subModuleNameFile = "data_provider_processor.temp";

    public static void putModuleName(String moduleName) {
        try {
            FileWriter w = new FileWriter(subModuleNameFile, true);
            BufferedWriter bfw = new BufferedWriter(w);
            bfw.write(moduleName);
            bfw.newLine();
            bfw.flush();
            bfw.close();
            w.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getModuleNames() {
        List<String> stringList = new ArrayList<>();
        try {
            FileReader r = new FileReader(subModuleNameFile);
            BufferedReader bwr = new BufferedReader(r);
            String s = bwr.readLine();
            while (s != null) {
                stringList.add(s);
                s = bwr.readLine();
            }
            bwr.close();
            r.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringList;
    }

    public static void clear() {
        File file = new File(subModuleNameFile);
        if (file.exists()) {
            file.delete();
        }
    }
}
