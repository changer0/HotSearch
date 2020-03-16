package com.qq.reader.common.monitor

import android.text.TextUtils
import com.qq.reader.common.monitor.net.ReadTimeUploadTask
import com.qq.reader.core.readertask.ReaderTaskHandler
import com.tencent.mars.xlog.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

/**
 *
 * 需要加密上报的日志管理
 *
 * 阅读时长上报管理类
 *
 * @author lvxinghe
 */
open class ReadTimeUploadManager private constructor(): BaseStatisticsManager() {
    companion object{
        val TAG = "stat ReadTimeUploadManager"
        val INSTANCE: ReadTimeUploadManager by lazy{
            ReadTimeUploadManager()
        }
    }

    /**
     * 处理数据，往内存list写数据
     */
    fun commit(node: Node?) {
        Log.d(TAG, "commit node :  level "+ node?.toString())
        try {
            if(null === node){
                return
            }
            tryCommit(node, true, StatisticsConstant.STAT_LEVEL_HIGH)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun addData2Map(level: Int, key: String, data: ArrayList<Node>) {
        if (data.size > 0) {
            mReadTimeHighLevelCacheMap[key] = data.clone()
        }
    }

    override fun dealSendData(sendTaskSize: Int): Int = sendByLevel(mReadTimeHighLevelCacheMap,sendTaskSize, mUploadTastSizeEachTime)

    override fun removeUploadDataFromMap(key: String?) {
        if (!TextUtils.isEmpty(key)) {
            try {
//                Log.i("adfasdfadsfa","remove key:${key}")
                mReadTimeHighLevelCacheMap.remove(key)
                mStartedSet.remove(key)
            } catch (e: Exception) {
                Log.e(TAG,"key = $key exception = ${e.message}")
            }
        }
    }

    override fun buildUploadContent(list: ArrayList<Node>): JSONArray? {
        val statisticsJsonarray = JSONArray()
        try {
            for (node in list) {
                val json = JSONObject()
                Log.d("stat", "build " + node.stat_params)
                if (node.stat_params != null) {
                    val iter = node.stat_params.keys()
                    while (iter.hasNext()) {
                        val _key = iter.next()
                        json.put(_key, node.stat_params[_key])
                    }
                }
                if (node.other != null) {
                    val iter = node.other.keys()
                    while (iter.hasNext()) {
                        val _key = iter.next()
                        json.put(_key, node.other[_key])
                    }
                }
                statisticsJsonarray.put(json)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return statisticsJsonarray
    }

    override fun uploadStatiticsDelay(json: JSONObject?, key: String) {
        Log.d(TAG, "uploadStatiticsDelay $key")
        val task = ReadTimeUploadTask(StatiticsReaderNetTaskListener(key), json)
        ReaderTaskHandler.getInstance().addTask(task)
    }
}