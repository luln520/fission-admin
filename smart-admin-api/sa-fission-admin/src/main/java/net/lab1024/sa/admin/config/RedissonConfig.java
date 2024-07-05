package net.lab1024.sa.admin.config;

import net.lab1024.sa.admin.common.RedissonLockUtils;
import net.lab1024.sa.admin.module.system.TwAdmin.service.impl.RedissonDistributeLocker;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * redisson bean管理
 */
@Configuration
public class RedissonConfig {

    @Autowired
    private Environment env;

    /**
     * Redisson客户端注册
     * 单机模式
     */
    @Bean(destroyMethod = "shutdown")
    public RedissonClient createRedissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://206.238.199.169:6379")
//                .setAddress("redis://154.82.113.95:6379")
                .setPassword("coin_123456"); // 如果设置了密码
//                .setPassword("yBx7EtPjK2jhCRDX"); // 如果设置了密码

        return Redisson.create(config);

    }

    /**
     * 分布式锁实例化并交给工具类
     * @param redissonClient
     */
    @Bean
    public RedissonDistributeLocker redissonLocker(RedissonClient redissonClient) {
        RedissonDistributeLocker locker = new RedissonDistributeLocker(redissonClient);
        RedissonLockUtils.setLocker(locker);
        return locker;
    }

}
