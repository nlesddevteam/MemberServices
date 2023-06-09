<%@ page language="java"
         import="java.util.*, com.esdnl.school.registration.kindergarten.bean.*" 
         isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="KINDERGARTEN-REGISTRATION-SCHOOL-VIEW" /> 

<html>
  
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">	
    <TITLE>Student Registration</title>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
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
  
    <div align='center' style='font-size:14pt;font-weight:bold;color:#004178;;padding-bottom:15px;'>
  		${kr.registration.schoolYear} ${ usr.personnel.school.schoolName } Registration Information <br/>
	  	<span style="color:Green;">ADD NEW REGISTRANT</span>
  	</div>
  
  <div align='center'>
					<span style='color:red;'><i class="fas fa-exclamation-circle"></i></span> Required field. <span style='color:green;'><i class="fas fa-check"></i></span> Not Required/OK
				</div>
				<br />
				
			  
  <c:if test="${ msg ne null }">
			<div clas="alert alert-danger" align='center' >
				<b>ERRORS:</b><br/> ${ msg }
			</div>
</c:if>
  
  <div id='add-registrant-form' style='width:100%; display:inline;'>
			<form method='post' action="/MemberServices/schools/registration/kindergarten/admin/school/addKindergartenRegistrant.html" class="was-validated">
				<input type='hidden' name='op' value='registrant-added' />
				<input type='hidden' name='registration_id' value='${ krp.registrationId }' />
  
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
									<input placeholder="Student First Name" class='required form-control' errortext='1. STUDENT INFORMATION, (a) DEMOGRAPHICS First Name' type='text' id='txt_StudentFirstName' name='txt_StudentFirstName' required autocomplete="f"/>
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
									<b>Date of Birth (DD/DMM/YYYY):</b>
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
									<input class='required form-control' placeholder="mm/yyyy" autocomplete="f" onkeydown="return false;" errortext='1. STUDENT INFORMATION, (b) STUDENT MCP -  MCP Expiration' type='text' id='txt_MCPExpiration' name='txt_MCPExpiration' maxlength="7"  required/>
									<div class="invalid-feedback"><i class="fas fa-arrow-up"></i>Please select date.</div>
									</div>
						</div>
							
							
							<br />
								<b>(c) PHYSICAL ADDRESS:</b>
								<div class="row container-fluid" style="padding-top:5px;">
      								<div class="col-lg-6 col-12">	
											<b>Address: (Street Address, P.O. Box, etc)</b>
											<input required class='required form-control' placeholder="Enter Street Address (i.e. 10 Main Street)" errortext='1. STUDENT INFORMATION, (c) PHYSICAL ADDRESS - Street Address' type='text' id='txt_PhysicalStreetAddress1' name='txt_PhysicalStreetAddress1'  autocomplete="f"/>
											<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please fill out this field.</div>
											<br/>
											<input placeholder="Enter PO Box, if any (i.e. P.O. Box 100)" class="form-control" type='text' id='txt_PhysicalStreetAddress2' name='txt_PhysicalStreetAddress2'  autocomplete="f"/>
									</div>
								<div class="col-lg-3 col-12">
								<b>City/Town:</b>
								
										<select id='txt_PhysicalCityTown' name='txt_PhysicalCityTown' errortext='1. STUDENT INFORMATION, (c) PHYSICAL ADDRESS -  City/Town' class="form-control" required>
											<jsp:include page="../../includes/townlist.jsp" />                      		
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
								<input placeholder="Enter PO Box, if any (i.e. P.O. Box 100)" class="form-control"  type='text' id='txt_MailingAddress2' name='txt_MailingAddress2'  autocomplete="f"/>
								</div>
								<div class="col-lg-3 col-12">
								<b>City/Town:</b>
										<select class="form-control" id='txt_MailingCityTown' name='txt_MailingCityTown' errortext='1. STUDENT INFORMATION, (d) MAILING ADDRESS - City/Town' required>											
                       						<jsp:include page="../../includes/townlist.jsp" />       
                       						<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please select.</div>       		
                       					 </select>
                       			</div>		   
								<div class="col-lg-1 col-12">	
								<b>Province:</b><br/> NL
								</div>
								<div class="col-lg-2 col-12">
								<b>Postal Code (X#X #X#):</b>
								<!-- <input required onkeyup="this.value = this.value.toUpperCase();" onchange="this.value = this.value.replace(' ','').replace(/[^\dA-Z]/g,' ').replace(/(.{3})/g, '$1 ').trim();" maxlength="7"/>-->
								<input required placeholder="X#X #X#" class='required form-control pcode' errortext='1. STUDENT INFORMATION, (d) MAILING ADDRESS - Postal Code' type='text' id='txt_MailingPostalCode' name='txt_MailingPostalCode' maxlength="7"  style="text-transform: uppercase"/>
								<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please fill out this field.</div>		
						</div>
						</div>
						</div></div>
  
    <br/>
<!-- SCHOOL INFORMATION ----------------------------------------------------------------------------------------->						
						<div class="card">
							  <div class="card-header"><b>2. SCHOOL INFORMATION </b></div>
							  <div class="card-body">							  
							  <span style='color:red;'><i class="fas fa-exclamation-circle"></i></span> Required field. <span style='color:green;'><i class="fas fa-check"></i></span> Not Required/OK
						 <br/><br/>
									<div class="row container-fluid" style="padding-top:5px;">		
											<div class="col-lg-2 col-12">	
											<b>School Year:</b><br/>
											${krp.schoolYear}
											</div>					
											<div class="col-lg-6 col-12">	
											<b>School:</b>
											<input type='hidden' name='hdn_School' value='${ usr.personnel.school.schoolID }' />
											<sreg:SchoolsDDL cls='required form-control' id='ddl_School' period='${krp}' dummy='true' />
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
											<b>Full Name: (Firstname Lastname)</b>
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
											<input placeholder="Optional" class='one-required form-control phone' errortext='3. CONTACT INFORMATION, (a) PRIMARY CONTACT - Cell Phone' type='text' id='txt_PrimaryContactCellPhone' name='txt_PrimaryContactCellPhone' maxlength="14"  autocomplete="f" />
											</div>
											<div class="col-lg-3 col-12">		
											<b>Email:</b>
											<input required placeholder="Enter Valid Email Address" class='required form-control' errortext='3. CONTACT INFORMATION, (a) PRIMARY CONTACT - Email Address' type='text' id='txt_PrimaryContactEmail' name='txt_PrimaryContactEmail'  autocomplete="f" />
											<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please provide valid email address.</div>
											</div>
									</div>
									<br/>
									
							
							<b>(b) OPTIONAL CONTACT</b>
										<div class="row container-fluid" style="padding-top:5px;">			      							
											<div class="col-lg-8 col-12">								
											<b>Full Name: (Firstname Lastname)</b>
											<input placeholder="Optional" class='optionally-required form-control' type='text' id='txt_SecondaryContactName' name='txt_SecondaryContactName'  autocomplete="f"/>
											</div>
											<div class="col-lg-4 col-12">			
											<b>Relationship to Student:</b>
											<sreg:ContactRelationshipDDL id='ddl_SecondaryContactRelationship' cls='form-control'/>
											</div>
										</div>
										<div class="row container-fluid" style="padding-top:5px;">	
											<div class="col-lg-3 col-12">			
											<b>Primary Phone (xxx) xxx-xxxx:</b>
											<input placeholder="Optional" class='optionally-one-required form-control phone' type='text' id='txt_SecondaryContactHomePhone' name='txt_SecondaryContactHomePhone' maxlength="14"  autocomplete="f"/>
											</div>
											<div class="col-lg-3 col-12">
											<b>Work Phone (xxx) xxx-xxxx:</b>
											<input placeholder="Optional" class='optionally-one-required form-control phone' type='text' id='txt_SecondaryContactWorkPhone' name='txt_SecondaryContactWorkPhone' maxlength="14"  autocomplete="f"/>
											</div>
											<div class="col-lg-3 col-12">			
											<b>Cell Phone (xxx) xxx-xxxx:</b>
											<input placeholder="Optional" class='optionally-one-required form-control phone' type='text' id='txt_SecondaryContactCellPhone' name='txt_SecondaryContactCellPhone' maxlength="14"  autocomplete="f"/>
											</div>
											<div class="col-lg-3 col-12">			
											<b>Email:</b>
											<input placeholder="Optional" class='optionally-required form-control' type='text' id='txt_SecondaryContactEmail' name='txt_SecondaryContactEmail'  autocomplete="f" />
											</div>
									</div>
							
							<br/>
							<b>(c) EMERGENCY  CONTACT</b><br/>
							<span style="color:Red;">&nbsp;&nbsp;All parents/guardians must provide an alternative contact in case of emergency.</span>
										<div class="row container-fluid" style="padding-top:5px;">			      							
											<div class="col-lg-8 col-12">								
											<b>Full Name (Firstname Lastname):</b>
											<input placeholder="Enter name (Firstname Lastname)" required class='required form-control' errortext='3. CONTACT INFORMATION, (c) EMERGENCY CONTACT - Full Name' type='text' id='txt_EmergencyContactName' name='txt_EmergencyContactName'  autocomplete="f" />
											<div class="invalid-feedback"><i class="fas fa-arrow-up"></i> Please enter name.</div>
											</div>
											<div class="col-lg-4 col-12">
											<b>Telephone (xxx) xxx-xxxx:</b>
											<input placeholder="Enter Number" required class='required form-control phone' errortext='3. CONTACT INFORMATION, (c) EMERGENCY CONTACT - Telephone' type='text' id='txt_EmergencyContactPhone' name='txt_EmergencyContactPhone'  maxlength="14" autocomplete="f" />
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
											<sreg:YesNoRBG id='rbg_CustodyIssues'/>											
											</div>
										</div>	
										<div class="row container-fluid" style="padding-top:5px;border-top:1px solid #e5e5e5;">			      							
											<div class="col-8">														
											(b) Does your child have any health or other concerns of which we should be aware?
											</div>
											<div class="col-4">
											<sreg:YesNoRBG id='rbg_HealthOtherConcerns'/>
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
				
					<br />
				<div style='text-align: center;' class="no-print">
					<input id='btn_SubmitRegistration' type='button' value='Add Registrant' class='btn btn-primary btn-sm' /> <a onclick="loadingData();" class='btn btn-sm btn-danger' href="index.html">Cancel</a>
				</div>
				<br />
			</form>
		</div>
		
			
	</body>
	
</html>