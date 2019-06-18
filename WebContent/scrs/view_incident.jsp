<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/scrs.tld" prefix="b" %>

<esd:SecurityCheck permissions='BULLYING-ANALYSIS-SCHOOL-VIEW,BULLYING-ANALYSIS-ADMIN-VIEW' />

<c:url value="index.html" var="addIncidentURL" />
<c:url value="listSchoolIncidentReports.html" var="listSchoolIncidentsURL">
	<c:param name="schoolId" value="${incident.school.schoolID}" />
</c:url>
<c:url value="listSchools.html" var="listSchoolsURL" />
	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>SCRS - View Incident</title>
	<style type="text/css">
		ul {
			list-style-position:inside;
			padding: 0px;
			margin: 0px;
		}
		li{
			text-indent: 0px;
		}
		.referral-reason-category {
			font-style: italic !important;
		}
		.referral-reason-box {
		 		margin-left: 10px;
		 		padding-bottom:5px;
		}
		.referral-reason-other-box {
		 		margin-left: 25px;
		}
	</style>
	<script type="text/javascript">
		$('document').ready(function(){
			$('.inputform tr:even').css({'background-color':'white'});
			$('.inputform tr:odd').css({'background-color':'#F4F4F4'});
			$('.inputform tr:not(.heading, .error) td').css({'border':'solid 1px #c4c4c4'});
			$('.inputform td.heading2').css({'border-right' : 'solid 3px #FF8000'});
		});
	</script>
