package com.example.providermoduledemo.sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.providermoduledemo.R;
import com.example.providermoduledemo.build.PageTypes;

/**
 * SampleFragmentActivity
 */
public class SampleFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_fragment);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragmentContainer, SampleCommonSecondPageFragment.newInstance(PageTypes.BOY_PAGE));
        ft.commitAllowingStateLoss();
    }
}