/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.epoint.Redis_benchmark.Standalone_benchmark;

import com.epoint.Redis_benchmark.DataSizeUtil;
import org.openjdk.jmh.annotations.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

// 对单节点的Redis进行写入测试

@State(Scope.Benchmark)
@Warmup(iterations = 0)
public class Redis_Standalone_Benchmark_WriteOP {
    @Param("127.0.0.1")
    public static String HOST="";
    @Param("6379")
    public static int PORT=6379;
    @Param("1024")
    public static int DATASIZE=1024;
    public static Random random=new Random(100000);
    public static JedisPool pool=null;
    public static DataSizeUtil dataSizeUtil=null;
    public static Long rndnumber=0L;
    public static HashMap<String,String> hashMap10=new HashMap<String, String>();
    public static HashMap<String,String> hashMap20=new HashMap<String, String>();
    public static HashMap<String,String> hashMap40=new HashMap<String, String>();
    public static HashMap<String,String> hashMap80=new HashMap<String, String>();
    public static HashMap<String,String> hashMap100=new HashMap<String, String>();
    public static HashMap<String,String> hashMap200=new HashMap<String, String>();
    public static HashMap<String,String> hashMap400=new HashMap<String, String>();

    @Setup
    public static void Bench_init(){
        try {
            System.out.println("#### 测试Redis单节点的写入操作性能...............................................测试机器地址"+HOST+"-----"+"测试端口"+PORT+"测试数据大小(byte):"+DATASIZE);
            //初始化做一些对象的配置工作
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(10000);
            jedisPoolConfig.setMaxTotal(500);
            jedisPoolConfig.setMinIdle(0);
            jedisPoolConfig.setMaxWaitMillis(2000);
            pool=new JedisPool(jedisPoolConfig,HOST,PORT);
            dataSizeUtil=new DataSizeUtil(DATASIZE);
            Jedis op=pool.getResource();
            op.flushAll();//每次测试前先清空数据库
            op.close();
            HashMap<String,String> tmpmap=new HashMap<String, String>();
            for(int i =1;i<=400;i++){
                tmpmap.put((rndnumber++).toString(),DataSizeUtil.BENCHSIZE);
                if(i==10){
                    hashMap10=(HashMap<String, String>) tmpmap.clone();
                }
                if (i==20){
                    hashMap20=(HashMap<String, String>) tmpmap.clone();
                }
                if (i==40){
                    hashMap40=(HashMap<String, String>) tmpmap.clone();
                }
                if (i==80){
                    hashMap80=(HashMap<String, String>) tmpmap.clone();
                }
                if (i==100){
                    hashMap100=(HashMap<String, String>) tmpmap.clone();
                }
                if (i==200){
                    hashMap200=(HashMap<String, String>) tmpmap.clone();
                }
                if (i==400){
                    hashMap400=(HashMap<String, String>) tmpmap.clone();
                }
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
    public void test_SET() {
        //对Redis的进行测试
        Jedis jedisop=pool.getResource();
        jedisop.set("epoint"+random.nextInt(DataSizeUtil.RANDNUM),DataSizeUtil.BENCHSIZE);
        jedisop.close();
    }


    /**
     *  Hash 类操作
     *
     */
    @Benchmark
    public void test_HSET(){
        //对Redis的进行测试
        Jedis jedisop=pool.getResource();
        jedisop.hset("epoint_HASH"+random.nextInt(DataSizeUtil.RANDNUM),(rndnumber++).toString(),DataSizeUtil.BENCHSIZE);
        jedisop.close();
    }
    @Benchmark
    public void test_HMSET10(){
        Jedis jedisop=pool.getResource();
        jedisop.hmset("epoint_HASH"+random.nextInt(DataSizeUtil.RANDNUM),hashMap10);
        jedisop.close();

    }
    @Benchmark
    public void test_HMSET20(){
        Jedis jedisop=pool.getResource();
        jedisop.hmset("epoint_HASH"+random.nextInt(DataSizeUtil.RANDNUM),hashMap20);
        jedisop.close();
    }
    @Benchmark
    public void test_HMSET40(){
        Jedis jedisop=pool.getResource();
        jedisop.hmset("epoint_HASH"+random.nextInt(DataSizeUtil.RANDNUM),hashMap40);
        jedisop.close();
    }
    @Benchmark
    public void test_HMSET80(){
        Jedis jedisop=pool.getResource();
        jedisop.hmset("epoint_HASH"+random.nextInt(DataSizeUtil.RANDNUM),hashMap80);
        jedisop.close();
    }
    @Benchmark
    public void test_HMSET100(){
        Jedis jedisop=pool.getResource();
        jedisop.hmset("epoint_HASH"+random.nextInt(DataSizeUtil.RANDNUM),hashMap100);
        jedisop.close();
    }
    @Benchmark
    public void test_HMSET200(){
        Jedis jedisop=pool.getResource();
        jedisop.hmset("epoint_HASH"+random.nextInt(DataSizeUtil.RANDNUM),hashMap200);
        jedisop.close();
    }
    @Benchmark
    public void test_HMSET400(){
        Jedis jedisop=pool.getResource();
        jedisop.hmset("epoint_HASH"+random.nextInt(DataSizeUtil.RANDNUM),hashMap400);
        jedisop.close();
    }

    /**
     *  SET 结合类操作
     */
    @Benchmark
    public  void test_SADD(){
        //对Redis的进行测试
        Jedis jedisop=pool.getResource();
        jedisop.sadd("epoint_SET",String.valueOf(random.nextInt(DataSizeUtil.RANDNUM)),DataSizeUtil.BENCHSIZE);
        jedisop.close();
    }

    /**
     *  SortedSet 排序类集合
     *
     */
    @Benchmark
    public void test_ZADD(){
        //对Redis的进行测试
        Jedis jedisop=pool.getResource();
        jedisop.sadd("epoint_ZSET",String.valueOf(random.nextInt(DataSizeUtil.RANDNUM)),DataSizeUtil.BENCHSIZE);
        jedisop.close();
    }

    /**
     *  LIST 集合操作
     */
    @Benchmark
    public void  test_LPUSH(){
        Jedis jedisop=pool.getResource();
        String uuid=UUID.randomUUID().toString();
        jedisop.lpush("LPUSH",DataSizeUtil.BENCHSIZE);
        jedisop.close();
    }
    @Benchmark
    public void test_RPUSH(){
        Jedis jedisop=pool.getResource();
        jedisop.lpush("RPUSH",DataSizeUtil.BENCHSIZE);
        jedisop.close();
    }
}
