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
 * Created by jerry on 2017/6/20.
 */

public class BoManDetial implements Serializable {
    private Long uuid;
    private String user_name;
    private String phone_one;
    private Long company_uuid;

    public Long getCompany_uuid() {
        return company_uuid;
    }

    public void setCompany_uuid(Long company_uuid) {
        this.company_uuid = company_uuid;
    }

    @Override
    public String toString() {
        return "BoManDetial{" +
                "uuid=" + uuid +
                ", user_name='" + user_name + '\'' +
                ", phone_one='" + phone_one + '\'' +
                ", company_uuid=" + company_uuid +
                '}';
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone_one() {
        return phone_one;
    }

    public void setPhone_one(String phone_one) {
        this.phone_one = phone_one;
    }
}
