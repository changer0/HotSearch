package com.yuewen.reader.bookstore.define;

import android.os.Bundle;

/**
 * 加载信号
 */
public class LoadSignal {
    public static final String LOAD_SIGNAL = "LOAD_SIGNAL";
    public static final int LOAD_SIGNAL_DEFAULT = -1;
    public static final int LOAD_SIGNAL_INIT = 0;
    public static final int LOAD_SIGNAL_REFRESH = 1;
    public static final int LOAD_SIGNAL_MORE = 2;

    /**
     * 生成加载信号的 Bundle
     * @param loadSignal 加载信号
     * @param bundle 原 Bundle
     * @return 生成的 Bundle
     */
    public static Bundle generateLoadBundle(int loadSignal, Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putInt(LoadSignal.LOAD_SIGNAL, loadSignal);
        return bundle;
    }

    /**
     * 解析 Signal
     * @param bundle 待传入 Bundle
     * @return Signal
     */
    public static int parseSignal(Bundle bundle) {
        if (bundle == null) {
            return LOAD_SIGNAL_DEFAULT;
        }
        return bundle.getInt(LOAD_SIGNAL, LOAD_SIGNAL_DEFAULT);
    }
}
