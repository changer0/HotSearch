package com.example.providermoduledemo.viewmodel

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.providermoduledemo.R
import com.qq.reader.provider.viewmodel.IView
import kotlinx.android.synthetic.main.view_model_item_title.view.*

/**
 * @author zhanglulu on 2020/10/23.
 * for
 */
class ItemTitleView : LinearLayout, IView<ItemTitleViewModel>{

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    private fun init(context: Context) {
        orientation = VERTICAL
        addView(LayoutInflater.from(context).inflate(R.layout.view_model_item_title, null))
    }


    override fun setModel(model: ItemTitleViewModel) {
        itemTitle.text = "${model.title}"
    }

}