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
 
  </head>

  <body>
		<c:choose>
			<c:when test="${kr ne null}">			
				<div class="alert alert-success no-print" style="text-align:center;">
				<b>SUCCESS:</b> Your application for Kindergarten registration for the ${kr.registration.schoolYear} school year has been received at 
				<b><fmt:formatDate type="both" dateStyle="long" value="${kr.registrationDate}" /></b>. <br/>
				A confirmation email has also been sent to ${kr.primaryContactEmail}.
				</div>				
				<br/>Below is a copy of the application you submitted for your reference. Acceptance letters will follow when acceptance has been approved.		
																
					 <div align="center" class="no-print">
					 	<hr>
					 <b>Please print this page for your records. You can also register another child or exit.</b><br/><br/>
					 <a class="btn btn-sm btn-primary" href="/schools/registration/kindergarten/index.html?rel=${kr.registrantId}"><i class="fas fa-edit"></i> Register Another Child</a> &nbsp; 	
					 <a href='#' class="no-print noJump btn btn-sm btn-warning" title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>KINDERSTART/KINDERGARTEN REGISTRATION</b></div><br/><br/>'});"><i class="fas fa-print"></i> Print this Page</a> &nbsp; 
                     <a href="/index.jsp" class="no-print btn btn-sm btn-danger"><i class="fas fa-sign-out-alt"></i> Exit Registration</a>
                      </div>    
			
						<br/><br/>		
					<div id='add-registrant-form' style='width:100%; display:inline;'>
					<input type='hidden' name='registration_id' value='${ap.registrationId}' />
					
<!-- SCHOOL INFORMATION -->						
					<div class="card">
							  <div class="card-header"><b>SCHOOL INFORMATION</b></div>
							  <div class="card-body">	
									<div class="row container-fluid" style="padding-top:5px;">
		      							<div class="col-lg-4 printSet"><b>School Year:</b><br/><div class="dField">${kr.registration.schoolYear ne null?kr.registration.schoolYear:"N/A"} </div></div>
										<div class="col-lg-4 printSet"><b>School:</b><br/><div class="dField">${kr.school.schoolName ne null?kr.school.schoolName:"N/A"}</div></div>
										<div class="col-lg-4 printSet"><b>Stream:</b><br/><div class="dField">${kr.schoolStream.text ne null?kr.schoolStream.text:"N/A"}</div></div>
									</div>
							</div>
					</div>		
					<br/>	
<!-- STUDENT INFORMATION -->						
					<div class="card">
							  <div class="card-header"><b>STUDENT INFORMATION</b></div>
							  <div class="card-body">	
								<div class="row container-fluid" style="padding-top:5px;">
		      							<div class="col-lg-6 printSet"><b>Student Name:</b><br/><div class="dField">${kr.studentLastName ne null?kr.studentLastName:"N/A"}, ${kr.studentFirstName ne null?kr.studentFirstName:"N/A"}</div></div>
										<div class="col-lg-3 printSet"><b>Gender:</b><br/><div class="dField">${kr.studentGender.text ne null?kr.studentGender.text:"N/A"}</div></div>
										<div class="col-lg-3 printSet"><b>Date of Birth:</b><br/><div class="dField"><fmt:formatDate type="date" dateStyle="long" value="${kr.dateOfBirth}" /></div></div>
								</div>
								<div class="row container-fluid" style="padding-top:5px;">
		      							<div class="col-lg-4 printSet"><b>MCP Number:</b><br/><div class="dField">${kr.mcpNumber ne null?kr.mcpNumber:"N/A"}</div></div>
										<div class="col-lg-4 printSet"><b>MCP Expiration: (mm/yyyy)</b><br/><div class="dField">${kr.mcpExpiry ne null?kr.mcpExpiry:"N/A"}</div></div>										
								</div>	
								<div class="row container-fluid" style="padding-top:15px;">
		      							<div class="col-lg-6 printSet"><b>PHYSICAL ADDRESS:</b><br/>
				      							<div class="dField">${kr.physicalStreetAddress1 ne null?kr.physicalStreetAddress1:"N/A" } <br />
												<c:if test='${kr.physicalStreetAddress2 ne null}'>
													${kr.physicalStreetAddress2} <br />
												</c:if>
												${kr.physicalCityTown ne null?kr.physicalCityTown:"N/A"}, NL &middot; ${kr.physicalPostalCode ne null?kr.physicalPostalCode:"N/A"}
		      							</div></div>
										<div class="col-lg-6 printSet"><b>MAILING ADDRESS:</b><br/>
												<div class="dField">${kr.mailingStreetAddress1 ne null?kr.mailingStreetAddress1:"N/A"} <br />
												<c:if test='${kr.mailingStreetAddress2 ne null}'>
													${kr.mailingStreetAddress2} <br />
												</c:if>
												${kr.mailingCityTown ne null?kr.mailingCityTown:"N/A"}, NL &middot;	${kr.mailingPostalCode ne null?kr.mailingPostalCode:"N/A"}
										</div></div>
							</div>									
							</div>
					</div>						
				
							
				<br/>
					
					
					<!-- STUDENT INFORMATION -->						
					<div class="card">
							  <div class="card-header"><b>3. CONTACT INFORMATION</b></div>
							  <div class="card-body">	
								<div class="row container-fluid" style="padding-top:5px;">
		      							<div class="col-lg-4 printSet" style="padding:5px;"><b>(a) PRIMARY CONTACT</b><br/>
		      								<b>Name:</b><br/>
		      								<div class="dField">${kr.primaryContactName ne null?kr.primaryContactName:"N/A"}</div>
		      								<b>Relationship to Student:</b><br/>
		      								<div class="dField">${kr.primaryContactRelationship.text ne null?kr.primaryContactRelationship.text:"N/A"}</div>
		      								<b>Home Phone:</b><br/>
		      								<div class="dField">${kr.primaryContactHomePhone ne null?kr.primaryContactHomePhone:"N/A"}</div>
		      								<b>Work Phone:</b><br/>
		      								<div class="dField">${kr.primaryContactWorkPhone ne null?kr.primaryContactWorkPhone:"N/A"}</div>
		      								<b>Cell Phone:</b><br/>
		      								<div class="dField">${kr.primaryContactCellPhone ne null?kr.primaryContactCellPhone:"N/A"}</div>
		      								<b>Email:</b><br/>
		      								<div class="dField">${kr.primaryContactEmail ne null?kr.primaryContactEmail:"N/A"}</div>
		      								
										</div>
										<div class="col-lg-4 printSet" style="padding:5px;"><b>(b) OPTIONAL CONTACT</b><br/>
		      								<b>Name:</b><br/>
		      								<div class="dField">${!(kr.secondaryContactName.val())?"N/A":kr.secondaryContactName}</div>
		      								<b>Relationship to Student:</b><br/>
		      								<div class="dField">${kr.secondaryContactRelationship.text ne null?kr.secondaryContactRelationship.text:"N/A"}</div>
		      								<b>Home Phone:</b><br/>
		      								<div class="dField">${!(kr.secondaryContactHomePhone.val())?"N/A":kr.secondaryContactHomePhone}</div>
		      								<b>Work Phone:</b><br/>
		      								<div class="dField">${kr.secondaryContactWorkPhone ne null?kr.secondaryContactWorkPhone:"N/A"}</div>
		      								<b>Cell Phone:</b><br/>
		      								<div class="dField">${kr.secondaryContactCellPhone ne null?kr.secondaryContactCellPhone:"N/A"}</div>
		      								<b>Email:</b><br/>
		      								<div class="dField">${kr.secondaryContactEmail ne null?kr.secondaryContactEmail:"N/A"}</div>			
										</div>			
										<div class="col-lg-4 printSet" style="padding:5px;"><b>(c) EMERGENCY CONTACT</b><br/>		      								
		      								<b>Name:</b><br/>
		      								<div class="dField">${kr.emergencyContactName ne null?kr.emergencyContactName:"N/A"}</div>		      								   								
		      								<b>Telephone:</b><br/>
		      								<div class="dField">${kr.emergencyContactTelephone ne null?kr.emergencyContactTelephone:"N/A"}</div>		      											
										</div>								
									</div>									
							</div>
					</div>	
					<br/>
					<div class="pageBreak"></div>
