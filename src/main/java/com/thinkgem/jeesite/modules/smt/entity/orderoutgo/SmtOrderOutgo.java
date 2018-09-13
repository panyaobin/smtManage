/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.entity.orderoutgo;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 产品出货表Entity
 * @author popo
 * @version 2018-06-10
 */
public class SmtOrderOutgo extends DataEntity<SmtOrderOutgo> {
	
	private static final long serialVersionUID = 1L;
	private String customerNo;		// 客户代码
	private String customerName;	// 客户名称
	private String productNo;		// 产品型号
	private String orderNo;		// 入库订单号
	private int counts;		// 出货数量
	private int pointCounts;		// 出货点数
	private String outgoOrderNo;		// 出货单号
	private String productType;		// 产品类型
	
	public SmtOrderOutgo() {
		super();
	}

	public SmtOrderOutgo(String id){
		super(id);
	}

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
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Length(min=0, max=255, message="出货数量长度必须介于 0 和 255 之间")
	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
	}
	
	@Length(min=0, max=255, message="出货点数长度必须介于 0 和 255 之间")
	public int getPointCounts() {
		return pointCounts;
	}

	public void setPointCounts(int pointCounts) {
		this.pointCounts = pointCounts;
	}
	
	@Length(min=0, max=32, message="出货单号长度必须介于 0 和 32 之间")
	public String getOutgoOrderNo() {
		return outgoOrderNo;
	}

	public void setOutgoOrderNo(String outgoOrderNo) {
		this.outgoOrderNo = outgoOrderNo;
	}
	
	@Length(min=0, max=255, message="产品类型长度必须介于 0 和 255 之间")
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