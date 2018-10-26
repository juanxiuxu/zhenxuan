package com.zhenxuan.tradeapi.common.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by pippo on 14-5-25.
 */
public class PostByParametersCreator extends PostByURLCreator {

    private static String parameterValuesDelimiter = ",";

    public PostByParametersCreator(String url, Map<String, ?> parameters) {
        super(url);
        this.parameters = parameters;
    }

    public PostByParametersCreator(String url, Map<String, ?> parameters, Charset charset) {
        super(url);
        this.parameters = parameters;
        this.charset = charset;
    }

    public PostByParametersCreator(String url, Map<String, String> headers, Map<String, ?> parameters) {
        super(url, headers);
        this.parameters = parameters;
    }

    public PostByParametersCreator(int connectTimeout,
            int soTimeout,
            String url,
            Map<String, String> headers, Map<String, ?> parameters) {
        super(connectTimeout, soTimeout, url, headers);
        this.parameters = parameters;
    }

    Map<String, ?> parameters;

    Charset charset = Consts.UTF_8;

    @Override
    public HttpPost create() throws Exception {
        HttpPost post = super.create();

        if (parameters != null) {
            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            for (String name : parameters.keySet()) {
                Object value = parameters.get(name);

                if (value == null) {
                    value = StringUtils.EMPTY;
                }

                if (value instanceof String[]) {
                    String[] values = (String[]) value;
                    value = values.length > 1 ? StringUtils.join(value, parameterValuesDelimiter) : values[0];
                }

                pairs.add(new BasicNameValuePair(name, value instanceof String ? (String) value : value.toString()));
            }
            post.setEntity(new UrlEncodedFormEntity(pairs, charset));
        }

        return post;
    }

}
