package com.qq.reader.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.TextView;


/**
 * Created by zhanglulu on 2017/2/13.
 * ColorOS的Dialog
 */

public class ReaderAlertDialog implements IAlertDialog {
    private AlertDialog mDialog;

    // 私有构造方法，防止直接创建
    private ReaderAlertDialog(AlertDialog dialog) {
        mDialog = dialog;
    }

    @Override
    public Dialog getBaseDialog() {
        if (mDialog != null) {
            return mDialog;
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public void show() {
        if (mDialog != null) {
            mDialog.show();
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public void setOnCancelListener(OnCancelListener onCancelListener) {
        if (mDialog != null) {
            mDialog.setOnCancelListener(onCancelListener);
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public void setCancelable(boolean cancelable) {
        if (mDialog != null) {
            mDialog.setCancelable(cancelable);
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public boolean isShowing() {
        if (mDialog != null) {
            return mDialog.isShowing();
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public void cancel() {
        if (mDialog != null) {
            mDialog.cancel();
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public Window getWindow() {
        if (mDialog != null) {
            return mDialog.getWindow();
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        if (mDialog != null) {
            mDialog.setCanceledOnTouchOutside(cancel);
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public void setOnKeyListener(OnKeyListener listener) {
        if (mDialog != null) {
            mDialog.setOnKeyListener(listener);
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        if (mDialog != null) {
            mDialog.setOnDismissListener(listener);
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public void setView(View view) {
        if (mDialog != null) {
            mDialog.setView(view);
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public void setView(View view, int viewSpacingLeft, int viewSpacingTop, int viewSpacingRight, int viewSpacingBottom) {
        if (mDialog != null) {
            mDialog.setView(view);
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        if (mDialog != null) {
            mDialog.setTitle(title);
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public void setTitle(int titleId) {
        if (mDialog != null) {
            mDialog.setTitle(titleId);
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public void setMessage(CharSequence title) {
        if (mDialog != null) {
            mDialog.setMessage(title);
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public void setButton(int whichButton, CharSequence text, Message msg) {
        if (mDialog != null) {
            mDialog.setButton(whichButton, text, msg);
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public void setButton(int whichButton, CharSequence text, OnClickListener listener) {
        if (mDialog != null) {
            mDialog.setButton(whichButton, text, listener);
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public TextView getButton(int whichButton) {
        if (mDialog != null) {
            return mDialog.getButton(whichButton);
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    @Override
    public View findViewById(int id) {
        if (mDialog != null) {
            return mDialog.findViewById(id);
        } else {
            throw new RuntimeException("Dialog no create !!! ");
        }
    }

    public static class Builder implements IBuilder {
        private AlertDialog.Builder mBuilder;

        public Builder(Context context) {
            mBuilder = new AlertDialog.Builder(context);
        }

        @Override
        public IBuilder setTitle(int titleId) {
            mBuilder.setTitle(titleId);
            return this;
        }

        @Override
        public IBuilder setTitle(CharSequence title) {
            mBuilder.setTitle(title);
            return this;
        }

        @Override
        public IBuilder setCustomTitle(View customTitleView) {
            mBuilder.setCustomTitle(customTitleView);
            return this;
        }

        @Override
        public IBuilder setMessage(int messageId) {
            mBuilder.setMessage(messageId);
            return this;
        }

        @Override
        public IBuilder setMessage(String message) {
            mBuilder.setMessage(message);
            return this;
        }

        @Override
        public IBuilder setIcon(int iconId) {
            mBuilder.setIcon(iconId);
            return this;
        }

        @Override
        public IBuilder setIcon(Drawable icon) {
            mBuilder.setIcon(icon);
            return this;
        }

        @Override
        public IBuilder setView(int layoutResId) {
            // API 21 之后可用
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                mBuilder.setView(layoutResId);
//            }
            return this;
        }

        @Override
        public IBuilder setView(View view) {
//            if (Utils.isEmui50()) {//5.0要设置padding
//                view.setPadding(CommonUtility.dip2px(16), CommonUtility.dip2px(16), CommonUtility.dip2px(16), 0);
//            } else if (!Utils.isEmui30()) {//3.1和4.0也要设置padding
//                view.setPadding(CommonUtility.dip2px(15), 0, CommonUtility.dip2px(15), 0);
//            }
            mBuilder.setView(view);
            return this;
        }

        @Override
        public IBuilder setDeleteDialogOption(int option) {
//            mBuilder.setDeleteDialogOption(option);
            return this;
        }

        @Override
        public IBuilder setPositiveButton(int textId, OnClickListener listener) {
            mBuilder.setPositiveButton(textId, listener);
            return this;
        }

        @Override
        public IBuilder setPositiveButton(CharSequence text, OnClickListener listener) {
            mBuilder.setPositiveButton(text, listener);
            return this;
        }

        @Override
        public IBuilder setNegativeButton(int textId, OnClickListener listener) {
            mBuilder.setNegativeButton(textId, listener);
            return this;
        }

        @Override
        public IBuilder setNegativeButton(CharSequence text, OnClickListener listener) {
            mBuilder.setNegativeButton(text, listener);
            return this;
        }

        @Override
        public IBuilder setOnCancelListener(OnCancelListener listener) {
            mBuilder.setOnCancelListener(listener);
            return this;
        }

        @Override
        public IBuilder setAdapter(ListAdapter adapter, OnClickListener listener) {
            mBuilder.setAdapter(adapter, listener);
            return this;
        }

        @Override
        public IBuilder setCancelable(boolean cancelable) {
            mBuilder.setCancelable(cancelable);
            return this;
        }

        @Override
        public IBuilder setItems(CharSequence[] items, final OnClickListener listener) {
            mBuilder.setItems(items, listener);
            return this;
        }

        @Override
        public IBuilder setItems(int itemsId, final OnClickListener listener) {
            mBuilder.setItems(itemsId, listener);
            return this;
        }

        public IBuilder setSingleChoiceItems(CharSequence[] items, int checkedItem, final OnClickListener listener) {
            mBuilder.setSingleChoiceItems(items, checkedItem, listener);
            return this;
        }

        @Override
        public IAlertDialog create() {
            return new ReaderAlertDialog(mBuilder.create());
        }
    }
}
