package com.qq.reader.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.qq.reader.baseui.R;
import com.qq.reader.common.utils.CommonUtility;
import com.qq.reader.core.BaseApplication;
import com.qq.reader.core.config.AppConstant;

import java.lang.ref.WeakReference;

public class AlertController {

	public final Window mWindow;
	private final Context mContext;
	private final DialogInterface mDialogInterface;
	private CharSequence mTitle;

	private CharSequence mMessage;

	private ListView mListView;

	private View mView;

	private int mViewSpacingLeft;

	private int mViewSpacingTop;

	private int mViewSpacingRight;

	private int mViewSpacingBottom;

	private boolean mViewSpacingSpecified = false;

	private TextView mButtonPositive;

	private CharSequence mButtonPositiveText;

	private int mButtonPositiveBackgroundResId = -1;
	private int mButtonPositiveTextColor = -1;

	private Message mButtonPositiveMessage;

	private TextView mButtonNegative;

	private TextView mButtonSingle;

	private CharSequence mButtonNegativeText;

	private int mButtonNegativeBackgroundResId = -1;
	private int mButtonNegativeTextColor = -1;

	private Message mButtonNegativeMessage;

	private TextView mButtonNeutral;

	private CharSequence mButtonNeutralText;

	private Message mButtonNeutralMessage;

	private ScrollView mScrollView;

	private int mIconId = -1;

	private Drawable mIcon;

	private ImageView mIconView;

	private TextView mTitleView;

	private TextView mMessageView;

	private View mCustomTitleView;

	private ViewGroup mTitleViewLayout;

	private boolean mForceInverseBackground;

	private ListAdapter mAdapter;

	private int mCheckedItem = -1;

	private Handler mHandler;

	public static final int DIALOG_CENTER_VIEW_PADDING = CommonUtility.dip2px(24);

	private boolean isCancelOnClickPositiveButton = true;
	View.OnClickListener mButtonHandler = new View.OnClickListener() {
		public void onClick(View v) {
			Message m = null;
			if (v == mButtonPositive && mButtonPositiveMessage != null) {
				m = Message.obtain(mButtonPositiveMessage);
			} else if (v == mButtonNegative && mButtonNegativeMessage != null) {
				m = Message.obtain(mButtonNegativeMessage);
			} else if (v == mButtonNeutral && mButtonNeutralMessage != null) {
				m = Message.obtain(mButtonNeutralMessage);
			} else if (v == mButtonSingle) {
				if (mButtonPositiveMessage != null) {
					m = Message.obtain(mButtonPositiveMessage);
				} else if (mButtonNegativeMessage != null) {
					m = Message.obtain(mButtonNegativeMessage);
				}
			}
			if (m != null) {
				m.sendToTarget();
			}

			// Post a message so we dismiss after the above handlers are
			// executed
			if (v == mButtonPositive && mButtonPositiveMessage != null
					&& !isCancelOnClickPositiveButton) {
				return;
			} else {
				mHandler.obtainMessage(ButtonHandler.MSG_DISMISS_DIALOG,
						mDialogInterface).sendToTarget();
			}
		}
	};
	//设置弹窗中自定义View外层Framelayout的高度，因Framelayout的高度min为100dp
	private int mSpecificViewHeight = -1;
	private DialogInterface.OnClickListener mPositiveListener,mNegativeListener;
	public View mBottomLayout;
	public ViewGroup mBodyLayout;

	public AlertController(Context context, DialogInterface di, Window window) {
		mContext = context;
		mDialogInterface = di;
		mWindow = window;
		mHandler = new ButtonHandler(di);
	}

	static boolean canTextInput(View v) {
		if (v.onCheckIsTextEditor()) {
			return true;
		}

		if (!(v instanceof ViewGroup)) {
			return false;
		}

		ViewGroup vg = (ViewGroup) v;
		int i = vg.getChildCount();
		while (i > 0) {
			i--;
			v = vg.getChildAt(i);
			if (canTextInput(v)) {
				return true;
			}
		}

		return false;
	}

	public void setOnClickPositiveCancelEnable(boolean b) {
		isCancelOnClickPositiveButton = b;
	}

