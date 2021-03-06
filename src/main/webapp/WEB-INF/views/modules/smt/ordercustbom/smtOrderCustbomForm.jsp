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

            var num = 8;
            //添加按钮，新增客户bom电子料条数
            $("#add_order").click(function () {
                num++;
                if (num > 24) {
                    showTip("最后一根稻草，不要再加了！");
                    num = 24;
                } else {
                    $("#main_table").append("<tr><td><input type='text' style='width: 15px' readonly value='" + num + "' tabindex='-1'></td><td><select name='dzlbomList' id='' class='input-medium dzlbomId' onchange='btnChange(this[selectedIndex].value,this);'><option value=''>请选择</option></select></td><td><input type='text' id='' name='' class='input-mini' readonly tabindex='-2'></td><td><input type='text' id='' name='counts' class='' style='width: 30px'></td><td><input type='text' id='' name='piecePosition' placeholder='请输入件位' class='input-large'></td><td><input type='text' id='' name='stockCounts' class='' style='width: 30px'></td><td><input type='text' id='' name='remarks' class='input-medium'></td><td><input type=\"text\" id=\"\" name=\"dzlbomId\" class=\"input-medium\"></td></tr>")
                    initDzlList();
                    
                }
            });

            //删除新增记录条数，保留8条
            $("#del_order").on("click", function () {
                if (num > 8) {
                    $("#main_table tr:last").remove();
                    num--;
                } else {
                    showTip("外星人阻止了你的动作!")
                }
            })

            //新增客户BOM验证是否存在
            $("#productNo").blur(function () {
                validateProduct();
            })
            
            //如果先选择型号，后选择客户，也要验证一次
            $("#customerNo").change(function () {
                var customerNo = $("#customerNo").val();
                var productNo = $("#productNo").val().trim();
                if (customerNo!=""){
                    if (productNo!=""){
                        validateProduct();
                    }
                } 
            })
        });

        //验证型号是否存在
        function validateProduct() {
            var productNo = $("#productNo").val().trim();
            var customerNo = $("#customerNo").val();
            if (productNo != "" && customerNo != "") {
                $.ajax({
                    url: "${ctx}/smt/ordercustbom/smtOrderCustbom/validateProductNo",
                    type: "POST",
                    data: {"productNo": productNo, "customerNo": customerNo},
                    success: function (data) {
                        if (null != data && data != "") {
                            $("#tip").html(data);
                            $("#tip").css({"color":"#ff0000"})
                            $("#productNo").focus();
                        }else{
                            $("#tip").html("");
                        }
                    }
                });
            } else if (customerNo == "") {
                showTip("请先选择客户!")
            }
        }


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
                    $("select[name='dzlbomList']").html("<option value=''>请选择</option>");
                    for (var i = 0; i < data.length; i++) {
                        $("select[name='dzlbomList']").append("<option value='" + data[i].id + "'>" + data[i].bomName + "</option>");
                    }
                }, error: function (msg) {
                    showTip("数据获取异常!")
                }
            });
        }

        //切换电子料型号，动态获取电子料类型
        function btnChange(value, obj) {
            for (var i = 0; i < data.length; i++) {
                if (data[i].id == value) {
                    $(obj).parent().next().children().val(data[i].bomType);
                    $(obj).parent().next().next().next().next().next().next().children().val(value);
                }
            }
        }

    </script>
