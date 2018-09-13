/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.web.syscustomer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.smt.entity.syscustomer.SmtSysCustomer;
import com.thinkgem.jeesite.modules.smt.service.syscustomer.SmtSysCustomerService;

/**
 * 客户信息管理Controller
 * @author popo
 * @version 2018-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/smt/syscustomer/smtSysCustomer")
public class SmtSysCustomerController extends BaseController {

	@Autowired
	private SmtSysCustomerService smtSysCustomerService;
	
	@ModelAttribute
	public SmtSysCustomer get(@RequestParam(required=false) String id) {
		SmtSysCustomer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smtSysCustomerService.get(id);
		}
		if (entity == null){
			entity = new SmtSysCustomer();
		}
		return entity;
	}
	
	@RequiresPermissions("smt:syscustomer:smtSysCustomer:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmtSysCustomer smtSysCustomer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmtSysCustomer> page = smtSysCustomerService.findPage(new Page<SmtSysCustomer>(request, response), smtSysCustomer); 
		model.addAttribute("page", page);
		model.addAttribute("smtSysCustomer", smtSysCustomer);
		return "modules/smt/syscustomer/smtSysCustomerList";
	}

	@RequiresPermissions("smt:syscustomer:smtSysCustomer:view")
	@RequestMapping(value = "form")
	public String form(SmtSysCustomer smtSysCustomer, Model model) {
		model.addAttribute("smtSysCustomer", smtSysCustomer);
		return "modules/smt/syscustomer/smtSysCustomerForm";
	}

	@RequiresPermissions("smt:syscustomer:smtSysCustomer:edit")
	@RequestMapping(value = "save")
	public String save(SmtSysCustomer smtSysCustomer, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smtSysCustomer)){
			return form(smtSysCustomer, model);
		}
		smtSysCustomerService.save(smtSysCustomer);
		addMessage(redirectAttributes, "保存客户信息管理成功");
		return "redirect:"+Global.getAdminPath()+"/smt/syscustomer/smtSysCustomer/?repage";
	}
	
	@RequiresPermissions("smt:syscustomer:smtSysCustomer:edit")
	@RequestMapping(value = "delete")
	public String delete(SmtSysCustomer smtSysCustomer, RedirectAttributes redirectAttributes) {
		smtSysCustomerService.delete(smtSysCustomer);
		addMessage(redirectAttributes, "删除客户信息管理成功");
		return "redirect:"+Global.getAdminPath()+"/smt/syscustomer/smtSysCustomer/?repage";
	}

}