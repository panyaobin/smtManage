/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.web.ordercustbom;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.export.excel.ExcelFactory;
import com.thinkgem.jeesite.common.utils.export.excel.XSSFExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.smt.entity.ordercustbom.SmtOrderCustbom;
import com.thinkgem.jeesite.modules.smt.entity.ordercustbomdetail.SmtOrderCustbomDetail;
import com.thinkgem.jeesite.modules.smt.entity.orderdzlbom.SmtOrderDzlbom;
import com.thinkgem.jeesite.modules.smt.entity.syscustomer.SmtSysCustomer;
import com.thinkgem.jeesite.modules.smt.service.ordercustbom.SmtOrderCustbomService;
import com.thinkgem.jeesite.modules.smt.service.orderdzlbom.SmtOrderDzlbomService;
import com.thinkgem.jeesite.modules.smt.service.syscustomer.SmtSysCustomerService;
import com.thinkgem.jeesite.modules.smt.vo.SmtOrderCustBomVO;
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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户BOMController
 *
 * @author popo
 * @version 2018-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/smt/ordercustbom/smtOrderCustbom")
public class SmtOrderCustbomController extends BaseController {

    @Autowired
    private SmtOrderCustbomService smtOrderCustbomService;

    @Autowired
    private SmtSysCustomerService smtSysCustomerService;

    @Autowired
    private SmtOrderDzlbomService smtOrderDzlbomService;

