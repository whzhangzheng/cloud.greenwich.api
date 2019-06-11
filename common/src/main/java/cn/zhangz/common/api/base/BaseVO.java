package cn.zhangz.common.api.base;

import java.io.Serializable;
import java.util.Date;

public class BaseVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer createBy;
    private Date createDate;
    private Integer updateBy;
    private Date updateDate;
    private String remarks;

    public Integer getCreateBy() {
        return this.createBy;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public Integer getUpdateBy() {
        return this.updateBy;
    }

    public Date getUpdateDate() {
        return this.updateDate;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String toString() {
        return "BaseVO(createBy=" + this.getCreateBy() + ", createDate=" + this.getCreateDate() + ", updateBy=" + this.getUpdateBy() + ", updateDate=" + this.getUpdateDate() + ", remarks=" + this.getRemarks() + ")";
    }

    public BaseVO() {
    }
}
