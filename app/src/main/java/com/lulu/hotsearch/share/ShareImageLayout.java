package com.lulu.hotsearch.share;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lulu.baseutil.bezelless.DensityUtil;
import com.lulu.hotsearch.R;

/**
 * @author zhanglulu on 2019/7/26.
 * for 用于分享生成图片的布局
 */
public class ShareImageLayout extends LinearLayout {
    private static int IMAGE_WIDTH;
    private static int IMAGE_HEIGHT;
    private ImageView imageView;

    public ShareImageLayout(Context context) {
        super(context);
        init(context);
    }

    public ShareImageLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShareImageLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.share_image_layout, this, true);
        imageView = (ImageView) inflate.findViewById(R.id.ivShare);
    }

    /**
     * 设置分享码
     */
    public void setImageByBitmap(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    /**
     * 生成图片
     *
     * @return
     */
    public Bitmap createImage() {
        IMAGE_WIDTH = DensityUtil.dip2px(375);
        IMAGE_HEIGHT = DensityUtil.dip2px(667);
        //由于直接new出来的view是不会走测量、布局、绘制的方法的，所以需要我们手动去调这些方法，不然生成的图片就是黑色的。

        int widthMeasureSpec = MeasureSpec.makeMeasureSpec(IMAGE_WIDTH, MeasureSpec.EXACTLY);
        int heightMeasureSpec = MeasureSpec.makeMeasureSpec(IMAGE_HEIGHT, MeasureSpec.EXACTLY);

        measure(widthMeasureSpec, heightMeasureSpec);
        layout(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        Bitmap bitmap = Bitmap.createBitmap(IMAGE_WIDTH, IMAGE_HEIGHT, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        draw(canvas);

        return bitmap;
    }
}
