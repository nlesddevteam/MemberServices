<%@ page language="java"
         import="com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*,
                  com.esdnl.personnel.jobs.constants.*,
                  com.esdnl.util.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
$("#loadingSpinner").css("display","none");
</script>
<style>		
		.tablePosTitle {width:25%;}
		.tableLocation {width:25%;}		
		.tableReqBy {width:15%;}
		.tableReqDate {width:10%;}
		.tableStatus {width:20%;}
		.tableOptions {width:5%;}
		input[type="checkbox"] {margin-top: -1px;margin-left:6px;margin-right:2px;vertical-align: middle;}
</style>

<script>
$('document').ready(function(){
$('.otherJobsList').DataTable({'order': [[ 1, 'asc' ]],'bLengthChange': true,'paging': true, 'lengthMenu': [[50, 100, 200,-1], [50, 100, 200,"All"]] });
});
</script>	

</head>
<body>
  
  <esd:SecurityCheck permissions="PERSONNEL-ADREQUEST-APPROVE,PERSONNEL-ADREQUEST-POST" />
  
  
  <div class="pageHeader">${status} Requests</div>
  
                                  <%if(request.getAttribute("msg")!=null){%>
                                      <div class="alert alert-danger">                                       
                                          <%=(String)request.getAttribute("msg")%>
                                        </div>
                                    <%}%>
  <table class="table table-striped table-condensed otherJobsList" style="font-size:11px;padding-top:3px;border-top:1px solid silver;">
               <thead>
               <tr>
               		
               		<th class='tablePosTitle'>Position Title</th>
					<th class='tableLocation'>Location</th>
					<th class='tableReqBy'>Requested By</th>
					<th class='tableReqDate'>Request Date</th>
					<th class='tableStatus'>Status</th>
					<th class='tableOptions'>Options</th>
				</tr>
				</thead>
                 <tbody>                
                     
									<c:choose>
						      			<c:when test="${fn:length(requests) > 0}">
							      			<c:forEach items="${requests}" var="rule">						      				
							      				<tr>							      										      							      				
                                      			<td>${rule.jobTitle }</td>
                                        		<td>${rule.locationDescription }</td>
                                        		<td>${rule.requestBy }</td>
                                        		<td>${rule.dateRequestedFormatted }</td>
                                        		<td>
                                        		<c:choose>                                        		
                                        		<c:when test="${rule.status.value eq 1 }">
                                        		<span style="color:White;background-color:Orange;" data-trigger="hover" data-toggle="popover" data-content="${rule.status.description }">&nbsp;SUBMITTED&nbsp;</span>
                                        		</c:when>
                                        		<c:when test="${rule.status.value eq 2 }">
                                        		<span style="color:White;background-color:Green;" data-trigger="hover" data-toggle="popover" data-content="${rule.status.description }">&nbsp;APPROVED&nbsp;</span>
                                        		</c:when>
                                        		<c:when test="${rule.status.value eq 3 }">
                                        		<span style="color:White;background-color:Green;" data-trigger="hover" data-toggle="popover" data-content="${rule.status.description }">&nbsp;APPROVED&nbsp;</span>
                                        		</c:when>
                                        		<c:when test="${rule.status.value eq 4 }">
                                        		<span style="color:White;background-color:Green;" data-trigger="hover" data-toggle="popover" data-content="${rule.status.description }">&nbsp;APPROVED&nbsp;</span>
                                        		</c:when>
                                        		<c:when test="${rule.status.value eq 5 }">
                                        		<span style="color:White;background-color:Green;" data-trigger="hover" data-toggle="popover" data-content="${rule.status.description }">&nbsp;APPROVED&nbsp;</span>
                                        		</c:when>
                                        		<c:when test="${rule.status.value eq 6 }">
                                        		<span style="color:White;background-color:Blue;" data-trigger="hover" data-toggle="popover" data-content="${rule.status.description }">&nbsp;AD CREATED&nbsp;</span>
                                        		</c:when>
                                        		<c:when test="${rule.status.value eq 7 }">
                                        		<span style="color:White;background-color:Red;" data-trigger="hover" data-toggle="popover" data-content="${rule.status.description }">&nbsp;REJECTED&nbsp;</span>
                                        		</c:when>
                                        		<c:when test="${rule.status.value eq 9 }">
                                        		<span style="color:White;background-color:cyan;" data-trigger="hover" data-toggle="popover" data-content="${rule.status.description }">&nbsp;NOTE SENT&nbsp;</span>
                                        		</c:when>
                                        		<c:when test="${rule.status.value eq 10}">
                                        		<span style="color:White;background-color:cyan;" data-trigger="hover" data-toggle="popover" data-content="${rule.status.description }">&nbsp;NOTE RESENT&nbsp;</span>
                                        		</c:when>
                                        		<c:when test="${rule.status.value eq 11}">
                                        		<span style="color:Yellow;background-color:black;" data-trigger="hover" data-toggle="popover" data-content="${rule.status.description }">&nbsp;UPDATED&nbsp;</span>
                                        		</c:when>
                                        		<c:otherwise>${rule.status.description }</c:otherwise>                                        		
                                        		</c:choose>
                                        		</td>
                                        		<td><a class="btn btn-warning btn-xs" href='addRequestToHire.html?rid=${rule.id}'>View</a></td>
                                      			</tr>							      			
							      			</c:forEach>
										</c:when>
				     					<c:otherwise>
				     					<tr><td colspan='6' style="color:Red;">No requests found</td></tr>
				     					</c:otherwise>
				     				</c:choose>                                  
               </tbody>
               </table>
               
               <script>
$(document).ready(function(){
  $('[data-toggle="popover"]').popover();  
 
});

</script>
               
                
</body>
</html>
