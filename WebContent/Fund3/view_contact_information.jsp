<%@ page language ="java" 
         session = "true"
         import = "java.util.*,
         					 com.esdnl.webupdatesystem.tenders.bean.*,com.esdnl.util.*"
         isThreadSafe="false"%><%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

	<head>
		<title>Fund 3 MANAGEMENT SYSTEM</title>
				<link href="includes/css/fund3.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" media="all" type="text/css" href="includes/css/bootstrap.min.css" />
		<link href="includes/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" media="all" type="text/css" href="includes/css/hover_drop_2.css" />
		 <link rel="stylesheet" href="includes/css/jquery-ui.css" />	
		<script type="text/javascript" src="includes/js/jquery-1.9.1.min.js"></script>
		<script src="includes/js/iefix.js" type="text/javascript"></script>
		<script src="includes/js/jquery.validate.js" type="text/javascript"></script>

	</head>

	<body>
			
		<table width="1000px" border="0" cellspacing="0" cellpadding="0" align="center" style="border: 3px solid Black; background-color: White;">
			<tr>
			<td align="center">
			<jsp:include page="header.jsp" />
			<br />
			<br />
			</td>
			</tr>
			<tr>

				<td height="400" valign="top" align="center">
					<table width="95%" border="0" cellspacing="2" cellpadding="2" align="center" class="mainbody">
						<tr>
							<td>

								<table align="center" width="90%" cellspacing="2" cellpadding="2" border="0" class="headerbox">
									<tr><td class="fund3addscreentextnb" align='center'>CONTACT INFORMATION</td></tr>
								</table>
							</td>
						</tr>
					</table>
										<br>
					<table align="center" cellspacing="0" style="font-size: 11px;" cellpadding="0" border="1">
									<c:choose>
	                                  	<c:when test='${fn:length(zones) gt 0}'>
                                  		<c:forEach items='${zones}' var='g'>
                                  			<tr>
                                  			<td class="displayText1" style="background-color:#87CEFA">${fn:toUpperCase(g.zoneName)}</td>
		                                      <td class="displayText1" >
		                                      <c:forEach items='${contacts}' var='c'>
		                                      	<c:choose>
		                                      		<c:when test='${g.zoneId eq c.contactZone.zoneId}'>
		                                      			<table border='1'width='100%' cellspacing="0" cellpadding="0">
		                                      				<tr><td  style="background-color:#87CEFA">${c.contactName}</td></tr>
		                                      				<tr><td>${c.contactTitle}</td></tr>
		                                      				<tr><td>${c.contactPhone}</td></tr>
		                                      				<tr><td>${c.contactEmail}</td></tr>
		                                      			</table>
		                                      		</c:when>
		                                      	</c:choose>
		                                      </c:forEach>
		                                      
		                                      </td>
											</tr>
                                  		</c:forEach>
                                  		
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='2'>Contact Information Not Available</td></tr>
										</c:otherwise>
									</c:choose>
									

					</table>
					<br>
			<jsp:include page="fund3links.jsp" />
			<tr bgcolor="#000000">
				<td colspan="1"><div align="center" class="copyright">&copy; 2014 Newfoundland and Labrador English School District. All Rights Reserved.</div></td>
			</tr>
		</table>
	</body>
</html>
