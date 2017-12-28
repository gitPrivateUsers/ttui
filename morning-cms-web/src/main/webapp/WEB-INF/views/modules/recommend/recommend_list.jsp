<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
  <title>商品推荐 - 拓语网络</title>
  <link rel="stylesheet" href="${ctxsta}/common/bootstrap-table/bootstrap-table.min.css" />
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
  <div class="row">
    <div class="col-sm-12">
      <div class="ibox float-e-margins">
        <div class="ibox-title">
          <h5>推荐列表</h5>
          <div class="ibox-tools"> <a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a class="close-link"><i class="fa fa-times"></i></a> </div>
        </div>
        <div class="ibox-content">
          <div class="row row-lg">
            <div class="col-sm-12">
              <div class="example-wrap">
                <div class="example">
                  <div id="toolbar" class="btn-group m-t-sm">
                   <%-- <shiro:hasPermission name="product:recommend:create">--%>
                      <button type="button" class="btn btn-default" title="创建商品推荐位"  onclick="layer_show('创建商品推荐位','${ctx}/product/detail/recommendCreate','800','700')"> <i class="glyphicon glyphicon-plus"></i> </button>
                   <%-- </shiro:hasPermission>--%>
                  </div>
                  <table id="table"
                         data-toggle="table"
                         data-height="600"
                         data-search="true"
                         data-show-refresh="true"
                         data-show-toggle="true"
                         data-show-export="true"
                         data-show-pagination-switch="true"
                         data-show-columns="true"
                         data-url="${ctx}/product/detail/recommendList"
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
                      <th data-field="recommendProductId" data-halign="center" data-align="center" data-sortable="true">产品推荐ID</th>
                      <th data-field="recommendId" data-halign="center" data-align="center" data-sortable="true">推荐位ID</th>
                      <th data-field="productId" data-halign="center" data-align="center" data-sortable="true">商品ID</th>
                      <th data-field="sort" data-halign="center" data-align="center" data-sortable="true">排序</th>
                      <th data-formatter="statusFormatter" data-field="status" data-halign="center" data-align="center" data-sortable="true">状态</th>
                      <th data-formatter="timeFormatter" data-field="beginTime" data-halign="center" data-align="center" data-sortable="true">推荐起始时间</th>
                      <th data-formatter="timeFormatter" data-field="endTime" data-halign="center" data-align="center" data-sortable="true">推荐结束时间</th>
                      <%--<th data-formatter="timeFormatter" data-field="createTime" data-halign="center" data-align="center" data-sortable="true">创建时间</th>--%>
                      <%--<th data-formatter="timeFormatter" data-field="createBy" data-halign="center" data-align="center" data-sortable="true">创建者</th>--%>
                      <th data-formatter="timeFormatter" data-field="updateTime" data-halign="center" data-align="center" data-sortable="true">更新时间</th>
                      <th data-field="updateBy" data-halign="center" data-align="center" data-sortable="true">更新者</th>
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
  <script src="${ctxsta}/cms/js/recommendList.js"></script>
</myfooter>
</body>
</html>