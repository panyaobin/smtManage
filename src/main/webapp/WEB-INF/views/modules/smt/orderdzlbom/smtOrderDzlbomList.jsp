<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>电子料物料bom管理</title>
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
    <shiro:hasPermission name="smt:orderdzlbom:smtOrderDzlbom:edit">
        <li><a href="${ctx}/smt/orderdzlbom/smtOrderDzlbom/form">物料信息添加</a></li>
    </shiro:hasPermission>
    <li class="active"><a href="${ctx}/smt/orderdzlbom/smtOrderDzlbom/">物料信息表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="smtOrderDzlbom" action="${ctx}/smt/orderdzlbom/smtOrderDzlbom/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li>物料型号：<input type="text" name="bomName" value="${smtOrderDzlbom.bomName}" style="width: 50%"></li>
        <li>物料类型：<input type="text" name="bomType" value="${smtOrderDzlbom.bomType}" style="width: 50%"></li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnReset" class="btn btn-primary" type="reset" onclick="window.location.href='${ctx}/smt/orderdzlbom/smtOrderDzlbom'" value="重置"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>物料型号</th>
        <th>物料类型</th>
        <th>封装尺寸</th>
        <th>功能值</th>
        <th>耐压</th>
        <th>供应商</th>
        <th>备注</th>
        <shiro:hasPermission name="smt:orderdzlbom:smtOrderDzlbom:edit">
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
    <c:forEach items="${page.list}" var="smtOrderDzlbom" varStatus="od">
        <tr>
            <td>
                    ${od.count}
            </td>
            <td>
                    ${smtOrderDzlbom.bomName}
            </td>

            <td>
                    ${smtOrderDzlbom.bomType}
            </td>
            <td>
                    ${smtOrderDzlbom.packageSize}
            </td>
            <td>
                    ${smtOrderDzlbom.funcValue}
            </td>
            <td>
                    ${smtOrderDzlbom.voltage}
            </td>
            <td>
                    ${smtOrderDzlbom.business}
            </td>

            <td>
                    ${smtOrderDzlbom.remarks}
            </td>
            <shiro:hasPermission name="smt:orderdzlbom:smtOrderDzlbom:edit">
                <td>
                    <a href="${ctx}/smt/orderdzlbom/smtOrderDzlbom/form?id=${smtOrderDzlbom.id}">修改</a>
                    <a href="${ctx}/smt/orderdzlbom/smtOrderDzlbom/delete?id=${smtOrderDzlbom.id}" onclick="return confirmx('确认要删除该电子料物料bom吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>