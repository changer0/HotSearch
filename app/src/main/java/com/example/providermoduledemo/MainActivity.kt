package com.example.providermoduledemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.providermoduledemo.normallist.NormalListActivity
import com.example.providermoduledemo.preload.PreLoadActivity
import com.example.providermoduledemo.preload.PreLoadProviderCreator
import com.example.providermoduledemo.viewmodel.ViewModelActivity
import com.qq.reader.provider.cache.CacheMode
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listActivity.setOnClickListener {
            startActivity(Intent(this, NormalListActivity::class.java))
        }
        viewModelActivity.setOnClickListener {
            startActivity(Intent(this, ViewModelActivity::class.java))
        }
        preLoadActivity.setOnClickListener {
            PreLoadProviderCreator.get().provider.loadData()
            PreLoadProviderCreator.get().loader.cacheMode = CacheMode.CACHE_MODE_NOT_USE_CACHE
            startActivity(Intent(this, PreLoadActivity::class.java))
        }
    }

}

