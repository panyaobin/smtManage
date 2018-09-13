/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.service.ordercustbomdetail;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.smt.entity.ordercustbomdetail.SmtOrderCustbomDetail;
import com.thinkgem.jeesite.modules.smt.dao.ordercustbomdetail.SmtOrderCustbomDetailDao;

/**
 * 客户BOM明细表Service
 * @author popo
 * @version 2018-06-16
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class SmtOrderCustbomDetailService extends CrudService<SmtOrderCustbomDetailDao, SmtOrderCustbomDetail> {

	public SmtOrderCustbomDetail get(String id) {
		return super.get(id);
	}
	
	public List<SmtOrderCustbomDetail> findList(SmtOrderCustbomDetail smtOrderCustbomDetail) {
		return super.findList(smtOrderCustbomDetail);
	}
	
	public Page<SmtOrderCustbomDetail> findPage(Page<SmtOrderCustbomDetail> page, SmtOrderCustbomDetail smtOrderCustbomDetail) {
		return super.findPage(page, smtOrderCustbomDetail);
	}
	
	@Transactional(readOnly = false)
	public void save(SmtOrderCustbomDetail smtOrderCustbomDetail) {
		super.save(smtOrderCustbomDetail);
	}
	
	@Transactional(readOnly = false)
	public void delete(SmtOrderCustbomDetail smtOrderCustbomDetail) {
		super.delete(smtOrderCustbomDetail);
	}
	
}