/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.entity.syscustomer;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 客户信息管理Entity
 * @author popo
 * @version 2018-06-10
 */
public class SmtSysCustomer extends DataEntity<SmtSysCustomer> {
	
	private static final long serialVersionUID = 1L;
	private String customerNo;		// 客户代码
	private String customerName;		// 客户名称
	private String balanceType;		// 结算方式
	
	public SmtSysCustomer() {
		super();
	}

	public SmtSysCustomer(String id){
		super(id);
	}

	@Length(min=0, max=255, message="客户代码长度必须介于 0 和 255 之间")
	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	
	@Length(min=0, max=255, message="客户名称长度必须介于 0 和 255 之间")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	@Length(min=0, max=255, message="结算方式长度必须介于 0 和 255 之间")
	public String getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}
	
}