<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>付款申请管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			var id = $("#paymentId").val();
			if (id){
				getPaymentInfo(id);
			}

			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});

			//收款单位下拉框切换回显对应的收款信息
			$("#paymentId").change(function () {
				var id = $(this).val();
				if (id){
					getPaymentInfo(id);
				}else{
					$("#accountName").val("");
					$("#accountNumber").val("");
					$("#accountBank").val("");
				}
			})
		});

		//根据收款单位查询对应的收款信息
		function getPaymentInfo(id) {
			$.ajax({
				url:"${ctx}/smt/paymentinfo/smtPaymentInfo/getPaymentInfoById",
				type:"POST",
				data:{
					id:id
				},
				success:function(data){
					if (data){
						$("#accountName").val(data.accountName);
						$("#accountNumber").val(data.accountNumber);
						$("#accountBank").val(data.accountBank);
						$("#paymentMoney").focus();
					}else{
						showTip("获取付款信息一场!");
					}
				}
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/smt/paymentapply/smtPaymentApply/form?id=${smtPaymentApply.id}">付款申请<shiro:hasPermission name="smt:paymentapply:smtPaymentApply:edit">${not empty smtPaymentApply.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="smt:paymentapply:smtPaymentApply:edit">查看</shiro:lacksPermission></a></li>
		<li><a href="${ctx}/smt/paymentapply/smtPaymentApply/">付款申请列表</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="smtPaymentApply" action="${ctx}/smt/paymentapply/smtPaymentApply/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">收款单位：</label>
			<div class="controls">
				<select name="paymentId" id="paymentId" class="input-small required">
					<option value="">请选择</option>
					<c:forEach items="${paymentInfoList}" var="pay">
						<option <c:if test="${pay.id==smtPaymentApply.paymentId}">selected="selected"</c:if> value="${pay.id}" title="${pay.collectionUnit}">${pay.collectionUnit}</option>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>&nbsp;
				户名：<input type="text" id="accountName" class="input-mini" readonly>
			</div>

		</div>
		<div class="control-group">
			<label class="control-label">卡号：</label>
			<div class="controls">
				<input type="text" id="accountNumber" class="input-xlarge" readonly>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">开户行：</label>
			<div class="controls">
				<input type="text" class="input-xlarge" id="accountBank" readonly>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">金额：</label>
			<div class="controls">
				<form:input path="paymentMoney" id="paymentMoney" maxlength="14" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>

		<div class="control-group">
			<label class="control-label">付款原因：</label>
			<div class="controls">
				<form:input path="paymentReason" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="smt:paymentapply:smtPaymentApply:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>