/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data.request_entity;

/**
 * Created by jerry on 2017/6/26.
 */

public class LoadCarReuqest {

    private String plate_number;
    private String driver_name;
    private String driver_phone;
    private String uuid;
    private Long loading_batches_id;

    public Long getLoading_batches_id() {
        return loading_batches_id;
    }

    public void setLoading_batches_id(Long loading_batches_id) {
        this.loading_batches_id = loading_batches_id;
    }

    public String getPlate_number() {
        return plate_number;
    }

    public void setPlate_number(String plate_number) {
        this.plate_number = plate_number;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_phone() {
        return driver_phone;
    }

    public void setDriver_phone(String driver_phone) {
        this.driver_phone = driver_phone;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    @Override
    public String toString() {
        return "LoadCarReuqest{" +
                "plate_number='" + plate_number + '\'' +
                ", driver_name='" + driver_name + '\'' +
                ", driver_phone='" + driver_phone + '\'' +
                ", uuid='" + uuid + '\'' +
                ", loading_batches_id=" + loading_batches_id +
                '}';
    }
}
