package com.qq.reader.provider.simple;

import android.text.TextUtils;

import com.qq.reader.provider.inter.INetQuestParams;

/**
 * 针对一次请求参数封装。【解决同步问题】
 *
 * 解决由于请求参数导致的线程同步问题：
 *
 * DataProvider 中持有 requestBean 作为请求参数，设计的目的后续可以通过他去构建请求参数。
 *
 *考虑一个场景，当调用 DataProvider.loadData 方法后，如果在进行网络请求前再次修改了 requestBean 就会导致上次请求丢失！
 *
 *通过封装这个类在 loadData 时将一次请求的参数构建好直接传给各 Task
 */
public class OnceRequestParams implements INetQuestParams {

    String url;
    String requestMethod;
    String requestContent;
    String requestContentType;
    boolean needGzip;

    private OnceRequestParams(String url, String requestMethod, String requestContent, String requestContentType, boolean needGzip) {
        this.url = url;
        this.requestMethod = requestMethod;
        this.requestContent = requestContent;
        this.requestContentType = requestContentType;
        this.needGzip = needGzip;
    }

    /**
     * 构建一次请求参数
     */
    public static OnceRequestParams buildParams(INetQuestParams params) {
        return new OnceRequestParams(params.getUrl(),
                params.getRequestMethod(),
                params.getRequestContent(),
                params.getContentType(),
                params.needGzip());
    }

    /**
     * 获取缓存 Key
     * POST 请求使用 url+contentType+requestContent
     * GET 请求使用 url
     * <br/>
     * 缓存文件,如果是post请求，则以url+contentType+requestContent生成的SHA256值作为key，取出缓存文件，因缓存未考虑区分用户，所以具体先不以uid加入其中作为区分。
     * Key 的生成由缓存层去做！
     */
    public String getCacheKey() {
        if (TextUtils.equals(requestMethod, "POST")) {
            return url + requestMethod + requestContent + requestContentType;
        }
        return url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getRequestMethod() {
        return requestMethod;
    }

    @Override
    public String getContentType() {
        return requestContentType;
    }

    @Override
    public String getRequestContent() {
        return requestContent;
    }

    @Override
    public boolean needGzip() {
        return needGzip;
    }
}
