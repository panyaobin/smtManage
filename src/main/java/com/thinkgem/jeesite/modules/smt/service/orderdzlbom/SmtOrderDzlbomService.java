/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.service.orderdzlbom;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.smt.dao.orderdzlbom.SmtOrderDzlbomDao;
import com.thinkgem.jeesite.modules.smt.entity.orderdzlbom.SmtOrderDzlbom;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 电子料物料bomService
 *
 * @author popo
 * @version 2018-06-10
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SmtOrderDzlbomService extends CrudService<SmtOrderDzlbomDao, SmtOrderDzlbom> {

    public SmtOrderDzlbom get(String id) {
        return super.get(id);
    }

    public List<SmtOrderDzlbom> findList(SmtOrderDzlbom smtOrderDzlbom) {
        return super.findList(smtOrderDzlbom);
    }

    public Page<SmtOrderDzlbom> findPage(Page<SmtOrderDzlbom> page, SmtOrderDzlbom smtOrderDzlbom) {
        return super.findPage(page, smtOrderDzlbom);
    }

    @Transactional(readOnly = false)
    public void save(SmtOrderDzlbom smtOrderDzlbom) {
        super.save(smtOrderDzlbom);
    }

    @Transactional(readOnly = false)
    public void delete(SmtOrderDzlbom smtOrderDzlbom) {
        super.delete(smtOrderDzlbom);
    }

    /**
     * 通过客户编码和产品型号查询对应的电子料信息
     *
     * @param customerNo
     * @param productNo
     * @return
     */
    public List<SmtOrderDzlbom> findDzlDetail(String customerNo,String productNo) {
        return dao.findDzlDetail(customerNo, productNo);
    }

    /**
     * 验证是否存在bom
     * @param bomName
     * @return
     */
    public int validateBomName(String bomName){
        return dao.validateBomName(bomName);
    }
}