</head>
<body>
	<table cellspacing="4" cellpadding="" border="0" align="center" width="60%">
		<tr>
			<td>
				<a href="${addIncidentURL}" class="menu">add incident</a> 
				&nbsp;|&nbsp;<a href="${listSchoolIncidentsURL}" class="menu">view all &quot;${incident.school.schoolName}&quot; incidents</a>
				<esd:SecurityAccessRequired permissions="BULLYING-ANALYSIS-ADMIN-VIEW">
					&nbsp;|&nbsp;<a href="${listSchoolsURL}" class="menu">list schools</a>
				</esd:SecurityAccessRequired>
			</td>
		</tr>
	</table>
	<form action="" method="post">
		<table cellspacing="4" cellpadding="4" border="0" align="center" width="60%" class="inputform">
			<tr class="heading">
				<td colspan="2" class='submitstyle heading1' style="color:white;">View Incident Report</td>
			</tr>
			
			<tr>
				<td class='heading2' width="175px">Incident Date</td>
				<td width="*">
					<fmt:formatDate type="date" dateStyle="long" value="${ incident.incidentDate }" />
				</td>
			</tr>
			
			<tr>
				<td class='heading2'>School</td>
				<td width="*">
					${incident.school.schoolName}
				</td>
			</tr>
			
			<tr>
				<td class='heading2'>Reporting Person</td>
				<td>
					${incident.submittedBy.fullName}
				</td>
			</tr>
			
			<tr>
				<td class='heading2'>Student ID/Name</td>
				<td>
					${incident.student.lastName}, ${incident.student.firstName} ${ not empty incident.student.middleName ? incident.student.middleName : '' } (${ incident.student.studentId })
				</td>
			</tr>
			
			<tr>
				<td class='heading2'>Student Gender</td>
				<td>
					${incident.student.gender.name}
				</td>
			</tr>
			
			<tr>
				<td class='heading2'>Student Age</td>
				<td>
					${incident.studentAge}
				</td>
			</tr>
			
			<tr>
				<td class='heading2'>Grade</td>
				<td>
					${incident.studentGrade.name}
				</td>
			</tr>
			
			<tr>
				<td class='heading2' valign="top">Location of Incident</td>
				<td>
					<ul>
						<c:forEach items="${incident.locationTypes}" var="type" varStatus="status">
							<li>
								${ type.typeName }
								<c:if test="${ not empty type.specified }">
									<div class='referral-reason-other-box'>
										${ type.specified }
									</div>
								</c:if>
							</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
			
			<tr>
				<td class='heading2' valign="top">Time of incident</td>
				<td>
					<ul>
						<c:forEach items="${incident.timeTypes}" var="type" varStatus="status">
							<li>
								${ type.typeName }
								<c:if test="${ not empty type.specified }">
									<div class='referral-reason-other-box'>
										${ type.specified }
									</div>
								</c:if>
							</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
			
			<tr>
				<td class='heading2' valign="top">Target(s)</td>
				<td>
					<ul>
						<c:forEach items="${incident.targetTypes}" var="type" varStatus="status">
							<li>
								${ type.typeName }
								<c:if test="${ not empty type.specified }">
									<div class='referral-reason-other-box'>
										${ type.specified }
									</div>
								</c:if>
							</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
			
			<tr>
				<td class='heading2' valign="top">Type of Behavior(s)</td>
				<td>
					<c:if test="${ fn:length(incident.bullyingBehaviorTypes) gt 0 }">
						<label class='referral-reason-category'>Bullying</label>
						<div class='referral-reason-box'>
							<span>Bullying behavior:</span>
							<ul>
								<c:forEach items="${incident.bullyingBehaviorTypes}" var="type" varStatus="status">
									<li>${ type.typeName }</li>
								</c:forEach>
							</ul>
							<c:if test="${ fn:length(incident.bullyingReasonTypes) gt 0 }">
								<span>Reason for bullying behavior:</span>
								<ul>
									<c:forEach items="${incident.bullyingReasonTypes}" var="type" varStatus="status">
										<li>
											${ type.typeName }
											<c:if test="${ not empty type.specified }">
												<div class='referral-reason-other-box'>
													${ type.specified }
												</div>
											</c:if>
										</li>
									</c:forEach>
								</ul>
							</c:if>
						</div>
					</c:if>
					
					<c:if test="${ fn:length(incident.illegalSubstanceTypes) gt 0 }">
						<label class='referral-reason-category'>Illegal Substance Use/Possession</label>
						<div class='referral-reason-box'>
							<ul>
								<c:forEach items="${incident.illegalSubstanceTypes }" var="type" varStatus="status">
									<li>
										${ type.typeName }
										<c:if test="${ not empty type.specified }">
											<div class='referral-reason-other-box'>
												${ type.specified }
											</div>
										</c:if>
									</li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
					
					<c:if test="${ fn:length(incident.sexualBehaviourTypes) gt 0 }">
						<label class='referral-reason-category'>Inappropriate Sexual Behaviour</label>
						<div class='referral-reason-box'>
							<ul>
								<c:forEach items="${incident.sexualBehaviourTypes }" var="type" varStatus="status">
									<li>
										${ type.typeName }
										<c:if test="${ not empty type.specified }">
											<div class='referral-reason-other-box'>
												${ type.specified }
											</div>
										</c:if>
									</li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
					
					<c:if test="${ fn:length(incident.threateningBehaviorTypes) gt 0 }">
						<label class='referral-reason-category'>Threat or Threatening Behavior</label>
						<div class='referral-reason-box'>
							<ul>
								<c:forEach items="${incident.threateningBehaviorTypes }" var="type" varStatus="status">
									<li>
										${ type.typeName }
										<c:if test="${ not empty type.specified }">
											<div class='referral-reason-other-box'>
												${ type.specified }
											</div>
										</c:if>
									</li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
					
					<c:if test="${ fn:length(incident.schoolSafetyIssueTypes) gt 0 }">
						<label class='referral-reason-category'>School Safety Issues</label>
						<div class='referral-reason-box'>
							<ul>
								<c:forEach items="${incident.schoolSafetyIssueTypes }" var="type" varStatus="status">
									<li>
										${ type.typeName }
										<c:if test="${ not empty type.specified }">
											<div class='referral-reason-other-box'>
												${ type.specified }
											</div>
										</c:if>
									</li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
					
				</td>
			</tr>
			
			<tr>
				<td class='heading2' valign="top">Action(s) Taken</td>
				<td>
					<ul>
						<c:forEach items="${incident.actionTypes}" var="type" varStatus="status">
							<li>
								${ type.typeName }
								<c:if test="${ not empty type.specified }">
									<div class='referral-reason-other-box'>
										${ type.specified }
									</div>
								</c:if>
							</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
			
		</table>
	</form>
</body>
</html>