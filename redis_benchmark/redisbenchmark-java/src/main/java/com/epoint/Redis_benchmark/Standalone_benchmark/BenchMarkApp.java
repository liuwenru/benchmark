package com.epoint.Redis_benchmark.Standalone_benchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchMarkApp {
    //调度benchmark—APPS
    public static String[] bench_data_size_arry={"128","512","1024","5120","10240","20480","30720","1024000"};
    public static int[] bench_clients_threads={1,8,16,32,64,128};
    public static void main(String[] args) throws RunnerException {
        // 对Write类操作进行测试
        for (int i=0;i< bench_clients_threads.length;i++){
            for(int j=0;j<bench_data_size_arry.length;j++){
                Redis_Standalone_Benchmark_WriteOP.VALUESIZE_BENCH=bench_data_size_arry[j];
                Options options = new OptionsBuilder().output("redis_Standalone_"+bench_clients_threads[i]+"__"+bench_data_size_arry[j]+".log").include(Redis_Standalone_Benchmark_WriteOP.class.getSimpleName()).threads(bench_clients_threads[i]).build();
                new Runner(options).run();
            }
        }
    }
}
