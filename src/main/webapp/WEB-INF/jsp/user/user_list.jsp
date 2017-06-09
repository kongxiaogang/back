<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../shared/taglib.jsp"%>
<div class="row">
  <div class="col-xs-12">
    <div class="box">
      <div class="box-header">
     <form id="queryForm" name="queryForm" class="form-horizontal" method="post" action="<%=request.getContextPath()%>/user/showUserList.do">
        <h3 class="box-title">用户列表</h3>
        <div class="box-tools pull-right">
          <div class="input-group input-group-sm" >
            <label for="inputEmail3" class="col-sm-2 control-label">电话：</label>
            <div class="col-sm-3">
            	<input type="text" name="userTel" value="${queryCondition.userTel }" class="form-control input-sm " style="width: 180px;" placeholder="">
            </div>
            <label for="inputEmail2" class="col-sm-3 control-label">用户名：</label>
            <div class="col-sm-3	">
            	<input type="text" name="userName" class="form-control input-sm " style="width: 180px;" placeholder="">
            </div>
            <div class="input-group-btn">
            	<button type="button" class="btn btn-default " onclick="">查询</button>
            </div>
            <div class="input-group-btn">
              <button type="button" class="btn btn-default btn-info" onclick="back.common.addModel('/user/showUserAdd.do')">增加</button>
            </div>
          </div>
        </div> 
     </form>
      </div>
      <!-- /.box-header -->
      <div class="box-body">
        <table id="dataTable" class="table table-bordered table-hover" style="font-size:14px">
          <thead>
          <tr>
            <th>序号</th>
            <th>用户名</th>
            <th>邮箱</th>
            <th>电话</th>
            <th>状态</th>
            <th>用户角色</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody id="usertablebody">
           <c:forEach items="${page.list}" var="user" varStatus="status">
               <tr>
               	<td>${page.startRow+status.index}</td>
                   <td>${user.userName}</td>
                   <td>${user.userEmail}</td>
             		<td>${user.userTelephone}</td>
             		<td>${user.userStatus==0?'正常':user.userStatus==1?'冻结':'未知'}</td>
             		<td>${user.roleName}</td>
             		<td>
             			<button class="btn btn-xs btn-info" onclick="editUser('${user.userId }','${user.userName }');">修改</button>
             			<button class="btn btn-xs btn-info" onclick="delUser('${user.userId }','${user.userName }');">删除</button>
             		</td>
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
<script type="text/javascript">
	//提交
	function gotoPage(pageNum,pageSize){
		$("#queryForm").append('<input value="'+pageNum+'" id="pageNum" type="hidden" name="pageNum"/>');  
		$("#queryForm").append('<input value="'+pageSize+'" id="pageSize" type="hidden" name="pageSize"/>');
		back.common.search("queryForm","/user/showUserList.do");
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

</body>
</html>
