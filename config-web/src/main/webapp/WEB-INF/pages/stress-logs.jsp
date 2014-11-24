<%--
  Created by IntelliJ IDEA.
  User: edwardsbean
  Date: 14-11-20
  Time: 上午11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>


<div class="container-fluid">
    <h1>Stress Log Browser</h1>

    <div class="table-responsive">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>任务id</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="logs">
            </tbody>
        </table>
    </div>
    <div id="warn"></div>
</div>
<%@ include file="../common/footer.jsp" %>
<script src="/resources/script.js" type="text/javascript" charset="utf-8"></script>

<script type="text/template" id="tpl_log_table">
    {@each list as it}
    <tr style="cursor: pointer;" onclick="location.href='/#{it.id}/logs/web-config.log'">
        <td>#{it.id}</td>
        <td>#{it.time}</td>
        <td>
            <button type="button" onclick="deleteLog('#{it.id}',event)" class="btn btn-default btn-sm">删除</button>
        </td>
    </tr>
    {@/each}
</script>
<script>
    juicer.set({
        'tag::operationOpen': '{@',
        'tag::operationClose': '}',
        'tag::interpolateOpen': '#{',
        'tag::interpolateClose': '}',
        'tag::noneencodeOpen': '##{',
        'tag::noneencodeClose': '}',
        'tag::commentOpen': '{#',
        'tag::commentClose': '}'
    });

    function deleteLog(id,event) {
        var data = new Object();
        data["id"] = id;
        $.ajax({
            type: "post",
            url: "logs/delete",
            async: false,
            data: data,
            dataType: "json",
            success: function (json) {
                if (json.code == "0") {
                    location.href=location.href;
                } else {
                    alert("服务端异常:" + json.msg);
                }
            }
        });

        event.stopPropagation();
    }

    function addLog() {
        var template = $('#tpl_log_table').html();
        var logList;
        $.ajax({
            type: "get",
            url: "logs",
            async: false,
            dataType: "json",
            success: function (json) {
                if (json.code == "0") {
                    logList = json.returnData;
                    if (logList.length == 0) {
                        $("#warn").html("<div class='alert alert-warning' style='display: block;'><strong>Warning!</strong> 没有发现日志文件</div>");
                    }
                } else {
                    alert("服务端异常:" + json)
                }
            }
        });
        var data =
        {
            list: logList
        };
        var html = juicer(template, data);
        $("#logs").append(html);
    }
    addLog();
</script>

