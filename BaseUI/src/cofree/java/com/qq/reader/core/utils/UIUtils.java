package com.qq.reader.core.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qq.reader.baseui.R;
import com.qq.reader.common.utils.CommonUtility;

public class UIUtils {

    /**
     *  生成标题位于中间的 titleView
     * @param activity
     * @param title
     * @return
     */
    public static View generateAlertDialogTitleCenterView(Activity activity, String title) {
        View view = LayoutInflater.from(activity).inflate(R.layout.message_dialog_title_center, null);
        ((TextView) view.findViewById(R.id.message_dialog_title)).setText(title);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return view;
    }
    /**
     *  生成 titleTip 自定义 titleView
     * @param activity
     * @param title
     * @param tips
     * @return
     */
    public static View generateAlertDialogTitleTipsView(Activity activity, String title, String tips) {
        View view = LayoutInflater.from(activity).inflate(R.layout.message_dialog_title_tip, null);
        ((TextView) view.findViewById(R.id.message_dialog_title)).setText(title);
        ((TextView) view.findViewById(R.id.message_dialog_title_tip)).setText(tips);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = CommonUtility.dip2px(8);//距离底部 8 dp
        view.setLayoutParams(lp);
        return view;
    }

    /**
     *  生成 MsgTip 自定义 View
     * @param activity
     * @param msg
     * @param tips
     * @return
     */
    public static View generateAlertDialogMsgTipsView(Activity activity, String msg, String tips) {
        View view = LayoutInflater.from(activity).inflate(R.layout.message_dialog_msg_tip, null);
        ((TextView) view.findViewById(R.id.message_dialog_msg)).setText(msg);
        ((TextView) view.findViewById(R.id.message_dialog_msg_tips)).setText(tips);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return view;
    }

}
