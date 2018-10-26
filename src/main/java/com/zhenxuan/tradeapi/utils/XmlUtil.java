/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package com.zhenxuan.tradeapi.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.base.CaseFormat;

import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 *
 * @author tianyu07.
 * @date 16/6/20 21:36.
 */
public class XmlUtil {

    private static XmlMapper XML_MAPPER;

    private static CaseFormat fromFormat = CaseFormat.LOWER_UNDERSCORE;
    private static CaseFormat toFormat = CaseFormat.LOWER_CAMEL;

    static {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XML_MAPPER = new XmlMapper(module);
    }

    public static String toString(Object object) {
        try {
            return XML_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toBytes(Object object) {
        try {
            return XML_MAPPER.writeValueAsBytes(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(byte[] result, Class<T> clazz) {
        if (result == null || result.length == 0) {
            return null;
        }

        try {
            return XML_MAPPER.readValue(result, clazz);
        } catch (Exception e) {
            throw new RuntimeException(String.format("convert json:[%s] to object:[%s] due to error",
                    new String(result),
                    clazz), e);
        }
    }

    public static <T> T toObject(InputStream result, Class<T> clazz) {
        try {
            return XML_MAPPER.readValue(result, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(byte[] result, TypeReference<T> reference) {
        try {
            return XML_MAPPER.readValue(result, reference);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(InputStream result, TypeReference<T> reference) {
        try {
            return XML_MAPPER.readValue(result, reference);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    /**
//     * 将xml文本转换为Json
//     *
//     * @param xmlString
//     *
//     * @return
//     */
//    public static String xml2Json(String xmlString) {
//        String jsonStr = null;
//
//        StringReader reader = new StringReader(xmlString);
//        InputSource source = new InputSource(reader);
//        SAXBuilder sb = new SAXBuilder();
//        try {
//            Document document = sb.build(source);
//            Element rootElement = document.getRootElement();
//            Map<String, Object> resMap = iterateElement(rootElement);
//
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.putAll(resMap);
//            jsonStr = jsonObject.toJSONString();
//
//        } catch (JDOMException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return jsonStr;
//    }
//
//    private static String xml2JSON(Element element) {
//        JSONObject jsonObject = new JSONObject();
//        Map<String, Object> obMap = iterateElement(element);
//        jsonObject.put(element.getName(), obMap);
//        return jsonObject.toString();
//    }
//
//    private static Map<String, Object> iterateElement(Element element) {
//        List<Element> elist = element.getChildren();
//        Map<String, Object> subMap = Maps.newHashMap();
//        for (Element ele : elist) {
//            if (ele.getChildren().size() > 0) {
//                subMap.put(ele.getName(), iterateElement(ele));
//            } else {
//                subMap.put(ele.getName(), ele.getTextTrim());
//            }
//        }
//        return subMap;
//    }
}
