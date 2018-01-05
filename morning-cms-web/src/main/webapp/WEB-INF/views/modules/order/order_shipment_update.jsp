<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/base.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>修改配送信息- 拓语网络</title>
    <link rel="stylesheet" href="${ctxsta}/common/icheck/flat/green.css"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>修改订单配送信息
                        <small> 广告位信息时应当遵循合法、正当、必要的原则，明示目的、方式和范围。</small>
                    </h5>
                    <div class="ibox-tools"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
                            class="close-link"><i class="fa fa-times"></i></a></div>
                </div>
                <div class="ibox-content">
                    <form id="form" class="form-horizontal" action="${ctx}/system/order/${orderShipment.orderShipmentId}/updateShipment"
                          data-method="put">
                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">订单配送ID：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="orderShipmentId" readonly="readonly" value="${orderShipment.orderShipmentId}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">用户名：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="userName" readonly="readonly" value="${orderShipment.userName}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">手机号：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="userPhone"  value="${orderShipment.userPhone}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">省份名字：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="provinceName" value="${orderShipment.provinceName}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">城市名字：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="cityName"  value="${orderShipment.cityName}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">区域名字：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="districtName"  value="${orderShipment.districtName}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">详细地址：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="userAdress" value="${orderShipment.userAdress}">
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
    <script src="${ctxsta}/common/switchery/switchery.min.js"></script>
    <!-- 自定义js -->
    <script src="${ctxsta}/cms/js/orderShipmentUpdate.js"></script>
</myfooter>
</body>
</html>