	public void doDismiss() {
		mHandler.obtainMessage(ButtonHandler.MSG_DISMISS_DIALOG,
				mDialogInterface).sendToTarget();
	}

	public View findViewById(int id) {
		if (mView != null) {
			return mView.findViewById(id);
		}

		return null;
	}

	public void installContent() {
		/* We use a custom title so never request a window title */
		mWindow.requestFeature(Window.FEATURE_NO_TITLE);
		mWindow.setContentView(R.layout.message_dialog);
		if (mView == null || !canTextInput(mView)) {
			mWindow.setFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
					WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		}
		//by ivan
		WindowManager.LayoutParams lp = mWindow.getAttributes();
		//通用白牌从底部弹出 距离底部56dp
		lp.gravity = android.view.Gravity.BOTTOM;
		lp.y = CommonUtility.dip2px(56);
//		Display display = mWindow.getWindowManager().getDefaultDisplay();
//		lp.width = (int) (display.getWidth()); // 设置宽度
        //这里设置宽度为MATCH_PARENT，防止有分屏模式的手机，采用系统宽度过宽，dialog内容展示不全。
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		if (Build.VERSION.SDK != null && Integer.valueOf(Build.VERSION.SDK) > 3) {
			// 1.5以上可以自定义windows动画, 或者只有g3或是sense_ui有bug, 需要更多机型测试
			mWindow.setWindowAnimations(R.style.Animation_menuAnim);
		}
		mWindow.setAttributes(lp);
		setupView();
	}

	public void setTitle(CharSequence title) {
		mTitle = title;
		if (mTitleView != null) {
			mTitleView.setText(title);
		}
	}

	/**
	 * 隐藏标题栏
	 */
	public void hideTitle() {
		if (mTitleView != null) {
			mTitleView.setVisibility(View.GONE);
		}
	}

	/**
	 * @see AlertDialog.Builder#setCustomTitle(View)
	 */
	public void setCustomTitle(View customTitleView) {
		mCustomTitleView = customTitleView;
	}

	public void setMessage(CharSequence message) {
		mMessage = message;
		if (mMessageView != null) {
			mMessageView.setText(message);
		}
	}

	public TextView getMessageView() {
		return mMessageView;
	}

	/**
	 * Set the view to display in the dialog.
	 */
	public void setView(View view) {
		mView = view;
		mViewSpacingSpecified = false;
	}

	/**
	 * Set the view to display in the dialog along with the spacing around that
	 * view
	 */
	public void setView(View view, int viewSpacingLeft, int viewSpacingTop,
			int viewSpacingRight, int viewSpacingBottom) {
		mView = view;
		mViewSpacingSpecified = true;
		mViewSpacingLeft = viewSpacingLeft;
		mViewSpacingTop = viewSpacingTop;
		mViewSpacingRight = viewSpacingRight;
		mViewSpacingBottom = viewSpacingBottom;
	}

	/**
	 * Sets a click listener or a message to be sent when the button is clicked.
	 * You only need to pass one of {@code listener} or {@code msg}.
	 *
	 * @param whichButton
	 *            Which button, can be one of
	 *            {@link DialogInterface#BUTTON_POSITIVE},
	 *            {@link DialogInterface#BUTTON_NEGATIVE}, or
	 *            {@link DialogInterface#BUTTON_NEUTRAL}
	 * @param text
	 *            The text to display in positive button.
	 * @param listener
	 *            The {@link DialogInterface.OnClickListener} to use.
	 * @param msg
	 *            The {@link Message} to be sent when clicked.
	 */
	public void setButton(int whichButton, CharSequence text,
			DialogInterface.OnClickListener listener, Message msg) {

		if (msg == null && listener != null) {
			msg = mHandler.obtainMessage(whichButton, listener);
		}

		switch (whichButton) {

		case DialogInterface.BUTTON_POSITIVE:
			mButtonPositiveText = text;
			if(mButtonPositiveMessage !=null){
				mButtonPositiveMessage.obj = null;
				mButtonPositiveMessage = null;
			}
			mButtonPositiveMessage = msg;
			mPositiveListener = listener;
			break;

		case DialogInterface.BUTTON_NEGATIVE:
			mButtonNegativeText = text;
			if (mButtonNegativeMessage != null){
				mButtonNegativeMessage.obj = null;
				mButtonNegativeMessage = null;
			}
			mButtonNegativeMessage = msg;
			mNegativeListener = listener;
			break;

		case DialogInterface.BUTTON_NEUTRAL:
			mButtonNeutralText = text;
			mButtonNeutralMessage = msg;
			break;

		default:
			throw new IllegalArgumentException("Button does not exist");
		}
	}

