package com.zhenxuan.tradeapi.thirdparty;

import com.zhenxuan.tradeapi.common.enums.SignConfig;
import com.zhenxuan.tradeapi.common.http.HttpJSONHelper;
import com.zhenxuan.tradeapi.entity.OrderEntity;
import com.zhenxuan.tradeapi.utils.JsonUtil;
import com.zhenxuan.tradeapi.vo.weixin.WXPayUnifiedOrderReqVo;
import com.zhenxuan.tradeapi.vo.weixin.WXPayUnifiedOrderRespVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.UUID;

/**
 * 微信统一下单
 */
public class WXPayUnifiedOrder extends WXPayBaseRpc {

    private static final Logger logger = LoggerFactory.getLogger(WXPayUnifiedOrder.class);


    @Value("${wx.pay.unifiedorder}")
    private String unifiedOrderUrl;

    public WXPayUnifiedOrderRespVo execute(OrderEntity orderEntity) {

        WXPayUnifiedOrderReqVo payReqVo = new WXPayUnifiedOrderReqVo();
        payReqVo.setAppId(appId);
        payReqVo.setMerchantId(merchantId);
        payReqVo.setNonceStr(UUID.randomUUID().toString());
//        payReqVo.setBody("爱真选");
//        payReqVo.set
        String sign = wxPaySign.buildSign(SignConfig.WX_PAY_SIGN, payReqVo);
        payReqVo.setSign(sign);

        String body = mapToXml(JsonUtil.convert(payReqVo, Map.class));
        String resp = HttpJSONHelper.instance().post(unifiedOrderUrl, body, "UTF-8");
        logger.info("WXPay unifiedOrder resp is {}", resp);

        WXPayUnifiedOrderRespVo respVo = processResponseXml(resp, WXPayUnifiedOrderRespVo.class);
        if (respVo == null) {
            return null;
        }

        wxPaySign.checkSign(SignConfig.WX_PAY_SIGN, respVo.getSign());
    }
}
