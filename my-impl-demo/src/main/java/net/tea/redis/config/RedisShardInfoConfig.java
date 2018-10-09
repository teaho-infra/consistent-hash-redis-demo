package net.tea.redis.config;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-09
 */
public class RedisShardInfoConfig {

    List<RedisStandaloneConfiguration> list = new ArrayList<>();

    public List<RedisStandaloneConfiguration> getList() {
        return list;
    }

    public void setList(List<RedisStandaloneConfiguration> list) {
        this.list = list;
    }
}
