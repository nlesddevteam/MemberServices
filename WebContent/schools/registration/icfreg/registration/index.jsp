<%@ page language="java"
         import="java.util.*" 
         isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib prefix="esd" uri="/WEB-INF/memberservices.tld" %>
<html>
  
  <head>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">	

    <TITLE>ICF Registration</title>
 
     
     <script>
 $('document').ready(function(){  		
	 			$("#loadingSpinner").css("display","none");
	 			$('#txt_ContactNumber1').mask('(000) 000-0000');
	 			$('#txt_ContactNumber2').mask('(000) 000-0000');
	 			
	 		
    		  }
    		);

	</script>
     
        <style>
        .pcode {text-transform:uppercase;}
        
        </style>
    
  </head>

  <body>
  
  
  		<c:choose>
				<c:when test="${ap.icfRegPerId lt 1}">
				<!-- IF REGISTRATION CLOSED -->
				<div class="alert alert-danger" style="text-align:center;margin-top:10px;">
				<div style="float:left;font-size:24px;"><i class="fas fa-exclamation-circle"></i></div>
				<div style="float:right;font-size:24px;"><i class="fas fa-exclamation-circle"></i></div>
					<b>****** IMPORTANT NOTICE ******</b><br/>					
					We are currently NOT accepting ICF registrations at this time. <br/>
					Official ICF registration dates and times will be displayed on the <a href="/families/icfregistration.jsp">ICF information page HERE</a> and/or below.
					</div>	
						
						<c:if test="${fp ne null and fn:length(fp) gt 0}">							
							<div class="alert alert-info">
									<b>UPCOMING REGISTRATION DATES</b><br/><br/>							
									<ul>
										<c:forEach items="${fp}" var='f'>											
												<li><b><fmt:formatDate type="both" dateStyle="long" value="${f.icfRegStartDate}" /></b> to <b><fmt:formatDate type="both" dateStyle="long" value="${f.icfRegEndDate}" /></b>
										</c:forEach>
									</ul>
							</div>
						</c:if>
					<div class="alert alert-warning"><b>PLEASE NOTE:</b>
							<ul>
							<li>Once registration starts, the registration form will be displayed on this page.
							<li>DO NOT ATTEMPT to fill out the registration form prior to 9:00 a.m.
							<li>Information entered prior to 9:00 a.m. will be lost and not submitted. 
							<li>This page must be reloaded/refreshed after 9:00 a.m. server time (see bottom) for valid registration entry.

							</ul>
					</div>
					<br/>
		 			<div align="center">
		 			<a href="/families/icfregistration.jsp" class="no-print btn btn-sm btn-danger"><i class="fas fa-sign-out-alt"></i> Exit Registration</a>
					</div>
				</c:when>
				
				
				
			<c:when test="${ap.icfRegPerId gt 0}">	
			<!--IF  REGISTRATION OPEN -->
					
				<div class="alert alert-success" style="margin-top:10px;text-align:center;"><b>REGISTRATION IS OPEN</b></div>			
				<div id='add-registrant-form' style='width:100%; display:inline;'>
					<form autocomplete="off" method='post' action="addNewRegistration.html" class="was-validated">
						<input type='hidden' name='registration_id' value='${ap.icfRegPerId}' />
						<input type='hidden' name='hidschool' id='hidschool'/>
						<div class="card">
							  <div class="card-header" style="font-size:16px;"><b>Please complete the following information to register.</b></div>
							  <div class="card-body">
							  
					<p>This online registration is an application to enroll your child in <b>Intensive Core French (ICF)</b> for the 
					<b>${ap.icfRegPerSchoolYear} school year</b> at the school you select below.<br/><br/>   
							  
						 <span style='color:red;'><i class="fas fa-exclamation-circle"></i></span> Required field. <span style='color:green;'><i class="fas fa-check"></i></span> Not Required/OK
						 <br/><br/>
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
											<b>Parent/Guardian Email Address:</b> (No spaces)
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
											<b>School Registering For:</b>									
											<select id="selSchool" name="selSchool"  class='form-control' required>
												<option value="">Select School</option>
												<c:forEach items="${slist}" var='ss'>
													<option value="${ss.icfSchSchoolId}">${ss.icfSchSchool}</option>
												</c:forEach>	
											</select>
											
								</div>
						</div>	
						
						
						</div>
						
							
						</div>
						<br />
	
					
						
						
						
						<br/>
						


