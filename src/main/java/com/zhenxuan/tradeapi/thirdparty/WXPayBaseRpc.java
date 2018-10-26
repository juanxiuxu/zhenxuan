package com.zhenxuan.tradeapi.thirdparty;

import com.google.common.base.Throwables;
import com.zhenxuan.tradeapi.sign.Sign;
import com.zhenxuan.tradeapi.utils.JsonUtil;
import com.zhenxuan.tradeapi.vo.weixin.WXPayBaseVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class WXPayBaseRpc {

    private static final Logger logger = LoggerFactory.getLogger(WXPayBaseRpc.class);

    @Value("${wx.zhenxuan.appid}")
    protected String appId;

    @Value("${wx.pay.merchantid}")
    protected String merchantId;

    @Autowired
    @Qualifier("wxPaySign")
    protected Sign wxPaySign;

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     */
    protected Map<String, String> xmlToMap(String strXML) {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilder documentBuilder = newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            stream.close();
            return data;

        } catch (Exception ex) {
            logger.error("Invalid XML, can not convert to map. error: {}. XML content: {}", Throwables.getStackTraceAsString(ex), strXML);
            return null;
        }
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     */
    protected String mapToXml(Map<String, String> data) {
        try {
            org.w3c.dom.Document document = newDocument();
            org.w3c.dom.Element root = document.createElement("xml");
            document.appendChild(root);
            for (String key : data.keySet()) {
                String value = data.get(key);
                if (value == null) {
                    value = "";
                }
                value = value.trim();
                org.w3c.dom.Element filed = document.createElement(key);
                filed.appendChild(document.createTextNode(value));
                root.appendChild(filed);
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");

            writer.close();
            return output;

        } catch (Exception ex) {
            logger.error("maoToXml error. {}", Throwables.getStackTraceAsString(ex));
            return null;
        }
    }

    protected <T extends WXPayBaseVo> T processResponseXml(String xmlStr, Class<T> clazz) {
        if (StringUtils.isEmpty(xmlStr)) {
            logger.error("resp is empty");
            return null;
        }

        Map<String, String> respData = xmlToMap(xmlStr);
        T respObj = JsonUtil.convert(respData, clazz);

        String returnCode = respObj.getReturnCode();
        if (WXPayBaseVo.WXPayRespCode.SUCCESS.equals(returnCode)) {
            return respObj;

        } else if (WXPayBaseVo.WXPayRespCode.FAIL.equals(returnCode)) {
            logger.error("WXPay response's return fail msg is {}", respObj.getReturnMsg());
            return null;

        } else {
            logger.error("invalid return_code:{}.", returnCode);
            throw new RuntimeException(String.format("No `return_code` in XML: %s", xmlStr));
        }
    }

    private DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        documentBuilderFactory.setXIncludeAware(false);
        documentBuilderFactory.setExpandEntityReferences(false);

        return documentBuilderFactory.newDocumentBuilder();
    }

    private Document newDocument() throws ParserConfigurationException {
        return newDocumentBuilder().newDocument();
    }
}
