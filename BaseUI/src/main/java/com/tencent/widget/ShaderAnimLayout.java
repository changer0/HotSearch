package com.tencent.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

import com.tencent.mars.xlog.Log;

//import com.tencent.mobileqq.util.ReflectionUtil;

/**
 * 带遮罩动画的布局
 * @author blackiedeng
 *
 */
public class ShaderAnimLayout extends RelativeLayout {

    /**
     * 动画播放的时间
     */
    public static final long ANIM_DURATION = 200;
    
    /**
     * 动画参数
     */
    float mAnimFactor = 0;
    
    /**
     * 是否是播放隐藏的动画
     */
    boolean mHide = false;
    
    /**
     * 计算遮罩的Animation(这里不用scroller是为了方便...其实用scroller就足够了)
     */
    private Animation mCalcAnimation = new Animation() {
        
        @Override
        protected void applyTransformation(float interpolatedTime, 
                Transformation t) {
            if (mHide == true) {
                mAnimFactor = 1 - interpolatedTime;                
            } else {
                mAnimFactor = interpolatedTime; 
            }
            invalidate();
        };
    };
    
    /**
     * 是否已经初始化了
     */
    private boolean mIsInitial = false;
    
    /**
     * 绘制遮罩的path
     */
    private Path mPath = new Path();
    
    /*
     * ============
     * 三个基本的构造方法
     * ============
     */
    public ShaderAnimLayout(Context context) {
        super(context);
        init();
    }
    public ShaderAnimLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public ShaderAnimLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        if (mIsInitial == false) {
            mCalcAnimation.setDuration(ANIM_DURATION);
            mCalcAnimation.setInterpolator(new LinearInterpolator());
            
            mIsInitial = true;
        }
    }
    
    /**
     * 显示这个控件(带动画)
     */
    public void show() {
       if (getVisibility() != View.VISIBLE) {
           mHide = false;
           mCalcAnimation.setAnimationListener(null);
           clearAnimation();
           setVisibility(View.VISIBLE);
           startAnimation(mCalcAnimation);           
       }
    }
    
    /**
     * 无动画显示这个控件
     */
    public void showDirectly() {
        clearAnimation();
        mCalcAnimation.setAnimationListener(null);
        mHide = false;
        setVisibility(View.VISIBLE);
        mAnimFactor = 1;
    }
    
    /**
     * 无动画隐藏这个控件
     */
    public void hideDirectly() {
        clearAnimation();
        mCalcAnimation.setAnimationListener(null);
        mHide = true;
        setVisibility(View.GONE);
        mAnimFactor = 0;
    }
    
    /**
     * 隐藏这个控件(带动画)
     */
    public void hide() {
        if (getVisibility() == View.VISIBLE) {
            mHide = true;
            clearAnimation();
            mCalcAnimation.setAnimationListener(mAnimListener);
            startAnimation(mCalcAnimation);            
        }
    }
    
    /**
     * 动画监听器
     */
    private AnimationListener mAnimListener = new AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
        }
        
        @Override
        public void onAnimationRepeat(Animation animation) {
        }
        
        @Override
        public void onAnimationEnd(Animation animation) {
            setVisibility(View.GONE);
        }
    };
    
    @Override
    protected void dispatchDraw(Canvas canvas) {
        mPath.reset();
        mPath.addRect(getWidth() * (1-mAnimFactor), 0, 
                getWidth(), getBottom(),Direction.CW);
        try {
            canvas.clipPath(mPath,Region.Op.INTERSECT);            
        } catch (Exception e) {
            Log.printErrStackTrace("ShaderAnimLayout", e, null, null);
//            ReflectionUtil.setLayerType(this,
//                    ReflectionUtil.LAYER_TYPE_SOFTWARE, null);
        }
        super.dispatchDraw(canvas);
    }
    
    public void hideWithoutAnimation(){
    	setVisibility(View.GONE);
    }
}
