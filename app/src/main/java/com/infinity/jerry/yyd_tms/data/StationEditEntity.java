/*
 * Copyright (c) 2019. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 2019/3/24.
 * You are good enough to do everthing
 */
public class StationEditEntity extends DataSupport {

    private long id;
    private String name;
    private int sort = 0;
    private List<StationToSmallEntity> smallEntityList = new ArrayList<>();
    private boolean isShow = true;
    private boolean isLast = false;

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<StationToSmallEntity> getSmallEntityList() {
        return smallEntityList;
    }

    public void setSmallEntityList(List<StationToSmallEntity> smallEntityList) {
        this.smallEntityList = smallEntityList;
    }

    @Override
    public String toString() {
        return "StationEditEntity{" +
                ", name='" + name + '\'' +
                ", smallEntityList=" + smallEntityList +
                ", isShow=" + isShow +
                ", isLast=" + isLast +
                '}';
    }
}
