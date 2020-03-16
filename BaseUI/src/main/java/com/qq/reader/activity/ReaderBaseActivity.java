package com.qq.reader.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.VisibleForTesting;
import androidx.test.espresso.IdlingResource;

import com.qq.reader.baseui.R;
import com.qq.reader.common.account.AccountConstant;
import com.qq.reader.common.define.CommonMsgType;
import com.qq.reader.common.download.SystemDownloadManager;
import com.qq.reader.common.login.ILoginNextTask;
import com.qq.reader.common.login.define.LoginConstant;
import com.qq.reader.common.memoryleak.ActivityLeakSolution;
import com.qq.reader.common.monitor.EventNames;
import com.qq.reader.common.monitor.RDM;
import com.qq.reader.common.note.NoteInfo;
import com.qq.reader.common.utils.CommonConfig;
import com.qq.reader.common.utils.CommonConstant;
import com.qq.reader.common.utils.ScreenModeUtils;
import com.qq.reader.core.BaseApplication;
import com.qq.reader.core.qqreadertask.NetworkStateForConfig;
import com.qq.reader.core.utils.BaseContract;
import com.qq.reader.core.utils.WeakReferenceHandler;
import com.qq.reader.core.view.ReaderToast;
import com.qq.reader.module.login.UserInfoBean;
import com.qq.reader.service.IPropsService;
import com.qq.reader.service.login.IUserInfoManagerService;
import com.qq.reader.service.login.LoginRouterService;
import com.qq.reader.service.share.ShareRouterService;
import com.qq.reader.test.utils.EspressoIdlingResourceNetManager;
import com.qq.reader.view.DeepLinkBackView;
import com.qq.reader.ARouterUtils;
import com.qq.reader.view.ReaderProgressDialog;
import com.tencent.mars.xlog.Log;

import java.util.ArrayList;


/**
 * @author SawRen
 * @email: sawren@tencent.com
 * @date 2010-10-9
 */
public class ReaderBaseActivity extends BranchBaseActivity implements Callback {
    private static final String TAG = "ReaderBaseActivity";

    // TODO: p_zlulzhang 2020-02-11 这个变量暂时放在这里，后面务必优化掉
    public static  boolean isNeedUploadBooksFromBookshelf = true;

    protected boolean mUseAnimation = true;

    private boolean mIsFirstResume = true;
    public ILoginNextTask mLoginNextTask;

    //private SkinnableActivityProcesser processer;

//    private TextView debugTextView;

    protected String mBackUrl;
    protected String mBackName;

    public ReaderProgressDialog mProgressDialog;
    private NetworkStateForConfig mNetworkStateForConfig = new NetworkStateForConfig();

    private IUserInfoManagerService.UserInfoListener mUserinfoListener = (userInfoBean, loginFirst) -> {
        IUserInfoManagerService.getInstance()
                .handleBaseActivityOnGetUserInfo(ReaderBaseActivity.this, mHandler, loginFirst);
        onGetUserInfoSuccess(userInfoBean);
    };

    protected void onGetUserInfoSuccess(UserInfoBean userInfoBean) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //支持谷歌提供的凹口屏解决方案
        ScreenModeUtils.supportCutout(getWindow());
//        if(needChangeSkin()) {//这里和开屏sdk有冲突，开屏页面不涉及换肤功能，暂时这么处理
//            processer = new SkinnableActivityProcesser(this, null);
//        }

        if (BaseApplication.Companion.getINSTANCE().isAllowNet()) {
            BaseApplication.getInstance().appNetworkStart(true);
        }

        mHandler = new WeakReferenceHandler(this);

