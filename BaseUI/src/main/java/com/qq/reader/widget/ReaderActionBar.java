package com.qq.reader.widget;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.qq.reader.baseui.R;
import com.qq.reader.common.utils.CommonUtility;
import com.qq.reader.common.utils.FlavorUtils;
import com.qq.reader.core.BaseApplication;
import com.qq.reader.view.DeepLinkBackView;
import com.qq.reader.view.HighLightInfo;
import com.tencent.mars.xlog.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * vivo的ActionBar实现类,用作公共的。
 * Created by qiaoshouqing on 2017/1/16.
 */

public class ReaderActionBar implements IActionBar, QRPopupMenu.QRPopupMenuListener, View.OnClickListener {

    private static final String XML_MENU = "menu";
    private static final String XML_GROUP = "group";
    private static final String XML_ITEM = "item";
    private static final int SECOND_TO_LAST_ITEM = 2;
    private static final int RIGHT_MOST_ITEM = 1;
    private static final int BACK_ITEM = 0;

    protected View mActionBar;
    private StateChangeTitler stateChangeTitler;
    private Activity mActivity;
    protected TextView mTitleView;
    protected DeepLinkBackView mDeepLinkBackView;
    private IOnNavigationListener mOnNavigationListener;
    private IOnItemSelectedListener mOnItemSelectedListener;
    private SpinnerAdapter mSpinnerAdapter;
    private QRPopupMenu mPopupMenu;
    private ImageView mSortImage;
    private View.OnClickListener onPopupClick;
    protected IOnOptionsMenuClickListener mOnClickListener;
    protected List<IMenuItem> actionBarItemList = new ArrayList<>();
    protected boolean haveNavigation = false;
    private HighLightInfo mTitleinfo;
    private boolean isAddBackItem = true;
    private boolean isShowBackItem = true;

    public ReaderActionBar(Activity activity) {
        this.mActivity = activity;
        mActionBar = activity.findViewById(R.id.common_titler);
        if (mActionBar != null) {
            Log.e("ReaderActionBar", "初始化成功");
            mActionBar.setVisibility(View.VISIBLE);
            mTitleView = (TextView) activity.findViewById(R.id.profile_header_title);
            mDeepLinkBackView = (DeepLinkBackView) activity.findViewById(R.id.back_deep_link);
        } else {
            Log.e("ReaderActionBar", "ReaderActionBar初始化失败。" +
                    "有可能是布局里没有common_titler布局id，也有可能是在setContentView之前调用了该代码");
        }
    }

    @Override
    public DeepLinkBackView getDeepLinkBackView() {
        return mDeepLinkBackView;
    }

    @Override
    public void setTitle(String str) {
        if (mTitleView != null) {
            mTitleView.setText(str);
        }
        if (isAddBackItem) addBackItem();
    }

    @Override
    public void setTitle(int i) {
        if (mTitleView != null) {
            mTitleView.setText(i);
        }
        if (isAddBackItem) addBackItem();
    }

    @Override
    public void setBackTitle(String str) {
        if (mActionBar != null) {
            TextView leftItem = (TextView) mActivity.findViewById(R.id.profile_header_left_button);
            leftItem.setVisibility(View.VISIBLE);
            leftItem.setText(str);
        }
    }

