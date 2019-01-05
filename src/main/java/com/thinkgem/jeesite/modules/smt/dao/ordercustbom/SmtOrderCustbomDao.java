/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.dao.ordercustbom;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.smt.entity.ordercustbom.SmtOrderCustbom;
import com.thinkgem.jeesite.modules.smt.vo.SmtOrderCustBomVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户BOMDAO接口
 * @author popo
 * @version 2018-06-10
 */
@MyBatisDao
public interface SmtOrderCustbomDao extends CrudDao<SmtOrderCustbom> {
    /**
     * 批量新增
     * @param smtOrderCustboms
     */
     void batchInsert(List<SmtOrderCustbom> smtOrderCustboms);

    /**
     * 查询明细信息
     * @return
     */
     List<SmtOrderCustBomVO> findCustBomDetailById(SmtOrderCustbom bom);

    /**
     * 验证客户bom是否存在
     * @param productNo
     * @param customerNo
     * @return
     */
     int validateProductNo(@Param("productNo") String productNo,@Param("customerNo") String customerNo);

    /**
     * 查询所有的客户BOM列表信息，主表带明细
     * @param custbom
     * @return
     */
     List<SmtOrderCustbom> findOrderCustBomListWithDetail(SmtOrderCustbom custbom);

    /**
     * 查询客户bom用料信息
     * @param bom
     * @return
     */
    List<SmtOrderCustbom> findOrderCustbomList(@Param("bom") SmtOrderCustbom bom);

    
}