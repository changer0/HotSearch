package com.qq.reader.common.memoryleak;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qq.reader.common.utils.FlavorUtils;
import com.qq.reader.view.AlertController;
import com.qq.reader.view.AlertDialog;
import com.tencent.mars.xlog.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * @author acolorzhang
 *
 */
public class ActivityLeakSolution {
	private final static String TAG = "ActivityLeakSolution";

	/**
	 * @author sodinochen
	 * @param destContext
	 */
	public static void fixInputMethodManagerLeak(Context destContext) {
		if (destContext == null) {
			return;
		}
		// 解决bug:http://tapd.oa.com/10066461/bugtrace/bugs/view?bug_id=1010066461049548927&url_cache_key=e8baf96f24d40b6f946bdf286c6be58e
		InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm == null) {
			return;
		}
		// Method imm_windowDismissed =
		// imm.getClass().getDeclaredMethod("windowDismissed",
		// IBinder.class);
		// if (imm_windowDismissed != null) {
		// if (imm_windowDismissed.isAccessible() == false) {
		// imm_windowDismissed.setAccessible(true);
		// }
		// imm_windowDismissed.invoke(imm,
		// this.getWindow().getDecorView().getWindowToken());
		// }
		// Method imm_startGettingWindowFocus =
		// imm.getClass().getDeclaredMethod("startGettingWindowFocus",
		// View.class); // 设置mCurRootView值为null
		// if (imm_startGettingWindowFocus != null) {
		// if (imm_startGettingWindowFocus.isAccessible() == false) {
		// imm_startGettingWindowFocus.setAccessible(true);
		// }
		// imm_startGettingWindowFocus.invoke(imm, new Object[]{null});
		// }

