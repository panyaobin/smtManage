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
	private String customerNo;		// 客户代码
	private String customerName;    // 客户名称
	private String productNo;		// 产品型号
	private int orderNo;		// 订单号
	private String entryCounts;		// 成品入库数量
	private String productType;		// 产品类型
	
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
}