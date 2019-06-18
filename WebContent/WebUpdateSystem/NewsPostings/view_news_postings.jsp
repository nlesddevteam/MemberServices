<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>
                 
<%@ page
	import="java.util.*,
					com.esdnl.webupdatesystem.newspostings.bean.*,
					com.esdnl.webupdatesystem.newspostings.dao.*,
					com.esdnl.webupdatesystem.newspostings.constants.*,
					com.esdnl.util.*;"%>                 
                 
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>                
<%@ taglib uri="/taglib/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck />
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
			<script src="../includes/js/jquery-1.10.2.js" type="text/javascript"></script>
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
		
		<script>
		jQuery(function(){
	     $(".img-swap").hover(
	          function(){this.src = this.src.replace("-off","-on");},
	          function(){this.src = this.src.replace("-on","-off");});
		});
	 
		
		
	</script>
		<script>
    $(document).ready(
    		  
    		  /* This is the function that will get executed after the DOM is fully loaded */
    		  function () {
    		    $( "#news_date" ).datepicker({
    		      changeMonth: true,//this option for allowing user to select month
    		      changeYear: true, //this option for allowing user to select from year range
    		      dateFormat: "dd/mm/yy"
    		    });
    		  }

	);

</script>


<script src="../includes/ckeditor/ckeditor.js"></script>
   
	
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
				<br/>
				<div class="pageTitleHeader siteHeaders">View School News and Announcements</div>
				<div class="pageBody"> 
				Listed below are all the school news and announcements categorized by status. To edit an item, simply click on the title of the story listed or use the options at right. Once you delete a item it is removed from the database permanently. If you wish to not remove a story, it is best to Archive or Disabled it. 
				 If you add special documents (in the Other Files Section when editing a posting) to a Story or Announcement, please use keywords in their title like form or presentation, so the display icon will match the appropriate file on the public site. If no keyword, then a default doc icon will show for any doc attachments.
				 
				
				<p><div align="center">
					<a href="addNewNewsPostings.html"><img src="../includes/img/addnews-off.png" class="img-swap menuImage" title="Add News Item"></a>&nbsp;<a href="viewNewsPostings.html"><img src="../includes/img/viewnews-off.png" class="img-swap menuImage" title="View News"></a>&nbsp;<a href="../index.jsp"><img src="../includes/img/backtomenu-off.png" class="img-swap menuImage" title="Back to Web Update Menu"></a>
					</div>
				<p>
			</div>
					
									<%if(request.getAttribute("msg")!=null){%>
									<div class="messageText" align="center">
										*** <%=(String)request.getAttribute("msg")%> ***
									</div>	
                             		 <%} else { %>   
				
				<p><div style="border:1px solid silver;" class="bgcolor3">
  			<div class="pageTitleHeader siteHeaders">Announcements</div>	
			
  			<div class="pageSectionHeader siteSubHeaders">Active</div>
				
                      <div class="pageBody">  
    								 
    <c:set var='newspostings' value='<%= NewsPostingsManager.getNewsPostingsByCat(2,250,1) %>' />
    								<table class="newsTable">

									<tr class="tableHeader">
									<!-- <td style="width:10%;">Category</td>-->										
									<td style="width:65%;">Title</td>										
									<td style="width:10%;">News Date</td>									
									<td style="width:10%;">Added</td>
									<td style="width:15%;">Options</td>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<!-- <td>${g.newsCategory.description}</td>	-->	  	                                    
		                                    <td> <a href="viewNewsPostingsDetails.html?id=${g.id}">${g.newsTitle}</a></td>	                                   
		                                    <td>${g.newsDateFormatted}</td>		                                   
		                                    <td>${g.dateAddedFormatted}</td>
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">Edit</a>
		                                    <esd:SecurityAccessRequired roles="ADMINISTRATOR"> | 
		                                      <a class="small" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>Delete</a>
		                                      </esd:SecurityAccessRequired>
		                					</td>
		                                    </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No News Postings Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

									</table>
    
    
    
    					</div>
    					
    					
    					
    					<div class="pageSectionHeader siteSubHeaders">Archived</div>
				
                      <div class="pageBody">
    
    								<c:set var='newspostings' value='<%= NewsPostingsManager.getNewsPostingsByCat(2,250,3) %>' />
    								<table class="newsTable">

									<tr class="tableHeader">
									<!-- <td style="width:10%;">Category</td>-->										
									<td style="width:65%;">Title</td>										
									<td style="width:10%;">News Date</td>									
									<td style="width:10%;">Archived</td>
									<td style="width:15%;">Options</td>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<!-- <td>${g.newsCategory.description}</td>	-->	  	                                    
		                                    <td> <a href="viewNewsPostingsDetails.html?id=${g.id}">${g.newsTitle}</a></td>		                                   
		                                    <td>${g.newsDateFormatted}</td>
		                                    <td>${g.dateAddedFormatted}</td>
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">Edit</a>
		                                     <esd:SecurityAccessRequired roles="ADMINISTRATOR"> |  
		                                   <a class="small" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>Delete</a>
		                                   </esd:SecurityAccessRequired>
		                					</td>
		                                   </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No News Postings Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

									</table>
    
    
    
    					</div>
    					
    					
    					<div class="pageSectionHeader siteSubHeaders">Disabled</div>
				
                      <div class="pageBody">
    
    								<c:set var='newspostings' value='<%= NewsPostingsManager.getNewsPostingsByCat(2,250,2) %>' />
    								<table class="newsTable">

									<tr class="tableHeader">
									<!-- <td style="width:10%;">Category</td>-->										
									<td style="width:65%;">Title</td>										
									<td style="width:10%;">News Date</td>									
									<td style="width:10%;">Disabled</td>
									<td style="width:15%;">Options</td>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<!-- <td>${g.newsCategory.description}</td>-->		                                    
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">${g.newsTitle}</a></td>		                                   
		                                    <td>${g.newsDateFormatted}</td>
		                                    <td>${g.dateAddedFormatted}</td>
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">Edit</a>
		                                     <esd:SecurityAccessRequired roles="ADMINISTRATOR"> |
		                                   <a class="small" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>Delete</a>
		                                   </esd:SecurityAccessRequired>
		                					</td>
		                                   </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No News Postings Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

									</table>
    
    
    
    					</div>
  			</div>
				
				<br/>
				<br/>										
			<div style="border:1px solid silver;"  class="bgcolor2">	
				<div class="pageTitleHeader siteHeaders">School News Stories (Good News / School News)</div>	
			
  			<div class="pageSectionHeader siteSubHeaders">Active</div>
				
                      <div class="pageBody">
     
    <c:set var='newspostings' value='<%= NewsPostingsManager.getNewsPostingsByCat(1,250,1) %>' />
    								<table class="newsTable">

									<tr class="tableHeader">
									<!-- <td style="width:10%;">Category</td>-->										
									<td style="width:65%;">Title</td>											
									<td style="width:10%;">News Date</td>									
									<td style="width:10%;">Added</td>
									<td style="width:15%;">Options</td>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<!-- <td>${g.newsCategory.description}</td>	-->	  		                                    
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">${g.newsTitle}</a></td>	                                   
		                                    <td>${g.newsDateFormatted}</td>		                                   
		                                    <td>${g.dateAddedFormatted}</td>
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">Edit</a>
		                                     <esd:SecurityAccessRequired roles="ADMINISTRATOR"> |  
		                                      <a class="small" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>Delete</a>
		                                      </esd:SecurityAccessRequired>
		                					</td>
		                                    </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No News Postings Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

									</table>
    
    
    
    					</div>
    					
    					
    					
    					<div class="pageSectionHeader siteSubHeaders">Archived</div>
				
                      <div class="pageBody">
    
    								
    								<c:set var='newspostings' value='<%= NewsPostingsManager.getNewsPostingsByCat(1,250,3) %>' />
    								<table class="newsTable">

									<tr class="tableHeader">
									<!-- <td style="width:10%;">Category</td>-->										
									<td style="width:65%;">Title</td>											
									<td style="width:10%;">News Date</td>									
									<td style="width:10%;">Archived</td>
									<td style="width:15%;">Options</td>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<!-- <td>${g.newsCategory.description}</td>	-->	  		                                    
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">${g.newsTitle}</a></td>		                                   
		                                    <td>${g.newsDateFormatted}</td>
		                                    <td>${g.dateAddedFormatted}</td>
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">Edit</a>
		                                     <esd:SecurityAccessRequired roles="ADMINISTRATOR"> |  
		                                   <a class="small" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>Delete</a>
		                                   </esd:SecurityAccessRequired>
		                					</td>
		                                   </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No News Postings Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

									</table>
    
    
    
    					</div>
    					
    					
    					<div class="pageSectionHeader siteSubHeaders">Disabled</div>
				
                      <div class="pageBody">
    
    								<c:set var='newspostings' value='<%= NewsPostingsManager.getNewsPostingsByCat(1,250,2) %>' />
    								<table class="newsTable">

									<tr class="tableHeader">
									<!-- <td style="width:10%;">Category</td>-->										
									<td style="width:65%;">Title</td>											
									<td style="width:10%;">News Date</td>									
									<td style="width:10%;">Disabled</td>
									<td style="width:15%;">Options</td>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<!-- <td>${g.newsCategory.description}</td>	-->	  		                                    
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">${g.newsTitle}</a></td>		                                   
		                                    <td>${g.newsDateFormatted}</td>
		                                    <td>${g.dateAddedFormatted}</td>
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">Edit</a>
		                                     <esd:SecurityAccessRequired roles="ADMINISTRATOR"> | 
		                                   <a class="small" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>Delete</a>
		                                   </esd:SecurityAccessRequired>
		                					</td>
		                                   </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No News Postings Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

									</table>
    
    
    
    					</div>
  			</div>
  			<br/>
  
  
  			<div style="border:1px solid silver;"  class="bgcolor2">	
				<div class="pageTitleHeader siteHeaders">Schools in the News Stories</div>	
			
  			<div class="pageSectionHeader siteSubHeaders">Active</div>
				
                      <div class="pageBody">
     
    <c:set var='newspostings' value='<%= NewsPostingsManager.getNewsPostingsByCat(4,250,1) %>' />
    								<table class="newsTable">

									<tr class="tableHeader">
									<!-- <td style="width:10%;">Category</td>-->										
									<td style="width:65%;">Title</td>											
									<td style="width:10%;">News Date</td>									
									<td style="width:10%;">Added</td>
									<td style="width:15%;">Options</td>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<!-- <td>${g.newsCategory.description}</td>	-->	  		                                    
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">${g.newsTitle}</a></td>	                                   
		                                    <td>${g.newsDateFormatted}</td>		                                   
		                                    <td>${g.dateAddedFormatted}</td>
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">Edit</a>
		                                     <esd:SecurityAccessRequired roles="ADMINISTRATOR"> |  
		                                      <a class="small" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>Delete</a>
		                                      </esd:SecurityAccessRequired>
		                					</td>
		                                    </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No News Postings Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

									</table>
    
    
    
    					</div>
    					
    					
    					
    					<div class="pageSectionHeader siteSubHeaders">Archived</div>
				
                      <div class="pageBody">
    
    								
    								<c:set var='newspostings' value='<%= NewsPostingsManager.getNewsPostingsByCat(4,250,3) %>' />
    								<table class="newsTable">

									<tr class="tableHeader">
									<!-- <td style="width:10%;">Category</td>-->										
									<td style="width:65%;">Title</td>											
									<td style="width:10%;">News Date</td>									
									<td style="width:10%;">Archived</td>
									<td style="width:15%;">Options</td>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<!-- <td>${g.newsCategory.description}</td>	-->	  		                                    
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">${g.newsTitle}</a></td>		                                   
		                                    <td>${g.newsDateFormatted}</td>
		                                    <td>${g.dateAddedFormatted}</td>
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">Edit</a>
		                                     <esd:SecurityAccessRequired roles="ADMINISTRATOR"> |  
		                                   <a class="small" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>Delete</a>
		                                   </esd:SecurityAccessRequired>
		                					</td>
		                                   </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No News Postings Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

									</table>
    
    
    
    					</div>
    					
    					
    					<div class="pageSectionHeader siteSubHeaders">Disabled</div>
				
                      <div class="pageBody">
    
    								<c:set var='newspostings' value='<%= NewsPostingsManager.getNewsPostingsByCat(4,250,2) %>' />
    								<table class="newsTable">

									<tr class="tableHeader">
									<!-- <td style="width:10%;">Category</td>-->										
									<td style="width:65%;">Title</td>											
									<td style="width:10%;">News Date</td>									
									<td style="width:10%;">Disabled</td>
									<td style="width:15%;">Options</td>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<!-- <td>${g.newsCategory.description}</td>	-->	  		                                    
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">${g.newsTitle}</a></td>		                                   
		                                    <td>${g.newsDateFormatted}</td>
		                                    <td>${g.dateAddedFormatted}</td>
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">Edit</a>
		                                     <esd:SecurityAccessRequired roles="ADMINISTRATOR"> | 
		                                   <a class="small" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>Delete</a>
		                                   </esd:SecurityAccessRequired>
		                					</td>
		                                   </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No News Postings Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

									</table>
    
    
    
    					</div>
  			</div>
  
  
  			
  			
				<br/>
		<div style="border:1px solid silver;" class="bgcolor4">	
  			
  			<div class="pageTitleHeader siteHeaders">Staff News</div>	
			
  			<div class="pageSectionHeader siteSubHeaders">Active</div>
				
                      <div class="pageBody">
    
    								
    <c:set var='newspostings' value='<%= NewsPostingsManager.getNewsPostingsByCat(6,250,1) %>' />
    								<table class="newsTable">

									<tr class="tableHeader">
									<!-- <td style="width:10%;">Category</td>-->										
									<td style="width:65%;">Title</td>										
									<td style="width:10%;">News Date</td>									
									<td style="width:10%;">Added</td>
									<td style="width:15%;">Options</td>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<!-- <td>${g.newsCategory.description}</td>	-->	  	                                    
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">${g.newsTitle}</a></td>	                                   
		                                    <td>${g.newsDateFormatted}</td>		                                   
		                                    <td>${g.dateAddedFormatted}</td>
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">Edit</a>
		                                     <esd:SecurityAccessRequired roles="ADMINISTRATOR"> |  
		                                      <a class="small" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>Delete</a>
		                                      </esd:SecurityAccessRequired>
		                					</td>
		                                    </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No News Postings Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

									</table>
    
    
    
    					</div>
    					
    					
    					
    					<div class="pageSectionHeader siteSubHeaders">Archived</div>
				
                      <div class="pageBody">
    
    								<c:set var='newspostings' value='<%= NewsPostingsManager.getNewsPostingsByCat(6,250,3) %>' />
    								<table class="newsTable">

									<tr class="tableHeader">
									<!-- <td style="width:10%;">Category</td>-->										
									<td style="width:65%;">Title</td>											
									<td style="width:10%;">News Date</td>									
									<td style="width:10%;">Archived</td>
									<td style="width:15%;">Options</td>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<!-- <td>${g.newsCategory.description}</td>	-->	  	                                    
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">${g.newsTitle}</a></td>		                                   
		                                    <td>${g.newsDateFormatted}</td>
		                                    <td>${g.dateAddedFormatted}</td>
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">Edit</a>
		                                     <esd:SecurityAccessRequired roles="ADMINISTRATOR"> | 
		                                   <a class="small" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>Delete</a>
		                                   </esd:SecurityAccessRequired>
		                					</td>
		                                   </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No News Postings Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

									</table>
    
    
    
    					</div>
    					
    					
    					<div class="pageSectionHeader siteSubHeaders">Disabled</div>
				
                      <div class="pageBody">
     
    								<c:set var='newspostings' value='<%= NewsPostingsManager.getNewsPostingsByCat(6,250,2) %>' />
    								<table class="newsTable">

									<tr class="tableHeader">
									<!-- <td style="width:10%;">Category</td>-->										
									<td style="width:65%;">Title</td>										
									<td style="width:10%;">News Date</td>									
									<td style="width:10%;">Disabled</td>
									<td style="width:15%;">Options</td>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<!-- <td>${g.newsCategory.description}</td>	-->	  	                                    
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">${g.newsTitle}</a></td>		                                   
		                                    <td>${g.newsDateFormatted}</td>
		                                    <td>${g.dateAddedFormatted}</td>
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">Edit</a>
		                                     <esd:SecurityAccessRequired roles="ADMINISTRATOR"> | 
		                                   <a class="small" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>Delete</a>
		                                   </esd:SecurityAccessRequired>
		                					</td>
		                                   </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No News Postings Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

									</table>
    
    
    
    					</div>
				
				</div>
				
				<br/>
				<br/>
				
				
<div style="border:1px solid silver;" class="bgcolor5">
  			<div class="pageTitleHeader siteHeaders">Media Releases</div>	
			
  			<div class="pageSectionHeader siteSubHeaders">Active</div>
				
                      <div class="pageBody">  
    								 
    <c:set var='newspostings' value='<%= NewsPostingsManager.getNewsPostingsByCat(3,250,1) %>' />
    								<table class="newsTable">

									<tr class="tableHeader">
									<!-- <td style="width:10%;">Category</td>-->										
									<td style="width:65%;">Title</td>										
									<td style="width:10%;">News Date</td>									
									<td style="width:10%;">Added</td>
									<td style="width:15%;">Options</td>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<!-- <td>${g.newsCategory.description}</td>	-->	  	                                    
		                                    <td> <a href="viewNewsPostingsDetails.html?id=${g.id}">${g.newsTitle}</a></td>	                                   
		                                    <td>${g.newsDateFormatted}</td>		                                   
		                                    <td>${g.dateAddedFormatted}</td>
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">Edit</a>
		                                     <esd:SecurityAccessRequired roles="ADMINISTRATOR"> | 
		                                      <a class="small" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>Delete</a>
		                                      </esd:SecurityAccessRequired>
		                					</td>
		                                    </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No Media Release Postings Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

									</table>
    
    
    
    					</div>
    					
    					
    					
    					<div class="pageSectionHeader siteSubHeaders">Archived</div>
				
                      <div class="pageBody">
    
    								<c:set var='newspostings' value='<%= NewsPostingsManager.getNewsPostingsByCat(3,250,3) %>' />
    								<table class="newsTable">

									<tr class="tableHeader">
									<!-- <td style="width:10%;">Category</td>-->										
									<td style="width:65%;">Title</td>										
									<td style="width:10%;">News Date</td>									
									<td style="width:10%;">Archived</td>
									<td style="width:15%;">Options</td>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<!-- <td>${g.newsCategory.description}</td>	-->	  	                                    
		                                    <td> <a href="viewNewsPostingsDetails.html?id=${g.id}">${g.newsTitle}</a></td>		                                   
		                                    <td>${g.newsDateFormatted}</td>
		                                    <td>${g.dateAddedFormatted}</td>
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">Edit</a>
		                                     <esd:SecurityAccessRequired roles="ADMINISTRATOR"> | 
		                                   <a class="small" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>Delete</a>
		                                   </esd:SecurityAccessRequired>
		                					</td>
		                                   </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No Media Release Postings Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

									</table>
    
    
    
    					</div>
    					
    					
    					<div class="pageSectionHeader siteSubHeaders">Disabled</div>
				
                      <div class="pageBody">
    
    								<c:set var='newspostings' value='<%= NewsPostingsManager.getNewsPostingsByCat(3,250,2) %>' />
    								<table class="newsTable">

									<tr class="tableHeader">
									<!-- <td style="width:10%;">Category</td>-->										
									<td style="width:65%;">Title</td>										
									<td style="width:10%;">News Date</td>									
									<td style="width:10%;">Disabled</td>
									<td style="width:15%;">Options</td>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<!-- <td>${g.newsCategory.description}</td>-->		                                    
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">${g.newsTitle}</a></td>		                                   
		                                    <td>${g.newsDateFormatted}</td>
		                                    <td>${g.dateAddedFormatted}</td>
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">Edit</a>
		                                     <esd:SecurityAccessRequired roles="ADMINISTRATOR"> |
		                                   <a class="small" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>Delete</a>
		                                   </esd:SecurityAccessRequired>
		                					</td>
		                                   </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No Media Release Postings Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

									</table>
    
    
    
    					</div>
  			</div>				
				
				
				
				<br/>
  			
  			
				<br/>
				
				
				
				
				<div style="border:1px solid silver;" class="bgcolor1">	
  			
  			<div class="pageTitleHeader siteHeaders">Important Announcements</div>	
			
  			<div class="pageSectionHeader siteSubHeaders">Active</div>
				
                      <div class="pageBody">
    
    								
    <c:set var='newspostings' value='<%= NewsPostingsManager.getNewsPostingsByCat(5,250,1) %>' />
    								<table class="newsTable">

									<tr class="tableHeader">
									<!-- <td style="width:10%;">Category</td>-->										
									<td style="width:65%;">Title</td>											
									<td style="width:10%;">News Date</td>									
									<td style="width:10%;">Added</td>
									<td style="width:15%;">Options</td>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<!-- <td>${g.newsCategory.description}</td>	-->	  		                                    
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">${g.newsTitle}</a></td>	                                   
		                                    <td>${g.newsDateFormatted}</td>		                                   
		                                    <td>${g.dateAddedFormatted}</td>
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">Edit</a>
		                                     <esd:SecurityAccessRequired roles="ADMINISTRATOR"> | 
		                                      <a class="small" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>Delete</a>
		                                      </esd:SecurityAccessRequired>
		                					</td>
		                                    </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No News Postings Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

									</table>
    
    
    
    					</div>
    					
    					
    					
    					<div class="pageSectionHeader siteSubHeaders">Archived</div>
				
                      <div class="pageBody">
    
    								 
    								<c:set var='newspostings' value='<%= NewsPostingsManager.getNewsPostingsByCat(5,250,3) %>' />
    								<table class="newsTable">

									<tr class="tableHeader">
									<!-- <td style="width:10%;">Category</td>-->										
									<td style="width:65%;">Title</td>										
									<td style="width:10%;">News Date</td>									
									<td style="width:10%;">Archived</td>
									<td style="width:15%;">Options</td>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<!-- <td>${g.newsCategory.description}</td>	-->	  	                                    
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">${g.newsTitle}</a></td>		                                   
		                                    <td>${g.newsDateFormatted}</td>
		                                    <td>${g.dateAddedFormatted}</td>
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">Edit</a>
		                                     <esd:SecurityAccessRequired roles="ADMINISTRATOR"> | 
		                                   <a class="small" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>Delete</a>
		                                   </esd:SecurityAccessRequired>
		                					</td>
		                                   </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No News Postings Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

									</table>
    
    
    
    					</div>
    					
    					
    					<div class="pageSectionHeader siteSubHeaders">Disabled</div>
				
                      <div class="pageBody">
    
    								   
    								<c:set var='newspostings' value='<%= NewsPostingsManager.getNewsPostingsByCat(5,250,2) %>' />
    								<table class="newsTable">

									<tr class="tableHeader">
									<!-- <td style="width:10%;">Category</td>-->										
									<td style="width:65%;">Title</td>										
									<td style="width:10%;">News Date</td>									
									<td style="width:10%;">Disabled</td>
									<td style="width:15%;">Options</td>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(newspostings) gt 0}'>
                                  		<c:forEach items='${newspostings}' var='g'>
                                  			<tr>
                                  			<!-- <td>${g.newsCategory.description}</td>	-->	                                    
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">${g.newsTitle}</a></td>		                                   
		                                    <td>${g.newsDateFormatted}</td>
		                                    <td>${g.dateAddedFormatted}</td>
		                                    <td><a href="viewNewsPostingsDetails.html?id=${g.id}">Edit</a>
		                                     <esd:SecurityAccessRequired roles="ADMINISTRATOR"> | 
		                                   <a class="small" onclick="return confirm('Are you sure you want to DELETE this NEWS POSTINGS?');" href='deleteNewsPostings.html?pid=${g.id}'>Delete</a>
		                                   </esd:SecurityAccessRequired>
		                					</td>
		                                   </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No News Postings Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

									</table>
    
    
    
    					</div>
				
				</div>
				
				
				
				
				
				
    					
    					
    					
    					
    					
    					
    					
    
     <script>
    CKEDITOR.replace( 'news_description' );
    </script>
		
		<br/><br/>
	<% } %>	
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