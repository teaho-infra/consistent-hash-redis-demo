package net.tea.redis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-09
 */
@Configuration
public class BeanConfiguration {

    @Bean
    @ConditionalOnProperty("app.cache.redis.hostName")
    @ConfigurationProperties(prefix="app.cache.redis")
    public RedisStandaloneConfiguration redisStandaloneConfiguration() {
        return new RedisStandaloneConfiguration();
    }

    @Bean
    @ConditionalOnBean(RedisStandaloneConfiguration.class)
    public CacheManager jedisCacheManager() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration());
        RedisCacheManager redisCacheManager = RedisCacheManager.create(connectionFactory);
        return redisCacheManager;
    }
}
