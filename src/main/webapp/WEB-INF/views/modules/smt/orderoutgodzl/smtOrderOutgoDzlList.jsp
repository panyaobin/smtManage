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
    <li><a href="${ctx}/smt/orderoutgo/smtOrderOutgo/outgo_fpc_list">FPC出货列表</a></li>
    <li class="active"><a href="${ctx}/smt/orderoutgo/smtOrderOutgo/outgo_dzl_list">电子料出货列表</a></li>
    <li><a href="${ctx}/smt/orderoutgo/smtOrderOutgo/return_goods_lists">退货列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="smtOrderOutgoDzl" action="${ctx}/smt/orderoutgo/smtOrderOutgo/outgo_dzl_list" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li style="width: 230px">客户：<select name="customerNo" id="" class="input-medium">
            <option value="">请选择</option>
            <c:forEach items="${custList}" var="cust">
                <option <c:if test="${cust.customerNo==smtOrderOutgoDzl.customerNo}">selected="selected"</c:if> value="${cust.customerNo}" title="${cust.customerName}">${cust.customerName}</option>
            </c:forEach>
        </select>
        </li>
        <li>物料型号：<input type="text" name="bomName" value="${smtOrderOutgoDzl.bomName}" style="width: 50%"></li>
        <li>产品型号：<input type="text" name="productNo" value="${smtOrderOutgoDzl.productNo}" style="width: 50%"></li>
        <li>出货单号：<input type="text" name="outgoOrderNo" value="${smtOrderOutgoDzl.outgoOrderNo}" style="width: 50%"></li>
        <li>
            <label>出货时间：</label>
            <input name="startDate" type="text"  id="startDate" readonly="readonly" maxlength="20" style="width: 150px;" class=" Wdate "
                   value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
            -
            <input name="endDate" type="text" id="endDate" readonly="readonly" maxlength="20" style="width: 150px;" class=" Wdate "
                   value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd 23:59:59',isShowClear:false});"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnReset" class="btn btn-primary" type="reset" onclick="window.location.href='${ctx}/smt/orderoutgo/smtOrderOutgo/outgo_dzl_list'" value="重置"/></li>
        <li class="btns"><input id="btnDzlExport" class="btn btn-primary" type="button" onclick="window.location.href='${ctx}/smt/orderoutgo/smtOrderOutgo/btnDzlExport?'+$('#searchForm').serialize()+''" value="导出数据"/></li>

        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <td>序号</td>
        <th>客户名称</th>
        <td>物料型号</td>
        <td>物料类型</td>
        <td>用量</td>
        <td>产品型号</td>
        <td>FPC出货</td>
        <td>订单号</td>
        <td>出货单号</td>
        <td>出货数量</td>
        <td>备品数量</td>
        <td>出货时间</td>
    </tr>
    </thead>
    <tbody>
    <c:if test="${page.list==null || page.list.size()<=0}">
        <tr>
            <td colspan="11" style="text-align: center">对不起，没有数据……</td>
        </tr>
    </c:if>
    <c:forEach items="${page.list}" var="smtOrderOutgoDzl" varStatus="dzl">
        <tr>
            <td>
                    ${dzl.count}
            </td>
            <td>
                    ${smtOrderOutgoDzl.customerName}
            </td>
            <td>
                    ${smtOrderOutgoDzl.bomName}
            </td>
            <td>
                    ${smtOrderOutgoDzl.bomType}
            </td>
            <td>
                    ${smtOrderOutgoDzl.custBomCount}
            </td> 
            <td>
                    ${smtOrderOutgoDzl.productNo}
            </td> 
            <td>
                    ${smtOrderOutgoDzl.fpcCounts}
            </td> 
            <td>
                    ${smtOrderOutgoDzl.orderNo}
            </td>
            <td>
                    ${smtOrderOutgoDzl.outgoOrderNo}
            </td>
            <td>
                    ${smtOrderOutgoDzl.counts}
            </td>
            <td>
                    ${smtOrderOutgoDzl.stockCounts}
            </td>
            <td>
                <fmt:formatDate value="${smtOrderOutgoDzl.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>


        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>