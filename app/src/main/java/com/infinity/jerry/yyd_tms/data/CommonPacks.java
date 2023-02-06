/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

import org.litepal.crud.DataSupport;

/**
 * Created by jerry on 2017/6/15.
 */

public class CommonPacks extends DataSupport {

    private String packing_name;

    public String getPacking_name() {
        return packing_name;
    }

    public void setPacking_name(String packing_name) {
        this.packing_name = packing_name;
    }

    @Override
    public String toString() {
        return "CommonPacks{" +
                "packing_name='" + packing_name + '\'' +
                '}';
    }
}
