<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../shared/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AdminLTE 2 | 星河财富</title>
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <%@ include file="../shared/importCss.jsp"%>
  <%@ include file="../shared/importJs.jsp"%>
  <script src="<%=request.getContextPath()%>/resources/xhcf/js/user.js"></script>
</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
  <%@ include file="../shared/pageHeader.jsp"%>
  <!-- Left side column. contains the logo and sidebar -->
  <%@ include file="../shared/sidebarMenu.jsp"%>
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        <small></small>
      </h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> 系统管理</a></li>
        <li><a href="#">用户管理</a></li>
        <li class="active">修改密码</li>
      </ol>
    </section>

	<!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
           <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">修改密码</h3>
            </div>
            <!-- /.box-header -->
            <!-- form start -->
            <form id="userform" name="userform" class="form-horizontal" action="<%=request.getContextPath()%>/user/modifyPasswd.do"  method="post" >
              <div class="box-body">
                <div class="form-group">
                  <label for="oldPwd" class="col-sm-2 control-label">原密码：</label>
                  <div class="col-sm-10">
                    <input id="oldPwd" name="oldPwd" type="password" class="form-control" onBlur="checkPassword(this.value)"  placeholder="请输入原密码" style="width: 70%;" >
                  </div>
                </div>
                <div class="form-group">
                  <label for="newPwd" class="col-sm-2 control-label">新密码：</label>
                  <div class="col-sm-10">
                    <input id="newPwd" name="newPwd" type="password" class="form-control" placeholder="请输入新密码" style="width: 70%;">
                  </div>
                </div>
                <div class="form-group">
                  <label for="confirmNewPwd" class="col-sm-2 control-label">确认密码：</label>
                  <div class="col-sm-10">
                    <input id="confirmNewPwd" name="confirmNewPwd" type="password" class="form-control" placeholder="请输入新密码" style="width: 70%;">
                  </div>
                </div>
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="button" class="btn btn-default pull-right " onclick="goBack()">返回</button>
                <button type="submit" class="btn btn-info pull-right">提交</button>
              </div>
              <!-- /.box-footer -->
            </form>
          </div>
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </section>
    <!-- /.content -->
   
  </div>
  <!-- /.content-wrapper -->
  <%@ include file="../shared/pageFooter.jsp"%>

  <%@ include file="../shared/controlSidebar.jsp"%>
</div>
<script type="text/javascript">
	var message = '<%=(String)request.getParameter("message")%>'
	if(message!==null&&message!=='null'&&message!=='') {
		bootbox.alert(message);
	}
	//返回
	function goBack(){
		window.history.back(-1); 
	}
	//校验密码
	function checkPassword(oldPwd) {
		if(oldPwd.length>=6) {
			$.ajax({
	            type: "POST",
	            url: "<%=request.getContextPath()%>/user/checkPassword.do",
	            data: {oldPwd:oldPwd},
	            dataType: "json",
	            success: function(data){
	                if(data.code!=0) {
	                	$('#userform').data('bootstrapValidator').updateStatus('oldPwd', 'NOT_VALIDATED');
	                	bootbox.alert(data.msg);
	                }
	            }
	        });
		}
	}
</script>
</body>
</html>
