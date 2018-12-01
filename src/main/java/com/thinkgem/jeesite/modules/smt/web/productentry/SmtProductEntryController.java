/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.web.productentry;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.smt.entity.modalEntity.SmtModalProductEntry;
import com.thinkgem.jeesite.modules.smt.entity.ordercustbom.SmtOrderCustbom;
import com.thinkgem.jeesite.modules.smt.entity.orderentry.SmtOrderEntry;
import com.thinkgem.jeesite.modules.smt.entity.orderonline.SmtOrderOnline;
import com.thinkgem.jeesite.modules.smt.entity.orderoutgo.OutGoVO;
import com.thinkgem.jeesite.modules.smt.entity.orderoutgo.SmtOrderOutgoVO;
import com.thinkgem.jeesite.modules.smt.entity.productentry.SmtProductEntry;
import com.thinkgem.jeesite.modules.smt.entity.syscustomer.SmtSysCustomer;
import com.thinkgem.jeesite.modules.smt.service.ordercustbom.SmtOrderCustbomService;
import com.thinkgem.jeesite.modules.smt.service.orderentry.SmtOrderEntryService;
import com.thinkgem.jeesite.modules.smt.service.orderonline.SmtOrderOnlineService;
import com.thinkgem.jeesite.modules.smt.service.orderoutgo.SmtOrderOutgoService;
import com.thinkgem.jeesite.modules.smt.service.productentry.SmtProductEntryService;
import com.thinkgem.jeesite.modules.smt.service.syscustomer.SmtSysCustomerService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 成品入库表Controller
 *
 * @author popo
 * @version 2018-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/smt/productentry/smtProductEntry")
public class SmtProductEntryController extends BaseController {

    @Autowired
    private SmtProductEntryService smtProductEntryService;

    @Autowired
    private SmtSysCustomerService smtSysCustomerService;

    @Autowired
    private SmtOrderOnlineService smtOrderOnlineService;

    @Autowired
    private SmtOrderEntryService smtOrderEntryService;

    @Autowired
    private SmtOrderCustbomService smtOrderCustbomService;
    
    @Autowired
    private SmtOrderOutgoService smtOrderOutgoService;
    
    private SmtModalProductEntry modalData = new SmtModalProductEntry();


