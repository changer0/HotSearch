package com.qq.reader.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.qq.reader.activity.ReaderBaseActivity;
import com.qq.reader.common.login.define.LoginConstant;
import com.qq.reader.common.utils.CommonConstant;
import com.qq.reader.core.BaseApplication;
import com.qq.reader.core.utils.BaseContract;
import com.qq.reader.core.utils.WeakReferenceHandler;
import com.qq.reader.listener.OnPageSelectListener;
import com.tencent.mars.xlog.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;


public abstract class BaseFragment extends Fragment implements Callback
		, OnPageSelectListener, FragmentUserVisibleController.UserVisibleCallback {
	private final static String DISPLAY_STATUS = "display_status";
	public final static int DELAY = 0;
	private final static int LAOD = 1001;
	private int delay = DELAY;
	private HashMap<String, Object> args;
	// private Message msg = new Message();
	private LoadAsyncTask mLoadTask = new LoadAsyncTask();
	private boolean hasDoCreateView = true;
	private boolean mNotExcuteWhenCreate = true;
	private boolean mHasLoad = false;
	private long thistime;
	protected String mTitle = "";
	private boolean isDisplay = true;
	protected WeakReferenceHandler mHandler;
	private Handler mLoadHandler = new Handler(Looper.getMainLooper()) {
		public void handleMessage(Message msg) {
			// Log.d("xcz","HandleMessage  " + msg.what +" name " +
			// getArguments().get("titlename") + "   url  " +
			// getArguments().get("book_url"));
			if (msg.what == LAOD) {
				if (hasDoCreateView) {
					mNotExcuteWhenCreate = true;
					if (mLoadTask.getStatus() != Status.FINISHED) {
						mLoadTask.execute();
					}
				} else {
					mNotExcuteWhenCreate = false;
				}
			}
		}
	};

	BroadcastReceiver loginOkReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i("BaseFragment", "loginOkReceiver");
			if (intent.hasExtra("loginSuccess")) {
				boolean isLoginSuccess = intent.getBooleanExtra("loginSuccess", false);
				Log.i("BaseFragment", "loginOkReceiver   isLoginSuccess = " + isLoginSuccess);
				loginFinish(isLoginSuccess);
			}
		}
	};

	private BroadcastReceiver mLoginOutReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			logOutFinish();
		}
	};

	public Handler getHandler() {
		return mHandler;
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return handleMessageImp(msg);
	}

	protected boolean handleMessageImp(Message msg) {
		if (!isActive())
			return true;
		return false;
	}

	public Application getApplicationContext() {
		return BaseApplication.getInstance();
	}

	// private Handler mHandler = new Handler() {
	// public void handleMessage(Message msg) {
	// handleMessageImp(msg);
	// };
	// };

	public ReaderBaseActivity getBaseActivity() {
		return (ReaderBaseActivity) getActivity();
	}

	private FragmentUserVisibleController userVisibleController;

	public BaseFragment() {
		userVisibleController = new FragmentUserVisibleController(this, this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		thistime = System.currentTimeMillis();
		super.onCreate(savedInstanceState);
		mHandler = new WeakReferenceHandler(this);
		Log.d("fragment", this.getClass().getSimpleName());
		//fragment被回收时做隐藏和显示的恢复
		if (savedInstanceState != null) {
			boolean isHidden = savedInstanceState.getBoolean(DISPLAY_STATUS);
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			if (isHidden) {
				ft.hide(this);
			} else {
				ft.show(this);
			}
			ft.commitAllowingStateLoss();
		}
		setHasOptionsMenu(true);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		userVisibleController.activityCreated();
	}

	@Override
	public void onResume() {
		super.onResume();
		userVisibleController.resume();
	}

	@Override
	public void onPause() {
		super.onPause();
		userVisibleController.pause();
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		userVisibleController.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void setWaitingShowToUser(boolean waitingShowToUser) {
		userVisibleController.setWaitingShowToUser(waitingShowToUser);
	}

	@Override
	public boolean isWaitingShowToUser() {
		return userVisibleController.isWaitingShowToUser();
	}

	@Override
	public boolean isVisibleToUser() {
		return userVisibleController.isVisibleToUser();
	}

	@Override
	public void callSuperSetUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
	}

	@Override
	public void onVisibleToUserChanged(boolean isVisibleToUser, boolean invokeInResumeOrPause) {

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		android.util.Log.e("test", "++++++++++");
		super.onCreateOptionsMenu(menu, inflater);
	}

	private WeakReference<FragmentActivity> weakMActivity;

	public FragmentActivity getActivityAfterDettash() {
		return weakMActivity.get();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		weakMActivity = new WeakReference<FragmentActivity>((FragmentActivity) activity);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(DISPLAY_STATUS, isHidden());
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		onPreLoad();
		hasDoCreateView = true;
		if (!mNotExcuteWhenCreate) {
			if (mLoadTask.getStatus() != Status.FINISHED) {
				mLoadTask.execute();
			}
		}

		Activity context = getActivity();
		if (context instanceof ReaderBaseActivity) {
			IntentFilter filterwx = new IntentFilter(LoginConstant.LOGIN_OK_BROADCAST);
			context.registerReceiver(loginOkReceiver, filterwx, CommonConstant.BROADCAST_PERMISSION, null);
			context.registerReceiver(mLoginOutReceiver, new IntentFilter(LoginConstant.LOGOUT_BROADCAST), CommonConstant.BROADCAST_PERMISSION, null);
		}
		return view;
	}

	public HashMap getHashArguments() {
		return args;
	}

	public void setHashArguments(HashMap map) {
		if (map != null) {
			args = (HashMap<String, Object>) map.clone();
		}
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public void executeLoadDataWithDelay() {
		if (mHasLoad) {
			doSomeRefreshThing();
			return;
		}
		mHasLoad = true;
		if (mLoadHandler != null) {
			mLoadHandler.sendMessageDelayed(mLoadHandler.obtainMessage(LAOD), delay);
		}
	}

	public void executeLoadData() {
		if (mHasLoad) {
			return;
		}
		mHasLoad = true;
		if (mLoadHandler != null) {
			mLoadHandler.sendMessage(mLoadHandler.obtainMessage(LAOD));
		}
	}

	public void cancleLoadData() {
		mHasLoad = false;
		if (mLoadHandler != null) {
			mLoadHandler.removeMessages(LAOD);
		}
	}

	/**
	 * 当前fragment是否是显示状态，未全部实现
	 * 顶部滑动tag，参考NativeBookStoreTwoLevelActivity
	 * .onPageSelected实现
	 *
	 * @return 当前fragment是否是显示状态
	 */
	public boolean isDisplay() {
		return isDisplay;
	}

	public void setDisplay(boolean display) {
		Log.d("devStat", getClass().getSimpleName() + " setDisplay " + display);
		isDisplay = display;
	}

	private class LoadAsyncTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected void onPostExecute(Void result) {
			onLoadFinished();
//			if (Debug.isShowStartTime) {
//				long now = System.currentTimeMillis();
//				if (now - thistime > 50) {
//					Toast.makeText(
//							ReaderApplication.getInstance()
//									.getApplicationContext(),
//							BaseFragment.this.getClass().getName() + " : "
//									+ mTitle + " 启动时间  " + (now - thistime)
//									+ "ms", Toast.LENGTH_SHORT).show();
//				}
//			}
		}

		@Override
		protected Void doInBackground(Void... params) {
			onLoading();

			return null;
		}
	}

	public abstract void onPreLoad();

	public abstract void onLoading();

	public abstract void onLoadFinished();

	public void doSomeRefreshThing() {

	}

	public void refreshFragment() {
		new LoadAsyncTask().execute();
	}

	protected FragmentLoadListener mFragmentLoadListener;

	public void setFragmentLoadListener(FragmentLoadListener l) {
		mFragmentLoadListener = l;
	}

	public interface FragmentLoadListener {
		public void onLoadingFinished(Bundle bundle);
		// public void onPrepareLoad(Bundle bundle);
		// public void onLoading(int progress);
	}

	public void refresh() {

	}

	public boolean onBackPress() {
		return false;
	}

	public void onDataNotify() {

	}

	///////////////////////////////////////////////////////////////////////////
	// 处理MyAlertDialogFragment
	///////////////////////////////////////////////////////////////////////////

	protected boolean isAutoUpdate = true;
	protected boolean isCheckShowDialog = true;
	protected Context mContext;

	// /////////////////////////////////////////////////////////////////////
	// //////////////////////////FragmentDialog/////////////////////////////
	// /////////////////////////////////////////////////////////////////////
//	private final static String BUNDLE_DIALOG_TYPE = "BUNDLE_DIALOG_TYPE";
//	private final static String BUNDLE_DIALOG_BUNDLE = "BUNDLE_DIALOG_BUNDLE";


//	public static class MyAlertDialogFragment extends DialogFragment {
//
//		public MyAlertDialogFragment() {
//
//		}
//
//		public static MyAlertDialogFragment newInstance(int type, Bundle bundle) {
//			MyAlertDialogFragment frag = new MyAlertDialogFragment();
//			Bundle args = new Bundle();
//			args.putInt(BUNDLE_DIALOG_TYPE, type);
//			if (bundle != null) {
//				args.putBundle(BUNDLE_DIALOG_BUNDLE, bundle);
//			}
//			frag.setArguments(args);
//			return frag;
//		}
//
//		@Override
//		public Dialog onCreateDialog(Bundle savedInstanceState) {
//			if (getActivity() != null) {
//				int type = getArguments().getInt(BUNDLE_DIALOG_TYPE);
//				Bundle b = getArguments().getBundle(BUNDLE_DIALOG_BUNDLE);
//				Dialog d = createDialog(type, b);
//				return d;
//			}
//			return null;
//		}
//
//		@Override
//		public void onCancel(DialogInterface di) {
////			onFragmentDialgoCancel(di);
//		}
//
//		@Override
//		public void show(FragmentManager manager, String tag) {
//			setDialogFramentField();
//			FragmentTransaction ft = manager.beginTransaction();
//			ft.add(this, tag);
//			ft.commitAllowingStateLoss();
//		}
//
//		private void setDialogFramentField() {
//			try {
//				Class<DialogFragment> viewClass = DialogFragment.class;
//
//				Field dismissFiled = viewClass.getDeclaredField("mDismissed");
//				dismissFiled.setAccessible(true);
//				dismissFiled.setBoolean(this, false);
//
//				Field showBymeField = viewClass.getDeclaredField("mShownByMe");
//				showBymeField.setAccessible(true);
//				showBymeField.setBoolean(this, true);
//
//			} catch (Exception e) {
//				Log.printErrStackTrace("MyAlertDialogFragment", e, null, null);
//				e.printStackTrace();
//			}
//		}
//	}


	//有的页面不需要显示无网提示条
	protected boolean isShowNetErrorView = true;

	public void setNeedShowNetErrorView(boolean showView) {
		isShowNetErrorView = showView;
	}

	public boolean isActive() {
		return isAdded() && getActivity() != null && !getActivity().isFinishing();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		try {
			Activity activity = getActivity();
			if (activity instanceof ReaderBaseActivity) {
				activity.unregisterReceiver(loginOkReceiver);
				activity.unregisterReceiver(mLoginOutReceiver);
			}
		} catch (Exception e) {
			Log.printErrStackTrace("BaseFragment", e, null, null);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacksAndMessages(null);
		for (int i = 0; i < presenterList.size(); i++) {
			presenterList.get(i).release();
		}
		presenterList.clear();
	}

	public void addPresenterToList(BaseContract.BasePresenter presenter) {
		presenterList.add(presenter);
	}

	ArrayList<BaseContract.BasePresenter> presenterList = new ArrayList<BaseContract.BasePresenter>();

	@Override
	public void onPageSelected(boolean select) {
		Log.d("devStat", "BaseFragment onPageSelected " + select);
		isDisplay = select;
	}

	@Override
	public void onPageSelected(boolean isSelected, boolean isNeedStat) {
	}

	protected void loginFinish(boolean isLoginSuccess) {

	}

	protected void logOutFinish() {

	}
}
