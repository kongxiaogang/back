<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"  content="width=device-width, initial-scale=1">
<title>分页</title>
<%--     <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/plugins/page/js/bootstrap.min.css"/> --%>
</head>
<body>
	<div style="text-align: center;">
    <p id="p"></p> 
    <div id="pagination"></div>
    
    <!-- 存放生成分页控件的DIV  -->
    
	<!--  分页结果集对象，里面存放着结果集，每页记录数，当前第几页，记录总数等数据-->
	<div id="pageResult">
		pageCount : <input id="pageCount" type="text" value="${pageCount}"/>
		dataCount : <input id="dataCount" type="text" value="${dataCount}"/>
		currentPage : <input id="currentPage" type="text" value="${currentPage}"/>
	</div>
        <!-- footer -->
        <p>vinace-Demo by lvshiyang</p>
    </div>
    
    
    
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/resources/plugins/page/js/jquery-1.7.2.min.js"></script> --%>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/plugins/page/js/jqPaginator.js"></script>
<script type="text/javascript">
// 	$(document).ready(
//         function() {
// //             var pageSize = 6;   //每页记录数
//             var totalCount = $("#dataCount").val();  //记录总数
//             var currentPage = $("#currentPage").val();   //当前页码
// 	});

    $.jqPaginator('#pagination', {
        totalPages: '${pageCount}',
        visiblePages: 5,
        currentPage: 1,
        wrapper:'<ul class="pagination"></ul>',
        first: '<li class="first"><a href="javascript:void(0);">First</a></li>',
        prev: '<li class="prev"><a href="javascript:void(0);">Previous</a></li>',
        next: '<li class="next"><a href="javascript:void(0);">Next</a></li>',
        last: '<li class="last"><a href="javascript:void(0);">Last</a></li>',
        page: '<li class="page"><a href="javascript:void(0);">{{page}}</a></li>',
        onPageChange: function (num) {
				  alert(num)			
 		$.ajax({
 		   type: "POST",
		   url: "<%=request.getContextPath()%>/video/searchVideo.do",
		   data: {currentPage:num}, 
		   success: function(results){
			   alert(results);
			   $("#videoTable tr:not(:first)").remove();
			   var dataObj = eval("(" + results + ")");       //转换为json对象
		     for (var i = 0; i < dataObj.length; i++) {
			      var rowContent = 
			      		"<tr>"
			      		 	+ "<td>"+dataObj[i].id+"</td>"
			      			+ "<td>"+results[i].id+"</td>"
			      			+ "<td>"+dataObj[i].id+"</td>"
			              //+ "<td><input name=‘proId‘ class=‘checkbox‘ type=‘checkbox‘ value=‘" + results[i].pid + "‘/></td>"
			              //+ "<td>" + results[i].pid + "</td>"
			             /*  + "<td>" + results[i].product_name + "</td>"
			              + "<td>"  + results[i].note + "</td>"
			              + "<td>" + results[i].note + "</td>"
			              + "<td>" + results[i].note + "</td>"
			              + "<td><button name=‘editBtn‘ objId=‘" + results[i].pid + "‘ class=‘btn btn-sm-block‘>修改</button></td>" */
			              + "</tr>";
			      $("#videoTable").append(rowContent);
		    }
		   }
		});
// 					   success : function(data) {
// 						   alert(data.mavResult1)
	// 				  $("#videoTable tr:not(:first)").remove();
	// 				  var results = data.pageResult.resultList;
	// 				  for (var i = 0; i < results.length; i++) {
	// 				      var rowContent = 
	// 				      		"<tr>"
	// 				              + "<td><input name=‘proId‘ class=‘checkbox‘ type=‘checkbox‘ value=‘" + results[i].pid + "‘/></td>"
	// 				              + "<td>" + results[i].pid + "</td>"
	// 				              + "<td>" + results[i].product_name + "</td>"
	// 				              + "<td>"  + results[i].note + "</td>"
	// 				              + "<td>" + results[i].note + "</td>"
	// 				              + "<td>" + results[i].note + "</td>"
	// 				              + "<td><button name=‘editBtn‘ objId=‘" + results[i].pid + "‘ class=‘btn btn-sm-block‘>修改</button></td>"
	// 				              + "</tr>";
	// 				      $("#videoTable").append(rowContent);
	// 				  }
// 						},
// 					    error : function(html) {
// 					                 return;
// 					    }
// 		         });		
		  }
	});
</script>
</body>
</html>