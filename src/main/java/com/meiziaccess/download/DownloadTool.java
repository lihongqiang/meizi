package com.meiziaccess.download;

import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * Created by user-u1 on 2016/6/17.
 */
public class DownloadTool implements DownloadToolInterface {

    @Value("${configure.download.remote_path}")
    private String download_remote_path;


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

    //Linux和Mac指令版本
    public void download(String localDir, String remoteDir){
        System.out.println("download_remote_path is " + remoteDir);
        System.out.println("scp -P 10722 derc@162.105.180.15:" + remoteDir + "/*.xml "  + localDir);
        execCmds("scp -P 10722 derc@162.105.180.15:" + remoteDir + "/*.xml "  + localDir);
    }

}
