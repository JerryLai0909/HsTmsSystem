/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.infinity.jerry.dearmacfirst.R

/**
 * Created by jerry on 2017/6/11.
 */
class ZBossItemView : LinearLayout {

    var inflater: LayoutInflater? = null
    var imPicture: ImageView? = null
    var tvContent: TextView? = null
    var picReference: Int? = null
    var tvSomething: String = ""

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        var typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZBossItemView)
        picReference = typedArray.getResourceId(R.styleable.ZBossItemView_image_reference, R.mipmap.ic_launcher)
        tvSomething = typedArray.getString(R.styleable.ZBossItemView_text_content)
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }


    private fun initView(context: Context) {
        inflater = LayoutInflater.from(context)
        var view = inflater!!.inflate(R.layout.z_boss_itemview, this, true)
        imPicture = view.findViewById(R.id.zBoss_imageView) as ImageView
        tvContent = view.findViewById(R.id.zBoss_textView) as TextView

        imPicture!!.setImageDrawable(resources.getDrawable(picReference!!))
        tvContent!!.setText(tvSomething)
    }

    fun setTitlePic(pic: Drawable) {
        imPicture!!.setImageDrawable(pic)
    }

    fun setTvContent(content: String) {
        tvContent!!.setText(content)
    }

}