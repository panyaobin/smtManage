/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.entity.orderonline;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * smt在线生产表Entity
 * @author popo
 * @version 2018-06-09
 */
public class SmtOrderOnline extends DataEntity<SmtOrderOnline> {
	
	private static final long serialVersionUID = 1L;
	private String index;		// 序列号
	private String orderNo;		// 订单编号
	private String customerNo;		// 客户编号
	private String customerName;	// 客户名称
	private String productNo;		// 产品编号
	private String productType;		// 产品类型 1.FPC 2.电子料
	private String counts;		// 数量
	private int sendNo;		// 数量
	
	public SmtOrderOnline() {
		super();
	}

	public SmtOrderOnline(String id){
		super(id);
	}

	@Length(min=0, max=32, message="订单编号长度必须介于 0 和 32 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@Length(min=0, max=32, message="客户编号长度必须介于 0 和 32 之间")
	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Length(min=0, max=32, message="产品编号长度必须介于 0 和 32 之间")
	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

    @Length(min=0, max=11, message="电子料长度必须介于 0 和 11 之间")
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	@Length(min=0, max=11, message="数量长度必须介于 0 和 11 之间")
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

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}