package com.example.providermoduledemo.viewmodel

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.providermoduledemo.R
import com.yuewen.reader.bookstore.part.IPartView
import kotlinx.android.synthetic.main.view_model_item_title.view.*

/**
 * @author zhanglulu on 2020/10/23.
 * for 标题 View
 */
class TitlePartView : LinearLayout,
    IPartView<TitlePartViewModel> {

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


    override fun setPartViewModel(modelPart: TitlePartViewModel) {
        itemTitle.text = "${modelPart.title}"
    }

}