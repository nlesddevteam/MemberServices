<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         		 com.awsd.school.*,
         		 com.awsd.personnel.*,
         		 com.nlesd.psimport.bean.*,  
         		 com.nlesd.psimport.dao.*,               
                 com.awsd.common.*,
                 org.apache.commons.lang.math.NumberUtils,
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
  PSClassInformationBean psbean = null;
  if(usr.getPersonnel() != null && usr.getPersonnel().getSchool() != null){
	  psbean = PSClassManager.getPSClassInformtion(usr.getPersonnel().getSchool().getSchoolID());
  }
  
  
%>
<c:set var='schools' value="<%= schools %>" />
<c:set var="zonePerm" value='all' />
<c:set var="psinfo" value='<%= psbean %>' />
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
  
  If you are NOT the Principal or Assistant Principal for the following school(s), DO NOT make any changes and please contact <a href="mailto:geofftaylor@nlesd.ca?subject=School Profile Page">geofftaylor@nlesd.ca</a>.
  
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

<esd:SecurityAccessRequired permissions='WEBMAINTENANCE-SCHOOLPROFILE-PRINCIPAL,
WEBMAINTENANCE-SCHOOLPROFILE-SECRETARY'>

<br />

<div class="siteHeaderGreen">School Class Sizes</div>

	Below is a list of class size(s) for your school.  This count is exported weekly from Powerschool on Sundays.  It uses the current 
	sections and the enrollment for these section<p>
	<ul>
	<li>The counts will show on the school profile page for your school  on the main public website.  Below is a preview of those numbers.
	<li>K-9 schools will show the Powerschool section number and class size for the homerooms.
	<li>High Schools will show the number of sections for all courses sorted into ranges by class size.
</ul>

<div class="alert alert-warning"><b>NOTICE:</b> If the numbers below are not accurate, please make sure you have your PowerSchool updated correctly. 
This includes any class homeroom names, duplicates, and/or info you may have on file. This data is now publicly displayed for each school and must be accurate and up-to-date.</div>
											<br/><br/>


      
