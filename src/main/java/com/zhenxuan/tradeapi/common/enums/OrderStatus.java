package com.zhenxuan.tradeapi.common.enums;

/**
 * 订单状态
 */
public enum OrderStatus {

    UNKNOWN(-1, "unknown order status"),
    PAY_WAITING(1, "pay waitting"), // 等待支付
    PAY_SUCCESS(2, "pay success"), // 支付成功
    PAY_FAILED(3, "pay failed"), // 支付失败
    REFUND_SUCCESS(4, "refund success"), // 退款成功
    PAY_CLOSED(5, "pay closed");  // 支付交易关闭

    OrderStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public final int code;
    public final String desc;

    public static OrderStatus fromCode(int code) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.code == code) {
                return status;
            }
        }

        throw new IllegalArgumentException(String.format("Not supported OrderStatus code:[%s]", code));
    }
}
