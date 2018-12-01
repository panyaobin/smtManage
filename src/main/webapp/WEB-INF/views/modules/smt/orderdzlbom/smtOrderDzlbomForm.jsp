<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>电子料物料bom管理</title>
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

            //输入电子料型号判断是否存在，存在则提示
            $("#bomName").blur(function () {
                var bomName = $(this).val().trim();
                $.ajax({
                    url: "${ctx}/smt/orderdzlbom/smtOrderDzlbom/validateBomName",
                    type: "POST",
                    data: {"bomName": bomName},
                    success: function (data) {
                        if (null != data && data != "") {
                            $("#tip").html(data);
                            $("#bomName").focus();
                        }
                    }
                });
            })
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/smt/orderdzlbom/smtOrderDzlbom/form?id=${smtOrderDzlbom.id}">物料信息<shiro:hasPermission name="smt:orderdzlbom:smtOrderDzlbom:edit">${not empty smtOrderDzlbom.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="smt:orderdzlbom:smtOrderDzlbom:edit">查看</shiro:lacksPermission></a></li>
    <li><a href="${ctx}/smt/orderdzlbom/smtOrderDzlbom/">物料信息表</a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="smtOrderDzlbom" action="${ctx}/smt/orderdzlbom/smtOrderDzlbom/save" onkeypress="return event.keyCode != 13;" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">物料型号：</label>
        <div class="controls">
            <form:input path="bomName" id="bomName" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
            <span id="tip" style="color: red;margin-left: 3px"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">封装尺寸：</label>
        <div class="controls">
            <form:input path="packageSize" htmlEscape="false" maxlength="255" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">物料类型：</label>
        <div class="controls">
            <form:input path="bomType" htmlEscape="false" maxlength="255" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">功能值：</label>
        <div class="controls">
            <form:input path="funcValue" htmlEscape="false" maxlength="255" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">电压：</label>
        <div class="controls">
            <form:input path="voltage" htmlEscape="false" maxlength="255" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">供应商：</label>
        <div class="controls">
            <form:input path="business" htmlEscape="false" maxlength="255" class="input-xlarge "/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注：</label>
        <div class="controls">
            <form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="smt:orderdzlbom:smtOrderDzlbom:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>