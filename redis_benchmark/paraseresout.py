# -*- coding: UTF-8 -*-

# 解析压力测试的结果为TXT格式便于分析
import re

#bench_size_list=[1024,5120,10240,128,512,20480]
#bench_clients_list=[1,8,16,32,64,128]


datamap={}

bench_size_list=[1024,10240,5120]
bench_clients_list=[1,8,16,32,64,128]

readopgroup=["GET","HGET","ZRANGEBYSCORE10","ZRANGEBYSCORE20","ZRANGEBYSCORE40","ZRANGEBYSCORE80","ZRANGEBYSCORE100","ZRANGEBYSCORE200","ZRANGEBYSCORE400"]
writeopgroup=["HSET","SET","LPUSH","RPUSH","SADD","ZADD","HMSET10","HMSET20","HMSET40","HMSET80","HMSET100","HMSET200","HMSET400"]
arry=[[0 for i in range(len(bench_size_list))] for i in range(len(bench_clients_list))]
print("arry is:"+str(arry))
for j in readopgroup+writeopgroup:
        datamap['cluster_'+j]=arry*len(bench_clients_list)
    

READMATCH=r'Redis_benchmark\.Cluster_benchmark\.Redis_Cluster_Benchmark_ReadOP\.test_([a-zA-Z0-9]{0,20})(.+)\s([0-9]{1,}[.][0-9]*)'
WRITEMATCH=r'Redis_benchmark\.Cluster_benchmark\.Redis_Cluster_Benchmark_WriteOP\.test_([a-zA-Z0-9]{0,20})(.+)\s([0-9]{1,}[.][0-9]*)'


def parase_cluster_resoutfile():
    print("解析处理结果文件")
    for clients in bench_clients_list:
        print(str(clients)+"-----------")
        for benchsize in bench_size_list:
            print(str(benchsize)+"-----------")
            # 处理 Read 测试样本数据
            try:
                with open('./resout/cluster-'+str(benchsize)+'-'+str(clients)+'_ReadOP','r') as f:
                    for context in f.readlines():
                        if context.startswith('Redis_benchmark'):
                            matchs=re.match(READMATCH, context)
                            opname=str(matchs.group(1))
                            opvalue=str(matchs.group(3))
                            mapkeyname='cluster_'+str(opname)
                            rowindex=bench_clients_list.index(clients)
                            cloumindex=bench_size_list.index(benchsize)
                            #print("martchs ops:"+str(matchs.group(1))+" values :"+str(matchs.group(3)))
                            print("mapkey:{mapkey}  rowindex:{rowindex}  cloum:{cloumindex} opvalue:{opvalue}".format(mapkey=mapkeyname,rowindex=rowindex,cloumindex=cloumindex,opvalue=opvalue))
                            datamap[mapkeyname][rowindex][cloumindex]=opvalue
            except FileNotFoundError:
                print("未获取到数据文件")
                        
            # 处理 Write 测试数据样本
            try:
                with open('./resout/cluster-'+str(benchsize)+'-'+str(clients)+'_WriteOP','r') as f:
                    for context in f.readlines():
                        if context.startswith('Redis_benchmark'):
                            matchs=re.match(WRITEMATCH, context)
                            opname=str(matchs.group(1))
                            opvalue=str(matchs.group(3))
                            mapkeyname='cluster_'+str(matchs.group(1))
                            rowindex=bench_clients_list.index(clients)
                            cloumindex=bench_size_list.index(benchsize)
                            #print("martchs ops:"+str(matchs.group(1))+" values :"+str(matchs.group(3)))
                            print("mapkey:{mapkey}  rowindex:{rowindex}  cloum:{cloumindex} opvalue:{opvalue}".format(mapkey=mapkeyname,rowindex=rowindex,cloumindex=cloumindex,opvalue=opvalue))
            except FileNotFoundError:
                print("未获取到数据文件")





def displaymap(showmap):
    print("查看测试数据")
    for k in showmap:
        print("-----------"+k+"-----------")
        for i in range(len(bench_clients_list)):
            disstrline="clients-"+str(bench_clients_list[i])+" :"
            datastrline=""
            for j in range(len(bench_size_list)):
                datastrline=datastrline+" "+str(showmap[k][i][j])
            print(disstrline+datastrline)



if __name__ == "__main__":
    print("开始解析测试数据......")
    parase_cluster_resoutfile()
    displaymap(datamap)
