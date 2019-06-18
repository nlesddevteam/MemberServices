<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
		<title>Home</title>
		
		<script type="text/javascript">
			$('document').ready(function() {
				
				$('table.data-list').each(function(idx){
					$(this).children().children(':even')
						.filter(':not(tfoot tr)')
						.filter(':not(thead tr)')
						.css('background-color', '#F0F0F0');
				});
				
			});

			function readnote(id) {
				//popup('admin/viewNote.html?note-id='+id, 600, 600);
				document.location.href = 'trustee/viewNote.html?note-id='+id;
			}

			function readdocument(id) {
				document.location.href = 'trustee/viewDocument.html?doc-id='+id;
			}
		</script>
	</head>
	
	<body>
		<div class='title'><span>Unread Notes</span></div>
		<div>
			<table cellspacing='0' cellpadding='0' border='0' class='data-list'>
				<thead>
					<tr><th width='34%'>Title</th><th width='34%'>Date Added</th><th width='*'>&nbsp;</th></tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(notes) > 0}">
							<c:forEach items="${notes}" var="note">
								<tr class='note'>
									<td>${note.noteTitle}</td>
									<td>${note.dateAdded}</td>
									<td class='options'><a href='javascript:readnote(${note.noteId})'>read note</a></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class='user-message'><td colspan='3'>No unread notes.</td></tr>
						</c:otherwise>
					</c:choose>
				</tbody>
				<tfoot>
					<tr><td class='options' colspan='3' align='right'><a href='trustee/viewAllNotes.html'>View All Notes</a></td></tr>
				</tfoot>
			</table>
		</div>
		<br />
		<div class='title'><span>Unread Documents</span></div>
		<div>
			<table cellspacing='0' cellpadding='0' border='0' class='data-list'>
				<thead>
					<tr><th width='34%'>Title</th><th width='34%'>Date Added</th><th width='*'>&nbsp;</th></tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(docs) > 0}">
							<c:forEach items="${docs}" var="doc">
								<tr class='doc'>
									<td>${doc.documentTitle}</td>
									<td>${doc.dateAdded}</td>
									<td class='options'><a href='javascript:readdocument(${doc.documentId})'>read document</a></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class='user-message'><td colspan='3'>No unread documents.</td></tr>
						</c:otherwise>
					</c:choose>
				</tbody>
				<tfoot>
					<tr><td class='options' colspan='3' align='right'><a href='trustee/viewAllDocuments.html'>View All Documents</a></td></tr>
				</tfoot>
			</table>
		</div>
	</body>

</html>