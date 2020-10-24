package com.example.providermoduledemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.example.providermoduledemo.listdemo.ListActivity
import com.example.providermoduledemo.pagelist.ReaderBaseListProviderActivity
import com.example.providermoduledemo.viewmodel.ViewModelActivity
import com.qq.reader.provider.loader.DataProviderLoader
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listActivity.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }
        viewModelActivity.setOnClickListener {
            startActivity(Intent(this, ViewModelActivity::class.java))
        }
    }

}

