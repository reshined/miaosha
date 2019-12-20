package com.seckill.common.util;

public class PageUtil {


    public static int getStartIndex(int pageNum,int pageSize){
        return (pageNum-1)*pageSize;
    }
}
