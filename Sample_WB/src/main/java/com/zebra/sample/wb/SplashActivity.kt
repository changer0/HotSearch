package com.zebra.sample.wb

import android.app.ActivityOptions
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.qq.reader.activity.ReaderBaseActivity
import com.qq.reader.bookstore.BookStoreActivityLauncher
import com.qq.reader.bookstore.LaunchParams


class SplashActivity : ReaderBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed({
            BookStoreActivityLauncher.launch(this, Constant.WB_HOT_SEARCH,
                LaunchParams.Builder()
                    .setTitle("微博热搜榜")
                    .setPullRefreshEnable(true)
                    .build(),
                //转场动画 https://blog.csdn.net/w630886916/article/details/78319502
                ActivityOptions.makeSceneTransitionAnimation(this, findViewById(R.id.img), "weibo")
                    .toBundle())
            finish()
        }, 500)

    }
}