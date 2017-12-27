<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/base.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>修改订单信息- 拓语网络</title>
    <link rel="stylesheet" href="${ctxsta}/common/icheck/flat/green.css"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>修改订单信息
                        <small> 广告位信息时应当遵循合法、正当、必要的原则，明示目的、方式和范围。</small>
                    </h5>
                    <div class="ibox-tools"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
                            class="close-link"><i class="fa fa-times"></i></a></div>
                </div>
                <div class="ibox-content">
                    <form id="form" class="form-horizontal" action="${ctx}/system/order/${order.orderId}"
                          data-method="put">
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">订单ID：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="orderId" readonly="readonly" value="${order.orderId}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">支付方式：</label>
                            <div class="col-sm-9">
                                <label class="radio-inline">
                                    <input type="radio" class="js-switch" name="payType" value="1" ${order.payType}/>在线支付</label>
                                <label class="radio-inline">
                                    <input type="radio" class="js-switch" name="payType" value="0" ${order.payType}/>线下支付</label>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">配送时间：</label>
                            <div class="col-sm-9">
                                <label class="radio-inline">
                                <input type="radio" class="js-switch" name="shipmentTime" value="1"${order.shipmentTime}>不限</label>
                                <label class="radio-inline">
                                <input type="radio" class="js-switch" name="shipmentTime" value="2"${order.shipmentTime}>工作日</label>
                                <label class="radio-inline">
                                <input type="radio" class="js-switch" name="shipmentTime" value="3"${order.shipmentTime}>双休日/节假日</label>
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
    <script src="${ctxsta}/cms/js/orderList.js"></script>
</myfooter>
</body>
</html>
