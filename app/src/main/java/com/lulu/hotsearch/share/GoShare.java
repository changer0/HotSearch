package com.lulu.hotsearch.share;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

public class GoShare {

    public static final int REQUEST_SHARE_CODE = 100;

    private Activity context;

    @ShareContentType
    private String contentType;

    private Uri fileUri;

    private String title = "分享";

    private String text = "分享内容";

    public GoShare(Activity context) {
        this.context = context;
    }

    public GoShare setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public GoShare setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
        return this;
    }

    public GoShare setTitle(String title) {
        this.title = title;
        return this;
    }

    public GoShare setText(String text) {
        this.text = text;
        return this;
    }

    public void goShare() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType(contentType);
        if (TextUtils.equals(contentType, ShareContentType.TEXT)) {
            intent.putExtra(Intent.EXTRA_TEXT, text);
        } else {
            intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        }
        context.startActivity(Intent.createChooser(intent, title));
    }
}
