<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/base.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>修改评论状态- 拓语网络</title>
    <link rel="stylesheet" href="${ctxsta}/common/icheck/flat/green.css"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>修改评论状态
                        <small> 评论信息时应当遵循合法、正当、必要的原则，明示目的、方式和范围。</small>
                    </h5>
                    <div class="ibox-tools"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
                            class="close-link"><i class="fa fa-times"></i></a></div>
                </div>
                <div class="ibox-content">
                    <form id="form" class="form-horizontal" action="${ctx}/system/order/${comment.commentId}"
                          data-method="put">
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">评论ID：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="commentId" readonly="readonly" value="${comment.commentId}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">评论状态：</label>
                            <div class="col-sm-9">
                                <label class="radio-inline">
                                    <input type="radio" class="js-switch" name="status" value="1" ${comment.status}/>显示</label>
                                <label class="radio-inline">
                                    <input type="radio" class="js-switch" name="status" value="0" ${comment.status}/>隐藏</label>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">评论类型：</label>
                            <div class="col-sm-9">
                                <label class="radio-inline">
                                    <input type="radio" class="js-switch" name="type" value="1" ${comment.type}/>显示</label>
                                <label class="radio-inline">
                                    <input type="radio" class="js-switch" name="type" value="0" ${comment.type}/>隐藏</label>
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
    <script src="${ctxsta}/cms/js/commentList.js"></script>
</myfooter>
</body>
</html>
