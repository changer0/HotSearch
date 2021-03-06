package com.lulu.hotsearch.view

import android.animation.*
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.lulu.baseutil.CommonUtil
import com.lulu.baseutil.Init
import com.lulu.baseutil.bezelless.DensityUtil
import com.lulu.basic.image.ImageUtils
import com.lulu.basic.utils.ToastUtil
import com.lulu.hotsearch.HotSearchTitle
import com.lulu.hotsearch.bean.HotSearchConfigBean
import com.lulu.hotsearch.manager.HotSearchConfigManager
import com.lulu.hotsearch.utils.FabAnimUtil
import com.lulu.hotsearch.R
import com.qq.reader.bookstore.define.BookStoreViewParams
import com.qq.reader.bookstore.view.BasePageView
import com.qq.reader.bookstore.view.CommonLoadMoreView


private const val TAG = "WBHotSearchView"
class HotSearchView(context: Context) : BasePageView(context) {
    private var isAdd = false

    private var addBillTranslateList = mutableListOf<AnimatorSet>()
    private var lastIsIdle = true
    private var displayAnim: ObjectAnimator? = null

    lateinit var fabRoot: FloatingActionButton
    lateinit var llFloatContainer: LinearLayout
    lateinit var titleRightTime: TextView
    lateinit var leftImage: ImageView
    lateinit var rightImage: ImageView


    override fun onCreateParams(): BookStoreViewParams {
        return BookStoreViewParams.Builder(
            R.layout.hot_search_fragment,
            R.id.common_recycler_view
        )
            .setDataErrorViewIdRes(R.id.loading_failed_layout)
            .setPullDownViewIdRes(R.id.pull_down_list)
            .setLoadingViewIdRes(R.id.loading_layout)
            .setLoadMoreView(CommonLoadMoreView())
            .setActionBarContainerIdRes(R.id.common_title)
            .setActionBarTitleViewIdRes(R.id.profile_header_title)
            .build()
    }

    override fun onCreateView(contentView: View) {
        initDisplayAnim()
        fabRoot = contentView.findViewById(R.id.fabRoot)
        titleRightTime = contentView.findViewById(R.id.title_right_time)
        leftImage = contentView.findViewById(R.id.leftImage)
        rightImage = contentView.findViewById(R.id.rightImage)
        rightImage.setImageDrawable(Init.context.resources.getDrawable(R.drawable.ic_more_vert_black_24dp))
        initFabList(contentView)
        bindEvents()
    }

    private fun bindEvents() {
        fabRoot.setOnClickListener{fabRootClick()}
        fabRoot.setOnLongClickListener {
            ToastUtil.showShortToast("当前版本：${CommonUtil.getVersionName(context)}")
            return@setOnLongClickListener true
        }
        llFloatContainer.setOnClickListener { hideFABMenu() }
    }

    private fun initFabList(contentView: View) {
        llFloatContainer = contentView.findViewById(R.id.llFloatContainer)
        val configBeanList = HotSearchConfigManager.getConfigList()
        for (withIndex in configBeanList.withIndex()) {
            val value = withIndex.value
            val index = withIndex.index
            val itemView = LayoutInflater.from(context).inflate(R.layout.layout_float_button_item, null)
            val tvFloat = itemView.findViewById<TextView>(R.id.tvFloat)
            val fabFloat = itemView.findViewById<FloatingActionButton>(R.id.fabFloat)
            tvFloat.text = "${value.name}热搜"
            ImageUtils.displayImage(context, value.icon, fabFloat)

            val param = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            param.topMargin = DensityUtil.dip2px(10F)
            llFloatContainer.addView(itemView, param)

            val animatorSet = AnimatorInflater.loadAnimator(context, R.animator.add_bill_anim) as AnimatorSet
            addBillTranslateList.add(animatorSet)
            if (index == configBeanList.size-1) {
                animatorSet.startDelay = 0
            } else {
                animatorSet.startDelay = (150 + (configBeanList.size-2 - index)*50).toLong()
            }
            animatorSet.setTarget(itemView)
            animatorSet.start()//先启动一遍否则有卡顿？？
            fabFloat.setOnClickListener {
                fabClickListener?.onClick(it, value)
                hideFABMenu()
            }
        }

    }


    private fun initDisplayAnim() {
        //https://www.cnblogs.com/sanfeng4476/p/6112284.html

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
                displayAnim = FabAnimUtil.startDisplayAnimForTranslation(
                    fabRoot,
                    isIdle
                )

            }
        })
    }

    private fun fabRootClick() {
        //fabRoot.setImageResource(if (isAdd) R.drawable.ic_add_24px else R.drawable.ic_close_24px)
        if (isAdd) {
            ObjectAnimator.ofFloat(fabRoot, "rotation", -45F, 0F).start()
        } else {
            ObjectAnimator.ofFloat(fabRoot, "rotation", 0F, -45F).start()
        }
        isAdd = !isAdd
        llFloatContainer.visibility = (if (isAdd) View.VISIBLE else View.GONE)
        if (isAdd) {
            for (animatorSet in addBillTranslateList) {
                Log.d(TAG, "onClick: ${animatorSet.startDelay}")
                animatorSet.start()
            }
        }
    }

    public fun hideFABMenu() {
        llFloatContainer.visibility = View.GONE
        ObjectAnimator.ofFloat(fabRoot, "rotation", -45F, 0F).start()
        isAdd = false
    }


    public fun refreshActionBar(type: String) {
        val curConfigBean = HotSearchConfigManager.getCurConfigBean()
        ImageUtils.displayImage(context, curConfigBean.icon, leftImage)
        actionBarTitle.text = curConfigBean.title

    }

    interface OnFabClickListener {
        fun onClick(view: View, bean: HotSearchConfigBean)
    }

    private var fabClickListener: OnFabClickListener? = null

    public fun setOnFabClickListener(listener: OnFabClickListener) {
        fabClickListener = listener
    }

}