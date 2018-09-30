package com.xinze.haoke.module.invite.model;


import java.io.Serializable;


/**
 * @feibai
 */
public class TruckownerDriver implements Serializable {
    /**
     * 编号
     **/
    private String id;

    /**
     * 车主id
     **/
    private String truckownerUserId;

    /**
     * 司机id
     **/
    private String driverUserId;

    /**
     * 邀请状态
     **/
    private String inviteFlag;

    /**
     * 内容
     **/
    private String content;

    /**
     * 创建者
     **/
    private String createBy;

    /**
     * 创建时间
     **/
    private String createDate;

    /**
     * 更新者
     **/
    private String updateBy;

    /**
     * 更新时间
     **/
    private String updateDate;

    /**
     * 备注信息
     **/
    private String remarks;

    /**
     * 删除标记
     **/
    private String delFlag;


    /**
     * 司机抢单权限,1T0F
     **/
    protected String rightFlag;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public TruckownerDriver withId(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTruckownerUserId() {
        return truckownerUserId;
    }

    public TruckownerDriver withTruckownerUserId(String truckownerUserId) {
        this.setTruckownerUserId(truckownerUserId);
        return this;
    }

    public void setTruckownerUserId(String truckownerUserId) {
        this.truckownerUserId = truckownerUserId == null ? null : truckownerUserId.trim();
    }

    public String getDriverUserId() {
        return driverUserId;
    }

    public TruckownerDriver withDriverUserId(String driverUserId) {
        this.setDriverUserId(driverUserId);
        return this;
    }

    public void setDriverUserId(String driverUserId) {
        this.driverUserId = driverUserId == null ? null : driverUserId.trim();
    }

    public String getInviteFlag() {
        return inviteFlag;
    }

    public TruckownerDriver withInviteFlag(String inviteFlag) {
        this.setInviteFlag(inviteFlag);
        return this;
    }

    public void setInviteFlag(String inviteFlag) {
        this.inviteFlag = inviteFlag == null ? null : inviteFlag.trim();
    }

    public String getContent() {
        return content;
    }

    public TruckownerDriver withContent(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public TruckownerDriver withCreateBy(String createBy) {
        this.setCreateBy(createBy);
        return this;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getCreateDate() {
        return createDate;
    }

    public TruckownerDriver withCreateDate(String createDate) {
        this.setCreateDate(createDate);
        return this;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public TruckownerDriver withUpdateBy(String updateBy) {
        this.setUpdateBy(updateBy);
        return this;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public TruckownerDriver withUpdateDate(String updateDate) {
        this.setUpdateDate(updateDate);
        return this;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public TruckownerDriver withRemarks(String remarks) {
        this.setRemarks(remarks);
        return this;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public TruckownerDriver withDelFlag(String delFlag) {
        this.setDelFlag(delFlag);
        return this;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public String getRightFlag() {
        return rightFlag;
    }

    public void setRightFlag(String rightFlag) {
        this.rightFlag = rightFlag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", truckownerUserId=").append(truckownerUserId);
        sb.append(", driverUserId=").append(driverUserId);
        sb.append(", inviteFlag=").append(inviteFlag);
        sb.append(", content=").append(content);
        sb.append(", createBy=").append(createBy);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", remarks=").append(remarks);
        sb.append(", delFlag=").append(delFlag);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TruckownerDriver other = (TruckownerDriver) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getTruckownerUserId() == null ? other.getTruckownerUserId() == null : this.getTruckownerUserId().equals(other.getTruckownerUserId()))
                && (this.getDriverUserId() == null ? other.getDriverUserId() == null : this.getDriverUserId().equals(other.getDriverUserId()))
                && (this.getInviteFlag() == null ? other.getInviteFlag() == null : this.getInviteFlag().equals(other.getInviteFlag()))
                && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
                && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
                && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
                && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
                && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()))
                && (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
                && (this.getDelFlag() == null ? other.getDelFlag() == null : this.getDelFlag().equals(other.getDelFlag()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTruckownerUserId() == null) ? 0 : getTruckownerUserId().hashCode());
        result = prime * result + ((getDriverUserId() == null) ? 0 : getDriverUserId().hashCode());
        result = prime * result + ((getInviteFlag() == null) ? 0 : getInviteFlag().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
        result = prime * result + ((getDelFlag() == null) ? 0 : getDelFlag().hashCode());
        return result;
    }
}