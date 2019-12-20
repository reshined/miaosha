package com.seckill.common.util;


import java.io.File;
import java.io.FileInputStream;

public class FileUtil {

    private final static String file_dir = "src//file//pdf//";

    private final static String fast_Add = "http://file.qjyapp.com/";

    /**
     *
     * @param remote_url
     * @param clientPath  fast配置文件绝对路径
     * @return
     */
    public static String getUrl(String remote_url,String clientPath){
//        remote_url = "http://invtest.nntest.cn/group1/M00/00/7D/wKjScV2cL-mAAnZLAACJMZ5iZIY155.pdf";
        String fileName = System.currentTimeMillis()+".pdf";
        ////1.下载该pdf文件保存到本地
        FileTool.downloadByApacheCommonIO(remote_url,file_dir,fileName);

        File file = new File(file_dir+fileName);

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
//            fastDFSClient = new FastDFSClientUtil("classpath:client.conf");
            fastDFSClient = new FastDFSClientUtil(clientPath);
            String extName = FastDFSClientUtil.getFileExt(file.getPath());
            //保存到文件服务器
            url = fast_Add + fastDFSClient.uploadFile(file_buff,extName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }
}
