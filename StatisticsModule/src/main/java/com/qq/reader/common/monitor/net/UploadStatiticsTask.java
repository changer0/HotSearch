package com.qq.reader.common.monitor.net;

import com.qq.reader.common.monitor.StatisticsAgent;
import com.qq.reader.core.http.Http;
import com.qq.reader.core.readertask.ReaderTask;
import com.qq.reader.core.readertask.tasks.ReaderJSONNetTaskListener;
import com.qq.reader.core.readertask.tasks.ReaderReportTask;
import com.tencent.mars.xlog.Log;

import org.json.JSONObject;

public class UploadStatiticsTask extends ReaderReportTask {
    private JSONObject mJsonObject;

    public UploadStatiticsTask(ReaderJSONNetTaskListener listener,
                               JSONObject json) {
        super(listener);
        //设置为使用阅文自己的线程池
        //  最终上报 需要查看开这里
//            Log.d("StatisticsManager", "upload json = " + json.toString());
        mJsonObject = json;
        if (StatisticsAgent.getInstance().getConfig() != null) {
            mUrl = StatisticsAgent.getInstance().getConfig().getServerUrl() + "common/log";
        } else {
            Log.e("UploadStatiticsTask", "StatisticsAgent.getInstance().getConfig bug 拿不到地址");
        }
        // 只重试1次
        setFailedType(ReaderTask.AUTO_RETRY, 1);
    }

    @Override
    public String getRequestMethod() {
        return Http.POST;
    }

    @Override
    public String getRequestContent() {
        String content = "";
        if(mJsonObject != null) {
            content = mJsonObject.toString();
        }
        Log.d("UploadStatiticsTask", "mJsonObject " + mJsonObject.toString());
        return content;
    }

    @Override
    public String getContentType() {
        return "application/json";
    }

    @Override
    protected boolean interuptNoConn() {
        return true;
    }

    @Override
    public boolean isRequestGzip() {
        return true;
    }

    @Override
    public boolean isUseQQReaderThreadPool() {
        return true;
    }

}
