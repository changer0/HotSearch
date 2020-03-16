package com.qq.reader.view;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Created by zhanglulu on 2017/2/13.
 *
 * 用于兼容不同平台下的Dialog
 */
public interface IAlertDialog extends DialogInterface {

    // oppo 特殊的UI属性
    public static final int DELETE_ALERT_DIALOG_ONE = 1;
    public static final int DELETE_ALERT_DIALOG_TWO = 2;
    public static final int DELETE_ALERT_DIALOG_THREE = 3;


    boolean isShowing();
    void show();
    void dismiss();
    void cancel();

    void setCancelable(boolean cancelable);
    void setCanceledOnTouchOutside(boolean cancel);
    void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener);
    void setOnKeyListener(DialogInterface.OnKeyListener listener);

    void setOnDismissListener(DialogInterface.OnDismissListener listener);

    void setView(View view);
    void setView(View view, int viewSpacingLeft, int viewSpacingTop, int viewSpacingRight, int viewSpacingBottom);
    void setTitle(CharSequence title);
    void setTitle(int titleId);

    Window getWindow();
    Dialog getBaseDialog();

    void setMessage(CharSequence title);
    void setButton(int whichButton, CharSequence text, Message msg);
    void setButton(int whichButton, CharSequence text, DialogInterface.OnClickListener listener);


    TextView getButton(int whichButton);
    View findViewById(int id);


    interface IBuilder {
        IBuilder setTitle(int titleId);
        IBuilder setTitle(CharSequence title);
        IBuilder setCustomTitle(View customTitleView);
        IBuilder setMessage(int messageId);
        IBuilder setMessage(String message);
        IBuilder setIcon(int iconId);
        IBuilder setIcon(Drawable icon);
        IBuilder setView(int layoutResId);
        IBuilder setView(View view);
        IBuilder setDeleteDialogOption(int option);

        IBuilder setPositiveButton(int textId, DialogInterface.OnClickListener listener);
        IBuilder setPositiveButton(CharSequence text, DialogInterface.OnClickListener listener);
        IBuilder setNegativeButton(int textId, DialogInterface.OnClickListener listener);
        IBuilder setNegativeButton(CharSequence text, DialogInterface.OnClickListener listener);
        IBuilder setOnCancelListener(DialogInterface.OnCancelListener listener);
        IBuilder setAdapter(ListAdapter adapter, DialogInterface.OnClickListener listener);
        IBuilder setSingleChoiceItems(CharSequence[] items, int checkedItem, final OnClickListener listener);

        IBuilder setItems(CharSequence[] items, OnClickListener listener);

        IBuilder setItems(int itemsId, OnClickListener listener);

        IBuilder setCancelable(boolean cancelable);

        IAlertDialog create();
    }
}
