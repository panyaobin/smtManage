/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.service.orderentry;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.smt.dao.orderentry.SmtOrderEntryDao;
import com.thinkgem.jeesite.modules.smt.entity.orderentry.SmtOrderEntry;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * smt订单入库表Service
 *
 * @author popo
 * @version 2018-06-09
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SmtOrderEntryService extends CrudService<SmtOrderEntryDao, SmtOrderEntry> {

    @Autowired
    private SmtOrderEntryDao smtOrderEntryDao;

    public SmtOrderEntry get(String id) {
        return super.get(id);
    }

    public List<SmtOrderEntry> findList(SmtOrderEntry smtOrderEntry) {
        return super.findList(smtOrderEntry);
    }

    public Page<SmtOrderEntry> findPage(Page<SmtOrderEntry> page, SmtOrderEntry smtOrderEntry) {
        return super.findPage(page, smtOrderEntry);
    }

    /**
     * 电子料待出货
     * @param page 分页对象
     * @param smtOrderEntry
     * @return
     */
    public Page<SmtOrderEntry> findDzlMainPage(Page<SmtOrderEntry> page, SmtOrderEntry smtOrderEntry) {
        smtOrderEntry.setPage(page);
        page.setList(dao.findDzlMainPage(smtOrderEntry));
        return page;
    }

    /**
     * 主信息
     * @param page 分页对象
     * @param smtOrderEntry
     * @return
     */
    public Page<SmtOrderEntry> findMainPage(Page<SmtOrderEntry> page, SmtOrderEntry smtOrderEntry) {
        smtOrderEntry.setPage(page);
        page.setList(dao.findMainList(smtOrderEntry));
        return page;
    }


    public Page<SmtOrderEntry> findTotalPage(Page<SmtOrderEntry> page, SmtOrderEntry smtOrderEntry) {
        smtOrderEntry.setPage(page);
        page.setList(dao.findTotalPageList(smtOrderEntry));
        return page;
    }

    /**
     * 在线FPC待出货导出查询数据
     * @param smtOrderEntry
     * @return
     */
    public List<SmtOrderEntry> findTotalLists(SmtOrderEntry smtOrderEntry) {
       return dao.findTotalPageList(smtOrderEntry);
    }
    /**
     * 在线电子料待出货导出查询数据
     * @param smtOrderEntry
     * @return
     */
    public List<SmtOrderEntry> findTotalDzlLists(SmtOrderEntry smtOrderEntry) {
       return dao.findDzlMainPage(smtOrderEntry);
    }

    public Page<SmtOrderEntry> findPages(Page<SmtOrderEntry> page, SmtOrderEntry smtOrderEntry) {
        smtOrderEntry.setPage(page);
        page.setList(dao.findLists(smtOrderEntry));
        return page;
    }

    /**
     * 导出查询列表使用
     *
     * @param smtOrderEntry
     * @return
     */
    public List<SmtOrderEntry> findLists(SmtOrderEntry smtOrderEntry) {
        return dao.findLists(smtOrderEntry);
    }

    /**
     * 可退货列表
     *
     * @param page
     * @param smtOrderEntry
     * @return
     */
    public Page<SmtOrderEntry> findReturnGoods(Page<SmtOrderEntry> page, SmtOrderEntry smtOrderEntry) {
        smtOrderEntry.setPage(page);
        page.setList(dao.findReturnGoodsList(smtOrderEntry));
        return page;
    }

    /**
     * 获取退货信息
     *
     * @param smtOrderEntry
     * @return
     */
    public List<SmtOrderEntry> findReturnGoodsList(SmtOrderEntry smtOrderEntry) {
        return dao.findReturnGoodsList(smtOrderEntry);
    }


    /*这里是选择发货后根据ID查询订单信息*/
    public List<SmtOrderEntry> findSendRecord(SmtOrderEntry smtOrderEntry) {
        return dao.findLists(smtOrderEntry);
    }

    @Transactional(readOnly = false)
    public void save(SmtOrderEntry smtOrderEntry) {
        super.save(smtOrderEntry);
    }

    @Transactional(readOnly = false)
    public void delete(SmtOrderEntry smtOrderEntry) {
        smtOrderEntry.preUpdate();
        super.delete(smtOrderEntry);
    }

    /**
     * 批量新增入库单
     *
     * @param smtOrderEntry
     */
    @Transactional(readOnly = false)
    public void act_save(SmtOrderEntry smtOrderEntry) {
        if (null != smtOrderEntry && StringUtils.isNotBlank(smtOrderEntry.getProductType())) {
            String[] productType = smtOrderEntry.getProductType().split(",");
            String[] productNo = smtOrderEntry.getProductNo().split(",");
            String[] counts = smtOrderEntry.getCounts().split(",");
            String[] remarks = {};
            if (StringUtils.isNotBlank(smtOrderEntry.getRemarks())) {
                remarks = smtOrderEntry.getRemarks().split(",");//拆分备注
            }
            for (int i = 0; i < productType.length; i++) {
                SmtOrderEntry entry = new SmtOrderEntry();
                entry.setCustomerNo(smtOrderEntry.getCustomerNo());
                entry.setOrderNo(smtOrderEntry.getOrderNo());
                entry.setOrderDate(smtOrderEntry.getOrderDate() == null ? new Date() : smtOrderEntry.getOrderDate());
                entry.setProductType(productType[i]);
                entry.setProductNo(productNo[i]);
                entry.setCounts(counts[i]);
                if (remarks.length > 0 && remarks.length > i && StringUtils.isNotBlank(remarks[i])) {
                    entry.setRemarks(remarks[i]);
                } else {
                    entry.setRemarks("");
                }
                entry.setCreateBy(UserUtils.getUser());
                entry.setCreateDate(new Date());
                entry.setUpdateBy(UserUtils.getUser());
                entry.setUpdateDate(new Date());
                smtOrderEntryDao.insert(entry);
            }
        }
    }

    /**
     * 通过ID查找产品入库信息和点数
     *
     * @param id
     * @return
     */
    public SmtOrderEntry findOrderInfoById(String id) {
        return dao.findOrderInfoById(id);
    }
}