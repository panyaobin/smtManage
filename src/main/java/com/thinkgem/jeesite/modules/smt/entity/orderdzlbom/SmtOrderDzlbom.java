/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.entity.orderdzlbom;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 电子料物料bomEntity
 * @author popo
 * @version 2018-06-10
 */
public class SmtOrderDzlbom extends DataEntity<SmtOrderDzlbom> {
	
	private static final long serialVersionUID = 1L;
	private String bomName;		// 物料型号
	private String packageSize;		// 封装尺寸
	private String bomType;		// 物料类型
	private String funcValue;		// 功能值
	private String voltage;		// 电压
	private String business;		// 供应商
    
    private String counts;         //总用量数
    private String stockCounts;    //备品数
	
	public SmtOrderDzlbom() {
		super();
	}

	public SmtOrderDzlbom(String id){
		super(id);
	}

	@Length(min=0, max=255, message="物料型号长度必须介于 0 和 255 之间")
	public String getBomName() {
		return bomName;
	}

	public void setBomName(String bomName) {
		this.bomName = bomName;
	}
	
	@Length(min=0, max=255, message="封装尺寸长度必须介于 0 和 255 之间")
	public String getPackageSize() {
		return packageSize;
	}

	public void setPackageSize(String packageSize) {
		this.packageSize = packageSize;
	}
	
	@Length(min=0, max=255, message="物料类型长度必须介于 0 和 255 之间")
	public String getBomType() {
		return bomType;
	}

	public void setBomType(String bomType) {
		this.bomType = bomType;
	}
	
	@Length(min=0, max=255, message="功能值长度必须介于 0 和 255 之间")
	public String getFuncValue() {
		return funcValue;
	}

	public void setFuncValue(String funcValue) {
		this.funcValue = funcValue;
	}
	
	@Length(min=0, max=255, message="电压长度必须介于 0 和 255 之间")
	public String getVoltage() {
		return voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}
	
	@Length(min=0, max=255, message="供应商长度必须介于 0 和 255 之间")
	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
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