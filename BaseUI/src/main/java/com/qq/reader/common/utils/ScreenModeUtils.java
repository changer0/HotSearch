package com.qq.reader.common.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qq.reader.baseui.R;
import com.qq.reader.core.BaseApplication;
import com.qq.reader.core.config.AppConstant;
import com.tencent.mars.xlog.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ScreenModeUtils {

	//自己实现沉浸式
	public static final int IMMERSE_MODE_IMMERSE = 0;
	//不自己实现沉浸式，如果系统有沉浸式，交给系统实现
	public static final int IMMERSE_MODE_NONE = 1;
	//自己实现沉浸式，并模仿系统沉浸式样式，OPPO独有功能
	public static final int IMMERSE_MODE_IMITATE = 2;
	//设置状态栏文字颜色。SYSTEM_UI_FLAG_LIGHT_STATUS_BAR直接引用报找不到错误。
	private static final int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = 8192;
	private static final int SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN = 0x00000400;
	private static final int SYSTEM_UI_FLAG_OP_STATUS_BAR_TINT = 16; // OPPO
	// oppo虚拟键按钮反色函数
	private static int SYSTEM_UI_FLAG_APP_LIGHT_NAVIGATION_BAR = 0x00000080;
	private static int SYSTEM_UI_FLAG_APP_LIGHT_NAVIGATION_BAR_COMMON = 0x00002000;

	public static int cutOutHeight = 0;

	//状态栏类型 1:MIUUI 2:Flyme 3:android6.0
	private static int statusbar_type = -1;

	public static void addImmersiveStatusBarFlag(Activity activity) {
		if (VERSION.SDK_INT >= 21) {// 5.0以上使用FLAG_DRAWS_SYSTEM_BAR_BACKGROUND
			Window window = activity.getWindow();
			// FLAG_TRANSLUCENT_STATUS=0x04000000
			window.clearFlags(0x04000000);
			// SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN=0x00000400
			if(FlavorUtils.isCommon() || FlavorUtils.isCoFree()) {
				window.getDecorView().setSystemUiVisibility(0x00000400 | SYSTEM_UI_FLAG_APP_LIGHT_NAVIGATION_BAR_COMMON);
			} else {
				window.getDecorView().setSystemUiVisibility(0x00000400);
			}
			// FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS=0x80000000
			window.addFlags(0x80000000);
		} else if (Build.VERSION.SDK_INT >= 19) {// 4.4以上使用FLAG_TRANSLUCENT_STATUS
			Window window = activity.getWindow();
			window.clearFlags(0x80000000);
			window.addFlags(0x04000000 | SYSTEM_UI_FLAG_APP_LIGHT_NAVIGATION_BAR_COMMON);
		}
		setStatusBarWithColor(activity, Color.TRANSPARENT);
		setStatusBarLightMode(activity);
	}

	/**
	 * 状态栏亮色模式，设置状态栏黑色文字、图标，
	 * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
	 *
	 * @param activity
	 * @return 1:MIUUI 2:Flyme 3:android6.0
	 */
	public static void setStatusBarLightMode(Activity activity) {
		if (statusbar_type > 0) {
			if (statusbar_type == 1) {
				StatusBarBlackTextFixer.MIUISetStatusBarLightMode(activity, true);
			} else if (statusbar_type == 2) {
				StatusBarBlackTextFixer.FlymeSetStatusBarLightMode(activity.getWindow(), true);
			} else if (statusbar_type == 3) {
				activity.getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			} else if (statusbar_type == 1000) {
				setStatusBarWithColor(activity, activity.getResources().getColor(R.color.status_bar_bg)); //状态栏设为灰色
			}
		} else {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

				if (StatusBarBlackTextFixer.MIUISetStatusBarLightMode(activity, true)) {
					statusbar_type = 1;
				} else if (StatusBarBlackTextFixer.FlymeSetStatusBarLightMode(activity.getWindow(), true)) {
					statusbar_type = 2;
				} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					activity.getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
					statusbar_type = 3;
				} else {
					statusbar_type = 1000;
				}
			}
		}
	}

	/**
	 * 状态栏暗色模式，清除MIUI、flyme或6.0以上版本状态栏黑色文字、图标
	 */
	public static void setStatusBarDarkMode(Activity activity) {
		if (statusbar_type == 1) {
			StatusBarBlackTextFixer.MIUISetStatusBarLightMode(activity, false);
		} else if (statusbar_type == 2) {
			StatusBarBlackTextFixer.FlymeSetStatusBarLightMode(activity.getWindow(), false);
		} else if (statusbar_type == 3) {
			activity.getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		} else if (statusbar_type == 1000) {
			setStatusBarWithColor(activity, Color.TRANSPARENT);
		}

	}

	public static class StatusBarBlackTextFixer {
		/**
		 * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
		 * 可以用来判断是否为Flyme用户
		 *
		 * @param window 需要设置的窗口
		 * @param dark   是否把状态栏字体及图标颜色设置为深色
		 * @return boolean 成功执行返回true
		 */
		public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
			boolean result = false;
			if (window != null) {
				try {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
						//已验证魅族android7.0 、8.0手机用老的方法设置无效且不会报错，所以此处还是走原系统api设置
						if (dark) {
							window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
						} else {
							window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
						}
					} else {
						WindowManager.LayoutParams lp = window.getAttributes();
						Field darkFlag = WindowManager.LayoutParams.class
								.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
						Field meizuFlags = WindowManager.LayoutParams.class
								.getDeclaredField("meizuFlags");
						darkFlag.setAccessible(true);
						meizuFlags.setAccessible(true);
						int bit = darkFlag.getInt(null);
						int value = meizuFlags.getInt(lp);
						if (dark) {
							value |= bit;
						} else {
							value &= ~bit;
						}
						meizuFlags.setInt(lp, value);
						window.setAttributes(lp);
					}
					result = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return result;
		}

		/**
		 * 需要MIUIV6以上
		 *
		 * @param activity
		 * @param dark     是否把状态栏字体及图标颜色设置为深色
		 * @return boolean 成功执行返回true
		 */
		public static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
			boolean result = false;
			Window window = activity.getWindow();
			if (window != null) {
				Class clazz = window.getClass();
				try {
					int darkModeFlag = 0;
					Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
					Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
					darkModeFlag = field.getInt(layoutParams);
					Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
					if (dark) {
						extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
					} else {
						extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
					}
					result = true;

					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
						//开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
						if (dark) {
							//View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
							activity.getWindow().getDecorView().setSystemUiVisibility(0x00000400 | 0x00002000);
						} else {
							activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
						}
					}
				} catch (Exception e) {

				}
			}
			return result;
		}
	}

	/**
	 * 设置导航栏字体颜色为黑色，只有common用了
	 * @param activity
	 */
	public static void setStatusBarColor(Activity activity) {
		if (VERSION.SDK_INT >= 21) {// 5.0以上使用FLAG_DRAWS_SYSTEM_BAR_BACKGROUND
			Window window = activity.getWindow();
			// FLAG_TRANSLUCENT_STATUS=0x04000000
			window.clearFlags(0x04000000);
			// SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN=0x00000400
			window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_APP_LIGHT_NAVIGATION_BAR_COMMON);
			// FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS=0x80000000
			window.addFlags(0x80000000);
		} else if (Build.VERSION.SDK_INT >= 19) {// 4.4以上使用FLAG_TRANSLUCENT_STATUS
			Window window = activity.getWindow();
			window.clearFlags(0x80000000);
			window.addFlags(0x04000000 | SYSTEM_UI_FLAG_APP_LIGHT_NAVIGATION_BAR_COMMON);
		}
		setStatusBarWithColor(activity, Color.TRANSPARENT);
