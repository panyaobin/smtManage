<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>成品入库表管理</title>
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
    <li><a href="${ctx}/smt/orderonline/smtOrderOnline/fpc_list">主FPC结存</a></li>
    <shiro:hasPermission name="smt:orderonline:smtOrderOnline:view">
        <li><a href="${ctx}/smt/orderonline/smtOrderOnline/dzl_list">电子料结存</a></li>
    </shiro:hasPermission>
    <shiro:hasPermission name="smt:orderonline:smtOrderOnline:view">
        <li><a href="${ctx}/smt/orderonline/smtOrderOnline/total_list">在线结存</a></li>
    </shiro:hasPermission>
    <shiro:hasPermission name="smt:orderonline:smtOrderOnline:view">
        <li class="active"><a href="${ctx}/smt/productentry/smtProductEntry/product_entry_list">成品入库记录</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="smtProductEntry" action="${ctx}/smt/productentry/smtProductEntry/product_entry_list" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li style="width: 230px">客户：<select name="customerNo" id="" class="input-medium">
            <option value="">请选择</option>
            <c:forEach items="${custList}" var="cust">
                <option
                        <c:if test="${cust.customerNo==smtProductEntry.customerNo}">selected="selected"</c:if> value="${cust.customerNo}" title="${cust.customerName}">${cust.customerName}</option>
            </c:forEach>
        </select>
        </li>
        <li>产品型号：<input type="text" name="productNo" value="${smtProductEntry.productNo}" style="width: 50%"></li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnReset" class="btn btn-primary" type="reset" onclick="window.location.href='${ctx}/smt/productentry/smtProductEntry/product_entry_list'" value="重置"/></li>
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
        <th>入库号</th>
        <th>产品类型</th>
        <th>入库数量</th>
        <th>入库时间</th>
        <th>备注</th>
        <shiro:hasPermission name="smt:productentry:smtProductEntry:edit">
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
    <c:forEach items="${page.list}" var="smtProductEntry" varStatus="pe">
        <tr>
            <td>
                    ${pe.count}
            </td>
            <td>
                    ${smtProductEntry.customerName}
            </td>
            <td>
                    ${smtProductEntry.productNo}
            </td>
            <td>
                    ${smtProductEntry.orderNo}
            </td>

            <td>
                    ${fns:getDictLabel(smtProductEntry.productType, "smt_product_type","" )}
            </td>
            <td>
                    ${smtProductEntry.entryCounts}
            </td>
            <td>
                <fmt:formatDate value="${smtProductEntry.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <td>
                    ${smtProductEntry.remarks}
            </td>
            <shiro:hasPermission name="smt:productentry:smtProductEntry:edit">
                <td>
                    <a href="${ctx}/smt/productentry/smtProductEntry/delete?id=${smtProductEntry.id}" onclick="return confirmx('确认要撤销该入库记录吗？', this.href)">撤销</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>