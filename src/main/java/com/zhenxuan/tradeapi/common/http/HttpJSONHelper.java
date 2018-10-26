package com.zhenxuan.tradeapi.common.http;


import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.zhenxuan.tradeapi.common.ZXException;
import com.zhenxuan.tradeapi.common.enums.ResultStatusCode;
import com.zhenxuan.tradeapi.utils.JsonUtil;
import com.zhenxuan.tradeapi.utils.XmlUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by pippo on 15/8/23.
 */
public class HttpJSONHelper extends HttpHelper {

    protected static HttpJSONHelper instance = new HttpJSONHelper();

    public static HttpJSONHelper instance() {
        return instance;
    }

    private HttpJSONHelper() {
        super();
    }

    public <T> T get(final String address, final Class<T> clazz) {
        HttpGet get = new HttpGet(address);
        get.addHeader("Content-Type", "application/json");
        return execute(get, new ResponseHandler<T>() {

            //@Override
            public T handleResponse(HttpResponse response) throws IOException {
                return HttpJSONHelper.this.handleResponse(response, clazz, address, null);
            }

        });
    }

    public <T> T get(String address, Map<String, Object> params, Class<T> clazz) {
        return get(address, params, clazz, Consts.UTF_8);
    }

    public <T> T get(String address, Map<String, Object> params, Class<T> clazz, Charset charset) {

        Map<String, Object> urlEncodeMap = null;
        try {
            urlEncodeMap = encodeParams(params, charset);
        } catch (UnsupportedEncodingException e) {
            throw new ZXException(ResultStatusCode.HTTP_ERROR,
                    "urlencode params failed,params:" + JsonUtil.toString(params));
        }
        String url = setParamToAddress(address, urlEncodeMap);
        return get(url, clazz);
    }

    private Map<String, Object> encodeParams(Map<String, Object> params, Charset charset)
            throws UnsupportedEncodingException {
        Map<String, Object> res = Maps.newHashMap();
        String charsetStr = charset.displayName();

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            res.put(URLEncoder.encode(entry.getKey(), charsetStr), URLEncoder.encode(entry.getValue()
                    .toString(), charsetStr));

        }
        return res;
    }

    private String setParamToAddress(String address, Map<String, Object> params) {
        if (StringUtils.isBlank(address) || params == null || params.isEmpty()) {
            return Strings.nullToEmpty(address);

        }
        StringBuilder stbURL = new StringBuilder(address);
        stbURL.append(address.contains("?") ? "&" : "?");
        for (Map.Entry<String, Object> entrySet : params.entrySet()) {
            if (entrySet.getValue() == null) {
                continue;
            }
            if (StringUtils.isBlank(entrySet.getValue().toString())) {
                continue;
            }
            stbURL.append(entrySet.getKey())
                    .append("=")
                    .append(entrySet.getValue())
                    .append("&");
        }
        // 删除末尾多余的 & 或 ?
        stbURL.delete(stbURL.length() - 1, stbURL.length());
        return stbURL.toString();
    }

    public <T> T post(String address, Object request, Class<T> clazz) {
        return post(address, JsonUtil.toBytes(request), clazz);
    }

    public <T> T post(String address, String body, Class<T> clazz) {
        return post(address, body.getBytes(), clazz);
    }

    public <T> T post(final String address, final byte[] body, final Class<T> clazz) {
        HttpPost post = new HttpPost(address);
        post.addHeader("Content-Type", "application/json");
        post.setEntity(new ByteArrayEntity(body));

        return execute(post, new ResponseHandler<T>() {

            //@Override
            public T handleResponse(HttpResponse response) throws IOException {
                return HttpJSONHelper.this.handleResponse(response, clazz, address, body);
            }
        });
    }

    public <T> T post(final String address, final Map<String, ?> parameters, final Class<T> clazz) {
        return execute(new PostByParametersCreator(address, parameters), new ResponseHandler<T>() {

            // @Override
            public T handleResponse(HttpResponse response) throws IOException {
                return HttpJSONHelper.this.handleResponse(response, clazz, address, parameters);
            }
        });
    }

    public <T> T post(final String address, final Map<String, ?> parameters, final Charset charset,
                      final Class<T> clazz) {
        return execute(new PostByParametersCreator(address, parameters, charset), new ResponseHandler<T>() {

            // @Override
            public T handleResponse(HttpResponse response) throws IOException {
                return HttpJSONHelper.this.handleResponse(response, clazz, address, parameters);
            }
        });
    }

    private <T> T handleResponse(HttpResponse response, Class<T> clazz, String address, Object body) throws
            IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        switch (statusCode) {
            case 200:
                return serializeResponse(response, clazz);
            default:
                throw new HttpRouteException(String.format(
                        "route to address:[%s] with body:[%s] due to error:[%s]",
                        address,
                        body instanceof byte[] ? new String((byte[]) body) : body,
                        IOUtils.toString(response.getEntity().getContent())));
        }
    }

    private <T> T serializeResponse(HttpResponse response, Class<T> clazz) {
        // 增加了个对xml返回数据格式的处理
        String contentType = response.getEntity().getContentType().getValue();

        try {
            if (contentType.toLowerCase().contains(DataInterchange.XML.des)) {
                return XmlUtil.toObject(response.getEntity().getContent(), clazz);
            } else {
                return JsonUtil.toObject(response.getEntity().getContent(), clazz);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

}
