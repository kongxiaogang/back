<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head runat="server">
    <title>page</title>
<%--     <link href="<%=request.getContextPath()%>/resources/plugins/myPage/res/bootstrap.min.css" rel="stylesheet" type="text/css" /> --%>
<%--     <script src="<%=request.getContextPath()%>/resources/plugins/myPage/res/jquery-1.7.2.min.js" type="text/javascript"></script> --%>
    <script src="<%=request.getContextPath()%>/resources/plugins/myPage/res/jqPaginator.js" type="text/javascript"></script>
    <link href="<%=request.getContextPath()%>/resources/plugins/myPage/res/myPage.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
    function loadData(num) {
            $("#PageCount").val("${dataCount}");
            $("#PageSize").val("${pageSize}");
        }
//             $("#countindex").val("${pageCount}");
    </script>
<!-- </head> -->
<!-- <body> -->
	    <form id="searchForm" runat="server">
    <div>
    </div>
    <div>
    	<div id="pagination">
    	</div>
        <!-- <div style="text-align: center;">
        	<ul class="pagination" id="pagination"></ul>
        </div> -->
		<!-- 数据总数量 -->
        <input type="text" id="PageCount" name = "PageCount" runat="server" />
        <!-- 每页多少条数据 -->
        <input type="text" id="PageSize" name = "PageSize" runat="server" value="5" />
		<!-- 页面总数   -->
        <input type="text" id="countindex" name = "countindex" runat="server" value=""/>
        <!--设置最多显示的页码数 可以手动设置 默认为7-->
        <input type="hidden" id="visiblePages" name = "visiblePages" runat="server" value="10" />
    </div>
    <script src="<%=request.getContextPath()%>/resources/plugins/myPage/res/myPage.js" type="text/javascript"></script>
    </form>
</body>
</html>