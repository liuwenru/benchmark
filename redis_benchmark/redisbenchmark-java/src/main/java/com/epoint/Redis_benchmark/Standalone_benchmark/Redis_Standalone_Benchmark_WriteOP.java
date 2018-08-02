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

import org.openjdk.jmh.annotations.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.math.BigInteger;
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

    public static JedisPool pool=null;
    public static DataSizeUtil dataSizeUtil=null;
    public static Long rndnumber=0L;

    @Setup
    public static void Bench_init(){
        try {
            System.out.println("#### 测试Redis单节点的写入操作性能...............................................测试机器地址"+HOST+"-----"+"测试端口"+PORT+"测试数据大小(byte):"+DATASIZE);
            //初始化做一些对象的配置工作
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(100);
            pool=new JedisPool(jedisPoolConfig,HOST,PORT);
            dataSizeUtil=new DataSizeUtil(DATASIZE);
            Jedis op=pool.getResource();
            op.flushAll();//每次测试前先清空数据库
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
    public void test_SET() {
        //对Redis的进行测试
        Jedis jedisop=pool.getResource();
        //String uuid=UUID.randomUUID().toString();
        jedisop.set("epoint"+(rndnumber++),DataSizeUtil.BENCHSIZE);
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
        String uuid=UUID.randomUUID().toString();
        jedisop.hset("epoint_HSET",(rndnumber++).toString(),DataSizeUtil.BENCHSIZE);
        jedisop.close();
    }

    /**
     *  SET 结合类操作
     */
    @Benchmark
    public  void test_SADD(){
        //对Redis的进行测试
        Jedis jedisop=pool.getResource();
        String uuid=UUID.randomUUID().toString();
        jedisop.sadd("epoint_SET",(rndnumber++).toString(),DataSizeUtil.BENCHSIZE);
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
        String uuid=UUID.randomUUID().toString();
        jedisop.sadd("epoint_ZSET",(rndnumber++).toString(),DataSizeUtil.BENCHSIZE);
        jedisop.close();
    }
}
