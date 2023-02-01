<%@ page language="java" isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions'%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd"%>

<esd:SecurityCheck permissions="ICF-REGISTRATION-ADMIN-ADD-NEW" />

<html>

<head>

<meta name="viewport" content="width=device-width, initial-scale=1.0">
<TITLE>Add Registrant</title>
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

	
		<div class="siteHeaderGreen">${perbean.icfRegPerSchoolYear} - Add New Registrant</div> 
		<form autocomplete="off" method='post' class="was-validated">
		<input type="hidden" id="regbeanid" value="${perbean.icfRegPerId}">	
	
					<div class="card">
							  <div class="card-header"><b>REGISTRANT INFORMATION</b></div>
							  <div class="card-body">	
								<div class="row" style="padding-top:5px;">
      							<div class="col-lg-6 col-md-6 col-sm-12">
									<b>Student Full Name:</b>
									<input placeholder="Student Full Name" class='form-control' errortext='Student Full Name' type='text' id='txt_StudentName' name='txt_StudentName'  required autocomplete="f"/>
									<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
								</div>
						
								<div class="col-lg-6 col-md-6 col-sm-12">
									<b>Parent/Guardian Full Name:</b>
									<input placeholder="Parent/Guardian Full Name" class='form-control' errortext='Parent/Guardian Full Name' type='text' id='txt_GuardianName' name='txt_GuardianName' required autocomplete="f"/>
									<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
								</div>
						</div>
						<div class="row" style="padding-top:5px;">		
								<div class="col-lg-4 col-md-4 col-sm-12">		
											<b>Parent/Guardian Email Address:</b>
											<input required placeholder="Enter a valid email address" class='form-control' errortext='Parent/Guardian Email Address' type='text' id='txt_ParentGuardianEmail' name='txt_ParentGuardianEmail' autocomplete="f" />
											<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please provide valid email address.</div>
								</div>
							
								<div class="col-lg-4 col-md-4 col-sm-12">
											<b>Contact Telephone Number: (xxx) xxx-xxxx</b>									
											<input placeholder="i.e. (709) 111-1111" class='form-control phone'  errortext='Contact Number 1' type='text' id='txt_ContactNumber1' name='txt_ContactNumber1' maxlength="14" required autocomplete="f"/>
											<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please enter a contact telephone number.</div>
								</div>
								
								<div class="col-lg-4 col-md-4 col-sm-12">
											<b>Optional Telephone Number: (xxx) xxx-xxxx</b>									
											<input placeholder="i.e. (709) 111-1111" class='form-control' type='text' id='txt_ContactNumber2' name='txt_ContactNumber2' maxlength="14" autocomplete="f"/>
								</div>
						</div>
						<div class="row" style="padding-top:5px;">		
								<div class="col-lg-6 col-md-6 col-sm-12">
								<esd:SecurityAccessRequired permissions="ICF-REGISTRATION-CHANGE-SCHOOL">
											<b>School Registering For:</b>									
											<select id="selSchool" name="selSchool"  class='form-control' required>
												<option value="">Select School</option>
												<c:forEach items="${slist}" var='ss'>
													<option value="${ss.icfSchSchoolId}">${ss.icfSchSchool}</option>
												</c:forEach>	
											</select>
								</esd:SecurityAccessRequired>	
								<esd:SecurityAccessRequired permissions="ICF-REGISTRATION-SCHOOL-READONLY">
												<input type="hidden" id="selSchool" value="-999">
								</esd:SecurityAccessRequired>		
								</div>
						</div>		
											
											
											
								</div>
								</div>
								
								<div class="row" style="padding-top:5px;">
								<div class="col-md-12" align="center">
									<a onclick="validateRegistrantFormAdd()" class='btn btn-sm btn-success' >Save</a>&nbsp;&nbsp;									
									<a onclick="window.history.go(-1);" class='btn btn-sm btn-danger' >Back to Registrants List</a>
								
      							</div>
      							</div>
      							
      							<br/><br/>
      							<div class="card" id="pnl-error-msg" style='display:none;'>
							  <div class="card-header bg-danger" style="color:White;"><b><i class="fas fa-exclamation-circle"></i> SUBMISSION ERROR: YOU HAVE FORM SUBMIT ERRORS! </b></div>							  
								<div class="card-body">The following fields MUST be corrected before your registration can be submitted:<br/>
										<ul>
										<div id='error-msg'></div>
										</ul>
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