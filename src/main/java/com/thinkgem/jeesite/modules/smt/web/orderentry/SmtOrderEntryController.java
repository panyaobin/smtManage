/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.web.orderentry;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.export.excel.ExcelFactory;
import com.thinkgem.jeesite.common.utils.export.excel.XSSFExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.smt.entity.ordercustbom.SmtOrderCustbom;
import com.thinkgem.jeesite.modules.smt.entity.orderentry.SmtOrderEntry;
import com.thinkgem.jeesite.modules.smt.entity.orderoutgo.BomList;
import com.thinkgem.jeesite.modules.smt.entity.syscustomer.SmtSysCustomer;
import com.thinkgem.jeesite.modules.smt.service.ordercustbom.SmtOrderCustbomService;
import com.thinkgem.jeesite.modules.smt.service.orderentry.SmtOrderEntryService;
import com.thinkgem.jeesite.modules.smt.service.syscustomer.SmtSysCustomerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * smt订单入库表Controller
 *
 * @author popo
 * @version 2018-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/smt/orderentry/smtOrderEntry")
public class SmtOrderEntryController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(SmtOrderEntryController.class);
    @Autowired
    private SmtOrderEntryService smtOrderEntryService;

    @Autowired
    private SmtSysCustomerService smtSysCustomerService;

    @Autowired
    private SmtOrderCustbomService smtOrderCustbomService;

    @ModelAttribute
    public SmtOrderEntry get(@RequestParam(required = false) String id) {
        SmtOrderEntry entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = smtOrderEntryService.get(id);
        }
        if (entity == null) {
            entity = new SmtOrderEntry();
        }
        return entity;
    }

    @RequiresPermissions("smt:orderentry:smtOrderEntry:view")
    @RequestMapping(value = {"list", ""})
    public String list(SmtOrderEntry smtOrderEntry, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SmtOrderEntry> page = smtOrderEntryService.findMainPage(new Page<SmtOrderEntry>(request, response), smtOrderEntry);
        model.addAttribute("custList", getCustomerList());
        model.addAttribute("page", page);
        model.addAttribute("smtOrderEntry", smtOrderEntry);
        return "modules/smt/orderentry/smtOrderEntryList";
    }

    /**
     * 根据客户代码获取产品入库表中 产品型号
     *
     * @param custbom  客户bom表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("smt:orderentry:smtOrderEntry:view")
    @RequestMapping("/get_fpc_customer")
    @ResponseBody
    public String get_fpc_customer(SmtOrderCustbom custbom, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<SmtOrderCustbom> list = smtOrderCustbomService.findList(custbom);
        return JSON.toJSONString(list);
    }

    /**
     * 根据客户代码获取产品入库表中 电子料
     *
     * @param smtOrderEntry
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("smt:orderentry:smtOrderEntry:view")
    @RequestMapping("/get_dzl_customer")
    @ResponseBody
    public String get_dzl_customer(SmtOrderEntry smtOrderEntry, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SmtOrderEntry> page = smtOrderEntryService.findPage(new Page<SmtOrderEntry>(request, response), smtOrderEntry);
        model.addAttribute("page", page);
        return "modules/smt/orderentry/smtOrderEntryList";
    }


    /**
     * 仓库查询列表，区分产品类型  -----fpc入库列表
     *
     * @param smtOrderEntry
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("smt:orderentry:smtOrderEntry:view")
    @RequestMapping("/fpc_list")
    public String fpc_list(SmtOrderEntry smtOrderEntry, HttpServletRequest request, HttpServletResponse response, Model model) {
        smtOrderEntry.setProductType("1");
        Page<SmtOrderEntry> page = smtOrderEntryService.findPages(new Page<SmtOrderEntry>(request, response), smtOrderEntry);
        model.addAttribute("custList", getCustomerList());
        model.addAttribute("page", page);
        model.addAttribute("smtOrderEntry", smtOrderEntry);
        return "modules/smt/warehouse/smtOrderEntryFpcList";
    }

    /**
     * 仓库查询列表，区分产品类型   ----电子料入库列表
     *
     * @param smtOrderEntry
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("smt:orderentry:smtOrderEntry:view")
    @RequestMapping("/dzl_list")
    public String dzl_list(SmtOrderEntry smtOrderEntry, HttpServletRequest request, HttpServletResponse response, Model model) {
        smtOrderEntry.setProductType("2");
        Page<SmtOrderEntry> page = smtOrderEntryService.findPages(new Page<SmtOrderEntry>(request, response), smtOrderEntry);
        //加载客户信息
        SmtSysCustomer customer = new SmtSysCustomer();
        List<SmtSysCustomer> list = smtSysCustomerService.findList(customer);
        model.addAttribute("custList", list);
        model.addAttribute("page", page);
        model.addAttribute("smtOrderEntry", smtOrderEntry);
        return "modules/smt/warehouse/smtOrderEntryDzlList";
    }

    /**
     * 仓库查询列表，区分产品类型   ----电子料入库列表
     *
     * @param smtOrderEntry
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("smt:orderentry:smtOrderEntry:view")
    @RequestMapping("/total_list")
    public String total_list(SmtOrderEntry smtOrderEntry, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SmtOrderEntry> page = smtOrderEntryService.findPages(new Page<SmtOrderEntry>(request, response), smtOrderEntry);
        //加载客户信息
        SmtSysCustomer customer = new SmtSysCustomer();
        List<SmtSysCustomer> list = smtSysCustomerService.findList(customer);
        model.addAttribute("custList", list);
        model.addAttribute("page", page);
        model.addAttribute("smtOrderEntry", smtOrderEntry);
        return "modules/smt/warehouse/smtOrderEntryList";
    }

    /**
     * 仓库库存导出
     *
     * @param smtOrderEntry
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("smt:orderentry:smtOrderEntry:view")
    @RequestMapping("/export")
    @ResponseBody
    public void export(SmtOrderEntry smtOrderEntry, HttpServletRequest request, HttpServletResponse response) {
        log.info("这里是仓库库存导出数据");
        response.setContentType("application/vnd.ms-excel"); // 告知类型为excel文件
        response.setCharacterEncoding("utf-8");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + new String("仓库库存".getBytes("utf-8"), "ISO8859-1") + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        List<SmtOrderEntry> list = smtOrderEntryService.findLists(smtOrderEntry);
        if (null != list && list.size() > 0) {
            String[] titles = {"客户名称", "产品型号", "产品类型", "数量", "入库时间", "备注"}; // 标题
            List<Map<String, Object>> datas = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> data = new LinkedHashMap<>();
                SmtOrderEntry entry = list.get(i);
                data.put("a", entry.getCustomerName());
                data.put("b", entry.getProductNo());
                data.put("c", Integer.valueOf(entry.getProductType())==1?"FPC":"电子料");
                data.put("d", Integer.valueOf(entry.getCounts()));
                data.put("e", entry.getCreateDate());
                data.put("f", entry.getRemarks());
                datas.add(data);

            }
            Object[] keys = {"a", "b", "c", "d", "e", "f"};
            XSSFExcel excel = (XSSFExcel) ExcelFactory.createExcel(3, titles, datas, keys);
            try {
                excel.export(response.getOutputStream());
                log.info("----------------------仓库库存导出成功----------------------");
            } catch (IOException e) {
                log.info("----------------------仓库库存导出失败----------------------");
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "logo")
    public String logo() {
        return "modules/smt/orderentry/index";
    }


    @RequiresPermissions("smt:orderentry:smtOrderEntry:view")
    @RequestMapping(value = "form")
    public String form(SmtOrderEntry smtOrderEntry, Model model) {
        model.addAttribute("smtOrderEntry", smtOrderEntry);
        model.addAttribute("custList", getCustomerList());
        return "modules/smt/orderentry/smtOrderEntryForm";
    }

    @RequiresPermissions("smt:orderentry:smtOrderEntry:edit")
    @RequestMapping(value = "save")
    public String save(SmtOrderEntry smtOrderEntry, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, smtOrderEntry)) {
            return form(smtOrderEntry, model);
        }
        try {
            smtOrderEntryService.act_save(smtOrderEntry);
            addMessage(redirectAttributes, "保存smt订单入库表成功");
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "操作失败!");
        }
        return "redirect:" + Global.getAdminPath() + "/smt/orderentry/smtOrderEntry/?repage";
    }


    @RequiresPermissions("smt:orderentry:smtOrderEntry:edit")
    @RequestMapping(value = "delete")
    public String delete(SmtOrderEntry smtOrderEntry, RedirectAttributes redirectAttributes) {
        smtOrderEntryService.delete(smtOrderEntry);
        addMessage(redirectAttributes, "删除smt订单入库表成功");
        return "redirect:" + Global.getAdminPath() + "/smt/orderentry/smtOrderEntry/?repage";
    }

    @RequestMapping(value = "test")
    public void test(HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("customerName", "腾xx");
        map.put("productNo", "3338DPA");
        map.put("createTime", DateUtils.formatDateTime(new Date()));
        
        List<BomList> lists = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            BomList list = new BomList();
            list.setIndex(String.valueOf(i));
            list.setBomName("张三"+i);
            lists.add(list);
        }
        String jasperPath = "";
//        String jasperPath = "F:\\custbomlist.jasper";
        demo(response, map, lists, jasperPath);
    }
    
    
    /**
     * 入库列表根据入库单号查询弹窗
     * @param orderNo
     * @return
     */
    @RequiresPermissions("smt:orderentry:smtOrderEntry:view")
    @RequestMapping("/selectOrderEntryModal")
    @ResponseBody
    public List<SmtOrderEntry> selectOrderEntryModal(String orderNo) {
        List<SmtOrderEntry> entryList = smtOrderEntryService.selectByOrderNo(orderNo);
        return entryList;
    }
    
   

}