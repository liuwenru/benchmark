# -*- coding: UTF-8 -*-
import os
import shutil
import subprocess
import matplotlib.pyplot as plot
import matplotlib
import time
'''
基本测试步骤先使用redis-benchmark工具发送测试命令，然后再读取测试结果进行绘图

./redis-benchmark -h 192.168.188.76 -p 6380  -n 1000 -c 100 --csv


'''


redis_host="192.168.188.76"
redis_port=6380
redis_clients_num=1
redis_requests_num=9000000





# 解决画图中文标题的问题
shutil.copyfile('msyh.ttf',os.path.dirname(matplotlib.matplotlib_fname())+"/fonts/ttf/msyh.ttf")
matplotlib.rcParams['font.sans-serif'] = 'Microsoft YaHei'

bench_size_list=[128,512,1024,5120,10240,20480,30720]
bench_clients_list=[1,8,16,32,64,128]


def bench_standalone():
    print("开始测试单节点模式redis.........")
    for i in bench_size_list:
        print("测试value_size:{0}bytes".format(i))
        for j in bench_clients_list:
            benchmark_cmd="./redis-benchmark -h {redis_host} -p {redis_port}  -n 900000 -c {benchclients} -d {datasize} --csv > resout/standalone-{datasize}-{benchclients}".format(redis_host=redis_host,redis_port=redis_port,datasize=i,benchclients=j)
            print(benchmark_cmd)
            subprocess.check_call(benchmark_cmd, shell=True)
            time.sleep(5) 





def draw_picture():
    print("对测试结果进行绘图........")
    x_area=[1,2,3,4,5]
    y1_area=[100,200,23,45,34]
    y2_area=[200,100,123,435,324]
    plot.xticks(x_area)
    plot.yticks(y2_area)
    plot.ylabel('中文的标题')
    plot.xlabel('数据量大小')
    plot.show()
    exit()


if __name__ == "__main__":
    bench_standalone()
