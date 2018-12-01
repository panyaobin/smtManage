/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.web.orderonline;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.export.excel.ExcelFactory;
import com.thinkgem.jeesite.common.utils.export.excel.XSSFExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.smt.entity.orderentry.SmtOrderEntry;
import com.thinkgem.jeesite.modules.smt.entity.orderonline.SmtOrderOnline;
import com.thinkgem.jeesite.modules.smt.entity.syscustomer.SmtSysCustomer;
import com.thinkgem.jeesite.modules.smt.service.orderentry.SmtOrderEntryService;
import com.thinkgem.jeesite.modules.smt.service.orderonline.SmtOrderOnlineService;
import com.thinkgem.jeesite.modules.smt.service.syscustomer.SmtSysCustomerService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * smt在线生产表Controller
 *
 * @author popo
 * @version 2018-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/smt/orderonline/smtOrderOnline")
public class SmtOrderOnlineController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(SmtOrderOnlineController.class);
    
    @Autowired
    private SmtOrderOnlineService smtOrderOnlineService;

    @Autowired
    private SmtOrderEntryService smtOrderEntryService;

    @Autowired
    private SmtSysCustomerService smtSysCustomerService;

    @ModelAttribute
    public SmtOrderOnline get(@RequestParam(required = false) String id) {
        SmtOrderOnline entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = smtOrderOnlineService.get(id);
        }
        if (entity == null) {
            entity = new SmtOrderOnline();
        }
        return entity;
    }

    @RequiresPermissions("smt:orderonline:smtOrderOnline:view")
    @RequestMapping(value = {"list", ""})
    public String list(SmtOrderOnline smtOrderOnline, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SmtOrderOnline> page = smtOrderOnlineService.findPage(new Page<SmtOrderOnline>(request, response), smtOrderOnline);
        model.addAttribute("page", page);
        return "modules/smt/orderonline/smtOrderOnlineFpcList";
    }


    /**
     * 仓库在线生产列表，区分产品类型  -----fpc入库列表
     *
     * @param smtOrderOnline
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("smt:orderentry:smtOrderEntry:view")
    @RequestMapping("/fpc_list")
    public String fpc_list(SmtOrderOnline smtOrderOnline, HttpServletRequest request, HttpServletResponse response, Model model) {
        smtOrderOnline.setProductType("1");
        Page<SmtOrderOnline> page = smtOrderOnlineService.findPages(new Page<SmtOrderOnline>(request, response), smtOrderOnline);
        model.addAttribute("custList",getCustomerList());
        model.addAttribute("page", page);
        model.addAttribute("smtOrderOnline", smtOrderOnline);
        return "modules/smt/orderonline/smtOrderOnlineFpcList";
    }


    /**
     * 仓库在线生产列表，区分产品类型   ----电子料入库列表
     *
     * @param smtOrderOnline
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("smt:orderentry:smtOrderEntry:view")
    @RequestMapping("/dzl_list")
    public String dzl_list(SmtOrderOnline smtOrderOnline, HttpServletRequest request, HttpServletResponse response, Model model) {
        smtOrderOnline.setProductType("2");
        Page<SmtOrderOnline> page = smtOrderOnlineService.findPages(new Page<SmtOrderOnline>(request, response), smtOrderOnline);
        //加载客户信息
        SmtSysCustomer customer=new SmtSysCustomer();
        List<SmtSysCustomer> list = smtSysCustomerService.findList(customer);
        model.addAttribute("custList",list);
        model.addAttribute("page", page);
        return "modules/smt/orderonline/smtOrderOnlineDzlList";
    }
    
    /**
     * 仓库在线生产列表，不区分产品类型 
     *
     * @param smtOrderOnline
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("smt:orderentry:smtOrderEntry:view")
    @RequestMapping("/total_list")
    public String total_list(SmtOrderOnline smtOrderOnline, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SmtOrderOnline> page = smtOrderOnlineService.findPages(new Page<SmtOrderOnline>(request, response), smtOrderOnline);
        //加载客户信息
        SmtSysCustomer customer=new SmtSysCustomer();
        List<SmtSysCustomer> list = smtSysCustomerService.findList(customer);
        model.addAttribute("custList",list);
        model.addAttribute("page", page);
        return "modules/smt/orderonline/smtOrderOnlineList";
    }


    /**
     * 在线结存导出
     *
     * @param smtOrderOnline
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("smt:orderonline:smtOrderOnline:view")
    @RequestMapping("/export")
    @ResponseBody
    public void export(SmtOrderOnline smtOrderOnline, HttpServletRequest request, HttpServletResponse response) {
        log.info("这里是仓库结存导出数据");
        response.setContentType("application/vnd.ms-excel"); // 告知类型为excel文件
        response.setCharacterEncoding("utf-8");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + new String("在线结存".getBytes("utf-8"), "ISO8859-1") + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        List<SmtOrderOnline> list = smtOrderOnlineService.findLists(smtOrderOnline);
        if (null != list && list.size() > 0) {
            String[] titles = {"客户名称", "产品型号", "产品类型", "数量", "入库时间", "备注"}; // 标题
            List<Map<String, Object>> datas = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> data = new LinkedHashMap<>();
                SmtOrderOnline online = list.get(i);
                data.put("a", online.getCustomerName());
                data.put("b", online.getProductNo());
                data.put("c", Integer.valueOf(online.getProductType())==1?"FPC":"电子料");
                data.put("d", Integer.valueOf(online.getCounts()));
                data.put("e", online.getCreateDate());
                data.put("f", online.getRemarks());
                datas.add(data);

            }
            Object[] keys = {"a", "b", "c", "d", "e", "f"};
            XSSFExcel excel = (XSSFExcel) ExcelFactory.createExcel(3, titles, datas, keys);
            try {
                excel.export(response.getOutputStream());
                log.info("----------------------在线结存导出成功----------------------");
            } catch (IOException e) {
                log.info("----------------------在线结存导出失败----------------------");
                e.printStackTrace();
            }
        }
    }
    
    
    /**
     * 发料记录查询
     *
     * @param smtOrderOnline
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("smt:orderonline:smtOrderOnline:view")
    @RequestMapping("/send_record")
    public String send_record(SmtOrderOnline smtOrderOnline, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SmtOrderOnline> page = smtOrderOnlineService.findPage(new Page<SmtOrderOnline>(request, response), smtOrderOnline);
        model.addAttribute("custList",getCustomerList());
        model.addAttribute("page", page);
        model.addAttribute("smtOrderOnline", smtOrderOnline);
        return "modules/smt/warehouse/smtOrderOnlineList";
    }

    @RequiresPermissions("smt:orderonline:smtOrderOnline:view")
    @RequestMapping(value = "form")
    public String form(SmtOrderOnline smtOrderOnline, Model model) {
        model.addAttribute("smtOrderOnline", smtOrderOnline);
        return "modules/smt/orderonline/smtOrderOnlineDzlList";
    }



    /**
     * 在线生产录入操作，由仓库发料而来
     *
     * @param smtOrderOnline
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("smt:orderonline:smtOrderOnline:edit")
    @RequestMapping(value = "print")
    public void to_save_send(SmtOrderOnline smtOrderOnline, Model model, RedirectAttributes redirectAttributes,HttpServletResponse response) {
        String [] orderNo=smtOrderOnline.getOrderNo().split(",");  
        String [] customerNo=smtOrderOnline.getCustomerNo().split(",");  
        String [] customerName=smtOrderOnline.getCustomerName().split(",");  
        String [] productNo=smtOrderOnline.getProductNo().split(",");  
        String [] productType=smtOrderOnline.getProductType().split(",");
        String [] counts=smtOrderOnline.getCounts().split(",");
        String [] remarks=null;
        if (StringUtils.isNotBlank(smtOrderOnline.getRemarks())){
            remarks=smtOrderOnline.getRemarks().split(",");
        }else{
            
        }
        List<SmtOrderOnline> onlines=new ArrayList<SmtOrderOnline>();
        for (int i = 0; i <orderNo.length ; i++) {
            SmtOrderOnline line=new SmtOrderOnline();
            line.setIndex(String.valueOf(i+1));
            line.setOrderNo(orderNo[i]);
            line.setCustomerNo(customerNo[i]);
            line.setCustomerName(customerName[i]);
            line.setProductNo(productNo[i]);
            line.setProductType(productType[i].equalsIgnoreCase("2")?"电子料":"FPC");
            line.setCounts(counts[i]);
            if (remarks.length > 0 && i < remarks.length) {
                line.setRemarks(remarks[i] == null ? "" : remarks[i]);
            } else {
                line.setRemarks("");
            }
            onlines.add(line);
        }
        if (onlines.size()<8){
            for (int i = 0; i <8-orderNo.length; i++) {
                SmtOrderOnline on=new SmtOrderOnline();
                on.setIndex("");
                on.setOrderNo("");
                on.setCustomerNo("");
                on.setCustomerName("");
                on.setCounts("");
                on.setProductNo("");
                on.setProductType("");
                on.setRemarks("");
                onlines.add(on);    
            }
        }
        int send=0;
        try {
             send= smtOrderOnlineService.to_save_send(smtOrderOnline);
             logger.info("自动生成的发料单号是："+String.valueOf(send));
            addMessage(redirectAttributes, "发料操作成功");
            
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "操作失败");
        }
        model.addAttribute("onlines",onlines);
//        String jasperPath = "F:\\Blank_A4_1.jasper";
        String jasperPath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\ROOT\\WEB-INF\\classes\\jasper\\Blank_A4_1.jasper";

        Map<String, Object> map = new HashMap<>(10);
        map.put("customerName", onlines.get(0).getCustomerName());
        map.put("sendNo",String.valueOf(send));
        map.put("createUser",UserUtils.getUser().getName());
        map.put("createDate",new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        try {
            System.out.println(jasperPath);
            demo(response,map,onlines,jasperPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在线生产录入操作，由仓库发料而来
     *
     * @return
     */
    @RequiresPermissions("smt:orderonline:smtOrderOnline:edit")
    @RequestMapping(value = "save_record")
    public String save_fpc_send(SmtOrderEntry smtOrderEntry, Model model) {
        List<SmtOrderEntry> fpcRecord = smtOrderEntryService.findSendRecord(smtOrderEntry);
        //加载客户信息
        SmtSysCustomer customer = new SmtSysCustomer();
        List<SmtSysCustomer> list = smtSysCustomerService.findList(customer);
        model.addAttribute("custList", list);
        model.addAttribute("fpcRecord", fpcRecord);
        return "modules/smt/warehouse/smtSendRecordFpcList";
    }

    /**
     * 返回发料记录提示信息使用
     *
     * @param smtOrderOnline
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("smt:orderonline:smtOrderOnline:view")
    @RequestMapping("/send_records")
    public String send_records(SmtOrderOnline smtOrderOnline, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SmtOrderOnline> page = smtOrderOnlineService.findPage(new Page<SmtOrderOnline>(request, response), smtOrderOnline);
        model.addAttribute("page", page);
        addMessage(redirectAttributes, "发料成功");
        return "modules/smt/warehouse/smtOrderOnlineList";
    }


    @RequiresPermissions("smt:orderonline:smtOrderOnline:edit")
    @RequestMapping(value = "delete")
    public String delete(SmtOrderOnline smtOrderOnline, RedirectAttributes redirectAttributes) {
        smtOrderOnlineService.delete(smtOrderOnline);
        addMessage(redirectAttributes, "撤销发料记录成功");
        return "redirect:send_record";
    }

}