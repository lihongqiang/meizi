package com.meiziaccess.model;

/**
 * Created by user-u1 on 2016/4/14.
 */
//获取Post过来的订单数据
public class Order {

    private Long uuid;

    private boolean isEntire;

    private Long starttime;

    private Long endtime;

    private String highdef_video_path;

    public Order() {super();}

    public Order(Long uuid, boolean isEntire, Long starttime, Long endtime, String highdef_video_path) {
        super();
        this.uuid = uuid;
        this.isEntire = isEntire;
        this.starttime = starttime;
        this.endtime = endtime;
        this.highdef_video_path = highdef_video_path;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public boolean isEntire() {
        return isEntire;
    }

    public void setEntire(boolean entire) {
        isEntire = entire;
    }

    public Long getStarttime() {
        return starttime;
    }

    public void setStarttime(Long starttime) {
        this.starttime = starttime;
    }

    public Long getEndtime() {
        return endtime;
    }

    public void setEndtime(Long endtime) {
        this.endtime = endtime;
    }

    public String getHighdef_video_path() {
        return highdef_video_path;
    }

    public void setHighdef_video_path(String highdef_video_path) {
        this.highdef_video_path = highdef_video_path;
    }
}
