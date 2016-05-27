package com.meiziaccess.upload;

import com.meiziaccess.uploadModel.UploadLog;
import com.meiziaccess.uploadModel.UploadLogRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by user-u1 on 2016/5/27.
 */
public class UploadTool implements UploadToolInterface {

    public  static void main(String[] args){
//        String cmd = "cmd /c dir d:" ;
//        UploadTool tool = new UploadTool();
//        Vector<String> outs = tool.execCmds(cmd);
//        System.out.println(outs.toString());

//        UploadTool tool = new UploadTool();
//        if(tool.checkFolder("D:")){
//            System.out.println("不为空");
//        }else{
//            System.out.println("为空");
//        }

//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
//        System.out.println(date.getTime());
//        System.out.println("It is " + formatter.format(date.getTime()));

    }

    @Override
    public String getOSName() {
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name").split(" ")[0];
        return osName;
    }

    @Override
    public Vector<String> execCmds(String cmd) {
        Vector<String> outs = new Vector<>();
        try {
            Process pro = Runtime.getRuntime().exec(cmd);
            pro.waitFor();
            InputStream in = pro.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while((line = read.readLine())!=null){
                outs.add(line);
                System.out.println(line);
            }
            //如果pro不为空，那么要清空
            if(null!=pro){
                pro.destroy();
                pro=null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outs;
    }

    @Override
    public boolean checkFolder(String floderPath) {
        //查看系统
        String osName = getOSName();

        Vector<String> outs;
        if(osName.equals("Windows")){
            outs = execCmds("cmd /c dir " + floderPath);
        }else {
            outs = execCmds("/bin/ls -al" + floderPath);
        }
        if(!outs.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     * 获取上架信息
     */
    public  Map<String, String> readFile(String fileName)  {
        Map<String, String> map = new HashMap<>();
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 获取上架信息
                String [] tmp = tempString.split(" ");
                map.put(tmp[0], tmp[1]);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return map;
    }


//    @Autowired
//    private UploadLogRepository uploadLogRepository;

    @Override
    public boolean updateDatabase(String folderPath, UploadLogRepository uploadLogRepository) {

        Map<String, String> map = new HashMap<>();
        //获取上架信息
        try {
             map = readFile(folderPath + "/upload.txt");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        //修改数据库信息
        UploadLog log = new UploadLog("dianshitai", new Date(),  "admin", "/home/derc/upload/xml",
                "/home/derc/upload/video", folderPath, Double.parseDouble(map.get("price")), map.get("copyright"));
//        log.setVendor_name("dianshitai");
//        System.out.println(log.getVendor_name());
//        log.setUpload_time(new Date());
//        log.setUploader_name("admin");
//        log.setXml_upload_path("/home/derc/upload/xml");            //xml上传路径
//        log.setVideo_upload_path("/home/derc/upload/video");        //video上传路径
//        log.setVendor_path(folderPath);                       //高码视频路径
//        log.setVideo_price(Double.parseDouble(map.get("price")));
//        log.setVideo_copyright(map.get("copyright"));
        uploadLogRepository.save(log);
        return true;
    }

    @Override
    public boolean uploadFile(String folderPath) {

        return false;
    }
}
