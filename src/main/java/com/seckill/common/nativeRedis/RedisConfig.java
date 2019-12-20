//package com.seckill.common.nativeRedis;
//
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
//@Configuration
//@Data
//@EnableCaching
//public class RedisConfig {
//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${spring.redis.port}")
//    private int port;
//    @Value("${spring.redis.timeout}")
//    private int timeout;//秒
//    @Value("${spring.redis.password}")
//    private String password;
//    @Value("${spring.redis.pool.max-idle}")
//    private int poolMaxIdle;
//    @Value("${spring.redis.pool.max-wait}")
//    private int poolMaxWait;//秒
//    @Value("${spring.redis.database}")
//    private int database;//缓存库
//
//
//
//    @Bean
//    public JedisPool RedisConfig() {
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxIdle(poolMaxIdle);
//        poolConfig.setMaxWaitMillis(poolMaxWait * 1000);
//        JedisPool jp = new JedisPool(poolConfig, host, port,
//                timeout*1000, password, database);
//        return jp;
//    }
//}
