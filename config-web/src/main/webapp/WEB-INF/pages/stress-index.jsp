<%--
  Created by IntelliJ IDEA.
  User: edwardsbean
  Date: 14-10-27
  Time: 下午4:15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>

<div class="container-fluid">
    <h1>Stress Test Browser</h1>

    <div class="well">
        <form class="form-inline" role="form">
                    <span class="btn-group pull-right">
                        <a class="btn btn-status btn-success" data-value="RUNNING">正在运行</a>
                        <a class="btn btn-status btn-danger disable-feedback" data-value="KILLED">已停止</a>
                    </span>
            <input type="text" id="filterInput" class="form-control" placeholder="搜索用户名、名称等...">
            <a href="/stress/create" class="btn btn-default" role="button">创建</a>
        </form>
    </div>

    <div class="table-responsive">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>服务名</th>
                <th>描述</th>
                <th>所有者</th>
                <th>组名</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="configs">

            <%--<tr style="cursor: pointer;">--%>
                <%--<td>softquery</td>--%>
                <%--<td>对softquery进行压测</td>--%>
                <%--<td>consectetur</td>--%>
                <%--<td>--%>
                    <%--<span class="label label-success">RUNNING</span>--%>
                <%--</td>--%>
                <%--<td>2014</td>--%>
                <%--<td>--%>
                    <%--<button type="button" class="btn btn-default btn-sm">停止</button>--%>
                <%--</td>--%>
            <%--</tr>--%>
            </tbody>
        </table>
    </div>

    <div class="row">
        <div class="col-sm-4" style="padding-top: 25px">
            <div class="dataTables_info" id="jobsTable_info">Showing 1 to 30 of 100 entries</div>
        </div>

        <div class="pull-right" style="padding-right: 20px">
            <ul class="pagination">
                <li><a href="#">&laquo;</a></li>
                <li><a href="#">1</a></li>
                <li><a href="#">2</a></li>
                <li><a href="#">3</a></li>
                <li><a href="#">4</a></li>
                <li><a href="#">5</a></li>
                <li><a href="#">&raquo;</a></li>
            </ul>
        </div>
    </div>
</div>
<%@ include file="../common/footer.jsp" %>
<script src="/resources/script.js" type="text/javascript" charset="utf-8"></script>
<script type="text/template" id="tpl_config_table">
    {@each list as it}
    <tr style="cursor: pointer;">
        <td>#{it.serviceName}</td>
        <td>#{it.description}</td>
        <td>admin</td>
        <td>#{it.groupName}</td>
        <td>2014</td>
        <td>
            <button type="button" onclick="submitTest('#{it.configId}',event)" class="btn btn-default btn-sm">提交</button>
            <button type="button" onclick="deleteTest('#{it.configId}',event)" class="btn btn-default btn-sm">删除</button>
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

    function submitTest(id,event) {
        var data = new Object();
        data["id"] = id;
        $.ajax({
            type: "get",
            url: "/stress/run",
            async: false,
            data: data,
            dataType: "json",
            success: function (json) {
                if (json.code == "0") {
                    alert("提交成功，任务id:" + json.returnData)
                } else {
                    alert("服务端异常:" + json.msg);
                }
            }
        });

        event.stopPropagation();
    }

    function deleteTest(id,event) {
        var data = new Object();
        data["id"] = id;
        $.ajax({
            type: "post",
            url: "/stress/delete",
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
    function addConfigRow() {
        var template = $('#tpl_config_table').html();
        var logList;
        $.ajax({
            type: "get",
            url: "/stress/configs",
            async: true,
            dataType: "json",
            success: function (json) {
                if (json.code == "0") {
                    list = json.returnData;
                    var data =
                    {
                        list: list
                    };
                    var html = juicer(template, data);
                    $("#configs").append(html);
                } else {
                    alert("服务端异常:" + json)
                }
            }
        });
    }
    addConfigRow();
</script>