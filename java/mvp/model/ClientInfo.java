/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.mvp.model;

import java.util.List;

/**
 * Created by jerry on 2018/8/22.
 * You are good enough to do everthing
 */
public class ClientInfo {

    private List<ConsignerBean> consigner;
    private List<ConsigneeBean> consignee;

    public List<ConsignerBean> getConsigner() {
        return consigner;
    }

    public void setConsigner(List<ConsignerBean> consigner) {
        this.consigner = consigner;
    }

    public List<ConsigneeBean> getConsignee() {
        return consignee;
    }

    public void setConsignee(List<ConsigneeBean> consignee) {
        this.consignee = consignee;
    }

    public static class ConsignerBean {
        /**
         * consigner : 赖文杰
         * consigner_phone : 18908363895
         */

        private String consigner;
        private String consigner_phone;

        public String getConsigner() {
            return consigner;
        }

        public void setConsigner(String consigner) {
            this.consigner = consigner;
        }

        public String getConsigner_phone() {
            return consigner_phone;
        }

        public void setConsigner_phone(String consigner_phone) {
            this.consigner_phone = consigner_phone;
        }
    }

    public static class ConsigneeBean {
        /**
         * consignee : 李自强
         * consignee_phone : 18911111111
         */

        private String consignee;
        private String consignee_phone;

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getConsignee_phone() {
            return consignee_phone;
        }

        public void setConsignee_phone(String consignee_phone) {
            this.consignee_phone = consignee_phone;
        }
    }
}
