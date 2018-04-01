package com.wechat.entity.po;

/**
 * Created by liudaxia on 2018/3/21.
 */
public class Chat {

    private String face;//微信头像
    private String nickname;//
    private String time;//评论时间
    private String words;//评论内容


    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}
