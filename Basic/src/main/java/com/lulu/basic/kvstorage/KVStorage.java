package com.lulu.basic.kvstorage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.getkeepsafe.relinker.ReLinker;
import com.tencent.mmkv.MMKV;
import com.tencent.mmkv.MMKVLogLevel;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class KVStorage {
    public static final String KEY_MMKV_MIGRATE = "key_mmkv_migrate";
    public static final String KEY_MMKV_SP_NEED_MIGTATE = "key_mmkv_sp_need_migrate";
    public static final String KEY_MMKV_SP_MIGTATED = "key_mmkv_sp_migrated";
    public static final String STRING_SPLIT = "!@#";
    private static final String TAG = "KVStorage";

    private static Map<String, IKVStorageExecutor> KVStorageExecuters = Collections.synchronizedMap(new HashMap<String, IKVStorageExecutor>());

    private static ILoggerAgent mLoggerAgent;

    /**
     * 默认初始化
     * 默认存储在context.getFilesDir().getAbsolutePath() + "/mmkv"路径中。
     *
     * @param context
     */
    public static String init(Context context) {
        String root = context.getFilesDir().getAbsolutePath() + "/mmkv";
        return init(context, root, null);
    }

    /**
     * 默认初始化
     * 默认存储在context.getFilesDir().getAbsolutePath() + "/mmkv"路径中。
     *
     * @param context     application上下文
     * @param loggerAgent 实现日志代理接口，KVStorage中可以使用宿主的log系统来打日志。
     */
    public static String init(Context context, ILoggerAgent loggerAgent) {
        String root = context.getFilesDir().getAbsolutePath() + "/mmkv";
        return init(context, root, loggerAgent);
    }

    /**
     * 初始化
     * 存储到指定位置
     *
     * @param context application上下文
     * @param rootDir kv文件存储的位置
     */
    public static String init(Context context, String rootDir) {
        return init(context, rootDir, null);
    }


    public static void initNeedMigrateSPNameList(String[] needMigrateSPNames) {

        StringBuilder needMigrateSPNamesSB = new StringBuilder();
        for (String name : needMigrateSPNames) {
            needMigrateSPNamesSB.append(name).append(STRING_SPLIT);
        }
        if (needMigrateSPNamesSB.toString().endsWith(STRING_SPLIT)) {
            needMigrateSPNamesSB.delete(needMigrateSPNamesSB.length() - 3, needMigrateSPNamesSB.length());
        }
        SharedPreferences.Editor editor = obtainEditor(KEY_MMKV_MIGRATE, MMKV.MULTI_PROCESS_MODE)
                .putString(KEY_MMKV_SP_NEED_MIGTATE, needMigrateSPNamesSB.toString());
        if (mLoggerAgent != null) {
            mLoggerAgent.i(TAG, "initNeedMigrateSPNameList needMigrateSPNamesSB" + needMigrateSPNamesSB.toString(), true);
        }
        if (editor instanceof IKVStorageExecutor) {
            ((IKVStorageExecutor) editor).sync();
        }
    }

    /**
     * 初始化
     * 存储到指定位置。
     *
     * @param context     application上下文
     * @param rootDir     kv文件存储的位置
     * @param loggerAgent 实现日志代理接口，KVStorage中可以使用宿主的log系统来打日志。
     */
    public static String init(final Context context, String rootDir, ILoggerAgent loggerAgent) {
        mLoggerAgent = loggerAgent;
        MMKV.LibLoader libLoader = null;
        // 官方文档中描述在targetsdkverison=19的部分手机上可能会出错，需要特殊处理一下，文档如下。
        // https://github.com/Tencent/MMKV/wiki/android_advance_cn#%E8%87%AA%E5%AE%9A%E4%B9%89-library-loader
        if (android.os.Build.VERSION.SDK_INT <= 19) {
            libLoader = new MMKV.LibLoader() {
                @Override
                public void loadLibrary(String libName) {
                    if (mLoggerAgent != null) {
                        mLoggerAgent.i(TAG, "start load library" + libName, true);
                    }
                    ReLinker.loadLibrary(context, libName);
                }
            };
        }
        //默认info级别。
        MMKVLogLevel logLevel = MMKVLogLevel.LevelInfo;
        return MMKV.initialize(rootDir, libLoader, logLevel);
    }

    @SuppressLint("LongLogTag")
    public static void checkAndMigrate(Context context, IMigrateListener migrateListener) {
        long time1 = System.currentTimeMillis();
        String hasMigratedNames = getHasMigratedSPNames();
        String needMigrateSpNames = getNeedMigratSPNames();
        List<String> needMigrateSpNameList = Arrays.asList(needMigrateSpNames.split(STRING_SPLIT));
        if (needMigrateSpNameList.size() > 0) {
            Iterator<String> iterator = needMigrateSpNameList.iterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                if (!TextUtils.isEmpty(next) && !hasMigratedNames.contains(next)) {
                    hasMigratedNames = getHasMigratedSPNames();
                    needMigrateSpNames = getNeedMigratSPNames();
                    migrate(context, needMigrateSpNames, hasMigratedNames, next, migrateListener);
                }
            }
        }
        long time2 = System.currentTimeMillis();
        if (mLoggerAgent != null) {
            mLoggerAgent.i(TAG, (time2 - time1) + "ms", true);
        }
    }

    private static void migrate(Context context,
                                String needMigrateSpNames,
                                String hasMigratedNames,
                                String needMigrateSpName,
                                IMigrateListener migrateListener) {
        if (!TextUtils.isEmpty(needMigrateSpName)) {
            if (mLoggerAgent != null) {
                mLoggerAgent.i(TAG, "migrate needMigrateSpName:" + needMigrateSpName, true);
            }
            IKVStorageExecutor kvStorageExecuter = (KVStorageExecutor) obtainKVStorageExecutor(needMigrateSpName, MMKV.SINGLE_PROCESS_MODE);
            SharedPreferences sharedPreferences = context.getSharedPreferences(needMigrateSpName, Context.MODE_PRIVATE);
            if (sharedPreferences.getAll().size() <= 0) {
                addHasMigratedSPNames(hasMigratedNames, needMigrateSpName);
                removeNeedMigrateSPNames(needMigrateSpNames, needMigrateSpName);
                if (migrateListener != null) {
                    migrateListener.onFail(needMigrateSpName);
                }
                return;
            }
            if (migrateListener != null) {
                migrateListener.onStart(needMigrateSpName);
            }
            kvStorageExecuter.importFromSharedPreferences(sharedPreferences);
            if (mLoggerAgent != null) {
                mLoggerAgent.i(TAG, "importFromSharedPreferences:" + needMigrateSpName, true);
            }
            sharedPreferences.edit().clear().apply();
            addHasMigratedSPNames(hasMigratedNames, needMigrateSpName);
            removeNeedMigrateSPNames(needMigrateSpNames, needMigrateSpName);
            if (mLoggerAgent != null) {
                mLoggerAgent.i(TAG, "removeNeedMigrateSPNames:" + needMigrateSpName, true);
            }
            if (migrateListener != null) {
                migrateListener.onComplete(needMigrateSpName);
            }
        }
    }

    private static String getHasMigratedSPNames() {
        return obtainSP(KEY_MMKV_MIGRATE).getString(KEY_MMKV_SP_MIGTATED, "");
    }

    private static String getNeedMigratSPNames() {
        return obtainSP(KEY_MMKV_MIGRATE).getString(KEY_MMKV_SP_NEED_MIGTATE, "");
    }

    /**
     * 添加一个需要merge的 sp
     *
     * @param needMigrateSpNames
     * @param spName
     */
    private static void addNeedMigrateSPNames(String needMigrateSpNames, String spName) {
        obtainEditor(KEY_MMKV_MIGRATE).putString(KEY_MMKV_SP_NEED_MIGTATE, needMigrateSpNames + spName + STRING_SPLIT);
    }

    private static void addHasMigratedSPNames(String hasMigratedNames, String spName) {
        obtainEditor(KEY_MMKV_MIGRATE).putString(KEY_MMKV_SP_MIGTATED, hasMigratedNames + spName + STRING_SPLIT);
    }

    private static void removeNeedMigrateSPNames(String needMigrateSpNames, String hasMigratedName) {
        obtainEditor(KEY_MMKV_MIGRATE).putString(KEY_MMKV_SP_NEED_MIGTATE, needMigrateSpNames.replace(hasMigratedName + STRING_SPLIT, ""));
    }

    protected static void doCommit(SharedPreferences.Editor e) {
        doCommit(e, false);
    }

    protected static void doCommit(SharedPreferences.Editor e, boolean isCommit) {
        if (isCommit) {
            e.commit();
        } else {
            e.apply();
        }
    }

    protected static SharedPreferences obtainSP(String name) {
        return obtainSP(name, Context.MODE_PRIVATE);
    }

    protected static SharedPreferences obtainSP(String name, int mode) {
        int realMode;
        switch (mode) {
            case Context.MODE_MULTI_PROCESS:
                realMode = MMKV.MULTI_PROCESS_MODE;
                break;
            case Context.MODE_PRIVATE:
            default:
                realMode = MMKV.SINGLE_PROCESS_MODE;
                break;
        }

        IKVStorageExecutor kvStorageExecuter = KVStorageExecuters.get(name);
        if (kvStorageExecuter != null) {
            return kvStorageExecuter;
        } else {
            kvStorageExecuter = new KVStorageExecutor(MMKV.mmkvWithID(name, realMode));
            KVStorageExecuters.put(name, kvStorageExecuter);
        }
        return kvStorageExecuter;
    }

    protected static SharedPreferences.Editor obtainEditor(String name) {
        return obtainEditor(name, Context.MODE_PRIVATE);
    }

    protected static SharedPreferences.Editor obtainEditor(String name, int mode) {
        return obtainSP(name, mode).edit();
    }

    private static IKVStorageExecutor obtainKVStorageExecutor(String name) {
        return obtainKVStorageExecutor(name, MMKV.SINGLE_PROCESS_MODE);
    }

    private static IKVStorageExecutor obtainKVStorageExecutor(String name, int mode) {
        IKVStorageExecutor kvStorageExecuter = KVStorageExecuters.get(name);
        if (kvStorageExecuter != null) {
            return kvStorageExecuter;
        } else {
            kvStorageExecuter = new KVStorageExecutor(MMKV.mmkvWithID(name, mode));
            KVStorageExecuters.put(name, kvStorageExecuter);
        }
        return kvStorageExecuter;
    }

    public static String[] allKeys(String name) {
        IKVStorageExecutor kvStorageExecuter = KVStorageExecuters.get(name);
        if (kvStorageExecuter != null) {
            return kvStorageExecuter.allKeys(name);
        } else {
            kvStorageExecuter = new KVStorageExecutor(MMKV.mmkvWithID(name, MMKV.SINGLE_PROCESS_MODE));
            KVStorageExecuters.put(name, kvStorageExecuter);
        }
        return kvStorageExecuter.allKeys(name);
    }

}
