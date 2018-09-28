package net.tea.redis.algorithm;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/**
 * @author teaho2015<at>gmail<dot>com
 * @since 2018-09
 */
public class KetamaHash implements Hash {

    public ThreadLocal<MessageDigest> md5Holder = new ThreadLocal<>();

    @Override
    public long hash(String key) {
        byte[] bytes = null;
        try {
            bytes = key.getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("key parsing error!", e);
        }

        MessageDigest md5 = Optional.ofNullable(md5Holder.get()).orElseGet(() -> {
            try {
                MessageDigest instance = MessageDigest.getInstance("MD5");
                md5Holder.set(instance);
                return instance;
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException("no md5 algorythm found");
            }

        });

        md5.reset();
        md5.update(bytes);
        byte[] bKey = md5.digest();
        long res = ((long) (bKey[3] & 0xFF) << 24)
                | ((long) (bKey[2] & 0xFF) << 16)
                | ((long) (bKey[1] & 0xFF) << 8) | (long) (bKey[0] & 0xFF);
        return res;

    }

    @Override
    public long hash(byte[] key) {
        throw new UnsupportedOperationException();
    }
}
