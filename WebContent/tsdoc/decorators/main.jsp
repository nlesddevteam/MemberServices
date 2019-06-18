<%@ page language="java" contentType="text/html" %>
<%@ taglib uri='http://java.sun.com/jstl/core_rt' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn' %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions='TSDOC-ADMIN-VIEW,TSDOC-TRUSTEE-VIEW' />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<html>
	<head>
		<title>Trustee Secure Documents Repository::<decorator:title default="Home" /></title>
		
		<style type="text/css">@import "<c:url value='/tsdoc/includes/css/tsdoc.css'/>";</style>
		<style type="text/css">@import "<c:url value='/tsdoc/includes/css/south-street/jquery-ui-1.8.4.custom.css'/>";</style> 
		<script type="text/javascript" src="<c:url value='/tsdoc/includes/js/jquery-1.4.2.min.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/tsdoc/includes/js/jquery-ui-1.8.4.custom.min.js'/>"></script>
		
		<script type="text/javascript">
			$('document').ready(function() {
				$('td.label').each(function(){
					$(this).html($(this).html() + ':');
				});
			});

			function popup(url, width, height) 
			{
			 var left   = (screen.width  - width)/2;
			 var top    = (screen.height - height)/2;
			 var params = 'width='+width+', height='+height;
			 params += ', top='+top+', left='+left;
			 params += ', directories=no';
			 params += ', location=no';
			 params += ', menubar=no';
			 params += ', resizable=no';
			 params += ', scrollbars=yes';
			 params += ', status=no';
			 params += ', toolbar=no';
			 newwin=window.open(url,'windowname5', params);
			 if (window.focus) {newwin.focus()}
			 return false;
			}
			
		</script>

		<decorator:head />
	</head>

	<body>	
		<table width="760" border="0" cellspacing="0" cellpadding="0" align='center' class='main'>
			<tr>
				<td class='header'>
					<!-- HEADER CONTENT START -->
					<div style='float: left;'><img src="<c:url value='/tsdoc/includes/images/securetrustee-main.png'/>" /></div>
					<div style='float: right;font-weight:bold;color:#15411D;'> Welcome ${usr.personnel.firstName}</div>
					<!-- HEADER CONTENT END -->
				</td>
			</tr>
			<tr>
				<td valign="top" class="maincontent">
					<!-- MAIN CONTENT START -->
					<table width='100%' cellspacing='0' cellpadding='0' border='0'>
						<esd:SecurityAccessRequired permissions='TSDOC-ADMIN-VIEW'>
							<tr>
								<td width='100%' align="center" class='menu'>
									<div id='admin-panel' >
										<div class='title'>Administration</div>
										<div class='menu'>
											<c:set var='menu'><decorator:getProperty property="meta.menu" /></c:set>
											
											<c:choose>
												<c:when test="${menu eq 'home'}">
													<a href="<c:url value='/tsdoc/'/>">
														<img src="<c:url value='/tsdoc/includes/images/home-off.png'/>" 
																 onmouseover="src='<c:url value='/tsdoc/includes/images/home-on.png'/>';"
														 		 onmouseout="src='<c:url value='/tsdoc/includes/images/home-off.png'/>';" />
													</a>
													
													<a href="<c:url value='/tsdoc/admin/viewCommittees.html'/>">
														<img src="<c:url value='/tsdoc/includes/images/committees-off.png'/>" 
																 onmouseover="src='<c:url value='/tsdoc/includes/images/committees-on.png'/>';"
														 		 onmouseout="src='<c:url value='/tsdoc/includes/images/committees-off.png'/>';" />
													</a>
													
													<a href="<c:url value='/tsdoc/admin/addNote.html'/>">
														<img src="<c:url value='/tsdoc/includes/images/addnote-off.png'/>" 
																 onmouseover="src='<c:url value='/tsdoc/includes/images/addnote-on.png'/>';"
														 		 onmouseout="src='<c:url value='/tsdoc/includes/images/addnote-off.png'/>';" />
													</a>
														 		 
													<a href="<c:url value='/tsdoc/admin/addDocument.html'/>">
														<img src="<c:url value='/tsdoc/includes/images/adddoc-off.png'/>" 
																 onmouseover="src='<c:url value='/tsdoc/includes/images/adddoc-on.png'/>';"
														 		 onmouseout="src='<c:url value='/tsdoc/includes/images/adddoc-off.png'/>';" />
													</a>
												</c:when>
												<c:when test="${menu ne 'home'}">
													<a href="<c:url value='/tsdoc/'/>">
														<img src="<c:url value='/tsdoc/includes/images/home-off.png'/>" 
																 onmouseover="src='<c:url value='/tsdoc/includes/images/home-on.png'/>';"
														 		 onmouseout="src='<c:url value='/tsdoc/includes/images/home-off.png'/>';" />
													</a>
													<c:choose>
														<c:when test="${menu eq 'committees'}">
															<a href="<c:url value='/tsdoc/admin/viewCommittees.html'/>">
																<img src="<c:url value='/tsdoc/includes/images/committees-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/committees-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/committees-off.png'/>';" />
															</a>
															
															<a href="<c:url value='/tsdoc/admin/addCommittee.html'/>">
																<img src="<c:url value='/tsdoc/includes/images/addcommittee-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/addcommittee-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/addcommittee-off.png'/>';" />
															</a>
															
															<a id='delete-committee' href="#">
																<img src="<c:url value='/tsdoc/includes/images/deletecommittee-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/deletecommittee-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/deletecommittee-off.png'/>';" />
															</a>
															
															<a id='committee-membership' href="#">
																<img src="<c:url value='/tsdoc/includes/images/committeemembership-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/committeemembership-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/committeemembership-off.png'/>';" />
															</a>
														</c:when>
														
														<c:when test="${menu eq 'add-committee'}">
															<a href="<c:url value='/tsdoc/admin/viewCommittees.html'/>">
																<img src="<c:url value='/tsdoc/includes/images/committees-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/committees-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/committees-off.png'/>';" />
															</a>
															
															<a href="<c:url value='/tsdoc/admin/addCommittee.html'/>">
																<img src="<c:url value='/tsdoc/includes/images/addcommittee-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/addcommittee-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/addcommittee-off.png'/>';" />
															</a>
														</c:when>
														
														<c:when test="${menu eq 'committee-membership'}">
															<a href="<c:url value='/tsdoc/admin/viewCommittees.html'/>">
																<img src="<c:url value='/tsdoc/includes/images/committees-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/committees-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/committees-off.png'/>';" />
															</a>
															
															<a id='add-committee-membership' href="#">
																<img src="<c:url value='/tsdoc/includes/images/addmember-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/addmember-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/addmember-off.png'/>';" />
															</a>
														</c:when>
														
														<c:when test="${menu eq 'add-committee-membership'}">
															<a href="<c:url value='/tsdoc/admin/viewCommittees.html'/>">
																<img src="<c:url value='/tsdoc/includes/images/committees-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/committees-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/committees-off.png'/>';" />
															</a>
															
															<a id='committee-membership' href="#">
																<img src="<c:url value='/tsdoc/includes/images/committeemembership-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/committeemembership-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/committeemembership-off.png'/>';" />
															</a>
															
															<a id='add-committee-membership' href="#">
																<img src="<c:url value='/tsdoc/includes/images/addmember-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/addmember-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/addmember-off.png'/>';" />
															</a>
														</c:when>
														
														<c:when test="${menu eq 'add-note'}">
															<a href="<c:url value='/tsdoc/admin/viewCommittees.html'/>">
																<img src="<c:url value='/tsdoc/includes/images/committees-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/committees-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/committees-off.png'/>';" />
															</a>
															
															<a href="<c:url value='/tsdoc/admin/addNote.html'/>">
																<img src="<c:url value='/tsdoc/includes/images/addnote-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/addnote-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/addnote-off.png'/>';" />
															</a>
																 		 
															<a href="<c:url value='/tsdoc/admin/addDocument.html'/>">
																<img src="<c:url value='/tsdoc/includes/images/adddoc-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/adddoc-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/adddoc-off.png'/>';" />
															</a>
														</c:when>
														
														<c:when test="${menu eq 'view-note'}">
															<a id='view-note-audit' href="#">
																<img src="<c:url value='/tsdoc/includes/images/auditnote-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/auditnote-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/auditnote-off.png'/>';" />
															</a>
														</c:when>
														
														<c:when test="${menu eq 'view-note-audit'}">
															<a id='view-note' href="#">
																<img src="<c:url value='/tsdoc/includes/images/viewnote-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/viewnote-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/viewnote-off.png'/>';" />
															</a>
														</c:when>
														
														<c:when test="${menu eq 'add-document'}">
															<a href="<c:url value='/tsdoc/admin/viewCommittees.html'/>">
																<img src="<c:url value='/tsdoc/includes/images/committees-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/committees-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/committees-off.png'/>';" />
															</a>
															
															<a href="<c:url value='/tsdoc/admin/addNote.html'/>">
																<img src="<c:url value='/tsdoc/includes/images/addnote-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/addnote-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/addnote-off.png'/>';" />
															</a>
																 		 
															<a href="<c:url value='/tsdoc/admin/addDocument.html'/>">
																<img src="<c:url value='/tsdoc/includes/images/adddoc-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/adddoc-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/adddoc-off.png'/>';" />
															</a>
														</c:when>
														
														<c:when test="${menu eq 'view-document'}">
															<a id='view-doc-audit' href="#">
																<img src="<c:url value='/tsdoc/includes/images/auditdoc-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/auditdoc-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/auditdoc-off.png'/>';" />
															</a>
														</c:when>
														
														<c:when test="${menu eq 'view-document-audit'}">
															<a id='view-document' href="#">
																<img src="<c:url value='/tsdoc/includes/images/viewdoc-off.png'/>" 
																		 onmouseover="src='<c:url value='/tsdoc/includes/images/viewdoc-on.png'/>';"
																 		 onmouseout="src='<c:url value='/tsdoc/includes/images/viewdoc-off.png'/>';" />
															</a>
														</c:when>
														
													</c:choose>
												</c:when>
											</c:choose>
										</div>
									</div>
								</td>
							</tr>
						</esd:SecurityAccessRequired>
						
						<esd:SecurityAccessRequired permissions='TSDOC-TRUSTEE-VIEW'>
							<tr>
								<td width='100%' align="center" class='menu'>
									<div id='admin-panel' >
										<div class='title'>Navigation</div>
										<div class='menu'>
											<c:set var='menu'><decorator:getProperty property="meta.menu" /></c:set>
											
											<c:choose>
												<c:when test="${menu eq 'home'}">
													<a href="<c:url value='/tsdoc/'/>">
														<img src="<c:url value='/tsdoc/includes/images/home-off.png'/>" 
																 onmouseover="src='<c:url value='/tsdoc/includes/images/home-on.png'/>';"
														 		 onmouseout="src='<c:url value='/tsdoc/includes/images/home-off.png'/>';" />
													</a>
												</c:when>
											</c:choose>
										</div>
									</div>
								</td>
							</tr>
						</esd:SecurityAccessRequired>
						
						<tr>
							<td class='content'>
								<decorator:body />
							</td>
						</tr>	
					</table>
					<!-- MAIN CONTENT END -->
				</td>
			</tr>
			<tr>
				<td class='footer'>
					<!-- FOOTER CONTENT START -->
					<div align="center">Secure Trustee Document Repository &copy;2010</div>
					<!-- FOOTER CONTENT END -->
				</td>
			</tr>
		</table>
		
	</body>
	
</html>

