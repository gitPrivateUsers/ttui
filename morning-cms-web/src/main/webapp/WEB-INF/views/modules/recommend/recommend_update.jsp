<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>更新推荐位- 拓语网络</title>
    <link rel="stylesheet" href="${ctxsta}/common/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />
    <link rel="stylesheet" href="${ctxsta}/common/icheck/flat/green.css" />
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>更新推荐位</h5>
                    <div class="ibox-tools"> <a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a class="close-link"><i class="fa fa-times"></i></a> </div>
                </div>
                <div class="ibox-content">
                    <form id="form" class="form-horizontal" action="${ctx}/product/detail/${productRecommend.recommendProductId}/productRecommendEdit" data-method="put">
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">产品推荐ID：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="recommendProductId" readonly="readonly" value="${productRecommend.recommendProductId}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">推荐位ID：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="recommendId" value="${productRecommend.recommendId}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">商品ID：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="productId" value="${productRecommend.productId}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">排序：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="sort" value="${productRecommend.sort}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">是否可用：</label>
                            <div class="col-sm-9">
                                <label class="radio-inline">
                                    <input type="radio" name="status" value="1" ${productRecommend.status eq '1'?'checked="checked"':''}>
                                    显示</label>
                                <label class="radio-inline">
                                    <input type="radio" name="status" value="0" ${productRecommend.status eq '0'?'checked="checked"':''}>
                                    隐藏</label>
                                <label class="radio-inline status-tip"><strong>提示：</strong> 状态</label>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">开始时间：</label>
                            <div class="col-sm-7">
                                <div class="input-group date form_datetime">
                                    <input class="form-control" size="16" type="text" name="beginTime" value="<fmt:formatDate value="${productRecommend.beginTime}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly>
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span> <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span> </div>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">结束时间：</label>
                            <div class="col-sm-7">
                                <div class="input-group date form_datetime">
                                    <input class="form-control" size="16" type="text" name="endTime" value="<fmt:formatDate value="${productRecommend.endTime}" pattern="yyyy-MM-dd HH:mm:ss" />" readonly>
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span> <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span> </div>
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
    <script src="${ctxsta}/common/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
    <script src="${ctxsta}/common/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
    <script src="${ctxsta}/common/bootstrap-prettyfile/bootstrap-prettyfile.js"></script>
    <!-- 自定义js -->
    <script src="${ctxsta}/cms/js/recommendCreate.js"></script>
    <script type="text/javascript">
        $(".form_datetime").datetimepicker({
            language:  'zh-CN',
            format: "yyyy-mm-dd hh:ii:ss",
            autoclose: true,
            todayBtn: true,
            minuteStep: 10,
            pickerPosition: 'bottom-left',
        });
//        $('input[type="file"]').prettyFile();
    </script>
</myfooter>
</body>
</html>
