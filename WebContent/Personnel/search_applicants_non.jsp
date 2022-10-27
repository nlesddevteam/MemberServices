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

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd"%>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job"%>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="jobv2"%>

<esd:SecurityCheck permissions="PERSONNEL-SEARCH-APPLICANTS-NON" />

<c:set var="permanentVal" value="0" />
<html>
<head>
<title>Search Applicant Profiles</title>
<script type="text/javascript" src='includes/js/encoder.js'></script>


<script>
	$('document').ready(function(){
		$("#loadingSpinner").css("display","none");	
	
		
	});

		
		</script>

<style>
input,select {border: 1px solid silver;}
</style>


</head>

<body>
<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success">
			<div class="panel-heading"><b>Search Applicant Profiles</b></div>
			<div class="panel-body">
	
	<div style="width:30%;min-width:300px;float:left;padding-right:5px;">	
			<select class="form-control" id="selectby">
			<option value="">SEARCH BY? (Please Select)</option>
				<option value="EM">Email</option>
				<option value="FN">First Name</option>
				<option value="LN">Last Name</option>
			</select>
	</div>		
	<div style="width:40%;min-width:300px;float:left;">
	<div class="input-group">
    <input type="text" class="form-control" id="txtfor" placeholder="Search Text">
    <div class="input-group-btn">
      <button class="btn btn-danger btn-md" type="submit" onclick="searchApplicantProfileNon()">
        <i class="glyphicon glyphicon-search" style="height:19px;"></i>
      </button>
    </div>
  </div>
	</div>
	
	
		
		
	
	<div style="clear:both;">	
	<div class="alert alert-danger" role="alert" style="display: none;"	id="diverror">
		<br /> <span id="spanerror"></span>
	</div>
	
	<br />
	     <div class="panel-body">  
    	  <div id='applicants-header-row' style='display:block;'>
    	
    	
					   <table id='applicants-table' class="table table-sm table-responsive" style="font-size:11px;background-color:#FFFFFF;border:0px;">
						    
						    <thead class="thead-light">
								      <tr>
								       <th width='15%'>LAST NAME</th>
								       <th width='15%'>FIRST NAME</th>
								       <th width='20%'>EMAIL</th>
								       <th width='10%'>COE DOC</th>
								       <th width='15%'>SDS LINKED</th>
								       <th width='15%'>PROFILE TYPE</th>
								       <th width='10%'>OPTIONS</th>
								      </tr>
								    </thead>
								 <tbody>
								
								 </tbody>  
				  		</table>
				
    	

    	
    	
    	</div>
    	
    	
  </div>
  </div>
  </div>  	
    </div>
    	
</div>
</body>

</html>
