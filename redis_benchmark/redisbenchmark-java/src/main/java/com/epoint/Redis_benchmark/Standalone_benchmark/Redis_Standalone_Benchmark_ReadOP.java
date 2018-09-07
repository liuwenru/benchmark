package com.epoint.Redis_benchmark.Standalone_benchmark;


import com.epoint.Redis_benchmark.DataSizeUtil;
import org.openjdk.jmh.annotations.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Random;

/**
 *  对单节点的Redis进行读取测试
 */
@State(Scope.Benchmark)
@Warmup(iterations = 0)
public class Redis_Standalone_Benchmark_ReadOP {
    @Param("127.0.0.1")
    public static String HOST="";

    @Param("6379")
    public static int PORT=6379;

    @Param("1024")
    public static int DATASIZE=1024;

    public static DataSizeUtil dataSizeUtil=null;
    public static JedisPool pool=null;
    public static Random random=new Random(10000);
    // 清楚上一把压测数据的脚本地址
    @Param("/root/cleansingle.sh")
    public static  String cleanbenchdatashell="/root/cleansingle.sh";
    @Setup(Level.Trial)
    public static void Bench_init(){
        try {
            System.out.println("#### 测试Redis单节点的读取操作性能...............................................测试机器地址 "+HOST+" -----"+"测试端口 "+PORT+" 测试数据大小(byte): "+DATASIZE);
            //初始化做一些对象的配置工作
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(10000);
            jedisPoolConfig.setMaxTotal(500);
            jedisPoolConfig.setMinIdle(0);
            pool=new JedisPool(jedisPoolConfig,HOST,PORT,120000);
            dataSizeUtil=new DataSizeUtil(DATASIZE);
            Jedis op=pool.getResource();
            // 在调用每一个方法前都清理一下数据库，避免内存问题导致CPU挂掉了
            System.out.println("#### 正在清理上一把测试的数据内容.........");
            DataSizeUtil.cleanbenchdata(cleanbenchdatashell);
            //构造压力测试数据
            for (int i=0;i<=100000;i++){
                op.set("epoint"+Integer.toString(i),DataSizeUtil.BENCHSIZE);
                op.hset("epoint_HASH",Integer.toString(i),DataSizeUtil.BENCHSIZE);
                op.sadd("epoint_SET",DataSizeUtil.BENCHSIZE+Integer.toString(i));
                //op.zadd("epoint_ZSET",(j++),DataSizeUtil.BENCHSIZE+Integer.toString(i));
            }
            op.close();
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
        Jedis jedisop=pool.getResource();
        jedisop.get("epoint"+random.nextInt(DataSizeUtil.RANDNUM));
        jedisop.close();
    }
    /**
     *  Hash 类操作
     *
     */
    @Benchmark
    public void test_HGET(){
        //对Redis的进行测试
        Jedis jedisop=pool.getResource();
        jedisop.hget("epoint_HASH",Integer.toString(random.nextInt(100000)));
        jedisop.close();
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
        Jedis jedisop=pool.getResource();
        jedisop.srandmember("epoint_SET",10);
        jedisop.close();
    }
    @Benchmark
    public void test_ZRANGEBYSCORE20(){
        //对Redis的进行测试
        Jedis jedisop=pool.getResource();
        jedisop.srandmember("epoint_SET",20);
        jedisop.close();
    }
    @Benchmark
    public void test_ZRANGEBYSCORE40(){
        //对Redis的进行测试
        Jedis jedisop=pool.getResource();
        jedisop.srandmember("epoint_SET",40);
        jedisop.close();
    }
    @Benchmark
    public void test_ZRANGEBYSCORE80(){
        //对Redis的进行测试
        Jedis jedisop=pool.getResource();
        jedisop.srandmember("epoint_SET",80);
        jedisop.close();
    }
    @Benchmark
    public void test_ZRANGEBYSCORE100(){
        //对Redis的进行测试
        Jedis jedisop=pool.getResource();
        jedisop.srandmember("epoint_SET",100);
        jedisop.close();
    }
    @Benchmark
    public void test_ZRANGEBYSCORE200(){
        //对Redis的进行测试
        Jedis jedisop=pool.getResource();
        jedisop.srandmember("epoint_SET",200);
        jedisop.close();
    }
    @Benchmark
    public void test_ZRANGEBYSCORE400(){
        //对Redis的进行测试
        Jedis jedisop=pool.getResource();
        jedisop.srandmember("epoint_SET",400);
        jedisop.close();
    }
}
