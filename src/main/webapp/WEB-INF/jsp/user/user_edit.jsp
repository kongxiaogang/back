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
        <li><a href="#"> 用户管理</a></li>
        <li class="active">用户修改</li>
      </ol>
    </section>

	<!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
           <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">用户修改</h3>
            </div>
            <!-- /.box-header -->
            <!-- form start -->
            <form id="userform" name="userform" class="form-horizontal" action="<%=request.getContextPath()%>/user/editUser.do" method="post" >
              <input name="userId" value="${user.userId}" type="hidden" class="form-control" >
              <div class="box-body">
                <div class="form-group">
                  <label for="roleId" class="col-sm-2 control-label">用户角色：</label>
                  <div class="col-sm-10">
	                  <select id="roleId" name="roleId" class="form-control select2" style="width: 50%;">
	                  	<c:forEach items="${rolelist}" var="role">
		                    <option value="${role.roleId}" <c:if test="${user.roleId==role.roleId}">selected</c:if>  >${role.roleName}</option>
	                    </c:forEach>
	                   <!--  <option disabled="disabled">California (disabled)</option> -->
	                  </select>
                  </div>
                </div>
                <div class="form-group">
                  <label for="userName" class="col-sm-2 control-label">用户名：</label>
                  <div class="col-sm-10">
                  	<input id="userName" name="userName" value="${user.userName}" type="text" class="form-control" placeholder="Enter ..." style="width: 70%;">
                  </div>
                </div>
                <div class="form-group">
                  <label for="userPwd" class="col-sm-2 control-label">密码：</label>
                  <div class="col-sm-10">
                    <input id="userPwd" name="userPwd" value="${user.userPwd}" type="password" class="form-control"  placeholder="Password" style="width: 70%;">
                  </div>
                </div>
                <div class="form-group">
                  <label for="userEmail" class="col-sm-2 control-label">邮箱：</label>
                  <div class="col-sm-10">
                    <input id="userEmail" name="userEmail" value="${user.userEmail}" type="email" class="form-control"  placeholder="Email" style="width: 70%;">
                  </div>
                </div>
                <div class="form-group">
                  <label for="userStatus" class="col-sm-2 control-label">状态：</label>
                  <div class="col-sm-10">
	                  <select id="userStatus" name="userStatus" class="form-control select2" style="width: 50%;">
	                    <option selected="selected" value="0">正常</option>
	                    <option value="1">冻结</option>
	                   <!--  <option disabled="disabled">California (disabled)</option> -->
	                  </select>
                  </div>
                </div>
                <div class="form-group">
                  <label for="userTelephone" class="col-sm-2 control-label">用户电话：</label>
                  <div class="col-sm-10">
                    <input id="userTelephone" name="userTelephone" value="${user.userTelephone}" type="text" class="form-control"  placeholder="Enter ..." style="width: 70%;">
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
	//返回
	function goBack(){
		window.history.back(-1); 
	}
</script>
<script>
  $(function () {
	  $("#userform").validate({
	  		rules : {
	  			roleId : "required",
	  			userId : "required",
	  			userName:"required",
	  			userPwd:"required",
	  			userEmail:{
	  				required: true,
	  				email:true
	  			},
	  			userStatus:"required",
	  			userTelephone : {
	  				required: true,
	  			 	number:true,
					maxlength : 20
				}
			},
			
			messages : {
				userId : '请输入用户编码！',
				userTelephone : {
					maxlength : '输入的字符不能超过20个字符！'
				}
			},
	        submitHandler:function(form){
	        	form.submit();
	        },
	        errorElement : "em"
		});
	    $('#example2').DataTable({
	      "paging": false,
	      "bFilter": false,
	      "bSort": false,
	      "info": false,
	      "sInfo":false
	    });
  });
</script>
</body>
</html>
