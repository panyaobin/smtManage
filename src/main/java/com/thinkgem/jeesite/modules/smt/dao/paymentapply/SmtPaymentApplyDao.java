/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.dao.paymentapply;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.smt.entity.paymentapply.SmtPaymentApply;
import com.thinkgem.jeesite.modules.smt.vo.SmtPaymentApplyVO;
import org.apache.ibatis.annotations.Param;

/**
 * 付款申请DAO接口
 * @author popo
 * @version 2019-03-10
 */
@MyBatisDao
public interface SmtPaymentApplyDao extends CrudDao<SmtPaymentApply> {
    /**
     * 根据ID查询付款申请集合VO
     * @param id
     * @return
     */
    SmtPaymentApplyVO getSmtPaymentApplyVoById(@Param("id") String id);
}