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
    		    $( "#mh_date" ).datepicker({
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
				
				<div class="pageTitleHeader siteHeaders">Add Meeting Highlight</div>
                      <div class="pageBody">

				<%if(request.getAttribute("msg") != null){%>
                      
                        <div class="messageText" align="center"><br>*** <%=(String)request.getAttribute("msg")%> ***</div><p>
                        <div align="center">
                      <a href="addNewMeetingHighlights.html"><img src="../includes/img/addanother-off.png" class="img-swap menuImage" title="Add Another Meeting Highlight"></a> 
                      <a href="viewMeetingHighlights.html"><img src="../includes/img/viewhighlights-off.png" class="img-swap menuImage" title="View Meeting Highlights"></a>
                      <a href="../index.jsp"><img src="../includes/img/backtomenu-off.png" class="img-swap menuImage" title="Back to Web Update Menu"></a></div>
                      
                      <br/>&nbsp;<br/>
                    <%}
				 else {
                    %>


    <form id="pol_cat_frm" action="addNewMeetingHighlights.html" method="post" ENCTYPE="multipart/form-data">
      <input type="hidden" id="op" name="op" value="CONFIRM">
      
      
      <p>Title:<br/>
      <input type="text" class="requiredinput" id="mh_title"  name="mh_title" style="width:250px;">
      <p>Date:<br/>
      <input type="text" class="requiredinput" id="mh_date" name="mh_date"  style="width:250px;">
      <p>Document:<br/>
      <input type="file" size="60" id="mm_doc" name="mh_doc"  class="requiredinput">      
      <p>Presentation Title (If Any):<br/>
      <input type="text" class="requiredinput" id="mh_pre_title" name="mh_pre_title" style="width:250px;">
      <p>Presentation File:<br/>
      <input type="file" size="60" id="mh_pre_doc" name="mh_pre_doc"  class="requiredinput">      
      <p>Related Document Title:<br/>
      <input type="text" class="requiredinput" id="mh_r_doc_title" name="mh_r_doc_title" style="width:250px;">
      <p>Related Document File:<br/>
      <input type="file" size="60" id="mh_r_doc" name="mh_r_doc"  class="requiredinput">
     	<p>Meeting Video:<br/>
      <input type="text" class="requiredinput" id="mh_meeting_video"  name="mh_meeting_video" style="width:250px;">
   
 
                    
                    <%if(request.getAttribute("msg") != null){%>
                      
                        <p><span class="message_info"><br>*** <%=(String)request.getAttribute("msg")%> ***</span>
                      
                    <%}%>
                    
                  <p> <button id="butSave">Add Highlight</button>
</form>
    
     <br/><br/>
		<div align="center">
					<A HREF='viewMeetingHighlights.html'><img src="../includes/img/back-off.png" border="0" class="img-swap"></a>
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