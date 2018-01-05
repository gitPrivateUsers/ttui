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
          <h5>用户地址信息列表</h5>
          <div class="ibox-tools"> <a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a class="close-link"><i class="fa fa-times"></i></a> </div>
        </div>
        <div class="ibox-content">
          <div class="row row-lg">
            <div class="col-sm-12">
              <div class="example-wrap">
                <div class="example">
                  <div id="toolbar" class="btn-group m-t-sm">
                    <shiro:hasPermission name="customer:detail:create">
                      <button type="button" class="btn btn-default" title="添加地址"  onclick="layer_show('添加地址','${ctx}/customer/detail/address/create/${userId}','800','700')"> <i class="glyphicon glyphicon-plus"></i> </button>
                    </shiro:hasPermission>
                  </div>
                  <table id="table"
                         data-toggle="table"
                         data-height="600"
                         data-search="false"
                         data-show-refresh="true"
                         data-show-toggle="true"
                         data-show-export="true"
                         data-show-pagination-switch="true"
                         data-show-columns="true"
                         data-url="${ctx}/customer/detail/address/${userId}"
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
                      <%--<th data-field="addressId" data-halign="center" data-align="center" data-sortable="true">收货地址ID</th>--%>
                      <th data-field="userId" data-halign="center" data-align="center" data-sortable="true">用户ID</th>
                      <th data-field="userName" data-halign="center" data-align="center" data-sortable="true">姓名</th>
                      <th data-field="userTag" data-halign="center" data-align="center" data-sortable="true">地址标签</th>
                      <th data-field="userPhone" data-halign="center" data-align="center" data-sortable="true">手机号码</th>
                      <%--<th data-field="provinceId" data-halign="center" data-align="center" data-sortable="true">省份ID</th>--%>
                      <th data-field="provinceName" data-halign="center" data-align="center" data-sortable="true">省份名字</th>
                      <%--<th data-field="cityId" data-halign="center" data-align="center" data-sortable="true">城市ID</th>--%>
                      <th data-field="cityName" data-halign="center" data-align="center" data-sortable="true">城市名字</th>
                      <%--<th data-field="districtId" data-halign="center" data-align="center" data-sortable="true">区域ID</th>--%>
                      <th data-field="districtName" data-halign="center" data-align="center" data-sortable="true">区域名字</th>
                      <th data-field="userAdress" data-halign="center" data-align="center" data-sortable="true">详细地址</th>
                      <th data-field="userZipcode" data-halign="center" data-align="center" data-sortable="true">邮政编码</th>
                      <th data-formatter="timeFormatter"data-field="createTime" data-halign="center" data-align="center" data-sortable="true">创建时间</th>
                      <%--<th data-formatter="timeFormatter"data-field="updateTime" data-halign="center" data-align="center" data-sortable="true">更新时间</th>--%>
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
  <script src="${ctxsta}/cms/js/customerAddressList.js"></script>
</myfooter>
</body>
</html>