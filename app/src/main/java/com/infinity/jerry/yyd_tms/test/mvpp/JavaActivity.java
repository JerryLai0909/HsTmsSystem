/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.test.mvpp;

/**
 * Created by jerry on 2017/7/18.
 * 这个是一个假的activity
 */

public class JavaActivity implements IViewLogin {



    /**
     * activity 作为 mvp的 v层，也就是view层
     * model 作为 m层 叫做业务层，逻辑层。
     * presenter 是 p层，叫做主持层
     *
     *
     * presenter 里面有model的对象，里面有new xxModel（）
     * 也有一个IViewLogin 的对象。IView是一个接口 不能new ，实现这个接口的是 这个Activity
     *
     * presenter 充当一个主持人，来负责m 和v的调用。
     *
     *
     */

    private LoginPresenter presenter ;

    public void onCreate(String name) {
        //xxxxxxx
        presenter = new LoginPresenter(this);//这是的this 就是只带这个Activity 因为他实现了IViewLogin这个接口。

        //登录
        presenter.loginApp("用户名","密码");

        //当把这层关系 也就是 presenter iView  model 建立好。
        //这理发送请求。中间什么都不用管
        //成功就会执行 下面的 loginSucc 方法 失败就loginError方法。

    }


    @Override
    public void loginSucc(LoginEntity entity) {//这个是请求成功的时候执行的方法
        //entity 这是就得到了请求的数据 并且通过框架自动转换成了对象 需要的时候直接调用就行了
        String email = entity.getData().getEmail();
        //这样就可以得到 email  可以 textView.setText(email)之类的了。
    }

    @Override
    public void loginError() {//这个是失败的时候执行的方法
        //失败的处理就根据自己的需求来，比如打一个Toast 提示用户。或者其他的都可以
    }
}
