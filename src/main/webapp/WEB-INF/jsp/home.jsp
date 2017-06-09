<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="shared/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AdminLTE 2 | 星河财富</title>
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <%@ include file="shared/importCss.jsp"%>
  <link rel="stylesheet" href="dist/css/skins/skin-blue.min.css">
  <%@ include file="shared/importJs.jsp"%>
  <script src="${ctx}/resources/back/common.js"></script>
<script type="text/javascript">
	$(function() {
		back.index.menu.initMenuEvent();
	});
</script>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
  <%@ include file="shared/pageHeader.jsp"%>
  <!-- Left side column. contains the logo and sidebar -->
  <%@ include file="shared/sidebarMenu.jsp"%>
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1 style="height:26px">
        <small></small>
      </h1>
      <ol class="breadcrumb">
      </ol>
    </section>
	<!-- Main content -->
    <section class="content">

    </section>
    <!-- /.content -->
   
  </div>
  <!-- /.content-wrapper -->
  <%@ include file="shared/pageFooter.jsp"%>

  <%@ include file="shared/controlSidebar.jsp"%>
</div>
<script type="text/javascript">
	//提交
	function gotoPage(pageNum,pageSize){
		$("#queryForm").append('<input value="'+pageNum+'" id="pageNum" type="hidden" name="pageNum"/>');  
		$("#queryForm").append('<input value="'+pageSize+'" id="pageSize" type="hidden" name="pageSize"/>');
	    this.document.queryForm.submit();
	}
	//删除用户
	function delUser(userId,msg){
		bootbox.confirm("确定要删除["+msg+"]吗?", function(result) {
			if(result) {
				window.location.href = "<%=request.getContextPath()%>"+"/user/deleteUser.do?userId="+userId+"&tm="+new Date().getTime();
			}
		});
	}
	//更新用户
	function editUser(userId,msg){
		window.location.href = "<%=request.getContextPath()%>"+"/user/showUserEdit.do?userId="+userId+"&tm="+new Date().getTime();
	}
	//添加用户
	function addUser(){
		window.location.href = "<%=request.getContextPath()%>"+"/user/showUserAdd.do";
	}
</script>
<script>
  $(function () {
	var message = '<%=(String)request.getParameter("message")%>'
	if(message!==null&&message!=='null'&&message!=='') {
		bootbox.alert(message);
	}
    $('#dataTable').DataTable({
   	  "scrollX": true,
   	  "bScrollCollapse": true,
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
