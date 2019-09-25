<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         		 com.awsd.school.*,
         		 com.awsd.personnel.*,                 
                 com.awsd.common.*,
		         java.util.*,
		         java.io.*,
		         java.text.*,
		         com.esdnl.util.*"%>  


<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%
  User usr = null;
  usr = (User) session.getAttribute("usr"); 
  Collection<School> schools = null;
  schools = SchoolDB.getSchools();	
  
  
%>
<c:set var='schools' value="<%= schools %>" />
<c:set var="zonePerm" value='all' />
<!-- Check if not assigned to a office -->
<%	if(usr.getPersonnel().getSchool().getSchoolID() !=277) { %>
<c:set var="updaterSchoolName" value='<%=  usr.getPersonnel().getSchool() !=null?usr.getPersonnel().getSchool().getSchoolName() : "" %>' />  
<% }else{ %>
<c:set var="updaterSchoolName" value="" /> 
<%} %>



<esd:SecurityCheck permissions='WEBMAINTENANCE-SCHOOLPROFILE-PRINCIPAL,WEBMAINTENANCE-SCHOOLPROFILE-SECRETARY,WEBMAINTENANCE-SCHOOLPROFILE-ADMIN' />
<html>

	<head>
		<title>NLESD - Web Update Posting System</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		 <link rel="stylesheet" href="../includes/css/jquery-ui-1.10.3.custom.css" >
		<link href="../includes/css/ms.css" rel="stylesheet" type="text/css">	
			<script src="../includes/js/jquery-1.7.2.min.js"></script>
			<script src="../includes/js/jquery-1.9.1.js"></script>
			<script src="../includes/js/jquery-ui-1.10.3.custom.js"></script>
			<script type="text/javascript" src="../includes/js/common.js"></script>
			
			<link rel="stylesheet" href="../includes/css/jquery-ui.css" />
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>	
		

	
	<style>
	
