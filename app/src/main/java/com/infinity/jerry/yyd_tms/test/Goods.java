/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.test;

import java.util.List;

/**
 * Created by jerry on 2017/7/12.
 */

public class Goods {

    private String name;
    private List<Property> lists;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Property> getLists() {
        return lists;
    }

    public void setLists(List<Property> lists) {
        this.lists = lists;
    }

    public static class Property {
        private String id;
        private String key;
        private String vlaue;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getVlaue() {
            return vlaue;
        }

        public void setVlaue(String vlaue) {
            this.vlaue = vlaue;
        }

        @Override
        public String toString() {
            return "Property{" +
                    "id='" + id + '\'' +
                    ", key='" + key + '\'' +
                    ", vlaue='" + vlaue + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Goods{" +
                "name='" + name + '\'' +
                ", lists=" + lists +
                '}';
    }
}
