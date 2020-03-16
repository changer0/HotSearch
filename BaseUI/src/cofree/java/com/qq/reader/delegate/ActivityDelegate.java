
package com.qq.reader.delegate;

import android.app.Activity;
import android.view.MenuItem;
import android.view.WindowManager;

import com.qq.reader.activity.BranchBaseActivity;
import com.qq.reader.widget.IActionBar;


public class ActivityDelegate {

    private final Activity mActivity;
    private final ActivityConfig mActivityConfig;

    public ActivityDelegate(Activity activity) {
        mActivity = activity;
        mActivityConfig = (ActivityConfig) mActivity;
        if (mActivityConfig.getStatusType() == ActivityConfig.STATUS_TRANSLUCENT) {
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void onCreate(BranchBaseActivity delegate) {
        IActionBar actionBar = delegate.getReaderActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(mActivityConfig.isHomeAsUpEnabled());
        }
//        if (mActivityConfig.isTitleNeedUpdate()) {
//            TitleDelegate.setTitle(mActivity);
//        }
    }

//    public void onContentChanged() {
//        SystemUiDelegate.setStatusBarTint(mActivity, mActivityConfig.getStatusType());
//    }

    public void onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
//        case R.id.action_cancel:
            mActivity.finish();
            break;
        default:
//            if (mActivityConfig.isShowMenuDescription()) {
//                MenuDelegate.showDescription(mActivity, item);
//            }
            break;
        }
    }
}
