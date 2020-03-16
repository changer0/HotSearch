package com.qq.reader.widget.recyclerview.decoration;

import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qq.reader.core.BaseApplication;
import com.qq.reader.widget.recyclerview.base.BaseQuickAdapter;
import com.tencent.mars.xlog.Log;


/**
 * 应用于RecyclerView的GridLayoutManager，水平方向上固定间距大小，从而使条目宽度自适应。<br>
 * Grid中Item的宽度应设为MATCH_PARAENT
 */
public class GridAverageGapItemDecoration extends RecyclerView.ItemDecoration {
    private float gapHorizontalDp;
    private float gapVerticalDp;
    private float sectionEdgeHPaddingDp;
    private float sectionEdgeVPaddingDp;
    private int gapHSizePx = -1;
    private int gapVSizePx = -1;
    private int sectionEdgeHPaddingPx;
    private int eachItemHPaddingPx; //每个条目应该在水平方向上加的padding 总大小，即=paddingLeft+paddingRight
    private int sectionEdgeVPaddingPx;
    private Rect preRect = new Rect();

    /**
     * @param gapHorizontalDp       水平间距
     * @param gapVerticalDp         垂直间距
     * @param sectionEdgeHPaddingDp 左右两端的padding大小
     * @param sectionEdgeVPaddingDp 上下两端的padding大小
     */
    public GridAverageGapItemDecoration(float gapHorizontalDp, float gapVerticalDp, float sectionEdgeHPaddingDp, float sectionEdgeVPaddingDp) {
        this.gapHorizontalDp = gapHorizontalDp;
        this.gapVerticalDp = gapVerticalDp;
        this.sectionEdgeHPaddingDp = sectionEdgeHPaddingDp;
        this.sectionEdgeVPaddingDp = sectionEdgeVPaddingDp;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() instanceof GridLayoutManager && parent.getAdapter() instanceof BaseQuickAdapter) {
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            BaseQuickAdapter adapter = (BaseQuickAdapter) parent.getAdapter();

            int left;
            int right;
            int top;
            int bottom;
            if (adapter == null || layoutManager == null) {
                return;
            }

            int spanCount = layoutManager.getSpanCount();
            int position = parent.getChildAdapterPosition(view);


            if (adapter.getHeaderLayoutCount() != 0 && position <= adapter.getHeaderViewPosition()) {
                return;
            }

            if (adapter.getEmptyViewCount() != 0 && position <= adapter.getEmptyViewPosition()) {
                return;
            }

            if (gapHSizePx < 0 || gapVSizePx < 0) {
                transformGapDefinition(parent, spanCount);
            }
            top = gapVSizePx;
            bottom = 0;

            //下面的visualPos为单个Section内的视觉Pos
            int visualPos = position + 1 - adapter.getHeaderLayoutCount();
            if (visualPos % spanCount == 1) {
                //第一列
                left = sectionEdgeHPaddingPx;
                right = eachItemHPaddingPx - sectionEdgeHPaddingPx;
            } else if (visualPos % spanCount == 0) {
                //最后一列
                left = eachItemHPaddingPx - sectionEdgeHPaddingPx;
                right = sectionEdgeHPaddingPx;
            } else {
                left = (visualPos % spanCount - 1) * (gapHSizePx - eachItemHPaddingPx) + sectionEdgeHPaddingPx;
                right = eachItemHPaddingPx - left;
            }

            //第一行
            if (visualPos - spanCount <= 0) {
                //每个section的第一行
                top = sectionEdgeVPaddingPx;
            }
            
            //存在即是第一行，又是最后一行的情况，故不用elseif
            int sectionItemCount = 0;
            if (isLastRow(visualPos, spanCount, sectionItemCount)) {
                //每个section的最后一行
                bottom = sectionEdgeVPaddingPx;
            }
//            preRect = outRect;

            outRect.set((int) left, (int) top, (int) right, (int) bottom);
            Log.d("GridAverageGapItem", "pos=" + position + ","
                    + "Rect(left:" + px2dip(left) + ", "
                    + "top:" + px2dip(top) + " - "
                    + "right:" + px2dip(right) + ", "
                    + "bottom:" + px2dip(bottom) + ")");
        } else {
            super.getItemOffsets(outRect, view, parent, state);
        }
    }
    
    private void transformGapDefinition(RecyclerView parent, int spanCount) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            parent.getDisplay().getMetrics(displayMetrics);
        }
        gapHSizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, gapHorizontalDp, displayMetrics);
        gapVSizePx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, gapVerticalDp, displayMetrics);
        sectionEdgeHPaddingPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sectionEdgeHPaddingDp, displayMetrics);
        sectionEdgeVPaddingPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sectionEdgeVPaddingDp, displayMetrics);
        eachItemHPaddingPx = (sectionEdgeHPaddingPx * 2 + gapHSizePx * (spanCount - 1)) / spanCount;
    }




    private boolean isLastRow(int visualPos, int spanCount, int sectionItemCount) {
        int lastRowCount = sectionItemCount % spanCount;
        lastRowCount = lastRowCount == 0 ? spanCount : lastRowCount;
        return visualPos > sectionItemCount - lastRowCount;
    }

    public int px2dip(int value) {
        float v = BaseApplication.Companion.getInstance().getResources().getDisplayMetrics().density;
        return (int) (value / v + 0.5f);
    }
}
