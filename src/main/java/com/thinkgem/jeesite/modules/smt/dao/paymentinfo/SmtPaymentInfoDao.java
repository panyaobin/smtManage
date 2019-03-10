/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.dao.paymentinfo;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.smt.entity.paymentinfo.SmtPaymentInfo;

/**
 * 付款信息DAO接口
 * @author popo
 * @version 2019-03-10
 */
@MyBatisDao
public interface SmtPaymentInfoDao extends CrudDao<SmtPaymentInfo> {
	
}