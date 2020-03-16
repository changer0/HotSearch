package com.qq.reader.view;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qq.reader.appconfig.BaseUIConfig;
import com.qq.reader.badge.BadgeManager;
import com.qq.reader.badge.BadgeObservable;
import com.qq.reader.badge.BadgeObserver;
import com.qq.reader.badge.BadgeTreeNodeItem;
import com.qq.reader.badge.define.BadgeIds;
import com.qq.reader.badge.define.BadgeConstant;
import com.qq.reader.baseui.R;
import com.qq.reader.common.monitor.EventNames;
import com.qq.reader.common.monitor.RDM;
import com.qq.reader.common.utils.CommonUtility;
import com.qq.reader.core.BaseApplication;
import com.qq.reader.service.login.LoginRouterService;
import com.qq.reader.widget.IBottomNavigationView;
import com.tencent.mars.xlog.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lvxinghe on 2018/7/6.
 */

public class ReaderBottomNavigationView implements IBottomNavigationView, BadgeObserver {

    private final String TAG = "ReaderBottomNavigationView";

    public static final int TAB_INDEX_BOOKSHELF = 0;
    public static final int TAB_INDEX_READER_DYNAMIC = 1;
    public static final int TAB_INDEX_STACK_ROOM = 2;
    public static final int TAB_INDEX_MAIN_WEB = 3;
    public static final int TAB_INDEX_PROFILE = 4;
    
    private TabGroup tabGroup;
    private OnTabChangedListener mOnTabChangedListener;

    private ImageView mBadgeBookshelf;
    private ImageView mBadgeRecommend;
    private ImageView mBadgeStack;
    private ImageView mBadgeWeb;
    private ImageView mBadgeProfile;
    private View mTabButton_Recommend;

    private ImageView mTabsBookshelf;
    private ImageView mTabsRecommend;
    private ImageView mTabsCategory;
    private ImageView mTabsMe;
    private ImageView mTabsWeb;

    private int[] rect = null;

    @Override
    public void inflate(Context context, ViewGroup rootView) {

        if(rootView == null){
            Log.e(TAG,"rootView is null");
            return;
        }

        View bottomView =  LayoutInflater.from(context).inflate(R.layout.bottom_tabs_main, null);
        tabGroup = (TabGroup) bottomView.findViewById(R.id.main_radio);
        tabGroup.setPadding(CommonUtility.dip2px(10),0,CommonUtility.dip2px(10),0);

        View bgView = new View(context);
        bgView.setBackgroundResource(R.drawable.skin_bg_navigation);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, CommonUtility.dip2px(50));
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bgView.setLayoutParams(lp);

        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bottomView.setLayoutParams(lp2);

        ((RelativeLayout)rootView).addView(bgView);
        ((RelativeLayout)rootView).addView(bottomView);

        //红点
        mBadgeBookshelf = (ImageView) tabGroup.findViewById(R.id.maintab_badge_bookshelf);
        mBadgeRecommend = (ImageView) tabGroup.findViewById(R.id.maintab_badge_recommend);
        mBadgeStack = (ImageView) tabGroup.findViewById(R.id.maintab_badge_stack);
        mBadgeWeb = (ImageView) tabGroup.findViewById(R.id.maintab_badge_web);
        mBadgeProfile = (ImageView) tabGroup.findViewById(R.id.maintab_badge_profile);

        mTabButton_Recommend = tabGroup.findViewById(R.id.radio_button_recommend);


        //tab
        mTabsBookshelf = (ImageView)tabGroup.findViewById(R.id.iv_tabs_bookshelf);
        mTabsRecommend = (ImageView)tabGroup.findViewById(R.id.iv_tabs_recommend);
        mTabsCategory = (ImageView)tabGroup.findViewById(R.id.iv_tabs_stack);
        mTabsMe = (ImageView)tabGroup.findViewById(R.id.iv_tabs_profile);
        mTabsWeb = (ImageView)tabGroup.findViewById(R.id.iv_tabs_web);

        tabGroup.setOnTabChangedListener(new TabGroup.OnTabChangedListener(){
            @Override
            public void onTabSelectionChanged(int lastPosition, int currentPosition) {
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {//
                    requestIconLayout(lastPosition);
                    requestIconLayout(currentPosition);
                }
                if(mOnTabChangedListener != null) {
                    mOnTabChangedListener.onTabSelectionChanged(lastPosition, currentPosition);
                }

                if (currentPosition == TAB_INDEX_MAIN_WEB) {
                    if (!LoginRouterService.isLogin(BaseApplication.getInstance()) && mBadgeWeb != null && mBadgeWeb.getVisibility() != View.GONE) {
                        handleRedDot(TAB_INDEX_MAIN_WEB, false);
                    }

                    Map<String, String> map = new HashMap<>();
                    if (mBadgeWeb != null) {
                        if (mBadgeWeb.getVisibility() == View.VISIBLE) {
                            map.put("ext", "1");
                        } else {
                            map.put("ext", "0");
                        }
                    }
                    RDM.stat(EventNames.EVENT_XG109, map);
                }
            }
        });
        
//       单处处理签到红点
        if (!LoginRouterService.isLogin(BaseApplication.getInstance()) && BaseUIConfig.getShouldShowMyTabReddot()) {
            handleRedDot(TAB_INDEX_MAIN_WEB, true);
        }

