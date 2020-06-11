package com.leyou.controller;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("upload")
public class UploadController {

    @PostMapping("image")
    public String image(@RequestParam("file") MultipartFile file) throws IOException, MyException {
        //加载客户端配置文件，配置文件中指明了tracker服务器的地址
        ClientGlobal.init("fastdfs.conf");
        //验证配置文件是否加载成功
        System.out.println(ClientGlobal.configInfo());

        //创建TrackerClient对象，客户端对象
        TrackerClient trackerClient = new TrackerClient();

        //获取到调度对象，也就是与Tracker服务器取得联系
        TrackerServer trackerServer = trackerClient.getConnection();

        //创建存储客户端对象
        StorageClient storageClient = new StorageClient(trackerServer,null);

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);

        String[] urls = storageClient.upload_file(file.getBytes(),suffix,null);
        String url = "http://192.168.58.132/"+urls[0]+"/"+urls[1];
        return url;
    }
}
