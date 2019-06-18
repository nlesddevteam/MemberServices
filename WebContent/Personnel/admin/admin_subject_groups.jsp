<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.security.*,
                 com.esdnl.personnel.v2.utils.StringUtils,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<c:set var="grpCount" value="0" />   
<c:set var="subjectCount" value="0" />    

<html>

	<head>
		<title>NLESD - Member Services - Personnel-Package</title>		
		<meta name="viewport" content="width=device-width, initial-scale=1.0">	
	<style>
		.tableGroupName {width:25%;}
		.tableSubjects {width:65%;}		
		.tableOptions {width:10%;}			
		 
			
	</style>
	</head>

	<body>
	
	
	 <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Subject Groups and Lists</b></div>
      			 	<div class="panel-body"> 
						Current subject list groups and subjects can be found below. 
						<br/><br/>To edit/update a particular list, select edit to the right of each list. To add a new subject group, click on the Add Subject Group link to the right.	
					 
						 <br/><br/>
						 <table class="table table-striped table-condensed" style="font-size:12px;margin-top:5px;border-top:1px solid silver;">
						 	
											<c:choose>
							                     <c:when test="${ fn:length(groups) gt 0 }">		                     
						  				  				<thead>
														<tr>
														<th class='tableGroupName'>GROUP NAME</th>
														<th class='tableSubjects'>SUBJECT(S)</th>
														<th class='tableOptions no-print'>OPTIONS</th>
														</tr>
														</thead> 									
					  									<c:forEach items='${ groups }' var='grp'>  
						  									<tr>
						  									<c:set var="grpCount" value="${grpCount + 1}" /> 
						  									<c:set var="subjectCount" value="0" />                          		
						                                 		<td>${ grp.groupName } (<span class="subjectNumCount${grpCount}"></span>)</td>
						                                 		<td>   
						                                 			<c:forEach items='${ grp.subjects }' var='sub'>
					                           						<c:set var="subjectCount" value="${subjectCount + 1}" />
					                           							<div style="float:left;min-width:200px;width:33%;color:DimGrey;">&middot; ${ sub }</div>
					                           						</c:forEach>
					                           						<script>$(".subjectNumCount${grpCount}").html("${subjectCount}");</script>
								                             				
						                                 		 </td>  
						  										 <td class="no-print">
						  										 <a href='editSubjectGroup.html?id=${grp.groupId}' class="btn btn-xs btn-primary" role="button">Edit List</a>
						  										 </td>
						  									
						  									</tr>
					  									</c:forEach>
							                       </c:when>		                             		
							                       <c:otherwise>
					                           			<tr>
					                           			<td colspan=3>
					                           			No subject groups found.
					                           			</td>
					                           			</tr>                           			
							                       </c:otherwise>
							                 </c:choose> 									
						  
						</table>  
						
						<div align="center" class="no-print"><a href='addSubjectGroup.html' class="btn btn-xs btn-success" role="button">Add Subject Group</a></div>
	                   
					   </div>
					   </div>
   </div>                
 
  </body>
</html>
