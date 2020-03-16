package com.qq.reader.service.share;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface IShareRouterService extends IProvider {

    boolean onQQShareRespon(int requestCode, int resultCode, Intent data);

    void showShareDialogForBook(Activity context, String bid, String bookName);

    void showShareDialogForPage(Activity context, String title, String pageUrl, String coverUrl, String summery);

    void showShareDialogForNote(Activity context, String bid, String title, String summery);

    /**
     * 系统分享书籍
     * @param context
     * @param bookName
     */
    void shareBookForSys(Context context, String bookName);
}
