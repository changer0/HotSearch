package com.qq.reader.view.linearmenu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.qq.reader.baseui.R;
import com.qq.reader.view.BaseDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author ivanyang
 * 
 */
public abstract class LinearBaseMenu extends BaseDialog {
	// private PopupWindow mMenuWindow;
	// private View mRoot;
	public static final int CLOSE = 1001;
	public static final int OPEN = 1002;
	protected ListView mListView;
	protected MenuAdapter mAdapter;
	protected Context mContext;
	private Handler mHandler;
	protected OnLinearMenuListener mMenuListener;

	public LinearBaseMenu(final Activity act) {
		mContext = act;

		LayoutInflater inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		mListView = (ListView) inflater.inflate(R.layout.linear_readermenu,
				null);

		initDialog(act, mListView, R.layout.linear_readermenu, true, false,
				true);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.gravity = Gravity.CENTER;
        mDialog.getWindow().setAttributes(lp);
        mDialog.getWindow()
				.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}

	protected void initAdapter() {
		mAdapter = new MenuAdapter(mContext);
		mListView.setAdapter(mAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (id >= 0) {
					if (mMenuListener != null) {
						mMenuListener.onMenuItemSelected((int) id,
								(Bundle) view.getTag());
					}
					cancel();
				}
			}

		});
	}

	public OnLinearMenuListener getMenuListener() {
		return mMenuListener;
	}

	public View getView() {
		return mListView;
	}

	public void setMenuListener(OnLinearMenuListener menuListener) {
		mMenuListener = menuListener;
	}

	public void setHandler(Handler handler) {
		mHandler = handler;
	}

	public void add(int itemid, String name, Bundle b) {
		add(itemid, name, false, b);
	}

	public String getItemNameById(int id) {
		return mAdapter.getItemNameById(id);
	}
	
	public void add(int itemid, String name, boolean show, Bundle b) {
		mAdapter.addItem(itemid, name, show, b);
	}

	public void addAt(int location, int itemid, String name, Bundle b) {
		mAdapter.addAt(location, itemid, name, false, b);
	}

	public void remove(int itemid) {
		mAdapter.removeByItemId(itemid);
	}

	public void modifyName(int itemid, String name) {
		mAdapter.modifyName(itemid, name);
	}

	public void clear() {
		mAdapter.clear();
	}
	public int getMenuCount() {
		return mAdapter.getCount();
	}

	protected class MenuAdapter extends BaseAdapter {

		List<String> mMenuNameList;
		List<Integer> mItemId;
		List<Boolean> mNewShow;
		List<Bundle> mBundles;
		Context mContext;
		boolean mUpdataState = false;
		boolean mSoftState = false;

		public MenuAdapter(Context context) {
			mContext = context;
			mMenuNameList = new ArrayList<String>();
			mItemId = new ArrayList<Integer>();
			mNewShow = new ArrayList<Boolean>();
			mBundles = new ArrayList<Bundle>();
		}

		public boolean addItem(int itemid, String name, boolean show, Bundle b) {
			return mMenuNameList.add(name) && mItemId.add(new Integer(itemid))
					&& mNewShow.add(new Boolean(show)) && mBundles.add(b);
		}

		public String getItemNameById(int id) {
			for (int i = 0; mMenuNameList != null && mMenuNameList.size() == mItemId.size()
								&&mItemId != null && i < mItemId.size();i++) {
				int menuId = mItemId.get(i);
				if (menuId == id && i < mMenuNameList.size()) {
					return  mMenuNameList.get(i);
				}
			}
			return null;
		}
		public void addAt(int location, int itemid, String name, boolean show,
				Bundle b) {
			if (mItemId.contains(new Integer(itemid)))
				return;
			mMenuNameList.add(location, name);
			mItemId.add(location, new Integer(itemid));
			mNewShow.add(location, new Boolean(show));
			mBundles.add(location, b);
			notifyDataSetChanged();
		}

		public void removeByItemId(int itemid) {
			int location = mItemId.indexOf(new Integer(itemid));
			if (location >= 0 && location < mItemId.size()) {
				remove(location);
			}
		}

		public void remove(int location) {
			mMenuNameList.remove(location);
			mItemId.remove(location);
			mNewShow.remove(location);
			mBundles.remove(location);
			notifyDataSetChanged();
		}
        
		public void clear() {
			mMenuNameList.clear();
			mItemId.clear();
			mNewShow.clear();
			mBundles.clear();
		}
		
		public void modifyName(int itemid, String name) {
			int location = mItemId.indexOf(new Integer(itemid));
			if (location >= 0 && location < mItemId.size()) {
				mMenuNameList.set(location, name);
				notifyDataSetChanged();
			}
		}

		// public void setItem(int itemid, String name, boolean show) {
		// for (int i = 0; i < mItemId.size(); i++) {
		// if (getItemId(i) == itemid) {
		// mMenuNameList.set(i, name);
		// mNewShow.set(i, show);
		// break;
		// }
		// }
		// }

		@Override
		public int getCount() {
			return mMenuNameList.size();
		}

		@Override
		public String getItem(int position) {
			return mMenuNameList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return mItemId.get(position).intValue();
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LinearBaseMenuItemView mItemView;
			mItemView = (LinearBaseMenuItemView) LayoutInflater.from(mContext)
					.inflate(R.layout.linear_menuitem, null);
			mItemView.init();

			mItemView.setText(mMenuNameList.get(position));
			mItemView.setNewShow(mNewShow.get(position));
			mItemView.setTag(mBundles.get(position));
			Bundle bundle = mBundles.get(position);

//			if (bundle != null && bundle.getBoolean("setrightview", false)) {
//				int resourceid = bundle.getInt("resourceid");
//				LayoutInflater inflater = (LayoutInflater) mContext
//						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//				View view = inflater.inflate(resourceid, null);
//				int openviewid = bundle.getInt("openview");
//				final View openview = view.findViewById(openviewid);
//				int closeviewid = bundle.getInt("closeview");
//				final View closeview = view.findViewById(closeviewid);
//				mUpdataState = CommonConfig.getBookNoticeState();
//				// mSoftState = UserConfig.getBookShelfSortBy(mContext);
//				if (openview != null && closeview != null) {
//					switch (mItemId.get(position)) {
//					case BookShelfFragment.MENU_ID_NONE:
//						openview.setSelected(mUpdataState);
//						closeview.setSelected(!mUpdataState);
//						break;
//					case BookShelfFragment.MENU_ID_SOFR_WITHTIME:
//						openview.setSelected(mSoftState);
//						closeview.setSelected(!mSoftState);
//						break;
//					default:
//						break;
//					}
//					openview.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							switch (mItemId.get(position)) {
//							case BookShelfFragment.MENU_ID_NONE:
//								mUpdataState = true;
//								openview.setSelected(mUpdataState);
//								closeview.setSelected(!mUpdataState);
//								CommonConfig.setBookNoticeState(
//										mUpdataState);
//								if (mHandler != null) {
//									Message msg = new Message();
//									msg.what = BookShelfFragment.MENU_ID_NONE;
//									msg.arg1 = OPEN;
//									mHandler.sendMessage(msg);
//								}
//								break;
//							case BookShelfFragment.MENU_ID_SOFR_WITHTIME:
//								mSoftState = true;
//								openview.setSelected(mSoftState);
//								closeview.setSelected(!mSoftState);
//								CommonConfig.setBookShelfSortBy(
//										mSoftState ? 0 : 1);
//								if (mHandler != null) {
//									Message msg = new Message();
//									msg.what = BookShelfFragment.MENU_ID_SOFR_WITHTIME;
//									msg.arg1 = OPEN;
//									mHandler.sendMessage(msg);
//								}
//								break;
//							}
//						}
//					});
//					closeview.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//
//							switch (mItemId.get(position)) {
//							case BookShelfFragment.MENU_ID_NONE:
//								mUpdataState = false;
//								openview.setSelected(mUpdataState);
//								closeview.setSelected(!mUpdataState);
//								CommonConfig.setBookNoticeState(
//										mUpdataState);
//								if (mHandler != null) {
//									Message msg = new Message();
//									msg.what = BookShelfFragment.MENU_ID_NONE;
//									msg.arg1 = CLOSE;
//									mHandler.sendMessage(msg);
//								}
//								break;
//							case BookShelfFragment.MENU_ID_SOFR_WITHTIME:
//								mSoftState = false;
//								openview.setSelected(mSoftState);
//								closeview.setSelected(!mSoftState);
//								// UserConfig.setSoftBookState(mContext,
//								// mSoftState);
//								if (mHandler != null) {
//									Message msg = new Message();
//									msg.what = BookShelfFragment.MENU_ID_SOFR_WITHTIME;
//									msg.arg1 = CLOSE;
//									mHandler.sendMessage(msg);
//								}
//								break;
//							}
//						}
//					});
//				}
//				LinearLayout layout = mItemView.getRightLayout();
//				if (layout.getChildCount() <= 0) {
//					layout.addView(view);
//					layout.setVisibility(View.VISIBLE);
//				}
//			}
			return mItemView;
		}
	}

	public interface OnLinearMenuListener {
		public boolean onMenuItemSelected(int id, Bundle b);
	}
}
