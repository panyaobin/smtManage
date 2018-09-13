/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.dao.orderentry;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.smt.entity.orderentry.SmtOrderEntry;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * smt订单入库表DAO接口
 * @author popo
 * @version 2018-06-09
 */
@MyBatisDao
public interface SmtOrderEntryDao extends CrudDao<SmtOrderEntry> {

    List<SmtOrderEntry> findLists(SmtOrderEntry smtOrderEntry);

    /**
     * 通过ID查找产品入库信息和点数
     * @param id
     * @return
     */
    SmtOrderEntry findOrderInfoById(@Param("id") String id);

    /**
     * 出货列表 订单出货数据统计
     * @param smtOrderEntry
     * @return
     */
    List<SmtOrderEntry> findTotalPageList(SmtOrderEntry smtOrderEntry);
   
    /**
     * 查询可退货列表
     * @param smtOrderEntry
     * @return
     */
    List<SmtOrderEntry> findReturnGoodsList(SmtOrderEntry smtOrderEntry);

    /**
     * 主列表信息
     * @param smtOrderEntry
     * @return
     */
    List<SmtOrderEntry> findMainList(SmtOrderEntry smtOrderEntry);

    /**
     * 电子料待出货列表
     * @param smtOrderEntry
     * @return
     */
    List<SmtOrderEntry> findDzlMainPage(SmtOrderEntry smtOrderEntry);
}