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
                        </tr>
                        </thead>
                        <tbody>

                        <#list questionList as question>
                            <tr>
                                <td>${question.id}</td>
                                <td>${question.data}</td>
                                <td>${question.hasSyn?string('yes', 'no')}</td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>



    <form role="form" action="${ctx}/question/save" class="form-horizontal" method="post">
        <div class="form-group">
            <label for="exampleInputPassword1">用例信息</label>
            <textarea name="data" required class="form-control"></textarea>
        </div>
        <button type="submit" class="btn btn btn-success">Submit</button>
    </form>
</div>


</body>
</html>