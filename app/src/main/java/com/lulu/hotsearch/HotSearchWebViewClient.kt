package com.lulu.hotsearch

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.lulu.basic.utils.AssetsUtil
import com.lulu.hotsearch.bean.FilterRule
import com.yuewen.reader.zebra.utils.GSONUtil
import com.yuewen.reader.zebra.utils.MD5Utils
import java.lang.StringBuilder
import java.net.URLEncoder

private const val TAG = "HotSearchWebViewClient"
private const val AD_RULES = "local_ad_filter_rules.json"
class HotSearchWebViewClient(private val activity: Activity): WebViewClient() {
    private val assetsFileToString: String = AssetsUtil.getAssetsFileToString(AD_RULES)
    private val filterRules = GSONUtil.parseJsonToList<FilterRule>(assetsFileToString, FilterRule::class.java)

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
        val uri: Uri = request.url
         if (TextUtils.equals(uri.scheme,"weixin")) {
            activity.startActivity(Intent(Intent.ACTION_VIEW, uri))
        } else if (TextUtils.equals(uri.scheme, "zhihu") || TextUtils.equals(uri.host, "www.zhihu.com")) {
            //https://blog.csdn.net/qq_41188773/article/details/89669354?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.control
            //当返回false，表示不进行阻止，webview认为当前的url需要进行处理，会继续加载；返回 true，表示阻止webview继续加载url，等待我们进行处理
            return true
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

    override fun onPageFinished(view: WebView, url: String) {

        Log.d(TAG, "onPageFinished: url: $url")
        //https://blog.csdn.net/niubitianping/article/details/51212541
        for (filterRule in filterRules) {
            if (url.contains(filterRule.filter)) {
                val invokeMethod = MD5Utils.getMD5ByStr(filterRule.filter)
                val invokeRules = StringBuilder()
                for (rule in filterRule.rules) {
                    invokeRules.append(rule).append("; ")
                }
                val filterUrl = "javascript:" +
                        "function $invokeMethod() { $invokeRules}\n" +
                        "$invokeMethod();"
                view.loadUrl(filterUrl)
                Log.d(TAG, "onPageFinished: $filterUrl")
            }

        }
        super.onPageFinished(view, url)
    }
}