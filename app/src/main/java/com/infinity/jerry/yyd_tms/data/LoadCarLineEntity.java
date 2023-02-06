/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by jerry on 2017/6/26.
 */

public class LoadCarLineEntity implements Parcelable, Serializable {

    private boolean isChoose = false;
    private boolean isHeader = false;
    private boolean isOpen = false;
    private Long uuid;
    private Long initial_station_id;
    private String initial_station_name;
    private Long terminal_station_id;
    private String terminal_station_name;
    private String transit_destination;
    private String goods_name;
    private String packaging;
    private Long quantity;
    private String waybill_number;
    private String invoice_date;
    private String article_number;

    private int piao ;
    private int jian;

    public int getPiao() {
        return piao;
    }

    public void setPiao(int piao) {
        this.piao = piao;
    }

    public int getJian() {

        return jian;
    }

    public void setJian(int jian) {
        this.jian = jian;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getArticle_number() {
        return article_number;
    }

    public void setArticle_number(String article_number) {
        this.article_number = article_number;
    }

    private boolean isHeaderAll = false;

    public boolean isHeaderAll() {
        return isHeaderAll;
    }

    public void setHeaderAll(boolean headerAll) {
        isHeaderAll = headerAll;
    }

    public static Creator<LoadCarLineEntity> getCREATOR() {
        return CREATOR;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }


    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getInitial_station_id() {
        return initial_station_id;
    }

    public void setInitial_station_id(Long initial_station_id) {
        this.initial_station_id = initial_station_id;
    }

    public String getInitial_station_name() {
        return initial_station_name;
    }

    public void setInitial_station_name(String initial_station_name) {
        this.initial_station_name = initial_station_name;
    }

    public Long getTerminal_station_id() {
        return terminal_station_id;
    }

    public void setTerminal_station_id(Long terminal_station_id) {
        this.terminal_station_id = terminal_station_id;
    }

    public String getTerminal_station_name() {
        return terminal_station_name;
    }

    public void setTerminal_station_name(String terminal_station_name) {
        this.terminal_station_name = terminal_station_name;
    }

    public String getTransit_destination() {
        return transit_destination;
    }

    public void setTransit_destination(String transit_destination) {
        this.transit_destination = transit_destination;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getWaybill_number() {
        return waybill_number;
    }

    public void setWaybill_number(String waybill_number) {
        this.waybill_number = waybill_number;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }

    @Override
    public String toString() {
        return "LoadCarLineEntity{" +
                "isChoose=" + isChoose +
                ", isHeader=" + isHeader +
                ", uuid=" + uuid +
                ", initial_station_id=" + initial_station_id +
                ", initial_station_name='" + initial_station_name + '\'' +
                ", terminal_station_id=" + terminal_station_id +
                ", terminal_station_name='" + terminal_station_name + '\'' +
                ", transit_destination='" + transit_destination + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", packaging='" + packaging + '\'' +
                ", quantity=" + quantity +
                ", waybill_number='" + waybill_number + '\'' +
                ", invoice_date='" + invoice_date + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isChoose ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isHeader ? (byte) 1 : (byte) 0);
        dest.writeValue(this.uuid);
        dest.writeValue(this.initial_station_id);
        dest.writeString(this.initial_station_name);
        dest.writeValue(this.terminal_station_id);
        dest.writeString(this.terminal_station_name);
        dest.writeString(this.transit_destination);
        dest.writeString(this.goods_name);
        dest.writeString(this.packaging);
        dest.writeValue(this.quantity);
        dest.writeString(this.waybill_number);
        dest.writeString(this.invoice_date);
    }

    public LoadCarLineEntity() {
    }

    protected LoadCarLineEntity(Parcel in) {
        this.isChoose = in.readByte() != 0;
        this.isHeader = in.readByte() != 0;
        this.uuid = (Long) in.readValue(Long.class.getClassLoader());
        this.initial_station_id = (Long) in.readValue(Long.class.getClassLoader());
        this.initial_station_name = in.readString();
        this.terminal_station_id = (Long) in.readValue(Long.class.getClassLoader());
        this.terminal_station_name = in.readString();
        this.transit_destination = in.readString();
        this.goods_name = in.readString();
        this.packaging = in.readString();
        this.quantity = (Long) in.readValue(Long.class.getClassLoader());
        this.waybill_number = in.readString();
        this.invoice_date = in.readString();
    }

    public static final Parcelable.Creator<LoadCarLineEntity> CREATOR = new Parcelable.Creator<LoadCarLineEntity>() {
        @Override
        public LoadCarLineEntity createFromParcel(Parcel source) {
            return new LoadCarLineEntity(source);
        }

        @Override
        public LoadCarLineEntity[] newArray(int size) {
            return new LoadCarLineEntity[size];
        }
    };
}
