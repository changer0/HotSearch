package com.qq.reader.core.utils;

import android.app.Activity;
import android.content.Context;
import androidx.viewpager.widget.ViewPager;


import com.tencent.mars.xlog.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ReflectionUtils {
	public static final String CLS_ = "com.huawei.android.os.BuildEx";
	public static final String CLS_SubTabWidget = "huawei.android.widget.SubTabWidget";
	public static final String CLS_SubTabFragmentPagerAdapter = "huawei.support.v13.app.SubTabFragmentPagerAdapter";
	public static final String CLS_ActionModeEx = "com.huawei.android.view.ActionModeEx";
	private static final String TAG = "ReflectionUtils";
	
	private static Class sClsSubTabWidget;
	private static Class sClsSubTabFragmentPagerAdapter;
	private static Class sObject = Object.class;
	private static Method sMethod = null;
	private static HashMap<String, Class> sClasses = new HashMap<String, Class>();
	private static HashMap<String, Method> sMethods = new HashMap<String, Method>();
	
	public static void init(){};
	
	public static class MethodSign {
		public String fullMethodName;
		public Class[] parameterTypes;
		
		public MethodSign(String fullMethodName, Class... parameterTypes) {
			super();
			this.fullMethodName = fullMethodName;
			this.parameterTypes = parameterTypes;
		}
	}
	
	public static class ObjectValue {
		public Object receiver;
		public Object[] args;
		
		public ObjectValue(Object receiver, Object... args) {
			super();
			this.receiver = receiver;
			this.args = args;
		}
	}
	
	public static Object invokeMethod(MethodSign methodSign, ObjectValue target) {
		Method m = getMethod(methodSign);
		if (m == null || m == sMethod) {
			Log.w(TAG, "Method not found");
			return null;
		}
		Object receiver = target.receiver;
		Object[] args = target.args;
		Object result = null;
		try {
			result = m.invoke(receiver, args);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static Object getFieldValue(String fieldSign, Object object) {
		if (fieldSign == null || "".equals(fieldSign)) {
			return null;
		}
		String[] strs = fieldSign.split(":");
		if (strs.length != 2) {
			Log.w(TAG,"illegal param : fieldSign  = "+fieldSign);
			return null;
		}
		String clsName = strs[0];
		String fieldName = strs[1];
		Class c = sClasses.get(clsName);
		if (c == null) {
			try {
				c = Class.forName(clsName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		if (c != null) {
			try {
				return c.getField(fieldName).get(object);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static Method getMethod(MethodSign methodSign) {
		if (sMethod == null) {
			try {
				sMethod = ReflectionUtils.class.getMethod("init", (Class<?>) null);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		Method m = sMethods.get(methodSign.fullMethodName);
		if (m != null && m != sMethod) {
			return m;
		}
		if (methodSign.fullMethodName == null || "".equals(methodSign.fullMethodName)) {
			Log.w(TAG,"illegal param : methodName  = "+methodSign.fullMethodName);
			return null;
		}
		String[] strs = methodSign.fullMethodName.split(":");
		if (strs.length != 2) {
			Log.w(TAG,"illegal param : methodName  = "+methodSign.fullMethodName);
			return null;
		}
		String clsName = strs[0];
		String methodName = strs[1];
		Class c = sClasses.get(clsName);
		if (c == null) {
			try {
				c = Class.forName(clsName);
				sClasses.put(clsName, c);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				c = sObject;
				sClasses.put(clsName, c);
			}
			
		}
		if (c == sObject) {
			Log.w(TAG,"class not found");
			return null;
		}
		try {
			 m = c.getMethod(methodName, methodSign.parameterTypes);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			m = sMethod;
		}
		 sMethods.put(methodSign.fullMethodName, m);
		return m;
	}
	
	public static Object newSubTabWidget(Context ctx) {
		Object o = null;
		try {
			if (sClsSubTabWidget == null) {
				sClsSubTabWidget = Class.forName(CLS_SubTabWidget);
			}
			Log.e("aaa","getClassLoader = "+sClsSubTabWidget.getClassLoader());
			o = sClsSubTabWidget.getConstructor(Context.class).newInstance(ctx);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
	
	public static Object newSubTabFragmentPagerAdapter(Activity activity, ViewPager pager, Object subTabWidget) {
		Object o = null;
		try {
			if (sClsSubTabWidget == null) {
				sClsSubTabWidget = Class.forName(CLS_SubTabWidget);
			}
			if (sClsSubTabFragmentPagerAdapter == null) {
				sClsSubTabFragmentPagerAdapter = sClsSubTabWidget.getClassLoader().loadClass(CLS_SubTabFragmentPagerAdapter);
//				sClsSubTabFragmentPagerAdapter = Class.forName(CLS_SubTabFragmentPagerAdapter);
			}
			Constructor c = sClsSubTabFragmentPagerAdapter.getConstructor(Activity.class,ViewPager.class,sClsSubTabWidget);
			o = c.newInstance(activity,pager,subTabWidget);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return o;
	}
}
