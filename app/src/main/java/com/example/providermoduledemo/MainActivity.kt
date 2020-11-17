package com.example.providermoduledemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.providermoduledemo.preload.PreLoadLiveDataManager
import com.example.providermoduledemo.sample.SampleActivity
import com.example.providermoduledemo.sample.SampleListPageActivity
import com.example.providermoduledemo.sample.SampleResponseBean
import com.example.providermoduledemo.sample.SampleViewBindItemBuilder
import com.qq.reader.provider.DataProvider
import com.qq.reader.provider.loader.ObserverEntity
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModelActivity.setOnClickListener {
            startActivity(Intent(this, SampleActivity::class.java))
        }
        preLoadActivity.setOnClickListener {
            val liveData = DataProvider.with(SampleResponseBean::class.java)
                .url(String.format(SampleActivity.SERVER_URL, 1))
                .viewBindItemBuilder(SampleViewBindItemBuilder())
                .load()
            PreLoadLiveDataManager.savePreLoadLiveData("bid", liveData)
            startActivity(Intent(this, SampleActivity::class.java))
        }
        sampleListPageActivity.setOnClickListener {
            startActivity(Intent(this, SampleListPageActivity::class.java))
        }
    }
}

