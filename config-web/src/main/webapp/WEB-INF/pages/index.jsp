<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>

<div class="container-fluid">
    <div class="row">
        <%@ include file="monitor-navigator.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1>Monitor Browser</h1>

            <div class="well">
                <form class="form-inline" role="form">
                    <span class="btn-group pull-right">
                        <a class="btn btn-status btn-success" data-value="RUNNING">正在运行</a>
                        <a class="btn btn-status btn-danger disable-feedback" data-value="KILLED">已停止</a>
                    </span>
                    <input type="text" id="filterInput" class="form-control" placeholder="搜索用户名、名称等...">
                </form>
            </div>

            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>服务名</th>
                        <th>描述</th>
                        <th>所有者</th>
                        <th>状态</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr style="cursor: pointer;">
                        <td>appcache</td>
                        <td>缓存查询框架</td>
                        <td>loren</td>
                        <td>
                            <span class="label label-success">RUNNING</span>
                        </td>
                        <td>2014</td>
                        <td>
                            <button type="button" class="btn btn-default btn-sm">停止</button>
                        </td>
                    </tr>
                    <tr style="cursor: pointer;">
                        <td>softquery</td>
                        <td>amet</td>
                        <td>consectetur</td>
                        <td>
                            <span class="label label-success">RUNNING</span>
                        </td>
                        <td>elit</td>
                        <td>
                            <button type="button" class="btn btn-default btn-sm">停止</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div class="row">
                <div class="col-sm-4" style="padding-top: 25px">
                    <div class="dataTables_info" id="jobsTable_info">Showing 1 to 30 of 100 entries</div>
                </div>

                <div class="pull-right">
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
    </div>
</div>
</div>
<%@ include file="../common/footer.jsp" %>
