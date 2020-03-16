package com.qq.reader.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qq.reader.baseui.R;
import com.qq.reader.widget.CooperateLoadingView;

public class ReaderProgressDialog implements IProgressDialog{


    private final TextView progressMsg;
    private final CooperateLoadingView progressBar;
    private final IAlertDialog progressDialog;

    public ReaderProgressDialog(Activity context) {
        ReaderAlertDialog.Builder progressDialogBuilder = new ReaderAlertDialog.Builder(context);
        View progressView = context.getLayoutInflater().inflate(R.layout.reader_progress_dialog, null, false);
        progressMsg = ((TextView) progressView.findViewById(R.id.progress_msg));
        progressBar = ((CooperateLoadingView) progressView.findViewById(R.id.progress_bar));
        progressDialogBuilder.setView(progressView);
        progressDialog = progressDialogBuilder.create();
    }

    public void setMSG(String message) {
        progressMsg.setText(message);
    }

    @Override
    public void show() {
        progressDialog.show();
    }

    @Override
    public void setCancelable(boolean isCancelable) {
        progressDialog.setCancelable(isCancelable);
    }

    @Override
    public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        progressDialog.setOnCancelListener(onCancelListener);
    }

    @Override
    public boolean isShowing() {
        return progressDialog.isShowing();
    }


    @Override
    public void dismiss() {
        progressDialog.dismiss();
    }

    @Override
    public void cancel() {
        progressDialog.cancel();
    }

    @Override
    public void setCanceledOnTouchOutside(boolean b) {
        progressDialog.setCanceledOnTouchOutside(b);
    }

    @Override
    public void setOnKeyListener(DialogInterface.OnKeyListener listener) {
        progressDialog.setOnKeyListener(listener);
    }
}
