/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.view_interface

import com.infinity.jerry.yyd_tms.data.BoManDetial

/**
 * Created by jerry on 2017/6/20.
 */
interface IViewManAll : IViewNetState {

    fun getAllManSucc(manLists: List<BoManDetial>)
    fun deleteManSucc()
    fun remoteManError(type: Int)

    companion object {
        val MAM_GET_ALL_ERROR: Int = 12
        val MAN_DELETE_ERROR: Int = 14
    }
}