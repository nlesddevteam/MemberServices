<%@ page contentType="text/html;charset=windows-1252"
         import="com.esdnl.util.*"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>


<esd:SecurityCheck permissions="COLLABORATION-ADMIN-VIEW" />

<html>
<head>
	<title>Eastern School District - Collaboration and Feedback on Policy</title>
	<link href="includes/css/sca.css" rel="stylesheet" type="text/css">
	<link href="includes/css/smoothness/jquery-ui-1.8.6.custom.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src='includes/js/jquery-1.4.4.min.js'></script>
	<script type="text/javascript" src='includes/js/jquery-ui-1.8.6.custom.min.js'></script>
	<script type="text/javascript">
		$('document').ready(function(){
				$('#disussion_date').datepicker({dateFormat: 'dd/mm/yy'});
		});
	</script>
</head>
<body>
	<table width="780" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#FFFFFF" style="border: thin solid #00407A;">
	<tr bgcolor="#000000">
		<td colspan="2">
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
		<td width="634" valign="top">
		<table width="95%" border="0" cellspacing="2" cellpadding="2" class="maintable">
	<tr><td width="100%">
		<form action="addDiscussion.html" method="POST">
			<input type="hidden" id="op" name="op" value='CONFIRM' />
			<!-- Mainbody content here -->
			
			<span class="header1">Add Discussion Topic</span><br>
			
			<table cellspacing="0" cellpadding="0" align="center">
				<tr><td colspan="2" align="center" class='label'><span style="color:#FF0000;">* Required fields.</span></td></tr>
				<tr style='padding-top:10px;'>
					<td width="90" class="label"><span style="color:#FF0000;">*</span>Date</td>
					<td width="*">
						<input class="requiredInputBox" type="text" id="disussion_date" name="discussion_date" />
					</td>
				</tr>
				<tr style='padding-top:10px;'>
					<td width="90" class="label"><span style="color:#FF0000;">*</span>Topic Title</td>
					<td width="*">
						<input class="requiredInputBox" type="text" id="disussion_title" name="discussion_title" style="width:250px;" />
					</td>
				</tr>
				<tr style='padding-top:10px;'><td colspan="2" class="label"><span style='color:#FFFFFF;'>*</span>Topic Description</td></tr>
				<tr>
					<td colspan="2" style="padding-left:10px;">
						<textarea id="discussion_description" name="discussion_description" style='width:375px;height:175px;'></textarea>
					</td>
				</tr>
				<tr><td colspan="2" align="center" style='padding-top:10px;'><input type="submit" value="Add Topic" /></td></tr>
			</table>
			
			<%if(!StringUtils.isEmpty((String)request.getAttribute("msg"))){%>
				<p align="center" style='color:#FF0000;'>
					<%=(String)request.getAttribute("msg")%>
				</p>
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
