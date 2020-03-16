package com.qq.reader.module.bookstore.search;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.Selection;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.qq.reader.baseui.R;
import com.qq.reader.common.utils.FlavorUtils;
import com.tencent.mars.xlog.Log;

import java.lang.ref.WeakReference;

/**
 * Created by zhangqingning on 2016/5/5.
 */
public class DropDownEditText extends EditText {
    private static final String TAG = "DropDownEditText";

    //    BasePopupWindow mDropPop;
    ListView mListView;
    private int dropDownMarginTop = 10;
    private int mListDividerColor = getResources().getColor(R.color.oppo_color_c103);
    private int mListDividerHeight = getResources().getDimensionPixelOffset(R.dimen.common_divider_height_thin);
    private int mBgResId = android.R.color.white;
//    private int mDropDownHeight = -1;
    BaseAdapter mAdapter;
//    private Context mContext;
    private WrapperOnItemClickListener mItemClickListener = new WrapperOnItemClickListener();


    private DataSetObserver mObserver;

    public DropDownEditText(Context context) {
        super(context);
//        init(context);
    }

//    private void init(Context context) {
//        mContext = context;
//        mListView = new ListView(context);
//        mListView.setBackgroundResource(mBgResId);
//        mListView.setDivider(new ColorDrawable(mListDividerColor));
//        mListView.setDividerHeight(mListDividerHeight);
//        mListView.setOnItemClickListener(mItemClickListener);
//        mDropPop = new BasePopupWindow(mListView, ViewGroup.LayoutParams.MATCH_PARENT, mDropDownHeight);
//        mDropPop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        mDropPop.setTouchable(true);
//        mDropPop.setOutsideTouchable(false);
//        mDropPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//    }

    public void setListView(ListView listview) {
        mListView = listview;
        mListView.setBackgroundResource(mBgResId);
        if (!FlavorUtils.isCommonUI()) {
            mListView.setDivider(new ColorDrawable(mListDividerColor));
            mListView.setDividerHeight(mListDividerHeight);
        }
        mListView.setOnItemClickListener(mItemClickListener);
    }

    public DropDownEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init(context);
    }

    public DropDownEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        init(context);
    }

    public void showDropDown() {
        if (getWindowVisibility() == View.GONE) {
            return;
        }
        if (mListView != null && mAdapter != null && mAdapter.getCount() > 0) {
            mListView.setVisibility(View.VISIBLE);
        }
        if (mPopChangeListener != null) {
            mPopChangeListener.onShow();
        }
    }

//    public void showDropDown() {
//        try {
//            if (getWindowVisibility() == View.GONE) {
//                return;
//            }
//            if (!isPopupShowing()) {
//                // Make sure the list does not obscure the IME when shown for the first time.
//                mDropPop.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//                if (android.os.Build.VERSION.SDK_INT >= 24) {
//                    //statusbar height + searchBarHeight + dropDownMarginTop
//                    mDropPop.showAtLocation(((Activity)mContext).getWindow().getDecorView(),
//                            Gravity.NO_GRAVITY, 0 , (int) mContext.getResources().getDimension(R.dimen.common_dp_48) +
//                                    dropDownMarginTop +
//                                    Constant.statusBarHeight);
//                } else{
//                    mDropPop.showAsDropDown(this, 0, dropDownMarginTop);
//                }
////                mDropPop.showAsDropDown(this, 0, dropDownMarginTop);
//                if (mPopChangeListener != null) {
//                    mPopChangeListener.onShow();
//                }
//            }
//        } catch (Exception e) {
//
//        }
//    }

//    public void dismissDropDown() {
//        try {
//            if (mDropPop.isShowing()) {
//                mDropPop.dismiss();
//                if (mPopChangeListener != null) {
//                    mPopChangeListener.onDismiss();
//                }
//            }
//        } catch (Exception e) {
//
//        }
//    }

    public void dismissDropDown() {
        if (mListView != null) {
            mListView.setVisibility(View.GONE);
            Log.d(TAG, "handlegone - dismissDropDown: 隐藏底部ListView");
        }
        if (mPopChangeListener != null) {
            mPopChangeListener.onDismiss();
        }
    }

    private PopChangeListener mPopChangeListener;

    public void setDropDownVerticalOffset(int dimensionPixelOffset) {
        this.dropDownMarginTop = dimensionPixelOffset;
    }

