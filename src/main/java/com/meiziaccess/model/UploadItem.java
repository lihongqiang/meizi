package com.meiziaccess.model;

import javax.persistence.*;

/**
 * Created by user-u1 on 2016/11/18.
 */

@Entity
@Table(name = "upload_item")
public class UploadItem {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "upload")
    boolean upload;

    @Column(name = "title")
    String title;

    @Column(name = "upload_time")
    String upload_time;

    @Column(name = "duration")
    int duration;

    @Column(name = "price_type")
    int price_type;     //0:单价  1:一口价

    @Column(name = "price")
    double price;

    @Column(name = "copyright_type")
    int copyright_type;     //0:RF  1:RM

    @Column(name = "copyright_duration")
    int copyright_duration;   //0:6个月  1:一年  2:两年    3:三年   4::五年   5:长期

    @Column(name = "md5")
    String md5;

    @Column(name = "path")
    String path;

    public UploadItem() {
        this.upload = true;
        this.title = "";
        this.upload_time = "";
        this.duration = 0;
        this.price = 0;
        this.price = 0;
        this.copyright_type = 0;
        this.copyright_duration = 0;
        this.md5 = "";
        this.path = "";
    }

    public UploadItem(String title, String md5, String path) {
        this.title = title;
        this.md5 = md5;
        this.path = path;

        this.upload = true;
        this.upload_time = "";
        this.duration = 0;
        this.price = 0;
        this.price = 0;
        this.copyright_type = 0;
        this.copyright_duration = 0;
    }



    public UploadItem(boolean upload, String title, String upload_time, int duration, int price_type, double price,
                      int copyright_type, int copyright_duration, String md5, String path) {
        this.upload = upload;
        this.title = title;
        this.upload_time = upload_time;
        this.duration = duration;
        this.price_type = price_type;
        this.price = price;
        this.copyright_type = copyright_type;
        this.copyright_duration = copyright_duration;
        this.md5 = md5;
        this.path = path;
    }

    public boolean getUpload() {
        return upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPrice_type() {
        return price_type;
    }

    public void setPrice_type(int price_type) {
        this.price_type = price_type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCopyright_type() {
        return copyright_type;
    }

    public void setCopyright_type(int copyright_type) {
        this.copyright_type = copyright_type;
    }

    public int getCopyright_duration() {
        return copyright_duration;
    }

    public void setCopyright_duration(int copyright_duration) {
        this.copyright_duration = copyright_duration;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
