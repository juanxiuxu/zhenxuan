package com.zhenxuan.tradeapi.common.http;

import org.apache.http.Consts;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * Created by pippo on 16/1/2.
 */
public class HttpClientBuilderWrapper {

    public HttpClient build() {
        return HttpClientBuilder.create()
                .setConnectionManager(getClientConnectionManager())
                .setDefaultRequestConfig(getClientRequestConfig())
                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.DEFAULT)
                .addInterceptorFirst(AbstractHttpHelper.GZIPRequestInterceptor.DEFAULT)
                .addInterceptorLast(AbstractHttpHelper.GZIPResponseInterceptor.DEFAULT)
                .build();
    }

    protected HttpClientConnectionManager getClientConnectionManager() {
        PoolingHttpClientConnectionManager clientConnectionManager =
                new PoolingHttpClientConnectionManager(getRegistry(),
                        null,
                        null,
                        null,
                        5,
                        TimeUnit.MINUTES);
        clientConnectionManager.setDefaultConnectionConfig(ConnectionConfig.custom()
                .setBufferSize(4096)
                .setCharset(Consts.UTF_8)
                .build());
        clientConnectionManager.setDefaultSocketConfig(SocketConfig.custom().setSoTimeout(2000).build());
        clientConnectionManager.setDefaultMaxPerRoute(1024);
        clientConnectionManager.setMaxTotal(4096);
        return clientConnectionManager;
    }

    protected RequestConfig getClientRequestConfig() {
        return RequestConfig.custom()
                .setConnectionRequestTimeout(1000)
                .setConnectTimeout(1000 * 10)
                .setSocketTimeout(1000 * 60)
                .build();
    }

    protected Registry<ConnectionSocketFactory> getRegistry() {
        try {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(
                    null,
                    new TrustAllStrategy()).build();
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext,
                    new NopHostnameVerifier());

            return RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", socketFactory)
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class TrustAllStrategy implements TrustStrategy {

        @Override
        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            return true;
        }
    }

    public static class NopHostnameVerifier implements X509HostnameVerifier {

        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }

        @Override
        public void verify(String host, SSLSocket ssl) throws IOException {

        }

        @Override
        public void verify(String host, X509Certificate cert) throws SSLException {

        }

        @Override
        public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {

        }
    }

}
