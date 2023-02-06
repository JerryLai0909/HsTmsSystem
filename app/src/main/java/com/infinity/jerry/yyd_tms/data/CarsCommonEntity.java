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
 * Created by jerry on 2017/6/18.
 */

public class CarsCommonEntity implements Serializable {

    private Long uuid;
    private Long company_uuid;
    private String company_name;
    private String plate_number;
    private String driver_name;
    private String driver_phone;
    private String create_time;
    private String update_time;
    private boolean is_choosed = false;

    @Override
    public String toString() {
        return "CarsCommonEntity{" +
                "uuid=" + uuid +
                ", company_uuid=" + company_uuid +
                ", company_name='" + company_name + '\'' +
                ", plate_number='" + plate_number + '\'' +
                ", driver_name='" + driver_name + '\'' +
                ", driver_phone='" + driver_phone + '\'' +
                ", create_time='" + create_time + '\'' +
                ", update_time='" + update_time + '\'' +
                ", is_choosed=" + is_choosed +
                '}';
    }

    public boolean isIs_choosed() {
        return is_choosed;
    }

    public void setIs_choosed(boolean is_choosed) {
        this.is_choosed = is_choosed;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getCompany_uuid() {
        return company_uuid;
    }

    public void setCompany_uuid(Long company_uuid) {
        this.company_uuid = company_uuid;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
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

}
