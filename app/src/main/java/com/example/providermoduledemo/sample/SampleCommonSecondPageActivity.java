package com.example.providermoduledemo.sample;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.providermoduledemo.R;
import com.example.providermoduledemo.build.BookStoreConstants;
import com.yuewen.dataprovider.page.IPage;
import com.yuewen.dataprovider.page.PageManger;


/**
 * 通用二级页 示例页面
 */
public class SampleCommonSecondPageActivity extends AppCompatActivity {

    private Fragment mHoldFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_fragment_layout);
        try {
            Intent intent = getIntent();
            if (intent == null) throw new NullPointerException("CommonSecondPageActivity intent 不可为空");
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mHoldFragment = (Fragment) fm.findFragmentByTag("fragment");
            if (mHoldFragment == null){
                //如果不为空则为进程被杀死后恢复的，由Fragment自行恢复
                mHoldFragment = analyzePageFragment(intent.getExtras());
                ft.add(R.id.fragment_content, mHoldFragment, "fragment");
                ft.commitAllowingStateLoss();
            }
            mHoldFragment.setArguments(intent.getExtras());
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }


    private Fragment analyzePageFragment(Bundle extras) throws Exception {
        String pageType = extras.getString(BookStoreConstants.PAGE_BUILDER_TYPE);
        //IPage page = PageManger.getPage(pageType);
        //Class<? extends Fragment> fragment = page.getFragment();
        //return fragment.newInstance();
        return null;
    }
}
