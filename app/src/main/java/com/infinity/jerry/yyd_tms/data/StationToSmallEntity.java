/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2019/3/24.
 * You are good enough to do everthing
 */
public class StationToSmallEntity extends DataSupport {

    private long id;
    private long stationEditEntityId;
    private long smallId;
    private String name;
    private long logistics_uuid;
    private int sortCount = 0;

    public int getSortCount() {
        return sortCount;
    }

    public void setSortCount(int sortCount) {
        this.sortCount = sortCount;
    }

    public long getLogistics_uuid() {
        return logistics_uuid;
    }

    public void setLogistics_uuid(long logistics_uuid) {
        this.logistics_uuid = logistics_uuid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStationEditEntityId() {
        return stationEditEntityId;
    }

    public void setStationEditEntityId(long stationEditEntityId) {
        this.stationEditEntityId = stationEditEntityId;
    }

    public long getSmallId() {
        return smallId;
    }

    public void setSmallId(long smallId) {
        this.smallId = smallId;
    }

    public String getName() {
        return name;
    }


    @Override
    public String
    toString() {
        return "StationToSmallEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", logistics_uuid=" + logistics_uuid +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

}
