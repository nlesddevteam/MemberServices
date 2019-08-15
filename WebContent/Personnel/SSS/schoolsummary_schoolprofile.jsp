<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"
				 import="java.util.*"%>
				 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="taglib/sss.tld" prefix="sss" %>

<esd:SecurityCheck permissions="PERSONNEL-RECOGNITION-ADMIN-VIEW" />

<c:set var='now' value='<%=Calendar.getInstance().getTime()%>' />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>Student Support Services - School Summary</title>
	<link rel="stylesheet" type="text/css" href="<c:url value='/Personnel/SSS/includes/css/sss.css' />" />
	<script type="text/javascript" src="<c:url value='/Personnel/SSS/includes/js/jquery.min.js' />" ></script>

	<script type="text/javascript">
		
	$('document').ready(function(){
		
			$('#school_id').change(function(){
				$('#schoolDeptId').text('Loading...');
				$.post("<c:url value='/Personnel/SSS/ajax/loadSchoolInfo.html' />", { id : $(this).val(), ajax : true }, function(data){
					$('#schoolDeptId').text($(data).find('DEPT-ID').text());
				}, "xml");
			});
			
			$('#btn_continue').click(function(){
				$('#pnl_continue')
					.empty()
					.html('<img src="/MemberServices/Personnel/SSS/includes/images/ajax-loader-2.gif" border="0" />');
				
				$('#SSForm').submit();
			});
			
			
			if(${usr.personnel.school ne null}) {
				$.post("<c:url value='/Personnel/SSS/ajax/loadSchoolInfo.html' />", { id : $('#school_id').val(), ajax: true }, function(data){
					$('#schoolDeptId').text($(data).find('DEPT-ID').text());
				}, "xml");
			}
			
		});
	</script>
</head>
<body>

	<table width="760" cellspacing="2" cellpadding="2" align="center" class="tableSSMain">
		<tr>
			<td>
				<form action="<c:url value='/Personnel/SSS/addProfileSchoolInfo.html' />" method="post" name="SSForm" id="SSForm">
					<c:if test="${schoolprofile ne null }">
						<input type='hidden' id="profile_id" value="${schoolprofile.profileId}" />
					</c:if>
					<table width="750" border="0" cellspacing="2" cellpadding="2" class="tableA" id="SS">
						
						<tr>
							<td>
								<img src="<c:url value='/Personnel/SSS/includes/images/esdnl_logo.jpg' />" alt="ESDNL" width="150" height="63" border="0" />
							</td>
							<td colspan="3" class="tableSSTitle">
								Student Support Services Profile<br />School Summary
							</td>
						</tr>

						<tr>
							<td colspan="4">
								<c:if test="${msg ne null}">
									${msg}
								</c:if>
							</td>
						</tr>

						<tr>
							<td colspan="4" class="tableSSheader">
								SCHOOL INFORMATION
							</td>						
						</tr>

						<tr>
							<td colspan="4">
								Please enter the School Name and ID # for whom you will be entering student data.
							</td>
						</tr>

						<tr valign="top">
							<td>
								<b>SCHOOL NAME</b><br />
								<sss:Schools id="school_id" cls="requiredInputBox"  value="${schoolprofile ne null ? schoolprofile.school : usr.personnel.school}"/>
							</td>
							<td colspan="3">
								<b>SCHOOL ID#</b><br />
								<span id='schoolDeptId'></span>
							</td>
						</tr>
						
						<tr>
							<td>
								<b>LENGTH OF SCHOOL PERIOD (minutes)</b><br />
								<input type="text" name="periodMinutes" id="periodMinutes" size="4" maxlength="2" class="requiredInputBox" 
									value='${schoolprofile ne null ? schoolprofile.periodLength : ""}' />
							</td>
							<td>
								<b># PERIODS PER DAY</b><br />
								<input type="text" name="periodsPerDay" id="periodsPerDay" size="4" maxlength="2" class="requiredInputBox" 
									value='${schoolprofile ne null ? schoolprofile.periodsPerDay : ""}' />
							</td>
							<td colspan="2">
								<b># DAYS IN SCHOOL CYCLE</b><br />
								<input type="text" name="daysInCycle" id="daysInCycle" size="4" maxlength="2" class="requiredInputBox"  
									value='${schoolprofile ne null ? schoolprofile.daysInCycle : ""}' />
							</td>
						</tr>
						
						<tr>
							<td>
								<b>DATA BEING ENTERED ON:</b><br />
								<input type="text" name="enteredOn" id="enteredOn" size="35" readonly="readonly" class="requiredInputBox" value="${now}" />
							</td>
							<td>
								<b>FOR PROJECTED YEAR:</b><br />
								<sss:SchoolYearListbox id="schoolYear" cls="requiredInputBox" value="${schoolprofile ne null ? schoolprofile.projectedSchoolYear : sss:nextSchoolYear()}"/>
								<!-- 
								<input type="text" name="schoolYear" id="schoolYear" size="20" readonly="readonly" class="requiredInputBox" value="${sss:nextSchoolYear()}"  />
								 -->
							</td>
							<td colspan="2">
								<b>DATA BEING ENTERED BY:</b><br />
								<input type='hidden' name="enteredById" id="enteredById" value="${usr.personnel.personnelID}" />
								<input type="text" name="enteredBy" id="enteredBy" size="35" readonly="readonly" class="requiredInputBox" value="${usr.personnel.fullName}" />
							</td>
						</tr>

						<tr>
							<td colspan="4"><div align="center">On submission we
									will validate your form and any fields that are empty will be
									highlighted in red. if all fields are validated, you will be
									taken to a preview page to review your data entry and make any
									changes if necessary and/or print off a copy for your own
									records.</div>
							</td>
						</tr>

						<tr>
							<td colspan="4">
								<hr />
							</td>
						</tr>

						<tr>
							<td colspan="4">
								<div align="center">
									<b>PERVASIVE NEEDS STUDENT PROGRAMMING SCHEDULES</b>
									<p>Please note that you are now required to submit the
										programming schedule of each student approved for pervasvive
										needs support. In addition, you are required to submit
										schedules for any new students whom your Service Delivery Team
										are submitting to request pervasive level of support to
										District personnel when your school's profile is submitted.
									</p>
									
									<b>STUDENT SCHEDULES CAN BE SUBMITTED VIA BOARD MAIL.</b>
								</div>
							</td>
						</tr>
						
						<tr>
							<td colspan="4">&nbsp;</td>
						</tr>

						<tr>
							<td colspan="4">
								<div align="center" id='pnl_continue'>
									<input id='btn_continue' style='color:red;font-weight:bold;' type="button" value="Continue" />
								</div>
							</td>
						</tr>
						
					</table>
				</form>
			</td>
		</tr>
	</table>
	
	<div align="center" class="copyright">&copy; 2012 Eastern School District &middot; All Rights Reserved</div>

</body>
</html>
