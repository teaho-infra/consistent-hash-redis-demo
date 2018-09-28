package net.tea.jmh;

import net.tea.redis.algorithm.Fnv1_32Hash;
import net.tea.redis.algorithm.Hash;
import net.tea.redis.algorithm.KetamaHash;
import net.tea.redis.algorithm.MurmurHash;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class RedisAlgorithmBenchmark {

    static final String s = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()-_+=~`[]{}:;'|<>?,./";



    @Benchmark
    @Warmup(iterations=10, time=100, timeUnit = TimeUnit.MILLISECONDS, batchSize = 10)
    @Measurement(iterations=1000, time=100, timeUnit = TimeUnit.MILLISECONDS, batchSize = 10)
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime})
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void redisMurmurHashing() {
        MurmurHash m = new MurmurHash();
        Random rand = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i= 0; i<20; i++) {
            sb.append(s.charAt(rand.nextInt(s.length() - 1)));
        }
        m.hash(sb.toString());
    }


    @Benchmark
    @Warmup(iterations=10, time=100, timeUnit = TimeUnit.MILLISECONDS, batchSize = 10)
    @Measurement(iterations=1000, time=100, timeUnit = TimeUnit.MILLISECONDS, batchSize = 10)
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime})
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void redisKetama() {
        Hash m = new KetamaHash();
        Random rand = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i= 0; i<20; i++) {
            sb.append(s.charAt(rand.nextInt(s.length() - 1)));
        }
        m.hash(sb.toString());

    }

    @Benchmark
    @Warmup(iterations=10, time=100, timeUnit = TimeUnit.MILLISECONDS, batchSize = 10)
    @Measurement(iterations=1000, time=100, timeUnit = TimeUnit.MILLISECONDS, batchSize = 10)
    @BenchmarkMode({Mode.Throughput, Mode.AverageTime, Mode.SampleTime, Mode.SingleShotTime})
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void redisFnv1() {
        Hash m = new Fnv1_32Hash();
        Random rand = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i= 0; i<20; i++) {
            sb.append(s.charAt(rand.nextInt(s.length() - 1)));
        }
        m.hash(sb.toString());

    }



    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(RedisAlgorithmBenchmark.class.getSimpleName())
                .forks(2)
                .build();

        new Runner(opt).run();
    }

}


/*


# Run complete. Total time: 00:35:14

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

Benchmark                                                                Mode      Cnt       Score    Error   Units
RedisAlgorithmBenchmark.redisFnv1                                       thrpt     2000       0.296 ±  0.001  ops/us
RedisAlgorithmBenchmark.redisKetama                                     thrpt     2000       0.081 ±  0.002  ops/us
RedisAlgorithmBenchmark.redisMurmurHashing                              thrpt     2000       0.238 ±  0.002  ops/us
RedisAlgorithmBenchmark.redisFnv1                                        avgt     2000       3.493 ±  0.020   us/op
RedisAlgorithmBenchmark.redisKetama                                      avgt     2000     279.561 ± 48.298   us/op
RedisAlgorithmBenchmark.redisMurmurHashing                               avgt     2000       4.252 ±  0.090   us/op
RedisAlgorithmBenchmark.redisFnv1                                      sample  6921481       3.814 ±  0.077   us/op
RedisAlgorithmBenchmark.redisFnv1:redisFnv1·p0.00                      sample                2.828            us/op
RedisAlgorithmBenchmark.redisFnv1:redisFnv1·p0.50                      sample                3.184            us/op
RedisAlgorithmBenchmark.redisFnv1:redisFnv1·p0.90                      sample                3.536            us/op
RedisAlgorithmBenchmark.redisFnv1:redisFnv1·p0.95                      sample                3.540            us/op
RedisAlgorithmBenchmark.redisFnv1:redisFnv1·p0.99                      sample               13.088            us/op
RedisAlgorithmBenchmark.redisFnv1:redisFnv1·p0.999                     sample               49.152            us/op
RedisAlgorithmBenchmark.redisFnv1:redisFnv1·p0.9999                    sample              427.444            us/op
RedisAlgorithmBenchmark.redisFnv1:redisFnv1·p1.00                      sample            77856.768            us/op
RedisAlgorithmBenchmark.redisKetama                                    sample  4584904      22.213 ±  2.618   us/op
RedisAlgorithmBenchmark.redisKetama:redisKetama·p0.00                  sample                7.072            us/op
RedisAlgorithmBenchmark.redisKetama:redisKetama·p0.50                  sample                9.904            us/op
RedisAlgorithmBenchmark.redisKetama:redisKetama·p0.90                  sample               13.088            us/op
RedisAlgorithmBenchmark.redisKetama:redisKetama·p0.95                  sample               17.664            us/op
RedisAlgorithmBenchmark.redisKetama:redisKetama·p0.99                  sample               26.880            us/op
RedisAlgorithmBenchmark.redisKetama:redisKetama·p0.999                 sample              112.512            us/op
RedisAlgorithmBenchmark.redisKetama:redisKetama·p0.9999                sample             1473.555            us/op
RedisAlgorithmBenchmark.redisKetama:redisKetama·p1.00                  sample           501219.328            us/op
RedisAlgorithmBenchmark.redisMurmurHashing                             sample  6052992       4.387 ±  0.031   us/op
RedisAlgorithmBenchmark.redisMurmurHashing:redisMurmurHashing·p0.00    sample                3.184            us/op
RedisAlgorithmBenchmark.redisMurmurHashing:redisMurmurHashing·p0.50    sample                3.892            us/op
RedisAlgorithmBenchmark.redisMurmurHashing:redisMurmurHashing·p0.90    sample                4.240            us/op
RedisAlgorithmBenchmark.redisMurmurHashing:redisMurmurHashing·p0.95    sample                4.240            us/op
RedisAlgorithmBenchmark.redisMurmurHashing:redisMurmurHashing·p0.99    sample               14.144            us/op
RedisAlgorithmBenchmark.redisMurmurHashing:redisMurmurHashing·p0.999   sample               53.056            us/op
RedisAlgorithmBenchmark.redisMurmurHashing:redisMurmurHashing·p0.9999  sample              532.278            us/op
RedisAlgorithmBenchmark.redisMurmurHashing:redisMurmurHashing·p1.00    sample            30081.024            us/op
RedisAlgorithmBenchmark.redisFnv1                                          ss     2000       8.133 ±  0.844   us/op
RedisAlgorithmBenchmark.redisKetama                                        ss     2000      56.295 ±  5.579   us/op
RedisAlgorithmBenchmark.redisMurmurHashing                                 ss     2000      15.379 ±  1.162   us/op

Process finished with exit code 0



 */
