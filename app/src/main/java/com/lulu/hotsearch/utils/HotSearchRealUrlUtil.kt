package com.lulu.hotsearch.utils

import androidx.fragment.app.FragmentActivity
import com.lulu.basic.net.CoroutineScopeManager
import com.lulu.basic.net.HttpCoroutineUtils
import com.lulu.basic.define.ServerUrl
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception

object HotSearchRealUrlUtil {
    public fun parseReadUrl(activity: FragmentActivity, destUrl: String, finish: (realUrl: String) -> Unit) {
        val url = ServerUrl.DOMAIN + "obtainRealUrl?destUrl=" + destUrl
        CoroutineScopeManager.getScope(activity).launch {
            val doRequestGet = HttpCoroutineUtils.doRequestGet(url)
            var realUrl = ""
            try {
                val jsonObject = JSONObject(doRequestGet.jsonStr)
                realUrl = jsonObject.optString("url");
            } catch (e: Exception){
                e.printStackTrace()
            }
            finish(realUrl)

        }
    }
}