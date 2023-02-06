/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.data.new_tms_entity;

import java.io.Serializable;

/**
 * Created by jerry on 2018/9/26.
 * You are good enough to do everthing
 */
public class NewBillEntity implements Serializable {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private int endPointId;

    public int getEndPointId() {
        return endPointId;
    }

    public void setEndPointId(int endPointId) {
        this.endPointId = endPointId;
    }

    private int uuid;
    private long waybillNumber;
    private int initialStationId;
    private String startStation;
    private String endStation;
    private int terminalStationId;
    private int owner;
    private String invoiceDate;
    private String articleNumber;
    private String consignerUuid;
    private String consigner;
    private String consignerPhone;
    private String consignerAddr;
    private String consignee;
    private String consigneePhone;
    private String consigneeAddr;
    private String goodsName;
    private int quantity;
    private String packaging;
    private String weight;
    private String volume;
    private String deliveryMethod;
    private String numberOfCopies;
    private int drawerUuid;
    private String invoiceStatus;
    private String inputArticleNumber;
    private String transitDestination;
    private String loadingBatchesId;
    private String source;
    private int isUnusual;
    private String remarks;
    private String updateTime;
    private String operationUserUuid;
    private String collectStatus;
    private String paymentStatus;
    private String barCode;
    private String paymentTime;
    private String driverName;
    private String distributionTime;
    private boolean returnStatus;
    private String collectDate;
    private String paymentDate;
    private String deliverCode;
    private boolean loss;

    private Double totalFreight;
    private String paymentMethod;
    private Double collectPayment;
    private Double cashPayment;
    private Double monthlyPayment;
    private Double declaredValue;
    private Double valuationFee;
    private Double deliveryFee;
    private Double advance;
    private Double collectionFee;
    private Double freight;
    private String userName;
    private String log;
    private Double totalFee;
    private String isLoss;
    private String collectName;
    private String bankName;
    private String bankCode;

    private String something;

    public String getSomething() {
        return something;
    }

    public void setSomething(String something) {
        this.something = something;
    }

