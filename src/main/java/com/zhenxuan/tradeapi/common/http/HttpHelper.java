/*
 * Copyright © 2010 www.myctu.cn. All rights reserved.
 */
/**
 * project : des-rss-fetcher
 * user created : pippo
 * date created : 2009-8-29 - 上午01:00:51
 */
package com.zhenxuan.tradeapi.common.http;

import com.zhenxuan.tradeapi.utils.JsonUtil;
import org.apache.http.client.HttpClient;

import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

/**
 * HttpHelper
 *
 * @author pippo
 * @since 2009-8-29
 */
public class HttpHelper extends AbstractHttpHelper {

    protected static HttpHelper instance = new HttpHelper();

    public static HttpHelper instance() {
        return instance;
    }

    protected HttpHelper() {
        httpClient = new HttpClientBuilderWrapper().build();
    }

    protected HttpClient httpClient;

    protected HttpClient getHttpClient() {
        return this.httpClient;
    }

    public String post(String url, Map<String, ?> parameters) {
        return execute(new PostByParametersCreator(url, parameters), ResponseToString.DEFAULT);
    }

    public String postWithJson(String url, Map<String, String> headers, Map<String, ?> parameters) {
        byte[] bytes = JsonUtil.toBytes(parameters);
        return execute(new PostByJsonBodyCreator(url, headers, bytes), ResponseToString.DEFAULT);
    }


    public String post(String url, Map<String, ?> parameters, String charset) {
        return execute(new PostByParametersCreator(url, parameters), new ResponseToString(charset));
    }

    public <T> T post(String url, Map<String, ?> parameters, DelegateResponseHandler<T> handler) {
        return execute(new PostByParametersCreator(url, parameters), new DefaultResponseHandler<>(handler));
    }

    public <T> T post(String url,
                      Map<String, String> headers,
                      Map<String, ?> parameters,
                      DelegateResponseHandler<T> handler) {
        return execute(new PostByParametersCreator(url, headers, parameters), new DefaultResponseHandler<>(handler));
    }

    public <T> T post(int connectTimeout,
                      int soTimeout,
                      String url,
                      Map<String, String> headers,
                      Map<String, ?> parameters,
                      DelegateResponseHandler<T> handler) {
        return execute(new PostByParametersCreator(connectTimeout, soTimeout, url, headers, parameters),
                new DefaultResponseHandler<>(handler));
    }

    public String post(String url, String body) {
        return execute(new PostByBodyCreator(url, body.getBytes()), ResponseToString.DEFAULT);
    }

    public String post(String url, String body, String charset) {
        return execute(new PostByBodyCreator(url, body.getBytes()), new ResponseToString(charset));
    }

    public String post(int connectTimeout, int soTimeout, String url, String body, String charset) {
        return execute(new PostByBodyCreator(connectTimeout,
                        soTimeout,
                        url,
                        Collections.<String, String>emptyMap(),
                        body.getBytes()),
                new ResponseToString(charset));
    }

    public <T> T post(String url, String body, DelegateResponseHandler<T> handler) {
        return this.post(url, body.getBytes(), handler);
    }

    public <T> T post(String url, byte[] body, DelegateResponseHandler<T> handler) {
        return execute(new PostByBodyCreator(url, body), new DefaultResponseHandler<>(handler));
    }

    public <T> T post(String url, Map<String, String> headers, byte[] body, DelegateResponseHandler<T> handler) {
        return execute(new PostByBodyCreator(url, headers, body), new DefaultResponseHandler<>(handler));
    }

    public <T> T post(int connectTimeout,
                      int soTimeout,
                      String url,
                      Map<String, String> headers,
                      byte[] body,
                      DelegateResponseHandler<T> handler) {
        return execute(new PostByBodyCreator(connectTimeout, soTimeout, url, headers, body),
                new DefaultResponseHandler<>(handler));
    }

    public String get(String url) {
        return execute(new GetByURLCreator(url), ResponseToString.DEFAULT);
    }

    public String get(String url, String charset) {
        return execute(new GetByURLCreator(url), new ResponseToString(charset));

    }

    public <T> T get(String url, DelegateResponseHandler<T> handler) {
        return execute(new GetByURLCreator(url), new DefaultResponseHandler<>(handler));
    }

    public <T> T get(int connectTimeout, int soTimeout, String url, DelegateResponseHandler<T> handler) {
        return execute(new GetByURLCreator(connectTimeout, soTimeout, url, Collections.<String, String>emptyMap()),
                new DefaultResponseHandler<>(handler));
    }

    public interface DelegateResponseHandler<T> {

        T handle(int status, Map<String, String> headers, InputStream input) throws Exception;

    }

}
