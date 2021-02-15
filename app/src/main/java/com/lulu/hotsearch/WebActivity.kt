package com.lulu.hotsearch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.*
import android.widget.TextView
import com.lulu.hotsearch.wb.R
import com.qq.reader.activity.ReaderBaseActivity

class WebActivity : ReaderBaseActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        val url = intent.getStringExtra(Constant.WEB_URL)
        webView = findViewById(R.id.webView)
        findViewById<TextView>(R.id.profile_header_title).text = intent.getStringExtra(
            Constant.WEB_TITLE)
        webView.loadUrl(url)
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val uri: Uri = Uri.parse(url)
                if (uri.scheme == "weixin") {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
                return super.shouldOverrideUrlLoading(webView, request)
            }
        }
        val webSettings = webView.settings
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript

        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setBuiltInZoomControls(true); // 设置内置的缩放控制 手势 + 缩放控件
        webSettings.setDisplayZoomControls(false); // 不显示缩放控件
        webSettings.setSaveFormData(false);
        webSettings.setUseWideViewPort(true); // 支持html设置viewport
        webSettings.setLoadWithOverviewMode(true); // body宽度超出自动缩放
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowFileAccess(true); // 支持以 file:/// 打开本地文件,file:///android_asset 是默认被允许的
        webSettings.setAllowFileAccessFromFileURLs(false); // 本地文件能否通过ajax访问别的本地文件
        webSettings.setAllowUniversalAccessFromFileURLs(true); // 本地文件能否通过ajax跨域访问http/https
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); // 允许https中加载http


    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}