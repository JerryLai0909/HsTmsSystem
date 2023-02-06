/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.view_interface

import com.infinity.jerry.yyd_tms.data.ErrorBillingEntity

/**
 * Created by jerry on 2017/7/17.
 */
interface IViewErrorRemote : IViewNetState {

    fun addErorrSucc()
    fun searchErrorSucc(entity: ErrorBillingEntity)
    fun errorRemoteError(type: Int)

    companion object {
        val ERROR_EDIT_ERROR = 1
        val ERROR_SEARCH_ERROR = 2
    }
}