/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.web.orderoutgodzl;

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
import com.thinkgem.jeesite.modules.smt.entity.orderoutgodzl.SmtOrderOutgoDzl;
import com.thinkgem.jeesite.modules.smt.service.orderoutgodzl.SmtOrderOutgoDzlService;

/**
 * 电子料出货信息表Controller
 * @author popo
 * @version 2018-07-14
 */
@Controller
@RequestMapping(value = "${adminPath}/smt/orderoutgodzl/smtOrderOutgoDzl")
public class SmtOrderOutgoDzlController extends BaseController {

	@Autowired
	private SmtOrderOutgoDzlService smtOrderOutgoDzlService;
	
	@ModelAttribute
	public SmtOrderOutgoDzl get(@RequestParam(required=false) String id) {
		SmtOrderOutgoDzl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smtOrderOutgoDzlService.get(id);
		}
		if (entity == null){
			entity = new SmtOrderOutgoDzl();
		}
		return entity;
	}
	
	@RequiresPermissions("smt:orderoutgodzl:smtOrderOutgoDzl:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmtOrderOutgoDzl smtOrderOutgoDzl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmtOrderOutgoDzl> page = smtOrderOutgoDzlService.findPage(new Page<SmtOrderOutgoDzl>(request, response), smtOrderOutgoDzl); 
		model.addAttribute("page", page);
		return "modules/smt/orderoutgodzl/smtOrderOutgoDzlList";
	}

	@RequiresPermissions("smt:orderoutgodzl:smtOrderOutgoDzl:view")
	@RequestMapping(value = "form")
	public String form(SmtOrderOutgoDzl smtOrderOutgoDzl, Model model) {
		model.addAttribute("smtOrderOutgoDzl", smtOrderOutgoDzl);
		return "modules/smt/orderoutgodzl/smtOrderOutgoDzlForm";
	}

	@RequiresPermissions("smt:orderoutgodzl:smtOrderOutgoDzl:edit")
	@RequestMapping(value = "save")
	public String save(SmtOrderOutgoDzl smtOrderOutgoDzl, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smtOrderOutgoDzl)){
			return form(smtOrderOutgoDzl, model);
		}
		smtOrderOutgoDzlService.save(smtOrderOutgoDzl);
		addMessage(redirectAttributes, "保存电子料出货成功");
		return "redirect:"+Global.getAdminPath()+"/smt/orderoutgodzl/smtOrderOutgoDzl/?repage";
	}
	
	@RequiresPermissions("smt:orderoutgodzl:smtOrderOutgoDzl:edit")
	@RequestMapping(value = "delete")
	public String delete(SmtOrderOutgoDzl smtOrderOutgoDzl, RedirectAttributes redirectAttributes) {
		smtOrderOutgoDzlService.delete(smtOrderOutgoDzl);
		addMessage(redirectAttributes, "删除电子料出货成功");
		return "redirect:"+Global.getAdminPath()+"/smt/orderoutgodzl/smtOrderOutgoDzl/?repage";
	}

}