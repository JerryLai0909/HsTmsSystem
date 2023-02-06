/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data.request_entity;

import java.util.List;

/**
 * Created by jerry on 2017/7/25.
 */

public class CooperListRequest {

    private List<PointAddRequest> lists ;

    public List<PointAddRequest> getLists() {
        return lists;
    }

    public void setLists(List<PointAddRequest> lists) {
        this.lists = lists;
    }
}
