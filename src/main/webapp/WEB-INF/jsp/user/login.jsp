<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>后台系统</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <%@ include file="../shared/importCss_login.jsp"%>
  <%@ include file="../shared/importJs_login.jsp"%>
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a href="#"><b>后台</b>系统</a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">登录</p>

    <form id="loginForm" action="<%=request.getContextPath()%>/user/login.do" method="post">
      <div class="form-group has-feedback">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
        <input type="text" class="form-control" placeholder="请输入用户名" name="userName">
      </div>
      <div class="form-group has-feedback ">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
        <input type="password" class="form-control" placeholder="请输入密码" name="userPwd">
      </div>
      <div class="form-group has-feedback">
      	<input id="captcha" name="captcha" type="text" class="form-control" placeholder="请输入验证码" />
        <img class="glyphicon " id="captchaImage"  style="position:absolute;right:0;" src="<%=request.getContextPath()%>/user/captcha.do"/>
      </div>
      <div id="errMessage" class="row" style="padding-left:20px;">
        <font color="red"><b>${errMessage}</b></font><br>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <label>
              <a href="#">忘记密码</a><br>
            </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
        </div>
        <!-- /.col -->
      </div>
    </form>
    
  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<script>

  $(function () {
	$("#loginForm").validate({
		rules : {
			userName:"required",
			userPwd:"required",
		},
		messages : {
			userName : '请输入用户名！',
			userPwd: '请输入密码！',
		}, 
		
	    submitHandler:function(form){
	    	form.submit();
	    }, 
	});
	$('#captchaImage').click(function() {
    	$('#captchaImage').attr("src", "<%=request.getContextPath()%>/user/captcha.do?timestamp=" + (new Date()).valueOf());
    }); 
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%'
    });
  });
</script>
</body>
</html>
