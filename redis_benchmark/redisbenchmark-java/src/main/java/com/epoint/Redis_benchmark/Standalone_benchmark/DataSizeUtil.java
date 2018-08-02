package com.epoint.Redis_benchmark.Standalone_benchmark;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import redis.clients.jedis.Jedis;
import java.util.Random;



@State(Scope.Benchmark)
public class DataSizeUtil {
    public static int RANDNUM=100000;
    //生产对应大小的数据
    public static String BENCHSIZE="";

    public DataSizeUtil(int size) {
        StringBuilder stringBuilder = new StringBuilder("e");
        for (int i=1;i<=size;i++){
            stringBuilder.append("e");
        }
        BENCHSIZE=stringBuilder.toString();
    }
    public static void PrepareData(String host,int port){
        Jedis op=new Jedis(host,port);
        Random random=new Random(RANDNUM);
        op.flushAll();//每次测试前先清空数据库
        for(int i=0;i<=RANDNUM;i++){
            String rnd=Integer.toString(random.nextInt(RANDNUM));
            op.set("epoint"+rnd,BENCHSIZE);
            op.hset("epoint_HGET",rnd, BENCHSIZE);
            op.sadd("epoint_SET",BENCHSIZE+rnd);
        }
        op.close();
    }
}
