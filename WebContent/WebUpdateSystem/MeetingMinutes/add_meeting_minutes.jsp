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
    		    $( "#mm_date" ).datepicker({
    		      changeMonth: true,//this option for allowing user to select month
    		      changeYear: true, //this option for allowing user to select from year range
    		      dateFormat: "dd/mm/yy"
    		    });
    		  }

	);

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
				
				<div class="pageTitleHeader siteHeaders">Add Meeting Minute</div>
                      <div class="pageBody">

				<%if(request.getAttribute("msg") != null){%>
                      
                        <div class="messageText" align="center"><br>*** <%=(String)request.getAttribute("msg")%> ***</div><p>
                        <div align="center">
                      <a href="addNewMeetingMinutes.html"><img src="../includes/img/addanother-off.png" class="img-swap menuImage" title="Add Another Meeting Minute"></a> 
                      <a href="viewMeetingMinutes.html"><img src="../includes/img/viewminutes-off.png" class="img-swap menuImage" title="View Meeting Minutes"></a>
                      <a href="../index.jsp"><img src="../includes/img/backtomenu-off.png" class="img-swap menuImage" title="Back to Web Update Menu"></a></div>
                      
                      <br/>&nbsp;<br/>
                    <%}
				 else {
                    %>





    <form id="pol_cat_frm" action="addNewMeetingMinutes.html" method="post" ENCTYPE="multipart/form-data">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      
      <p>Title:<br/>
      <input type="text" class="requiredinput" id="mm_title"  name="mm_title" style="width:250px;">
      
      <p>Minute Category:<br/>
      <select id="meeting_category" name="meeting_category" class="requiredinput">
            				<c:forEach var="item" items="${categorylist}" >
                				<option value="${item.key}">${item.value}</option>
            				</c:forEach>
                        </select>
                     
                     <p>Date:<br/>
                     <input type="text" class="requiredinput" id="mm_date" name="mm_date"  style="width:250px;">
                     
                     <p>Document:<br/>
                     <input type="file" style="width:250px;" id="mm_doc" name="mm_doc"  class="requiredinput">

					<p>Presentation Title:<br/>
					<input type="text" class="requiredinput" id="mm_rel_pre_title" name="mm_rel_pre_title" style="width:250px;">
					
					<p>Presentation File (PDF):<br/>
					<input type="file" style="width:250px;" id="mm_rel_pre_doc" name="mm_rel_pre_doc"  class="requiredinput">
					
					<p>Related Documentation Title:<br/>
					<input type="text" class="requiredinput" id="mm_rel_doc_title" name="mm_rel_doc_title" style="width:250px;">
					
					<p>Related Documentation File:<br/>
                      <input type="file" style="width:250px;" id="mm_rel_doc" name="mm_rel_doc"  class="requiredinput">  
       				<p>Meeting Video YouTube Link:<br/>
      				<input type="text" class="requiredinput" id="mm_meeting_video"  name="mm_meeting_video" style="width:250px;">
                    
                    <%if(request.getAttribute("msg") != null){%>
                      
                       <p> <span class="message_info">*** <%=(String)request.getAttribute("msg")%> ***</span>
                     
                    <%}%>
                    
                   <p><button id="butSave">Add New</button>
                   
    </form>
    <br/><br/>
		<div align="center">
					<A HREF='viewMeetingMinutes.html'><img src="../includes/img/back-off.png" border="0" class="img-swap"></a>
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
    