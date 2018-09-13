<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>客户信息管理管理</title>
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
    <shiro:hasPermission name="smt:syscustomer:smtSysCustomer:edit">
        <li><a href="${ctx}/smt/syscustomer/smtSysCustomer/form">客户信息添加</a></li>
    </shiro:hasPermission>
    <li class="active"><a href="${ctx}/smt/syscustomer/smtSysCustomer/">客户信息列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="smtSysCustomer" action="${ctx}/smt/syscustomer/smtSysCustomer/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li>客户代码：<input type="text" name="customerNo" value="${smtSysCustomer.customerNo}" style="width: 50%"></li>
        <li>客户名称：<input type="text" name="customerName" value="${smtSysCustomer.customerName}" style="width: 50%"></li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnReset" class="btn btn-primary" type="reset" onclick="window.location.href='${ctx}/smt/syscustomer/smtSysCustomer'" value="重置"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>客户代码</th>
        <th>客户名称</th>
        <th>结算方式</th>
        <th>备注</th>
        <shiro:hasPermission name="smt:syscustomer:smtSysCustomer:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="smtSysCustomer" varStatus="sc">
        <tr>
            <td>
                ${sc.count}
            </td>
            <td>
                    ${smtSysCustomer.customerNo}
            </td>
            <td>
                    ${smtSysCustomer.customerName}
            </td>
            <td>
                    ${smtSysCustomer.balanceType}
            </td>
            <td>
                    ${smtSysCustomer.remarks}
            </td>
            <shiro:hasPermission name="smt:syscustomer:smtSysCustomer:edit">
                <td>
                    <a href="${ctx}/smt/syscustomer/smtSysCustomer/form?id=${smtSysCustomer.id}">修改</a>
                    <a href="${ctx}/smt/syscustomer/smtSysCustomer/delete?id=${smtSysCustomer.id}" onclick="return confirmx('确认要删除该客户信息管理吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>