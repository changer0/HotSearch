package com.lulu.baseutil.bezelless.samsung;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.view.WindowInsets;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lichenyang on 2019/1/8.
 */

public class DisplayCutoutWrapper {

    private static final String GET_DISPLAY_CUTOUT = "getDisplayCutout";
    private static final String GET_SAFE_INSET_TOP = "getSafeInsetTop";
    private static final String GET_SAFE_INSET_BOTTOM = "getSafeInsetBottom";
    private static final String GET_SAFE_INSET_LEFT = "getSafeInsetLeft";
    private static final String GET_SAFE_INSET_RIGHT = "getSafeInsetRight";
    private static final String GET_BOUNDING_RECTS = "getBoundingRects";
    private final Rect mSafeInsets = new Rect();
    private final List<Rect> mBoundingRects = new ArrayList<Rect>();

    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    public DisplayCutoutWrapper(WindowInsets windowInsets) {
        try {
            Method method = WindowInsets.class.getDeclaredMethod
                    (GET_DISPLAY_CUTOUT);
            Object displayCutoutInstance = method.invoke(windowInsets);
            Class cls = displayCutoutInstance.getClass();
            int top = (Integer) cls.getDeclaredMethod(GET_SAFE_INSET_TOP).invoke
                    (displayCutoutInstance);
            int bottom = (Integer) cls.getDeclaredMethod(GET_SAFE_INSET_BOTTOM).
                    invoke(displayCutoutInstance);
            int left = (Integer) cls.getDeclaredMethod(GET_SAFE_INSET_LEFT).
                    invoke(displayCutoutInstance);
            int right = (Integer) cls.getDeclaredMethod(GET_SAFE_INSET_RIGHT).
                    invoke(displayCutoutInstance);
            mSafeInsets.set(left, top, right, bottom);
            mBoundingRects.addAll((List<Rect>) cls.getDeclaredMethod
                    (GET_BOUNDING_RECTS).invoke(displayCutoutInstance));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the inset from the top which avoids the display cutout in
     * pixels.
     */
    public int getSafeInsetTop() {
        return mSafeInsets.top;
    }

    /**
     * Returns the inset from the bottom which avoids the display cutout
     * in pixels.
     */
    public int getSafeInsetBottom() {
        return mSafeInsets.bottom;
    }

    /**
     * Returns the inset from the left which avoids the display cutout in
     * pixels.
     */
    public int getSafeInsetLeft() {
        return mSafeInsets.left;
    }

    /**
     * Returns the inset from the right which avoids the display cutout
     * in pixels.
     */
    public int getSafeInsetRight() {
        return mSafeInsets.right;
    }

    /**
     * Returns a list of {@code Rect}s, each of which is the bounding
     * rectangle for a non-functional
     * area on the display.
     * <p>
     * There will be at most one non-functional area per short edge of the
     * device, and none on
     * the long edges.
     *
     * @return a list of bounding {@code Rect}s, one for each display cutout
     * area.
     */
    public List<Rect> getBoundingRects() {
        return mBoundingRects;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append("DisplayCutout{");
        sb.append("safeInsets=" + mSafeInsets);
        sb.append(", boundingRect=");
        if (mBoundingRects.isEmpty()) {
            sb.append("Empty");
        }
        for (int i = 0; i < mBoundingRects.size(); ++i) {
            sb.append(mBoundingRects.get(i));
        }
        sb.append("}");
        return sb.toString();
    }

}
