package com.zhenxuan.tradeapi.common.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;

import java.util.Collections;
import java.util.Map;

/**
 * Created by pippo on 14-5-25.
 */
public class GetByURLCreator implements RequestCreator {

    public GetByURLCreator(String url) {
        this(url, Collections.<String, String> emptyMap());
    }

    public GetByURLCreator(String url, Map<String, String> headers) {
        this.url = url;
        this.headers = headers;
        this.config = CONFIG;
    }

    public GetByURLCreator(int connectTimeout, int soTimeout, String url, Map<String, String> headers) {
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
    public HttpGet create() throws Exception {
        HttpGet request = new HttpGet(url);
        request.setConfig(config);

        if (headers != null) {
            for (String name : headers.keySet()) {
                request.addHeader(name, headers.get(name));
            }
        }

        return request;
    }
}
