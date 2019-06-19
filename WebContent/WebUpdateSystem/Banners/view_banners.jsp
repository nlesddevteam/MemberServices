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
				
				<div class="pageTitleHeader siteHeaders">View Banners</div>
                      <div class="pageBody">
                      <div align="center">
					<a href="addNewBanner.html"><img src="../includes/img/addbanner-off.png" class="img-swap menuImage" title="Add Banner"></a>&nbsp;<a href="viewBanners.html"><img src="../includes/img/viewbanners-off.png" class="img-swap menuImage" title="View Banners"></a>&nbsp;<a href="../index.jsp"><img src="../includes/img/backtomenu-off.png" class="img-swap menuImage" title="Back to Web Update Menu"></a>
					</div><p>
                      
								<%if(request.getAttribute("msg")!=null){%>
									<div class="messageText" align="center">
										*** <%=(String)request.getAttribute("msg")%> ***
									</div>	
                             		 <%} else { %>  
					
					
					
					
					<br>
					<table class="blogTable">
					
					
					<tr><td colspan=6 class="pageSectionHeader sitesubHeaders">PUBLIC ENABLED BANNERS</td></tr>
									<tr class="header">
										<th width="65%">File / Web Link</th>
										<th width="5%">Order</th>									
										
										<th width="5%">Public</th>
										<th width="5%">Staff</th>
										<th width="5%">Bus</th>										
										<th width="10%">Options</th>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(banners) gt 0}'>
                                  		<c:forEach items='${banners}' var='g'>
                                  		
                                  		<c:if test="${(g.bannerStatus eq '1')  and (g.bannerShowPublic eq '1')}">
                                  		
                                  			<tr class='datalist'>
                                  			<td><a href="${g.bannerLink}"><img src="/includes/files/banners/img/${g.bannerFile}" style="width:100%;height:auto;"></a></td>
		                                      <td style="text-align:center">${g.bannerRotation}</td>
		                                      <td style="text-align:center">
		                                      <c:if test="${g.bannerShowPublic eq '0'}">No</c:if>
		                                      <c:if test="${g.bannerShowPublic eq '1'}">Yes</c:if>
		                                      </td>
		                                      <td style="text-align:center">
		                                      <c:if test="${g.bannerShowStaff eq '0'}">No</c:if>
		                                      <c:if test="${g.bannerShowStaff eq '1'}">Yes</c:if>
		                                      </td>
		                                      <td style="text-align:center">
		                                      <c:if test="${g.bannerShowBusiness eq '0'}">No</c:if>
		                                      <c:if test="${g.bannerShowBusiness eq '1'}">Yes</c:if>		                                      
		                                      </td>		                                      
		                                      <td style="text-align:center"><a href="viewBannerDetails.html?id=${g.id}">Edit</a> | 		                                      
		                                      <a class="small" onclick="return confirm('Are you sure you want to DELETE this BANNER?');" href='deleteBanner.html?bid=${g.id}'>Del</a>
		                				</tr>
		                				</c:if>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='6'>No Banners Found.</td></tr>
										</c:otherwise>
									</c:choose>
									

										<tr><td colspan=6><br/>&nbsp;<br/><img src="../includes/img/bar.png" width=100%><br/>&nbsp;<br/></td></tr>
					
					
					
					
					
					
					
					
					
					
									<tr><td colspan=6 class="pageSectionHeader sitesubHeaders" style="color:#1F4279;">STAFF ROOM ENABLED BANNERS</td></tr>
									<tr class="header" style="background:#1F4279;">
										<th width="65%">File / Web Link</th>
										<th width="5%">Order</th>									
										
										<th width="5%">Public</th>
										<th width="5%">Staff</th>
										<th width="5%">Bus</th>										
										<th width="10%">Options</th>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(banners) gt 0}'>
                                  		<c:forEach items='${banners}' var='g'>
                                  		
                                  		<c:if test="${(g.bannerStatus eq '1') and (g.bannerShowStaff eq '1') }">
                                  		
                                  			<tr class='datalist'>
                                  			<td><a href="${g.bannerLink}"><img src="/includes/files/banners/img/${g.bannerFile}" style="width:100%;height:auto;"></a></td>
		                                      <td style="text-align:center">${g.bannerRotation}</td>
		                                      <td style="text-align:center">
		                                      <c:if test="${g.bannerShowPublic eq '0'}">No</c:if>
		                                      <c:if test="${g.bannerShowPublic eq '1'}">Yes</c:if>
		                                      </td>
		                                      <td style="text-align:center">
		                                      <c:if test="${g.bannerShowStaff eq '0'}">No</c:if>
		                                      <c:if test="${g.bannerShowStaff eq '1'}">Yes</c:if>
		                                      </td>
		                                      <td style="text-align:center">
		                                      <c:if test="${g.bannerShowBusiness eq '0'}">No</c:if>
		                                      <c:if test="${g.bannerShowBusiness eq '1'}">Yes</c:if>		                                      
		                                      </td>		                                      
		                                      <td style="text-align:center"><a href="viewBannerDetails.html?id=${g.id}">Edit</a> | 		                                      
		                                      <a class="small" onclick="return confirm('Are you sure you want to DELETE this BANNER?');" href='deleteBanner.html?bid=${g.id}'>Del</a>
		                				</tr>
		                				</c:if>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='6'>No Banners Found.</td></tr>
										</c:otherwise>
									</c:choose>
									
									
									<tr><td colspan=6><br/>&nbsp;<br/><img src="../includes/img/bar.png" width=100%><br/>&nbsp;<br/></td></tr>
					
					
					
					
					
					
					
					
					
					
									<tr><td colspan=6 class="pageSectionHeader sitesubHeaders" style="color:#E6A75A;">BUSINESS ENABLED BANNERS</td></tr>
									<tr class="header" style="background:#E6A75A;">
										<th width="65%">File / Web Link</th>
										<th width="5%">Order</th>									
										
										<th width="5%">Public</th>
										<th width="5%">Staff</th>
										<th width="5%">Bus</th>										
										<th width="10%">Options</th>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(banners) gt 0}'>
                                  		<c:forEach items='${banners}' var='g'>
                                  		
                                  		<c:if test="${(g.bannerStatus eq '1') and (g.bannerShowBusiness eq '1') }">
                                  		
                                  			<tr class='datalist'>
                                  			<td><a href="${g.bannerLink}"><img src="/includes/files/banners/img/${g.bannerFile}" style="width:100%;height:auto;"></a></td>
		                                      <td style="text-align:center">${g.bannerRotation}</td>
		                                      <td style="text-align:center">
		                                      <c:if test="${g.bannerShowPublic eq '0'}">No</c:if>
		                                      <c:if test="${g.bannerShowPublic eq '1'}">Yes</c:if>
		                                      </td>
		                                      <td style="text-align:center">
		                                      <c:if test="${g.bannerShowStaff eq '0'}">No</c:if>
		                                      <c:if test="${g.bannerShowStaff eq '1'}">Yes</c:if>
		                                      </td>
		                                      <td style="text-align:center">
		                                      <c:if test="${g.bannerShowBusiness eq '0'}">No</c:if>
		                                      <c:if test="${g.bannerShowBusiness eq '1'}">Yes</c:if>		                                      
		                                      </td>		                                      
		                                      <td style="text-align:center"><a href="viewBannerDetails.html?id=${g.id}">Edit</a> | 		                                      
		                                      <a class="small" onclick="return confirm('Are you sure you want to DELETE this BANNER?');" href='deleteBanner.html?bid=${g.id}'>Del</a>
		                				</tr>
		                				</c:if>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='6'>No Banners Found.</td></tr>
										</c:otherwise>
									</c:choose>
									
									
										<tr><td colspan=6><br/>&nbsp;<br/><img src="../includes/img/bar.png" width=100%><br/>&nbsp;<br/></td></tr>
									
									<tr><td colspan=6 class="pageSectionHeader sitesubHeaders" style="color:Red;">DISABLED BANNERS</td></tr>
									<tr class="header" style="background:Red;">
										<th width="65%">File / Web Link</th>
										<th width="5%">Order</th>										
										
										<th width="5%">Public</th>
										<th width="5%">Staff</th>
										<th width="5%">Bus</th>										
										<th width="10%">Options</th>
									</tr>

									<c:choose>
	                                  	<c:when test='${fn:length(banners) gt 0}'>
                                  		<c:forEach items='${banners}' var='g'>
                                  		<c:if test="${g.bannerStatus eq '0'}">
                                  			<tr class='datalist'>
                                  			<td><a href="${g.bannerLink}"><img src="/includes/files/banners/img/${g.bannerFile}" style="width:100%;height:auto;"></a></td>
		                                      <td style="text-align:center">${g.bannerRotation}</td>
		                                       <td style="text-align:center">
		                                      <c:if test="${g.bannerShowPublic eq '0'}">No</c:if>
		                                      <c:if test="${g.bannerShowPublic eq '1'}">Yes</c:if>
		                                      </td>
		                                      <td style="text-align:center">
		                                      <c:if test="${g.bannerShowStaff eq '0'}">No</c:if>
		                                      <c:if test="${g.bannerShowStaff eq '1'}">Yes</c:if>
		                                      </td>
		                                      <td style="text-align:center">
		                                      <c:if test="${g.bannerShowBusiness eq '0'}">No</c:if>
		                                      <c:if test="${g.bannerShowBusiness eq '1'}">Yes</c:if>		                                      
		                                      </td>		              		                                      
		                                      <td style="text-align:center"><a href="viewBannerDetails.html?id=${g.id}">Edit</a> | 		                                      
		                                      <a class="small" onclick="return confirm('Are you sure you want to DELETE this BANNER?');" href='deleteBanner.html?bid=${g.id}'>Del</a>
		                				</tr>
		                				</c:if>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='6'>No Banners Found.</td></tr>
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
			