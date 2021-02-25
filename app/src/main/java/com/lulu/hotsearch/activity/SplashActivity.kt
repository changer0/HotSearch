package com.lulu.hotsearch.activity

import android.app.ActivityOptions
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.lulu.basic.net.CoroutineScopeManager
import com.lulu.basic.net.HttpCoroutineUtils
import com.lulu.hotsearch.define.Constant
import com.lulu.hotsearch.manager.FilterRuleManager
import com.lulu.basic.define.ServerUrl
import com.lulu.hotsearch.manager.HotSearchConfigManager
import com.lulu.hotsearch.R
import com.lulu.basic.activity.BaseActivity
import com.qq.reader.bookstore.BookStoreActivityLauncher
import com.qq.reader.bookstore.LaunchParams
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

private const val TAG = "SplashActivity"
class SplashActivity : BaseActivity() {
    val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(true)
        setContentView(R.layout.activity_main)

        text.text = getString(R.string.splash_text)

        CoroutineScopeManager.getScope(this).launch {
            val rule = async {requestFilterRule()}
            val configInfo = async { requestConfigInfo() }
            //并发请求
            rule.await()
            configInfo.await()
            //初始信息请求完毕，启动
            launchHotSearch()
        }


    }

    override fun adapterStatus() {
        //
    }

    private fun launchHotSearch() {
        val bundle = Bundle()
        bundle.putString(
            Constant.HOT_SEARCH_TYPE,
            HotSearchConfigManager.getCurType())
        BookStoreActivityLauncher.launch(this,
            Constant.WB_HOT_SEARCH,
            LaunchParams.Builder()
                .setPullRefreshEnable(true)
                .setExtras(bundle)
                .build(),
            //转场动画 https://blog.csdn.net/w630886916/article/details/78319502
            ActivityOptions.makeSceneTransitionAnimation(this, findViewById(R.id.img), "anim_weibo_img")
                .toBundle())
        //跳转之后消失
        handler.postDelayed({finish()}, 2000)
    }

    /**
     * 请求过滤规则
     */
    private suspend fun requestFilterRule() {
        Log.d(TAG, "requestFilterRule: 正在请求过滤规则信息")
        val result =
            HttpCoroutineUtils.doRequestGet(ServerUrl.CONFIG_DOMAIN + "filter_rule.json")
        if (result.isSuccess) {
            Log.d(TAG, "requestFilterRule: 请求过滤规则信息：${result.jsonStr}")
            FilterRuleManager.saveFilterRule(result.jsonStr)
        }
    }
    /**
     * 请求配置信息
     */
    private suspend fun requestConfigInfo() {
        Log.d(TAG, "requestConfigInfo: 正在配置信息")
        val result =
            HttpCoroutineUtils.doRequestGet(ServerUrl.CONFIG_DOMAIN + "config.json")
        if (result.isSuccess) {
            Log.d(TAG, "requestConfigInfo: 请求配置信息：${result.jsonStr}")
            HotSearchConfigManager.saveConfigInfo(result.jsonStr)
        }
    }
}