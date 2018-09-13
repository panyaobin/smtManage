/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.entity.orderoutgodzl;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 电子料出货信息表Entity
 *
 * @author popo
 * @version 2018-07-14
 */
public class SmtOrderOutgoDzl extends DataEntity<SmtOrderOutgoDzl> {

    private static final long serialVersionUID = 1L;
    private String fId;        // FPC出货表id
    private String dzlId;        // 电子料BOM_ID

    private String bomName; //物料型号
    private String bomType; //物料类型
    private String productNo; //产品型号
    private String outgoOrderNo; //出货单号
    private String orderNo; //订单号
    private String counts;        // 总用量
    private String stockCounts;        // 备品用量
    private String customerName;        // 客户名称
    private String customerNo;        // 客户代码

    public SmtOrderOutgoDzl() {
        super();
    }

    public SmtOrderOutgoDzl(String id) {
        super(id);
    }

    @Length(min = 0, max = 11, message = "FPC出货表id长度必须介于 0 和 11 之间")
    public String getFId() {
        return fId;
    }

    public void setFId(String fId) {
        this.fId = fId;
    }

    @Length(min = 0, max = 11, message = "电子料BOM_ID长度必须介于 0 和 11 之间")
    public String getDzlId() {
        return dzlId;
    }

    public void setDzlId(String dzlId) {
        this.dzlId = dzlId;
    }

    @Length(min = 0, max = 32, message = "总用量长度必须介于 0 和 32 之间")
    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    @Length(min = 0, max = 32, message = "备品用量长度必须介于 0 和 32 之间")
    public String getStockCounts() {
        return stockCounts;
    }

    public void setStockCounts(String stockCounts) {
        this.stockCounts = stockCounts;
    }

    public String getBomName() {
        return bomName;
    }

    public void setBomName(String bomName) {
        this.bomName = bomName;
    }

    public String getBomType() {
        return bomType;
    }

    public void setBomType(String bomType) {
        this.bomType = bomType;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getOutgoOrderNo() {
        return outgoOrderNo;
    }

    public void setOutgoOrderNo(String outgoOrderNo) {
        this.outgoOrderNo = outgoOrderNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}