<!-- OTHER INFORMATION -->						
					<div class="card">
							  <div class="card-header"><b>4. OTHER INFORMATION</b></div>
							  <div class="card-body">	
								<div class="row container-fluid" style="padding-top:5px;border-top:1px solid #e5e5e5;">	
		      							<div class="col-8">(a) Are there any custody issues of which the school should be aware?<br />
										<i>Court documentation is required if either parent is to be denied from receiving academic information and/or access to child.</i></div>
										<div class="col-4">${kr.custodyIssues ? "Yes" : "No"}</div>
								</div>
								<div class="row container-fluid" style="padding-top:5px;border-top:1px solid #e5e5e5;">	
		      							<div class="col-8">(b) Does your child have any health or other concerns of which we should be aware?</div>
										<div class="col-4">${kr.healthConcerns ? "Yes" : "No"}</div>
								</div>
								<div class="row container-fluid" style="padding-top:5px;border-top:1px solid #e5e5e5;">	
		      							<div class="col-8">(c) Does your child require an accessible facility?</div>
										<div class="col-4">${kr.accessibleFacility ? "Yes" : "No"}</div>
								</div>
								<div class="row container-fluid" style="padding-top:5px;border-top:1px solid #e5e5e5;">	
		      							<div class="col-8">(d) Do you have a child currently enrolled in the Early French Immersion Program in this school?</div>
										<div class="col-4">${kr.efiSibling ? "Yes" : "No"}</div>
								</div>
							</div>
					</div>					

<hr>
<br/>

							<b>NOTE:</b> Please present proof of address to the school on or before <fmt:formatDate type="date" dateStyle="long" value="${kr.registration.addressConfirmationDeadline}" />
							 to complete the registration process. This does not guarantee acceptance to the school or program, the District reserves the right to make 
							 the final decision regarding student acceptance and placement. 
							<br/><br/>

				</div>
			</c:when>
			<c:otherwise>
			<div align="center">
			<div class="alert alert-danger">Sorry, we are current <b>not</b> accepting Kinderstart/Kindergarten registrations at this time. Please try again later, thank you.</div>
					<br/><br/>
					<a class='btn btn-danger btn-sm' href='/families/kindergartenregistration.jsp'><i class="fas fa-sign-out-alt"></i> Exit Registration</a>
			</div>
			</c:otherwise>
		</c:choose>
<br/><br/>		
<script>
$("#loadingSpinner").css("display","none");
</script>
	</body>
	
</html>