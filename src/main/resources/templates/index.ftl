<!DOCTYPE HTML>
<html>
<head>
<#assign ctx=springMacroRequestContext.contextPath>
    <title>Getting Started: Serving Web Content</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="${ctx}/bootstrap/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
    <script src="${ctx}/bootstrap/js/bootstrap.min.js"></script>

</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-8">
            <div class="panel panel-primary">
                <div class="panel-heading">数据信息</div>
                <div class="panel-body">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>id信息</th>
                            <th>用例信息</th>
                            <th>节点路径</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="panel panel-primary">
                <div class="panel-heading">可用服务器信息</div>
                <div class="panel-body">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>服务器名称</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>



    <form role="form" action="${ctx}/saveQuestion" class="form-horizontal" method="post">
        <div class="form-group">
            <label for="exampleInputEmail1">节点ID</label>
            <input type="text" class="form-control" name="id" required value="${questionInfo.id!}">
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1">用例信息</label>
            <textarea name="data" required class="form-control">${questionInfo.data!}</textarea>
        </div>
        <button type="submit" class="btn btn btn-success">Submit</button>
    </form>
</div>


</body>
</html>