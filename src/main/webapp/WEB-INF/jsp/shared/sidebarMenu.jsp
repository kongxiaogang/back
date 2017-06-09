<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<aside class="main-sidebar">
  <!-- sidebar: style can be found in sidebar.less -->
  <section class="sidebar">

    <!-- Sidebar user panel (optional) -->
    <div class="user-panel">
      <div class="pull-left image">
        <img src="<%=request.getContextPath()%>/resources/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
      </div>
      <div class="pull-left info">
        <p>admin</p>
        <!-- Status -->
        <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
      </div>
    </div>

    <!-- search form (Optional) -->
    <form action="#" method="get" class="sidebar-form">
      <div class="input-group">
        <input type="text" name="q" class="form-control" placeholder="Search...">
            <span class="input-group-btn">
              <button type="submit" name="search" id="search-btn" class="btn btn-flat"><i class="fa fa-search"></i>
              </button>
            </span>
      </div>
    </form>
    <!-- /.search form -->

    <!-- Sidebar Menu -->
    <ul class="sidebar-menu">
      <!-- Optionally, you can add icons to the links -->
      <c:forEach items="${menuList}" var="menu" varStatus="status">
      <%-- <c:forEach items="${authorityMenus}" var="menu" varStatus="status"> --%>
      	<!-- 一级 -->
       	<li id="${menu.menuId}" class="treeview " level="level1" >
      		<a href="javascript:void(0)" <c:if test="${fn:length(menu.pageUrl) gt 0 }"> nav-menu="${menu.menuName },${menu.pageUrl }"</c:if>>
      			<i class="fa fa-link"></i> 
      			<span>${menu.menuName}</span>
      			<span class="pull-right-container">
            		<i class="fa fa-angle-left pull-right"></i>
          		</span>
          	</a>
          	<c:if test="${not empty menu.childrens }">
	      	  	<ul class="treeview-menu ">
	      	  		<c:forEach items="${menu.childrens}" var="child">
	      	  			<!-- 二级 -->
	          			<li class="active" id="${child.menuId }" level="level2" >
			         		<a href="javascript:void(0)" <c:if test="${fn:length(child.pageUrl) gt 0 }">nav-menu="${menu.menuName },${child.menuName },${child.pageUrl }"</c:if>>
			         			<i class="fa fa-circle-o"></i>${child.menuName}
			         			<c:if test="${not empty child.childrens }">
				         			<span class="pull-right-container">
		                  				<i class="fa fa-angle-left pull-right"></i>
		                			</span>
	                			</c:if>
			         		</a>
			         		<c:if test="${not empty child.childrens }">
				         		<ul class="treeview-menu">
						         	<c:forEach items="${child.childrens}" var="grandson">
					          			<li class="active" id="${grandson.menuId }" level="level2" >
							         		<a href="javascript:void(0)" <c:if test="${fn:length(grandson.pageUrl) gt 0 }">nav-menu="${menu.menuName },${child.menuName },${grandson.menuName },${grandson.pageUrl }"</c:if>>
							         			<i class="fa fa-circle-o"></i>${grandson.menuName}
							         		</a>
							         	</li>
						         	</c:forEach>
					         	</ul>
				         	</c:if>
			         	</li>
	      	  		</c:forEach>
	        	</ul>
        	</c:if>
      	</li>
      </c:forEach>
    </ul>
    <!-- /.sidebar-menu -->
  </section>
  <!-- /.sidebar -->
</aside>