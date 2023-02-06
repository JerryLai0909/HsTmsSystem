/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

import java.io.Serializable;

/**
 * Created by jerry on 2017/6/15.
 */

public class Declaration implements Serializable {

    Long uuid;
    Long logistics_uuid;
    Integer logistics_sort;
    String logistics_notice;
    String create_time;
    String update_time;

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

    public Integer getLogistics_sort() {
        return logistics_sort;
    }

    public void setLogistics_sort(Integer logistics_sort) {
        this.logistics_sort = logistics_sort;
    }

    public String getLogistics_notice() {
        return logistics_notice;
    }

    public void setLogistics_notice(String logistics_notice) {
        this.logistics_notice = logistics_notice;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "Declaration{" +
                "uuid=" + uuid +
                ", logistics_uuid=" + logistics_uuid +
                ", logistics_sort=" + logistics_sort +
                ", logistics_notice='" + logistics_notice + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }
}
