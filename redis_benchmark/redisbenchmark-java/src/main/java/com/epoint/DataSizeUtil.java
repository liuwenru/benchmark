package com.epoint;

public class DataSizeUtil {
    //生产对应大小的数据
    public static String VALUESIZE_128="";
    public static String VALUESIZE_512="";
    public static String VALUESIZE_1k="";
    public static String VALUESIZE_5k="";
    public static String VALUESIZE_10k="";
    public static String VALUESIZE_20k="";
    public static String VALUESIZE_30k="";
    public static String VALUESIZE_100k="";
    public DataSizeUtil() {
        String s="a";
        for (int i=1;i<=100*1024;i++){
            s=s+"a";
            if(i==128){
                VALUESIZE_128=s;
            }else if(i==512) {
                VALUESIZE_512=s;
            }else if(i==1024){
                VALUESIZE_1k=s;
            }else if(i==5120){
                VALUESIZE_5k=s;
            }else if(i==10240){
                VALUESIZE_10k=s;
            }else if(i==20480){
                VALUESIZE_20k=s;
            }else if(i==30720){
                VALUESIZE_30k=s;
            }else if(i==102400){
                VALUESIZE_100k=s;
            }
        }
    }
}
