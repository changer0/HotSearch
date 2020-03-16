package com.qq.reader.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.qq.reader.baseui.R;
import com.qq.reader.core.utils.SystemUIUtils;

/**
 * Created by wangzhiheng on 2016/5/30.
 */
public class EmptyView extends LinearLayout {

    /**
     * 在setEmptyViewType(int)中设置类型的常量，确定是什么类型的空白页
     * 在设置完后类型后要根据所选类型设置文本和icon，也可在布局文件中用自定义属性形式填充，详见EmptyPage自定义属性集
     * 在listview中也可以实现在剩余空间中自动居中，在布局文件或代码中设置autoCenterInListView即可
     */
    public static final int SINGLE_TEXT = 0;//只有单行文字提醒的空白页+icon
    public static final int TITLE_TEXT = 1;//两行文字空白页+icon
    public static final int RELOAD_TEXT = 2;//单行+icon+白色按钮
    public static final int BUTTON_TEXT = 3;//单行+icon+蓝色按钮
    public static final int BUTTON_TITLE_TEXT = 4;//双行+icon+蓝色按钮
    public static final int BUTTON_TWO_TEXT = 5;//单行+icon+白色按钮+蓝色按钮
    public static final int SINGLE_BUTTON = 6;//icon+蓝色按钮


    private TextView contentView, contentTitleView, buttonView;
    private TextView mOppSettingCenterBtn;
    private ImageView iconView;
    private View containerView;
    private Context mContext;
    private boolean autoCenterInListView = false;
    private boolean isFirstCenter = true;
    private int miniHeight = 0;
    private int parentHierarchy = 4;
    private boolean reMeasure = false;
    private int mPaddingBottom = 0;

