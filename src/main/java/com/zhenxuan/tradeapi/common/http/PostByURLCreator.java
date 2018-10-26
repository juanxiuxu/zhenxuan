package com.zhenxuan.tradeapi.common.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;

import java.util.Collections;
import java.util.Map;

/**
 * Created by pippo on 14-5-25.
 */
public class PostByURLCreator implements RequestCreator {

    public PostByURLCreator(String url) {
        this(url, Collections.<String, String> emptyMap());
    }

    public PostByURLCreator(String url, Map<String, String> headers) {
        this.url = url;
        this.headers = headers;
        this.config = CONFIG;
    }

    public PostByURLCreator(int connectTimeout, int soTimeout, String url, Map<String, String> headers) {
        this.config = RequestConfig.custom()
                .setConnectionRequestTimeout(1000)
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(soTimeout)
                .build();
        this.url = url;
        this.headers = headers;
    }

    RequestConfig config;
    String url;
    Map<String, String> headers;

    @Override
    public HttpPost create() throws Exception {
        HttpPost request = new HttpPost(url);
        request.setConfig(config);

        if (headers != null) {
            for (String name : headers.keySet()) {
                request.addHeader(name, headers.get(name));
            }
        }

        return request;
    }
}
