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
		<meta name="menu" content="view-document" />
		<title>View Document</title>
		
		<script type="text/javascript">
			$('document').ready(function() {

				$('#view-doc-audit').click(function() {
					document.location.href = 'viewDocAudit.html?doc-id=${doc.documentId}';
				});

				$(window).unload(function() {
					$.post("viewDocument.html", { op : 'clean-up', 'doc-id' : '${doc.documentId}', tmpdir : '${tmpdir}' });
				});

				/*
				 $(this).bind("contextmenu", function(e) {
					 e.preventDefault();
					 });
				*/
				
				var isiPad = navigator.userAgent.match(/iPad/i) != null;
				//alert(navigator.userAgent);
				if(!isiPad){
						$('#document-container').append(
								$('<iframe>')
									.attr({
										'src'   : '/MemberServices/tsdoc/${tmpdir}/${doc.filename}#toolbar=0&navpane=0&messages=0',
										'width'  : '100%',
										'height' : '100%',
										'frameborder' : '0'
									})
						);
				}
				else {
					$('#document-container')
						.attr({'height' : '200px'})
						.append(
							$('<a>')
								.attr({
									'href'   : '/MemberServices/tsdoc/${tmpdir}/${doc.filename}#toolbar=0&navpane=0&messages=0',
									'target' : '_blank'
								})
								.text('Click here to view \"${doc.documentTitle}\"!' )
					);
				}
			});
		</script>
	</head>
	
	<body>
		<div class='title'><span>View Document</span></div>
		<br />
		<table width='100%' cellspacing='0' cellpadding='0' border='0'>
			<c:if test="${not empty msg}">
				<tr>
					<td class='user-message'>
						<c:out value="${msg}" />
					</td>
				</tr>
			</c:if>
			<tr>
				<td>
					<table cellspacing='0' cellpadding='0' border='0' class='data-list'>
						<CAPTION>${doc.documentTitle}</CAPTION>
						<THEAD>
							<tr>
								<th>Added on ${doc.dateAdded}</th>
							</tr>
						</THEAD>
						<TBODY>
							<tr class='document-view'>
								<td id='document-container' valign='top'>									
									
								</td>
							</tr>
						</TBODY>
						<TFOOT>
							<tr><td>&nbsp;</td></tr>
						</TFOOT>				
					</table>
				</td>
			</tr>
		</table>
	</body>
	
</html>