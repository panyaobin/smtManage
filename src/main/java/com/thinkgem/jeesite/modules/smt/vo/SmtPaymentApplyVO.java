/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.vo;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

/**
 * 付款申请Entity
 * @author popo
 * @version 2019-03-10
 */
public class SmtPaymentApplyVO extends DataEntity<SmtPaymentApplyVO> {

	private static final long serialVersionUID = 1L;
	private String paymentId;		// 收款单位ID
	private String accountName;		// 收款单位名称
	private BigDecimal paymentMoney;		// 金额
	private String accountNumber;		// 账号 卡号
	private String accountBank;		// 开户行
	private String paymentReason;		// 付款原因

	public SmtPaymentApplyVO() {
		super();
	}

	public SmtPaymentApplyVO(String id){
		super(id);
	}

	@Length(min=1, max=64, message="收款单位ID长度必须介于 1 和 64 之间")
	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public BigDecimal getPaymentMoney() {
		return paymentMoney;
	}

	public void setPaymentMoney(BigDecimal paymentMoney) {
		this.paymentMoney = paymentMoney;
	}

	@Length(min=1, max=255, message="付款原因长度必须介于 1 和 255 之间")
	public String getPaymentReason() {
		return paymentReason;
	}

	public void setPaymentReason(String paymentReason) {
		this.paymentReason = paymentReason;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

}