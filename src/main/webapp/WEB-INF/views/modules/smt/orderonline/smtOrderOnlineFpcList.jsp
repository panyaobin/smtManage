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

        /**
         * 成品入库弹窗
         */
        function showModal(customerNo, customerName, productNo, counts) {
            $("#productEntryModal").modal("show");
            $("#customerDzlBomTable").bootstrapTable('destroy');
            $("#counts").val("");
            $("#saveBtn").css("display", "none");
            $("#cancelBtn").css("display", "none");
            $("#productEntryModal").css("width", "1000px");
            $("#productEntryModal").css("height", "500px");
            $("#productEntryModal").css("text-align", "center");
            //弹窗回显数据
            $("#customerNo").val(customerNo);
            $("#customerName").val(customerName);
            $("#productNo").val(productNo);
            $("#hasCounts").val(counts);
            $("#counts").focus();
        }

        //取消关闭modal弹窗
        function act_to_cancel() {
            $("#productEntryModal").modal("hide");
        }

        /**
         * 弹窗计算
         */
        function productEntryCalc() {
            var customerNo = $("#customerNo").val();
            var productNo = $("#productNo").val();
            var hasCounts = $("#hasCounts").val();
            var counts = $("#counts").val();
            if (counts == "") {
                showTip("请输入入库数量!");
                $("#counts").focus();
                return;
            }
            if (parseInt(counts) > parseInt(hasCounts)) {
                showTip("入库数量超出结存!");
                $("#counts").focus();
                return;
            }
            //通过 客户编号，产品型号查询客户bom信息，入库数量计算 用料
            var data = {"customerNo": customerNo, "productNo": productNo, "counts": counts};
            initBootstrapTable(data);
            $("#saveBtn").css("display", "block");
            $("#cancelBtn").css("display", "block");
        }

        function initBootstrapTable(data) {
            $("#customerDzlBomTable").bootstrapTable('destroy');
            var $table = $('#customerDzlBomTable').bootstrapTable({
                url: '${ctx}/smt/productentry/smtProductEntry/selectCustomerBomUsage',  /// /请求后台的URL（*）
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
                    field: "bomName",
                    title: "电子料型号",
                    titleTooltip: "电子料型号",
                    align: 'center',
                    valing: 'middle'
                }, {
                    field: "bomType",
                    title: "电子料类型",
                    titleTooltip: "电子料类型",
                    align: 'center',
                    valing: 'middle'
                }, {
                    field: "piecePosition",
                    title: "件位",
                    titleTooltip: "件位",
                    align: 'center',
                    valing: 'middle'
                },
                    {
                        field: 'counts',
                        title: "总用量",
                        titleTooltip: "总用量",
                        align: 'center',
                        valing: 'middle'
                    },
                    {
                        field: 'stockCounts',
                        title: "备品数量",
                        titleTooltip: "备品数量",
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

        /**
         * 弹窗表格信息保存，成品入库
         */
        function act_to_save() {
            var customerNo = $("#customerNo").val();
            var customerName = $("#customerName").val();
            var productNo = $("#productNo").val();
            var counts = $("#counts").val();
            var custBomTableData = $("#customerDzlBomTable").bootstrapTable("getData");
            // var data = [];
            // $.each(custBomTableData, function (k, v) {
            //     data.push({'bomName': v.bomName, 'counts': v.counts, 'stockCounts': v.stockCounts, 'remarks': v.remarks})
            // });
            $.ajax({
                url: "${ctx}/smt/productentry/smtProductEntry/saveProductEntryModalData",
                type: "POST",
                data: {
                    customerNo: customerNo,
                    customerName:customerName,
                    productNo: productNo,
                    counts: counts,
                    custBomTableData: JSON.stringify(custBomTableData)
                },
                success: function (msg) {
                    //act_to_cancel();
                    if (msg == "success") {
                        window.location.href = "${ctx}/smt/productentry/smtProductEntry/printModal";
                        window.event.returnValue=false;
                    }else{
                        showTip("操作失败，不执行打印!")
                    }
                    <%--window.location.href = "${ctx}/smt/orderonline/smtOrderOnline/fpc_list";--%>
                }
            })
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/smt/orderonline/smtOrderOnline/fpc_list">主FPC结存</a></li>
    <shiro:hasPermission name="smt:orderonline:smtOrderOnline:view">
        <li><a href="${ctx}/smt/orderonline/smtOrderOnline/dzl_list">电子料结存</a></li>
    </shiro:hasPermission>
    <shiro:hasPermission name="smt:orderonline:smtOrderOnline:view">
        <li><a href="${ctx}/smt/orderonline/smtOrderOnline/total_list">在线结存</a></li>
    </shiro:hasPermission>
    <shiro:hasPermission name="smt:orderonline:smtOrderOnline:view">
        <li><a href="${ctx}/smt/productentry/smtProductEntry/product_entry_list?storageType=1">成品入库记录</a></li>
    </shiro:hasPermission>
    <shiro:hasPermission name="smt:orderonline:smtOrderOnline:view">
        <li><a href="${ctx}/smt/productentry/smtProductEntry/product_entry_list_half?storageType=2">半成品入库记录</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="smtOrderOnline" action="${ctx}/smt/orderonline/smtOrderOnline/fpc_list" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <input id="productType" name="productType" type="hidden" value="${smtOrderOnline.productType}"/>
    <ul class="ul-form">
        <li style="width: 230px">客户：<select name="customerNo" id="" class="input-medium">
            <option value="">请选择</option>
            <c:forEach items="${custList}" var="cust">
                <option
                        <c:if test="${cust.customerNo==smtOrderOnline.customerNo}">selected="selected"</c:if> value="${cust.customerNo}" title="${cust.customerName}">${cust.customerName}</option>
            </c:forEach>
        </select>
        </li>
        <li>产品型号：<input type="text" name="productNo" value="${smtOrderOnline.productNo}" style="width: 50%"></li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="btns"><input id="btnReset" class="btn btn-primary" type="reset" onclick="window.location.href='${ctx}/smt/orderonline/smtOrderOnline/fpc_list'" value="重置"/></li>
            <%--<li class="btns"><input id="btnSend" class="btn btn-primary" type="button" value="成品入库"/></li>--%>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <%--<th style="width:3em;text-align: center"><input type="checkbox" id="ids_"></th>--%>
        <th>序号</th>
        <th>客户名称</th>
        <th>产品型号</th>
        <th>产品类型</th>
        <th>数量</th>
        <th>入库时间</th>
        <th>备注</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:if test="${page.list==null || page.list.size()<=0}">
        <tr>
            <td colspan="9" style="text-align: center">对不起，没有数据……</td>
        </tr>
    </c:if>
    <c:forEach items="${page.list}" var="smtOrderOnline" varStatus="oo">
        <tr>
                <%--<td style="text-align: center">--%>
                <%--<input type="checkbox" id="id_${smtOrderOnline.id}" value="${smtOrderOnline.id}"/>--%>
                <%--</td>--%>
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
                    ${fns:getDictLabel(smtOrderOnline.productType , 'smt_product_type', '')}
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
            <td>
                <a href="javascript:void(0)" onclick='showModal("${smtOrderOnline.customerNo}","${smtOrderOnline.customerName}","${smtOrderOnline.productNo}","${smtOrderOnline.counts}")'>成品入库</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>

<%--成品出货弹窗--%>
<div class="modal fade" id="productEntryModal" tabindex="-1" role="dialog" aria-labelledby="ProjectContractModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title"><span id="operateTip"></span></h4>
                <input type="hidden" id="customerNo">
            </div>
            <div class="modal-body center">
                <div class="row" style="margin-bottom: 10px;">
                    <div class="form-group col-xs-12 col-sm-6 col-md-6">
                        <div class="col-xs-8 col-sm-8 col-md-8 respnsive_width">
                            <label class="col-xs-4 col-sm-4 col-md-2 control-label text-right padding0"><span class="require">客户名称</span></label>
                            <input type="text" class="form-control input-small middle" readonly id="customerName" style="margin-bottom: 0px;margin-right: 20px;">
                            <label class="col-xs-4 col-sm-4 col-md-4 control-label text-right padding0"><span class="require">产品型号</span></label>
                            <input type="text" class="form-control input-small" readonly id="productNo" style="margin-bottom: 0px;margin-right: 20px;">
                            <label class="col-xs-4 col-sm-4 col-md-4 control-label text-right padding0"><span class="require">入库数量</span></label>
                            <input type="text" class="form-control input-small" id="counts" style="margin-bottom: 0px;margin-right: 20px;">
                            <label class="col-xs-4 col-sm-4 col-md-4 control-label text-right padding0"><span class="require">在线结存</span></label>
                            <input type="text" class="form-control input-mini" id="hasCounts" style="margin-bottom: 0px;margin-right: 20px;" readonly>
                            <button class="btn btn-primary" style="float: right" onclick="productEntryCalc()">计算</button>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <table id="customerDzlBomTable"></table>
                </div>
            </div>
            <div style="text-align:center;float: right;margin-right: 45px;margin-bottom: 10px;" id="saveOperateBtn">
                <!--保存 提交按钮-->
                <div class="btn-con" style="display: inline-block;margin-right: 5px;">
                    <button class="btn btn-primary" style="display: none" id="saveBtn" onclick="act_to_save()" data-i18n-key="Save">保存</button>
                </div>
                <div class="btn-con" style="display: inline-block">
                    <button class="btn btn-primary" style="display:none;" id="cancelBtn" onclick="act_to_cancel()" data-i18n-key="cancel">取消</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>