/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.smt.web.orderoutgo;

import com.alibaba.fastjson.JSON;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.export.excel.ExcelFactory;
import com.thinkgem.jeesite.common.utils.export.excel.XSSFExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.smt.entity.orderdzlbom.SmtOrderDzlbom;
import com.thinkgem.jeesite.modules.smt.entity.orderentry.SmtOrderEntry;
import com.thinkgem.jeesite.modules.smt.entity.orderoutgo.OutGoVO;
import com.thinkgem.jeesite.modules.smt.entity.orderoutgo.SmtOrderOutgo;
import com.thinkgem.jeesite.modules.smt.entity.orderoutgo.SmtOrderOutgoVO;
import com.thinkgem.jeesite.modules.smt.entity.orderoutgodzl.SmtOrderOutgoDzl;
import com.thinkgem.jeesite.modules.smt.service.orderdzlbom.SmtOrderDzlbomService;
import com.thinkgem.jeesite.modules.smt.service.orderentry.SmtOrderEntryService;
import com.thinkgem.jeesite.modules.smt.service.orderoutgo.SmtOrderOutgoService;
import com.thinkgem.jeesite.modules.smt.service.orderoutgodzl.SmtOrderOutgoDzlService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 产品出货表Controller
 *
 * @author popo
 * @version 2018-06-10
 */
@Controller
@RequestMapping(value = "${adminPath}/smt/orderoutgo/smtOrderOutgo")
public class SmtOrderOutgoController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(SmtOrderOutgoController.class);

    @Autowired
    private SmtOrderOutgoService smtOrderOutgoService;

    @Autowired
    private SmtOrderOutgoDzlService smtOrderOutgoDzlService;

    @Autowired
    private SmtOrderEntryService smtOrderEntryService;

    @Autowired
    private SmtOrderDzlbomService smtOrderDzlbomService;

    private static List<OutGoVO> outGoVOList = new ArrayList<>();

    private static Map<String, Object> maps = new HashMap<>();

    private static SmtOrderOutgo smtOrderOutGos = new SmtOrderOutgo();

    private static HttpServletResponse responses;

