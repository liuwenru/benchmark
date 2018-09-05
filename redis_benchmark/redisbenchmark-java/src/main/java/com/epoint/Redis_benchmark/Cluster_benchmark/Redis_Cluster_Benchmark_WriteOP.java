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

package com.epoint.Redis_benchmark.Cluster_benchmark;

import com.epoint.Redis_benchmark.DataSizeUtil;
import org.openjdk.jmh.annotations.*;
import redis.clients.jedis.*;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.*;

// Cluster集群的Redis进行写入测试

@State(Scope.Benchmark)
@Warmup(iterations = 0)
public class Redis_Cluster_Benchmark_WriteOP {
    @Param("192.168.188.77")
    public static String HOST="";
    @Param("6379")
    public static int PORT=6379;
    @Param("1024")
    public static int DATASIZE=1024;

    public static Set<HostAndPort> jedisClusterNodes=new HashSet<HostAndPort>();;
    public static JedisCluster  jcstatic=null;

    public static Random random=new Random(10000);

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
            System.out.println("#### 测试Redis集群的写入操作性能...............................................测试机器地址"+HOST+"-----"+"测试数据大小(byte):"+DATASIZE);
            dataSizeUtil=new DataSizeUtil(DATASIZE);
            //初始化做一些对象的配置工作
            jedisClusterNodes.add(new HostAndPort(HOST, PORT));
            // Jedis连接池配置
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(500);
            jedisPoolConfig.setTestOnBorrow(false);
            jcstatic= new JedisCluster(jedisClusterNodes,jedisPoolConfig);
            //jcstatic.flushAll();
            HashMap<String,String> tmpmap=new HashMap<>();
            for(int i =1;i<=400;i++){
                tmpmap.put((rndnumber++).toString(),DataSizeUtil.BENCHSIZE);
                if(i==10){
                    hashMap10= (HashMap<String, String>) tmpmap.clone();
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
    public void test_SET() throws IOException {
        //对Redis的进行测试
        jcstatic.set("epoint"+random.nextInt(DataSizeUtil.RANDNUM),DataSizeUtil.BENCHSIZE);
    }
    /**
     *  Hash 类操作
     *
     */
    @Benchmark
    public void test_HSET() throws IOException {
        //对Redis的进行测试
        jcstatic.hset("epoint_HASH"+random.nextInt(DataSizeUtil.RANDNUM),(rndnumber++).toString(),DataSizeUtil.BENCHSIZE);
    }
    @Benchmark
    public void test_HMSET10() throws IOException {
        jcstatic.hmset("epoint_HASH"+random.nextInt(DataSizeUtil.RANDNUM),hashMap10);
    }
    @Benchmark
    public void test_HMSET20() throws IOException {
        jcstatic.hmset("epoint_HASH"+random.nextInt(DataSizeUtil.RANDNUM),hashMap20);
    }
    @Benchmark
    public void test_HMSET40() throws IOException {
        jcstatic.hmset("epoint_HASH"+random.nextInt(DataSizeUtil.RANDNUM),hashMap40);
    }
    @Benchmark
    public void test_HMSET80() throws IOException {
        jcstatic.hmset("epoint_HASH"+random.nextInt(DataSizeUtil.RANDNUM),hashMap80);
    }
    @Benchmark
    public void test_HMSET100() throws IOException {
        jcstatic.hmset("epoint_HASH"+random.nextInt(DataSizeUtil.RANDNUM),hashMap100);
    }
    @Benchmark
    public void test_HMSET200() throws IOException {
        jcstatic.hmset("epoint_HASH"+random.nextInt(DataSizeUtil.RANDNUM),hashMap200);

    }
    @Benchmark
    public void test_HMSET400() throws IOException {
        jcstatic.hmset("epoint_HASH"+random.nextInt(DataSizeUtil.RANDNUM),hashMap400);
    }

    /**
     *  SET 结合类操作
     */
    @Benchmark
    public  void test_SADD() throws IOException {
        //对Redis的进行测试
        jcstatic.sadd("epoint_SET",String.valueOf(random.nextInt(DataSizeUtil.RANDNUM)),DataSizeUtil.BENCHSIZE);
    }

    /**
     *  SortedSet 排序类集合
     *
     */
    @Benchmark
    public void test_ZADD() throws IOException {
        //对Redis的进行测试
        jcstatic.sadd("epoint_ZSET",String.valueOf(random.nextInt(DataSizeUtil.RANDNUM)),DataSizeUtil.BENCHSIZE);
    }

    /**
     *  LIST 集合操作
     */
    @Benchmark
    public void  test_LPUSH() throws IOException {
        jcstatic.lpush("LPUSH",DataSizeUtil.BENCHSIZE);
    }
    @Benchmark
    public void test_RPUSH() throws IOException {
        jcstatic.lpush("RPUSH",DataSizeUtil.BENCHSIZE);
    }
}
