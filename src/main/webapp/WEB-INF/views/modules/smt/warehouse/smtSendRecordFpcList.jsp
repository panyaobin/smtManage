<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>smt主FPC发料</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(function () {
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
        })

        /*失焦验证发料数量*/
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
    <style type="text/css">
        table tr td input {
            text-align: center !important;
        }
    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <shiro:hasPermission name="smt:orderentry:smtOrderEntry:view">
        <li><a href="${ctx}/smt/orderentry/smtOrderEntry/fpc_list">主FPC仓</a></li>
        <li><a href="${ctx}/smt/orderentry/smtOrderEntry/dzl_list">电子料仓</a></li>
        <li><a href="${ctx}/smt/orderentry/smtOrderEntry/total_list">仓库发料</a></li>
        <li class="active"><a href="${ctx}/smt/orderentry/smtOrderEntry/dzl_list">发料</a></li>
        <li><a href="${ctx}/smt/orderonline/smtOrderOnline/send_record">发料记录</a></li>
    </shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="smtOrderOnline" onkeydown="if(event.keyCode==13)return false;"  action="${ctx}/smt/orderonline/smtOrderOnline/print" method="post" class="breadcrumb form-search">
    <table style="text-align: center;">
        <tr style="height: 60px">
            <td colspan="6"><h4>仓库发料</h4></td>
        </tr>
        <tr>
            <td>序号</td>
            <td>客户名称</td>
            <td>产品型号</td>
            <td>订单号</td>
            <td>数量</td>
            <td>备注</td>
            <td>未发料</td>
        </tr>
        <tr>
            <input type="hidden" name="productType" value="${fpcRecord[0].productType}">
            <input type="hidden" name="customerNo" value="${fpcRecord[0].customerNo}">
            <td><input type="text" value="1" style="width: 10px" readonly tabindex="-1"></td>
            <td><input type="text" name="customerName" value="${fpcRecord[0].customerName}" class="input-mini" tabindex="-1" readonly></td>
            <td><input type="text" name="productNo" value="${fpcRecord[0].productNo}" class="input-small" tabindex="-1" readonly></td>
            <td><input type="text" name="orderNo" value="${fpcRecord[0].orderNo}" class="input-small" tabindex="-1" readonly></td>
            <td><input type="text" name="counts" onblur="validCount(this);" id="counts" value="" class="input-mini " required></td>
            <td><input type="text" name="remarks" value="" class="input-small"></td>
            <td><input type="text" value="${fpcRecord[0].counts}" tabindex="-1" readonly class="input-small"></td>
        </tr>

        <tr>
            <input type="hidden" name="productType" value="${fpcRecord[1].productType}">
            <input type="hidden" name="customerNo" value="${fpcRecord[1].customerNo}">
            <td><input type="text" value="2" style="width: 10px" readonly tabindex="-1"></td>
            <td><input type="text" name="customerName" value="${fpcRecord[1].customerName}" class="input-mini" tabindex="-1" readonly></td>
            <td><input type="text" name="productNo" value="${fpcRecord[1].productNo}" class="input-small" tabindex="-1" readonly></td>
            <td><input type="text" name="orderNo" value="${fpcRecord[1].orderNo}" class="input-small" tabindex="-1" readonly></td>
            <td><input type="text" name="counts" value="" class="input-mini"></td>
            <td><input type="text" name="remarks" value="" class="input-small"></td>
            <td><input type="text" value="${fpcRecord[1].counts}" tabindex="-1" readonly class="input-small"></td>
        </tr>

        <tr>
            <input type="hidden" name="productType" value="${fpcRecord[2].productType}">
            <input type="hidden" name="customerNo" value="${fpcRecord[2].customerNo}">
            <td><input type="text" value="3" style="width: 10px" readonly tabindex="-1"></td>
            <td><input type="text" name="customerName" value="${fpcRecord[2].customerName}" class="input-mini" tabindex="-1" readonly></td>
            <td><input type="text" name="productNo" value="${fpcRecord[2].productNo}" class="input-small" tabindex="-1" readonly></td>
            <td><input type="text" name="orderNo" value="${fpcRecord[2].orderNo}" class="input-small" tabindex="-1" readonly></td>
            <td><input type="text" name="counts" value="" class="input-mini"></td>
            <td><input type="text" name="remarks" value="" class="input-small"></td>
            <td><input type="text" value="${fpcRecord[2].counts}" tabindex="-1" readonly class="input-small"></td>
        </tr>

        <tr>
            <input type="hidden" name="productType" value="${fpcRecord[3].productType}">
            <input type="hidden" name="customerNo" value="${fpcRecord[3].customerNo}">
            <td><input type="text" value="4" style="width: 10px" readonly tabindex="-1"></td>
            <td><input type="text" name="customerName" value="${fpcRecord[3].customerName}" class="input-mini" tabindex="-1" readonly></td>
            <td><input type="text" name="productNo" value="${fpcRecord[3].productNo}" class="input-small" tabindex="-1" readonly></td>
            <td><input type="text" name="orderNo" value="${fpcRecord[3].orderNo}" class="input-small" tabindex="-1" readonly></td>
            <td><input type="text" name="counts" value="" class="input-mini"></td>
            <td><input type="text" name="remarks" value="" class="input-small"></td>
            <td><input type="text" value="${fpcRecord[3].counts}" tabindex="-1" readonly class="input-small"></td>
        </tr>

        <tr>
            <input type="hidden" name="productType" value="${fpcRecord[4].productType}">
            <input type="hidden" name="customerNo" value="${fpcRecord[4].customerNo}">
            <td><input type="text" value="5" style="width: 10px" readonly tabindex="-1"></td>
            <td><input type="text" name="customerName" value="${fpcRecord[4].customerName}" class="input-mini" tabindex="-1" readonly></td>
            <td><input type="text" name="productNo" value="${fpcRecord[4].productNo}" class="input-small" tabindex="-1" readonly></td>
            <td><input type="text" name="orderNo" value="${fpcRecord[4].orderNo}" class="input-small" tabindex="-1" readonly></td>
            <td><input type="text" name="counts" value="" class="input-mini"></td>
            <td><input type="text" name="remarks" value="" class="input-small"></td>
            <td><input type="text" value="${fpcRecord[4].counts}" tabindex="-1" readonly class="input-small"></td>
        </tr>

        <tr>
            <input type="hidden" name="productType" value="${fpcRecord[5].productType}">
            <input type="hidden" name="customerNo" value="${fpcRecord[5].customerNo}">
            <td><input type="text" value="6" style="width: 10px" readonly tabindex="-1"></td>
            <td><input type="text" name="customerName" value="${fpcRecord[5].customerName}" class="input-mini" tabindex="-1" readonly></td>
            <td><input type="text" name="productNo" value="${fpcRecord[5].productNo}" class="input-small" tabindex="-1" readonly></td>
            <td><input type="text" name="orderNo" value="${fpcRecord[5].orderNo}" class="input-small" tabindex="-1" readonly></td>
            <td><input type="text" name="counts" value="" class="input-mini"></td>
            <td><input type="text" name="remarks" value="" class="input-small"></td>
            <td><input type="text" value="${fpcRecord[5].counts}" tabindex="-1" readonly class="input-small"></td>
        </tr>

        <tr>
            <input type="hidden" name="productType" value="${fpcRecord[6].productType}">
            <input type="hidden" name="customerNo" value="${fpcRecord[6].customerNo}">
            <td><input type="text" value="7" style="width: 10px" readonly tabindex="-1"></td>
            <td><input type="text" name="customerName" value="${fpcRecord[6].customerName}" class="input-mini" tabindex="-1" readonly></td>
            <td><input type="text" name="productNo" value="${fpcRecord[6].productNo}" class="input-small" tabindex="-1" readonly></td>
            <td><input type="text" name="orderNo" value="${fpcRecord[6].orderNo}" class="input-small" tabindex="-1" readonly></td>
            <td><input type="text" name="counts" value="" class="input-mini"></td>
            <td><input type="text" name="remarks" value="" class="input-small"></td>
            <td><input type="text" value="${fpcRecord[6].counts}" tabindex="-1" readonly class="input-small"></td>
        </tr>

        <tr>
            <input type="hidden" name="productType" value="${fpcRecord[7].productType}">
            <input type="hidden" name="customerNo" value="${fpcRecord[7].customerNo}">
            <td><input type="text" value="8" style="width: 10px" readonly tabindex="-1"></td>
            <td><input type="text" name="customerName" value="${fpcRecord[7].customerName}" class="input-mini" tabindex="-1" readonly></td>
            <td><input type="text" name="productNo" value="${fpcRecord[7].productNo}" class="input-small" tabindex="-1" readonly></td>
            <td><input type="text" name="orderNo" value="${fpcRecord[7].orderNo}" class="input-small" tabindex="-1" readonly></td>
            <td><input type="text" name="counts" value="" class="input-mini"></td>
            <td><input type="text" name="remarks" value="" class="input-small"></td>
            <td><input type="text" value="${fpcRecord[7].counts}" tabindex="-1" readonly class="input-small"></td>
        </tr>

        <tr style="height: 50px;">
            <td colspan="6"></td>
            <td>
                <input id="btnSubmit" style="float: left;" class="btn btn-primary" type="submit" value="打印"/>
                <input id="btnCancel" class="btn" style="float: right" type="button" value="返 回" onclick="history.go(-1)"/>
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>