/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.web.orderonline;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.modules.smt.entity.orderentry.SmtOrderEntry;
import com.thinkgem.jeesite.modules.smt.entity.syscustomer.SmtSysCustomer;
import com.thinkgem.jeesite.modules.smt.service.orderentry.SmtOrderEntryService;
import com.thinkgem.jeesite.modules.smt.service.syscustomer.SmtSysCustomerService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
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
import com.thinkgem.jeesite.modules.smt.entity.orderonline.SmtOrderOnline;
import com.thinkgem.jeesite.modules.smt.service.orderonline.SmtOrderOnlineService;

import java.util.Date;
import java.util.List;

/**
 * smt在线生产表Controller
 * @author popo
 * @version 2018-06-09
 */
@Controller
@RequestMapping(value = "${adminPath}/smt/orderonline/smtOrderOnline")
public class SmtOrderOnlineController extends BaseController {

	@Autowired
	private SmtOrderOnlineService smtOrderOnlineService;

	@Autowired
    private SmtOrderEntryService smtOrderEntryService;

    @Autowired
    private SmtSysCustomerService smtSysCustomerService;

	@ModelAttribute
	public SmtOrderOnline get(@RequestParam(required=false) String id) {
		SmtOrderOnline entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = smtOrderOnlineService.get(id);
		}
		if (entity == null){
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
        model.addAttribute("page", page);
        return "modules/smt/orderonline/smtOrderOnlineFpcList";
    }

    /**
     * 仓库在线生产列表，区分产品类型   ----电子料入库列表
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
        model.addAttribute("page", page);
        return "modules/smt/orderonline/smtOrderOnlineDzlList";
    }


    /**
     * 发料记录查询
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
        //加载客户信息
        SmtSysCustomer customer=new SmtSysCustomer();
        List<SmtSysCustomer> list = smtSysCustomerService.findList(customer);
        model.addAttribute("custList",list);
		model.addAttribute("page", page);
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
     * @param smtOrderOnline
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("smt:orderonline:smtOrderOnline:edit")
    @RequestMapping(value = "to_save_record")
    public String to_save_send(SmtOrderOnline smtOrderOnline, Model model, RedirectAttributes redirectAttributes) {
//        String[] proNo = smtOrderOnline.getProductNo().split(",");
//        String[] orderNo = smtOrderOnline.getOrderNo().split(",");
//        String[] proType = smtOrderOnline.getProductType().split(",");
//
//        for (int i = 0; i < proNo.length; i++) {
//            SmtOrderOnline online = new SmtOrderOnline();
//            online.setOrderNo();
//        }

        //后端验证发料数据不得为空

            /*for (int i = 0; i < arr.length; i++) {
                SmtOrderEntry orderEntry = smtOrderEntryService.get(arr[i]);
                SmtOrderOnline orderOnline = new SmtOrderOnline();
                BeanUtils.copyProperties(orderEntry, orderOnline);
                orderOnline.setCreateDate(new Date());
                orderOnline.setCreateBy(UserUtils.getUser());
                orderOnline.setUpdateDate(new Date());
                orderOnline.setUpdateBy(UserUtils.getUser());
                orderOnline.setId(""); //通过copy而来 主id有值，save方法为修改方法，需要置空
                try {
                    smtOrderOnlineService.save(orderOnline);
                    addMessage(redirectAttributes, "发料成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    addMessage(redirectAttributes, "    发料失败");
                }
            }*/
        return "redirect:send_records";
    }

    /**
     * 在线生产录入操作，由仓库发料而来
     * @return
     */
    @RequiresPermissions("smt:orderonline:smtOrderOnline:edit")
    @RequestMapping(value = "save_fpc_record")
    public String save_fpc_send(SmtOrderEntry smtOrderEntry,Model model) {
        List<SmtOrderEntry> fpcRecord=smtOrderEntryService.findSendRecord(smtOrderEntry);
        //加载客户信息
        SmtSysCustomer customer=new SmtSysCustomer();
        List<SmtSysCustomer> list = smtSysCustomerService.findList(customer);
        model.addAttribute("custList",list);
        model.addAttribute("fpcRecord",fpcRecord);
        return "modules/smt/warehouse/smtSendRecordFpcList";
    }

    /**
     * 在线生产录入操作，由仓库发料而来
     * @param ids 表示勾选的 仓库已有订单，录入在线生产表
     * @return
     */
    @RequiresPermissions("smt:orderonline:smtOrderOnline:edit")
    @RequestMapping(value = "save_dzl_record")
    public String save_dzl_send(String ids) {


        return "modules/smt/warehouse/smtSendRecordDzlList";
    }

    /**
     * 返回发料记录提示信息使用
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
		addMessage(redirectAttributes, "删除smt在线生产表成功");
		return "redirect:"+Global.getAdminPath()+"/smt/orderonline/smtOrderOnline/?repage";
	}

}