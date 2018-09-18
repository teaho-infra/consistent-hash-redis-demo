package net.tea.jedis.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-09
 */
public class RedisShardInfoConfig {

    List<RedisShardInfo> list = new ArrayList<>();


    public List<RedisShardInfo> getList() {
        return list;
    }

    public void setList(List<RedisShardInfo> list) {
        this.list = list;
    }
}
