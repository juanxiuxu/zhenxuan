package com.zhenxuan.tradeapi.utils;

import org.apache.commons.codec.binary.Base64;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 全局唯一id工具类
 */
public class GlobalIdUtil {

    private static final SeqInfo loginUidSeqInfo = new SeqInfo(SeqInfo.IDTYPE_LOGINUID);

    private static class SeqInfo {

        static final byte IDTYPE_LOGINUID = 1;

        SeqInfo(byte idType) {
            this.idType = idType;
        }

        byte idType;

        AtomicLong sequenceNum = new AtomicLong(0);

        long lastTimestamp;

        Lock lock = new ReentrantLock();
    }

    /**
     * 登陆用户id
     * @return
     */
    public static String newLoginUid() {
        return genId1(loginUidSeqInfo);
    }

    /**
     * 订单id
     * @return
     */
    public static String newOrderId() {
        return genId1(loginUidSeqInfo);
    }

    /**
     * id类型码(1字节) + mac addr(6字节) + 时间戳(6字节) + seqNum(4字节) = 共17个字节
     * @param seqInfo
     * @return
     */
    private static String genId1(SeqInfo seqInfo) {
        long curSeqNum = seqInfo.sequenceNum.addAndGet(1);
        long curTimestamp = System.currentTimeMillis() / 1000;

        seqInfo.lock.lock();
        if (seqInfo.lastTimestamp > curTimestamp) {
            curTimestamp = seqInfo.lastTimestamp;
        }

        if (curSeqNum == 0) {
            curTimestamp++;
        }

        seqInfo.lastTimestamp = curTimestamp;
        seqInfo.lock.unlock();

        byte[] docIdBytes = new byte[17];
        for (int i = 0; i < 6; i++) {
            docIdBytes[i] = CommonUtil.getLocalMacAddr()[i];
        }
        for (int i = 0; i < 6; i++) {
            docIdBytes[11 - i] = (byte) (curTimestamp >> (i * 8));
        }
        for (int i = 0; i < 4; i++) {
            docIdBytes[15 - i] = (byte) (curSeqNum >> (i * 8));
        }
        for (int i = 0; i < 1; i++) {
            docIdBytes[16 - i] = seqInfo.idType;
        }

        return Base64.encodeBase64String(docIdBytes);
    }

    /**
     * 请求id
     * @return
     */
    public static String newRequestId() {
        return genId2(hexIp(CommonUtil.getLocalIpAddr()), requestIdLastSeq.incrementAndGet());
    }

    private static final long systemBootTimeStamp = System.currentTimeMillis();
    private static final AtomicLong requestIdLastSeq = new AtomicLong(0);

    /**
     * 特定前缀prefix(不定长) + 系统启动时间戳 + 固定分割符"-"(1字符) + seqNum(最大8字节)
     * @param prefix
     * @param seqNum
     * @return
     */
    private static String genId2(String prefix, long seqNum) {
        return String.format("%s%s-%d", prefix, Long.toString(systemBootTimeStamp, Character.MAX_RADIX), seqNum);
    }

    // 255.255.255.255 -> FFFFFFFF
    private static String hexIp(String ip) {
        StringBuilder sb = new StringBuilder();
        for (String seg : ip.split("\\.")) {
            String h = Integer.toHexString(Integer.parseInt(seg));
            if (h.length() == 1) sb.append("0");
            sb.append(h);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.printf("%s %s", GlobalIdUtil.newRequestId(), GlobalIdUtil.newLoginUid());
        for (int i = 0; i < 10; i++) {
            System.out.println(GlobalIdUtil.newOrderId());
        }
    }
}
