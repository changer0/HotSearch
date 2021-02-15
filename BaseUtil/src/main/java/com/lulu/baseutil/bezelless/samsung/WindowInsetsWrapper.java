package com.lulu.baseutil.bezelless.samsung;

import android.annotation.TargetApi;
import android.os.Build;
import androidx.annotation.Nullable;
import android.view.WindowInsets;

import java.lang.reflect.Method;

/**
 * Created by lichenyang on 2019/1/8.
 */

public class WindowInsetsWrapper {

    private static final String CONSUME_DISPLAY_CUTOUT =
            "consumeDisplayCutout";
    private WindowInsets mInner;
    private DisplayCutoutWrapper mDisplayCutoutWrapper;

    public WindowInsetsWrapper(WindowInsets windowInsets) {
        mInner = windowInsets;
        mDisplayCutoutWrapper = new DisplayCutoutWrapper(mInner);
    }

    /**
     * Returns the display cutout if there is one.
     *
     * @return the display cutout or null if there is none
     * @see DisplayCutout
     */
    @Nullable
    public DisplayCutoutWrapper getDisplayCutoutWrapper() {
        return mDisplayCutoutWrapper;
    }

    /**
     * Returns a copy of this WindowInsets with the cutout fully consumed.
     *
     * @return A modified copy of this WindowInsets
     */
    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    public WindowInsets consumeDisplayCutout() {
        WindowInsets insets = null;
        try {
            Method method = WindowInsets.class.getDeclaredMethod
                    (CONSUME_DISPLAY_CUTOUT);
            insets = (WindowInsets) method.invoke(mInner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return insets;
    }

}
