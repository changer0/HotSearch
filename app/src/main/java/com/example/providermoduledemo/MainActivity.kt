package com.example.providermoduledemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.providermoduledemo.build.PageBuilderTypes
import com.example.providermoduledemo.preload.PreLoadLiveDataManager
import com.example.providermoduledemo.sample.*
import com.qq.reader.provider.DataProvider
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sampleListPageActivity.setOnClickListener {
            startActivity(Intent(this, SampleListPageActivity::class.java))
        }

        boyCommonSecondPage.setOnClickListener {
            val intent = Intent(this, SampleCommonSecondPageActivity::class.java)
            intent.putExtra(SampleCommonSecondPageActivity.PROVIDER_BUILDER_TYPE, PageBuilderTypes.BOY_PAGE)
            startActivity(intent)
        }

        girlCommonSecondPage.setOnClickListener {
            val intent = Intent(this, SampleCommonSecondPageActivity::class.java)
            intent.putExtra(SampleCommonSecondPageActivity.PROVIDER_BUILDER_TYPE, PageBuilderTypes.GIRL_PAGE)
            startActivity(intent)
        }
    }
}

