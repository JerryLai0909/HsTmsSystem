/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

/**
 * Created by jerry on 2017/6/20.
 */

public class BoPointDetail {

    private Long uuid;
    private Long logistics_uuid;
    private Long market_uuid;
    private String point_name;
    private String point_addr;
    private String point_phone;
    private String point_owner;
    private String point_owner_phone_o;
    private String point_owner_phone_t;
    private Long point_longitude;
    private Long point_latitude;
    private Integer point_status;
    private String create_time;
    private String point_code;


    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getLogistics_uuid() {
        return logistics_uuid;
    }

    public void setLogistics_uuid(Long logistics_uuid) {
        this.logistics_uuid = logistics_uuid;
    }

    public Long getMarket_uuid() {
        return market_uuid;
    }

    public void setMarket_uuid(Long market_uuid) {
        this.market_uuid = market_uuid;
    }

    public String getPoint_name() {
        return point_name;
    }

    public void setPoint_name(String point_name) {
        this.point_name = point_name;
    }

    public String getPoint_addr() {
        return point_addr;
    }

    public void setPoint_addr(String point_addr) {
        this.point_addr = point_addr;
    }

    public String getPoint_phone() {
        return point_phone;
    }

    public void setPoint_phone(String point_phone) {
        this.point_phone = point_phone;
    }

    public String getPoint_owner() {
        return point_owner;
    }

    public void setPoint_owner(String point_owner) {
        this.point_owner = point_owner;
    }

    public String getPoint_owner_phone_o() {
        return point_owner_phone_o;
    }

    public void setPoint_owner_phone_o(String point_owner_phone_o) {
        this.point_owner_phone_o = point_owner_phone_o;
    }

    public String getPoint_owner_phone_t() {
        return point_owner_phone_t;
    }

    public void setPoint_owner_phone_t(String point_owner_phone_t) {
        this.point_owner_phone_t = point_owner_phone_t;
    }

    public Long getPoint_longitude() {
        return point_longitude;
    }

    public void setPoint_longitude(Long point_longitude) {
        this.point_longitude = point_longitude;
    }

    public Long getPoint_latitude() {
        return point_latitude;
    }

    public void setPoint_latitude(Long point_latitude) {
        this.point_latitude = point_latitude;
    }

    public Integer getPoint_status() {
        return point_status;
    }

    public void setPoint_status(Integer point_status) {
        this.point_status = point_status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPoint_code() {
        return point_code;
    }

    public void setPoint_code(String point_code) {
        this.point_code = point_code;
    }

    @Override
    public String toString() {
        return "PointDetailEntity{" +
                "uuid=" + uuid +
                ", logistics_uuid=" + logistics_uuid +
                ", market_uuid=" + market_uuid +
                ", point_name='" + point_name + '\'' +
                ", point_addr='" + point_addr + '\'' +
                ", point_phone='" + point_phone + '\'' +
                ", point_owner='" + point_owner + '\'' +
                ", point_owner_phone_o='" + point_owner_phone_o + '\'' +
                ", point_owner_phone_t='" + point_owner_phone_t + '\'' +
                ", point_longitude=" + point_longitude +
                ", point_latitude=" + point_latitude +
                ", point_status=" + point_status +
                ", create_time=" + create_time +
                ", point_code='" + point_code + '\'' +
                '}';
    }
}
