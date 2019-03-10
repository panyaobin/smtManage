/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.web.paymentinfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.smt.entity.paymentinfo.SmtPaymentInfo;
import com.thinkgem.jeesite.modules.smt.service.paymentinfo.SmtPaymentInfoService;

/**
 * 付款信息Controller
 *
 * @author popo
 * @version 2019-03-10
 */
@Controller
@RequestMapping(value = "${adminPath}/smt/paymentinfo/smtPaymentInfo")
public class SmtPaymentInfoController extends BaseController {

    @Autowired
    private SmtPaymentInfoService smtPaymentInfoService;

    @ModelAttribute
    public SmtPaymentInfo get(@RequestParam(required = false) String id) {
        SmtPaymentInfo entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = smtPaymentInfoService.get(id);
        }
        if (entity == null) {
            entity = new SmtPaymentInfo();
        }
        return entity;
    }

    @RequiresPermissions("smt:paymentinfo:smtPaymentInfo:view")
    @RequestMapping(value = {"list", ""})
    public String list(SmtPaymentInfo smtPaymentInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SmtPaymentInfo> page = smtPaymentInfoService.findPage(new Page<SmtPaymentInfo>(request, response), smtPaymentInfo);
        model.addAttribute("page", page);
        return "modules/smt/paymentinfo/smtPaymentInfoList";
    }

    @RequiresPermissions("smt:paymentinfo:smtPaymentInfo:view")
    @RequestMapping(value = "form")
    public String form(SmtPaymentInfo smtPaymentInfo, Model model) {
        model.addAttribute("smtPaymentInfo", smtPaymentInfo);
        return "modules/smt/paymentinfo/smtPaymentInfoForm";
    }

    @RequiresPermissions("smt:paymentinfo:smtPaymentInfo:edit")
    @RequestMapping(value = "save")
    public String save(SmtPaymentInfo smtPaymentInfo, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, smtPaymentInfo)) {
            return form(smtPaymentInfo, model);
        }
        smtPaymentInfoService.save(smtPaymentInfo);
        addMessage(redirectAttributes, "保存付款信息成功");
        return "redirect:" + Global.getAdminPath() + "/smt/paymentinfo/smtPaymentInfo/?repage";
    }

    @RequiresPermissions("smt:paymentinfo:smtPaymentInfo:edit")
    @RequestMapping(value = "delete")
    public String delete(SmtPaymentInfo smtPaymentInfo, RedirectAttributes redirectAttributes) {
        smtPaymentInfoService.delete(smtPaymentInfo);
        addMessage(redirectAttributes, "删除付款信息成功");
        return "redirect:" + Global.getAdminPath() + "/smt/paymentinfo/smtPaymentInfo/?repage";
    }

    /**
     * 通过ID查询付款信息
     * @param id
     * @return
     */
    @RequestMapping(value = "getPaymentInfoById")
    @ResponseBody
    public SmtPaymentInfo getPaymentInfoById(String id) {
        SmtPaymentInfo paymentInfo = smtPaymentInfoService.get(id);
        return paymentInfo;
    }

}