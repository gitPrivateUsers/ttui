<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<title>创建商品 - 拓语网络</title>
<link rel="stylesheet" href="${ctxsta}/common/icheck/flat/green.css" />
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content">
  <div class="row">
    <div class="col-sm-12">
      <div class="ibox float-e-margins">
        <div class="ibox-title">
          <h5>创建商品</h5>
          <div class="ibox-tools"> <a class="collapse-link"><i class="fa fa-chevron-up"></i></a> <a class="close-link"><i class="fa fa-times"></i></a> </div>
        </div>
        <div class="ibox-content">
          <form id="form" class="form-horizontal" action="${ctx}/product/detail" data-method="post">
            <div class="form-group m-t">
              <label class="col-sm-2 col-xs-offset-1 control-label">商品名称：</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="name">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label">商品显示价格：</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="showPrice">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label">商品简介：</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="introduce">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label">设置：</label>
              <div class="col-sm-9">
                <label class="radio-inline">
                  <input type="checkbox" class="js-switch" name="showInTop" value="1" ${product.showInTop eq '1'?'checked="checked"':''}/>
                  是否置顶</label>
                <label class="radio-inline">
                  <input type="checkbox" class="js-switch" name="showInNav" value="1" ${product.showInNav eq '1'?'checked="checked"':''}/>
                  是否导航</label>
                <label class="radio-inline">
                  <input type="checkbox" class="js-switch" name="showInHot" value="1" ${product.showInHot eq '1'?'checked="checked"':''} />
                  是否热门</label>
                <label class="radio-inline">
                  <input type="checkbox" class="js-switch" name="showInShelve" value="1" ${product.showInShelve eq '1'?'checked="checked"':''} />
                  是否上架</label>
              </div>
            </div>
            <div class="hr-line-dashed"></div>    
            <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label">商品编码：</label>
              <div class="col-sm-7">
                <input type="text" class="form-control" name="productNumber">
              </div>
            </div>
            <div class="hr-line-dashed"></div>            
            <div class="form-group">
              <label class="col-sm-2 col-xs-offset-1 control-label">图片：</label>
              <div class="col-sm-7">
                <input type="text"   class="form-control" name="picImg">
              </div>
            </div>
            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-sm-2  col-xs-offset-1 control-label">搜索关键词：</label>
              <div class="col-sm-7">
                <textarea class="form-control" rows="2" name="searchKey" placeholder="请输入搜索关键词..."></textarea>
              </div>
            </div>


            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-sm-2  col-xs-offset-1 control-label">页面标题：</label>
              <div class="col-sm-7">
                <textarea class="form-control" rows="2" name="pageTitle" placeholder="请输入页面标题..."></textarea>
              </div>
            </div>



            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-sm-2  col-xs-offset-1 control-label">页面关键词：</label>
              <div class="col-sm-7">
                <textarea class="form-control" rows="2" name="pageKeyword" placeholder="请输入页面关键词..."></textarea>
              </div>
            </div>


            <div class="hr-line-dashed"></div>
            <div class="form-group">
              <label class="col-sm-2  col-xs-offset-1 control-label">页面描述：</label>
              <div class="col-sm-7">
                <textarea class="form-control" rows="2" name="pageDescription" placeholder="请输入页面描述..."></textarea>
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
  <script src="${ctxsta}/cms/js/productCreate.js"></script>
</myfooter>
</body>
</html>
