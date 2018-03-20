package com.wechat.entity.po;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liudaxia on 2018/3/20.
 */
public class Wedding {
    /**
     * music_url
     * mainInfo
     * image
     */

    private String music_url;
    private WeddingMainInfo mainInfo;
    private List<WeddingImage> slideList = new ArrayList<WeddingImage>();

    private List<WeddingZanLog> zanLog = new ArrayList<WeddingZanLog>();
    private Integer zanNum;
    private String msg;



    public List<WeddingZanLog> getZanLog() {
        return zanLog;
    }

    public void setZanLog(List<WeddingZanLog> zanLog) {
        this.zanLog = zanLog;
    }

    public Integer getZanNum() {
        return zanNum;
    }

    public void setZanNum(Integer zanNum) {
        this.zanNum = zanNum;
    }

    public String getMusic_url() {
        return music_url;
    }

    public void setMusic_url(String music_url) {
        this.music_url = music_url;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public WeddingMainInfo getMainInfo() {
        return mainInfo;
    }

    public void setMainInfo(WeddingMainInfo mainInfo) {
        this.mainInfo = mainInfo;
    }

    public List<WeddingImage> getSlideList() {
        return slideList;
    }

    public void setSlideList(List<WeddingImage> slideList) {
        this.slideList = slideList;
    }
}
