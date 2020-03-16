package com.qq.reader.view;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by hexiaole on 2019/10/9.
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration{
    private int spanCount;
    private int spacing;  //中间间距
    private int mFirstSpacing; //第一排距顶部 20dp
    private int mLeftRightSpacing;  //左侧右侧间距

    public GridSpacingItemDecoration(int spanCount, int spacing, int topSpacing, int leftrightSpacing) {
        this.spanCount = spanCount;
        this.spacing = spacing;

        mFirstSpacing = topSpacing;
        mLeftRightSpacing = leftrightSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column


//        if (position < spanCount) { // top edge
//            outRect.top = mFirstSpacing;
//        }else {
//            outRect.top = mLeftRightSpacing / 2;
//        }
//
//        if (position % 3 == 0) {  // left edge
//            outRect.left = mLeftRightSpacing;
//        }else {
//            outRect.left = spacing - column * spacing / spanCount;
//        }
//
//        if (position % 3 == 2) {  // right edge
//            outRect.right = mLeftRightSpacing;
//        }else {
//            outRect.right = (column + 1) * spacing / spanCount;
//        }
//
//        outRect.bottom = mLeftRightSpacing / 2; // item bottom

        outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
        outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

        if (position < spanCount) { // top edge
            outRect.top = mFirstSpacing;
        } else {
            outRect.top = mLeftRightSpacing / 2;
        }

        outRect.bottom = mLeftRightSpacing / 2; // item bottom

        Log.d("GridSpacingDecoration", "positon = " + position
                + ";  outRect.top = " + outRect.top
                + ";  outRect.left = " + outRect.left
                + ";  outRect.right = " + outRect.right
                + ";  outRect.bottom = " + outRect.bottom
                + "\n");
    }
}
