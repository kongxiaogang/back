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
        <li><a href="#">权限管理</a></li>
        <li class="active">权限修改</li>
      </ol>
    </section>

	<!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
           <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">权限修改</h3>
            </div>
            <!-- /.box-header -->
            <!-- form start -->
            <form name="permissionform" class="form-horizontal" action="<%=request.getContextPath()%>/permission/editPermission.do" method="post" >
              <input name="perId" value="${permission.perId}" type="hidden" class="form-control" >
              <div class="box-body">
                <div class="form-group">
                  <label for="inputEmail3" class="col-sm-2 control-label">权限名称：</label>
                  <div class="col-sm-10">
                  	<input name="perName" value="${permission.perName}" type="text" class="form-control" placeholder="Enter ..." style="width: 70%;">
                  </div>
                </div>
                <div class="form-group">
                  <label for="inputEmail3" class="col-sm-2 control-label" >权限类型：</label>
                  <div class="col-sm-10">
	                  <select name="perType" class="form-control select2" style="width: 50%;">
	                    <option selected="selected" value="0">菜单</option>
	                    <option disabled="disabled" value="1">操作</option>
	                  </select>
                  </div>
                </div>
                <div class="form-group">
                  <label for="inputEmail3" class="col-sm-2 control-label">权限选择：</label>
                  <div class="col-sm-10">
	                  <select name="menuId" class="form-control select2" style="width: 50%;">
	                  	<c:forEach items="${childMenuList}" var="menu">
		                    <option value="${menu.menuId}" <c:if test="${permission.menuId==menu.menuId}">selected</c:if> >${menu.menuName}</option>
	                    </c:forEach>
	                   <!--  <option disabled="disabled">California (disabled)</option> -->
	                  </select>
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
	//删除用户
	function delPermission(permissionId,msg){
		bootbox.confirm("确定要删除["+msg+"]吗?", function(result) {
			if(result) {
				var url = "deletePermission.do?permissionID="+permissionId+"&tm="+new Date().getTime();
				$.get(url,function(data){
					
				});
			}
		});
	}
	//返回
	function goBack(){
		window.history.back(-1);
	}
</script>
<script>
  $(function () {
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
