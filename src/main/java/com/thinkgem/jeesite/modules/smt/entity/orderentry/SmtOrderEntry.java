/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.entity.orderentry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * smt订单入库表Entity
 * @author popo
 * @version 2018-06-09
 */
public class SmtOrderEntry extends DataEntity<SmtOrderEntry> {
	
	private static final long serialVersionUID = 1L;
	private String orderNo;		    // 入库订单编号
	private Date orderDate;		    // 订单入库时间
	private String customerNo;		// 客户编号
	private String customerName;	// 客户名称
	private String productNo;		// 产品编号
	private String productType;		// 产品类型 1.FPC 2.电子料
	private String counts;		    // 入库数量
	private String pointCounts;		// 点数数量
	private String packageSize;		// 封装尺寸
	private String productSpec;		// 产品规格
	
	public SmtOrderEntry() {
		super();
	}

	public SmtOrderEntry(String id){
		super(id);
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Length(min=0, max=255, message="电子料长度必须介于 0 和 255 之间")
	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
	
	@Length(min=0, max=255, message="入库数量长度必须介于 0 和 255 之间")
	public String getCounts() {
		return counts;
	}

	public void setCounts(String counts) {
		this.counts = counts;
	}
	
	public String getPackageSize() {
		return packageSize;
	}

	public void setPackageSize(String packageSize) {
		this.packageSize = packageSize;
	}
	
	@Length(min=0, max=255, message="产品规格长度必须介于 0 和 255 之间")
	public String getProductSpec() {
		return productSpec;
	}

	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}

    public String getPointCounts() {
        return pointCounts;
    }

    public void setPointCounts(String pointCounts) {
        this.pointCounts = pointCounts;
    }
}