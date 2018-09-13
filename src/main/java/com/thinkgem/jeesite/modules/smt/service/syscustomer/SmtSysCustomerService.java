/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.service.syscustomer;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.smt.entity.syscustomer.SmtSysCustomer;
import com.thinkgem.jeesite.modules.smt.dao.syscustomer.SmtSysCustomerDao;

/**
 * 客户信息管理Service
 * @author popo
 * @version 2018-06-10
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class SmtSysCustomerService extends CrudService<SmtSysCustomerDao, SmtSysCustomer> {

	public SmtSysCustomer get(String id) {
		return super.get(id);
	}
	
	public List<SmtSysCustomer> findList(SmtSysCustomer smtSysCustomer) {
		return super.findList(smtSysCustomer);
	}
	
	public Page<SmtSysCustomer> findPage(Page<SmtSysCustomer> page, SmtSysCustomer smtSysCustomer) {
		return super.findPage(page, smtSysCustomer);
	}
	
	@Transactional(readOnly = false)
	public void save(SmtSysCustomer smtSysCustomer) {
		super.save(smtSysCustomer);
	}
	
	@Transactional(readOnly = false)
	public void delete(SmtSysCustomer smtSysCustomer) {
		super.delete(smtSysCustomer);
	}

}