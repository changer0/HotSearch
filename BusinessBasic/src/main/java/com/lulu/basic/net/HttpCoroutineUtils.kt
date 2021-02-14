package com.lulu.basic.net

import android.util.Log
import com.yuewen.reader.zebra.cache.core.IoUtils
import com.yuewen.reader.zebra.utils.GSONUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.io.InputStream
import java.util.*
import java.util.zip.GZIPInputStream

/**
 * @author zhanglulu on 2020/6/29.
 * for Http 请求 协程工具
 */
private const val TAG = "HttpCoroutineUtils"

public class HttpCoroutineUtils {
    companion object {

        /**
         * 协程 Get 请求 <br/>
         * @param url 请求协议 <br/>
         * @param params 请求参数 <br/>
         * @param contentType 类型 <br/>
         * @param needGzip 是否支持 Gzip <br/>
         */
        public suspend fun doRequestGet(url: String,
                                        requestContent: String? = null,
                                        params: HashMap<String, String>? = null,
                                        contentType: String = "application/json",
                                        needGzip: Boolean = false): HttpResponseEntity<Response> = withContext(Dispatchers.IO) {
            val responseEntity = HttpResponseEntity<Response>()
            try {
                responseEntity.responseBean = Http.doRequest(url, requestContent, needGzip, Http.GET, params, contentType, null, null)
            } catch (e: Exception) {
                responseEntity.throwable = e
                Log.e(TAG, "doRequestGet: 请求异常异常")
                e.printStackTrace()
            }
            responseEntity
        }

        /**
         * 协程 POST 请求 <br/>
         * @param url 请求协议 <br/>
         * @param params 请求参数 <br/>
         * @param contentType 类型 <br/>
         * @param needGzip 是否支持 Gzip <br/>
         */
        public suspend fun doRequestPost(url: String,
                                         requestContent: String? = null,
                                         params: HashMap<String, String>? = null,
                                         contentType: String = "application/json",
                                         needGzip: Boolean = false): HttpResponseEntity<Response> = withContext(Dispatchers.IO) {
            val responseEntity = HttpResponseEntity<Response>()
            try {
                responseEntity.responseBean = Http.doRequest(url, requestContent, needGzip, Http.POST, params, contentType, null, null)
            } catch (e: Exception) {
                responseEntity.throwable = e
                Log.e(TAG, "doRequestPost: 请求异常异常")
                e.printStackTrace()
            }
            responseEntity
        }

        /**
         * 协程 Get 请求 <br/>
         * @param url 请求协议 <br/>
         * @param beanClass 返回 Bean 类型 <br/>
         * @param requestContent 请求体 <br/>
         * @param params 请求参数 <br/>
         * @param contentType 类型 <br/>
         * @param needGzip 是否支持 Gzip <br/>
         */
        public suspend fun <T> doRequestGetGSONBean(url: String, beanClass: Class<T>,
                                                    requestContent: String? = null,
                                                    params: HashMap<String, String>? = null,
                                                    contentType: String = "application/json",
                                                    needGzip: Boolean = false): HttpResponseEntity<T> = withContext(Dispatchers.IO) {
            var responseEntity = HttpResponseEntity<T>()
            try {
                val response = Http.doRequest(url, requestContent, needGzip, Http.GET, params, contentType, null, null)
                responseEntity = analyseResponse(response, needGzip, beanClass)
            } catch (e: Exception) {
                responseEntity.throwable = e
                Log.e(TAG, "doRequestGetGSONBean: 请求异常异常")
                e.printStackTrace()
            }
            responseEntity
        }

        /**
         * 协程 Get 请求 <br/>
         * @param url 请求协议 <br/>
         * @param beanClass 返回 Bean 类型 <br/>
         * @param requestContent 请求体 <br/>
         * @param params 请求参数 <br/>
         * @param contentType 类型 <br/>
         * @param needGzip 是否支持 Gzip <br/>
         */
        public suspend fun <T> doRequestPostGSONBean(url: String,
                                                     beanClass: Class<T>,
                                                     requestContent: String? = null,
                                                     params: HashMap<String, String>? = null,
                                                     contentType: String = "application/json",
                                                     needGzip: Boolean = false): HttpResponseEntity<T> = withContext(Dispatchers.IO) {
            var responseEntity = HttpResponseEntity<T>()
            try {
                val response = Http.doRequest(url, requestContent, needGzip, Http.POST, params, contentType, null, null)
                responseEntity = analyseResponse(response, needGzip, beanClass)
            } catch (e: Exception) {
                responseEntity.throwable = e
                Log.e(TAG, "doRequestPostGSONBean: 请求异常异常")
                e.printStackTrace()
            }
            responseEntity
        }

        /**
         * 解析 Response
         */
        private fun <T> analyseResponse(response: Response?, needGzip: Boolean, beanClass: Class<T>): HttpResponseEntity<T> {
            val responseEntity = HttpResponseEntity<T>()
            response?.let {
                var inputStream: InputStream? = null
                var jsonStr = ""
                try {
                    val responseBody: ResponseBody? = response.body()
                    responseBody?.apply {
                        inputStream = if (needGzip) {
                            GZIPInputStream(byteStream())
                        } else {
                            byteStream()
                        }
                        jsonStr = IoUtils.getString(inputStream)
                        //输出 Log
                        val contentLength = contentLength()
                        Log.i(TAG, "协议 URL：" + response.request().url().toString() + " contentLength： " + contentLength)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "analyseResponse: 解析异常")
                    responseEntity.throwable = e
                    e.printStackTrace()
                } finally {
                    response.close()
                    try {
                        inputStream?.close()
                        inputStream = null
                    } catch (var14: IOException) {
                        responseEntity.throwable = var14
                        Log.e(TAG, "inputStream?.close(): 异常")
                        var14.printStackTrace()
                    }
                }
                try {
                    responseEntity.jsonStr = jsonStr
                    responseEntity.responseBean = GSONUtil.parseJsonWithGSON(jsonStr, beanClass)
                    responseEntity.isSuccess = true
                } catch (e: Exception) {
                    Log.e(TAG, "analyseResponse: GSON 转换异常")
                    responseEntity.throwable = e
                    e.printStackTrace()
                }

            }
            return responseEntity
        }
    }
}