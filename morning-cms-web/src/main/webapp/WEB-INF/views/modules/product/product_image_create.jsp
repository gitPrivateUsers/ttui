<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/base.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>创建商品图片 - 拓语网络</title>
    <link rel="stylesheet" href="${ctxsta}/common/icheck/flat/green.css"/>
    <%--引入上传组件CSS文件--%>
    <link rel="stylesheet" href="${ctxsta}/common/bootstrap-fileinput/css/fileinput.css">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>创建商品图片 <small> 广告位信息时应当遵循合法、正当、必要的原则，明示目的、方式和范围。</small></h5>
                    <div class="ibox-tools"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
                            class="close-link"><i class="fa fa-times"></i></a></div>
                   </div>
                   <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-sm-2 col-xs-offset-1 control-label">添加图片：</label>
                        <div class="col-sm-7">
                            <input  id="myFile" type="file" name="myFile" class="projectfile" value=""/>
                            <p class="help-block">注意：图片类型支持jpg、jpeg、png、gif格式，大小不超过2.0M</p>
                        </div>
                    </div>
                    <div class="ibox-content">
                    <form id="form" class="form-horizontal" action="${ctx}/product/detail/save/addImg" data-method="post">
                        <%--<input  id="picPath" type="hidden" name="picPath" value="${picPath}"/>--%>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">商品ID：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="productId"  value="${productId}" readonly="readonly">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">排序：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="sort">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>

                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">是否可用：</label>
                            <div class="col-sm-9">
                                <label class="radio-inline">
                                    <input type="radio" name="status" value="1" checked="checked">
                                    显示</label>
                                <label class="radio-inline">
                                    <input type="radio" name="status" value="0">
                                    隐藏</label>
                                <label class="radio-inline status-tip"><strong>提示：</strong> 状态</label>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-12 text-center">
                                <button id="sss" class="btn btn-primary" type="submit">保存</button>
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
    <script href="${ctxsta}/common/jquery/jquery-2.1.4.min.js"/>
    <script src="${ctxsta}/cms/js/productImageCreate.js"></script>
    <%--引入上传组件js文件--%>
    <script src="${ctxsta}/common/bootstrap-fileinput/js/fileinput.js"></script>
    <script src="${ctxsta}/common/bootstrap-fileinput/js/locales/zh.js"></script>
    <script type="text/javascript">
        $("#myFile").fileinput({
            language : 'zh',
            //路径

            uploadUrl : "${ctx}/product/detail/upload/image",
            autoReplace : true,
            maxFileCount : 1,
            allowedFileExtensions : [ "jpg", "png", "gif" ],
            browseClass : "btn btn-primary", //按钮样式
            previewFileIcon : "<i class='glyphicon glyphicon-king'></i>"
        }).on("fileuploaded",function(e,data){
            var res =data.response;
            debugger
            $("#picPath").val(res.success);
        })
    </script>
</myfooter>
</body>
</html>
