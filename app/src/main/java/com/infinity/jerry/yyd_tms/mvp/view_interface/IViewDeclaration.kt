/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.view_interface

import com.infinity.jerry.yyd_tms.data.Declaration

/**
 * Created by jerry on 2017/6/15.
 */
interface IViewDeclaration : IViewNetState {

    fun getDeclarsSucc(declas: List<Declaration>)
    fun editDeclarSucc()
    fun deleDeclarSucc()
    fun remoteDelarsError(type: Int)

    companion object {
        val DECLARS_GET_ERROR: Int = 32
        val DECLARS_DELETE_ERROR: Int = 64
        val DECLARS_EDIT_ERROR: Int = 128
    }

}