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
        <li class="active">角色管理</li>
      </ol>
    </section>

	<!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              <h3 class="box-title">角色列表</h3>
              <div class="box-tools">
                <div class="input-group input-group-sm" style="width: 300px;">
                  <input type="text" name="table_search" class="form-control pull-right" placeholder="Search">
                  <div class="input-group-btn">
                    <button type="submit" class="btn btn-default"><i class="fa fa-search"></i></button>
                    <button class="btn btn-default" onclick="">查询</button>
                  </div>
                  <div class="input-group-btn">
                    <button class="btn btn-default" onclick="addRole();">增加</button>
                  </div>
                </div>
              </div> 
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example2" class="table table-bordered table-hover">
                <thead>
                <tr>
                  <th>序号</th>
                  <th>角色名称</th>
                  <th>角色描述</th>
                </tr>
                </thead>
                <tbody>
	                <c:forEach items="${page.list}" var="role" varStatus="status">
	                    <tr>
	                    	<td>${page.startRow+status.index}</td>
	                        <td>${role.roleName}</td>
	                        <td>${role.roleDes}</td>
	                  		<th>
	                  			<button class="btn btn-xs btn-info" onclick="editRole('${role.roleId }','${role.roleDes }');">修改</button>
	                  			<button class="btn btn-xs btn-info" onclick="delRole('${role.roleId }','${role.roleDes }');">删除</button>
	                  		</th>
	                    </tr>
	                </c:forEach>
                </tbody>
              </table>
              <table class="gridtable" style="width:50%;text-align: right;">
               <tr>
                   <c:if test="${page.hasPreviousPage}">
                       <td><a href="showRoleList.do?pageNum=${page.prePage}&pageSize=${page.pageSize}">前一页</a></td>
                   </c:if>
                   <c:forEach items="${page.navigatepageNums}" var="nav">
                       <c:if test="${nav == page.pageNum}">
                           <td style="font-weight: bold;">${nav}</td>
                       </c:if>
                       <c:if test="${nav != page.pageNum}">
                           <td><a href="showRoleList.do?pageNum=${nav}&pageSize=${page.pageSize}">${nav}</a></td>
                       </c:if>
                   </c:forEach>
                   <c:if test="${page.hasNextPage}">
                       <td><a href="showRoleList.do?pageNum=${page.nextPage}&pageSize=${page.pageSize}">下一页</a></td>
                   </c:if>
               </tr>
            </table>
            </div>
          </div>
          <!-- /.box -->
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
	//删除角色
	function delRole(roleId,msg){
		bootbox.confirm("确定要删除["+msg+"]吗?", function(result) {
			if(result) {
				window.location.href = "<%=request.getContextPath()%>"+"/role/deleteRole.do?id="+roleId+"&tm="+new Date().getTime();
			}
		});
	}
	//更新角色
	function editRole(id,msg){
		window.location.href = "<%=request.getContextPath()%>"+"/role/showRoleEdit.do?id="+id+"&tm="+new Date().getTime();
	}
	//添加角色
	function addRole(){
		window.location.href = "<%=request.getContextPath()%>"+"/role/showRoleAdd.do";
	}
</script>
<script>
  $(function () {
	var message = '<%=(String)request.getParameter("message")%>'
	if(message!==null&&message!=='null'&&message!=='') {
		swal(message);
	}
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
