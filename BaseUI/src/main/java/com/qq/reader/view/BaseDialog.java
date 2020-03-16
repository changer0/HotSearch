package com.qq.reader.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.qq.reader.baseui.R;
import com.qq.reader.common.memoryleak.ActivityLeakSolution;
import com.qq.reader.common.utils.CommonConfig;
import com.qq.reader.common.utils.CommonConstant;
import com.qq.reader.common.utils.FlavorUtils;
import com.qq.reader.common.utils.HardwareUtils;
import com.qq.reader.common.utils.NightModeUtil;
import com.qq.reader.common.utils.ScreenModeUtils;
import com.tencent.mars.xlog.Log;

import java.lang.reflect.Field;

/**
 * 封装dialog,实现按键，触摸内部封装，达到各种弹出菜单，窗口交互和UI要求。
 *
 * @author SawRen
 * @email: sawren@tencent.com
 * @date 2010-9-19
 */
public class BaseDialog {
    public static final int TYPE_CENTER = 0;
    public static final int TYPE_BOTTOM = 1;
    public static final int TYPE_TOP_RIGHT_NOT_FOCUSABLE = 2;
    public static final int TYPE_CENTER_NOT_FOCUSABLE = 3;
    public static final int TYPE_CENTER_NOMENUCANCEL = 4;
    public static final int TYPE_LEFT_TOP_NOT_FOCUSABLE = 5;
    public static final int TYPE_TOP_CENTER_NOT_FOCUSABLE = 6;
    public static final int TYPE_TOP_RIGHT_DOWN_NOT_FOCUSABLE = 7;
    public static final int TYPE_TOP_CENTER_NOT_FOCUSABLE_WRAPCONTENT_WINDOW = 8;
    public static final int TYPE_TOP_DOWN = 9;
    public static final int TYPE_TOP_CENTER_FILLHOR_PARENT = 10;
    public static final int TYPE_TOP_CENTER_FILLPARENT = 11;
    public static final int TYPE_TOP_CENTER_FOCUSABLE = 12;
    public static final int TYPE_TOP_RIGHT_DOWN = 13;
    public static final int TYPE_BOTTOM_WITHOUTMASK = 14;
    public static final int TYPE_BOTTOM_LEFT_NOT_FOCUSABLE = 16;
    public static final int TYPE_BOTTOM_LEFT = 17;
    public static final int TYPE_BOTTOM_NOT_FOCUSABLE = 18;
    public static final int TYPE_BOTTOM_FOCUSABLE = 19;
    public boolean isMenuCancel = true;
    protected ReaderDialog mDialog;
    protected Activity mActivity;
    Object object = null;
    private NightModeUtil mNMC = null;

