<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.awsd.common.*, java.util.*;" %>
<%@ taglib uri='http://java.sun.com/jstl/core_rt' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="TSDOC-TRUSTEE-VIEW" />

<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
		<meta name='menu' content='home' />
		<title>All Documents</title>
		
		<script type="text/javascript">
			$('document').ready(function() {
				
				$('table.data-list').each(function(idx){
					$(this).children().children(':even')
						.filter(':not(tfoot tr)')
						.filter(':not(thead tr)')
						.css('background-color', '#F0F0F0');
				});

				$('.myaccordion').accordion({ collapsible: true, autoHeight: false, active: false });
				
			});

			function readdocument(id) {
				document.location.href = 'viewDocument.html?doc-id='+id;
			}
		</script>
		<style type="text/css">@import "<c:url value='/tsdoc/includes/css/custom-theme/jquery-ui-1.8.6.custom.css'/>";</style>
	</head>
	
	<body>
		<div class='title'><span>All Documents</span></div>
		<div class='myaccordion' style='padding-top:10px;'>
			<c:choose>
				<c:when test="${fn:length(years) > 0}">
					<c:forEach items="${years}"  var="y">
							<h3 style='padding-left:25px;'>${y.key}</h3>
							<div id='${y.key}'>
								<div class='myaccordion'>
									<c:forEach items="${y.value}" var="m">
										<h4 style='padding-left:25px;'><%=Utils.getMonthString(((Integer)((Map.Entry)pageContext.getAttribute("m", PageContext.PAGE_SCOPE)).getKey()).intValue())%></h4>
										<div style='padding:0px;'>
											<table cellspacing='0' cellpadding='0' border='0' class='data-list'>
												<caption></caption>
												<thead>
													<tr><th width='34%'>Title</th><th width='34%'>Date Added</th><th width='*'>&nbsp;</th></tr>
												</thead>
												<tbody>
													<c:forEach items="${m.value}" var="doc">
														<tr class='note'>
															<td>${doc.documentTitle}</td>
															<td>${doc.dateAdded}</td>
															<td class='options'><a href='javascript:readdocument(${doc.documentId})'>read document</a></td>
														</tr>
													</c:forEach>
												</tbody>
												<tfoot>
													<tr><td colspan='3'>&nbsp;</td></tr>
												</tfoot>
											</table>
										</div>
									</c:forEach>
								</div>		
							</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<table cellspacing='0' cellpadding='0' border='0' class='data-list'>
						<tr class='user-message'><td colspan='3'>No documents found.</td></tr>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
		
	</body>

</html>