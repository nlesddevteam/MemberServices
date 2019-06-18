<?xml version="1.0" encoding="ISO-8859-1" ?>
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
		<meta name='menu' content='view-document-audit' />
		<title>Document Audit</title>
		
		<script type="text/javascript">
			$('document').ready(function() {
				
				$('table.data-list').each(function(idx){
					$(this).children().children(':even')
						.filter(':not(tfoot tr)')
						.filter(':not(thead tr)')
						.css('background-color', '#F0F0F0');
				});

				$('#view-document').click(function() {
					document.location.href = 'viewDocument.html?doc-id=${document.documentId}';
				});
				
			});
		</script>
	</head>
	
	<body>
		<div class='title'><span>Document Audit Trail</span></div>
		<br />
		<div>
			<table cellspacing='0' cellpadding='0' border='0' class='data-list'>
				<CAPTION>${document.documentTitle}</CAPTION>
				<thead>
					<tr><th width='25%'>Date</th><th width='25%'>Who</th><th width='25%'>Action</th><th width='25%'>Comment</th></tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(audits) > 0}">
							<c:forEach items="${audits}" var="audit">
								<tr>
									<td>${audit.auditDate}</td>
									<td>${audit.personnel.fullName}</td>
									<td>${audit.action}</td>
									<td>${audit.actionComment}</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class='user-message'><td colspan='4'>No document audit trail found.</td></tr>
						</c:otherwise>
					</c:choose>
				</tbody>
				<tfoot>
					<tr><td colspan='4'>&nbsp;</td></tr>
				</tfoot>
			</table>
		</div>
	</body>

</html>