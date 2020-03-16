package com.qq.reader.common.monitor.net;

import com.qq.reader.common.utils.ParamEncryptHelper;
import com.qq.reader.common.utils.ServerUrl;
import com.qq.reader.core.http.Http;
import com.qq.reader.core.readertask.tasks.ReaderJSONNetTaskListener;
import com.qq.reader.core.readertask.tasks.ReaderProtocolJSONTask;
import com.tencent.mars.xlog.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class ReadTimeUploadTask extends ReaderProtocolJSONTask {
    private JSONObject mJsonObject;

    public ReadTimeUploadTask(ReaderJSONNetTaskListener listener,
                              JSONObject json) {
        super(listener);
        mJsonObject = json;
        mUrl = ServerUrl.DOMAINNAME_ADR  + "read-time/reportReadTime";
    }

    @Override
    public String getRequestMethod() {
        return Http.POST;
    }

    @Override
    public boolean isUseQQReaderThreadPool() {
        return true;
    }

    @Override
    public String getRequestContent() {
        String content = "";
        if(mJsonObject != null) {
            try {
                ParamEncryptHelper.EncryptKVBean encryptKVBean = ParamEncryptHelper.encryptString(mJsonObject.toString());
                JSONObject encryptJSON = new JSONObject();
                encryptJSON.put("encryptKey", encryptKVBean.getEncryptKey());
                encryptJSON.put("encryptValue", encryptKVBean.getEncryptValue());
                content = encryptJSON.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d("ReadTimeUploadTask", "mJsonObject " + mJsonObject.toString());
        return content;
    }

    @Override
    public String getContentType() {
        return "application/json";
    }

    @Override
    public boolean isRequestGzip() {
        return true;
    }

    @Override
    protected boolean interuptNoConn() {
        return true;
    }

}
