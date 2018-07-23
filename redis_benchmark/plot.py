# -*- coding: UTF-8 -*-


import os
import shutil
import matplotlib.pyplot as plot
import matplotlib


# 解决画图中文标题的问题
shutil.copyfile('msyh.ttf',os.path.dirname(matplotlib.matplotlib_fname())+"/fonts/ttf/msyh.ttf")
matplotlib.rcParams['font.sans-serif'] = 'Microsoft YaHei'



if __name__ == "__main__":
    x_area=[1,2,3,4,5]
    y1_area=[100,200,23,45,34]
    y2_area=[200,100,123,435,324]
    plot.plot(x_area, y1_area)
    plot.plot(x_area, y2_area)
    plot.xticks(x_area,['1','2','3','4','5'])
    plot.yticks(y2_area)
    plot.ylabel('中文的标题')
    plot.xlabel('数据量大小')
    plot.show()


