<%@ page contentType="text/html;charset=windows-1252"
         import="com.esdnl.colaboration.bean.*"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<%
	DiscussionGroupBean group = (DiscussionGroupBean) request.getAttribute("DISCUSSIONGROUPBEAN");
%>

<esd:SecurityCheck permissions="COLLABORATION-GROUP-VIEW" />

<html>
<head>
<title>Eastern School District - Collaboration and Feedback on Policy</title>
<link href="includes/css/sca.css" rel="stylesheet" type="text/css">

<script type="text/javascript">
	function deleteComment(comment_id)
	{
		document.forms[0].action = "deleteComment.html?comment_id="+comment_id;
		document.forms[0].submit();	
	}
</script>
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
	<form action="addComment.html" method="POST">
		<input type="hidden" id="op" name="op" value='ADD' />
		<input type="hidden" id="id" name="id" value="<%=group.getDiscussion().getId()%>" />
		<%if(group.getId() > 0){%>
			<input type="hidden" id="group_id" name="group_id" value="<%=group.getId()%>" />
		<%}%>
		<!-- Mainbody content here -->
		<span class="header1">Topic Feedback</span><br />
		
			<table cellspacing="0" cellpadding="0" align="center">
				<tr style='padding-top:10px;'><td colspan="2" class="header3" align='center'><%=group.getDiscussion().getTitle() %></td></tr>
				<tr style='padding-top:10px;'>
					<%if(group.getId() > 0){%>
						<td colspan="2"><span class='label' style="color:#FF0000;">*</span><span class='label'>Group Name:</span>&nbsp;
						<span class='normal'><%= group.getGroupName() %></span>
						</td>
					<%}else{%>
						<td  class="label"><span style="color:#FF0000;">*</span>Group Name:</td>
						<td  align="left" class ="normal">						
								<input class="requiredInputBox" type="text" id="group_name" name="group_name" style="width:250px;" />
						</td>
					<%}%>
				</tr>
				<tr style='padding-top:10px;'><td colspan="2" class="label"><span style='color:#FF0000;'>*</span> Comment:</td></tr>
				<tr>
					<td colspan="2" style="padding-left:10px;">
						<textarea id="comment" name="comment" style='width:375px;height:175px;'></textarea>
					</td>
				</tr>
				<tr><td colspan="2" align="center" style='padding-top:10px;'><input type="submit" value="Add Comment" /></td></tr>
			</table>
			
			<br /> <br />
			<span class="header2">Previous Comments</span><br />
			<%if((group.getComments() != null) && (group.getComments().length > 0)){
				GroupCommentBean[] comments = group.getComments();
			%>
			<ul>
			<%for(int i=0; i < comments.length; i++){%>
				<li>
					<span class='normal'><%=comments[i].getComment()%></span>
					&nbsp;<a href="javascript:deleteComment(<%=comments[i].getId()%>);">Delete</a>					
				</li>
			<%}%>
			</ul>
		<%}else{%>
			No comments added.
		<%}%>
		<!--End Mainbody -->
		</form>
		<br>&nbsp;<br>
	</td></tr></table>
	</td></tr>
<tr bgcolor="#000000">
	<td colspan="2"><div align="center" class="copyright">&copy; 2008 Eastern School District. All Rights Reserved.</div></td>
</tr>
</table>



</body>
</html>
