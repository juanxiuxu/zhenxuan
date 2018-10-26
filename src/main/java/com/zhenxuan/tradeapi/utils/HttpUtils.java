/*
 * ©1999-2018 Neptune, Inc. All rights reserved.
 * 
 * http://www.neptune.com
 *
 */

package com.zhenxuan.tradeapi.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * http get post
 * @author yupeng.qin
 * @since 2016-06-04
 */
public class HttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * HttpConnection 池化
     */
    private static final PoolingClientConnectionManager manager = new PoolingClientConnectionManager();
    private static final HttpClient client = new DefaultHttpClient(manager);
    private static final int TIME_OUT = 6000;

    private static void setHeader(HttpRequestBase base, Map<String, Object> header) {
        if (header != null && ! header.isEmpty()) {
            for (Map.Entry<String, Object> e : header.entrySet()) {
                base.setHeader(e.getKey(), String.valueOf(e.getValue()));
            }
        }
    }

    private static String url(String url) {
        Preconditions.checkArgument(StringUtils.isNotBlank(url), "url有误!");
        if (url.startsWith("http")) {
            return url;
        }
        return "http://" + url;
    }

    static {
        client.getParams().setParameter("Accept-Encoding", "gzip, deflate, sdch");
        client.getParams().setParameter("Cache-Control", "max-age=0");
        client.getParams().setParameter("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537"
                        + ".36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT); // 连接时间
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, TIME_OUT);         // 数据传输时间
    }

    public static String doPost(String url , String content) {
        return doPost(url, content, null, 0);
    }

    public static String doPost(String url, String content,
                                Map<String, Object> header, int timeOut) {
        HttpPost post = new HttpPost(url(url));

        setHeader(post, header);
        if (timeOut > 0) {
            post.setHeader(CoreConnectionPNames.SO_TIMEOUT, Integer.toString(timeOut));
        }

        HttpResponse response = null;
        try {
            if (StringUtils.isNotBlank(content)) {
                StringEntity entity = new StringEntity(content, Charset.defaultCharset());
                post.setEntity(entity);
            }
            response = client.execute(post);
            if (response == null || response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                logger.error("httpPost请求返回状态有误, 请求连接:{}, 请求参数:{}", url, content);
                return null;
            }
            String s = null;
            try {
                s = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                logger.error("httpPost请求返回值异常, 请求连接:{}, 请求参数:{}", url, content);
            }
            return s;
        } catch (IOException e) {
            logger.error("httpPost 请求异常, ", e);
        } finally {
            // 通过请求的释放委托给HttpClient, 交由系统配置的关闭策略. 而不是每次都关闭连接.
            // 当前系统中无需每次都关闭所有连接再池化连接池.
            post.releaseConnection();
        }
        return null;
    }


    public static String doGet(String url, Map<String, Object> body) {
        return doGet(url, null, body, 0);
    }

    public static String doGet(String url) {
        return doGet(url, null, null, 0);
    }

    public static String doGet(String url, Map<String, Object> header, Map<String, Object> body, int timeOut) {

        StringBuilder sb = new StringBuilder(url(url));
        if (body != null && !body.isEmpty()) {
            if (!url.contains("?")) {
                sb.append("?");
            }
            for (Map.Entry<String, Object> e : body.entrySet()) {
                sb.append(e.getKey()).append("=").append(e.getValue()).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        HttpGet get = new HttpGet(sb.toString());

        setHeader(get, header);
        if (timeOut > 0) {
            get.setHeader(CoreConnectionPNames.SO_TIMEOUT, Integer.toString(timeOut));
        }

        HttpResponse response = null;
        try {
            response = client.execute(get);
            if (response == null || response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                logger.error("httpGet请求返回状态有误, 请求连接:{}, 请求参数:{}", url, body);
                return null;
            }
            String s = null;
            try {
                s = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                logger.error("httpGet请求返回值异常, 请求连接:{}, 请求参数:{}", url, body, e);
            }
            return s;
        } catch (IOException e) {
            logger.error("httpGet 请求异常, ", e);
        } finally {
            // 通过请求的释放委托给HttpClient, 交由系统配置的关闭策略. 而不是每次都关闭连接.
            // 当前系统中无需每次都关闭所有连接再池化连接池.
            get.releaseConnection();
        }
        return null;
    }

    public static String doPostJson(String url , String content) {
        Map<String, Object> m = Maps.newHashMap();
        m.put("Content-Type", "application/json;charset=UTF-8");
        return doPost(url, content, m, 0);
    }
}
