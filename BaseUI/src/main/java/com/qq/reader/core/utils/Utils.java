package com.qq.reader.core.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {
	private static final String EMUI_PREF = "EmotionUI_";
	private static final String EMUI30 = "EmotionUI_3.0";
	private static final String TAG = "Utils";
	private static int EMUI_SDK_INT = -1;
	private static String EMUI_VERSION = "NONE";
	
    private synchronized static void initEmuiVersionInfo() {
    	if (EMUI_SDK_INT == -1 || "NONE".equals(EMUI_VERSION)) {
    		try {
    			Class<?> classType = Class.forName("android.os.SystemProperties");
    			Method getMethod = classType.getDeclaredMethod("get", String.class,String.class);
    			Method getIntMethod = classType.getDeclaredMethod("getInt", String.class,int.class);
    			EMUI_VERSION = (String) getMethod.invoke(null, "ro.build.version.emui","");
    			EMUI_SDK_INT = (Integer)getIntMethod.invoke(null, "ro.build.hw_emui_api_level",0);
    		} catch (ClassNotFoundException e1) {
    			e1.printStackTrace();
    		} catch (NoSuchMethodException e1) {
    			e1.printStackTrace();
    		} catch (InvocationTargetException e1) {
    			e1.printStackTrace();
    		} catch (IllegalAccessException e1) {
    			e1.printStackTrace();
    		} finally {
    			EMUI_SDK_INT = ((EMUI_SDK_INT == -1) ? 0 : EMUI_SDK_INT);
    			if (EMUI_SDK_INT == 0 && !"NONE".equals(EMUI_VERSION)) {
    				EMUI_SDK_INT = isEmui30() ? EMUI_VERSION_CODES.EMUI_3_0 : 0;
    			}
    		}
    		Log.d(TAG, "EMUI_VERSION = "+EMUI_VERSION+" EMUI_SDK_INT = "+EMUI_SDK_INT);
    	}
    }
    
    public static int getEMUISDKINT() {
    	initEmuiVersionInfo();
    	return EMUI_SDK_INT;
    }
    
    public static boolean isEmui() {
    	initEmuiVersionInfo();
    	if (EMUI_SDK_INT > EMUI_VERSION_CODES.UNKNOWN_EMUI) {
    		return true;
    	}
    	if (EMUI_VERSION.contains(EMUI_PREF)) {
    		return true;
    	}
    	return false;
    }
    
    public static boolean isEmui30() {
    	if (isEmui()) {
    		boolean isEmui30 = EMUI_SDK_INT == EMUI_VERSION_CODES.EMUI_3_0;
    		return isEmui30 ? isEmui30 : EMUI_VERSION.contains(EMUI30);
    	}
    	return false;
    }

    public static boolean isEmui31() {
    	if (isEmui()) {
    		return EMUI_SDK_INT == EMUI_VERSION_CODES.EMUI_3_1; 
    	}
    	return false;
    }

    public static boolean isEmui40() {
    	if (isEmui()) {
    		return EMUI_SDK_INT == EMUI_VERSION_CODES.EMUI_4_0;
    	}
    	return false;
    }

	public static boolean isEmui41() {
		if (isEmui()) {
			return EMUI_SDK_INT == EMUI_VERSION_CODES.EMUI_4_1;
		}
		return false;
	}

	public static boolean isEmui50() {
		if (isEmui()) {
			return EMUI_SDK_INT >= EMUI_VERSION_CODES.EMUI_5_0;
		}
		return false;
	}

	public static boolean isEmui60() {
		if (isEmui()) {
			return EMUI_SDK_INT >= EMUI_VERSION_CODES.EMUI_6_0;
		}
		return false;
	}
    
    public static class EMUI_VERSION_CODES {
        /**
         * Magic version number for a current development build, which has
         * not yet turned into an official release.
         */
        public static final int CUR_DEVELOPMENT = 10000;

        /**Not emui verion code.*/
        public static final int UNKNOWN_EMUI = 0;

        /**Emui 1.0 verion code.*/
        public static final int EMUI_1_0 = 1;

        /**Emui 1.5 verion code.*/
        public static final int EMUI_1_5 = 2;

        /**Emui 1.6 verion code.*/
        public static final int EMUI_1_6 = 3;

        /**Emui 2.0 verion code for jellybean.*/
        public static final int EMUI_2_0_JB = 4;

        /**Emui 2.0 verion code for kitkat.*/
        public static final int EMUI_2_0_KK = 5;

        /**Emui 2.3 verion code.*/
        public static final int EMUI_2_3 = 6;

        /**Emui 3.0 verion code.*/
        public static final int EMUI_3_0 = 7;

        /**Emui 3.0.5 version code for lollipop.*/
        public static final int EMUI_3_0_5 = 8;

        /**Emui 3.1 version code for lollipop, equals to EMUI_3_0_5.*/
         public static final int EMUI_3_1 = EMUI_3_0_5;

        /**Emui 4.0 version code for lollipop.*/
		public static final int EMUI_4_0 = 9;

		/**Emui 4.1 */
		public static final int EMUI_4_1 = 10;

		/**Emui 50. */
		public static final int EMUI_5_0 = 11;

		/**Emui 60. */
		public static final int EMUI_6_0 = 14;
    }

	/**
	 * 进入设置网络页面
	 * @param context
	 */
	public static void openWifiOrDataStrings(Context context){
		if(context == null){
			return;
		}

		Intent it = new Intent();
		//Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
		if(Build.VERSION.SDK_INT >= 26){
			it.setAction(Settings.ACTION_WIRELESS_SETTINGS);
			it.putExtra("use_emui_ui",true);
		}else{
			it.setAction(Settings.ACTION_SETTINGS);
		}
		context.startActivity(it);
	}
	
	
	/** 
	 * Get image from newwork 
	 * @param path The path of image 
	 * @return InputStream 
	 * @throws Exception
	 */  
	public static InputStream getImageStream(String path) throws Exception {
	    URL url = new URL(path);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setConnectTimeout(5 * 1000);  
	    conn.setRequestMethod("GET");  
	    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
	        return conn.getInputStream();  
	    }  
	    return null;  
	}
	
	/** 
	 * 保存文件 
	 * @param bm 
	 * @param fileName 
	 * @throws IOException
	 */  
	public static void saveFile(Bitmap bm, String fileName) throws IOException {
	    File f = new File(fileName);
//	    if(!f.exists()){  
//	        f.createNewFile();  
//	    }  
//	    File myCaptureFile = new File(HwAccountManager.PIC_PATH + fileName);  
	    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
	    bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
	    bos.flush();  
	    bos.close();  
	}
	
	/**
	 * 获取裁剪后的圆形图片
	 * @param radius半径
	 */
	public static Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {
		Bitmap scaledSrcBmp;
		int diameter = radius * 2;
		// 为了防止宽高不相等，造成圆形图片变形，因此截取长方形中处于中间位置最大的正方形图片
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		int squareWidth = 0, squareHeight = 0;
		int x = 0, y = 0;
		Bitmap squareBitmap;
		if (bmpHeight > bmpWidth) {
			// 高大于宽
			squareWidth = squareHeight = bmpWidth;
			x = 0;
			y = (bmpHeight - bmpWidth) / 2;
			// 截取正方形图片
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth, squareHeight);
		} else if (bmpHeight < bmpWidth) {
			// 宽大于高
			squareWidth = squareHeight = bmpHeight;
			x = (bmpWidth - bmpHeight) / 2;
			y = 0;
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth, squareHeight);
		} else {
			squareBitmap = bmp;
		}
		
		if (diameter > 0 && (squareBitmap.getWidth() != diameter || squareBitmap.getHeight() != diameter)) {
			scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter,diameter, true);
		} else {
			scaledSrcBmp = squareBitmap;
		}
		Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight());
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawCircle(scaledSrcBmp.getWidth() / 2, scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2,
				paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
		bmp = null;
		squareBitmap = null;
		scaledSrcBmp = null;
		return output;
	}
	
	public static boolean isEmpty(String[] strs) {
		if (strs.length == 0) {
			return true;
		}
		for (String s : strs) {
			if (s != null && !"".equals(s)) {
				return false;
			}
		}
		return true;
	}
}
