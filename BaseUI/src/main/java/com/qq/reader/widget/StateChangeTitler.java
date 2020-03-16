package com.qq.reader.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qq.reader.baseui.R;
import com.qq.reader.common.utils.FlavorUtils;
import com.qq.reader.common.utils.ScreenModeUtils;
import com.qq.reader.core.BaseApplication;
import com.qq.reader.core.config.AppConstant;
import com.qq.reader.module.bookstore.qnative.model.TitlerControlModel;

/**
/**
 * Created by weilai1 on 2016/6/16.change by qiaoshouqing
 *
 * 这是一个渐变标题栏的类。
 */
// TODO: 2018/2/28 这个类后续要和ReaderActionBar有效结合起来。

public class StateChangeTitler extends RelativeLayout {
    private final String TAG = "StateChangeTitler";
    public View common_titler;
    public View common_titler_container;
    public TitlerControlModel model;
    public Drawable bgDrawable;
    public Drawable translucentDrawable;
    public StateChangeListener stateChangeListener;
    public Activity mActivity;

    public int isShowing;
    public TextView title;
    public TextView backView;
    public boolean isSetBgNull = true;
    private float alpha;
    protected Drawable mBackgroundDrawable;
    protected int mLastAlpha;

    public void setConTrollerModel(TitlerControlModel m) {
        model = m;
    }
    public void setStateChangeListener(StateChangeListener l) {
        if(!isAvailable) {
            setVisibility(GONE);
            return;
        }
        stateChangeListener = l;
    }

    public StateChangeTitler(Context context) {
        super(context);
        mActivity = (Activity) context;
        initUI();
    }

    public StateChangeTitler(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = (Activity) context;
        initAvailable(attrs);
        initUI(context, attrs);
    }

    protected void initUI() {
        //set default background drawable
        if(!isAvailable()) {
            setVisibility(GONE);
            return;
        }
        bgDrawable = BaseApplication.Companion.getINSTANCE().getResources().getDrawable(R.drawable.titler_bg);
        translucentDrawable = BaseApplication.Companion.getINSTANCE().getResources().getDrawable(R.drawable.translucent);
        View rootView = View.inflate(getContext(), R.layout.common_titler, this);
        common_titler = rootView.findViewById(R.id.common_titler);
        if(common_titler != null) {
            common_titler.setVisibility(VISIBLE);
            mBackgroundDrawable = common_titler.getBackground();
            title = (TextView) common_titler.findViewById(R.id.profile_header_title);
            backView = (TextView) common_titler.findViewById(R.id.profile_header_left_button);
        }
        changeNormalTitleStatus(common_titler);
    }

    /**
     * 提供以下修改状态的方法供外部使用，因为在不用页面的要求可能有所不同。
     */
    public void setDefaultStyle() {
        //给返回键设置透明时候的颜色
        setBackIcon(R.drawable.statechangetitler_back_icon_hide);
        //给标题设置透明时候的颜色
        if (title != null) {
            title.setTextColor(BaseApplication.Companion.getINSTANCE().getResources().getColor(R.color.statechangetitler_titler_hide));
        }
        setDefaultStatusStyle();
    }

    public void setDefaultStyle(boolean isHideTitle) {
        if(title != null && isHideTitle) {
            title.setVisibility(GONE);
        }
        setDefaultStyle();
    }

    public void setScrolledStyle() {
        //给返回键设置原来的颜色
        setBackIcon(R.drawable.statechangetitler_back_icon_normal);
        //给标题栏设置原来的颜色
        if (title != null) {
            title.setTextColor(BaseApplication.Companion.getINSTANCE().getResources().getColor(R.color.statechangetitler_titler_normal));
        }
        setScrolledStatusStyle();
    }

    public void setWebPageDefaultStyle() {
        setWebPageDefaultStyle(true);
    }

    public void setWebPageDefaultStyle(boolean isShowBackIcon) {
        //给返回键设置透明时候的颜色
        if (isShowBackIcon) {
            setBackIcon(R.drawable.statechangetitler_back_icon_hide);
            setStatusStyle(false);
        } else {
            if (backView != null) {
                backView.setVisibility(GONE);
            }
            setStatusStyle(true);
        }
        //给标题设置透明时候的颜色
        if (title != null) {
            title.setTextColor(BaseApplication.Companion.getINSTANCE().getResources().getColor(R.color.statechangetitler_titler_hide));
        }
    }

    public void setWebPageScrollStyle() {
        setWebPageScrollStyle(true);
    }

    public void setWebPageScrollStyle(boolean isShowBackIcon) {
        //给返回键设置原来的颜色
        if (isShowBackIcon) {
            setBackIcon(R.drawable.statechangetitler_back_icon_normal);
        } else {
            if (backView != null) {
                backView.setVisibility(GONE);
            }
        }
        //给标题栏设置原来的颜色
        if (title != null) {
            title.setTextColor(BaseApplication.Companion.getINSTANCE().getResources().getColor(R.color.statechangetitler_titler_normal));
        }
        setStatusStyle(true);
    }

