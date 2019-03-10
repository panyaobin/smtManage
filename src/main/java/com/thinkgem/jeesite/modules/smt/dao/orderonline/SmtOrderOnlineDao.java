/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.dao.orderonline;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.smt.entity.orderonline.SmtOrderOnline;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * smt在线生产表DAO接口
 * @author popo
 * @version 2018-06-09
 */
@MyBatisDao
public interface SmtOrderOnlineDao extends CrudDao<SmtOrderOnline> {

    List<SmtOrderOnline> findLists(SmtOrderOnline smtOrderEntry);

    List<SmtOrderOnline> findProductEntryCount(SmtOrderOnline smtOrderOnline);

    /**
     * 获取最大发料号
     * @return
     */
    String findSendNoFromList();

    /**
     * 根据发料单号查询发料记录
     * @param sendNo
     * @return
     */
    List<SmtOrderOnline> findListBySendNo(@Param("sendNo") String sendNo);
}