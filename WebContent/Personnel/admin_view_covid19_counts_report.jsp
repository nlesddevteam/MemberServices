<%@ page language="java"
         import="java.util.*,
                 java.text.*,com.awsd.security.*,
                 com.esdnl.personnel.v2.model.sds.bean.LocationBean,
                 com.esdnl.personnel.v2.utils.StringUtils,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->
		
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="jobv2" %>
 
<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW-COVID19,PERSONNEL-ADMIN-VIEW-COVID19-STATUS,PERSONNEL-ADMIN-VIEW-COVID19-COUNTS" />
<%
	ArrayList<Covid19CountsReportBean> alist =  Covid19CountsReportManager.getCovid19CountsReport();
%>
<c:set var="locs" value="<%=alist %>" />
<html>
	<head>
		<title>Location Counts</title>				
		<script type="text/javascript" src='includes/js/encoder.js'></script>
	<style>
		.allocation-addon-text {background-color: #e6ffe6;color:Black; }
		input {border: 1px solid silver;}
	</style>
	
	
			
	</head>
	
	<body>	
	<form id="frm-add-allocation" action="addTeacherAllocation.html" method="post">
	                                	
	     <input id='hdn-allocation-id' type='hidden' value='' />
	                             		
<!--PERMANENT POSITIONS TAB ---------------------------------------------------------->	                             		
<br/>					
	                             								
	<div class="panel-group">
  <div class="panel panel-success">
    <div class="panel-heading">
      <h4 class="panel-title"> <b><span class="theSchoolName"></span> Location View COVID19 Counts Report(s)</b>
      </h4>
    </div>
    
      <div class="panel-body">  
    	  <div id='permanent-positions-header-row' style='display:block;'>
    	
    	
					   <table id='permanent-positions-table' class="empTable table table-sm table-striped" style="width:100%;font-size:11px;">
						    
						    <thead class="thead-dark">
								      <tr>
								       <th width='30%'>LOCATION</th>
								       <th width='10%'>EMPLOYEE(S)</th>
								        <th width='10%'>DOC UPLOADED</th>
								        <th width='10%'>NOT VERIFIED</th>
								        <th width='10%'>VERIFIED</th>
								        <th width='10%'>REJECTED</th>
								        <th width='10%'>SPECIAL STATUS</th>
								        <th width='10%'>OPTIONS</th>
								        
								      </tr>
								    </thead>
								 <tbody>
	                              		<c:forEach items='${locs}' var='loc'>
	                              				<tr>
		                                    	<td>${loc.location}</td>
		                                    	<td>${loc.locationCount}</td>
		                                    	<td>${loc.documentCount}</td>
		                                    	<td>${loc.notVerifiedCount}</td>
		                                    	<td>${loc.verifiedCount}</td>
		                                    	<td>${loc.rejectCount}</td>
		                                    	<td>${loc.specialStatusCount}</td>
		                                    	<td><a class='viewdoc btn btn-xs btn-primary' href="admin_view_covid19_school_report.jsp?sid=${loc.location}" target='_blank'>VIEW</a></td>
		                                    	</tr>
	                              		</c:forEach>
	                              </tbody>  
				  		</table>
				
    	

    	
    	
    	</div>
    	
    	
    	
    	
    	
</div>
      
    
  </div>
</div>

</form>
<script>
$('.empTable').dataTable({
		
		"order" : [[0,"asc"]],		
		//"bPaginate": false,
		"lengthMenu" : [ [ 25, 50, 100, 200, -1 ], [ 25, 50, 100, 200, "All" ] ],
		responsive: true,
});
	


$(".dataTables_filter").addClass("no-print");
</script>

</body>

</html>