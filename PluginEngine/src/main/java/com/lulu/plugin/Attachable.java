package com.lulu.plugin;

public interface Attachable<T> {
    void attach(T proxy, PluginApk apk);
}
