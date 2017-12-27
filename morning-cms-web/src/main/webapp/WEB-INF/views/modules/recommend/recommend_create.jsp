<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>创建推荐- 拓语网络</title>
<link rel="stylesheet" href="${ctxsta}/common/icheck/flat/green.css" />
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
  <div class="row">
    <div class="col-sm-12">
      <div class="ibox float-e-margins">
        <div class="ibox-title">
          <h5>创建推荐</h5>
          <div class="ibox-tools"> <a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a class="close-link"><i class="fa fa-times"></i></a> </div>
        </div>
        <div class="ibox-content">
          <form id="form" class="form-horizontal" action="${ctx}/product/recommend" data-method="post">
            <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">推荐位ID：</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="recommendId">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">商品ID：</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="productId">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">排序：</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="sort">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label">状态：</label>
              <div class="col-sm-9">
                <label class="radio-inline">
                  <input type="radio" class="js-switch" name="status" value="0" ${productRecommend.status}/>
                  隐藏</label>
                <label class="radio-inline">
                  <input type="radio" class="js-switch" name="status" value="1" ${productRecommend.status}/>
                  显示</label>
              </div>
            </div>
            <div class="hr-line-dashed"></div>
              <div class="form-group">
                <label class="col-sm-2 col-xs-offset-1 control-label">开始时间：</label>
                  <div class='input-group date col-sm-4' id='datetimepicker1'>
                    <input type='text' class="form-control" />
                  <span class="input-group-addon">
                      <span class="glyphicon glyphicon-calendar"></span>
                  </span>
                  </div>
              </div>
              <div class="hr-line-dashed"></div>
              <div class="form-group">
                <label class="col-sm-2 col-xs-offset-1 control-label">结束时间：</label>
                  <!--指定 date标记-->
                  <div class='input-group date col-sm-4' id='datetimepicker2'>
                    <input type='text' class="form-control" />
                  <span class="input-group-addon">
                      <span class="glyphicon glyphicon-calendar"></span>
                  </span>
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
  <script src="${ctxsta}/cms/js/recommendCreate.js"></script>
</myfooter>
</body>
</html>
