package com.example.providermoduledemo.viewmodel
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.example.providermoduledemo.R
import com.qq.reader.provider.viewmodel.IView
import kotlinx.android.synthetic.main.view_model_single_book.view.*

/**
 * @author zhanglulu on 2020/10/23.
 * for 左图右文 View
 */
class LIRTView : ConstraintLayout, IView<LIRTViewModel>{
    constructor(context: Context) : super(context) {init(context)}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {init(context) }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle ) {
        init(context)
    }

    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.view_model_single_book, this, true)
    }

    override fun setModel(model: LIRTViewModel) {
        rightText.text = "${model.rightText}"
        Glide.with(context).load(model.leftImgUrl).into(leftImg)
        leftImg.setOnClickListener {Toast.makeText(context, model.leftImgUrl, Toast.LENGTH_SHORT).show()}
    }
}