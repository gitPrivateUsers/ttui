<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" %>
<%@ include file="/WEB-INF/layouts/base.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>修改用户信息- 拓语网络</title>
    <link rel="stylesheet" href="${ctxsta}/common/icheck/flat/green.css"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>修改用户信息
                        <small> 广告位信息时应当遵循合法、正当、必要的原则，明示目的、方式和范围。</small>
                    </h5>
                    <div class="ibox-tools"><a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a
                            class="close-link"><i class="fa fa-times"></i></a></div>
                </div>
                <div class="ibox-content">
                    <form id="form" class="form-horizontal" action="${ctx}/customer/detail/${customer.userId}"
                          data-method="put">

                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">昵称：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="userName" value="${customer.userName}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <%--<div class="form-group m-t">--%>
                            <%--<label class="col-sm-2 col-xs-offset-1 control-label">登录密码：</label>--%>
                            <%--<div class="col-sm-7">--%>
                                <%--<input type="text" class="form-control" name="userName" value="${customer.loginPassword}">--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">真实姓名：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="realName" value="${customer.realName}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">性别：</label>
                            <div class="col-sm-9">
                                <label class="radio-inline">
                                    <input type="radio" class="js-switch" name="sex" value="0" ${customer.sex}/>
                                    保密</label>
                                <label class="radio-inline">
                                    <input type="radio" class="js-switch" name="sex" value="1" ${customer.sex}/>
                                    男</label>
                                <label class="radio-inline">
                                    <input type="radio" class="js-switch" name="sex" value="2" ${customer.sex}/>
                                    女</label>
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">年龄：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="age" value="${customer.age}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">电子邮箱：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="email" value="${customer.email}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group m-t">
                            <label class="col-sm-2 col-xs-offset-1 control-label">手机号码：</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" name="telephone" value="${customer.telephone}">
                            </div>
                        </div>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 col-xs-offset-1 control-label">状态[是否冻结]：</label>
                            <div class="col-sm-9">
                                <label class="radio-inline">
                                    <input type="checkbox" class="js-switch" name="status" value="1" ${customer.status eq '1'?'checked="checked"':''}/>
                                    是</label>
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
    <script src="${ctxsta}/cms/js/customerList.js"></script>
</myfooter>
</body>
</html>