<!-- ERROR INFORMATION ----------------------------------------------------------------------------------------->				

				<div class="card" id="pnl-error-msg" style='display:none;margin-bottom:10px;'>
							  <div class="card-header bg-danger" style="color:White;"><b><i class="fas fa-exclamation-circle"></i> SUBMISSION ERROR: YOU HAVE FORM SUBMIT ERRORS! </b></div>							  
								<div class="card-body"><b>The following fields MUST be corrected before your registration can be submitted:</b><br/><br/>
										<ul>
										<div id='error-msg'></div>
										</ul>
										
								<div class="alert alert-danger">
								<b>PLEASE NOTE:</b> If the form gives a submission error saying <b>&quot;Parent/Guardian Email Address invalid format&quot;</b>, please make sure there are NO SPACES entered 
								before or at the end of the email address. Using auto-fill and/or copy and paste, on some phones may insert a space by 
								default which will result in an error.
								</div>		
										
								</div>
				</div>
												
						<c:choose>
							<c:when test="${ap ne null}">							
								<div align='center' class="alert alert-warning">								
										By submitting this form, I hereby certify that the information given on this form is accurate and complete to the best of my knowledge. 
								</div>								
								<br />
								<div style='text-align: center;' class="no-print">
									<input id='btn_SubmitRegistration'  type='button' value='Submit Registration' class=' btn btn-primary btn-sm' onclick="return submitRegistration();" /> 
									<input id='btn_cancelAddReg' type='button'  value='Cancel' class='btn btn-danger btn-sm' onclick="cancelRegistration();"/>
								</div>
							</c:when>
							<c:otherwise>
								<div style='text-align: center;' class="no-print">						
									<a href='/families/icfregistration.jsp' class="btn btn-sm btn-primary">EXIT REGISTRATION</a>
								</div>
							</c:otherwise>
						</c:choose>	
						
						<br/><br/>
						<div align='center' class="alert alert-info">							
								<b>NOTICE:</b> This personal information is collected under the authority of the Schools Act, 1997 and will be used for the establishment of a student record, 
								determination of residency, to administer educational programs and support services, and for other purposes necessary for an operating program 
								or activity, including program placement, determination of eligibility for funding, contact and health related information in the event 
								of problems or emergencies. This information will be treated in accordance with the privacy protection provisions of the Access to Information 
								and Protection of Privacy Act. If you require further information on the collection and use of this information, contact your school principal 
								or the NLESD ATIPP Coordinator at 709-758-2372.							
						</div>
											
					</form>
				</div>
			</c:when>
			<c:otherwise>
			<div class="alert alert-danger" style="text-align:center;margin-top:10px;">
				<div style="float:left;font-size:24px;"><i class="fas fa-exclamation-circle"></i></div>
				<div style="float:right;font-size:24px;"><i class="fas fa-exclamation-circle"></i></div>
					<b>****** IMPORTANT NOTICE REGISTRATION IS CLOSED ******</b><br/>					
					We are currently NOT accepting ICF registrations at this time. <br/>
					Official ICF registration dates and times will be displayed on the <a href="/families/icfregistration.jsp">ICF information page HERE</a>.
					</div>	
					
					<div style='text-align: center;' class="no-print">						
									<a href='/families/icfregistration.jsp' class="btn btn-sm btn-primary">EXIT REGISTRATION</a>
								</div>
					
			</c:otherwise>
		</c:choose>
	</body>
	
</html>