    @ModelAttribute
    public SmtOrderCustbom get(@RequestParam(required = false) String id) {
        SmtOrderCustbom entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = smtOrderCustbomService.get(id);
        }
        if (entity == null) {
            entity = new SmtOrderCustbom();
        }
        return entity;
    }

    @RequiresPermissions("smt:ordercustbom:smtOrderCustbom:view")
    @RequestMapping(value = {"list", ""})
    public String list(SmtOrderCustbom smtOrderCustbom, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SmtOrderCustbom> page = smtOrderCustbomService.findPage(new Page<SmtOrderCustbom>(request, response), smtOrderCustbom);
        model.addAttribute("page", page);
        model.addAttribute("custList", getCustomerList());
        model.addAttribute("smtOrderCustbom", smtOrderCustbom);
        return "modules/smt/ordercustbom/smtOrderCustbomList";
    }

    /**
     * 查询所有的电子料信息
     *
     * @param smtOrderDzlbom
     * @return
     */
    @RequiresPermissions("smt:orderdzlbom:smtOrderDzlbom:view")
    @RequestMapping("/get_dzl_customer")
    @ResponseBody
    public String get_fpc_customer(SmtOrderDzlbom smtOrderDzlbom) {
        List<SmtOrderDzlbom> list = smtOrderDzlbomService.findList(smtOrderDzlbom);
        return JSON.toJSONString(list);
    }


    @RequiresPermissions("smt:ordercustbom:smtOrderCustbom:view")
    @RequestMapping(value = "form")
    public String form(SmtOrderCustbom smtOrderCustbom, Model model) {
        model.addAttribute("smtOrderCustbom", smtOrderCustbom);
        SmtSysCustomer customer = new SmtSysCustomer();
        List<SmtSysCustomer> list = smtSysCustomerService.findList(customer);
        model.addAttribute("custList", list);
        return "modules/smt/ordercustbom/smtOrderCustbomForm";
    }

    /**
     * 修改页面
     *
     * @param
     * @param model
     * @return
     */
    @RequiresPermissions("smt:ordercustbom:smtOrderCustbom:view")
    @RequestMapping(value = "modify")
    public String modify(SmtOrderCustbom bom, Model model) {

        return "modules/smt/ordercustbom/smtOrderCustbomForm";
    }

    /**
     * 查看详情
     *
     * @param bom
     * @param model
     * @return
     */
    @RequiresPermissions("smt:ordercustbom:smtOrderCustbom:view")
    @RequestMapping(value = "view")
    public String view(SmtOrderCustbom bom, Model model, String tag) {
        //根据ID查询明细信息
        if (null != bom) {
            List<SmtOrderCustBomVO> vo = smtOrderCustbomService.findCustBomDetailById(bom);
            if (null != vo && vo.size() > 0) {
                bom.setCustomerNo(vo.get(0).getCustomerNo());
                bom.setProductNo(vo.get(0).getProductNo());
                bom.setPointCounts(vo.get(0).getPointCounts());
            }
            model.addAttribute("ordercustbom", bom);
            model.addAttribute("ordercustbomvo", vo);
        }

        //电子料信息
        SmtOrderDzlbom dzlbom = new SmtOrderDzlbom();
        List<SmtOrderDzlbom> dzlboms = smtOrderDzlbomService.findList(dzlbom);
        model.addAttribute("dzlboms", dzlboms);

        //查询客户信息
        SmtSysCustomer customer = new SmtSysCustomer();
        List<SmtSysCustomer> list = smtSysCustomerService.findList(customer);
        model.addAttribute("custList", list);

        //根据操作吗判断是修改还是新增
        if (StringUtils.isNotBlank(tag)) {
            return "modules/smt/ordercustbom/smtOrderCustbomModify";
        }

        return "modules/smt/ordercustbom/smtOrderCustbomView";
    }

    @RequiresPermissions("smt:ordercustbom:smtOrderCustbom:edit")
    @RequestMapping(value = "save")
    public String save(SmtOrderCustbom smtOrderCustbom, SmtOrderCustbomDetail smtOrderCustbomDetail, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, smtOrderCustbom)) {
            return form(smtOrderCustbom, model);
        }
        try {
            if (StringUtils.isNotBlank(smtOrderCustbom.getId())) {
                //如果ID存在，修改操作
                smtOrderCustbomService.add_modify(smtOrderCustbom, smtOrderCustbomDetail);
                addMessage(redirectAttributes, "修改客户BOM成功");
            } else {
                //如果ID不存在就是新增
                smtOrderCustbomService.add_save(smtOrderCustbom, smtOrderCustbomDetail);
                addMessage(redirectAttributes, "保存客户BOM成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "操作失败！");

        }
        return "redirect:" + Global.getAdminPath() + "/smt/ordercustbom/smtOrderCustbom/?repage";
    }

    @RequiresPermissions("smt:ordercustbom:smtOrderCustbom:edit")
    @RequestMapping(value = "delete")
    public String delete(SmtOrderCustbom smtOrderCustbom, SmtOrderCustbomDetail custbomDetail, RedirectAttributes redirectAttributes) {
        try {
            smtOrderCustbomService.act_delete(smtOrderCustbom, custbomDetail);
            addMessage(redirectAttributes, "删除客户BOM成功");
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "操作失败！");
        }
        return "redirect:" + Global.getAdminPath() + "/smt/ordercustbom/smtOrderCustbom/?repage";
    }


    /**
     * 添加客户bom，验证是否存在
     *
     * @param productNo
     * @return
     */
    @RequestMapping(value = "validateProductNo")
    @ResponseBody
    public String validateProductNo(String productNo, String customerNo) {
        String tip = "";
        if (StringUtils.isNotBlank(productNo) && StringUtils.isNotBlank(customerNo)) {
            int i = smtOrderCustbomService.validateProductNo(productNo, customerNo);
            if (i > 0) {
                tip = "型号已存在!";
            }
        } else {
            tip = "未获取到型号";
        }
        return tip;
    }

    /**
     * 客户bom详情，所有数据
     *
     * @param smtOrderCustbom
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("smt:ordercustbom:smtOrderCustbom:view")
    @RequestMapping(value = "smtOrderCustbomDetail")
    public String smtOrderCustbomDetail(SmtOrderCustbom smtOrderCustbom, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SmtOrderCustbom> page = smtOrderCustbomService.findOrderCustBomListWithDetail(new Page<SmtOrderCustbom>(request, response), smtOrderCustbom);
        model.addAttribute("page", page);
        model.addAttribute("custList", getCustomerList());
        model.addAttribute("smtOrderCustbom", smtOrderCustbom);
        return "modules/smt/ordercustbom/smtOrderCustbomDetail";
    }

    /**
     * 导出数据
     * @param smtOrderCustbom
     * @param response
     * @return
     */
    @RequiresPermissions("smt:ordercustbom:smtOrderCustbom:view")
    @RequestMapping(value = "btnExport")
    public String btnExport(SmtOrderCustbom smtOrderCustbom,HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel"); // 告知类型为excel文件
        response.setCharacterEncoding("utf-8");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + new String("客户BOM列表".getBytes("utf-8"), "ISO8859-1") + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> datas = new ArrayList<>();
        List<SmtOrderCustbom> list = smtOrderCustbomService.export(smtOrderCustbom);
        String[] titles = {"序号", "客户", "产品型号", "总点数", "电子料型号", "电子料类型", "件位", "用量", "备品", "录入日期", "备注"}; // 标题
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> data = new LinkedHashMap<>();
                SmtOrderCustbom custbom = list.get(i);
                data.put("a",i+1);
                data.put("b",custbom.getCustomerName());
                data.put("c",custbom.getProductNo());
                data.put("d",custbom.getPointCounts());
                data.put("e",custbom.getBomName());
                data.put("f",custbom.getBomType());
                data.put("gg",custbom.getPiecePosition());
                data.put("h",custbom.getCounts());
                data.put("i",custbom.getStockCounts());
                data.put("j",custbom.getCreateDate());
                data.put("k",custbom.getRemarks());
                datas.add(data);
            }
        }
        Object[] keys = {"a", "b", "c", "d", "e", "f", "gg", "h", "i", "j", "k"};
        try {
            XSSFExcel excel = (XSSFExcel) ExcelFactory.createExcel(3, titles, datas, keys);
            excel.export(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "modules/smt/ordercustbom/smtOrderCustbomDetail";
    }
}
