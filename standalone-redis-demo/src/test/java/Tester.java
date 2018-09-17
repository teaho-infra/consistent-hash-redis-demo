import net.tea.redis.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-09
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class Tester {
    private static final Logger LOG = LoggerFactory.getLogger(Tester.class);
    @Autowired CacheManager cacheManager;

    @Test
    public void testSetKey() throws Exception {
        Cache cache = cacheManager.getCache("USER");
        String s = cache.get("user1", () -> "user1name");
        LOG.info(s);
    }
}
