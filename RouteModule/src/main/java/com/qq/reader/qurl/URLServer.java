package com.qq.reader.qurl;

import android.app.Activity;
import android.os.Message;

import com.qq.reader.service.app.AppRouterService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 每一个实现类对应服务器端的一个Server Name
 * Created by yangtao on 2015/8/24.
 */
public abstract class URLServer {
    private String mParameter = "";
    private String mServerAction = "";
    private Map<String, String> parameterMap;
    private String mDataQURL = null;
    private WeakReference<Activity> mBindActivity;
    private WeakReference<URLCallBack> mURLCallBack = null;
    private int requestCode = -1;
    private JumpActivityParameter jp;
    private List<String> mMatchServerActionPool;

    public URLServer(final Activity activity, final String serverAction, final String parameter) {
        mParameter = parameter;
        mServerAction = serverAction;
        mBindActivity = new WeakReference<Activity>(activity);
        parameterMap = URLCenter.getQueryStringMap(mParameter);
        jp = new JumpActivityParameter();
        mMatchServerActionPool = new ArrayList<String>();
        initMatchServerActionPool(mMatchServerActionPool);
    }

    /**
     * 子类初始化自己能处理的ServerAction
     */
    public abstract void initMatchServerActionPool(List<String> mMatchServerActionPool);

    /**
     * 将getServerAction()从mMatchServerActionPool进行匹配
     *
     * @return
     */
    public boolean isMatch() {
        return mMatchServerActionPool.contains(getServerAction());
    }

    /**
     * urlServer 自己去处理那些没有匹配到的Server action
     */
    public void doExcuteNotMatchURL() {
        AppRouterService.checkUpgradeManual(mBindActivity.get());
    }

    public void setRequestCode(int code) {
        requestCode = code;
        jp.setRequestCode(code);
    }

    public void bindJumpActivityParameter(JumpActivityParameter j) {
        if (j != null) {
            jp = j;
        }
    }

    public JumpActivityParameter getJumpActivityParameter() {
        return jp;
    }

    protected Activity getBindActivity() {
        return mBindActivity.get();
    }

    public void setURLCallBack(URLCallBack uRLCallBack) {
        if (uRLCallBack == null) {
            return;
        }
        mURLCallBack = new WeakReference<URLCallBack>(uRLCallBack);
    }

    protected boolean doURLJumpReslut(Message msg) {
        if (mURLCallBack == null) {
            return false;
        }
        URLCallBack callBack = mURLCallBack.get();
        if (callBack != null) {
            return callBack.qURLJumpResult(msg);

        }
        return false;
    }

    public void setDataQURL(String dataQURL) {
        mDataQURL = dataQURL;
    }

    public String getDataQURL() {
        return mDataQURL;
    }

    public String getServerAction() {
        return mServerAction;
    }

    public Map<String, String> getParameterMap() {
        return parameterMap;
    }

    public abstract boolean parserURL() throws Exception;

}
