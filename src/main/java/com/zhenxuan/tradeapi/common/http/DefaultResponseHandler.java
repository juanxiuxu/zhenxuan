package com.zhenxuan.tradeapi.common.http;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pippo on 16/1/2.
 */
public class DefaultResponseHandler<T> implements ResponseHandler<T> {

    public DefaultResponseHandler(HttpHelper.DelegateResponseHandler<T> delegate) {
        this.delegate = delegate;
    }

    private HttpHelper.DelegateResponseHandler<T> delegate;

    @Override
    public T handleResponse(HttpResponse response) throws IOException {
        try {
            return delegate.handle(response.getStatusLine().getStatusCode(),
                    parseHeaders(response.getAllHeaders()),
                    response.getEntity().getContent());
        } catch (Exception e) {
            throw new HttpRuntimeException(e);
        }
    }

    protected Map<String, String> parseHeaders(Header[] headers) {
        Map<String, String> hm = new HashMap<>();
        for (Header header : headers) {
            hm.put(header.getName(), header.getValue());
        }
        return hm;
    }
}
