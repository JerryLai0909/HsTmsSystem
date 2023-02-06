/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
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

public class TempPage {

    private String pageSize;
    private String page;

    @Override
    public String toString() {
        return "PageCountEntity{" +
                "pageSize='" + pageSize + '\'' +
                ", page='" + page + '\'' +
                '}';
    }

    public TempPage() {
        pageSize = ConstantPool.COMMON_PAGE_SIZE;
        page = "1";
    }

}