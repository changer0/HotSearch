package com.qq.reader.activity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.qq.reader.baseui.R;
import com.qq.reader.common.define.CommonMsgType;
import com.qq.reader.common.utils.CommonConfig;
import com.qq.reader.common.utils.CommonUtility;
import com.qq.reader.common.utils.NightModeUtil;
import com.qq.reader.common.utils.ScreenModeUtils;
import com.qq.reader.core.BaseApplication;
import com.qq.reader.core.config.AppConstant;
import com.qq.reader.core.utils.ReflectionUtils;
import com.qq.reader.core.utils.SysDeviceUtils;
import com.qq.reader.core.utils.WeakReferenceHandler;
import com.qq.reader.core.utils.compat.UIStyleCompat;
import com.qq.reader.delegate.ActivityConfig;
import com.qq.reader.delegate.ActivityDelegate;
import com.qq.reader.view.ReaderBottomNavigationView;
import com.qq.reader.widget.BranchActionBar;
import com.qq.reader.widget.IActionBar;
import com.qq.reader.widget.IBottomMenu;
import com.qq.reader.widget.IBottomNavigationView;
import com.qq.reader.widget.ReaderActionBar;
import com.qq.reader.widget.ReaderBottomMenu;
import com.tencent.mars.xlog.Log;

import java.lang.reflect.Field;

/**
 * Created by zhanglulu on 2017/5/2.
 * <p>
 */

public class BranchBaseActivity extends FragmentActivity implements ActivityConfig {

    protected WeakReferenceHandler mHandler;
    protected IActionBar mActionBar;
    protected IBottomMenu mBottomMenu;
    protected ActivityDelegate mActivityDelegate = null;

    private View mCustTitleView;
    private TextView titleSelectNum;
//    private TextView titleStatus;
    protected int mMode;
    public static final int MODE_STANDER = 0;
    public static final int MODE_EIDT = 1;

    private ReflectionUtils.MethodSign methodSign = null;
    private ReflectionUtils.ObjectValue target = null;

    protected ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = null;
    protected FrameLayout content;
    protected NightModeUtil mNMC = null;
    protected boolean setMaskHide = false;
    protected boolean hasNavigationBarShow = false;

    private boolean isNeedChangeNavigationBarColor = true;

    @Override
    public void onContentChanged() {
//        if (Build.VERSION.SDK_INT >= 22 && Utility.isOppoRomImmerse()) {
        if (Build.VERSION.SDK_INT >= 22) {
            super.onContentChanged();
//            mActivityDelegate.onContentChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mActivityDelegate.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mActivityDelegate = new ActivityDelegate(this);
        super.onCreate(savedInstanceState);
        mActivityDelegate.onCreate(this);

        initNightMask();
    }

    public IBottomNavigationView getBottomView(ViewGroup rootview){
        IBottomNavigationView iBottomNavigationView = new ReaderBottomNavigationView();
        iBottomNavigationView.inflate(this, rootview);
        return iBottomNavigationView;
    }

    // TODO: p_zlulzhang 2020-02-10 没有找到有使用的位置
//    /**
//     * 静默登录，职位刷新用户信息，不必关注登录回调
//     */
//    public void startLoginQuickly() {
//        LoginManager.Companion.login(this,0);
////        OtherLoginHelper.getInstance(this).login(this, null);
////        OtherLoginHelper.getInstance(this).setLoginListener(
////                new ReaderLoginListener() {
////                    @Override
////                    public void onLoginSuccess(int type) {
////                    }
////
////                    @Override
////                    public void onLoginError(String message, int loginType, int errCode) {
////
////                    }
////
////                    @Override
////                    public void onNeedVerifyImage(String uin, byte[] imageBuffer) {
////
////                    }
////
////                });
//    }

    @Override
    protected void onResume() {
        super.onResume();
        handleDarkMode();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseNightMode();
    }

    public IActionBar getReaderActionBar() {
        if (mActionBar == null || mActionBar.isNull()) {
            if(getImmerseMode() == ScreenModeUtils.IMMERSE_MODE_IMITATE || getImmerseMode() == ScreenModeUtils.IMMERSE_MODE_IMMERSE){
                if (getActionBar() != null) {
                    getActionBar().hide();
                }
                mActionBar = new ReaderActionBar(this);
            } else {
                if (getActionBar() != null) {
                    getActionBar().hide();
                }
                //MainActivity 做了特殊处理，这里不能设置颜色
                ScreenModeUtils.setStatusBarColor(this);
                mActionBar = new BranchActionBar(this);
            }
        }
        return mActionBar;
    }
    
    public IBottomMenu getReaderBottomMenu() {
        if (mBottomMenu == null || mBottomMenu.isNull()) {
            mBottomMenu = new ReaderBottomMenu(this);
        }
        return mBottomMenu;
    }

//    @Override
//    public android.app.ActionBar getActionBar() {
//        throw new UnsupportedOperationException();
//    }

//    @Override
//    public color.support.v7.app.ActionBar getSupportActionBar(){
//        throw new UnsupportedOperationException();
//    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        switchImmerseMode();
    }

