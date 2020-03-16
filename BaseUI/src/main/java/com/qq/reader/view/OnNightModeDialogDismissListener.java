package com.qq.reader.view;

import android.content.DialogInterface;

import com.qq.reader.common.utils.NightModeUtil;

/**
 * 一个basedialog的弹窗，使用了夜间模式后，需要使用这个dismisslistener，用于移除夜间模式的蒙版
 */
public abstract class OnNightModeDialogDismissListener implements DialogInterface.OnDismissListener{

	/**
	 * 实现此方法，用于移除夜间模式蒙版
	 * @return
	 */
	abstract public NightModeUtil getNightMode_Util();
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		NightModeUtil nightModeUtil = getNightMode_Util();
		if (nightModeUtil!=null) {
			nightModeUtil.removeMask();
		}
	}
	
}