package com.yatang.sc.common.lock;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.ReplicatedServersConfig;

public class RedisDistributedLockFactory {

    private RedissonClient redissionClient;

    private String lockRedisServer;

    public void initFactory(){
        Config config = new Config();
        ReplicatedServersConfig  serversConfig = config.useReplicatedServers();
        String[] lockRedisServers = lockRedisServer.split(";");
        serversConfig.addNodeAddress(lockRedisServers);
        redissionClient = Redisson.create(config);
    }

    public RLock getLock(String locPath) {
        return redissionClient.getLock(locPath);
    }

    public String getLockRedisServer() {
        return lockRedisServer;
    }

    public void setLockRedisServer(String pLockRedisServer) {
        lockRedisServer = pLockRedisServer;
    }
}
