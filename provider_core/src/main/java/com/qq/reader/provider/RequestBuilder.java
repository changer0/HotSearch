package com.qq.reader.provider;

/**
 * 请求构建类
 */
public class RequestBuilder<T> {

    private Class<T> responseClazz;

    public RequestBuilder(Class<T> responseClazz) {
        this.responseClazz = responseClazz;
    }

    /**
     * 协议地址
     */
    private String url;

    public void url(String url) {
        this.url = url;
    }

    public void load() {
        //new DataProvider<>()
    }
}
