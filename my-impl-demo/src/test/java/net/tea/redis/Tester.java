package net.tea.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-09
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Tester {
    private static final Logger LOG = LoggerFactory.getLogger(Tester.class);

    @Autowired
    CacheManager cacheManager;

    @Test
    public void testSetKey() throws Exception {

        LOG.info(setKey());
        TimeUnit.SECONDS.sleep(10);
        LOG.info(setKey());

    }

    public String setKey() {
        Cache c = cacheManager.getCache("USER");
        return c.get("user1", () -> {
            LOG.info("calling !!!!!!!!");

            return "tea";
        });
    }

}