//		setNavigationBarColor(activity,Color.parseColor("#FFFFFF"));//由于不同机型不同，这个虚拟按键先使用默认的
	}

	public static void setStatusBarWithColor(Activity activity, int color) {
		if (VERSION.SDK_INT >= 21) {// 5.0以上使用FLAG_DRAWS_SYSTEM_BAR_BACKGROUND
			Window window = activity.getWindow();
			window.setStatusBarColor(color);
		}
		else if (Build.VERSION.SDK_INT >= 19) {// 4.4以上使用FLAG_TRANSLUCENT_STATUS
			if (color != Color.TRANSPARENT) {
				createStatusBar(activity, color);// 4.4需要自己绘制一个view控制状态栏颜色
			}
		}
	}

	private static void createStatusBar(Activity activity, int color) {
		// createISBView
		View ISBView = new View(activity);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				AppConstant.statusBarHeight);
		params.gravity = Gravity.TOP;
		ISBView.setLayoutParams(params);
		ISBView.setVisibility(View.VISIBLE);
		// set color
		ISBView.setBackgroundColor(color);
		// addISBView
		((ViewGroup) activity.getWindow().getDecorView()).addView(ISBView);
	}

	public static void clearImmersiveStatusBarFlag(Activity activity) {
		if (VERSION.SDK_INT >= 21) {// 5.0
			activity.getWindow().clearFlags(0x04000000);
			activity.getWindow().clearFlags(0x80000000);
			activity.getWindow().getDecorView()
					.setSystemUiVisibility(0x00000000);
		} else if (Build.VERSION.SDK_INT >= 19) {// 4.4
			activity.getWindow().clearFlags(0x04000000);
		}
	}

