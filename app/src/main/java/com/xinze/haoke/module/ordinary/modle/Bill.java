package com.xinze.haoke.module.ordinary.modle;

import java.io.Serializable;

/**
 * 货单
 * @author lxf
 */
public class Bill implements Serializable{


    /**
     * userId : 4eb0c89c16a04160bcf125eb731aa5a6
     * fromAreaId : 110000
     * fromDetailAdress : 天安门
     * fromName : 毛主席
     * fromPhone : 19999999999
     * toAreaId : 120000
     * toDetailAdress : 大宅门
     * toName : 霍元甲
     * toPhone : 11111111111
     * productName : 银元
     * distance : 300
     * price : 26.5
     * dateFrom : 2018-05-03 00:00:00
     * dateTo : 2018-06-30 00:00:00
     * truckNumber : 321
     * truckCode : jzx
     * truckLong : 20.6
     * journeyLoss : 4.5
     * remarks : 雨天注意路滑
     * wlBilltype : 1                      //发货单类型。0:普通发货。1:定向发货
     * driverId : eaadfc955a604770a8ab30002875b5c5
     * msgPrice : 123.5
     * loadPrice : 100.5
     * unloadPrice : 100.5
     * confirmFlag : 1                     //是否需要对方确认。0:不需要货主确认。 1:需要货主确认
     */

    private String userId;
    private String fromAreaId;
    private String fromDetailAdress;
    private String fromName;
    private String fromPhone;
    private String toAreaId;
    private String toDetailAdress;
    private String toName;
    private String toPhone;
    private String productName;
    private String distance;
    private String price;
    private String dateFrom;
    private String dateTo;
    private String truckNumber;
    private String truckName;
    private String truckCode;
    private String truckLong;
    private String journeyLoss;
    private String remarks;
    private String wlBilltype;
    private String driverId;
    private String msgPrice;
    private String loadPrice;
    private String unloadPrice;


    private String confirmFlag;


    private String id;
    private String orderid;
    private String orderstatus;
    private String orderstatus_desc;
    private int left_number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String getOrderstatus_desc() {
        return orderstatus_desc;
    }

    public void setOrderstatus_desc(String orderstatus_desc) {
        this.orderstatus_desc = orderstatus_desc;
    }
    public String getTruckName() {
        return truckName;
    }

    public void setTruckName(String truckName) {
        this.truckName = truckName;
    }

    public int getLeft_number() {
        return left_number;
    }

    public void setLeft_number(int left_number) {
        this.left_number = left_number;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFromAreaId() {
        return fromAreaId;
    }

    public void setFromAreaId(String fromAreaId) {
        this.fromAreaId = fromAreaId;
    }

    public String getFromDetailAdress() {
        return fromDetailAdress;
    }

    public void setFromDetailAdress(String fromDetailAdress) {
        this.fromDetailAdress = fromDetailAdress;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromPhone() {
        return fromPhone;
    }

    public void setFromPhone(String fromPhone) {
        this.fromPhone = fromPhone;
    }

    public String getToAreaId() {
        return toAreaId;
    }

    public void setToAreaId(String toAreaId) {
        this.toAreaId = toAreaId;
    }

    public String getToDetailAdress() {
        return toDetailAdress;
    }

    public void setToDetailAdress(String toDetailAdress) {
        this.toDetailAdress = toDetailAdress;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public String getToPhone() {
        return toPhone;
    }

    public void setToPhone(String toPhone) {
        this.toPhone = toPhone;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getTruckNumber() {
        return truckNumber;
    }

    public void setTruckNumber(String truckNumber) {
        this.truckNumber = truckNumber;
    }

    public String getTruckCode() {
        return truckCode;
    }

    public void setTruckCode(String truckCode) {
        this.truckCode = truckCode;
    }

    public String getTruckLong() {
        return truckLong;
    }

    public void setTruckLong(String truckLong) {
        this.truckLong = truckLong;
    }

    public String getJourneyLoss() {
        return journeyLoss;
    }

    public void setJourneyLoss(String journeyLoss) {
        this.journeyLoss = journeyLoss;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getWlBilltype() {
        return wlBilltype;
    }

    public void setWlBilltype(String wlBilltype) {
        this.wlBilltype = wlBilltype;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getMsgPrice() {
        return msgPrice;
    }

    public void setMsgPrice(String msgPrice) {
        this.msgPrice = msgPrice;
    }

    public String getLoadPrice() {
        return loadPrice;
    }

    public void setLoadPrice(String loadPrice) {
        this.loadPrice = loadPrice;
    }

    public String getUnloadPrice() {
        return unloadPrice;
    }

    public void setUnloadPrice(String unloadPrice) {
        this.unloadPrice = unloadPrice;
    }

    public String getConfirmFlag() {
        return confirmFlag;
    }

    public void setConfirmFlag(String confirmFlag) {
        this.confirmFlag = confirmFlag;
    }
}
