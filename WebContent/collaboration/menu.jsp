<%@ page contentType="text/html;charset=windows-1252"
         isThreadSafe="false"%>
         
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck permissions="COLLABORATION-ADMIN-VIEW,COLLABORATION-GROUP-VIEW" />

<table width="95%" cellspacing="2" cellpadding="2" border="0">
	<tr>
		<td valign="top">
			<!--- Menu Items / Sidebar stuff here -->
			&middot;&nbsp;<a href="index.html" class="sidemenu">Home</a><br>
			<esd:SecurityAccessRequired permissions="COLLABORATION-ADMIN-VIEW">
				&middot;&nbsp;<a href="addDiscussion.html" class="sidemenu">Add Discussion</a><br>
				&middot;&nbsp;<a href="allDiscussions.html" class="sidemenu">All Discussions</a><br>
			</esd:SecurityAccessRequired>
			
			<esd:SecurityAccessRequired permissions="COLLABORATION-GROUP-VIEW">
				&middot;&nbsp;<a href="groupDiscussions.html" class="sidemenu">All Discussions</a><br>
			</esd:SecurityAccessRequired>		
			<!-- End Sidebar -->
		</td>
	</tr>
</table>