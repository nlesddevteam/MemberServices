<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,java.util.*,java.io.*,java.text.*,com.esdnl.util.*;"%>   
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  					               
<%@ taglib uri="/taglib/memberservices.tld" prefix="esd" %>

<%
  User usr = (User) session.getAttribute("usr");
%>
<html>

	<head>
		<title>NLESD - Web Update Posting System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		 <link rel="stylesheet" href="../includes/css/jquery-ui-1.10.3.custom.css" >
		<link href="../includes/css/ms.css" rel="stylesheet" type="text/css">	
			<script src="../includes/js/jquery-1.7.2.min.js"></script>
			<script src="../includes/js/jquery-1.9.1.js"></script>
			<script src="../includes/js/jquery-ui-1.10.3.custom.js"></script>
			<script type="text/javascript" src="../includes/js/common.js"></script>
			<script src="../includes/js/nlesd.js"></script>
			<link rel="stylesheet" href="../includes/css/jquery-ui.css" />
		<script >
		
		
		$(document).ready(function() {

			$(function() {
			    var images = ['0.jpg','1.jpg','2.jpg','3.jpg','4.jpg','5.jpg','6.jpg','7.jpg','8.jpg','9.jpg','10.jpg','11.jpg','12.jpg','13.jpg','14.jpg','15.jpg','16.jpg','17.jpg','18.jpg','19.jpg'];
			    $('html').css({'background': 'url(../includes/img/bg/' + images[Math.floor(Math.random() * images.length)] + ') no-repeat center center fixed',
			    	'-webkit-background-size':'cover',
			    	'-moz-background-size':'cover',
			    	'-o-background-size':'cover',
			    	'background-size':'cover'});
			   });


		}); 
		</script>
		<script type="text/javascript">
			$('document').ready(function(){
				$('tr.datalist:odd').css('background-color', '#E0E0E0');
				
			});
		</script>
		<script>
		jQuery(function(){
	     $(".img-swap").hover(
	          function(){this.src = this.src.replace("-off","-on");},
	          function(){this.src = this.src.replace("-on","-off");});
		});
	 
		
		
	</script>
	
	
	
	
	</head>

  <body><br/>
  
  <div class="mainContainer">

  	   	<div class="section group">
	   		
	   		<div class="col full_block topper">
	   		<div class="toppertextleft">Welcome <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></div>
	   		<div class="toppertextright"><script src="../includes/js/date.js"></script></div>	   		
			</div>
			
			<div class="full_block center">
				<img src="../includes/img/header.png" alt="" width="90%" border="0"><br/>				
			</div>

			<div class="col full_block content">
				<div class="bodyText">						
				
				<div class="pageTitleHeader siteHeaders">View Blogs</div>
                      <div class="pageBody">
					The following are current blog entries posted in the system and their current status. Click on the blog title to view/edit. 
					If you add special documents (in the Other Files Section when editing a posting) to a blog, please use keywords in their title like form or presentation, so the display icon will match the appropriate file on the public site. If no keyword, then a default doc icon will show for any doc attachments.
				<p>

<div align="center">
					<a href="addNewBlog.html"><img src="../includes/img/addblog-off.png" class="img-swap menuImage" title="Add Director's Blog"></a>&nbsp;<a href="viewBlogs.html"><img src="../includes/img/viewblogs-off.png" class="img-swap menuImage" title="View Blogs"></a>&nbsp;<a href="../index.jsp"><img src="../includes/img/backtomenu-off.png" class="img-swap menuImage" title="Back to Web Update Menu"></a>
					</div><p>


									<%if(request.getAttribute("msg")!=null){%>
									<div class="messageText" align="center">
										*** <%=(String)request.getAttribute("msg")%> ***
									</div>	
                             		 <%} else { %>   
									
									<p>
					<table class="blogTable">

									<tr class="header">
										<th>Title</th>
										<th>Date</th>
										<!-- <th>Content</th>
										<!-- <th>Photo</th>
										<th>Photo Caption</th>
										<th>Document</th>-->
										<th>Status</th>
										<!-- <th>Added By</th>
										<th>Date Added</th> -->
										<th>Options</th>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(blogs) gt 0}'>
                                  		<c:forEach items='${blogs}' var='g'>
                                  		
                                  		<c:if test="${ g.blogStatus.description eq 'ACTIVE' }">	
										<c:set var="statusColor" value="openColor"/>
										</c:if>	
										<c:if test="${ g.blogStatus.description eq 'ARCHIVED' }">	
										<c:set var="statusColor" value="archivedColor"/>
										</c:if>
                                  		<c:if test="${ g.blogStatus.description eq 'DISABLED' }">	
										<c:set var="statusColor" value="colosedColor"/>
										</c:if>
                                  		
                                  		
                                  		
                                  			<tr class='datalist'>
                                  			<td><a href="viewBlogDetails.html?id=${g.id}">${g.blogTitle}</a></td>
		                                      <td  style="text-align:center;">${g.blogDateFormatted}</td>
		                                     <!--  <td>${g.blogContent}</td>
		                                      <td>${g.blogPhoto}</td>
		                                      <td>${g.blogPhotoCaption}</td>
		                                      <td>${g.blogDocument}</td>-->
		                                      <td class="${statusColor}" style="text-align:center;">${g.blogStatus.description}</td>
		                                     <!--<td>${g.addedBy}</td>
		                                      <td>${g.dateAddedFormatted}</td>-->
		                                      <td  style="text-align:center;"><a href="viewBlogDetails.html?id=${g.id}">Edit</a> |
		                                      <a class="small" onclick="return confirm('Are you sure you want to DELETE this BLOG?');" href='deleteBlog.html?bid=${g.id}'>Delete</a>
		                				</tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='4'>No Blogs Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

</table>
		<% } %>	
			
	</div>
    
    
		
		<br/><br/>
		
			</div>
			</div>
<div style="float:right;padding-right:3px;width:25%;text-align:right;"><a href="../../navigate.jsp" title="Back to MemberServices Main Menu"><img src="../includes/img/ms-footerlogo.png" border=0></a></div>
		<div class="section group">
			<div class="col full_block copyright">&copy; 2016 Newfoundland and Labrador English School District</div>
		</div>	
</div>
  
</div>
    <br/>
  </body>

</html>	
			
			
			