package com.zhenxuan.tradeapi.common.http;

import org.apache.http.client.methods.HttpPost;

import java.util.Map;

/**
 * Created by pippo on 15/11/2.
 */
public class PostByJsonBodyCreator extends PostByBodyCreator {

    public PostByJsonBodyCreator(String url, byte[] body) {
        super(url, body);
    }

    public PostByJsonBodyCreator(String url, Map<String, String> headers, byte[] body) {
        super(url, headers, body);
    }

    public PostByJsonBodyCreator(int connectTimeout,
            int soTimeout,
            String url,
            Map<String, String> headers, byte[] body) {
        super(connectTimeout, soTimeout, url, headers, body);
    }

    @Override
    public HttpPost create() throws Exception {
        HttpPost request = super.create();
        request.setHeader("Content-Type", "application/json");
        return request;
    }
}
