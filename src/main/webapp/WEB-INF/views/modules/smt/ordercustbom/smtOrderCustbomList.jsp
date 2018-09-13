<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>客户BOM管理</title>
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
    <shiro:hasPermission name="smt:ordercustbom:smtOrderCustbom:edit">
        <li><a href="${ctx}/smt/ordercustbom/smtOrderCustbom/form">客户BOM添加</a></li>
    </shiro:hasPermission>
    <li class="active"><a href="${ctx}/smt/ordercustbom/smtOrderCustbom/">客户BOM列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="smtOrderCustbom" action="${ctx}/smt/ordercustbom/smtOrderCustbom/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li style="width: 230px">客户：<select name="customerNo" id="" class="input-medium">
            <option value="">请选择</option>
            <c:forEach items="${custList}" var="cust">
                <option <c:if test="${cust.customerNo==smtOrderCustbom.customerNo}">selected="selected"</c:if> value="${cust.customerNo}" title="${cust.customerName}">${cust.customerName}</option>
            </c:forEach>
        </select>
        </li>
        <li>产品型号：<input type="text" name="productNo" value="${smtOrderCustbom.productNo}" style="width: 50%"></li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnReset" class="btn btn-primary" type="reset" onclick="window.location.href='${ctx}/smt/ordercustbom/smtOrderCustbom'" value="重置"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>客户</th>
        <th>产品型号</th>
        <th>总点数</th>
        <th>录入日期</th>
        <shiro:hasPermission name="smt:ordercustbom:smtOrderCustbom:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:if test="${page.list==null || page.list.size()<=0}">
        <tr>
            <td colspan="7" style="text-align: center">对不起，没有数据……</td>
        </tr>
    </c:if>
    <c:forEach items="${page.list}" var="smtOrderCustbom" varStatus="oc">
        <tr>
            <td>
                    ${oc.count}
            </td>
            <td>
                    ${smtOrderCustbom.customerName}
            </td>
            <td>
                    ${smtOrderCustbom.productNo}
            </td>
            <td>
                    ${smtOrderCustbom.pointCounts}
            </td>
            <td>
                <fmt:formatDate value="${smtOrderCustbom.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <shiro:hasPermission name="smt:ordercustbom:smtOrderCustbom:edit">
                <td>
                    <a href="${ctx}/smt/ordercustbom/smtOrderCustbom/view?id=${smtOrderCustbom.id}">详情</a>
                    <a href="${ctx}/smt/ordercustbom/smtOrderCustbom/view?id=${smtOrderCustbom.id}&tag=1">修改</a>
                    <a href="${ctx}/smt/ordercustbom/smtOrderCustbom/delete?id=${smtOrderCustbom.id}" onclick="return confirmx('确认要删除该客户BOM吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>