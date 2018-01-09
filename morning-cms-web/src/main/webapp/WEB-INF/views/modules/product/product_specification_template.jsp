<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/base.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>商品类目信息- 拓语网络</title>
    <link rel="stylesheet" href="${ctxsta}/common/icheck/flat/green.css"/>
    <link rel="stylesheet" href="${ctxsta}/common/bootstrap/css/bootstrap.css" />
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>添加商品类目信息
                        <small> 添加商品信息时应当遵循合法、正当、必要的原则。</small>
                    </h5>
                    <div class="ibox-tools"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
                            class="close-link"><i class="fa fa-times"></i></a></div>
                </div>
                <div class="ibox-content">
                        <div id="treeviews"></div>
                        <div class="form-group">
                            <div class="col-sm-12 text-center">
                                <button class="btn btn-primary" type="submit">确定</button>
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
    <script src="${ctxsta}/cms/js/productSpecificationTemplate.js"></script>
    <script src="${ctxsta}/common/jquery/jquery-3.1.1.min.js"></script>
    <script src="${ctxsta}/common/bootstrap-treeview/js/bootstrap-treeview.js"></script>
    <script>
        $(function(){
            var defaultData =[{
                text: "Parent 1",
                nodes: [
                    {
                        text: "Child 1",
                        nodes: [
                            {
                                text: "Grandchild 1"
                            },
                            {
                                text: "Grandchild 2"
                            }
                        ]
                    },
                    {
                        text: "Child 2"
                    }
                ]
            },
                {
                    text: "Parent 2"
                },
                {
                    text: "Parent 3"
                },
                {
                    text: "Parent 4"
                },
                {
                    text: "Parent 5"
                }] ;
            $('#treeviews').treeview({
                expandIcon: "glyphicon glyphicon-stop",
                collapseIcon: "glyphicon glyphicon-unchecked",
                nodeIcon: "glyphicon glyphicon-user",
                color: "yellow",
                backColor: "purple",
                onhoverColor: "orange",
                borderColor: "red",
                showBorder: false,
                showTags: true,
                highlightSelected: true,
                selectedColor: "yellow",
                selectedBackColor: "darkorange",
                data:defaultData
            });
        })

    </script>
</myfooter>
</body>
</html>