    public void switchImmerseMode() {
        switch (getImmerseMode()) {
            //华为只有沉浸式和非沉浸式
            //沉浸式
            case ScreenModeUtils.IMMERSE_MODE_IMMERSE:
            case ScreenModeUtils.IMMERSE_MODE_IMITATE:
                ScreenModeUtils.addImmersiveStatusBarFlag(this);
                ScreenModeUtils.changeTitleBarHeight(this);
                break;
            //非沉浸式
            case ScreenModeUtils.IMMERSE_MODE_NONE:
            default:

                break;

        }
    }

    /**
     * IMMERSE_MODE_IMMERSE：自己实现沉浸式
     * IMMERSE_MODE_NONE:系统实现沉浸式
     * IMMERSE_MODE_IMITATE：自己实现沉浸式，并模仿系统沉浸式样式
     */
    public int getImmerseMode() {
        return ScreenModeUtils.IMMERSE_MODE_IMMERSE;
    }


    //actionbar的返回箭头，可在子类里设为true
    @Override
    public boolean isHomeAsUpEnabled() {
        return false;
    }
//
//    @Override
//    public boolean isTitleNeedUpdate() {
//        return false;
//    }
//
//    @Override
//    public boolean isShowMenuDescription() {
//        return false;
//    }

    @Override
    public int getStatusType() {
        return ActivityConfig.DEFAULT_STATUS_TYPE;
    }


    // /////////////////////////////////////////////////////////////////////
    // //////////////////////////FragmentDialog/////////////////////////////
    // /////////////////////////////////////////////////////////////////////
    protected final static String BUNDLE_DIALOG_TYPE = "BUNDLE_DIALOG_TYPE";
    protected final static String BUNDLE_DIALOG_BUNDLE = "BUNDLE_DIALOG_BUNDLE";

    public static class MyAlertDialogFragment extends DialogFragment {

        public static MyAlertDialogFragment newInstance(int type, Bundle bundle) {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt(BUNDLE_DIALOG_TYPE, type);
            if (bundle != null) {
                args.putBundle(BUNDLE_DIALOG_BUNDLE, bundle);
            }
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int type = getArguments().getInt(BUNDLE_DIALOG_TYPE);
            Bundle b = getArguments().getBundle(BUNDLE_DIALOG_BUNDLE);
            Dialog d = ((BranchBaseActivity) getActivity()).createDialog(type, b);
            return d;
        }

        @Override
        public void onCancel(DialogInterface di) {
            ((ReaderBaseActivity) getActivity()).onFragmentDialgoCancel(di);
        }

        @Override
        public void show(FragmentManager manager, String tag) {
            setDialogFramentField();
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(this, tag);
            ft.commitAllowingStateLoss();
        }

        private void setDialogFramentField() {
            try {
                Class<DialogFragment> viewClass = DialogFragment.class;

                Field dismissFiled = viewClass.getDeclaredField("mDismissed");
                dismissFiled.setAccessible(true);
                dismissFiled.setBoolean(this, false);

                Field showBymeField = viewClass.getDeclaredField("mShownByMe");
                showBymeField.setAccessible(true);
                showBymeField.setBoolean(this, true);

            } catch (Exception e) {
                Log.printErrStackTrace("MyAlertDialogFragment", e, null, null);
                e.printStackTrace();
            }
        }
    }

    protected Dialog createDialog(int type, Bundle b) {
        return null;
    }

    protected void onFragmentDialgoCancel(DialogInterface di) {

    }

    public void showFragmentDialog(int type) {
        showFragmentDialog(type, null);
    }

    public void showFragmentDialog(int type, Bundle bundle) {
        try {
            DialogFragment df = MyAlertDialogFragment.newInstance(type, bundle);
            df.show(getSupportFragmentManager(), "dialog");
        } catch (Exception e) {
            Log.printErrStackTrace("BranchBaseActivity", e, null, null);
            Log.e("ReaderBaseActivity", e.getMessage());
        }
    }

