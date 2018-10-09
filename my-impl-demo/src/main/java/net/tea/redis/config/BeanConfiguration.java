package net.tea.redis.config;

import net.tea.redis.component.DHT;
import net.tea.redis.service.RedisShardCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-09
 */
@Configuration
public class BeanConfiguration {

    @Bean
    //    @Profile()
    @ConfigurationProperties(prefix="app.cache.redis")
    public RedisShardInfoConfig redisShardInfoConfig() {
        return new RedisShardInfoConfig();
    }


    @Bean
    @ConditionalOnBean(RedisShardInfoConfig.class)
    public DHT<RedisCacheManager> dht(RedisShardInfoConfig redisShardInfoConfig) {

        Map<String, RedisCacheConfiguration> map = new HashMap<>();
        map.put("USER" ,  RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(5)));

        List<RedisCacheManager> l = redisShardInfoConfig.getList().stream().map(info -> {
            JedisConnectionFactory connectionFactory = new JedisConnectionFactory(info);
            RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory).withInitialCacheConfigurations(map).build();
            redisCacheManager.afterPropertiesSet();
            return redisCacheManager;
        }).collect(Collectors.toList());
        return new DHT<>(l);

    }

    @Bean
    @ConditionalOnBean(RedisShardInfoConfig.class)
    public CacheManager redisShardCacheManager(DHT<RedisCacheManager> dht) {
        return new RedisShardCacheManager(dht);
    }




}
