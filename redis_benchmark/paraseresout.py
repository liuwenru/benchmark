# -*- coding: UTF-8 -*-

# 解析压力测试的结果为TXT格式便于分析
import re
import os
import shutil
import subprocess
import matplotlib.pyplot as plot
import matplotlib
import time
import random

# 解决画图中文标题的问题
shutil.copyfile('msyh.ttf',os.path.dirname(matplotlib.matplotlib_fname())+"/fonts/ttf/msyh.ttf")
matplotlib.rcParams['font.sans-serif'] = 'Microsoft YaHei'

#bench_size_list=[1024,5120,10240,128,512,20480]
#bench_clients_list=[1,8,16,32,64,128]

# 存放测试结果的map结构 key的形式为  single_SET    cluster_SET
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



# 解析数据文件至map中  
#   resoutmode参数为cluster或者standalone
def parase_resoutfile(resoutmode):
    print("解析处理结果文件")
    for clients in bench_clients_list:
        print(str(clients)+"-----------")
        for benchsize in bench_size_list:
            print(str(benchsize)+"-----------")
            # 处理 Read 测试样本数据
            try:
                with open('./resout/'+resoutmode+'-'+str(benchsize)+'-'+str(clients)+'_ReadOP','r') as f:
                    for context in f.readlines():
                        if context.startswith('Redis_benchmark'):
                            matchs=re.match(READMATCH, context)
                            opname=str(matchs.group(1))
                            opvalue=str(matchs.group(3))
                            mapkeyname=resoutmode+'_'+str(opname)
                            rowindex=bench_clients_list.index(clients)
                            cloumindex=bench_size_list.index(benchsize)
                            #print("martchs ops:"+str(matchs.group(1))+" values :"+str(matchs.group(3)))
                            print("mapkey:{mapkey}  rowindex:{rowindex}  cloum:{cloumindex} opvalue:{opvalue}".format(mapkey=mapkeyname,rowindex=rowindex,cloumindex=cloumindex,opvalue=opvalue))
                            datamap[mapkeyname][rowindex][cloumindex]=opvalue
            except FileNotFoundError:
                print("未获取到数据文件")
                        
            # 处理 Write 测试数据样本
            try:
                with open('./resout/'+resoutmode+'-'+str(benchsize)+'-'+str(clients)+'_WriteOP','r') as f:
                    for context in f.readlines():
                        if context.startswith('Redis_benchmark'):
                            matchs=re.match(WRITEMATCH, context)
                            opname=str(matchs.group(1))
                            opvalue=str(matchs.group(3))
                            mapkeyname=resoutmode+'_'+str(matchs.group(1))
                            rowindex=bench_clients_list.index(clients)
                            cloumindex=bench_size_list.index(benchsize)
                            #print("martchs ops:"+str(matchs.group(1))+" values :"+str(matchs.group(3)))
                            print("mapkey:{mapkey}  rowindex:{rowindex}  cloum:{cloumindex} opvalue:{opvalue}".format(mapkey=mapkeyname,rowindex=rowindex,cloumindex=cloumindex,opvalue=opvalue))
            except FileNotFoundError:
                print("未获取到数据文件")



# 根绝解析出来的map数据结构画图
def draw__picture():
    print("对测试结果进行绘图........")
    rowplotgroup=readopgroup+writeopgroup
    for i in rowplotgroup:
        #plot.subplot(len(rowplotgroup),1,rowplotgroup.index(i)+1)
        plot.title(i)
        plot.ylabel('QPM')
        plot.xlabel('线程数')
        plot.xticks(bench_clients_list,bench_clients_list)
        plot.yticks([1,4,5,87,4,6],[1,4,5,87,4,6])
        plot.plot(bench_clients_list, [1,4,5,87,4,6])
        plot.show()
    



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
    parase_resoutfile("cluster")
    #parase_resoutfile("standalone")
    displaymap(datamap)
    draw__picture()