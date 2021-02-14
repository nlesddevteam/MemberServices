<%@ page language="java"
	import="java.util.*,
                 java.text.*,
                 com.awsd.security.*,
                 com.esdnl.personnel.v2.utils.StringUtils,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*"
	isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd"%>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job"%>
<!-- LOAD JAVA TAG LIBRARIES -->
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

<esd:SecurityCheck
	permissions="PERSONNEL-ADMIN-VIEW,PERSONNEL-PRINCIPAL-VIEW,PERSONNEL-VICEPRINCIPAL-VIEW" />

<html>

<head>
<title>NLESD - Member Services - Personnel-Package</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style>
.tableGroupName {
	width: 25%;
}

.tableSubjects {
	width: 65%;
}

.tableOptions {
	width: 10%;
}
</style>
<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.print.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/1.5.1/js/buttons.html5.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/buttons/1.5.1/js/dataTables.buttons.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.5/jszip.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.40/pdfmake.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.40/vfs_fonts.js"></script>
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/buttons/1.5.1/css/buttons.dataTables.min.css">
<script type="text/javascript">
	$('document').ready(function(){
		
		$('#reportdata').DataTable({
			
			"order": [[ 1, 'asc' ]],
			  dom: 'Bfrtip',			  
			  buttons: [ 'copyHtml5', 'excelHtml5', 
				  {
	                extend: 'pdfHtml5',
	                orientation: 'landscape',
	                pageSize: 'LETTER'
	                
	            }, 'csvHtml5',{
	                extend: 'print',
	                orientation: 'landscape',
	                pageSize: 'LETTER'
	            }
			  ]
			 ,"bAutoWidth": false
			  
			} );
		
		$("#reportdata").css('table-layout', "fixed");
		
	});
</script>
</head>
<body>
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success">
			<div class="panel-heading">
				<b>View Substitute List By School</b>
			</div>
			<div class="panel-body">
				Please select Region, School and List before pressing the View List
				button<br/><br/>
				<div class="row">
					<div class="col-sm-3">
						<div class="input-group">
							<span class="input-group-addon">Region:</span> <select
								id="selregion" name="selregion" class="form-control"
								onchange="getSubListsSchools();">
								<option value="-1">Select region</option>
								<c:forEach var="entry" items="${zones}">
									<option VALUE='${entry.zoneId}'>${fn:toUpperCase(entry.zoneName)}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="input-group">
							<span class="input-group-addon">School:</span> <select
								id="selschool" name="selschool" class="form-control">

								<option value="-1">Select school</option>

							</select>
						</div>
					</div>
					<div class="col-sm-4">
						<div class="input-group">
							<span class="input-group-addon">List:</span> <select
								id="selsublist" name="selssublist" class="form-control">
								<option value="-1">Select list</option>

							</select>
						</div>
					</div>
					<div class="col-sm-1">
						<div class="input-group">
							<input type="button" value="View List" onclick="getSubListShortlistAppsBySchool()">
						</div>
					</div>
				</div>

				<br />
				<br />
				<table id="reportdata"
					class="table table-striped table-condensed dt-responsive"
					style="font-size: 11px; background-color: #FFFFFF; margin-top: 5px;">
					<thead>
						<tr>
							<th width="10%">First Name</th>
							<th width="10%">Last Name</th>
							<th width="25%">Major(s)/Minor(s)</th>
							<th width="15%">Email</th>
							<th width="10%">Community</th>
							<th width="15%">Telephone</th>
							<th width="10%" class="no-print">Options</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>


				<br />
				<br />

			</div>
		</div>
	</div>

</body>
</html>