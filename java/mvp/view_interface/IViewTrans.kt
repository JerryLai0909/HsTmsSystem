/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.view_interface

import com.infinity.jerry.yyd_tms.data.request_entity.TransPageEntity

/**
 * Created by jerry on 2017/7/24.
 */

interface IViewTrans :IViewNetState{

    fun addTransSucc()
    fun searchTransSucc(entity : TransPageEntity)
    fun transError(type :Int)

    companion object {
        val TRANS_ADD_ERROR = 1
        val TRANS_GET_ERRLR = 2
    }

}