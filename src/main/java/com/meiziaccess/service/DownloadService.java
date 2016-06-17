package com.meiziaccess.service;

import com.meiziaccess.download.DownloadTool;
import com.meiziaccess.download.DownloadToolInterface;
import org.springframework.stereotype.Service;

/**
 * Created by user-u1 on 2016/6/17.
 */
@Service
public class DownloadService {

    DownloadToolInterface downloadTool;

    //    下载处理好的编目和订单文件
    public boolean downloadXmlAndOrder(){
        if(downloadTool == null){
            downloadTool = new DownloadTool();
        }
        downloadTool.download();
        return true;
    }
}
