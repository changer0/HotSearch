package com.lulu.component.download;

import androidx.annotation.Nullable;

abstract public class SimpleDownloadListener implements DownloadListener{

    @Override
    public void onStart(int id) {

    }

    @Override
    public void onProgressChanged(int id, long offset, long totalLength, String speed) {

    }

    @Override
    public void onPause(int id) {

    }

    @Override
    public void onCancel(int id) {

    }

    @Override
    abstract public void onFailed(int id, @Nullable String msg);

    @Override
    abstract public void onSuccess(int id, String averageSpeed);
}
