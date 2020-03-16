package com.qq.reader.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by liuxiaoyang on 2017/5/3.
 * <p>
 * 解耦ColorLoadingView
 * oppo分支使用ColorLoadingView
 * vivo分支使用ProgressBar
 * huawei分支用ProgressBar
 * <p>
 * 在分支代码中使用BaseCooprateLoading
 * TODO 命名
 */

public class CooperateLoadingView extends BaseCooperateLoading {

    public CooperateLoadingView(Context context) {
        super(context, (AttributeSet) null);
    }

    public CooperateLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CooperateLoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
