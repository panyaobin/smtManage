<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>付款申请管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#testPrint").click(function () {
                window.location.href = "${ctx}/smt/paymentapply/smtPaymentApply/testPrint";
            })
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
    <shiro:hasPermission name="smt:paymentapply:smtPaymentApply:edit">
        <li><a href="${ctx}/smt/paymentapply/smtPaymentApply/form">付款申请添加</a></li>
    </shiro:hasPermission>
    <li class="active"><a href="${ctx}/smt/paymentapply/smtPaymentApply/">付款申请列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="smtPaymentApply" action="${ctx}/smt/paymentapply/smtPaymentApply/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>收款单位：</label>
            <select name="paymentId" id="paymentId" class="input-medium">
                <option value="">请选择</option>
                <c:forEach items="${paymentInfoList}" var="pay">
                    <option
                            <c:if test="${pay.id==smtPaymentApply.paymentId}">selected="selected"</c:if> value="${pay.id}" title="${pay.collectionUnit}">${pay.collectionUnit}</option>
                </c:forEach>
            </select>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>单号</th>
        <th>收款单位</th>
        <th>金额</th>
        <th>付款原因</th>
        <th>创建时间</th>
        <th>备注信息</th>
        <shiro:hasPermission name="smt:paymentapply:smtPaymentApply:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:if test="${page.list==null || page.list.size()<=0}">
        <tr>
            <td colspan="8" style="text-align: center">对不起，没有数据……</td>
        </tr>
    </c:if>
    <c:forEach items="${page.list}" var="smtPaymentApply" varStatus="apply">
        <tr>
            <td>
                    ${apply.count}
            </td>
            <td><a href="${ctx}/smt/paymentapply/smtPaymentApply/print?id=${smtPaymentApply.id}">
                    ${1000000+smtPaymentApply.id}
            </a></td>
            <td><a href="${ctx}/smt/paymentapply/smtPaymentApply/form?id=${smtPaymentApply.id}">
                    ${smtPaymentApply.accountName}
            </a></td>
            <td>
                    ${smtPaymentApply.paymentMoney}
            </td>
            <td>
                    ${smtPaymentApply.paymentReason}
            </td>
            <td>
                <fmt:formatDate value="${smtPaymentApply.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                    ${smtPaymentApply.remarks}
            </td>
            <shiro:hasPermission name="smt:paymentapply:smtPaymentApply:edit">
                <td>
                    <a href="${ctx}/smt/paymentapply/smtPaymentApply/form?id=${smtPaymentApply.id}">修改</a>
                    <a href="${ctx}/smt/paymentapply/smtPaymentApply/delete?id=${smtPaymentApply.id}" onclick="return confirmx('确认要删除该付款申请吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>