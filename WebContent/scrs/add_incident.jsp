<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/scrs.tld" prefix="b" %>

<esd:SecurityCheck permissions='BULLYING-ANALYSIS-SCHOOL-VIEW,BULLYING-ANALYSIS-ADMIN-VIEW' />

<c:url value="listSchoolIncidentReports.html" var="listSchoolIncidentsURL">
	<c:param name="schoolId" value="${usr.personnel.school.schoolID}" />
</c:url>
<c:url value="noIncidentsToReport.html" var="noIncidentsToReportURL">
	<c:param name="schoolId" value="${usr.personnel.school.schoolID}" />
</c:url>

<c:url value="listSchools.html" var="listSchoolsURL" />
<c:url value="listAnalysisReports.html" var="listAnalysisReportsURL" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>SCRS - Add Incident</title>
	
	<script type="text/javascript">
		$('document').ready(function(){
			$('.inputform tr td').css({'color':'black'});
			$('.inputform tr:not(table.inputform-interior tr, .heading, .error):even').css({'background-color':'#FFFFFF'});
			$('.inputform tr:not(table.inputform-interior tr,  .heading):odd').css({'background-color':'#F4F4F4'});
			$('.inputform tr:not(.heading, .heading3, .error) td').css({'border':'solid 1px #c4c4c4'});
			$('.inputform td.heading2').css({'border-right' : 'solid 3px #FF8000'});
			$('.inputform td.heading3').css({'border' : 'none'});
			$('table.inputform-interior tr:even').css({'background-color':'#FFFFFF'});
			$('table.inputform-interior tr:odd').css({'background-color':'#F4F4F4'});
			$('table.inputform-interior td.heading2').css({'border-right' : 'solid 3px #678FC2'});
			
			$('.datefield').datepicker({ dateFormat: "dd/mm/yy" });
			
			$('.other[type=checkbox]').click(function() {
				if($('#' + $(this).attr("id") + '_specified').length > 0)
					$('#' + $(this).attr("id") + '_specified').toggle();
			});
			
			$('.isparent[type=checkbox]').click(function() {
				if($('#pnl_' + $(this).attr("id")).length > 0)
					$('#pnl_' + $(this).attr("id")).toggle();
			});
			
			$('.other[type=checkbox]:checked').each(function(){
				if($('#' + $(this).attr("id") + '_specified').length > 0)
					$('#' + $(this).attr("id") + '_specified').show();
			});
			
			$('.isparent[type=checkbox]:checked').click(function() {
				if($('#pnl_' + $(this).attr("id")).length > 0)
					$('#pnl_' + $(this).attr("id")).show();
			});
			
			$('#lnkNoIncidentsToReport').click(function(){
				if(confirm('Are you sure you have NO incidents of bullying in your school to report?')) {
					$.post("${noIncidentsToReportURL}", { ajax: true }, function(data){
						alert($(data).find('NO-BULLYING-INCIDENT-REPORTED-RESULT').text());
					}, "xml");
				}
				
				return false;
			});
			
			$( "#studentId" ).autocomplete({
				minLength: 2,
				source: function(request, response) {
					$.getJSON( "ajax/studentAutoComplete.html", { term: request.term }, response );
			  },
			  search: function(event, ui){
					$( "#studentFirstName" ).val( "" );
					$( "#studentLastName" ).val( "" );
					$( "#studentMiddleName" ).val( "" );
				},
			  select: function( event, ui ) {
				  $( "#studentId" ).val( ui.item.studentid );
					$( "#studentFirstName" ).val( ui.item.firstname ).attr("readonly", "readonly");
					$( "#studentLastName" ).val( ui.item.lastname ).attr("readonly", "readonly");
					$( "#studentMiddleName" ).val( ui.item.middlename ).attr("readonly", "readonly");
					$( "#studentGenderId_" + ui.item.gender ).attr("CHECKED", "CHECKED");
					$( "#studentGenderId_1, #studentGenderId_2" ).click(function(){
						return false;
					});
					
					
					return false;
				},
		    response: function(event, ui) {
		    	if (ui.content.length === 0) {
						$( "#studentFirstName" ).removeAttr("readonly");
						$( "#studentLastName" ).removeAttr("readonly");
						$( "#studentMiddleName" ).removeAttr("readonly");
						$( "#studentGenderId_1, #studentGenderId_2" ).unbind('click').removeAttr("checked");
		      } 
		    }
			})
			.data( "autocomplete" )._renderItem = function( ul, item ) {
				return $( "<li></li>" )
					.data( "item.autocomplete", item )
					.append("<a>" + item.lastname + ", " + item.firstname 
							+ (!$.isEmptyObject(item.middlename) ? " " + item.middlename : "") + " (" + item.studentid + ")</a>")
					.appendTo( ul );
			};
			
			
			$('#chk_Bullying').click(function(){
				if($(this).is(":checked"))
					$('#pnl_Bullying').show();
				else
					$('#pnl_Bullying').hide();
			});
			
			$('#chk_IllegalSubstance').click(function(){
				if($(this).is(":checked"))
					$('#pnl_IllegalSubstance').show();
				else
					$('#pnl_IllegalSubstance').hide();
			});
			
			$('#chk_SexualBehaviour').click(function(){
				if($(this).is(":checked"))
					$('#pnl_SexualBehaviour').show();
				else
					$('#pnl_SexualBehaviour').hide();
			});
			
			$('#chk_ThreateningBehavior').click(function(){
				if($(this).is(":checked"))
					$('#pnl_ThreateningBehavior').show();
				else
					$('#pnl_ThreateningBehavior').hide();
			});
			
			$('#chk_SchoolSafetyIssue').click(function(){
				if($(this).is(":checked"))
					$('#pnl_SchoolSafetyIssue').show();
				else
					$('#pnl_SchoolSafetyIssue').hide();
			});
			
			$('.referral-reason-box').each(function(){
				if($(this).children().children('input[type=checkbox]:checked').length > 0) {
					$(this).show();
					
					if($('#' + $(this).attr('for')).length > 0)
						$('#' + $(this).attr('for')).attr('CHECKED', 'CHECKED');
				}
			});
	
		});
	</script>
	<style>    
		.ui-autocomplete {        
			max-height: 100px;        
			overflow-y: auto;        
			/* prevent horizontal scrollbar */        
			overflow-x: hidden;    
		}    
		
		html .ui-autocomplete { height: 100px; }
		 
		label {vertical-align: middle; padding-left: 5px; font-size: 11px;}
		 
		input[type=checkbox], input[type=radio]{
		    vertical-align: middle; 
		    margin: 0px;
		    margin-left: 5px;
		    margin-top:5px;
		    margin-bottom:5px;
		}
		
		input[type=text] {
		    vertical-align: middle;
		} 
		 
		.referral-reason-category {
		 		font-size: 12px;
		 		font-weight: normal;
		}
		 
		td.heading3 { font-weight: bold; font-size: 12px; text-decoration: underline; }
		 
		.referral-reason-box {
		 		display: none;
		 		padding-left: 25px;
		}
		.referral-reason-box  div{
		 		/*border-left: solid 2px #678FC2;*/
		}
		 
		.checkboxlist-label {
		 		padding-left: 5px;
		 		font-style: italic;
		}
	</style>
