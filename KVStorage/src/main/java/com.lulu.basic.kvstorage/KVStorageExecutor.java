package com.lulu.basic.kvstorage;

import android.content.SharedPreferences;
import com.tencent.mmkv.MMKV;
import java.util.Map;
import java.util.Set;

class KVStorageExecutor implements IKVStorageExecutor {

    final private MMKV mmkv;

    public KVStorageExecutor(MMKV mmkv) {
        this.mmkv = mmkv;
    }


    @Override
    public Map<String, ?> getAll() {
        return mmkv.getAll();
    }

    @Override
    public String getString(String key, String defValue) {
        return mmkv.getString(key, defValue);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        return mmkv.getStringSet(key, defValues);
    }

    @Override
    public int getInt(String key, int defValue) {
        return mmkv.getInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return mmkv.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return mmkv.getFloat(key, defValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return mmkv.getBoolean(key, defValue);
    }

    @Override
    public boolean contains(String key) {
        return mmkv.contains(key);
    }

    @Override
    public Editor edit() {
        return mmkv.edit();
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        mmkv.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        mmkv.unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public Editor putString(String key, String value) {
        return mmkv.putString(key, value);
    }

    @Override
    public Editor putStringSet(String key, Set<String> values) {
        return mmkv.putStringSet(key, values);
    }

    @Override
    public Editor putInt(String key, int value) {
        return mmkv.putInt(key, value);
    }

    @Override
    public Editor putLong(String key, long value) {
        return mmkv.putLong(key, value);
    }

    @Override
    public Editor putFloat(String key, float value) {
        return mmkv.putFloat(key, value);
    }

    @Override
    public Editor putBoolean(String key, boolean value) {
        return mmkv.putBoolean(key, value);
    }

    @Override
    public Editor remove(String key) {
        return mmkv.remove(key);
    }

    @Override
    public Editor clear() {
        return mmkv.clear();
    }

    @Override
    public boolean commit() {
        return mmkv.commit();
    }

    @Override
    public void apply() {
        mmkv.apply();
    }

    @Override
    public int importFromSharedPreferences(SharedPreferences sharedPreferences) {
        return mmkv.importFromSharedPreferences(sharedPreferences);
    }

    @Override
    public void sync() {
        mmkv.sync();
    }

    @Override
    public String[] allKeys(String name) {
        return mmkv.allKeys();
    }
}
