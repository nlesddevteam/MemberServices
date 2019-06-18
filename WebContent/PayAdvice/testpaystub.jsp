<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/taglib/memberservices.tld" prefix="esd" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<html>

	<head>
	<meta http-equiv="pragma" content="no-cache">
		<title>NLESD - Teacher Pay Advice Manager</title>
		<link href="includes/css/payadvice.css" rel="stylesheet" type="text/css">

		<script type="text/javascript">
			$('document').ready(function(){
				$('tr.datalist:odd').css('background-color', '#E0E0E0');
			});
		</script>
	</head>

	<body>
			
		<table width="780" border="0" cellspacing="0" cellpadding="0" align="center" style="border: 3px solid Black; background-color: White;">
			<tr>
				<td colspan="2" valign="top" style="border:solid 1px #c4c4c4;">
					put image here
				</td>
			</tr>
			<tr>
				<td width="200" style="background-color:#e0e0e0;border-right:solid 1px #333333;padding-top:5px;" valign="top">
					<jsp:include page="admin_menu.jsp" flush="true" />
				</td>
				<td width="*" height="400" valign="top">
					<table width="100%" border="0" cellspacing="2" cellpadding="2" align="center" class="mainbody">
						<tr>
							<td>
								<table align="center" width="100%" cellspacing="2" cellpadding="2" border="0" class="headerbox">
									<tr><td class="headertitle">View Test Pay Stub</td></tr>
								</table>

								
								<br>&nbsp;<br>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr bgcolor="#000000">
				<td colspan="2"><div align="center" class="copyright">&copy; 2014 Newfoundland and Labrador English School District. All Rights Reserved.</div></td>
			</tr>
		</table>
	</body>
</html>
