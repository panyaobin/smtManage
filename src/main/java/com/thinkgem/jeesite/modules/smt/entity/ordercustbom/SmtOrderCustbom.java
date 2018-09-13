/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.entity.ordercustbom;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 客户BOMEntity
 * @author popo
 * @version 2018-06-10
 */
public class SmtOrderCustbom extends DataEntity<SmtOrderCustbom> {
	
	private static final long serialVersionUID = 1L;
	private String customerNo;		// 客户代码
	private String customerName;		// 客户代码
	private String productNo;		// 型号
	private String pointCounts;		// 总点数

	
	public SmtOrderCustbom() {
		super();
	}

	public SmtOrderCustbom(String id){
		super(id);
	}

	@Length(min=0, max=255, message="客户代码长度必须介于 0 和 255 之间")
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
	
	@Length(min=0, max=255, message="总点数长度必须介于 0 和 255 之间")
	public String getPointCounts() {
		return pointCounts;
	}

	public void setPointCounts(String pointCounts) {
		this.pointCounts = pointCounts;
	}

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}