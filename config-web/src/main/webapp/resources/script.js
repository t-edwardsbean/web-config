/**
 * Created by edwardsbean on 14-11-3.
 */
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
        } else {
            $("#backBtn").addClass("disabled");
        }
        if (step == "step3") {
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

    $("#saveBtn").click(function () {
        var data = new Object();
        $(".field").each(function () {
            data[$(this).attr("name")] = $(this).val();
        });
        $.ajax({
            url: "save",
            data: data,
            type: 'post',
            success: function (json) {
                if (json.code == "0") {
                    //成功，跳转到主界面
                    alert("保存成功")
                } else {
                    alert("保存失败")
                }
            }
        });
    });
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
    $("#checkError").hide();
    $("#nextBtn").removeClass("disabled");
    $("#nextBtn").addClass("disabled");
    $("#methods").html("");
}

function resetParams(id) {
    $("#" + id).find("input").val("");
}

function testClose() {
    $("#downloadCheck").html("");
}

function comfirmCheck() {
    $("#nextBtn").removeClass("disabled");
}

function addMethod() {
    currentMethod++;
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
    $("#" + id + "Method").remove();
    $("#" + id + "MethodParam").remove();
}

function paramSet(id) {
    //获取用户选中的方法
    var selectedIndex = $("#" + id + "MethodList")[0].selectedIndex;
    var lastSelectIndex = $("#" + id + "MethodSelectIndex").val();
    var paramID = "#" + id + "MethodParam";
    var methodName = $("#" + id + "MethodList").val();

    if (lastSelectIndex != selectedIndex) {
        //用户切换了测试方法，或者第一次要设置参数,删除旧的参数填写框
        $(paramID).remove();
        var methodParams = methodList[selectedIndex].methodParams;
        var template = $("#tpl_param").html();
        var data =
        {
            methodID: id,
            selectIndex: selectedIndex,
            list: methodParams,
            methodName: methodName
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
                $("#downloadCheck").append("<dd>失败</dd>");
                $("#downloadProgress").removeClass("progress-bar-success");
                $("#downloadProgress").addClass("progress-bar-danger");
            }
        }
    });


}