		// 以上注释掉的是google来的,但效果太差了,仍不回收,mat path to gc结果为unknow
		// 来个粗暴的,直接将path to gc的链路剪断:mCurRootView mServedView mNextServedView
		String [] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
		if (FlavorUtils.isCommonUI()){
			//mLastSrvView为华为系统特有字段
			arr = new String[]{"mLastSrvView","mCurRootView", "mServedView", "mNextServedView"};
		}
		Field f = null;
		Object obj_get = null;
		for (int i = 0;i < arr.length;i ++) {
			String param = arr[i];
			try{
				f = imm.getClass().getDeclaredField(param);
				if (f.isAccessible() == false) {
					f.setAccessible(true);
				}
				obj_get = f.get(imm);
				if (obj_get != null && obj_get instanceof View) {
					View v_get = (View) obj_get;
					if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
						f.set(imm, null); // 置空，破坏掉path to gc节点
						Log.d(TAG, "fixInputMethodManagerLeak break,get_context=" + v_get.getContext() + " dest_context=" + destContext+" param="+param);
					} else {
						// 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
						Log.d(TAG, "fixInputMethodManagerLeak break, context is not suitable, get_context=" + v_get.getContext() + " dest_context=" + destContext+" param="+param);
						break;
					}
				}
			}catch(Throwable t){
//				Log.printErrStackTrace("ActivityLeakSolution", t, null, null);
				t.printStackTrace();
			}
		}
	}

	/**
	 * 修复bug:http://tapd.oa.com/10066461/bugtrace/bugs/view?bug_id=1010066461049555451&url_cache_key=e8baf96f24d40b6f946bdf286c6be58e
	 * */
	public static void fixMesssageLeak(Dialog dlg) {
		if (dlg == null) {
			return;
		}
		String arr [] = new String[] { "mDismissMessage", "mCancelMessage", "mShowMessage"};
		for (String param : arr) {
			try {
				Field f = Dialog.class.getDeclaredField(param);
				if (f == null) {
					continue;
				}
				if (f.isAccessible() == false) {
					f.setAccessible(true);
				}
				Object obj_get = f.get(dlg);
				if (obj_get instanceof Message) {
					Message msg = (Message) obj_get;
//					msg.recycle();// 清除各种target/callback/what
					// 这里会引起一个anr:http://tapd.oa.com/QzoneInQqAndroid/bugtrace/bugs/view?bug_id=1110065771049563223
					// 原因未明，就放弃使用msg.recycle()吧..
					// 简单处理，清掉obj、what就好了..
					if (msg.obj != null) {
						msg.obj = null;
						msg.what = 0; // 清除掉what，避免被send之后导致obj的NPE
					}
				}
			} catch (NoSuchFieldException e) {
				Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
				e.printStackTrace();
			} catch (Throwable tr) {
				Log.printErrStackTrace("ActivityLeakSolution", tr, null, null);
				tr.printStackTrace();
			}
		}
	}

	/**
	 * 修复bug:http://tapd.oa.com/10066461/bugtrace/bugs/view?bug_id=1010066461049555451&url_cache_key=e8baf96f24d40b6f946bdf286c6be58e
	 * */
	public static void fixAlertMesssageLeak(AlertDialog dlg) {
		if (dlg == null) {
			return;
		}
		String arr [] = new String[] { "mDismissMessage", "mCancelMessage", "mShowMessage"};
		for (String param : arr) {
			try {
				Field f = Dialog.class.getDeclaredField(param);
				if (f == null) {
					continue;
				}
				if (f.isAccessible() == false) {
					f.setAccessible(true);
				}
				Object obj_get = f.get(dlg);
				if (obj_get instanceof Message) {
					Message msg = (Message) obj_get;
//					msg.recycle();// 清除各种target/callback/what
					// 这里会引起一个anr:http://tapd.oa.com/QzoneInQqAndroid/bugtrace/bugs/view?bug_id=1110065771049563223
					// 原因未明，就放弃使用msg.recycle()吧..
					// 简单处理，清掉obj、what就好了..
					if (msg.obj != null) {
						msg.obj = null;
						msg.what = 0; // 清除掉what，避免被send之后导致obj的NPE
					}
				}
			} catch (NoSuchFieldException e) {
				Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
				e.printStackTrace();
			} catch (Throwable tr) {
				Log.printErrStackTrace("ActivityLeakSolution", tr, null, null);
				tr.printStackTrace();
			}
		}

		String alertParam  = "mAlert";
		String msgParam[] = new String[] { "mButtonPositiveMessage", "mButtonNegativeMessage"};
		try {
			Field f = AlertDialog.class.getDeclaredField(alertParam);
			if (f == null) {
				return;
			}
			if (f.isAccessible() == false) {
				f.setAccessible(true);
			}
			Object obj_get = f.get(dlg);
			if (obj_get instanceof AlertController) {
				AlertController alertController = (AlertController) obj_get;

				for (String param: msgParam) {
					try {
						Field f1 = AlertController.class.getDeclaredField(param);
						if (f1 == null) {
							continue;
						}
						if (f1.isAccessible() == false) {
							f1.setAccessible(true);
						}
						Object obj_get1 = f1.get(alertController);
						if (obj_get1 instanceof Message) {
							Message msg = (Message) obj_get1;
//					msg.recycle();// 清除各种target/callback/what
							// 这里会引起一个anr:http://tapd.oa.com/QzoneInQqAndroid/bugtrace/bugs/view?bug_id=1110065771049563223
							// 原因未明，就放弃使用msg.recycle()吧..
							// 简单处理，清掉obj、what就好了..
							if (msg.obj != null) {
								msg.obj = null;
								msg.what = 0; // 清除掉what，避免被send之后导致obj的NPE
							}
						}
					} catch (NoSuchFieldException e) {
						Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
						e.printStackTrace();
					} catch (Throwable tr) {
						Log.printErrStackTrace("ActivityLeakSolution", tr, null, null);
						tr.printStackTrace();
					}
				}

			}
		} catch (NoSuchFieldException e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			e.printStackTrace();
		} catch (Throwable tr) {
			Log.printErrStackTrace("ActivityLeakSolution", tr, null, null);
			tr.printStackTrace();
		}
	}
	