</head>
<body>
	<table cellspacing="4" cellpadding="" border="0" align="center" width="80%">
		<tr>
			<td>
				<esd:SecurityAccessRequired permissions="BULLYING-ANALYSIS-SCHOOL-VIEW">
					<a href="${listSchoolIncidentsURL}" class="menu">view all incidents</a>&nbsp;|&nbsp;
					<a id="lnkNoIncidentsToReport" href="#" class="menu noincidents">No Incidents to Report? Click Here</a>
				</esd:SecurityAccessRequired>
				<esd:SecurityAccessRequired permissions="BULLYING-ANALYSIS-ADMIN-VIEW">
					<a href="${listSchoolsURL}" class="menu">list schools</a>&nbsp;|&nbsp;<a href="${listAnalysisReportsURL}" class="menu">list Analysis Reports</a>
				</esd:SecurityAccessRequired>
			</td>
		</tr>
	</table>
	<form action="addIncidentReport.html" method="post">
		<table cellspacing="6" cellpadding="4" border="0" align="center" width="80%" class="inputform">
			
			<tr class="heading">
				<td colspan="2" class='submitstyle heading1' style="color:white;">Add Incident Report</td>
			</tr>
			
			<c:if test="${msg ne null}">
				<tr class="error">
					<td colspan="2" style="color:red !important;">${msg}</td>
				</tr>
			</c:if>
			
			<tr>
				<td class='heading2'>Incident Date</td>
				<td style='padding: 8px !important;'>
					<input name='incidentDate' type='text' class='datefield' value="${ not empty incident.incidentDate ? incident.incidentDateFormatted : '' }" />
				</td>
			</tr>
			
			<tr>
				<td class='heading2' width="40%" valign="top">Student Engaging in Misconduct</td>
				<td width="*" align="left">
					<table align="left" cellspacing="4" cellpadding="4" border="0" align="center" width="100%" class="inputform-interior" >
						<tr>
							<td class='heading2'>Student ID:</td>
							<td><input type="text" id="studentId" name="studentId" value="${ incident.student.studentId }" /></td>
						</tr>
						<tr>
							<td class='heading2'>First Name:</td>
							<td>
								<input type="text" id="studentFirstName" name="studentFirstName" value="${ incident.student.firstName }" />
							</td>
						</tr>
						<tr>
							<td class='heading2'>Last Name:</td>
							<td><input type="text" id="studentLastName" name="studentLastName" value="${ incident.student.lastName }" /></td>
						</tr>
						<tr>
							<td class='heading2'>Middle Name:</td>
							<td><input type="text" id="studentMiddleName" name="studentMiddleName" value="${ incident.student.middleName }" /></td>
						</tr>
						<tr>
							<td class='heading2'>Gender:</td>
							<td id="td_studentgender"><b:GenderRadioButtonList id="studentGenderId" value="${ incident.student.gender }" /></td>
						</tr>
						<tr>
							<td colspan='2' class='heading3'>Current Info:</td>
						</tr>
						<tr>
							<td class='heading2' width="40%">School:</td>
							<td width="*">
								<esd:SecurityAccessRequired permissions="BULLYING-ANALYSIS-SCHOOL-VIEW">
									<input type='hidden' name='schoolId' value='${usr.personnel.school.schoolID}' />
									${usr.personnel.school.schoolName}
								</esd:SecurityAccessRequired>
								
								<esd:SecurityAccessRequired permissions="BULLYING-ANALYSIS-ADMIN-VIEW">
									<b:Schools id="schoolId" dummy="true" value="${incident.school}" />
								</esd:SecurityAccessRequired>
							</td>
						</tr>
						<tr>
							<td class='heading2'>Grade:</td>
							<td>
								<b:SchoolGradesListbox id="gradeId" value="${incident.studentGrade}" />
							</td>
						</tr>
						<tr>
							<td class='heading2'>Age:</td>
							<td>
								<b:AgeListbox id="studentAge" minAge="4" maxAge="20" dummy="true" value="${incident.studentAge}" />
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<tr>
				<td class='heading2'>Reporting Administrator/Teacher</td>
				<td style='padding: 8px !important;'>
					<input type='hidden' name='submittedById' value='${usr.personnel.personnelID}' />
					${usr.personnel.fullName}
				</td>
			</tr>
			
			<tr>
				<td class='heading2' valign='top'>Location of Incident<br/><span class="smalltext">(check those which apply)</span></td>
				<td>
					<b:LocationTypeCheckboxList id="lst_Location" value="${incident.locationTypes}" />
				</td>
			</tr>
			
			<tr>
				<td class='heading2' valign='top'>Time of Incident<br/><span class="smalltext">(check those which apply<br/>specify exact time if relevant)</span></td>
				<td>
					<b:TimeTypeCheckboxList id="lst_Time" value="${incident.timeTypes}" />
				</td>
			</tr>
			
			<tr>
				<td class='heading2' valign='top'>Target<br/><span class="smalltext">(check those which apply)</span></td>
				<td>
					<b:TargetTypeCheckboxList id="lst_Target" value="${incident.targetTypes}" />
				</td>
			</tr>
			
			<tr>
				<td class='heading2' valign='top'>Type of Behavior<br /><span class="smalltext">(choose those which apply)</span></td>
				<td>
					<input type='checkbox' id='chk_Bullying' /><label for='chk_Bullying' class='referral-reason-category'>Bullying</label>
					<div id='pnl_Bullying' for='chk_Bullying' class='referral-reason-box'>
						<div>
							<b:BullyingBehaviorTypeCheckboxList id="lst_BullingBehavior" value="${incident.bullyingBehaviorTypes}" label='Bullying behavior:' />
						</div><br />
						<div>
							<b:BullyingReasonTypeCheckboxList id="lst_BullyingReason" value="${incident.bullyingReasonTypes}" label='Reason for bullying behavior:' />
						</div>
					</div><br />
					
					<input type='checkbox' id='chk_IllegalSubstance' /><label for='chk_IllegalSubstance' class='referral-reason-category'>Illegal Substance Use/Possession</label>
					<div id='pnl_IllegalSubstance' for='chk_IllegalSubstance' class='referral-reason-box'>
						<div>
							<b:IllegalSubstanceTypeCheckboxList id="lst_IllegalSubstance" value="${incident.illegalSubstanceTypes}" />
						</div>
					</div><br />
					
					<input type='checkbox' id='chk_SexualBehaviour' /><label for='chk_SexualBehaviour' class='referral-reason-category'>Inappropriate Sexual Behaviour</label>
					<div id='pnl_SexualBehaviour' for='chk_SexualBehaviour' class='referral-reason-box'>
						<div>
							<b:SexualBehaviourTypeCheckboxList id="lst_SexualBehaviour" value="${incident.sexualBehaviourTypes}" />
						</div>
					</div><br />
					
					<input type='checkbox' id='chk_ThreateningBehavior' /><label for='chk_ThreateningBehavior' class='referral-reason-category'>Threat or Threatening Behavior</label>
					<div id='pnl_ThreateningBehavior' for='chk_ThreateningBehavior' class='referral-reason-box'>
						<div>
							<b:ThreateningBehaviorTypeCheckboxList id="lst_ThreateningBehavior" value="${incident.threateningBehaviorTypes}" />
						</div>
					</div><br />
					
					<input type='checkbox' id='chk_SchoolSafetyIssue' /><label for='chk_SchoolSafetyIssue' class='referral-reason-category'>School Safety Issues</label>
					<div id='pnl_SchoolSafetyIssue' for='chk_SchoolSafetyIssue' class='referral-reason-box'>
						<div>
							<b:SchoolSafetyIssueTypeCheckboxList id="lst_SchoolSafetyIssue" value="${incident.schoolSafetyIssueTypes}" />
						</div>
					</div>
				</td>
			</tr>
			
			<tr>
				<td class='heading2' valign="top">Action Taken<br/><span class="smalltext">(check those which apply)</span></td>
				<td>
					<b:ActionTypeCheckboxList id="lst_Action" value="${incident.actionTypes}" />
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="center" class="submitstyle"><input type="submit" value="submit" style="font-weight:bold;font-size:20px;" /></td>
			</tr>
			
		</table>
	</form>
</body>
</html>