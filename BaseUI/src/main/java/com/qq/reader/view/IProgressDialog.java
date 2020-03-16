package com.qq.reader.view;

import android.content.DialogInterface;

/**
 * @author zhanglulu on 2018/11/3.
 * for 自定义ProgressDialog
 */
public interface IProgressDialog {

    void show();

    void setCancelable(boolean isCancelable);

    void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener);

    boolean isShowing();

    void dismiss();

    void cancel();

    void setCanceledOnTouchOutside(boolean b);

    void setOnKeyListener(DialogInterface.OnKeyListener listener);
}
