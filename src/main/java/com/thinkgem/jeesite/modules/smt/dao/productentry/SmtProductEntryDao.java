/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.dao.productentry;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.smt.entity.productentry.SmtProductEntry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 成品入库表DAO接口
 * @author popo
 * @version 2018-06-10
 */
@MyBatisDao
public interface SmtProductEntryDao extends CrudDao<SmtProductEntry> {
    /**
     * 查询最大的入库单号
     * @return
     */
	String findOrderNoFromList();

    /**
     *
     * @param name
     * @return
     */
    String queryTypeByName(@Param("name") String name);

    /**
     *  根据入库单号重新打印
     * @param orderNo
     * @return
     */
    List<SmtProductEntry> selectListByOrderNo(@Param("orderNo") String orderNo);
}