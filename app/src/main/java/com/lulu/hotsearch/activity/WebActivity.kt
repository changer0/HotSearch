package com.lulu.hotsearch.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.webkit.WebSettings
import android.widget.ImageView
import android.widget.TextView
import com.lulu.basic.image.ImageUtils
import com.lulu.basic.utils.ToastUtil
import com.lulu.hotsearch.define.Constant
import com.lulu.hotsearch.utils.HotSearchRealUrlUtil
import com.lulu.hotsearch.bean.HotSearchBean
import com.lulu.hotsearch.manager.HotSearchConfigManager
import com.lulu.hotsearch.utils.FabAnimUtil
import com.lulu.hotsearch.view.HotSearchWebView
import com.lulu.basic.activity.BaseActivity
import com.lulu.hotsearch.*
import com.lulu.hotsearch.utils.ShareUtil
import com.lulu.hotsearch.web.HotSearchWebChromeClient
import com.lulu.hotsearch.web.HotSearchWebViewClient
import com.lulu.hotsearch.web.OnFinishLoadingListener
import com.lulu.hotsearch.web.OnStartLoadingListener

private const val TAG = "WebActivity"
class WebActivity : BaseActivity() {

    private lateinit var webView: HotSearchWebView
    lateinit var ivLeftImage: ImageView
    lateinit var ivRightImage: ImageView
    lateinit var ivPre: ImageView
    lateinit var ivNext: ImageView
    lateinit var ivRefreshBtn: ImageView
    lateinit var actionBarTitle: TextView
    lateinit var tvLoadMsg: TextView
    lateinit var ivOpen: ImageView
    private var needShowOpenButton = false
    private var hotSearchBean: HotSearchBean? = null
    private var curIndex = 0
    private var curOrder = "0"
    private var isLoading = true

    private lateinit var refreshAnim: Animation
    private lateinit var hotSearchWebViewClient: HotSearchWebViewClient

