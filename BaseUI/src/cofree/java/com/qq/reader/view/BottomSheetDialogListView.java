package com.qq.reader.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by zhanglulu on 2020/3/4.
 * for 适配 BottomSheetDialog 的 ListView ，解决手势冲突
 */
public class BottomSheetDialogListView extends ListView {
    public BottomSheetDialogListView(Context context) {
        super(context);
    }

    public BottomSheetDialogListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomSheetDialogListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //canScrollVertically(-1)的值表示是否能向下滚动，false表示已经滚动到顶部
        if (canScrollVertically(-1)) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(ev);
    }
}
