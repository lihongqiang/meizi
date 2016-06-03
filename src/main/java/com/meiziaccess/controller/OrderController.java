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
    private String IPAddress;

    @Autowired
    private ItemMediaRepository itemMediaRepository;


    //订单PostAPI
    @RequestMapping(value = "/order", method = RequestMethod.POST, produces = "application/json;charset-UTF-8")
    @ResponseBody
    public Map<String, Object> order(@RequestBody ItemMedia ord){
        Map<String, Object> order_return = new HashMap<>();
        System.out.println(ord.getUuid() + ", " + ord.getIsEntire() + ", " + ord.getStarttime() + ", " + ord.getEndtime() + ", " + ord.getHighdef_video_path());
        String url =  IPAddress + "/media?id=1";

        //处理视频，修改链接和地址
        ord.setStatus(1);
        ord.setUrl(url);
        ItemMedia itemMedia = itemMediaRepository.save(ord);

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

//    @RequestMapping(value="/media/", method=RequestMethod.GET)
//    public void getDownload(Long id, HttpServletRequest request, HttpServletResponse response) {
//
//        // Get your file stream from wherever.
//        String fullPath = "E:/" + id +".rmvb";
//        File downloadFile = new File(fullPath);
//
//        String path = request.getContextPath();
//        ServletContext context = request.getServletContext();
//
//        // get MIME type of the file
//        String mimeType = context.getMimeType(fullPath);
//        if (mimeType == null) {
//            // set to binary type if MIME mapping not found
//            mimeType = "application/octet-stream";
//            System.out.println("context getMimeType is null");
//        }
//        System.out.println("MIME type: " + mimeType);
//
//        // set content attributes for the response
//        response.setContentType(mimeType);
//        response.setContentLength((int) downloadFile.length());
//
//        // set headers for the response
//        String headerKey = "Content-Disposition";
//        String headerValue = String.format("attachment; filename=\"%s\"",
//                downloadFile.getName());
//        response.setHeader(headerKey, headerValue);
//
//        // Copy the stream to the response's output stream.
//        try {
//            InputStream myStream = new FileInputStream(fullPath);
//            IOUtils.copy(myStream, response.getOutputStream());
//            response.flushBuffer();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
