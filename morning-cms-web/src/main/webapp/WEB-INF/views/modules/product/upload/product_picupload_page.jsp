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
                    <%--<input type="hidden" id="" value="${productId}">--%>
                   <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-sm-2 col-xs-offset-1 control-label">添加图片：</label>
                        <div class="col-sm-7">
                            <input  id="myFile" type="file" name="myFile" class="projectfile" value=""/>
                            <p class="help-block">注意：图片类型支持jpg、jpeg、png、gif格式，大小不超过2.0M</p>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <div class="col-sm-12 text-center">
                            <button id="btnClose" type="button" value="" onClick="shut_down()" />关闭页面</button>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<myfooter>
    <!-- 自定义js -->
    <script href="${ctxsta}/common/jquery/jquery-2.1.4.min.js"></script>
    <script src="${ctxsta}/cms/js/productImageCreate.js"></script>

    <script src="${ctxsta}/common/bootstrap-fileinput/js/fileinput.js"></script>
    <script src="${ctxsta}/common/bootstrap-fileinput/js/locales/zh.js"></script>
    <script type="text/javascript">
        $("#myFile").fileinput({
            language : 'zh',
            uploadUrl : "${ctx}/product/upload/addImg/uploadPic/${productId}/uploads",
            autoReplace : true,
            maxFileCount : 1,
            allowedFileExtensions : [ "jpg", "png", "gif" ],
            browseClass : "btn btn-primary", //按钮样式
            previewFileIcon : "<i class='glyphicon glyphicon-king'></i>"
        }).on("fileuploaded",function(e,data){
            var res =data.response;
            alert("上传已完成：",res.success);
            //window.opener.document.getElementById('picPath').value = path// 赋值
//            this.window.opener = null;
        })

        //关闭页面
 //       function shut_down(){
 //           if (confirm("您确定要关闭本页吗？")){
//                window.opener=null;
//                window.open('','_self');
 //               window.close();
 //           }
 //           else{}
  //      }

    </script>
</myfooter>
</body>
</html>
