<%@ page language="java"
         import="com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 com.esdnl.util.*,
                 java.util.Arrays"
         isThreadSafe="false"%>
<!-- LOAD JAVA TAG LIBRARIES -->
		
		<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
		<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
		<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
		
			<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
			<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<c:set var="today" value="<%=new java.util.Date()%>" />
<fmt:formatDate value="${today}" pattern="yyyy" var="todayYear"/>
<fmt:formatDate value="${today}" pattern="MM" var="todayMonth"/>



<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>


$("#loadingSpinner").css("display","none");
</script>
<script>
 $('document').ready(function(){
	  $("#applicant-list").DataTable(
		{
			
			"order": [[ 0, "asc" ]],
			"lengthMenu": [[-1], ["All"]],
			"lengthChange": false
		}	  
	  );
 });
    </script>
 	 
    <style>
		input {    
    border:1px solid silver;
		}
		.btn {font-size:11px;}
</style>	

</head>
<body>
  
  <esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW" />
  
  
  <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading">
	               	<span style="font-size:16px;font-weight:bold;">Deleted Applicants</span>
	               	
	               	<br />
	               	<div class="alert alert-danger" role="alert" id="diverror" style="display:none;text-align:center;">

					</div>
					<br />
					<div class="alert alert-success" role="alert" id="divsuccess" style="display:none;text-align:center;">

					</div>	               	
	               	
	               	</div>
   
                    <div style="clear:both;"></div>

	                                    									
					
                    
                    <div class="table-responsive"> 
					<div style="clear:both;">&nbsp;</div>        
                    
                    			 <table id='applicant-list' class="jobsappListing table table-condensed" style="font-size:11px;background-color:#FFFFFF;margin-top:10px;">
								    <thead>
								      <tr style="border-top:1px solid black;">
								        <th width='20%'>NAME (Last, First)</th>								        
								        <th width='20%'>EMAIL</th>								        
								        <th width='12%'>TELEPHONE</th>
								        <th width='12%'>CELL</th>
								        <th width='16%'>TYPE</th>
								        <th width='20%'>OPTIONS</th>
								      </tr>
								    </thead>
								    
								    <tbody>
								    <c:choose>
								    <c:when test="${fn:length(applicants) > 0}">
								    	<c:forEach var="app" items="${applicants }">
								    	<tr>
								    		<td>${app.surname}, ${app.firstname}</td>
                                      		<td><a href="mailto:<${ app.email}?subject=HR Applicant Profile">${ app.email}</a></td>   
		                               		<td>${app.homephone eq null ? 'N/A' : app.homephone}</td>
                                      		<td>${app.cellphone eq null ? 'N/A' : app.cellphone}</td>  
                                      		<td>${ app.profileType eq 'S' ? 'Support/Management' : 'Teaching/TLA/Admin'} </td>                                                                        
                                     		<td>
                                     		<a onclick="loadingData()" href="viewApplicantProfile.html?sin=${app.SIN}" title="View this Applicant's Profile" class="btn btn-xs btn-primary">PROFILE</a>
                                     		&nbsp;
                                     		<a onclick="openPDeleteApplicantAjax('${app.SIN}',this)"  title="Delete Applicant's Profile" class="btn btn-xs btn-primary">DELETE</a>
                                     		</td>
                                    	</tr>
								    	</c:forEach>
								    </c:when>
								    <c:otherwise>
								    	<tr><td style="text-transform:capitalize;" colspan="6">No applicants found</td></tr>
								    </c:otherwise>
								    </c:choose>
								   
								    </tbody>
						  		</table>
							</div>
    		</div>
    	</div>
    		<esd:SecurityAccessRequired
				permissions="PERSONNEL-ADMIN-DELETE-APPLICANT-PROFILE">
			<!-- Modal for deleting app -->
		<div id="delete_app_dialog" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Permanently Delete Applicant</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<h4 class="modal-title"><span id="spandelete">Are you sure you want to delete this applicant?</span></h4>
						</div>
						<div class="modal-footer">
							<button type="button" id='btn_delete_app_ok_ajax'
								class="btn btn-success btn-xs" style="float: left;">Permanently Delete</button>
							<button type="button" class="btn btn-danger btn-xs"
								data-dismiss="modal">Close</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		</esd:SecurityAccessRequired>                        
</body>
</html>