//	/**
//	 * changeTitleBarHeight的逆方法
//	 *
//	 * @param activity
//	 */
//	public static void revertTitleBarHeight(Activity activity) {
//		if (Build.VERSION.SDK_INT >= 19) {
//			RelativeLayout titleLayout = (RelativeLayout) activity
//					.findViewById(R.id.common_titler);
//			if (titleLayout != null) {
//				// common_titler
//				titleLayout.setPadding(0, 0, 0, 0);
//				int titleBarHeight = (int) activity.getResources().getDimension(
//						R.dimen.bookstore_titlerbar_height);
//				titleLayout.setLayoutParams(new RelativeLayout.LayoutParams(
//						LayoutParams.MATCH_PARENT, titleBarHeight));
//
//			}
//		}
//	}

	/**
	 * 配合沉浸式状态栏一起使用 用于改变id为common_titler的view的高度
	 *
	 * @param fragmentView
	 */
	public static void changeTitleBarHeight(View fragmentView) {
		if (fragmentView == null) {
			return;
		}
		if (Build.VERSION.SDK_INT >= 19) {
			RelativeLayout titleLayout = (RelativeLayout) fragmentView
					.findViewById(R.id.common_titler);
			RelativeLayout titleContainer = (RelativeLayout) fragmentView
					.findViewById(R.id.common_title_container);

			if (titleLayout != null && titleContainer != null) {
				// common_titler
//				titleLayout.setPadding(0, AppConstant.statusBarHeight, 0, 0);
				int titleBarHeight = AppConstant.statusBarHeight
						+ (int) BaseApplication.Companion.getINSTANCE().getResources().getDimension(
						R.dimen.bookstore_titlerbar_height);

				//ChildView
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) titleContainer.getLayoutParams();
				layoutParams.setMargins(layoutParams.leftMargin, AppConstant.statusBarHeight,
						layoutParams.rightMargin, layoutParams.bottomMargin);

				titleContainer.setLayoutParams(layoutParams);


				//RooView
				titleLayout.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, titleBarHeight));

				// toastbar
				TextView toastbarTV = (TextView) fragmentView
						.findViewById(R.id.main_toastbar);
				if (toastbarTV != null) {
					int paddingTop = AppConstant.statusBarHeight
							+ (int) BaseApplication.Companion.getINSTANCE().getResources().getDimension(
							R.dimen.toastbar_paddingtop);
					toastbarTV.setPadding(0, paddingTop, 0, 0);
					toastbarTV.setLayoutParams(new FrameLayout.LayoutParams(
							LayoutParams.MATCH_PARENT, titleBarHeight));
				}
			}

			RelativeLayout searchLayout = (RelativeLayout) fragmentView
					.findViewById(R.id.search_header);
			if (searchLayout != null) {
				searchLayout.setPadding(0, AppConstant.statusBarHeight, 0, 0);
				int titleBarHeight = AppConstant.statusBarHeight
						+ (int) BaseApplication.Companion.getINSTANCE().getResources().getDimension(
						R.dimen.bookstore_searchbar_height);
				searchLayout.setLayoutParams(new RelativeLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, titleBarHeight));
			}
		}
	}

	public static void changeTitleBarHeight(Activity activity) {
		if (Build.VERSION.SDK_INT >= 19) {
			RelativeLayout titleLayout = (RelativeLayout) activity
					.findViewById(R.id.common_titler);
			RelativeLayout titleContainer = (RelativeLayout) activity
					.findViewById(R.id.common_title_container);

			if (titleLayout != null && titleContainer != null) {
				// common_titler
//				titleLayout.setPadding(0, AppConstant.statusBarHeight, 0, 0);
				int titleBarHeight = AppConstant.statusBarHeight
						+ (int) BaseApplication.Companion.getINSTANCE().getResources().getDimension(
						R.dimen.bookstore_titlerbar_height);

				//RooView
				titleLayout.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, titleBarHeight));

				//ChildView
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) titleContainer.getLayoutParams();
				layoutParams.setMargins(layoutParams.leftMargin, AppConstant.statusBarHeight,
						layoutParams.rightMargin, layoutParams.bottomMargin);


				titleContainer.setLayoutParams(layoutParams);

			}


