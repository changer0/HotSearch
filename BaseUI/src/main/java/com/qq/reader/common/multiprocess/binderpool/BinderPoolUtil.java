package com.qq.reader.common.multiprocess.binderpool;

import android.content.Context;
import android.os.RemoteException;

import com.qq.reader.activity.ReaderBaseActivity;
import com.qq.reader.common.aidl.IDialogClickListener;
import com.qq.reader.core.view.ReaderToast;
import com.qq.reader.view.IAlertDialog;
import com.qq.reader.view.ReaderAlertDialog;


/**
 * Created by qiaoshouqing on 2017/12/24.
 *
 * 公用的多进程工具类，当前内容为主进程回调使用。
 */

public class BinderPoolUtil {

    //业务逻辑
    public static void showToast(Context context, String msg, int duration) {
        if (BinderPool.mListener != null) {
            try {
                BinderPool.mListener.showToast(msg, duration);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else {
            ReaderToast.makeText(context, msg, duration).show();
        }
    }

    //业务逻辑
    public static void showDialog(Context context, String title, String message, String positiveStr, String negativeStr, final IDialogClickListener dialogClickListener) {
        if (BinderPool.mListener != null) {
            try {
                BinderPool.mListener.showDialog(title, message, positiveStr, negativeStr, dialogClickListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else {
            IAlertDialog dialog = new ReaderAlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(positiveStr,
                            (dialog1, which) -> {
                                try {
                                    dialogClickListener.onPositiveButtonClick();
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            })
                    .setNegativeButton(negativeStr,
                            (dialog12, which) -> {
                                try {
                                    dialogClickListener.onNegativeButtonClick();
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }).create();
            dialog.show();
        }
    }

    public static void startLogin(Context context) {
        if (BinderPool.mListener != null) {
            try {
                BinderPool.mListener.startLogin();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else {
            ReaderBaseActivity activity = (ReaderBaseActivity) context;
            activity.startLogin();
        }
    }
}
