<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/base.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>评论管理 - 拓语网络</title>
    <link rel="stylesheet" href="${ctxsta}/common/bootstrap-table/bootstrap-table.min.css"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>评论管理</h5>
                    <div class="ibox-tools"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
                            class="close-link"><i class="fa fa-times"></i></a></div>
                </div>
                <div class="ibox-content">
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <div class="example-wrap">
                                <div class="example">
                                <!-- 添加按钮 -->
                                 <%-- <div id="toolbar" class="btn-group m-t-sm">
                                          <shiro:hasPermission name="product:list:view">
                                            <button type="button" class="btn btn-default"  title="创建订单" onclick="layer_show('创建订单','${ctx}/administrator/role/create','1000','700')"> <i class="glyphicon glyphicon-plus"></i> </button>
                                          </shiro:hasPermission>
                                  </div> --%>
                                    <table id="table"
                                           data-toggle="table"
                                           data-height="600"
                                           data-search="true"
                                           data-show-refresh="true"
                                           data-show-toggle="true"
                                           data-show-export="true"
                                           data-show-pagination-switch="true"
                                           data-show-columns="true"
                                           data-url="${ctx}/system/order/reviews/list"
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
                                            <th data-field="commentId" data-halign="center" data-align="center" data-sortable="true">评论ID</th>
                                            <th data-field="productId" data-halign="center" data-align="center" data-sortable="true">商品ID</th>
                                            <th data-field="userId" data-halign="center" data-align="center" data-sortable="true">用户ID</th>
                                            <th data-field="userName" data-halign="center" data-align="center" data-sortable="true">昵称</th>
                                            <th data-field="orderId" data-halign="center" data-align="center" data-sortable="true">订单ID</th>
                                            <th data-formatter="starFormatter" data-field="star" data-halign="center" data-align="center" data-sortable="true">评论星级</th>
                                            <th data-field="content" data-halign="center" data-align="center" data-sortable="true">评论内容</th>
                                            <th data-field="goodCount" data-halign="center" data-align="center" data-sortable="true">点赞次数</th>
                                            <th data-formatter="statusFormatter" data-field="status" data-halign="center" data-align="center" data-sortable="true">状态</th>
                                            <th data-formatter="styleFormatter"data-field="type" data-halign="center" data-align="center" data-sortable="true">评论类型</th>
                                            <th data-field="createBy" data-halign="center" data-align="center" data-sortable="true">评论人</th>
                                            <th data-formatter="timeFormatter"data-field="createTime" data-halign="center" data-align="center" data-sortable="true">评论时间</th>
                                            <th data-formatter="actionFormatter"data-events="actionEvents" data-halign="center" data-align="center" data-sortable="true">操作</th>
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
    <script src="${ctxsta}/cms/js/commentList.js"></script>
</myfooter>
</body>
</html>