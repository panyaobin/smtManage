/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.service.orderonline;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.smt.dao.orderonline.SmtOrderOnlineDao;
import com.thinkgem.jeesite.modules.smt.entity.orderonline.SmtOrderOnline;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * smt在线生产表Service
 *
 * @author popo
 * @version 2018-06-09
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SmtOrderOnlineService extends CrudService<SmtOrderOnlineDao, SmtOrderOnline> {

    public SmtOrderOnline get(String id) {
        return super.get(id);
    }

    public List<SmtOrderOnline> findList(SmtOrderOnline smtOrderOnline) {
        return super.findList(smtOrderOnline);
    }

    public Page<SmtOrderOnline> findPage(Page<SmtOrderOnline> page, SmtOrderOnline smtOrderOnline) {
        return super.findPage(page, smtOrderOnline);
    }

    public Page<SmtOrderOnline> findPages(Page<SmtOrderOnline> page, SmtOrderOnline smtOrderEntry) {
        smtOrderEntry.setPage(page);
        page.setList(dao.findLists(smtOrderEntry));
        return page;
    }

    /**
     * 在线结存导出查询数据
     * @param smtOrderEntry
     * @return
     */
    public List<SmtOrderOnline> findLists(SmtOrderOnline smtOrderEntry) {
        return dao.findLists(smtOrderEntry);
    }

    @Transactional(readOnly = false)
    public void save(SmtOrderOnline smtOrderOnline) {
        super.save(smtOrderOnline);
    }

    @Transactional(readOnly = false)
    public void delete(SmtOrderOnline smtOrderOnline) {
        super.delete(smtOrderOnline);
    }

    /**
     * 发料记录信息
     *
     * @param smtOrderOnline
     */
    @Transactional(readOnly = false)
    public int to_save_send(SmtOrderOnline smtOrderOnline) {
        //首先查询已经发料的发料号，如果没有从00001
        String sendNos = dao.findSendNoFromList();
        int sendNo=0;
        if (StringUtils.isBlank(sendNos)){
            sendNo=100000001;
        }else {
            sendNo=Integer.valueOf(sendNos)+1; 
        }
        //产品编号，订单号，产品类型，客户代码，发料数量，备注
        String[] proNo = smtOrderOnline.getProductNo().split(",");
        String[] orderNo = smtOrderOnline.getOrderNo().split(",");
        String[] proType = smtOrderOnline.getProductType().split(",");
        String[] custNo = smtOrderOnline.getCustomerNo().split(",");
        String[] counts = smtOrderOnline.getCounts().split(",");
        String[] remarks = null;
        if (StringUtils.isNotBlank(smtOrderOnline.getRemarks())) {
            remarks = smtOrderOnline.getRemarks().split(",");
        }

        for (int i = 0; i < proNo.length; i++) {
            SmtOrderOnline online = new SmtOrderOnline();
            online.setOrderNo(orderNo[i]);
            online.setSendNo(sendNo);
            online.setCustomerNo(custNo[i]);
            online.setProductNo(proNo[i]);
            online.setCounts(counts[i]);
            online.setProductType(proType[i]);
            online.preInsert();
            if (remarks.length > 0 && i<remarks.length) {
                online.setRemarks(remarks[i]==null?"":remarks[i]);
            }
            dao.insert(online);
        }
        return sendNo;
    }

    /**
     * 查询产品入库数量
     *
     * @param smtOrderOnline
     * @return
     */
    public List<SmtOrderOnline> findProductEntryCount(SmtOrderOnline smtOrderOnline) {
        return dao.findProductEntryCount(smtOrderOnline);
    }
}