    @Override
    public void setBackIcon(int drawableResource) {
        if (mActionBar != null) {
            TextView leftItem = (TextView) mActivity.findViewById(R.id.profile_header_left_button);
            Drawable drawable = BaseApplication.Companion.getINSTANCE().getResources().getDrawable(drawableResource);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            leftItem.setCompoundDrawables(drawable, null, null, null);
            leftItem.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setRightItem(String str) {
        if (actionBarItemList != null) {
            IActionBar.IMenuItem rightItem = actionBarItemList.get(RIGHT_MOST_ITEM);
            if (rightItem != null) {
                rightItem.setTitle(str);
            }
        }
    }

    @Override
    public void setRightItemColor(int color) {
        if (actionBarItemList != null && SECOND_TO_LAST_ITEM < actionBarItemList.size()) {
            IActionBar.IMenuItem rightItem = actionBarItemList.get(SECOND_TO_LAST_ITEM);
            if (rightItem != null) {
                rightItem.setTitleColor(color);
            }
        }
    }

    @Override
    public void setLeftIcon(Drawable drawable) {
        setLeftItem(drawable, true);
    }

    @Override
    public void setLeftIcon(int drawableResource) {
        setLeftItem(drawableResource, true);
    }

    @Override
    public void setLeftTitle(String str) {
        setLeftItem(str, true);
    }

    @Override
    public void hide() {
//        mActionBar.setVisibility(View.GONE);
    }

    @Override
    public void show() {
//        if(mActionBar!=null){
//            mActionBar.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void hideBottom() {

    }

    @Override
    public void showBottom() {

    }

    @Override
    public boolean isNull() {
        return mActionBar == null;
    }

    @Override
    public void setNavigationMode(int var1) {
        initPopupMenu();
        haveNavigation = true;
    }

    private void initPopupMenu() {
        mSortImage = (ImageView) mActivity.findViewById(R.id.profile_header_title_sort);
        if (mPopupMenu == null) {
            mPopupMenu = new QRPopupMenu(mActivity);
            showShadowView();
        }

        setSelectedNavigationItem(0);
        mSortImage.setVisibility(View.VISIBLE);
        mSortImage.setImageResource(R.drawable.bookstore_title_arrow);
        onPopupClick = v -> showPopMenu();
        mSortImage.setOnClickListener(onPopupClick);
        mTitleView.setOnClickListener(onPopupClick);
    }

    private void showPopMenu() {
        if (mPopupMenu.isShowing()) {
            mSortImage.setImageResource(R.drawable.bookstore_title_arrow);
            mPopupMenu.cancel();
        } else {
            mSortImage
                    .setImageResource(R.drawable.bookstore_title_arrow_up);
            mPopupMenu.show();
        }
    }

    private void showShadowView() {
        if (mPopupMenu == null) {
            return;
        }
    }

    private HighLightInfo getTitlebarHighLightArea() {
        if (mTitleinfo == null) {
            // 返回顶部整个矩形区域
            View view = mActionBar;
            int[] rect = new int[4];
            view.getLocationOnScreen(rect);
            rect[2] = rect[0] + view.getWidth();
            rect[3] = rect[1] + view.getHeight();
            mTitleinfo = new HighLightInfo();
            mTitleinfo.rect = new Rect(rect[0], rect[1], rect[2], rect[3]);
            mTitleinfo.shape = HighLightInfo.RECT;
        }
        return mTitleinfo;
    }

    @Override
    public void setListNavigationCallbacks(SpinnerAdapter var1, IOnNavigationListener var2) {
        mPopupMenu.removeAllItems();
        for (int i = 0; i < var1.getCount(); i++) {
            mPopupMenu.add(i, var1.getItem(i).toString(), null);
        }
        mSpinnerAdapter = var1;
        setTitle(mSpinnerAdapter.getItem(0).toString());
        mOnNavigationListener = var2;
        mPopupMenu.setMenuListener(this);
    }

    @Override
    public void setSelectedNavigationItem(int var1) {
        mPopupMenu.setSelected(var1);
    }

    @Override
    public boolean haveNavigation() {
        return haveNavigation;
    }

    @Override
    public void setBackgroundResource(int titler_bg) {
        if (mActionBar != null) {
            mActionBar.setBackgroundResource(titler_bg);
        }
    }

    @Override
    public void setBackgroundColor(int color) {
        if (mActionBar != null) {
            mActionBar.setBackgroundColor(color);
        }
    }

    @Override
    public void setTitleAlpha(int i) {
        if (mTitleView != null) {
            CommonUtility.setAlpha(mTitleView, i);
        }
    }

    private void removeRightMostTextItem() {
        TextView rightButton = (TextView) mActivity.findViewById(R.id.profile_header_right_button);
        rightButton.setVisibility(View.GONE);
        //解析布局顺便添加进去的是第二个，第一个是返回键。
        if (actionBarItemList != null && actionBarItemList.size() > RIGHT_MOST_ITEM) {
            actionBarItemList.remove(RIGHT_MOST_ITEM);
        }
    }

    private void addBackItem() {
        if (mActionBar == null) {
            return;
        }
        MyActionBarItem backItem = new MyActionBarItem(mActivity, R.id.profile_header_left_button);
        if (mActivity.findViewById(R.id.profile_header_left_back)!=null)
        mActivity.findViewById(R.id.profile_header_left_back).setVisibility(View.GONE);
        if (!isShowBackItem) {
            backItem.setVisible(false);
        } else {
            backItem.setVisible(true);
        }
        if(!FlavorUtils.isSamsung()) {
            //三星的返回按钮有点特殊是图片加文字。不能单单设置成图片。
            backItem.setIcon(R.drawable.titlebar_icon_back_selector);
        }
        backItem.setOnClickListener(this);
        backItem.setItemId(android.R.id.home);

        actionBarItemList.add(backItem);
        isAddBackItem = false;
    }

    private void setLeftItem(int drawableResource, boolean itemVisible) {
        Drawable drawable = mActivity.getResources().getDrawable(drawableResource);
        setLeftItem(drawable, itemVisible);
    }

    private void setLeftItem(Drawable drawable, boolean itemVisible) {
        if (mActionBar == null) {
            return;
        }
        IMenuItem leftItem = actionBarItemList.get(BACK_ITEM);

        if (leftItem != null) {
            if (!isShowBackItem) {
                leftItem.setVisible(false);
            } else {
                leftItem.setVisible(itemVisible);
            }
            leftItem.setIcon(drawable);
        }
    }

    private void setLeftItem(String str, boolean itemVisible) {
        if (mActionBar == null) {
            return;
        }
        IMenuItem leftItem = actionBarItemList.get(BACK_ITEM);
        if (leftItem != null) {
            if (!isShowBackItem) {
                leftItem.setVisible(false);
            } else {
                leftItem.setVisible(itemVisible);
            }
            leftItem.setTitle(str);
        }
    }

    private void addRightItem(int drawableResource, int itemId, boolean itemVisible, int index) {
        int layoutId = 0;
        if (index == RIGHT_MOST_ITEM) {
            layoutId = R.id.profile_header_right_button;
        }
        else if (index == SECOND_TO_LAST_ITEM) {
            layoutId = R.id.profile_header_right_button2;
        }
        MyActionBarItem mRightTextItem = new MyActionBarItem(mActivity, layoutId);
        mRightTextItem.setVisible(itemVisible);
        if (drawableResource != 0) {
            Drawable drawable = mActivity.getResources().getDrawable(drawableResource);
            mRightTextItem.setIcon(drawable);
        }
        mRightTextItem.setOnClickListener(this);

        mRightTextItem.setItemId(itemId);
        actionBarItemList.add(mRightTextItem);
    }

    private void addRightItem(String str, int itemId, boolean itemVisible, int index) {
        if (mActionBar == null) {
            return;
        }
        int layoutId = 0;
        if (index == RIGHT_MOST_ITEM) {
            layoutId = R.id.profile_header_right_button;
        }
        else if (index == SECOND_TO_LAST_ITEM) {
            layoutId = R.id.profile_header_right_button2;
        }
        MyActionBarItem mRightTextItem = new MyActionBarItem(mActivity, layoutId);
        mRightTextItem.setVisible(itemVisible);
        mRightTextItem.setTitle(str);
        mRightTextItem.setOnClickListener(this);

        mRightTextItem.setItemId(itemId);
        actionBarItemList.add(mRightTextItem);
    }

    @Override
    public void inflate(int menuRes, Menu menu) {
//        if (menuRes == EMPTY_LAYOUT) {
//            return;
//        }
//        XmlResourceParser parser = null;
//        try {
//            parser = mActivity.getResources().getLayout(menuRes);
//            AttributeSet attrs = Xml.asAttributeSet(parser);
//
//            parseMenu(parser, attrs, menu);
//        } catch (XmlPullParserException e) {
//            Log.printErrStackTrace("ReaderActionBar", e, null, null);
//            throw new InflateException("Error inflating menu XML", e);
//        } catch (IOException e) {
//            Log.printErrStackTrace("ReaderActionBar", e, null, null);
//            throw new InflateException("Error inflating menu XML", e);
//        } finally {
//            if (parser != null) parser.close();
//        }
        if (menuRes != IActionBar.EMPTY_LAYOUT) {
            mActivity.getMenuInflater().inflate(menuRes, menu);

            if (menu != null) {
                for (int i = 0; i < menu.size(); i++) {
                    if (menu.getItem(i) == null) {
                        return;
                    }
                    String itemTitle = (String) menu.getItem(i).getTitle();
                    int itemIconResId = 0;

                    int itemTitleId = menu.getItem(i).getItemId();
                    boolean itemVisible = menu.getItem(i).isVisible();
                    if (i == 0) {
                        //menu文件里item只会写icon和title其中一个，只有icon是图片按钮，只有title是文字按钮
                        if (itemTitle != null) {
                            addRightItem(itemTitle, itemTitleId, itemVisible, RIGHT_MOST_ITEM);
                        } else {
                            addRightItem(itemIconResId, itemTitleId, itemVisible, RIGHT_MOST_ITEM);
                        }
                    } else if (i == 1) {
                        removeRightMostTextItem();
                        if (itemTitle != null) {
                            addRightItem(itemTitle, itemTitleId, itemVisible, SECOND_TO_LAST_ITEM);

                        } else {
                            addRightItem(itemIconResId, itemTitleId, itemVisible, SECOND_TO_LAST_ITEM);
                        }
                    } else if (i == 2) {
                        if (itemTitle != null) {
                            addRightItem(itemTitle, itemTitleId, itemVisible, RIGHT_MOST_ITEM);

                        } else {
                            addRightItem(itemIconResId, itemTitleId, itemVisible, RIGHT_MOST_ITEM);
                        }
                    }
                    menu.getItem(i).setVisible(false);
                }
            }
        }
    }

    @Override
    public View inflateBottom(ViewGroup rootView, int layout, int[] layoutId, int[] textId, int[] iconId) {
        LinearLayout bottomView = (LinearLayout) LayoutInflater.from(mActivity).inflate(layout, null);
        rootView.addView(bottomView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        RelativeLayout.LayoutParams params =
                (RelativeLayout.LayoutParams) bottomView.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bottomView.setLayoutParams(params);

        for (int i = 0; i < layoutId.length; i++) {
            BottomMenuItem bottomItem = new BottomMenuItem(bottomView, layoutId[i], textId[i], iconId[i]);
            bottomItem.setOnClickListener(this);
            actionBarItemList.add(bottomItem);
        }

        return bottomView;
    }

    @Override
    public void inflateBottomMenu(SparseArray<MenuItem> mMenuItems,int[] mMenuItemIds) {
    }

    private void parseMenu(XmlPullParser parser, AttributeSet attrs, Menu menu)
            throws XmlPullParserException, IOException {

        int eventType = parser.getEventType();
        String tagName;
        boolean lookingForEndOfUnknownTag = false;
        String unknownTagName = null;

        // This loop will skip to the menu start tag
        do {
            if (eventType == XmlPullParser.START_TAG) {
                tagName = parser.getName();
                if (tagName.equals(XML_MENU)) {
                    // Go to next tag
                    eventType = parser.next();
                    break;
                }

                throw new RuntimeException("Expecting menu, got " + tagName);
            }
            eventType = parser.next();
        } while (eventType != XmlPullParser.END_DOCUMENT);

        boolean reachedEndOfMenu = false;
        int index = 0;
        while (!reachedEndOfMenu) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (lookingForEndOfUnknownTag) {
                        break;
                    }

                    tagName = parser.getName();
                    if (tagName.equals(XML_ITEM)) {
                        readItem(attrs, index);
                        index++;
                    } else if (tagName.equals(XML_MENU)) {
                        parseMenu(parser, attrs, null);
                    } else {
                        lookingForEndOfUnknownTag = true;
                        unknownTagName = tagName;
                    }
                    break;

                case XmlPullParser.END_TAG:
                    tagName = parser.getName();
                    if (lookingForEndOfUnknownTag && tagName.equals(unknownTagName)) {
                        lookingForEndOfUnknownTag = false;
                        unknownTagName = null;
                    } else if (tagName.equals(XML_MENU)) {
                        reachedEndOfMenu = true;
                    }
                    break;

                case XmlPullParser.END_DOCUMENT:
                    throw new RuntimeException("Unexpected end of document");
            }
            eventType = parser.next();
        }
    }

    protected void readItem(AttributeSet attrs, int index) {

        int[] textAppearanceStyleArr = new int[0];
        int titleStyle = 0;
        int iconStyle = 0;
        int titleIdStyle = 0;
        int visibleStyle = 0;
        try {
            Class clasz = Class.forName("com.android.internal.R$styleable");
            Field field = clasz.getDeclaredField("MenuItem");
            field.setAccessible(true);
            textAppearanceStyleArr = (int[])field.get(null);

            field = clasz.getDeclaredField("MenuItem_title");
            field.setAccessible(true);
            titleStyle = (Integer)field.get(null);

            field = clasz.getDeclaredField("MenuItem_icon");
            field.setAccessible(true);
            iconStyle = (Integer)field.get(null);

            field = clasz.getDeclaredField("MenuItem_id");
            field.setAccessible(true);
            titleIdStyle = (Integer)field.get(null);

            field = clasz.getDeclaredField("MenuItem_visible");
            field.setAccessible(true);
            visibleStyle = (Integer)field.get(null);


        } catch (ClassNotFoundException e) {
            Log.printErrStackTrace("ReaderActionBar", e, null, null);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.printErrStackTrace("ReaderActionBar", e, null, null);
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            Log.printErrStackTrace("ReaderActionBar", e, null, null);
            e.printStackTrace();
        }


        TypedArray a = mActivity.obtainStyledAttributes(attrs,
                textAppearanceStyleArr);

        String itemTitle = (String) a.getText(titleStyle);
        int itemIconResId = a.getResourceId(iconStyle, 0);
        int itemTitleId = a.getResourceId(titleIdStyle, 0);
        boolean itemVisible = a.getBoolean(visibleStyle, true);
        if (index == 0) {
            //menu文件里item只会写icon和title其中一个，只有icon是图片按钮，只有title是文字按钮
            if (itemTitle != null) {
                addRightItem(itemTitle, itemTitleId, itemVisible, RIGHT_MOST_ITEM);
            }
            else {
                addRightItem(itemIconResId, itemTitleId, itemVisible, RIGHT_MOST_ITEM);
            }
        }
        else if(index == 1) {
            removeRightMostTextItem();
            if (itemTitle != null) {
                addRightItem(itemTitle, itemTitleId, itemVisible, SECOND_TO_LAST_ITEM);

            }
            else {
                addRightItem(itemIconResId, itemTitleId, itemVisible, SECOND_TO_LAST_ITEM);
            }
        }
        else if (index == 2) {
            if (itemTitle != null) {
                addRightItem(itemTitle, itemTitleId, itemVisible, RIGHT_MOST_ITEM);

            }
            else {
                addRightItem(itemIconResId, itemTitleId, itemVisible, RIGHT_MOST_ITEM);
            }
        }
        Log.e("qiaoevent", "BaseActionBar -> readItem: itemTitle:" + itemTitle);
        Log.e("qiaoevent", "BaseActionBar -> readItem: itemIconResId:" + itemIconResId);
        a.recycle();
    }

    @Override
    public void setOnOptionsMenuClickListener(IOnOptionsMenuClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public IMenuItem findItem(int itemId) {
        IMenuItem mActionBarItem = null;
        for (IMenuItem myActionBarItem : actionBarItemList) {
            if (myActionBarItem.getItemId() == itemId) {
                mActionBarItem = myActionBarItem;
            }
        }
        return mActionBarItem;
    }

    @Override
    public void setDisplayHomeAsUpEnabled(boolean var1) {
        TextView backItem = (TextView) mActivity.findViewById(R.id.profile_header_left_button);
        if (backItem != null) {
            backItem.setVisibility(var1 ? View.VISIBLE : View.GONE);
        }

        if (!var1 && mTitleView != null) {
            mTitleView.setPadding(CommonUtility.dip2px(16), 0, 0, 0);
        }
    }

    @Override
    public void setCustomView(View var1) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setCustomViewSpinner(IOnItemSelectedListener listener, SpinnerAdapter adapter, int section) {
        if (!haveNavigation) {
            initPopupMenu();
            haveNavigation = true;
            mPopupMenu.removeAllItems();
            for (int i = 0; i < adapter.getCount(); i++) {
                mPopupMenu.add(i, adapter.getItem(i).toString(), null);
            }
            mSpinnerAdapter = adapter;
            mOnItemSelectedListener = listener;
            mPopupMenu.setMenuListener(this);
        }
        setSelectedNavigationItem(section);
    }

    @Override
    public boolean onPopupMenuItemSelected(int id, Bundle b) {
        if (mSpinnerAdapter != null && mSpinnerAdapter.getCount() > id) {
            setTitle(mSpinnerAdapter.getItem(id).toString());
            setSelectedNavigationItem(id);
        }
        if (mOnNavigationListener != null) {
            return mOnNavigationListener.onNavigationItemSelected(id, 0);
        }
        if (mOnItemSelectedListener != null) {
            mOnItemSelectedListener.onItemSelected(id, 0);
            return true;
        }
        return false;
    }

    @Override
    public void onShow() {
        mSortImage
                .setImageResource(R.drawable.bookstore_title_arrow_up);
    }

    @Override
    public void onCancel() {
        mSortImage
                .setImageResource(R.drawable.bookstore_title_arrow);
    }

    @Override
    public void onClick(View v) {
        IMenuItem mItem = null;
        for (IMenuItem myActionBarItem : actionBarItemList) {
            if (myActionBarItem.getLayoutId() == v.getId()) {
                mItem = myActionBarItem;
            }

        }

        if (mOnClickListener == null || !mOnClickListener.onClick(mItem)) {
            int i = v.getId();
            if (i == R.id.profile_header_left_back || i == R.id.profile_header_left_button) {
                mActivity.finish();

            }
        }
    }

    @Override
    public CharSequence getTitle() {
        CharSequence title = "";
        if (mTitleView != null) {
           title = mTitleView.getText();
        }
        return title;
    }

    @Override
    public void setDisplayShowTitleEnabled(boolean showTitle) {
//        if(mActionBar != null) {
//            mActionBar.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getNavigationMode() {
//        if(mActionBar != null) {
//            mActionBar.getNavigationMode();
//        }
        return -1;
    }

    @Override
    public void setDisplayOptions(int options, int mask) {

    }

    @Override
    public void setAddBackItem(boolean addBackItem) {
        isAddBackItem = addBackItem;
    }

    @Override
    public void setShowBackItem(boolean showBackItem) {
        isShowBackItem = showBackItem;
    }
}
