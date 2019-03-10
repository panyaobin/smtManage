/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.web.paymentapply;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.NumberToCN;
import com.thinkgem.jeesite.modules.smt.vo.SmtPaymentApplyVO;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
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
import com.thinkgem.jeesite.modules.smt.entity.paymentapply.SmtPaymentApply;
import com.thinkgem.jeesite.modules.smt.service.paymentapply.SmtPaymentApplyService;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.thinkgem.jeesite.common.utils.DateUtils.getDate;

/**
 * 付款申请Controller
 *
 * @author popo
 * @version 2019-03-10
 */
@Controller
@RequestMapping(value = "${adminPath}/smt/paymentapply/smtPaymentApply")
public class SmtPaymentApplyController extends BaseController {

    @Autowired
    private SmtPaymentApplyService smtPaymentApplyService;

    @ModelAttribute
    public SmtPaymentApply get(@RequestParam(required = false) String id) {
        SmtPaymentApply entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = smtPaymentApplyService.get(id);
        }
        if (entity == null) {
            entity = new SmtPaymentApply();
        }
        return entity;
    }

    @RequiresPermissions("smt:paymentapply:smtPaymentApply:view")
    @RequestMapping(value = {"list", ""})
    public String list(SmtPaymentApply smtPaymentApply, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SmtPaymentApply> page = smtPaymentApplyService.findPage(new Page<SmtPaymentApply>(request, response), smtPaymentApply);
        model.addAttribute("page", page);
        model.addAttribute("paymentInfoList", getPaymentInfoList());
        return "modules/smt/paymentapply/smtPaymentApplyList";
    }

    @RequiresPermissions("smt:paymentapply:smtPaymentApply:view")
    @RequestMapping(value = "form")
    public String form(SmtPaymentApply smtPaymentApply, Model model) {
        model.addAttribute("smtPaymentApply", smtPaymentApply);
        model.addAttribute("paymentInfoList", getPaymentInfoList());
        return "modules/smt/paymentapply/smtPaymentApplyForm";
    }

    @RequiresPermissions("smt:paymentapply:smtPaymentApply:edit")
    @RequestMapping(value = "save")
    public String save(SmtPaymentApply smtPaymentApply, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, smtPaymentApply)) {
            return form(smtPaymentApply, model);
        }
        smtPaymentApplyService.save(smtPaymentApply);
        addMessage(redirectAttributes, "保存付款申请成功");
        return "redirect:" + Global.getAdminPath() + "/smt/paymentapply/smtPaymentApply/?repage";
    }

    @RequiresPermissions("smt:paymentapply:smtPaymentApply:edit")
    @RequestMapping(value = "delete")
    public String delete(SmtPaymentApply smtPaymentApply, RedirectAttributes redirectAttributes) {
        smtPaymentApplyService.delete(smtPaymentApply);
        addMessage(redirectAttributes, "删除付款申请成功");
        return "redirect:" + Global.getAdminPath() + "/smt/paymentapply/smtPaymentApply/?repage";
    }

    /**
     * 付款申请打印功能
     * @param id
     * @param response
     */
    @RequestMapping(value = "print")
    public void test(String id,HttpServletResponse response) {
        SmtPaymentApplyVO apply = smtPaymentApplyService.getSmtPaymentApplyVoById(id);
        String jasperPath = Global.getConfig("jasper.paymentApply.path");
        System.out.println("当前jasper文件的路径是：" + jasperPath);
        Map<String, Object> map = new HashMap<>(10);
        String date =DateFormatUtils.format(apply.getCreateDate(), "yyyy年MM月dd日");
        map.put("createDate",date);
        map.put("num",String.valueOf(Integer.valueOf(apply.getId())+1000000));
        map.put("paymentUnit",apply.getAccountName());
        map.put("accountNumber",apply.getAccountNumber());
        map.put("accountBank",apply.getAccountBank());
        map.put("paymentMoney", NumberToCN.number2CNMontrayUnit(apply.getPaymentMoney()));
        map.put("money",String.valueOf(apply.getPaymentMoney()));
        map.put("paymentReason",apply.getPaymentReason());
        map.put("remarks",apply.getRemarks());
        map.put("createUser", UserUtils.getUser().getName());
        List<SmtPaymentApplyVO> list=new ArrayList<>();
        list.add(apply);
        try {
            demo(response, map, list, jasperPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}