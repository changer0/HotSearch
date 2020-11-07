package com.qq.reader.provider;

/**
 *
 */
public class Provider {

    class TestBean {

    }
    public static void main(String[] args) {
        Provider.with(TestBean.class)
                .url("http://xxx");
    }

    public static <T> RequestBuilder with(Class<T> responseClazz) {
        return new RequestBuilder(responseClazz);
    }

}
