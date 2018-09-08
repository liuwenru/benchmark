# -*- coding: UTF-8 -*-


import os
import shutil
import matplotlib.pyplot as plot
import matplotlib


# 解决画图中文标题的问题
shutil.copyfile('msyh.ttf',os.path.dirname(matplotlib.matplotlib_fname())+"/fonts/ttf/msyh.ttf")
matplotlib.rcParams['font.sans-serif'] = 'Microsoft YaHei'



if __name__ == "__main__":
    x_area=[1,8,16,32,64,128]
    y1_area=[100,200,23,45,33,56]
    y2_area=[200,100,123,435,324,23]
    #for i in  range(len(x_area)):
    #    print(i)
    #    plot.bar(i,y1_area[i])
    #    plot.bar(i+0.5,y2_area[i])
    x1=(0.7,0.8,0.9,1.0,1.1,1.2)
    x2=(1.7,1.8,1.9,2.0,2.1,2.2)
    x3=(2.7,2.8,2.9,2.0,2.1,2.2)
    x4=(3.7,3.8,3.9,3.0,3.1,3.2)
    x5=(4.7,4.8,4.9,4.0,4.1,4.2)
    x6=(5.7,5.8,5.9,5.0,5.1,5.2)
    print(x1)
    print(x2)
    plot.bar(x1,y1_area,0.1)
    plot.bar(x2,y2_area,0.1)
    plot.bar(x3,y2_area,0.1)
    plot.bar(x4,y2_area,0.1)
    plot.bar(x5,y2_area,0.1)
    plot.bar(x6,y2_area,0.1)
    plot.show()


