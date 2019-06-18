<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri='http://java.sun.com/jstl/core_rt' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="TSDOC-ADMIN-VIEW" />

<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<meta name="menu" content="committee-membership" />
		<title>Committee Membership</title>
		
		<script type="text/javascript">
			$('document').ready(function() {

				$('table.data-list tr:even').css('background-color', '#F0F0F0');
				
				$('#add-committee-membership').click(function() {
					document.location.href = 'addCommitteeMembership.html?committee-id=${committee.groupId}';
				});

				$('a.delete').click(function() {
					return confirm('Are you should you want to remove this member?');
				});
				
			});
		</script>
	</head>
	
	<body>
		<div class='title'><span>Committee Membership</span></div>
		<br />
		<table width='100%' cellspacing='0' cellpadding='0' border='0'>
			<c:if test="${not empty msg}">
				<tr class='user-message'>
					<td>
						<c:out value="${msg}" />
					</td>
				</tr>
			</c:if>
			<tr>
				<td>
					<fieldset class='committee-name'>
						<legend><span>${committee.groupName}</span></legend>
						<table cellspacing='0' cellpadding='0' border='0' class='data-list'>
							<c:choose>
								<c:when test="${fn:length(committee.membership) > 0}">
									<c:forEach items='${committee.membership}' var='member'>
										<tr>
											<td>${member.lastName}, ${member.firstName}</td>
											<td class='options'>
												<a class='delete' href="removeCommitteeMembership.html?committee-id=${committee.groupId}&member-id=${member.personnelID}">remove</a>
											</td>
										</tr>				
									</c:forEach>					
								</c:when>
								<c:otherwise>
									<tr class='user-message'><td>Group has no membership.</td></tr>	
								</c:otherwise>
							</c:choose>
						</table>
					</fieldset>	
				</td>
			</tr>
		</table>
	</body>
	
</html>