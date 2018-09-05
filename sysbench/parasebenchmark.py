# -*- coding: UTF-8 -*-
import re
import os
import shutil
import subprocess
import matplotlib.pyplot as plot
import matplotlib
import time
import random



# 实现读取sysbench测试的结果集划出磁盘读写速率的曲线图





# 解决画图中文标题的问题
shutil.copyfile('msyh.ttf',os.path.dirname(matplotlib.matplotlib_fname())+"/fonts/ttf/msyh.ttf")
matplotlib.rcParams['font.sans-serif'] = 'Microsoft YaHei'







