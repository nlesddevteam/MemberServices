<%@ page language="java"
         import="com.esdnl.school.registration.kindergarten.bean.*" 
         isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
	System.out.println(">>> KINDERGARTEN REGISTRATION COMPLETED BY " + request.getRemoteAddr() + " <<<");
%>

<html>
  
  <head>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">	
    <TITLE>Student Registration</title>
    <script type='text/javascript'>
    	jQuery(function(){
    		$('fieldset table').each(function() {
	    			$(this).children().children('tr:odd').css({'background-color': "#ffffff"});
	    			$(this).children().children('tr:even').css({'background-color': "#f0f0f0"});
	    			$(this).children().children('tr:not(:first)').children('td').css({'border-top': 'solid 1px #333333'});
    		});
    	});
    </script>
  </head>

  <body>
		<c:choose>
			<c:when test="${kr ne null}">
				<div align='center'>
					<div style='padding-bottom:8px; width:100%; text-align:left;'>
						<p>
							<span style="color:Green;font-size:14px;font-weight:bold;">Your application for Kindergarten registration for the ${kr.registration.schoolYear} school year has been received at <b><fmt:formatDate type="both" dateStyle="long" value="${kr.registrationDate}" /></b>. 
							A confirmation email has also been sent to ${kr.primaryContactEmail}.</span>
						</p>
						<p> 
							Please present proof of address to the school on or before <fmt:formatDate type="date" dateStyle="long" value="${kr.registration.addressConfirmationDeadline}" />
							 to complete the registration process. This does not guarantee acceptance to the school or program, the District reserves the right to make 
							 the final decision regarding student acceptance and placement. 
						</p>
						<p>
							Below is a copy of the application you submitted for your reference. Acceptance letters will follow when acceptance has been approved.<br /><br />
							Thank You,<br />
							Newfoundland and Labrador English School District
						</p>
						<p>
							<b>To register another child please click <a style='text-decoration:none;color:red;' href="<c:url value='/schools/registration/kindergarten/index.html?rel=${kr.registrantId}'/>">HERE</a>.</b>
						</p>
					</div>
				</div>
				
				<div id='add-registrant-form' style='width:100%; display:inline;'>
					<input type='hidden' name='registration_id' value='${ap.registrationId}' />
					<div align='center'>
						<fieldset>
							<legend>School Information</legend>
							<table align='center' cellspacing='0' cellpadding='8' width='100%'>
								<tr>
									<td class='label'>School Year:</td>
									<td style='font-weight:bold;' align='left'>${kr.registration.schoolYear}</td>
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
							<table align='center' cellspacing='0' cellpadding='8' width='100%'>
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
							<table align='center' cellspacing='0' cellpadding='8' width='100%'>
								<caption>Physical &amp; Mailing Addresses</caption>
								<tr>
									<td class='label' style='text-align:left;'>Physical Address</td>
									<td class='label' style='text-align:left;'>Mailing Address</td>
								</tr>
								<tr>
									<td align='left'>
										${kr.physicalStreetAddress1} <br />
										<c:if test='${kr.physicalStreetAddress2 ne ""}'>
											${kr.physicalStreetAddress2} <br />
										</c:if>
										${kr.physicalCityTown}, NL<br />
										${kr.physicalPostalCode}
									</td>
									<td align='left'>
										${kr.mailingStreetAddress1} <br />
										<c:if test='${kr.mailingStreetAddress2 ne ""}'>
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
							<table align='center' cellspacing='0' cellpadding='8' width='100%'>
								<caption>Primary Contact</caption>
								<tr>
									<td class='label required'>Full Name:</td>
									<td align='left'>${kr.primaryContactName}</td>
								</tr>
								<tr>
									<td class='label required'>Relationship to Student:</td>
									<td align='left'>${kr.primaryContactRelationship.text}</td>
								</tr>
								<c:if test='${kr.primaryContactHomePhone ne ""}'>
									<tr>
										<td class='label one-required'>Home Phone:</td>
										<td align='left'>
											${kr.primaryContactHomePhone}
										</td>
									</tr>
								</c:if>
								<c:if test='${kr.primaryContactWorkPhone ne ""}'>
									<tr>
										<td class='label one-required'>Work Phone:</td>
										<td align='left'>
											${kr.primaryContactWorkPhone}
										</td>
									</tr>
								</c:if>
								<c:if test='${kr.primaryContactCellPhone ne ""}'>
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
							<c:if test='${kr.secondaryContactName ne ""}'>
								<table align='center' cellspacing='0' cellpadding='8' width='100%'>
									<caption>Optional Contact</caption>
									<tr>
										<td class='label optionally-required'>Full Name:</td>
										<td align='left'>${kr.secondaryContactName}</td>
									</tr>
									<tr>
										<td class='label optionally-required'>Relationship to Student:</td>
										<td align='left'>${kr.secondaryContactRelationship.text}</td>
									</tr>
									<c:if test='${kr.secondaryContactHomePhone ne ""}'>
										<tr>
											<td class='label optionally-one-required'>Home Phone:</td>
											<td align='left'>
												${kr.secondaryContactHomePhone}
											</td>
										</tr>
									</c:if>
									<c:if test='${kr.secondaryContactWorkPhone ne ""}'>
										<tr>
											<td class='label optionally-one-required'>Work Phone:</td>
											<td align='left'>
												${kr.secondaryContactWorkPhone}
											</td>
										</tr>
									</c:if>
									<c:if test='${kr.secondaryContactCellPhone ne ""}'>
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
							<table align='center' cellspacing='0' cellpadding='8' width='100%'>
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
							<table align='center' cellspacing='0' cellpadding='8' width='100%'>
								<tr>
									<td class='label required' style='width:110px;' valign='top'>${kr.custodyIssues ? "Yes" : "No"}</td>
									<td align='left'>
										Are there any custody issues of which the school should be aware?<br /><br />
										<i>Court documentation is required if either parent is to be denied from receiving academic information and/or access to child.</i>
									</td>
								</tr>
								<tr>
									<td class='label required' style='width:110px;' valign='top'>${kr.healthConcerns ? "Yes" : "No"}</td>
									<td align='left'>Does your child have any health or other concerns of which we should be aware?</td>
								</tr>
								<tr>
									<td class='label required' style='width:110px;' valign='top'>${kr.accessibleFacility ? "Yes" : "No"}</td>
									<td align='left'>Does your child require an accessible facility?</td>
								</tr>
								<tr>
									<td class='label required' style='width:110px;' valign='top'>${kr.efiSibling ? "Yes" : "No"}</td>
									<td align='left'>Do you have a child currently enrolled in the Early French Immersion Program in this school?</td>
								</tr>
							</table>
						</fieldset>
					</div>
					<br />
					<br />
				</div>
			</c:when>
			<c:otherwise>
				<div style='width=:50%;' align='center'>
					<br /><br />
					<fieldset style='text-align:left;'>
							<legend>Registration Form</legend>
							We are current not accepting Kindergarten registrations at this time. Please try again later, thank you.
					</fieldset>
					<br /><br />
					<a class='swap' href='http://www.nlesd.ca'><img border='0' src="<c:url value='/schools/registration/kindergarten/includes/images/home-off.png'/>" /></a>
				</div>
			</c:otherwise>
		</c:choose>
		
	</body>
	
</html>