/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.entity.modalEntity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.smt.entity.orderoutgo.OutGoVO;
import com.thinkgem.jeesite.modules.smt.entity.productentry.SmtProductEntry;

import java.util.List;

/**
 * 成品入库表Entity
 * @author popo
 * @version 2018-06-10
 */
public class SmtModalProductEntry extends DataEntity<SmtModalProductEntry> {
    
    private SmtProductEntry productEntry;
    
    private List<OutGoVO> outGoVOList;

    public SmtProductEntry getProductEntry() {
        return productEntry;
    }

    public void setProductEntry(SmtProductEntry productEntry) {
        this.productEntry = productEntry;
    }

    public List<OutGoVO> getOutGoVOList() {
        return outGoVOList;
    }

    public void setOutGoVOList(List<OutGoVO> outGoVOList) {
        this.outGoVOList = outGoVOList;
    }
}