    @Override
    public String toString() {
        return "NewBillEntity{" +
                "id=" + id +
                ", endPointId=" + endPointId +
                ", uuid=" + uuid +
                ", waybillNumber=" + waybillNumber +
                ", initialStationId=" + initialStationId +
                ", startStation='" + startStation + '\'' +
                ", endStation='" + endStation + '\'' +
                ", terminalStationId=" + terminalStationId +
                ", owner=" + owner +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", articleNumber='" + articleNumber + '\'' +
                ", consignerUuid='" + consignerUuid + '\'' +
                ", consigner='" + consigner + '\'' +
                ", consignerPhone='" + consignerPhone + '\'' +
                ", consignerAddr='" + consignerAddr + '\'' +
                ", consignee='" + consignee + '\'' +
                ", consigneePhone='" + consigneePhone + '\'' +
                ", consigneeAddr='" + consigneeAddr + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", quantity=" + quantity +
                ", packaging='" + packaging + '\'' +
                ", weight='" + weight + '\'' +
                ", volume='" + volume + '\'' +
                ", deliveryMethod='" + deliveryMethod + '\'' +
                ", numberOfCopies='" + numberOfCopies + '\'' +
                ", drawerUuid=" + drawerUuid +
                ", invoiceStatus='" + invoiceStatus + '\'' +
                ", inputArticleNumber='" + inputArticleNumber + '\'' +
                ", transitDestination='" + transitDestination + '\'' +
                ", loadingBatchesId='" + loadingBatchesId + '\'' +
                ", source='" + source + '\'' +
                ", isUnusual=" + isUnusual +
                ", remarks='" + remarks + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", operationUserUuid='" + operationUserUuid + '\'' +
                ", collectStatus='" + collectStatus + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", barCode='" + barCode + '\'' +
                ", paymentTime='" + paymentTime + '\'' +
                ", driverName='" + driverName + '\'' +
                ", distributionTime='" + distributionTime + '\'' +
                ", returnStatus=" + returnStatus +
                ", collectDate='" + collectDate + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                ", deliverCode='" + deliverCode + '\'' +
                ", loss=" + loss +
                ", totalFreight=" + totalFreight +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", collectPayment=" + collectPayment +
                ", cashPayment=" + cashPayment +
                ", monthlyPayment=" + monthlyPayment +
                ", declaredValue=" + declaredValue +
                ", valuationFee=" + valuationFee +
                ", deliveryFee=" + deliveryFee +
                ", advance=" + advance +
                ", collectionFee=" + collectionFee +
                ", freight=" + freight +
                ", userName='" + userName + '\'' +
                ", log='" + log + '\'' +
                ", totalFee=" + totalFee +
                ", isLoss='" + isLoss + '\'' +
                ", collectName='" + collectName + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", isChoosed=" + isChoosed +
                '}';
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getCollectPayment() {
        return collectPayment;
    }

    public void setCollectPayment(Double collectPayment) {
        this.collectPayment = collectPayment;
    }

    public Double getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(Double cashPayment) {
        this.cashPayment = cashPayment;
    }

    public Double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(Double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public Double getDeclaredValue() {
        return declaredValue;
    }

    public void setDeclaredValue(Double declaredValue) {
        this.declaredValue = declaredValue;
    }

    public Double getValuationFee() {
        return valuationFee;
    }

    public void setValuationFee(Double valuationFee) {
        this.valuationFee = valuationFee;
    }

    public Double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Double getAdvance() {
        return advance;
    }

    public void setAdvance(Double advance) {
        this.advance = advance;
    }

    public Double getCollectionFee() {
        return collectionFee;
    }

    public void setCollectionFee(Double collectionFee) {
        this.collectionFee = collectionFee;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public String getIsLoss() {
        return isLoss;
    }

    public void setIsLoss(String isLoss) {
        this.isLoss = isLoss;
    }

    public String getCollectName() {
        return collectName;
    }

    public void setCollectName(String collectName) {
        this.collectName = collectName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Double getTotalFreight() {

        return totalFreight;
    }

    public void setTotalFreight(Double totalFreight) {
        this.totalFreight = totalFreight;
    }

    private boolean isChoosed;

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public long getWaybillNumber() {
        return waybillNumber;
    }

    public void setWaybillNumber(long waybillNumber) {
        this.waybillNumber = waybillNumber;
    }

    public int getInitialStationId() {
        return initialStationId;
    }

    public void setInitialStationId(int initialStationId) {
        this.initialStationId = initialStationId;
    }

    public int getTerminalStationId() {
        return terminalStationId;
    }

    public void setTerminalStationId(int terminalStationId) {
        this.terminalStationId = terminalStationId;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getConsignerUuid() {
        return consignerUuid;
    }

    public void setConsignerUuid(String consignerUuid) {
        this.consignerUuid = consignerUuid;
    }

    public String getConsigner() {
        return consigner;
    }

    public void setConsigner(String consigner) {
        this.consigner = consigner;
    }

    public String getConsignerPhone() {
        return consignerPhone;
    }

    public void setConsignerPhone(String consignerPhone) {
        this.consignerPhone = consignerPhone;
    }

    public String getConsignerAddr() {
        return consignerAddr;
    }

    public void setConsignerAddr(String consignerAddr) {
        this.consignerAddr = consignerAddr;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public String getEndStation() {
        return endStation;
    }

    public void setEndStation(String endStation) {
        this.endStation = endStation;
    }

    public String getConsigneePhone() {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }

    public String getConsigneeAddr() {
        return consigneeAddr;
    }

    public void setConsigneeAddr(String consigneeAddr) {
        this.consigneeAddr = consigneeAddr;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(String numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public int getDrawerUuid() {
        return drawerUuid;
    }

    public void setDrawerUuid(int drawerUuid) {
        this.drawerUuid = drawerUuid;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getInputArticleNumber() {
        return inputArticleNumber;
    }

    public void setInputArticleNumber(String inputArticleNumber) {
        this.inputArticleNumber = inputArticleNumber;
    }

    public String getTransitDestination() {
        return transitDestination;
    }

    public void setTransitDestination(String transitDestination) {
        this.transitDestination = transitDestination;
    }

    public String getLoadingBatchesId() {
        return loadingBatchesId;
    }

    public void setLoadingBatchesId(String loadingBatchesId) {
        this.loadingBatchesId = loadingBatchesId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getIsUnusual() {
        return isUnusual;
    }

    public void setIsUnusual(int isUnusual) {
        this.isUnusual = isUnusual;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getOperationUserUuid() {
        return operationUserUuid;
    }

    public void setOperationUserUuid(String operationUserUuid) {
        this.operationUserUuid = operationUserUuid;
    }

    public String getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(String collectStatus) {
        this.collectStatus = collectStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDistributionTime() {
        return distributionTime;
    }

    public void setDistributionTime(String distributionTime) {
        this.distributionTime = distributionTime;
    }

    public boolean isReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(boolean returnStatus) {
        this.returnStatus = returnStatus;
    }

    public String getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getDeliverCode() {
        return deliverCode;
    }

    public void setDeliverCode(String deliverCode) {
        this.deliverCode = deliverCode;
    }

    public boolean isLoss() {
        return loss;
    }

    public void setLoss(boolean loss) {
        this.loss = loss;
    }
}