    //是否需要重新绘制
    private boolean isReDraw = false;


    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initStyleAttr(attrs);
        setGravity(Gravity.CENTER);
        setWillNotDraw(false);
        //设置这个空的clicklistener是因为2.3系统会透传触摸状态到所有子控件，xml属性控制不起作用
        this.setOnClickListener(null);

    }

    private void initView() {

        View.inflate(mContext, R.layout.empty_page_container, this);
        containerView = findViewById(R.id.empty_container);
        iconView = (ImageView) findViewById(R.id.empty_page_icon);
        contentView = (TextView) findViewById(R.id.empty_page_content);
        contentTitleView = (TextView) findViewById(R.id.empty_page_content2);
        buttonView = (TextView) findViewById(R.id.empty_page_button);
        mOppSettingCenterBtn = (TextView) findViewById(R.id.empty_page_button2);

        mOppSettingCenterBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUIUtils.gotoNetSetting(mContext);
            }
        });
    }

    private void initStyleAttr(AttributeSet attrs) {
        TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.EmptyPage, 0, 0);
        try {
            int type = a.getInt(R.styleable.EmptyPage_type, -1);
            String contentText = a.getString(R.styleable.EmptyPage_contentText);
            String titleText = a.getString(R.styleable.EmptyPage_contentTitle);
            String reloadText = a.getString(R.styleable.EmptyPage_reloadText);
            String buttonText = a.getString(R.styleable.EmptyPage_buttonText);
            int marginLeft = a.getDimensionPixelOffset(R.styleable.EmptyPage_view_marginLeft, 0);
            int marginRight = a.getDimensionPixelOffset(R.styleable.EmptyPage_view_marginRight, 0);
            Drawable iconDrawable = a.getDrawable(R.styleable.EmptyPage_icon);
            boolean auto = a.getBoolean(R.styleable.EmptyPage_autoCenterInListView, false);
            if (type != -1) {
                setEmptyViewType(type);
            }
            setContent(contentText);
            setContentTitle(titleText);
            setReloadText(reloadText);
            setButtonText(buttonText);
            setIcon(iconDrawable);
            setAutoCenterInListView(auto);
            Drawable background = a.getDrawable(R.styleable.EmptyPage_viewBackground);
            if (background != null) {
                setBackground(background);
            }
//            Log.e("test", "MarginLayoutParams l = " + marginLeft);
            MarginLayoutParams lp = (MarginLayoutParams)
                    containerView.getLayoutParams();
            lp.setMargins(marginLeft, 0, marginRight, 0);

//            Log.e("emptyview", "footerDividerEnable " + footerDividerEnable);
            miniHeight = this.getMinimumHeight();

        } finally {
            a.recycle();
        }
    }

    public EmptyView setEmptyViewType(int type) {
        switch (type) {
            case SINGLE_TEXT:
                iconView.setVisibility(VISIBLE);
                contentView.setVisibility(VISIBLE);
                contentTitleView.setVisibility(GONE);
                buttonView.setVisibility(GONE);
                mOppSettingCenterBtn.setVisibility(GONE);
                break;
            case TITLE_TEXT:
                iconView.setVisibility(VISIBLE);
                contentView.setVisibility(VISIBLE);
                contentTitleView.setVisibility(VISIBLE);
                buttonView.setVisibility(GONE);
                mOppSettingCenterBtn.setVisibility(GONE);
                break;
            case RELOAD_TEXT:
                iconView.setVisibility(VISIBLE);
                contentView.setVisibility(VISIBLE);
                contentTitleView.setVisibility(GONE);
                buttonView.setVisibility(GONE);
                mOppSettingCenterBtn.setVisibility(VISIBLE);
                break;
            case BUTTON_TEXT:
                iconView.setVisibility(VISIBLE);
                contentView.setVisibility(VISIBLE);
                contentTitleView.setVisibility(GONE);
                buttonView.setVisibility(VISIBLE);
                mOppSettingCenterBtn.setVisibility(GONE);
                break;
            case BUTTON_TITLE_TEXT:
                iconView.setVisibility(VISIBLE);
                contentView.setVisibility(VISIBLE);
                contentTitleView.setVisibility(VISIBLE);
                buttonView.setVisibility(VISIBLE);
                mOppSettingCenterBtn.setVisibility(GONE);
                break;
            case BUTTON_TWO_TEXT:
                iconView.setVisibility(VISIBLE);
                contentView.setVisibility(VISIBLE);
                contentTitleView.setVisibility(GONE);
                buttonView.setVisibility(VISIBLE);
                mOppSettingCenterBtn.setVisibility(VISIBLE);
                break;
            case SINGLE_BUTTON:
                iconView.setVisibility(VISIBLE);
                contentView.setVisibility(GONE);
                contentTitleView.setVisibility(GONE);
                buttonView.setVisibility(VISIBLE);
                mOppSettingCenterBtn.setVisibility(GONE);
                break;
            default:
                break;

        }
        return this;
    }

    public EmptyView setIcon(int drawable) {
        iconView.setImageResource(drawable);
        return this;
    }

    public EmptyView setIcon(Bitmap bitmap) {
        iconView.setImageBitmap(bitmap);
        return this;
    }

    public EmptyView setIcon(Drawable drawable) {
        iconView.setImageDrawable(drawable);
        return this;
    }

    public EmptyView setContent(CharSequence content) {
        contentView.setText(content);
        return this;
    }

    public EmptyView setContentTextColor(int contentTextColor) {
        contentView.setTextColor(contentTextColor);
        return this;
    }

    public EmptyView setContentTitle(CharSequence title) {
        contentTitleView.setText(title);
        return this;
    }

    public EmptyView setButtonText(CharSequence buttonString) {
        buttonView.setText(buttonString);
        return this;
    }

    public EmptyView setReloadText(CharSequence reloadText) {
        mOppSettingCenterBtn.setText(reloadText);
        return this;
    }

    public EmptyView setReloadTextColor(int textColor) {
        mOppSettingCenterBtn.setTextColor(textColor);
        return this;
    }

    public EmptyView setButtonOnclick(OnClickListener listener) {
        buttonView.setOnClickListener(listener);
        return this;
    }

    public EmptyView setReloadOnclick(OnClickListener listener) {
        mOppSettingCenterBtn.setOnClickListener(listener);
        return this;
    }

    public EmptyView setAutoCenterInListView(boolean auto) {
        //由于设计改需求不要求居中了，固此处注释掉我的血汗。。。来日有用到 打开此注释即可！
        autoCenterInListView = auto;
        return this;
    }

    /**
     * 空白页初始时设置为0的高度，就不会在footerView中占位置（footerView即使设置为gone也会被撑开）
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int newheightMeasureSpec = heightMeasureSpec;
        if (!this.isShown()) {
            newheightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, newheightMeasureSpec);
    }

    /**
     * 在这里才有view.getTop()的值，因此在这里计算listview剩余的高度，全部赋值给空白页，这样空白页就能自动居中显示
     * 此外在需要重新计算高度时不调用super.onLayout()，避免一次计算和绘制，即减少一次计算开销，又可避免二次绘制造成的闪跳现象
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (autoCenterInListView && isFirstCenter) {
            measureNewHeight();
            if (reMeasure && !isReDraw) {
                return;
            }
        }
        super.onLayout(changed, l, t, r, b);
    }

    /**
     * 只有在measure和layout的周期走完后requestLayout（）调用才会重新计算measure和layout
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (reMeasure && !isReDraw) {
            reMeasure = false;
            requestLayout();
        } else {
            super.onDraw(canvas);
        }
    }

    public void setPaddingBottoms(int paddingBottom) {
        mPaddingBottom = paddingBottom;
    }

    /**
     * 为了让空白item能填充满listview剩余的高度，要动态的计算空白item的高度
     */
    private void measureNewHeight() {
        if (!this.isShown()) {
            return;
        }
        isFirstCenter = false;
        int topViewHeight = this.getTop();
        View view = (View) this.getParent();
        while (!(view instanceof ListView || view instanceof RecyclerView) && view != null && parentHierarchy > 0) {
            topViewHeight += view.getTop();
            view = (View) view.getParent();
            parentHierarchy--;
        }

        int emptyHeight = 0;
        if (view instanceof ListView) {
            emptyHeight = view.getMeasuredHeight() - topViewHeight;

        } else if (view instanceof RecyclerView) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) ((RecyclerView)view).getLayoutManager();
            //取第一个View
            if (layoutManager != null) {
                View firstView = layoutManager.findViewByPosition(0);
                if (firstView != null) {
                    emptyHeight = view.getMeasuredHeight() - firstView.getHeight();
                }
            }
        }

        if (emptyHeight < getMeasuredHeight()) {
            return;
        }
        // 先设置一个最小高度 防止计算高度导致显示不出来 后续如果有显示小的再处理
        emptyHeight = Math.max(emptyHeight, miniHeight);

        if (mPaddingBottom > 0) {
            emptyHeight -= mPaddingBottom;
            Log.d("emptyHeight", "emptyHeight=" + emptyHeight + "mPaddingBottom=" + mPaddingBottom);
        }

        getLayoutParams().height = emptyHeight;
//            Log.e("test", "measureNewHeight heighti " + emptyHeight);
        containerView.getLayoutParams().height = emptyHeight;
        setMinimumHeight(emptyHeight);
        reMeasure = true;
    }

    public void setFirstCenter(boolean firstCenter) {
        isFirstCenter = firstCenter;
    }

    public void setReDraw(boolean isReDraw) {
        this.isReDraw = isReDraw;
    }

    public View getSettingBtn() {
        return this.mOppSettingCenterBtn;
    }
}
