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

    //执行指令
    public Vector<String> execCmds(String cmd);

    //检查文件夹是否为空
    public boolean checkFolder(String floderPath);

    //读取文件夹中的编目,素材文件列表和上架信息，修改数据库内容
    public boolean updateDatabase(String floderPath, UploadLogRepository uploadLogRepository);

    //执行shell脚本，上传文件，删除文件
    public boolean uploadFile(String folderPath);

    //读取上架文件
    public  Map<String, String> readFile(String fileName);
}
