<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
  <title>用户列表 - 拓语网络</title>
  <link rel="stylesheet" href="${ctxsta}/common/bootstrap-table/bootstrap-table.min.css" />
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
  <div class="row">
    <div class="col-sm-12">
      <div class="ibox float-e-margins">
        <div class="ibox-title">
          <h5>用户列表</h5>
          <div class="ibox-tools"> <a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a class="close-link"><i class="fa fa-times"></i></a> </div>
        </div>
        <div class="ibox-content">
          <div class="row row-lg">
            <div class="col-sm-12">
              <div class="example-wrap">
                <div class="example">
                  <%--<div id="toolbar" class="btn-group m-t-sm">--%>
                    <%--<shiro:hasPermission name="product:detail:create">--%>
                      <%--<button type="button" class="btn btn-default"  title="创建用户" onclick="layer_show('添加用户','${ctx}/user/message/create','800','700')"> <i class="glyphicon glyphicon-plus"></i> </button>--%>
                    <%--</shiro:hasPermission>--%>
                  <%--</div>--%>
                  <table id="table"
                         data-toggle="table"
                         data-height="600"
                         data-search="true"
                         data-show-refresh="true"
                         data-show-toggle="true"
                         data-show-export="true"
                         data-show-pagination-switch="true"
                         data-show-columns="true"
                         data-url="${ctx}/customer/detail/info"
                         data-pagination="true"
                         data-page-size="20"
                         data-page-list="[20, 50, 100, 200]"
                         data-side-pagination="server"
                         data-striped="true"
                         data-pagination="true"
                         data-sort-order="desc"
                         data-toolbar="#toolbar">
                    <thead>
                    <tr>
                      <th data-field="userId" data-halign="center" data-align="center" data-sortable="true">用户ID</th>
                      <th data-field="userNumber" data-halign="center" data-align="center" data-sortable="true">用户编号</th>
                      <th data-field="userName" data-halign="center" data-align="center" data-sortable="true">昵称</th>
                      <th data-field="loginPassword" data-halign="center" data-align="center" data-sortable="true">登录密码</th>
                      <th data-field="salt" data-halign="center" data-align="center" data-sortable="true">加密密码的盐</th>
                      <th data-field="realName" data-halign="center" data-align="center" data-sortable="true">真实姓名</th>
                      <th data-field="sex" data-halign="center" data-align="center" data-sortable="true">性别</th>
                      <th data-field="age" data-halign="center" data-align="center" data-sortable="true">年龄</th>
                      <th data-field="picImg" data-halign="center" data-align="center" data-sortable="true">用户头像</th>
                      <th data-field="status" data-halign="center" data-align="center" data-sortable="true">状态</th>
                      <th data-field="emailIsActive" data-halign="center" data-align="center" data-sortable="true">邮箱激活</th>
                      <th data-field="email" data-halign="center" data-align="center" data-sortable="true">电子邮箱</th>
                      <th data-field="tmlephone" data-halign="center" data-align="center" data-sortable="true">手机号码</th>
                      <th data-field="lastLoginTime" data-halign="center" data-align="center" data-sortable="true">最后登录时间</th>
                      <th data-field="lastLoginIP" data-halign="center" data-align="center" data-sortable="true">最后登录IP</th>
                      <th data-field="loginNumber" data-halign="center" data-align="center" data-sortable="true">登录次数</th>
                      <th data-field="registTime" data-halign="center" data-align="center" data-sortable="true">注册时间</th>
                      <th data-field="createBy" data-halign="center" data-align="center" data-sortable="true">创建者</th>
                      <th data-field="updateTime" data-halign="center" data-align="center" data-sortable="true">更新时间</th>
                      <th data-field="updateBy" data-halign="center" data-align="center" data-sortable="true">更新者</th>
                      <th data-field="amount" data-halign="center" data-align="center" data-sortable="true">消费额</th>
                      <th data-field="rankId" data-halign="center" data-align="center" data-sortable="true">会员等级ID</th>
                      <th data-field="score" data-halign="center" data-align="center" data-sortable="true">会员积分</th>
                    <%--<th data-field="remarks" data-halign="center" data-align="center" data-sortable="true">备注</th>--%>
                      <th data-formatter="actionFormatter" data-events="actionEvents" data-halign="center" data-align="center" data-sortable="true">操作</th>
                    </tr>
                    </thead>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<myfooter>
  <!-- Bootstrap table -->
  <script src="${ctxsta}/common/bootstrap-table/bootstrap-table.min.js"></script>
  <script src="${ctxsta}/common/bootstrap-table/extensions/export/bootstrap-table-export.js"></script>
  <script src="${ctxsta}/common/bootstrap-table/tableExport.js"></script>
  <script src="${ctxsta}/common/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
  <!-- 自定义js -->
  <script src="${ctxsta}/cms/js/customerList.js"></script>
</myfooter>
</body>
</html>