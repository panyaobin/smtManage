<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>smt订单入库表管理</title>
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

        /**
         * 入库列表根据单号显示信息
         */
        function showOrderEntryModal(customerName,orderNo,orderDate) {
            $("#orderEntryModal").modal("show");
            $("#orderEntryModal").css("width", "780px");
            $("#orderEntryModal").css("height", "510px");
            
            //弹窗数据回显
            $("#customerName").val(customerName);
            $("#orderNo").val(orderNo);
            $("#orderDate").val(orderDate);

            var data = {"orderNo": orderNo};
            initBootstrapTable(data);

        }

        //初始化表格
        function initBootstrapTable(data) {
            $("#orderEntryTableModal").bootstrapTable('destroy');
            var $table = $('#orderEntryTableModal').bootstrapTable({
                url: '${ctx}/smt/orderentry/smtOrderEntry/selectOrderEntryModal',  /// /请求后台的URL（*）
                method: 'post',                      /// /请求方式（*）
                striped: true,                      /// /是否显示行间隔色
                cache: false,                       /// /是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: false,                   /// /是否显示分页（*）
                sortable: true,                     /// /是否启用排序
                sortOrder: "asc",                   /// /排序方式
                queryParams: data,/// /传递参数（*）
                sidePagination: "server",           /// /分页方式：client客户端分页，server服务端分页（*）
                pageNumber: 1,                       /// /初始化加载第一页，默认第一页
                pageSize: 10,                       /// /每页的记录行数（*）
                pageList: [10, 25, 50, 100],        /// /可供选择的每页的行数（*）
                search: false,                       /// /是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                strictSearch: false,
                showColumns: false,                  /// /是否显示所有的列
                showExport: false, 						//显示导出按钮
                showRefresh: false,                  /// /是否显示刷新按钮
                clickToSelect: false,                /// /是否启用点击选中行 /// 行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                contentType: "application/x-www-form-urlencoded",
                uniqueId: "id",                     /// /每一行的唯一标识，一般为主键列
                showToggle: false,                    /// /是否显示详细视图和列表视图的切换按钮
                cardView: false,                    /// /是否显示详细视图
                detailView: false,                   /// /是否显示父子表
                resizable: true,      //设置table可以调整列宽
                responseHandler: function (res) {
                    return {total: res.length, rows: res};
                },
                onLoadSuccess:function(){
                    var tableId = document.getElementById("orderEntryTableModal");
                    for(var i = 1;i < tableId.rows.length;i++) {
                        tableId.rows[i].setAttribute("style", "background: red;")
                    }
                },
                columns: [{
                    title: "序号",
                    width: '50px',
                    formatter: rowIndexFormatter,
                    align: 'center',
                    valing: 'middle'
                }, {
                    field: "dzlbomId",
                    title: "电子料ID",
                    titleTooltip: "电子料Id",
                    align: 'center',
                    visible: false,
                    valing: 'middle'
                }, {
                    field: "productNo",
                    title: "电子料型号",
                    titleTooltip: "电子料型号",
                    align: 'center',
                    valing: 'middle'
                }, {
                    field: "productType",
                    title: "电子料类型",
                    titleTooltip: "电子料类型",
                    align: 'center',
                    valing: 'middle',
                    formatter:function (value) {
                        return value == "1"?"FPC":"电子料";
                    }
                }, {
                    field: 'counts',
                    title: "数量",
                    titleTooltip: "数量",
                    align: 'center',
                    valing: 'middle'
                },
                    {
                        field: 'remarks',
                        title: "备注",
                        titleTooltip: "备注",
                        align: 'center',
                        valing: 'middle'
                    }

                ]
            });
        }

        //表格序号
        function rowIndexFormatter(value, row, index) {
            return ++index;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <shiro:hasPermission name="smt:orderentry:smtOrderEntry:edit">
        <li><a href="${ctx}/smt/orderentry/smtOrderEntry/form">订单入库添加</a></li>
    </shiro:hasPermission>
    <li class="active"><a href="${ctx}/smt/orderentry/smtOrderEntry/">订单入库列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="smtOrderEntry" action="${ctx}/smt/orderentry/smtOrderEntry/" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li style="width: 230px">客户：<select name="customerNo" id="" class="input-medium">
            <option value="">请选择</option>
            <c:forEach items="${custList}" var="cust">
                <option
                        <c:if test="${cust.customerNo==smtOrderEntry.customerNo}">selected="selected"</c:if> value="${cust.customerNo}" title="${cust.customerName}">${cust.customerName}</option>
            </c:forEach>
        </select>
        </li>
        <li>产品型号：<input type="text" name="productNo" value="${smtOrderEntry.productNo}" style="width: 50%"></li>
        <li>订单号：<input type="text" name="orderNo" value="${smtOrderEntry.orderNo}" style="width: 50%"></li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnReset" class="btn btn-primary" type="reset" onclick="window.location.href='${ctx}/smt/orderentry/smtOrderEntry'" value="重置"/></li>
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
        <th>订单号</th>
        <th>产品类型</th>
        <th>数量</th>
        <th>入库时间</th>
        <th>备注</th>
        <shiro:hasPermission name="smt:orderentry:smtOrderEntry:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:if test="${page.list==null || page.list.size()<=0}">
        <tr>
            <td colspan="10" style="text-align: center">对不起，没有数据……</td>
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
                <a href="javascript:void(0)" title="查看" onclick='showOrderEntryModal("${smtOrderEntry.customerName}","${smtOrderEntry.orderNo}","<fmt:formatDate value="${smtOrderEntry.orderDate}" pattern="yyyy-MM-dd HH:mm:ss"/>")'>${smtOrderEntry.orderNo}</a>
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
            <shiro:hasPermission name="smt:orderentry:smtOrderEntry:edit">
                <td>
                        <%--<a href="${ctx}/smt/orderentry/smtOrderEntry/form?id=${smtOrderEntry.id}">修改</a>--%>
                    <a href="${ctx}/smt/orderentry/smtOrderEntry/delete?id=${smtOrderEntry.id}" onclick="return confirmx('确认要删除该smt订单入库表吗？', this.href)">删除</a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%--成品入库详情弹窗--%>
<div class="modal fade" id="orderEntryModal" tabindex="-1" role="dialog" aria-labelledby="ProjectContractModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" style="padding:10px;background-color: #45aeea">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" style="text-align: center"><span id="operateTip">客 户 订 单</span></h4>
                <input type="hidden" id="customerNo">
            </div>
            <div class="modal-body center">
                <div class="row" style="margin-bottom: 5px;">
                    <div class="form-group col-xs-12 col-sm-6 col-md-6">
                        <div class="col-xs-8 col-sm-8 col-md-8 respnsive_width">
                            <label class="col-xs-4 col-sm-4 col-md-2 control-label text-right padding0" style="margin-left: 20px;"><span class="require">客户名称</span></label>
                            <input type="text" class="form-control input-small middle" readonly id="customerName" style="margin-bottom: 0px;margin-right: 20px;">
                            <label class="col-xs-4 col-sm-4 col-md-2 control-label text-right padding0" style="margin-left: 40px"><span class="require">订单编号</span></label>
                            <input type="text" class="form-control input-small middle" readonly id="orderNo" style="margin-bottom: 0px;margin-right: 20px;">
                            <label class="col-xs-4 col-sm-4 col-md-2 control-label text-right padding0" style="margin-left:35px"><span class="require">入库时间</span></label>
                            <input type="text" class="form-control input-medium middle" readonly id="orderDate" style="margin-bottom: 0px;float: right">
                        </div>
                    </div>
                </div>
                <div class="row"  style="height: 350px">
                    <table id="orderEntryTableModal"></table>
                </div>
            </div>
            <div style="text-align:center;float: right;margin-right: 15px;margin-bottom: 15px;" id="saveOperateBtn">
                <div class="btn-con" style="display: inline-block;">
                    <button class="btn btn-primary" id="cancelBtn" onclick="$('#orderEntryModal').modal('hide')" data-i18n-key="cancel">关 闭</button>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="pagination">${page}</div>
</body>
</html>