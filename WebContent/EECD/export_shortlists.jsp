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

<html>

	<head>
		<title>Education and Early Childhood Development Groups Admin</title>

  	
  	<script>
  	$(document).ready(function() {
  	    $('#exportdatatable').DataTable( {
  	        dom: 'Bfrtip',
  	        buttons: [
  	            'copy', 'csv', 'excel', 'pdf', 'print'
  	        ]
  	    } );
  	} );
  	
  	</script>
  	
	</head>

  <body>
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Export Teachers Shortlist(s)</b></div>
      			 	<div class="panel-body"> 
  
  
							Below is a list all teachers shortlisted for the selected EECD Area(s).  Please use the Search box to filter the list further if needed.  Please use
							the buttons to Copy, Export to CSV, Excel, PDF or Print the list below.
						
                    <jsp:include page="eecd_menu.jsp"/>
										
					 		<%if(request.getAttribute("msg")!=null){%>								
								<div id="fadeMessage" class="alert alert-danger">${ msg }</div>  									
							<%} %>                 	
                 	
                 			 <div class="alert alert-success" id="successMsg" style="text-align:center;display:none;" align="center"></div>
							 <div class="alert alert-danger" id="errorMsg" style="text-align:center;display:none;" align="center"></div>
					 		<div style="padding-left:5px; padding-right:5px; padding-top:10px; border-top:1px solid Silver;">
		            		<c:choose>
								<c:when test="${fn:length(areas) gt 0}">
								<c:set var="first" value="${areas[0].questions}"/> 
								<table id="exportdatatable" class="table table-condensed table-striped" style="font-size:12px;background-color:#FFFFFF;">								
					  				<thead>
					            		<tr>
					                		<th>AREA</th>
					                		<th>TEACHER</th>
					                		<th>SCHOOL</th>
					                		<th>ASSIGNMENT</th>
					                		<th>YEARS TEACHING</th>
					                		<th>EECD EXPERIENCE</th>
					                		<c:choose>
												<c:when test="${fn:length(first) gt 0}">
													<c:forEach items='${ first }' var='question'>
														<th>${ question.value.questionText}</th>
													</c:forEach>
												</c:when>
											</c:choose>
					            		</tr>
					            	</thead>
					            	<tbody>							
								 
								<c:forEach items='${ areas }' var='area'>
									<tr>
										<td>${ area.areaDescription}</td>
										<td>${ area.teacherName }</td>
										<td>${ area.currentSchool}</td>
										<td>${ area.currentAssignment}</td>
										<td>${ area.seniority }</td>
										<td>${ area.committees }</td>
										<c:forEach items='${ area.questions }' var='question'>
											<td>${ question.value.questionAnswer}</td>
										</c:forEach>
									</tr>
								</c:forEach>
								
								</tbody>
        				</table>
								
								</c:when>
								
								
								<c:otherwise>
									Sorry, no teachers currently shortlisted.
								</c:otherwise>	
								</c:choose>
        				
  </div></div></div></div>
</body>
</html>	