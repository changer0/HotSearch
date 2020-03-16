package com.qq.reader.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.qq.reader.baseui.R;
import com.qq.reader.common.utils.CommonUtility;
import com.qq.reader.core.BaseApplication;
import com.qq.reader.core.config.AppConstant;
import com.tencent.widget.AdapterView;
import com.tencent.widget.AdapterView.OnItemClickListener;
import com.tencent.widget.XListView;

/**
 * 自定义Actionbar专用
 */
public class QRPopupMenu {
    private View mRoot;
    private XListView mListView;
    private Context mContext;
    private QRPopupMenuListener mMenuListener;
    private QRPopupMenuAdapter mAdapter;

    public QRPopupMenu(Activity act) {
        mContext = act;
        LayoutInflater inflater = (LayoutInflater) act
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRoot = inflater.inflate(R.layout.webpage_popup_menu, null);
        mRoot.setVisibility(View.GONE);
        ((ViewGroup)act.getWindow().getDecorView()).addView(mRoot);

        if (Build.VERSION.SDK_INT >= 19) {//沉浸式需要增加高度statusBarHeight
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) mRoot.getLayoutParams();
            int titleBarHeight = AppConstant.statusBarHeight
                    + (int) BaseApplication.Companion.getINSTANCE().getResources().getDimension(
                    R.dimen.bookstore_titlerbar_height);
            lp.setMargins(0, titleBarHeight, 0, 0);
            mRoot.setLayoutParams(lp);
        }

        mListView = (XListView) mRoot.findViewById(R.id.webpage_popupmenu_listview);
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
                int y = (int) paramMotionEvent.getY();
                if (y > CommonUtility.dip2px(50) * mAdapter.getCount()) {//ListItem以外的区域
                    final int action = paramMotionEvent.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_CANCEL:
                        case MotionEvent.ACTION_UP:
                            cancel();
                            return true;
                    }
                }
                return false;
            }
        });

        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (mMenuListener != null) {
                    if (id != getSelectedIndex()) {
                        mMenuListener.onPopupMenuItemSelected((int) id,
                                (Bundle) view.getTag());
                    }
                }
                cancel();
            }
        });

        mAdapter = new QRPopupMenuAdapter(mContext);
        mListView.setAdapter(mAdapter);

    }

    public void show() {
        mListView.startAnimation(AnimationUtils.loadAnimation(BaseApplication.Companion.getINSTANCE(), R.anim.dropdown_enter));
        mRoot.setVisibility(View.VISIBLE);
        if (mMenuListener != null) {
            mMenuListener.onShow();
        }
    }

    public boolean isShowing() {
        return mRoot.getVisibility()==View.VISIBLE;
    }

    public void cancel() {
        mListView.startAnimation(AnimationUtils.loadAnimation(BaseApplication.Companion.getINSTANCE(), R.anim.dropdown_out));
        mRoot.setVisibility(View.GONE);
        if (mMenuListener != null) {
            mMenuListener.onCancel();
        }
    }

    public QRPopupMenuListener getMenuListener() {
        return mMenuListener;
    }


    public void setMenuListener(QRPopupMenuListener menuListener) {
        mMenuListener = menuListener;
    }

    public void add(int itemid, String name, Bundle b) {
        add(itemid, name, false, b);
    }

    public void add(int itemid, String name, boolean show, Bundle b) {
        mAdapter.addItem(itemid, name, show, b);
    }

    public void removeAllItems() {
        mAdapter.removeAllItems();
    }

    public int getMenuCount() {
        return mAdapter.getCount();
    }

    public int getSelectedIndex() {
        if (mAdapter != null) {
            return mAdapter.mSelectedIndex;
        }
        return 0;
    }

    public void setSelected(int sel) {
        mAdapter.mSelectedIndex = sel;
        mAdapter.notifyDataSetChanged();
    }

    public interface QRPopupMenuListener {
        public boolean onPopupMenuItemSelected(int id, Bundle b);
        public void onShow();
        public void onCancel();
    }

}