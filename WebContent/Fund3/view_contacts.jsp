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
		<script>
			$( document ).ready(function() {
		    	if(!($('#hidmsg').val() == '')){
		    		$('#spansuccess').html($('#hidmsg').val());
		    		$('#divsuccess').show();
		    	}
			});
		</script>
		

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
				<td align="center">
					<table width="90%" border="0" cellspacing="2" cellpadding="2" align="center" class="mainbody">
						<tr>
							<td>

								<table align="center" width="100%" cellspacing="2" cellpadding="2" border="0" class="headerbox">
									<tr><td class="fund3addscreentextnb"  align="center">View Contacts</td></tr>
									<tr>
									<td class="messageText" align='center'>
									<%if(request.getAttribute("msg")!=null){%>
										<input type='hidden' id='hidmsg' name='hidmsg' value='<%=(String)request.getAttribute("msg")%>'>
                             		 <%}else{ %>
                             		 	<input type='hidden' id='hidmsg' name='hidmsg' value=''>
                             		 <%}%>   
									</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table align="center" cellspacing="1" style="font-size: 11px;" cellpadding="1" border="0" class='datatable'>
									<tr>
										<th class='fund3tableheader'>Name</th>
										<th class='fund3tableheader'>Title</th>
										<th class='fund3tableheader'>Phone</th>
										<th class='fund3tableheader'>Email</th>
										<th class='fund3tableheader'>Zone</th>
										<th class='fund3tableheader'>Status</th>
										<th  class='fund3tableheader' colspan='2'></th>
										
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(contacts) gt 0}'>
                                  		<c:forEach items='${contacts}' var='g'>
                                  			<tr>
                                  			<td class="displayTextMed" >${g.contactName}</td>
		                                      <td class="displayTextMed" >${g.contactTitle}</td>
		                                      <td class="displayTextMed" >${g.contactPhone}</td>
		                                      <td class="displayTextMed" >${g.contactEmail}</td>
		                                      <td class="displayTextMed" >${g.zoneNameCap}</td>
		                                      <td class="displayTextMed" >${g.isActiveString}</td>
		                                      <td class="displayTextMed">
		                                      <a href="viewContactDetails.html?id=${g.id}">EDIT</a>
		                                      </td>
		                                      <td class="displayTextMed">
		                                      	<a onclick="return confirm('Are you sure you want to DELETE this Contact?');" href='deleteContact.html?cid=${g.id}'>DELETE</a>
		                					</td>
		                                      </tr>
                                  		</c:forEach>
                                  		
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='8'  class='fund3tableheader'>No Contacts Found.</td></tr>
										</c:otherwise>
									</c:choose>
									<tr><td colspan="8"><hr size="1" /></td></tr>
							</table>
						</td>
					</tr>
					</table>
				</td>
				</tr>	
				<br />
			<jsp:include page="fund3links.jsp" />
			<tr bgcolor="#000000">
				<td colspan="1"><div align="center" class="copyright">&copy; 2014 Newfoundland and Labrador English School District. All Rights Reserved.</div></td>
			</tr>
		</table>
	</body>
</html>