</head>
`
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/smt/ordercustbom/smtOrderCustbom/form?id=${smtOrderCustbom.id}">客户BOM<shiro:hasPermission name="smt:ordercustbom:smtOrderCustbom:edit">${not empty smtOrderCustbom.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="smt:ordercustbom:smtOrderCustbom:edit">查看</shiro:lacksPermission></a></li>
    <li><a href="${ctx}/smt/ordercustbom/smtOrderCustbom/">客户BOM列表</a></li>
    <li><a href="${ctx}/smt/ordercustbom/smtOrderCustbom/smtOrderCustbomDetail">详情</a></li>
    
</ul>
<br/>
<form:form id="inputForm" modelAttribute="smtOrderCustbom" action="${ctx}/smt/ordercustbom/smtOrderCustbom/save" onkeypress="return event.keyCode != 13;" method="post" class="form-horizontal">
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
            </select>&nbsp;&nbsp;&nbsp;&nbsp;
            型号：<input type="text" id="productNo" name="productNo" class="input-small">&nbsp;&nbsp;
            <lable id="tip" name="tip"></lable>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            总点数：<input type="text" id="pointCounts" name="pointCounts" class="input-medium">
        </div>
    </div>


    <div class="control-group">
        <table id="main_table">
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
                    <select name="dzlbomList" id="" class="input-medium dzlbomId" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td><input type="text" id="" name="" class="input-mini" readonly tabindex="-2"></td>
                <td><input type="text" id="" name="counts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="piecePosition" placeholder="请输入件位" class="input-large"></td>
                <td><input type="text" id="" name="stockCounts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="remarks" class="input-medium"></td>
                <td><input type="hidden" id="" name="dzlbomId" class="input-medium"></td>
            </tr>
            <tr>
                <td><input type="text" style="width: 15px" readonly value="2" tabindex="-1"></td>
                <td>
                    <select name="dzlbomList" id="" class="input-medium dzlbomId" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td><input type="text" id="" name="" class="input-mini" readonly tabindex="-2"></td>
                <td><input type="text" id="" name="counts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="piecePosition" placeholder="请输入件位" class="input-large"></td>
                <td><input type="text" id="" name="stockCounts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="remarks" class="input-medium"></td>
                <td><input type="hidden" id="" name="dzlbomId" class="input-medium"></td>
            </tr>
            <tr>
                <td><input type="text" style="width: 15px" readonly value="3" tabindex="-1"></td>
                <td>
                    <select name="dzlbomList" id="" class="input-medium dzlbomId" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td><input type="text" id="" name="" class="input-mini" readonly tabindex="-2"></td>
                <td><input type="text" id="" name="counts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="piecePosition" placeholder="请输入件位" class="input-large"></td>
                <td><input type="text" id="" name="stockCounts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="remarks" class="input-medium"></td>
                <td><input type="hidden" id="" name="dzlbomId" class="input-medium"></td>
            </tr>
            <tr>
                <td><input type="text" style="width: 15px" readonly value="4" tabindex="-1"></td>
                <td>
                    <select name="dzlbomList" id="" class="input-medium dzlbomId" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td><input type="text" id="" name="" class="input-mini" readonly tabindex="-2"></td>
                <td><input type="text" id="" name="counts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="piecePosition" placeholder="请输入件位" class="input-large"></td>
                <td><input type="text" id="" name="stockCounts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="remarks" class="input-medium"></td>
                <td><input type="hidden" id="" name="dzlbomId" class="input-medium"></td>
            </tr>
            <tr>
                <td><input type="text" style="width: 15px" readonly value="5" tabindex="-1"></td>
                <td>
                    <select name="dzlbomList" id="" class="input-medium dzlbomId" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td><input type="text" id="" name="" class="input-mini" readonly tabindex="-2"></td>
                <td><input type="text" id="" name="counts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="piecePosition" placeholder="请输入件位" class="input-large"></td>
                <td><input type="text" id="" name="stockCounts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="remarks" class="input-medium"></td>
                <td><input type="hidden" id="" name="dzlbomId" class="input-medium"></td>
            </tr>
            <tr>
                <td><input type="text" style="width: 15px" readonly value="6" tabindex="-1"></td>
                <td>
                    <select name="dzlbomList" id="" class="input-medium dzlbomId" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td><input type="text" id="" name="" class="input-mini" readonly tabindex="-2"></td>
                <td><input type="text" id="" name="counts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="piecePosition" placeholder="请输入件位" class="input-large"></td>
                <td><input type="text" id="" name="stockCounts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="remarks" class="input-medium"></td>
                <td><input type="hidden" id="" name="dzlbomId" class="input-medium"></td>
            </tr>
            <tr>
                <td><input type="text" style="width: 15px" readonly value="7" tabindex="-1"></td>
                <td>
                    <select name="dzlbomList" id="" class="input-medium dzlbomId" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td><input type="text" id="" name="" class="input-mini" readonly tabindex="-2"></td>
                <td><input type="text" id="" name="counts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="piecePosition" placeholder="请输入件位" class="input-large"></td>
                <td><input type="text" id="" name="stockCounts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="remarks" class="input-medium"></td>
                <td><input type="hidden" id="" name="dzlbomId" class="input-medium"></td>
            </tr>
            <tr>
                <td><input type="text" style="width: 15px" readonly value="8" tabindex="-1"></td>
                <td>
                    <select name="dzlbomList" id="" class="input-medium dzlbomId" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                    </select>
                </td>
                <td><input type="text" id="" name="" class="input-mini" readonly tabindex="-2"></td>
                <td><input type="text" id="" name="counts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="piecePosition" placeholder="请输入件位" class="input-large"></td>
                <td><input type="text" id="" name="stockCounts" class="" style="width: 30px"></td>
                <td><input type="text" id="" name="remarks" class="input-medium"></td>
                <td><input type="hidden" id="" name="dzlbomId" class="input-medium"></td>
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