	public void resetButtonListener(){
		if (mPositiveListener != null){
			setButton(DialogInterface.BUTTON_POSITIVE,mButtonPositiveText,mPositiveListener,null);
		}
		if (mNegativeListener != null){
			setButton(DialogInterface.BUTTON_NEGATIVE, mButtonNegativeText, mNegativeListener,null);
		}
	}

	/**
	 * Set resId to 0 if you don't want an icon.
	 *
	 * @param resId
	 *            the resourceId of the drawable to use as the icon or 0 if you
	 *            don't want an icon.
	 */
	public void setIcon(int resId) {
		mIconId = resId;
		if (mIconView != null) {
			if (resId > 0) {
				mIconView.setImageResource(mIconId);
			} else if (resId == 0) {
				mIconView.setVisibility(View.GONE);
			}
		}
	}

	public void setIcon(Drawable icon) {
		mIcon = icon;
		if ((mIconView != null) && (mIcon != null)) {
			mIconView.setImageDrawable(icon);
		}
	}

	public void setSpecificViewHeight(int height) {
		mSpecificViewHeight = height;
	}

	public void setInverseBackgroundForced(boolean forceInverseBackground) {
		mForceInverseBackground = forceInverseBackground;
	}

	public ListView getListView() {
		return mListView;
	}

	public TextView getButton(int whichButton) {
		switch (whichButton) {
		case DialogInterface.BUTTON_POSITIVE:
			return mButtonPositiveMessage != null ? mButtonPositive : null;
		case DialogInterface.BUTTON_NEGATIVE:
			return mButtonNegativeMessage != null ? mButtonNegative : null;
		case DialogInterface.BUTTON_NEUTRAL:
			return mButtonNeutralMessage != null ? mButtonNeutral : null;
		default:
			return null;
		}
	}

	public TextView getDialogButton(int whichButton) {
		switch (whichButton) {
			case DialogInterface.BUTTON_POSITIVE:
				return mButtonPositive;
			case DialogInterface.BUTTON_NEGATIVE:
				return mButtonNegative;
			case DialogInterface.BUTTON_NEUTRAL:
				return mButtonNeutral;
			default:
				return null;
		}
	}

	public void setButtonBackgroundResId(int whichButton, int resId) {
		switch (whichButton) {
			case DialogInterface.BUTTON_POSITIVE :
				mButtonPositiveBackgroundResId = resId;
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				mButtonNegativeBackgroundResId = resId;
				break;
		}
	}

	public void setButtonTextColor(int whichButton, int textColorId) {
		switch (whichButton) {
			case DialogInterface.BUTTON_POSITIVE :
				mButtonPositiveTextColor = textColorId;
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				mButtonNegativeTextColor = textColorId;
				break;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mScrollView != null && mScrollView.executeKeyEvent(event))
			return true;
		return false;
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (mScrollView != null && mScrollView.executeKeyEvent(event))
			return true;
		return false;
	}

