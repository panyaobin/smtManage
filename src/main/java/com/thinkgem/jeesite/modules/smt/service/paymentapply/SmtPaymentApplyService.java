/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.service.paymentapply;

import java.util.List;

import com.thinkgem.jeesite.modules.smt.vo.SmtPaymentApplyVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.smt.entity.paymentapply.SmtPaymentApply;
import com.thinkgem.jeesite.modules.smt.dao.paymentapply.SmtPaymentApplyDao;

/**
 * 付款申请Service
 * @author popo
 * @version 2019-03-10
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class SmtPaymentApplyService extends CrudService<SmtPaymentApplyDao, SmtPaymentApply> {

	public SmtPaymentApply get(String id) {
		return super.get(id);
	}
	
	public List<SmtPaymentApply> findList(SmtPaymentApply smtPaymentApply) {
		return super.findList(smtPaymentApply);
	}
	
	public Page<SmtPaymentApply> findPage(Page<SmtPaymentApply> page, SmtPaymentApply smtPaymentApply) {
		return super.findPage(page, smtPaymentApply);
	}
	
	@Transactional(readOnly = false)
	public void save(SmtPaymentApply smtPaymentApply) {
		super.save(smtPaymentApply);
	}
	
	@Transactional(readOnly = false)
	public void delete(SmtPaymentApply smtPaymentApply) {
		super.delete(smtPaymentApply);
	}


	/**
	 * 根据ID查询付款申请集合VO
	 * @param id
	 * @return
	 */
	public SmtPaymentApplyVO getSmtPaymentApplyVoById(String id){
		return dao.getSmtPaymentApplyVoById(id);
	}
}