//    private static final  String jasperPath="D:\\develop\\jasperPath\\outgo.jasper";
    private static final String jasperPath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0\\webapps\\ROOT\\WEB-INF\\classes\\jasper\\outgo.jasper";

    @ModelAttribute
    public SmtOrderOutgo get(@RequestParam(required = false) String id) {
        SmtOrderOutgo entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = smtOrderOutgoService.get(id);
        }
        if (entity == null) {
            entity = new SmtOrderOutgo();
        }
        return entity;
    }
    
    @RequiresPermissions("smt:orderoutgo:smtOrderOutgo:view")
    @RequestMapping(value = {"list", ""})
    public String list(SmtOrderOutgo smtOrderOutgo, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SmtOrderOutgo> page = smtOrderOutgoService.findPage(new Page<SmtOrderOutgo>(request, response), smtOrderOutgo);
        model.addAttribute("page", page);
        return "modules/smt/orderoutgodzl/smtOrderOutgoFpcList";
    }

    /**
     * 产品出货加载入库订单信息  ----fpc
     *
     * @param smtOrderEntry
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("smt:orderentry:smtOrderEntry:view")
    @RequestMapping("/order_entry_fpc_list")
    public String order_entry_fpc_list(SmtOrderEntry smtOrderEntry, HttpServletRequest request, HttpServletResponse response, Model model) {
        smtOrderEntry.setProductType("1");
        Page<SmtOrderEntry> page = smtOrderEntryService.findTotalPage(new Page<SmtOrderEntry>(request, response), smtOrderEntry);
        model.addAttribute("page", page);
        model.addAttribute("custList", getCustomerList());
        model.addAttribute("smtOrderEntry", smtOrderEntry);
        return "modules/smt/orderoutgo/smtOrderEntryOutgoFpcList";
    }


    @RequiresPermissions("smt:orderentry:smtOrderEntry:view")
    @RequestMapping("/export")
    @ResponseBody
    public void export(SmtOrderEntry smtOrderEntry, HttpServletRequest request, HttpServletResponse response) {
        log.info("这里是在线FPC待出货导出数据");
        response.setContentType("application/vnd.ms-excel"); // 告知类型为excel文件
        response.setCharacterEncoding("utf-8");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + new String("在线FPC待出货".getBytes("utf-8"), "ISO8859-1") + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        List<SmtOrderEntry> list = smtOrderEntryService.findTotalLists(smtOrderEntry);
        if (null != list && list.size() > 0) {
            String[] titles = {"客户名称", "产品型号", "订单号", "产品类型", "数量", "入库时间", "备注"}; // 标题
            List<Map<String, Object>> datas = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> data = new LinkedHashMap<>();
                SmtOrderEntry entry = list.get(i);
                data.put("a", entry.getCustomerName());
                data.put("b", entry.getProductNo());
                data.put("c", entry.getOrderNo());
                data.put("d", Integer.valueOf(entry.getProductType()) == 1 ? "FPC" : "电子料");
                data.put("e", Integer.valueOf(entry.getCounts()));
                data.put("f", entry.getCreateDate());
                data.put("g", entry.getRemarks());
                datas.add(data);

            }
            Object[] keys = {"a", "b", "c", "d", "e", "f", "g"};
            XSSFExcel excel = (XSSFExcel) ExcelFactory.createExcel(3, titles, datas, keys);
            try {
                excel.export(response.getOutputStream());
                log.info("----------------------在线FPC待出货导出成功----------------------");
            } catch (IOException e) {
                log.info("----------------------在线FPC待出货导出失败----------------------");
                e.printStackTrace();
            }
        }
    }


    /**
     * 产品出货加载入库订单信息  ----电子料
     *
     * @param smtOrderEntry
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("smt:orderentry:smtOrderEntry:view")
    @RequestMapping("/order_entry_dzl_list")
    public String order_entry_dzl_list(SmtOrderEntry smtOrderEntry, HttpServletRequest request, HttpServletResponse response, Model model) {
        smtOrderEntry.setProductType("2");
        Page<SmtOrderEntry> page = smtOrderEntryService.findDzlMainPage(new Page<SmtOrderEntry>(request, response), smtOrderEntry);
        model.addAttribute("page", page);
        model.addAttribute("custList", getCustomerList());
        model.addAttribute("smtOrderEntry", smtOrderEntry);
        return "modules/smt/orderoutgo/smtOrderEntryOutgoDzlList";
    }


    @RequiresPermissions("smt:orderentry:smtOrderEntry:view")
    @RequestMapping("/dzl_export")
    @ResponseBody
    public void dzl_export(SmtOrderEntry smtOrderEntry, HttpServletRequest request, HttpServletResponse response) {
        log.info("这里是在线电子料待出货导出数据");
        response.setContentType("application/vnd.ms-excel"); // 告知类型为excel文件
        response.setCharacterEncoding("utf-8");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + new String("在线电子料待出货".getBytes("utf-8"), "ISO8859-1") + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        smtOrderEntry.setProductType("2");
        List<SmtOrderEntry> list = smtOrderEntryService.findTotalDzlLists(smtOrderEntry);
        if (null != list && list.size() > 0) {
            String[] titles = {"客户名称", "产品型号", "产品类型", "数量"}; // 标题
            List<Map<String, Object>> datas = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> data = new LinkedHashMap<>();
                SmtOrderEntry entry = list.get(i);
                data.put("a", entry.getCustomerName());
                data.put("b", entry.getProductNo());
                data.put("c", Integer.valueOf(entry.getProductType()) == 1 ? "FPC" : "电子料");
                data.put("d", Integer.valueOf(entry.getCounts()));
                datas.add(data);

            }
            Object[] keys = {"a", "b", "c", "d"};
            XSSFExcel excel = (XSSFExcel) ExcelFactory.createExcel(3, titles, datas, keys);
            try {
                excel.export(response.getOutputStream());
                log.info("----------------------在线电子料待出货导出成功----------------------");
            } catch (IOException e) {
                log.info("----------------------在线电子料待出货导出失败----------------------");
                e.printStackTrace();
            }
        }
    }

    @RequiresPermissions("smt:orderentry:smtOrderEntry:view")
    @RequestMapping("/return_goods")
    public String return_goods_list(SmtOrderEntry smtOrderEntry, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SmtOrderEntry> page = smtOrderEntryService.findReturnGoods(new Page<SmtOrderEntry>(request, response), smtOrderEntry);
        model.addAttribute("page", page);
        model.addAttribute("custList", getCustomerList());
        model.addAttribute("smtOrderEntry", smtOrderEntry);
        return "modules/smt/orderoutgo/smtOrderEntryReturnGoodsList";
    }

    /**
     * 出货列表------------fpc列表
     *
     * @param smtOrderOutgo
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("smt:orderoutgo:smtOrderOutgo:view")
    @RequestMapping("/outgo_fpc_list")
    public String outgo_fpc_list(SmtOrderOutgo smtOrderOutgo, String msg, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
        Page<SmtOrderOutgo> page = smtOrderOutgoService.findPage(new Page<SmtOrderOutgo>(request, response), smtOrderOutgo);
        model.addAttribute("page", page);
        model.addAttribute("smtOrderOutgo", smtOrderOutgo);
        model.addAttribute("custList", getCustomerList());
        if (StringUtils.isNotBlank(msg)) {
            addMessage(redirectAttributes, "出货成功");
        }
        return "modules/smt/orderoutgodzl/smtOrderOutgoFpcList";
    }

    /**
     * 出货列表------------电子料列表
     *
     * @param smtOrderOutgoDzl
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequiresPermissions("smt:orderoutgo:smtOrderOutgo:view")
    @RequestMapping("/outgo_dzl_list")
    public String outgo_dzl_list(SmtOrderOutgoDzl smtOrderOutgoDzl, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<SmtOrderOutgoDzl> page = smtOrderOutgoDzlService.findPage(new Page<SmtOrderOutgoDzl>(request, response), smtOrderOutgoDzl);
        model.addAttribute("page", page);
        model.addAttribute("smtOrderOutgoDzl", smtOrderOutgoDzl);
        model.addAttribute("custList", getCustomerList());
        
        if (StringUtils.isNotBlank(smtOrderOutgoDzl.getStartDate())){
            model.addAttribute("startDate", DateUtils.parseDate(smtOrderOutgoDzl.getStartDate()));
        }
        if (StringUtils.isNotBlank(smtOrderOutgoDzl.getEndDate())){
            model.addAttribute("endDate", DateUtils.parseDate(smtOrderOutgoDzl.getEndDate()));
        }
        
        return "modules/smt/orderoutgodzl/smtOrderOutgoDzlList";
    }


    /**
     * fpc待出货根据ID查询出货信息
     *
     * @param id
     * @param model
     * @return
     */
    @RequiresPermissions("smt:orderoutgo:smtOrderOutgo:view")
    @RequestMapping(value = "outgo")
    public String outgo(String id, Model model) {
        SmtOrderEntry smtOrderEntry = smtOrderEntryService.findOrderInfoById(id);
        model.addAttribute("smtOrderEntry", smtOrderEntry);
        model.addAttribute("custList", getCustomerList());
        return "modules/smt/orderoutgo/smtOutGoCountList";
    }

    /**
     * 自动计算电子料信息
     *
     * @param smtOrderOutgo
     * @return
     */
    @RequestMapping(value = "search")
    @ResponseBody
    public String search(SmtOrderOutgo smtOrderOutgo) {
        //通过客户编码和产品型号查询对应的客户电子料信息
        List<SmtOrderDzlbom> detail = smtOrderDzlbomService.findDzlDetail(smtOrderOutgo.getCustomerNo(), smtOrderOutgo.getProductNo());
        for (SmtOrderDzlbom smtOrderDzlbom : detail) {
            int sp = Integer.valueOf(smtOrderDzlbom.getCounts());
            smtOrderDzlbom.setCounts(String.valueOf(Integer.valueOf(smtOrderDzlbom.getCounts()) * smtOrderOutgo.getCounts()));
            double sc = Double.valueOf(smtOrderDzlbom.getStockCounts()) / 1000 * smtOrderOutgo.getCounts() * sp;
            smtOrderDzlbom.setStockCounts(String.valueOf(Math.round(sc)));
        }
        return JSON.toJSONString(detail);
    }

