<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>smt订单入库表管理</title>
    <meta name="decorator" content="default"/>

    <script type="text/javascript">
        $(document).ready(function () {
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



            //添加多条数据
            var num = 8; //初始值为8
            $("#add_order").click(function () {
                num++;
                if (num > 15) {
                    showTip("最后一根稻草，不要再加了！");
                    num = 15;
                } else {
                    var html = "<td><input type='text' style='width: 15px' readonly value='" + num + "' tabindex='-1'></td><td><select name=\"productType\" tabindex=\"-1\" class='input-medium' onchange='btnChange(this[selectedIndex].value,this);'><option value=\"\">请选择</option><option value=\"1\">FPC</option><option value=\"2\">电子料</option></select></td><td><select name='productNo' id='' class='input-medium'><option value=''>请选择</option></select></td><td><input type='text' name='counts' id='' class='input-mini ' required></td><td><input type='text' name='remarks' id='' class='input-large'></td>"
                    $("#order_first").parent().append("<tr>" + html + "</tr>");
                }
            }); 
            //删除数据记录条数
            $("#del_order").on("click", function () {
                if (num > 8) {
                    $("#dataTable tr:last").remove();
                    num--;
                } else {
                    showTip("外星人阻止了你的动作!")
                }
            })

            //新增默认显示系统当前时间
            var d = new Date();
            var str = d.getFullYear() + "-" + ("0" + (d.getMonth() + 1)).slice(-2) + "-" + ("0" + (d.getDate())).slice(-2)+ " " +d.getHours()+ ":" +d.getMinutes()+ ":" +d.getSeconds();
            $("#orderDate").val(str);

        });

        var data;
        //初始化电子料列表，避免重复请求服务器
        function initDzlList() {
            var url = "${ctx}/smt/ordercustbom/smtOrderCustbom/get_dzl_customer";
            $.ajax({
                url: url,
                async: false,
                datatype: "application/json",
                type: "POST",
                success: function (msg) {
                    data = eval(msg);
                }, error: function (msg) {
                    showTip("数据获取异常!")
                }
            });
        }


        function showCustomerInfoDialog() {
            top.$.jBox.open("iframe:${ctx}/tag/treeselect?url=" + encodeURIComponent("${url}") + "&module=${module}&checked=${checked}&extId=${extId}&isAll=${isAll}", "选择${title}", 520, 400, {});
            /*layer.open({

                type: 1,
                skin: 'layui-layer-rim', //加上边框
                area: ['1000px', '700px'], //宽高
                title: '帖子详情',
                content: "w我",

            });*/
        }


        //动态加载产品型号
        function btnChange(value, obj) {
            initDzlList();//页面加载初始化电子料信息
            var customerNo = $("select[name='customerNo']").val();
            if (customerNo == '') {
                showTip("请先选择客户！")
                return;
            }
            //选择客户之后加载 对应的产婆型号

            if (value != '' && value == 0) {
                //没有选择
               $(obj).parent().next().children("select[name='productNo']").html("<option value='' selected>请选择</option>");  //如果没有选择,需要先置空产品型号
            }
            if (value != '' && value == 1) {
                //加载客户FPCbom列表
                $(obj).parent().next().children("select[name='productNo']").html("<option value='' selected>请选择</option>");  //如果选择FPC,则需要先置空产品型号

                var url = "${ctx}/smt/orderentry/smtOrderEntry/get_fpc_customer";
                $.ajax({
                    url: url,
                    async: false,
                    data:{"customerNo":customerNo},
                    datatype: "application/json",
                    type: "POST",
                    success: function (msg) {
                       var tip = eval(msg);
                        for (var i = 0; i < tip.length; i++) {
                            $(obj).parent().next().children("select[name='productNo']").append("<option value='" + tip[i].productNo + "'>" + tip[i].productNo + "</option>");
                        }
                    }, error: function (msg) {
                        showTip("数据获取异常!")
                    }
                });

            }
            if (value != '' && value == 2) {
                //加载电子料bom列表
                $(obj).parent().next().children("select[name='productNo']").html("<option value='' selected>请选择</option>");
                for (var i = 0; i < data.length; i++) {
                    $(obj).parent().next().children("select[name='productNo']").append("<option value='" + data[i].bomName + "'>" + data[i].bomName + "</option>");
                }
            }
        }



    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/smt/orderentry/smtOrderEntry/form?id=${smtOrderEntry.id}">订单入库<shiro:hasPermission name="smt:orderentry:smtOrderEntry:edit">${not empty smtOrderEntry.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="smt:orderentry:smtOrderEntry:edit">查看</shiro:lacksPermission></a></li>
    <li><a href="${ctx}/smt/orderentry/smtOrderEntry/">订单入库列表</a></li>
    <%--<li><a href="${ctx}/smt/orderentry/smtOrderEntry/test">测试PDF</a></li>--%>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="smtOrderEntry" action="${ctx}/smt/orderentry/smtOrderEntry/save" onkeypress="return event.keyCode != 13;" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>

    <div class="control-group" style="text-align: center;width: 50%"><h3>产品入库</h3></div>

    <div class="control-group" style="margin-left: 20px">   
        <table>
            <tr>
                <td class="input-large">
                    <label class="" style="">客户：</label>
                    <select name="customerNo" id="" class="input-medium">
                        <option value="">请选择</option>
                        <c:forEach items="${custList}" var="cust">
                            <option value="${cust.customerNo}" title="${cust.customerName}">${cust.customerName}</option>
                        </c:forEach>
                    </select>
                </td>
                <td class="" style="width: 223px">
                    <label class="">订单编号：</label>
                    <form:input path="orderNo" htmlEscape="false" class="input-small"/>
                </td>
                <td>
                    <label>入库时间：</label>
                    <input name="orderDate" type="text" id="orderDate" readonly="readonly" maxlength="20" style="width: 150px;" class=" Wdate "
                           value="<fmt:formatDate value="${smtOrderEntry.orderDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                           onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
                </td>
            </tr>
        </table>
    </div>


    <div class="control-group">

        <table id="dataTable">

            <tr id="order_first">
                <td class="text-center" style="width: 15px">序号</td>
                <td class="text-center input-medium">类型</td>
                <td class="text-center input-medium">型号</td>
                <%--<td class="text-center input-mini">封装尺寸</td>--%>
                <td class="text-center input-mini">数量</td>
                <td class="text-center input-mini">备注</td>
            </tr>

            <tr>
                <td><input type="text" style="width: 15px" readonly value="1" tabindex="-1"></td>
                <td>
                    <select name="productType" id="" class="input-medium" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="0">请选择</option>
                        <option value="1">FPC</option>
                        <option value="2">电子料</option>
                    </select>
                </td>
                <td>
                    <select name="productNo" id="" class="input-medium">
                        <option value="">请选择</option>
                    </select>
                </td>
                <%--<td><input type="text" name="packageSize" id="" class="input-mini"></td>--%>
                <td><input type="text" name="counts" id="" class="input-mini " required></td>
                <td><input type="text" name="remarks" id="" class="input-large"></td>
            </tr>

            <tr>
                <td><input type="text" style="width: 15px" readonly value="2" tabindex="-1"></td>
                <td>
                    <select name="productType" id="" class="input-medium" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                        <option value="1">FPC</option>
                        <option value="2">电子料</option>
                    </select>
                </td>
                <td><select name="productNo" id="" class="input-medium">
                    <option value="">请选择</option>
                </select></td>
                <%--<td><input type="text" name="packageSize" id="" class="input-mini"></td>--%>
                <td><input type="text" name="counts" id="" class="input-mini " required></td>
                <td><input type="text" name="remarks" id="" class="input-large"></td>
            </tr>

            <tr>
                <td><input type="text" style="width: 15px" readonly value="3" tabindex="-1"></td>
                <td>
                    <select name="productType" id="" class="input-medium" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                        <option value="1">FPC</option>
                        <option value="2">电子料</option>
                    </select>
                </td>
                <td><select name="productNo" id="" class="input-medium">
                    <option value="">请选择</option>
                </select></td>
                <%--<td><input type="text" name="packageSize" id="" class="input-mini"></td>--%>
                <td><input type="text" name="counts" id="" class="input-mini " required></td>
                <td><input type="text" name="remarks" id="" class="input-large"></td>
            </tr>

            <tr>
                <td><input type="text" style="width: 15px" readonly value="4" tabindex="-1"></td>
                <td>
                    <select name="productType" id="" class="input-medium" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                        <option value="1">FPC</option>
                        <option value="2">电子料</option>
                    </select>
                </td>
                <td><select name="productNo" id="" class="input-medium">
                    <option value="">请选择</option>
                </select></td>
                <%--<td><input type="text" name="packageSize" id="" class="input-mini"></td>--%>
                <td><input type="text" name="counts" id="" class="input-mini " required></td>
                <td><input type="text" name="remarks" id="" class="input-large"></td>
            </tr>

            <tr>
                <td><input type="text" style="width: 15px" readonly value="5" tabindex="-1"></td>
                <td>
                    <select name="productType" id="" class="input-medium" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                        <option value="1">FPC</option>
                        <option value="2">电子料</option>
                    </select>
                </td>
                <td><select name="productNo" id="" class="input-medium">
                    <option value="">请选择</option>
                </select></td>
                <%--<td><input type="text" name="packageSize" id="" class="input-mini"></td>--%>
                <td><input type="text" name="counts" id="" class="input-mini " required></td>
                <td><input type="text" name="remarks" id="" class="input-large"></td>
            </tr>

            <tr>
                <td><input type="text" style="width: 15px" readonly value="6" tabindex="-1"></td>
                <td>
                    <select name="productType" id="" class="input-medium" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                        <option value="1">FPC</option>
                        <option value="2">电子料</option>
                    </select>
                </td>
                <td><select name="productNo" id="" class="input-medium">
                    <option value="">请选择</option>
                </select></td>
                    <%--<td><input type="text" name="packageSize" id="" class="input-mini"></td>--%>
                <td><input type="text" name="counts" id="" class="input-mini " required></td>
                <td><input type="text" name="remarks" id="" class="input-large"></td>
            </tr>

            <tr>
                <td><input type="text" style="width: 15px" readonly value="7" tabindex="-1"></td>
                <td>
                    <select name="productType" id="" class="input-medium" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                        <option value="1">FPC</option>
                        <option value="2">电子料</option>
                    </select>
                </td>
                <td><select name="productNo" id="" class="input-medium">
                    <option value="">请选择</option>
                </select></td>
                    <%--<td><input type="text" name="packageSize" id="" class="input-mini"></td>--%>
                <td><input type="text" name="counts" id="" class="input-mini " required></td>
                <td><input type="text" name="remarks" id="" class="input-large"></td>
            </tr>

            <tr>
                <td><input type="text" style="width: 15px" readonly value="8" tabindex="-1"></td>
                <td>
                    <select name="productType" id="" class="input-medium" onchange="btnChange(this[selectedIndex].value,this);">
                        <option value="">请选择</option>
                        <option value="1">FPC</option>
                        <option value="2">电子料</option>
                    </select>
                </td>
                <td><select name="productNo" id="" class="input-medium">
                    <option value="">请选择</option>
                </select></td>
                    <%--<td><input type="text" name="packageSize" id="" class="input-mini"></td>--%>
                <td><input type="text" name="counts" id="" class="input-mini " required></td>
                <td><input type="text" name="remarks" id="" class="input-large"></td>
            </tr>


        </table>
    </div>

    <div class="control-group" style="width: 41%">
        <div>
            <input id="add_order" class="btn" type="button" value="添 加"/>
            <input id="del_order" class="btn" type="button" value="删 除" onclick=""/>

            <span style="float: right">
                    <shiro:hasPermission name="smt:orderentry:smtOrderEntry:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
                <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
                </span>
        </div>


    </div>

</form:form>
</body>
</html>