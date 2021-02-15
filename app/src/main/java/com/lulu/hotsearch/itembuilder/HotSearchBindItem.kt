package com.lulu.hotsearch.itembuilder

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.lulu.basic.net.CoroutineScopeManager
import com.lulu.basic.net.HttpCoroutineUtils
import com.qq.reader.bookstore.CommonViewHolder
import com.yuewen.reader.zebra.BaseViewBindItem
import com.lulu.hotsearch.Constant
import com.lulu.hotsearch.ServerUrl
import com.lulu.hotsearch.wb.R
import com.lulu.hotsearch.activity.WebActivity
import com.lulu.hotsearch.bean.HotSearchBean
import com.lulu.hotsearch.bean.Result
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception

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
        when(hotSearchBean.type){
            Constant.HOT_SEARCH_WB -> {
                //微博无需解析
                goWeb(activity, hotSearchBean, hotTitle, itemData.url)
            }
            else -> {
                goOtherHotSearch(activity as FragmentActivity, hotSearchBean, hotTitle)
            }
        }

    }

    private fun goOtherHotSearch(
        activity: FragmentActivity,
        hotSearchBean: HotSearchBean,
        hotTitle: TextView?) {
        val url = ServerUrl.DOMAIN + "obtainRealUrl?destUrl=" + itemData.url
        CoroutineScopeManager.getScope(activity).launch {
            val doRequestGet = HttpCoroutineUtils.doRequestGet(url)
            var realUrl = ""
            try {
                val jsonObject = JSONObject(doRequestGet.jsonStr)
                realUrl = jsonObject.optString("url");
            } catch (e: Exception){
                e.printStackTrace()
            }
            if (TextUtils.isEmpty(realUrl)) {
                Toast.makeText(activity, "跳转失败", Toast.LENGTH_SHORT).show()
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
        intent.putExtra(Constant.WEB_TITLE, itemData.title)
        intent.putExtra(Constant.HOT_SEARCH_TYPE, hotSearchBean.type)
        activity.startActivity(
            intent,
            ActivityOptions.makeSceneTransitionAnimation(activity, hotTitle, "anim_weibo_item")
                .toBundle()
        )
    }

}