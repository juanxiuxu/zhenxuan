package com.zhenxuan.tradeapi.thirdparty;

import com.zhenxuan.tradeapi.common.enums.SignConfig;
import com.zhenxuan.tradeapi.entity.OrderEntity;
import com.zhenxuan.tradeapi.sign.Sign;
import com.zhenxuan.tradeapi.vo.weixin.WXPayUnifiedOrderReqVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

/**
 * 微信统一下单
 */
public class WXPayUnifiedOrder {

    private static final Logger logger = LoggerFactory.getLogger(WXPayUnifiedOrder.class);

    @Value("${wx.zhenxuan.appid}")
    private String appId;

    @Value("${wx.pay.merchantid}")
    private String merchantId;

    @Autowired
    @Qualifier("wxPaySign")
    private Sign wxPaySign;

    public void execute(OrderEntity orderEntity) {

        WXPayUnifiedOrderReqVo payReqVo = new WXPayUnifiedOrderReqVo();
//        payReqVo.setAppId(appId);
//        payReqVo.setMerchantId(merchantId);
//        payReqVo.setNonceStr(UUID.randomUUID().toString());
//        payReqVo.setBody("爱真选");
//        payReqVo.set
//
        String sign = wxPaySign.buildSign(SignConfig.WX_PAY_SIGN, payReqVo);
//        payReqVo.setSign(sign);

       // HttpJSONHelper.instance().get()

    }
}
