<%@ page contentType="text/html;charset=windows-1252"
         import="java.text.*,
                 java.util.*,
                 com.esdnl.util.*,
                 com.esdnl.colaboration.bean.*"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
	DiscussionBean[] discussions = (DiscussionBean[]) request.getAttribute("DISCUSSIONBEANS");
%>

<esd:SecurityCheck permissions="COLLABORATION-ADMIN-VIEW" />

<html>
<head>
<title>Eastern School Disctrict - Collaboration and Feedback on Policy</title>
<link href="includes/css/sca.css" rel="stylesheet" type="text/css">
</head>
<body>
<table width="780" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#FFFFFF" style="border: thin solid #00407A;">
<tr bgcolor="#000000">
	<td colspan="2" >
	<table align="center" width="100%" cellspacing="2" cellpadding="2" border="0">
<tr>
	<td><div align="left" class="toptext">Welcome</div></td>
	<td><div align="right" class="toptext"><a href="index.html" class="topmenu">Home</a>&nbsp;</div></td>
</tr>
</table>
	</td>
</tr>
<tr>
	<td colspan="2"><img src="includes/images/header.gif" alt="Collaboration and Feedback on Policy" width="780" height="98" border="0"></td>
</tr>
<tr>
	<td width="146" background="includes/images/sidemenubkg.gif" valign="top">
		<jsp:include page="menu.jsp" flush="true"/>
	</td>
	<td width="634" valign="top" style="height:400px;">
	<table width="95%" border="0" cellspacing="2" cellpadding="2" class="maintable">
<tr><td>
	<!-- Mainbody content here -->
	<span class="header1">Discussion List</span><p>
	<%if(discussions.length > 0){ %>
		<ul>
		<%for(int i=0; i < discussions.length; i++){%>
			<li>
				<span class='discussion_title'><%=discussions[i]%></span><BR>
				<a class="addfeedback" href="viewSummary.html?id=<%=discussions[i].getId()%>">View Summary</a>
				&nbsp;|&nbsp;<a class="addfeedback" href="deleteDiscussion.html?id=<%=discussions[i].getId()%>">Delete</a>
				<%if(!discussions[i].isReleased()){ %>
					&nbsp;|&nbsp;<a class="addfeedback" href="releaseDiscussion.html?id=<%=discussions[i].getId()%>">Release</a>
				<%}else{%>
					&nbsp;|&nbsp;<span class="released">RELEASED</span>
				<%}%>
				<%if(!StringUtils.isEmpty(discussions[i].getDescription())){%>
					<br /><span class="normal"><%=discussions[i].getDescription()%></span>
				<%}%>
			</li>
		<%}%>
		</ul>
	<%}else{%>
		None available today.
	<%}%>
	<!--End Mainbody -->
		<br>&nbsp;<br>
	</td></tr></table>
	</td></tr>
<tr bgcolor="#000000">
	<td colspan="2"><div align="center" class="copyright">&copy; 2008 Eastern School District. All Rights Reserved.</div></td>
</tr>
</table>



</body>
</html>
