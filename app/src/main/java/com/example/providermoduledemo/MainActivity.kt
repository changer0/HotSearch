package com.example.providermoduledemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.providermoduledemo.sample.SampleActivity
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
            startActivity(Intent(this, SampleActivity::class.java))
        }
    }
}

