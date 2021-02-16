package com.lulu.hotsearch.view

import android.animation.*
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lulu.hotsearch.define.Constant
import com.lulu.hotsearch.utils.FabAnimUtil
import com.lulu.hotsearch.wb.R
import com.qq.reader.bookstore.define.BookStoreViewParams
import com.qq.reader.bookstore.view.BaseBookStoreView
import com.qq.reader.bookstore.view.CommonLoadMoreView


private const val TAG = "WBHotSearchView"
class HotSearchView(context: Context) : BaseBookStoreView(context), View.OnClickListener {
    private var isAdd = false
    private val llId = intArrayOf(
        R.id.ll01,
        R.id.ll02,
        R.id.ll03
    )
    public val ll = arrayOfNulls<LinearLayout>(llId.size)
    private val fabId = intArrayOf(
        R.id.miniFab01,
        R.id.miniFab02,
        R.id.miniFab03
    )
    public val fab = arrayOfNulls<FloatingActionButton>(fabId.size)
    //动画
    private lateinit var addBillTranslate1: AnimatorSet
    private lateinit var addBillTranslate2: AnimatorSet
    private lateinit var addBillTranslate3: AnimatorSet
    private var lastIsIdle = true
    private var displayAnim: ObjectAnimator? = null

    public lateinit var fabRoot: FloatingActionButton
    private lateinit var rlAddBill: View
    public lateinit var titleRightTime: TextView
    public lateinit var leftImage: ImageView


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
        leftImage = contentView.findViewById(R.id.leftImage)
        for (i in llId.indices) {
            ll[i] = contentView.findViewById(llId[i]) as LinearLayout?
        }
        for (i in fabId.indices) {
            fab[i] = contentView.findViewById(fabId[i]) as FloatingActionButton
        }

        bindEvents()
    }

    private fun initAnim() {
        //https://www.cnblogs.com/sanfeng4476/p/6112284.html
        addBillTranslate1 = AnimatorInflater.loadAnimator(context, R.animator.add_bill_anim) as AnimatorSet
        addBillTranslate2 = AnimatorInflater.loadAnimator(context, R.animator.add_bill_anim) as AnimatorSet
        addBillTranslate3 = AnimatorInflater.loadAnimator(context, R.animator.add_bill_anim) as AnimatorSet

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val isIdle = newState == RecyclerView.SCROLL_STATE_IDLE
                Log.d(TAG, "newState SCROLL_STATE_IDLE: $isIdle")
                if (isIdle == lastIsIdle) return
                lastIsIdle = isIdle
                if (displayAnim?.isRunning == true) {
                    displayAnim?.cancel()
                }
                displayAnim = FabAnimUtil.startDisplayAnim(
                    fabRoot,
                    isIdle
                )

            }
        })
    }

    private fun bindEvents() {
        fabRoot.setOnClickListener(this)
        rlAddBill.setOnClickListener(this)
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
                    addBillTranslate3.setTarget(ll[2])
                    addBillTranslate3.start()
                    addBillTranslate3.startDelay = 200
                }
            }
            else -> hideFABMenu()
        }
    }

    public fun hideFABMenu() {
        rlAddBill.visibility = View.GONE
        fabRoot.setImageResource(R.drawable.ic_add_24px)
        isAdd = false
    }

    public fun setLeftImage(type: String) {
        when(type) {
            Constant.HOT_SEARCH_WB -> leftImage.setImageResource(R.drawable.sina_wb)
            Constant.HOT_SEARCH_DOUYIN -> leftImage.setImageResource(R.drawable.douyin)
            Constant.HOT_SEARCH_ZHIHU -> leftImage.setImageResource(R.drawable.zhihu)
        }
    }

    public fun setTitle(type: String) {
        when(type) {
            Constant.HOT_SEARCH_WB -> actionBarTitle.setText(R.string.hot_search_wb)
            Constant.HOT_SEARCH_DOUYIN -> actionBarTitle.setText(R.string.hot_search_douyin)
            Constant.HOT_SEARCH_ZHIHU -> actionBarTitle.setText(R.string.hot_search_zhihu)
        }
    }

    public fun refreshActionBar(type: String) {
        setTitle(type)
        setLeftImage(type)
    }
}