<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/base.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>修改商品信息- 拓语网络</title>
    <link rel="stylesheet" href="${ctxsta}/common/icheck/flat/green.css"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>修改商品信息
                        <small> 广告位信息时应当遵循合法、正当、必要的原则，明示目的、方式和范围。</small>
                    </h5>
                    <div class="ibox-tools"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
                            class="close-link"><i class="fa fa-times"></i></a></div>
                </div>
                <div class="ibox-content">
                    <form id="form" class="form-horizontal" action="${ctx}/product/detail/${product.productId}"
                          data-method="put">
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">商品名称：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="name" value="${product.name}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">显示价格：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="code" value="${product.showPrice}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">商品简介：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="introduce" value="${product.introduce}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">商品编码：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="productNumber" value="${product.productNumber}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">图片：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="picImg" value="${product.picImg}">
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2  col-xs-offset-1 control-label">搜索关键词：</label>
                            <div class="col-sm-7">
                                <textarea class="form-control" rows="2" name="searchKey"
                                          placeholder="请输入消息...">${product.searchKey}</textarea>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">页面标题：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="pageTitle" value="${product.pageTitle}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">页面关键词：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="pageKeyword" value="${product.pageKeyword}">
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2  col-xs-offset-1 control-label">页面描述：</label>
                            <div class="col-sm-7">
                                <textarea class="form-control" rows="2" name="pageDescription"
                                          placeholder="请输入消息...">${product.pageDescription}</textarea>
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
