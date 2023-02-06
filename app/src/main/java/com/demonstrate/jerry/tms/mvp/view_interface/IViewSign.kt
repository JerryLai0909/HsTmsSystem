/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.view_interface

import com.infinity.jerry.yyd_tms.data.MyBillListEntity

/**
 * Created by jerry on 2017/6/26.
 */
interface IViewSign : IViewNetState {


    fun getSignBillsSucc(lists: MyBillListEntity)

    fun ensureSignBillsSucc()

    fun remoteSignError(type: Int)

    companion object {
        val SIGN_BILLS_GET_ERROR = 14
        val SIGN_BILLS_ENSURE_ERROR = 16
    }
}