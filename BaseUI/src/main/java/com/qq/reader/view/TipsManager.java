package com.qq.reader.view;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;

import com.qq.reader.baseui.R;
import com.qq.reader.common.utils.CommonConfig;
import com.qq.reader.common.utils.CommonUtility;
import com.qq.reader.common.utils.FlavorUtils;
import com.qq.reader.view.tips.Tip;

/**
 * 引导管理器（夜间模式引导等等）
 * @author p_jwcao
 *
 */
public class TipsManager {
	

	public static final int TIP_NIGHTMODE = 0;//夜间模式引导
	public static final int TIP_BATDOWNLOAD = 1;//批量下载引导
	public static final int TIP_VOTE = 2;//阅读页 投票 引导
	@Deprecated
	public static final int TIP_PREFERENCE_SELECT=3;//书城偏好引导
	@Deprecated
	public static final int TIP_RANKTITLE=4;//分类页 标题引导
	@Deprecated
	public static final int TIP_MAINTAB_RANKANDCHARTS= 5;//主界面 发现排行引导
    @Deprecated
	public static final int TIP_FOCUSCATEGORY= 6;//二级页面 关注分类引导
	
	public static final int TIP_CLICPTABTOTOP = 7;//点击精选tab到顶部并刷新的引导
	public static final int TIP_MANUALFRESHFEED = 8;//精选tab提示下拉刷新的引导
    public static final int TIP_BOOKSHELF_PRIVATE_FEATURE = 9;//私密阅读功能提示

    public static final int TIP_INK_SCREEN_MODE = 10;//墨水屏模式引导

    public static final int TIP_BOOKSHELF_MORE_LIST = 11;//书架更多列表引导
    public static final int TIP_BOOKSHELF_EDIT = 12;//书架宫格编辑引导
    public static final int TIP_BOOKSHELF_DOWNLOAD_TIME = 13;//书架阅读福利引导

    public static final int TIP_BOOK_DETAIL_TO_READ = 14;//详情页阅读提示

    public static final int TIP_READ_TOP_MENU = 15;//阅读页顶部菜单引导

	public static final String NEED_TIP = "need_tip";
	
    public static Tip createTip(int type,Activity activity){
        Tip tip = null;
        switch (type) {
            case TIP_NIGHTMODE:
                //阅读页 夜间模式
                tip = new Tip(activity,type);
                tip.setGrivity(Gravity.TOP | Gravity.RIGHT);
                tip.setXoffset(activity.getResources().getDimensionPixelOffset(R.dimen.bightmodel_tip_width));
                tip.setYoffset(activity.getResources().getDimensionPixelOffset(R.dimen.bightmodel_tip_height));
                tip.setShadowEnable(!CommonConfig.isNightMode);
                tip.setText1(activity.getResources()
                        .getString(R.string.reader_tip_nightmode));
                break;
            case TIP_BATDOWNLOAD:
                //阅读页 批量下载引导
                tip = new Tip(activity,type);
                tip.setGrivity(Gravity.TOP | Gravity.RIGHT);
                tip.setXoffset(activity.getResources().getDimensionPixelOffset(R.dimen.batdownload_tip_marginRight));
                tip.setYoffset(activity.getResources().getDimensionPixelOffset(R.dimen.batdownload_tip_marginTop));
                tip.setShadowEnable(!CommonConfig.isNightMode);
                tip.setImageVisibility(View.VISIBLE);
                tip.setText1(activity.getResources()
                        .getString(R.string.reader_tip_offline_download));
                break;
            case TIP_VOTE:
                //阅读页 菜单投票 引导
                tip = new Tip(activity,type);
                tip.setGrivity(Gravity.TOP | Gravity.RIGHT);
                tip.setXoffset(activity.getResources().getDimensionPixelOffset(R.dimen.vote_tip_marginright));
                tip.setYoffset(activity.getResources().getDimensionPixelOffset(R.dimen.vote_tip_margintop));
                tip.setShadowEnable(!CommonConfig.isNightMode);
                tip.setImageVisibility(View.VISIBLE);
                tip.setText1(activity.getResources()
                        .getString(R.string.reader_tip_vote));
                break;
            case TIP_CLICPTABTOTOP:
                //信息流底部精选tab
                tip = new Tip(activity,R.layout.feed_tip, type,true, false);
                tip.setGrivity(Gravity.BOTTOM | Gravity.LEFT);
                tip.setXoffset(activity.getResources().getDimensionPixelOffset(R.dimen.maintab_tip_margin_left));
                tip.setYoffset(activity.getResources().getDimensionPixelOffset(R.dimen.maintab_tip_margin_bottom));
                tip.setShadowEnable(false);//免费不设置蒙层
                tip.setGuideBackGroundRes(R.drawable.tip_common_bg_left_bottom);
                tip.setText1(activity.getResources()
                        .getString(R.string.feed_tip_back_to_top));
                break;
            case TIP_INK_SCREEN_MODE:
                //阅读页 水墨屏模式
                tip = new Tip(activity,type, true);
                tip.setGrivity(Gravity.BOTTOM | Gravity.LEFT);
                tip.setXoffset(activity.getResources().getDimensionPixelOffset(R.dimen.ink_screen_tip_width));
                tip.setYoffset(activity.getResources().getDimensionPixelOffset(R.dimen.ink_screen_tip_height));
                tip.setShadowEnable(!CommonConfig.isNightMode);
                tip.setTextToRight();
                if (FlavorUtils.isCommonUI()) {
                    tip.setImageVisibility(View.VISIBLE);
                }
                tip.setText2(activity.getResources().getString(R.string.ink_screen_mode_guide));
                break;
            case TIP_BOOKSHELF_MORE_LIST:
                tip = new Tip(activity,R.layout.bookshelf_tip,type,false,true);
                tip.setGrivity(Gravity.TOP | Gravity.RIGHT);
                tip.setShadowEnable(true);
                tip.setGuideBackGroundRes(R.drawable.tip_bookshelf_more_list);
                break;
            case TIP_BOOKSHELF_EDIT:
                tip = new Tip(activity,R.layout.bookshelf_tip,type,false,true);
                tip.setGrivity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                tip.setShadowEnable(true);
                tip.setGuideBackGroundRes(R.drawable.tip_bookshelf_edit);
                break;
            case TIP_BOOKSHELF_DOWNLOAD_TIME:
                tip = new Tip(activity,R.layout.bookshelf_tip_dowanload_time,type,false,true);
                tip.setGrivity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                tip.setShadowEnable(true);
                break;
            case TIP_BOOK_DETAIL_TO_READ:
                tip = new Tip(activity,R.layout.book_detail_guide, type,true, false);
                tip.setGrivity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                tip.setShadowEnable(false);//免费不设置蒙层
                tip.setYoffset(CommonUtility.dip2px(66));
                break;
            case TIP_READ_TOP_MENU:
                tip = new Tip(activity, type);
                tip.setGrivity(Gravity.TOP | Gravity.END);
                tip.setShadowEnable(true);
                tip.setXoffset(CommonUtility.dip2px(26));
                tip.setGuideBackGroundRes(R.drawable.tip_read_top_menu);
                break;
        }
        return tip;
    }

}