    private var curUrl = ""
    private var displayAnim: ObjectAnimator? = null
    private var lastIsIdle = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        val url = intent.getStringExtra(Constant.WEB_URL)
        if (TextUtils.isEmpty(url)) {
            finish()
            return
        }
        refreshAnim = AnimationUtils.loadAnimation(this, R.anim.refrsh_anim)
        parseIntent()
        initView()
        initWebView()
        loadUrl(url)
        refreshPreNextControl()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        hotSearchWebViewClient = HotSearchWebViewClient(this)
        webView.webViewClient = hotSearchWebViewClient
        webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            handleOpenBtn(Uri.parse(url))
            loadFinish()
        }
        webView.webChromeClient = HotSearchWebChromeClient(this)
        hotSearchWebViewClient.setOnFinishLoadingListener(object :
            OnFinishLoadingListener {
            override fun onFinishLoading(url: String?) {
                loadFinish()
            }

        })
        hotSearchWebViewClient.setOnStartLoadingListener(object :
            OnStartLoadingListener {
            override fun onStartLoading(url: String?) {
                startLoading(url?:"")
            }
        })
        webView.setOnStartLoadingListener(object :
            OnStartLoadingListener {
            override fun onStartLoading(url: String?) {
                startLoading(url?:"")
            }
        })

        webView.seOnScrollChangedListener(object : HotSearchWebView.OnScrollChangedListener {
            override fun onScrollStateChanged(isIdle: Boolean) {
                super.onScrollStateChanged(isIdle)
                Log.d(TAG, "onIdle: $isIdle")
                if (isIdle == lastIsIdle || needShowOpenButton.not()) return
                lastIsIdle = isIdle
                if (displayAnim?.isRunning == true) {
                    displayAnim?.cancel()
                }
                displayAnim = FabAnimUtil.startDisplayAnimForAlpha(ivOpen, isIdle)
            }
        })

        val webSettings = webView.settings
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.javaScriptEnabled = true;
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT;
        webSettings.javaScriptCanOpenWindowsAutomatically = true;
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.builtInZoomControls = true; // 设置内置的缩放控制 手势 + 缩放控件
        webSettings.displayZoomControls = false; // 不显示缩放控件
        webSettings.setSaveFormData(false);
        webSettings.useWideViewPort = true; // 支持html设置viewport
        webSettings.loadWithOverviewMode = true; // body宽度超出自动缩放
        webSettings.databaseEnabled = true;
        webSettings.domStorageEnabled = true;
        webSettings.setGeolocationEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.allowFileAccess = true; // 支持以 file:/// 打开本地文件,file:///android_asset 是默认被允许的
        webSettings.allowFileAccessFromFileURLs = false; // 本地文件能否通过ajax访问别的本地文件
        webSettings.allowUniversalAccessFromFileURLs = true; // 本地文件能否通过ajax跨域访问http/https
        webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW; // 允许https中加载http
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
        ivLeftImage = findViewById(R.id.leftImage)
        ivPre = findViewById(R.id.ivPre)
        ivNext = findViewById(R.id.ivNext)
        ivOpen = findViewById(R.id.ivOpen)
        ivRefreshBtn = findViewById(R.id.ivRefreshBtn)
        ivRightImage = findViewById(R.id.rightImage)
        tvLoadMsg = findViewById(R.id.tvLoadMsg)
        actionBarTitle = findViewById(R.id.profile_header_title)

        //setLeftImage(hotSearchBean?.type?: Constant.HOT_SEARCH_WB)
        actionBarTitle.text = intent.getStringExtra(Constant.WEB_TITLE)
        ivRightImage.visibility = View.VISIBLE
        val webViewContent = findViewById<View>(R.id.webViewContent)
        ivRightImage.setOnClickListener {
            ShareUtil.showShareSelectDialog(this, curUrl, webViewContent)
        }
        ivLeftImage.setOnClickListener { finish() }
        initRefreshBtn()
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
     * 刷新控制
     */
    private fun initRefreshBtn() {
        refreshAnim.interpolator = LinearInterpolator()
        ivRefreshBtn.setOnClickListener {
            if (isLoading) {
                return@setOnClickListener
            }
            loadUrl(curUrl)
        }
    }

    /**
     * 滑动切换内容
     */
    private fun switchContent() {
        val url = hotSearchBean?.result?.get(curIndex)?.url ?: return
        actionBarTitle.text = hotSearchBean?.result?.get(curIndex)?.title
        if (TextUtils.equals(hotSearchBean?.type, Constant.HOT_SEARCH_WB)) {
            loadUrl(url)
        } else {
            HotSearchRealUrlUtil.parseReadUrl(this@WebActivity, url) { realUrl ->
                if (TextUtils.isEmpty(realUrl)) {
                    ToastUtil.showShortToast("跳转失败")
                } else {
                    loadUrl(realUrl)
                }
            }
        }
    }

    /**
     * 刷新 前后 加载控制
     */
    private fun refreshPreNextControl() {
        ivPre.isEnabled = webView.canGoBack()
        ivNext.isEnabled = webView.canGoForward()
        ivPre.setOnClickListener {
            if (webView.canGoBack()) {
                webView.goBack()
                refreshPreNextControl()
            }
        }
        ivNext.setOnClickListener {
            if (webView.canGoForward()) {
                webView.goForward()
                refreshPreNextControl()
            }
        }
    }

    /**
     * 加载 URL
     */
    public fun loadUrl(url: String) {
        curUrl = url
        webView.loadUrl(url)
    }

    /**
     * 由 WebView 通知 loading
     */
    private fun startLoading(url: String) {
        isLoading = true
        ivRefreshBtn.startAnimation(refreshAnim)
        tvLoadMsg.setText(R.string.filter_msg)
        //showProgress(getString(R.string.filter_msg))
        refreshPreNextControl()
    }


    private fun setLeftImage(type: String) {
        val curConfigBean = HotSearchConfigManager.getCurConfigBean()
        ImageUtils.displayImage(this, curConfigBean.icon, ivLeftImage)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    public fun loadFinish() {
        isLoading = false
        ivRefreshBtn.clearAnimation()
        ivRefreshBtn.rotation = 0F
        tvLoadMsg.setText(R.string.load_finish)
        //hideProgress()
        refreshPreNextControl()
    }

    public fun handleOpenBtn(uri: Uri) {
        showOpenBtn()
        setOnClickOpenListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            } catch (e: Exception) {
                ToastUtil.showShortToast("对应应用可能未安装，请安装后重试")
            }
        }
    }

    private fun showOpenBtn() {
        if(ivOpen.visibility == View.VISIBLE) {
            return
        }
        needShowOpenButton = true
        ivOpen.visibility = View.VISIBLE
        tvLoadMsg.text = "点击右侧按钮打开应用"
        val set = AnimatorSet()
        set.playTogether(
            ObjectAnimator.ofFloat(ivOpen, "alpha", 0F, 1F)
            ,ObjectAnimator.ofFloat(ivOpen, "scaleX", 1F, 1.5F)
            ,ObjectAnimator.ofFloat(ivOpen, "scaleY", 1F, 1.5F)
        )
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                val set2 = AnimatorSet()
                set2.playTogether(
                    ObjectAnimator.ofFloat(ivOpen, "scaleX", 1.5F, 1F)
                    ,ObjectAnimator.ofFloat(ivOpen, "scaleY", 1.5F, 1F)
                )
                set2.start()
            }
        })
        set.start()
    }

    private fun setOnClickOpenListener(i: (v: View) -> Unit) {
        ivOpen.setOnClickListener(i)
    }

}