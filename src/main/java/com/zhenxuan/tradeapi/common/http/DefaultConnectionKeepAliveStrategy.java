package com.zhenxuan.tradeapi.common.http;

import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;

/**
 * Created by pippo on 14-5-25.
 */
public class DefaultConnectionKeepAliveStrategy implements ConnectionKeepAliveStrategy {

    // public static final Logger LOGGER = LoggerFactory.getLogger(DefaultConnectionKeepAliveStrategy.class);

    public static final DefaultConnectionKeepAliveStrategy DEFAULT = new DefaultConnectionKeepAliveStrategy();

    // public static final String KEY_NAME = "timeout";

    public static final long DEFAULT_TIMEOUT = 60 * 1000 * 5;

    //@Override
    public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
        //        if (response == null) {
        //            return DEFAULT_TIMEOUT;
        //        }
        //
        //        final HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
        //        while (it.hasNext()) {
        //            final HeaderElement he = it.nextElement();
        //            final String param = he.getName();
        //            final String value = he.getValue();
        //            if (value != null && param.equalsIgnoreCase(KEY_NAME)) {
        //                try {
        //                    return Long.parseLong(value) * 1000;
        //                } catch (final NumberFormatException ignore) {
        //                    LOGGER.warn("invalid timeout value:[{}]", value);
        //                }
        //            }
        //        }

        return DEFAULT_TIMEOUT;
    }

}
