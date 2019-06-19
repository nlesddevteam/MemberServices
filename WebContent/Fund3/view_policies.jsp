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
				<td align="center">
					<table width="95%" border="0" cellspacing="2" cellpadding="2" align="center" class="mainbody">
						<tr>
							<td align='center'>

								<table align="center" width="100%" cellspacing="2" cellpadding="2" border="0" class="headerbox">
									<tr><td class="fund3addscreentextnb" align="center">Policies & Forms</td></tr>
									<tr>
									<td class="messageText" align='center'>
									<%if(request.getAttribute("msg")!=null){%>
										<%=(String)request.getAttribute("msg")%>
                             		 <%} %>   
									</td>
									</tr>
									<tr>
									<td align='center'>
										<table cellspacing="1" style="font-size: 11px;" cellpadding="1" border="0" class='datatable' width="100%">
											<tr>
												<th  class='fund3tableheader'>Link Text</th>
												<th  class='fund3tableheader'>File</th>
												<th  class='fund3tableheader'>Status</th>
												<th colspan='2'  class='fund3tableheader'></th>
											</tr>
											<c:choose>
			                        			<c:when test='${fn:length(policies) gt 0}'>
			                               			<c:forEach items='${policies}' var='g'>
			                               				<tr>
			                               					<td class="displayTextMed" >${g.linkText}</td>
			                                     			<td class="displayTextMed" ><a href='${g.fileLink}' target='_blank'>View File</a></td>
															<td class="displayTextMed" >${g.isActiveString}</td>
			                                     			<td class="displayTextMed">
			                                     				<a href="viewPolicyDetails.html?id=${g.id}">EDIT</a>
			                                     			</td>
			                                     			<td class="displayTextMed">
			                                     				<a onclick="return confirm('Are you sure you want to DELETE this Policy?');" href='deletePolicy.html?pid=${g.id}'>Delete</a>
			               									</td>
			                                     		</tr>
			                               			</c:forEach>
			                            		</c:when>
												<c:otherwise>
													<tr><td colspan='5' class='fund3tableheader'>No Policies Found</td></tr>
												</c:otherwise>
											</c:choose>
											<tr><td colspan="5"><hr size="1" /></td></tr>
										</table>
									</td>
									</tr>
								</table>
							</td>
						</tr>
						</table>
				</td>
				</tr>
					<br>
			<jsp:include page="fund3links.jsp" />
			<tr bgcolor="#000000">
				<td colspan="1"><div align="center" class="copyright">&copy; 2014 Newfoundland and Labrador English School District. All Rights Reserved.</div></td>
			</tr>
		</table>
	</body>
</html>
