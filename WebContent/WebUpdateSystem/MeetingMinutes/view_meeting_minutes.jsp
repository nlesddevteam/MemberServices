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
				
				<div class="pageTitleHeader siteHeaders">View Meeting Minutes</div>
                      <div class="pageBody">
                      If you add special documents (in the Other Files Section when editing a posting) to a Minute, please use keywords in their title like form or presentation, so the display icon will match the appropriate file on the public site. If no keyword, then a default doc icon will show for any doc attachments.
			<p><div align="center">
					<a href="addNewMeetingMinutes.html"><img src="../includes/img/addminutes-off.png" class="img-swap menuImage" title="Add Meeting Minutes"></a>&nbsp;
					 <a href="viewMeetingMinutes.html"><img src="../includes/img/viewminutes-off.png" class="img-swap menuImage" title="View Meeting Minutes"></a>&nbsp;
                      <a href="../index.jsp"><img src="../includes/img/backtomenu-off.png" class="img-swap menuImage" title="Back to Web Update Menu"></a></div>
					</div><p>
									<%if(request.getAttribute("msg")!=null){%>
									<div class="messageText" align="center">
										*** <%=(String)request.getAttribute("msg")%> ***
									</div>	
                             		 <%} else { %>   
									
									<p>
									
									
							
					
					
					
					
							
									
					<table class="blogTable">
									
									
									<tr><td colspan=3 class="pageSectionHeader sitesubHeaders">BOARD MINUTES</td></tr>
									<tr class="header">
										<td width="60%">Name</td>										
										<td width="30%">Title</td>																				
										<td width="10%">Options</td>
										
									</tr>
									
									
									<c:choose>
	                                  	<c:when test='${fn:length(mms) gt 0}'>
                                  		<c:forEach items='${mms}' var='g'>
                                  		
                                  		<c:if test="${g.meetingCategory.description eq 'BOARD'}">
                                  		<fmt:formatDate value="${g.mMDate}" type="date" dateStyle="long" var="parsedDate"/>
                                  		
                                  		
                                  			<tr class='datalist'>
                                  			<td><a href="viewMeetingMinutesDetails.html?id=${g.id}">${parsedDate} Meeting Minutes</a></td>                            			
                                  			<td>${g.mMTitle}</td>	                                      
		                                      
		                                      <td><a href="viewMeetingMinutesDetails.html?id=${g.id}">Edit</a> | 
		                                      <a class="small" onclick="return confirm('Are you sure you want to DELETE these MEETING MINUTES?');" href='deleteMeetingMinutes.html?mmid=${g.id}'>Del</a>
		                				</tr>
		                				
		                				</c:if>
                                  		</c:forEach>
                                  		</c:when>
                                  		                                  		
                                  		
										<c:otherwise>
											<tr><td colspan='3'>No Meeting Minutes Found.</td></tr>
										</c:otherwise>
									</c:choose>
									
									<tr><td colspan=3><br/><img src="../includes/img/bar.png" width=100%><br/></td></tr>
									
									<tr><td colspan=3 class="pageSectionHeader sitesubHeaders">EXECUTIVE MINUTES</td></tr>
									<tr class="header">
										<td width="60%">Name</td>										
										<td width="30%">Title</td>																				
										<td width="10%">Options</td>
										
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(mms) gt 0}'>
                                  		<c:forEach items='${mms}' var='g'>
                                  		
                                  		<c:if test="${g.meetingCategory.description eq 'EXECUTIVE'}">
                                  		<fmt:formatDate value="${g.mMDate}" type="date" dateStyle="long" var="parsedDate"/>                                 		
                                  		
                                  			<tr class='datalist'>
                                  			<td><a href="viewMeetingMinutesDetails.html?id=${g.id}">${parsedDate} Meeting Minutes</a></td>                                 				                                      
		                                    <td>${g.mMTitle}</td>  
		                                      <td><a href="viewMeetingMinutesDetails.html?id=${g.id}">Edit</a> | 
		                                      <a class="small" onclick="return confirm('Are you sure you want to DELETE these MEETING MINUTES?');" href='deleteMeetingMinutes.html?mmid=${g.id}'>Del</a>
		                				</tr>
		                				
		                				</c:if>
                                  		</c:forEach>
                                  		</c:when>
                                  		                                  		
                                  		
										<c:otherwise>
											<tr><td colspan='3'>No Meeting Minutes Found.</td></tr>
										</c:otherwise>
									</c:choose>
									
									
									<tr><td colspan=3><br/><img src="../includes/img/bar.png" width=100%><br/></td></tr>
									
									<tr><td colspan=3 class="pageSectionHeader sitesubHeaders">PROGRAMS &amp; HUMAN RESOURCES MINUTES</td></tr>
									<tr class="header">
										<td width="60%">Name</td>										
										<td width="30%">Title</td>																				
										<td width="10%">Options</td>										
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(mms) gt 0}'>
                                  		<c:forEach items='${mms}' var='g'>
                                  		
                                  		<c:if test="${g.meetingCategory.description eq 'PROGRAMS AND HUMAN RESOURCES'}">
                                  		<fmt:formatDate value="${g.mMDate}" type="date" dateStyle="long" var="parsedDate"/>                                 		
                                  		
                                  			<tr class='datalist'>
                                  			<td><a href="viewMeetingMinutesDetails.html?id=${g.id}">${parsedDate} Meeting Minutes</a></td>                                 			
                                  			<td>${g.mMTitle}</td>	                                      
		                                      
		                                      <td><a href="viewMeetingMinutesDetails.html?id=${g.id}">Edit</a> | 
		                                      <a class="small" onclick="return confirm('Are you sure you want to DELETE these MEETING MINUTES?');" href='deleteMeetingMinutes.html?mmid=${g.id}'>Del</a>
		                				</tr>
		                				
		                				</c:if>
                                  		</c:forEach>
                                  		</c:when>
                                  		                                  		
                                  		
										<c:otherwise>
											<tr><td colspan='3'>No Meeting Minutes Found.</td></tr>
										</c:otherwise>
									</c:choose>
									
									<tr><td colspan=3><br/><img src="../includes/img/bar.png" width=100%><br/></td></tr>
									
									<tr><td colspan=3 class="pageSectionHeader sitesubHeaders">FINANCE AND OPERATIONS MINUTES</td></tr>
									<tr class="header">
										<td width="60%">Name</td>										
										<td width="30%">Title</td>																				
										<td width="10%">Options</td>										
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(mms) gt 0}'>
                                  		<c:forEach items='${mms}' var='g'>
                                  		
                                  		<c:if test="${g.meetingCategory.description eq 'FINANCE AND OPERATIONS'}">
                                  		<fmt:formatDate value="${g.mMDate}" type="date" dateStyle="long" var="parsedDate"/>                                 		
                                  		
                                  			<tr class='datalist'>
                                  			<td><a href="viewMeetingMinutesDetails.html?id=${g.id}">${parsedDate} Meeting Minutes</a></td>                                 			
                                  			<td>${g.mMTitle}</td>	                                      
		                                      
		                                      <td><a href="viewMeetingMinutesDetails.html?id=${g.id}">Edit</a> | 
		                                      <a class="small" onclick="return confirm('Are you sure you want to DELETE these MEETING MINUTES?');" href='deleteMeetingMinutes.html?mmid=${g.id}'>Del</a>
		                				</tr>
		                				
		                				</c:if>
                                  		</c:forEach>
                                  		</c:when>
                                  		                                  		
                                  		
										<c:otherwise>
											<tr><td colspan='3'>No Meeting Minutes Found.</td></tr>
										</c:otherwise>
									</c:choose>
									
									<tr><td colspan=3><br/><img src="../includes/img/bar.png" width=100%><br/></td></tr>
									
									<tr><td colspan=3 class="pageSectionHeader sitesubHeaders">NISEP/OTHER</td></tr>
									<tr class="header">
										<td width="60%">Name</td>										
										<td width="30%">Title</td>																				
										<td width="10%">Options</td>										
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(mms) gt 0}'>
                                  		<c:forEach items='${mms}' var='g'>
                                  		
                                  		<c:if test="${(g.meetingCategory.description eq 'NISEP') or (g.meetingCategory.description eq 'OTHER')}">
                                  		<fmt:formatDate value="${g.mMDate}" type="date" dateStyle="long" var="parsedDate"/>                                 		
                                  		
                                  			<tr class='datalist'>
                                  			<td><a href="viewMeetingMinutesDetails.html?id=${g.id}">${parsedDate} Meeting Minutes</a></td>                                 			
                                  			<td>${g.mMTitle}</td>	                                      
		                                      
		                                      <td><a href="viewMeetingMinutesDetails.html?id=${g.id}">Edit</a> | 
		                                      <a class="small" onclick="return confirm('Are you sure you want to DELETE these MEETING MINUTES?');" href='deleteMeetingMinutes.html?mmid=${g.id}'>Del</a>
		                				</tr>
		                				
		                				</c:if>
                                  		</c:forEach>
                                  		</c:when>
                                  		                                  		
                                  		
										<c:otherwise>
											<tr><td colspan='3'>No Meeting Minutes Found.</td></tr>
										</c:otherwise>
									</c:choose>

</table>
<%} %>
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
			
