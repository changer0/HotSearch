package com.lulu.hotsearch.activity

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.WebSettings
import android.widget.ImageView
import android.widget.TextView
import com.lulu.baseutil.DrawableUtil
import com.lulu.basic.utils.ColorDrawableUtils
import com.lulu.basic.utils.ToastUtil
import com.lulu.hotsearch.Constant
import com.lulu.hotsearch.HotSearchRealUrlUtil
import com.lulu.hotsearch.HotSearchWebViewClient
import com.lulu.hotsearch.bean.HotSearchBean
import com.lulu.hotsearch.view.HotSearchWebView
import com.lulu.hotsearch.wb.R
import com.qq.reader.activity.ReaderBaseActivity

class WebActivity : ReaderBaseActivity() {

    private lateinit var webView: HotSearchWebView
    public lateinit var leftImage: ImageView
    public lateinit var rightImage: ImageView
    public lateinit var actionBarTitle: TextView
    private var hotSearchBean: HotSearchBean? = null
    private var curIndex = 0
    private var curOrder = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        val url = intent.getStringExtra(Constant.WEB_URL)
        parseIntent()
        initView()
        webView.loadUrl(url)
        webView.webViewClient = HotSearchWebViewClient(this)
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

    private fun parseIntent() {
        curOrder = intent.getStringExtra(Constant.WEB_CUR_ORDER)
        hotSearchBean = intent.getSerializableExtra(Constant.WEB_HOT_SEARCH_DATA) as HotSearchBean?
        hotSearchBean?.let {
            for (i in 0 until it.result.size) {
                if (TextUtils.equals(curOrder, it.result[i].order)) {
                    curIndex = i
                    break
                }
            }
        }
    }

    private fun initView() {
        webView = findViewById(R.id.webView)
        leftImage = findViewById(R.id.leftImage)
        rightImage = findViewById(R.id.rightImage)
        actionBarTitle = findViewById(R.id.profile_header_title)

        setLeftImage(hotSearchBean?.type?:Constant.HOT_SEARCH_WB)
        actionBarTitle.text = intent.getStringExtra(Constant.WEB_TITLE)
        rightImage.visibility = View.VISIBLE
        rightImage.setImageDrawable( DrawableUtil.tintDrawable(rightImage.drawable, Color.parseColor("#FF000000")))
        rightImage.setOnClickListener { finish() }

        hotSearchBean?.let {
            webView.setOnSwitchListener(object : HotSearchWebView.OnSwitchListener {
                override fun onPre() {
                    curIndex--
                    if (curIndex < 0) {
                        ToastUtil.showShortToast("已经是第一个了")
                        curIndex++
                        return
                    }
                    switchContent()
                }

                override fun onNext() {
                    curIndex++
                    if (curIndex >= hotSearchBean?.result?.size?:-1) {
                        ToastUtil.showShortToast("已经是最后一个了")
                        curIndex--
                        return
                    }
                    switchContent()
                }

            })

        }
    }

    /**
     * 滑动切换内容
     */
    private fun switchContent() {
        val url = hotSearchBean?.result?.get(curIndex)?.url ?: return
        actionBarTitle.text = hotSearchBean?.result?.get(curIndex)?.title
        if (TextUtils.equals(hotSearchBean?.type, Constant.HOT_SEARCH_WB)) {
            webView.loadUrl(url)
        } else {
            HotSearchRealUrlUtil.parseReadUrl(this@WebActivity, url) { realUrl ->
                if (TextUtils.isEmpty(realUrl)) {
                    ToastUtil.showShortToast("跳转失败")
                } else {
                    webView.loadUrl(realUrl)
                }
            }
        }

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
            Constant.HOT_SEARCH_ZHIHU -> leftImage.setImageResource(R.drawable.zhihu)
        }
    }

}