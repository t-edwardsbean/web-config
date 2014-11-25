<%--
  Created by IntelliJ IDEA.
  User: edwardsbean
  Date: 14-11-20
  Time: 上午11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>


<div class="container-fluid" style="padding-top: 20px;">
    <div class="panel panel panel-info" style="margin-bottom: 0">
        <!-- Default panel contents -->
        <div class="panel-heading">任务浏览器</div>
        <div class="panel-body">
            <p>所有已提交的任务，都可以在这里查看到</p>
            <p>通过日志可以查看压测进程的运行情况</p>
        </div>
    </div>
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
    <tr style="cursor: pointer;" onclick="location.href='http://192.168.253.251/ganglia/?r=hour&cs=&ce=&m=load_one&s=by+name&c=server&h=192.168.253.126&host_regex=&max_graphs=0&tab=m&vn=&hide-hf=false&sh=1&z=small&hc=4'">
        <td>#{it.id}</td>
        <td>#{it.time}</td>
        <td>
            <button type="button" onclick="deleteLog('#{it.id}',event)" class="btn btn-default btn-sm">停止</button>
            <button type="button" onclick="showLog('#{it.id}',event)" class="btn btn-default btn-sm">日志</button>
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

    function showLog(id,event) {
        location.href='/' + id + '/logs/web-config.log';
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

