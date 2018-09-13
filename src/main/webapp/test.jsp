<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/6/11
  Time: 22:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <table id="dbtableDefTb" data-toggle="table" data-toolbar="#toolbar"
           data-search="true"
           data-show-refresh="true"
           data-show-toggle="true"
           data-show-columns="true"
           data-show-export="true"
           data-detail-view="true"
           data-detail-formatter="detailFormatter"
           data-pagination="true"
           data-page-size="50"
           data-page-list="[50, 100, ALL]"
           data-show-footer="false"
           data-side-pagination="server"
           data-url="${pageContext.request.contextPath}/dbtableDef/select/cols.oem">
        <thead>
        <tr>
            <th data-field="" data-checkbox="true"></th>
            <th data-field="" data-formatter="viewIndex">序号</th>
            <th data-field="" data-formatter="viewOptions">操作</th>
            <th data-field="dbColName" data-formatter="editCol">数据表字段名称</th>
            <th data-field="mdColName" data-formatter="editCol">模型表字段名称</th>
            <th data-field="dataType" data-formatter="editColDataType">数据类型</th>
            <th data-field="dataTypeLen" data-formatter="editCol">数据长度</th>
            <th data-field="isNull" data-formatter="editColIsNull">是否可空</th>
            <th data-field="colDescribe" data-formatter="editCol">描述</th>
            <th data-field="orderBy" data-formatter="editCol">排序值</th>
        </tr>
        </thead>
    </table>
</head>
<body>

</body>
</html>
