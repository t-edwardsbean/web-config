<%--
  Created by IntelliJ IDEA.
  User: edwardsbean
  Date: 14-10-27
  Time: 上午11:45
  To change this template use File | Settings | File Templates.
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
</style>

<div class="container-fluid">
<h1>Monitor Creator</h1>
<div class="alert alert-info">
    <h3>监控配置</h3>
    请尽量完整填写下面参数，用于生成向ganglia汇报的metrics
</div>
<form class="form-horizontal" id="itemForm" method="POST" style="padding-top: 20px" action="/monitor-create">
    <div class="form-group">
        <span class="col-sm-2 control-label" id="input01">名称</span>

        <div class="col-sm-3">
            <input type="name" class="form-control" validate="true">
            <p class="help-block">如：缓存查询服务。</p>
        </div>
    </div>
    <div class="form-group">
        <span class="col-sm-2 control-label" id="input02">描述</span>

        <div class="col-sm-3">
            <input type="name" class="form-control">
            <p class="help-block">如：统计缓存查询服务的QPS等</p>
        </div>
    </div>
    <div class="form-group">
        <span class="col-sm-2 control-label" id="input03">ip</span>

        <div class="col-sm-3">
            <input type="name" class="form-control">
            <p class="help-block">服务主机ip,如：192.168.253.126</p>
        </div>
    </div>
    <div class="form-group">
        <span class="col-sm-2 control-label" id="input04">端口</span>

        <div class="col-sm-3">
            <input type="name" class="form-control">
            <p class="help-block">如：服务对外开放的端口号</p>
        </div>
    </div>
    <div class="form-group">
        <span class="col-sm-2 control-label" id="input05">用户名</span>
        <div class="col-sm-3">
            <input type="name" class="form-control">
            <p class="help-block">请填写jmx的账号，如果有的话</p>
        </div>
    </div>
    <div class="form-group">
        <span class="col-sm-2 control-label" id="input06">密码</span>
        <div class="col-sm-3">
            <input type="name" class="form-control">
            <p class="help-block">请填写jmx的密码，如果有的话</p>
        </div>
    </div>
    <div class="form-group">
        <span class="col-sm-2 control-label" id="input07">监控类</span>
        <div class="col-sm-3">
            <input type="name" class="form-control">
            <p class="help-block">提供jmx监控属性的类，如：com.baidu.unicorn:name=ServiceManager</p>
        </div>
    </div>
    <div class="form-actions">
        <button type="submit" class="btn btn-primary">保存</button>
        <a href="/" class="btn btn-default">取消</a>
    </div>
</form>

</div>
<%@ include file="../common/footer.jsp" %>
