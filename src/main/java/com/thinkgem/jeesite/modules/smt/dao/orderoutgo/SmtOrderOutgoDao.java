/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.dao.orderoutgo;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.smt.entity.orderoutgo.SmtOrderOutgo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 产品出货表DAO接口
 * @author popo
 * @version 2018-06-10
 */
@MyBatisDao
public interface SmtOrderOutgoDao extends CrudDao<SmtOrderOutgo> {
	String getExistOutgoOrderNo(@Param("customerNo") String customerNo);

    /**
     * 查询退货列表
     * @param smtOrderOutgo
     * @return
     */
	List<SmtOrderOutgo> findReturnGoodsPage(SmtOrderOutgo smtOrderOutgo);
}