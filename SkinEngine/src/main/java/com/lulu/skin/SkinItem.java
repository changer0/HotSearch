package com.lulu.skin;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * 皮肤收集类 Item
 */
public class SkinItem {

    private View view;

    private List<SkinAttr> attrs;


    public SkinItem(View view, List<SkinAttr> attrs) {
        this.view = view;
        this.attrs = attrs;
    }

    public void apply(Context context) {
        if (view == null || attrs == null)
            return;
        for (SkinAttr attr : attrs) {
            String attrName = attr.getAttrName();
            String attrType = attr.getAttrType();
            String resName = attr.getResName();
            int resId = attr.getResId();
            if ("background".equals(attrName)) {
                if ("color".equals(attrType)) {
                    view.setBackgroundColor(SkinEngine.get().getColor(context, resName,resId));
                } else if ("drawable".equals(attrType)) {
                    view.setBackground(SkinEngine.get().getDrawable(context, resName,resId));
                }
            } else if ("textColor".equals(attrName)) {
                if (view instanceof TextView && "color".equals(attrType)) {
                    ((TextView) view).setTextColor(SkinEngine.get().getColor(context, resName,resId));
                }
            }
        }
    }

}