    //设置标题栏透明的颜色
    public void setDefaultTitleStyle() {
        //给返回键设置透明时候的颜色
        setBackIcon(R.drawable.statechangetitler_back_icon_hide);
        //给标题设置透明时候的颜色
        if (title != null) {
            title.setTextColor(BaseApplication.Companion.getINSTANCE().getResources().getColor(R.color.statechangetitler_titler_hide));
        }
    }

    //设置标题栏主题颜色
    public void setScrolledTitleStyle() {
        //给返回键设置原来的颜色
        setBackIcon(R.drawable.statechangetitler_back_icon_normal);
        //给标题栏设置原来的颜色
        if (title != null) {
            title.setTextColor(BaseApplication.Companion.getINSTANCE().getResources().getColor(R.color.statechangetitler_titler_normal));
        }
    }

    //给状态栏文字图标颜色设置成黑色
    public void setDefaultStatusStyle() {
        post(() -> {
            ScreenModeUtils.setStatusIconLight(mActivity, true);
        });
    }

    //给状态栏文字图标颜色设置成白色
    public void setScrolledStatusStyle() {
        post(() -> {
            ScreenModeUtils.setStatusIconLight(mActivity, false);
        });
    }

    /**
     * 设置沉浸式状态栏颜色
     * true:  黑色
     * false: 白色
     * @param b
     */
    public void setStatusStyle(boolean b) {
        post(() -> {
            ScreenModeUtils.setStatusIconLight(mActivity, b);
        });
    }

