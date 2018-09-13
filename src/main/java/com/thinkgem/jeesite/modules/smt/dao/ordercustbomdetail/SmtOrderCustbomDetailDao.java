/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.dao.ordercustbomdetail;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.smt.entity.ordercustbomdetail.SmtOrderCustbomDetail;

/**
 * 客户BOM明细表DAO接口
 * @author popo
 * @version 2018-06-16
 */
@MyBatisDao
public interface SmtOrderCustbomDetailDao extends CrudDao<SmtOrderCustbomDetail> {

    /**
     * 删除bom子表明细信息
     * @param custbomDetail
     */
    void act_delete(SmtOrderCustbomDetail custbomDetail);
}