/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.service.productentry;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.smt.dao.orderoutgo.SmtOrderOutgoDao;
import com.thinkgem.jeesite.modules.smt.dao.productentry.SmtProductEntryDao;
import com.thinkgem.jeesite.modules.smt.entity.orderonline.SmtOrderOnline;
import com.thinkgem.jeesite.modules.smt.entity.orderoutgo.SmtOrderOutgo;
import com.thinkgem.jeesite.modules.smt.entity.productentry.SmtProductEntry;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 成品入库表Service
 *
 * @author popo
 * @version 2018-06-10
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SmtProductEntryService extends CrudService<SmtProductEntryDao, SmtProductEntry> {

    @Autowired
    private SmtOrderOutgoDao smtOrderOutgoDao;
    
    public SmtProductEntry get(String id) {
        return super.get(id);
    }

    public List<SmtProductEntry> findList(SmtProductEntry smtProductEntry) {
        return super.findList(smtProductEntry);
    }

    public Page<SmtProductEntry> findPage(Page<SmtProductEntry> page, SmtProductEntry smtProductEntry) {
        return super.findPage(page, smtProductEntry);
    }

    @Transactional(readOnly = false)
    public void save(SmtProductEntry smtProductEntry) {
        super.save(smtProductEntry);
    }

    @Transactional(readOnly = false)
    public void delete(SmtProductEntry smtProductEntry) {
        super.delete(smtProductEntry);
    }


    /**
     * 在线批量成品入库
     *
     * @param smtOrderOnline
     */
    @Transactional(readOnly = false)
    public int to_save(SmtOrderOnline smtOrderOnline) {
        //保存结存的时候生成入库号，如果没有200000001开始
        String orderNos = dao.findOrderNoFromList();
        int orderNo = 0;
        if (StringUtils.isBlank(orderNos)) {
            orderNo = 200000001;
        } else {
            orderNo = Integer.valueOf(orderNos) + 1;
        }

        /*产品编号，订单号，产品类型，客户代码，发料数量，备注*/
        String[] proNo = smtOrderOnline.getProductNo().split(",");
        String[] proType = smtOrderOnline.getProductType().split(",");
        String[] custNo = smtOrderOnline.getCustomerNo().split(",");
        String[] counts = smtOrderOnline.getCounts().split(",");
        String[] remarks = null;

        if (StringUtils.isNotBlank(smtOrderOnline.getRemarks())) {
            remarks = smtOrderOnline.getRemarks().split(",");
        }
        

        for (int i = 0; i < proNo.length; i++) {
            SmtProductEntry entry = new SmtProductEntry();
            entry.setCustomerNo(custNo[i]);
            entry.setProductNo(proNo[i]);
            entry.setOrderNo(orderNo);
            entry.setEntryCounts(counts[i]);
            entry.setProductType(proType[i]);
            entry.preInsert();
            if (remarks.length > 0 && i < remarks.length) {
                if (com.thinkgem.jeesite.common.utils.StringUtils.isNotBlank(remarks[i])) {
                    entry.setRemarks(remarks[i]);
                }
            }
            dao.insert(entry);
        }
        return orderNo;
    }


    /**
     * 退后保存数据
     *
     * @param smtOrderOnline
     */
    @Transactional(readOnly = false)
    public int to_save_return(SmtOrderOnline smtOrderOnline) {
        //保存结存的时候生成入库号，如果没有200000001开始
        String returnNo ="";
        int returnNos =999110000;
        /*产品编号，订单号，产品类型，客户代码，发料数量，备注*/
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
            SmtOrderOutgo entry = new SmtOrderOutgo();
            entry.setCustomerNo(custNo[i]);
            entry.setProductNo(proNo[i]);
            entry.setOrderNo(orderNo[i]);
            entry.setOutgoOrderNo(String.valueOf(returnNos+i));
            entry.setCounts(Integer.valueOf(counts[i]));
            entry.setProductType(proType[i]);
            entry.preInsert();
            if (remarks.length > 0 && i < remarks.length) {
                if (com.thinkgem.jeesite.common.utils.StringUtils.isNotBlank(remarks[i])) {
                    entry.setRemarks(remarks[i]);
                }
            }
            smtOrderOutgoDao.insert(entry);
        }
        return returnNos;
    }

}