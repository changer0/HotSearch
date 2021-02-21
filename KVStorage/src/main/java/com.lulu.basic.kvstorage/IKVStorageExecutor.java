package com.lulu.basic.kvstorage;

import android.content.SharedPreferences;

public interface IKVStorageExecutor extends SharedPreferences, SharedPreferences.Editor  {
    int importFromSharedPreferences(SharedPreferences sharedPreferences);
    void sync();
    String[] allKeys(String name);
}
