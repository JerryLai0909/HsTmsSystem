/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data.request_entity;

/**
 * Created by jerry on 2017/7/10.
 */

public class CooperRequest {

    private Long uuid;
    private Integer partner_status;

    @Override
    public String toString() {
        return "CooperRequest{" +
                "uuid=" + uuid +
                ", partner_status=" + partner_status +
                '}';
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Integer getPartner_status() {
        return partner_status;
    }

    public void setPartner_status(Integer partner_status) {
        this.partner_status = partner_status;
    }
}
