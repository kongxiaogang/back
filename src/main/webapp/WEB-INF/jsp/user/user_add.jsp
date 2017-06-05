<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../shared/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>星河财富</title>
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
        <li class="active">用户添加</li>
      </ol>
    </section>

	<!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
           <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">用户添加</h3>
            </div>
            <!-- /.box-header -->
            <!-- form start -->
            <form id="userform" name="userform" class="form-horizontal" action="<%=request.getContextPath()%>/user/addUser.do"  method="post" >
              <div class="box-body">
              	 <div class="form-group">
                  <label for="roleId" class="col-sm-2 control-label">用户角色：</label>
                  <div class="col-sm-5">
	                  <select id="roleId" name="roleId" class="form-control select2" style="width: 90%;">
	                  	<c:forEach items="${rolelist}" var="role">
		                    <option value="${role.roleId}">${role.roleName}</option>
	                    </c:forEach>
	                   <!--  <option disabled="disabled">California (disabled)</option> -->
	                  </select>
                  </div>
                </div>
                <div class="form-group">
                  <label for="userName" class="col-sm-2 control-label">用户名：</label>
                  <div class="col-sm-6">
                  	<input id="userName" name="userName" type="text" class="form-control" placeholder="Enter ..." >
                  </div>
                </div>
                <div class="form-group">
                  <label for="userPwd" class="col-sm-2 control-label">密码：</label>
                  <div class="col-sm-6">
                    <input id="userPwd" name="userPwd" type="password" class="form-control" id="inputPassword3" placeholder="Password" >
                  </div>
                </div>
                <div class="form-group">
                  <label for="userEmail" class="col-sm-2 control-label">邮箱：</label>
                  <div class="col-sm-6">
                    <input id="userEmail" name="userEmail" type="email" class="form-control" id="inputEmail3" placeholder="Email" >
                  </div>
                </div>
                <div class="form-group">
                  <label for="userStatus" class="col-sm-2 control-label">状态：</label>
                  <div class="col-sm-5">
	                  <select id="userStatus" name="userStatus" class="form-control select2" style="width: 90%;">
	                    <option selected="selected" value="0">正常</option>
	                    <option value="1">冻结</option>
	                   <!--  <option disabled="disabled">California (disabled)</option> -->
	                  </select>
                  </div>
                </div>
                <div class="form-group">
                  <label for="userTelephone" class="col-sm-2 control-label">用户电话：</label>
                  <div class="col-sm-6">
                    <input id="userTelephone" name="userTelephone" type="text" class="form-control" id="inputEmail3" placeholder="Enter ..." >
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
</script>
</body>
</html>
