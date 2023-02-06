/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.view_interface

import com.infinity.jerry.yyd_tms.data.BoPointDetail

/**
 * Created by jerry on 2017/6/20.
 */
interface IViewPointEdit {

    fun getDetailSucc(entity: BoPointDetail)
    fun remoteSucc()
    fun remoteError(type: Int)


    companion object {
        val POINT_DETAIL_ERROR: Int = 21
        val POINT_REMOTE_ERROR: Int = 22
    }
}