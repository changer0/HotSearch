package com.lulu.hotsearch.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.ImageView
import android.widget.TextView
import com.lulu.hotsearch.Constant
import com.lulu.hotsearch.wb.R
import com.qq.reader.activity.ReaderBaseActivity

class WebActivity : ReaderBaseActivity() {

    private lateinit var webView: WebView
    public lateinit var leftImage: ImageView
    public lateinit var rightImage: ImageView
    public lateinit var actionBarTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        val url = intent.getStringExtra(Constant.WEB_URL)
        initView()
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

    private fun initView() {
        webView = findViewById(R.id.webView)
        leftImage = findViewById(R.id.leftImage)
        rightImage = findViewById(R.id.rightImage)
        actionBarTitle = findViewById(R.id.profile_header_title)
        setLeftImage(intent.getStringExtra(Constant.HOT_SEARCH_TYPE))
        actionBarTitle.text = intent.getStringExtra(Constant.WEB_TITLE)
        rightImage.visibility = View.VISIBLE
        rightImage.setOnClickListener { finish() }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }


    public fun setLeftImage(type: String) {
        when(type) {
            Constant.HOT_SEARCH_WB -> leftImage.setImageResource(R.drawable.sina_wb)
            Constant.HOT_SEARCH_DOUYIN -> leftImage.setImageResource(R.drawable.douyin)
        }
    }

}