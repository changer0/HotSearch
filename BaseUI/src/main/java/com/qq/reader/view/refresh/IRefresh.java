package com.qq.reader.view.refresh;

import androidx.recyclerview.widget.RecyclerView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by liubin on 2017/1/30.
 */

public interface IRefresh {

    void setIsInterptAnimation(boolean isInterptAnimation);

    ListView getListView();

    RecyclerView getRecyclerView();

    void setPullToRefreshEnabled(boolean enable);

    void cleanAnimation();

    void showRefreshHeader();

    void hideRefreshHeader();

    boolean isRefreshing();

    void initHeader();

    void setPullRefreshTimeSaveKey(String className);

    void setRefreshing(boolean refreshing, String text, TextView toastView);

    void setRefreshing(boolean refreshing, String text);
    
}
