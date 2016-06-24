package com.meiziaccess.task;

import com.meiziaccess.service.DownloadService;
import com.meiziaccess.service.UploadService;
import com.meiziaccess.uploadModel.UploadLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user-u1 on 2016/6/10.
 */
@Component
public class MyScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private UploadService uploadService;
    private DownloadService downloadService;

    @Autowired
    private UploadLogRepository uploadLogRepository;

//    定时上传编目和低码视频文件
//    @Scheduled(fixedRate = 1000*3600*24)
    public void uploadTasks() {
        System.out.println("The time is now " + dateFormat.format(new Date()));

        //测试上传文件服务
        if(uploadService == null){
            uploadService =  new UploadService();
        }
		uploadService.uploadXmlAndVideo("E:\\dianshitai", uploadLogRepository);
//        uploadService.uploadXmlAndVideo("/Users/lhq/Workspace/dianshitai", uploadLogRepository);

    }

//    定时下载处理后的编目和订单文件
    @Scheduled(fixedRate = 1000*3600*24)
    public void downLoadTasks(){
        System.out.println("The time is now " + dateFormat.format(new Date()));
        //测试上传文件服务
        if(downloadService == null){
            downloadService =  new DownloadService();
        }
        downloadService.downloadXmlAndOrder();
    }
}
