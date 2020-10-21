package com.example.providermoduledemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.example.providermoduledemo.listdemo.ListActivity
import com.qq.reader.provider.loader.DataProviderLoader
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val provider = MyDataProvider(MyRequestDataBean())
        DataProviderLoader.getInstance().loadData(provider)

        removeCacheBtn.setOnClickListener {
            provider.removeCache()
        }

        reloadData.setOnClickListener {
            DataProviderLoader.getInstance().loadData(provider)
            content.text = "加载中"
        }

        modifyCacheModel.setOnClickListener {
            content.text = "加载中"
            var cacheMode = provider.cacheMode + 1
            if (cacheMode > 3) {
                cacheMode = 0;
            }
            provider.cacheMode = cacheMode
            DataProviderLoader.getInstance().loadData(provider)
        }
        listActivity.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }

        provider.liveData.observe(this, Observer {
            if (it.isSuccess) {
                urlTv.text = it.provider.url
                cacheModeTv.text = it.provider.cacheMode.toString()
                isCacheTv.text = it.provider.isCache.toString()
                val jsonObj = JSONObject(it.provider.jsonStr)
                content.text = jsonObj.toString(4)
                Log.d(TAG, "接受数据：${it.provider.jsonStr}")
            } else {
                urlTv.text = it.provider.url
                cacheModeTv.text = it.provider.cacheMode.toString()
                isCacheTv.text = it.provider.isCache.toString()
                content.text = "加载失败！"
                Log.d(TAG, "数据异常")
            }
        })
    }
}