<c:choose> 
										<c:when test="${ not empty psinfo }">
											<div style="clear:both;"></div>	
																				
											<c:set var="knum" value="1"/>
											<c:set var="gr1num" value="1"/>
											<c:set var="gr2num" value="1"/>
											<c:set var="gr3num" value="1"/>
											<c:set var="gr4num" value="1"/>
											<c:set var="gr5num" value="1"/>
											<c:set var="gr6num" value="1"/>
											<c:set var="gr7num" value="1"/>
											<c:set var="gr8num" value="1"/>
											<c:set var="gr9num" value="1"/>
											<c:set var="gr10num" value="1"/>
											<c:set var="totalStd" value="0"/>
											
											
										
										<c:choose> 
												<c:when test="${ not empty psinfo }">
												
												<c:if test="${ psinfo.scBean ne null}">
												
												<table class="table table-condensed table-bordered" style="margin:0 auto;font-size:12px;max-width:1024px;width:100%;text-align:center;">
																								<thead class="thead-dark">																				
																								<tr style="font-weight:bold;">
												<c:if test="${ psinfo.scBean.studentsK gt -1}">
												<th>K</th>
												</c:if>
												<c:if test="${ psinfo.scBean.students1 gt -1}">
												<th>GRADE 1</th>
												</c:if>
												<c:if test="${ psinfo.scBean.students2 gt -1}">
												<th>GRADE 2</th>
												</c:if>
												<c:if test="${ psinfo.scBean.students3 gt -1}">
												<th>GRADE 3</th>
												</c:if>
												<c:if test="${ psinfo.scBean.students4 gt -1}">
												<th>GRADE 4</th>
												</c:if>
												<c:if test="${ psinfo.scBean.students5 gt -1}">
												<th>GRADE 5</th>
												</c:if>
												<c:if test="${ psinfo.scBean.students6 gt -1}">
												<th>GRADE 6</th>
												</c:if>
												<c:if test="${ psinfo.scBean.students7 gt -1}">
												<th>GRADE 7</th>
												</c:if>
												<c:if test="${ psinfo.scBean.students8 gt -1}">
												<th>GRADE 8</th>
												</c:if>
												<c:if test="${ psinfo.scBean.students9 gt -1}">
												<th>GRADE 9</th>
												</c:if>
												<c:if test="${ psinfo.scBean.students10 gt -1}">
												<th>LEVEL 1</th>
												</c:if>
												<c:if test="${ psinfo.scBean.students11 gt -1}">
												<th>LEVEL 2</th>
												</c:if>
												<c:if test="${ psinfo.scBean.students12 gt -1}">
												<th>LEVEL 3</th>
												</c:if>
												<c:if test="${ psinfo.scBean.students13 gt -1}">
												<th>LEVEL 4</th>
												</c:if>
												<c:if test="${ psinfo.scBean.students14 gt -1}">
												<th>LEVEL 5</th>
												</c:if>
												<th>TOTAL</th>
												</tr>
												</thead>
												<tbody>
												<tr>
												<c:if test="${ psinfo.scBean.studentsK gt -1}">
												<td>${ psinfo.scBean.studentsK}</td>
												<c:set var="totalStd" value="${ psinfo.scBean.studentsK + totalStd}"/>
												</c:if>
												<c:if test="${ psinfo.scBean.students1 gt -1}">
												<td>${ psinfo.scBean.students1}</td>
												<c:set var="totalStd" value="${ psinfo.scBean.students1 + totalStd}"/>
												</c:if>
												<c:if test="${ psinfo.scBean.students2 gt -1}">
												<td>${ psinfo.scBean.students2}</td>
												<c:set var="totalStd" value="${ psinfo.scBean.students2 + totalStd}"/>
												</c:if>
												<c:if test="${ psinfo.scBean.students3 gt -1}">
												<td>${ psinfo.scBean.students3}</td>
												<c:set var="totalStd" value="${ psinfo.scBean.students3 + totalStd}"/>
												</c:if>
												<c:if test="${ psinfo.scBean.students4 gt -1}">
												<td>${ psinfo.scBean.students4}</td>
												<c:set var="totalStd" value="${ psinfo.scBean.students4 + totalStd}"/>
												</c:if>
												<c:if test="${ psinfo.scBean.students5 gt -1}">
												<td>${ psinfo.scBean.students5}</td>
												<c:set var="totalStd" value="${ psinfo.scBean.students5 + totalStd}"/>
												</c:if>
												<c:if test="${ psinfo.scBean.students6 gt -1}">
												<td>${ psinfo.scBean.students6}</td>
												<c:set var="totalStd" value="${ psinfo.scBean.students6 + totalStd}"/>
												</c:if>
												<c:if test="${ psinfo.scBean.students7 gt -1}">
												<td>${ psinfo.scBean.students7}</td>
												<c:set var="totalStd" value="${ psinfo.scBean.students7 + totalStd}"/>
												</c:if>
												<c:if test="${ psinfo.scBean.students8 gt -1}">
												<td>${ psinfo.scBean.students8}</td>
												<c:set var="totalStd" value="${ psinfo.scBean.students8 + totalStd}"/>
												</c:if>
												<c:if test="${ psinfo.scBean.students9 gt -1}">
												<td>${ psinfo.scBean.students9}</td>
												<c:set var="totalStd" value="${ psinfo.scBean.students9 + totalStd}"/>
												</c:if>
												<c:if test="${ psinfo.scBean.students10 gt -1}">
												<td>${ psinfo.scBean.students10}</td>
												<c:set var="totalStd" value="${ psinfo.scBean.students10 + totalStd}"/>
												</c:if>
												<c:if test="${ psinfo.scBean.students11 gt -1}">
												<td>${ psinfo.scBean.students11}</td>
												<c:set var="totalStd" value="${ psinfo.scBean.students11 + totalStd}"/>
												</c:if>
												<c:if test="${ psinfo.scBean.students12 gt -1}">
												<td>${ psinfo.scBean.students12}</td>
												<c:set var="totalStd" value="${ psinfo.scBean.students12 + totalStd}"/>
												</c:if>
												<c:if test="${ psinfo.scBean.students13 gt -1}">
												<td>${ psinfo.scBean.students13}</td>
												<c:set var="totalStd" value="${ psinfo.scBean.students13 + totalStd}"/>
												</c:if>
												<c:if test="${ psinfo.scBean.students14 gt -1}">
												<td>${ psinfo.scBean.students14}</td>
												<c:set var="totalStd" value="${ psinfo.scBean.students14 + totalStd}"/>
												</c:if>
												<td style="background-color:Silver;">${totalStd}</td>
												</tr>
												</tbody>
												</table>
												</c:if>
												</c:when>
												</c:choose>
										
										<br/>&nbsp;<br/>
										
											<c:if test="${ not psinfo.kClass.isEmpty()}">
											
												<table class="table table-condensed table-striped table-bordered" style="text-align:center;margin:0 auto;font-size:12px;max-width:800px;width:100%;">
												<thead class="thead-dark">																						
												<tr style="font-weight:bold;color:White;">
												<th width="30%">CLASS NUMBER</th>
												<th width="40%">POWER SCHOOL SECTION</th>
												<th width="20%"># STUDENTS</th>
												<th width="10%">GRADE(S)</th>
												</tr>
												</thead>
												<tbody>
													<c:forEach var="entry" items="${psinfo.kClass}">	
													<c:if test="${entry.value.numberOfStudents gt '0'}">							
														<tr>
														    		
														    <td>HR-${knum}
															<c:set var="knum" value="${knum+1}"/>
															</td>
															<td>${entry.value.sectionNumber}</td>
															<td>${entry.value.numberOfStudents}
															</td>															
															<td>${entry.value.gradesString eq '0'?'K':entry.value.gradesString}</td>
														</tr>	
														</c:if>													
													</c:forEach>
													</tbody>													
													</table>
															
												</c:if>
												
													<br/>&nbsp;<br/>
													
												<c:if test="${ not psinfo.hClass.isEmpty()}">
												
													<table class="table table-condensed table-striped table-bordered" style="text-align:center;margin:0 auto;font-size:12px;max-width:800px;width:100%;">
												<thead class="thead-dark">																						
												<tr style="font-weight:bold;color:White;">											
																<th width="10%">LEVELS</th>																
																<th width="15%"> <15 STUDENTS </th>
																<th width="15%"> 15-19 </th>
																<th width="15%"> 20-24 </th>
																<th width="15%"> 25-29 </th>
																<th width="15%"> 30-34 </th>
																<th width="15%"> >35 </th>																
														</tr>
														</thead>
														<tbody>
														<c:forEach var="entry" items="${psinfo.hClass}">
														<c:if test="${entry.value.gradeLevel le '13'}">
															<tr>
																	<td width="10%"> I, II, III, IV <!-- ${entry.value.gradeLevel}--> </td>											
																	<td width="15%"> ${entry.value.lessThan15} </td>
																	<td width="15%"> ${entry.value.between1520} </td>
																	<td width="15%"> ${entry.value.between2025} </td>
																	<td width="15%"> ${entry.value.between2530} </td>
																	<td width="15%"> ${entry.value.between3035} </td>
																	<td width="15%"> ${entry.value.greaterThan35} </td>
															</tr>																
															</c:if>													
													</c:forEach>
													</tbody>													
													</table>
												</c:if>
												
										</c:when>
										<c:otherwise>
											<p>Please contact the School District for further details.</p>
										</c:otherwise>
									</c:choose>

</esd:SecurityAccessRequired>
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
			

			