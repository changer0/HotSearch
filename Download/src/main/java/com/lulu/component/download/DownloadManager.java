package com.lulu.component.download;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.SparseArray;

import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.OkDownload;
import com.liulishuo.okdownload.SpeedCalculator;
import com.liulishuo.okdownload.core.breakpoint.BlockInfo;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed;
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 下载管理类
 */
public class DownloadManager {

    private static DownloadManager mInstance;

    private SparseArray<DownloadTask> mUnFinishedTasks = new SparseArray<DownloadTask>();

    public static DownloadManager getInstance(Context c) {
        if (mInstance == null) {
            synchronized (DownloadManager.class) {
                if (mInstance == null) {
                    mInstance = new DownloadManager(c);
                }
            }
        }
        return mInstance;
    }

    private DownloadManager(Context c) {

        //        OkDownload.Builder builder = new OkDownload.Builder(context)
        //                .downloadStore(downloadStore)
        //                .callbackDispatcher(callbackDispatcher)
        //                .downloadDispatcher(downloadDispatcher)
        //                .connectionFactory(connectionFactory)
        //                .outputStreamFactory(outputStreamFactory)
        //                .downloadStrategy(downloadStrategy)
        //                .processFileStrategy(processFileStrategy)
        //                .monitor(monitor);
        //
        //        OkDownload.setSingletonInstance(builder.build());
        //        DownloadDispatcher.setMaxParallelRunningCount(3);
        //        OkDownload.with().downloadDispatcher().cancelAll();

        try {
            OkDownload.setSingletonInstance(new OkDownload.Builder(c).build());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void cancel(int id) {
        DownloadTask task = mUnFinishedTasks.get(id);
        mUnFinishedTasks.remove(id);
        if (task != null) {
            task.cancel();
        }
    }

    public void resume(int id) {
        DownloadTask task = mUnFinishedTasks.get(id);
        if (task != null) {
            task.enqueue(task.getListener());
        }
    }

    public void pause(int id) {
        DownloadTask task = mUnFinishedTasks.get(id);
        if (task != null) {
            task.cancel();
        }
    }

    public int add(String url, String filePath, boolean callbackInUIThread, @Nullable final DownloadListener listener) {
        if (TextUtils.isEmpty(filePath)) {
            if (listener != null) {
                listener.onFailed(-1, "filePath is empty");
            }
            return -1;
        }

        if (TextUtils.isEmpty(url)){
            if (listener != null) {
                listener.onFailed(-1, "download url is empty");
            }
            return -1;
        }

        File file = new File(filePath);
        DownloadTask task = new DownloadTask.Builder(url, file.getParentFile())
                .setFilename(file.getName())
                // the minimal interval millisecond for callback progress
                .setMinIntervalMillisCallbackProcess(30)
                // do re-download even if the task has already been completed in the past.
                .setPassIfAlreadyCompleted(false)
                .setAutoCallbackToUIThread(callbackInUIThread)
                .build();

        mUnFinishedTasks.put(task.getId(), task);

        task.enqueue(new DownloadListener4WithSpeed() {
                         private long totalLength;

                         @Override
                         public void taskStart(@NonNull DownloadTask task) {
                             if (listener != null) {
                                 listener.onStart(task.getId());
                             }
                         }

                         @Override
                         public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {

                         }

                         @Override
                         public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {

                         }

                         @Override
                         public void infoReady(@NonNull DownloadTask task, @NonNull BreakpointInfo info, boolean fromBreakpoint, @NonNull Listener4SpeedAssistExtend.Listener4SpeedModel model) {
                             totalLength = info.getTotalLength();
                         }

                         @Override
                         public void progressBlock(@NonNull DownloadTask task, int blockIndex, long currentBlockOffset, @NonNull SpeedCalculator blockSpeed) {

                         }

                         @Override
                         public void progress(@NonNull DownloadTask task, long currentOffset, @NonNull SpeedCalculator taskSpeed) {
                             if (listener != null) {
                                 listener.onProgressChanged(task.getId(), currentOffset, totalLength, taskSpeed.speed());
                             }
                         }

                         @Override
                         public void blockEnd(@NonNull DownloadTask task, int blockIndex, BlockInfo info, @NonNull SpeedCalculator blockSpeed) {

                         }

                         @Override
                         public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull SpeedCalculator taskSpeed) {
                             switch (cause) {
                                 case CANCELED:
                                     if (mUnFinishedTasks.get(task.getId()) != null) {
                                         if (listener != null) {
                                             listener.onPause(task.getId());
                                         }
                                     } else {
                                         if (listener != null) {
                                             listener.onCancel(task.getId());
                                         }
                                         mUnFinishedTasks.remove(task.getId());
                                     }
                                     break;
                                 case COMPLETED:
                                     if (listener != null) {
                                         listener.onSuccess(task.getId(), taskSpeed.averageSpeed());
                                     }
                                     mUnFinishedTasks.remove(task.getId());
                                     break;
                                 case ERROR:
                                 default:
                                     if (listener != null) {
                                         String causeStr = null;
                                         if (realCause != null) {
                                             causeStr = realCause.getMessage();
                                         }
                                         listener.onFailed(task.getId(), causeStr);
                                     }
                                     mUnFinishedTasks.remove(task.getId());
                                     break;
                             }
                         }

                     }
        );

        return task.getId();
    }


}
