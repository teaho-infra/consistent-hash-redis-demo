package net.tea.jedis;

import net.tea.jedis.config.RedisShardInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.List;

/**
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-09
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Tester {
    private static final Logger LOG = LoggerFactory.getLogger(Tester.class);

    @Autowired
    ShardedJedisPool shardedJedisPool;

    @Test
    public void testSetKey() throws Exception {

        ShardedJedis jedis = shardedJedisPool.getResource();
        jedis.set("jedisUser1", "jedisUser1name");
        jedis.close();
    }

}
