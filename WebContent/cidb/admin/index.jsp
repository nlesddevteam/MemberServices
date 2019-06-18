<%@ page language ="java" 
         session = "true"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<html>
	<head>
		<title>Eastern School District - Critical Issues Database</title>
		<link href="../includes/css/cidb.css" rel="stylesheet" type="text/css">
		<script language="JavaScript" src="../includes/js/menu.js"></script>
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
				<td valign="top">
					<table width="100%" border="0" cellspacing="2" cellpadding="2" align="center" class="maintable">
						<tr>
							<td style='padding-top:50px;padding-bottom:50px;'>
								<!-- Mainbody content here -->
		
								<table align="center" cellspacing="2" cellpadding="2" border="0" >
									<tr>
										<td><a href="viewReportsByType.html?id=2" onMouseOver="dsgo('b1')" onMouseOut="dsleave('b1')"><img src="../includes/images/env-off.jpg" alt="Environmental Health & Services Inspection report" name="b1" id="b1" width="120" height="109" border="0"></a></td>
										<td><a href="viewReportsByType.html?id=3" onMouseOver="dsgo('b2')" onMouseOut="dsleave('b2')"><img src="../includes/images/gov-off.jpg" alt="Government Services School Inspection" name="b2" id="b2" width="120" height="109" border="0"></a></td>
										<td><a href="viewReportsByType.html?id=4" onMouseOver="dsgo('b3')" onMouseOut="dsleave('b3')"><img src="../includes/images/day-off.jpg" alt="Daily School Fire Inspection" name="b3" id="b3" width="120" height="109" border="0"></a></td>
										<td><a href="viewReportsByType.html?id=1" onMouseOver="dsgo('b4')" onMouseOut="dsleave('b4')"><img src="../includes/images/fire-off.jpg" alt="Fire Alarm testing and Inspection" name="b4" id="b4" width="120" height="109" border="0"></a></td>
									</tr>
									<tr style="padding-top:25px;">
										<td colspan="2" align="right"><a href="import_report.jsp" onMouseOver="dsgo('b5')" onMouseOut="dsleave('b5')"><img src="../includes/images/imp-off.jpg" alt="Import Inspection Report (PDF Only)" name="b5" id="b5" width="120" height="109" border="0"></a></td>
										<td colspan="2" align="left"><a href="linkone.jsp" onMouseOver="dsgo('b6')" onMouseOut="dsleave('b6')"><img src="../includes/images/rep-off.jpg" alt="Reports" name="b6" id="b6" width="120" height="109" border="0"></a></td>
									</tr>
								</table>
								
								<%if(request.getAttribute("msg")!=null){%>
									<table align="center" cellspacing="2" cellpadding="2" border="0" >
	                	<tr class="messageText" style="padding-top:8px;padding-bottom:8px;">
	                  	<td colspan="3" align="center">
	                    	<%=(String)request.getAttribute("msg")%>
	                    </td>
	                  </tr>
	                </table>
	              <%}%>
			
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