        BadgeManager.getInstance().addObserver(BadgeIds.TYPE_BADGE_MAIN_TAB_PROFILE, this);
        BadgeManager.getInstance().addObserver(BadgeIds.TYPE_BADGE_MAIN_TAB_FEED, this);
        BadgeManager.getInstance().addObserver(BadgeIds.TYPE_BADGE_MAIN_TAB_WELFARE, this);
    }

    @Override
    public boolean setCurrentTab(int index) {
        return tabGroup.setCurrentTab(index);
    }

    @Override
    public void setOnTabChangedListener(OnTabChangedListener listener) {
        mOnTabChangedListener = listener;
    }

    @Override
    public int getPreTabIndex() {
        return tabGroup.popHistory();
    }

    @Override
    public int getCurrentTabIndex() {
        return tabGroup.getCurrentTabIndex();
    }

    @Override
    public void addToHistory(int index) {
        tabGroup.addToHistory(index);
    }

    @Override
    public void clearHistory() {
        tabGroup.clearHistory();
    }

    @Override
    public void handleRedDot(int positoin, boolean isShowReddot) {
        if (isShowReddot) {
            if (positoin == TAB_INDEX_BOOKSHELF) {
                mBadgeBookshelf.setVisibility(View.VISIBLE);
            } else if (positoin == TAB_INDEX_PROFILE) {
                mBadgeProfile.setVisibility(View.VISIBLE);
            } else if (positoin == TAB_INDEX_MAIN_WEB) {
                mBadgeWeb.setVisibility(View.VISIBLE);
            }
        } else {
            if (positoin == TAB_INDEX_BOOKSHELF) {
                mBadgeBookshelf.setVisibility(View.GONE);
            } else if (positoin == TAB_INDEX_PROFILE) {
                mBadgeProfile.setVisibility(View.GONE);
            } else if (positoin == TAB_INDEX_MAIN_WEB) {
                mBadgeWeb.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int[] getArea(){
        if (rect == null) {
            rect = new int[4];
            mTabButton_Recommend.getLocationOnScreen(rect);
            rect[2] = rect[0] + mTabButton_Recommend.getWidth();
            rect[3] = rect[1] + mTabButton_Recommend.getHeight();
        }
        return rect;
    }

    @Override
    public View getView() {
        return tabGroup;
    }

    private void requestIconLayout(int position){
        switch (position){
            case 0:
                mTabsBookshelf.requestLayout();
                break;
            case 1:
                mTabsRecommend.requestLayout();
                break;
            case 2:
                mTabsCategory.requestLayout();
                break;
            case 3:
                mTabsWeb.requestLayout();
            case 4:
                mTabsMe.requestLayout();
                break;
            default:
                break;
        }
    }


    /**
     * 更新红点状态
     * @param observable
     * @param item
     */
    @Override
    public void updateBadge(BadgeObservable observable, BadgeTreeNodeItem item) {
        if (item == null) {
            return;
        }

        if (item.getBadgeId() == BadgeIds.TYPE_BADGE_MAIN_TAB_PROFILE) {
            //当前tab不展示
            if (item.getBadgeType() != BadgeConstant.TYPE_BADGE_CLEAR_CALCULATE_CHILD_NODE
                    && getCurrentTabIndex() == TAB_INDEX_PROFILE
                    && item.getBadgeStatus() == BadgeConstant.TYPE_BADGE_STATUS_SHOW) {

                BadgeManager.getInstance().handleClearBadge(item.getBadgeId(), false);
                if (mBadgeProfile != null) {
                    mBadgeProfile.setVisibility(View.GONE);
                }
                return;
            }

            if (mBadgeProfile != null) {
                BadgeManager.getInstance().handleBadgeExposure(item.getBadgeId());
                mBadgeProfile.setVisibility(item.getBadgeStatus() == BadgeConstant.TYPE_BADGE_STATUS_SHOW ? View.VISIBLE : View.GONE);
            }
        } else if (item.getBadgeId() == BadgeIds.TYPE_BADGE_MAIN_TAB_FEED) {
            if (item.getBadgeType() != BadgeConstant.TYPE_BADGE_CLEAR_CALCULATE_CHILD_NODE
                    && getCurrentTabIndex() == TAB_INDEX_READER_DYNAMIC
                    && item.getBadgeStatus() == BadgeConstant.TYPE_BADGE_STATUS_SHOW) {
                BadgeManager.getInstance().handleClearBadge(item.getBadgeId(), false);
                if (mBadgeRecommend != null) {
                    mBadgeRecommend.setVisibility(View.GONE);
                }
                return;
            }

            if (mBadgeRecommend != null) {
                BadgeManager.getInstance().handleBadgeExposure(item.getBadgeId());
                mBadgeRecommend.setVisibility(item.getBadgeStatus() == BadgeConstant.TYPE_BADGE_STATUS_SHOW ? View.VISIBLE : View.GONE);
            }
        } else if (item.getBadgeId() == BadgeIds.TYPE_BADGE_MAIN_TAB_WELFARE) {
            if (item.getBadgeType() != BadgeConstant.TYPE_BADGE_CLEAR_CALCULATE_CHILD_NODE
                    && getCurrentTabIndex() == TAB_INDEX_MAIN_WEB
                    && item.getBadgeStatus() == BadgeConstant.TYPE_BADGE_STATUS_SHOW) {
                BadgeManager.getInstance().handleClearBadge(item.getBadgeId(), false);
                if (mBadgeWeb != null) {
                    mBadgeWeb.setVisibility(View.GONE);
                }
                return;
            }

            if (mBadgeWeb != null) {

                BadgeManager.getInstance().handleBadgeExposure(item.getBadgeId());

                mBadgeWeb.setVisibility(item.getBadgeStatus() == BadgeConstant.TYPE_BADGE_STATUS_SHOW ? View.VISIBLE : View.GONE);

            }
        }
    }


    //先注释掉 后面华为控件支持了 再换回来
//    private final String TAG = "ReaderBottomNavigationView";
//
//    private HwBottomNavigationView mHwBottomView;
//    private OnTabChangedListener mOnTabChangedListener;
//    // 浏览顺序栈，当前用于精选新手card跳分类，返回到精选，其他情况均不使用
//    private Stack<Integer> historyStack = new Stack<Integer>();
//    private int mCurrentIndex = -1;
//
//    @Override
//    public void inflate(Context context, ViewGroup rootView) {
//
//        if(rootView == null){
//            Log.e(TAG,"rootView is null");
//            return;
//        }
//
//        mHwBottomView = new HwBottomNavigationView(context);
//        mHwBottomView.addMenu(R.string.bookshelf, context.getResources().getDrawable(R.drawable.maintab_bookstand_icon_bg));
//        mHwBottomView.addMenu(R.string.bookrecommend, context.getResources().getDrawable(R.drawable.maintab_city_icon_bg));
//        mHwBottomView.addMenu(R.string.bookstack, context.getResources().getDrawable(R.drawable.maintab_category_icon_bg));
//        mHwBottomView.addMenu(R.string.classify, context.getResources().getDrawable(R.drawable.maintab_stack_icon_bg));
//        mHwBottomView.addMenu(R.string.profile, context.getResources().getDrawable(R.drawable.maintab_profile_icon_bg));
//        rootView.addView(mHwBottomView);
//
//        mHwBottomView.setBottomNavListener(new HwBottomNavigationView.BottomNavListener() {
//            @Override
//            public void onBottomNavItemReselected(MenuItem menuItem, int i) {
//
//
//            }
//
//            @Override
//            public void onBottomNavItemSelected(MenuItem menuItem, int i) {
//                if(mOnTabChangedListener != null) {
//                    mOnTabChangedListener.onTabSelectionChanged(mCurrentIndex, i);
//                }
//                mCurrentIndex = i;
//            }
//
//            @Override
//            public void onBottomNavItemUnselected(MenuItem menuItem, int i) {
//
//            }
//        });
//    }
//
//    @Override
//    public boolean setCurrentTab(int index) {
//        mHwBottomView.setItemChecked(index);
//        return false;
//    }
//
//    @Override
//    public void setOnTabChangedListener(OnTabChangedListener listener) {
//        mOnTabChangedListener = listener;
//    }
//
//    @Override
//    public int getPreTabIndex() {
//        if (!historyStack.empty()) {
//            return historyStack.pop();
//        }
//        return -1;
//    }
//
//    @Override
//    public int getCurrentTabIndex() {
//        return mCurrentIndex;
//    }
//
//    @Override
//    public void addToHistory(int index) {
//        historyStack.push(index);
//    }
//
//    @Override
//    public void clearHistory() {
//        historyStack.clear();
//    }
//
//    @Override
//    public void handleRedDot(int positoin, boolean isShowReddot) {
//
//    }
//
//    @Override
//    public int[] getArea() {
//        return null;
//    }
//
//    @Override
//    public View getView() {
//        return mHwBottomView;
//    }
}
