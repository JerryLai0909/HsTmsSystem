/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

/**
 * Created by jerry on 2017/7/28.
 */

public class RateEntity {
    private Long logistics_uuid;
    private Double Insured_rate;
    private Double commission_rate;

    @Override
    public String toString() {
        return "RateEntity{" +
                "logistics_uuid=" + logistics_uuid +
                ", Insured_rate=" + Insured_rate +
                ", commission_rate=" + commission_rate +
                '}';
    }

    public Long getLogistics_uuid() {
        return logistics_uuid;
    }

    public void setLogistics_uuid(Long logistics_uuid) {
        this.logistics_uuid = logistics_uuid;
    }

    public Double getInsured_rate() {
        return Insured_rate;
    }

    public void setInsured_rate(Double insured_rate) {
        Insured_rate = insured_rate;
    }

    public Double getCommission_rate() {
        return commission_rate;
    }

    public void setCommission_rate(Double commission_rate) {
        this.commission_rate = commission_rate;
    }
}
