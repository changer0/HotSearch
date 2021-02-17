package com.lulu.hotsearch

import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.lulu.hotsearch.activity.WebActivity
import java.lang.StringBuilder

private const val TAG = "HotSearchWebViewClient"
private const val AD_RULES = "auto_invoke_rules.json"
class HotSearchWebViewClient(private val activity: WebActivity): WebViewClient() {
    private val filterRules = FilterRuleManager.getFilterRule()

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
        val uri: Uri = request.url
         if (TextUtils.equals(uri.scheme,"weixin")) {
            activity.startActivity(Intent(Intent.ACTION_VIEW, uri))
             return true
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
        view.postDelayed({
            filterRule(url, view)
            activity.loadFinish()
        }, 500)
        super.onPageFinished(view, url)
    }

    private fun filterRule(url: String, view: WebView) {
        //https://blog.csdn.net/niubitianping/article/details/51212541
        var methodIndex = 0
        for (filterRule in filterRules) {
            if (url.contains(filterRule.filter)) {
                val invokeMethod = "filterRule${methodIndex++}"
                val invokeRules = StringBuilder()
                for (rule in filterRule.rules) {
                    invokeRules.append(rule).append("; ")
                }
                val filterUrl = "javascript:" +
                        "function $invokeMethod() { \n$invokeRules}\n" +
                        "$invokeMethod();"
                view.loadUrl(filterUrl)
                Log.d(TAG, "onPageFinished: $filterUrl")
            }

        }
    }
}