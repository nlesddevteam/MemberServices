<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*, 
                 com.esdnl.personnel.jobs.constants.*" %>

<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<esd:SecurityCheck permissions="PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />
 
<html>
<head>
	<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display","none");
</script>
 <script>
 $('document').ready(function(){
	  $("#jobsapp").DataTable(
				{
					"order": [[ 1, "asc" ]],
					"lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]]
				}		  
	  );
 });
    </script>
    
    <style>
input {    
    border:1px solid silver;font-weight:normal;
		}
</style>


</head>
<body>

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Completed Reference Checks</b></div>
      			 	<div class="panel-body"> 
                    <div class="table-responsive"> 
					Below is the list of completed reference checks sorted by applicant.
					<br/><br/>
								<c:choose>
	                              	<c:when test="${fn:length(refs) gt 0}">
	                              	
	                              	<table id="jobsapp" class="table table-condensed table-striped" style="font-size:12px;background-color:#FFFFFF;">
									    <thead>
									      <tr>
									        <th width='40%'>REFERENCE DATE</th>
									        <th width='40%'>APPLICANT</th>
									        <th width='20%'>OPTIONS</th>
									      </tr>
									    </thead>
									    <tbody>
	                              		<c:forEach items='${refs}' var='ref'>
	                              				<tr>
		                                    	<td>${ref.providedDateFormatted}</td>
		                                    	<td>${ref.fullName}</td>
		                                    	<td>
		                                    	<a class="btn btn-xs btn-primary" href='${ref.viewUrl}'>view</a> 
		                                    	<a class="btn btn-xs btn-warning" href='${ref.editUrl}'>edit</a>
		                                    	</td>
		                                    	</tr>
	                              		</c:forEach>
	                              		</tbody>
                					 	</table>
	                              	</c:when>
	                              	<c:otherwise>
	                              		
	                              	<div class="alert alert-danger">No references added within the last 6 months.</div>
	                              			
	                              	</c:otherwise>
	                              </c:choose>
                 			
                 </div>
                 
                 <br/>
                 <div class="no-print" align="center"><a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a></div>
                 
                 </div>
                 </div>
</div>

                   
 
</body>
</html>
