package com.zhenxuan.tradeapi.utils;

import com.google.common.base.CaseFormat;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Map;

/**
 * Common method utils.
 *
 * @author heyijie
 * @author leimingshan
 * @since 2016/6/1
 */
public class CommonUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    private static CaseFormat camelFormat = CaseFormat.LOWER_CAMEL;
    private static CaseFormat underscoreFormat = CaseFormat.LOWER_UNDERSCORE;

    /**
     * Used to parse passport query result in map.
     *
     * @param passportResult passport query result
     *
     * @return one map store field and values
     *
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> parsePassportResult(String passportResult) throws UnsupportedEncodingException {
        Map<String, String> resultMap = Splitter.onPattern("[&]{1,}").trimResults()
                .withKeyValueSeparator('=').split(passportResult);
        return resultMap;
    }

    /**
     * MD5 digest algorithm.
     * 字符串编码后,对字符串计算MD5，输出小写MD5值
     *
     * @param res     string to be calculated
     * @param charset character set
     *
     * @return MD5 digest string in lower case
     */
    public static String md5Digest(String res, String charset) {
        logger.debug("md5Digest string:{},charset:{}", res, charset);
        if (res == null || "".equals(res)) {
            return null;
        }
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        byte[] strTemp;
        try {
            strTemp = res.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            logger.error("md5Digest exception", e);
            return null;
        }

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            String dd = new String(str);
            return dd;
        } catch (NoSuchAlgorithmException e) {
            logger.error("md5Digest exception", e);
            return null;
        }
    }

    /**
     * 生成 HMACSHA256
     * @param data 待处理数据
     * @param key 密钥
     * @return 加密结果
     * @throws Exception
     */
    public static String hmacSHA256Digest(String data, String key, String charset) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(charset), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] array = sha256_HMAC.doFinal(data.getBytes(charset));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    public static Map<String, Object> convertParam2LowerScore(Map<String, Object> map) {
        Map<String, Object> res = Maps.newHashMap();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            res.put(convertParam2LowerScore(entry.getKey()), entry.getValue());
        }
        return res;
    }

    private static String convertParam2LowerScore(String fromStr) {
        return camelFormat.to(underscoreFormat, fromStr);
    }

    /**
     * Get client IP address on HttpServletRequest.
     *
     * @param request http servlet request
     *
     * @return string of ip address in dot format
     */
    public static String getRequestIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null) {
            return "";
        }
        if (ip.contains(",")) {
            int index = ip.indexOf(",");
            ip = ip.substring(0, index);
        }
        return ip;
    }

    /**
     * 根据参数拼接url
     */
    public static String setParam(final String url, final Map<String, Object> params) {
        if (url == null || params == null || params.isEmpty()) {
            return Strings.nullToEmpty(url);
        }

        StringBuilder stbURL = new StringBuilder(url);
        stbURL.append(url.contains("?") ? "&" : "?");

        for (Map.Entry<String, Object> entrySet : params.entrySet()) {
            if (StringUtils.isBlank(entrySet.getValue().toString())) {
                continue;
            }
            try {
                stbURL.append(entrySet.getKey())
                        .append("=")
                        .append(URLEncoder.encode(entrySet.getValue().toString(), "GBK"))
                        .append("&");
            } catch (UnsupportedEncodingException e) {
                logger.error("Error when prepared for remote http params", e);
            }
        }
        // 删除末尾多余的 & 或 ?
        stbURL.delete(stbURL.length() - 1, stbURL.length());
        return stbURL.toString();
    }

    private static byte[] localMacAddr;
    private static String localIpAddr;
    static {
        localMacAddr = getLocalMacAddr();
        if (localMacAddr == null || localMacAddr.length == 0) {
            throw new RuntimeException("BUG: Can not get local mac address when bootstrap");
        }

        localIpAddr = getLocalIpAddr();
        if (StringUtils.isEmpty(localIpAddr)) {
            throw new RuntimeException("BUG: Can not get local ip address when bootstrap");
        }

        logger.info("localMacAddr=[{}], localIpAddr=[{}]", localMacAddr, localIpAddr);
    }

    /**
     * 获取server mac地址
     * return 6 bytes
     */
    public static byte[] getLocalMacAddr() {
        if (localMacAddr != null) {
            return localMacAddr;
        }

        try {
            InetAddress ia = InetAddress.getLocalHost();
            return NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        } catch (Exception e) {
            logger.error("{}", Throwables.getStackTraceAsString(e));
        }
        return null;
    }

    /**
     * 获取server ip地址
     * @return
     */
    public static String getLocalIpAddr() {
        if (localIpAddr != null) {
            return localIpAddr;
        }

        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            InetAddress ip = null;
            boolean finded = false;// 是否找到外网IP
            while (netInterfaces.hasMoreElements() && !finded) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();
//                  System.out.println(ni.getName() + ";" + ip.getHostAddress()
//                          + ";ip.isSiteLocalAddress()="
//                          + ip.isSiteLocalAddress()
//                          + ";ip.isLoopbackAddress()="
//                          + ip.isLoopbackAddress());
                    if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                        netip = ip.getHostAddress();
                        finded = true;
                        break;
                    } else if (ip.isSiteLocalAddress()
                            && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                        localip = ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }
}
