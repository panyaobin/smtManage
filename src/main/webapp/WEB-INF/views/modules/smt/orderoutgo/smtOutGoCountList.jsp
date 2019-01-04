<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>FPC出货</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#counts").focus();
            /*点击计算电子料消耗量*/
            $("#calc").click(function () {
                var counts= $("#counts").val();

                if (!(/(^[1-9]\d*$)/.test(counts))) {
                    $("#counts").focus();
                    $("#counts").val("");
                    showTip("请输入有效数量!");
                    return;
                }
                
                if (counts.trim()==""){
                    showTip("请输入数量！");
                    $("#counts").focus();
                    return;
                }
                /*这里是此次出货最大数量*/
                var total_counts=$("#totalCounts").val();
                if (parseInt(counts) > parseInt(total_counts)){
                    showTip("超出最大出货量!");
                    $("#counts").focus();
                    $("#counts").val("");
                    return;
                } 
                loadTable();
            })
        });


        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        function queryParams() {
            var custNo = $("#customerNo").val();
            var proNo = $("#productNo").val();
            var counts= $("#counts").val();
            var data = {"productNo": proNo, "customerNo": custNo,"counts":counts};
            return data;
        }

        function rowIndexFormatter(value, row, index) {
            return ++index;
        }

        function loadTable() {
            $("#dzlUseCounts").bootstrapTable('destroy');
            var $table = $('#dzlUseCounts').bootstrapTable({
                url: '${ctx}/smt/orderoutgo/smtOrderOutgo/search',  /// /请求后台的URL（*）
                method: 'post',                      /// /请求方式（*）
                //toolbar: '#wmsRoHeader_table_toolbar_id',     /// /工具按钮用哪个容器
                striped: true,                      /// /是否显示行间隔色
                cache: false,                       /// /是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: false,                   /// /是否显示分页（*）
                sortable: true,                     /// /是否启用排序
                sortOrder: "asc",                   /// /排序方式
                queryParams: queryParams,/// /传递参数（*）
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
                responseHandler:function (res) {
                    return {total:res.length,rows:res};
                },
                columns: [{
                    title: "序号",
                    width: '50px',
                    formatter: rowIndexFormatter,
                    align: 'center',
                    valing: 'middle'
                },{
                        field: "id",
                        title: "电子料BOM_ID",
                        titleTooltip: "电子料BOM_ID",
                        align: 'center',
                        valing: 'middle'
                    },{
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
        
        /*提交表单是获取数据*/
        function checkInfo() {
            var counts=$("#counts").val();
            if (counts.trim()==""){
                showTip("请输入出货数量!")
                return;
            }
            debugger
            var rows = $('#dzlUseCounts').bootstrapTable("getData");

            if (typeof rows[0].bomName=="undefined"){
                showTip("请先计算电子料!")
                return;
            }

            var arr=[];
            for (var i = 0; i <rows.length ; i++) {
                var memo={"id":rows[i].id,"bomName":rows[i].bomName,"bomType":rows[i].bomType,"counts":rows[i].counts,"stockCounts":rows[i].stockCounts};
                arr.push(memo);
            }
            var productType=$("#productType").val();
            var pointCounts=$("#pointCounts").val();
            var customerNo=$("#customerNo").val();
            var customerName=$("#customerName").val();
            var productNo=$("#productNo").val();
            var orderNo=$("#orderNo").val();
            var counts=$("#counts").val();
            <%--$.get("${ctx}/smt/orderoutgo/smtOrderOutgo/outgo_save",{"rows":JSON.stringify(arr),"productType":productType,"pointCounts":pointCounts,"customerNo":customerNo,"productNo":productNo,"orderNo":orderNo,"counts":counts},"application/json");--%>
            $.ajax({
                url:"${ctx}/smt/orderoutgo/smtOrderOutgo/outgo_save",
                type:"GET",
                contentType: "application/json",
                data:{"rows":JSON.stringify(arr),"productType":productType,"pointCounts":pointCounts,"customerNo":customerNo,"customerName":customerName,"productNo":productNo,"orderNo":orderNo,"counts":counts},
                success:function (msg) {
                    if(msg=="success"){
                        showTip("出货成功!");
                        window.location.href="${ctx}/smt/orderoutgo/smtOrderOutgo/print";
                    }else{
                        showTip(msg);
                    }
                }
                
            })
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <shiro:hasPermission name="smt:orderentry:smtOrderEntry:view">
        <li><a href="${ctx}/smt/orderoutgo/smtOrderOutgo/order_entry_fpc_list">在线主FPC待出货</a></li>
    </shiro:hasPermission>

    <shiro:hasPermission name="smt:orderentry:smtOrderEntry:view">
        <li class="active"><a href="">主FPC出货</a></li>
    </shiro:hasPermission>

    <shiro:hasPermission name="smt:orderentry:smtOrderEntry:view">
        <li><a href="${ctx}/smt/orderoutgo/smtOrderOutgo/order_entry_dzl_list">在线电子料待出货</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="smtOrderEntry" action="" onkeydown="if(event.keyCode==13){return false;}"  method="post" class="breadcrumb form-search">
    <input type="hidden" name="productType" id="productType" value="${smtOrderEntry.productType}">
    <input type="hidden" name="pointCounts" id="pointCounts" value="${smtOrderEntry.pointCounts}"> 
    <input type="hidden" name="customerName" id="customerName" value="${smtOrderEntry.customerName}"> 
    <table style="height: 150px">
        <tr>
            <td style="height: 60px;text-align: center" colspan="5"><h4>FPC出货信息</h4></td>
        </tr>
        <tr>
            <td class="input-large">客户名称:<select name="customerNo" id="customerNo" class="input-small">
                <option value="">请选择</option>
                <c:forEach items="${custList}" var="cust">
                    <option
                            <c:if test="${cust.customerNo==smtOrderEntry.customerNo}">selected="selected"</c:if> value="${cust.customerNo}" title="${cust.customerName}">${cust.customerName}</option>
                </c:forEach>
            </select>
            </td>
            <td class="input-large">产品型号：<input type="text" name="productNo" id="productNo" value="${smtOrderEntry.productNo}" class="input-mini"></td>
            <td class="input-large">订单号：<input type="text" name="orderNo" id="orderNo" value="${smtOrderEntry.orderNo}" style="width: 50%"></td>
            <td class="input-large">出货数量:<input type="text" name="counts" id="counts" class="input-mini" required></td>
            <td class="input-medium">总数量:<input type="text" id="totalCounts" value="${smtOrderEntry.counts}" class="input-mini" readonly tabindex="-1"></td>
            <td class="input-small"><input id="calc" class="btn btn-primary input-mini" type="button" value="计算电子料"/></td>
            <td class="input-small"><input class="btn btn-primary input-mini" type="button" onclick="checkInfo();" value="出货"/></td>
        </tr>
    </table>
    <table id="dzlUseCounts" style="text-align: center"></table>
</form:form>


</body>
</html>