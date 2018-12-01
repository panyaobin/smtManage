<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>新建HTML</title>
    <script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript">
        $(function () {
            tdclick($("rd[name='a']"));
        });
        function save_para_table(){
            var tableinfo = gettableinfo();
            alert(tableinfo);
        }
        //get table infomation
        function gettableinfo(){
            var key = "";
            var value = "";
            var tabledata = "";
            var table = $("#para_table");
            var tbody = table.children();
            var trs = tbody.children();
            for(var i=1;i<trs.length;i++){
                var tds = trs.eq(i).children();
                for(var j=0;j<tds.length;j++){
                    if(j==0){
                        if(tds.eq(j).text()==null||tds.eq(j).text()==""){
                            return null;
                        }
                        key = "key\":\""+tds.eq(j).text();
                    }
                    if(j==1){
                        if(tds.eq(j).text()==null||tds.eq(j).text()==""){
                            return null;
                        }
                        value = "value\":\""+tds.eq(j).text();
                    }
                }
                if(i==trs.length-1){
                    tabledata += "{\""+key+"\",\""+value+"\"}";
                }else{
                    tabledata += "{\""+key+"\",\""+value+"\"},";
                }
            }
            tabledata = "["+tabledata+"]";
            return tabledata;
        }

        function tdclick(tdobject){
            var td=$(tdobject);
            td.attr("onclick", "");
            //1,取出当前td中的文本内容保存起来
            var text=td.text();
            //2,清空td里面的内容
            td.html(""); //也可以用td.empty();
            //3，建立一个文本框，也就是input的元素节点
            var input=$("<input>");
            //4，设置文本框的值是保存起来的文本内容
            input.attr("value",text);
            input.bind("blur",function(){
                var inputnode=$(this);
                var inputtext=inputnode.val();
                var tdNode=inputnode.parent();
                tdNode.html(inputtext);
                tdNode.click(tdclick);
                td.attr("onclick", "tdclick(this)");
            });
            input.keyup(function(event){
                var myEvent =event||window.event;
                var kcode=myEvent.keyCode;
                if(kcode==13){
                    var inputnode=$(this);
                    var inputtext=inputnode.val();
                    var tdNode=inputnode.parent();
                    tdNode.html(inputtext);
                    tdNode.click(tdclick);
                }
            });

            //5，将文本框加入到td中
            td.append(input);
            var t =input.val();
            input.val("").focus().val(t);
//       input.focus();

            //6,清除点击事件
            td.unbind("click");
        }
        var row=0;
        function addtr(){
            if(row<8){
                row++;
                var table = $("#para_table");
                var tr= $("<tr>" +
                    "<td name='a' style='text-align: center;' onclick='tdclick(this)'>"+"</td>" +
                    "<td name='a' style='text-align: center;' onclick='tdclick(this)'>"+"</td>" +
                    "<td name='a' style='text-align: center;' onclick='tdclick(this)'>"+"</td>" +
                    "<td name='a' style='text-align: center;' onclick='tdclick(this)'>"+"</td>" +
                    "<td name='a' style='text-align: center;' onclick='tdclick(this)'>"+"</td>" +
                    "<td align='center' onclick='deletetr(this)'><button type='button' class='btn btn-xs btn-link' >"+"删除"+"</button></td></tr>");
                table.append(tr);
            }else{
                alert("已达到最大数");
            }
        }
        function deletetr(tdobject){
            row--;
            var td=$(tdobject);
            td.parents("tr").remove();
        }


    </script>
    <link href="${ctxStatic}/modules/cms/front/themes/weixin/lyb/bootstrap.min.css" rel="external nofollow" rel="stylesheet" type="text/css">
</head>
<body>
<table class="table table-bordered" id="para_table" >
    <thead>
    <tr>
        <th style="text-align:center" width="200">类型</th>
        <th style="text-align:center" width="200">型号</th>
        <th style="text-align:center" width="200">封装尺寸</th>
        <th style="text-align:center" width="200">数量</th>
        <th style="text-align:center" width="200">备注</th>
        <th style="text-align:center" width="100">操作</th>
        <button type='button' class='btn btn-xs btn-link' onclick='addtr()'>添加</button>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td style="text-align:center; " onclick="tdclick(this)"></td>
        <td style="text-align:center; " onclick="tdclick(this)"></td>
        <td style="text-align:center; " onclick="tdclick(this)"></td>
        <td style="text-align:center; " onclick="tdclick(this)"></td>
        <td style="text-align:center; " onclick="tdclick(this)"></td>
        <td style="text-align:center; " onclick="deletetr(this)"><button type="button" class="btn btn-xs btn-link">删除</button></td>
    </tr>

    </tbody>
</table>

<div id="addtrdiv" style="margin-top:-15px; width: 15%; float: right;">

</div>
</body>
</html>