<%@ page language="java"
         import="com.esdnl.school.registration.kindergarten.bean.*" 
         isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="KINDERGARTEN-REGISTRATION-ADMIN-VIEW" /> 

<html>
  
  <head>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <TITLE>Student Registration</title>
    <script type='text/javascript'>
    	jQuery(function(){
    		
    		$('#btnResendConfirmation').click(function(){
    			if(confirm('Resend Confirmation Email?')) {
    				
    				$('#btnResendConfirmation').attr('disabled', 'disabled');
    				$.post("/MemberServices/schools/registration/kindergarten/admin/district/ajax/resendRegistrationConfirmationEmail.html", 
    						{	
    							id: ${kr.registrantId},
    							ajax : true 
    						}, 
    						function(data){
    							if($(data).find('RESEND-CONFIRMATION-RESPONSE').length > 0) {
    								$('.divResponseMsg').html("<br/>" + $(data).find('RESEND-CONFIRMATION-RESPONSE').first().attr('msg') + "<br/>");
    							}
    							else{
    								$('#btnResendConfirmation').removeAttr('disabled');
    							}
    						}, 
    						"xml");
    			}
    		});
    		
    		$('.opbutton').button();
    		
    		$('fieldset table').each(function() {
	    			$(this).children().children('tr:odd').css({'background-color': "#ffffff"});
	    			$(this).children().children('tr:even').css({'background-color': "#f0f0f0"});
	    			$(this).children().children('tr:not(:first)').children('td').css({'border-top': 'solid 1px #333333'});
    		});
    	});
    </script>
  </head>

  <body bgcolor="#BF6200">
  	<div align='center' style='font-size:14pt;font-weight:bold;color:#33cc33;padding-bottom:15px;'>
	  	View Registrant Information
  	</div>
		<div class='divResponseMsg' style='font-weight:bold; color:red;' align='center'></div>
		<div id='add-registrant-form' style='width:100%; display:inline;'>
			<div align='center'>
				<fieldset>
					<legend>Registration Information</legend>
					<table align='center' cellspacing='0' cellpadding='8' width='75%'>
						<tr>
							<td class='label'>Registration Date:</td>
							<td style='font-weight:bold;' align='left'><fmt:formatDate type="both" dateStyle="long" value="${kr.registrationDate}" /></td>
						</tr>
						<tr>
							<td class='label required'>Physical Address &amp; MCP Approved:</td>
							<td  align='left'>
								${kr.physicalAddressApproved ? "<span style='font-weight:bold;color:green;'>Yes</span>" : "<span style='font-weight:bold;color:red;'>No</span>"}
								<c:if test="${not kr.physicalAddressApproved}">
									&nbsp;&nbsp;<a class='opbutton small' href="<c:url value='/schools/registration/kindergarten/admin/district/approveRegistrantPhysicalAddress.html?kr=${kr.registrantId}' />">approve</a>
								</c:if>
							</td>
						</tr>
						<tr>
							<td class='label'>Registration Status:</td>
							<td align='left'>
								${kr.status.accepted ? "<span style='font-weight:bold;color:green;'>Accepted</span>" : kr.status.waitlisted ? "<span style='font-weight:bold;color:orange;'>Waitlisted</span>" : "<span style='font-weight:bold;color:#9E7BFF;'>Processing</span>"}
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
			<br />
			<div align='center'>
				<fieldset>
					<legend>School Information</legend>
					<table align='center' cellspacing='0' cellpadding='8' width='75%'>
						<tr>
							<td class='label'>School Year:</td>
							<td align='left' style='font-weight:bold;'>${kr.registration.schoolYear}</td>
						</tr>
						<tr>
							<td class='label required'>School:</td>
							<td align='left'>${kr.school.schoolName}</td>
						</tr>
						<tr>
							<td class='label required'>Stream:</td>
							<td align='left'>${kr.schoolStream.text}</td>
						</tr>
					</table>
				</fieldset>
			</div>
			<br />
			<div align='center'>
				<fieldset>
					<legend>Student Information</legend>
					<table align='center' cellspacing='0' cellpadding='8' width='75%'>
						<tr>
							<td class='label required'>Full Name:</td>
							<td align='left'>${kr.studentLastName}, ${kr.studentFirstName}</td>
						</tr>
						<tr>
							<td class='label required'>Gender:</td>
							<td align='left'>${kr.studentGender.text}</td>
						</tr>
						<tr>
							<td class='label required' valign='top'>Date of Birth:</td>
							<td align='left'>
								<fmt:formatDate type="date" dateStyle="long" value="${kr.dateOfBirth}" />
							</td>
						</tr>
						<tr>
							<td class='label required'>MCP Number:</td>
							<td align='left'>${kr.mcpNumber}</td>
						</tr>
						<tr>
							<td class='label required' valign='top'>MCP Expiration:</td>
							<td align='left'>
								${kr.mcpExpiry}
							</td>
						</tr>
					</table>
					<br />
					<table align='center' cellspacing='0' cellpadding='8' width='75%'>
						<caption>Physical &amp; Mailing Addresses</caption>
						<tr>
							<td class='label' style='text-align:left;'>Physical Address</td>
							<td class='label' style='text-align:left;'>Mailing Address</td>
						</tr>
						<tr>
							<td align="left">
								${kr.physicalStreetAddress1} <br />
								<c:if test="${kr.physicalStreetAddress2 ne null}">
									${kr.physicalStreetAddress2} <br />
								</c:if>
								${kr.physicalCityTown}, NL<br />
								${kr.physicalPostalCode}
							</td>
							<td align="left">
								${kr.mailingStreetAddress1} <br />
								<c:if test="${kr.mailingStreetAddress2 ne null}">
									${kr.mailingStreetAddress2} <br />
								</c:if>
								${kr.mailingCityTown}, NL<br />
								${kr.mailingPostalCode}
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
			<br />
			<div align='center'>
				<fieldset>
					<legend>Contact Information</legend>								
					<table align='center' cellspacing='0' cellpadding='8' width='75%'>
						<caption>Primary Contact</caption>
						<tr>
							<td class='label required'>Full Name:</td>
							<td align='left'>${kr.primaryContactName}</td>
						</tr>
						<tr>
							<td class='label required'>Relationship to Student:</td>
							<td align='left'>${kr.primaryContactRelationship.text}</td>
						</tr>
						<c:if test="${kr.primaryContactHomePhone ne null}">
							<tr>
								<td class='label one-required'>Home Phone:</td>
								<td align='left'>
									${kr.primaryContactHomePhone}
								</td>
							</tr>
						</c:if>
						<c:if test="${kr.primaryContactWorkPhone ne null}">
							<tr>
								<td class='label one-required'>Work Phone:</td>
								<td align='left'>
									${kr.primaryContactWorkPhone}
								</td>
							</tr>
						</c:if>
						<c:if test="${kr.primaryContactCellPhone ne null}">
							<tr>
								<td class='label one-required'>Cell Phone:</td>
								<td align='left'>
									${kr.primaryContactCellPhone}
								</td>
							</tr>
						</c:if>
						<tr>
							<td class='label required'>Email:</td>
							<td align='left'>${kr.primaryContactEmail}</td>
						</tr>
					</table><br/>
					<c:if test='${kr.secondaryContactName ne null}'>
						<table align='center' cellspacing='0' cellpadding='8' width='75%'>
							<caption>Optional Contact</caption>
							<tr>
								<td class='label optionally-required'>Full Name:</td>
								<td align='left'>${kr.secondaryContactName}</td>
							</tr>
							<tr>
								<td class='label optionally-required'>Relationship to Student:</td>
								<td align='left'>${kr.secondaryContactRelationship.text}</td>
							</tr>
							<c:if test="${kr.secondaryContactHomePhone ne null}">
								<tr>
									<td class='label optionally-one-required'>Home Phone:</td>
									<td align='left'>
										${kr.secondaryContactHomePhone}
									</td>
								</tr>
							</c:if>
							<c:if test="${kr.secondaryContactWorkPhone ne null}">
								<tr>
									<td class='label optionally-one-required'>Work Phone:</td>
									<td align='left'>
										${kr.secondaryContactWorkPhone}
									</td>
								</tr>
							</c:if>
							<c:if test="${kr.secondaryContactCellPhone ne null}">
								<tr>
									<td class='label optionally-one-required'>Cell Phone:</td>
									<td align='left'>
										${kr.secondaryContactCellPhone}
									</td>
								</tr>
							</c:if>
							<tr>
								<td class='label optionally-required'>Email:</td>
								<td align='left'>${kr.secondaryContactEmail}</td>
							</tr>
						</table>
					</c:if>
					<div align='center'>
						<div style='padding-top:8px; padding-bottom:5px; font-style:italic; width:60%; text-align:left;'>
							All parents/guardians must provide an alternative contact in case of emergency.
						</div>
					</div>
					<table align='center' cellspacing='0' cellpadding='8' width='75%'>
						<caption>Emergency Contact</caption>
						<tr>
							<td class='label required'>Full Name:</td>
							<td align='left'>${kr.emergencyContactName}</td>
						</tr>
						<tr>
							<td class='label required'>Telephone:</td>
							<td align='left'>
								${kr.emergencyContactTelephone}
							</td>
						</tr>
					</table>
				</fieldset>
			</div><br />
			<div align='center'>
				<fieldset>
					<legend>Other Information</legend>
					<table align='center' cellspacing='0' cellpadding='8' width='75%'>
						<tr>
							<td class='label required' style='width:110px;' valign='top'>${kr.custodyIssues ? "Yes" : "No"}</td>
							<td align="left">
								Are there any custody issues of which the school should be aware?<br /><br />
								<i>Court documentation is required if either parent is to be denied from receiving academic information and/or access to child.</i>
							</td>
						</tr>
						<tr>
							<td class='label required' style='width:110px;' valign='top'>${kr.healthConcerns ? "Yes" : "No"}</td>
							<td align="left">Does your child have any health or other concerns of which we should be aware?</td>
						</tr>
						<tr>
							<td class='label required' style='width:110px;' valign='top'>${kr.accessibleFacility ? "Yes" : "No"}</td>
							<td align="left">Does your child require an accessible facility?</td>
						</tr>
						<tr>
							<td class='label required' style='width:110px;' valign='top'>${kr.efiSibling ? "Yes" : "No"}</td>
							<td align="left">Do you have a child currently enrolled in the Early French Immersion Program in this school?</td>
						</tr>
					</table>
				</fieldset>
			</div>
			<br />
			<div align='center'>
				<a class='opbutton' href="index.html">Registration Home</a> <a class='opbutton' href="${ReturnURL}">Back to Registrants List</a> <input id='btnResendConfirmation' type='button' class='opbutton'  value='Resend Confirmation Email' />
			</div>
			<div class='divResponseMsg' style='font-weight:bold; color:red;' align='center'></div>
			<br />
		</div>

	</body>
	
</html>