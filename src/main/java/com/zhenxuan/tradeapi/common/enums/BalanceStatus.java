package com.zhenxuan.tradeapi.common.enums;

/**
 * 订单打款状态
 */
public enum BalanceStatus {

    UNKNOWN(-1, "unknown balance status"),
    CASHBACK_PUSHED(1, "cashback pushed");  //打完分润余额了

    BalanceStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public final int code;
    public final String desc;

    public static BalanceStatus fromCode(int code) {
        for (BalanceStatus status : BalanceStatus.values()) {
            if (status.code == code) {
                return status;
            }
        }

        throw new IllegalArgumentException(String.format("Not supported BalanceStatus code:[%s]", code));
    }
}
