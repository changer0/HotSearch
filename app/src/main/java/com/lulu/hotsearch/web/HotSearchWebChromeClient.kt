package com.lulu.hotsearch.web

import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar
import com.lulu.hotsearch.R
import com.lulu.hotsearch.activity.WebActivity

/**
 * WebChromeClient
 */
private const val TAG = "HotSearchWebChromeClien"
class HotSearchWebChromeClient(private val webActivity: WebActivity) : WebChromeClient() {


    private val webViewProgress = webActivity.findViewById<ProgressBar>(R.id.webViewProgress)

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        Log.d(TAG, "onProgressChanged: newProgress: $newProgress")
        if (newProgress >= 100) {
            webViewProgress.visibility = View.GONE
        } else {
            webViewProgress.progress = newProgress
            webViewProgress.visibility = View.VISIBLE
        }
    }
}