/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.customview

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R

/**
 * Created by jerry on 2017/6/7.
 */
class ZTitlebar : RelativeLayout {

    var inflater: LayoutInflater? = null
    var tvTitle: TextView? = null
    var imBack: ImageView? = null
    var tvPlus: TextView? = null
    var imPlus: ImageView? = null

    var textlistener: OnTextModeListener? = null
    var imageListener: OnImageModeListener? = null

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context) {
        inflater = LayoutInflater.from(context)
        var view = inflater!!.inflate(R.layout.z_titlebar, this, true)
        tvTitle = view.findViewById(R.id.tvTitle) as TextView
        imBack = view.findViewById(R.id.imBack) as ImageView
        tvPlus = view.findViewById(R.id.tv_plus) as TextView
        imPlus = view.findViewById(R.id.im_plus) as ImageView

        imBack!!.setOnClickListener({
            (context as Activity).finish()
        })

        tvPlus!!.setOnClickListener({
            textlistener?.onClickTextMode()
        })

        imPlus!!.setOnClickListener({
            imageListener?.onClickImageModel()
        })
        setTitleMode(MODE_NONE)
    }

    fun setTitle(content: String) {
        tvTitle!!.text = content
    }

    fun noBack() {
        imBack!!.visibility = View.GONE
    }

    fun setTitleMode(mode: Int) {
        when (mode) {
            MODE_IMAGE -> {
                imPlus?.visibility = View.VISIBLE
                tvPlus?.visibility = View.GONE
            }
            MODE_TEXT -> {
                tvPlus?.visibility = View.VISIBLE
                imPlus?.visibility = View.GONE
            }
            MODE_NONE -> {
                imPlus?.visibility = View.GONE
                tvPlus?.visibility = View.GONE
            }
        }
    }

    fun setTvPlusText(string: String) {
        tvPlus?.setText(string)
    }

    fun setImPlusDrawable(drawable: Drawable) {
        imPlus?.setImageDrawable(drawable)
    }

    interface OnTextModeListener {
        fun onClickTextMode()
    }

    interface OnImageModeListener {
        fun onClickImageModel()
    }

    fun setOnTextModeListener(listener: OnTextModeListener) {
        textlistener = listener
    }

    fun setOnImageModeListener(listener: OnImageModeListener) {
        imageListener = listener
    }

    companion object {
        val MODE_IMAGE: Int = 200
        val MODE_TEXT: Int = 100
        val MODE_NONE: Int = 150
    }


}