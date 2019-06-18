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
		<meta name="menu" content="committees" />
		<title>Committees</title>
		
		<script type="text/javascript">
			$('document').ready(function(){
				
				$('table.data-list').each(function(idx){
					$(this).children().children(':even').css('background-color', '#F0F0F0');
				});

				$('#delete-committee').click(function() {

					var id = $('input[name="committee-id"]:radio:checked').val();
					
					if(id) {
						if(confirm("Are you sure you want to delete?"))
							document.location.href = 'deleteCommittee.html?id=' + id;
					}
					else
						alert('Please select a committee to delete.');
				});

				$('#committee-membership').click(function() {

					var id = $('input[name="committee-id"]:radio:checked').val();
					
					if(id)
						document.location.href = 'viewCommitteeMembership.html?id=' + id;
					else
						alert('Please select a committee to view membership.');
				});
				
			});
		</script>
	</head>
	
	<body>
		<div class='title'><span>Committees</span></div>
		<br />
		<table width='100%' cellspacing='0' cellpadding='0' border='0'>
			<c:if test="${not empty msg}">
				<tr>
					<td class='user-message'>
						<c:out value="${msg}" />
					</td>
				</tr>
			</c:if>
			<c:forEach items='${committees}' var='committee'>
				<tr>
					<td>
						<fieldset class='committee-name'>
							<legend>
								<table cellpadding='0' cellspacing='0' border='0'>
									<tr>
										<td><input type='radio' id='committee-id' name='committee-id' value='${committee.groupId}' /></td>
										<td>${committee.groupName}</td>
									</tr>
								</table>
							</legend>
							<table cellspacing='0' cellpadding='0' border='0' class='data-list'>
								<c:choose>
									<c:when test="${fn:length(committee.membership) > 0}">
										<c:forEach items='${committee.membership}' var='member'>
											<tr><td>${member.lastName}, ${member.firstName}</td></tr>				
										</c:forEach>					
									</c:when>
									<c:otherwise>
										<tr class='user-message'><td style='text-align:left;'>Group has no membership.</td></tr>	
									</c:otherwise>
								</c:choose>
							</table>
						</fieldset>	
					</td>
				</tr>
			</c:forEach>
		</table>
	</body>
	
</html>