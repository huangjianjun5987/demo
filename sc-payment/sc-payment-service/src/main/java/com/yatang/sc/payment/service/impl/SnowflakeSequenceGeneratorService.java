package com.yatang.sc.payment.service.impl;

import com.yatang.sc.payment.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by yuwei on 2017/7/10.
 * 生成支付流水号:<br>
 * 支付订单流水号的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 0000000000 - 000000000000 <br>
 * <li>1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0</li>
 * <li>41位时间截(毫秒级)</li>
 * <li>服务器Node 编号</li>
 * <li>12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号</li>
 * 加起来刚好64位，为一个Long型。<br>
 */
@Service("snowflakeSequenceGeneratorService")
public class SnowflakeSequenceGeneratorService implements SequenceGeneratorService {

    @Value("${server.node.id}")
    private int mServerNodeId;

    /**
     * 开始时间截 (2017-01-01)
     */
    private final long mTwepoch = 1483200000000L;

    /**
     * 服务器Node 编号
     */
    private final long mServerNodeBits = 10L;

    /**
     * 序列在id中占的位数
     */
    private final long mSequenceBits = 12L;

    /**
     * 时间截向左移22位(5+5+12)
     */
    private final long mTimestampLeftShift = mSequenceBits + mServerNodeBits;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private final long mSequenceMask = -1L ^ (-1L << mSequenceBits);

    /**
     * 毫秒内序列(0~4095)
     */
    private long mSequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long mLastTimestamp = -1L;

    @Override
    public synchronized String genSequence() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < mLastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", mLastTimestamp - timestamp));
        }
        //如果是同一时间生成的，则进行毫秒内序列
        if (mLastTimestamp == timestamp) {
            mSequence = (mSequence + 1) & mSequenceMask;
            //毫秒内序列溢出
            if (mSequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(mLastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            mSequence = 0L;
        }

        //上次生成ID的时间截
        mLastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        Long id = ((timestamp - mTwepoch) << mTimestampLeftShift)
                | mServerNodeId << mServerNodeBits
                | mSequence;
        return String.valueOf(id);
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
