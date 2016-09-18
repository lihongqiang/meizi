package com.meiziaccess.task;

import com.meiziaccess.service.DownloadService;
import com.meiziaccess.service.UploadService;
import com.meiziaccess.uploadModel.UploadLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${configure.upload.local_path}")
    private String upload_local_path;

    @Value("${configure.download.local_path}")
    private String download_local_path;

    @Value("${configure.download.remote_path}")
    private String download_remote_path;

    /* upload variable*/
    /*****************************************/
    @Value("${configure.upload.remote_path}")
    private  String upload_remote_path;

    @Value("${configure.upload.vendor_name}")
    private  String upload_vendor_name;

    @Value("${configure.upload.uploader_name}")
    private  String uploader_name;

    @Value("${configure.local.vendor_path}")
    private  String vendor_path;

    @Value("${configure.upload.trans_path}")
    private String trans_path;

    @Value("${configure.upload.play_path}")
    private String play_path;
    /****************************************/



//    定时上传编目和低码视频文件
    @Scheduled(fixedRate = 1000*3600*24)
    public void uploadTasks() {
        System.out.println("Execute Scheduled upload task. The time is " + dateFormat.format(new Date()));

        //测试上传文件服务
        if(uploadService == null){
            uploadService =  new UploadService();
        }

        System.out.println("upload_remote_path = " + upload_remote_path);
        System.out.println("upload_vendor_name = " + upload_vendor_name);
        System.out.println("uploader_name = " + uploader_name);

        System.out.println("upload_local_path = " + upload_local_path);
        System.out.println("download_local_path = " + download_local_path);
        System.out.println("uploader_name = " + download_remote_path);

        uploadService.uploadXmlAndVideo(upload_local_path, uploadLogRepository, upload_remote_path, upload_vendor_name, uploader_name, vendor_path, trans_path, play_path);

    }

//    定时下载处理后的编目和订单文件
//    @Scheduled(fixedRate = 1000*3600*24)
    public void downLoadTasks(){
        System.out.println("Run download task. The time is  " + dateFormat.format(new Date()));
        System.out.println("download_remote_path is " + download_remote_path);
        System.out.println("download_local_path is " + download_local_path);
        if(downloadService == null){
            downloadService =  new DownloadService();
        }
        downloadService.downloadXmlAndOrder(download_local_path, download_remote_path);
    }
}
