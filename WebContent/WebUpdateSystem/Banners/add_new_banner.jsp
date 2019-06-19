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
				
				<div class="pageTitleHeader siteHeaders">Add Banner</div>
                      <div class="pageBody">

					<%if(request.getAttribute("msg") != null){%>
                      
                        <div class="messageText" align="center"><br>*** <%=(String)request.getAttribute("msg")%> ***</div><p>
                        <div align="center">
                      <a href="addNewBanner.html"><img src="../includes/img/addanother-off.png" class="img-swap menuImage" title="Add Another Banner"></a> 
                      <a href="viewBanners.html"><img src="../includes/img/viewbanners-off.png" class="img-swap menuImage" title="View Banners"></a>
                      <a href="../index.jsp"><img src="../includes/img/backtomenu-off.png" class="img-swap menuImage" title="Back to Web Update Menu"></a></div>
                      
                      <br/>&nbsp;<br/>
                    <%}
				 else {
                    %>


		Banner file MUST BE 900 x 200 pixles in size.

    <form id="pol_cat_frm" action="addNewBanner.html" method="post" ENCTYPE="multipart/form-data">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      
      <p>File (png or jpg):<br/>
      <input type="file" style="width:250px;" id="banner_file" name="banner_file"  class="requiredinput">
      
      <p>Rotation Order (#):<br/>
      <input type="text" class="requiredinput" id="banner_rotation"  name="banner_rotation" style="width:30px;">
      
      <p>Web Link:<br/>
      <input type="text" class="requiredinput" id="banner_link" name="banner_link"  style="width:250px;">
      
      <p>Status:<br/>
      <INPUT TYPE="radio" NAME="banner_status" ID="banner_status" VALUE="1" CHECKED> Enabled<br/>
      <INPUT TYPE="radio" NAME="banner_status" ID="banner_status" VALUE="0"> Disabled<br/>
                      
                      
      <p><INPUT TYPE="checkbox" NAME="banner_show_public" ID="banner_show_public">Public <INPUT TYPE="checkbox" NAME="banner_show_staff" ID="banner_show_staff"> Staff <INPUT TYPE="checkbox" NAME="banner_show_business" ID="banner_show_business">Business
                      
                     
      <p>Code:<br/>
                     <input type="text" class="requiredinput" id="banner_code" name="banner_code"  style="width:250px;">
                     
                    
                                      
                  <p><button id="butSave">Add Banner</button>
    </form>
    
    <br/><br/>
    <div align="center">
					<A HREF='viewBanners.html'><img src="../includes/img/back-off.png" border="0" class="img-swap"></a>
								
					</div>
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
   