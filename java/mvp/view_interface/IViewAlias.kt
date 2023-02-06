/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.view_interface

import com.infinity.jerry.yyd_tms.data.AliasEntity

/**
 * Created by jerry on 2017/6/18.
 */
interface IViewAlias {

    fun getAliasSucc(alias: List<AliasEntity>)

    fun aliasEditError()

    companion object {
        val ALIAS_GET_ERROR = 22
        val ALIAS_EDIT_ERROR = 23
        val ALIAS_ADD_ERROR = 24
        val ALIAS_DELETE_ERROR = 25
    }
}