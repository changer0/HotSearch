package com.qq.reader.common.monitor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import com.qq.reader.core.config.AppConstant;

import com.qq.reader.core.readertask.ReaderTask;
import com.qq.reader.core.readertask.ReaderTaskHandler;
import com.qq.reader.core.readertask.tasks.ReaderIOTask;
import com.qq.reader.core.utils.FileUtils;
import com.tencent.mars.xlog.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * log上报日志，在上报时保存到文件，成功后删除；
 * 未成功的在未来启动时从文件加入再次发送
 */
public class LocalHashMap extends ConcurrentHashMap<String, Object> {

    private static final String TAG = "LocalHashMap";
    private static final long serialVersionUID = 1001000L;

    // 统计目录
    // 历史数据，冷启动，之前没有上报的数据存储在这个类里
    private String STAT_PATH_HISTORY = AppConstant.ROOT_PATH + "stat/history/";
    // 不同优先级的上报事件
    private String STAT_PATH_LEVEL_HIGH = AppConstant.ROOT_PATH + "stat/high/";
    private String STAT_PATH_LEVEL_NORMAL = AppConstant.ROOT_PATH + "stat/normal/";
    private String STAT_PATH_LEVEL_LOW = AppConstant.ROOT_PATH + "stat/low/";
    private String STAT_PATH_LEVEL_CUSTOMIZE = AppConstant.ROOT_PATH + "stat/";

    public static final int STAT_LEVEL_HIGH = 0;//这里的本地文件不会copy到history，并且都是即时上报
    public static final int STAT_LEVEL_NORMAL = 1;//这里的本地文件会copy到history，并且都是即时上报
    public static final int STAT_LEVEL_LOW = 2;//这里的本地文件会copy到history，非即时上报
    public static final int STAT_LEVEL_HISTORY = 3;

    // 缓存从数据库中加载 一次最多200条
    public static final int MEMORY_MAX_SIZE = 200;// 临时测试200条
    // 高优先级
    public static final int MEMORY_MAX_SIZE_HIGH = 1000;// 临时测试200条

    OnLoadMoreListener mOnLoadMoreListener;

    // 该类上报地址
    public String STAT_PATH;
    private int mType;

    public LocalHashMap(int capacity, int type) {
        super(capacity);
        // 初始化文件保存地址
        mType = type;
        switch (type) {
            case STAT_LEVEL_HIGH:
                STAT_PATH = STAT_PATH_LEVEL_HIGH;
                break;
            case STAT_LEVEL_LOW:
                STAT_PATH = STAT_PATH_LEVEL_LOW;
                break;
            case STAT_LEVEL_HISTORY:
                STAT_PATH = STAT_PATH_HISTORY;
                break;
            case STAT_LEVEL_NORMAL:
            default:
                STAT_PATH = STAT_PATH_LEVEL_NORMAL;
                break;
        }
        initMemory();
    }

    /**
     *
     * 支持自定义文件路径，但路径不能重复。
     *
     * @param capacity
     * @param type
     * @param path
     */
    public LocalHashMap(int capacity, int type, String path) {
        super(capacity);
        // 初始化文件保存地址
        mType = type;
        STAT_PATH = STAT_PATH_LEVEL_CUSTOMIZE + path;
        initMemory();
    }

    @Override
    public Object put(String key, Object value) {
        String string = decode(value);
        saveData(key, string);
        return super.put(key, value);
    }

    @Override
    public Object get(Object key) {
        return super.get(key);
    }

    @Override
    public Object remove(Object key) {
        removeData((String) key);
        return super.remove(key);
    }

    @Override
    public boolean containsKey(Object key) {
        return super.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return super.containsValue(value);
    }

    public void initMemory() {
        String filename = STAT_PATH;
        Log.d(TAG, "path = " + filename);
        ReaderTask task = new ReaderIOTask() {
            public void run() {
                super.run();
                switch (mType) {
                    case STAT_LEVEL_NORMAL:
                    case STAT_LEVEL_LOW:
                        copyFolder(STAT_PATH, STAT_PATH_HISTORY);
                        break;
                }
                // 这个是在主线程还是子线程执行的啊…… 好像是主线程啊 坑啊
                // 要复制 lowlevel normallevel中内容到History中
                initFileToMap(filename, mType);
            }
        };
        ReaderTaskHandler.getInstance().addTask(task);
    }

    public void tryLoadMore(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;

        if (this.size() > 0) {
            if (mOnLoadMoreListener != null) {
                Log.d(TAG, "mOnLoadMoreListener size() > 0" + size());
                mOnLoadMoreListener.onLoad(mType, this.size());
            }
            // 本身内存里还有数据的情况就不加载更多了
            return;
        }
        String filename = STAT_PATH;
        Log.d(TAG, "path = " + filename);
        ReaderTask task = new ReaderIOTask() {
            public void run() {
                super.run();
                // 这个是在主线程还是子线程执行的啊…… 好像是主线程啊 坑啊
                // 要复制 lowlevel normallevel中内容到History中
                initFileToMap(filename, mType);
            }
        };
        ReaderTaskHandler.getInstance().addTask(task);
    }

