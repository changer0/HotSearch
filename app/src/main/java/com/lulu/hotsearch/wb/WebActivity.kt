package com.lulu.hotsearch.wb

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.qq.reader.activity.ReaderBaseActivity

class WebActivity : ReaderBaseActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        val url = intent.getStringExtra(Constant.WEB_URL)
        webView = findViewById(R.id.webView)
        findViewById<TextView>(R.id.profile_header_title).text = intent.getStringExtra(Constant.WEB_TITLE)
        webView.loadUrl(url)
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val uri: Uri = Uri.parse(url)
                if (uri.scheme == "weixin") {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    return false
                }
                return super.shouldOverrideUrlLoading(webView, request)
            }
        }
        val webSettings = webView.settings
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript

        webSettings.javaScriptEnabled = true



    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}