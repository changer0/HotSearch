package com.lulu.hotsearch.wb

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.qq.reader.bookstore.define.BookStoreViewParams
import com.qq.reader.bookstore.view.BaseBookStoreView
import com.qq.reader.bookstore.view.CommonLoadMoreView


class WBHotSearchView(context: Context) : BaseBookStoreView(context), View.OnClickListener {
    private var isAdd = false
    private val llId = intArrayOf(R.id.ll01, R.id.ll02)
    private val ll = arrayOfNulls<LinearLayout>(llId.size)
    private val fabId = intArrayOf(
        R.id.miniFab01,
        R.id.miniFab02
    )
    private val fab = arrayOfNulls<FloatingActionButton>(fabId.size)
    //动画
    private lateinit var addBillTranslate1: AnimatorSet
    private lateinit var addBillTranslate2: AnimatorSet

    public lateinit var fabRoot: FloatingActionButton
    private lateinit var rlAddBill: RelativeLayout
    public lateinit var titleRightTime: TextView

    override fun onCreateParams(): BookStoreViewParams {
        return BookStoreViewParams.Builder(
            R.layout.wb_hot_search_fragment,
            R.id.list_layout
        )
            .setDataErrorViewIdRes(R.id.loading_failed_layout)
            .setPullDownViewIdRes(R.id.pull_down_list)
            .setLoadingViewIdRes(R.id.loading_layout)
            .setLoadMoreView(CommonLoadMoreView())
            .setActionBarContainerIdRes(R.id.common_titler)
            .setActionBarTitleViewIdRes(R.id.profile_header_title)
            .build()
    }

    override fun onCreateView(contentView: View) {
        initAnim()
        fabRoot = contentView.findViewById(R.id.fabRoot)
        titleRightTime = contentView.findViewById(R.id.title_right_time)
        rlAddBill = contentView.findViewById(R.id.rlAddBill)
        for (i in llId.indices) {
            ll[i] = contentView.findViewById(llId[i]) as LinearLayout?
        }
        for (i in fabId.indices) {
            fab[i] = contentView.findViewById(fabId[i]) as FloatingActionButton
        }

        bindEvents()
    }

    private fun initAnim() {
        addBillTranslate1 = AnimatorInflater.loadAnimator(context, R.animator.add_bill_anim) as AnimatorSet
        addBillTranslate2 = AnimatorInflater.loadAnimator(context, R.animator.add_bill_anim) as AnimatorSet
    }

    private fun bindEvents() {
        fabRoot.setOnClickListener(this)
        rlAddBill.setOnClickListener(this)
        for (i in fabId.indices) {
            fab[i]?.setOnClickListener(this)
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.fabRoot -> {
                fabRoot.setImageResource(if (isAdd) R.drawable.ic_add_24px else R.drawable.ic_close_24px)
                isAdd = !isAdd
                rlAddBill.visibility = (if (isAdd) View.VISIBLE else View.GONE)
                if (isAdd) {
                    addBillTranslate1.setTarget(ll[0])
                    addBillTranslate1.start()
                    addBillTranslate2.setTarget(ll[1])
                    addBillTranslate2.start()
                    addBillTranslate2.startDelay = 150
                }
            }
            else -> hideFABMenu()
        }
    }

    private fun hideFABMenu() {
        rlAddBill.visibility = View.GONE
        fabRoot.setImageResource(R.drawable.ic_add_24px)
        isAdd = false
    }
}