/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.dao.orderoutgodzl;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.smt.entity.orderoutgodzl.SmtOrderOutgoDzl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 电子料出货信息表DAO接口
 * @author popo
 * @version 2018-07-14
 */
@MyBatisDao
public interface SmtOrderOutgoDzlDao extends CrudDao<SmtOrderOutgoDzl> {
	void cancle_outgo(@Param("fId") String fId);

    /**
     * 通过FPC出货编号查询电子料出货信息
     * @param outgoId
     * @return
     */
    List<SmtOrderOutgoDzl> selectByOutGoId(@Param("outgoId") String outgoId);
}