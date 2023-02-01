<%@ page language="java" isThreadSafe="false"%>

<%@ taglib prefix="sreg" uri="/WEB-INF/school_registration.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<html>
  
  <head>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
	<meta name="viewport" content="width=device-width, initial-scale=1.0">	
    <TITLE>ICF Student Registration</title>
 
  </head>

  <body>
		<c:choose>
			<c:when test="${reg ne null}">			
				<div class="alert alert-success no-print" style="text-align:center;font-size:14px;">
				<b>SUCCESS:</b> Your application for ICF Registration for the ${regper.icfRegPerSchoolYear} school year has been received at 
				<b><fmt:formatDate type="both" dateStyle="long" value="${reg.icfAppDateSubmitted}" /></b>. <br/>
				A confirmation email has also been sent to ${reg.icfAppEmail}.
				</div>				
				<br/>Below is a copy of the application you submitted for your reference. Acceptance letters will follow when acceptance has been approved.		
																
					 <div align="center" class="no-print">
					 	<hr>
					 <b>Please print this page for your records. You can also register another child or exit.</b><br/><br/>
					 <a class="btn btn-sm btn-primary" href="/MemberServices/schools/registration/icfreg/registration/viewStudentRegistration.html"><i class="fas fa-edit"></i> Register Another Child</a> &nbsp; 	
					 <a href='#' class="no-print noJump btn btn-sm btn-warning" title='Print this page (pre-formatted)' onclick="jQuery('#printJob').print({prepend : '<div align=center><img width=400 src=includes/img/nlesd-colorlogo.png><br/><br/><b>ICF REGISTRATION</b></div><br/><br/>'});"><i class="fas fa-print"></i> Print this Page</a> &nbsp; 
                     <a href="/families/icfregistration.jsp" class="no-print btn btn-sm btn-danger"><i class="fas fa-sign-out-alt"></i> Exit Registration</a>
                      </div>    
			
						<br/><br/>		
					<div id='add-registrant-form' style='width:100%; display:inline;'>
					<input type='hidden' name='registration_id' value='${regper.icfRegPerId}' />
					

<!-- STUDENT INFORMATION -->						
					<div class="card">
							  <div class="card-header"><b>Registration Details</b></div>
							  <div class="card-body">	
								<div class="row" style="padding-top:5px;">
		      							<div class="col-lg-6 printSet"><b>Student Full Name:</b><br/><div class="dField">${reg.icfAppFullName}</div></div>
										<div class="col-lg-3 printSet"><b>Parent/Guardian Full Name:</b><br/><div class="dField">${reg.icfAppGuaFullName}</div></div>
										<div class="col-lg-3 printSet"><b>Parent/Guardian Email Address:</b><br/><div class="dField">${reg.icfAppEmail}</div></div>
										<div class="col-lg-3 printSet"><b>Telephone:</b><br/><div class="dField">${reg.icfAppContact1}</div></div>
										<div class="col-lg-3 printSet"><b>Optional Telephone:</b><br/><div class="dField">${reg.icfAppContact2 eq null? 'N/A': reg.icfAppContact2}</div></div>
										<div class="col-lg-3 printSet"><b>Registered School:</b><br/><div class="dField">${reg.icfAppSchoolName}</div></div>
										
								</div>
								
																
							</div>
					</div>						
				
							
				<br/>
				



				</div>
			</c:when>
			<c:otherwise>
			<div align="center">
			<div class="alert alert-danger">Sorry, we are current <b>not</b> accepting ICF Registrations at this time. Please try again later, thank you.</div>
					<br/><br/>
					<a class='btn btn-danger btn-sm' href='/families/icfregistration.jsp'><i class="fas fa-sign-out-alt"></i> Exit Registration</a>
			</div>
			</c:otherwise>
		</c:choose>
<br/><br/>		
<script>
$("#loadingSpinner").css("display","none");
</script>
	</body>
	
</html>