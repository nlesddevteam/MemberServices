<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
 <title><decorator:title default="Eastern School District Personnel Package 2.0" /></title>
	<decorator:head />
    <style type="text/css">@import 'includes/personnelmenu.css';</style>
 	<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="js/personnelmenu.js"></script> 
</head>
<body>
<table cellpadding="0" cellspacing="0" width ="955px" align="center" style="border: 1px solid #00407A;">
	<tr>
		<td>
			<jsp:include page="/Personnel/includes/jsp/admin_menu.jsp"/>
			<jsp:include page="/Personnel/includes/jsp/logo_header.jsp"/>
				
				<span class="ppbody">
						<decorator:body />
				</span>
		</td>
	</tr>
	<tr>
      <td width="100%" class="displayCopyright" > 
        Personnel Package 2.1. Copyright &copy; 2013 Eastern School District. All Rights Reserved.
      </td>
	</tr>
</table>
</body>
</html>