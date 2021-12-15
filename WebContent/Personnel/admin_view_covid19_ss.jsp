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
 
<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW-COVID19" />

<c:set var="permanentVal" value="0" />
<html>
	<head>
	<%
		ArrayList<Covid19ReportBean> alist =  Covid19ReportManager.getCovid19SpecialStatus();
	%>
	<c:set var="locs" value="<%=alist %>" />
		<title>View COVID19 Special Status Report</title>				
		<script type="text/javascript" src='includes/js/encoder.js'></script>
	<style>
		.allocation-addon-text {background-color: #e6ffe6;color:Black; }
		input {border: 1px solid silver;}
	</style>
	
	
			
	</head>
	
	<body>	
	<form id="frm-add-allocation" action="addTeacherAllocation.html" method="post">
	                                	
	     <input id='hdn-allocation-id' type='hidden' value='' />
	                                	
	     <div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading no-print"><b>View COVID19 Special Status Report</b></div>
      			 	<div class="panel-body">       				            
<div class="panel-group">
  <div class="panel panel-success">
    <div class="panel-heading">
      <h4 class="panel-title"> <b>Employees With Special Status(Retired/On Leave)</b>
      </h4>
    </div>
    
      <div class="panel-body">  
    	  <div id='permanent-positions-header-row' style='display:block;'>
    	
    	
					   <table id='permanent-positions-table' class="empTable table table-sm table-striped" style="font-size:11px;background-color:#FFFFFF;border:0px;">
						    
						    <thead class="thead-light">
								      <tr>
								       <th width='25%'>EMPLOYEE</th>
								       <th width='25%'>LOCATION</th>
								        <th width='50%'>STATUS</th>
								      </tr>
								    </thead>
								 <tbody>
								 	   <c:forEach items='${locs}' var='loc'>
	                              				<tr>
		                                    	<td>${loc.employeeName}</td>
		                                    	<td>${loc.employeeLocation}</td>
		                                    	<td>${loc.ssText}</td>
		                                    	</tr>
	                              		</c:forEach>
								 
								
								 </tbody>  
				  		</table>
				
    	

    	
    	
    	</div>
    	
    	
    	
    	
    	
</div>
      
    
  </div>
</div>	



       
</div>	
</div>
</div>
</form>
</body>

</html>