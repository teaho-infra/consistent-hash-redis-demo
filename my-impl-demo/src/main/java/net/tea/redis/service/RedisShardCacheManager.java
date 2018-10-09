package net.tea.redis.service;

import net.tea.redis.component.DHT;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * 目前是以缓存块为单位放置于哈希环中，比如：USER、ORDER等，这样可能相对便于管理，但由于粒度相对粗，目测有些情况（比如当缓存集群相对较大 或 缓存量较集中）会导致某个redis主从压力较大。当然，改写此类，不extends AbstractManager，等等，可简单解决。
 *
 * 同理，如需要较细粒度的自定义过期时间，可重写此类，例如，DHT<RedisTemplate> 可实现较高自定义。
 *
 * Easy DHT manager Implement by using Cache chunk manager, it's suitable for managing, but it's a quite coarse-grained implement, so it will cause some performance issue in some case;
 * and it also not customizable for setting ttl.
 *
 *  you could rewrite this by using DHT<RedisTemplate> etc.
 *
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-09
 *
 */
public class RedisShardCacheManager extends AbstractCacheManager {

    private DHT<RedisCacheManager> dht;

    public RedisShardCacheManager(DHT<RedisCacheManager> dht) {
        this.dht = dht;
    }

    /**
     * load from actual cache manager
     * @param name
     * @return
     */
    @Nullable
    @Override
    protected Cache getMissingCache(String name) {
        RedisCacheManager rm = dht.getVirtualNode(name);
        return rm.getCache(name);
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        List<RedisCache> caches = new LinkedList<>();
        return caches;
    }
}
