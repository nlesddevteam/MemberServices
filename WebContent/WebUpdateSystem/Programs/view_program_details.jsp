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
				 <form id="pol_cat_frm" action="updateProgram.html" method="post" ENCTYPE="multipart/form-data">
				<div class="pageTitleHeader">View Program Descriptor Details</div>
                      <div class="pageBody">
                      
                      
    
      <input type="hidden" id="op" name="op" value="CONFIRM">
      <input type="hidden" value="${program.id}" id="id" name="id">
                      
                      Title:<br/>
                      <input type="text" class="requiredinput" id="descriptor_title"  name="descriptor_title" style="width:250px;" value="${program.descriptorTitle}">
                      
                      Region:
                            <select id="program_region" name="program_region" class="requiredinput">
								<c:forEach var="item" items="${programsregions}">
    								<c:choose>
    									<c:when test="${item.key eq program.pRegion.value}">
       										<option value="${item.key}" selected="selected">${item.value}</option>
    								</c:when>
    								<c:otherwise>
        								<option value="${item.key}">${item.value}</option>
    								</c:otherwise>
    							</c:choose>
							</c:forEach>
                        </select>
                     
                     Level
                            <select id="program_level" name="program_level" class="requiredinput">
								<c:forEach var="item" items="${programslevels}">
    								<c:choose>
    									<c:when test="${item.key eq program.pLevel.value}">
       										<option value="${item.key}" selected="selected">${item.value}</option>
    								</c:when>
    								<c:otherwise>
        								<option value="${item.key}">${item.value}</option>
    								</c:otherwise>
    							</c:choose>
							</c:forEach>
                        </select>
                      
                      Program Status:
                     <c:choose>
    					<c:when test="${program.programStatus eq 1}">
    						Disabled<INPUT TYPE="radio" NAME="program_status" ID="program_status" VALUE="0">
                      		Enabled<INPUT TYPE="radio" NAME="program_status"  ID="program_status" VALUE="1" CHECKED>
    					</c:when>
						<c:otherwise>
    						Disabled<INPUT TYPE="radio" NAME="program_status" ID="program_status" VALUE="0" CHECKED>
                      		Enabled<INPUT TYPE="radio" NAME="program_status"  ID="program_status" VALUE="1">
    					</c:otherwise>
					</c:choose>

                     
                    <%if(request.getAttribute("msg") != null){%>
                     
                        <span class="message_info">*** <%=(String)request.getAttribute("msg")%> ***</span>
                     
                    <%}%>
                    
                   
                        <button id="butSave">Update Program</button>
                        
                     </div>   
					<!-- <table align="center" width="70%" cellspacing="1" style="font-size: 11px;" cellpadding="1" border="1" id="showlists">
					<tr><th colspan='3'>Other Program Files</th>
					<th colspan='2'><a class="fancybox" href="#inline1" title="Add Other Program File" onclick="OpenPopUp('${program.id}');">Add File</a>
					</th></tr>
					<tr>
					<th>Program File Title</th>
					<th>Program File Document</th>
					<th>Added By</th>
					<th>Date Added</th>
					<th></th>
					</tr>
					<c:forEach var="p" items="${otherfiles}" varStatus="counter">
						<tr>
						<td>${p.pfTitle}</td>
						<td>${p.pfDoc}</td>
						<td>${p.addedBy}</td>
						<td>${p.dateAddedFormatted}</td>
						<td>
						<a class="small" onclick="return confirm('Are you sure you want to DELETE this document?');" href='deleteOtherProgramDocument.html?id=${p.id}&fid=${p.pfDoc}&pid=${p.programsId}'>Delete File</a>
		                                      
						</td>
						</tr>
					</c:forEach>
					</table>
				 -->
			</form>	
				
      <!-- <div id="inline1" style="width:400px;display: none;">
		
			<span class="headertitle">Add Other Program File</span>
			<table width="300px;" cellpadding="0" cellspacing="3" align="center" border="0" style="padding: 5px;">

				<tr>
					<td class="subheader" valign="middle" width='125px'>
						Other Program File Title:
					</td>
					<td>
						<input type="text" class="requiredinput" id="other_program_title"  name="other_program_title" style="width:250px;" >
					</td>
				</tr>
				<tr>
					<td class="subheader" valign="middle" width='125px'>
						Other Program File:
					</td>
					<td>
						<input type="file"  id="other_program_file" name="other_program_file"  class="requiredinput">
					</td>
				</tr>				
				
				<tr>
					<td colspan="2" valign="middle" align="center">
						<input type="button" value="Add File" onclick="sendprograminfo();"/>
						<input type="button" value="Cancel" onclick="closewindow();"/>

					</td>
				</tr>
		</table>
	</div> -->
	 <br/><br/>
		<div align="center">
					<A HREF='javascript:history.go(-1)'><img src="../includes/img/back-off.png" border="0" class="img-swap"></a>
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