/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.service.orderoutgodzl;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.smt.dao.orderoutgodzl.SmtOrderOutgoDzlDao;
import com.thinkgem.jeesite.modules.smt.entity.orderoutgodzl.SmtOrderOutgoDzl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 电子料出货信息表Service
 * @author popo
 * @version 2018-07-14
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class SmtOrderOutgoDzlService extends CrudService<SmtOrderOutgoDzlDao, SmtOrderOutgoDzl> {

	public SmtOrderOutgoDzl get(String id) {
		return super.get(id);
	}
	
	public List<SmtOrderOutgoDzl> findList(SmtOrderOutgoDzl smtOrderOutgoDzl) {
		return super.findList(smtOrderOutgoDzl);
	}
	
	public Page<SmtOrderOutgoDzl>findPage(Page<SmtOrderOutgoDzl> page, SmtOrderOutgoDzl smtOrderOutgoDzl) {
		return super.findPage(page, smtOrderOutgoDzl);
	}
	
	@Transactional(readOnly = false)
	public void save(SmtOrderOutgoDzl smtOrderOutgoDzl) {
		super.save(smtOrderOutgoDzl);
	}
	
	@Transactional(readOnly = false)
	public void delete(SmtOrderOutgoDzl smtOrderOutgoDzl) {
		super.delete(smtOrderOutgoDzl);
	}


    /**
     * 通过FPC出货编号查询电子料出货信息
     * @param outgoId
     * @return
     */
	public List<SmtOrderOutgoDzl> selectByOutGoId(String outgoId){
	    return dao.selectByOutGoId(outgoId);
    }
	
}