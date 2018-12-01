<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>客户BOM管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            initDzlList();
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
                    $("select[name='dzlbomId']").html("");
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
            debugger
            console.log(value);
            for (var i = 0; i < data.length; i++) {
                if (data[i].id==value){
                    $(obj).parent().next().children().val(data[i].bomType);
                    $(obj).parent().prev().children().val(value);
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
<form:form id="inputForm" modelAttribute="smtOrderCustbom" action="${ctx}/smt/ordercustbom/smtOrderCustbom/save" onkeypress="return event.keyCode != 13;" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">

        <div class="">
            客户：
            <select name="customerNo" id="customerNo" class="input-large" >
                <option value="">请选择</option>
                <c:forEach items="${custList}" var="cust">
                    <option value="${cust.customerNo}" title="${cust.customerName}" <c:if test="${ordercustbom.customerNo==cust.customerNo}">selected="selected"</c:if>>${cust.customerName}</option>
                </c:forEach>
            </select>
            型号：<input type="text" id="productNo" name="productNo" value="${ordercustbom.productNo}" class="input-large" >

            总点数：<input type="text" id="pointCounts" value="${ordercustbom.pointCounts}" name="pointCounts" class="input-large" >
        </div>
    </div>


    <div class="control-group">
        <table>
            <tr>
                <td class="text-center" style="width: 35px">序号</td>
                <th><label class="text-center input-medium">电子料型号</label></th>
                <th><label class="text-center input-mini">电子料类型</label></th>
                <th><label class="text-center " style="width: 30px">用量</label></th>
                <th><label class="text-center input-large">件位</label></th>
                <th><label class="text-center " style="width: 30px">备品</label></th>
                <th><label class="text-center input-medium">备注</label></th>
            </tr>

            <c:forEach varStatus="bvo" items="${ordercustbomvo}" var="vo">
                <tr>
                    <td><input type="text" style="width: 15px"  value="${bvo.count}" tabindex="-1"></td>
                    <td style="display:none;"><input type="text" name="dzlbomId" value="${vo.dzlbomId}"></td>
                    <td>
                        <select name="" id="" class="input-medium dzlbomId" onchange="btnChange(this[selectedIndex].value,this);" >
                            <c:forEach items="${dzlboms}" var="bom">
                                <option value="${bom.id}" title="${bom.bomName}" <c:if test="${vo.dzlbomId==bom.id}">selected="selected"</c:if>>${bom.bomName}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <c:forEach items="${dzlboms}" var="bom">
                            <c:if test="${bom.id==vo.dzlbomId}">
                                <input type="text" id="" name="" value="${bom.bomType}" class="input-mini"  tabindex="-2">
                            </c:if>
                        </c:forEach>
                    </td>
                    <td><input type="text" id="" name="counts" value="${vo.counts}" class=""  style="width: 30px"></td>
                    <td><input type="text" id="" name="piecePosition" value="${vo.piecePosition}" placeholder="请输入件位"  class="input-large"></td>
                    <td><input type="text" id="" name="stockCounts" value="${vo.stockCounts}"  class="" style="width: 30px"></td>
                    <td><input type="text" id="" name="remarks" value="${vo.remarks}"  class="input-medium"></td>
                </tr>
            </c:forEach>


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