/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.infinity.jerry.yyd_tms.ui.customview

import android.content.Context
import android.widget.LinearLayout

/**
 * Created by jerry on 2017/6/11.
 */
class ZDrawEditText : LinearLayout {

    //    var inflater: LayoutInflater? = null
//    var tvTitle: TextView? = null
//    var tvContent: AutoCompleteTextView? = null
//    var tvChoose:TextView?= null
//
//    var textTitle:String = "描述"
//    var textHint:String? = ""
//    var hasChoose :Boolean = false
//    var tvChooseContent :String? =null
//    var textTitleColor :Int? =null
//    //1 red  2 black
//
    constructor(context: Context) : super(context) {
    }
//
//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
//        var typedArray = context.obtainStyledAttributes(attrs,R.styleable.ZDrawEditText)
//        textTitle = typedArray.getString(R.styleable.ZDrawEditText_titleText)
//        textHint = typedArray.getString(R.styleable.ZDrawEditText_contentHint)
//        hasChoose = typedArray.getBoolean(R.styleable.ZDrawEditText_chooseItem,false)
//        tvChooseContent = typedArray.getString(R.styleable.ZDrawEditText_chooseContent)
//        textTitleColor = typedArray.getColor(R.styleable.ZDrawEditText_mTitleTextColor,2)
//        initView(context)
//    }
//
//    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
//        initView(context)
//    }
//
//    private fun initView(context: Context) {
//        inflater = LayoutInflater.from(context)
//        var view = inflater!!.inflate(R.layout.z_draw_edit, this, true)
//        tvTitle = view.findViewById(R.id.z_tv_title) as TextView
//        tvContent = view.findViewById(R.id.z_auto_content) as AutoCompleteTextView
//        tvChoose = view.findViewById(R.id.z_auto_choose) as TextView
//        if (!hasChoose) {
//            tvChoose!!.visibility = View.GONE
//        }else{
//            tvChoose!!.visibility = View.VISIBLE
//        }
//        tvTitle!!.setText(textTitle)
//        when (textTitleColor) {
//            1 -> tvTitle!!.setTextColor(ContextCompat.getColor(context,R.color.gColor_red))
//            2 -> tvTitle!!.setTextColor(ContextCompat.getColor(context,R.color.color_black))
//        }
//        if (textHint != null) {
//            tvContent!!.hint = textHint
//        }
//        if (tvChooseContent != null) {
//            tvChoose!!.setText(tvChooseContent)
//        }
//
//    }
//
//    fun setContentText(content: String) {
//        tvContent!!.setText(content)
//    }
//
//    fun getContentText(): String {
//        return tvContent!!.text.toString()
//    }
//
//    fun setContentHint(hint: String) {
//        tvContent!!.hint = hint
//    }
//
//    fun setChooseWhere(listener :OnChooseItemClickListener) {
//        listener.doItemClick()
//    }
//
//    interface OnChooseItemClickListener{
//        fun doItemClick()
//    }
//
//    fun setTitleText(titleText : String){
//        tvTitle!!.setText(titleText)
//    }


}