.schoollistDocLinkContainer {float:left;width:160px;text-align:center;}
.schoollistHeader {color: #007F01;font-weight: bold;}
.schoollist .row .email a { color:#007F01;  text-decoration: none;}
.schoollist .row .email a:visited { color:#007F01;  text-decoration: none;}
.schoollist .row .email a:hover {	color: #FF0000;	 text-decoration: underline;}
.schoollist .row .email a:active { color: #FF0000; text-decoration: none;}
.schoollist {display:table;  width:100%;font-size:11px;}
.schoollist .table {display:table;}
.schoollist .rowCaption {caption-side: top; display: table-caption;width:100%;text-align:left; font-weight:bold;color:#007F01;margin-bottom:3px;}
.schoollist .row { display:table-row; width:100%;}
.schoollist .column { display: table-cell;border-bottom:1px solid grey;padding:2px;}
.schoollist .row .header {text-align:left; color:white; font-weight:bold; }
.schoollist .row .SchoolName {width:35%;vertical-align: text-top;}
.schoollist .row .SchoolRegion { width:25%;border-left:1px solid grey;vertical-align: text-top;}
.schoollist .row .SchoolLocation { width:25%;border-left:1px solid grey;vertical-align: text-top;}
.schoollist .row .SchoolOptions { width:15%;border-left:1px solid grey;vertical-align: text-top;text-align:center;}

div.schoollist > div:nth-of-type(odd) {background: #FDF5E6;}
	
.regionDefault {background-color: #1F4279;}
.region1 {background-color:rgba(191, 0, 0, 0.1);}
.region1a {border-left:10px solid rgba(191, 0, 0, 0.3);}
.region1solid {background-color:rgba(191, 0, 0, 1);}
.region1half {background-color:rgba(191, 0, 0, 0.5);}
.region2 {background-color:rgba(0, 191, 0, 0.1);}
.region2a {border-left:10px solid rgba(0, 191, 0, 0.3);}
.region2solid {background-color:rgba(0, 191, 0, 1);}
.region2half {background-color:rgba(0, 191, 0, 0.5);}
.region3 {background-color:rgba(255, 132, 0, 0.1);}
.region3solid {background-color:rgba(255, 132, 0, 1);}
.region3half {background-color:rgba(255, 132, 0, 0.5);}
.region3a {border-left:10px solid rgba(255, 132, 0, 0.3);}
.region4 {background-color:rgba(127, 130, 255, 0.1);}
.region4a {border-left:10px solid rgba(127, 130, 255, 0.3);}
.region4solid {background-color:rgba(127, 130, 255,1);}
.region4half {background-color:rgba(127, 130, 255, 0.5);}	
	</style>
	
	
	</head>

  <body><br/>
  
  <div class="mainContainer">

  	   	<div class="section group">
	   		
	   		<div class="col full_block topper">
	   		<div class="toppertextleft">Welcome <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></div>
	   		<div class="toppertextright"><script src="../includes/js/date.js"></script></div>	   		
			</div>
			
			<div class="full_block center">
				<img src="/MemberServices/WebUpdateSystem/SchoolProfiles/img/header.png" alt="" width="90%" border="0"><br/>				
			</div>

			<div class="col full_block content">
				<div class="bodyText">						
				
				<div class="pageTitleHeader siteHeaders">School Profile Update</div>
                      <div class="pageBody">
                      
                      <p>
					
					  Below is a list of schools you are currently permitted to update for the NLESD School Profile page(s) (depending on your permissions). <p>
					  If you are NOT the Principal or Assistant Principal for the following school(s), DO NOT make any changes and please contact mssupport@nlesd.ca.
					  You may notice who last updated your school. This can be anyone who updates any files relating to your profile page. It doesnt mean that the entire or even any of the actual profile page information is correct and updated entirely. The school admins must make sure all info is update and correct.
                 <p>
                 
                                 
					
					 <div align="center">
					 <esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-SCHOOLPROFILE-ADMIN">					 
					 	<a href='addSchool.html' class="btn btn-sm btn-success" style="color:White;" title="Add School">+ Add School</a> &nbsp; 
					 </esd:SecurityAccessRequired> 
					 <a href="/MemberServices/navigate.jsp" class="btn btn-sm btn-danger" style="color:White;" title="Back to Member Services">Back to Member Services</a></div>
					 <p>
                      
									<%if(request.getAttribute("msg")!=null){%>								
														
							<div id="fadeMessage" style="background-color: #e6ffe6;color:Green;margin-top:10px;margin-bottom:10px;padding:5px;text-align:center;">${ msg } </div>   									
									
                             		 <%}%>   
									
									<p>
							 
     							
	     								
 
  	
  <div class="row">	
	 <div class="schoollist">	
  	
  		
     
  
   
     <div class="row">
									   		<div class="column header SchoolName region1solid">SCHOOL (ID)</div>	
									        <div class="column header SchoolRegion region1solid">REGION/ZONE</div>									        
											<div class="column header SchoolLocation region1solid">TOWN/CITY</div>											
											<div class="column header SchoolOptions region1solid">OPTIONS</div>   
	</div>  
	
	
	
	
								<c:forEach items='${ schools }' var='school'>
                    				<c:choose>                    				
   									<c:when test="${fn:containsIgnoreCase(school.schoolName, updaterSchoolName)}">
               							<div class='row contact-row'> 
	     									<div class="column SchoolName"> 											
	     									${ school.schoolName } (${ school.schoolDeptID })<br/>
	     									<span style="color:Grey;font-size:10px;">Last Updated by: <span style="text-transform:Capitalize;">${school.detailsOther.addedBy }</span> on ${school.detailsOther.getDateAddedFormatted()}</span>
	     									</div>	
									        <div class="column SchoolRegion">
									        <span style="text-transform:capitalize;">${ school.zone } (${ school.region.name })</span>
									        </div>									        
											<div class="column SchoolLocation">${ school.townCity } </div>    
											
											<div class="column SchoolOptions">
												<a href="editSchool.html?id=${school.schoolID}" class="btn btn-primary btn-xs" style="color:white;">EDIT</a>
											</div>   
										</div>
									</c:when>
									
									</c:choose>
								</c:forEach> 
  
	
	<!-- Sorry, you do not have permission to update a school profile. If you believe this to be in error, please contact mssupport@nlesd.ca.-->
   
	

   
  <br/>&nbsp;<br/>
     
  
 
  
     
     
      </div>
</div>  							
     							
     							
  								
    
     
     
     							
  			
					

								
</div>
    
    	
	
		<br/><br/>
		
			</div>
			</div>
<div style="float:right;padding-right:3px;width:25%;text-align:right;"><a href="../../navigate.jsp" title="Back to MemberServices Main Menu"><img src="../includes/img/ms-footerlogo.png" border=0></a></div>
		<div class="section group">
			<div class="col full_block copyright">&copy; 2019 Newfoundland and Labrador English School District</div>
		</div>	
</div>
  
</div>
    <br/>
  </body>



<script>
$(document).ready(function(){
	
//Write the variables to divs	
	$('.regionsAllowed').html('${region1Allowed} ${region2Allowed} ${region3Allowed} ${region4Allowed}');
	$('#fadeMessage').delay('3000').fadeOut();

});
</script>
</html>	
			

			