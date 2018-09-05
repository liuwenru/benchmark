package com.epoint.Redis_benchmark;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;



@State(Scope.Benchmark)
public class DataSizeUtil {
    public static int RANDNUM=10000;
    //生产对应大小的数据
    public static String BENCHSIZE="";

    public DataSizeUtil(int size) {
        StringBuilder stringBuilder = new StringBuilder("e");
        for (int i=1;i<=size;i++){
            stringBuilder.append("e");
        }
        BENCHSIZE=stringBuilder.toString();
    }
}
