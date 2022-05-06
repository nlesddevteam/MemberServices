<%@ page language="java"
         import="java.util.*, 
         com.esdnl.school.registration.kindergarten.bean.*" 
         isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib prefix="esd" uri="/WEB-INF/memberservices.tld" %>

<%
	System.out.println(">>> KINDERGARTEN REGISTRATION VIEWED BY " + request.getRemoteAddr() + " <<<");
%>

<html>
  
  <head>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">	
     <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <TITLE>Kinderstart/Kindergarten Registration</title>
     <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.10/jquery.mask.js"></script>
     <script>
 $('document').ready(function(){  		
	 			$("#loadingSpinner").css("display","none");
    		    $( "#txt_DateOfBirth" ).datepicker({
    		      changeMonth: true,//this option for allowing user to select month
    		      changeYear: true, //this option for allowing user to select from year range
    		      dateFormat: "dd/mm/yy",
    		      maxDate: '-2y',
    		      minDate: '-8y'
    		    });       		  
    		    
    		    $( "#txt_MCPExpiration" ).datepicker({
      		      changeMonth: true,//this option for allowing user to select month
      		      changeYear: true, //this option for allowing user to select from year range
      		      dateFormat: "mm/yy",
      		      maxDate: '+10y',
      		      minDate: '+0y'
      		    });  
    		    
    		  
    		    $('.phone').mask('(000) 000-0000');
    		    $('.pcode').mask('S0S 0S0');
    		    $('.mcpNum').mask('000000000000');
    		    
    		  }
    		);

	</script>
     
        <style>
        .pcode {text-transform:uppercase;}
        
        </style>
    
  </head>

  <body>
  
  		
			<c:choose>
				<c:when test="${ap eq null}">
				<!-- IF REGISTRATION CLOSED -->
				<div class="alert alert-danger" style="text-align:center;">
				<div style="float:left;font-size:24px;"><i class="fas fa-exclamation-circle"></i></div>
				<div style="float:right;font-size:24px;"><i class="fas fa-exclamation-circle"></i></div>
					<b>****** IMPORTANT NOTICE ******</b><br/>					
					We are currently NOT accepting Kinderstart/Kindergarten registrations at this time. <br/>
					Official Kinderstart/Kindergarten registration dates and times will be displayed on the <a href="/families/kindergartenregistration.jsp">Kindergarten information page HERE</a> and/or below.
					</div>	
						
						<c:if test="${fp ne null and fn:length(fp) gt 0}">							
							<div class="alert alert-info">
									<b>UPCOMING REGISTRATION DATES</b><br/>							
									
										<c:forEach items="${fp}" var='f'>											
												<br/><b><fmt:formatDate type="both" dateStyle="long" value="${f.startDate}" /></b> to <b><fmt:formatDate type="both" dateStyle="long" value="${f.endDate}" /></b>
												<ul>
												<c:forEach items='${f.zones}' var='z'>
													<li style='text-transform: capitalize'>
													<c:choose>
													<c:when test="${ (z.zoneName eq 'eastern') or (z.zoneName eq 'avalon') }">
													Avalon Region													
													</c:when>
													<c:otherwise>
													${z.zoneName} Region
													</c:otherwise>
													</c:choose>													
													</li>
												</c:forEach>
												</ul>
										
										</c:forEach>
									
							</div>
						</c:if>
					<div class="alert alert-warning"><b>PLEASE NOTE:</b>
							<ul>
							<li>DO NOT ATTEMPT to fill out the registration form prior to 9:00 a.m.
							<li>Information entered prior to 9:00 a.m. will be lost and not submitted. 
							<li>This page must be reloaded/refreshed after 9:00 a.m. (server time - see bottom right) for valid registration entry.

							</ul>
					</div>
					<br/>
		 			<div align="center">
		 			<a href="/families/kindergartenregistration.jsp" class="no-print btn btn-sm btn-danger"><i class="fas fa-sign-out-alt"></i> Exit Registration</a>
					</div>
				</c:when>
				
				
				<c:otherwise>
					<p>This online registration is an application to enroll your child in Kindergarten for the ${ap.schoolYear} school year. 
						The registration process will be completed once you have provided proof of address in person at the school on or before  
						<fmt:formatDate type="date" dateStyle="long" value="${ap.addressConfirmationDeadline}" />.
					
					<p><b>English:</b> Please register for your zoned school.		
				
					<p><b>French Immersion:</b> Please register for your zoned school if program is offered. If the program is not offered select the school closest to your residence.
				</c:otherwise>
		</c:choose>

				
		<c:choose>
			<c:when test="${ap ne null}">	
			<!--IF  REGISTRATION OPEN -->
				<div align='center'>
					<span style='color:red;'><i class="fas fa-exclamation-circle"></i></span> Required field. <span style='color:green;'><i class="fas fa-check"></i></span> Not Required/OK
				</div>
				<br />
				
							
				<div id='add-registrant-form' style='width:100%; display:inline;'>
					<form autocomplete="off" method='post' action="<c:url value='/schools/registration/kindergarten/addKindergartenRegistrant.html'/>" class="was-validated">
						<input type='hidden' name='registration_id' value='${ap.registrationId}' />
						
						<!-- SIBLING CHART -->
						<c:if test="${sibling ne null}">
							<input type='hidden' name='related_registrant_id' value='${sibling.registrantId}' />					

							<div class="card">
							  <div class="card-header">PREVIOUS STUDENT REGISTRATION</div>
							  <div class="card-body">
							  <table class="table table-responsive table-sm" width="100%" style="font-size:12px;">
							  		<tr><td width="25%" style="background-color:#f7f7f7;"><b>STUDENT NAME:</b></td><td width="75%">${sibling.studentLastName}, ${sibling.studentFirstName}</td></tr>
									<tr><td width="25%" style="background-color:#f7f7f7;"><b>MCP NUMBER:</b></td><td width="75%">${sibling.mcpNumber}</td></tr>
									<tr><td width="25%" style="background-color:#f7f7f7;"><b>REGISTRATION DATE:</b></td><td width="75%"><fmt:formatDate type="both" dateStyle="long" value="${sibling.registrationDate}" /></td></tr>
							  </table>
							  </div>  
							</div>
						</c:if>
						
						
						
						<div class="card">
							  <div class="card-header"><b>1. STUDENT INFORMATION</b></div>
							  <div class="card-body">
						 Please complete the following information to register your child for Kinderstart/Kindergarten. <br/>
						 <span style='color:red;'><i class="fas fa-exclamation-circle"></i></span> Required field. <span style='color:green;'><i class="fas fa-check"></i></span> Not Required/OK
						 <br/><br/>
						<b>(a) DEMOGRAPHICS</b>								
								
						<div class="row container-fluid" style="padding-top:5px;">
      							<div class="col-lg-3 col-12">
									<b>Student First Name:</b>
									<input placeholder="Student First Name" class='required form-control' errortext='1. STUDENT INFORMATION, (a) DEMOGRAPHICS First Name' type='text' id='txt_StudentFirstName' name='txt_StudentFirstName'  required autocomplete="f"/>
									<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
								</div>
								<div class="col-lg-3 col-12">
									<b>Student Last Name:</b>
									<input placeholder="Student Last Name" class='form-control' errortext='1. STUDENT INFORMATION, (a) DEMOGRAPHICS Last Name' type='text' id='txt_StudentLastName' name='txt_StudentLastName' required autocomplete="f"/>
									<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
								</div>
								<div class="col-lg-3 col-12">	
									<b>Gender:</b>
									<sreg:GenderDDL id='ddl_Gender' cls='form-control required'/>
									<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
								</div>
								<div class="col-lg-3 col-12">
									<b>Date of Birth (DD/MM/YYYY):</b>
									<input placeholder="dd/mm/yyyy" class='required datefield form-control' onkeydown="return false;" errortext='1. STUDENT INFORMATION, (a) DEMOGRAPHICS - Date of Birth' type='text' id='txt_DateOfBirth' name='txt_DateOfBirth' required autocomplete="f"/>
									<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please select date.</div>
								</div>
						</div>
								<!-- 
								<div align='center'>
									<div style='padding-top:8px; padding-bottom:5px; font-style:italic; width:60%; text-align:left;'>
										If an MCP number has not yet been assigned, please click the <b>GENERATE MCP</b> button below to generate a temporary number and expiration date 
										you can use to complete the registration.
									</div>
									<a id="lnkGenerateMCPInfo" class='opbutton'>GENERATE MCP</a>
								</div>
								 -->
						<br/>
						<b>(b) STUDENT MCP</b>
						<div class="row container-fluid" style="padding-top:5px;">
      							<div class="col-lg-4 col-12">	
									<b>MCP Number (12 digits):</b>
									<input placeholder="Valid MCP Number" class='required form-control mcpNum' autocomplete="f" errortext='1. STUDENT INFORMATION, (b) STUDENT MCP - MCP Number' type='text' id='txt_MCPNumber' name='txt_MCPNumber' maxlength="12" required/>
									<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please fill out this field.</div>
									<div class="alert alert-danger" id="mcpErr" style="display:none;">
									<b>ERROR:</b> 
									This MCP number has already been registered. Make sure you have not already registered. 
									Please confirm your entry and try again or contact the school for assistance.
									</div>
									</div>
									<div class="col-lg-3 col-12">
									<b>MCP Expiration (mm/yyyy):</b>
									<input class='required form-control' placeholder="mm/yyyy" autocomplete="f" onkeydown="return false;" errortext='1. STUDENT INFORMATION, (b) STUDENT MCP -  MCP Expiration' type='text' id='txt_MCPExpiration' name='txt_MCPExpiration' maxlength="7" required/>
									<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please select date.</div>
									</div>
						</div>
							
							
							<br />
								<b>(c) PHYSICAL ADDRESS:</b>
								<div class="row container-fluid" style="padding-top:5px;">
      								<div class="col-lg-6 col-12">	
											<b>Address: (Street Address, P.O. Box, etc)</b>
											<input required class='required form-control' placeholder="Enter Street Address (i.e. 10 Main Street)" errortext='1. STUDENT INFORMATION, (c) PHYSICAL ADDRESS - Street Address' type='text' id='txt_PhysicalStreetAddress1' name='txt_PhysicalStreetAddress1' autocomplete="f"/>
											<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please fill out this field.</div>
											<br/>
											<input placeholder="Enter PO Box, if any (i.e. P.O. Box 100)" class="form-control" type='text' id='txt_PhysicalStreetAddress2' name='txt_PhysicalStreetAddress2' autocomplete="f"/>
									</div>
								<div class="col-lg-3 col-12">
								<b>City/Town:</b>
								
										<select id='txt_PhysicalCityTown' name='txt_PhysicalCityTown' errortext='1. STUDENT INFORMATION, (c) PHYSICAL ADDRESS -  City/Town' class="form-control" required>
											<jsp:include page="includes/townlist.jsp" />                      		
                       					 </select>
                       					 <div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please select.</div>
                       			</div>		                      
								<div class="col-lg-1 col-12">	
								<b>Province:</b><br/>NL
								</div>
								<div class="col-lg-2 col-12">
								<b>Postal Code (X#X #X#):</b>
								<!-- make letters capital, add space if no space entered. -->
								<input required placeholder="X#X #X#" class='required form-control pcode' errortext='1. STUDENT INFORMATION, (c) PHYSICAL ADDRESS -  Postal Code' type='text' id='txt_PhysicalPostalCode' name='txt_PhysicalPostalCode' maxlength="7" autocomplete="f" style="text-transform: uppercase"/>
								<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please fill out this field.</div>
								</div>		
								</div>		
								
								<br/>
								<b>(d) MAILING ADDRESS:</b> <input id='chk_MailingAddressSame' type="checkbox" /> Same as physical address?
								<div class="row container-fluid" style="padding-top:5px;">
								<div class="col-lg-6 col-12">										
								<b>Address: (Street Address, P.O. Box, etc)</b>
								<input placeholder="Enter Street Address (i.e. 10 Main Street)" required class='required form-control' required errortext='1. STUDENT INFORMATION, (d) MAILING ADDRESS - Address' type='text' id='txt_MailingAddress1' name='txt_MailingAddress1' autocomplete="f"/>
								<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please fill out this field.</div>
								<br/>
								<input placeholder="Enter PO Box, if any (i.e. P.O. Box 100)" class="form-control"  type='text' id='txt_MailingAddress2' name='txt_MailingAddress2' autocomplete="f"/>
								</div>
								<div class="col-lg-3 col-12">
								<b>City/Town:</b>
										<select class="form-control" id='txt_MailingCityTown' name='txt_MailingCityTown' errortext='1. STUDENT INFORMATION, (d) MAILING ADDRESS - City/Town' required>											
                       						<jsp:include page="includes/townlist.jsp" />       
                       						<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please select.</div>       		
                       					 </select>
                       			</div>		   
								<div class="col-lg-1 col-12">	
								<b>Province:</b><br/> NL
								</div>
								<div class="col-lg-2 col-12">
								<b>Postal Code (X#X #X#):</b>
								<!-- <input required onkeyup="this.value = this.value.toUpperCase();" onchange="this.value = this.value.replace(' ','').replace(/[^\dA-Z]/g,' ').replace(/(.{3})/g, '$1 ').trim();" maxlength="7"/>-->
								<input required placeholder="X#X #X#" class='required form-control pcode' errortext='1. STUDENT INFORMATION, (d) MAILING ADDRESS - Postal Code' type='text' id='txt_MailingPostalCode' name='txt_MailingPostalCode' maxlength="7" style="text-transform: uppercase"/>
								<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please fill out this field.</div>		
						</div>
						</div>
						</div></div>
						
						<br/>
						
<!-- SCHOOL INFORMATION ----------------------------------------------------------------------------------------->						
						<div class="card">
							  <div class="card-header"><b>2. SCHOOL INFORMATION 							  
							  <c:choose>
							  <c:when test="${ap.schoolYear ne null}">
							    for YEAR ${ap.schoolYear}
							  </c:when>
							  <c:otherwise>
							  
							  </c:otherwise>
							  </c:choose>
							 </b></div>
							  <div class="card-body">							  
							  <span style='color:red;'><i class="fas fa-exclamation-circle"></i></span> Required field. <span style='color:green;'><i class="fas fa-check"></i></span> Not Required/OK
						 <br/><br/>
									<div class="row container-fluid" style="padding-top:5px;">			      							
											<div class="col-lg-6 col-12">	
											<b>School:</b>
											<sreg:SchoolsDDL cls='required form-control' id='ddl_School' period='${ap}' dummy='true'/>
											<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please select.</div>
											</div>
											<div class="col-lg-4 col-12">	
											<b>Program Stream:</b>
											<sreg:SchoolStreamDDL cls='required form-control' id='ddl_Stream' />
											<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please select (Select school first to get list.)</div>
											</div>
									</div>
							</div>
						</div>
					
						<br/>
						
<!-- CONTACT INFORMATION ----------------------------------------------------------------------------------------->		
						
						<div class="card">
							  <div class="card-header"><b>3. CONTACT INFORMATION </b></div>
								<div class="card-body">
								<span style='color:red;'><i class="fas fa-exclamation-circle"></i></span> Required field. <span style='color:green;'><i class="fas fa-check"></i></span> Not Required/OK
						 <br/><br/>
																
								<b>(a) PRIMARY CONTACT</b>
								
									<div class="row container-fluid" style="padding-top:5px;">			      							
											<div class="col-lg-8 col-12">	
											<b>Full Name (Firstname Lastname):</b>
											<input placeholder="Full name (Firstname Lastname)" required class='required form-control' errortext='3. CONTACT INFORMATION, (a) PRIMARY CONTACT - Full Name' type='text' id='txt_PrimaryContactName' name='txt_PrimaryContactName' autocomplete="f" />
											<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please enter name.</div>
											</div>
											<div class="col-lg-4 col-12">		
											<b>Relationship to Student:</b>
											<sreg:ContactRelationshipDDL id='ddl_PrimaryContactRelationship' cls='form-control required' />
											<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please select.</div>
											</div>
									</div>
									<div class="row container-fluid" style="padding-top:5px;">
											<div class="col-lg-3 col-12">
											<b>Primary Phone (xxx) xxx-xxxx :</b>									
											<input placeholder="Primary Contact Number" required class='one-required form-control phone' data-mask="00/00/0000" errortext='3. CONTACT INFORMATION, (a) PRIMARY CONTACT - Home Phone' type='text' id='txt_PrimaryContactHomePhone' name='txt_PrimaryContactHomePhone' maxlength="14" autocomplete="f"/>
											<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please fill out at least one contact number.</div>
											</div>
											<div class="col-lg-3 col-12">
											<b>Work Phone (xxx) xxx-xxxx :</b>
											<input placeholder="Optional" class='one-required form-control phone' errortext='3. CONTACT INFORMATION, (a) PRIMARY CONTACT -  Work Phone' type='text' id='txt_PrimaryContactWorkPhone' name='txt_PrimaryContactWorkPhone' maxlength="14" autocomplete="f" />
											</div>
											<div class="col-lg-3 col-12">	
											<b>Cell Phone (xxx) xxx-xxxx :</b>
											<input placeholder="Optional" class='one-required form-control phone' errortext='3. CONTACT INFORMATION, (a) PRIMARY CONTACT - Cell Phone' type='text' id='txt_PrimaryContactCellPhone' name='txt_PrimaryContactCellPhone' maxlength="14" autocomplete="f" />
											</div>
											<div class="col-lg-3 col-12">		
											<b>Email:</b>
											<input required placeholder="Enter Valid Email Address" class='required form-control' errortext='3. CONTACT INFORMATION, (a) PRIMARY CONTACT - Email Address' type='text' id='txt_PrimaryContactEmail' name='txt_PrimaryContactEmail' autocomplete="f" />
											<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please provide valid email address.</div>
											</div>
									</div>
									<br/>
									
							
							<b>(b) OPTIONAL CONTACT</b>
										<div class="row container-fluid" style="padding-top:5px;">			      							
											<div class="col-lg-8 col-12">								
											<b>Full Name: (Firstname Lastname)</b>
											<input placeholder="Optional" class='optionally-required form-control' type='text' id='txt_SecondaryContactName' name='txt_SecondaryContactName' autocomplete="f"/>
											</div>
											<div class="col-lg-4 col-12">			
											<b>Relationship to Student:</b>
											<sreg:ContactRelationshipDDL id='ddl_SecondaryContactRelationship' cls='form-control'/>
											</div>
										</div>
										<div class="row container-fluid" style="padding-top:5px;">	
											<div class="col-lg-3 col-12">			
											<b>Primary Phone (xxx) xxx-xxxx:</b>
											<input placeholder="Optional" class='optionally-one-required form-control phone' type='text' id='txt_SecondaryContactHomePhone' name='txt_SecondaryContactHomePhone' maxlength="14" autocomplete="f"/>
											</div>
											<div class="col-lg-3 col-12">
											<b>Work Phone (xxx) xxx-xxxx:</b>
											<input placeholder="Optional" class='optionally-one-required form-control phone' type='text' id='txt_SecondaryContactWorkPhone' name='txt_SecondaryContactWorkPhone' maxlength="14" autocomplete="f"/>
											</div>
											<div class="col-lg-3 col-12">			
											<b>Cell Phone (xxx) xxx-xxxx:</b>
											<input placeholder="Optional" class='optionally-one-required form-control phone' type='text' id='txt_SecondaryContactCellPhone' name='txt_SecondaryContactCellPhone' maxlength="14" autocomplete="f"/>
											</div>
											<div class="col-lg-3 col-12">			
											<b>Email:</b>
											<input placeholder="Optional" class='optionally-required form-control' type='text' id='txt_SecondaryContactEmail' name='txt_SecondaryContactEmail' autocomplete="f" />
											</div>
									</div>
							
							<br/>
							<b>(c) EMERGENCY  CONTACT</b><br/>
							<span style="color:Red;">&nbsp;&nbsp;All parents/guardians must provide an alternative contact in case of emergency.</span>
										<div class="row container-fluid" style="padding-top:5px;">			      							
											<div class="col-lg-8 col-12">								
											<b>Full Name: (Firstname Lastname)</b>
											<input placeholder="Enter name (Firstname Lastname)" required class='required form-control' errortext='3. CONTACT INFORMATION, (c) EMERGENCY CONTACT - Full Name' type='text' id='txt_EmergencyContactName' name='txt_EmergencyContactName' autocomplete="f" />
											<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please enter name.</div>
											</div>
											<div class="col-lg-4 col-12">
											<b>Telephone (xxx) xxx-xxxx:</b>
											<input placeholder="Enter Number" required class='required form-control phone' errortext='3. CONTACT INFORMATION, (c) EMERGENCY CONTACT - Telephone' type='text' id='txt_EmergencyContactPhone' name='txt_EmergencyContactPhone' maxlength="14" autocomplete="f" />
											<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please provide valid contact number.</div>
											</div>
										</div>
							</div>		
							
						</div><br />
						
<!-- OTHER INFORMATION ----------------------------------------------------------------------------------------->							
						<div class="card">
							  <div class="card-header"><b>4. OTHER INFORMATION </b></div>							  
								<div class="card-body">
								<span style="color:red;">This section must have all questions answered.</span><br/>
																
										<div class="row container-fluid" style="padding-top:5px;border-top:1px solid #e5e5e5;">			      							
											<div class="col-8">	
											(a) Are there any custody issues of which the school should be aware?<br/>
											<i>Court documentation is required if either parent is to be denied from receiving academic information and/or access to child.</i>
											</div>
											<div class="col-4">
											<sreg:YesNoRBG id='rbg_CustodyIssues' />											
											</div>
										</div>	
										<div class="row container-fluid" style="padding-top:5px;border-top:1px solid #e5e5e5;">			      							
											<div class="col-8">														
											(b) Does your child have any health or other concerns of which we should be aware?
											</div>
											<div class="col-4">
											<sreg:YesNoRBG id='rbg_HealthOtherConcerns' />
											</div>
										</div>	
										<div class="row container-fluid" style="padding-top:5px;border-top:1px solid #e5e5e5;">			      							
											<div class="col-8">
											(c) Does your child require an accessible facility?
											</div>
											<div class="col-4">
											<sreg:YesNoRBG id='rbg_AccessibleFacility'/>
											</div>
										</div>	
										<div class="row container-fluid" style="padding-top:5px;border-top:1px solid #e5e5e5;">			      							
											<div class="col-8">											
											(d) Do you have a child currently enrolled in the Early French Immersion Program in this school?
											</div>
											<div class="col-4">
											<sreg:YesNoRBG id='rbg_CurrentChildEFI' />
											</div>
										</div>
								</div>
						</div>
						<br/><br/>
<!-- ERROR INFORMATION ----------------------------------------------------------------------------------------->				

				<div class="card" id="pnl-error-msg" style='display:none;'>
							  <div class="card-header bg-danger" style="color:White;"><b><i class="fas fa-exclamation-circle"></i> FORM SUBMIT ERRORS! </b></div>							  
								<div class="card-body" style="color:red;">The following fields MUST be corrected before your registration can be submitted:<br/><br/>
										<div class="row container-fluid" style="padding-top:5px;">			      							
											<div class="col-12">	
												<div id='error-msg'></div>
											</div>
											
											</div>
										</div>
								</div>
									
						
						
						<br/><br/>
						<div align='center' class="alert alert-info">							
								This personal information is collected under the authority of the Schools Act, 1997 and will be used for the establishment of a student record, 
								determination of residency, to administer educational programs and support services, and for other purposes necessary for an operating program 
								or activity, including program placement, determination of eligibility for funding, contact and health related information in the event 
								of problems or emergencies. This information will be treated in accordance with the privacy protection provisions of the Access to Information 
								and Protection of Privacy Act. If you require further information on the collection and use of this information, contact your school principal 
								or the NLESD ATIPP Coordinator at 709-758-2372.							
						</div>
						<c:choose>
							<c:when test="${ap ne null}">
								<div align='center' class="alert alert-danger">								
										By submitting this form, I hereby certify that the information given on this form is accurate and complete to the best of my knowledge. 
								</div>								
								<br />
								<div style='text-align: center;' class="no-print">
									<input id='btn_SubmitRegistration' type='button' value='Submit Registration' class=' btn btn-primary btn-sm' /> 
									<input id='btn_cancelAddReg' type='button' value='Cancel' class='btn btn-danger btn-sm' />
								</div>
							</c:when>
							<c:otherwise>
								<div style='text-align: center;' class="no-print">						
									<a href='/index.jsp' class="btn btn-sm btn-primary">HOME</a>
								</div>
							</c:otherwise>
						</c:choose>						
					</form>
				</div>
			</c:when>
			<c:otherwise>
			<!-- REGISTRATION CLOSED -->
			</c:otherwise>
		</c:choose>

	</body>
	
</html>