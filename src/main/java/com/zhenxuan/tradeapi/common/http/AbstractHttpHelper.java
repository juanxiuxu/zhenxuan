/*
 * Copyright © 2010 www.myctu.cn. All rights reserved.
 */
/**
 * project : ctu-utils
 * user created : pippo
 * date created : 2013-4-17 - 下午12:11:51
 */
package com.zhenxuan.tradeapi.common.http;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketException;
import java.util.Arrays;

/**
 * AbstractHttpHelper
 *
 * @author pippo
 * @since 2013-4-17
 */
public abstract class AbstractHttpHelper {

    private static Logger logger = LoggerFactory.getLogger(HttpHelper.class);

    public static class GZIPRequestInterceptor implements HttpRequestInterceptor {

        public static final GZIPRequestInterceptor DEFAULT = new GZIPRequestInterceptor();

        public static final String GZIP = "gzip";

        public static final String ACCEPT_ENCODING = "Accept-Encoding";

        // @Override
        public void process(final HttpRequest request, final HttpContext context) throws HttpException, IOException {
            if (!request.containsHeader(ACCEPT_ENCODING)) {
                request.addHeader(ACCEPT_ENCODING, GZIP);
            }
        }

    }

    public static class GZIPResponseInterceptor implements HttpResponseInterceptor {

        public static final GZIPResponseInterceptor DEFAULT = new GZIPResponseInterceptor();

        // @Override
        public void process(final HttpResponse response, final HttpContext context) throws HttpException, IOException {
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return;
            }

            Header header = entity.getContentEncoding();
            if (header == null) {
                return;
            }

            HeaderElement[] codecs = header.getElements();

            for (int i = 0; i < codecs.length; i++) {
                if (codecs[i].getName().equalsIgnoreCase(GZIPRequestInterceptor.GZIP)) {
                    logger.debug("the content encoding is:{}, use gzip decompressing", Arrays.toString(codecs));
                    response.setEntity(new GzipDecompressingEntity(entity));
                    return;
                }
            }
        }

    }

    public static class ResponseToString implements ResponseHandler<String> {

        public static final ResponseToString DEFAULT = new ResponseToString(CharEncoding.UTF_8);

        public ResponseToString(String charset) {
            this.charset = charset;
        }

        private String charset;

        // @Override
        public String handleResponse(HttpResponse response) throws IOException {
            return new String(IOUtils.toByteArray(response.getEntity().getContent()), charset);
        }
    }

    protected abstract HttpClient getHttpClient();

    protected <T> T execute(RequestCreator creator, ResponseHandler<T> handler) {
        try {
            return execute(creator.create(), handler);
        } catch (HttpRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new HttpRuntimeException(e);
        }
    }

    protected <T> T execute(HttpRequestBase request, ResponseHandler<T> handler) {
        try {
            return getHttpClient().execute(request, handler);
        } catch (HttpRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new HttpRuntimeException(e);
        } finally {
            request.releaseConnection();
        }
    }

    protected <T> T execute(HttpRequestBase request, ResponseHandler<T> handler, int retry) {
        try {
            return getHttpClient().execute(request, handler);
        } catch (NoHttpResponseException | SocketException e) {
            if (retry > 5) {
                throw new HttpRuntimeException(e);
            }
            logger.warn("Will Retry:{} By Catch NoHttpResponseException|SocketException", retry);
            return execute(request, handler, retry + 1);
        } catch (HttpRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new HttpRuntimeException(e);
        } finally {
            request.releaseConnection();
        }
    }

}