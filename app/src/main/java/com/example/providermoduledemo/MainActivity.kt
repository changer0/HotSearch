package com.example.providermoduledemo


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.providermoduledemo.sample.*
import com.yuewen.reader.bookstore.BookStoreActivityLauncher
import com.yuewen.reader.bookstore.LaunchParams
import com.yuewen.reader.bookstore.define.BookStoreConstant

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentCommonSecondPageFragment.setOnClickListener {
            //启动通用二级页
            BookStoreActivityLauncher.launch(
                this,
                BookStoreConstant.BOOK_STORE_COMMON_FRAGMENT,
                LaunchParams.Builder()
                    .setPullRefreshEnable(true)
                    .setLoadMoreEnable(true)
                    .setViewModelClass(SampleBookStoreViewModel::class.java)
                    .setTitle("通用页面示例")
                    .build()
            )

        }
    }


}

