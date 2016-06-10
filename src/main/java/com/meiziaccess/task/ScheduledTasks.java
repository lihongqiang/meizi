package com.meiziaccess.task;

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
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private UploadLogRepository uploadLogRepository;

    @Scheduled(fixedRate = 1000*5)
    public void reportCurrentTime() {
        System.out.println("The time is now " + dateFormat.format(new Date()));

        //测试上传文件服务
        UploadService uploadService = new UploadService();
		uploadService.uploadXmlAndVideo("E:\\dianshitai", uploadLogRepository);
//        uploadService.uploadXmlAndVideo("/Users/lhq/Workspace/dianshitai", uploadLogRepository);
    }
}
