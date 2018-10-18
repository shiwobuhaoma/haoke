package com.xinze.haoke.module.receive.modle;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReceiverBill {



    /**
     * wayBillid : 568f16a04ed54cbf95b7b9c83e1c23df
     * waybillOrderEntities : [{"id":"DDH5DDB22A905314F4A923F","waybillId":"568f16a04ed54cbf95b7b9c83e1c23df","orderStatus":"3","truckOwnerid":"cc9202e676884f15ad8ba640d97be189","driverId":"eaadfc955a604770a8ab30002875b5c5","truckId":"18ad54163f1a4f9a8d38285291f7313f","createBy":"eaadfc955a604770a8ab30002875b5c5","createDate":"2018-06-26 22:48:28","updateBy":"eaadfc955a604770a8ab30002875b5c5","updateDate":"2018-06-26 22:48:43","remarks":"雨天注意路滑","delFlag":"0","truckownername":"李晓飞","trucknumber":"京A321"}]
     */

    private String wayBillid;
    private List<WaybillOrderEntitiesBean> waybillOrderEntities;



    public String getWayBillid() {
        return wayBillid;
    }

    public void setWayBillid(String wayBillid) {
        this.wayBillid = wayBillid;
    }

    public List<WaybillOrderEntitiesBean> getWaybillOrderEntities() {
        return waybillOrderEntities;
    }

    public void setWaybillOrderEntities(List<WaybillOrderEntitiesBean> waybillOrderEntities) {
        this.waybillOrderEntities = waybillOrderEntities;
    }

    public static class WaybillOrderEntitiesBean {
        /**
         * id : DDH5DDB22A905314F4A923F
         * waybillId : 568f16a04ed54cbf95b7b9c83e1c23df
         * orderStatus : 3
         * truckOwnerid : cc9202e676884f15ad8ba640d97be189
         * driverId : eaadfc955a604770a8ab30002875b5c5
         * truckId : 18ad54163f1a4f9a8d38285291f7313f
         * createBy : eaadfc955a604770a8ab30002875b5c5
         * createDate : 2018-06-26 22:48:28
         * updateBy : eaadfc955a604770a8ab30002875b5c5
         * updateDate : 2018-06-26 22:48:43
         * remarks : 雨天注意路滑
         * delFlag : 0
         * truckownername : 李晓飞
         * trucknumber : 京A321
         */

        @SerializedName("id")
        private String idX;
        private String waybillId;
        @SerializedName("orderStatus")
        private String orderStatusX;
        private String truckOwnerid;
        private String driverId;
        private String truckId;
        private String createBy;
        private String createDate;
        private String updateBy;
        private String updateDate;
        private String remarks;
        private String delFlag;
        @SerializedName("truckownername")
        private String truckownernameX;
        @SerializedName("trucknumber")
        private String trucknumberX;

        public String getIdX() {
            return idX;
        }

        public void setIdX(String idX) {
            this.idX = idX;
        }

        public String getWaybillId() {
            return waybillId;
        }

        public void setWaybillId(String waybillId) {
            this.waybillId = waybillId;
        }

        public String getOrderStatusX() {
            return orderStatusX;
        }

        public void setOrderStatusX(String orderStatusX) {
            this.orderStatusX = orderStatusX;
        }

        public String getTruckOwnerid() {
            return truckOwnerid;
        }

        public void setTruckOwnerid(String truckOwnerid) {
            this.truckOwnerid = truckOwnerid;
        }

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public String getTruckId() {
            return truckId;
        }

        public void setTruckId(String truckId) {
            this.truckId = truckId;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public String getTruckownernameX() {
            return truckownernameX;
        }

        public void setTruckownernameX(String truckownernameX) {
            this.truckownernameX = truckownernameX;
        }

        public String getTrucknumberX() {
            return trucknumberX;
        }

        public void setTrucknumberX(String trucknumberX) {
            this.trucknumberX = trucknumberX;
        }
    }
}
