package com.meiziaccess.controller;


import com.meiziaccess.model.ItemMedia;
import com.meiziaccess.model.ItemMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user-u1 on 2016/4/12.
 */

@Controller
public class OrderController {

    @Value("${configure.IPAdderss}")
    private String IPAddress;           //当前服务器ip

    @Autowired
    private ItemMediaRepository itemMediaRepository;

    //订单PostAPI
    @RequestMapping(value = "/order", method = RequestMethod.POST, produces = "application/json;charset-UTF-8")
    @ResponseBody
    public Map<String, Object> order(@RequestBody ItemMedia ord){
        Map<String, Object> order_return = new HashMap<String, Object>();
        System.out.println(ord.getUuid() + ", " + ord.getIsEntire() + ", " + ord.getStarttime() + ", " + ord.getEndtime() + ", " + ord.getHighdef_video_path());
        String url =  IPAddress + "/media?id=1";

        //处理视频，修改链接和地址
        ord.setStatus(1);
        ord.setUrl(url);
        ItemMedia itemMedia = itemMediaRepository.save(ord);

//        url = IPAddress + "/media?id=" +

        //返回字段
        order_return.put("uuid", itemMedia.getUuid());
        order_return.put("status", itemMedia.getStatus());
        order_return.put("url", itemMedia.getUrl());

        return order_return;
    }

    //视频下载链接
    @RequestMapping(value = "/media", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile( Long id)
            throws IOException {
        //生成相应的文件下载链接
//        String filePath = "E:/" + 1 + ".rmvb";
        String filePath = "/home/derc/video/" + id + ".rmvb";
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
