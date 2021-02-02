package com.example.providermoduledemo


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.providermoduledemo.sample.*
import com.qq.reader.bookstore.BaseBookStoreViewModel
import com.qq.reader.bookstore.BookStoreActivityLauncher
import com.qq.reader.bookstore.LaunchParams
import com.qq.reader.bookstore.define.LoadSignal
import com.qq.reader.zebra.Zebra
import com.qq.reader.zebra.ZebraLiveData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentCommonSecondPageFragment.setOnClickListener {
            //启动通用二级页
            BookStoreActivityLauncher.launchCommon(
                this,
                TestViewModel::class.java,
                LaunchParams.Builder().build()
            )

        }
    }

    class TestViewModel: BaseBookStoreViewModel() {


        override fun getZebraLiveData(params: Bundle?): ZebraLiveData {

            return Zebra.with(SampleResultBean::class.java)
                .parser(SampleConvertParser())
                .load(LoadSignal.LOAD_SIGNAL_INIT)

        }

    }

}

