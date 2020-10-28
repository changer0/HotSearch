package com.qq.reader.provider.task;

/**
 * 网络请求请求参数接口
 */
public interface INetQuestParams {
    String getUrl();
    String getRequestMethod();
    String getContentType();
    String getRequestContent();
    boolean needGzip();
}
