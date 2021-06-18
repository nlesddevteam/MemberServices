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
   	<style>
		input {border: 1px solid silver;}	
		.dataTables_length,.dt-buttons {float:left;}
		.choices__list--multiple .choices__item {background-color:#004178;border:1px solid #004178;}
		</style>
		
	<script>	
		
	$('document').ready(function(){
		aTable = $(".schoolTable").dataTable({
			"order" : [[1,"asc"]],			
			  "paging":   false,
			  "searching": true,			 
				responsive: true,
				"lengthChange": false,
				"columnDefs": [
					 {
			                "targets": [0,6],			               
			                "searchable": false,
			                "visible": true
			            },{
			            	 "targets": [5,6],			               
				                "sortable": false	              
			            }
			        ]
		});
		
		$("tr").not(':first').hover(
				  function () {
				    $(this).css("background","yellow");
				  }, 
				  function () {
				    $(this).css("background","");
				  }
				);			
		
	
		$(".loadPage").show();
		$(".loadingTable").css("display","none");
		$("#loadingSpinner").css("display","none");
	});

	
	
	
		
		</script>
		
	<c:set var="countAvalonSchools" value="0" />
	<c:set var="countCentralSchools" value="0" />
	<c:set var="countWesternSchools" value="0" />	
	<c:set var="countLabradorSchools" value="0" />
	<c:set var="countProvincialSchools" value="0" />	
	<c:set var="countOtherSchools" value="0" />
	</head>

  <body>
  
   <div class="siteHeaderGreen">School Directory Administration</div>
<div class="loadingTable" align="center" style="margin-top:10px;margin-bottom:10px;">
<img src="../includes/img/loading4.gif" style="max-width:150px;" border=0/><br/>Loading and Sorting School Data, please wait.<br/>This will take a few moments!
</div>		

<div style="display:none;" class="loadPage"> 
  
  Below is a list of schools you are currently permitted to update for the NLESD School Profile page(s) (depending on your permissions). <p>
  
  If you are NOT the Principal or Assistant Principal for the following school(s), DO NOT make any changes and please contact mssupport@nlesd.ca.
  
	You may notice who last updated your school. This can be anyone who updates any files relating to your profile page. 
	It doesnt mean that the entire or even any of the actual profile page information is correct and updated entirely. 
	<br/>
	<div class="alert alert-danger" style="text-align:center;"><b>NOTICE:</b> School Administrators must make sure all info is up to date and correct.</div>
      
    
    <esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-SCHOOLPROFILE-ADMIN">
      Schools are sorted by name and colour coded by region.        
              <br/>
              <table class="table table-sm table-bordered responsive" style="width:100%;font-size:12px;">
              <thead>
              <tr style="text-align:center;color:White;">
              <th width="20%" class="region5solid">PROVINCIAL SCHOOLS</th>
              <th width="20%" class="region1solid">AVALON SCHOOLS</th>
              <th width="20%" class="region2solid">CENTRAL SCHOOLS</th>
              <th width="20%" class="region3solid">WESTERN SCHOOLS</th>
              <th width="20%" class="region4solid">LABRADOR SCHOOLS</th>
              </tr>
              </thead>
              <tbody>
               <tr style="text-align:center;color:black;font-size:14px;">
              <td width="20%"><span id="provincialCnt">0</span></td>
              <td width="20%"><span id="avalonCnt">0</span></td>
              <td width="20%"><span id="centralCnt">0</span></td>
              <td width="20%"><span id="westernCnt">0</span></td>
              <td width="20%"><span id="labradorCnt">0</span></td>
              </tr>
              </tbody>
              </table>
             </esd:SecurityAccessRequired> 
              <br/>
              
					 <div align="center">
					 <esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-SCHOOLPROFILE-ADMIN">					 
					 	<a href='addSchool.html' class="btn btn-sm btn-success" style="color:White;" title="Add School"><i class="fas fa-plus"></i> Add School</a> &nbsp; 
					 </esd:SecurityAccessRequired> 
					 <a href="/MemberServices/navigate.jsp" class="btn btn-sm btn-danger" style="color:White;" title="Back to Member Services"><i class="fas fa-backward"></i> Back to Member Services</a></div>
					 <p>
                      
														
								     							
	      <table class="schoolTable table table-sm table-bordered responsive" width="100%" style="font-size:11px;background-color:White;">
					<thead class="thead-dark">
					<tr style="color:white;font-size:12px;">
					<th width="5%">ID#</th>							
					<th width="25%">SCHOOL NAME</th>
					<th width="10%">REGION</th>							
					<th width="10%">ZONE</th>	
					<th width="15%">TOWN/CITY</th>		
					<th width="20%">UPDATED</th>						
					<th width="10%">OPTIONS</th>	
				</tr>
				</thead>
				<tbody>                         	
									<c:forEach items='${ schools }' var='school'>
                    				<c:choose>                    				
   									<c:when test="${fn:containsIgnoreCase(school.schoolName, updaterSchoolName)}">   									
   									<c:choose>
   									<c:when test="${school.zone eq 'avalon'}">
   									<c:set var="regionColor" value="region1"/>   	
   									<c:set var="countAvalonSchools" value="${countAvalonSchools + 1 }" />								
   									</c:when>
   									<c:when test="${school.zone eq 'central'}">
   									<c:set var="regionColor" value="region2"/> 
   									<c:set var="countCentralSchools" value="${countCentralSchools + 1 }" />  									
   									</c:when>
   									<c:when test="${school.zone eq 'western'}">
   									<c:set var="regionColor" value="region3"/>   	
   									<c:set var="countWesternSchools" value="${countWesternSchools + 1 }" />								
   									</c:when>
   									<c:when test="${school.zone eq 'labrador'}">
   									<c:set var="regionColor" value="region4"/>   	
   									<c:set var="countLabradorSchools" value="${countLabradorSchools + 1 }" />								
   									</c:when>
   									<c:when test="${school.zone eq 'provincial'}">
   									<c:set var="regionColor" value="region5"/>   	
   									<c:set var="countProvincialSchools" value="${countProvincialSchools + 1 }" />								
   									</c:when>
   									<c:otherwise>
   									<c:set var="regionColor" value="region0"/>   
   									<c:set var="countOtherSchools" value="${countOtherSchools + 1 }" />
   									</c:otherwise>
   									</c:choose>
   									
               						<tr class="${regionColor}">	
               						<td width="5%">${ school.schoolDeptID }</td>
               						<td width="25%" style="text-transform:capitalize;">${ school.schoolName }</td>			
               						<td width="10%" style="text-transform:capitalize;">${ school.zone }</td>
               						<td width="15%" style="text-transform:capitalize;">${ school.region.name }</td>
               						<td width="15%" style="text-transform:capitalize;"> ${ school.townCity } </td>
               						<td width="20%">
               						<c:if test="${school.detailsOther.getDateAddedFormatted() ne null}">
               						${school.detailsOther.getDateAddedFormatted()} by <span style="text-transform:capitalize;">${school.detailsOther.addedBy }</span>
               						</c:if>
               						</td>
               						<td width="10%" style="text-transform:capitalize;">
               						<a href="editSchool.html?id=${school.schoolID}" class="btn btn-warning btn-xs"><i class="far fa-edit"></i> EDIT</a>
               						<esd:SecurityAccessRequired permissions="MEMBERADMIN-VIEW,WEBMAINTENANCE-SCHOOLPROFILE-ADMIN">	
               						<a href="" class="btn btn-xs btn-danger" onclick="if(confirm('Are you sure you want to delete ${school.schoolName}?'))document.location.href='deleteSchool.html?sid=${school.schoolID}';"><i class="far fa-trash-alt"></i> DEL</a>
               						</esd:SecurityAccessRequired>
               						</td>
	     							</tr>	
									</c:when>									
									</c:choose>
								</c:forEach> 
  </tbody>
  </table>

</div>

  <script>
  $("#provincialCnt").html(${countProvincialSchools});
  $("#avalonCnt").html(${countAvalonSchools});
  $("#centralCnt").html(${countCentralSchools});
  $("#westernCnt").html(${countWesternSchools});
  $("#labradorCnt").html(${countLabradorSchools});
  $("#otherCnt").html(${countOtherSchools});
  
  </script>
     
  </body>
</html>	
			

			