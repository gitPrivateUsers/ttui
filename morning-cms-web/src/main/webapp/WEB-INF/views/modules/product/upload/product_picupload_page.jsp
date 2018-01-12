<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/base.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>上传图片窗口 - 拓语网络</title>
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

                   <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-sm-2 col-xs-offset-1 control-label">添加图片：</label>
                        <div class="col-sm-7">
                            <input  id="myFile" type="file" name="myFile" class="projectfile" value=""/>
                            <p class="help-block">注意：图片类型支持jpg、jpeg、png、gif格式，大小不超过2.0M</p>
                        </div>
                    </div>

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

            uploadUrl : "${ctx}/product/upload/addImg/uploadPic/uploads",
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
