<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>成品入库表管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#counts").focus();
            // $("#searchForm").validate({
            //     submitHandler: function (form) {
            //         loading('正在提交，请稍等...');
            //         form.submit();
            //     },
            //     errorContainer: "#messageBox",
            //     errorPlacement: function (error, element) {
            //         $("#messageBox").text("输入有误，请先更正。");
            //         if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
            //             error.appendTo(element.parent().parent());
            //         } else {
            //             error.insertAfter(element);
            //         }
            //     }
            // });
        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        /*失焦验证入库数量*/
        function validCount(obj) {
            var counts = $(obj).val(); //输入的数量
            var counts_tol = $(obj).parent().next().next().children().val(); //剩余总数量
            if (parseInt(counts) > parseInt(counts_tol)) {
                showTip("超出未发料数量，请重新输入!");
                $(obj).val("");
                $(obj).focus();
            }
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
        <li class="active"><a href="">入库</a></li>
    </shiro:hasPermission>
    

    <shiro:hasPermission name="smt:orderonline:smtOrderOnline:view">
        <li><a href="${ctx}/smt/productentry/smtProductEntry/product_entry_list">成品入库记录</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="smtProductEntry" action="${ctx}/smt/productentry/smtProductEntry/print" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
<sys:message content="${message}"/>
<table style="text-align: center;">
    <tr style="height: 60px">
        <td colspan="6"><h4>结存入库</h4></td>
    </tr>
    <tr>
        <td>序号</td>
        <td>客户名称</td>
        <td>产品型号</td>
        <td>数量</td>
        <td>备注</td>
        <td>未发料</td>
    </tr>
    <tr style="height: 30px">
        <input type="hidden" name="productType" value="${orderOnlines[0].productType}">
        <input type="hidden" name="customerNo" value="${orderOnlines[0].customerNo}">
        <td><input type="text" value="1" style="width: 10px" readonly tabindex="-1"></td>
        <td><input type="text" name="customerName" value="${orderOnlines[0].customerName}" class="input-mini" tabindex="-1" readonly></td>
        <td><input type="text" name="productNo" value="${orderOnlines[0].productNo}" class="input-small" tabindex="-1" readonly></td>
        <td><input type="text" name="counts" onblur="validCount(this);" id="counts" value="" class="input-mini " required></td>
        <td><input type="text" name="remarks" value="" class="input-small"></td>
        <td><input type="text" value="${orderOnlines[0].counts}" tabindex="-1" readonly class="input-small"></td>
    </tr>

    <tr>
        <input type="hidden" name="productType" value="${orderOnlines[1].productType}">
        <input type="hidden" name="customerNo" value="${orderOnlines[1].customerNo}">
        <td><input type="text" value="2" style="width: 10px" readonly tabindex="-1"></td>
        <td><input type="text" name="customerName" value="${orderOnlines[1].customerName}" class="input-mini" tabindex="-1" readonly></td>
        <td><input type="text" name="productNo" value="${orderOnlines[1].productNo}" class="input-small" tabindex="-1" readonly></td>
        <td><input type="text" name="counts" value="" class="input-mini"></td>
        <td><input type="text" name="remarks" value="" class="input-small"></td>
        <td><input type="text" value="${orderOnlines[1].counts}" tabindex="-1" readonly class="input-small"></td>
    </tr>

    <tr>
        <input type="hidden" name="productType" value="${orderOnlines[2].productType}">
        <input type="hidden" name="customerNo" value="${orderOnlines[2].customerNo}">
        <td><input type="text" value="3" style="width: 10px" readonly tabindex="-1"></td>
        <td><input type="text" name="customerName" value="${orderOnlines[2].customerName}" class="input-mini" tabindex="-1" readonly></td>
        <td><input type="text" name="productNo" value="${orderOnlines[2].productNo}" class="input-small" tabindex="-1" readonly></td>
        <td><input type="text" name="counts" value="" class="input-mini"></td>
        <td><input type="text" name="remarks" value="" class="input-small"></td>
        <td><input type="text" value="${orderOnlines[2].counts}" tabindex="-1" readonly class="input-small"></td>
    </tr>

    <tr>
        <input type="hidden" name="productType" value="${orderOnlines[3].productType}">
        <input type="hidden" name="customerNo" value="${orderOnlines[3].customerNo}">
        <td><input type="text" value="4" style="width: 10px" readonly tabindex="-1"></td>
        <td><input type="text" name="customerName" value="${orderOnlines[3].customerName}" class="input-mini" tabindex="-1" readonly></td>
        <td><input type="text" name="productNo" value="${orderOnlines[3].productNo}" class="input-small" tabindex="-1" readonly></td>
        <td><input type="text" name="counts" value="" class="input-mini"></td>
        <td><input type="text" name="remarks" value="" class="input-small"></td>
        <td><input type="text" value="${orderOnlines[3].counts}" tabindex="-1" readonly class="input-small"></td>
    </tr>

    <tr>
        <input type="hidden" name="productType" value="${orderOnlines[4].productType}">
        <input type="hidden" name="customerNo" value="${orderOnlines[4].customerNo}">
        <td><input type="text" value="5" style="width: 10px" readonly tabindex="-1"></td>
        <td><input type="text" name="customerName" value="${orderOnlines[4].customerName}" class="input-mini" tabindex="-1" readonly></td>
        <td><input type="text" name="productNo" value="${orderOnlines[4].productNo}" class="input-small" tabindex="-1" readonly></td>
        <td><input type="text" name="counts" value="" class="input-mini"></td>
        <td><input type="text" name="remarks" value="" class="input-small"></td>
        <td><input type="text" value="${orderOnlines[4].counts}" tabindex="-1" readonly class="input-small"></td>
    </tr>

    <tr>
        <input type="hidden" name="productType" value="${orderOnlines[5].productType}">
        <input type="hidden" name="customerNo" value="${orderOnlines[5].customerNo}">
        <td><input type="text" value="6" style="width: 10px" readonly tabindex="-1"></td>
        <td><input type="text" name="customerName" value="${orderOnlines[5].customerName}" class="input-mini" tabindex="-1" readonly></td>
        <td><input type="text" name="productNo" value="${orderOnlines[5].productNo}" class="input-small" tabindex="-1" readonly></td>
        <td><input type="text" name="counts" value="" class="input-mini"></td>
        <td><input type="text" name="remarks" value="" class="input-small"></td>
        <td><input type="text" value="${orderOnlines[5].counts}" tabindex="-1" readonly class="input-small"></td>
    </tr>

    <tr>
        <input type="hidden" name="productType" value="${orderOnlines[6].productType}">
        <input type="hidden" name="customerNo" value="${orderOnlines[6].customerNo}">
        <td><input type="text" value="7" style="width: 10px" readonly tabindex="-1"></td>
        <td><input type="text" name="customerName" value="${orderOnlines[6].customerName}" class="input-mini" tabindex="-1" readonly></td>
        <td><input type="text" name="productNo" value="${orderOnlines[6].productNo}" class="input-small" tabindex="-1" readonly></td>
        <td><input type="text" name="counts" value="" class="input-mini"></td>
        <td><input type="text" name="remarks" value="" class="input-small"></td>
        <td><input type="text" value="${orderOnlines[6].counts}" tabindex="-1" readonly class="input-small"></td>
    </tr>

    <tr>
        <input type="hidden" name="productType" value="${orderOnlines[7].productType}">
        <input type="hidden" name="customerNo" value="${orderOnlines[7].customerNo}">
        <td><input type="text" value="8" style="width: 10px" readonly tabindex="-1"></td>
        <td><input type="text" name="customerName" value="${orderOnlines[7].customerName}" class="input-mini" tabindex="-1" readonly></td>
        <td><input type="text" name="productNo" value="${orderOnlines[7].productNo}" class="input-small" tabindex="-1" readonly></td>
        <td><input type="text" name="counts" value="" class="input-mini"></td>
        <td><input type="text" name="remarks" value="" class="input-small"></td>
        <td><input type="text" value="${orderOnlines[7].counts}" tabindex="-1" readonly class="input-small"></td>
    </tr>

    <tr style="height: 50px;">
        <td colspan="5"></td>
        <td>
            <input id="btnSubmit" style="float: left;" class="btn btn-primary" type="submit" value="打印"/>
            <input id="btnCancel" class="btn" style="float: right" type="button" value="返 回" onclick="history.go(-1)"/>
        </td>
    </tr>
</table>
</form:form>
<div class="pagination">${page}</div>
</body>
</html>