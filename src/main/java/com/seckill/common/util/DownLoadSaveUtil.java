package com.seckill.common.util;

import java.io.File;
import java.io.FileInputStream;

/**
 *  将文件下载到本地
 */
public class DownLoadSaveUtil {
    public static void main(String[] args) {

        String remote_url = "http://invtest.nntest.cn/group1/M00/00/7D/wKjScV2cL-mAAnZLAACJMZ5iZIY155.pdf";
        String dir = "src//file//pdf//";
        String fileName = System.currentTimeMillis()+".pdf";
        //文件保存到本地
        FileTool.downloadByApacheCommonIO(remote_url,dir,fileName);

        File file = new File(dir+fileName);

        FastDFSClientUtil fastDFSClient = null;
        FileInputStream fis = null;
        String url="";
        try {
            fis = new FileInputStream(file);
            byte[] file_buff = null;
            if(fis != null){
                int len = fis.available();
                file_buff = new byte[len];
                fis.read(file_buff);
            }
            //初始化
            fastDFSClient = new FastDFSClientUtil("classpath:client.conf");
            String extName = FastDFSClientUtil.getFileExt(file.getPath());
            url = fastDFSClient.uploadFile(file_buff,extName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(url);
    }
}
