/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

import com.infinity.jerry.yyd_tms.constant.ConstantPool;

/**
 * Created by jerry on 2017/6/21.
 */

public class PageCountEntity {

    private String pageSize;
    private String page;
    private String invoice_status = "3";

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

    public String getInvoice_status() {
        return invoice_status;
    }

    public void setInvoice_status(String invoice_status) {
        this.invoice_status = invoice_status;
    }

    @Override
    public String toString() {
        return "PageCountEntity{" +
                "pageSize='" + pageSize + '\'' +
                ", page='" + page + '\'' +
                ", invoice_status='" + invoice_status + '\'' +
                '}';
    }

    public PageCountEntity() {
        pageSize = ConstantPool.COMMON_PAGE_SIZE;
        page = "1";
    }

}
