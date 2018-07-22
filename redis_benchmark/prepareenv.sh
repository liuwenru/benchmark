#!/bin/bash - 
#===============================================================================
#
#          FILE: prepareenv.sh
# 
#         USAGE: ./prepareenv.sh 
# 
#   DESCRIPTION: 准备编译测试环境脚本
# 
#       OPTIONS: ---
#  REQUIREMENTS: ---
#          BUGS: ---
#         NOTES: ---
#        AUTHOR: YOUR NAME (), 
#  ORGANIZATION: 
#       CREATED: 07/22/2018 13:06
#      REVISION:  ---
#===============================================================================

set -o nounset                              # Treat unset variables as an error


curl -O  https://www.python.org/ftp/python/3.6.1/Python-3.6.1.tar.xz


tar xvf Python-3.6.1.tar.xz  && cd Python-3.6.1
./configure && make && make install
cd ..
python3 -m venv py3
source py3/bin/activate
pip install matplotlib






