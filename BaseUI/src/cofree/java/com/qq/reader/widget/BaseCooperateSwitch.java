package com.qq.reader.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Switch;

import com.qq.reader.baseui.R;

/**
 * @author zhanglulu on 2018/11/6.
 * for
 */
public class BaseCooperateSwitch extends Switch {
    public BaseCooperateSwitch(Context context) {
        super(context);
        initSwitch(context);
    }

    public BaseCooperateSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSwitch(context);
    }

    public BaseCooperateSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSwitch(context);
    }

    /**
     * 初始化
     * @param context
     */
    private void initSwitch(Context context) {
        setThumbDrawable(context.getResources().getDrawable(R.drawable.switch_thumb));//设置Switch中的圆
        setTrackDrawable(context.getResources().getDrawable(R.drawable.switch_track));//设置底部样式
    }


}
