package com.lulu.basic.net

/**
 * @author zhanglulu on 2020/7/2.
 * for Http 响应实体类
 */
class HttpResponseEntity<T> {
    /**
     * 响应 Bean
     */
    public var responseBean: T? = null

    /**
     * 异常信息
     */
    public var throwable: Throwable? = null

    /**
     * 是否成功
     */
    public var isSuccess = false

    /**
     * Json 字符串
     */
    public var jsonStr = ""
}