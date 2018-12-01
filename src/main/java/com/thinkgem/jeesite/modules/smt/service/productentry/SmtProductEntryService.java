/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.service.productentry;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.smt.dao.orderoutgo.SmtOrderOutgoDao;
import com.thinkgem.jeesite.modules.smt.dao.productentry.SmtProductEntryDao;
import com.thinkgem.jeesite.modules.smt.entity.modalEntity.SmtModalProductEntry;
import com.thinkgem.jeesite.modules.smt.entity.orderonline.SmtOrderOnline;
import com.thinkgem.jeesite.modules.smt.entity.orderoutgo.OutGoVO;
import com.thinkgem.jeesite.modules.smt.entity.orderoutgo.SmtOrderOutgo;
import com.thinkgem.jeesite.modules.smt.entity.productentry.SmtProductEntry;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.thinkgem.jeesite.common.persistence.BaseEntity.DEL_FLAG_NORMAL;
import static com.thinkgem.jeesite.common.persistence.BaseEntity.SMT_PRODUCT_TYPE_1;

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
        int returnNos;
        //查询最后一次的退货单号
        String outgoOrderNo = smtOrderOutgoDao.selectMaxOutgoOrderNo();
        if (StringUtils.isBlank(outgoOrderNo)) {
            returnNos = 999110000;
        } else {
            returnNos = Integer.valueOf(outgoOrderNo) + 1;
        }

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
            entry.setOutgoOrderNo(String.valueOf(returnNos));
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

    /**
     * 成品入库弹窗数据保存
     *
     * @param online
     */
    @Transactional(readOnly = false)
    public SmtModalProductEntry saveProductEntryModalData(SmtOrderOnline online){
        SmtModalProductEntry modalProductEntry =new SmtModalProductEntry();
        SmtProductEntry entry = new SmtProductEntry();
        entry.setCustomerNo(online.getCustomerNo());
        entry.setCustomerName(online.getCustomerName());
        entry.setProductNo(online.getProductNo());
        entry.setOrderNo(createOrderNo());
        entry.setProductType(SMT_PRODUCT_TYPE_1);
        entry.setEntryCounts(online.getCounts());
        entry.setDelFlag(DEL_FLAG_NORMAL);
        entry.preInsert();
        dao.insert(entry);
        
        modalProductEntry.setProductEntry(entry);
        
        List<OutGoVO> entryList = new ArrayList<>();
        
        if (StringUtils.isNotBlank(online.getCustBomTableData())) {
            String tableData = online.getCustBomTableData().replaceAll("&quot;", "\"");
            JSONArray array = JSONArray.fromObject(tableData);
            for (int i = 0; i < array.size(); i++) {
                JSONObject object = array.getJSONObject(i);
                SmtProductEntry productEntry = new SmtProductEntry();
                OutGoVO go = new OutGoVO();
                
                productEntry.setCustomerNo(online.getCustomerNo());
                productEntry.setProductNo(String.valueOf(object.get("bomName")));
                go.setBomName(String.valueOf(object.get("bomName")));
                productEntry.setOrderNo(entry.getOrderNo());
                int total = Integer.parseInt(String.valueOf(object.get("counts"))) + Integer.parseInt(String.valueOf(object.get("stockCounts")));
                productEntry.setEntryCounts(String.valueOf(total));
                go.setCounts(String.valueOf(object.get("counts")));
                go.setStockCounts(String.valueOf(object.get("stockCounts")));
                productEntry.setProductType(String.valueOf(object.get("bomType")));
                go.setBomType(String.valueOf(object.get("bomType")));
                productEntry.setDelFlag(DEL_FLAG_NORMAL);
                productEntry.preInsert();
                productEntry.setRemarks(String.valueOf(object.get("remarks")));
                entryList.add(go);
                dao.insert(productEntry);
            }
            modalProductEntry.setOutGoVOList(entryList);
        }
        //保存完之后打印PDF
       return modalProductEntry;
    }

    /**
     * 保存结存的时候生成入库号，如果没有200000001开始
     */
    public int createOrderNo() {
        String orderNos = dao.findOrderNoFromList();
        int orderNo = 0;
        if (StringUtils.isBlank(orderNos)) {
            orderNo = 200000001;
        } else {
            orderNo = Integer.valueOf(orderNos) + 1;
        }
        return orderNo;
    }


    /**
     * 生成PDF
     *
     * @param response
     */
    public void demo(HttpServletResponse response, Map<String, Object> map, List lists, String jasperPath) {
        try {
            JRDataSource jrDataSource = new JRBeanCollectionDataSource(lists);
            convertPDF(jrDataSource, jasperPath, response, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void convertPDF(JRDataSource jrDataSource, String jasperPath, HttpServletResponse response, Map map) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(jasperPath));
            response.setContentType("application/pdf");
            OutputStream os = response.getOutputStream();
            JasperRunManager.runReportToPdfStream(fileInputStream, os, map, jrDataSource);
            fileInputStream.close();
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}