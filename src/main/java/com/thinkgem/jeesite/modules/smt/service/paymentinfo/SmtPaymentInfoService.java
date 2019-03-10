/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.service.paymentinfo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.smt.entity.paymentinfo.SmtPaymentInfo;
import com.thinkgem.jeesite.modules.smt.dao.paymentinfo.SmtPaymentInfoDao;

/**
 * 付款信息Service
 * @author popo
 * @version 2019-03-10
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class SmtPaymentInfoService extends CrudService<SmtPaymentInfoDao, SmtPaymentInfo> {

	public SmtPaymentInfo get(String id) {
		return super.get(id);
	}
	
	public List<SmtPaymentInfo> findList(SmtPaymentInfo smtPaymentInfo) {
		return super.findList(smtPaymentInfo);
	}
	
	public Page<SmtPaymentInfo> findPage(Page<SmtPaymentInfo> page, SmtPaymentInfo smtPaymentInfo) {
		return super.findPage(page, smtPaymentInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SmtPaymentInfo smtPaymentInfo) {
		super.save(smtPaymentInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(SmtPaymentInfo smtPaymentInfo) {
		super.delete(smtPaymentInfo);
	}
	
}