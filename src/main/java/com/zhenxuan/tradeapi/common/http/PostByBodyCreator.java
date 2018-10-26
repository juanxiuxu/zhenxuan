package com.zhenxuan.tradeapi.common.http;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

import java.util.Map;

/**
 * Created by pippo on 14-5-25.
 */
public class PostByBodyCreator extends PostByURLCreator {

    public PostByBodyCreator(String url, byte[] body) {
        super(url);
        this.body = body;
    }

    public PostByBodyCreator(String url, Map<String, String> headers, byte[] body) {
        super(url, headers);
        this.body = body;
    }

    public PostByBodyCreator(int connectTimeout,
            int soTimeout,
            String url,
            Map<String, String> headers, byte[] body) {
        super(connectTimeout, soTimeout, url, headers);
        this.body = body;
    }

    byte[] body;

    @Override
    public HttpPost create() throws Exception {
        HttpPost post = super.create();
        post.setEntity(new ByteArrayEntity(body));
        return post;
    }

}
