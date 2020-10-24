package com.example.providermoduledemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.providermoduledemo.normallist.NormalListActivity
import com.example.providermoduledemo.viewmodel.ViewModelActivity
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
    }

}

