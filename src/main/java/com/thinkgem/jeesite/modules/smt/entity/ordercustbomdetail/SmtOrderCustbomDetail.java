/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.entity.ordercustbomdetail;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 客户BOM明细表Entity
 * @author popo
 * @version 2018-06-16
 */
public class SmtOrderCustbomDetail extends DataEntity<SmtOrderCustbomDetail> {
	
	private static final long serialVersionUID = 1L;
	private String custbomId;		// 客户BOM主表ID
	private String dzlbomId;		// 电子料ID
	private String piecePosition;		// 件位
	private String counts;		// 用量
	private String stockCounts;		// 备品
	
	public SmtOrderCustbomDetail() {
		super();
	}

	public SmtOrderCustbomDetail(String id){
		super(id);
	}

	@Length(min=0, max=32, message="客户BOM主表ID长度必须介于 0 和 32 之间")
	public String getCustbomId() {
		return custbomId;
	}

	public void setCustbomId(String custbomId) {
		this.custbomId = custbomId;
	}
	
	@Length(min=0, max=11, message="电子料ID长度必须介于 0 和 11 之间")
	public String getDzlbomId() {
		return dzlbomId;
	}

	public void setDzlbomId(String dzlbomId) {
		this.dzlbomId = dzlbomId;
	}
	
	@Length(min=0, max=255, message="件位长度必须介于 0 和 255 之间")
	public String getPiecePosition() {
		return piecePosition;
	}

	public void setPiecePosition(String piecePosition) {
		this.piecePosition = piecePosition;
	}
	
	@Length(min=0, max=255, message="用量长度必须介于 0 和 255 之间")
	public String getCounts() {
		return counts;
	}

	public void setCounts(String counts) {
		this.counts = counts;
	}
	
	@Length(min=0, max=255, message="备品长度必须介于 0 和 255 之间")
	public String getStockCounts() {
		return stockCounts;
	}

	public void setStockCounts(String stockCounts) {
		this.stockCounts = stockCounts;
	}
	
}