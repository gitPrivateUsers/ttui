<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/base.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>修改商品详情信息- 拓语网络</title>
    <link rel="stylesheet" href="${ctxsta}/common/icheck/flat/green.css"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>修改商品详情信息
                        <%--<small> 广告位信息时应当遵循合法、正当、必要的原则，明示目的、方式和范围。</small>--%>
                    </h5>
                    <div class="ibox-tools"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
                            class="close-link"><i class="fa fa-times"></i></a></div>
                </div>
                <div class="ibox-content">
                    <form id="form" class="form-horizontal" action="${ctx}/product/detail/update" data-method="put">

                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">详情ID：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="productDetailId" readonly="readonly" value="${productDetail.productDetailId}">
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">商品ID：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="productId"  readonly="readonly" value="${productDetail.productId}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">详情描述：</label>
                            <div class="col-sm-7">
                                <textarea class="form-control" rows="8" name="description"
                                          placeholder="请输入消息...">${productDetail.description}</textarea>
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
    <script src="${ctxsta}/cms/js/productList.js"></script>
</myfooter>
</body>
</html>
