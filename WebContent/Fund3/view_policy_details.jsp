<%@ page language ="java" 
         session = "true"
         import = "com.awsd.security.*"
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

	<body style="margin:20px;">
		    <form id="pol_cat_frm" action="updatePolicy.html" method="post" ENCTYPE="multipart/form-data">
      			<input type="hidden" id="op" name="op" value="CONFIRM">	
		<table width="1000px" border="0" cellspacing="0" cellpadding="0" align="center" style="border: 3px solid Black; background-color: White;">
			<tr>
			<td align="center">
			<jsp:include page="header.jsp" />
			<br />
			<br />
			</td>
			</tr>
			<tr align="center">
				<td>
				<br />
<br />
<br />

				<table width="500" style="background: white;border-radius:10px;-moz-border-radius:10px;-webkit-border-radius:10px;">
				<tr>
				<th colspan='2' align='center' class='fund3addscreentextnb'>View Policy Details<br /><input type="hidden" value="${policy.id}" id="id" name="id"></th>
				</tr>
				<tr>
				<td class="messageText" colspan='2' align='center'>
					<%if(request.getAttribute("msg")!=null){%>
					<%=(String)request.getAttribute("msg")%>
                 	<%} %>   
				</td>
				</tr>
				<tr>
				<td class='fund3formtext'>Link Text:</td>
				<td><input type='text' id='linktext' name='linktext' required value="${policy.linkText}"></td>
				</tr>
				<tr>
				<td class='fund3formtext'>Current File:</td>
				<td><a href='${policy.fileLink}'>Click To View File</a></td>				
				</tr>				
				<tr>
				<td class='fund3formtext'>File:</td>
				<td><input type='file' id='filename' name='filename'></td>
				</tr>
				<tr>
				<td class='fund3formtext'>Policy Status</td>
				<td>
					<select id="isactive" name="isactive"  class="inputtext">
						<c:choose>
    						<c:when test="${policy.isActive eq 1}">
       							<option value='1' SELECTED>YES</option>
								<option value='0'>NO</option>
    						</c:when>
    						<c:otherwise>
        						<option value='1'>YES</option>
								<option value='0' SELECTED>NO</option>
							</c:otherwise>
						</c:choose>						
					</select>
				</td>
				</tr>
				<tr>
				<td align="center" colspan='2'>
				<br />
				<input type="submit" value="Update Policy" class="btnExample">
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="Cancel onclick="javascript:window.location='index.jsp';" class="btnExample">
				<br />
				</td>				
				</table>
								<br />
<br />
<br />

				
				</td>
			</tr>
			<jsp:include page="fund3links.jsp" />

			<tr bgcolor="#000000">
				<td><div align="center" class="copyright">&copy; 2014-15 Newfoundland and Labrador English School District. All Rights Reserved.</div></td>
			</tr>
		</table>
		</form>
		<script>
		$("#pol_cat_frm").validate();
		</script>
	</body>
</html>