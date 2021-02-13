package com.zebra.sample.wb

import android.os.Bundle
import com.qq.reader.activity.ReaderBaseActivity
import com.qq.reader.bookstore.BookStoreActivityLauncher
import com.qq.reader.bookstore.LaunchParams
import com.qq.reader.bookstore.define.BookStoreConstant

class MainActivity : ReaderBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BookStoreActivityLauncher.launch(this, BookStoreConstant.BOOK_STORE_COMMON_FRAGMENT,
            LaunchParams.Builder()
                .setTitle("微博热搜榜")
                .setViewModelClass(WBViewModel::class.java)
                .setPullRefreshEnable(true)
                .build())
        finish()
    }
}