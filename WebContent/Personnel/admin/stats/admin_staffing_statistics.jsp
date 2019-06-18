<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.awsd.security.*,
                 com.esdnl.personnel.v2.utils.StringUtils,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="jobv2" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<esd:SecurityCheck permissions="PERSONNEL-ADMIN-VIEW-STAFFING-STATS" />


<html>
	<head>
		<title>MyHRP Applicant Profiling System</title>
		
		
		<script type='text/javascript'>
		
			$(function(){
				
				$('#btn-go').click(function(){
					
					$('#tbl-subject-groups tbody').children().remove();
					
					if($('#txt-search-window').val() != ''){
						$("#resultsFound").css("display","block");
						$("#dataFound").css("display","block").delay(4000).fadeOut();
						var query = $("#txt-search-window").val();
						$("#statsForDate").css("display","block").html("Stats for " + query);
						var params = {};
						params.sd = $('#txt-search-window').val();
						
						$.post('/MemberServices/Personnel/admin/stats/ajax/getStaffingStatistics.html', params, function(data){
							$('#tbl-subject-groups tbody').append(
									$('<tr>')
										.append($('<td>').text('Jobs Posted'))
										.append($('<td>').text(data.jobsPosted))
							);
							
							$('#tbl-subject-groups tbody').append(
									$('<tr>')
										.append($('<td>').text('Applications Received'))
										.append($('<td>').text(data.applicationsReceived))
							);
							
							$('#tbl-subject-groups tbody').append(
									$('<tr>')
										.append($('<td>').text('Recommendations Submitted'))
										.append($('<td>').text(data.recommendationsMade))
							);
							
							$('#tbl-subject-groups tbody').append(
									$('<tr>')
										.append($('<td>').text('Contract Offers Accepted'))
										.append($('<td>').text(data.offersAccepted))
							);
							
						
						});	
					} else {
						$("#resultsFound").css("display","none");
						$("#noData").css("display","block").delay(5000).fadeOut();
						$("#statsForDate").css("display","none");
					}
					
				});
				
				$('.datefield').datepicker({
					autoSize: true,
					showOn: 'focus',
					showAnim: 'drop',
					dateFormat: 'dd/mm/yy',
					changeMonth: true,
					changeYear: true
				});
				
				
			});
		</script>
	<style>
		.tableTitle {font-weight:bold;width:80%;}
		.tableResult {font-weight:bold;width:20%;}		
		</style>
		<script>
		    $("#resultsFound").css("display","none");
			$("#loadingSpinner").css("display","none");
		</script>
	</head>
	
	<body>
	<div class="panel-group" style="padding-top:5px;">  
	<div class="panel panel-success">
  <div class="panel-heading"><b>Staffing Statistics</b></div>
  <div class="panel-body">Select the starting date and press <b>Get Stats</b> to view the staffing statistics for that date.
  <br/><br/>
  
  <form>
  <div class="input-group">
    <input type="text" class="form-control datefield input-sm" id='txt-search-window' placeholder="Select date to search stats.">
    <div class="input-group-btn">
      <button class="btn btn-success btn-sm" type="button" id='btn-go'>
        Get Stats <i class="glyphicon glyphicon-search"></i>
      </button>
    </div>
  </div>
  <div id="statsForDate" style="display:none;font-size:36px;font-weight:bold;color:rgba(223,240,216,0.8);"></div>
  <div class="alert alert-success" id="dataFound" style="display:none;">Success! Data results found.</div> 

	 <div class="table-responsive document-box" id="resultsFound" style="display:none;">      			 	       
      			
   				
   				<table id='tbl-subject-groups' class='document-box-table table table-striped table-condensed' style='font-size:12px;'>
   					<thead>
   						<tr>
						     <th class='tableTitle'>Data Point</th>
						     <th class='tableResult'>Value</th>
			     		</tr>
			     	</thead>
   				<tbody>
   				
   				</tbody>
			     </table>
   		</div>     	
								


<div class="alert alert-danger" id="noData" style="display:none;">Sorry. No results found. Please make sure you select a date above.</div> 
</form>
</div>
</div>	
	
</div>	               
	</body>
</html>
