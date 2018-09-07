package com.epoint.Redis_benchmark;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


@State(Scope.Benchmark)
public class DataSizeUtil {
    public static int RANDNUM=1000;
    //生产对应大小的数据
    public static String BENCHSIZE="";

    public DataSizeUtil(int size) {
        StringBuilder stringBuilder = new StringBuilder("e");
        for (int i=1;i<=size;i++){
            stringBuilder.append("e");
        }
        BENCHSIZE=stringBuilder.toString();
    }
    public static void cleanbenchdata(String cleanbashpath) throws IOException, InterruptedException {
        InputStream in = null;
        Process pro = Runtime.getRuntime().exec(new String[]{"sh", cleanbashpath});
        pro.waitFor();
        in = pro.getInputStream();
        BufferedReader read = new BufferedReader(new InputStreamReader(in));
        String result = read.readLine();
        System.out.println("INFO:"+result);
    }
}



