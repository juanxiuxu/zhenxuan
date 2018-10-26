package com.zhenxuan.tradeapi.common.enums;

/**
 * 用户角色
 */
public enum Member {

    GENERIC(0), // 普通用户
    VIP(1), // 会员
    MERCHANT(2); // 店主

    Member(int code) {
        this.code = code;
    }

    public final int code;

    public static Member fromCode(int code) {
        for (Member member : Member.values()) {
            if (member.code == code) {
                return member;
            }
        }

        throw new IllegalArgumentException(String.format("Not supported Member code:[%s]", code));
    }
}
