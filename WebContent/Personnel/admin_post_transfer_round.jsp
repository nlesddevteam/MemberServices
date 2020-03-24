<%@ page language="java"
	import="java.util.*,
                 java.text.*,
                 com.esdnl.personnel.jobs.bean.*"
	isThreadSafe="false"%>

<!-- LOAD JAVA TAG LIBRARIES -->

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd"%>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job"%>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="jobv2"%>

<esd:SecurityCheck roles="ADMINISTRATOR" />
<html>

<head>
<title>MyHRP Applicant Profiling System</title>
<script type='text/javascript'>
		$("#loadingSpinner").css("display","none");
		</script>
		<script type="text/javascript" src="includes/js/changepopup.js"></script>
</head>
<body>
	<c:set var="now" value="<%=new java.util.Date()%>" />
	<fmt:formatDate value="${now}" pattern="yyyy" var="yearyyyy" />
	<fmt:formatDate value="${now}" pattern="yy" var="yearyy" />

	<form id='frm-interview-guide' action="updatePTRSettings.html" method="post" onsubmit="return checkptrvalues()">
		<input type='hidden' name='confirmed' value='true' />
		<c:if test="${not empty msg}">
			<div class="alert alert-danger">${msg}</div>
		</c:if>
		<div class="panel-group" style="padding-top: 5px;">
			<div class="panel panel-success">
				<div class="panel-heading">
					<b>Update Post Transfer Round Settings</b>
				</div>
				<div class="panel-body">

					<div class="container-fluid" >
						<div class="row justify-content-center">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="alert alert-danger" id="errorText"
									style="display: none; text-align: center;"></div>
							</div>
						</div>
						<div class="col align-self-center">
							<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
								<div class="input-group">
									<span class="input-group-addon formTitleArea">Start Date:</span> <input
										type="date" class="form-control" id='startdate' name='startdate' value="${settings.ptrStartDateFormatted}"
										placeholder="Please select start date" required >
								</div>
							</div>
						</div>
						<div class="col align-self-center">
							<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
								<div class="input-group">
									<span class="input-group-addon formTitleArea">End Date:</span> <input
										type="date" class="form-control" id='enddate' name='enddate' value="${settings.ptrEndDateFormatted}"
										placeholder="Please select end date" required>
								</div>
							</div>
						</div>
						<div class="col align-self-center">
							<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
								<div class="input-group">
									<span class="input-group-addon formTitleArea">Position Limit:</span> <input
										type="text" class="form-control" id='positionlimit' name='positionlimit' value="${settings.ptrPositionLimit}"
										placeholder="Please enter position limit" required >
								</div>
							</div>
						</div>						
						<div class="col align-self-center">
							<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
								<div class="input-group">
									<span class="input-group-addon formTitleArea">Current Status</span>
									<select id="currentstatus" name="currentstatus" class="form-control">
										<option value="-1">- PLEASE SELECT -</option>
										<option value="0" ${settings.ptrStatus eq 0 ? 'selected':''}>Disabled</option>
										<option value="1" ${settings.ptrStatus eq 1 ? 'selected':''}>Enabled</option>
									</select>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			
	</div>
		<div align="center" class="no-print">
			<input id='btn-submit' type="submit" class="btn btn-success">
			&nbsp; <a href="/MemberServices/Personnel/admin_index.jsp"
				class="btn btn-danger">Cancel</a>
		</div>

	</form>

</body>
</html>
