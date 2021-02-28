package com.lulu.hotsearch.share;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

public class GoShareBuilder {

    private Context context;

    @ShareContentType
    private String contentType;

    private Uri fileUri;

    private String title = "分享";

    private String text = "分享内容";

    public GoShareBuilder (Context context) {
        this.context = context;
    }

    public GoShareBuilder setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public GoShareBuilder setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
        return this;
    }

    public GoShareBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public GoShareBuilder setText(String text) {
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
