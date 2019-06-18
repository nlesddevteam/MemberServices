<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*;"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="taglib/sss.tld" prefix="sss"%>

<html>
<head>
<title>Student Support Services - School Summary</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/Personnel/SSS/includes/css/sss.css' />" />
<link rel="stylesheet" type="text/css"
	href="<c:url value='/Personnel/SSS/includes/css/smoothness/jquery-ui.custom.css' />" />
<script type="text/javascript"
	src="<c:url value='/Personnel/SSS/includes/js/jquery-1.7.2.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/Personnel/SSS/includes/js/jquery-ui.min.js' />"></script>
<script type="text/javascript">
	$('document').ready(function() {	
		
		$('tr.proginfo:even td').css({'background-color' : '#F0F8FF'});
		
		$('a.editProgInfo').click(function(){
			var edit = $(this);
			
			$.ajax({ 
					url : "<c:url value='/Personnel/SSS/ajax/loadStudentProgrammingInfo.html' />", 
					data : 
					{ 
						sid : $(this).attr('student-id'), 
						sy : $(this).attr('school-year') 
					},
					beforeSend: function() { 
						edit.empty().html('<img src="/MemberServices/Personnel/SSS/includes/images/ajax-loader-1.gif" border="0" />'); 
					}, 
			    complete: function() { 
			    	edit.empty().html(edit.attr('student-id')); 
			    },
					success: function(data){
						loadStudentProgrammingInfoUI(data);
					}
			});
			
		});
		
		$('#student_id').blur(function(){
			var readonly = $(this).attr('readonly'); 
			
			if (((typeof readonly) == 'undefined' || readonly != 'readonly' || readonly == 'false') && ($('#student_id').val() != "")) {
				$.ajax({ 
						url : "<c:url value='/Personnel/SSS/ajax/loadStudentProgrammingInfo.html' />", 
						data : 
						{ 
							sid : $(this).val(), 
							sy : $('#school_year').val() 
						},
						beforeSend: function() { 
							$('#hdrStudentId').empty().html('<img src="/MemberServices/Personnel/SSS/includes/images/ajax-loader-3.gif" border="0" />'); 
						}, 
				    complete: function() { 
				    	$('#hdrStudentId').empty().html('Student ID'); 
				    },
						success: function(data){
							var found = $(data).find('LoadStudentProgrammingInfoResponse').attr('found');
							
							if(found == 'true') {
								if(confirm("A student record with matching student id " + $('#student_id').val() + " was found. Do you want to load that record?"))
									loadStudentProgrammingInfoUI(data);
								else
									clearStudentProgrammingInfoUI();
							}
						}
				});
			}
			
		});
		
		$('#cancelProgInfo').click(function() {
			clearStudentProgrammingInfoUI();
		});
		
		
		$('#PCatHelp').dialog({ autoOpen : false, width : '780px', height : '600px' });
		$('#ECodeHelp').dialog({ autoOpen : false, width : '380px', height : '600px' });
		
		$('#imgEHelp')
			.css({'cursor' : 'hand'})
			.click(function(){
				$('#ECodeHelp').dialog('open');
			});
		
		$('#imgPCatHelp')
			.css({'cursor' : 'hand'})
			.click(function(){
				$('#PCatHelp').dialog('open');
			});
		
		$('#btn_continue').click(function(){
			$('#pnl_continue')
				.empty()
				.html('<img src="/MemberServices/Personnel/SSS/includes/images/ajax-loader-2.gif" border="0" />');
			
			document.location.href = 'listCurrentlyApprovedStudents.html?profile_id=' + $('#profile_id').val();
		});

	});
	
	function clearStudentProgrammingInfoUI(){
		$('#student_id').val("").removeAttr('readonly');
		$('#student_name').val("");
		$('#student_exceptionality_id').val("");
		$('#student_grade_id').val("0");
		$('#student_pervasive_category_id').val("");
		$('#student_stream_english').removeAttr('checked');
		$('#student_stream_french').removeAttr('checked');
		$('#student_issp').removeAttr('checked');
		$('#student_iep').removeAttr('checked');
		$('#pathway_1_courses').val("");
		$('#pathway_2_courses').val("");
		$('#pathway_3_courses').val("");
		$('#pathway_4_cc_courses').val("");
		$('#pathway_4_ncc_courses').val("");
		$('#pathway_4_pp_courses').val("");
		$('#pathway_4_ncp_courses').val("");
		$('#pathway_5').removeAttr('checked');
		$('#student_approved').removeAttr('checked');	
		$('#addProgInfo').val('Add Student');
		$('#cancelProgInfo').hide();
		
		$('#SSSForm').attr('action', 'addStudentProgrammingInfo.html');
	}
	
	function loadStudentProgrammingInfoUI(data) {
		var found = $(data).find('LoadStudentProgrammingInfoResponse').attr('found');
		
		if(found == 'true') {
			$('#SSSForm').attr('action', 'updateStudentProgrammingInfo.html');
			
			$('#student_id').val($(data).find('StudentId').text()).attr('readonly', 'readonly');
			
			
			$('#student_name').val($(data).find('StudentName').text());
			
			$('#student_exceptionality_id').val($(data).find('Exceptionality').text());
			
			if($(data).find('Grade'))
				$('#student_grade_id').val($(data).find('Grade').text());
			
			$('#student_pervasive_category_id').val($(data).find('PervasiveCategory').text());
			
			if($(data).find('Stream').text() == 'english')
				$('#student_stream_english').attr('checked', 'checked');
			else if($(data).find('Stream').text() == 'french')
				$('#student_stream_french').attr('checked', 'checked');
			
			if($(data).find('ISSP').text() == 'true')
				$('#student_issp').attr('checked', 'checked');
			
			if($(data).find('IEP').text() == 'true')
				$('#student_iep').attr('checked', 'checked');
			
			$('#pathway_1_courses').val($(data).find('P1').text());
			
			$('#pathway_2_courses').val($(data).find('P2').text());
			
			$('#pathway_3_courses').val($(data).find('P3').text());
			
			$('#pathway_4_cc_courses').val($(data).find('P4CC').text());
			
			$('#pathway_4_ncc_courses').val($(data).find('P4NCC').text());
			
			$('#pathway_4_pp_courses').val($(data).find('P4PP').text());
			
			$('#pathway_4_ncp_courses').val($(data).find('P4NCP').text());
			
			if($(data).find('P5').text() == 'true')
				$('#pathway_5').attr('checked', 'checked');
			
			if($(data).find('StudentAssistantSupportApproved').text() == 'true')
				$('#student_approved').attr('checked', 'checked');
				
			$('#addProgInfo').val('Update Student');
			$('#cancelProgInfo').show();
		}
	}
