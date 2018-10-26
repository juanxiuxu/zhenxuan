/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.zhenxuan.tradeapi.filter;

import com.google.common.base.Throwables;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class TokenWrapper extends HttpServletRequestWrapper {

    private static final Logger logger = LoggerFactory.getLogger(TokenWrapper.class);

    /**
     * 存放原有请求参数
     */
    private Map<String, String[]> originParams;

    /**
     * 存放原油请求body
     */
    private byte[] originBody;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @throws IllegalArgumentException if the request is null
     */
    public TokenWrapper(HttpServletRequest request) {
        super(request);
        this.originParams = readReqParams(request);
        this.originBody = readReqBody(request);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Set<String> paramNames = Sets.newLinkedHashSet();
        paramNames.addAll(originParams.keySet());
        return Collections.enumeration(paramNames);
    }

    @Override
    public String getParameter(String name) {
        String[] values = originParams.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        return originParams.get(name);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {
            ByteArrayInputStream bais = new ByteArrayInputStream(originBody);

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                throw new RuntimeException("Not supported");
            }

            @Override
            public boolean isFinished() {
                return bais.available() == 0;
            }
        };
    }

    public Map<String, String[]> getOriginParams() {
        return originParams;
    }

    public byte[] getOriginBody() {
        return originBody;
    }

    private Map<String, String[]> readReqParams(ServletRequest request) {
        return request.getParameterMap();
    }

    private byte[] readReqBody(ServletRequest request) {
        InputStream is = null;
        BufferedReader reader = null;

        try {
            is = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is));

            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            return buffer.toString().getBytes();
        } catch (IOException e) {
            logger.error("Fail to read content from request. err:{}", Throwables.getStackTraceAsString(e));
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("Fail to close inputStream");
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    logger.error("Fail to close reader");
                }
            }
        }

        return null;
    }
}


