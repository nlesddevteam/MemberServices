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
		<meta name='menu' content='home' />
		<title>Home</title>
		
		<style>
			table.main td.content form {
				font-family: Verdana, sans-serif;
			  font-size: 11px;
			  /*background-color: #d6e5f4;*/
			  background: none;
			  padding: 0px;
				width:100%;
				border: none;
			}
			
			table.main td.content form td {
				text-align:left;
				padding: 0px;	
			}
		</style>
		
		<script type="text/javascript">
			$('document').ready(function() {
				
				$('table.data-list').each(function(idx){
					$(this).children().children(':even')
						.filter(':not(tfoot tr)')
						.filter(':not(thead tr)')
						.css('background-color', '#F0F0F0');
				});

				$('#chkSelectAllNotes').click(function() {
						$('.chknote').attr('checked', $('#chkSelectAllNotes').attr('checked'));
				});

				$('#chkSelectAllDocs').click(function() {
					$('.chkdoc').attr('checked', $('#chkSelectAllDocs').attr('checked'));
			});
				
			});

			function readnote(id) {
				//popup('admin/viewNote.html?note-id='+id, 600, 600);
				document.location.href = 'admin/viewNote.html?note-id='+id;
			}

			function readdocument(id) {
				document.location.href = 'admin/viewDocument.html?doc-id='+id;
			}
		</script>
	</head>
	
	<body>
		<form action='<c:url value="/tsdoc/admin/markAsRead.html"/>'>
		<div class='title'><span>Unread Notes</span></div>
		<div>
			<table cellspacing='0' cellpadding='0' border='0' class='data-list'>
				<thead>
					<tr>
						<th width='55%' valign='middle' style='padding:0px;padding-left:5px;'>
							<table cellpadding='0' cellspacing='0'><tr>
								<c:if test='${usr.admin eq true}'>
									<td><input type='checkbox' id='chkSelectAllNotes' /></td>
								</c:if>
								<td>Title</td></tr></table>
						</th>
					<th width='30%'>Date Added</th><th width='*'>&nbsp;</th></tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(notes) > 0}">
							<c:forEach items="${notes}" var="note">
								<tr class='note'>
									<td>
										<table cellpadding='0' cellspacing='0'>
											<tr>
												<c:if test='${usr.admin eq true}'>
													<td><input class='chknote' type="checkbox" name='note-id' value='${note.noteId}' /></td>
												</c:if>
												<td>${note.noteTitle}</td>
											</tr>
										</table>
									</td>
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
					<tr>
						<td class='options' colspan='3' align='right'>
							<c:if test="${usr.admin}"><a href='#' onclick="document.forms[0].submit();">Mark As Read</a>&nbsp;|&nbsp;</c:if>
							<a href='<c:url value="/tsdoc/admin/viewAllNotes.html"/>'>View All Notes</a>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
		<br />
		<div class='title'><span>Unread Documents</span></div>
		<div>
			<table cellspacing='0' cellpadding='0' border='0' class='data-list'>
				<thead>
					<tr><th width='55%' style='padding:0px;padding-left:5px;'><table cellpadding='0' cellspacing='0'><tr>
					<c:if test='${usr.admin eq true}'>
						<td><input type='checkbox' id='chkSelectAllDocs' /></td>
					</c:if>
					<td>Title</td></tr></table></th><th width='30%'>Date Added</th><th width='*'>&nbsp;</th></tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(docs) > 0}">
							<c:forEach items="${docs}" var="doc">
								<tr class='doc'>
									<td>
										<table cellpadding='0' cellspacing='0'>
											<tr>
												<c:if test='${usr.admin eq true}'>
													<td>
														<input class='chkdoc' type="checkbox" name='document-id' value='${doc.documentId}' />
													</td>
												</c:if>
												<td>${doc.documentTitle}</td>
											</tr>
										</table>
									</td>
									<td>${doc.dateAdded}</td>
									<td class='options'>
										<a href='javascript:readdocument(${doc.documentId})'>read document</a>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class='user-message'><td colspan='3'>No unread documents.</td></tr>
						</c:otherwise>
					</c:choose>
				</tbody>
				<tfoot>
					<tr>
						<td class='options' colspan='3' align='right'>
							<c:if test="${usr.admin}"><a href='#' onclick="document.forms[0].submit();">Mark As Read</a>&nbsp;|&nbsp;</c:if>
							<a href='<c:url value="/tsdoc/admin/viewAllDocuments.html"/>'>View All Documents</a>
						</td>
					</tr>
				</tfoot>
			</table>
		</div>
		</form>
	</body>

</html>