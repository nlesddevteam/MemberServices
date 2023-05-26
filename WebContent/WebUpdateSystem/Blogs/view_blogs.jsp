<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,java.util.*,java.io.*,java.text.*,com.esdnl.util.*"%>   
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  					               
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
  User usr = (User) session.getAttribute("usr");
%>
<html>

	<head>
		<title>BLOG Posting System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    
	<style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
		.choices__list--multiple .choices__item {background-color:#004178;border:1px solid #004178;}
		td {vertical-align:middle;}
		</style>
		
	<script>	
		
	$('document').ready(function(){
		
		
		$("tr").not(':first').hover(
				  function () {
				    $(this).css("background","yellow");
				  }, 
				  function () {
				    $(this).css("background","");
				  }
				);	
		
		
		aTable = $(".blogTable").dataTable({
			"order" : [[ 1, "desc" ]],			
			  "paging":   true,
			  "searching": true,			 
				responsive: true,
				"pageLength": 10,
				"lengthMenu": [[10, 25, 50, 75, 100, -1], [10, 25, 50, 75, 100, "All"]],
				"lengthChange": true,
				"columnDefs": [
					 {
			             
			            
			                "targets": [5],			               
			                "sortable": false,
			                "visible": true,
			            },
			        ]
		});
		
		$(".loadPage").show();
		$(".loadingTable").css("display","none");
		$("#loadingSpinner").css("display","none");
	});

	
	
	
		
		</script>
	
	
	</head>

  <body>
    <div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
<div class="siteHeaderGreen">BLOGS</div>
  
  The following are current blog entries posted in the system and their current status. Click on the blog title to view/edit. 
					If you add special documents (in the Other Files Section when editing a posting) to a blog, please use keywords in their title like form or presentation, so the display icon will match the appropriate file on the public site. If no keyword, then a default doc icon will show for any doc attachments.
				<p>


<p><div align="center">
					<a href="addNewBlog.html" class="btn btn-sm btn-primary" onclick="loadingData();">ADD BLOG</a> &nbsp; 
					<a class="btn btn-sm btn-danger" href="/MemberServices/navigate.jsp" onclick="loadingData();">Back to StaffRoom</a>
					</div>




					<p>					
				<table class="blogTable table table-sm responsive" style="width:100%;font-size:11px;">
				<thead class="thead-dark">
				<tr>
										<th>TITLE</th>
										<th>BLOG DATE</th>										
										<th>STATUS</th>
										<th>DATE ADDED</th>
										<th>ADDED BY</th>
										<th>OPTIONS</th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
	                                  	<c:when test='${fn:length(blogs) gt 0}'>
                                  		<c:forEach items='${blogs}' var='g'>
                                  		<tr class='datalist'>
                                  			<td><a href="viewBlogDetails.html?id=${g.id}">${g.blogTitle}</a></td>
                                  			<td><fmt:formatDate pattern="yyyy/MM/dd"  value="${g.blogDate}" /></td>
                                  		<c:choose>	
                                  		<c:when test="${ g.blogStatus.description eq 'ACTIVE' }">	
										<td style="text-align:center;background-color:Green;color:White;">${g.blogStatus.description}</td>
										</c:when>	
										<c:when test="${ g.blogStatus.description eq 'ARCHIVED' }">	
										<td style="text-align:center;background-color:Silver;color:White;">${g.blogStatus.description}</td>
										</c:when>
                                  		<c:when test="${ g.blogStatus.description eq 'DISABLED' }">	
										<td style="text-align:center;background-color:Red;color:White;">${g.blogStatus.description}</td>
										</c:when>
                                  		<c:otherwise>
                                  		<td style="text-align:center;background-color:White;color:Silver;">N/A</td>
                                  		</c:otherwise>
                                  		</c:choose>
                                  		<td><fmt:formatDate pattern="yyyy/MM/dd"  value="${g.dateAdded}" /></td>
		                                    <td style="text-transform:Capitalize;">${g.addedBy}</td>
		                                    <td style="text-align:center;"><a class="btn btn-xs btn-warning" href="viewBlogDetails.html?id=${g.id}">EDIT</a> &nbsp; 
		                                    <a class="btn btn-xs btn-danger" onclick="return confirm('Are you sure you want to DELETE this BLOG?');" href='deleteBlog.html?bid=${g.id}'>DEL</a>
		                				</tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr>
										<td>No Blogs Found.</td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>										
										</tr>
										</c:otherwise>
									</c:choose>
									</tbody>
									</table>
	</div>
	</div>
	</div>
	</body>

</html>	
			
			
			