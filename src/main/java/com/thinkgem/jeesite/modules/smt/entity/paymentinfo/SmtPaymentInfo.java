/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.entity.paymentinfo;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 付款信息Entity
 * @author popo
 * @version 2019-03-10
 */
public class SmtPaymentInfo extends DataEntity<SmtPaymentInfo> {
	
	private static final long serialVersionUID = 1L;
	private String collectionUnit;		// 收款单位
	private String accountName;		// 户名
	private String accountNumber;		// 卡号
	private String accountBank;		// 开户银行
	
	public SmtPaymentInfo() {
		super();
	}

	public SmtPaymentInfo(String id){
		super(id);
	}

	@Length(min=1, max=255, message="收款单位长度必须介于 1 和 255 之间")
	public String getCollectionUnit() {
		return collectionUnit;
	}

	public void setCollectionUnit(String collectionUnit) {
		this.collectionUnit = collectionUnit;
	}
	
	@Length(min=1, max=255, message="户名长度必须介于 1 和 255 之间")
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@Length(min=1, max=32, message="卡号长度必须介于 1 和 32 之间")
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	@Length(min=1, max=255, message="开户银行长度必须介于 1 和 255 之间")
	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}
	
}