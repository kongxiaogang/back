<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width, initial-scale=1">
<title>demo</title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/plugins/page/js/bootstrap.min.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/plugins/page/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/plugins/page/js/jqPaginator.js"></script>
<%-- <jsp:include page="addProduct.jsp"></jsp:include> --%>
<!-- jQuery (necessary for Bootstrap‘s JavaScript plugins) -->
<script src="../manager/product/js/jquery-1.9.1.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->


<script src="../manager/product/js/bootstrap.min.js"></script>

<!-- 引入核心JS 这个组件-->

<script src="../manager/product/js/jqPaginator.js"></script>

<!---自己的JS -->
<script  src="../manager/product/js/product.js"></script>
<!-- Bootstrap -->

<link
    href="../manager/product/css/bootstrap.min.css"
    rel="stylesheet">


<style>
body {
    font-size: 16px;
    font-family: Arial, Helvetica, sans-serif;
}
</style>
</head>
<body>
    <div
        class="container-fluid"
        style="width: 960px;">
        <div>
            <h3>产品列表</h3>
        </div>
        <div>
            <div class="col-md-2 pull-right">
                <ul class="nav nav-pills nav-justified" role="tablist">
                    <li role="presentation" class=" active"><a href="#">Home</a></li>
                    <li role="presentation"><a href="#">Profile</a></li>
                    <li role="presentation"><a href="#">Messages</a></li>
                </ul>
            </div>
        </div>

        <div class="row" style="margin-top: 30px;">
            <div class="col-sm-2" style="border-width: 2px; border-color: #AA0000">
                <ul class="nav nav-pills nav-stacked" role="tablist">
                    <li role="presentation" class="active"><a href="#">Home</a></li>
                    <li role="presentation"><a href="#">Profile</a></li>
                    <li role="presentation"><a href="#">Messages</a></li>
                </ul>
            </div>
            <div class=" col-sm-8" style="margin-left: 50px; margin-right: 0; border-width: 2px; border-color: #AA0000">

                <div id="tools" class="col-md-4 pull-right">
                    <ul class="nav nav-pills nav-justified " role="tablist">
                        <li role="presentation" class=" active" style="padding-left: 10px;">
                            <button class="btn  btn-sm btn-info">查询</button>
                        </li>
                        <li role="presentation" class=" active" style="padding-left: 10px;">
                            <button id="addProduct" class="btn btn-sm btn-success" data-toggle="modal">添加产品</button>
                        </li>
                        <li role="presentation" class=" active" style="padding-left: 10px;">
                            <button id="deleteBtn" class="btn btn-sm btn-danger">删除</button>
                        </li>
                    </ul>
                </div>
                <div style="margin-top: 40px;">

                    <table id="productList" class="table table-bordered table-hover">
                        <tr>
                            <td><input id="selectAll" class="checkbox" type="checkbox" /></td>
                            <td>产品编号</td>
                            <td>产品名称</td>
                            <td>产品规格</td>
                            <td>产品数量</td>
                            <td>产品价格</td>
                            <td>操作</td>
                        </tr>
<!-- 循环遍历结果集 开始 -->
                        <c:forEach var="product" items="${pageResult.resultList}">
                            <tr>
                                <td><input name="proId" class="checkbox" type="checkbox" value="${product.pid}" /></td>
                                <td>${product.product_code}</td>
                                <td>${product.product_name}</td>
                                <td>${product.detail}</td>
                                <td>${product.note}</td>
                                <td>${product.product_code}</td>
                                <td><button name="editBtn" objId="${product.pid}" class="btn btn-sm-block" onclick="">修改</button></td>
                            </tr>
                        </c:forEach>
<!-- 循环遍历结果集 结束 -->
					</table>
                </div>
            </div>
        </div>
    </div>
    
    <div id="p2"></div> <ul id="pagination2"></ul>

<!-- 存放生成分页控件的DIV  -->
    <div style="text-align: center;">

	<!--  分页结果集对象，里面存放着结果集，每页记录数，当前第几页，记录总数等数据-->
	<div id="pageResult">
	<input id="pageCount" type="hidden" value="${pageResult.pageCount}"/>
	<input id="totalCount" type="hidden" value="${pageResult.totalCount}"/>
	<input id="pageIndex" type="hidden" value="${pageResult.pageIndex}"/>
	</div>
	
        <!-- footer -->
        <p>vinace-Demo by 御风林海</p>
    </div>
</body>
<script type="text/javascript">

//S 脚本代码：

$(document).ready(
  function() {
         var pSize = 10;   //每页记录数
         var totalCount = $("#totalCount").val();  //记录总数
         var pageIndex = $("#pageIndex").val();   //当前页码
})


    $('#pagination2').jqPaginator({
    							   totalCounts : totalCount,
                                   currentPage : pageIndex,
                                   pageSize : pSize,
                                   visiblePages : 10,
                                   prev : "<li class="prev"><a href="javascript:;">Previous</a></li>",
                                   next : "<li class="next"><a href="javascript:;">Next</a></li>",
                                   page : "<li class="page"><a href="javascript:;">{{page}}</a></li>",
                                   onPageChange : function(num, type) {
                                   // $(‘#p2‘).text(type + ‘：‘ + num);

                                   var queryUrl = "/video/searchVideo";
                                   $.ajax({
                                          url : queryUrl,
                                          dataType : "json",
                                          data : {
                                              pageIndex : num,
                                              pageSize : pSize
                                          },cache : false,

		             success : function(data) {
		                 $("#productList tr:not(:first)").remove();
		                 var results = data.pageResult.resultList;
		                 for (var i = 0; i < results.length; i++) {
		                     var rowContent = 
		                     		"<tr>"
		                             + "<td><input name=‘proId‘ class=‘checkbox‘ type=‘checkbox‘ value=‘" + results[i].pid + "‘/></td>"
		                             + "<td>" + results[i].pid + "</td>"
		                             + "<td>" + results[i].product_name + "</td>"
		                             + "<td>"  + results[i].note + "</td>"
		                             + "<td>" + results[i].note + "</td>"
		                             + "<td>" + results[i].note + "</td>"
		                             + "<td><button name=‘editBtn‘ objId=‘" + results[i].pid + "‘ class=‘btn btn-sm-block‘>修改</button></td>"
		                             + "</tr>";
		                     $("#productList").append(rowContent);
		                 }
		             },
                                    error : function(html) {
                                        return;
                                    }
                                });
                    }
        });

</script>

</html>