    /**
     * 三星项目里重设一下渐变状态栏的样式。
     */
    public static void changeNormalTitleStatus(View common_titler) {
        if (FlavorUtils.isSamsung()) {
            TextView backTextButton = (TextView) common_titler.findViewById(R.id.profile_header_left_button);
            TextView title = (TextView) common_titler.findViewById(R.id.profile_header_title);
            TextView rightButton = (TextView) common_titler.findViewById(R.id.profile_header_right_button);
            TextView rightButton2 = (TextView) common_titler.findViewById(R.id.profile_header_right_button2);
            View blackDivider = common_titler.findViewById(R.id.black_divider);
            //设置文字颜色为白色
            title.setTextColor(BaseApplication.Companion.getINSTANCE().getResources().getColor(R.color.textcolor_white));
            backTextButton.setTextColor(BaseApplication.Companion.getINSTANCE().getResources().getColorStateList(R.color.actionbar_text_color_c104_selector));
            rightButton.setTextColor(BaseApplication.Companion.getINSTANCE().getResources().getColorStateList(R.color.actionbar_text_color_c104_selector));
            rightButton2.setTextColor(BaseApplication.Companion.getINSTANCE().getResources().getColorStateList(R.color.actionbar_text_color_c104_selector));
            //设置文字大小
            backTextButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, BaseApplication.Companion.getINSTANCE().getResources().getDimension(R.dimen.oppo_text_size_class_1));
            title.setTextSize(TypedValue.COMPLEX_UNIT_PX, BaseApplication.Companion.getINSTANCE().getResources().getDimension(R.dimen.text_size_class_5));
            //去除标题加粗
            TextPaint tp = title.getPaint();
            tp.setFakeBoldText(false);
            //设置返回图标为白色的
            Drawable drawable = BaseApplication.Companion.getINSTANCE().getResources().getDrawable(R.drawable.titlebar_icon_back_selector);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            backTextButton.setCompoundDrawables(drawable, null, null, null);
            //把标题栏底部的黑色分割线隐藏掉
            blackDivider.setVisibility(GONE);
        }
    }

    public void initUI(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, new int[]{R.attr.layout, R.attr.setbackgroundnull});
        int layout = ta.getResourceId(0, R.layout.common_titler);
        isSetBgNull = ta.getBoolean(1, true);
        initUI();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (bgDrawable != null) {
            bgDrawable.setCallback(null);
            bgDrawable = null;
        }
        if (translucentDrawable != null) {
            translucentDrawable.setCallback(null);
            translucentDrawable = null;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        initUI();
    }

    public void onScroll(ViewGroup view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//        Log.d(TAG, "onScroll model != null" + (model != null) + " " + firstVisibleItem);
        if(!isAvailable) {
            setVisibility(GONE);
            return;
        }
        if (model != null) {
//            Log.d(TAG,"onScroll model " + model.mode + " startPosition " + model.startPosition);
            if (model.mode == TitlerControlModel.POSITION_MODE) {
                int startPos = model.startPosition;
                if (firstVisibleItem > startPos) {
                    changeTitleBackground();
                } else {
                    hideTitlerBg();
                }
            } else if (model.mode == TitlerControlModel.POSITION_Y_MODE) {
                int childCount = view.getChildCount();
//                Log.d(TAG,"onScroll childCount " + childCount + " " + model.startY);
                if (model.startPosition == firstVisibleItem && childCount > 0) {
                    View child = view.getChildAt(0);
                    if (Math.abs(child.getTop()) > model.startY) {
                        changeTitleBackground();
                    } else {
                        hideTitlerBg();
                    }
                } else if (firstVisibleItem > model.startPosition) {
                    changeTitleBackground();
                }

            }

        }
    }


    //背景跟随滑动距离变化
    public void onScroll(float y) {
        if(!isAvailable) {
            setVisibility(GONE);
            return;
        }
        if (model != null) {
            if (mBackgroundDrawable == null) {
                mBackgroundDrawable = new ColorDrawable(0xFFFFFFFF);
                common_titler.setBackground(mBackgroundDrawable);
            }

            alpha = y / model.startY;
            if (alpha > 1) {
                alpha = 1;
            } else if (alpha < 0) {
                alpha = 0;
            }

            //处理间监听回调
            if (stateChangeListener != null) {
                if (alpha > 0.5) {
                    stateChangeListener.onSetTitleBg();
                } else {
                    stateChangeListener.onHideTitleBg();
                }
            }
            Log.d(TAG, "changeTitleBackground y = " + y + " model.startY=" + model.startY + " alpha=" + alpha);

            mBackgroundDrawable.mutate().setAlpha((int) (255 * alpha));
        }
    }

    public void recoverBackground() {
        if (common_titler != null
                && common_titler.getBackground() != null) {
            common_titler.getBackground().setAlpha(255);
        }
    }


    public void changeTitleBackground() {
        Log.d(TAG,"onScroll changeTitleBackground = " );
        if (isShowing == 1) return;
        isShowing = 1;
        Log.d(TAG,"onScroll changeTitleBackground ======== " );
        TransitionDrawable transitionDrawable = null;
        Drawable bg = common_titler.getBackground();

        try {
            if (bg instanceof TransitionDrawable) {
                bg = ((TransitionDrawable)bg).getDrawable(1);
            }
            if (bg != bgDrawable) {
                transitionDrawable = new TransitionDrawable(new Drawable[] {
                        new BitmapDrawable(common_titler.getDrawingCache()), bgDrawable
                });
                common_titler.setBackground(transitionDrawable);

                //设置background后padding会失效，沉浸式的状态栏需要重新设置padding
//                recoverImmerseMode();
                transitionDrawable.startTransition(800);
            }
        } catch (Exception e) {
            Log.e(TAG, "transitionDrawable.setCallBack is null");
            e.printStackTrace();
        }

        //外部接口的操作
        if (stateChangeListener != null) {
            stateChangeListener.onSetTitleBg();
        }
    }

    public void hideTitlerBg() {
        Log.d(TAG,"onScroll hideTitlerBg" );
        if (isShowing == -1) return;
        isShowing = -1;
        Log.d(TAG,"onScroll hideTitlerBg !!!!!!!" );
        TransitionDrawable transitionDrawable = null;
        Drawable bg = common_titler.getBackground();
        if (bg instanceof TransitionDrawable) {
            bg = ((TransitionDrawable)bg).getDrawable(1);
        }

        if (bg != translucentDrawable) {
            transitionDrawable = new TransitionDrawable(new Drawable[] {
                    new BitmapDrawable(common_titler.getDrawingCache()), translucentDrawable
            });

            common_titler.setBackground(transitionDrawable);

            //设置background后padding会失效，沉浸式的状态栏需要重新设置padding
            // TODO: 2019-06-12 实现方式改了，背景不会影响margin by p_bbinlliu
//            recoverImmerseMode();
            transitionDrawable.startTransition(800);
        }

        //外部接口的操作
        if (stateChangeListener != null) {
            stateChangeListener.onHideTitleBg();
        }

    }
    private void recoverImmerseMode() {
        if (model != null && model.needImmerseMode && Build.VERSION.SDK_INT >= 19) {
            common_titler.setPadding(0, AppConstant.statusBarHeight, 0, 0);
        }
    }


    public void setBackgroundResource(int resId) {
        bgDrawable = BaseApplication.Companion.getINSTANCE().getResources().getDrawable(resId);
//        common_titler.setBackgroundResource(resId);
    }

    public void setBackgroundDrawable(Drawable d) {
        bgDrawable = d;
//        common_titler.setBackgroundDrawable(d);
    }

    public interface StateChangeListener{
        void onSetTitleBg();
        void onHideTitleBg();
    }


    /**
     * StateChangeTitler是否可用
     * @return
     */
    private boolean isAvailable = true;
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * 这个控件和别的抽离控件有些不同。区别在于有可能oppo这边使用该控件，但是华为这边不用。或者反之。
     * 但是在activity使用处代码都是公用的，所以为了判断是否使用在布局里面加了一个available属性。
     * @param attrs
     */
    public void initAvailable(AttributeSet attrs) {
        TypedArray typedArray = mActivity.obtainStyledAttributes(attrs, R.styleable.StateChangeTitler);
        if(typedArray != null) {
            isAvailable = typedArray.getBoolean(R.styleable.StateChangeTitler_available, true);
            typedArray.recycle();
        }
    }


    public void setBackIcon(int drawableResource) {
        Drawable drawable = BaseApplication.Companion.getINSTANCE().getResources().getDrawable(drawableResource);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        if (backView != null) {
            backView.setCompoundDrawables(drawable, null, null, null);
            backView.setVisibility(View.VISIBLE);
        }
    }


    public TextView getTitle(){
        return title;
    }

    public int getIsShowing() {
        return isShowing;
    }

}
