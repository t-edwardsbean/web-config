<%--
  Created by IntelliJ IDEA.
  User: edwardsbean
  Date: 14-10-27
  Time: 下午4:15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>
<script src="/resources/ext/js/routie-0.3.0.min.js" type="text/javascript" charset="utf-8"></script>
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
    var serviceId = 0;
    var methodList;
    var serviceName = "";

    $(document).ready(function () {

        var currentStep = "step1";
        //锚点路由
        routie({
            "step1": function () {
                showStep("step1");
            },
            "step2": function () {
                if (validateStep("step1")) {
                    showStep("step2");
                }
                else {
                    routie("step1");
                }
            },
            "step3": function () {
                if (validateStep("step1") && validateStep("step2")) {
                    showStep("step3");
                }
                else {
                    routie("step1");
                }
            }
        });

        function showStep(step) {
            currentStep = step;
            if (step != "step1") {
                $("#backBtn").removeClass("disabled");
            }else {
                $("#backBtn").addClass("disabled");
            }
            if(step == "step3") {
                $("#nextBtn").addClass("disabled");
                $("#saveBtn").show();
            } else {
                $("#nextBtn").removeClass("disabled");
                $("#saveBtn").hide();
            }
            $("a.step").parent().removeClass("active");
            $("a.step[href=#" + step + "]").parent().addClass("active");
            $(".stepDetails").hide();
            $("#" + step).show();
        }

        function validateStep(step) {
            var proceed = true;
            $("#" + step).find("[validate=true]").each(function () {
                if ($(this).val().trim() == "") {
                    proceed = false;
                    $(this).parents(".form-group").addClass("has-error");
                    $(this).parent().find(".help-block").remove();
                    $(this).after("<span class=\"help-block\"><strong>此字段为必填字段</strong></span>");
                }
            });
            return proceed;
        }


        $("#backBtn").click(function () {
            var nextStep = (currentStep.substr(4) * 1 - 1);
            if (nextStep >= 1) {
                routie("step" + nextStep);
            }
        });

        $("#nextBtn").click(function () {
            var nextStep = (currentStep.substr(4) * 1 + 1);
            if (nextStep <= $(".step").length) {
                routie("step" + nextStep);
            }
        });

        $("[validate=true]").change(function () {
            $(this).parents(".form-group").removeClass("has-error");
            $(this).parent().find(".help-block").remove();
        });


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

//        $("#nextBtn").click(function () {
//            $("#step1").hide();
//            $("#step2").show();
//            $("#backBtn").removeClass("disabled");
//            $("#saveBtn").show();
//            $("#nextBtn").addClass("disabled");
//            addMethod();
//
//        });
//
//        $("#backBtn").click(function () {
//            $("#step2").hide();
//            $("#step1").show();
//            $("#backBtn").addClass("disabled");
//            $("#saveBtn").hide();
//            $("#nextBtn").removeClass("disabled");
//        });

        $("#addBtn").click(function () {
            addMethod();
        });

    });

    function reset() {
        currentMethod = 0;
        methodList = null;
        serviceId = "";
        serviceName = "";
        $("#downloadCheck").html("");
        $("#nextBtn").removeClass("disabled");
        $("#nextBtn").addClass("disabled");
        $("#methods").html("");
    }

    function resetParams(id){
        $("#" + id).find("input").val("");
    }

    function testClose() {
        $("#downloadCheck").html("");
    }

    function comfirmCheck() {
        $("#nextBtn").removeClass("disabled");
    }

    function addMethod() {
        if (currentMethod == 0) {
            currentMethod = 1;
        } else {
            currentMethod++;
        }
        var template = $('#tpl_method').html();
        var data =
        {
            methodID: currentMethod,
            list: methodList
        };
        var html = juicer(template, data);
        $("#methods").append(html);
    }

    function deleteMethod(id) {
        $("#" + id).remove();
        $("#" + id + "Param").remove();
    }

    function paramSet(id) {
        //获取用户选中的方法
        var selectedIndex = $("#" + id + "List")[0].selectedIndex;
        var lastSelectIndex = $("#" + id + "SelectIndex").val();
        var paramID = "#" + id + "Param";

        if (lastSelectIndex != selectedIndex) {
            //用户切换了测试方法，或者第一次要设置参数,删除旧的参数填写框
            $(paramID).remove();
            var methodParams = methodList[selectedIndex].methodParams;
            var template = $("#tpl_param").html();
            var data =
            {
                methodID: id,
                selectIndex: selectedIndex,
                list: methodParams
            };
            //新建一个参数填写框
            var modal = juicer(template, data);
            $("#params").append(modal);
        }

        //弹出
        $(paramID).modal({
            backdrop: 'static',
            show: true
        });
    }

    function queryService() {

        reset();

        $("#query").modal({
            backdrop: 'static',
            show: true
        });

        $("#downloadCheck").append("<dt>查询服务:</dt>");
        $.ajax({
            type: "post",
            url: "check",
            data: {serviceName: "com.baidu.softquery.SoftQuery v1.0"},
            async: false,
            dataType: "json",
            success: function (json) {
                //查询服务
                if (json.code == "0") {
                    serviceId = json.returnData.serviceId;
                    serviceName = json.returnData.serviceName;
                    $("#downloadCheck").append("<dd>服务存在</dd>");
                    $("#downloadProgress").css("width", "40%");
                    $("#downloadCheck").append("<dt>载入接口:</dt>");
                    $.ajax({
                        type: "post",
                        url: "load",
                        data: {serviceId: serviceId, serviceName: "com.baidu.softquery.SoftQuery v1.0"},
                        async: false,
                        dataType: "json",
                        success: function (json) {
                            //载入接口列表
                            if (json.code == "0") {
                                methodList = json.returnData
                                $("#downloadCheck").append("<dd>成功</dd>");
                                $("#downloadCheck").append("<dt>服务名称:</dt>");
                                $("#downloadCheck").append("<dd>" + serviceName + "</dd>");
                                $("#downloadProgress").css("width", "100%");
                                $("#confirmBtn").removeClass("disabled");
                                addMethod();
                            } else {
                                $("#checkError").html(json.msg);
                                $("#checkError").show();
                                $("#downloadCheck").append("<dd>失败</dd>");
                                $("#downloadProgress").removeClass("progress-bar-success");
                                $("#downloadProgress").addClass("progress-bar-danger");
                            }
                        }
                    });
                } else {
                    $("#checkError").html(json.msg);
                    $("#checkError").show();
                    $("#downloadCheck").append("<dd>服务不存在</dd>");
                    $("#downloadProgress").removeClass("progress-bar-success");
                    $("#downloadProgress").addClass("bar bar-danger");
                }
            }
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
                <h4 class="modal-title">检测服务</h4>
            </div>
            <div class="modal-body" id="modalBody">
                <div class="progress">
                    <div id="downloadProgress" class="active progress-bar progress-bar-success progress-bar-striped"
                         role="progressbar"
                         aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="padding-top: 20px;width: 10%">
                    </div>
                </div>
                <dl class="dl-horizontal" id="downloadCheck">
                </dl>
                <div class="well" id="checkError" style="display: none"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="testClose()">Close</button>
                <a id="confirmBtn" class="btn btn-primary disabled" data-dismiss="modal" onclick="comfirmCheck()">确认</a>
            </div>
        </div>
    </div>
</div>


<div class="container-fluid">
    <h1>Stress Test Creator</h1>

    <ul class="nav nav-pills">
        <li class="active"><a href="#step1" class="step">第一步：服务</a></li>
        <li><a href="#step2" class="step">第二步：接口</a></li>
        <li><a href="#step3" class="step">第三步:频率</a></li>
    </ul>
    <form class="form-horizontal" id="itemForm" method="POST" style="margin-top: 0" action="/monitor-create">
        <div id="steps" class="steps">
            <%--参数设置--%>
            <div id="params">
            </div>

                <div id="step1" class="stepDetails">
                    <div class="alert alert-info">
                        <h3>服务配置</h3>
                        请填写需要测试的服务名称，并测试服务服务是否存在
                    </div>
                    <div class="form-group">
                        <span class="col-sm-2 control-label">名称</span>

                        <div class="col-sm-3">
                            <input id="serviceName" class="form-control" style="float: left" validate="true">

                            <p class="help-block">如：com.baidu.softquery.SoftQuery v1.0</p>
                        </div>
                        <a id="testBtn" class="btn btn-primary" onclick="queryService()" data-toggle="tooltip" data-placement="right" title="测试服务是否正常">测试</a>
                    </div>
                    <div class="form-group">
                        <span class="col-sm-2 control-label" id="input02">描述</span>

                        <div class="col-sm-3">
                            <input class="form-control">

                            <p class="help-block">如：统计缓存查询服务的QPS等</p>
                        </div>
                    </div>

                    <div class="form-group">
                        <span class="col-sm-2 control-label" id="input08">组名</span>

                        <div class="col-sm-3">
                            <input class="form-control"  validate="true">

                            <p class="help-block">Ganglia监控用的组名，如：SoftQuery</p>
                        </div>
                    </div>
                </div>

                <div id="step2" class="stepDetails" style="display: none">
                    <div class="alert alert-info">
                        <h3>接口配置</h3>
                        通过第一步的服务测试，就可以选择要测试的接口，并设置接口的参数值
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

                            <a id="addBtn" class="btn btn-default" style="margin-top: 10px">Add</a>
                        </div>
                    </div>
                </div>


                <div id="step3" class="stepDetails" style="display: none">
                    <div class="alert alert-info">
                        <h3>频率配置</h3>
                        请适当的配置压测的各项参数，不填写将按默认配置
                    </div>
                    <div class="form-group">
                        <span class="col-sm-2 control-label" id="input03">请求延迟</span>

                        <div class="col-sm-3">
                            <input class="form-control" type="text" size="5" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">

                            <p class="help-block">每次调用服务时，延迟多少毫秒，如：10</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <span class="col-sm-2 control-label" id="input05">失败延迟</span>

                        <div class="col-sm-3">
                            <input class="form-control" type="text" size="5" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">

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
                    <input type="text" id="#{methodID}SelectIndex" style="display: none" value="#{selectIndex}"/>
                    <div class="alert alert-success" role="alert">参数类型为
                        <strong>基本类型</strong>
                        的，如int,long,String,Enum等，请直接填写参数值。参数类型为
                        <strong>复杂类型</strong>
                        的，请使用json格式，如{ userName:edwardsbean,age:18 }
                    </div>
                    <table class="table-condensed designTable">
                        <thead>
                        <tr>
                            <th style="min-width: 40px">参数</th>
                            <th style="min-width: 100px">参数类型</th>
                            <th class="col-sm-10">参数值</th>
                        </tr>
                        </thead>
                        <tbody id="#{methodID}ParamBody">
                        {@each list as item,index}
                        <tr>
                            <td>arg#{index}</td>
                            <td>#{item}</td>
                            <td>
                                <input class="form-control"/>
                            </td>
                        </tr>
                        {@/each}
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" onclick="resetParams('#{methodID}ParamBody')" data-dismiss="modal">清空</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">保存</button>
                </div>
            </div>
        </div>
    </div>
</script>

<!-- 添加一条压测接口 -->
<script type="text/template" id="tpl_method">
    <tr id="#{methodID}Method">
        <td>
            <select id="#{methodID}MethodList" class="form-control">
                {@each list as it}
                <option value="#{it.methodName}">#{it.methodName}</option>
                {@/each}
            </select>
        </td>
        <td class="col-sm-3">
            <a class="btn btn-default" onclick="paramSet('#{methodID}Method')">设置</a>
            <a class="btn btn-default" onclick="deleteMethod('#{methodID}Method')">删除</a>
        </td>
    </tr>
</script>