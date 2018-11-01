package com.zhenxuan.tradeapi.thirdparty;

import com.zhenxuan.tradeapi.common.ZXException;
import com.zhenxuan.tradeapi.common.enums.ResultStatusCode;
import com.zhenxuan.tradeapi.common.enums.SignConfig;
import com.zhenxuan.tradeapi.common.vo.weixin.WXPayBaseVo;
import com.zhenxuan.tradeapi.common.vo.weixin.WXPayDirectNotifyReqVo;
import com.zhenxuan.tradeapi.domain.WXPayResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 微信统一下单回调支付结果
 */
@Component
public class WXPayUnifiedOrderNotify extends WXPayBaseRpc {

    private static final Logger logger = LoggerFactory.getLogger(WXPayUnifiedOrderNotify.class);

    public WXPayResultInfo execute(String notifyXml) {
        WXPayDirectNotifyReqVo notifyReqVo = processResponseXml(notifyXml, WXPayDirectNotifyReqVo.class);
        if (notifyReqVo == null) {
            logger.error("Fail to parse notify response in xml from weixin");
            throw new ZXException(ResultStatusCode.PARSE_WXPAY_RESP_FAILED);
        }
        if (!wxPaySign.checkSign(SignConfig.WX_PAY_SIGN, notifyReqVo, notifyReqVo.getSign())) {
            logger.error("checking sign of notify response from weixin is failed.");
            throw new ZXException(ResultStatusCode.WXPAY_RESP_SIGN_ERROR);
        }

        if (!appId.equals(notifyReqVo.getAppId())) {
            logger.error("unknown appId:[{}] from notify", notifyReqVo.getAppId());
            throw new ZXException(ResultStatusCode.UNKNOWN_WXPAY_NOTIFY);
        }
        if (!merchantId.equals(notifyReqVo.getMerchantId())) {
            logger.error("unknown merchantId:[{}] from notify", notifyReqVo.getMerchantId());
            throw new ZXException(ResultStatusCode.UNKNOWN_WXPAY_NOTIFY);
        }

        WXPayResultInfo payResultInfo;
        if (WXPayBaseVo.WXPayRespCode.SUCCESS.code.equals(notifyReqVo.getResultCode())) {
            payResultInfo = WXPayResultInfo.create(notifyReqVo);

        } else if (WXPayBaseVo.WXPayRespCode.FAIL.code.equals(notifyReqVo.getResultCode())) {
            logger.error("pay order result is fail");
            payResultInfo = WXPayResultInfo.create(notifyReqVo);

        } else {
            logger.error("unknown result code:[{}] from wxPay. errCode:{}, errDes:{}", notifyReqVo.getResultCode(),
                    notifyReqVo.getErrCode(), notifyReqVo.getErrCodeDesc());
            throw new ZXException(ResultStatusCode.UNKNOWN_WXPAY_NOTIFY);
        }
        logger.info("Pay Order notify result info is {}", payResultInfo);

        return payResultInfo;
    }
}
