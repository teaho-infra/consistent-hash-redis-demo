package net.tea.redis.algorithm;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 *
 * KETAMA_HASH refer to :  https://github.com/RJ/ketama/blob/master/java_ketama/SockIOPool.java
 * see jedis redis.clients.util
 *
 *
 *
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-09
 */
public interface Hash {

    public static final String CHARSET = "utf-8";


    public long hash(String key);

    public long hash(byte[] key);
}
