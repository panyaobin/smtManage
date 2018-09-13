/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.dao.syscustomer;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.smt.entity.syscustomer.SmtSysCustomer;

/**
 * 客户信息管理DAO接口
 * @author popo
 * @version 2018-06-10
 */
@MyBatisDao
public interface SmtSysCustomerDao extends CrudDao<SmtSysCustomer> {

}