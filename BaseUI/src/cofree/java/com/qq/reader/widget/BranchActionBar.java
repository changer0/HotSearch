package com.qq.reader.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.Xml;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qq.reader.baseui.R;
import com.qq.reader.core.config.AppConstant;
import com.tencent.mars.xlog.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Field;


/**
 * Created by qiaoshouqing on 2017/2/10.
 */

public class BranchActionBar extends ReaderActionBar{
    private static final String TAG = "BranchActionBar";
    private Activity mActivity;
    private static final String XML_MENU = "menu";
    private static final String XML_GROUP = "group";
    private static final String XML_ITEM = "item";
    private LayoutInflater inflater;
    private LinearLayout menuView;
    private View mBottomRoot;
    int[] mMenuItemIds;

    public BranchActionBar(Activity activity) {
        super(activity);
        mActivity = activity;
    }

    @Override
    public void hide() {
        if(mActionBar!=null){
            mActionBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void show() {
        if(mActionBar!=null){
            mActionBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideBottom() {
        if (mBottomRoot != null){
            mBottomRoot.setVisibility(View.GONE);
        }
    }

    @Override
    public void showBottom() {
        if (mBottomRoot != null){
            mBottomRoot.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void inflate(int menuRes, Menu menu) {
        super.inflate(menuRes,menu);
        if (menuRes != IActionBar.EMPTY_LAYOUT) {
            if(isHaveActionBarIcon()) {
                inflateXml(menuRes, menu);
            }
            mActivity.getMenuInflater().inflate(menuRes, menu);
        }
    }

    @Override
    public boolean isNull() {
        if (mActionBar==null&&mBottomRoot==null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void setCustomView(View var1) {
        ViewGroup searchTop = (ViewGroup) mActivity.findViewById(R.id.search_header_root);
        if (searchTop!=null){
            searchTop.setVisibility(View.VISIBLE);
            searchTop.addView(var1);
        }
    }

    private void initBottomMenu(){
        inflater = (LayoutInflater) mActivity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBottomRoot = mActivity.findViewById(R.id.reader_popbottom_menu);
        if (mBottomRoot!=null){
            menuView = (LinearLayout)mBottomRoot.findViewById(R.id.bottom_menu);
            Log.e("BranchActionBar","初始化成功");
        } else {
            Log.e("BranchActionBar", "BranchActionBar初始化失败。" +
                    "有可能是布局里没有reader_popbottom_menu布局id，也有可能是在setContentView之前调用了该代码");
        }
    }

    private void initBottomView(SparseArray<MenuItem> mMenuItems){
        menuView.removeAllViews();
        int menuId = 0;
        int itemInVisibles = 0;//记录不可见item的个数，如果均不可见，则隐藏menuView
        for (int i = 0; i < mMenuItems.size(); i++) {
            if (mMenuItemIds!=null){
                menuId = mMenuItemIds[i];
            } else {
                menuId = mMenuItems.keyAt(i);
            }
            MenuItem menuItem = mMenuItems.get(menuId);
            itemInVisibles = menuItem.isVisible() ? itemInVisibles : itemInVisibles+1;
            final View view = inflater.inflate(R.layout.reader_popbottom_menu_item, null);
            BranchBottomMenuItem bottomItem = new BranchBottomMenuItem(view, menuId, R.id.menu_name, R.id.menu_icon);
            bottomItem.setTitle(String.valueOf(menuItem.getTitle()));
            bottomItem.setIcon(menuItem.getIcon());
            bottomItem.setVisible(menuItem.isVisible());

            bottomItem.setEnabled(menuItem.isEnabled());
            bottomItem.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if (mOnClickListener!=null) mOnClickListener.onClick(bottomItem);
                }
            });

                    menuView.addView(view);
//            actionBarItemList.add(bottomItem);
        }
        if (itemInVisibles == mMenuItems.size()) {
            hideBottom();
        } else {
            showBottom();
        }
    }

    @Override
    public void inflateBottomMenu(SparseArray<MenuItem> mMenuItems,int[] mMenuItemIds) {
        super.inflateBottomMenu(mMenuItems,mMenuItemIds);
        if (mMenuItemIds.length == 0) return;
        this.mMenuItemIds = mMenuItemIds;
        if (inflater!=null && menuView!=null){
            initBottomView(mMenuItems);
        } else {
            initBottomMenu();
            initBottomView(mMenuItems);
        }

    }

    /**
     * 判断是否存在actionbar的按钮，避免没有按钮也要解析一遍。
     * @return
     */
    public boolean isHaveActionBarIcon() {
        return true;
    }


    public void inflateXml(int menuRes, Menu menu) {
        XmlResourceParser parser = null;
        try {
            parser = mActivity.getResources().getLayout(menuRes);
            AttributeSet attrs = Xml.asAttributeSet(parser);

            parseMenu(parser, attrs, menu);
        } catch (XmlPullParserException e) {
            Log.printErrStackTrace("ReaderActionBar", e, null, null);
            throw new InflateException("Error inflating menu XML", e);
        } catch (IOException e) {
            Log.printErrStackTrace("ReaderActionBar", e, null, null);
            throw new InflateException("Error inflating menu XML", e);
        } finally {
            if (parser != null) parser.close();
        }
    }

    private boolean isIgnore = true;
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
                        if(!isIgnore) {
                            readItem(attrs, index);
                            index++;
                        }
                    } else if (tagName.equals(XML_MENU)) {
                        parseMenu(parser, attrs, null);
                    } else if(tagName.equals(XML_GROUP)) {
//                        int groupId = 0;
                        String groupCategory = "";
                        try {
                            Class clasz = Class.forName("com.android.internal.R$styleable");
                            Field field = clasz.getDeclaredField("MenuGroup");
                            field.setAccessible(true);
                            int[] textAppearanceStyleArr = (int[]) field.get(null);

//                            field = clasz.getDeclaredField("MenuGroup_id");
//                            field.setAccessible(true);
//                            Integer idStyle = (Integer) field.get(null);

                            field = clasz.getDeclaredField("MenuGroup_menuCategory");
                            field.setAccessible(true);
                            Integer categortyStyle = (Integer) field.get(null);

                            TypedArray a = mActivity.obtainStyledAttributes(attrs,
                                    textAppearanceStyleArr);

//                            groupId = a.getResourceId(idStyle, 0);
                            groupCategory = (String) a.getText(categortyStyle);


                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }

//                        TypedArray a = mActivity.obtainStyledAttributes(attrs,
//                                com.android.internal.R.styleable.MenuGroup);
//                        int groupId = a.getResourceId(com.android.internal.R.styleable.MenuGroup_id, 0);
                        Log.d(TAG, groupCategory + "");
                        if(groupCategory == null || !groupCategory.equals(Menu.CATEGORY_ALTERNATIVE + "")) {
                            isIgnore = true;
                        } else {
                            isIgnore = false;
                        }
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



    ////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////// 华为右上角按钮
    ///////////////////////////////////////////////////////////////////////////////////////


    //华为actionbar
    public static final String METHOD_SETSTARTICON = "com.huawei.android.app" +
            ".ActionBarEx:setStartIcon";
    public static final String METHOD_SETCUSTOMTITLE = "com.huawei.android.app" +
            ".ActionBarEx:setCustomTitle";
    public static final String METHOD_SETENDICON = "com.huawei.android.app.ActionBarEx:setEndIcon";

}