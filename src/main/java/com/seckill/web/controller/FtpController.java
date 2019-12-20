package com.seckill.web.controller;

import com.seckill.common.util.FastDFSClientUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ftp")
public class FtpController {

    @RequestMapping(value = "/uploadFile",headers="content-type=multipart/form-data", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFile (@RequestParam("file") MultipartFile file){
        String result ;
        String originalFilename = file.getOriginalFilename();
        String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        //上传到图片服务器
        FastDFSClientUtil fastDFSClient = null;
        String url="";
        try {
            fastDFSClient = new FastDFSClientUtil("classpath:client.conf");
            url = fastDFSClient.uploadFile(file.getBytes(), extName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(url);
    }
}
