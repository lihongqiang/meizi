package com.meiziaccess.controller;


import com.meiziaccess.model.Order;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user-u1 on 2016/4/12.
 */

@Controller
public class OrderController {

    @Value("${configure.IPAdderss}")
    private String IPAddress;

    //订单PostAPI
    @RequestMapping(value = "/order", method = RequestMethod.POST, produces = "application/json;charset-UTF-8")
    @ResponseBody
    public Map<String, Object> order(@RequestBody  Order ord){
        Map<String, Object> order_return = new HashMap<>();
        System.out.println(ord.getUuid() + ", " + ord.isEntire() + ", " + ord.getStarttime() + ", " + ord.getEndtime() + ", " + ord.getHighdef_video_path());
        order_return.put("uuid", ord.getUuid());
        order_return.put("url", IPAddress + "/media?id=1");
        return order_return;
    }

    //视屏下载链接
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
