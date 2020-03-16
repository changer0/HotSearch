package com.qq.reader.view;

/**
 * @author SawRen
 * @email: sawren@tencent.com         
 * @date 2011-1-27
 */
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qq.reader.baseui.R;
import com.qq.reader.common.memoryleak.ActivityLeakSolution;
import com.qq.reader.common.utils.FlavorUtils;
import com.qq.reader.common.utils.NightModeUtil;
import com.tencent.mars.xlog.Log;

/**
 * A subclass of Dialog that can display one, two or three buttons. If you only
 * want to display a String in this dialog box, use the setMessage() method. If
 * you want to display a more complex view, look up the FrameLayout called
 * "body" and add your view to it:
 * 
 * <pre>
 * FrameLayout fl = (FrameLayout) findViewById(R.id.body);
 * fl.add(myView, new LayoutParams(FILL_PARENT, WRAP_CONTENT));
 * </pre>
 * 
 * <p>
 * The AlertDialog class takes care of automatically setting
 * {@link WindowManager.LayoutParams#FLAG_ALT_FOCUSABLE_IM
 * WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM} for you based on whether
 * any views in the dialog return true from {@link View#onCheckIsTextEditor()
 * View.onCheckIsTextEditor()}. Generally you want this set for a Dialog without
 * text editors, so that it will be placed on top of the current input method
 * UI. You can modify this behavior by forcing the flag to your desired mode
 * after calling {@link #onCreate}.
 */
public class AlertDialog extends Dialog implements DialogInterface {
	boolean avoiddismiss = false;
	private AlertController mAlert;
	private Context mContext = null;
	private NightModeUtil mNMC = null;
	private DialogInterface.OnShowListener mOnShowListener;
	private DialogInterface.OnCancelListener mCancelListener;
	private DialogInterface.OnDismissListener mDialogDismissListener;

	protected AlertDialog(Context context) {
		this(context, R.style.popBottomDialog);
	}


