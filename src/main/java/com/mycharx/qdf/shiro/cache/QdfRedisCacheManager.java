package com.mycharx.qdf.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class QdfRedisCacheManager implements CacheManager {

    private static final Logger logger = LoggerFactory
            .getLogger(QdfRedisCacheManager.class);

    // fast lookup by name map
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    @Autowired
    private QdfRedisManager qdfRedisManager;

    /**
     * The Redis key prefix for caches
     */
    @Value("${myshiro.shiro.redis-cache-key}")
    private String keyPrefix = "shiro:cache:";

    /**
     * Returns the Redis session keys
     * prefix.
     * @return The prefix
     */
    public String getKeyPrefix() {
        return keyPrefix;
    }

    /**
     * Sets the Redis sessions key
     * prefix.
     * @param keyPrefix The prefix
     */
    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        logger.debug("获取名称为: " + name + " 的RedisCache实例");

        Cache c = caches.get(name);

        if (c == null) {

            // initialize the Redis manager instance
//            redisManager.init();

            // create a new cache instance
            c = new QdfRedisCache<K, V>(qdfRedisManager, keyPrefix);

            // add it to the cache collection
            caches.put(name, c);
        }
        return c;
    }

    public QdfRedisManager getQdfRedisManager() {
        return qdfRedisManager;
    }

    public void setQdfRedisManager(QdfRedisManager qdfRedisManager) {
        this.qdfRedisManager = qdfRedisManager;
    }
}