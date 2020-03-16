package com.qq.reader.view.tips;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;

import com.qq.reader.baseui.R;
import com.qq.reader.view.GuideDialogTouchListener;
import com.qq.reader.view.GuidePopupView;
import com.qq.reader.view.IGuide;
import com.tencent.mars.xlog.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caojianwei on 2015/7/2.
 */
public class Tip {
    
    private boolean mEnable = true;
    private int mType;
    private GuidePopupView mGuidePopupView;
//    private InterruptView mInterruptView ;
    private List<IGuide> mIGuides = new ArrayList<IGuide>();

    private OnTipsListener onTipsListener;
    
    private boolean isShowing;

    float downX = 0;
    float downY = 0;
    float moveX = 0;
    float moveY = 0;
    public Tip(Activity activity, int layoutId, int type, boolean isGuideTouch, boolean isFocusable){
        mType = type;
        if (layoutId <= 0) {
            layoutId = R.layout.new_common_tip;
        }
        mGuidePopupView = new GuidePopupView(activity, layoutId, false, isFocusable);
        mGuidePopupView.setCanceledOnTouchOutside(true);
        mGuidePopupView.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dismiss();
            }
        });
        mGuidePopupView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        if (isGuideTouch){
            mGuidePopupView.setGuideTouchListener(new GuideDialogTouchListener() {
                @Override
                public boolean touchListener(MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            downX = event.getX();
                            downY = event.getY();
                            moveX = 0;
                            moveY = 0;
                            break;
                        case MotionEvent.ACTION_UP:
                            if(moveX<5 && moveY<5){
                                dismiss();
                                return true;
                            }
                            break;
                        case MotionEvent.ACTION_MOVE:
                            moveX = Math.abs(event.getX() - downX);//X轴距离
                            moveY = Math.abs(event.getY() - downY);//y轴距离
                            break;
                    }
                    return false;
                }
            });
        }
        mIGuides.add(mGuidePopupView);
    }
    public Tip(Activity activity,int type,boolean isGuideTouch, boolean isFocusable) {
        this(activity, -1, type, isGuideTouch, isFocusable);
    }
    public Tip(Activity activity, int type,boolean isGuideTouch) {
        this(activity,type,isGuideTouch, true);
    }
    public Tip(Activity activity,int type) {
        this(activity,type,false);
    }
    
    public void show(){
        isShowing = true;
        if (onTipsListener != null){
            onTipsListener.onPreShow(mType);
        }
       
        try {
            //虽然外部判断了 activity.isFinishing，但是不能完全保证，所以try catch一下
            mGuidePopupView.show();
//            mInterruptView.show();
        } catch (Exception e) {
            Log.printErrStackTrace("Tip", e, null, null);
            Log.e("Tip", e.getMessage());
        }catch(Error error){
            Log.printErrStackTrace("Tip", error, null, null);
            Log.e("Tip", error.getMessage());
        }
        
    }
    
    public void dismiss(){
        if (!mEnable){
            return;
        }
        mEnable = false;
        try {
            if (mGuidePopupView.isShowing()){
                mGuidePopupView.dismiss();
            }
        }catch (Exception e){
            Log.printErrStackTrace("Tip", e, null, null);
            Log.e("Tip",e.getMessage());
        }
        
        for (int i = 0;i<mIGuides.size(); i++){
            mIGuides.get(i).dismiss(mType);
        }
        
//        try {
////            mInterruptView.dismiss();
//        }catch (Exception e){
//            Log.printErrStackTrace("Tip", e, null, null);
//            Log.e("Tip", e.getMessage());
//        }
        release();
        isShowing = false;

        if (onTipsListener != null){
            onTipsListener.onPostDismiss(mType);
        }
    }

    private void release(){
        mGuidePopupView = null;
//        mInterruptView = null;
        mIGuides.clear();
    }

    public int getType() {
        return mType;
    }
    

    public void setType(int type) {
        mType = type;
    }

    public void setText1OnClickListener(View.OnClickListener onClickListener){
        mGuidePopupView.setText1OnClickListener(onClickListener);
    }
    public void setText2OnClickListener(View.OnClickListener onClickListener){
        mGuidePopupView.setText2OnClickListener(onClickListener);
    }
    
    public void setGrivity(int grivity){
        mGuidePopupView.setGrivity(grivity);
    }
    
    public void setXoffset(int xoffset){
        mGuidePopupView.setXoffset(xoffset);
    }
    
    public void setYoffset(int yoffset){
        mGuidePopupView.setYoffset(yoffset);
    }
    
    public void setGuideBackGroundRes(int resId){
        mGuidePopupView.setRootViewBg(resId);
    }

    public void setmGuideImageSize(int width, int height) {
        mGuidePopupView.setmGuideImageSize(width, height);
    }
    
    public void setText1(String text){
        mGuidePopupView.setText1(text);
    }
    
    public void setText2(String text){
        mGuidePopupView.setText2(text);
    }

    public void setImageVisibility(int visibility) {
        mGuidePopupView.setImageVisibility(visibility);
    }

    /**
     * 使得文字在图片的右侧
     * 默认在左侧
     */
    public void setTextToRight() {
        mGuidePopupView.setTextToRight();
    }


    public void setShadowEnable(boolean enable){
        mGuidePopupView.setIsShowShadow(enable);
    }

    public void addIGuide(IGuide iGuide){
        mIGuides.add(iGuide);
        if (iGuide.getHighLightArea(mType) != null){
            mGuidePopupView.setHighLightRect(iGuide.getHighLightArea(mType));
        }
    }

    public boolean isEnable() {
        return mEnable;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void setIsShowing(boolean isShowing) {
        this.isShowing = isShowing;
    }

    public void setOnTipsListener(OnTipsListener onTipsListener) {
        this.onTipsListener = onTipsListener;
    }

    public void setNavigationBarIconLight() {
        if (mGuidePopupView != null) {
            mGuidePopupView.setNavigationBarIconLight();
        }
    }

    public void hideGuidImage() {
        mGuidePopupView.hideGuideImage();
    }

    public interface OnTipsListener{
        void onPreShow(int type);
        void onPostDismiss(int type);
    }
}
