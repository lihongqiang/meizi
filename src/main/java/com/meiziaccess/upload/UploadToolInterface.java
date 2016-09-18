package com.meiziaccess.upload;

import com.meiziaccess.uploadModel.UploadLogRepository;

import java.util.Map;
import java.util.Vector;

/**
 * Created by user-u1 on 2016/5/27.
 */
//上传编目和素材文件
public interface UploadToolInterface  {

    //判断系统
    public String getOSName();

    //检查文件夹是否为空
    public boolean checkFolder(String floderPath);

    //读取文件夹中的编目,素材文件列表和上架信息，修改数据库内容
    public boolean updateDatabase(String folderPath, String xmlName, String videoName, UploadLogRepository uploadLogRepository,
                                  String upload_remote_path,String upload_vendor_name, String uploader_name, String vendor_path,
                                  String trans_path, String play_path);
//    public boolean updateDatabase(String folderPath, String fileName, UploadLogRepository uploadLogRepository,
//                                  String upload_remote_path,String upload_vendor_name, String uploader_name, String vendor_path);
//    public boolean updateDatabase(String floderPath,  String fileName, UploadLogRepository uploadLogRepository);

    //执行shell脚本，上传文件，删除文件
    public boolean uploadFile(String folderPath, UploadLogRepository uploadLogRepository, String upload_remote_path,
                              String upload_vendor_name, String uploader_name, String vendor_path, String trans_path, String play_path);

    //执行shell脚本，上传文件夹，删除文件夹
    public boolean uploadFiles(String folderPath, UploadLogRepository uploadLogRepository, String upload_remote_path,
                              String upload_vendor_name, String uploader_name, String vendor_path, String trans_path, String play_path);

    //读取上架文件
    public  Map<String, String> readFile(String fileName);
}
