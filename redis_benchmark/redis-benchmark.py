# -*- coding: UTF-8 -*-
import os
import shutil
import subprocess
import time
'''
基本测试步骤先使用redis-benchmark工具发送测试命令，然后再读取测试结果进行绘图

./redis-benchmark -h 192.168.188.76 -p 6380  -n 1000 -c 100 --csv


'''

redis_host="172.31.68.83"
redis_port=6379

rediscluster_host="172.31.68.83"
rediscluster_port=7001


#bench_size_list=[128,512,1024,5120,10240,20480,30720,1024000]
#bench_clients_list=[1,8,16,32,64,128]


bench_size_list=[1024,5120,10240,128,512,20480]
bench_clients_list=[1,8,16,32,64,128]




def bench_standalone():
    print("开始测试单节点模式redis.........")
    for i in bench_size_list:
        print("测试value_size:{0}bytes".format(i))
        for j in bench_clients_list:
            # benchmark_cmd="./redis-benchmark -h {redis_host} -p {redis_port}  -n 100 -c {benchclients} -d {datasize} --csv > resout/standalone-{datasize}-{benchclients}".format(redis_host=redis_host,redis_port=redis_port,datasize=i,benchclients=j)
            benchmark_read_cmd="java -jar benchmarks.jar com.epoint.Redis_benchmark.Standalone_benchmark.Redis_Standalone_Benchmark_ReadOP.* -p PORT={redis_port} -p HOST={redis_host} -p DATASIZE={datasize}  -t {benchclients}  -f 1  -i 1 > resout/standalone-{datasize}-{benchclients}_ReadOP".format(redis_host=redis_host,redis_port=redis_port,datasize=i,benchclients=j)
            print(benchmark_read_cmd)
            subprocess.call(benchmark_read_cmd, shell=True)
            time.sleep(5)
            clean_RedisSingle()
            benchmark_write_cmd="java -jar benchmarks.jar com.epoint.Redis_benchmark.Standalone_benchmark.Redis_Standalone_Benchmark_WriteOP.* -p PORT={redis_port} -p HOST={redis_host} -p DATASIZE={datasize}  -t {benchclients}  -f 1  -i 1 > resout/standalone-{datasize}-{benchclients}_WriteOP".format(redis_host=redis_host,redis_port=redis_port,datasize=i,benchclients=j)
            print(benchmark_write_cmd)
            subprocess.call(benchmark_write_cmd, shell=True)
            time.sleep(5) 
            clean_RedisSingle()

def bench_cluster():
    print("开始测试集群模式redis............")
    for i in bench_size_list:
        print("测试value_size:{0}bytes".format(i))
        for j in bench_clients_list:
            benchmark_read_cmd="java -jar benchmarks.jar com.epoint.Redis_benchmark.Cluster_benchmark.Redis_Cluster_Benchmark_ReadOP.* -p PORT={redis_port} -p HOST={redis_host} -p DATASIZE={datasize}  -t {benchclients}  -f 1  -i 1 > resout/cluster-{datasize}-{benchclients}_ReadOP".format(redis_host=rediscluster_host,redis_port=rediscluster_port,datasize=i,benchclients=j)
            print(benchmark_read_cmd)
            subprocess.call(benchmark_read_cmd, shell=True)
            time.sleep(5) 
            clean_RedisCluster()
            benchmark_write_cmd="java -jar benchmarks.jar com.epoint.Redis_benchmark.Cluster_benchmark.Redis_Cluster_Benchmark_WriteOP.* -p PORT={redis_port} -p HOST={redis_host} -p DATASIZE={datasize}  -t {benchclients}  -f 1  -i 1 > resout/cluster-{datasize}-{benchclients}_WriteOP".format(redis_host=rediscluster_host,redis_port=rediscluster_port,datasize=i,benchclients=j)
            print(benchmark_write_cmd)
            subprocess.call(benchmark_write_cmd, shell=True)
            time.sleep(5)
            clean_RedisCluster()

def clean_RedisCluster():
    print("清理集群测试数据中...........")
    for i in ["172.31.68.83","172.31.68.84","172.31.68.85"]:
        print("clean host {host}:7001&7002".format(host=i))
        print("redis-cli -p 7001 -h "+ i +" -c FLUSHALL")
        subprocess.call("redis-cli -p 7001 -h "+ i +" -c FLUSHALL",shell=True)
        subprocess.call("redis-cli -p 7002 -h "+ i +" -c FLUSHALL",shell=True)



def clean_RedisSingle():
    print("清理集群测试数据中...........")
    singlehost ="172.31.68.83"
    subprocess.call("redis-cli -p 6379 -h "+ i +"FLUSHALL",shell=True)



if __name__ == "__main__":
    #draw_standalone_picture()
    bench_standalone()
    bench_cluster()


