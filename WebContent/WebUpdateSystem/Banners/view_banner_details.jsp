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
			
			<!-- Add mousewheel plugin (this is optional) -->
		<script type="text/javascript" src="../fancybox/jquery.mousewheel-3.0.6.pack.js"></script>
		<!-- Add fancyBox main JS and CSS files -->
		<script type="text/javascript" src="../fancybox/jquery.fancybox.js?v=2.1.5"></script>
		<link rel="stylesheet" type="text/css" href="../fancybox/jquery.fancybox.css?v=2.1.5" media="screen" />
		<!-- Add Button helper (this is optional) -->
		<link rel="stylesheet" type="text/css" href="../fancybox/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
		<script type="text/javascript" src="../fancybox/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>
		<!-- Add Thumbnail helper (this is optional) -->
		<link rel="stylesheet" type="text/css" href="../fancybox/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
		<script type="text/javascript" src="../fancybox/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>
		<!-- Add Media helper (this is optional) -->
		<script type="text/javascript" src="../fancybox/helpers/jquery.fancybox-media.js?v=1.0.6"></script>
		<script type="text/javascript" src="../js/changepopup.js"></script>	<script src="../js/jquery-ui.js"></script>
			
			
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
	<script type="text/javascript">
			$('document').ready(function(){
				$('tr.datalist:odd').css('background-color', '#E0E0E0');
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
				 <form id="pol_cat_frm" action="updateBanner.html" method="post" ENCTYPE="multipart/form-data">
				<div class="pageTitleHeader siteHeaders">View Banner Details</div>
                      <div class="pageBody">

				<%if(request.getAttribute("msg") != null){%>                    
                       
                    
                    <div class="messageText" align="center"><br>*** <%=(String)request.getAttribute("msg")%> ***</div><p>
                        <div align="center">
                      <a href="addNewBanner.html"><img src="../includes/img/addbanner-off.png" class="img-swap menuImage" title="Add Banner"></a> 
                      <a href="viewBanners.html"><img src="../includes/img/viewbanners-off.png" class="img-swap menuImage" title="View Banners"></a>
                      <a href="../index.jsp"><img src="../includes/img/backtomenu-off.png" class="img-swap menuImage" title="Back to Web Update Menu"></a></div>
                      
                      <br/>&nbsp;<br/>
                    
                    
                    <%} else {%>

   
      <input type="hidden" id="op" name="op" value="CONFIRM">
      <input type="hidden" value="${banner.id}" id="id" name="id">
                     
                     <p>File:<br/>
                      <c:if test="${banner.bannerFile ne null }">	
                   		<img src="/includes/files/banners/img/${banner.bannerFile}" style="max-width:600px;"><br/>
                     </c:if>                      
                     <input type="file" size="60" id="banner_file" name="banner_file"  class="requiredinput">
                     <p>Rotation:<br/>
                     <input type="text" class="requiredinput" id="banner_rotation"  name="banner_rotation" style="width:30px;" value="${banner.bannerRotation}">
                     <p>Banner Link:<br/>
                     <input type="text" class="requiredinput" id="banner_link" name="banner_link"  style="width:250px;"  value="${banner.bannerLink}">
                     
                     <p>Banner Status:<br/>
                     <c:choose>
    					<c:when test="${banner.bannerStatus eq 1}">
    						Disabled<INPUT TYPE="radio" NAME="banner_status" ID="banner_status" VALUE="0">
                      		Enabled<INPUT TYPE="radio" NAME="banner_status"  ID="banner_status" VALUE="1" CHECKED>
    					</c:when>
						<c:otherwise>
    						Disabled<INPUT TYPE="radio" NAME="banner_status" ID="banner_status" VALUE="0" CHECKED>
                      		Enabled<INPUT TYPE="radio" NAME="banner_status"  ID="banner_status" VALUE="1">
    					</c:otherwise>
					</c:choose>

                      <p>Public:
                      <c:choose>
    					<c:when test="${banner.bannerShowPublic eq 1}">
							<INPUT TYPE="checkbox" NAME="banner_show_public" ID="banner_show_public" CHECKED>
    					</c:when>
						<c:otherwise>
							<INPUT TYPE="checkbox" NAME="banner_show_public" ID="banner_show_public">
    					</c:otherwise>
					</c:choose>
                      
                     Staff:
                      
                                            <c:choose>
    					<c:when test="${banner.bannerShowStaff eq 1}">
							<INPUT TYPE="checkbox" NAME="banner_show_staff" ID="banner_show_staff" CHECKED>
    					</c:when>
						<c:otherwise>
							<INPUT TYPE="checkbox" NAME="banner_show_staff" ID="banner_show_staff">
    					</c:otherwise>
					</c:choose>
                      
                      Show Business:
                      
                                                                  <c:choose>
    					<c:when test="${banner.bannerShowBusiness eq 1}">
							<INPUT TYPE="checkbox" NAME="banner_show_business" ID="banner_show_business" CHECKED>
    					</c:when>
						<c:otherwise>
							<INPUT TYPE="checkbox" NAME="banner_show_business" ID="banner_show_business">
    					</c:otherwise>
					</c:choose>
                      
                      
                      
                      <p>Code:<br/>
                      <input type="text" class="requiredinput" id="banner_code" name="banner_code"  style="width:250px;" value="${banner.bannerCode}">
                    
                    
                    
                   <p><button id="butSave">Update</button>
                   
                   
                   </div>
                   
                   <p>
                   
                   <div align="center">
					<A HREF='viewBanners.html'><img src="../includes/img/back-off.png" border="0" class="img-swap"></a>
								
					</div>
                   
                   <% } %>
                   
    </form>
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