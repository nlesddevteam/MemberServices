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

<esd:SecurityCheck roles="ETHICS-REPORT-VIEWER" />

<html>

<head>
<title>NLESD - Member Services - Personnel-Package</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<script>
	$('document').ready(function(){
		$("#loadingSpinner").css("display","none");
		$('#reportdata').DataTable({
			"order": [[ 0, 'asc' ]],
			"lengthMenu" : [ [ 10, 25, 50, 100, -1 ], [ 20, 25, 50, 100, "All" ] ],	
			  dom: 'Blfrtip',			  
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

<style>

input,select {border: 1px solid silver;}
</style>


</head>
<body>
	<div class="panel-group" style="padding-top: 5px;">
		<div class="panel panel-success">
			<div class="panel-heading">
				<b>View Missing Code of Ethics Report</b>
			</div>
			<div class="panel-body">
				<br /> <br />
				<table id="reportdata" class="table table-striped table-condensed dt-responsive" style="font-size: 11px; background-color: #FFFFFF; margin-top: 5px;">
					<thead>
						<tr>
							<th width='30%'>NAME</th>
							
							<th width='17%'>EMAIL</th>
							<th width='7%'>COE DOC</th>
							<th width='7%'>SDS LINKED</th>
							<th width='9%'>PROFILE TYPE</th>
							<th width='10%'>HR PROFILE</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${apps}" var="app">
									<tr>
										<td>${ app.sdsLastName}, ${ app.sdsFirstName }</td>
										<td>${ app.sdsEmail eq null ? '<span style="color:Silver;">---</span>' : app.sdsEmail }</td>
										<td><span style="color:Silver;">N/A</span></td>
										<td>${ app.profileType eq null ? '<span style="color:Red;">NOT LINKED</span>' : '<span style="color:Green;">LINKED</span>' }</td>
										<td>${ app.profileType eq null ? '<span style="color:Silver;">NA</span>' : app.profileType eq'S' ? '<span style="color:Brown;">SUPPORT</span>' : '<span style="color:Blue;">TEACHING</span>' }</td>
										<td>
										<c:choose>
											<c:when test="${ app.appSin eq null}">
												<span style="color:Red;">NONE</span>
											</c:when>
											<c:otherwise>
												<a class="btn btn-xs btn-success" href='viewApplicantNonHr.html?sin=${app.appSin}' target="_blank">VIEW</a>
											</c:otherwise>
										</c:choose>
										
										</td>
										
									</tr>
								</c:forEach>
					</tbody>
				</table>


				<br /> <br />

			</div>
		</div>
	</div>

</body>
</html>