    public boolean isFragmentDialogShowing() {
        try {
            DialogFragment df = (DialogFragment) getSupportFragmentManager().findFragmentByTag("dialog");
            if (df != null && df.getDialog() != null && df.getDialog().isShowing()) {
                return true;
            }
        } catch (Exception e) {
            Log.printErrStackTrace("BranchBaseActivity", e, null, null);
            e.printStackTrace();
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setNeedChangeNavigationBarColor(boolean needChangeNavigationBarColor) {
        isNeedChangeNavigationBarColor = needChangeNavigationBarColor;
    }

    protected void setUpActionBar() {
        if(mActionBar == null){
            return;
        }
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        mActionBar.setDisplayShowTitleEnabled(false);
//		mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
    }

    protected void exitEditMode() {
        if (mMode == MODE_EIDT) {
            mMode = MODE_STANDER;
//            mOptionsMenu.setGroupVisible(R.id.g1, false);
            toggleActionBar();
//            mViewPager.unLock();
        }
    }

    protected boolean showLeftIcon() {
        return true;
    }

    protected boolean showRightIcon() {
        return false;
    }

    protected Drawable getRightIcon() {
        return null;
    }

    protected void hiddenLeft() {
        methodSign = new ReflectionUtils.MethodSign(BranchActionBar.METHOD_SETSTARTICON,
                ActionBar.class, boolean.class, Drawable.class, View.OnClickListener.class);
        target = new ReflectionUtils.ObjectValue(null, getActionBar(), false, null, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                exitEditMode();
            }
        });
        ReflectionUtils.invokeMethod(methodSign, target);
    }

    public void updateSelectNum(int count) {
        if(count == 0){
            if (null != titleSelectNum) {
                titleSelectNum.setText(R.string.unselected);
            }
        }else {
            if (null != titleSelectNum) {
                titleSelectNum.setText(String.format(getString(R.string.selected_n), count));
            }
        }
    }

    public void setEidtTitle(String title) {
        if (null != titleSelectNum ) {
            titleSelectNum.setText(title);
        }
    }

    protected void enterEditMode() {
        if (mMode == MODE_STANDER) {
            mMode = MODE_EIDT;
//            mViewPager.lock();
//    		mOptionsMenu.setGroupVisible(R.id.g1, true);
            //invoking invalidateOptionsMenu instead of setGroupVisible to solve the issue of that the menu
            //can not be visiable again via invoking setGroupVisiable(id,true).
            invalidateOptionsMenu();
            toggleActionBar();
            updateSelectNum(0);
        }
    }

