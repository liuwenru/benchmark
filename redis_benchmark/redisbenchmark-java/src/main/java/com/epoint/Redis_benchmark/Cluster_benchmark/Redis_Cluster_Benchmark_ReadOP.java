package com.epoint.Redis_benchmark.Cluster_benchmark;


import com.epoint.Redis_benchmark.DataSizeUtil;
import org.openjdk.jmh.annotations.*;
import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *  对单节点的Redis进行读取测试
 */
@State(Scope.Benchmark)
@Warmup(iterations = 0)
public class Redis_Cluster_Benchmark_ReadOP {
    @Param("192.168.188.77")
    public static String HOST="";

    @Param("6379")
    public static int PORT=6379;

    @Param("1024")
    public static int DATASIZE=1024;

    public static DataSizeUtil dataSizeUtil=null;

    public static Set<HostAndPort> jedisClusterNodes=new HashSet<HostAndPort>();;
    public static JedisCluster jcstatic=null;

    public static Random random=new Random(100000);

    @Setup(Level.Trial)
    public static void Bench_init(){
        try {
            System.out.println("#### 测试Redis单节点的读取操作性能...............................................测试机器地址 "+HOST+" -----"+"测试端口 "+PORT+" 测试数据大小(byte): "+DATASIZE);
            //初始化做一些对象的配置工作
            jedisClusterNodes.add(new HostAndPort(HOST, PORT));
            // Jedis连接池配置
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(500);
            jedisPoolConfig.setTestOnBorrow(false);
            jcstatic= new JedisCluster(jedisClusterNodes,jedisPoolConfig);
            //jcstatic.flushAll();
            dataSizeUtil=new DataSizeUtil(DATASIZE);
            //构造压力测试数据
            for (int i=0;i<=100000;i++){
                jcstatic.set("epoint"+Integer.toString(i),DataSizeUtil.BENCHSIZE);
                jcstatic.hset("epoint_HASH",Integer.toString(i),DataSizeUtil.BENCHSIZE);
                jcstatic.sadd("epoint_SET",DataSizeUtil.BENCHSIZE+Integer.toString(i));
                //op.zadd("epoint_ZSET",(j++),DataSizeUtil.BENCHSIZE+Integer.toString(i));
            }
        }catch (Exception ex){
        }finally {
        }
    }


    /**
     * String 类操作
     *
     */
    @Benchmark
    public void test_GET() {
        //对Redis的进行测试
        jcstatic.get("epoint"+random.nextInt(DataSizeUtil.RANDNUM));
    }
    /**
     *  Hash 类操作
     *
     */
    @Benchmark
    public void test_HGET(){
        //对Redis的进行测试
        jcstatic.hget("epoint_HASH",Integer.toString(random.nextInt(100000)));
    }
    /**
     *  SET 集合类操作
     *      暂时先不测试存在报错，而且官方也没有测试
     */
//    @Benchmark
//    public  void test_SMEMBERS(){
//        //对Redis的进行测试
//        Jedis jedisop=pool.getResource();
//        jedisop.smembers("epoint_SET");
//        jedisop.close();
//    }


    /**
     *  SortedSet 排序类集合
     */
    @Benchmark
    public void test_ZRANGEBYSCORE10(){
        //对Redis的进行测试
        jcstatic.srandmember("epoint_SET",10);
    }
    @Benchmark
    public void test_ZRANGEBYSCORE20(){
        //对Redis的进行测试
        jcstatic.srandmember("epoint_SET",20);
    }
    @Benchmark
    public void test_ZRANGEBYSCORE40(){
        //对Redis的进行测试
        jcstatic.srandmember("epoint_SET",40);
    }
    @Benchmark
    public void test_ZRANGEBYSCORE80(){
        //对Redis的进行测试
        jcstatic.srandmember("epoint_SET",80);
    }
    @Benchmark
    public void test_ZRANGEBYSCORE100(){
        //对Redis的进行测试
        jcstatic.srandmember("epoint_SET",100);
    }
    @Benchmark
    public void test_ZRANGEBYSCORE200(){
        //对Redis的进行测试
        jcstatic.srandmember("epoint_SET",200);
    }
    @Benchmark
    public void test_ZRANGEBYSCORE400(){
        //对Redis的进行测试
        jcstatic.srandmember("epoint_SET",400);
    }
}
