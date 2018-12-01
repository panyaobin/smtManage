/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.service.ordercustbom;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.smt.dao.ordercustbom.SmtOrderCustbomDao;
import com.thinkgem.jeesite.modules.smt.dao.ordercustbomdetail.SmtOrderCustbomDetailDao;
import com.thinkgem.jeesite.modules.smt.entity.ordercustbom.SmtOrderCustbom;
import com.thinkgem.jeesite.modules.smt.entity.ordercustbomdetail.SmtOrderCustbomDetail;
import com.thinkgem.jeesite.modules.smt.vo.SmtOrderCustBomVO;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 客户BOMService
 *
 * @author popo
 * @version 2018-06-10
 */
@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class SmtOrderCustbomService extends CrudService<SmtOrderCustbomDao, SmtOrderCustbom> {

    @Autowired
    private SmtOrderCustbomDao smtOrderCustbomDao;

    @Autowired
    private SmtOrderCustbomDetailDao smtOrderCustbomDetailDao;


    public SmtOrderCustbom get(String id) {
        return super.get(id);
    }

    public List<SmtOrderCustbom> findList(SmtOrderCustbom smtOrderCustbom) {
        return super.findList(smtOrderCustbom);
    }

    public Page<SmtOrderCustbom> findPage(Page<SmtOrderCustbom> page, SmtOrderCustbom smtOrderCustbom) {
        return super.findPage(page, smtOrderCustbom);
    }

    @Transactional(readOnly = false)
    public void save(SmtOrderCustbom smtOrderCustbom) {
        super.save(smtOrderCustbom);
    }

    @Transactional(readOnly = false)
    public void delete(SmtOrderCustbom smtOrderCustbom) {
        super.delete(smtOrderCustbom);
    }

    @Transactional(readOnly = false)
    public void batchInsert(List<SmtOrderCustbom> smtOrderCustboms) {
        smtOrderCustbomDao.batchInsert(smtOrderCustboms);
    }

    /**
     * 验证客户bom型号是否存在
     * @param productNo
     * @return
     */
    public int validateProductNo(String productNo,String customerNo){
        return dao.validateProductNo(productNo,customerNo);
    }
    
    /**
     * 新增客户bom数据信息
     *
     * @param bom    客户bom主表
     * @param detail 客户bom明细表
     */
    @Transactional(readOnly = false)
    public void add_save(SmtOrderCustbom bom, SmtOrderCustbomDetail detail) {
        if (StringUtils.isNotBlank(detail.getDzlbomId())) {
            String[] split = detail.getDzlbomId().split(","); //拆分电子料号
            String[] counts = detail.getCounts().split(","); //拆分用量
            String[] piecePosition = detail.getPiecePosition().split(",");//拆分件位
            String[] stockCounts = detail.getStockCounts().split(",");//拆分备品
            String[] remarks={};
            if (StringUtils.isNotBlank(detail.getRemarks())) {
                remarks = detail.getRemarks().split(",");//拆分备注
            }
            SmtOrderCustbom custbom = new SmtOrderCustbom();
            //头部公共信息
            custbom.setCustomerNo(bom.getCustomerNo());
            custbom.setProductNo(bom.getProductNo());
            custbom.setPointCounts(bom.getPointCounts());

            custbom.setCreateDate(new Date());
            custbom.setCreateBy(UserUtils.getUser());
            custbom.setUpdateDate(new Date());
            custbom.setUpdateBy(UserUtils.getUser());

            smtOrderCustbomDao.insert(custbom);     //保存单头信息

            for (int i = 0; i < split.length; i++) {
                //拆分如果不为空，继续新增
                if (StringUtils.isNotBlank(split[i])) {

                    //下面list信息，可能有多条
                    detail.setCustbomId(custbom.getId());
                    detail.setDzlbomId(split[i]);
                    detail.setCounts(counts[i]);
                    detail.setPiecePosition(piecePosition[i]);
                    detail.setStockCounts(stockCounts[i]);
                    if (remarks.length>0&&remarks.length>i) {
                        detail.setRemarks(remarks[i]);
                    }else{
                        detail.setRemarks("");
                    }
                    detail.setCreateDate(new Date());
                    detail.setCreateBy(UserUtils.getUser());
                    detail.setUpdateDate(new Date());
                    detail.setUpdateBy(UserUtils.getUser());
                    smtOrderCustbomDetailDao.insert(detail);    //保存明细信息
                }
            }
        }
    }

    /**
     * 修改客户bom数据信息
     *
     * @param bom    客户bom主表
     * @param detail 客户bom明细表
     */
    @Transactional(readOnly = false)
    public void add_modify(SmtOrderCustbom bom, SmtOrderCustbomDetail detail) {
        if (StringUtils.isNotBlank(detail.getDzlbomId())) {
            String[] split = detail.getDzlbomId().split(","); //拆分电子料号
            String[] counts = detail.getCounts().split(","); //拆分用量
            String[] piecePosition = detail.getPiecePosition().split(",");//拆分件位
            String[] stockCounts = detail.getStockCounts().split(",");//拆分备品
            String[] remarks={};
            if (StringUtils.isNotBlank(detail.getRemarks())) {
                remarks = detail.getRemarks().split(",");//拆分备注
            }

            SmtOrderCustbom custbom=smtOrderCustbomDao.get(bom.getId());
            //头部公共信息
            custbom.setCustomerNo(bom.getCustomerNo());
            custbom.setProductNo(bom.getProductNo());
            custbom.setPointCounts(bom.getPointCounts());
            custbom.setUpdateDate(new Date());
            custbom.setUpdateBy(UserUtils.getUser());
            smtOrderCustbomDao.update(custbom);     //修改单头信息

            //根据单头ID查询明细信息
            SmtOrderCustbomDetail custbomDetail=new SmtOrderCustbomDetail();
            custbomDetail.setCustbomId(bom.getId());
            List<SmtOrderCustbomDetail> list = smtOrderCustbomDetailDao.findList(custbomDetail);
            if (null!=list&&list.size()>0){
                for (int i = 0; i <list.size() ; i++) {
                    SmtOrderCustbomDetail details=list.get(i);
                    details.setCustbomId(custbom.getId());

                    details.setDzlbomId(split[i]);
                    details.setCounts(counts[i]);
                    details.setPiecePosition(piecePosition[i]);
                    details.setStockCounts(stockCounts[i]);
                    if (remarks.length>0&&remarks.length>i) {
                        details.setRemarks(remarks[i]);
                    }else{
                        details.setRemarks("");
                    }
                    details.setUpdateDate(new Date());
                    details.setUpdateBy(UserUtils.getUser());
                    smtOrderCustbomDetailDao.update(details);    //修改明细信息
                }
            }
        }
    }

    /**
     * 删除bom主表及明细方法
     * @param custbom       bom主表
     * @param custbomDetail bom明细表
     */
    @Transactional(readOnly = false)
    public void act_delete(SmtOrderCustbom custbom,SmtOrderCustbomDetail custbomDetail){
        smtOrderCustbomDao.delete(custbom);//删除主表信息
        custbomDetail.setCustbomId(custbom.getId());
        smtOrderCustbomDetailDao.act_delete(custbomDetail); //删除明细表信息
    }


    /**
     * 根据主键查看bom详情
     *
     * @return
     */
    public List<SmtOrderCustBomVO> findCustBomDetailById(SmtOrderCustbom bom) {
        return smtOrderCustbomDao.findCustBomDetailById(bom);
    }

    /**
     * 查询所有的客户BOM列表信息，主表带明细
     * @param page 分页对象
     * @param smtOrderCustbom
     * @return
     */
    public Page<SmtOrderCustbom> findOrderCustBomListWithDetail(Page<SmtOrderCustbom> page, SmtOrderCustbom smtOrderCustbom) {
        smtOrderCustbom.setPage(page);
        page.setList(smtOrderCustbomDao.findOrderCustBomListWithDetail(smtOrderCustbom));
        return page;
    }

    /**
     * 导出数据，查询
     * @param custbom
     * @return
     */
    public  List<SmtOrderCustbom> export(SmtOrderCustbom custbom){
        return smtOrderCustbomDao.findOrderCustBomListWithDetail(custbom);
    }

    /**
     * 查询客户bom用料信息
     * @param bom
     * @return
     */
    public List<SmtOrderCustbom> findOrderCustbomList(SmtOrderCustbom bom){
        return smtOrderCustbomDao.findOrderCustbomList(bom);
    }
}