package com.qq.reader.widget;

import android.content.Context;
import android.content.res.TypedArray;

import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.qq.reader.baseui.R;
import com.qq.reader.module.bookstore.search.DropDownEditText;

public class SearchTopView extends LinearLayout{

    public static final int SEARCH_TOP_VIEW_MAIN = 0;
    public static final int SEARCH_TOP_VIEW_SEARCH = 1;

    private Context context;
    private int viewMode = SEARCH_TOP_VIEW_MAIN;

    private DropDownEditText mSearchBar;
    private TextView mSearchBarText;
    private View clearBtn;
    private View backButton;
    private TextView rightButton;
    private View searchBody;
    private boolean mHasDivider;

    public SearchTopView(Context context) {
        super(context);
        this.context = context;
        initView(null);
    }

    public SearchTopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(attrs);
    }

    public SearchTopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(attrs);
    }

    private void initView(AttributeSet attrs){

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchTopView);
        mHasDivider = typedArray.getBoolean(R.styleable.SearchTopView_hasDivider, true);
        boolean isLight = typedArray.getBoolean(R.styleable.SearchTopView_isLight, true);
        if (attrs != null) {
            setHasDivider(mHasDivider);
        }
        setOrientation(VERTICAL);
        setGravity(Gravity.BOTTOM);
        View actionbarLayout = inflate(context, R.layout.search_top,null);
        actionbarLayout.setPadding(context.getResources().getDimensionPixelOffset(R.dimen.spacing_L2),0,context.getResources().getDimensionPixelOffset(R.dimen.spacing_L2),0);
        addView(actionbarLayout);

        searchBody = actionbarLayout.findViewById(R.id.rl_search_request_focus);
        mSearchBar = (DropDownEditText) searchBody.findViewById(R.id.searchBar);
        mSearchBarText = (TextView) actionbarLayout.findViewById(R.id.searchBarText);
        clearBtn = searchBody.findViewById(R.id.clearTextBtn);

        backButton = actionbarLayout.findViewById(R.id.websearch_header_back);
        rightButton = (TextView)actionbarLayout.findViewById(R.id.search_header_right_text);

        if(isLight){
            searchBody.setBackgroundResource(R.drawable.bg_channel_search);
        }else{
            searchBody.setBackgroundResource(R.drawable.hw_input_grey_bg);
        }
    }

    public TextView getSearchViewText(){
        return mSearchBarText;
    }

    public String getSearchHit(){
        if(mSearchBarText == null || null == mSearchBarText.getHint()){
            return "";
        }
        return mSearchBarText.getHint().toString();

    }

    public void setViewMode(int viewMode){
        this.viewMode = viewMode;
        if(this.viewMode == SEARCH_TOP_VIEW_MAIN){
            mSearchBar.setVisibility(View.GONE);
            clearBtn.setVisibility(View.GONE);
            backButton.setVisibility(View.GONE);
            mSearchBarText.setVisibility(VISIBLE);
            setPadding(0,context.getResources().getDimensionPixelOffset(R.dimen.screen_statusbar_height),0, 0);
        }else{
            mSearchBarText.setVisibility(GONE);
            backButton.setVisibility(View.VISIBLE);
            mSearchBar.setVisibility(View.VISIBLE);
            clearBtn.setVisibility(View.VISIBLE);
        }
    }

    public void setRightButtonText(String text) {
        if (rightButton != null) {
            rightButton.setText(text);
        }
    }
    public void showRightButton(){
        if(rightButton != null){
            rightButton.setVisibility(View.VISIBLE);
        }
    }

    public void setRightButtonClickListener(OnClickListener listener){
        if(rightButton != null){
            rightButton.setOnClickListener(listener);
        }
    }

    public void setOnClickListener(OnClickListener listener){
        if(searchBody != null){
            searchBody.setOnClickListener(listener);
        }

        if(mSearchBarText != null){
            mSearchBarText.setOnClickListener(listener);
        }
    }

    public void setBackOnClickListener(OnClickListener listener) {
        if (backButton != null) {
            backButton.setOnClickListener(listener);
        }
    }

    public View getClearBtn() {
        return clearBtn;
    }

    public DropDownEditText getSearchBar() {
        return mSearchBar;
    }

    public void setHasDivider(boolean hasDivider) {
        this.mHasDivider = hasDivider;
        if (mHasDivider) {
            //分割线
            View view = new View(context);
            view.setBackgroundColor(getResources().getColor(R.color.top_bottom_divider));
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,getResources().getDimensionPixelOffset(R.dimen.devider_height));
            addView(view, lp);
        }

    }
}
