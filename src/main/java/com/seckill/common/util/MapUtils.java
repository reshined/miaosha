package com.seckill.common.util;

public class MapUtils {
	private static double EARTH_RADIUS = 6371.393;  
    private static double rad(double d)  
    {  
       return d * Math.PI / 180.0;  
    }  
  
    /** 
     * 计算两个经纬度之间的距离 
     * @param lat1 
     * @param lng1 
     * @param lat2 
     * @param lng2 
     * @return 
     */  
    public static double GetDistance(double lat1, double lng1, double lat2, double lng2)  
    {  
       double radLat1 = rad(lat1);  
       double radLat2 = rad(lat2);  
       double a = radLat1 - radLat2;      
       double b = rad(lng1) - rad(lng2);  
       double s = 2 * Math.asin(Math.sqrt(Math.abs(Math.pow(Math.sin(a/2),2) +   
        Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2))));  
       s = s * EARTH_RADIUS;  
       s = Math.round(s * 1000);  
       return s;  
    }  
      
      
      
    public static void main(String[] args) { 
    	double distance = MapUtils.GetDistance(30.6369510000,104.0905390000,30.5579370000,104.0742860000);
        System.out.println(distance/1000+"Km");  
    }  
}
