package com.qq.reader.zebra.inter;

/**
 * 网络请求请求参数接口
 */
public interface INetQuestParams {

    /*** 获取请求协议地址*/
    String getUrl();

    /*** 获取请求request的方式，get or post*/
    default String getRequestMethod(){
        return "GET";
    }

    /*** 获取 请求的contentType*/
    default String getContentType() {
        return "application/text";
    }

    /*** 获取请求对象的 内容*/
    default String getRequestContent() {
        return null;
    }

    /*** Gzip */
    default boolean needGzip() {
        return false;
    }
}