//    /**
//     * 电子料出货保存并显示打印信息
//     * @param smtOrderOutgo
//     * @param rows
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "outgo_save",method = RequestMethod.GET)
//    @ResponseBody
//    public String outgo_save(SmtOrderOutgo smtOrderOutgo,String rows,Model model) {
//        String state="success";
//        //前端bootstrapTable传过来的电子料使用量数据
//        String json = StringEscapeUtils.unescapeHtml(rows);
//        JSONArray jsonArray=JSONArray.fromObject(json);
//        try {
//            //smtOrderOutgoService.to_outgo(smtOrderOutgo, jsonArray);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        model.addAttribute("smtOrderOutgo",smtOrderOutgo);
//        model.addAttribute("jsonArray",jsonArray);
//       return  state;
//    }

    /**
     * 电子料出货信息保存
     *
     * @param smtOrderOutgo
     * @param rows
     */
    @RequestMapping(value = "outgo_save", method = RequestMethod.GET)
    @ResponseBody
    public String outgo_save_memo(SmtOrderOutgo smtOrderOutgo, String rows, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        String state = "success";
        //前端bootstrapTable传过来的电子料使用量数据
        String json = StringEscapeUtils.unescapeHtml(rows);
        JSONArray jsonArray = JSONArray.fromObject(json);
        try {
            String outgo = smtOrderOutgoService.to_outgo(smtOrderOutgo, jsonArray);
            addMessage(redirectAttributes, "出货成功!");
            Map<String, Object> map = new HashMap<>();
            map.put("address", "东莞市长安镇锦富路29号");
            map.put("phone", "18588206782");
            map.put("customerName", smtOrderOutgo.getCustomerName());
            map.put("sendNo", outgo);
            map.put("productNo", smtOrderOutgo.getProductNo());
            map.put("fpcCounts", String.valueOf(smtOrderOutgo.getCounts()));
            map.put("pointCounts", String.valueOf(smtOrderOutgo.getPointCounts()));
            map.put("createDate", DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
            map.put("orderNo", smtOrderOutgo.getOrderNo());
            map.put("createUser", UserUtils.getUser().getName());

            int size = 20 - jsonArray.size();
            List<OutGoVO> outGoVOS = new ArrayList<>();
            if (jsonArray.size() < 20) {
                for (int i = 0; i < size; i++) {
                    OutGoVO vo = new OutGoVO();
                    vo.setIndex(String.valueOf(jsonArray.size() + i));
                    vo.setBomName("");
                    vo.setBomType("");
                    vo.setCounts("");
                    vo.setStockCounts("");
                    jsonArray.add(vo);
                }
            }

            for (int i = 0; i < 10; i++) {
                OutGoVO vo = new OutGoVO();
                vo.setIndex(String.valueOf(i + 1));
                vo.setBomName(((JSONObject) jsonArray.get(i)).get("bomName").toString());
                vo.setBomType(((JSONObject) jsonArray.get(i)).get("bomType").toString());
                vo.setCounts(((JSONObject) jsonArray.get(i)).get("counts").toString());
                vo.setStockCounts(((JSONObject) jsonArray.get(i)).get("stockCounts").toString());
                vo.setIndexs(String.valueOf(11 + i));
                vo.setBomNames(((JSONObject) jsonArray.get(10 + i)).get("bomName").toString());
                vo.setBomTypes(((JSONObject) jsonArray.get(10 + i)).get("bomType").toString());
                vo.setCountss(((JSONObject) jsonArray.get(10 + i)).get("counts").toString());
                vo.setStockCountss(((JSONObject) jsonArray.get(10 + i)).get("stockCounts").toString());
                outGoVOS.add(vo);
            }
            maps = map;
            outGoVOList = outGoVOS;
            smtOrderOutGos = smtOrderOutgo;
            responses = response;
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "操作失败!");
            return "error/500";
        }
        return state;
    }


    /**
     * FPC出货列表重新打印PDF
     * @param response
     */
    @RequestMapping("/reprint_outgo")
    @ResponseBody
    public void reprint_outgo(HttpServletResponse response,String outgoOrderNo){
        List<SmtOrderOutgoVO> outgoList = smtOrderOutgoService.selectByOutGoOrderNo(outgoOrderNo);
        SmtOrderOutgoVO outgoVO = outgoList.get(0);

        List<SmtOrderOutgoDzl> dzlList = smtOrderOutgoDzlService.selectByOutGoId(outgoVO.getId());
        dzlList.forEach(x->{
            x.setBomName(x.getDzlId());
            x.setBomType("电子料");
        });
        Map<String, Object> map = new HashMap<>();
        map.put("address", "东莞市长安镇锦富路29号");
        map.put("phone", "18588206782");
        map.put("customerName", outgoVO.getCustomerName());
        map.put("sendNo", outgoVO.getOutgoOrderNo());
        map.put("productNo", outgoVO.getProductNo());
        map.put("fpcCounts", String.valueOf(outgoVO.getCounts()));
        map.put("pointCounts", String.valueOf(outgoVO.getPointCounts()));
        map.put("createDate", DateUtils.formatDate(outgoVO.getCreateDate(), "yyyy-MM-dd"));
        map.put("orderNo", outgoVO.getOrderNo());
        map.put("createUser",outgoVO.getCreateByName());
        
        
        int size = 24 - dzlList.size();
        List<SmtOrderOutgoDzl> outGoVOS = new ArrayList<>();
        if (dzlList.size() < 24) {
            for (int i = 0; i < size; i++) {
                SmtOrderOutgoDzl vo = new SmtOrderOutgoDzl();
                vo.setIndex(String.valueOf(dzlList.size() + i));
                vo.setBomName("");
                vo.setBomType("");
                vo.setCounts("");
                vo.setStockCounts("");
                dzlList.add(vo);
            }
        }

        for (int i = 0; i < 12; i++) {
            SmtOrderOutgoDzl vo = new SmtOrderOutgoDzl();
            vo.setIndex(String.valueOf(i + 1));
            vo.setBomName(dzlList.get(i).getBomName());
            vo.setBomType(dzlList.get(i).getBomType());
            vo.setCounts(dzlList.get(i).getCounts());
            vo.setStockCounts(dzlList.get(i).getStockCounts());
            vo.setIndexs(String.valueOf(13+i));
            vo.setBomNames((dzlList.get(12 + i)).getBomName());
            vo.setBomTypes((dzlList.get(12 + i)).getBomType());
            vo.setCountss((dzlList.get(12 + i)).getCounts());
            vo.setStockCountss(dzlList.get(12 + i).getStockCounts());
            outGoVOS.add(vo);
        }
        
        try {
            log.info("jasper文件路径：" + jasperPath);
            demo(response, map, outGoVOS, jasperPath);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    

    /**
     * 新版本的打印信息
     *
     * @return
     */
//    @RequestMapping(value = "print")
//    public  String print(Model model){
//        model.addAttribute("map",maps);
//        model.addAttribute("smtOrderOutgo",smtOrderOutGos);
//        model.addAttribute("outGoVOList",outGoVOList);
//        return "modules/smt/orderoutgo/smtOutGoPrintList";
//    }
    @RequestMapping(value = "print")
    @ResponseBody
    public String test() {
        log.info("jasper文件路径：" + jasperPath);
        demo(responses, maps, outGoVOList, jasperPath);
        return null;
    }


    @RequiresPermissions("smt:orderoutgo:smtOrderOutgo:view")
    @RequestMapping(value = "form")
    public String form(SmtOrderOutgo smtOrderOutgo, Model model) {
        model.addAttribute("smtOrderOutgo", smtOrderOutgo);
        return "modules/smt/orderoutgo/smtOrderOutgoForm";
    }

    @RequiresPermissions("smt:orderoutgo:smtOrderOutgo:edit")
    @RequestMapping(value = "save")
    public String save(SmtOrderOutgo smtOrderOutgo, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, smtOrderOutgo)) {
            return form(smtOrderOutgo, model);
        }
        smtOrderOutgoService.save(smtOrderOutgo);
        addMessage(redirectAttributes, "保存产品出货表成功");
        return "redirect:" + Global.getAdminPath() + "/smt/orderoutgo/smtOrderOutgo/?repage";
    }

    @RequiresPermissions("smt:orderoutgo:smtOrderOutgo:edit")
    @RequestMapping(value = "delete")
    public String delete(SmtOrderOutgo smtOrderOutgo, RedirectAttributes redirectAttributes) {
        try {
            smtOrderOutgoService.to_delete(smtOrderOutgo);
            addMessage(redirectAttributes, "撤销此次出货成功");
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "操作失败!");
        }

        return "redirect:" + Global.getAdminPath() + "/smt/orderoutgo/smtOrderOutgo/?repage";
    }

    /**
     * 撤销退货
     *
     * @param smtOrderOutgo
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("smt:orderoutgo:smtOrderOutgo:edit")
    @RequestMapping(value = "delete_return")
    public String delete_return(SmtOrderOutgo smtOrderOutgo, RedirectAttributes redirectAttributes) {
        try {
            smtOrderOutgoService.to_delete(smtOrderOutgo);
            addMessage(redirectAttributes, "撤销成功");
        } catch (Exception e) {
            e.printStackTrace();
            addMessage(redirectAttributes, "操作失败!");
        }

        return "redirect:" + Global.getAdminPath() + "/smt/orderoutgo/smtOrderOutgo/?repage";
    }

    /**
     * 已退货列表信息
     *
     * @param smtOrderOutgo
     * @return
     */
    @RequiresPermissions("smt:orderoutgo:smtOrderOutgo:view")
    @RequestMapping(value = "return_goods_lists")
    public String return_goods_lists(SmtOrderOutgo smtOrderOutgo, Model model, HttpServletRequest request, HttpServletResponse response) {
        Page<SmtOrderOutgo> page = smtOrderOutgoService.findReturnGoodsPage(new Page<SmtOrderOutgo>(request, response), smtOrderOutgo);
        model.addAttribute("page", page);
        model.addAttribute("smtOrderOutgo", smtOrderOutgo);
        model.addAttribute("custList", getCustomerList());
        return "modules/smt/orderoutgodzl/smtOutgoReturnGoodsList";
    }


    /**
     * 导出数据
     * @param outgoDzl
     * @param response
     * @return
     */
    @RequestMapping(value = "btnDzlExport")
    public String btnExport(SmtOrderOutgoDzl outgoDzl,HttpServletResponse response) {
        // 告知类型为excel文件
        response.setContentType("application/vnd.ms-excel"); 
        response.setCharacterEncoding("utf-8");
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + new String("电子料出货列表".getBytes("utf-8"), "ISO8859-1") + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> datas = new ArrayList<>();
        
        List<SmtOrderOutgoDzl> list = smtOrderOutgoService.export(outgoDzl);
        
        String[] titles = {"序号", "客户", "物料型号", "物料类型","出货日期","出货单号", "产品型号","FPC出货", "用量", "出货数量", "备品", "备注"}; // 标题
        if (null != list && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> data = new LinkedHashMap<>();
                SmtOrderOutgoDzl go = list.get(i);
                data.put("a",i+1);
                data.put("b",go.getCustomerName());
                data.put("c",go.getBomName());
                data.put("d",go.getBomType());
                data.put("e",go.getCreateDate());
                data.put("f",go.getOutgoOrderNo());
                data.put("g",go.getProductNo());
                data.put("h",go.getFpcCounts());
                data.put("i",go.getCustBomCount());
                data.put("j",go.getCounts());
                data.put("k",go.getStockCounts());
                data.put("l",go.getRemarks());
                datas.add(data);
            }
        }
        Object[] keys = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k","l"};
        try {
            XSSFExcel excel = (XSSFExcel) ExcelFactory.createExcel(3, titles, datas, keys);
            excel.export(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "modules/smt/orderoutgodzl/smtOrderOutgoDzlList";
    }
    
}