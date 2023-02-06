/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.ui.customview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/9/6.
 */
public class ZelasticScrollView extends ScrollView {

    private boolean isCalled = false;
    private ZScrollRefreshData mCallback;
    private View mView;
    private Rect mRect = new Rect();
    private int y = 0;
    private boolean isFirst = true;

    private Rect xRect = new Rect();

    public ZelasticScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ZelasticScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZelasticScrollView(Context context) {
        super(context);
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0)
            mView = getChildAt(0);
        xRect.set(mView.getLeft(), mView.getTop(), mView.getRight(), mView.getBottom());
        super.onFinishInflate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mView != null) {
            commonOnTouch(ev);
        }
        return super.onTouchEvent(ev);
    }


    private int firstCy = 0;
    private boolean isNeedChgView = true;
    private boolean isRefresh = false;

    private void commonOnTouch(MotionEvent ev) {
        int action = ev.getAction();
        int cy = (int) ev.getY();
        if (firstCy == 0) {
            firstCy = cy;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = cy - y;
                if (isFirst) {
                    dy = 0;
                    isFirst = false;
                }
                y = cy;
                if (isNeedMove()) {
                    if (mRect.isEmpty()) {
                        isRefresh = true;
                        mRect.set(mView.getLeft(), mView.getTop(), mView.getRight(), mView.getBottom());
                    }
                    mView.layout(mView.getLeft(), mView.getTop() + 2 * dy / 4, mView.getRight(),
                            mView.getBottom() + 2 * dy / 4);
                    //这里判定 需要刷新header的View
                    if (Math.abs(ev.getY() - firstCy) > 300) {
                        if (isNeedChgView && mCallback != null && !isCalled) {
                            mCallback.changeHeaderView();
                            isNeedChgView = false;
                        }

                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                double temp = ev.getY() - firstCy;
                if (Math.abs(temp) > 300 && isRefresh) {
                    if (temp > 0) {//下拉
                        if (!mRect.isEmpty()) {
                            if ((mCallback != null) && (temp > 450)) {
                                mCallback.downRefresh();
                            }
                            resetPosition();
                        }
                    }

                    if (temp < 0) {//上拉
                        if (!mRect.isEmpty()) {
                            if (mCallback != null) {
                                mCallback.upRefresh();
                            }
                            resetPosition();
                        }
                    }

                } else {
                    if (!mRect.isEmpty()) {
                        resetPosition();
                    }
                    break;
                }
                mRect.setEmpty();
                isFirst = true;
                isCalled = false;
                firstCy = 0;
                isRefresh = false;
                isNeedChgView = true;
                y = 0;


        }
    }

    private boolean shouldCallBack(int dy) {
        if (dy > 0 && mView.getTop() > getHeight() / 2)
            return true;
        return false;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if ((getScrollY() == 0 || getChildAt(0).getMeasuredHeight() <= getScrollY() + getHeight()) && !isRefresh) {
            isFirst = true;
            isCalled = false;
            firstCy = 0;
            isRefresh = false;
            isNeedChgView = true;
            y = 0;
        }
    }

    private void resetPosition() {
        Animation animation = new TranslateAnimation(0, 0, mView.getTop(), mRect.top);
        animation.setDuration(200);
        animation.setFillAfter(true);
        mView.startAnimation(animation);
        mView.layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
        mRect.setEmpty();
        if (mCallback != null) {
            mCallback.finishRefresh();
        }
        isFirst = true;
        isCalled = false;
        firstCy = 0;
        isRefresh = false;
        isNeedChgView = true;
        y = 0;
    }


    public boolean isNeedMove() {
        int offset = mView.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();

        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }

    public void setCallBack(ZScrollRefreshData callback) {
        mCallback = callback;
    }

    public interface ZScrollRefreshData {
        void finishRefresh();

        void downRefresh();

        void upRefresh();

        void changeHeaderView();
    }


}
