package com.qq.reader.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import com.qq.reader.ARouterUtils;

import java.util.Stack;

public class TabGroup extends LinearLayout {
	private OnTabChangedListener mSelectionChangedListener;
    private int mSelectedTab = -1;
	// 浏览顺序栈，当前用于精选新手card跳分类，返回到精选，其他情况均不使用
	private Stack<Integer> historyStack = new Stack<Integer>();

	public TabGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	public void addView(View child, android.view.ViewGroup.LayoutParams params) {
		initChildView(child);
		super.addView(child, params);
		child.setClickable(true);
        child.setOnClickListener(new TabClickListener(getTabCount() - 1));
	}
	@Override
    public void addView(View child) {
        initChildView(child);
        super.addView(child);
        child.setClickable(true);
        child.setOnClickListener(new TabClickListener(getTabCount() - 1));
    }
	private void initChildView(View child){
		if (child.getLayoutParams() == null) {
            final LinearLayout.LayoutParams lp = new LayoutParams(0,ViewGroup.LayoutParams.FILL_PARENT, 1.0f);
            lp.setMargins(0, 0, 0, 0);
            child.setLayoutParams(lp);
        }
        // Ensure you can navigate to the tab with the keyboard, and you can touch it
        child.requestFocus();
        child.setFocusable(true);
	}
	//registered with each tab indicator so we can notify tab host
	private class TabClickListener implements OnClickListener {
	
		private final int mTabIndex;

        private TabClickListener(int tabIndex) {
            mTabIndex = tabIndex;
        }

        public void onClick(View v) {
        	if (mSelectionChangedListener != null) {
				clearHistory();
        		if (mSelectedTab != mTabIndex) {
            		getChildTabViewAt(mSelectedTab).setSelected(false);
    			}
            	getChildTabViewAt(mTabIndex).setSelected(true);
        		mSelectionChangedListener.onTabSelectionChanged(mSelectedTab,mTabIndex);
        		mSelectedTab = mTabIndex;

				if (getContext() != null) {
					if (getContext() instanceof Activity) {
						Intent intent = ((Activity) getContext()).getIntent();
						//点击时清空 PUSH_ORIGIN
						ARouterUtils.getPushStatManagerService().clearIntentPushOrigin(intent);
					}
				}
        	}
        }  
		
	}
    public int getTabCount() {
        return getChildCount();
    }
    public View getChildTabViewAt(int index) {
        return getChildAt(index);
    }
    /**
     * you must set a default tabindex after init UI 
     * @param index
     */
	public boolean setCurrentTab(int index) {
        if (index < 0 || index >= getTabCount() || index == mSelectedTab) {
            return false;
        }       
      
        if (mSelectionChangedListener != null) {
        	 if (mSelectedTab != -1) {
                 getChildTabViewAt(mSelectedTab).setSelected(false);
             }
        	getChildTabViewAt(index).setSelected(true);
			mSelectionChangedListener.onTabSelectionChanged(mSelectedTab,index);
			mSelectedTab = index;
		}
        if (isShown()) {
            sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
        }
        return true;
    }

	/**
	 * @return int 当前选中位置
	 */
	public int getCurrentTabIndex(){
		return mSelectedTab;
	}

	/**
	 * 添加Tab浏览历史
	 * @param index
	 */
	public void addToHistory(int index) {
		historyStack.push(index);
	}

	/**
	 * 添加Tab浏览历史
	 */
	public void addToHistory() {
		historyStack.push(mSelectedTab);
	}

	/**
	 * 返回Tab浏览历史
	 * @return -1为空栈
	 */
	public int popHistory() {
		if (!historyStack.empty()) {
			return historyStack.pop();
		}
		return -1;
	}

	/**
	 * 清空历史栈
	 */
	public void clearHistory(){
		historyStack.clear();
	}

	/**
     * 当前tab和目标tab一样也会切换
     * @param index
     */
	public void forceSetCurrentTab(int index){
		if (index < 0 || index >= getTabCount()) {
            return;
        }
        clearHistory();
        if (mSelectionChangedListener != null) {
        	if (mSelectedTab != -1) {
                getChildTabViewAt(mSelectedTab).setSelected(false);
            }
        	getChildTabViewAt(index).setSelected(true);
			mSelectionChangedListener.onTabSelectionChanged(mSelectedTab,index);
			mSelectedTab = index;
		}
        if (isShown()) {
            sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
        }
	}
	/**
	 * 现在不再判断lastPosition和currentPosition是否是一样的，交给回调去判断
	 * @author p_jwcao
	 */
	public interface OnTabChangedListener {
		void onTabSelectionChanged(int lastPosition,int currentPosition);
	}
	public void setOnTabChangedListener(
			OnTabChangedListener mSelectionChangedListener) {
		this.mSelectionChangedListener = mSelectionChangedListener;
	}
}