	private void setupView() {
		mTitleViewLayout = (ViewGroup) mWindow.findViewById(R.id.message_dialog_title_layout);
		mTitleView = (TextView) mWindow.findViewById(R.id.message_dialog_title);
		if (mCustomTitleView == null) {
			//使用默认的 titleView
			if (mTitleView != null) {
				if (!TextUtils.isEmpty(mTitle)) {
					mTitleView.setText(mTitle);
				} else {
					mTitleView.setVisibility(View.GONE);
				}
			}
		} else {
			//使用自定义 titleView
			if (mTitleViewLayout != null) {
				mTitleViewLayout.removeAllViews();
				mTitleViewLayout.addView(mCustomTitleView);
			}
		}
		mMessageView = (TextView) mWindow
				.findViewById(R.id.message_dialog_message);
		if (mMessage != null) {
			mMessageView.setText(mMessage);
			mMessageView.setVisibility(View.VISIBLE);
		} else {
			mMessageView.setVisibility(View.GONE);
		}
		mBodyLayout = (ViewGroup) mWindow.findViewById(R.id.body);
		if (mListView != null) {
			//如果ListView
			mView = mListView;
			if (mAdapter != null) {
				mListView.setAdapter(mAdapter);
			}
		}
		if (mView != null) {
			if (mSpecificViewHeight > 0) {
				if (mBodyLayout != null) {
					mBodyLayout.setMinimumHeight(0);
					mBodyLayout.addView(mView,
							new LayoutParams(LayoutParams.MATCH_PARENT,
									mSpecificViewHeight));
				}
			} else {
				mBodyLayout.addView(mView,
						new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.MATCH_PARENT));
			}
			if (TextUtils.isEmpty(mButtonPositiveText) && TextUtils.isEmpty(mButtonNegativeText)) {
				mBodyLayout.setBackgroundColor(Color.TRANSPARENT);
				//View divider = mWindow.findViewById(R.id.content_button_divider);
				//divider.setVisibility(View.GONE);
			}
		}
		setupButtons();
		if (TextUtils.isEmpty(mButtonPositiveText) && TextUtils.isEmpty(mButtonNegativeText) && TextUtils.isEmpty(mButtonNeutralText)) {
			mBottomLayout.setVisibility(View.GONE);//如果都为空底部按钮不显示
			if (TextUtils.isEmpty(mTitle) ) {
				mMessageView.setPadding(DIALOG_CENTER_VIEW_PADDING, DIALOG_CENTER_VIEW_PADDING, DIALOG_CENTER_VIEW_PADDING, DIALOG_CENTER_VIEW_PADDING);
			} else {
				mMessageView.setPadding(DIALOG_CENTER_VIEW_PADDING, 0, DIALOG_CENTER_VIEW_PADDING, DIALOG_CENTER_VIEW_PADDING);
			}
		} else {
			if (TextUtils.isEmpty(mTitle) ) {
				mMessageView.setPadding(DIALOG_CENTER_VIEW_PADDING, DIALOG_CENTER_VIEW_PADDING, DIALOG_CENTER_VIEW_PADDING, 0);
			} else {
				mMessageView.setPadding(DIALOG_CENTER_VIEW_PADDING, 0, DIALOG_CENTER_VIEW_PADDING, 0);
			}
		}

