package com.lulu.skin;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.lulu.skin.support.SupportSkinManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 皮肤收集类 Factory
 */
public class SkinFactory implements LayoutInflater.Factory {
    private static final String TAG = "SkinFactory";

    private List<SkinItem> skinItems = new ArrayList<>();

    private Context context;

    public SkinFactory(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = createView(name,context,attrs);
        if (view!=null){
            collectViewAttr(view,context,attrs);
        }
        return view;
    }

    private View createView(String name, Context context, AttributeSet attrs) {
        View view = null;
        try {
            if (-1 == name.indexOf('.')){	//不带".",说明是系统的View
                if ("View".equals(name)) {
                    view = LayoutInflater.from(context).createView(name, "android.view.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.widget.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.webkit.", attrs);
                }
            }else {	//带".",说明是自定义的View
                view = LayoutInflater.from(context).createView(name, null, attrs);
            }
        } catch (Exception e) {
            view = null;
        }
        Log.d(TAG, "createView: name => " + name);
        return view;
    }

    /**
     * 收集对应属性
     * @param view
     * @param context
     * @param attrs
     */
    private void collectViewAttr(View view, Context context, AttributeSet attrs) {
        List<SkinAttr> skinAttrs = new ArrayList<>();
        int attCount = attrs.getAttributeCount();
        for (int i = 0;i<attCount;++i){
            String attributeName = attrs.getAttributeName(i);
            String attributeValue = attrs.getAttributeValue(i);
            if (isSupportedAttr(attributeName)){
                if (attributeValue.startsWith("@")){
                    int resId = Integer.parseInt(attributeValue.substring(1));
                    String resName = context.getResources().getResourceEntryName(resId);
                    String attrType = context.getResources().getResourceTypeName(resId);
                    skinAttrs.add(new SkinAttr(attributeName,attrType,resName,resId));
                }
            }
        }
        if (!skinAttrs.isEmpty()) {
            SkinItem skinItem = new SkinItem(view, skinAttrs);
            skinItems.add(skinItem);
            if (SkinEngine.get().isExternalSkin()){
                skinItem.apply(view.getContext());
            }
        }

        //Log.d(TAG, "collectViewAttr: skinItems: " + skinItems.toString());
    }

    private boolean isSupportedAttr(String attributeName){
        return SupportSkinManager.isSupportedAttr(attributeName);
    }

    /**
     * 外部调用
     */
    public void apply(){
        for (SkinItem item : skinItems) {
            item.apply(context);
        }
    }

}
