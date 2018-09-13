<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>客户BOM管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            initDzlList();//初始化电子料信息
            //$("#name").focus();

            $("#inputForm").validate({
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });

        });

        var data;
        //加载电子料信息
        function initDzlList() {
            var url = "${ctx}/smt/ordercustbom/smtOrderCustbom/get_dzl_customer";
            $.ajax({
                url: url,
                async: false,
                datatype: "application/json",
                type: "POST",
                success: function (msg) {
                    data = eval(msg);
                    $("select[name='dzlbomId']").html("<option value=''>请选择</option>");
                    for (var i = 0; i < data.length; i++) {
                        $("select[name='dzlbomId']").append("<option value='" + data[i].id + "'>" + data[i].bomName + "</option>");
                    }
                }, error: function (msg) {
                    showTip("数据获取异常!")
                }
            });
        }

        //切换电子料型号，动态获取电子料类型
        function btnChange(value,obj) {
            console.log(value);
            for (var i = 0; i < data.length; i++) {
                if (data[i].id==value){
                    $(obj).parent().next().children().val(data[i].bomType);
                }
            }
        }
        
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/smt/ordercustbom/smtOrderCustbom/form?id=${smtOrderCustbom.id}">客户BOM<shiro:hasPermission name="smt:ordercustbom:smtOrderCustbom:edit">${not empty smtOrderCustbom.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="smt:ordercustbom:smtOrderCustbom:edit">查看</shiro:lacksPermission></a></li>
    <li><a href="${ctx}/smt/ordercustbom/smtOrderCustbom/">客户BOM列表</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="smtOrderCustbom" action="${ctx}/smt/ordercustbom/smtOrderCustbom/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">

        <div class="">
            客户：
            <select name="customerNo" id="customerNo" class="input-large">
                <option value="">请选择</option>
                <c:forEach items="${custList}" var="cust">
                    <option value="${cust.customerNo}" title="${cust.customerName}">${cust.customerName}</option>
                </c:forEach>
            </select>
            型号：<input type="text" id="productNo" name="productNo" class="input-large">
                <%--<select name="productNo" id="productNo" class="input-medium">--%>
                <%--<option value="">请选择</option>--%>
                <%--</select>--%>
            总点数：<input type="text" id="pointCounts" name="pointCounts" class="input-large">
        </div>
    </div>


    <div class="control-group">
        <table>
            <tr>
                <td class="text-center" style="width: 15px">序号</td>
                <th><label class="text-center input-medium">电子料型号</label></th>
                <th><label class="text-center input-mini">电子料类型</label></th>
                <th><label class="text-center " style="width: 30px">用量</label></th>
                <th><label class="text-center input-large">件位</label></th>
                <th><label class="text-center " style="width: 30px">备品</label></th>
                <th><label class="text-center input-medium">备注</label></th>
            </tr>

            <tr>
                <td><input type="text" style="width: 15px" readonly value="1" tabindex="-1"></td>
                <td>
                    <select name="dzlbomId" id="" class="input-medium dzlbomId" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td><input type="text" id="" name="" class="input-mini" readonly tabindex="-2"></td>
                <td><input type="text" id="" name="counts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="piecePosition" placeholder="请输入件位" class="input-large"></td>
                <td><input type="text" id="" name="stockCounts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="remarks" class="input-medium"></td>
            </tr>
            <tr>
                <td><input type="text" style="width: 15px" readonly value="2" tabindex="-1"></td>
                <td>
                    <select name="dzlbomId" id="" class="input-medium dzlbomId" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td><input type="text" id="" name="" class="input-mini" readonly tabindex="-2"></td>
                <td><input type="text" id="" name="counts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="piecePosition" placeholder="请输入件位" class="input-large"></td>
                <td><input type="text" id="" name="stockCounts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="remarks" class="input-medium"></td>
            </tr>
            <tr>
                <td><input type="text" style="width: 15px" readonly value="3" tabindex="-1"></td>
                <td>
                    <select name="dzlbomId" id="" class="input-medium dzlbomId" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td><input type="text" id="" name="" class="input-mini" readonly tabindex="-2"></td>
                <td><input type="text" id="" name="counts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="piecePosition" placeholder="请输入件位" class="input-large"></td>
                <td><input type="text" id="" name="stockCounts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="remarks" class="input-medium"></td>
            </tr>
            <tr>
                <td><input type="text" style="width: 15px" readonly value="4" tabindex="-1"></td>
                <td>
                    <select name="dzlbomId" id="" class="input-medium dzlbomId" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td><input type="text" id="" name="" class="input-mini" readonly tabindex="-2"></td>
                <td><input type="text" id="" name="counts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="piecePosition" placeholder="请输入件位" class="input-large"></td>
                <td><input type="text" id="" name="stockCounts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="remarks" class="input-medium"></td>
            </tr>
            <tr>
                <td><input type="text" style="width: 15px" readonly value="5" tabindex="-1"></td>
                <td>
                    <select name="dzlbomId" id="" class="input-medium dzlbomId" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td><input type="text" id="" name="" class="input-mini" readonly tabindex="-2"></td>
                <td><input type="text" id="" name="counts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="piecePosition" placeholder="请输入件位" class="input-large"></td>
                <td><input type="text" id="" name="stockCounts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="remarks" class="input-medium"></td>
            </tr>
            <tr>
                <td><input type="text" style="width: 15px" readonly value="6" tabindex="-1"></td>
                <td>
                    <select name="dzlbomId" id="" class="input-medium dzlbomId" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td><input type="text" id="" name="" class="input-mini" readonly tabindex="-2"></td>
                <td><input type="text" id="" name="counts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="piecePosition" placeholder="请输入件位" class="input-large"></td>
                <td><input type="text" id="" name="stockCounts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="remarks" class="input-medium"></td>
            </tr>
            <tr>
                <td><input type="text" style="width: 15px" readonly value="7" tabindex="-1"></td>
                <td>
                    <select name="dzlbomId" id="" class="input-medium dzlbomId" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td><input type="text" id="" name="" class="input-mini" readonly tabindex="-2"></td>
                <td><input type="text" id="" name="counts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="piecePosition" placeholder="请输入件位" class="input-large"></td>
                <td><input type="text" id="" name="stockCounts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="remarks" class="input-medium"></td>
            </tr>
            <tr>
                <td><input type="text" style="width: 15px" readonly value="8" tabindex="-1"></td>
                <td>
                    <select name="dzlbomId" id="" class="input-medium dzlbomId" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td><input type="text" id="" name="" class="input-mini" readonly tabindex="-2"></td>
                <td><input type="text" id="" name="counts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="piecePosition" placeholder="请输入件位" class="input-large"></td>
                <td><input type="text" id="" name="stockCounts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="remarks" class="input-medium"></td>
            </tr>

        </table>
    </div>

    <div class="control-group">
        <span class="input-xxlarge" style="margin-right: 552px"><input id="add_order" class="btn" type="button" value="添 加"/>
            <input id="del_order" class="btn" type="button" value="删 除" onclick=""/>
        </span>
        <shiro:hasPermission name="smt:ordercustbom:smtOrderCustbom:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn text-right" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>