/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.entity.productentry;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 成品入库表Entity
 * @author popo
 * @version 2018-06-10
 */
public class SmtProductEntry extends DataEntity<SmtProductEntry> {
	
	private static final long serialVersionUID = 1L;
    private String index;		// 序列号
	private String customerNo;		// 客户代码
	private String customerName;    // 客户名称
	private String productNo;		// 产品型号
	private int orderNo;		// 订单号
	private String entryCounts;		// 成品入库数量
	private String productType;		// 产品类型
    private String storageType;     //入库类型


    private String indexs;
    private String bomName;
    private String bomNames;
    private String bomType;
    private String bomTypes;
    private String countss;
    private String stockCounts;
    private String stockCountss;


    private String counts;		// 数量
    private int sendNo;		// 数量
    
	
	public SmtProductEntry() {
		super();
	}

	public SmtProductEntry(String id){
		super(id);
	}

	@Length(min=0, max=32, message="客户代码长度必须介于 0 和 32 之间")
	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
	@Length(min=0, max=32, message="产品型号长度必须介于 0 和 32 之间")
	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	
	@Length(min=0, max=32, message="订单号长度必须介于 0 和 32 之间")
	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	
	@Length(min=0, max=32, message="成品入库数量长度必须介于 0 和 32 之间")
	public String getEntryCounts() {
		return entryCounts;
	}

	public void setEntryCounts(String entryCounts) {
		this.entryCounts = entryCounts;
	}
	
	@Length(min=0, max=64, message="产品类型长度必须介于 0 和 64 之间")
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public int getSendNo() {
        return sendNo;
    }

    public void setSendNo(int sendNo) {
        this.sendNo = sendNo;
    }

    public String getIndexs() {
        return indexs;
    }

    public void setIndexs(String indexs) {
        this.indexs = indexs;
    }

    public String getBomName() {
        return bomName;
    }

    public void setBomName(String bomName) {
        this.bomName = bomName;
    }

    public String getBomNames() {
        return bomNames;
    }

    public void setBomNames(String bomNames) {
        this.bomNames = bomNames;
    }

    public String getBomType() {
        return bomType;
    }

    public void setBomType(String bomType) {
        this.bomType = bomType;
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

    public String getStockCounts() {
        return stockCounts;
    }

    public void setStockCounts(String stockCounts) {
        this.stockCounts = stockCounts;
    }

    public String getStockCountss() {
        return stockCountss;
    }

    public void setStockCountss(String stockCountss) {
        this.stockCountss = stockCountss;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }
}