    protected void toggleActionBar() {

        if (mActionBar.getNavigationMode() == ActionBar.NAVIGATION_MODE_TABS) {
            mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE, ActionBar.DISPLAY_SHOW_TITLE);

            methodSign = new ReflectionUtils.MethodSign(BranchActionBar.METHOD_SETSTARTICON,
                    ActionBar.class, boolean.class, Drawable.class, View.OnClickListener.class);
            target = new ReflectionUtils.ObjectValue(null, getActionBar(), showLeftIcon(), null, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    exitEditMode();
                }
            });
            ReflectionUtils.invokeMethod(methodSign, target);

            methodSign = new ReflectionUtils.MethodSign(BranchActionBar.METHOD_SETENDICON,
                    ActionBar.class, boolean.class, Drawable.class, View.OnClickListener.class);
            target = new ReflectionUtils.ObjectValue(null, getActionBar(), showRightIcon(), getRightIcon(), new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mHandler.sendEmptyMessage(CommonMsgType.MESSAGE_ACTIONBAR_RIGHT_CLICK);
                }
            });
            ReflectionUtils.invokeMethod(methodSign, target);

            if (mCustTitleView == null) {
                mCustTitleView = UIStyleCompat.getUIStyleCompat(this).getCustTitleView();
                titleSelectNum = (TextView) mCustTitleView.findViewById(R.id.select_num);
//                titleStatus = (TextView) mCustTitleView.findViewById(R.id.txt_selected);
            }
            methodSign = new ReflectionUtils.MethodSign(BranchActionBar.METHOD_SETCUSTOMTITLE, ActionBar.class, View.class);
            target = new ReflectionUtils.ObjectValue(null, getActionBar(), mCustTitleView);
            ReflectionUtils.invokeMethod(methodSign, target);

        } else {
            mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            mActionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

        }
    }

    /************************************** 夜间模式相关 ********************************************/

    private void initNightMask(){
        mNMC = new NightModeUtil(this, false);
        mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d("ReaderPage", "onGlobalLayout  setMaskHide " + setMaskHide);
                boolean tempShow = SysDeviceUtils.isNavigationBarShow(BranchBaseActivity.this);
                hasNavigationBarShow = hasNavigationBarShow || tempShow;
                if (mNMC != null) {
                    if (mNMC.isNM() && CommonConfig.isNightMode) {
//                    if (tempShow) {
//                        setMaskHide = true;
//                    }
                        if (setMaskHide) {
                        } else {
                            if (!SysDeviceUtils.isMainProcess()) {
                                ScreenModeUtils.setStatusBarColor(BranchBaseActivity.this
                                        , BaseApplication.getInstance().getResources()
                                                .getColor(R.color.night_mode_navibar_color));
                            }
                            if(isNeedChangeNavigationBarColor){
                                ScreenModeUtils.setNavigationBarColor(BranchBaseActivity.this
                                        , BaseApplication.getInstance().getResources().getColor(
                                                R.color.night_mode_navibar_color));
                            }
                        }
                        if(isNeedChangeNavigationBarColor) {
                            ScreenModeUtils.setNavigationBarIconLight(BranchBaseActivity.this
                                    , false);
                        }
                    } else {
                        if (hasNavigationBarShow) {
                            setMaskHide = true;
                        }
                    }
                }
            }
        };

        content = (FrameLayout) findViewById(android.R.id.content);
        if (content != null) {
            content.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
        }
    }

    private void handleDarkMode() {

        if(!SysDeviceUtils.isMainProcess()) {
            CommonUtility.setTimeLog("getNightMode");
            CommonConfig.isNightMode = CommonConfig.getNightMode(this);
            CommonUtility.setTimeLog("getNightMode over");
        }

        if (mNMC != null) {
            boolean isNavigationBarShow = SysDeviceUtils.isNavigationBarShow(this);
            hasNavigationBarShow = hasNavigationBarShow || isNavigationBarShow;
            nightmodeRel();
            mNMC.showMask();
            Log.d("BaseActivity", "mNMC.isNM() " + mNMC.isNM()
                    + " config " + CommonConfig.isNightMode);
            if (mNMC.isNM() && CommonConfig.isNightMode) {
                Log.d("BaseActivity", " net mode setMaskHide " + setMaskHide);
                if (setMaskHide) {
                } else {
                    if(!SysDeviceUtils.isMainProcess()) {
                        ScreenModeUtils.setStatusBarColor(this, BaseApplication.getInstance().getResources().getColor(R.color.night_mode_navibar_color));
                    }
                    if(isNeedChangeNavigationBarColor) {
                        ScreenModeUtils.setNavigationBarColor(this, BaseApplication.getInstance().getResources().getColor(R.color.night_mode_navibar_color));
                    }
                }
                if(isNeedChangeNavigationBarColor) {
                    ScreenModeUtils.setNavigationBarIconLight(this, false);
                }
            } else {
                if (hasNavigationBarShow) {
                    setMaskHide = true;
                }
                Log.d("BaseActivity", "not net mode");
                if(isNeedChangeNavigationBarColor) {
                    ScreenModeUtils.setNavigationBarColor(this, BaseApplication.getInstance().getResources().getColor(R.color.day_mode_navibar_color));
                    ScreenModeUtils.setNavigationBarIconLight(this, false);
                }
            }
        }
    }

    /**
     * 页面是否支持夜间模式，默认支持
     * @param isShow
     */
    protected void setIsShowNightMask(boolean isShow) {
        if (mNMC != null) {
            mNMC.setIsNM(isShow);
        }
    }

    protected void showNightMode(boolean isShowMask) {
        if (mNMC != null) {
            if (isShowMask) {
                mNMC.showMask();
            } else {
                boolean isNavigationBarShow = SysDeviceUtils.isNavigationBarShow(this);
                hasNavigationBarShow = hasNavigationBarShow || isNavigationBarShow;
                if (hasNavigationBarShow) {
                    setMaskHide = true;
                }
                mNMC.removeMask();
                if(isNeedChangeNavigationBarColor) {
                    ScreenModeUtils.setNavigationBarColor(this, BaseApplication.getInstance().getResources().getColor(R.color.day_mode_navibar_color));
                    ScreenModeUtils.setNavigationBarIconLight(this, false);
                }
            }
        }
    }

    /**
     * 夜间模式顶部statusbar的容错处理
     */
    protected void nightmodeRel() {
        if (mNMC != null) {
            if (Build.VERSION.SDK_INT >= 22 && getWindow().getStatusBarColor() == 0xff888888) {//使用系统actionbar的夜间模式
                mNMC.setPaddingTop(AppConstant.statusBarHeight);
            } else {//自定义title的夜间模式
                mNMC.setPaddingTop(0);
            }
        }
    }


    private void releaseNightMode(){
        if (content != null && mOnGlobalLayoutListener != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                content.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
            }
        }
    }
}
