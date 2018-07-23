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

package com.epoint.Standalone_benchmark;

import com.epoint.DataSizeUtil;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.UUID;
@State(Scope.Benchmark)
@Warmup(iterations = 0)
public class Redis_Standalone_Benchmark {
    public static JedisPool pool=null;
    public static DataSizeUtil dataSizeUtil=null;

    @Setup
    public static void Bench_init(){
        //初始化做一些对象的配置工作

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);
        pool=new JedisPool(jedisPoolConfig,"192.168.188.76",6380);
        dataSizeUtil=new DataSizeUtil();
    }

    @Benchmark
    @Threads(4)
    public void test_SET() {
        //对Redis的进行测试
        Jedis jedisop=pool.getResource();
        String uuid=UUID.randomUUID().toString();
        jedisop.set("epoint"+uuid,DataSizeUtil.VALUESIZE_100k);
        jedisop.close();
    }
    @Benchmark
    @Threads(4)
    public void test_GET() {
        //对Redis的进行测试
        Jedis jedisop=pool.getResource();
        String uuid=UUID.randomUUID().toString();
        jedisop.set("epoint"+uuid,DataSizeUtil.VALUESIZE_100k);
        jedisop.close();
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder().include(Redis_Standalone_Benchmark.class.getSimpleName()).build();
        new Runner(options).run();
    }

}
