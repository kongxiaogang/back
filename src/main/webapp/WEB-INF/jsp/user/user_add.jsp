<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../shared/taglib.jsp"%>
<script src="<%=request.getContextPath()%>/resources/back/js/user.js"></script>
<div class="row">
  <div class="col-xs-12">
     <div class="box box-info">
      <div class="box-header with-border">
        <h3 class="box-title">用户添加</h3>
      </div>
      <!-- /.box-header -->
      <!-- form start -->
      <form id=userForm name="userForm" class="form-horizontal"  method="post" >
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
          <button type="button" class="btn btn-info pull-right" onclick="javascript:$('#userForm').submit();">提交</button>
        </div>
        <!-- /.box-footer -->
      </form>
    </div>
  </div>
  <!-- /.col -->
</div>
<!-- /.row -->
<script type="text/javascript">
	//返回
	function goBack(){
		window.history.back(-1); 
	}
</script>