    public void initDialog(Activity act, View inflateView, int layoutResId,
                           boolean isCanceledOnTouchOutside, boolean isCanTouchOutside,
                           boolean isFullScreen) {
        // initDialog(act, inflateView, layoutResId, TYPE_BOTTOM);
        mDialog = new ReaderDialog(act, R.style.popBottomDialog);
        mActivity = act;

        mNMC = new NightModeUtil(mDialog, false);
        if (inflateView == null) {
            mDialog.setContentView(layoutResId);
        } else {
            mDialog.setContentView(inflateView);
        }
        mDialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        mDialog.setOnDismissListener(new OnNightModeDialogDismissListener() {
            @Override
            public NightModeUtil getNightMode_Util() {
                return getNightModeUtil();
            }
        });
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        if (isFullScreen) {
            WindowManager.LayoutParams actlp = act.getWindow().getAttributes();
            lp.width = actlp.width;
        }
        lp.flags = lp.flags & (~WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if (isCanTouchOutside) {
            lp.flags = lp.flags
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        }
        lp.gravity = android.view.Gravity.BOTTOM;
        if (Build.VERSION.SDK != null && Integer.valueOf(Build.VERSION.SDK) > 3) {
            // 1.5以上可以自定义windows动画, 或者只有g3或是sense_ui有bug, 需要更多机型测试
            mDialog.getWindow().setWindowAnimations(R.style.Animation_menuAnim);
        }
        mDialog.getWindow().setAttributes(lp);
    }

    public Activity getActivity() {
        return mActivity;
    }

    public void initDialog(Activity act, View inflateView, int layoutResId,
                           int type, boolean isFullScreen) {
        initDialog(act, inflateView, layoutResId, type, isFullScreen, false);
    }

    public void initDialog(Activity act, View inflateView, int layoutResId,
                           int type, boolean isFullScreen, boolean isShowNM) {
        mDialog = new ReaderDialog(act, R.style.popBottomDialog);
        if (inflateView == null) {
            mDialog.setContentView(layoutResId);
        } else {
            mDialog.setContentView(inflateView);
        }
        mDialog.setCanceledOnTouchOutside(true);
        mActivity = act;
        mNMC = new NightModeUtil(mDialog, isShowNM);
        mDialog.setOnDismissListener(new OnNightModeDialogDismissListener() {
            @Override
            public NightModeUtil getNightMode_Util() {
                return getNightModeUtil();
            }
        });
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        if (isFullScreen) {
            WindowManager.LayoutParams actlp = act.getWindow().getAttributes();
            lp.width = actlp.width;
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        }
        switch (type) {
            case TYPE_CENTER:
                typeCenterAction(lp);
                break;
            case TYPE_CENTER_NOMENUCANCEL:
                isMenuCancel = false;
                break;
            case TYPE_BOTTOM:
                lp.flags = lp.flags & WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                lp.gravity = android.view.Gravity.BOTTOM;
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) > 3) {
                    // 1.5以上可以自定义windows动画, 或者只有g3或是sense_ui有bug, 需要更多机型测试
                    mDialog.getWindow().setWindowAnimations(
                            R.style.Animation_menuAnim);
                }
                break;
            case TYPE_BOTTOM_FOCUSABLE:
                lp.flags = lp.flags & (~WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                lp.gravity = android.view.Gravity.BOTTOM;
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) > 3) {
                    // 1.5以上可以自定义windows动画, 或者只有g3或是sense_ui有bug, 需要更多机型测试
                    mDialog.getWindow().setWindowAnimations(
                            R.style.Animation_menuAnim);
                }
                break;
            case TYPE_BOTTOM_NOT_FOCUSABLE:
                lp.flags = lp.flags & (~WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                lp.gravity = android.view.Gravity.BOTTOM;
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) > 3) {
                    // 1.5以上可以自定义windows动画, 或者只有g3或是sense_ui有bug, 需要更多机型测试
                    mDialog.getWindow().setWindowAnimations(
                            R.style.Animation_menuAnim);
                }
                break;
            case TYPE_BOTTOM_WITHOUTMASK:
                mNMC = new NightModeUtil(mDialog, false);

                lp.flags = lp.flags & WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                lp.gravity = android.view.Gravity.BOTTOM;
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) > 3) {
                    // 1.5以上可以自定义windows动画, 或者只有g3或是sense_ui有bug, 需要更多机型测试
                    mDialog.getWindow().setWindowAnimations(
                            R.style.Animation_menuAnim);
                }
                break;
            case TYPE_TOP_RIGHT_NOT_FOCUSABLE:
                lp.flags = lp.flags & (~WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                // lp.dimAmount = 0;
                lp.gravity = android.view.Gravity.TOP | android.view.Gravity.RIGHT;
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) > 3) {
                    mDialog.getWindow().setWindowAnimations(
                            R.style.Animation_lampcordAnim);
                }
                break;
            case TYPE_TOP_DOWN:
                lp.flags = lp.flags & (~WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                lp.gravity = android.view.Gravity.TOP | android.view.Gravity.RIGHT;
                lp.y = getContext().getResources().getDimensionPixelOffset(R.dimen.common_dp_48);
                lp.height = LayoutParams.WRAP_CONTENT;
                lp.width = LayoutParams.WRAP_CONTENT;
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) > 3) {
                    mDialog.getWindow().setWindowAnimations(
                            R.style.Animation_scalepointAnim);
                }
                break;
            case TYPE_TOP_RIGHT_DOWN_NOT_FOCUSABLE:
                lp.flags = lp.flags & (~WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                lp.gravity = android.view.Gravity.TOP | android.view.Gravity.RIGHT;
                lp.y = getContext().getResources().getDimensionPixelOffset(R.dimen.common_dp_48);
                lp.x = getContext().getResources().getDimensionPixelOffset(R.dimen.common_dp_8);
                lp.height = LayoutParams.WRAP_CONTENT;
                lp.width = LayoutParams.WRAP_CONTENT;
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) > 3) {
                    mDialog.getWindow().setWindowAnimations(
                            R.style.Animation_scalepointAnim);
                }
                break;
            case TYPE_TOP_RIGHT_DOWN:
			    lp.flags = lp.flags & (~WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                lp.gravity = android.view.Gravity.TOP | android.view.Gravity.RIGHT;
                lp.y = getContext().getResources().getDimensionPixelOffset(R.dimen.common_dp_48);
                lp.x = getContext().getResources().getDimensionPixelOffset(R.dimen.common_dp_8);
                lp.height = LayoutParams.WRAP_CONTENT;
                lp.width = LayoutParams.WRAP_CONTENT;
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) > 3) {
                    mDialog.getWindow().setWindowAnimations(
                            R.style.Animation_scalepointAnim);
                }
                break;
            case TYPE_TOP_CENTER_NOT_FOCUSABLE:
                lp.flags = lp.flags & (~WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                // lp.dimAmount = 0;
                lp.gravity = android.view.Gravity.TOP
                        | android.view.Gravity.CENTER_HORIZONTAL;
                if(FlavorUtils.isHuaWei()
                        &&!(HardwareUtils.isBezelLessDevice(mActivity) && Build.VERSION.SDK_INT >= 28)) {//华为系统居然自带margin。。。
                    lp.x = 0;
                    lp.y = 0;
                    try {
                        //华为部分系统增加了一个属性，我们自定义的dialog要去掉- -
                        Field isEmuiStyle = lp.getClass().getDeclaredField("isEmuiStyle");
                        isEmuiStyle.setAccessible(true);
                        isEmuiStyle.setInt(lp, Integer.MIN_VALUE);
                        isEmuiStyle.setAccessible(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) > 3) {
                    mDialog.getWindow().setWindowAnimations(
                            R.style.Animation_topbarAnim);
                }
                break;
            case TYPE_CENTER_NOT_FOCUSABLE:
                lp.flags = lp.flags & (~WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                lp.gravity = android.view.Gravity.CENTER;
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) > 3) {
                    mDialog.getWindow().setWindowAnimations(
                            R.style.Animation_orientationLockAnim);
                }
                break;
            case TYPE_LEFT_TOP_NOT_FOCUSABLE:
                lp.flags = lp.flags & (~WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                lp.gravity = android.view.Gravity.TOP | android.view.Gravity.LEFT;
                break;
            case TYPE_TOP_CENTER_NOT_FOCUSABLE_WRAPCONTENT_WINDOW:
                lp.flags = lp.flags & (~WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                lp.gravity = android.view.Gravity.TOP
                        | android.view.Gravity.CENTER_HORIZONTAL;
                lp.y = getContext().getResources().getDimensionPixelOffset(R.dimen.common_dp_48);
                lp.height = LayoutParams.WRAP_CONTENT;
                lp.width = getContext().getResources().getDimensionPixelOffset(R.dimen.common_dp_196);
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) > 3) {
                    mDialog.getWindow().setWindowAnimations(
                            R.style.Animation_dropdownAnim);
                }
                break;
            case TYPE_TOP_CENTER_FILLHOR_PARENT:
                lp.flags = lp.flags & (~WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                lp.gravity = android.view.Gravity.TOP
                        | android.view.Gravity.CENTER_HORIZONTAL;
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) <= 10) {
                    lp.y = getContext().getResources().getDimensionPixelOffset(R.dimen.common_dp_48);
                }
