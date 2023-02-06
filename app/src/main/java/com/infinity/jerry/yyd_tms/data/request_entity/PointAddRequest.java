/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data.request_entity;

/**
 * Created by jerry on 2017/6/21.
 */

public class PointAddRequest {

    private String partner_company_id;
    private String partner_point_id;

    @Override
    public String toString() {
        return "PointAddRequest{" +
                "partner_company_id='" + partner_company_id + '\'' +
                ", partner_point_id='" + partner_point_id + '\'' +
                '}';
    }

    public String getPartner_company_id() {
        return partner_company_id;
    }

    public void setPartner_company_id(String partner_company_id) {
        this.partner_company_id = partner_company_id;
    }

    public String getPartner_point_id() {
        return partner_point_id;
    }

    public void setPartner_point_id(String partner_point_id) {
        this.partner_point_id = partner_point_id;
    }
}
