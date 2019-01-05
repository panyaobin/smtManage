<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>smt在线生产表管理</title>
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
             * 出货验证，不允许超过8条
             */
            $("#btnSend").bind("click", function () {
                var proType = $("#productType").val();
                var ids = getAllCheckId();
                if (ids.split(",").length / 2 <= 8) {
                    if (ids != "" && ids != null) {
                        location.href = "${ctx}/smt/productentry/smtProductEntry/save_product_entry?id=" + ids + "&productType=" + proType;
                    } else {
                        showTip("请选择需要入库的订单！");
                    }
                } else {
                    showTip("入库一次性不得超过8条!");
                }
            });
            
            //导出
            $("#btnExport").click(function () {
                var customerNo=$("#customerNo").val().trim();
                var productNo=$("#productNo").val().trim();
                window.location.href="${ctx}/smt/orderonline/smtOrderOnline/export?customerNo="+customerNo+"&productNo="+productNo;
            });
        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

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
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li><a href="${ctx}/smt/orderonline/smtOrderOnline/fpc_list">主FPC结存</a></li>
    <shiro:hasPermission name="smt:orderonline:smtOrderOnline:view">
        <li><a href="${ctx}/smt/orderonline/smtOrderOnline/dzl_list">电子料结存</a></li>
    </shiro:hasPermission>
    <shiro:hasPermission name="smt:orderonline:smtOrderOnline:view">
        <li class="active"><a href="${ctx}/smt/orderonline/smtOrderOnline/total_list">在线结存</a></li>
    </shiro:hasPermission>
    <shiro:hasPermission name="smt:orderonline:smtOrderOnline:view">
        <li><a href="${ctx}/smt/productentry/smtProductEntry/product_entry_list?storageType=1">成品入库记录</a></li>
    </shiro:hasPermission>
    <shiro:hasPermission name="smt:orderonline:smtOrderOnline:view">
        <li><a href="${ctx}/smt/productentry/smtProductEntry/product_entry_list_half?storageType=2">半成品入库记录</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="smtOrderOnline" action="${ctx}/smt/orderonline/smtOrderOnline/total_list" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="productType" name="productType" type="hidden" value="${smtOrderOnline.productType}"/>
    <ul class="ul-form">
        <li style="width: 230px">客户：<select name="customerNo" id="customerNo" class="input-medium">
            <option value="">请选择</option>
            <c:forEach items="${custList}" var="cust">
                <option <c:if test="${cust.customerNo==smtOrderOnline.customerNo}">selected="selected"</c:if> value="${cust.customerNo}" title="${cust.customerName}">${cust.customerName}</option>
            </c:forEach>
        </select>
        </li>
        <li>产品型号：<input type="text" name="productNo" id="productNo" value="${smtOrderOnline.productNo}" style="width: 50%"></li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnReset" class="btn btn-primary" type="reset" onclick="window.location.href='${ctx}/smt/orderonline/smtOrderOnline/total_list'" value="重置"/></li>
        <li class="btns"><input id="btnSend" class="btn btn-primary" type="button" value="半成品入库"/></li>
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
    <c:forEach items="${page.list}" var="smtOrderOnline" varStatus="oo">
        <tr>
            <td style="text-align: center">
                <input type="checkbox" id="id_${smtOrderOnline.id}" value="${smtOrderOnline.id}"/>
            </td>
            <td>
                    ${oo.count}
            </td>

            <td>
                    ${smtOrderOnline.customerName}
            </td>

            <td>
                    ${smtOrderOnline.productNo}
            </td>
            <td>
                    ${fns:getDictLabel(smtOrderOnline.productType, 'smt_product_type', '')}
            </td>

            <td>
                    ${smtOrderOnline.counts}
            </td>

            <td>
                <fmt:formatDate value="${smtOrderOnline.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>

            <td>
                    ${smtOrderOnline.remarks}
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>