package com.meiziaccess.service;

import com.meiziaccess.upload.UploadTool;
import com.meiziaccess.upload.UploadToolInterface;
import com.meiziaccess.uploadModel.UploadLogRepository;

/**
 * Created by user-u1 on 2016/5/28.
 */
public class UploadService {

    UploadToolInterface tool = new UploadTool();

    //上传编目和低码视频,定时
    public boolean uploadXmlAndVideo(String folderPath, UploadLogRepository uploadLogRepository){

        //检查文件夹是否为空，为空则退出
        if(!tool.checkFolder(folderPath)){
            return false;
        }

        //根据文件夹中的上架信息修改数据库
        tool.updateDatabase(folderPath, uploadLogRepository);

        //扫描文件夹，上传文件

        return true;
    }

}
