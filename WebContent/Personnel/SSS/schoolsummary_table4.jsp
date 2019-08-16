<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*"%>

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
							$('#hdrStudentId').empty().html('<img src="/MemberServices/Personnel/SSS/includes/images/ajax-loader-2.gif" border="0" />'); 
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
		
		$('#imgPCatHelp')
			.css({'cursor' : 'hand'})
			.click(function(){
				$('#PCatHelp').dialog('open');
			});
		
		$('#student_staying').click(function(){
			
			if($.trim($('#student_id').val()) == ''){
				alert('Enter student id before proceeding.');
				$('#student_staying').removeAttr('checked');
				$('#student_id').focus();
				
				return;
			}
			
			if($(this).is(':checked')) {	
				
				$.ajax({ 
					url : "<c:url value='/Personnel/SSS/ajax/loadStudentProgrammingInfo.html' />", 
					data : 
					{ 
						sid : $('#student_id').val(), 
						sy : $('#projected_school_year').val() 
					},
					success: function(data){
						var found = $(data).find('LoadStudentProgrammingInfoResponse').attr('found');
						
						if(found == 'false') {
							if(confirm("A student record matching student id " 
									+ $('#student_id').val() + " was NOT found for school year " + $('#projected_school_year').val() 
									+ ". Do you want to add that record?")){
								document.location.href = 'listProjectedStudentProgrammingInfo.html?profile_id=' + $('#profile_id').val();
							}
						}
					}
				});
				
			}
			
			updateStudentStayingUI($(this));
		});
		
		$('#SSSForm').submit(function(){
			
			if(!$('#student_staying').is(':checked')){
				if((typeof ($('#transition_school_id').val()) == 'undefined'
						|| $('#transition_school_id').val() == '-1')
						&& !$('#student_graduating').is(':checked')
						&& !$('#student_leaving').is(':checked')
						&& !$('#student_moving').is(':checked')) {
					alert('If the student is not remaining at your school please choose one of the other options available.')
					return false;
				}
				else
					return true;
			}
			else
				return true;
		});

	});
	
	function clearStudentProgrammingInfoUI(){
		$('#student_id').val("").removeAttr('readonly');
		$('#student_name').val("");
		$('#student_pervasive_category_id').val("");
		$('#student_staying').removeAttr('checked');	
		$('#transitioning_school_id').val("");
		$('#student_graduating').removeAttr('checked');
		$('#student_leaving').removeAttr('checked');
		$('#student_moving').removeAttr('checked');
		
		$('#addProgInfo').val('Add Student');
		$('#cancelProgInfo').hide();
		
		$('#SSSForm').attr('action', 'addCurrentlyApprovedStudentProgrammingInfo.html');
	}
	
	function loadStudentProgrammingInfoUI(data) {
		var found = $(data).find('LoadStudentProgrammingInfoResponse').attr('found');
		
		if(found == 'true') {
			$('#SSSForm').attr('action', 'updateCurrentlyApprovedStudentProgrammingInfo.html');
			
			$('#student_id').val($(data).find('StudentId').text()).attr('readonly', 'readonly');
			
			$('#student_name').val($(data).find('StudentName').text());
			
			$('#student_pervasive_category_id').val($(data).find('PervasiveCategory').text());
			
			if($(data).find('Staying').text() == 'true')
				$('#student_staying').attr('checked', 'checked');
			else if($(data).find('TransitionSchool'))
				$('#transitioning_school_id').val($(data).find('TransitionSchool').find('ID').text());
			else if($(data).find('Graduating').text() == 'true')
				$('#student_graduating').attr('checked', 'checked');
			else if($(data).find('Leaving').text() == 'true')
				$('#student_leaving').attr('checked', 'checked');
			else if($(data).find('Moving').text() == 'true')
				$('#student_moving').attr('checked', 'checked');
			
			updateStudentStayingUI($('#student_staying'));
			
			$('#addProgInfo').val('Update Student');
			$('#cancelProgInfo').show();
		}
	}
	
	function updateStudentStayingUI(cb){
		if(cb.is(':checked')){
			$('#transitioning_school_id').attr('disabled', 'disabled');
			$('#student_graduating').attr('disabled', 'disabled');
			$('#student_leaving').attr('disabled', 'disabled');
			$('#student_moving').attr('disabled', 'disabled');
		}
		else {
			$('#transitioning_school_id').val("-1");
			$('#transitioning_school_id').removeAttr('disabled');
			
			$('#student_graduating').removeAttr('checked');
			$('#student_graduating').removeAttr('disabled');
			
			$('#student_leaving').removeAttr('checked');
			$('#student_leaving').removeAttr('disabled');
			
			$('#student_moving').removeAttr('checked');
			$('#student_moving').removeAttr('disabled');
		}
	}
