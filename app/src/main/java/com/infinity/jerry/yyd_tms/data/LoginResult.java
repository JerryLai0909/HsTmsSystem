/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

/**
 * Created by jerry on 2017/6/19.
 */

public class LoginResult {
    private Long uuid;
    private Long company_uuid;
    private String company_type;
    private Long role_uuid;
    private String head_img;
    private String user_name;
    private String phone_one;
    private String phone_two;
    private String pwd;
    private Long createtime;
    private Long last_login;
    private Long login_sum;
    private Boolean user_status;
    private String recieve_msg;
    private String remark;
    private String userinfo_parameter;
    private String point_name;
    private Long logistics_uuid;
    private String company_name;
    private String companyType;


    @Override
    public String toString() {
        return "LoginResult{" +
                "uuid=" + uuid +
                ", company_uuid=" + company_uuid +
                ", company_type='" + company_type + '\'' +
                ", role_uuid=" + role_uuid +
                ", head_img='" + head_img + '\'' +
                ", user_name='" + user_name + '\'' +
                ", phone_one='" + phone_one + '\'' +
                ", phone_two='" + phone_two + '\'' +
                ", pwd='" + pwd + '\'' +
                ", createtime=" + createtime +
                ", last_login=" + last_login +
                ", login_sum=" + login_sum +
                ", user_status=" + user_status +
                ", recieve_msg='" + recieve_msg + '\'' +
                ", remark='" + remark + '\'' +
                ", userinfo_parameter='" + userinfo_parameter + '\'' +
                ", point_name='" + point_name + '\'' +
                ", logistics_uuid=" + logistics_uuid +
                ", company_name='" + company_name + '\'' +
                ", companyType='" + companyType + '\'' +
                '}';
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public Long getLogistics_uuid() {
        return logistics_uuid;
    }

    public void setLogistics_uuid(Long logistics_uuid) {
        this.logistics_uuid = logistics_uuid;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getPoint_name() {
        return point_name;
    }

    public void setPoint_name(String point_name) {
        this.point_name = point_name;
    }

    public Long getUuid() {
        return uuid;
    }

    public void setUuid(Long uuid) {
        this.uuid = uuid;
    }

    public Long getCompany_uuid() {
        return company_uuid;
    }

    public void setCompany_uuid(Long company_uuid) {
        this.company_uuid = company_uuid;
    }

    public String getCompany_type() {
        return company_type;
    }

    public void setCompany_type(String company_type) {
        this.company_type = company_type;
    }

    public Long getRole_uuid() {
        return role_uuid;
    }

    public void setRole_uuid(Long role_uuid) {
        this.role_uuid = role_uuid;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPhone_one() {
        return phone_one;
    }

    public void setPhone_one(String phone_one) {
        this.phone_one = phone_one;
    }

    public String getPhone_two() {
        return phone_two;
    }

    public void setPhone_two(String phone_two) {
        this.phone_two = phone_two;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
    }

    public Long getLast_login() {
        return last_login;
    }

    public void setLast_login(Long last_login) {
        this.last_login = last_login;
    }

    public Long getLogin_sum() {
        return login_sum;
    }

    public void setLogin_sum(Long login_sum) {
        this.login_sum = login_sum;
    }

    public Boolean getUser_status() {
        return user_status;
    }

    public void setUser_status(Boolean user_status) {
        this.user_status = user_status;
    }

    public String getRecieve_msg() {
        return recieve_msg;
    }

    public void setRecieve_msg(String recieve_msg) {
        this.recieve_msg = recieve_msg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserinfo_parameter() {
        return userinfo_parameter;
    }

    public void setUserinfo_parameter(String userinfo_parameter) {
        this.userinfo_parameter = userinfo_parameter;
    }

}
