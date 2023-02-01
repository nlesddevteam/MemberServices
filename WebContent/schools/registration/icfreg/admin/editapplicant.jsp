<%@ page language="java" isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd"%>

<esd:SecurityCheck permissions="ICF-REGISTRATION-ADMIN-EDIT" />

<html>

<head>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<TITLE>Edit Registrant</title>

     <script>
 $('document').ready(function(){  	
	 			$("#loadingSpinner").css("display","none");
	 			$('#txt_ContactNumber1').mask('(000) 000-0000');
	 			$('#txt_ContactNumber2').mask('(000) 000-0000');
	 		}
    		);

	</script>
</head>

<body>

	<div align='center' style='font-size: 14pt; font-weight: bold; color: #004178; padding-top: 10px;padding-bottom: 15px;'>
		${perbean.icfRegPerSchoolYear} <span id="spanschoolname">${regbean.icfAppSchoolName ne null?regbean.icfAppSchoolName:"N/A"}</span>
		Registration Information<br /> <span style="text-transform: Uppercase; color: Black;" id="spanfullname">${regbean.icfAppFullName ne null?regbean.icfAppFullName:"N/A"}</span>
	</div>
	
	<form autocomplete="off" method='post' class="was-validated">
		<input type="hidden" id="testingid" value="${regbean.icfAppId}">	
					<div class="card">
							  <div class="card-header"><b>REGISTRANT INFORMATION</b></div>
							  <div class="card-body">	
								<div class="row" style="padding-top:5px;">
		      						<div class="col-lg-6 col-md-6 col-sm-12">
										<b>Student Full Name:</b>
										<input placeholder="Student Full Name" class='required form-control' errortext='Student Full Name' type='text' id='txt_StudentName' name='txt_StudentName'  
										required autocomplete="f" value="${regbean.icfAppFullName}"/>
										<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
									</div>
								
		      						<div class="col-lg-6 col-md-6 col-sm-12">
										<b>Parent/Guardian Full Namer:</b>
										<input placeholder="Parent/Guardian Full Name" class='required form-control' errortext='Parent/Guardian Full Name' type='text' id='txt_GuardianName' name='txt_GuardianName'  
										required autocomplete="f" value="${regbean.icfAppGuaFullName}"/>
										<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
									</div>
								</div>
								<div class="row" style="padding-top:5px;">		
								<div class="col-lg-4 col-md-4 col-sm-12">		
											<b>Parent/Guardian Email Address:</b>
											<input required placeholder="Enter Valid Email Address" class='required form-control' errortext='Parent/Guardian Email Address' type='text' id='txt_ParentGuardianEmail' name='txt_ParentGuardianEmail' autocomplete="f"
											value="${regbean.icfAppEmail}" />
											<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please provide valid email address.</div>
								</div>
									
								<div class="col-lg-4 col-md-4 col-sm-12">
											<b>Contact Number 1 (xxx) xxx-xxxx :</b>									
											<input placeholder="Contact Number 1" required class='one-required form-control phone'  errortext='Contact Number 1' type='text' id='txt_ContactNumber1' name='txt_ContactNumber1' maxlength="14" autocomplete="f"
											 value="${regbean.icfAppContact1}"/>
											<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please enter contact number 1.</div>
								</div>
										
								<div class="col-lg-4 col-md-4 col-sm-12">
											<b>Contact Number 2 (xxx) xxx-xxxx :</b>									
											<input placeholder="Contact Number 2"   class='optionally-required form-control' type='text' id='txt_ContactNumber2' name='txt_ContactNumber2' maxlength="14" autocomplete="f"
											value="${regbean.icfAppContact2 eq null?'':regbean.icfAppContact2}"/>
								</div>
								</div>
								<div class="row" style="padding-top:5px;">		
								<div class="col-lg-6 col-md-6 col-sm-12">
											<esd:SecurityAccessRequired permissions="ICF-REGISTRATION-CHANGE-SCHOOL">
											<b>School :</b>									
											<select id="selSchool" name="selSchool"  class='required form-control'>
												<option value="">Select School</option>
												<c:forEach items="${slist}" var='ss'>
												<c:choose>
												<c:when test="${regbean.icfAppSchool eq ss.icfSchSchoolId}">
													<option value="${ss.icfSchSchoolId}" SELECTED>${ss.icfSchSchool}</option>
												</c:when>
												<c:otherwise>
													<option value="${ss.icfSchSchoolId}">${ss.icfSchSchool}</option>
												</c:otherwise>
												</c:choose>
													
												</c:forEach>	
											</select>
											</esd:SecurityAccessRequired>
											<esd:SecurityAccessRequired permissions="ICF-REGISTRATION-SCHOOL-READONLY">
												<input type="hidden" id="selSchool" value="-999">
											</esd:SecurityAccessRequired>
											
								</div>
								</div>
								<div class="row" style="padding-top:5px;">
								<div class="col-md-6 col-sm-12">
		      					<b>Registration Date/Time :</b><br/><div class="dField">${regbean.getIcfAppDateSubmittedFormatted()}</div>
								</div>
								<div class="col-md-6 col-sm-12">
		      						<b>Registration Status :</b><div class="dField">${regbean.getApplicantStatusString()}</div>
								</div>
								</div>
								<div class="row"  style="padding-top:10px;">
								<div class="col-lg-12" style="text-align:center;">
									<a title="Save Changes" onclick="validateRegistrantForm();" class='btn btn-sm btn-success' >Save Changes</a>&nbsp;&nbsp;
									<a title="Back to Registrants List" onclick="window.history.go(-1);" class='btn btn-sm btn-danger' >Back to Registrants List</a>
      							</div>
      							</div>
      						<div class="card" id="pnl-error-msg" style='display:none;'>
							  <div class="card-header bg-danger" style="color:White;"><b><i class="fas fa-exclamation-circle"></i> FORM SUBMIT ERRORS! </b></div>							  
								<div class="card-body" style="color:red;">The following fields MUST be corrected before your registration can be submitted:<br/>
										<ul>
										<div id='error-msg'></div>
										</ul>
								</div>
							</div>
								
										
																
							</div>
					</div>

</form>



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