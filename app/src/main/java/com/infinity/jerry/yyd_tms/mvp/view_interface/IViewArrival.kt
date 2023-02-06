/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.view_interface

import com.infinity.jerry.yyd_tms.data.MyBillListEntity
import com.infinity.jerry.yyd_tms.data.NewArrivalEntity

/**
 * Created by jerry on 2017/6/26.
 */
interface IViewArrival : IViewNetState {

    fun getArrivalBillsSucc(lists: MyBillListEntity)
    fun ensureArrivalSucc()
    fun getNewArrivalSucc(entities: NewArrivalEntity)

    fun remoteArrivalError(type: Int)

    companion object {
        val ARRIVAL_BILLS_ERROR = 12
        val ARRIVAL_BILLS_ENSURE_ERROR = 14
    }
}