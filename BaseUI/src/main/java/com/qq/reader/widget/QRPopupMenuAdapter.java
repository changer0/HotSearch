package com.qq.reader.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qq.reader.baseui.R;

public class QRPopupMenuAdapter extends BaseAdapter {
	private final static int ITEM_RESID =R.layout.webpage_popupmenu_item;
	public List<String> mMenuNameList;
	public List<Integer> mItemId;
	public ArrayList<QRPopupMenuNode> mNodelist;
	protected Context mContext;
	public int mSelectedIndex = 0;
	private int mItemResId = ITEM_RESID;
	static class QRPopupMenuNode{
		int iconid;
		String titleinfo;
		public QRPopupMenuNode(int id, String info) {
			iconid = id;
			titleinfo = info;
		}
	}
	public QRPopupMenuAdapter(Context context) {
		mContext = context;
		mMenuNameList = new ArrayList<String>();
		mItemId = new ArrayList<Integer>();
	}


	public QRPopupMenuAdapter(Context context, int itemresid) {
		mContext = context;
		mMenuNameList = new ArrayList<String>();
		mItemId = new ArrayList<Integer>();
		mItemResId = itemresid;
		mNodelist = new ArrayList<QRPopupMenuAdapter.QRPopupMenuNode>();
	}
	
	public boolean addItem(int itemid, String name, boolean show, Bundle b) {
		return mMenuNameList.add(name) && mItemId.add(Integer.valueOf(itemid));
	}
	
//	public boolean addItem(int itemid, String name, int iconid, String info) {
//		return mMenuNameList.add(name) && mItemId.add(Integer.valueOf(itemid)) && mNodelist.add(new QRPopupMenuNode(iconid, info));
//	}

	
	public void removeAllItems() {
		mMenuNameList = new ArrayList<String>();
		mItemId = new ArrayList<Integer>();
	}

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
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView name;
		ImageView selected;
//		ImageView icon;
//		TextView info;
		View divider;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					mItemResId, null);
		}
		name = (TextView) convertView
				.findViewById(R.id.webpage_popupmenu_item_name);
		selected = (ImageView) convertView
				.findViewById(R.id.webpage_popupmenu_item_selected);
//		icon = (ImageView) convertView.findViewById(R.id.webpage_popupmenu_item_icon);
//		info = (TextView) convertView.findViewById(R.id.webpage_popupmenu_item_info);
		divider = convertView.findViewById(R.id.webpage_popupmenu_item_divider);
//		if(mNodelist!= null && mNodelist.size() >0){
//			if(icon != null ){
//				icon.setImageResource(mNodelist.get(position).iconid);
//			}
//			if(info != null){
//				info.setText(mNodelist.get(position).titleinfo);
//			}
//		}
		name.setText(mMenuNameList.get(position));
		selected.setVisibility(mSelectedIndex == position ? View.VISIBLE
				: View.GONE);
//		name.setSelected(mSelectedIndex == position);
		divider.setVisibility(position == getCount()-1 ? View.GONE : View.VISIBLE);
		return convertView;
	}
}
