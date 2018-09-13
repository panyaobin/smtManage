<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>出货管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {

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
    <li class="active"><a href="${ctx}/smt/orderoutgo/smtOrderOutgo/outgo_fpc_list">FPC出货列表</a></li>
    <li><a href="${ctx}/smt/orderoutgo/smtOrderOutgo/outgo_dzl_list">电子料出货列表</a></li>
    <li><a href="${ctx}/smt/orderoutgo/smtOrderOutgo/return_goods_lists">已退货列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="smtOrderOutgo" action="${ctx}/smt/orderoutgo/smtOrderOutgo/outgo_fpc_list" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li>出货单号：<input type="text" name="outgoOrderNo" value="${smtOrderOutgo.outgoOrderNo}" style="width: 50%"></li>
        <li style="width: 230px">客户：<select name="customerNo" id="" class="input-medium">
            <option value="">请选择</option>
            <c:forEach items="${custList}" var="cust">
                <option <c:if test="${cust.customerNo==smtOrderOutgo.customerNo}">selected="selected"</c:if> value="${cust.customerNo}" title="${cust.customerName}">${cust.customerName}</option>
            </c:forEach>
        </select>
        </li>
        <li>产品型号：<input type="text" name="productNo" value="${smtOrderOutgo.productNo}" style="width: 50%"></li>
        <li>订单号：<input type="text" name="orderNo" value="${smtOrderOutgo.orderNo}" style="width: 50%"></li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnReset" class="btn btn-primary" type="reset" onclick="window.location.href='${ctx}/smt/orderoutgo/smtOrderOutgo/outgo_fpc_list'" value="重置"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable"  class="table table-striped table-bordered table-condensed">
    <thead>
    <tr style="text-align: center">
        <th>序号</th>
        <th>出货单号</th>
        <th>客户名称</th>
        <th>产品型号</th>
        <th>订单号</th>
        <th>出货数量</th>
        <th>出货点数</th>
        <th>出货时间</th>
        <shiro:hasPermission name="smt:orderoutgo:smtOrderOutgo:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:if test="${page.list==null || page.list.size()<=0}">
        <tr>
            <td colspan="9" style="text-align: center">对不起，没有数据……</td>
        </tr>
    </c:if>
    <c:forEach items="${page.list}" var="smtOrderOutgo" varStatus="out">
        <tr>
            <td>
                    ${out.count}
            </td>
            <td>
                    ${smtOrderOutgo.outgoOrderNo}
            </td>
            <td>
                    ${smtOrderOutgo.customerName}
            </td>
            <td>
                    ${smtOrderOutgo.productNo}
            </td>
            <td>
                    ${smtOrderOutgo.orderNo}
            </td>
            <td>
                    ${smtOrderOutgo.counts}
            </td>
            <td>
                    ${smtOrderOutgo.pointCounts}
            </td>
            <td>
                <fmt:formatDate value="${smtOrderOutgo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>

            <shiro:hasPermission name="smt:orderoutgo:smtOrderOutgo:edit">
                <td>
                    <a href="${ctx}/smt/orderoutgo/smtOrderOutgo/delete?id=${smtOrderOutgo.id}" onclick="return confirmx('确认要撤销此次出货吗？', this.href)">撤销</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>