/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.web.ordercustbomdetail;

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
import com.thinkgem.jeesite.modules.smt.entity.ordercustbomdetail.SmtOrderCustbomDetail;
import com.thinkgem.jeesite.modules.smt.service.ordercustbomdetail.SmtOrderCustbomDetailService;

/**
 * 客户BOM明细表Controller
 * @author popo
 * @version 2018-06-16
 */
@Controller
@RequestMapping(value = "${adminPath}/smt/ordercustbomdetail/smtOrderCustbomDetail")
public class SmtOrderCustbomDetailController extends BaseController {

	@Autowired
	private SmtOrderCustbomDetailService smtOrderCustbomDetailService;
	
	@ModelAttribute
	public SmtOrderCustbomDetail get(@RequestParam(required=false) String id) {
		SmtOrderCustbomDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smtOrderCustbomDetailService.get(id);
		}
		if (entity == null){
			entity = new SmtOrderCustbomDetail();
		}
		return entity;
	}
	
	@RequiresPermissions("smt:ordercustbomdetail:smtOrderCustbomDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(SmtOrderCustbomDetail smtOrderCustbomDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SmtOrderCustbomDetail> page = smtOrderCustbomDetailService.findPage(new Page<SmtOrderCustbomDetail>(request, response), smtOrderCustbomDetail); 
		model.addAttribute("page", page);
		return "modules/smt/ordercustbomdetail/smtOrderCustbomDetailList";
	}

	@RequiresPermissions("smt:ordercustbomdetail:smtOrderCustbomDetail:view")
	@RequestMapping(value = "form")
	public String form(SmtOrderCustbomDetail smtOrderCustbomDetail, Model model) {
		model.addAttribute("smtOrderCustbomDetail", smtOrderCustbomDetail);
		return "modules/smt/ordercustbomdetail/smtOrderCustbomDetailForm";
	}

	@RequiresPermissions("smt:ordercustbomdetail:smtOrderCustbomDetail:edit")
	@RequestMapping(value = "save")
	public String save(SmtOrderCustbomDetail smtOrderCustbomDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, smtOrderCustbomDetail)){
			return form(smtOrderCustbomDetail, model);
		}
		smtOrderCustbomDetailService.save(smtOrderCustbomDetail);
		addMessage(redirectAttributes, "保存客户BOM明细表成功");
		return "redirect:"+Global.getAdminPath()+"/smt/ordercustbomdetail/smtOrderCustbomDetail/?repage";
	}
	
	@RequiresPermissions("smt:ordercustbomdetail:smtOrderCustbomDetail:edit")
	@RequestMapping(value = "delete")
	public String delete(SmtOrderCustbomDetail smtOrderCustbomDetail, RedirectAttributes redirectAttributes) {
		smtOrderCustbomDetailService.delete(smtOrderCustbomDetail);
		addMessage(redirectAttributes, "删除客户BOM明细表成功");
		return "redirect:"+Global.getAdminPath()+"/smt/ordercustbomdetail/smtOrderCustbomDetail/?repage";
	}

}