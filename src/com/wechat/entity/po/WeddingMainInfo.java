package com.wechat.entity.po;

/**
 * Created by liudaxia on 2018/3/20.
 * 邀请信息
 */
public class WeddingMainInfo {

    private String he;
    private String she;
    private String date;
    private String lunar;//农历
    private String hotel;
    private String address;
    private String he_tel;
    private String she_tel;

    private Float lat;//纬度
    private Float lng;//经度


    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public String getHe() {
        return he;
    }

    public void setHe(String he) {
        this.he = he;
    }

    public String getShe() {
        return she;
    }

    public void setShe(String she) {
        this.she = she;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLunar() {
        return lunar;
    }

    public void setLunar(String lunar) {
        this.lunar = lunar;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHe_tel() {
        return he_tel;
    }

    public void setHe_tel(String he_tel) {
        this.he_tel = he_tel;
    }

    public String getShe_tel() {
        return she_tel;
    }

    public void setShe_tel(String she_tel) {
        this.she_tel = she_tel;
    }
}
