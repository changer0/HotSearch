package com.qq.reader.common.monitor.statparam;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import android.text.TextUtils;

import com.qq.reader.common.monitor.StatisticsConstant;
import com.qq.reader.core.db.SDSQLiteOpenHelper;
import com.qq.reader.core.readertask.ReaderTaskHandler;
import com.qq.reader.core.readertask.tasks.ReaderDBTask;
import com.qq.reader.statistics.BuildConfig;
import com.tencent.mars.xlog.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zhanglulu on 2019/9/20.
 * for 来源信息上报参数持久化 处理类
 */
public class OriginStatParamHandle {
    public static final String TAG = "OriginStatParamHandle";
    private static SDSQLiteOpenHelper dbHelper;
    private static OriginStatParamHandle instance = null;

    private ConcurrentHashMap<String, OriginStatParam> mParamCache;
    // Version must be >= 1, was 0
    private static final int VERSION_1 = 1;
    public static int DB_VERSION = VERSION_1;

    public static OriginStatParamHandle getInstance() {
        if (instance == null) {
            synchronized (OriginStatParamHandle.class) {
                if (instance == null) {
                    instance = new OriginStatParamHandle();
                }
            }
        }
        return instance;
    }

    private OriginStatParamHandle() {
        dbHelper = new SDDatabaseHelper(StatisticsConstant.STAT_PARAM_DB, null, DB_VERSION);
        mParamCache = new ConcurrentHashMap<>();
    }

    private class SDDatabaseHelper extends SDSQLiteOpenHelper {

        public SDDatabaseHelper(String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //udpate
        }
    }

    public static final String ID = "_id";
    public static final String TABLE_NAME = "stat_param";
    public static final String BOOK_ID = "bid";
    public static final String ORIGIN = "origin";
    public static final String ALG = "alg";

    private void createTable(SQLiteDatabase db) {
        try {
            db.execSQL("create table if not exists " + TABLE_NAME + " ("
                    + ID + " integer primary key autoincrement,"
                    + BOOK_ID + " text unique not null,"
                    + ORIGIN + "  text,"
                    + ALG + "  text" + ");");
        } catch (Exception e) {
            Log.printErrStackTrace("ChannelHandler", e, null, null);
            Log.e("DB",
                    "createTable channel db with exception : " + e.getMessage());
        }
    }

    /**
     * 添加
     * @param bid
     * @param statParam
     * @param listener
     */
    public synchronized void add(String bid, OriginStatParam statParam, @Nullable StatParamHandleListener<Long> listener) {
        if (statParam == null || TextUtils.isEmpty(bid)) {
            return;
        }
        mParamCache.put(bid, statParam);
        ReaderDBTask readerDBTask = new ReaderDBTask() {
            @Override
            public void run() {
                super.run();

                synchronized (OriginStatParamHandle.this){
                    AtomicLong replaceCount = new AtomicLong(-1);
                    SQLiteDatabase db = null;
                    try {
                        db = dbHelper.getWritableDatabase();
                        if (db != null) {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(BOOK_ID, bid);
                            contentValues.put(ORIGIN, statParam.getOrigin());
                            contentValues.put(ALG, statParam.getAlg());
                            replaceCount.set(db.replace(TABLE_NAME, null, contentValues));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        runOnUiThread(() -> {
                            if (listener != null) {
                                //return the row ID of the newly inserted row, or -1 if an error occurred
                                listener.onResult(replaceCount.get());
                            }
                        });

                        if (dbHelper != null) {
                            dbHelper.close();
                        }

                    }
                }
            }
        };
        ReaderTaskHandler.getInstance().addTask(readerDBTask);
    }

    /**
     * 保存参数到缓存中
     */
    @WorkerThread
    public synchronized void copyStatParamDBToCache() {
        Cursor cursor = null;
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            if (db != null) {
                String[] exp = new String[] {BOOK_ID, ORIGIN, ALG};
                cursor = db.query(TABLE_NAME, exp, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do{
                        String bid = cursor.getString(cursor.getColumnIndex(BOOK_ID));
                        OriginStatParam statParam = new OriginStatParam()
                                .setOrigin(cursor.getString(cursor.getColumnIndex(ORIGIN)))
                                .setAlg(cursor.getString(cursor.getColumnIndex(ALG)));
                        mParamCache.put(bid, statParam);
                    } while (cursor.moveToNext());
                }
            }
            if (BuildConfig.DEBUG) {
                for (Map.Entry<String, OriginStatParam> statParamEntry : mParamCache.entrySet()) {
                    Log.d(TAG, "copyStatParamDBToCache: bid: " + statParamEntry.getKey() + " param: " + statParamEntry.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
    }

    /**
     * 从缓存中获取 上报参数
     * @param bid
     * @return
     */
    @Nullable
    public synchronized OriginStatParam getStatParamCacheByBid(String bid) {
        return mParamCache.get(bid);
    }

    /**
     * 根据 bid 获取上报参数
     * @param bid
     * @param listener
     */
    public synchronized void getStatParamByBid(String bid, @Nullable StatParamHandleListener<OriginStatParam> listener) {
        if (TextUtils.isEmpty(bid)) {
            if (listener != null) {
                listener.onResult(null);
            }
            return;
        }
        ReaderDBTask readerDBTask = new ReaderDBTask() {
            @Override
            public void run() {
                super.run();
                synchronized (OriginStatParamHandle.this){
                    //优先取缓存
                    AtomicReference<OriginStatParam> originStatParamAtomicReference = new AtomicReference<>();
                    OriginStatParam originStatParam = mParamCache.get(bid);
                    if (originStatParam != null ) {
                        originStatParamAtomicReference.set(originStatParam);
                        runOnUiThread(() -> {
                            if (listener != null) {
                                listener.onResult(originStatParamAtomicReference.get());
                            }
                        });
                        return;
                    }
                    originStatParam = new OriginStatParam();
                    mParamCache.put(bid, originStatParam);//添加缓存
                    Cursor cursor = null;
                    SQLiteDatabase db = null;
                    try {
                        db = dbHelper.getWritableDatabase();
                        if (db != null) {
                            String[] exp = new String[] {BOOK_ID, ORIGIN, ALG};
                            cursor = db.query(TABLE_NAME, exp, BOOK_ID + "='" + bid.replace("'", "''") + "'", null, null, null, null);
                            if (cursor.moveToFirst()) {
                                do{
                                    originStatParam.setOrigin(cursor.getString(cursor.getColumnIndex(ORIGIN)))
                                            .setAlg(cursor.getString(cursor.getColumnIndex(ALG)));
                                } while (cursor.moveToNext());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                        if (dbHelper != null) {
                            dbHelper.close();
                        }
                    }
                    originStatParamAtomicReference.set(originStatParam);
                    runOnUiThread(() -> {
                        if (listener != null) {
                            listener.onResult(originStatParamAtomicReference.get());
                        }
                    });
                }

            }
        };
        ReaderTaskHandler.getInstance().addTask(readerDBTask);
    }

    public static void runOnUiThread(RunOnUiThread listener) {
        if (listener != null) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(listener::runOnUiThread);
        }
    }

    public interface RunOnUiThread {
        void runOnUiThread();
    }
}
