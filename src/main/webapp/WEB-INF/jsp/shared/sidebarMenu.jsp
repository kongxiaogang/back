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
        <p>${sessionScope.userAuth.userName}</p>
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
      <li class="header">星河财富</li>
      <!-- Optionally, you can add icons to the links -->
      <c:forEach items="${sessionScope.userAuth.authorityMenus}" var="menu" varStatus="status">
      <%-- <c:forEach items="${authorityMenus}" var="menu" varStatus="status"> --%>
		<c:choose>
	    <c:when test="${status.first && empty sessionScope.currentMenu }">
	    	<li id="${menu.menuId}" class="treeview ">
	    </c:when>
		<c:when test="${not empty sessionScope.currentMenu && sessionScope.currentMenu.rootId == menu.menuId }">  
	    	<li id="${menu.menuId}" class="treeview active">
	    </c:when>
	    <c:otherwise>  
	        <li id="${menu.menuId}" class="treeview ">
	    </c:otherwise>  
		</c:choose>	
      		<a href="#"><i class="fa fa-link"></i> <span>${menu.menuName}</span>
      			<span class="pull-right-container">
            		<i class="fa fa-angle-left pull-right"></i>
          		</span>
          	</a>
      	  	<ul class="treeview-menu ">
      	  		<c:forEach items="${menu.childrens}" var="child">
      	  			<c:choose>
          			<c:when test="${not empty sessionScope.currentMenu && sessionScope.currentMenu.secondId eq child.menuId}">  
          				<li class="active" id="${child.menuId }">
          			</c:when>
          			<c:otherwise>  
		            	<li >
		        	</c:otherwise>
		         	</c:choose>
		         		<a href="${child.pageUrl}"><i class="fa fa-circle-o"></i>${child.menuName}
		         			<c:if test="${not empty child.childrens }">
			         			<span class="pull-right-container">
	                  				<i class="fa fa-angle-left pull-right"></i>
	                			</span>
                			</c:if>
		         		</a>
		         		<c:if test="${not empty child.childrens }">
		         		<ul class="treeview-menu">
				         	<c:forEach items="${child.childrens}" var="grandson">
				         		<c:choose>
			          			<c:when test="${not empty sessionScope.currentMenu && sessionScope.currentMenu.thirdId eq grandson.menuId}">  
			          				<li class="active" id="${grandson.menuId }">
			          			</c:when>
			          			<c:otherwise>  
					            	<li >
					        	</c:otherwise>
					         	</c:choose>
					         		<a href="${grandson.pageUrl}"><i class="fa fa-circle-o"></i>${grandson.menuName}
					         		</a>
					         	</li>
				         	</c:forEach>
			         	</ul>
			         	</c:if>
		         	</li>
      	  		</c:forEach>
        	</ul>
      	</li>
      </c:forEach>
    </ul>
    <!-- /.sidebar-menu -->
  </section>
  <!-- /.sidebar -->
</aside>