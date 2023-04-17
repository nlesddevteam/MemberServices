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
					com.esdnl.util.*"%> 
					    
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  					               
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="WEBMAINTENANCE-VIEW,WEBMAINTENANCE-ANNOUNCEMENTS" />
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
				
				<div class="pageTitleHeader siteHeaders">Add New News Postings</div>
                      <div class="pageBody">
                      
                      When you select a category, only Good News, Important Announcement, Announcements, and Staff News are currently implemented on the NLESD website.<p>
    <%if(request.getAttribute("msg") != null){%>
                      
                        <div class="messageText" align="center"><br>*** <%=(String)request.getAttribute("msg")%> ***</div><p>
                        <div align="center">
                      <a href="addNewNewsPostings.html"><img src="../includes/img/addanother-off.png" class="img-swap menuImage" title="Add Another News Item"></a> 
                      <a href="viewNewsPostings.html"><img src="../includes/img/viewnews-off.png" class="img-swap menuImage" title="View News Items"></a>
                      <a href="../index.jsp"><img src="../includes/img/backtomenu-off.png" class="img-swap menuImage" title="Back to Web Update Menu"></a></div>
                      
                      <br/>&nbsp;<br/>
                    <%}
				 else {
                    %>
    
    <form id="pol_cat_frm" action="addNewNewsPostings.html" method="post" ENCTYPE="multipart/form-data">
      <input type="hidden" id="op" name="op" value="CONFIRM">
    
                    <p>Date:<br/><input type="text" class="requiredinput" id="news_date" name="news_date"  style="width:250px;">                      
                    <p>Category:<br/>                      
                       <select id="news_category" name="news_category" class="requiredinput">
						<c:forEach var="item" items="${categorylist}">
    						<option value="${item.key}">${item.value}</option>
						</c:forEach>
                        </select>
                    <p>Location:<br/>
                       <select id="news_location" name="news_location" class="requiredinput">
                       		<option value='-1'>NONE</option>
                       		<c:forEach var="item" items="${locationlist}">
    							<option value="${item.locationId}">${item.locationDescription}</option>
							</c:forEach>
                        </select>                     
                    <p>Title: (Max 60 characters)<br/><input type="text" class="requiredinput" id="news_title"  name="news_title" style="width:350px;" maxlength="60" >
                    <p>Details:<br/>
                    <textarea id="news_description" name="news_description"></textarea>
                  	<p>Photo:<br/><input type="file" id="news_photo" name="news_photo"  class="requiredinput">                   
                   	<p>Caption:<br/><input type="text" class="requiredinput" id="news_photo_caption"  name="news_photo_caption" style="width:350px;">
                   	<p>Documentation: (PDF)<br/><input type="file" id="news_documentation" name="news_documentation"  class="requiredinput">                   
                   	<p>External Link:<br/><input type="text" class="requiredinput" id="news_external_link"  name="news_external_link" style="width:250px;" placeholder="http://">
                   	<p>Link Title:<br/><input type="text" class="requiredinput" id="news_external_link_title" name="news_external_link_title"  style="width:250px;">
                   	<p>Status:<br/>                      
                       <select id="news_status" name="news_status" class="requiredinput">
						<c:forEach var="item" items="${statuslist}">
    						<option value="${item.key}">${item.value}</option>
						</c:forEach>
                        </select>
                    <p><%if(request.getAttribute("msg") != null){%>
                     <span style='color:#FF0000;font-weight:bold;'>*** <%=(String)request.getAttribute("msg")%> ***</span>
                    <%}%>
                    
                    
                    <p><button id="butSave">Add This News Posting</button><br><br>
                      

          
    </form>
    
    <div align="center">
					<A HREF='viewNewsPostings.html'><img src="../includes/img/back-off.png" border="0" class="img-swap"></a>
								
					</div>
    
    <%} %>
    
    
    </div>
    
     <script>
    CKEDITOR.replace( 'news_description' );
    </script>
	
	
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