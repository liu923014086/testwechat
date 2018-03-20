package com.wechat.entity.po;

/**
 * Created by liudaxia on 2018/3/20.
 */
public class WeddingZanLog {
    private String face;//头像图片地址

    public WeddingZanLog() {
    }

    public WeddingZanLog(String face) {
        this.face = face;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }
}
