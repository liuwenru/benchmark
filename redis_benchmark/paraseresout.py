# -*- coding: UTF-8 -*-

# 解析压力测试的结果为TXT格式便于分析


bench_size_list=[1024,5120,10240,128,512,20480]
bench_clients_list=[1,8,16,32,64,128]


readopgroup=["GET","HGET"]
writeopgroup=["HSET","SET","LPUSH","RPUSH","SADD","ZADD"]







def paraseresoutfile():
    print("解析处理结果文件")
    for op in readopgroup:
        for clients in bench_clients_list:
            for benchsize in bench_size_list:
                with open('resout/cluster_'+benchsize+'-'+clients+'_ReadOP') as f:
                    



