    public interface OnLoadMoreListener {
        void onLoad(int type, int size);
    }

    /**
     * 从文件中加载数据出来
     *
     * @param filename 文件路径
     * @param level    高优先级首次加载多
     */
    private void initFileToMap(String filename, int level) {
        InputStream is = null;
        File file = new File(filename);
        int size = 0;
        if (file != null && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    int maxSize = MEMORY_MAX_SIZE;
                    if (level == STAT_LEVEL_HIGH) {
                        maxSize = MEMORY_MAX_SIZE_HIGH;
                    }
                    if (size >= maxSize) {
                        Log.d(TAG, "mOnLoadMoreListener " + ((mOnLoadMoreListener != null))
                                + " size " + size + " maxSize " + maxSize + " level " + level);
                        if (mOnLoadMoreListener != null) {
                            mOnLoadMoreListener.onLoad(mType, size);
                        }
                        return;
                    }
                    if (f != null && f.isFile()) {
                        try {
                            String encode = "UTF-8";
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();

                            is = new BufferedInputStream(new FileInputStream(f));
                            int len = 0;
                            byte[] b = new byte[512];
                            while ((len = is.read(b)) != -1) {
                                baos.write(b, 0, len);
                            }

                            byte[] uploadData = baos.toByteArray();
                            String upString = new String(uploadData, encode);
                            Object node = encode(upString);
                            if (node != null) {
                                super.put(f.getName(), node);
                                size++;
                            }
                        } catch (OptionalDataException e) {
                            Log.e("localhashmap", "  error " + e);
                            e.printStackTrace();
                        } catch (IOException e) {
                            Log.e("localhashmap", "  error " + e);
                            e.printStackTrace();
                        } catch (Throwable t) {
                            Log.e("localhashmap", "  error " + t);
                            t.printStackTrace();
                        } finally {
                            try {
                                if (null != is) {
                                    is.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
//                Log.d(TAG, "mOnLoadMoreListener " + ((mOnLoadMoreListener != null))
//                        + " size " + size);
                if (mOnLoadMoreListener != null) {
                    mOnLoadMoreListener.onLoad(mType, size);
                }
            }
        }
    }

    public synchronized void saveData(String filename, String savaData) {
        if (savaData == null) {
            return;
        }
        OutputStream output = null;
        File tmpFile = null;
        try {
            String profilePath = STAT_PATH + filename;
            String tempPath = profilePath + ".tmp";
            tmpFile = new File(tempPath);
            if (tmpFile.exists()) {
                tmpFile.delete();
            }
            boolean createOk = createFile(tmpFile);
            if (createOk) {
                output = new BufferedOutputStream(new FileOutputStream(tmpFile,
                        false));
                output.write(savaData.getBytes("UTF-8"));
                File profile = new File(profilePath);
                if (profile.exists()) {
                    profile.delete();
                }
                tmpFile.renameTo(profile);
            }
        } catch (FileNotFoundException e) {
            Log.e("localhashmap", "  error " + e);
            e.printStackTrace();

        } catch (IOException e) {
            Log.e("localhashmap", "  error " + e);
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void removeData(String key) {
        String filename = STAT_PATH;

        try {
            File file = new File(filename);
            if (file != null && file.isDirectory()) {
                File removefile = new File(filename + key);
                if (removefile != null) {
                    removefile.delete();
                    return;
                }
            }
        } catch (Exception e) {
            Log.printErrStackTrace("localhashmap", e, null, null);
        }
        Log.e("localhashmap", "remove error " + key + "  not found");

    }

    private boolean createFile(File file) {
        boolean createOk = false;
        if (!FileUtils.mkdirsIfNotExit(file.getParentFile())) {
            return false;
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
                createOk = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return createOk;
    }

    public String decode(Object obj) {
        ArrayList<Node> list = (ArrayList<Node>) obj;
        JSONArray ja = new JSONArray();

        for (int i = 0; list != null && i < list.size(); ++i) {
            ja.put(list.get(i).toString());
        }
        return ja.toString();

    }


    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public void copyFolder(String oldPath, String newPath) {

        Log.d(TAG, "copyFolder " + oldPath + " newPath " + newPath);
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                    temp.delete();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();

        }

    }

    public Object encode(String str) {
        try {
            JSONArray ja = new JSONArray(str);
            ArrayList<Node> list = new ArrayList<Node>();
            for (int i = 0; ja != null && i < ja.length(); ++i) {
                String json = (String) ja.opt(i);
                if (json != null) {
                    Node node = new Node();
                    node.other = new JSONObject(json);
                    list.add(node);
                }
            }
            return list;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
