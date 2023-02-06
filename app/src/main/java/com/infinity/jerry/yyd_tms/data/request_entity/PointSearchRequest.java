/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data.request_entity;

import com.infinity.jerry.yyd_tms.constant.ConstantPool;

/**
 * Created by jerry on 2017/6/21.
 */

public class PointSearchRequest {
    private String pageSize;
    private String page;
    private String uuid;

    public PointSearchRequest() {
        pageSize = ConstantPool.COMMON_PAGE_SIZE;
        page = "1";
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "PointSearchRequest{" +
                "pageSize='" + pageSize + '\'' +
                ", page='" + page + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
