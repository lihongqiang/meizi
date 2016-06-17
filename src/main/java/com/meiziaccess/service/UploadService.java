package com.meiziaccess.service;

import com.meiziaccess.upload.UploadTool;
import com.meiziaccess.upload.UploadToolInterface;
import com.meiziaccess.uploadModel.UploadLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by user-u1 on 2016/5/28.
 */
@Service
public class UploadService {

//    @Autowired
//    private  UploadLogRepository uploadLogRepository;

    UploadToolInterface tool = new UploadTool();

    //上传编目和低码视频,定时
    public boolean uploadXmlAndVideo(String folderPath, UploadLogRepository uploadLogRepository){

        //检查文件夹是否为空，为空则退出
        if(!tool.checkFolder(folderPath)){
            System.out.println("文件夹为空");
            return false;
        }

        //扫描文件夹，上传文件
        System.out.println("上传文件中......");
        tool.uploadFile(folderPath, uploadLogRepository);

        return true;
    }


//    public static void main(String[] args){
//        //测试上传文件服务
//        UploadService uploadService = new UploadService();
//        uploadService.uploadXmlAndVideo("E:\\dianshitai", uploadLogRepository);
////        uploadService.uploadXmlAndVideo("/Users/lhq/Workspace/dianshitai", uploadLogRepository);
//    }
}
