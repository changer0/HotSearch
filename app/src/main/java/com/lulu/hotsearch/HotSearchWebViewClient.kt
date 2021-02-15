package com.lulu.hotsearch

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class HotSearchWebViewClient(private val activity: Activity): WebViewClient() {


    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
        val uri: Uri = request.url
        if (uri.scheme == "weixin") {
            activity.startActivity(Intent(Intent.ACTION_VIEW, uri))
        }
        if (TextUtils.equals("www.iesdouyin.com", uri.host)
            || TextUtils.equals("snssdk1128", uri.scheme)) {
            val intent = Intent()
            intent.action = "android.intent.action.VIEW"
            intent.data = uri
            activity.startActivity(intent)
            return true
        }
        //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
        //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
        return super.shouldOverrideUrlLoading(view, request)
    }
}