    @ModelAttribute
    public SmtProductEntry get(@RequestParam(required = false) String id) {
        SmtProductEntry entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = smtProductEntryService.get(id);
        }
        if (entity == null) {
            entity = new SmtProductEntry();
        }
        return entity;
    }

    @RequiresPermissions("smt:productentry:smtProductEntry:view")
    @RequestMapping(value = {"list", ""})
    public String list(SmtProductEntry smtProductEntry, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SmtProductEntry> page = smtProductEntryService.findPage(new Page<SmtProductEntry>(request, response), smtProductEntry);
        model.addAttribute("page", page);
        return "modules/smt/productentry/smtProductEntryList";
    }


    /*
     * 在线生产成品入库记录信息查询
     * @param smtProductEntry
     * @param request
     * @param response
     * @param model
     * @return`
     */
    @RequiresPermissions("smt:productentry:smtProductEntry:view")
    @RequestMapping("product_entry_list")
    public String product_entry_list(SmtProductEntry smtProductEntry, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SmtProductEntry> page = smtProductEntryService.findPage(new Page<SmtProductEntry>(request, response), smtProductEntry);
        model.addAttribute("custList", getCustomerList());
        model.addAttribute("page", page);
        model.addAttribute("smtProductEntry", smtProductEntry);
        return "modules/smt/orderonline/smtProductEntryList";
    }


    @RequiresPermissions("smt:productentry:smtProductEntry:view")
    @RequestMapping(value = "form")
    public String form(SmtProductEntry smtProductEntry, Model model) {
        model.addAttribute("smtProductEntry", smtProductEntry);
        return "modules/smt/productentry/smtProductEntryForm";
    }

    @RequiresPermissions("smt:productentry:smtProductEntry:edit")
    @RequestMapping(value = "save")
    public String save(SmtProductEntry smtProductEntry, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, smtProductEntry)) {
            return form(smtProductEntry, model);
        }
        smtProductEntryService.save(smtProductEntry);
        addMessage(redirectAttributes, "保存成品入库表成功");
        return "redirect:" + Global.getAdminPath() + "/smt/productentry/smtProductEntry/?repage";
    }

    @RequiresPermissions("smt:productentry:smtProductEntry:edit")
    @RequestMapping(value = "delete")
    public String delete(SmtProductEntry smtProductEntry, RedirectAttributes redirectAttributes) {
        smtProductEntryService.delete(smtProductEntry);
        addMessage(redirectAttributes, "删除成品入库表成功");
        return "redirect:product_entry_list";
    }

    /**
     * @param smtOrderOnline
     * @param model
     * @return
     */
    @RequiresPermissions("smt:productentry:smtProductEntry:edit")
    @RequestMapping("save_product_entry")
    public String save_product_entry(SmtOrderOnline smtOrderOnline, Model model) {
        List<SmtOrderOnline> orderOnlines = smtOrderOnlineService.findProductEntryCount(smtOrderOnline);
        //加载客户信息
        SmtSysCustomer customer = new SmtSysCustomer();
        List<SmtSysCustomer> list = smtSysCustomerService.findList(customer);
        model.addAttribute("custList", list);
        model.addAttribute("orderOnlines", orderOnlines);
        return "modules/smt/orderonline/smtProductEntryCountList";
    }

    /**
     * 弹窗数据保存，入库
     *
     * @param entity
     * @return
     */
    @RequestMapping("/selectCustomerBomUsage")
    @ResponseBody
    public List<SmtOrderCustbom> selectCustomerBomUsage(SmtOrderCustbom entity) {
        List<SmtOrderCustbom> orderCustbomList = smtOrderCustbomService.findOrderCustbomList(entity);
        int counts = Integer.parseInt(entity.getCounts());
        orderCustbomList.forEach(x -> {
            x.setCounts(String.valueOf(Integer.parseInt(x.getCounts()) * counts));
            double v = Double.parseDouble(String.valueOf(Integer.parseInt(x.getStockCounts()) * Integer.parseInt(x.getCounts()) / 1000));
            x.setStockCounts(String.valueOf(new BigDecimal(v).setScale(0, BigDecimal.ROUND_HALF_UP)));
        });
        return orderCustbomList;
    }

    /**
     * 退货
     *
     * @param smtOrderEntry
     * @param model
     * @return
     */
    @RequiresPermissions("smt:productentry:smtProductEntry:edit")
    @RequestMapping("return_goods_list")
    public String return_goods_list(SmtOrderEntry smtOrderEntry, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<SmtOrderEntry> returnGoodsList = smtOrderEntryService.findReturnGoodsList(smtOrderEntry);
        model.addAttribute("orderOnlines", returnGoodsList);
        model.addAttribute("custList", getCustomerList());
        model.addAttribute("smtOrderEntry", smtOrderEntry);
        return "modules/smt/orderoutgo/smtProductReturnGoodsList";
    }


    /**
     * 在线生产 成品入库信息保存
     *
     * @param smtOrderOnline
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("smt:productentry:smtProductEntry:edit")
    @RequestMapping(value = "print")
    public void save_product_entry_count(SmtOrderOnline smtOrderOnline, Model model, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        String[] customerNo = smtOrderOnline.getCustomerNo().split(",");
        String[] customerName = smtOrderOnline.getCustomerName().split(",");
        String[] productNo = smtOrderOnline.getProductNo().split(",");
        String[] productType = smtOrderOnline.getProductType().split(",");
        String[] counts = smtOrderOnline.getCounts().split(",");
        String[] remarks = null;
        if (StringUtils.isNotBlank(smtOrderOnline.getRemarks())) {
            remarks = smtOrderOnline.getRemarks().split(",");
        }
        List<SmtOrderOnline> onlines = new ArrayList<SmtOrderOnline>();

        for (int i = 0; i < customerNo.length; i++) {
            logger.info("入库存储打印单数据！");
            SmtOrderOnline line = new SmtOrderOnline();
            line.setIndex(String.valueOf(i + 1));
            line.setCustomerNo(customerNo[i]);
            line.setCustomerName(customerName[i]);
            line.setProductNo(productNo[i]);
            line.setProductType(productType[i].equalsIgnoreCase("2") ? "电子料" : "FPC");
            line.setCounts(counts[i]);
            if (remarks.length > 0 && i < remarks.length) {
                line.setRemarks(remarks[i] == null ? "" : remarks[i]);
            } else {
                line.setRemarks("");
            }
            onlines.add(line);
        }
        if (onlines.size() < 8) {
            for (int i = 0; i < 8 - customerNo.length; i++) {
                logger.info("如果数量不够十条，需要凑齐十条,第 " + i + "条");
                SmtOrderOnline on = new SmtOrderOnline();
                on.setIndex("");
                on.setOrderNo("");
                on.setCustomerNo("");
                on.setCustomerName("");
                on.setProductNo("");
                on.setCounts("");
                on.setProductType("");
                on.setRemarks("");
                onlines.add(on);
            }
        }
        int orderNum = 0;
        try {
            orderNum = smtProductEntryService.to_save(smtOrderOnline);
            addMessage(redirectAttributes, "成品入库成功");
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "操作失败");
        }
        model.addAttribute("onlines", onlines);
        //这里是入库打印
//        String jasperPath = "F:\\Blank_A4_2.jasper";

        String jasperPath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\ROOT\\WEB-INF\\classes\\jasper\\Blank_A4_2.jasper";

        Map<String, Object> map = new HashMap<>(10);
        map.put("customerName", onlines.get(0).getCustomerName());
        map.put("sendNo", String.valueOf(orderNum));
        map.put("createUser", UserUtils.getUser().getName());
        map.put("createDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        try {
            System.out.println(jasperPath);
            demo(response, map, onlines, jasperPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 退货信息录入
     *
     * @param smtOrderOnline
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("smt:productentry:smtProductEntry:edit")
    @RequestMapping(value = "return_print")
    public void return_print(SmtOrderOnline smtOrderOnline, Model model, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        String[] customerNo = smtOrderOnline.getCustomerNo().split(",");
        String[] customerName = smtOrderOnline.getCustomerName().split(",");
        String[] productNo = smtOrderOnline.getProductNo().split(",");
        String[] orderNo = smtOrderOnline.getOrderNo().split(",");
        String[] productType = smtOrderOnline.getProductType().split(",");
        String[] counts = smtOrderOnline.getCounts().split(",");
        String[] remarks = null;
        if (StringUtils.isNotBlank(smtOrderOnline.getRemarks())) {
            remarks = smtOrderOnline.getRemarks().split(",");
        }
        List<SmtOrderOnline> onlines = new ArrayList<SmtOrderOnline>();

        for (int i = 0; i < customerNo.length; i++) {
            logger.info("退货信息保存！");
            SmtOrderOnline line = new SmtOrderOnline();
            line.setIndex(String.valueOf(i + 1));
            line.setCustomerNo(customerNo[i]);
            line.setOrderNo(orderNo[i]);
            line.setCustomerName(customerName[i]);
            line.setProductNo(productNo[i]);
            line.setProductType(productType[i].equalsIgnoreCase("2") ? "电子料" : "FPC");
            line.setCounts(counts[i]);
            if (remarks.length > 0 && i < remarks.length) {
                line.setRemarks(remarks[i] == null ? "" : remarks[i]);
            } else {
                line.setRemarks("");
            }
            onlines.add(line);
        }
        if (onlines.size() < 8) {
            for (int i = 0; i < 8 - customerNo.length; i++) {
                logger.info("如果退货数量不够十条，需要凑齐十条,第 " + i + "条");
                SmtOrderOnline on = new SmtOrderOnline();
                on.setIndex("");
                on.setOrderNo("");
                on.setCustomerNo("");
                on.setCustomerName("");
                on.setProductNo("");
                on.setOrderNo("");
                on.setCounts("");
                on.setProductType("");
                on.setRemarks("");
                onlines.add(on);
            }
        }
        int orderNum = 0;
        try {
            orderNum = smtProductEntryService.to_save_return(smtOrderOnline);
            addMessage(redirectAttributes, "退货成功");
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "操作失败");
        }
        model.addAttribute("onlines", onlines);
        //这里是退货打印
//        String jasperPath = "F:\\Blank_A4_return.jasper";
        String jasperPath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\ROOT\\WEB-INF\\classes\\jasper\\Blank_A4_return.jasper";
        Map<String, Object> map = new HashMap<>(10);
        map.put("customerName", onlines.get(0).getCustomerName());
        map.put("sendNo", String.valueOf(orderNum));
        map.put("createUser", UserUtils.getUser().getName());
        map.put("createDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        try {
            System.out.println(jasperPath);
            demo(response, map, onlines, jasperPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 退货列表重新打印
     * @param outgoOrderNo
     */
    @RequestMapping("/reprint")
    public void reprint(String outgoOrderNo,HttpServletResponse response){
        List<SmtOrderOutgoVO> outgoList = smtOrderOutgoService.selectByOutGoOrderNo(outgoOrderNo);
       
//        String jasperPath = "F:\\Blank_A4_2.jasper";
        String jasperPath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\ROOT\\WEB-INF\\classes\\jasper\\Blank_A4_2.jasper";
        Map<String, Object> map = new HashMap<>(10);
        map.put("customerName", outgoList.get(0).getCustomerName());
        map.put("sendNo", outgoList.get(0).getOutgoOrderNo());
        map.put("createUser", outgoList.get(0).getCreateByName());
        map.put("createDate", new SimpleDateFormat("yyyy-MM-dd").format(outgoList.get(0).getCreateDate()));

        List<SmtOrderOutgoVO> outgoVOList=new ArrayList<>();

        for (int i = 0; i < outgoList.size(); i++) {
            SmtOrderOutgoVO vo = outgoList.get(i);
            SmtOrderOutgoVO outgoVO = new SmtOrderOutgoVO();
            outgoVO.setIndex(String.valueOf(i+1));
            outgoVO.setProductNo(vo.getProductNo());
            outgoVO.setProductType(vo.getProductType()=="1"?"FPC":"电子料");
            outgoVO.setCounts(vo.getCounts());
            outgoVO.setRemarks(StringUtils.isBlank(vo.getRemarks())?"":vo.getRemarks());
            outgoVOList.add(outgoVO);
        }
        
        if (outgoList.size() < 8) {
            for (int i = 0; i < 8 - outgoList.size(); i++) {
                logger.info("如果数量不够十条，需要凑齐十条,第 " + i + "条");
                SmtOrderOutgoVO go = new SmtOrderOutgoVO();
                go.setIndex("");
                go.setOrderNo("");
                go.setCustomerNo("");
                go.setCustomerName("");
                go.setProductNo("");
                go.setCounts("");
                go.setProductType("");
                go.setRemarks("");
                outgoVOList.add(go);
            }
        }
        try {
            System.out.println(jasperPath);
            demo(response, map, outgoVOList, jasperPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 成品入库弹窗数据保存
     * @param online
     */
    @RequestMapping("/saveProductEntryModalData")
    @ResponseBody
    public String saveProductEntryModalData(SmtOrderOnline online){
        String code;
        try {
            SmtModalProductEntry modalProductEntry = smtProductEntryService.saveProductEntryModalData(online);
            modalData =modalProductEntry;
            code="success";
        }catch (Exception e){
            e.printStackTrace();
            code ="error";
        }
        return code;
    } 
    
    /**
     * 成品入库弹窗数据保存
     */
    @RequestMapping("/printModal")
    public void printModal(HttpServletResponse response){
        //这里是退货打印
//        String jasperPath = "F:\\outgo_modal.jasper";
        String jasperPath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\ROOT\\WEB-INF\\classes\\jasper\\outgo_modal.jasper";
        Map<String, Object> map = new HashMap<>(10);
        map.put("customerName",modalData.getProductEntry().getCustomerName());
        map.put("sendNo",String.valueOf(modalData.getProductEntry().getOrderNo()));
        map.put("productNo",String.valueOf(modalData.getProductEntry().getProductNo()));
        map.put("fpcCounts",String.valueOf(modalData.getProductEntry().getEntryCounts()));
        map.put("createUser", UserUtils.getUser().getName());
        map.put("createDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        try {
            List<OutGoVO> entryList = modalData.getOutGoVOList();
            for (int i = 0; i < 24; i++) {
                
            }

            int size = 24 - entryList.size();
            List<SmtProductEntry> entries = new ArrayList<>();
            if (entryList.size() < 24) {
                for (int i = 0; i < size; i++) {
                    OutGoVO vo = new OutGoVO();
                    vo.setIndex(String.valueOf(entries.size() + i));
                    vo.setBomName("");
                    vo.setBomType("");
                    vo.setCounts("");
                    vo.setStockCounts("");
                    entryList.add(vo);
                }
            }

            for (int i = 0; i < 12; i++) {
                SmtProductEntry vo = new SmtProductEntry();
                vo.setIndex(String.valueOf(i + 1));
                vo.setBomName(entryList.get(i).getBomName());
                vo.setBomType(entryList.get(i).getBomType());
                vo.setCounts(entryList.get(i).getCounts());
                vo.setStockCounts(entryList.get(i).getStockCounts());
                vo.setIndexs(String.valueOf(13 + i));
                vo.setBomNames(entryList.get(12 + i).getBomName());
                vo.setBomTypes(entryList.get(12 + i).getBomType());
                vo.setCountss(entryList.get(12 + i).getCounts());
                vo.setStockCountss(entryList.get(12 + i).getStockCounts());
                entries.add(vo);
            }
            System.out.println(jasperPath);
            demo(response, map, entries, jasperPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}