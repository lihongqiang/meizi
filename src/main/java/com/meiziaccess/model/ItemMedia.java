package com.meiziaccess.model;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.*;

/**
 * Created by user-u1 on 2016/4/14.
 */

//获取Post过来的订单数据
@Entity
@Table(name = "item_media")
public class ItemMedia {

    @Id
    @GeneratedValue
    private int id;

    //订单项ID
    @Column(name = "uuid")
    private int uuid;

    @Column(name = "isEntire")
    private Boolean isEntire;

    @Column(name = "starttime")
    private int starttime;

    @Column(name = "endtime")
    private int endtime;

    @Column(name = "highdef_video_path")
    private String highdef_video_path;

    @Column(name = "format")
    private String format;

    @Column(name = "status")
    private int status;

    @Column(name = "url")
    private String url;

    public ItemMedia() {super();}

    public ItemMedia(int uuid, Boolean isEntire, int starttime, int endtime, String highdef_video_path, String format, int status, String url) {
        super();
        this.uuid = uuid;
        this.isEntire = isEntire;
        this.starttime = starttime;
        this.endtime = endtime;
        this.highdef_video_path = highdef_video_path;
        this.format = format;
        this.status = status;
        this.url = url;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public Boolean getIsEntire() {
        return isEntire;
    }

    public void setIsEntire(Boolean isEntire) {
        this.isEntire = isEntire;
    }

    public int getStarttime() {
        return starttime;
    }

    public void setStarttime(int starttime) {
        this.starttime = starttime;
    }

    public int getEndtime() {
        return endtime;
    }

    public void setEndtime(int endtime) {
        this.endtime = endtime;
    }

    public String getHighdef_video_path() {
        return highdef_video_path;
    }

    public void setHighdef_video_path(String highdef_video_path) {
        this.highdef_video_path = highdef_video_path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
