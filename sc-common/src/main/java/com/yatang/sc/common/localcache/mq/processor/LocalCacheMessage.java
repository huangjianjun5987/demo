package com.yatang.sc.common.localcache.mq.processor;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.Serializable;

public class LocalCacheMessage implements Serializable {
    private String command;
    private String group;
    private String body;
    private String randomStr;

    public LocalCacheMessage() {
        randomStr = RandomStringUtils.randomAlphanumeric(20);
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String pCommand) {
        command = pCommand;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String pGroup) {
        group = pGroup;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String pBody) {
        body = pBody;
    }

    public String getRandomStr() {
        return randomStr;
    }

    public void setRandomStr(String pRandomStr) {
        randomStr = pRandomStr;
    }

    @Override
    public String toString() {
        return "LocalCacheMessage{" +
                "command='" + command + '\'' +
                ", group='" + group + '\'' +
                ", body='" + body + '\'' +
                ", randomStr='" + randomStr + '\'' +
                '}';
    }
}
