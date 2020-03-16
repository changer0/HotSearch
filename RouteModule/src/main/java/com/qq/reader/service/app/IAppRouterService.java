package com.qq.reader.service.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.qq.reader.adv.AdvLogicCallBack;
import com.qq.reader.common.download.DownloadBookTask;
import com.qq.reader.common.login.ILoginNextTask;
import com.qq.reader.common.mark.Mark;
import com.qq.reader.dispatch.IEventListener;
import com.qq.reader.entity.AdLinkBean;
import com.qq.reader.pay.BuyPackageListner;
import com.qq.reader.qurl.JumpActivityParameter;

import java.util.List;

/**
 * 此类存在意义：未提供对外service,并且未从app中拆分出来
 */
public interface IAppRouterService extends IProvider {

    void openBook(Activity activity, Intent i);

    boolean isReadDay(Context context);

    /*****************urlcenter部分下沉不下去*****************/
    void goFeedBack(Activity context, final JumpActivityParameter jp);
    void goReaderPage(final Activity activity, String bid, int cid, int offset, JumpActivityParameter jp);
    void goActivityArea(boolean FLAG_ACTIVITY_CLEAR_TOP, Activity context, int tabIndex);
    void goProfileLevelActivity(Activity context, int vipLevel, final JumpActivityParameter jp);
    /*****************urlcenter部分下沉不下去*****************/

    boolean downLoadBook(DownloadBookTask task, Activity activity, boolean isshowmsg);

    ILoginNextTask openBuyPackageDialog(int packageid, Activity act, BuyPackageListner listner);

    void showNotification(Context context, byte eventType, int count, DownloadBookTask dt);

//    void showNotification(Context context, byte eventType, Mark onlineTag, List<Integer> chapterList);

    void showNotification(Context context,
                          byte eventType,
                          Mark onlineTag,
                          String formatCountTime,
                          boolean isNetConnect,
                          List<Integer> chapterList);

    void copyInternalBooks(int sex);

    void goNewBookStore(Activity activity, final JumpActivityParameter jp);

    void goMyTicketConvert(Activity activity, int tabIndex, String title);

    void goH5Game(Activity context, String CmdParas, boolean urlIsEncode, JumpActivityParameter jp);

    void goLocalBookMatchActivity(Activity from, Bundle bundle);

    void gotoNetImportActivity(Activity activity);

    void goMessageActivity(boolean FLAG_ACTIVITY_CLEAR_TOP, Activity activity);

    void goMain(Activity activity, final JumpActivityParameter jp);

    void goMain(Activity activity, Intent intent, final JumpActivityParameter jp);

    void goCheckNet(Activity from, JumpActivityParameter pa);

    void goHallOfFameDetail(IEventListener eventListener, String name);

    void goAccountCoupon(boolean FLAG_ACTIVITY_CLEAR_TOP, Activity context, int tabIndex);

    void goEndBookStore(Activity activity, int sex, final JumpActivityParameter jp);

    void goMonthBookStore(Activity activity, int sex, final JumpActivityParameter jp);

    void goProfileLevelActivity(Activity context, int vipLevel, int autoReceive, final JumpActivityParameter jp);

    Bundle getJumpMessageBundle();

    boolean showGameView(Activity activity, String actionId);

    void checkUpgradeManual(Activity activity);

    void goBackFinish(Activity activity);

}