</script>
</head>
<body>
	<table width="1060" border="0" cellspacing="2" cellpadding="2"
		align="center" class="tableSSMain">
		<tr>
			<td>
				<form action="addStudentProgrammingInfo.html" method="post" name="SSSForm" id="SSSForm">
					<input type='hidden' id='profile_id' name='profile_id' value='${schoolprofile.profileId}' />
					<input type='hidden' id='school_year' value='${schoolprofile.projectedSchoolYear}' />
					
					<table width="1060" border="0" cellspacing="2" cellpadding="2"
						class="tableA" id="SS">
						<tr>
							<td class="tableSSTitle"><img
								src="<c:url value='/Personnel/SSS/includes/images/esdnl_logo.jpg' />"
								alt="ESDNL" width="150" height="63" border="0" align="left" />
								<div align="right">
									Student Support Services Profile<br />${schoolprofile.school.schoolName}
									Summary
								</div></td>
						</tr>

						<tr>
							<td>&nbsp;</td>
						</tr>

						<tr>
							<td><b>ALL students who will be accessing Special
									Education Teacher Support in
									${schoolprofile.projectedSchoolYear} school year will be listed
									in this database, INCLUDING those students <u>APPROVED by
										District Office</u> with Pervasive Needs.</b></td>
						</tr>

						<tr>
							<td class="tableSSheader">Individual Student Programming
								Information (${schoolprofile.projectedSchoolYear})</td>
						</tr>
						
						<c:if test="${msg ne null}">
							<tr>
								<td class="help" style='text-align:center;color:red;'>${msg}</td>
							</tr>
						</c:if>

						<tr valign="top">
							<td>
								<table width="1060" border="0" cellspacing="0" cellpadding="2"
									class="table3">
									<tr>
										<th id='hdrStudentId' rowspan='3'>Student ID</th>
										<th rowspan='3'>Student Name<br />
										</th>
										<th rowspan='3'>Exceptionality<br />
										<img id='imgEHelp'
											src='<c:url value='/Personnel/SSS/includes/images/help-icon.png' />'
											width='20px' height='20px' valign='middle' />
										</th>
										<th rowspan='3'>Grade/<br />Level</th>
										<th rowspan='3'>Pervasive<br />Category<br />
										<img id='imgPCatHelp'
											src='<c:url value='/Personnel/SSS/includes/images/help-icon.png' />'
											width='20px' height='20px' valign='middle' />
										</th>
										<th colspan='2'>Stream</th>
										<th colspan='2'>Student has ISSP/IEP</th>
										<th colspan='3'># Courses in Each Pathway</th>
										<th colspan='2'>P4<br />Course</th>
										<th colspan='2'>P4<br />Program</th>
										<th rowspan='3'>P5?</th>
										<th rowspan='3'>Student Approved for Student Assistant
											Support ${ schoolprofile.currentSchoolYear }</th>
									</tr>

									<tr>
										<th rowspan='2'>English</th>
										<th rowspan='2'>French</th>
										<th rowspan='2'>ISSP</th>
										<th rowspan='2'>&nbsp;IEP&nbsp;</th>
										<th rowspan='2'>1</th>
										<th rowspan='2'>2</th>
										<th rowspan='2'>3</th>
										<th colspan='4'>Indicate # of Below</th>
									</tr>

									<tr>
										<th>&nbsp;CC&nbsp;</th>
										<th>NCC</th>
										<th>&nbsp;PP&nbsp;</th>
										<th>NCP</th>
									</tr>

									<tr class='forminput'>
										<td><input type="text" name="student_id" id="student_id"
											size="15" maxlength="50" class="requiredInputBox" /></td>

										<td><input type="text" name="student_name"
											id="student_name" size="20" maxlength="50"
											class="requiredInputBox" /></td>

										<td><sss:ExceptionalityListbox
												id="student_exceptionality_id" cls="requiredInputBox" /></td>

										<td><sss:SchoolGradesListbox id="student_grade_id"
												cls="requiredInputBox" style='width:80px;' /></td>

										<td><sss:PervasiveCategoryListbox
												id="student_pervasive_category_id" cls="requiredInputBox" />
										</td>

										<td><input type="radio" id='student_stream_english' name="student_stream"
											value="english" /></td>

										<td><input type="radio" id='student_stream_french' name="student_stream"
											value="french" /></td>

										<td><input type="checkbox" id="student_issp" name="student_issp"
											value="ISSP" /></td>

										<td><input type="checkbox" id="student_iep" name="student_iep"
											value="IEP" /></td>

										<td><input type="text" id="pathway_1_courses" name="pathway_1_courses"
											class="requiredInputBox" style='width: 20px;' /></td>

										<td><input type="text" id="pathway_2_courses" name="pathway_2_courses"
											class="requiredInputBox" style='width: 20px;' /></td>

										<td><input type="text" id="pathway_3_courses" name="pathway_3_courses"
											class="requiredInputBox" style='width: 20px;' /></td>

										<td><input type="text" id="pathway_4_cc_courses" name="pathway_4_cc_courses"
											class="requiredInputBox" style='width: 20px;' /></td>

										<td><input type="text" id="pathway_4_ncc_courses" name="pathway_4_ncc_courses"
											class="requiredInputBox" style='width: 20px;' /></td>

										<td><input type="text" id="pathway_4_pp_courses" name="pathway_4_pp_courses"
											class="requiredInputBox" style='width: 20px;' /></td>

										<td><input type="text" id="pathway_4_ncp_courses" name="pathway_4_ncp_courses"
											class="requiredInputBox" style='width: 20px;' /></td>

										<td><input type="checkbox" id="pathway_5" name="pathway_5" style='width: 20px;' /></td>

										<td><input type="checkbox" id="student_approved" name="student_approved" style='width: 20px;' /></td>
									</tr>
									
									<tr class='formsubmit' style='border-bottom:solid 1px white;'>
										<td colspan='18'>
											<input id="addProgInfo" type="submit" value='Add Student' />
											<input id="cancelProgInfo" type="button" value='Cancel Update' style='display:none;'/>
										</td>
									</tr>
									
									<tr>
										<td colspan='18' style='text-align:left; background-color:white; padding: 2px; border-top:solid 1px white;'>
											<B>STUDENTS CURRENTLY ADDED:</b><br />
											List students user has already entered, sorted alphabetically and when click on student can autopopulate above to allow for editing.
										</td>
									</tr>
									
									<c:choose>
										<c:when test="${fn:length(proginfos) gt 0}">	
											<c:forEach items="${proginfos}" var="info">
												<tr class="proginfo">
													<td><a class='editProgInfo' href='#' student-id='${info.studentId}' school-year='${info.schoolYear}'>${info.studentId}</a></td>
			
													<td>${info.studentName}</td>
			
													<td>${info.exceptionality}</td>
			
													<td>${info.grade.name}</td>
			
													<td>${info.pervasiveCategory > 0 ? info.pervasiveCategory : "None"}</td>
			
													<td colspan='2'>${info.stream}</td>
			
													<td>${info.issp ? "YES" : "NO"}</td>
			
													<td>${info.iep ? "YES" : "NO"}</td>
			
													<td>${info.p1Courses}</td>
			
													<td>${info.p2Courses}</td>
			
													<td>${info.p3Courses}</td>
			
													<td>${info.p4CC}</td>
			
													<td>${info.p4NCC}</td>
			
													<td>${info.p4PP}</td>
			
													<td>${info.p4NCP}</td>
			
													<td>${info.p5 ? "YES" : "NO"}</td>
			
													<td>${info.studentAssistantSupport ? "YES" : "NO"}</td>
												</tr>
											</c:forEach>
											<tr class="proginfo" >
												<td colspan='18' style='background-color: #E4E4E4;text-align:left;font-weight:bold;'>Records: ${fn:length(proginfos)}</td>
											</tr>
										</c:when>
										<c:otherwise>
											<tr class="proginfo" >
												<td colspan='18' style='text-align:left;color:red;font-weight:bold;'>No student programming information records found.</td>
											</tr>
										</c:otherwise>
									</c:choose>

								</table>
							</td>
						</tr>
						
						<tr>
							<td>
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

	<div align="center" class="copyright">&copy; 2012 Eastern School
		District &middot; All Rights Reserved</div>

	<div class="help" id="PCatHelp" style='display: none;' title="Pervasive Categories">
		<b>Pervasive Categories:</b><br>
		<b>1.&nbsp;</b> Pathways 5 &nbsp;&nbsp;&nbsp; <b>2.&nbsp;</b> Severe Behavioral
		&nbsp;&nbsp;&nbsp; <b>3.&nbsp;</b> &gt;75% Pathways 4 Courses
		&nbsp;&nbsp;&nbsp; <b>4.&nbsp;</b> Pervasive Developmental Delay <br>
		
		<div class="criteria" id="PCriteria">
			<img src="<c:url value='/Personnel/SSS/includes/images/govnl.jpg' />"
				alt="" width="150" height="75" border="0" align="right" />
			<h3>Pervasive Needs Criteria</h3>
			<p>
				Government of Newfoundland and Labrador<br />Department of Education
			</p>
			<p>
				<b>Students identified with pervasive needs must meet one of the following <u>four categories:</u></b>
			</p>
			<ol>
				<li>The student's program consists of a <b>Pathway 5
						Functional Curriculum.</b>
				</li>
				
				<li>The student has a <b><u>diagnosed exceptionality
							related to behavior</u>
				</b>:
					<ul>
						<ul>
							<li>A Behaviour Management Plan is in place. <b>AND</b>
							</li>
							<li>The student is under the care of appropriate medical
								personnel. <b>AND</b>
							</li>
							<li>The student or others are at a significant risk for
								harm.</li>
						</ul>
						
						<li>It is important to make the distinction between a student
							requiring additional teacher supports for the purpose of program
							delivery and a student requiring additional student assistant
							support. It may be that the student who behaves aggressively in
							the larger classroom may be able to be supported in either the
							larger classroom or a small group setting with the existing
							instructional support teacher and/or a student assistant.
							
						<li>Students without a diagnosis or students whose behaviour
							is disruptive necessitating disciplinary action and/or
							counselling do not meet the criteria for additional support.
					</ul></li>
				
				<li>A<b> <u>high school student who requires 75% or
							more of their courses be Pathway 4</u>
				</b> curricular/non-curricular:
					<ul>
						<ul>
							<li>The student has a cognitive delay but does not meet the
								criteria for Pathway 5. <b>AND</b>
							</li>
							<li>The student will not meet graduation requirements. <b>AND</b>
							</li>
							<li>The student will receive a School Leaving Certificate
								upon high school completion.</li>
						</ul>
						
						<li>In consultation with district level staff, in extenuating
							circumstances, support in this category could apply to students
							in Grades 4-9.
					</ul></li>
				
				<li>A <b><u>Student with Pervasive Developmental
							Disorder (PDD) with significant needs related to the disorder</u>
				</b> who requires non-curricular programming that cannot be accommodated
					by the classroom or instructional support teacher:
					<ul>
						<ul>
							<li>Communication programming for a student who is nonverbal
								or has little meaningful speech;</li>
							<li>Anxiety management programming for a student who
								presents with severe anxiety that prevents him/her from being
								able to meaningfully participate and/or cope in the regular
								class setting;</li>
							<li>Self-regulation programming for a student who has severe
								difficulties in regulating his/her emotions and/or behaviour in
								the larger classroom setting.</li>
						</ul>
					</ul></li>
			</ol>
			[ <a href="#" onclick="toggle_visibility('PCriteria');">Close</a> ]
		</div>
	</div>

	<div class="help" id="ECodeHelp" style='display: none;' title="Exceptionality Codes">
		<H3>Exceptionality Codes</h3>
		<ul>
			<li><b>BI</b> &middot; Brain Injury</li>
			<li><b>CD</b> &middot; Cognitive Disorder</li>
			<li><b>DD</b> &middot; Developmental Delay</li>
			<li><b>EMBD</b> &middot; Emotional Mental Health and/or Behavioural Disorder</li>
			<li><b>GT</b> &middot; Gifted and Talented</li> 
			<li><b>HL</b> &middot; Hearing	Loss</li>
			<li><b>HD</b> &middot; Health Disorder</li>
			<li><b>PDD</b> &middot; Pervasive Developmental Disorder</li>
			<li><b>PD</b> &middot; Physical Disability</li>
			<li><b>SLP</b> &middot; Speech and/or Language Disorder</li> 
			<li><b>VL</b> &middot; Vision Loss</li>
			<li><b>LD</b> &middot; Learning Disability</li>
		</ul><br />
		[ <a href="#" onclick="toggle_visibility('ECodeHelp');">Close</a> ]
	</div>

</body>
</html>
