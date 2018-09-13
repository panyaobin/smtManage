/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.dao.orderdzlbom;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.smt.entity.orderdzlbom.SmtOrderDzlbom;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 电子料物料bomDAO接口
 *
 * @author popo
 * @version 2018-06-10
 */
@MyBatisDao
public interface SmtOrderDzlbomDao extends CrudDao<SmtOrderDzlbom> {

    List<SmtOrderDzlbom> findDzlDetail(@Param("customerNo") String customerNo, @Param("productNo") String productNo);
    
    int validateBomName(@Param("bomName") String bomName);
}   