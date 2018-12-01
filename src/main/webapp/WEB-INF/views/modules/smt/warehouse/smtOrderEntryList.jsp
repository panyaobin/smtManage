<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>仓库发料汇总信息</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            
            /*全选和反选 */
            $("input[type='checkbox'][id='ids_']").bind("click", function () {
                if ($(this).attr("checked") == 'checked') {
                    $("input[id^='id_']").attr("checked", true);
                } else {
                    $("input[id^='id_']").attr("checked", false);
                }
            });

            /**
             * 发料验证，不允许超过8条
             */
            $("#btnSend").bind("click",function(){
                var proType=$("#productType").val();
                var ids = getAllCheckId();
                if(ids.split(",").length<=8){
                    if(ids !="" && ids != null){
                        location.href= "${ctx}/smt/orderonline/smtOrderOnline/save_record?id="+ids+"&productType="+proType;
                    }else{
                        showTip("请选择需要发料的订单！");
                    }
                }else {
                    showTip("发料一次性不得超过8条!");
                }
            });
            
            //导出
            $("#btnExport").click(function () {
                var customerNo=$("#customerNo").val().trim();
                var productNo=$("#productNo").val().trim();
                window.location.href="${ctx}/smt/orderentry/smtOrderEntry/export?customerNo="+customerNo+"&productNo="+productNo;
            });

        });
        


        /**
         * 该方法用于获取所有已经选中的ID
         */
        function getAllCheckId() {
            var ids = "";
            $("input[id^='id_']:checked").each(function () {
                ids += $(this).val() + ",";
            });
            return ids.substring(0, ids.length - 1);
        }
        
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <shiro:hasPermission name="smt:orderentry:smtOrderEntry:view">
        <li><a href="${ctx}/smt/orderentry/smtOrderEntry/fpc_list">主FPC仓</a></li>
        <li><a href="${ctx}/smt/orderentry/smtOrderEntry/dzl_list">电子料仓</a></li>
        <li class="active"><a href="${ctx}/smt/orderentry/smtOrderEntry/total_list">仓库发料</a></li>
        <li><a href="${ctx}/smt/orderonline/smtOrderOnline/send_record">发料记录</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="smtOrderEntry" action="${ctx}/smt/orderentry/smtOrderEntry/total_list" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="productType" name="productType" type="hidden" value="${smtOrderEntry.productType}"/>
    <ul class="ul-form">
        <li style="width: 230px">客户：<select name="customerNo" id="customerNo" class="input-medium">
            <option value="">请选择</option>
            <c:forEach items="${custList}" var="cust">
                <option <c:if test="${cust.customerNo==smtOrderEntry.customerNo}">selected="selected"</c:if> value="${cust.customerNo}" title="${cust.customerName}">${cust.customerName}</option>
            </c:forEach>
        </select>
        </li>
        <li>产品型号：<input type="text" name="productNo" id="productNo" value="${smtOrderEntry.productNo}" style="width: 50%"></li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnReset" class="btn btn-primary" type="reset" onclick="window.location.href='${ctx}/smt/orderentry/smtOrderEntry/total_list'" value="重置"/></li>
        <li class="btns"><input id="btnSend" class="btn btn-primary" type="button" value="发料"/></li>
        <li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th style="width:3em;text-align: center"><input type="checkbox" id="ids_"></th>
        <th>序号</th>
        <th>客户名称</th>
        <th>产品型号</th>
        <th>产品类型</th>
        <th>数量</th>
        <th>入库时间</th>
        <th>备注</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${page.list==null || page.list.size()<=0}">
        <tr>
            <td colspan="7" style="text-align: center">对不起，没有数据……</td>
        </tr>
    </c:if>
    <c:forEach items="${page.list}" var="smtOrderEntry" varStatus="oe">
        <tr>
            <td style="text-align: center">
                <input type="checkbox" id="id_${smtOrderEntry.id}" value="${smtOrderEntry.id}"/>
            </td>
            <td>
                    ${oe.count}
            </td>

            <td>
                    ${smtOrderEntry.customerName}
            </td>

            <td>
                    ${smtOrderEntry.productNo}
            </td>
            <td>
                    ${fns:getDictLabel(smtOrderEntry.productType, 'smt_product_type', '')}
            </td>

            <td>
                    ${smtOrderEntry.counts}
            </td>

            <td>
                <fmt:formatDate value="${smtOrderEntry.orderDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>

            <td>
                    ${smtOrderEntry.remarks}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>