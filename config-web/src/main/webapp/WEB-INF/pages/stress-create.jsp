<%--
  Created by IntelliJ IDEA.
  User: edwardsbean
  Date: 14-10-27
  Time: 下午4:15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>
<style>
    .form-actions {
        padding: 19px 190px 20px;
        margin-top: 20px;
        margin-bottom: 20px;
        background-color: #f5f5f5;
        border-top: 1px solid #e5e5e5;
    }

    .steps {
        min-height: 350px;
        margin-top: 10px;
    }
</style>
<script>

    var currentMethod = 0;

    $(document).ready(function () {
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

        $("#nextBtn").click(function () {
            $("#step1").hide();
            $("#step2").show();
            $("#backBtn").removeClass("disabled");
            $("#saveBtn").show();
            $("#nextBtn").addClass("disabled");

        });

        $("#backBtn").click(function () {
            $("#step2").hide();
            $("#step1").show();
            $("#backBtn").addClass("disabled");
            $("#saveBtn").hide();
            $("#nextBtn").removeClass("disabled");
        });

        $("#addBtn").click(function () {
            addMethod();
        });

    });

    function addMethod() {
        if (currentMethod == 0) {
            currentMethod = 1;
        } else {
            currentMethod ++;
        }
        var template = $('#tpl_method').html();
        var data =
        {
            methodID: currentMethod
        };
        var html = juicer(template,data);
        $("#methods").append(html);
    }

    function deleteMethod(id) {
        $("#" + id).remove();
        $("#" + id + "Param").remove();
    }

    function paramSet(id) {
        var template = $("#tpl_param").html();
        var data =
        {
            methodID: id
        };
        var modal = juicer(template,data);
        $("#params").append(modal);
        var paramID = "#" + id + "Param";
        $(paramID).modal({
            backdrop:'static',
            show:true
        });
    }
</script>

<%--服务查询下载窗口--%>
<div class="modal fade" id="query" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title">查询并下载接口</h4>
            </div>
            <div class="modal-body">
                <div class="progress">
                    <div class="active progress-bar progress-bar-success progress-bar-striped" role="progressbar"
                         aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                        <span class="sr-only">40% Complete (success)</span>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>




<div class="container-fluid">
    <h1>Stress Test Creator</h1>


    <form class="form-horizontal" id="itemForm" method="POST" style="margin-top: 0" action="/monitor-create">
        <div id="steps" class="steps">
            <%--参数设置--%>
            <div id="params">
            </div>

            <div id="step1">
                <div class="alert alert-info">
                    <h3>压测配置</h3>
                    请尽量完整填写下面参数，配置测试的频率，以及用于生成向ganglia汇报的metrics
                </div>
                <div class="form-group">
                    <span class="col-sm-2 control-label" id="input01">名称</span>

                    <div class="col-sm-3">
                        <input class="form-control" style="float: left"/>
                        <p class="help-block">如：com.baidu.softquery.SoftQuery v1.0</p>
                    </div>
                    <a id="testBtn" class="btn btn-primary" data-toggle="modal" data-target="#query"
                       data-backdrop="static">测试</a>
                </div>
                <div class="form-group">
                    <span class="col-sm-2 control-label" id="input02">描述</span>

                    <div class="col-sm-3">
                        <input class="form-control">
                        <p class="help-block">如：统计缓存查询服务的QPS等</p>
                    </div>
                </div>
                <div class="form-group">
                    <span class="col-sm-2 control-label" id="input03">请求延迟</span>

                    <div class="col-sm-3">
                        <input class="form-control" type="text" size="5"
                               onkeyup="value=value.replace(/[^\d]/g,'')"
                               onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
                        <p class="help-block">每次调用服务时，延迟多少毫秒，如：10</p>
                    </div>
                </div>
                <div class="form-group">
                    <span class="col-sm-2 control-label" id="input05">失败延迟</span>

                    <div class="col-sm-3">
                        <input class="form-control" type="text" size="5"
                               onkeyup="value=value.replace(/[^\d]/g,'')"
                               onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>

                        <p class="help-block">每次服务调用失败时，延迟多少毫秒，如：10000</p>
                    </div>
                </div>
                <div class="form-group">
                    <span class="col-sm-2 control-label" id="input06">Server Num</span>
                    <div class="col-sm-3">
                        <select class="form-control">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                            <option>6</option>
                            <option>7</option>
                            <option>8</option>
                            <option>9</option>
                            <option>10</option>
                            <option>11</option>
                            <option>12</option>
                            <option>13</option>
                            <option>14</option>
                            <option>15</option>
                        </select>
                        <p class="help-block">模拟多少台客户端服务器</p>
                    </div>
                </div>
                <div class="form-group">
                    <span class="col-sm-2 control-label" id="input07">Thread Num</span>
                    <div class="col-sm-3">
                        <select class="form-control">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                            <option>6</option>
                            <option>7</option>
                            <option>8</option>
                            <option>9</option>
                            <option>10</option>
                            <option>11</option>
                            <option>12</option>
                            <option>13</option>
                            <option>14</option>
                            <option>15</option>
                        </select>
                        <p class="help-block">每台客户端服务器启动多少个请求线程</p>
                    </div>
                </div>
                <div class="form-group">
                    <span class="col-sm-2 control-label" id="input08">组名</span>
                    <div class="col-sm-3">
                        <input class="form-control">

                        <p class="help-block">Ganglia监控用的组名，如：SoftQuery</p>
                    </div>
                </div>
            </div>

            <div id="step2" style="display: none">
                <div class="alert alert-info">
                    <h3>压测配置</h3>
                    请尽量完整填写下面参数，配置需要测试的方法，以及对应的参数值
                </div>
                <div class="form-group">
                    <span class="col-sm-2 control-label" id="step2-name">压测接口</span>
                    <div class="col-sm-6">
                        <table class="table-condensed designTable">
                            <thead>
                            <tr>
                                <th style="min-width: 220px;">名称</th>
                                <th>参数值</th>
                            </tr>
                            </thead>
                            <tbody id="methods">
                            </tbody>
                        </table>
                        <%--添加一个测试接口--%>
                        <a id="addBtn" class="btn btn-default" style="margin-top: 10px">Add</a>
                    </div>
                </div>
            </div>


        </div>
        <div class="form-actions">
            <a id="backBtn" class="btn btn-default disabled">返回</a>
            <a id="nextBtn" class="btn btn-primary">下一步</a>
            <button id="saveBtn" class="btn btn-primary save" style="margin-left: 30px;display: none">保存</button>
        </div>
    </form>

</div>
<%@ include file="../common/footer.jsp" %>


<!-- 添加一个参数设置对话框 -->
<script type="text/template" id="tpl_param">
    <div class="modal fade" id="#{methodID}Param" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span
                            aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title">参数设置</h4>
                </div>
                <div class="modal-body">
                    ...
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </div>
    </div>
</script>

<!-- 添加一条压测接口 -->
<script type="text/template" id="tpl_method">
    <tr id="#{methodID}Method">
        <td>
            <select  class="form-control">
                <option>GetTinySoft</option>
                <option>GetLargeSoft</option>
            </select>
        </td>
        <td>
            <a class="btn btn-default" onclick="paramSet('#{methodID}Method')">设置</a>
            <a class="btn btn-default" onclick="deleteMethod('#{methodID}Method')">删除</a>
        </td>
    </tr>
</script>