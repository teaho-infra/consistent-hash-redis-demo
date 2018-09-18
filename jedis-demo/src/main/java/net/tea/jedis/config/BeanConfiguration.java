package net.tea.jedis.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;
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
//    @ConditionalOnBean(name = {"redisShardInfoConfig"})
    @ConditionalOnBean(RedisShardInfoConfig.class)
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        return jedisPoolConfig;
    }

    @Bean
    @ConditionalOnBean(RedisShardInfoConfig.class)
    public ShardedJedisPool sharedJedisPool() {
        RedisShardInfoConfig redisShardInfoConfig = redisShardInfoConfig();

        List<RedisShardInfo> l = redisShardInfoConfig.getList();
        List<JedisShardInfo> jedisShardInfoList = l.stream().map(info -> {
            JedisShardInfo jedisShardInfo = new JedisShardInfo(info.getHost(), info.getPort(), info.getName());
            if (StringUtils.hasText(info.getPassword())) {
                jedisShardInfo.setPassword(info.getPassword());
            }
            return jedisShardInfo;
        }).collect(Collectors.toList());

        return new ShardedJedisPool(jedisPoolConfig(), jedisShardInfoList);


    }

}
