package com.meiziaccess.upload;

import com.meiziaccess.CommandTool.CommandRunner;
import com.meiziaccess.model.UploadItem;
import com.meiziaccess.task.MyScheduledTasks;
import com.meiziaccess.uploadModel.UploadLog;
import com.meiziaccess.uploadModel.UploadLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by user-u1 on 2016/5/27.
 */
public class UploadTool implements UploadToolInterface {

        /* upload variable*/
    /*****************************************/
//    private  String upload_remote_path;
//
//    private  String upload_vendor_name;
//
//    private  String uploader_name;
//
//    private String vendor_path;

    /****************************************/



    @Override
    public String getOSName() {
        Properties props = System.getProperties();
        String osName = props.getProperty("os.name").split(" ")[0];
        return osName;
    }



    @Override
    public boolean checkFolder(String floderPath) {
        //查看系统
        String osName = getOSName();

        Vector<String> outs;
        if(osName.equals("Windows")){
            outs = CommandRunner.execCmds("cmd /c dir " + floderPath +" /ad /b " );
        }else {
            outs = CommandRunner.execCmds("/bin/ls  " + floderPath );
        }
        if(!outs.isEmpty()){ //文件夹有文件
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
                System.out.println(tempString);
                String [] tmp = tempString.split(" ");
                if(tmp.length != 2) continue;
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

    public boolean updateDatabase(String folderPath, String xmlName, String videoName, UploadLogRepository uploadLogRepository,
                                  String upload_remote_path,String upload_vendor_name, String uploader_name, String vendor_path,
                                  String trans_path, String play_path) {

        Map<String, String> map = new HashMap<String, String>();
        //获取上架信息:price copyright
        try {
             map = readFile(folderPath + "/upload.txt");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        if(uploadLogRepository == null){
            System.out.println("uploadLogRepository is null");
        }

        //查找高码视频地址
        String higeCodeVideoName = "";
        String[] cmdsArray = new String[]{"/bin/ls", vendor_path};
        System.out.println("/bin/ls "+vendor_path);
        Vector<String> lists = CommandRunner.execCmdsArray(cmdsArray);
        System.out.println(lists.toString());
        String videoNameWithFormat = videoName.substring(0, videoName.lastIndexOf("."));
        for(String s : lists){
            String fileName = s.substring(0, s.lastIndexOf("."));
            System.out.println(fileName + "   " + videoNameWithFormat);
            if(fileName.equals(videoNameWithFormat)){
                System.out.println(s);
                higeCodeVideoName = s;
                break;
            }
        }

        //修改数据库信息
        UploadLog log = new UploadLog(upload_vendor_name,
                                    new Date(),
                                    uploader_name,
                                    upload_remote_path +"/"+ xmlName ,          //xml上传路径
                                    upload_remote_path +"/"+ videoName,         //vedio上传路径
                                    vendor_path + "/"+higeCodeVideoName,        //高码视频路径
                                    Double.parseDouble(map.get("price")),   //价格
                                    map.get("copyright"));
        //xml转换路径
        log.setXml_trans_path(trans_path + "/" +"trans_"+new Date().getTime()+"_"+xmlName);

        //视频转换成mp4播放路径
        String videoTransName = videoName.split("\\.")[0] + ".mp4";
        log.setVideo_play_path(play_path+"/"+videoTransName);

        uploadLogRepository.save(log);
        return true;
    }

    public static String removeBlank(String s){
        String ans="";
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i) != ' '){
                ans += s.charAt(i);
            }
        }
        return ans;
    }