//	/**
//	 * fixBug:http://tapd.oa.com/androidQQ/bugtrace/bugs/view?bug_id=1010066461049562343&jumpfrom=RTX
//	 * */
//	public static void fixHtcTextSelectionManagerLeak(Context destContext) {
//		if (destContext == null) {
//			return;
//		}
//		String phoneInfo = android.os.Build.MANUFACTURER + android.os.Build.PRODUCT + android.os.Build.DEVICE;
//		phoneInfo = phoneInfo.toLowerCase();
//		if (phoneInfo.contains("htc") == false) {
//			return;
//		}
//		
//		String className = "com.htc.textselection.HtcTextSelectionManager";
//		try {
//			Class cls = Class.forName(className); // 如果没有该类，则直接就catch，退出了.
//			if (cls == null) {
//				return;
//			}
//			Field f = cls.getDeclaredField("sInstance");
//			if (f == null) {
//				return;
//			}
//			
//			Object obj_mInstance = f.get(null);
//			if (obj_mInstance == null) {
//				return;
//			} 
//			f = null;
//			
//			Object obj_mCurrentView = null;
//			f = cls.getDeclaredField("mCurrentView");
//			if (f == null) {
//				return;
//			}
//			obj_mCurrentView = f.get(obj_mInstance);
//			if (obj_mCurrentView instanceof View) {
//				Context context = ((View)obj_mCurrentView).getContext();
//				if (context == destContext) { // 目标一致
//					f.set(obj_mInstance, null);
//					if (QLog.isColorLevel()) {
//						QLog.d(ReflecterHelper.class.getSimpleName(), QLog.CLR, "fixHtcTextSelectionManagerLeak set null");
//					}
//				}
//			}
//		} catch (ClassNotFoundException e) {
////			e.printStackTrace();
//		} catch (NoSuchFieldException e) {
////			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
////			e.printStackTrace();
//		} catch (IllegalAccessException e) {
////			e.printStackTrace();
//		}
//	}
	

    public static void fixAudioManagerLeak(Context ctx)
	{
    	AudioManager am = (AudioManager) ctx.getSystemService(Context.AUDIO_SERVICE);
		Field field;
		try {
			field = am.getClass().getDeclaredField("mContext");
			field.setAccessible(true);
			field.set(am, null);
		} catch (NoSuchFieldException e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, e.toString());
		} catch (IllegalArgumentException e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, e.toString());
		} catch (IllegalAccessException e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, e.toString());
		}
	}
    

    /**
     * 下面两个方法为了干掉下面的泄漏
     * 修复这个东西：http://tapd.oa.com/10066461/bugtrace/bugs/view?bug_id=1010066461050089198
     * 系统已fix：https://android.googlesource.com/platform/frameworks/base/+/893d6fe%5E%21/#F0
    Class Name                                                                                                                   | Shallow Heap | Retained Heap
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
    mContext android.widget.EditText @ 0x411606d0                                                                                |          656 |         1,104
    '- this$0 android.widget.TextView$ChangeWatcher @ 0x41161840                                                                 |           16 |            16
       '- [0] java.lang.Object[13] @ 0x41162090                                                                                  |           64 |         2,304
          '- mSpans android.text.SpannableStringBuilder @ 0x41161d30                                                             |           48 |         2,576
             '- mSpanned, mSource android.text.method.ReplacementTransformationMethod$SpannedReplacementCharSequence @ 0x41161df8|           24 |         2,600
                '- mSpanned android.text.TextLine @ 0x410f7d00                                                                   |           72 |         2,768
                   '- [1] android.text.TextLine[3] @ 0x4102ac00                                                                  |           24 |         2,960
                      '- sCached class android.text.TextLine @ 0x40fbb7d8 System Class                                           |           16 |         3,064
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
     * 
     * @param tv
     */
    private static void fixTextWatcherLeak(TextView tv) {
    	if (tv == null) {
			return;
		}
    	//这个诡异的家伙，设置了Hint就会释放，奇怪
    	tv.setHint("");
		String param = "mListeners";
		try {
			Field f = TextView.class.getDeclaredField(param);
			if (f == null) {
				return;
			}
			if (f.isAccessible() == false) {
				f.setAccessible(true);
			}
			Object obj_get = f.get(tv);
			if (obj_get instanceof ArrayList) {
				ArrayList<TextWatcher> tws = (ArrayList<TextWatcher>) obj_get;
				Iterator<TextWatcher> iter = tws.iterator();
				while(iter.hasNext()) {
					TextWatcher tw = iter.next();
					iter.remove();
				}
			}
		} catch (NoSuchFieldException e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			e.printStackTrace();
		} catch (Throwable tr) {
			Log.printErrStackTrace("ActivityLeakSolution", tr, null, null);
			tr.printStackTrace();
		}
    }
    
    /**
     *
     */
    private static void fixTextWatcherLeak2() {
    	try {
			{
				Class<?> clazz = Class.forName("android.text.TextLine");
				Field sCachedF = clazz.getDeclaredField("sCached");
				sCachedF.setAccessible(true);
				Object[] sCached = (Object[])sCachedF.get(null);

				if (sCached == null) {
					throw new IllegalStateException("Failed to invoke currentActivityThread");
				}
				
				synchronized(sCached) {
					for (Object cache : sCached) {
						Field spanned = cache.getClass().getDeclaredField("mSpanned");
						spanned.setAccessible(true);
						Spanned mSpanned = (Spanned)spanned.get(cache);
	
						if (mSpanned != null) {
							spanned = mSpanned.getClass().getDeclaredField("mSpanned");
							spanned.setAccessible(true);
							mSpanned = (Spanned)spanned.get(mSpanned);
							if (mSpanned instanceof SpannableStringBuilder) {
								((SpannableStringBuilder) mSpanned).clearSpans();
							}
						}
					}
				}
			}

		} catch (ClassNotFoundException cnfe) {
			Log.printErrStackTrace("ActivityLeakSolution", cnfe, null, null);
			cnfe.printStackTrace();
		} catch (IllegalArgumentException iae) {
			Log.printErrStackTrace("ActivityLeakSolution", iae, null, null);
			iae.printStackTrace();
		} catch (IllegalAccessException iae) {
			Log.printErrStackTrace("ActivityLeakSolution", iae, null, null);
			iae.printStackTrace();
		} catch (NoSuchFieldException nsfe) {
			Log.printErrStackTrace("ActivityLeakSolution", nsfe, null, null);
			nsfe.printStackTrace();
		} catch (Exception e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			e.printStackTrace();
		}
    	
    }
    
    /**
     * 终极回收大法
     * @param app
     */
    public static void unbindDrawables(Activity app) {
    	if (app == null || app.getWindow() == null || app.getWindow().peekDecorView() == null) {
    		return ;
    	}
		try {
			View rootView = app.getWindow().peekDecorView().getRootView();
			unbindDrawablesAndRecyle(app, rootView);
		} catch (Throwable th) {
			Log.printErrStackTrace("ActivityLeakSolution", th, null, null);
			th.printStackTrace();
		}

	}
	
	private static void recycleView(View view) {
		if (view == null) {
			return ;
		}
		
		try {
			view.setOnClickListener(null);
		} catch (IncompatibleClassChangeError e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, "May cause dvmFindCatchBlock crash!");
			throw (IncompatibleClassChangeError) (new IncompatibleClassChangeError("May cause dvmFindCatchBlock crash!").initCause(e));
		} catch (Throwable mayHappen) {
			Log.printErrStackTrace("ActivityLeakSolution", mayHappen, null, null);
		}

		try {
			view.setOnCreateContextMenuListener(null);
		} catch (IncompatibleClassChangeError e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, "May cause dvmFindCatchBlock crash!");
			throw (IncompatibleClassChangeError) (new IncompatibleClassChangeError("May cause dvmFindCatchBlock crash!").initCause(e));
		} catch (Throwable mayHappen) {
			Log.printErrStackTrace("ActivityLeakSolution", mayHappen, null, null);
		}

		try {
			view.setOnFocusChangeListener(null);
		} catch (IncompatibleClassChangeError e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, "May cause dvmFindCatchBlock crash!");
			throw (IncompatibleClassChangeError) (new IncompatibleClassChangeError("May cause dvmFindCatchBlock crash!").initCause(e));
		} catch (Throwable mayHappen) {
			Log.printErrStackTrace("ActivityLeakSolution", mayHappen, null, null);
		}

		try {
			view.setOnKeyListener(null);
		} catch (IncompatibleClassChangeError e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, "May cause dvmFindCatchBlock crash!");
			throw (IncompatibleClassChangeError) (new IncompatibleClassChangeError("May cause dvmFindCatchBlock crash!").initCause(e));
		} catch (Throwable mayHappen) {
			Log.printErrStackTrace("ActivityLeakSolution", mayHappen, null, null);
		}

		try {
			view.setOnLongClickListener(null);
		} catch (IncompatibleClassChangeError e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, "May cause dvmFindCatchBlock crash!");
			throw (IncompatibleClassChangeError) (new IncompatibleClassChangeError("May cause dvmFindCatchBlock crash!").initCause(e));
		} catch (Throwable mayHappen) {
			Log.printErrStackTrace("ActivityLeakSolution", mayHappen, null, null);
		}

		try {
			view.setOnClickListener(null);
		} catch (IncompatibleClassChangeError e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, "May cause dvmFindCatchBlock crash!");
			throw (IncompatibleClassChangeError) (new IncompatibleClassChangeError("May cause dvmFindCatchBlock crash!").initCause(e));
		} catch (Throwable mayHappen) {
			Log.printErrStackTrace("ActivityLeakSolution", mayHappen, null, null);
		}

		try {
			view.setOnTouchListener(null);
		} catch (IncompatibleClassChangeError e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, "May cause dvmFindCatchBlock crash!");
			throw (IncompatibleClassChangeError) (new IncompatibleClassChangeError("May cause dvmFindCatchBlock crash!").initCause(e));
		} catch (Throwable mayHappen) {
			Log.printErrStackTrace("ActivityLeakSolution", mayHappen, null, null);
		}
		
		Drawable d = view.getBackground();
		if (d != null) {
			d.setCallback(null);

			view.setBackgroundDrawable(null);
		}

		view.destroyDrawingCache();
	}
	
	/**
	 * 可以回收TextView及子类
	 * @param tv
	 */
	private static void recycleTextView(TextView tv) {
		Drawable[] ds = tv.getCompoundDrawables();
		for (Drawable d : ds) {
			if (d != null) {
				d.setCallback(null);
			}
		}
		tv.setCompoundDrawables(null, null, null, null);
		//下面的几个注释，目前为止没出现过这种泄漏，先不管
//		tv.setOnEditorActionListener(null);
//		tv.setKeyListener(null);
//		tv.setMovementMethod(null);
		if (tv instanceof EditText) {
			fixTextWatcherLeak(tv);
		}
	}
	

	
	/**
	 * 回收ProgressBar等
	 * @param pb
	 */
	private static void recycleProgressBar(ProgressBar pb) {
		Drawable pd = pb.getProgressDrawable();
		if (pd != null) {
			pb.setProgressDrawable(null);
			pd.setCallback(null);
		}
		Drawable id = pb.getIndeterminateDrawable();
		if(id != null) {
			pb.setIndeterminateDrawable(null);
			id.setCallback(null);
		}		
	}
	
	/**
	 * 回收手Q的ListView
	 * @param listView
	 */
	private static void recycleXListView(com.tencent.widget.ListView listView) {
		Drawable selector = listView.getSelector();
		if (selector != null) {
			selector.setCallback(null);
			//不能设置空的selector
//			listView.setSelector(null);
		}
		
		try {
			android.widget.ListAdapter la = listView.getAdapter();
			if (la != null) {
				listView.setAdapter(null);
				//不能关闭cursor。万一出问题呢
//				if (la instanceof android.widget.CursorAdapter) {//判断CursorAdapter，关闭cursor
//					android.widget.CursorAdapter ca = (android.widget.CursorAdapter) la;
//					Cursor c = ca.getCursor();
//					if (c != null && !c.isClosed()) {
//						c.close();
//					}
//				}
			}
		} catch (IncompatibleClassChangeError e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, "May cause dvmFindCatchBlock crash!");
			throw (IncompatibleClassChangeError) (new IncompatibleClassChangeError("May cause dvmFindCatchBlock crash!").initCause(e));
		} catch (Throwable mayHappen) {
			Log.printErrStackTrace("ActivityLeakSolution", mayHappen, null, null);
		}

		try {
			listView.setOnScrollListener(null);
		} catch (IncompatibleClassChangeError e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, "May cause dvmFindCatchBlock crash!");
			throw (IncompatibleClassChangeError) (new IncompatibleClassChangeError("May cause dvmFindCatchBlock crash!").initCause(e));
		} catch (Throwable mayHappen) {
			Log.printErrStackTrace("ActivityLeakSolution", mayHappen, null, null);
		}

		try {
			listView.setOnItemClickListener(null);
		} catch (IncompatibleClassChangeError e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, "May cause dvmFindCatchBlock crash!");
			throw (IncompatibleClassChangeError) (new IncompatibleClassChangeError("May cause dvmFindCatchBlock crash!").initCause(e));
		} catch (Throwable mayHappen) {
			Log.printErrStackTrace("ActivityLeakSolution", mayHappen, null, null);
		}

		try {
			listView.setOnItemLongClickListener(null);
		} catch (IncompatibleClassChangeError e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);

			Log.e(TAG, "May cause dvmFindCatchBlock crash!");
			throw (IncompatibleClassChangeError) (new IncompatibleClassChangeError("May cause dvmFindCatchBlock crash!").initCause(e));
		} catch (Throwable mayHappen) {
			Log.printErrStackTrace("ActivityLeakSolution", mayHappen, null, null);
		}

		try {
			listView.setOnItemSelectedListener(null);
		} catch (IncompatibleClassChangeError e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, "May cause dvmFindCatchBlock crash!");
			throw (IncompatibleClassChangeError) (new IncompatibleClassChangeError("May cause dvmFindCatchBlock crash!").initCause(e));
		} catch (Throwable mayHappen) {
			Log.printErrStackTrace("ActivityLeakSolution", mayHappen, null, null);
		}
	}
	
	/**
	 * 回收ListView
	 * @param listView
	 */
	private static void recycleListView(android.widget.ListView listView) {
		Drawable selector = listView.getSelector();
		if (selector != null) {
			selector.setCallback(null);
			//不能设置空的selector
//			listView.setSelector(null);
		}
		
		try {
			android.widget.ListAdapter la = listView.getAdapter();
			if (la != null) {
				listView.setAdapter(null);
				//不能关闭cursor。万一出问题呢
//				if (la instanceof android.widget.CursorAdapter) {//判断CursorAdapter，关闭cursor
//					android.widget.CursorAdapter ca = (android.widget.CursorAdapter) la;
//					Cursor c = ca.getCursor();
//					if (c != null && !c.isClosed()) {
//						c.close();
//					}
//				}
			}
		} catch (IncompatibleClassChangeError e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, "May cause dvmFindCatchBlock crash!");
			throw (IncompatibleClassChangeError) (new IncompatibleClassChangeError("May cause dvmFindCatchBlock crash!").initCause(e));
		} catch (Throwable mayHappen) {
			Log.printErrStackTrace("ActivityLeakSolution", mayHappen, null, null);
		}

		try {
			listView.setOnScrollListener(null);
		} catch (IncompatibleClassChangeError e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, "May cause dvmFindCatchBlock crash!");
			throw (IncompatibleClassChangeError) (new IncompatibleClassChangeError("May cause dvmFindCatchBlock crash!").initCause(e));
		} catch (Throwable mayHappen) {
			Log.printErrStackTrace("ActivityLeakSolution", mayHappen, null, null);
		}

		try {
			listView.setOnItemClickListener(null);
		} catch (IncompatibleClassChangeError e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, "May cause dvmFindCatchBlock crash!");
			throw (IncompatibleClassChangeError) (new IncompatibleClassChangeError("May cause dvmFindCatchBlock crash!").initCause(e));
		} catch (Throwable mayHappen) {
			Log.printErrStackTrace("ActivityLeakSolution", mayHappen, null, null);
		}

		try {
			listView.setOnItemLongClickListener(null);
		} catch (IncompatibleClassChangeError e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, "May cause dvmFindCatchBlock crash!");
			throw (IncompatibleClassChangeError) (new IncompatibleClassChangeError("May cause dvmFindCatchBlock crash!").initCause(e));
		} catch (Throwable mayHappen) {
			Log.printErrStackTrace("ActivityLeakSolution", mayHappen, null, null);
		}

		try {
			listView.setOnItemSelectedListener(null);
		} catch (IncompatibleClassChangeError e) {
			Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
			Log.e(TAG, "May cause dvmFindCatchBlock crash!");
			throw (IncompatibleClassChangeError) (new IncompatibleClassChangeError("May cause dvmFindCatchBlock crash!").initCause(e));
		} catch (Throwable mayHappen) {
			Log.printErrStackTrace("ActivityLeakSolution", mayHappen, null, null);
		}
	}
	
	/**
	 * 回收FrameLayout资源
	 * @param fl
	 */
	private static void recycleFrameLayout(FrameLayout fl) {
		if (fl != null) {
			Drawable fg = fl.getForeground();
			if (fg != null) {
				fg.setCallback(null);
				//fl.setForeground(null);
			}
		}
	}
	
	/**
	 * 回收LinearLayout资源
	 * @param ll
	 */
	@TargetApi(16)
	private static void recycleLinearLayout(LinearLayout ll) {
		if (ll == null) {
			return ;
		}
		
		if (Build.VERSION_CODES.HONEYCOMB <= Build.VERSION.SDK_INT) {
			//API 11以上，设置Divider
			Drawable dd = null;
			if (Build.VERSION_CODES.JELLY_BEAN <= Build.VERSION.SDK_INT) {
				dd = ll.getDividerDrawable();
			} else {
				//API 16以下反射获取
				try {
					Field f = ll.getClass().getDeclaredField("mDivider");
					f.setAccessible(true);
					dd = (Drawable)f.get(ll);
				} catch (NoSuchFieldException e) {
					Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					Log.printErrStackTrace("ActivityLeakSolution", e, null, null);
					e.printStackTrace();
				}
			}
			if (dd != null) {
				dd.setCallback(null);
				ll.setDividerDrawable(null);
			}
		}
	}
	
	/**
	 * 回收ViewGroup
	 * @param vg
	 */
	private static void recycleViewGroup(Activity app, ViewGroup vg) {
		final int childCount = vg.getChildCount();
		for (int i = 0; i < childCount; i++) {
			unbindDrawablesAndRecyle(app, vg.getChildAt(i));
		}
//		try {
//			vg.removeAllViews();
//		} catch (IncompatibleClassChangeError e) {
//			if (QLog.isColorLevel()) {
//				QLog.e(TAG, QLog.CLR, "May cause dvmFindCatchBlock crash!");
//			}
//			throw (IncompatibleClassChangeError) (new IncompatibleClassChangeError("May cause dvmFindCatchBlock crash!").initCause(e));
//		} catch (Throwable mayHappen) {
//		}
	}
	
	private static void unbindDrawablesAndRecyle(Activity app, View view) {
		if (view == null)
			return;
		
		recycleView(view);
		
		if (view instanceof ImageView) {
			//ImageView ImageButton都会走这里
			recycleImageView(app, (ImageView)view);
		} else if (view instanceof TextView) {
			//释放TextView、Button周边图片资源
			recycleTextView((TextView)view);
		} else if (view instanceof ProgressBar) {
			//ProgressBar
			recycleProgressBar((ProgressBar)view);
		} else {
			//ListView
			if (view instanceof android.widget.ListView) {
				recycleListView((android.widget.ListView)view);
			} else if (view instanceof com.tencent.widget.ListView) {
				//手QListView
				recycleXListView((com.tencent.widget.ListView)view);
			} else if (view instanceof FrameLayout) {
				recycleFrameLayout((FrameLayout) view);
			} else if (view instanceof LinearLayout) {
				recycleLinearLayout((LinearLayout) view);
			}
			
			if (view instanceof ViewGroup) {
				recycleViewGroup(app, (ViewGroup)view);
			}
		}
		
	}
	/**
	 * 回收ImageView、ImageButton等
	 * @param iv
	 */
	private static void recycleImageView(Activity app, ImageView iv) {
		if (iv == null) {
			return ;
		}

		Drawable d = iv.getDrawable();
		if (d != null) {
			d.setCallback(null);
		}

		//Recycle FaceDrawable
//		if (d instanceof FaceDrawable) {
//			((FaceDrawable) d).cancel();
//		}

		iv.setImageDrawable(null);

		// Report if iamge too large
//		try {
//			if (d != null && d instanceof BitmapDrawable && UnifiedMonitor.getInstance().whetherReportDuringThisStartup(UnifiedMonitor.FAMILY_IMAGE_VIEW)) {
//				Bitmap b = (Bitmap) ((BitmapDrawable) d).getBitmap();
//				int imgW = b.getWidth();
//				int imgH = b.getHeight();
//				if (imgW > 0 && imgH > 0) {
//					int viewW = iv.getWidth();
//					int viewH = iv.getHeight();
//					int exceededSquere = 0;
//					if (viewW > 0 && viewH > 0) {
//						int ratioW = Math.round((float)imgW / (float)viewW);
//						int ratioH = Math.round((float)imgH / (float)viewH);
//						int maxZoomRatio = Math.max(ratioW, ratioH);
//						if (maxZoomRatio >= 2) {
//							exceededSquere = viewW * viewH * (maxZoomRatio * maxZoomRatio - 1);
//						}
//					} else {
//						exceededSquere = imgW * imgH;
//					}
//
//					if (exceededSquere > 0 && UnifiedMonitor.getInstance().whetherReportThisTime(UnifiedMonitor.FAMILY_IMAGE_VIEW)) {
//						int exceededBytes = exceededSquere * 4 / 1024; //KB
//						StringBuffer sb = new StringBuffer(100);
//						int vid = iv.getId();
//						sb.append(app.getClass().getName()).append('_').append(vid);
//						if (vid == -1) {
//							ViewParent parent = iv.getParent();
//							while(vid == -1 && parent != null && parent instanceof ViewGroup) {
//								vid = ((ViewGroup)parent).getId();
//								sb.append('_');
//								sb.append(vid);
//								parent = parent.getParent();
//							}
//						}
//						java.util.Map<String, String> extraInfo = new HashMap<String, String>(8);
//						extraInfo.put("viewsize", "(" + viewW + "," + viewH + ")");
//						extraInfo.put("picsize", "(" + imgW + "," + imgH + ")");
//						UnifiedMonitor.getInstance().addEvent(UnifiedMonitor.FAMILY_IMAGE_VIEW, sb.toString(), exceededBytes, 0, extraInfo);
//					}
//				}
//			}
//		} catch (Throwable ignore) {
//		}
	}
}