</script>
</head>
<body>
	<table width="1060" border="0" cellspacing="2" cellpadding="2"
		align="center" class="tableSSMain">
		<tr>
			<td>
				<form action="addCurrentlyApprovedStudentProgrammingInfo.html" method="post" name="SSSForm" id="SSSForm">
					<input type='hidden' id='profile_id' name='profile_id' value='${schoolprofile.profileId}' />
					<input type='hidden' id='school_year' value='${schoolprofile.currentSchoolYear}' />
					<input type='hidden' id='projected_school_year' value='${schoolprofile.projectedSchoolYear}' />
					
					<table width="1060" border="0" cellspacing="2" cellpadding="2"
						class="tableA" id="SS">
						<tr>
							<td class="tableSSTitle"><img
								src="<c:url value='/Personnel/SSS/includes/images/esdnl_logo.jpg' />"
								alt="ESDNL" width="150" height="63" border="0" align="left" />
								<div align="right">
									Listing of Currently (${schoolprofile.currentSchoolYear}) APPROVED Students<br />with Pervasive Needs ${schoolprofile.projectedSchoolYear}									
								</div>
							</td>
						</tr>

						<tr>
							<td>&nbsp;</td>
						</tr>
						
						<tr>
							<td>Complete this table for your projected September <b><u>${schoolprofile.projectedSchoolYear} enrollment</u></b>.</td>
						</tr>
						
						<tr>
							<td>&nbsp;</td>
						</tr>

						<tr>
							<td>
								<b>
									List all students <u>currently registered in your school</u> who have been approved in the Pervasive Needs Category <u>during 
									the ${schoolprofile.currentSchoolYear} school year and who will need this level of support next year</u>. Indicate 
									which school the student will be registered in <u>during the ${schoolprofile.projectedSchoolYear} school year</u>. Also include
									approved pervasive students who <u>are transitioning out</u> of your school June ${schoolprofile.projectedSchoolYear} <u>who will
									continue to need this level of support</u>.
								</b>
							</td>
						</tr>

						<tr>
							<td class="tableSSheader">
								${schoolprofile.currentSchoolYear} APPROVED Students with Pervasive Needs
							</td>
						</tr>
						
						<c:if test="${msg ne null}">
							<tr>
								<td class="help" style='text-align:center;color:red;'>${msg}</td>
							</tr>
						</c:if>

						<tr valign="top">
							<td>
								<table width="1060" border="0" cellspacing="0" cellpadding="2" class="table3">
									<tr>
										<th id='hdrStudentId'>Student ID</th>
										<th>Student Name<br /></th>
										<th>
											Pervasive<br />Category<br />
											<img id='imgPCatHelp' src='<c:url value='/Personnel/SSS/includes/images/help-icon.png' />' width='20px' height='20px' valign='middle' />
										</th>
										<th id='hdrStudentStaying'>At Your School<br />${ schoolprofile.projectedSchoolYear }?</th>
										<th>Transitioning to School<br />${ schoolprofile.projectedSchoolYear }?</th>
										<th>Graduating or Leaving School<br />${ schoolprofile.projectedSchoolYear }?</th>
										<th>Moving out of<br />District / Province?</th>
									</tr>

									<tr class='forminput'>
										<td>
											<input type="text" name="student_id" id="student_id" size="15" maxlength="50" class="requiredInputBox" />
										</td>

										<td>
											<input type="text" name="student_name" id="student_name" size="20" maxlength="50" class="requiredInputBox" />
										</td>

										<td>
											<sss:PervasiveCategoryListbox id="student_pervasive_category_id" cls="requiredInputBox" />
										</td>

										<td>
											<input type="checkbox" id='student_staying' name="student_staying" value="YES" /> Yes
										</td>

										<td>
											<sss:Schools id="transitioning_school_id" cls="requiredInputBox" dummy="true" />
										</td>

										<td>
											<input type="checkbox" id='student_graduating' name="student_graduating" value="YES" /> G
											<input type="checkbox" id='student_leaving' name="student_leaving" value="YES" /> L
										</td>

										<td>
											<input type="checkbox" id='student_moving' name="student_moving" value="YES" /> Yes
										</td>
									</tr>
									
									<tr class='formsubmit' style='border-bottom:solid 1px white;'>
										<td colspan='7'>
											<input id="addProgInfo" type="submit" value='Add Student' />
											<input id="cancelProgInfo" type="button" value='Cancel Update' style='display:none;'/>
										</td>
									</tr>
									
									<tr>
										<td colspan='7' style='text-align:left; background-color:white; padding: 2px; border-top:solid 1px white;'>
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
			
													<td>${info.pervasiveCategory > 0 ? info.pervasiveCategory : "None"}</td>
			
													<td>${info.staying ? "YES" : "NO"}</td>
			
													<td>${(info.transitionSchool ne null) ? info.tranitionSchool.schoolName : "NONE"}</td>
			
													<td>${info.graduating ? "Graduating" : info.leaving ? "Leaving" : "NO"}</td>
			
													<td>${info.moving ? "YES" : "NO"}</td>
												</tr>
											</c:forEach>
											<tr class="proginfo" >
												<td colspan='7' style='background-color: #E4E4E4;text-align:left;font-weight:bold;'>Records: ${fn:length(proginfos)}</td>
											</tr>
										</c:when>
										<c:otherwise>
											<tr class="proginfo" >
												<td colspan='7' style='text-align:left;color:red;font-weight:bold;'>No student programming information records found.</td>
											</tr>
										</c:otherwise>
									</c:choose>

								</table>
							</td>
						</tr>
						
						<tr>
							<td>
								<div align="center">
									<input style='color:red;font-weight:bold;' type="button" value="Continue" />
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

</body>
</html>
