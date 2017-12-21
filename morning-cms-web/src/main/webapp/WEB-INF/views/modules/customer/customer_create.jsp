<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>创建商品 - 拓语网络</title>
<link rel="stylesheet" href="${ctxsta}/common/icheck/flat/green.css" />
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
  <div class="row">
    <div class="col-sm-12">
      <div class="ibox float-e-margins">
        <div class="ibox-title">
          <h5>创建用户</h5>
          <div class="ibox-tools"> <a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a class="close-link"><i class="fa fa-times"></i></a> </div>
        </div>
        <div class="ibox-content">
          <form id="form" class="form-horizontal" action="${ctx}/customer/detail" data-method="post">
            <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">昵称：</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="userName">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">登录密码：</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="loginPassword">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">真实姓名：</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="realName">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">性别：</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="sex">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">年龄：</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="age">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">电子邮箱：</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="email">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">状态：</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="status">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <div class="col-sm-12 text-center">
                <button class="btn btn-primary" type="submit">提交</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>
<myfooter> 
  <!-- 自定义js --> 
  <script src="${ctxsta}/cms/js/customerCreate.js"></script>
</myfooter>
</body>
</html>
