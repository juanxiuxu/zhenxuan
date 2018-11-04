package com.zhenxuan.tradeapi.thirdparty;

import com.zhenxuan.tradeapi.common.ZXException;
import com.zhenxuan.tradeapi.common.enums.ResultStatusCode;
import com.zhenxuan.tradeapi.common.enums.SignConfig;
import com.zhenxuan.tradeapi.common.http.HttpJSONHelper;
import com.zhenxuan.tradeapi.common.vo.weixin.WXPayUnifiedOrderReqVo;
import com.zhenxuan.tradeapi.common.vo.weixin.WXPayUnifiedOrderRespVo;
import com.zhenxuan.tradeapi.dao.entity.OrderEntity;
import com.zhenxuan.tradeapi.domain.WXUnifiedOrderInfo;
import com.zhenxuan.tradeapi.utils.CommonUtil;
import com.zhenxuan.tradeapi.utils.DateUtil;
import com.zhenxuan.tradeapi.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 微信统一下单
 */
@Component
public class WXPayUnifiedOrder extends WXPayBaseRpc {

    private static final Logger logger = LoggerFactory.getLogger(WXPayUnifiedOrder.class);

    private final String WX_ORDER_EXPIRE_FORMAT = "yyyyMMddHHmmss";

    @Value("${wx.pay.prepayid.expired.ms}")
    private long wxPrepayIdExpireMS;

    @Value("${wx.pay.unifiedorder}")
    private String unifiedOrderUrl;

    @Value("${wx.pay.unifiedorder.notifyurl}")
    private String unifiedOrderNotifyUrl;

    /**
     * 统一下单
     * @param orderEntity
     * @param wxPayRealFee
     * @return WXUnifiedOrderInfo
     */
    public WXUnifiedOrderInfo execute(OrderEntity orderEntity, String wxOpenId, long wxPayRealFee) {

        WXPayUnifiedOrderReqVo payReqVo = new WXPayUnifiedOrderReqVo();
        payReqVo.setAppId(appId);
        payReqVo.setMerchantId(merchantId);
        payReqVo.setNonceStr(generateNonceStr());
        payReqVo.setBody("爱真选");
        payReqVo.setOpenId(wxOpenId);
        payReqVo.setOrderId(orderEntity.getOid());
        payReqVo.setTotalFee(String.valueOf(wxPayRealFee));
        payReqVo.setSpbillCreateIp(CommonUtil.getLocalIpAddr());

        long curTime = System.currentTimeMillis();
        payReqVo.setOrderStartTime(DateUtil.format(curTime, WX_ORDER_EXPIRE_FORMAT));
        payReqVo.setOrderExpireTime(DateUtil.format(curTime + wxPrepayIdExpireMS, WX_ORDER_EXPIRE_FORMAT));

        payReqVo.setNotifyUrl(unifiedOrderNotifyUrl);
        payReqVo.setTradeType("JSAPI");
        payReqVo.setSignType(SignConfig.WX_PAY_SIGN.signMethod.des);

        String sign = wxPaySign.buildSign(SignConfig.WX_PAY_SIGN, payReqVo);
        if (sign == null) {
            logger.error("can not build sign");
            throw new ZXException(ResultStatusCode.BUILD_SIGN_FAILED);
        }
        payReqVo.setSign(sign);

        String body = mapToXml(JsonUtil.convert(payReqVo, Map.class));
        logger.debug("body={}", body);
        String resp = HttpJSONHelper.instance().post(unifiedOrderUrl, body, "UTF-8");
        logger.info("WXPay unifiedOrder resp is {}", resp);

        WXPayUnifiedOrderRespVo respVo = processResponseXml(resp, WXPayUnifiedOrderRespVo.class);
        if (respVo == null) {
            logger.error("Fail to parse response in xml from weixin");
            throw new ZXException(ResultStatusCode.PARSE_WXPAY_RESP_FAILED);
        }

        if (!wxPaySign.checkSign(SignConfig.WX_PAY_SIGN, respVo, respVo.getSign())) {
            logger.error("checking sign of response from weixin is failed.");
            throw new ZXException(ResultStatusCode.WXPAY_RESP_SIGN_ERROR);
        }

        return new WXUnifiedOrderInfo(respVo.getAppId(), respVo.getPrepayId(), CommonUtil.getLocalIpAddr());
    }
}
