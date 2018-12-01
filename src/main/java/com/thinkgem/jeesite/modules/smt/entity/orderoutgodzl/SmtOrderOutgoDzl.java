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
    private String index;
    private String indexs;
    private String bomNames;
    private String bomTypes;
    private String countss;
    private String stockCountss;
    private String bomName; //物料型号
    private String bomType; //物料类型
    private String productNo; //产品型号
    private String outgoOrderNo; //出货单号
    private String orderNo; //订单号
    private String counts;        // 总用量
    private String fpcCounts;        // fpc总用量
    private String stockCounts;        // 备品用量
    private String customerName;        // 客户名称
    private String customerNo;        // 客户代码
    private String custBomCount;        // 客户电子料用量
    
    //出货筛选开始时间
    private String startDate;
    //出货筛选结束时间
    private String endDate;
    
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

    public String getCustBomCount() {
        return custBomCount;
    }

    public void setCustBomCount(String custBomCount) {
        this.custBomCount = custBomCount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFpcCounts() {
        return fpcCounts;
    }

    public void setFpcCounts(String fpcCounts) {
        this.fpcCounts = fpcCounts;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getIndexs() {
        return indexs;
    }

    public void setIndexs(String indexs) {
        this.indexs = indexs;
    }

    public String getBomNames() {
        return bomNames;
    }

    public void setBomNames(String bomNames) {
        this.bomNames = bomNames;
    }

    public String getBomTypes() {
        return bomTypes;
    }

    public void setBomTypes(String bomTypes) {
        this.bomTypes = bomTypes;
    }

    public String getCountss() {
        return countss;
    }

    public void setCountss(String countss) {
        this.countss = countss;
    }

    public String getStockCountss() {
        return stockCountss;
    }

    public void setStockCountss(String stockCountss) {
        this.stockCountss = stockCountss;
    }
}