        IntentFilter filterwx = new IntentFilter(LoginConstant.LOGIN_OK_BROADCAST);
        registerReceiver(loginReceiver, filterwx, CommonConstant.BROADCAST_PERMISSION, null);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AccountConstant.BROADCASTRECEIVER_SWITCH_ACCCOUNT);
        registerReceiver(switchAccountBroadcastReceiver, intentFilter);

        registerReceiver(mLoginOutReceiver, new IntentFilter(LoginConstant.LOGOUT_BROADCAST), CommonConstant.BROADCAST_PERMISSION, null);
        //更新下载广告的context以保证在每个activity中都能弹出广告下载成功弹窗
        SystemDownloadManager.getInstance().setContext(this);
    }


    //处理deepLink调起
    public void handleDeepLinkWake() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            mBackUrl = bundle.getString(DeepLinkBackView.DEEP_LINK_BACK_URL);
            Log.i(DeepLinkBackView.TAG, "ReaderBaseActivity -> handleDeepLinkWake -> mBackUrl=" + mBackUrl);
            if (!DeepLinkBackView.isEmpty(mBackUrl)) {
                String backName = bundle.getString(DeepLinkBackView.DEEP_LINK_BACK_NAME);
                ScreenModeUtils.setFullScreenExtra(this, true);
                DeepLinkBackView deepLinkBackView = (DeepLinkBackView) getReaderActionBar().getDeepLinkBackView();
                if (deepLinkBackView != null) {
                    deepLinkBackView.setStatEventListener(new DeepLinkBackView.StatEventListener() {
                        @Override
                        public void statClick() {
                            RDM.stat(EventNames.EVENT_XG103, null);
                        }
                    });
                    deepLinkBackView.setOnStateBarChangeListener(new DeepLinkBackView.onStateBarChangeListener() {
                        @Override
                        public void hide() {
                            ScreenModeUtils.setFullScreenExtra(ReaderBaseActivity.this, true);
                        }

                        @Override
                        public void show() {
                            ScreenModeUtils.setFullScreenExtra(ReaderBaseActivity.this, false);
                        }
                    });
                    deepLinkBackView.setVisibility(View.VISIBLE);
                    deepLinkBackView.setBackUrl(mBackUrl);
                    deepLinkBackView.setBackText(backName);
                    RDM.stat(EventNames.EVENT_XG102, null);
                }
            }
        }
    }


    private BroadcastReceiver switchAccountBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (AccountConstant.BROADCASTRECEIVER_SWITCH_ACCCOUNT.equals(action)) {
                excuteOnSwitchAccount(context);
            }
        }
    };

    private BroadcastReceiver mLoginOutReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: 登出");
            //退出QQ登录时清一下红包缓存
            CommonConfig.setRedPacketJson(null);
            ReaderBaseActivity.isNeedUploadBooksFromBookshelf = true;
            IPropsService propsService = IPropsService.getInstance();
            if (propsService != null) {
                propsService.clearRemoveAdPropsList();
            }
            logOutFinish();
        }
    };

    protected boolean needChangeSkin() {
        return true;
    }


    public void excuteOnSwitchAccount(Context context) {
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.appenderFlush(false);
        unregisterReceiver(loginReceiver);
        unregisterReceiver(switchAccountBroadcastReceiver);
        unregisterReceiver(mLoginOutReceiver);
//        if (processer != null) {
//            processer.destory();
//        }
        ActivityLeakSolution.fixInputMethodManagerLeak(this);

        mHandler.removeCallbacksAndMessages(null);
        for (int i = 0; i < presenterList.size(); i++) {
            presenterList.get(i).release();
        }
        presenterList.clear();
    }

    public void addPresenterToList(BaseContract.BasePresenter presenter) {
        presenterList.add(presenter);
    }

    ArrayList<BaseContract.BasePresenter> presenterList = new ArrayList<BaseContract.BasePresenter>();

    protected void onResume() {
        super.onResume();
        IntentFilter netIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetworkStateForConfig, netIntentFilter);

        IUserInfoManagerService.getInstance().addGetUserInfoListener(mUserinfoListener);

        showDebugUI();

        //处理外部deepLink调起，MainAvtivity单独配置
        if (isUseActionBarDeepLink() && mIsFirstResume) {
            handleDeepLinkWake();
            mIsFirstResume = false;
        }
    }

    public void setIsShowNightMask(boolean isShow) {
        super.setIsShowNightMask(isShow);
    }

    public void showNightMode(boolean isShowMask) {
        super.showNightMode(isShowMask);
    }

    public void nightmodeRel() {
        super.nightmodeRel();
    }

    protected void onPause() {
        super.onPause();
        //必须在onPause销毁，否则可能收多次
        unregisterReceiver(mNetworkStateForConfig);

        ARouterUtils.getUserInfoManagerService().removeGetUserInfoListener(mUserinfoListener);


        if (!isNeedToOverrideDeepLinkBackView() && !TextUtils.isEmpty(mBackUrl)) {
            if (getReaderActionBar() != null && getReaderActionBar().getDeepLinkBackView() != null) {
                getReaderActionBar().getDeepLinkBackView().setVisibility(View.GONE);
                ScreenModeUtils.setFullScreenExtra(this, false);
                ((DeepLinkBackView) (getReaderActionBar().getDeepLinkBackView())).setBackUrl(null);
                mBackUrl = null;
            }
        }
    }

    //子类是否需要重写DeepLinkBackView
    protected boolean isNeedToOverrideDeepLinkBackView() {
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //qq和qzon分享回调
        ShareRouterService.onQQShareRespon(this, requestCode, resultCode, data);
    }

    BroadcastReceiver loginReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "loginOkReceiver：" + ReaderBaseActivity.this.getLocalClassName());
            boolean isLoginSuccess = intent.getBooleanExtra(LoginConstant.LOGIN_SUCCESS,
                    false);

            if (isLoginSuccess) {
                if (mLoginNextTask != null) {
                    mLoginNextTask.doTask(ILoginNextTask.TYPE_SUCCESS);
                }
                mLoginNextTask = null;

                Log.i("PopuDialogTAG", "--------------->isFastNetWork2");
                if (!isFastActivityNetWork()) {//内存中有多个activity，这里的广播会重复调用
                    Log.i(TAG, "loginOkReceiver refresh data：" + ReaderBaseActivity.this.getLocalClassName());
                    // 登录成功设置灯塔UIN
                    BaseApplication.Companion.getInstance().setUIN();
//                    boolean isNewUserLoginCash = intent.getBooleanExtra(Constant.IS_NEW_USER_LOGIN_CASH,false);
//                    if(isNewUserLoginCash){
//                        //如果是运营弹窗登录的新用户，需要通知我的页面
//                        MainActivityTabManager.isNewUserLoginCash = true;
//                    }else{

//                    }

                    IUserInfoManagerService.getInstance().getNetUserInfo(true);

                    Log.i("PopuDialogTAG", "--------------->getNetUserInfo2");

                    //登录成功后刷新道具列表
                    IPropsService propsService = IPropsService.getInstance();
                    if (propsService != null) {
                        propsService.obtainPropsList();
                    }
                }
            }
            loginFinish(isLoginSuccess);
        }
    };


    protected void loginFinish(boolean isLoginSuccess) {

    }

    protected void logOutFinish() {

    }

    protected boolean handleMessageImp(Message msg) {
        switch (msg.what) {
            case CommonMsgType.MESSAGE_LOGIN_RETRY:
                LoginRouterService.tryLogin(this, false);
                return true;

            case CommonMsgType.CHANNEL_ADV_JUMP_WITH_LOGIN:
                ILoginNextTask nextTask = (ILoginNextTask) msg.obj;
                setLoginNextTask(nextTask);
                startLogin();
                return true;
        }
        return false;

    }

    public void synCloudNoteDone(NoteInfo info) {

    }


    public Handler getHandler() {
        return mHandler;
    }

    public void loginWithTask(final int taskID) {
        ILoginNextTask nextTask = new ILoginNextTask() {
            @Override
            public void doTask(int type) {
                switch (type) {
                    case ILoginNextTask.TYPE_SUCCESS:
                        // 登录完了后再执行一次
                        mHandler.sendEmptyMessage(taskID);
                        break;
                }
            }
        };
        mLoginNextTask = nextTask;
        startLogin();
    }

    public void loginWithTask(final int taskID, Bundle bundle) {
        ILoginNextTask nextTask = new ILoginNextTask() {
            @Override
            public void doTask(int type) {
                switch (type) {
                    case ILoginNextTask.TYPE_SUCCESS:
                        // 登录完了后再执行一次
                        mHandler.sendEmptyMessage(taskID);
                        break;
                }
            }
        };
        mLoginNextTask = nextTask;
        startLogin(bundle);
    }

    public void startLogin() {
        LoginRouterService.startLogin(this);
    }

    public void startLogin(Bundle bundle) {
        LoginRouterService.startLogin(this, bundle);
    }


    public void setLoginNextTask(ILoginNextTask nextTask) {
        mLoginNextTask = nextTask;
    }

    @Override
    public void finish() {
        super.finish();
        if (mUseAnimation) {
            try {
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) >= 5) {
                    overridePendingTransition(R.anim.slide_in_left,
                            R.anim.slide_out_right);
                }
            } catch (Exception e) {
                Log.printErrStackTrace("ReaderBaseActivity", e, null, null);
            }

        }
    }

    public void disableUseAnimation() {
        this.mUseAnimation = false;
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (mUseAnimation) {
            try {
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) >= 5) {
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                }
            } catch (Exception e) {
                Log.printErrStackTrace("ReaderBaseActivity", e, null, null);
            }

        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (mUseAnimation) {
            try {
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) >= 5) {
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                }
            } catch (Exception e) {
                Log.printErrStackTrace("ReaderBaseActivity", e, null, null);
            }

        }
    }


    @Override
    public boolean handleMessage(Message msg) {
        //TODO 这个方法应该防止重载
        if (isFinishing())
            return true;
        return handleMessageImp(msg);
    }


    private void showDebugUI() {
//        if (Debug.isDebug) {
//            if (Debug.isShowDebugView) {
//                if (debugTextView != null) {
//                    ViewGroup vg = (ViewGroup) debugTextView.getParent();
//                    vg.removeView(debugTextView);
//                }
//                debugTextView = new TextView(this);
//                debugTextView.setText(this.getClass().getCanonicalName() + "\r\n" + "程序占用内存：" + Utily.getTotalMemory() + "\r\n" + "cpu使用率：" + String.format("%.2f", Utily.getProcessCpuRate()) + "%");
//                debugTextView.setTextSize(15);
//                debugTextView.setTextColor(Color.parseColor("#ff0000"));
//                addContentView(debugTextView,
//                        new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
//                                FrameLayout.LayoutParams.WRAP_CONTENT));
//            } else {
//                if (debugTextView != null) {
//                    debugTextView.setVisibility(View.GONE);
//                }
//            }
//        }
    }


    public boolean isInMulti() {
        if (Build.VERSION.SDK_INT >= 24) {
            return isInMultiWindowMode();
        } else {
            return false;
        }
    }

    public boolean isActive() {
        return !isFinishing();
    }

    //Espresso异步测试调用
    @VisibleForTesting
    public IdlingResource getIdlingResource() {
        return EspressoIdlingResourceNetManager.getIdlingResource();
    }

    public void showProgress(String message, boolean isCancelable, boolean isBlockKey) {
        if (!isFinishing()) {
            if (mProgressDialog == null) {
                if (message == null) {
                    message = "";
                }
                mProgressDialog = new ReaderProgressDialog(this);
                mProgressDialog.setMSG(message);
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.setCancelable(isCancelable);
                mProgressDialog.show();
                if (isBlockKey) {
                    mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                return true;
                            }
                            return false;
                        }
                    });
                }
            } else {
                mProgressDialog.show();
            }
        }
    }

    /**
     * Activity没有进入关闭流程，且Dialog存在
     */
    public void progressCancel() {
        if (!isFinishing() && mProgressDialog != null
                && mProgressDialog.isShowing()) {
            try {
                mProgressDialog.cancel();
                mProgressDialog = null;
            } catch (Exception e) {
                Log.printErrStackTrace("MainActivity", e, null, null);
                e.printStackTrace();
            }
        }
    }

    public void showToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
    }

    public void showToast(String message, int type) {
        ReaderToast.makeText(this, message, type).show();
    }

    public boolean isUseActionBarDeepLink() {
        return true;
    }


    private static long lastBaseActivityTime = 0;

    private synchronized static boolean isFastActivityNetWork() {
        long time = System.currentTimeMillis();
        long delta = time - lastBaseActivityTime;
        if (delta > 0 && delta < 2000) {
            return true;
        }
        lastBaseActivityTime = time;
        return false;
    }
}
