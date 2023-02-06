/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data;

/**
 * Created by jerry on 2018/9/30.
 * You are good enough to do everthing
 */
public class NewBillDrawEntity {

    private boolean isChoosed;

    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "NewBillDrawEntity{" +
                "isChoosed=" + isChoosed +
                ", id=" + id +
                ", collectStatus='" + collectStatus + '\'' +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", articleNumber='" + articleNumber + '\'' +
                ", startStation='" + startStation + '\'' +
                ", endStation='" + endStation + '\'' +
                ", consignee='" + consignee + '\'' +
                ", consigneePhone='" + consigneePhone + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", quantity=" + quantity +
                ", packaging='" + packaging + '\'' +
                ", totalFreight='" + totalFreight + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", collectPayment='" + collectPayment + '\'' +
                ", cashPayment='" + cashPayment + '\'' +
                ", monthlyPayment='" + monthlyPayment + '\'' +
                ", declaredValue=" + declaredValue +
                ", valuationFee=" + valuationFee +
                ", deliveryFee=" + deliveryFee +
                ", advance=" + advance +
                ", collectionFee=" + collectionFee +
                ", freight=" + freight +
                ", waybillNumber='" + waybillNumber + '\'' +
                ", consigner='" + consigner + '\'' +
                ", consignerPhone='" + consignerPhone + '\'' +
                ", numberOfCopies=" + numberOfCopies +
                ", userName='" + userName + '\'' +
                ", remarks='" + remarks + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", inputArticleNumber='" + inputArticleNumber + '\'' +
                ", isUnusual='" + isUnusual + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", barCode='" + barCode + '\'' +
                ", driverName='" + driverName + '\'' +
                ", distributionTime='" + distributionTime + '\'' +
                ", collectDate='" + collectDate + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                ", paymentTime='" + paymentTime + '\'' +
                ", returnStatus='" + returnStatus + '\'' +
                ", log=" + log +
                ", totalFee=" + totalFee +
                ", invoiceStatus='" + invoiceStatus + '\'' +
                ", isLoss=" + isLoss +
                ", deliverCode='" + deliverCode + '\'' +
                ", collectName='" + collectName + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankCode='" + bankCode + '\'' +
                '}';
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    private Long id;
    private String collectStatus;
    private String invoiceDate;
    private String articleNumber;
    private String startStation;
    private String endStation;
    private String consignee;
    private String consigneePhone;
    private String goodsName;
    private Long quantity;
    private String packaging;
    private Double totalFreight;
    private String paymentMethod;
    private Double collectPayment;
    private Double cashPayment;
    private String monthlyPayment;
    private Double declaredValue;
    private Double valuationFee;
    private Double deliveryFee;
    private Double advance;
    private Double collectionFee;
    private Double freight;
    private String waybillNumber;
    private String consigner;
    private String consignerPhone;
    private Long numberOfCopies;
    private String userName;
    private String remarks;
    private String updateTime;
    private String inputArticleNumber;
    private Integer isUnusual;
    private String paymentStatus;
    private String barCode;
    private String driverName;
    private String distributionTime;
    private String collectDate;
    private String paymentDate;
    private String paymentTime;
    private String returnStatus;
    private Object log;
    private int totalFee;
    private String invoiceStatus;
    private Object isLoss;
    private String deliverCode;
    private String collectName;
    private String bankName;
    private String bankCode;

    public void setCollectStatus(String collectStatus) {
        this.collectStatus = collectStatus;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public void setTotalFreight(Double totalFreight) {
        this.totalFreight = totalFreight;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setCollectPayment(Double collectPayment) {
        this.collectPayment = collectPayment;
    }

    public void setCashPayment(Double cashPayment) {
        this.cashPayment = cashPayment;
    }

    public void setMonthlyPayment(String monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public void setDeclaredValue(Double declaredValue) {
        this.declaredValue = declaredValue;
    }

    public void setValuationFee(Double valuationFee) {
        this.valuationFee = valuationFee;
    }

    public void setDeliveryFee(Double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public void setAdvance(Double advance) {
        this.advance = advance;
    }

    public void setCollectionFee(Double collectionFee) {
        this.collectionFee = collectionFee;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public void setWaybillNumber(String waybillNumber) {
        this.waybillNumber = waybillNumber;
    }

    public void setConsigner(String consigner) {
        this.consigner = consigner;
    }

    public void setConsignerPhone(String consignerPhone) {
        this.consignerPhone = consignerPhone;
    }

    public void setNumberOfCopies(Long numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setInputArticleNumber(String inputArticleNumber) {
        this.inputArticleNumber = inputArticleNumber;
    }

    public void setIsUnusual(Integer isUnusual) {
        this.isUnusual = isUnusual;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setDistributionTime(String distributionTime) {
        this.distributionTime = distributionTime;
    }

    public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus;
    }

    public void setLog(Object log) {
        this.log = log;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public void setIsLoss(Object isLoss) {
        this.isLoss = isLoss;
    }

    public void setDeliverCode(String deliverCode) {
        this.deliverCode = deliverCode;
    }

    public void setCollectName(String collectName) {
        this.collectName = collectName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getCollectStatus() {
        return collectStatus;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public String getStartStation() {
        return startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public String getConsignee() {
        return consignee;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public String getPackaging() {
        return packaging;
    }

    public Double getTotalFreight() {
        return totalFreight;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Double getCollectPayment() {
        return collectPayment;
    }

    public Double getCashPayment() {
        return cashPayment;
    }

    public String getMonthlyPayment() {
        return monthlyPayment;
    }

    public Double getDeclaredValue() {
        return declaredValue;
    }

    public Double getValuationFee() {
        return valuationFee;
    }

    public Double getDeliveryFee() {
        return deliveryFee;
    }

    public Double getAdvance() {
        return advance;
    }

    public Double getCollectionFee() {
        return collectionFee;
    }

    public Double getFreight() {
        return freight;
    }

    public String getWaybillNumber() {
        return waybillNumber;
    }

    public String getConsigner() {
        return consigner;
    }

    public String getConsignerPhone() {
        return consignerPhone;
    }

    public Long getNumberOfCopies() {
        return numberOfCopies;
    }

    public String getUserName() {
        return userName;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getInputArticleNumber() {
        return inputArticleNumber;
    }

    public Integer getIsUnusual() {
        return isUnusual;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getBarCode() {
        return barCode;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDistributionTime() {
        return distributionTime;
    }

    public String getCollectDate() {
        return collectDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public Object getLog() {
        return log;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public Object getIsLoss() {
        return isLoss;
    }

    public String getDeliverCode() {
        return deliverCode;
    }

    public String getCollectName() {
        return collectName;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBankCode() {
        return bankCode;
    }
}
