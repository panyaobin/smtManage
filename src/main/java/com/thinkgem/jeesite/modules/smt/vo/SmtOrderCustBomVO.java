package com.thinkgem.jeesite.modules.smt.vo;

import com.thinkgem.jeesite.common.persistence.DataEntity;

public class SmtOrderCustBomVO extends DataEntity<SmtOrderCustBomVO> {

    private String customerNo;		// 客户代码
    private String productNo;		// 型号
    private String pointCounts;		// 总点数
    private String custbomId;		// 客户BOM主表ID
    private String dzlbomId;		// 电子料ID
    private String piecePosition;		// 件位
    private String counts;		// 用量
    private String stockCounts;		// 备品

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getPointCounts() {
        return pointCounts;
    }

    public void setPointCounts(String pointCounts) {
        this.pointCounts = pointCounts;
    }

    public String getCustbomId() {
        return custbomId;
    }

    public void setCustbomId(String custbomId) {
        this.custbomId = custbomId;
    }

    public String getDzlbomId() {
        return dzlbomId;
    }

    public void setDzlbomId(String dzlbomId) {
        this.dzlbomId = dzlbomId;
    }

    public String getPiecePosition() {
        return piecePosition;
    }

    public void setPiecePosition(String piecePosition) {
        this.piecePosition = piecePosition;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public String getStockCounts() {
        return stockCounts;
    }

    public void setStockCounts(String stockCounts) {
        this.stockCounts = stockCounts;
    }
}
