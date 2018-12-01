/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.service.orderoutgo;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.smt.dao.orderoutgo.SmtOrderOutgoDao;
import com.thinkgem.jeesite.modules.smt.dao.orderoutgodzl.SmtOrderOutgoDzlDao;
import com.thinkgem.jeesite.modules.smt.entity.orderoutgo.SmtOrderOutgo;
import com.thinkgem.jeesite.modules.smt.entity.orderoutgo.SmtOrderOutgoVO;
import com.thinkgem.jeesite.modules.smt.entity.orderoutgodzl.SmtOrderOutgoDzl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 产品出货表Service
 *
 * @author popo
 * @version 2018-06-10
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SmtOrderOutgoService extends CrudService<SmtOrderOutgoDao, SmtOrderOutgo> {

    @Autowired
    private SmtOrderOutgoDzlDao smtOrderOutgoDzlDao;
    
    public SmtOrderOutgo get(String id) {
        return super.get(id);
    }

    public List<SmtOrderOutgo> findList(SmtOrderOutgo smtOrderOutgo) {
        return super.findList(smtOrderOutgo);
    }

    public Page<SmtOrderOutgo> findPage(Page<SmtOrderOutgo> page, SmtOrderOutgo smtOrderOutgo) {
        return super.findPage(page, smtOrderOutgo);
    }

    /**
     * 查询退货列表
     * @param page
     * @param smtOrderOutgo
     * @return
     */
    public Page<SmtOrderOutgo> findReturnGoodsPage(Page<SmtOrderOutgo> page, SmtOrderOutgo smtOrderOutgo) {
        smtOrderOutgo.setPage(page);
        page.setList(dao.findReturnGoodsPage(smtOrderOutgo));
        return page;
    }

    @Transactional(readOnly = false)
    public void save(SmtOrderOutgo smtOrderOutgo) {
        super.save(smtOrderOutgo);
    }

    @Transactional(readOnly = false)
    public void delete(SmtOrderOutgo smtOrderOutgo) {
        super.delete(smtOrderOutgo);
    }

    /**
     * 产品出货信息记录
     * @param smtOrderOutgo
     * @param jsonArray
     */
    @Transactional(readOnly = false)
    public String to_outgo(SmtOrderOutgo smtOrderOutgo, JSONArray jsonArray){
        //先保存 订单出货量
        smtOrderOutgo.preInsert();
        smtOrderOutgo.setPointCounts(smtOrderOutgo.getPointCounts()*smtOrderOutgo.getCounts());
        String outNo=autoOutgoNo(smtOrderOutgo.getCustomerNo());
        smtOrderOutgo.setOutgoOrderNo(outNo);
        dao.insert(smtOrderOutgo);
        //接着保存电子料消耗信息量
        for (int i = 0; i <jsonArray.size() ; i++) {
            SmtOrderOutgoDzl outgoDzl = (SmtOrderOutgoDzl) JSONObject.toBean(JSONObject.fromObject(jsonArray.get(i)), SmtOrderOutgoDzl.class);
            outgoDzl.setFId(smtOrderOutgo.getId());
            outgoDzl.setDzlId(outgoDzl.getBomName());
            outgoDzl.setCustomerNo(smtOrderOutgo.getCustomerNo());
            outgoDzl.setId("");
            outgoDzl.preInsert();
            smtOrderOutgoDzlDao.insert(outgoDzl);
        }
        return outNo;
    }

    /**
     * 根据客户代码，查询是否已经存在出货单号
     * @param customerNo
     */
    public String autoOutgoNo(String customerNo){
        String outgoOrderNo;
        //根据客户代码，验证是否存在单号，存在继续递增，不存在从 00001开始
        String no=dao.getExistOutgoOrderNo(customerNo);
        
        if (StringUtils.isNotBlank(no)){
            outgoOrderNo=String.valueOf(Integer.valueOf(no)+1);
        }else{
            outgoOrderNo=customerNo+"00001";
        }
        return outgoOrderNo;
    }
    
    
    /**
     *  撤销FPC出货，删除对应的电子料损耗
     * @param smtOrderOutgo
     */
    @Transactional(readOnly = false)
    public void to_delete(SmtOrderOutgo smtOrderOutgo){
        dao.delete(smtOrderOutgo);
        smtOrderOutgoDzlDao.cancle_outgo(smtOrderOutgo.getId());
    }


    /**
     * 导出数据
     * @param dzl
     * @return
     */
    public List<SmtOrderOutgoDzl> export(SmtOrderOutgoDzl dzl){
        return smtOrderOutgoDzlDao.findList(dzl);
    }


    /**
     * 通过退货单号查询退货单
     * @param outgoOrderNo
     * @return
     */
    public List<SmtOrderOutgoVO> selectByOutGoOrderNo(String outgoOrderNo){
        return dao.selectByOutGoOrderNo(outgoOrderNo);
    }
}