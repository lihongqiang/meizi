package com.meiziaccess.uploadModel;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by user-u1 on 2016/5/27.
 */
@Entity
@Table(name = "upload_log")
public class UploadLog {

    public UploadLog() {super();}

    public UploadLog(String vendor_name, Date upload_time, String uploader_name,
                     String xml_upload_path, String video_upload_path, String vendor_path,
                     double video_price, String video_copyright, int video_price_type, int video_copyright_duration,
                     String frame_extract_path) {
        this.vendor_name = vendor_name;
        this.upload_time = upload_time;
        this.uploader_name = uploader_name;
        this.xml_upload_path = xml_upload_path;
        this.video_upload_path = video_upload_path;
        this.vendor_path = vendor_path;
        this.video_price = video_price;
        this.video_copyright = video_copyright;
        this.video_price_type = video_price_type;
        this.video_copyright_duration = video_copyright_duration;
        this.frame_extract_path = frame_extract_path;
    }

    public String toString(){
        return getUploader_name() + " " + getUpload_time() + "file for " + getVideo_price() + " with " + getVideo_copyright();
    }

    @Id
    @GeneratedValue
    @Column(name = "log_id")
    private int log_id ;

    @Column(name = "vendor_name")
    private String vendor_name;

    @Column(name = "upload_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date upload_time;

    @Column(name = "uploader_name")
    private String uploader_name;

    @Column(name = "xml_upload_path")
    private String xml_upload_path;

    @Column(name = "xml_trans_path")
    private String xml_trans_path;

    @Column(name = "video_play_path")
    private String video_play_path;

    @Column(name = "video_upload_path")
    private String video_upload_path;

    @Column(name = "video_cut_path")
    private String video_cut_path;

    @Column(name = "frame_extract_path")
    private String frame_extract_path;

    @Column(name = "vendor_path")
    private String vendor_path;

    @Column(name = "video_price")
    private double video_price;

    @Column(name = "video_copyright")
    private String video_copyright;

    @Column(name = "video_price_tpye")
    private int video_price_type;

    @Column(name = "video_copyright_duration")
    private int video_copyright_duration;

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public Date getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(Date upload_time) {
        this.upload_time = upload_time;
    }

    public String getUploader_name() {
        return uploader_name;
    }

    public void setUploader_name(String uploader_name) {
        this.uploader_name = uploader_name;
    }

    public String getXml_upload_path() {
        return xml_upload_path;
    }

    public void setXml_upload_path(String xml_upload_path) {
        this.xml_upload_path = xml_upload_path;
    }

    public String getXml_trans_path() {
        return xml_trans_path;
    }

    public void setXml_trans_path(String xml_trans_path) {
        this.xml_trans_path = xml_trans_path;
    }

    public String getVideo_upload_path() {
        return video_upload_path;
    }

    public void setVideo_upload_path(String video_upload_path) {
        this.video_upload_path = video_upload_path;
    }

    public String getVideo_cut_path() {
        return video_cut_path;
    }

    public void setVideo_cut_path(String video_cut_path) {
        this.video_cut_path = video_cut_path;
    }

    public String getFrame_extract_path() {
        return frame_extract_path;
    }

    public void setFrame_extract_path(String frame_extract_path) {
        this.frame_extract_path = frame_extract_path;
    }

    public String getVendor_path() {
        return vendor_path;
    }

    public void setVendor_path(String vendor_path) {
        this.vendor_path = vendor_path;
    }

    public double getVideo_price() {
        return video_price;
    }

    public void setVideo_price(double video_price) {
        this.video_price = video_price;
    }

    public String getVideo_copyright() {
        return video_copyright;
    }

    public void setVideo_copyright(String video_copyright) {
        this.video_copyright = video_copyright;
    }

    public String getVideo_play_path() {
        return video_play_path;
    }

    public void setVideo_play_path(String video_play_path) {
        this.video_play_path = video_play_path;
    }

    public int getVideo_price_type() {
        return video_price_type;
    }

    public void setVideo_price_type(int video_price_type) {
        this.video_price_type = video_price_type;
    }

    public int getVideo_copyright_duration() {
        return video_copyright_duration;
    }

    public void setVideo_copyright_duration(int video_copyright_duration) {
        this.video_copyright_duration = video_copyright_duration;
    }
}