		//handleDialogBodyPadding();
	}


	/**
	 * 处理AlertDialog Body Padding
	 * 自定义部分的padding交由使用者自己控制
	 */
	private void handleDialogBodyPadding() {
		int paddingLeft = CommonUtility.dip2px(DIALOG_CENTER_VIEW_PADDING);
		int paddingTop = 0;
		int paddingRight = CommonUtility.dip2px(DIALOG_CENTER_VIEW_PADDING);
		int paddingBottom = 0;
		if (TextUtils.isEmpty(mButtonPositiveText) && TextUtils.isEmpty(mButtonNegativeText) && TextUtils.isEmpty(mButtonNeutralText)) {
			mBottomLayout.setVisibility(View.GONE);
			if (TextUtils.isEmpty(mTitle)) {
				if (getListView() != null) {
					////没有底部button 没有标题 并且是列表
					paddingTop = 0;
					paddingBottom = 0;
				} else {
					//没有底部button 没有标题
					paddingTop = CommonUtility.dip2px(DIALOG_CENTER_VIEW_PADDING);
					paddingBottom = CommonUtility.dip2px(DIALOG_CENTER_VIEW_PADDING);
				}
			} else {
				//没有底部button 有标题
				paddingTop = 0;
				paddingBottom = CommonUtility.dip2px(DIALOG_CENTER_VIEW_PADDING);
			}
		} else {
			if (TextUtils.isEmpty(mTitle)) {
				//有底部button 没有标题
				paddingTop = CommonUtility.dip2px(DIALOG_CENTER_VIEW_PADDING);
				paddingBottom = 0;
			} else {
				//有底部button 有标题
				paddingTop = 0;
				paddingBottom = 0;
			}
		}

		//如果是列表 让列表自己写左右padding 防止滚动条覆盖内容
		if (getListView() != null) {
			paddingLeft = 0;
			paddingRight = 0;
		}

		mBodyLayout.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
	}

	private boolean setupTitle(LinearLayout topPanel) {
		boolean hasTitle = true;
		// TODO 填充title

		return hasTitle;
	}

	private boolean setupButtons() {
		View defaultButton = null;
		int BIT_BUTTON_POSITIVE = 1;
		int BIT_BUTTON_NEGATIVE = 2;
		int BIT_BUTTON_NEUTRAL = 4;
		int whichButtons = 0;

		mButtonPositive = (TextView) mWindow.findViewById(R.id.sureButton);
		//View buttonDivider = mWindow.findViewById(R.id.button_divider);
		mButtonPositive.setOnClickListener(mButtonHandler);

		mButtonNegative = (TextView) mWindow.findViewById(R.id.cancelButton);

		mButtonSingle = (TextView) mWindow.findViewById(R.id.singleButton);
		mBottomLayout = mWindow.findViewById(R.id.message_dialog_bottom_layout);
		mButtonNegative.setOnClickListener(mButtonHandler);

		mButtonSingle.setOnClickListener(mButtonHandler);
		// TODO button1的id
		if (mButtonPositiveBackgroundResId != -1) {
			mButtonPositive.setBackgroundResource(mButtonPositiveBackgroundResId);
		}
		if (mButtonPositiveTextColor != -1) {
			mButtonPositive.setTextColor(BaseApplication.Companion.getINSTANCE().getResources().getColor(mButtonPositiveTextColor));
		}

		if (TextUtils.isEmpty(mButtonPositiveText)) {
			mButtonPositive.setVisibility(View.GONE);
		} else {
			mButtonPositive.setText(mButtonPositiveText);
			mButtonSingle.setText(mButtonPositiveText);
			mButtonPositive.setVisibility(View.VISIBLE);
			defaultButton = mButtonPositive;
			whichButtons = whichButtons | BIT_BUTTON_POSITIVE;
		}
		// TODO button2的id

		if (mButtonNegativeBackgroundResId != -1) {
			mButtonNegative.setBackgroundResource(mButtonNegativeBackgroundResId);
		}
		if (mButtonNegativeTextColor != -1) {
			mButtonNegative.setTextColor(BaseApplication.Companion.getINSTANCE().getResources().getColor(mButtonNegativeTextColor));
		}

		if (TextUtils.isEmpty(mButtonNegativeText)) {
			mButtonNegative.setVisibility(View.GONE);
		} else {
			mButtonSingle.setText(mButtonNegativeText);
			mButtonNegative.setText(mButtonNegativeText);
			mButtonNegative.setVisibility(View.VISIBLE);

			if (defaultButton == null) {
				defaultButton = mButtonNegative;
			}
			whichButtons = whichButtons | BIT_BUTTON_NEGATIVE;
		}
		//单个按钮
		if (mButtonPositiveText==null || mButtonNegativeText==null) {
			if(mButtonPositiveText!=null) {
				mButtonPositive.setVisibility(View.GONE);
				mButtonNegative.setVisibility(View.GONE);
				mButtonSingle.setVisibility(View.VISIBLE);
				mButtonSingle.setBackground(mButtonSingle.getContext().getResources().getDrawable(R.drawable.bg_button_blue_selector));
				mButtonSingle.setTextColor(mButtonSingle.getContext().getResources().getColor(R.color.basebutton_style1_textcolor_selector));
			}
			if (mButtonNegativeText!=null) {
				mButtonPositive.setVisibility(View.GONE);
				mButtonNegative.setVisibility(View.GONE);
				mButtonSingle.setVisibility(View.VISIBLE);
				mButtonSingle.setBackground(mButtonSingle.getContext().getResources().getDrawable(R.drawable.bg_button_gray_selector));
				mButtonSingle.setTextColor(mButtonSingle.getContext().getResources().getColor(R.color.basebutton_style3_textcolor_selector));
			}
		}

		//设置body的最高 高度
		mBodyLayout.post(() -> {
            int maxBodyHeight = AppConstant.screenHeight - CommonUtility.dip2px(56) * 3 - CommonUtility.dip2px(64) - mBodyLayout.getPaddingTop() - mBodyLayout.getPaddingBottom() - AppConstant.statusBarHeight;
            if (mBodyLayout.getHeight() > maxBodyHeight) {
                LayoutParams layoutParams = mBodyLayout.getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, maxBodyHeight);
                } else {
                    layoutParams.height = maxBodyHeight;
                }
                mBodyLayout.setLayoutParams(layoutParams);
            }

        });

		if (whichButtons == BIT_BUTTON_POSITIVE) {
			centerButton(mButtonPositive);
		} else if (whichButtons == BIT_BUTTON_NEGATIVE) {
			centerButton(mButtonNegative);
		} else if (whichButtons == BIT_BUTTON_NEUTRAL) {
			centerButton(mButtonNeutral);
		} else if (whichButtons == 3) {
			// LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)
			// mButtonPositive
			// .getLayoutParams();
			// lp.addRule(LinearLayout.ALIGN_PARENT_LEFT);
			// mButtonPositive.setLayoutParams(lp);
			// lp = (LinearLayout.LayoutParams)
			// mButtonNegative.getLayoutParams();
			// lp.addRule(LinearLayout.ALIGN_PARENT_RIGHT);
			// mButtonNegative.setLayoutParams(lp);
		}

		// //TODO button3的id
		// mButtonNeutral = (Button) mWindow.findViewById(R.id.back);
		// mButtonNeutral.setOnClickListener(mButtonHandler);
		//
		// if (TextUtils.isEmpty(mButtonNeutralText)) {
		// mButtonNeutral.setVisibility(View.GONE);
		// } else {
		// mButtonNeutral.setText(mButtonNeutralText);
		// mButtonNeutral.setVisibility(View.VISIBLE);
		//
		// if (defaultButton == null) {
		// defaultButton = mButtonNeutral;
		// }
		// whichButtons = whichButtons | BIT_BUTTON_NEUTRAL;
		// }
		//
		// /*
		// * If we only have 1 button it should be centered on the layout and
		// * expand to fill 50% of the available space.
		// */
		// if (whichButtons == BIT_BUTTON_POSITIVE) {
		// centerButton(mButtonPositive);
		// } else if (whichButtons == BIT_BUTTON_NEGATIVE) {
		// centerButton(mButtonNeutral);
		// } else if (whichButtons == BIT_BUTTON_NEUTRAL) {
		// centerButton(mButtonNeutral);
		// }

		return whichButtons != 0;
	}

	// private void setupContent(LinearLayout contentPanel) {
	// mScrollView = (ScrollView) mWindow.findViewById(R.id.scrollView);
	// mScrollView.setFocusable(false);
	//
	// // Special case for users that only want to display a String
	// mMessageView = (TextView) mWindow.findViewById(R.id.message);
	// if (mMessageView == null) {
	// return;
	// }
	//
	// if (mMessage != null) {
	// mMessageView.setText(mMessage);
	// } else {
	// mMessageView.setVisibility(View.GONE);
	// mScrollView.removeView(mMessageView);
	//
	// if (mListView != null) {
	// contentPanel.removeView(mWindow.findViewById(R.id.scrollView));
	// contentPanel.addView(mListView,
	// new LinearLayout.LayoutParams(FILL_PARENT, FILL_PARENT));
	// contentPanel.setLayoutParams(new LinearLayout.LayoutParams(FILL_PARENT,
	// 0, 1.0f));
	// } else {
	// contentPanel.setVisibility(View.GONE);
	// }
	// }
	// }

	private void centerButton(TextView button) {
		// RelativeLayout.LayoutParams sLayoutParams = new
		// RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
		// LinearLayout.LayoutParams.WRAP_CONTENT);
		// RelativeLayout.LayoutParams sLayoutParams =
		// (RelativeLayout.LayoutParams) button
		// .getLayoutParams();
		// sLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		// button.setLayoutParams(sLayoutParams);
	}

	private void setBackground(LinearLayout topPanel,
			LinearLayout contentPanel, View customPanel, boolean hasButtons,
			TypedArray a, boolean hasTitle, View buttonPanel) {

	}

	private static final class ButtonHandler extends Handler {
		// Button clicks have Message.what as the BUTTON{1,2,3} constant
		private static final int MSG_DISMISS_DIALOG = 1;

		private WeakReference<DialogInterface> mDialog;

		public ButtonHandler(DialogInterface dialog) {
			mDialog = new WeakReference<DialogInterface>(dialog);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case DialogInterface.BUTTON_POSITIVE:
			case DialogInterface.BUTTON_NEGATIVE:
			case DialogInterface.BUTTON_NEUTRAL:
				((DialogInterface.OnClickListener) msg.obj).onClick(
						mDialog.get(), msg.what);
				break;

			case MSG_DISMISS_DIALOG:
				((DialogInterface) msg.obj).dismiss();
			}
		}
	}

	public static class RecycleListView extends ListView {
		boolean mRecycleOnMeasure = true;

		public RecycleListView(Context context) {
			super(context);
		}

		public RecycleListView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public RecycleListView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
		}
	}

	public static class AlertParams {
		public final Context mContext;
		public final LayoutInflater mInflater;

		public int mIconId = -1;
		public Drawable mIcon;
		public CharSequence mTitle;
		public View mCustomTitleView;
		public CharSequence mMessage;
		public CharSequence mPositiveButtonText;
		public DialogInterface.OnClickListener mPositiveButtonListener;
		public CharSequence mNegativeButtonText;
		public DialogInterface.OnClickListener mNegativeButtonListener;
		public CharSequence mNeutralButtonText;
		public DialogInterface.OnClickListener mNeutralButtonListener;
		public boolean mCancelable;
		public DialogInterface.OnCancelListener mOnCancelListener;
		public DialogInterface.OnKeyListener mOnKeyListener;
		public CharSequence[] mItems;
		public ListAdapter mAdapter;
		public DialogInterface.OnClickListener mOnClickListener;
		public View mView;
		public int mViewSpacingLeft;
		public int mViewSpacingTop;
		public int mViewSpacingRight;
		public int mViewSpacingBottom;
		public boolean mViewSpacingSpecified = false;
		public boolean[] mCheckedItems;
		public boolean mIsMultiChoice;
		public boolean mIsSingleChoice;
		public int mCheckedItem = -1;
		public DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
		public Cursor mCursor;
		public String mLabelColumn;
		public String mIsCheckedColumn;
		public boolean mForceInverseBackground;
		public AdapterView.OnItemSelectedListener mOnItemSelectedListener;
		public OnPrepareListViewListener mOnPrepareListViewListener;
		public boolean mRecycleOnMeasure = true;

		public AlertParams(Context context) {
			mContext = context;
			mCancelable = true;
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public void apply(AlertController dialog) {
			if (mTitle != null) {
				dialog.setTitle(mTitle);
			}
			else {
				dialog.hideTitle();
			}
			if (mCustomTitleView != null) {
				dialog.setCustomTitle(mCustomTitleView);
			}
			if (mMessage != null) {
				dialog.setMessage(mMessage);
			}
			if (mIcon != null) {
				dialog.setIcon(mIcon);
			}
			if (mIconId >= 0) {
				dialog.setIcon(mIconId);
			}
			if (mPositiveButtonText != null) {
				dialog.setButton(DialogInterface.BUTTON_POSITIVE,
						mPositiveButtonText, mPositiveButtonListener, null);
			}
			if (mNegativeButtonText != null) {
				dialog.setButton(DialogInterface.BUTTON_NEGATIVE,
						mNegativeButtonText, mNegativeButtonListener, null);
			}
			if (mNeutralButtonText != null) {
				dialog.setButton(DialogInterface.BUTTON_NEUTRAL,
						mNeutralButtonText, mNeutralButtonListener, null);
			}
			if (mForceInverseBackground) {
				dialog.setInverseBackgroundForced(true);
			}
			// For a list, the client can either supply an array of items or an
			// adapter or a cursor
			if ((mItems != null) || (mCursor != null) || (mAdapter != null)) {
				createListView(dialog);
			}
			if (mView != null) {
				dialog.setView(mView);
			}
		}

		private void createListView(final AlertController dialog) {
			// 替换自己的dialog
//			final RecycleListView listView = (RecycleListView) mInflater
//					.inflate(R.layout.lightdialog, null);
			final ListView listView =
					(ListView) mInflater.inflate(R.layout.alert_dialog_list, null);
			final ListAdapter adapter;
			if (mIsMultiChoice) {
				return;//不支持mIsMultiChoice,如果未来有需求可以接进来
			}
			final int layout;
			if (mIsSingleChoice) {
				layout = R.layout.alert_dialog_list_item_choice;
			} else {
				layout = R.layout.alert_dialog_list_item;
			}

			if (mCursor != null) {
				adapter = new SimpleCursorAdapter(mContext, layout, mCursor,
						new String[] { mLabelColumn }, new int[] { R.id.text1 });
			} else if (mAdapter != null) {
				adapter = mAdapter;
			} else {
				adapter = new CheckedItemAdapter(mContext, mItems, layout, mCheckedItem);
			}

			if (mOnPrepareListViewListener != null) {
				mOnPrepareListViewListener.onPrepareListView(listView);
			}
			dialog.mAdapter = adapter;
			dialog.mCheckedItem = mCheckedItem;

			if (mOnClickListener != null) {
				listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
						mOnClickListener.onClick(dialog.mDialogInterface, position);
						if (!mIsSingleChoice) {
							dialog.mDialogInterface.dismiss();
						}
					}
				});
			}

			// Attach a given OnItemSelectedListener to the ListView
			if (mOnItemSelectedListener != null) {
				listView.setOnItemSelectedListener(mOnItemSelectedListener);
			}

			//listView.mRecycleOnMeasure = mRecycleOnMeasure;
			dialog.mListView = listView;
		}

		/**
		 * Interface definition for a callback to be invoked before the ListView
		 * will be bound to an adapter.
		 */
		public interface OnPrepareListViewListener {

			/**
			 * Called before the ListView is bound to an adapter.
			 *
			 * @param listView
			 *            The ListView that will be shown in the dialog.
			 */
			void onPrepareListView(ListView listView);
		}
	}

	private static class CheckedItemAdapter extends BaseAdapter {

	    private Context mContext;
        private CharSequence[] mItems;
        private int layout;
        private int checkedItem;

        public CheckedItemAdapter(Context mContext, CharSequence[] mItems, int layout, int checkedItem) {
            this.mContext = mContext;
            this.mItems = mItems;
            this.layout = layout;
            this.checkedItem = checkedItem;
        }

        @Override
        public int getCount() {
            if (mItems != null) {
                return mItems.length;
            }
            return 0;
        }

        @Override
        public CharSequence getItem(int i) {
            if (mItems != null) {
                return mItems[i];
            }
            return "";
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(layout, parent, false);
                holder =  new ViewHolder(convertView);
            }
            if (holder != null) {
                holder.text1.setText(mItems[position]);
				if (holder.checkBox != null) {
					if (position == checkedItem) {
						holder.checkBox.setChecked(true);
					} else {
						holder.checkBox.setChecked(false);
					}
				}
				//如果是最后一个Item 隐藏Divider
				if (position >= getCount() - 1) {
					holder.divider.setVisibility(View.GONE);
				} else {
					holder.divider.setVisibility(View.VISIBLE);
				}
            }
            return convertView;
        }

        static class ViewHolder {
            TextView text1;
            CheckBox checkBox;
            View divider;

            public ViewHolder(View convertView) {
                text1 = (TextView) convertView.findViewById(R.id.text1);
                checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
				divider = convertView.findViewById(R.id.divider);
            }
        }

    }


}
