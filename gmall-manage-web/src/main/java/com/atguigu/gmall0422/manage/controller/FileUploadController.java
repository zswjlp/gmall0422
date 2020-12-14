package com.atguigu.gmall0422.manage.controller;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin
public class FileUploadController {
    //获取文件服务器Ip地址
    @Value("${fileServer.url}")
    private String fileUrl;

    @RequestMapping("fileUpload")
    public String fileUpload(MultipartFile file)throws IOException,MyException {

        String imgUrl =fileUrl;
        if(file!=null){
        String configfile = this.getClass().getResource("/tracker.conf").getFile();
        ClientGlobal.init(configfile);
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer=trackerClient.getTrackerServer();
        StorageClient storageClient=new StorageClient(trackerServer,null);
        //动态获取上传的文件名称
        String orginalFilename=file.getOriginalFilename();
        //文件后缀名
            String extName = StringUtils.substringAfterLast(orginalFilename, ".");

            String[] upload_file = storageClient.upload_file(file.getBytes(), extName,null);
        for (int i = 0; i < upload_file.length; i++) {
            String path = upload_file[i];
//            System.out.println("s = " + s);
            //s=group1
            //s=M00/00/00/wKgByl_XSoKAJCBiAACbuutfIxI468.jpg
            imgUrl+="/"+path;
        }
        }
        return imgUrl;
    }
}