    public String getFullName(String s){
        String ans="";
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i) == ' '){
                ans += "\" \"";
//                int j=i+1;
//                while(s.charAt(j)==' ') j++;
//                i = j-1;
            }else{
                ans += s.charAt(i);
            }

        }
        return ans;
    }

    public String getFullNameWithZhunayi(String s){
        String ans="";
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i) == ' ') {
                ans += '\\';
            }
            ans += s.charAt(i);
        }
        return ans;
    }

    public boolean uploadFile(String folderPath, UploadLogRepository uploadLogRepository, String upload_remote_path,
                              String upload_vendor_name, String uploader_name, String vendor_path, String trans_path,
                              String play_path) {

        if(uploadLogRepository == null){
            System.out.println("UploadLogRepository is null.");
            return false;
        }

        //查看系统
        String osName = getOSName();
        //获取文件列表
        Vector<String> outs;
        if(osName.equals("Windows")){               //如果系统是windows，需要下载pscp程序，添加到path中
            //上传文件
//            outs = CommandRunner.execCmds("cmd /c dir " + folderPath +" /a-d /b " );
//            String xmlName="", videoName="";
//            for(int i=0; i<outs.size(); i++){
//                String[] file = outs.get(i).split("\\.");
//                //判断是否是xml文件
//                if(file[file.length-1].equals("xml")){
//                    xmlName = outs.get(i);
//
//                    //上传xml文件
//                    CommandRunner.execCmds("pscp -P 10722 -pw pkulky201 " + folderPath + "\\" + outs.get(i) + " derc@162.105.180.15:" + upload_remote_path);
//                }else{
//                    if(outs.get(i).equals("upload.txt")){
//                        continue;
//                    }else{
//                        videoName = outs.get(i);
//                        //上传视频
//                        CommandRunner.execCmds("pscp -P 10722 -pw pkulky201 " + folderPath + "\\" + outs.get(i) + " derc@162.105.180.15:" + upload_remote_path );
//                    }
//                }
//                //删除文件
//                System.out.println("cmd /c del " + folderPath + "\\" + outs.get(i));
//                CommandRunner.execCmds("cmd /c del " + folderPath + "\\" + outs.get(i));
//            }
//
//            //更新数据库
//            updateDatabase(folderPath, xmlName, videoName, uploadLogRepository,  upload_remote_path,
//                    upload_vendor_name,  uploader_name,  vendor_path);
//
//            //删除upload.txt文件
//            CommandRunner.execCmds("cmd /c del " + folderPath + "\\" + "upload.txt");

        }else {
            outs = CommandRunner.execCmds("/bin/ls " + folderPath);
            String xmlName="", videoName="";
            for(int i=0; i<outs.size(); i++){
                String[] file = outs.get(i).split("\\.");
                System.out.println(outs.get(i) + " " +file.length);

                if(outs.get(i).contains(" ")){
                    String[] cmdsArray;
                    if(!file[file.length-1].equals("txt") && !file[file.length-1].equals("xml")){
                        cmdsArray = new String[]{"/bin/mv", vendor_path + "/" + outs.get(i),  vendor_path + "/" + removeBlank(outs.get(i))};
                        System.out.println("/bin/mv " + vendor_path + "/" + outs.get(i) + " " + vendor_path + "/" + removeBlank(outs.get(i)));
                        CommandRunner.execCmdsArray(cmdsArray);
                    }
                    cmdsArray = new String[]{"/bin/mv", folderPath+"/"+outs.get(i), folderPath+"/"+removeBlank(outs.get(i))};
                    System.out.println("/bin/mv "+folderPath+"/"+outs.get(i)+" "+folderPath+"/"+removeBlank(outs.get(i)));
                    CommandRunner.execCmdsArray(cmdsArray);
                    outs.set(i, removeBlank(outs.get(i)));
                }

                //判断是否是xml文件
                if(file[file.length-1].equals("xml")){
                    xmlName = outs.get(i);
                    //上传xml文件
//                    CommandRunner.execCmds("scp -P 10722 " + folderPath + "/" + outs.get(i) + " derc@162.105.180.15:" + upload_remote_path );
                }else{
                    if(outs.get(i).equals("upload.txt")){
                        continue;
                    }else{
                        //上传视频
//                        CommandRunner.execCmds("scp -P 10722 " + folderPath + "/" + outs.get(i) + " derc@162.105.180.15:" + upload_remote_path );
                        videoName = outs.get(i);
                    }
                }
                try {
                    System.out.println(folderPath + "/" + outs.get(i) + " " + upload_remote_path);
                    CommandRunner.scpPut(folderPath + "/" + outs.get(i), upload_remote_path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //删除文件
                System.out.println("rm " + folderPath + "/" + outs.get(i));
//                CommandRunner.execCmds("rm " + folderPath + "/" + outs.get(i));
            }
            //更新数据库
            updateDatabase(folderPath, xmlName , videoName, uploadLogRepository,  upload_remote_path,
                    upload_vendor_name,  uploader_name,  vendor_path, trans_path, play_path );
            //删除upload.txt文件
//            CommandRunner.execCmds("rm " + folderPath + "/" + "upload.txt");
        }
        return true;
    }

    public boolean uploadFiles(String folderPath, UploadLogRepository uploadLogRepository, String upload_remote_path,
                               String upload_vendor_name, String uploader_name, String vendor_path, String trans_path,
                               String play_path){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String day = dateFormat.format(date);
        String remote_full_path = upload_remote_path + "/" + day;

        //查看系统
        String osName = getOSName();
        //获取文件列表
        Vector<String> outs;
        if(osName.equals("Windows")){               //如果系统是windows，需要下载pscp程序，添加到path中
            //创建文件夹，文件夹不存在，第一次复制创建日期文件夹，第二次开始复制文件夹
//            System.out.println("/bin/mkdir " + remote_full_path );
//            try {
//                CommandRunner.runSSH( "/bin/mkdir " + remote_full_path );
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
////            CommandRunner.execCmds("pscp -P 10722 -pw pkulky201 -r " + folderPath  + " derc@162.105.180.15:" + remote_full_path);
//            //获取上传路径中的所有文件夹
//            outs = CommandRunner.execCmds("cmd /c dir " + folderPath +" /ad /b " );
//            for(int i=0; i<outs.size(); i++){
//                //上传文件夹
//                CommandRunner.execCmds("pscp -P 10722 -pw pkulky201 -r " + folderPath + "\\" + outs.get(i) + " derc@162.105.180.15:" + remote_full_path);
//                //删除文件夹
//                System.out.println("cmd /c rd /s/q  " + folderPath + "\\" + outs.get(i));
//                CommandRunner.execCmds("cmd /c rd /s/q " + folderPath + "\\" + outs.get(i));
//            }
//            //删除upload.txt文件
//            CommandRunner.execCmds("cmd /c del " + folderPath + "\\" + "upload.txt");
        }else {
            //创建远程文件夹
            System.out.println("/bin/mkdir " + remote_full_path );
            try {
                CommandRunner.runSSH( "/bin/mkdir " + remote_full_path );
            } catch (IOException e) {
                e.printStackTrace();
            }
            //查看文件夹列表
            outs = CommandRunner.execCmds("/bin/ls -F " + folderPath + " | grep '/$' ");
            System.out.println("/bin/ls -F " + folderPath + " | grep '/$' ");
            for(int i=0; i<outs.size(); i++){
                if(outs.get(i).charAt(outs.get(i).length()-1)==':') continue;
                System.out.println(""+i+": "+outs.get(i));

                //创建远程文件夹
                String folderName = outs.get(i).substring(0, outs.get(i).length()-1);
                if(folderName.contains(" ")){

                    System.out.println("/bin/mv "+folderPath+"/"+folderName+" "+folderPath+"/"+removeBlank(folderName));
                    String[] cmdsArray = new String [] {"/bin/mv", folderPath+"/"+folderName, folderPath+"/"+removeBlank(folderName)};
                    Vector<String> vecstrs = CommandRunner.execCmdsArray(cmdsArray);
                    System.out.println(vecstrs.toString());
                    folderName = removeBlank(folderName);
                }
                System.out.println("/bin/mkdir " + remote_full_path + "/" + folderName );
                try {
                    CommandRunner.runSSH("/bin/mkdir " + remote_full_path + "/" + folderName);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //上传和删除文件
                System.out.println("localPath="+folderPath+"/"+folderName);
                System.out.println("remotePath="+remote_full_path + "/" + folderName);
                uploadFile(folderPath+"/"+folderName, uploadLogRepository, remote_full_path + "/" + folderName,
                        upload_vendor_name, uploader_name,  vendor_path, trans_path, play_path);

                //删除本地文件夹
                System.out.println("rm -rf " + folderPath + "/" + folderName);
                CommandRunner.execCmds("rm -rf " + folderPath + "/" + folderName);
            }
        }
        return true;
    }

//    public  static void main(String[] args){
//        String filePath = "C:\\Users\\user-u1\\Desktop\\upload.txt";
//        UploadTool tool = new UploadTool();
//        Map<String, String> map = tool.readFile(filePath);
//        System.out.println(map.get("price") + "  " + map.get("copyright"));
//    }

    public static List<UploadItem> getUploadItems(String folderPath){

        List<UploadItem> list = new ArrayList<>();

        UploadTool tool = new UploadTool();
        //查看系统
        String osName = tool.getOSName();
        if(osName.equals("Windows")){
            //获取文件列表
            Vector<String> outs = CommandRunner.execCmds("cmd /c dir " + folderPath + " /B");
            for(int i=0; i<outs.size(); i++){
                String folderName = outs.get(i);
                UploadItem item = new UploadItem(folderName, getMD5(folderName));
                list.add(item);
            }
        }else{
            //获取文件列表
            Vector<String> outs = CommandRunner.execCmds("/bin/ls -F " + folderPath + " | grep '/$' ");
            for(int i=0; i<outs.size(); i++){

                //文件夹名是否含有~
                if(outs.get(i).charAt(0)=='~' || outs.get(i).charAt(0) == '.') continue;
                System.out.println(""+i+": "+outs.get(i));

                //文件夹名是否含有空格
                String folderName = outs.get(i).substring(0, outs.get(i).length()-1);
                if(folderName.contains(" ")){
                    String[] cmdsArray = new String [] {"/bin/mv -rf", folderPath+"/"+folderName, folderPath+"/"+removeBlank(folderName)};
                    Vector<String> vecstrs = CommandRunner.execCmdsArray(cmdsArray);

                    folderName = removeBlank(folderName);
                }

                System.out.println(folderName);
                UploadItem item = new UploadItem(folderName, getMD5(folderName));
                list.add(item);
            }
        }
        return list;
    }

    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            System.out.println("MD5加密出现错误");
        }
        return "";
    }

    public static List<UploadItem> getTestData(){
        List<UploadItem> list = new ArrayList<>();
        UploadItem item0 = new UploadItem(false, "长征 第1集", "2016/11/1 14:39", 600, 0, 100, 0, 5, UploadTool.getMD5("长征 第1集"));
        UploadItem item1 = new UploadItem(false, "长征 第2集", "2016/11/1 14:39", 600, 1, 10, 0, 5, UploadTool.getMD5("长征 第2集"));
        UploadItem item2 = new UploadItem(false, "长征 第3集", "2016/11/1 14:39", 600, 0, 100, 0, 5, UploadTool.getMD5("长征 第3集"));
        UploadItem item3 = new UploadItem(false, "长征 第4集", "2016/11/1 14:39", 600, 0, 100, 0, 5, UploadTool.getMD5("长征 第4集"));
        UploadItem item4 = new UploadItem(false, "长征 第5集", "2016/11/1 14:39", 600, 0, 100, 0, 5, UploadTool.getMD5("长征 第5集"));
        list.add(item0);
        list.add(item1);
        list.add(item2);
        list.add(item3);
        list.add(item4);
        return list;
    }

//    public static void main(String[] args){
//        UploadTool tool = new UploadTool();
//        List<UploadItem> list = tool.getUploadItems("E:\\program\\媒资\\data\\低码");
//        for(int i=0; i<list.size(); i++){
//            System.out.println(list.get(i).getTitle());
//        }
//    }
}
