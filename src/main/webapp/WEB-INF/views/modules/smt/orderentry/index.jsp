<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>公司信息</title>
</head>
<body>
<div style="padding-left: 200px;padding-top: 20px;">
    <p style="text-align: left">
        <span><a href="${ctx}/smt/orderentry/smtOrderEntry/form" style="cursor: pointer;text-decoration: none;font-size: 30px;font-weight: bolder">产品入库</a></span>
        <span><img style="" src="../../../../../static/ckeditor/images/jiantou.png"/></span>
        <span><a href="${ctx}/smt/orderentry/smtOrderEntry/total_list" style="cursor: pointer;text-decoration: none;font-size: 30px;font-weight: bolder">仓库发料</a></span>
        <span><img style="" src="../../../../../static/ckeditor/images/jiantou.png"/></span>
        <span><a href="${ctx}/smt/orderonline/smtOrderOnline/total_list" style="cursor: pointer;text-decoration: none;font-size: 30px;font-weight: bolder">生产入库</a></span>
    </p>
    <span style="text-align: left!important;margin-left: 22px"><img style="" src="../../../../../static/ckeditor/images/jiantouxia.png"/></span>
    <div style="display: inline;margin-left: 320px">
        <img style="" src="../../../../../static/ckeditor/images/logo.jpg"/>
        
    </div>
    <p><a href="${ctx}/smt/orderoutgo/smtOrderOutgo/order_entry_fpc_list" style="cursor: pointer;text-decoration: none;font-size: 30px;font-weight: bolder">产品出货</a></p>
</div>

</body>
</html>
