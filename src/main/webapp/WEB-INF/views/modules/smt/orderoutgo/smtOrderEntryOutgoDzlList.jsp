<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>smt订单入库表管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            //导出
            $("#btnExport").click(function () {
                var customerNo=$("#customerNo").val().trim();
                var productNo=$("#productNo").val().trim();
                var orderNo=$("#orderNo").val().trim();
                window.location.href="${ctx}/smt/orderoutgo/smtOrderOutgo/dzl_export?customerNo="+customerNo+"&productNo="+productNo+"&orderNo="+orderNo;
            });
        });

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
        <li><a href="${ctx}/smt/orderoutgo/smtOrderOutgo/order_entry_fpc_list">在线主FPC待出货</a></li>
    </shiro:hasPermission>
    <shiro:hasPermission name="smt:orderentry:smtOrderEntry:view">
        <li class="active"><a href="${ctx}/smt/orderoutgo/smtOrderOutgo/order_entry_dzl_list">在线电子料待出货</a></li>
    </shiro:hasPermission>
    <shiro:hasPermission name="smt:orderentry:smtOrderEntry:view">
        <li><a href="${ctx}/smt/orderoutgo/smtOrderOutgo/return_goods">成品退货</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="smtOrderEntry" action="${ctx}/smt/orderoutgo/smtOrderOutgo/order_entry_dzl_list" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li style="width: 230px">客户：<select name="customerNo" id="customerNo" class="input-medium">
            <option value="">请选择</option>
            <c:forEach items="${custList}" var="cust">
                <option <c:if test="${cust.customerNo==smtOrderEntry.customerNo}">selected="selected"</c:if> value="${cust.customerNo}" title="${cust.customerName}">${cust.customerName}</option>
            </c:forEach>
        </select>
        </li>
        <li>产品型号：<input type="text" name="productNo" id="productNo" value="${smtOrderEntry.productNo}" style="width: 50%"></li>
        <li>订单号：<input type="text" name="orderNo" id="orderNo" value="${smtOrderEntry.orderNo}" style="width: 50%"></li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnReset" class="btn btn-primary" type="reset" onclick="window.location.href='${ctx}/smt/orderoutgo/smtOrderOutgo/order_entry_dzl_list'" value="重置"/></li>
        <li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>客户名称</th>
        <th>产品型号</th>
        <th>数量</th>
        <th>产品类型</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${page.list==null || page.list.size()<=0}">
        <tr>
            <td colspan="9" style="text-align: center">对不起，没有数据……</td>
        </tr>
    </c:if>
    <c:forEach items="${page.list}" var="smtOrderEntry" varStatus="oe">
        <tr>
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
                    ${smtOrderEntry.counts}
            </td>
            <td>
                    ${fns:getDictLabel(smtOrderEntry.productType, 'smt_product_type', '')}
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>