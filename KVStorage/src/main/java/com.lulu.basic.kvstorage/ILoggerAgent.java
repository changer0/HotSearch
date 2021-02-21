package com.lulu.basic.kvstorage;

public interface ILoggerAgent {
    void v(String tag, final String log, boolean write2File);

    void d(String tag, final String log, boolean write2File);

    void i(String tag, final String log, boolean write2File);

    void w(String tag, final String log, boolean write2File);

    void e(String tag, final String log, boolean write2File);
}
