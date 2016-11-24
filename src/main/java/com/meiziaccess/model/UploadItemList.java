package com.meiziaccess.model;

import java.util.List;

/**
 * Created by user-u1 on 2016/11/23.
 */
public class UploadItemList{
    private List<UploadItem> uploadlist;

    public List<UploadItem> getApList() {
        return uploadlist;
    }

    public void setApList(List<UploadItem> apList) {
        this.uploadlist = apList;
    }
}