	protected AlertDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
		mAlert = new AlertController(context, this, getWindow());
		mNMC = new NightModeUtil(this, false);
		setOnDismissListener(new OnNightModeDialogDismissListener() {

			@Override
			public NightModeUtil getNightMode_Util() {
				return getNightModeUtil();
			}
		});
	}
	protected AlertDialog(Context context, boolean cancelable,
						  OnCancelListener cancelListener) {
		super(context, R.style.popBottomDialog);
		setCancelable(cancelable);
		setOnCancelListener(cancelListener);
		mAlert = new AlertController(context, this, getWindow());
		mNMC = new NightModeUtil(this, false);
		setOnDismissListener(new OnNightModeDialogDismissListener() {

			@Override
			public NightModeUtil getNightMode_Util() {
				return getNightModeUtil();
			}
		});
	}

	public void setOnClickPositiveCancelEnable(boolean b) {
		mAlert.setOnClickPositiveCancelEnable(b);
	}

	public void doDismiss() {
		mAlert.doDismiss();
	}

	public void setOnShowListener(DialogInterface.OnShowListener listener){
		mOnShowListener = listener;
//			super.setOnShowListener(listener);
	}

	public void setOnCancelListener(OnCancelListener clistener) {
		mCancelListener = clistener;
//			super.setOnCancelListener(clistener);
	}

	public void setOnDismissListener(OnDismissListener clistener) {
		mDialogDismissListener = clistener;
//			super.setOnDismissListener(clistener);

	}

	/**
	 * Gets one of the buttons used in the dialog.
	 * <p>
	 * If a button does not exist in the dialog, null will be returned.
	 *
	 * @param whichButton
	 *            The identifier of the button that should be returned. For
	 *            example, this can be {@link DialogInterface#BUTTON_POSITIVE}.
	 * @return The button from the dialog, or null if a button does not exist.
	 */
	public TextView getButton(int whichButton) {
		return mAlert.getButton(whichButton);
	}

	public void setSpecificViewHeight(int height) {
		mAlert.setSpecificViewHeight(height);
	}

	public TextView getDialogButton(int whichButton) {
		return mAlert.getDialogButton(whichButton);
	}

	public void setButtonBackgroundResId(int whichButton, int resId) {
		mAlert.setButtonBackgroundResId(whichButton, resId);
	}

	public void setButtonTextColorResId(int whichButton, int textColorId) {
		mAlert.setButtonTextColor(whichButton, textColorId);
	}

	/**
	 * Gets the list view used in the dialog.
	 *
	 * @return The {@link ListView} from the dialog.
	 */
	public ListView getListView() {
		return mAlert.getListView();
	}

	public TextView getMessageView() {
		return mAlert.getMessageView();

	}

	@Override
	public void setTitle(CharSequence title) {
		super.setTitle(title);
		mAlert.setTitle(title);
	}

	public View findViewById(int id) {
		return mAlert.findViewById(id);
	}

	public void setView(View content) {
		mAlert.setView(content);
	}

	public void setMessage(CharSequence message) {
		mAlert.setMessage(message);
	}

	/**
	 * Set a listener to be invoked when the positive button of the dialog is
	 * pressed.
	 *
	 * @param whichButton
	 *            Which button to set the listener on, can be one of
	 *            {@link DialogInterface#BUTTON_POSITIVE},
	 *            {@link DialogInterface#BUTTON_NEGATIVE}, or
	 *            {@link DialogInterface#BUTTON_NEUTRAL}
	 * @param text
	 *            The text to display in positive button.
	 * @param listener
	 *            The {@link DialogInterface.OnClickListener} to use.
	 */
	public void setButton(int whichButton, CharSequence text,
			OnClickListener listener) {
		mAlert.setButton(whichButton, text, listener, null);
	}

	/**
	 * Set a message to be sent when a button is pressed.
	 *
	 * @param whichButton Which button to set the message for, can be one of
	 *            {@link DialogInterface#BUTTON_POSITIVE},
	 *            {@link DialogInterface#BUTTON_NEGATIVE}, or
	 *            {@link DialogInterface#BUTTON_NEUTRAL}
	 * @param text The text to display in positive button.
	 * @param msg The {@link Message} to be sent when clicked.
	 */
	public void setButton(int whichButton, CharSequence text, Message msg) {
		mAlert.setButton(whichButton, text, null, msg);
	}

	/**
	 * Set resId to 0 if you don't want an icon.
	 *
	 * @param resId
	 *            the resourceId of the drawable to use as the icon or 0 if you
	 *            don't want an icon.
	 */
	public void setIcon(int resId) {
		mAlert.setIcon(resId);
	}

	public void setIcon(Drawable icon) {
		mAlert.setIcon(icon);
	}

	public void setInverseBackgroundForced(boolean forceInverseBackground) {
		mAlert.setInverseBackgroundForced(forceInverseBackground);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAlert.installContent();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (mAlert.onKeyDown(keyCode, event))
			return true;
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (mAlert.onKeyUp(keyCode, event))
			return true;
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void show() {
		//防止重用的时候，dialog的listener失效
		if (mOnShowListener != null) {
			super.setOnShowListener(mOnShowListener);
		}
		if (mCancelListener != null) {
			super.setOnCancelListener(mCancelListener);
		}
		if (mDialogDismissListener != null) {
			super.setOnDismissListener(mDialogDismissListener);
		}
		//重新设置buttonlistener,防止重用失效
		mAlert.resetButtonListener();

		super.show();
		if (!FlavorUtils.isHuaWei() && mNMC!= null) {
			mNMC.showMask();
		}
	}

	@Override
	public void dismiss() {
		if(avoiddismiss){
			avoiddismiss = false;
			return;
		}
		try {
			super.dismiss();
		}catch (Exception e) {
			Log.printErrStackTrace("AlertDialog", e, null, null);
			e.printStackTrace();
		}finally {
			ActivityLeakSolution.fixAlertMesssageLeak(this);
		}
	}

	public void avoiddismiss(){
		avoiddismiss = true;
	}
	
	public NightModeUtil getNightModeUtil(){
		return mNMC;
	}

	public void setPositiveListener(int resId, OnClickListener listener) {
		setPositiveListener(mContext.getText(resId), listener);
	}

	// @Override
	// public void cancel() {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void dismiss() {
	// // TODO Auto-generated method stub
	//
	// }

	public void setNegativeListener(int resId, OnClickListener listener) {
		setNegativeListener(mContext.getText(resId), listener);
	}

	public void setNeutralListener(int resId, OnClickListener listener) {
		setNeutralListener(mContext.getText(resId), listener);
	}

	public void setPositiveListener(CharSequence msg, OnClickListener listener) {
		setButton(DialogInterface.BUTTON_POSITIVE, msg, listener);
	}

	public void setNegativeListener(CharSequence msg, OnClickListener listener) {
		setButton(DialogInterface.BUTTON_NEGATIVE, msg, listener);
	}

	public void setNeutralListener(CharSequence msg, OnClickListener listener) {
		setButton(DialogInterface.BUTTON_NEUTRAL, msg, listener);
	}

	public static class Builder {
		private final AlertController.AlertParams P;

		/**
		 * Constructor using a context for this builder and the
		 * {@link AlertDialog} it creates.
		 */
		public Builder(Context context) {
			P = new AlertController.AlertParams(context);
		}

		/**
		 * Set the title using the given resource id.
		 *
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setTitle(int titleId) {
			P.mTitle = P.mContext.getText(titleId);
			return this;
		}

		/**
		 * Set the title displayed in the {@link Dialog}.
		 *
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setTitle(CharSequence title) {
			P.mTitle = title;
			return this;
		}

		public Builder setView(View content) {
			P.mView = content;
			return this;
		}

		/**
		 * Set the message to display using the given resource id.
		 *
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setMessage(int messageId) {
			P.mMessage = P.mContext.getText(messageId);
			return this;
		}

		/**
		 * Set the message to display.
		 *
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setMessage(CharSequence message) {
			P.mMessage = message;
			return this;
		}

		/**
		 * Set the resource id of the {@link Drawable} to be used in the title.
		 *
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setIcon(int iconId) {
			P.mIconId = iconId;
			return this;
		}

		/**
		 * Set the {@link Drawable} to be used in the title.
		 *
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setIcon(Drawable icon) {
			P.mIcon = icon;
			return this;
		}

		/**
		 * Set a listener to be invoked when the positive button of the dialog
		 * is pressed.
		 *
		 * @param textId
		 *            The resource id of the text to display in the positive
		 *            button
		 * @param listener
		 *            The {@link DialogInterface.OnClickListener} to use.
		 *
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setPositiveButton(int textId,
				final OnClickListener listener) {
			P.mPositiveButtonText = P.mContext.getText(textId);
			P.mPositiveButtonListener = listener;
			return this;
		}

		/**
		 * Set a listener to be invoked when the positive button of the dialog
		 * is pressed.
		 *
		 * @param text
		 *            The text to display in the positive button
		 * @param listener
		 *            The {@link DialogInterface.OnClickListener} to use.
		 *
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setPositiveButton(CharSequence text,
				final OnClickListener listener) {
			P.mPositiveButtonText = text;
			P.mPositiveButtonListener = listener;
			return this;
		}

		/**
		 * Set a listener to be invoked when the negative button of the dialog
		 * is pressed.
		 *
		 * @param textId
		 *            The resource id of the text to display in the negative
		 *            button
		 * @param listener
		 *            The {@link DialogInterface.OnClickListener} to use.
		 *
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setNegativeButton(int textId,
				final OnClickListener listener) {
			P.mNegativeButtonText = P.mContext.getText(textId);
			P.mNegativeButtonListener = listener;
			return this;
		}

		/**
		 * Set a listener to be invoked when the negative button of the dialog
		 * is pressed.
		 *
		 * @param text
		 *            The text to display in the negative button
		 * @param listener
		 *            The {@link DialogInterface.OnClickListener} to use.
		 *
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setNegativeButton(CharSequence text,
				final OnClickListener listener) {
			P.mNegativeButtonText = text;
			P.mNegativeButtonListener = listener;
			return this;
		}

		/**
		 * Set a listener to be invoked when the neutral button of the dialog is
		 * pressed.
		 *
		 * @param textId
		 *            The resource id of the text to display in the neutral
		 *            button
		 * @param listener
		 *            The {@link DialogInterface.OnClickListener} to use.
		 *
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setNeutralButton(int textId,
				final OnClickListener listener) {
			P.mNeutralButtonText = P.mContext.getText(textId);
			P.mNeutralButtonListener = listener;
			return this;
		}

		/**
		 * Set a listener to be invoked when the neutral button of the dialog is
		 * pressed.
		 *
		 * @param text
		 *            The text to display in the neutral button
		 * @param listener
		 *            The {@link DialogInterface.OnClickListener} to use.
		 *
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setNeutralButton(CharSequence text,
				final OnClickListener listener) {
			P.mNeutralButtonText = text;
			P.mNeutralButtonListener = listener;
			return this;
		}

		/**
		 * Sets whether the dialog is cancelable or not default is true.
		 *
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setCancelable(boolean cancelable) {
			P.mCancelable = cancelable;
			return this;
		}

		/**
		 * Sets the callback that will be called if the dialog is canceled.
		 *
		 * @see #setCancelable(boolean)
		 *
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setOnCancelListener(OnCancelListener onCancelListener) {
			P.mOnCancelListener = onCancelListener;
			return this;
		}

		/**
		 * Sets the callback that will be called if a key is dispatched to the
		 * dialog.
		 *
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setOnKeyListener(OnKeyListener onKeyListener) {
			P.mOnKeyListener = onKeyListener;
			return this;
		}

		/**
		 * Set a list of items to be displayed in the dialog as the content, you
		 * will be notified of the selected item via the supplied listener. This
		 * should be an array type i.e. R.array.foo
		 *
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setItems(int itemsId, final OnClickListener listener) {
			P.mItems = P.mContext.getResources().getTextArray(itemsId);
			P.mOnClickListener = listener;
			return this;
		}

		/**
		 * Set a list of items to be displayed in the dialog as the content, you
		 * will be notified of the selected item via the supplied listener.
		 *
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setItems(CharSequence[] items,
				final OnClickListener listener) {
			P.mItems = items;
			P.mOnClickListener = listener;
			return this;
		}

		/**
		 * Set a list of items, which are supplied by the given
		 * {@link ListAdapter}, to be displayed in the dialog as the content,
		 * you will be notified of the selected item via the supplied listener.
		 *
		 * @param adapter
		 *            The {@link ListAdapter} to supply the list of items
		 * @param listener
		 *            The listener that will be called when an item is clicked.
		 *
		 * @return This Builder object to allow for chaining of calls to set
		 *         methods
		 */
		public Builder setAdapter(final ListAdapter adapter,
				final OnClickListener listener) {
			P.mAdapter = adapter;
			P.mOnClickListener = listener;
			return this;
		}


		/**
		 * 添加List 选项卡
		 * @param items
		 * @param checkedItem
		 * @param listener
		 */
		public Builder setSingleChoiceItems(CharSequence[] items, int checkedItem, OnClickListener listener) {
			//todo 添加点选卡
			P.mItems = items;
			P.mOnClickListener = listener;
			P.mCheckedItem = checkedItem;
			P.mIsSingleChoice = true;
			return this;
		}

		/**
		 * Custom Title
		 * @param customTitleView
		 */
		public Builder setCustomTitle(View customTitleView) {
			P.mCustomTitleView = customTitleView;
			return this;
		}

		/**
		 * Creates a {@link AlertDialog} with the arguments supplied to this
		 * builder. It does not {@link Dialog#show()} the dialog. This allows
		 * the user to do any extra processing before displaying the dialog. Use
		 * {@link #show()} if you don't have any other processing to do and want
		 * this to be created and displayed.
		 */
		public AlertDialog create() {
			final AlertDialog dialog = new AlertDialog(P.mContext);
			dialog.setCanceledOnTouchOutside(true);
			P.apply(dialog.mAlert);
			dialog.setCancelable(P.mCancelable);
			dialog.setOnCancelListener(P.mOnCancelListener);
			if (P.mOnKeyListener != null) {
				dialog.setOnKeyListener(P.mOnKeyListener);
			}
			return dialog;
		}

		/**
		 * Creates a {@link AlertDialog} with the arguments supplied to this
		 * builder and {@link Dialog#show()}'s the dialog.
		 */
		public AlertDialog show() {
			AlertDialog dialog = create();
			dialog.show();
			return dialog;
		}

	}

	// public void setOnCancelListener(OnCancelListener listener) {
	// setOnCancelListener(listener);
	// }
	//
	// public void setCanceledOnTouchOutside(AlertDialog dialog,
	// boolean cancelable) {
	// dialog.setCanceledOnTouchOutside(cancelable);
	// }
}
