package com.lulu.component.download;

import androidx.annotation.Nullable;

public interface DownloadListener {

    void onStart(int id);

    void onProgressChanged(int id ,long offset, long totalLength, String speed);

    void onPause(int id);

    void onCancel(int id);

    void onFailed(int id,@Nullable String msg);

    void onSuccess(int id, String averageSpeed);

}
