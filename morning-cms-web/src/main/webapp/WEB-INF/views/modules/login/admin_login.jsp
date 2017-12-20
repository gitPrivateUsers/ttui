<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<%@ include file="/WEB-INF/layouts/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>后台管理系统 | 拓语Language - 拓语科技，不二选择</title>
<meta name="author" content="拓语网络. - 拓语科技，不二选择。" />
<meta name="keywords" content="拓语商城,拓语网络,拓语网络商城,网络,电子商城,拓语社区,网络商城,在线购物,社会责任">
<meta name="description" content="拓语网络网络商城是中国网络性在线电子商城，以商城B2C模式运营的网络在线商城，是一家致力于将传统网络商城互联网化的创新网络商城。">
<link rel="shortcut icon" href="${ctximg}/default/ico/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="${ctxsta}/cms/css/login.css" />
<script> var t1 = new Date().getTime(); baselocation='${ctx}';</script>
</head>

<body>
<dl class="admin_login">
  <dt> <strong>拓语后台管理系统</strong> <em>Language Message</em> </dt>
  <dd class="user_icon">
    <input type="text" name="loginName" placeholder="账号" class="login_txtbx"/>
  </dd>
  <dd class="pwd_icon">
    <input type="password" name="loginPassword" placeholder="密码" class="login_txtbx"/>
  </dd>
  <dd class="val_icon">
    <div class="checkcode">
      <input type="text" id="J_codetext" name="registerCode" placeholder="验证码" maxlength="4" class="login_txtbx">
      <img class="J_codeimg" src="${ctx}/captcha-image.jpg" id="kaptchaImage" /> </div>
    <input type="button" value="点击,换一张" class="ver_btn" onclick="$(this).prev().find('img').click()">
  </dd>
  <dd>
    <input type="button" value="立即登陆" class="submit_btn"/>
  </dd>
  <dd>
    <p>© 2016 拓语商城 版权所有</p>
    <p><span id="showsectime"></span></p>
  </dd>
</dl>
<script src="${ctxsta}/common/jquery/jquery-3.2.0.min.js"></script> 
<script src="${ctxsta}/common/layer/layer.js"></script> 
<script src="${ctxsta}/common/particleground/js/jquery.particleground.min.js"></script> 
<script src="${ctxsta}/cms/js/login.js"></script>
</body>
</html>