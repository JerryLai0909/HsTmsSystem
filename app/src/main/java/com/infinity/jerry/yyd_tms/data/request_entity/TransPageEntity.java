/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data.request_entity;

import com.infinity.jerry.yyd_tms.data.TransEntity;

import java.util.List;

/**
 * Created by jerry on 2017/7/24.
 */

public class TransPageEntity {

    private PagebeanBean pagebean;
    private List<TransEntity> pageList;

    public PagebeanBean getPagebean() {
        return pagebean;
    }

    public void setPagebean(PagebeanBean pagebean) {
        this.pagebean = pagebean;
    }

    public List<TransEntity> getPageList() {
        return pageList;
    }

    public void setPageList(List<TransEntity> pageList) {
        this.pageList = pageList;
    }

    public static class PagebeanBean {
        private int curPage;
        private int pageCount;
        private int rowsCount;
        private int pageSize;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getRowsCount() {
            return rowsCount;
        }

        public void setRowsCount(int rowsCount) {
            this.rowsCount = rowsCount;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }
    }

}
