/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data.new_tms_entity;

import java.util.List;

/**
 * Created by jerry on 2018/9/26.
 * You are good enough to do everthing
 */
public class NewListWrap {
    private boolean last;
    private int totalPages;
    private int totalElements;
    private int numberOfElements;
    private boolean first;
    private String sort;
    private int size;
    private int number;
    private List<NewBillEntity> content;

    @Override
    public String toString() {
        return "NewListWrap{" +
                "last=" + last +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", numberOfElements=" + numberOfElements +
                ", first=" + first +
                ", sort='" + sort + '\'' +
                ", size=" + size +
                ", number=" + number +
                ", content=" + content +
                '}';
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<NewBillEntity> getContent() {
        return content;
    }

    public void setContent(List<NewBillEntity> content) {
        this.content = content;
    }
}
