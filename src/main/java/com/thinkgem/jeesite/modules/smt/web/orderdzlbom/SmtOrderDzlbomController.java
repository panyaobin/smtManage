/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.web.orderdzlbom;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.smt.entity.orderdzlbom.SmtOrderDzlbom;
import com.thinkgem.jeesite.modules.smt.service.orderdzlbom.SmtOrderDzlbomService;
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

/**
 * 电子料物料bomController
 *
 * @author popo
 * @version 2018-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/smt/orderdzlbom/smtOrderDzlbom")
public class SmtOrderDzlbomController extends BaseController {

    @Autowired
    private SmtOrderDzlbomService smtOrderDzlbomService;

    @ModelAttribute
    public SmtOrderDzlbom get(@RequestParam(required = false) String id) {
        SmtOrderDzlbom entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = smtOrderDzlbomService.get(id);
        }
        if (entity == null) {
            entity = new SmtOrderDzlbom();
        }
        return entity;
    }

    @RequiresPermissions("smt:orderdzlbom:smtOrderDzlbom:view")
    @RequestMapping(value = {"list", ""})
    public String list(SmtOrderDzlbom smtOrderDzlbom, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SmtOrderDzlbom> page = smtOrderDzlbomService.findPage(new Page<SmtOrderDzlbom>(request, response), smtOrderDzlbom);
        model.addAttribute("page", page);
        model.addAttribute("smtOrderDzlbom", smtOrderDzlbom);
        return "modules/smt/orderdzlbom/smtOrderDzlbomList";
    }

    @RequiresPermissions("smt:orderdzlbom:smtOrderDzlbom:view")
    @RequestMapping(value = "form")
    public String form(SmtOrderDzlbom smtOrderDzlbom, Model model) {
        model.addAttribute("smtOrderDzlbom", smtOrderDzlbom);
        return "modules/smt/orderdzlbom/smtOrderDzlbomForm";
    }

    @RequiresPermissions("smt:orderdzlbom:smtOrderDzlbom:edit")
    @RequestMapping(value = "save")
    public String save(SmtOrderDzlbom smtOrderDzlbom, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, smtOrderDzlbom)) {
            return form(smtOrderDzlbom, model);
        }
        smtOrderDzlbomService.save(smtOrderDzlbom);
        addMessage(redirectAttributes, "保存电子料物料bom成功");
        return "redirect:" + Global.getAdminPath() + "/smt/orderdzlbom/smtOrderDzlbom/?repage";
    }

    @RequiresPermissions("smt:orderdzlbom:smtOrderDzlbom:edit")
    @RequestMapping(value = "delete")
    public String delete(SmtOrderDzlbom smtOrderDzlbom, RedirectAttributes redirectAttributes) {
        smtOrderDzlbomService.delete(smtOrderDzlbom);
        addMessage(redirectAttributes, "删除电子料物料bom成功");
        return "redirect:" + Global.getAdminPath() + "/smt/orderdzlbom/smtOrderDzlbom/?repage";
    }

    /**
     * 验证bomName是否存在
     *
     * @return
     */
    @RequestMapping(value = "validateBomName")
    @ResponseBody
    public String validateBomName(String bomName) {
        String tip = "";
        if (StringUtils.isNotBlank(bomName)) {
            int i = smtOrderDzlbomService.validateBomName(bomName);
            if (i>0){
                tip="bom已存在!";
            }
        }else{
            tip = "未获取到bom名称";
        }
        return tip;
    }


}