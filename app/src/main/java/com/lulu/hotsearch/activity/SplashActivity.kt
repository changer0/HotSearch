package com.lulu.hotsearch.activity

import android.app.ActivityOptions
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.lulu.hotsearch.Constant
import com.lulu.hotsearch.HotSearchKVStorage
import com.lulu.hotsearch.wb.R
import com.qq.reader.activity.ReaderBaseActivity
import com.qq.reader.bookstore.BookStoreActivityLauncher
import com.qq.reader.bookstore.LaunchParams
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "SplashActivity"
class SplashActivity : ReaderBaseActivity() {
    val handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text.text = getString(R.string.splash_text)

        handler.postDelayed({
            val bundle = Bundle()
            bundle.putString(
                Constant.HOT_SEARCH_TYPE,
                HotSearchKVStorage.getLastType())
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
        }, 500)

    }

//    override fun onStop() {
//        super.onStop()
//        Logger.d(TAG, "onStop")
//    }
//
//    override fun onRestart() {
//        super.onRestart()
//        Logger.d(TAG, "onRestart")
//        finish()
//    }
}