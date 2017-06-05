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
        <li class="active">权限管理</li>
      </ol>
    </section>

	<!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
           <form id="queryForm" name="queryForm" class="form-horizontal" method="post" >
              <h3 class="box-title">权限管理</h3>
              <div class="box-tools pull-right">
                <div class="input-group input-group-sm" >
                  <div class="input-group-btn">
                    <button type="button" class="btn btn-default btn-info" onclick="addPermission('');">增加</button>
                  </div>
                </div>
              </div> 
           </form>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="dataTable" class="table tree table-bordered table-hover">
                <thead>
                <tr>
                  <th>序号</th>
                  <th>权限名称</th>
                  <th>权限类型</th>
                  <th>操作</th>
                </tr>
                </thead>
                <tbody>
	                <c:forEach items="${page.list}" var="permission" varStatus="status">
	                    <tr class="treegrid-${permission.menuId} <c:if test="${permission.level != 1}">treegrid-parent-${permission.parentId}</c:if>">
	                    	<td>${page.startRow+status.index+1}</td>
	                        <td>${permission.perName}</td>
	                        <td>${permission.perType==0?'菜单':permission.perType==1?'操作':'未知'}</td>
	                  		<th>
	                  			<c:if test="${permission.leafNode ==0 }">
	                  				<button class="btn btn-xs btn-info" onclick="addPermission('${permission.menuId }');">添加下级</button>
	                  			</c:if>
	                  			<button class="btn btn-xs btn-info" onclick="editPermission('${permission.perId }','${permission.menuId }');">修改</button>
	                  			<button class="btn btn-xs btn-info" onclick="delPermission('${permission.perId }','${permission.perName }');">删除</button>
	                  		</th>
	                    </tr>
	                </c:forEach>
                </tbody>
              </table>
              <center>
           		<div id="paging" class = "container">
       				<ul class = "pagination">
       					<c:if test="${page.hasPreviousPage==true}">
       						<li><a onClick='gotoPage(${page.prePage},${page.pageSize })' >上一页</a></li>
       					</c:if>
       					<c:if test="${page.hasPreviousPage==false}">
       						<li class="disabled"><a href="javascript:void(0)">上一页</a></li>
       					</c:if>
       					<c:forEach items="${page.navigatepageNums}" var="nav">
       						<c:if test="${nav == page.pageNum}">
        						<li class="active"><a onClick='gotoPage(${nav},${page.pageSize })' >${nav}</a></li>
        					</c:if>
        					<c:if test="${nav != page.pageNum}">
        						<li><a onClick='gotoPage(${nav},${page.pageSize })' >${nav}</a></li>
        					</c:if>
       					</c:forEach>
       					<c:if test="${page.hasNextPage==true}">
       						<li><a onClick='gotoPage(${page.nextPage},${page.pageSize })' >下一页</a></li>
       					</c:if>
       					<c:if test="${page.hasNextPage==false}">
       						<li class="disabled"><a href="javascript:void(0)">下一页</a></li>
       					</c:if>
       				</ul>
           		</div>
              </center>
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
	//提交
	function gotoPage(pageNum,pageSize){
		$("#queryForm").append('<input value="'+pageNum+'" id="pageNum" type="hidden" name="pageNum"/>');  
		$("#queryForm").append('<input value="'+pageSize+'" id="pageSize" type="hidden" name="pageSize"/>');
	    this.document.queryForm.submit();
	}
	//删除权限
	function delPermission(permissionId,msg){
		bootbox.confirm("确定要删除["+msg+"]吗?", function(result) {
			if(result) {
				window.location.href = "<%=request.getContextPath()%>"+"/permission/deletePermission.do?id="+permissionId+"&tm="+new Date().getTime();
			}
		});
	}
	//更新权限
	function editPermission(permissionId,menuId){
		window.location.href = "<%=request.getContextPath()%>"+"/permission/showPermissionEdit.do?id="+permissionId+"&menuId="+menuId+"&tm="+new Date().getTime();
	}
	//添加权限
	function addPermission(parentId){
		window.location.href = "<%=request.getContextPath()%>"+"/permission/showPermissionAdd.do?parentId="+parentId+"&tm="+new Date().getTime();
	}
</script>
<script>
  $(function () {
    $('.tree').treegrid({
	  expanderExpandedClass: 'glyphicon glyphicon-minus',
	  expanderCollapsedClass: 'glyphicon glyphicon-plus',
	  'initialState': 'collapsed',
	  treeColumn: 1
	});
    $('#dataTable').DataTable({
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
