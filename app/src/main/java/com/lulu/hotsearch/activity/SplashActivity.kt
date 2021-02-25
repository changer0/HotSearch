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
    private val handler = Handler(Looper.getMainLooper())
    private var launchTime = 0L
    private var isLaunch = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(true)
        setContentView(R.layout.activity_main)
        isLaunch = false
        text.text = getString(R.string.splash_text)
        launchTime = System.currentTimeMillis()
        CoroutineScopeManager.getScope(this).launch {
            val rule = async {requestFilterRule()}
            val configInfo = async { requestConfigInfo() }

            //如果2s未启动 且有本地数据直接启动
            handler.postDelayed({
                if (HotSearchConfigManager.hasData()) {
                    if (!isLaunch) {
                        Log.i(TAG, "onCreate: 启动超时,直接使用本地数据加载")
                    }
                    launchHotSearch()
                }
            }, 2000)
            //并发请求
            rule.await()
            configInfo.await()
            //初始信息请求完毕，启动
            launchHotSearch()
            Log.i(TAG, "onCreate: 启动耗时: ${(System.currentTimeMillis() - launchTime)/1000f}s")
        }


    }

    override fun adapterStatus() {
        //
    }

    private fun launchHotSearch() {
        if (isLaunch) {
            return
        }
        isLaunch = true
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