//    public void setDropDownHeight(int mDropDownHeight) {
//        this.mDropDownHeight = mDropDownHeight;
//        mDropPop.setHeight(mDropDownHeight);
//    }

//    public void setDropDownBackgroundResource(int resId) {
//        this.mBgResId = resId;
//        mListView.setBackgroundResource(mBgResId);
//    }

//    public void setThreshold(int i) {
//    }


    public void setOnDismissListener(PopChangeListener onDropDownDissmissListener) {
        this.mPopChangeListener = onDropDownDissmissListener;
    }

//    public boolean isPopupShowing() {
//        return mDropPop.isShowing();
//    }

    public boolean isPopupShowing() {
        if (mListView != null) {
            return mListView.getVisibility() == View.VISIBLE;
        }
        return false;
    }

    public interface PopChangeListener {

        void onDismiss();

        void onShow();
    }

    public <T extends BaseAdapter & ItemContentProvider & ListViewOwnerProvider> void setAdapter(T adapter) {
        if (mObserver == null) {
            mObserver = new PopupDataSetObserver(this);
        } else if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mObserver);
        }
        mAdapter = adapter;
        if (mAdapter != null) {
            adapter.registerDataSetObserver(mObserver);
        }
        mListView.setAdapter(mAdapter);
        adapter.setOwnListView(mListView);
    }

    private static class PopupDataSetObserver extends DataSetObserver {
        private final WeakReference<DropDownEditText> mViewReference;

        private PopupDataSetObserver(DropDownEditText view) {
            mViewReference = new WeakReference<DropDownEditText>(view);
        }

        @Override
        public void onChanged() {
            final DropDownEditText textView = mViewReference.get();
            if (textView != null && textView.mAdapter != null) {
                // If the popup is not showing already, showing it will cause
                // the list of data set observers attached to the adapter to
                // change. We can't do it from here, because we are in the middle
                // of iterating through the list of observers.
                textView.post(updateRunnable);
            }
        }

        private final Runnable updateRunnable = new Runnable() {
            @Override
            public void run() {
                final DropDownEditText textView = mViewReference.get();
                if (textView == null) {
                    return;
                }
                final ListAdapter adapter = textView.mAdapter;
                if (adapter == null) {
                    return;
                }
                textView.updateDropDownForFilter(adapter.getCount());
            }
        };
    }

    private void updateDropDownForFilter(int count) {
        if (count == 0) {
            dismissDropDown();
        } else {
            showDropDown();
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        mItemClickListener.wrappedListener = listener;
    }

    private class WrapperOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (wrappedListener != null) {
                wrappedListener.onItemClick(parent, view, position, id);
            }
            CharSequence lText = ((ItemContentProvider) mAdapter).getText(position);
            if (lText != null) {
//                if (!lText.toString().equals(getText().toString())) {
//                    clearComposingText();
//                    setText(lText);
//                }
                // make sure we keep the caret at the end of the text view
                Editable spannable = getText();
                Selection.setSelection(spannable, spannable.length());
                dismissDropDown();
            }
        }

        private AdapterView.OnItemClickListener wrappedListener;
    }

    public interface ItemContentProvider {
        CharSequence getText(int position);
    }

    /**
     * 该接口为了避免popuwindow没有焦点,listview的onItemClick事件无法触发.(有一些手机没办法触发)
     * 如果popuwindow能有焦点,会和外部输入框的事件冲突,无法吊起键盘
     * 需要在自定义的adapter里面执行,该处没有抽取itemclick到base,因为不想挖个太深的坑.
     * <pre>
     * //convertView.setOnClickListener(new View.OnClickListener() {
     * //        @Override
     * //        public void onClick(View v) {
     * //            if (mOwnListView != null && mOwnListView.getOnItemClickListener() != null) {
     * //                mOwnListView.getOnItemClickListener().onItemClick(mOwnListView, convertView, pos, getItemId(pos));
     * //            }
     * //        }
     * //    });
     * </pre>
     */
    public interface ListViewOwnerProvider {
        void setOwnListView(ListView listView);
    }
}
