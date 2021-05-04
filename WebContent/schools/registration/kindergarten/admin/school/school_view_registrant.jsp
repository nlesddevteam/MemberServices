<%@ page language="java"
         import="com.esdnl.school.registration.kindergarten.bean.*" 
         isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="KINDERGARTEN-REGISTRATION-SCHOOL-VIEW" /> 

<html>
  
  <head>
   
    <TITLE>Student Registration</title>
   
   <script>
   $("#loadingSpinner").css("display","none");
   </script>
   
  </head>

  <body >
<div align='center' style='font-size:14pt;font-weight:bold;color:#004178;;padding-bottom:15px;'>
  		${kr.registration.schoolYear} ${kr.school.schoolName ne null?kr.school.schoolName:"N/A"} Registration Information<br/>
	  	<span style="text-transform:Uppercase;color:Black;">${kr.studentLastName ne null?kr.studentLastName:"N/A"}, ${kr.studentFirstName ne null?kr.studentFirstName:"N/A"}</span>
  	</div>
 	  
 	<div align='center' class="no-print">
				 <a href='#' class="no-print noJump btn btn-sm btn-warning" title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center><img width=400 src=/MemberServices/schools/registration/kindergarten/includes/img/nlesd-colorlogo.png></div><br/>'});"><i class="fas fa-print"></i> Print this Page</a> &nbsp; 
                   &nbsp; <a onclick="loadingData();" class='btn btn-sm btn-danger no-print' href="${ReturnURL}">Back to Registrant List</a>
			</div>  
 	 
 	 <br/>
  	
  	<!-- SCHOOL INFORMATION -->						
					<div class="card">
							  <div class="card-header"><b>1. SCHOOL INFORMATION</b></div>
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
							  <div class="card-header"><b>2. STUDENT INFORMATION</b></div>
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
		      								<div class="dField">${kr.secondaryContactName ne null?kr.secondaryContactName:"N/A"}</div>
		      								<b>Relationship to Student:</b><br/>
		      								<div class="dField">${kr.secondaryContactRelationship.text ne null?kr.secondaryContactRelationship.text:"N/A"}</div>
		      								<b>Home Phone:</b><br/>
		      								<div class="dField">${kr.secondaryContactHomePhone ne null?kr.secondaryContactHomePhone:"N/A"}</div>
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
  <br/>		
    	  		
  	
  	<!-- REGISTRATION INFORMATION -->						
					<div class="card">
							  <div class="card-header"><b>5. REGISTRATION INFORMATION</b></div>
							  <div class="card-body">	
									<div class="row container-fluid" style="padding-top:5px;">
		      							<div class="col-lg-4 printSet"><b>Registration Date:</b><br/><div class="dField"><fmt:formatDate type="both" dateStyle="long" value="${kr.registrationDate}" /></div></div>
										<div class="col-lg-3 printSet"><b>Address &amp; MCP Approved:</b><br/><div class="dField">${kr.physicalAddressApproved ? "<span style='font-weight:bold;color:green;'><i class='fas fa-check'></i> Yes</span>" : "<span style='font-weight:bold;color:red;'><i class='fas fa-times'></i> No</span>"}</div></div>
										<div class="col-lg-3 printSet"><b>Status:</b><br/><div class="dField">${kr.status.accepted ? "<span style='font-weight:bold;color:green;'><i class='fas fa-check'></i> Accepted</span>" : kr.status.waitlisted ? "<span style='font-weight:bold;color:orange;'><i class='far fa-hourglass'></i> Waitlisted</span>" : "<span style='font-weight:bold;color:#9E7BFF;'><i class='fas fa-cog'></i> Processing</span>"}</div></div>
									  	
									  		<c:if test="${not kr.physicalAddressApproved}">
									  		<div class="col-lg-2 printSet"><b>Options:</b><br/>
											<a class='btn btn-xs btn-success no-print' href="/MemberServices/schools/registration/kindergarten/admin/school/approveRegistrantPhysicalAddress.html?kr=${kr.registrantId}">APPROVE</a>
											</div>
											</c:if>
										
									</div>
							</div>
					</div>		
					
  	
  	
  	
  	<br/><br/>
  	
  	<div class="alert alert-danger"><b>Confidentiality Warning:</b> This document and any attachments are intended for the sole use of the intended recipient(s), and contain privileged 
  	and/or confidential information. If you are not an intended recipient, any review, retransmission, printing, copying, circulation or other use 
  	of this message and any attachments is strictly prohibited.</div>

	  	
	
			<div align='center'>
				 <a href='#' class="no-print noJump btn btn-sm btn-warning" title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center><img width=400 src=/MemberServices/schools/registration/kindergarten/includes/img/nlesd-colorlogo.png></div><br/>'});"><i class="fas fa-print"></i> Print this Page</a> &nbsp; 
                   &nbsp; <a onclick="loadingData();" class='btn btn-sm btn-danger no-print' href="${ReturnURL}">Back to Registrants List</a>
			</div>
		
		<br/>&nbsp;<br/>	

	</body>
	
</html>