package com.example.providermoduledemo


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.providermoduledemo.sample.*
import com.qq.reader.bookstore.BookStoreActivityLauncher
import com.qq.reader.bookstore.LaunchParams

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentCommonSecondPageFragment.setOnClickListener {
            //启动通用二级页
            BookStoreActivityLauncher.launchCommon(
                this,
                SampleBookStoreViewModel::class.java,
                LaunchParams.Builder()
                    .setPullRefreshEnable(true)
                    .setTitle("测试通用页面")
                    .build()
            )

        }
    }


}

