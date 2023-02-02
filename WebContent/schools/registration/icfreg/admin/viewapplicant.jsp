<%@ page language="java" isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd"%>

<esd:SecurityCheck permissions="ICF-REGISTRATION-ADMIN-VIEW,ICF-REGISTRATION-SCHOOL-VIEW" />

<html>

<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<TITLE>Student Registration Information</title>

</head>

<body>

	<div align='center' style='font-size: 14pt; font-weight: bold; color: #004178; padding-bottom: 15px;'>
		${perbean.icfRegPerSchoolYear} ${regbean.icfAppSchoolName ne null?regbean.icfAppSchoolName:"N/A"}
		Registration Information<br /> 
		<span style="text-transform: Uppercase; color: Black;">${regbean.icfAppFullName ne null?regbean.icfAppFullName:"N/A"}</span>
	</div>

	<div align='center' class="no-print">
			<esd:SecurityAccessRequired permissions="ICF-REGISTRATION-ADMIN-VIEW">
			<a class='btn btn-primary btn-sm' href="index.html"
			onclick="loadingData();">Registration Home</a> &nbsp; 
			</esd:SecurityAccessRequired>
			<a href='#'
			class="no-print noJump btn btn-sm btn-warning"
			title='Print this page (pre-formatted)'
			onclick="jQuery('#printJob').print({prepend : '<div align=center><img width=400 src=/MemberServices/schools/registration/icfreg/admin/includes/img/nlesd-colorlogo.png><br/><br/><b>ICF REGISTRATION</b></div><br/>'});"><i
			class="fas fa-print"></i> Print this Page</a> &nbsp; &nbsp; <a
			onclick="window.history.go(-1);" class='btn btn-sm btn-danger no-print'
			>Back to Registrant List</a> &nbsp;

	</div>
	<br />
	<div class='divResponseMsg alert alert-success' align='center' style="display: none;" id="successmsg"></div>
	<br />
					<div class="card">
							  <div class="card-header"><b>REGISTRANT INFORMATION</b><input type="hidden" id="hidrid" value ="${regbean.icfAppId}"></div>
							  <div class="card-body">	
								<div class="row" style="padding-top:5px;">
		      							<div class="col-lg-6 col-md-6 col-sm-12"><b>Student Full Name:</b><br/><div class="dField">${regbean.icfAppFullName}</div></div>								
		      							<div class="col-lg-6 col-md-6 col-sm-12"><b>Parent/Guardian Full Namer:</b><br/><div class="dField">${regbean.icfAppGuaFullName}</div></div>
								</div>
								<div class="row" style="padding-top:5px;">
		      							<div class="col-lg-4 col-md-4 col-sm-12"><b>Parent/Guardian Email Address:</b><br/><div class="dField">${regbean.icfAppEmail}
		      							<input type="hidden" id="hidemail" value="${regbean.icfAppEmail}"></div></div>
										<div class="col-lg-4 col-md-4 col-sm-12"><b>Telephone: (xxx) xxx-xxxx</b><br/><div class="dField">${regbean.icfAppContact1}</div></div>
										<div class="col-lg-4 col-md-4 col-sm-12"><b>Optional Telephone: (xxx) xxx-xxxx</b><br/><div class="dField">${regbean.icfAppContact2}</div></div>
								</div>
								<div class="row" style="padding-top:5px;">
		      							<div class="col-lg-4 col-md-4 col-sm-12"><b>Registration School:</b><br/><div class="dField">${regbean.icfAppSchoolName}</div></div>
										<div class="col-lg-4 col-md-4 col-sm-12"><b>Registration Date/Time :</b><br/><div class="dField">${regbean.getIcfAppDateSubmittedFormatted()}</div></div>
										<div class="col-lg-4 col-md-4 col-sm-12"><b>Registration Status :</b><br/><div class="dField"><span id="spanstatus">${regbean.getApplicantStatusString()}</span></div></div>
								</div>
										
																
							</div>
					</div>
					
					<esd:SecurityAccessRequired permissions="ICF-REGISTRATION-ADMIN-STATUS">
					<br/><br/>
					<div class="card no-print">
						<div class="card-header"><b>REGISTRANT STATUS OPTIONS</b></div>
							<div class="card-body">
		      				Please note that an email is sent to the registrant with each status change except when status is reset.
							<br/><br/>	
							<div align="center">
		      							<c:choose>
		      								<c:when test="${regbean.icfAppStatus eq 1}">
		      									<button type="button" class="btn btn-success btn-sm" id="butApprove" onclick="adminUpdateRegistrantAjax('2');">APPROVE</button>
		      									<button type="button" class="btn btn-danger btn-sm" id="butNotApprove" onclick="adminUpdateRegistrantAjax('3');">NOT APPROVED</button>
		      									<button type="button" class="btn btn-warning btn-sm" id="butWait" onclick="adminUpdateRegistrantAjax('4');">WAIT LISTED</button>
		      								</c:when>
		      								<c:when test="${regbean.icfAppStatus eq 2}">
		      									<button type="button" class="btn btn-danger btn-sm" id="butNotApprove" onclick="adminUpdateRegistrantAjax('3');">NOT APPROVED</button>
		      									<button type="button" class="btn btn-warning btn-sm" id="butWait" onclick="adminUpdateRegistrantAjax('4');">WAIT LISTED</button>
		      								</c:when>
		      								<c:when test="${regbean.icfAppStatus eq 3}">
		      									<button type="button" class="btn btn-success btn-sm" id="butApprove" onclick="adminUpdateRegistrantAjax('2');">APPROVE</button>
		      									<button type="button" class="btn btn-warning btn-sm" id="butWait" onclick="adminUpdateRegistrantAjax('4');">WAIT LISTED</button>
		      								 </c:when>
		      								 <c:when test="${regbean.icfAppStatus eq 4}">
		      									<button type="button" class="btn btn-success btn-sm" id="butApprove" onclick="adminUpdateRegistrantAjax('2');">APPROVE</button>
		      									<button type="button" class="btn btn-danger btn-sm" id="butNotApprove" onclick="adminUpdateRegistrantAjax('3');">NOT APPROVED</button>
		      								 </c:when>
		      							</c:choose>
		      							<button type="button" class="btn btn-info btn-sm" id="butReset" onclick="adminUpdateRegistrantAjax('1');">RESET STATUS</button>
		      							</div>
								</div>
					</div>
					
					</esd:SecurityAccessRequired>
					
					<esd:SecurityAccessRequired permissions="ICF-REGISTRATION-ADMIN-STATUS">
					<br/><br/>
					<div class="card no-print">
						<div class="card-header"><b>RESEND EMAIL TO PARENT/GUARDIAN</b></div>
						<div class="card-body">
							This will resend the email type select to the email address linked to the registrant
							<br/><br/>	
							<div class="row">
							<div class="col-lg-6 col-md-6 col-sm-12">
						   		<select id="selemail" class="form-control">
		      									<option value="-1">Please select Email Type</option>
		      									<option value="1">Submission Confirmation</option>
		      									<option value="2">Approved</option>
		      									<option value="3">Not Approved</option>
		      									<option value="4">Waitlisted</option>
		      					</select>	
		      				</div>		
		      				<div class="col-lg-6 col-md-6 col-sm-12">	
		      		<button type="button" class="btn btn-info btn-sm" id="butSendEmail" onclick="resendemail('${regbean.icfAppId}');">RESEND EMAIL</button>	
		      				</div>	
		      				</div>	
		      							<div class='divResponseMsg alert alert-success' align='center' style="display: none;" id="successmsgemail"></div>
		      							</div>
								</div>
					
					</esd:SecurityAccessRequired>		


<br/><br/>
	<div class="alert alert-warning">
		<b>Confidentiality Warning:</b> This document and any attachments are
		intended for the sole use of the intended recipient(s), and contain
		privileged and/or confidential information. If you are not an intended
		recipient, any review, retransmission, printing, copying, circulation
		or other use of this message and any attachments is strictly
		prohibited.
	</div>

	

	
	<br />&nbsp;<br />
	
	<script>
$("#loadingSpinner").css("display","none");
</script>

</body>

</html>