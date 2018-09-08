# -*- coding: UTF-8 -*-


import os
import shutil
import matplotlib.pyplot as plot
import matplotlib


# 解决画图中文标题的问题
shutil.copyfile('msyh.ttf',os.path.dirname(matplotlib.matplotlib_fname())+"/fonts/ttf/msyh.ttf")
matplotlib.rcParams['font.sans-serif'] = 'Microsoft YaHei'



if __name__ == "__main__":
    x_area=[1,8,16,32,64]
    benchsize=[128,512,1024,5120,10240,20480]
    y1_area=[100,200,23,34,23,1]
    y2_area=[90,20,13,64,53,2]
    y3_area=[70,10,23,14,13,1]
    y4_area=[73,11,223,14,13,1]
    y5_area=[72,12,23,34,63,3]
    y6_area=[71,13,33,54,73,7]
    #for i in  range(len(x_area)):
    #    print(i)
    #    plot.bar(i,y1_area[i])
    #    plot.bar(i+0.5,y2_area[i])
    # 对应的数据坐标轴是   128   512  1024 5120 10240 20480

    #x1=(0.7,0.8,0.9,1.0,1.1,1.2)
    #x2=(1.7,1.8,1.9,2.0,2.1,2.2)
    #x3=(2.7,2.8,2.9,3.0,3.1,3.2)
        
    #x1=(0.7,0.75,0.8,0.85,0.9,0.95,1.0,1.05,1.1,1.15,1.2,1.25)
    #x2=(1.7,1.75,1.8,1.85,1.9,1.95,2.0,2.05,2.1,2.15,2.2,2.25)
    #x3=(2.7,2.75,2.8,2.85,2.9,2.95,3.0,3.05,3.1,3.15,3.2,3.25)
    x1=(0.7,1.0,1.7,2.0,2.7,3.0) # 128 
    x2=(0.75,1.05,1.75,2.05,2.75,3.05) # 512
    x3=(0.8,1.1,1.8,2.1,2.8,3.1) # 1024
    x4=(0.85,1.15,1.85,2.15,2.85,3.15) # 5120
    x5=(0.9,1.2,1.9,2.2,2.9,3.2) # 10240
    x6=(0.95,1.25,1.95,2.25,2.95,3.25) # 20480





    plot.bar(x1,y1_area,0.05)
    plot.bar(x2,y2_area,0.05)
    plot.bar(x3,y3_area,0.05)
    plot.bar(x4,y4_area,0.05)
    plot.bar(x5,y5_area,0.05)
    plot.bar(x6,y6_area,0.05)
    plot.show()


