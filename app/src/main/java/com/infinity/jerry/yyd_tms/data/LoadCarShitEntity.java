/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jerry on 2017/6/26.
 */

public class LoadCarShitEntity implements Serializable {

    private List<LoadCarLineEntity> list;

    public List<LoadCarLineEntity> getList() {
        return list;
    }

    public void setList(List<LoadCarLineEntity> list) {
        this.list = list;
    }
}
