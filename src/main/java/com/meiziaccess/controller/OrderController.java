package com.meiziaccess.controller;


import com.meiziaccess.model.ItemMedia;
import com.meiziaccess.model.ItemMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user-u1 on 2016/4/12.
 */

@Controller
public class OrderController {

    @Value("${configure.IPAdderss}")
    private String IPAddress;           //当前服务器ip

    @Value("${configure.download.video_path}")
    private String download_path;       //订单项高码视频转码后的路径

    @Autowired
    private ItemMediaRepository itemMediaRepository;

    //订单PostAPI
    @RequestMapping(value = "/order", method = RequestMethod.POST, produces = "application/json;charset-UTF-8")
    @ResponseBody
    public Map<String, Object> order(@RequestBody ItemMedia ord){
        Map<String, Object> order_return = new HashMap<String, Object>();

        //查询该订单项是否已经处理过，已处理就直接返回url
        List<ItemMedia> list =  itemMediaRepository.findMediaByUuid(ord.getUuid());
        ItemMedia itemMedia;
        if(list.isEmpty()){

            //设置生成的url
            String url = "http://" + IPAddress + "/media?uuid=" + ord.getUuid();
            ord.setUrl(url);

            //设置成0，表示还未完成转码
            ord.setStatus(0);

            itemMedia = itemMediaRepository.save(ord);
        }else{
            itemMedia = list.get(0);
        }

        //返回字段
        order_return.put("uuid", itemMedia.getUuid());
        order_return.put("status", itemMedia.getStatus());
        order_return.put("url", itemMedia.getUrl());

        return order_return;
    }

    //视频下载链接
    @RequestMapping(value = "/media", method = RequestMethod.GET)
    public ResponseEntity downloadFile( Long uuid)
            throws IOException {
        //生成相应的文件下载链接

//        通过uuid查找高码视频路径
        List<ItemMedia> list = itemMediaRepository.findMediaByUuid(uuid);

        //如果没有uuid，则提示没有该订单项
        if(list.isEmpty()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        ItemMedia itemMedia = list.get(0);
        String filePath = "";

        //查看转码状态，如果完成就返回，否则提示转码未完成
        if(itemMedia.getStatus()==0){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }else{
            filePath = itemMedia.getOrder_video_path();
        }

        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }

}
