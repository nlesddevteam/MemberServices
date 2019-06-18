<%@ page language ="java" 
         session = "true"
         import="com.esdnl.criticalissues.bean.*;"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/cidb.tld" prefix="cidb" %>

<%
	ReportBean report = (ReportBean) request.getAttribute("REPORT_BEAN");
%>

<html>
	<head>
		<title>Eastern School District - Critical Issues Database</title>
		<link href="../includes/css/cidb.css" rel="stylesheet" type="text/css">
		<script language="JavaScript" src="../includes/js/menu.js"></script>
		<script language="JavaScript" src="../includes/js/CalendarPopup.js"></script>
	</head>
	
	<body>
	
		<esd:SecurityCheck permissions="CIDB-ADMIN-VIEW" />
		
		<table width="780" border="0" cellspacing="0" cellpadding="0" align="center" bgcolor="#FFFFFF" style="border: thin solid #000000;">
			<tr bgcolor="#000000">
				<td>
					<table align="center" width="100%" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td><div align="left" class="toptext"></div></td>
							<td><div align="right" class="toptext"><a href="index.jsp" class="topmenu">Home</a>&nbsp;</div></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr valign="top">
				<td><img src="../includes/images/header.gif" alt="Critical Issues Database" width="780" height="97" border="0"></td>
			</tr>
			<tr valign="top">
				<td valign="top" style='height:400px;'>
					<table width="100%" border="0" cellspacing="2" cellpadding="2" align="center" class="maintable">
						<tr>
							<td style='padding-top:10px;padding-bottom:50px;'>
								<!-- Mainbody content here -->
		
								<table align="center" width="90%" cellspacing="2" cellpadding="2" border="0" class="headerbox">
									<tr><td class="header1">Add Report Action Item</td></tr>
								</table>
								<form id="frmAddActionItem" action="addReportActionItem.html" method="post">
									<input type="hidden" name="op" value="add" />
									<input type="hidden" name="report_id" value="<%=report.getReportId() %>"/>
									
									<table align="center" width="75%" cellspacing="2" cellpadding="2" border="0">
										<tr>
                    	<td colspan="2" align="center" valign="middle" class="requiredText"><span class="requiredStar">*&nbsp;</span> Required fields.</td>
                    </tr>
										<%if(request.getAttribute("msg")!=null){%>
                    	<tr class="messageText" style="padding-top:10px;padding-bottom:10px;">
                        <td colspan="2" align="center">
                        	<%=(String)request.getAttribute("msg")%>
                        </td>
                      </tr>
                    <%}%>
                    
                    <tr>
                    	<td class="label" align="right"><span class="whiteStar">*&nbsp;</span>Report:</td>
                    	<td class="displayText"><%=report.getReportType().getDescription()%></td>
                    </tr>
                    
                    <tr>
                    	<td class="label" align="right"><span class="whiteStar">*&nbsp;</span>Date:</td>
                    	<td class="displayText"><%=report.getReportDateFormatted()%></td>
                    </tr>
                    
                    <tr>
                    	<td class="label" align="right"><span class="whiteStar">*&nbsp;</span>School:</td>
                    	<td class="displayText"><%=report.getSchool().getSchoolName()%></td>
                    </tr>
                    
                    <tr>
                    	<td class="label" align="right" valign="top"><span class="requiredStar">*&nbsp;</span>Action Item:</td>
                    	<td>&nbsp;</td>
                    </tr>
                    <tr>
                    	<td colspan="2" align="center"><textarea id="action_item" name="action_item" style="height:150px;width:70%;" class="requiredInputBox"></textarea></td>
                    </tr>
                    
                    <tr>
                    	<td colspan="2" align="center"><a href="" onMouseOver="dsgo('b7')" onMouseOut="dsleave('b7')" onclick="document.forms[0].submit(); return false;"><img src="../includes/images/addai-off.jpg" alt="Add Action Report" name="b7" id="b7" width="75" height="68" border="0"></a></td>
                    </tr>
                                    
									</table>
								</form>
								<br>&nbsp;<br>
			
								<!--End Mainbody -->
								<br>&nbsp;<br>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr bgcolor="#000000">
				<td><div align="center" class="copyright">&copy; 2009 Eastern School District. All Rights Reserved.</div></td>
			</tr>
		</table>

	</body>
	
</html>