//			if (titleLayout != null) {
//				// common_titler
//				titleLayout.setPadding(0, AppConstant.statusBarHeight, 0, 0);
//				int titleBarHeight = AppConstant.statusBarHeight + (int) activity.getResources().getDimension(
//						R.dimen.bookstore_titlerbar_height);
//				titleLayout.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, titleBarHeight));
//
//			}

			RelativeLayout searchLayout = (RelativeLayout) activity
					.findViewById(R.id.search_header);
			if (searchLayout != null) {
				searchLayout.setPadding(0, AppConstant.statusBarHeight, 0, 0);
				int titleBarHeight = AppConstant.statusBarHeight
						+ (int) activity.getResources().getDimension(
						R.dimen.bookstore_searchbar_height);
				searchLayout.setLayoutParams(new RelativeLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, titleBarHeight));
			}
		}
	}

	// Build.VERSION.SDK_INT < 19
	public static void setFullScreenOld(Activity activity, boolean fullScreen) {
		if (fullScreen) {
			activity.getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
			activity.getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
			activity.getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		} else {
			activity.getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
			activity.getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			activity.getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		}
	}

	// Build.VERSION.SDK_INT > 19
	// 阅读页只有topmenu和searchDlg显示的时候需要显示系统状态栏
	public static void setFullScreenExtra(Activity activity, boolean fullScreen) {
		if (Build.VERSION.SDK_INT >= 19) {
			if (fullScreen) {
				activity.getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
				activity.getWindow().addFlags(
						WindowManager.LayoutParams.FLAG_FULLSCREEN);
			} else {
				activity.getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_FULLSCREEN);
				activity.getWindow().addFlags(
						WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
			}
		}
	}

	/**
	 * 清除设置过的flags
	 * @param activity
	 */
	public static void clearAllFlags(Activity activity) {
		activity.getWindow().getDecorView()
				.setSystemUiVisibility(0x00000100// View.SYSTEM_UI_FLAG_LAYOUT_STABLE
						| 0x00000400| 0x00000001);// View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
		activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		activity.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		activity.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
	}

	/**
	 * 隐藏状态栏的全屏模式
	 *
	 * @param dialog
	 * @param fullScreen
	 */
	public static void setFullScreen(Dialog dialog, boolean fullScreen) {
		if (fullScreen) {
			dialog.getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
			dialog.getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		} else {
			dialog.getWindow().clearFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			dialog.getWindow().addFlags(
					WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		}
	}

	/**
	 * 判断底部是否有虚拟导航栏 （true：虚拟导航栏，false：物理导航栏）
	 *
	 * @param
	 * @return
	 */
	public static boolean checkDeviceHasNavigationBar() {
		// 荣耀9没有虚拟导航栏，但是无法通过一般方法判断出来。
		if(Build.MODEL.equals("STF-AL00")){
			return false;
		}
		//update by p_jwcao  at 2017.9.4 ,fix xiaomi 5(miui 8.5) bug
		//http://blog.etongwl.com/archives/1030.html
		Resources res = BaseApplication.Companion.getINSTANCE().getResources();
		int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
		if (resourceId != 0) {
			boolean hasNav = res.getBoolean(resourceId);
			// check override flag
			String sNavBarOverride = getNavBarOverride();
			if ("1".equals(sNavBarOverride)) {
				hasNav = false;
			} else if ("0".equals(sNavBarOverride)) {
				hasNav = true;
			}
			return hasNav;
		} else { // fallback
			// 通过判断设备是否有菜单键(不是虚拟键,是手机屏幕外的物理按键)来确定是否有navigation bar
			return !ViewConfiguration.get(BaseApplication.Companion.getINSTANCE()).hasPermanentMenuKey();
		}

	}

	/**
	 * 判断虚拟导航栏是否显示
	 *
	 * @param context 上下文对象
	 * @param window  当前窗口
	 * @return true(显示虚拟导航栏)，false(不显示或不支持虚拟导航栏)
	 */
	public static boolean checkNavigationBarShow(Context context, Window window) {
		boolean show;
		Display display = window.getWindowManager().getDefaultDisplay();
		Point point = new Point();
		display.getRealSize(point);

		View decorView = window.getDecorView();
		Configuration conf = context.getResources().getConfiguration();
		if (Configuration.ORIENTATION_LANDSCAPE == conf.orientation) {
			View contentView = decorView.findViewById(android.R.id.content);
			show = (point.x != contentView.getWidth());
		} else {
			Rect rect = new Rect();
			decorView.getWindowVisibleDisplayFrame(rect);
			show = (rect.bottom != point.y);
		}
		return show;
	}


	/**
	 * 判断虚拟按键栏是否重写
	 * @return
	 */
	private static String getNavBarOverride() {
		String sNavBarOverride = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			try {
				Class c = Class.forName("android.os.SystemProperties");
				Method m = c.getDeclaredMethod("get", String.class);
				m.setAccessible(true);
				sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
			} catch (Throwable e) {
			}
		}
		return sNavBarOverride;
	}

	public static boolean setMeizuStatusBarDarkIcon(Activity activity, boolean dark) {
		boolean result = false;
		if (activity != null) {
			try {
				WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
				Field darkFlag = WindowManager.LayoutParams.class
						.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
				Field meizuFlags = WindowManager.LayoutParams.class
						.getDeclaredField("meizuFlags");
				darkFlag.setAccessible(true);
				meizuFlags.setAccessible(true);
				int bit = darkFlag.getInt(null);
				int value = meizuFlags.getInt(lp);
				if (dark) {
					value |= bit;
				} else {
					value &= ~bit;
				}
				meizuFlags.setInt(lp, value);
				activity.getWindow().setAttributes(lp);
				result = true;
			} catch (Exception e) {
				Log.printErrStackTrace("ScreenModeUtils", e, null, null);
			}
		}
		return result;
	}

	public static void setNavigationStatusColor(Activity activity, int color) {
		Log.d("ScreenModeUtils","setNavigationStatusColor " + color);
		setNavigationStatusColor(activity.getWindow(),color);
	}

	public static void setNavigationStatusColor(Dialog dialog, int color) {
		Log.d("ScreenModeUtils","dialog setNavigationStatusColor " + color);
		setNavigationStatusColor(dialog.getWindow(),color);
	}

	public static void setNavigationStatusColor(Window window, int color) {
		//VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
		if (VERSION.SDK_INT >= 21) {
//			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setNavigationBarColor(color);
			window.setStatusBarColor(color);
		}
	}

	public static void setStatusBarColor(Activity activity, int color) {
		if (VERSION.SDK_INT >= 21) {
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			activity.getWindow().setStatusBarColor(color);
		}
	}

	public static void setNavigationBarColor(Activity activity, int color) {
		//VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
		if (VERSION.SDK_INT >= 21) {
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			activity.getWindow().setNavigationBarColor(color);
		}
	}

	public static void resetNavigationBarColor(Activity activity) {
		//VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
		if (VERSION.SDK_INT >= 21) {
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		}
	}

	public static void setNavigationBarIconLight(Activity activity, boolean lightNavColor) {
		int tmpVis = activity.getWindow().getDecorView().getSystemUiVisibility();
		// TODO 其他手机？
//		if (Build.VERSION.SDK_INT >= 26) {
//			SYSTEM_UI_FLAG_APP_LIGHT_NAVIGATION_BAR = 0x00000010;
//		}
		if (lightNavColor) {
			tmpVis &= ~SYSTEM_UI_FLAG_APP_LIGHT_NAVIGATION_BAR;
//			tmpVis |= 0x00000200; // View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
		} else {
			tmpVis |= SYSTEM_UI_FLAG_APP_LIGHT_NAVIGATION_BAR;
		}
		activity.getWindow().getDecorView().setSystemUiVisibility(tmpVis);// TODO  这个是专门管理阅读页的，不用改
	}

	public static void setStatusIconLight(Activity activity, boolean isLight) {
		if(isLight){
			setStatusBarLightMode(activity);
		}else{
			setStatusBarDarkMode(activity);
		}
	}



	public static void setDialogFullScreenLayout(Dialog mDialog, boolean fullScreenLayout) {
		Log.d("ScreenModeUtils","setDialogFullScreenLayout fullScreenLayout " + fullScreenLayout);
		if (ScreenModeUtils.checkDeviceHasNavigationBar()
				&& Build.VERSION.SDK_INT >= 21 || (Build.MODEL.equals("STF-AL00"))) {
			// 荣耀9 特殊处理一下
			View topView = mDialog.findViewById(R.id.reader_dialog_top_view);
			if (topView != null) {
				if (fullScreenLayout) {
					mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
					int height = AppConstant.statusBarHeight;
					// 异形屏，用户设置了不在沉浸式区域显示 不留白
					if (HardwareUtils.shouldNonImmersiveAdjust()) {
						height = 0;
					}
					Log.d("ScreenModeUtils", "setDialogFullScreenLayout height "
							+ height + " statusBarHeight " + AppConstant.statusBarHeight
							+ " " + HardwareUtils.shouldNonImmersiveAdjust());
					topView.setLayoutParams(new FrameLayout.LayoutParams(
							ViewGroup.LayoutParams.MATCH_PARENT, height));
				} else {
					mDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
					topView.setLayoutParams(new FrameLayout.LayoutParams(
							ViewGroup.LayoutParams.MATCH_PARENT, 0));
				}
			}
		}
	}

	/**
	 * 获取凹口屏幕的凹口高度 Android P
	 * @param view
	 */
	@TargetApi(Build.VERSION_CODES.P)//Build.VERSION_CODES.P
	public static void obtainCutoutHeight(View view) {
		if (view == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
			return;
		}
		view.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
			@Override
			public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
				cutOutHeight = windowInsets.getStableInsetTop();
				return windowInsets;
			}
		});
	}

	/**
	 * 支持凹口屏幕Mode Android P
	 * @param window
	 */
	@TargetApi(Build.VERSION_CODES.P)
	public static void supportCutout(Window window) {
		if (window == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
			return;
		}
		WindowManager.LayoutParams lp =window.getAttributes();
		lp.layoutInDisplayCutoutMode
				=WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
	}
}
