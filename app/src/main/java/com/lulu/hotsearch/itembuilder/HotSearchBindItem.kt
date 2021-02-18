package com.lulu.hotsearch.itembuilder

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.text.TextUtils
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.lulu.basic.utils.ToastUtil
import com.qq.reader.bookstore.CommonViewHolder
import com.yuewen.reader.zebra.BaseViewBindItem
import com.lulu.hotsearch.define.Constant
import com.lulu.hotsearch.utils.HotSearchRealUrlUtil
import com.lulu.hotsearch.R
import com.lulu.hotsearch.activity.WebActivity
import com.lulu.hotsearch.bean.HotSearchBean
import com.lulu.hotsearch.bean.Result

class HotSearchBindItem(itemData: Result?) :
    BaseViewBindItem<Result, CommonViewHolder>(itemData) {
    override fun getResLayoutId(): Int {
        return R.layout.hot_search_item
    }

    override fun bindView(holder: CommonViewHolder, activity: Activity): Boolean {
        val hotIndex = holder.getView<TextView>(R.id.hotIndex)
        val hotTitle = holder.getView<TextView>(R.id.hotTitle)
        val hotNum = holder.getView<TextView>(R.id.hotNum)
        val hotTag = holder.getView<TextView>(R.id.hotTag)
        hotIndex.text = itemData.order
        hotTitle.text = itemData.title
        hotNum.text = itemData.hotNum
        hotTag.text = itemData.tag
        holder.itemView.setOnClickListener {
            goDetail(activity, hotTitle)
        }
        return true
    }

    private fun goDetail(activity: Activity, hotTitle: TextView?) {
        val hotSearchBean: HotSearchBean? = zebra.getData<HotSearchBean>()
        hotSearchBean?:return
        if (TextUtils.isEmpty(itemData.realUrl)) {
            goOtherHotSearch(activity as FragmentActivity, hotSearchBean, hotTitle)
        } else {
            goWeb(activity, hotSearchBean, hotTitle, itemData.realUrl)
        }

    }

    private fun goOtherHotSearch(
        activity: FragmentActivity,
        hotSearchBean: HotSearchBean,
        hotTitle: TextView?) {
        HotSearchRealUrlUtil.parseReadUrl(activity, itemData.url) { realUrl ->
            if (TextUtils.isEmpty(realUrl)) {
                ToastUtil.showShortToast("跳转失败")
            } else {
                goWeb(activity, hotSearchBean, hotTitle, realUrl)
            }
        }
    }

    private fun goWeb(
        activity: Activity,
        hotSearchBean: HotSearchBean,
        hotTitle: TextView?,
        url: String
    ) {
        val intent = Intent(activity, WebActivity::class.java)
        intent.putExtra(Constant.WEB_URL, url)
        intent.putExtra(Constant.WEB_CUR_ORDER, itemData.order)
        intent.putExtra(Constant.WEB_TITLE, itemData.title)
        intent.putExtra(Constant.WEB_HOT_SEARCH_DATA, hotSearchBean)
        activity.startActivity(
            intent,
            ActivityOptions.makeSceneTransitionAnimation(activity, hotTitle, "anim_weibo_item")
                .toBundle()
        )
    }

}