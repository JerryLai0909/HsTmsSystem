/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.view_interface

/**
 * Created by jerry on 2017/6/19.
 */
interface IViewMine {

    fun updatePwdSucc()
    fun loginOutSucc()

    fun settingError(type: Int)


    companion object {
        val MINE_UPDATEPWD_ERROR = 32
        val MINE_QUITLOGIN = 34
    }

}