package com.meiziaccess.upload;

import com.meiziaccess.task.MyScheduledTasks;
import com.meiziaccess.uploadModel.UploadLog;
import com.meiziaccess.uploadModel.UploadLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by user-u1 on 2016/5/27.
 */
public class UploadTool implements UploadToolInterface {

        /* upload variable*/
    /*****************************************/
    private  String upload_remote_path;

    private  String upload_vendor_name;

    private  String uploader_name;

    /****************************************/


    @Override
    public String getOSName() {
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name").split(" ")[0];
        return osName;
    }

    @Override
    public Vector<String> execCmds(String cmd) {
        Vector<String> outs = new Vector<String>();
        try {
            Process pro = Runtime.getRuntime().exec(cmd);
            pro.waitFor();
            InputStream in = pro.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while((line = read.readLine())!=null){
                outs.add(line);
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
            outs = execCmds("cmd /c dir " + floderPath +" /a-d /b " );
        }else {
            outs = execCmds("/bin/ls " + floderPath);
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
        Map<String, String> map = new HashMap<String, String>();
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

    @Override
    public boolean updateDatabase(String folderPath, String fileName, UploadLogRepository uploadLogRepository) {

        Map<String, String> map = new HashMap<String, String>();
        //获取上架信息
        try {
             map = readFile(folderPath + "/upload.txt");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if(uploadLogRepository == null){
            System.out.println("uploadLogRepository is null");
        }
        //修改数据库信息
        UploadLog log = new UploadLog(upload_vendor_name,
                                    new Date(),
                                    uploader_name,
                                    upload_remote_path + "/xml/"+fileName+".xml" ,          //xml上传路径
                                    upload_remote_path + "/video/"+fileName+".mp4",         //video上传路径
                                    folderPath,                                         //高码视频路径
                                    Double.parseDouble(map.get("price")),   //价格
                                    map.get("copyright"));
        log.setXml_trans_path(upload_remote_path + "/xml_trans/"+fileName+new Date().getTime());
        uploadLogRepository.save(log);
        return true;
    }

    @Override
    public boolean uploadFile(String folderPath, UploadLogRepository uploadLogRepository, String upload_remote_path, String upload_vendor_name, String uploader_name) {

        this.upload_remote_path = upload_remote_path;
        this.upload_vendor_name = upload_vendor_name;
        this.uploader_name = uploader_name;

        if(uploadLogRepository == null){
            System.out.println("UploadLogRepository is null.");
            return false;
        }

        //查看系统
        String osName = getOSName();
        //获取文件列表
        Vector<String> outs;
        if(osName.equals("Windows")){
            //上传文件
            outs = execCmds("cmd /c dir " + folderPath +" /a-d /b " );
            for(int i=0; i<outs.size(); i++){
                String[] file = outs.get(i).split("\\.");
                //判断是否是xml文件
                if(file[file.length-1].equals("xml")){
                    //更新数据库
                    updateDatabase(folderPath, file[0], uploadLogRepository);
                    //上传xml文件
                    execCmds("pscp -P 10722 -pw pkulky201 " + folderPath + "\\" + outs.get(i) + " derc@162.105.180.15:" + upload_remote_path + "/xml");
                }else{
                    if(outs.get(i).equals("upload.txt")){
                        continue;
                    }else{
                        //上传视频
                        execCmds("pscp -P 10722 -pw pkulky201 " + folderPath + "\\" + outs.get(i) + " derc@162.105.180.15:" + upload_remote_path + "/video");
                    }
                }
                //删除文件
                System.out.println("cmd /c del " + folderPath + "\\" + outs.get(i));
                execCmds("cmd /c del " + folderPath + "\\" + outs.get(i));
            }
            //删除upload.txt文件
            execCmds("cmd /c del " + folderPath + "\\" + "upload.txt");
        }else {
            outs = execCmds("/bin/ls " + folderPath);
            for(int i=0; i<outs.size(); i++){
                String[] file = outs.get(i).split("\\.");
                System.out.println(outs.get(i) + " " +file.length);
                //判断是否是xml文件
                if(file[file.length-1].equals("xml")){
                    //更新数据库
                    updateDatabase(folderPath, file[0], uploadLogRepository);
                    //上传xml文件
                    execCmds("scp -P 10722 " + folderPath + "/" + outs.get(i) + " derc@162.105.180.15:" + upload_remote_path + "/xml");
                }else{
                    if(outs.get(i).equals("upload.txt")){
                        continue;
                    }else{
                        //上传视频
                        execCmds("scp -P 10722 " + folderPath + "/" + outs.get(i) + " derc@162.105.180.15:" + upload_remote_path + "/video");
                    }
                }
                //删除文件
                System.out.println("rm " + folderPath + "/" + outs.get(i));
                execCmds("rm " + folderPath + "/" + outs.get(i));
            }
            //删除upload.txt文件
            execCmds("rm " + folderPath + "/" + "upload.txt");
        }
        return true;
    }
}