//			lp.y = Utility.dip2px(getContext(), 48);
                lp.height = LayoutParams.WRAP_CONTENT;
                WindowManager.LayoutParams actlp = act.getWindow().getAttributes();
                lp.width = actlp.width;
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) > 3) {
                    mDialog.getWindow().setWindowAnimations(
                            R.style.Animation_dropdownAnim);
                }
                break;
            case TYPE_TOP_CENTER_FILLPARENT:
                lp.flags = lp.flags & (~WindowManager.LayoutParams.FLAG_DIM_BEHIND)
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                lp.gravity = android.view.Gravity.TOP
                        | android.view.Gravity.CENTER_HORIZONTAL;
                actlp = act.getWindow().getAttributes();
//			lp.y = Utility.dip2px(getContext(), 48);
//			lp.height = actlp.height-Utility.dip2px(getContext(), 48);
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.width = actlp.width;
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) > 3) {
                    mDialog.getWindow().setWindowAnimations(
                            R.style.Animation_dropdownAnim);
                }
                break;
            case TYPE_TOP_CENTER_FOCUSABLE:
                lp.flags = lp.flags & (~WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                lp.gravity = android.view.Gravity.TOP
                        | android.view.Gravity.CENTER_HORIZONTAL;
                lp.height = LayoutParams.WRAP_CONTENT;
                actlp = act.getWindow().getAttributes();
                lp.width = actlp.width;
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) > 3) {
                    mDialog.getWindow().setWindowAnimations(
                            R.style.Animation_dropdownAnim);
                }
                break;
            case TYPE_BOTTOM_LEFT_NOT_FOCUSABLE:
                lp.flags = lp.flags & (~WindowManager.LayoutParams.FLAG_DIM_BEHIND)| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                lp.gravity = Gravity.BOTTOM | Gravity.LEFT;
                lp.y = getContext().getResources().getDimensionPixelOffset(R.dimen.common_dp_58)+getContext().getResources().getDimensionPixelOffset(R.dimen.common_dp_10);
                lp.x = getContext().getResources().getDimensionPixelOffset(R.dimen.common_dp_16);
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) > 3) {
                    mDialog.getWindow().setWindowAnimations(
                            R.style.Animation_menuAnim);
                }
                break;
            case TYPE_BOTTOM_LEFT:
                lp.flags = lp.flags & (~WindowManager.LayoutParams.FLAG_DIM_BEHIND)| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                lp.gravity = Gravity.BOTTOM | Gravity.LEFT;
                lp.y = getContext().getResources().getDimensionPixelOffset(R.dimen.common_dp_58)+getContext().getResources().getDimensionPixelOffset(R.dimen.common_dp_10) + getContext().getResources().getDimensionPixelOffset(R.dimen.common_dp_56);
                lp.x = getContext().getResources().getDimensionPixelOffset(R.dimen.common_dp_16);
                if (Build.VERSION.SDK != null
                        && Integer.valueOf(Build.VERSION.SDK) > 3) {
                    mDialog.getWindow().setWindowAnimations(
                            R.style.Animation_menuAnim);
                }
                break;
        }
        mDialog.getWindow().setAttributes(lp);
    }

    public void typeCenterAction(WindowManager.LayoutParams lp){

    }

    public void initDialog(Activity act, View inflateView, int layoutResId,
                           int type, boolean isCanceledOnTouchOutside,
                           boolean isCanTouchOutside, boolean isFullScreen) {
        initDialog(act, inflateView, layoutResId, type, isFullScreen);
        mDialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.flags = lp.flags & WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        if (isCanTouchOutside) {
            lp.flags = lp.flags
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
        }
        mDialog.getWindow().setAttributes(lp);
        mNMC = new NightModeUtil(mDialog, false);
        mDialog.setOnDismissListener(new OnNightModeDialogDismissListener() {
            @Override
            public NightModeUtil getNightMode_Util() {
                return getNightModeUtil();
            }
        });
    }

    public View findViewById(int id) {
        return mDialog.findViewById(id);
    }

    public void setGravity(int gravity) {
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.gravity = gravity;
    }

    public void setOnKeyListener(Dialog.OnKeyListener listener){
        mDialog.setOnKeyListener(listener);
    }

    /**
     * 添加dialog ——onKeyDown的对外封装
     * @param keyCode
     * @param event
     */
    public void onKeyDown(int keyCode, KeyEvent event){
        mDialog.onKeyDown(keyCode, event);
    }

    public void setOnShowListener(DialogInterface.OnShowListener listener) {
        mDialog.setOnShowListener(listener);
    }

    public void setOnCancelListener(OnCancelListener clistener) {
        mDialog.setOnCancelListener(clistener);
    }

    public void setOnDismissListener(OnNightModeDialogDismissListener clistener) {
        mDialog.setOnDismissListener(clistener);
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        mDialog.setOnDismissListener(listener);
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
    }

    public void setCancelable(boolean cancel) {
        mDialog.setCancelable(cancel);
    }

    public NightModeUtil getNightModeUtil() {
        return mNMC;
    }

    public void show() {
        mDialog.show();
        if (!FlavorUtils.isHuaWei() && mNMC!= null) {
            mNMC.showMask();
        }
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public void cancel() {
        // rdm发现很多handle 回调时候activity已经结束了，activity结束的时候应该去掉handle的回调
        if (mDialog != null && mDialog.isShowing()) {
            try {
                mDialog.cancel();
            } catch (Exception e) {
                Log.printErrStackTrace("BaseDialog", e, null, null);
                e.printStackTrace();
            }
        }
    }

    /**
     * dialog dismiss取消之后的回调,如果需要释放资源等复写这个方法
     */
    public void onDismiss() {

    }

    public Context getContext() {
        return mDialog.getContext();
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }

    public Object getTouchListener() {
        return mDialog.getTouchListener();
    }

    public void setTouchListener(DialogTouchListener touchListener) {
        mDialog.setTouchListener(touchListener);
    }

    public Object getGuideTouchListener() {
        return mDialog.getGuideTouchListener();
    }

    public void setGuideTouchListener(GuideDialogTouchListener touchListener) {
        mDialog.setGuideTouchListener(touchListener);
    }

    public Object getTag() {
        return object;
    }

    public void setTag(Object obj) {
        object = obj;
    }

    /**
     * 亮度过低导致黑屏后，可以唤起菜单
     * 暂时弃用，因为会导致跟随系统亮度，通知栏调节亮度无法立即生效
     *
     * @param c
     * @param isLimit
     */
    @Deprecated
    public void setTheMinBrightness(Context c, boolean isLimit) {

        if (!CommonConfig.iSFollowSysBrightness()) {

            // 亮度小于最低亮度的时候，菜单不会黑，还可以调起
            WindowManager.LayoutParams layoutParams = mDialog.getWindow()
                    .getAttributes();

            int nowBright = CommonConfig.getBrightness();

            if ((nowBright < CommonConstant.LIGHT_MIN) && isLimit) {

                // Log.e("layoutParams.screenBrightness:", "" + nowBright);
                layoutParams.screenBrightness = CommonConstant.LIGHT_MIN / 255.0f;
            } else {
                layoutParams.screenBrightness = nowBright / 255.0f;
            }

            // Log.e("test screenBrightness:", ""+layoutParams.screenBrightness
            // *
            // 255.0f);

            mDialog.getWindow().setAttributes(layoutParams);
        } else {
            WindowManager.LayoutParams layoutParams = mDialog.getWindow()
                    .getAttributes();
            if (!isAutoBrightness(mActivity)) {
                int system = 0;
                try {
                    system = Settings.System.getInt(c.getContentResolver(),
                            Settings.System.SCREEN_BRIGHTNESS);
                } catch (SettingNotFoundException e) {
                    Log.printErrStackTrace("BaseDialog", e, null, null);
                    e.printStackTrace();
                    system = CommonConstant.LIGHT_MIN;
                }
                if (system <= 0) {
                    system = CommonConstant.LIGHT_MIN;
                }
                layoutParams.screenBrightness = system / 255.0f;
                mDialog.getWindow().setAttributes(layoutParams);
            } else {
                layoutParams.screenBrightness = -1.0f;
                mDialog.getWindow().setAttributes(layoutParams);
            }
        }
    }

    public void followSysBrightness(Activity actvity) {

    }

    public Dialog getDialog() {
        return mDialog;
    }

    public boolean isAutoBrightness(Activity act) {
        boolean automicBrightness = false;
        ContentResolver aContentResolver = act.getContentResolver();
        try {
            automicBrightness = Settings.System.getInt(aContentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Exception e) {
            Log.printErrStackTrace("Utility", e, null, null);
            Toast.makeText(act, "无法获取亮度", Toast.LENGTH_SHORT).show();
        }
        return automicBrightness;
    }

    public void setNavigationBarIconLight() {
        ScreenModeUtils.setNavigationStatusColor(mDialog , Color.TRANSPARENT);
    }

    public class ReaderDialog extends Dialog {

        private DialogTouchListener touchListener = null;
        private GuideDialogTouchListener guideTouchListener = null;

        private DialogInterface.OnShowListener mOnShowListener;
        private DialogInterface.OnCancelListener mCancelListener;
        private DialogInterface.OnDismissListener mDialogDismissListener;


        public ReaderDialog(Context context) {
            super(context);
        }

        public ReaderDialog(Context context, int theme) {
            super(context, theme);
        }

        public void setOnShowListener(DialogInterface.OnShowListener listener) {
            mOnShowListener = listener;
//			super.setOnShowListener(listener);
        }

        public void setOnCancelListener(OnCancelListener clistener) {
            mCancelListener = clistener;
//			super.setOnCancelListener(clistener);
        }

        public void setOnDismissListener(OnDismissListener clistener) {
            mDialogDismissListener = clistener;
//			super.setOnDismissListener(clistener);

        }

        public void show() {
            if (mOnShowListener != null) {
                super.setOnShowListener(mOnShowListener);
            }
            if (mCancelListener != null) {
                super.setOnCancelListener(mCancelListener);
            }
            if (mDialogDismissListener != null) {
                super.setOnDismissListener(mDialogDismissListener);
            }
            if (mActivity == null || mActivity.isFinishing()) {
                //fix android.os.DeadObjectException
                return;
            }
            super.show();
        }

        @Override
        public void dismiss() {
            try {
                super.dismiss();
                onDismiss();
            } catch (Exception e) {
                Log.printErrStackTrace("ReaderDialog", e, null, null);
                e.printStackTrace();
            } finally {
                ActivityLeakSolution.fixMesssageLeak(this);
            }

        }

        public Object getTouchListener() {
            return touchListener;
        }

        public void setTouchListener(DialogTouchListener touchListener) {

            this.touchListener = touchListener;
        }

        public Object getGuideTouchListener() {
            return guideTouchListener;
        }

        public void setGuideTouchListener(GuideDialogTouchListener guideTouchListener) {

            this.guideTouchListener = guideTouchListener;
        }

        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (null != touchListener
                    && touchListener.keyHandle(keyCode, event)) {
                return true;
            }
            switch (keyCode) {
                case KeyEvent.KEYCODE_MENU:
                    // if(BaseDialog.this instanceof ManageDialog){
                    // ((ManageDialog)BaseDialog.this).doMenuOnClick();
                    // return false;
                    // }
                    if (isMenuCancel && isShowing()) {
                        cancel();
                        return true;
                    }
                    break;
            }
            return super.onKeyDown(keyCode, event);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
            try {
                if (touchListener != null) {
                    touchListener.touchHandle(ev);
                }
                return super.dispatchTouchEvent(ev);
            } catch (Exception e) {
                Log.printErrStackTrace("ReaderDialog", e, null, null);
                e.printStackTrace();
            }

            return false;
        }


        @Override
        public boolean onTouchEvent(@NonNull MotionEvent event) {
            try {
                if (guideTouchListener != null) {
                    guideTouchListener.touchListener(event);
                    return false;
                }

            } catch (Exception e) {
                Log.printErrStackTrace("ReaderDialog", e, null, null);
                e.printStackTrace();
            }

            return super.onTouchEvent(event);
        }
    }

}
