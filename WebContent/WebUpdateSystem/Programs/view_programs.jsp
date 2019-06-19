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
		<title>Web Update System - Policies</title>

	</head>

	<body>
			
		<table width="980" border="0" cellspacing="0" cellpadding="0" align="center" style="border: 3px solid Black; background-color: White;">

			<tr>

				<td width="*" height="400" valign="top">
					<table width="100%" border="0" cellspacing="2" cellpadding="2" align="center" class="mainbody">
						<tr>
							<td>
								<table align="center" width="100%" cellspacing="2" cellpadding="2" border="0" class="headerbox">
									<tr><td class="headertitle">View</td></tr>
									<tr><td class="messageText">
									<%if(request.getAttribute("msg")!=null){%>
										<%=(String)request.getAttribute("msg")%>
                             		 <%} %>   
									</td></tr>
								</table>

							</td>
						</tr>
					</table>
					<br>
					<table align="center" width="100%" cellspacing="1" style="font-size: 11px;" cellpadding="1" border="0">

									<tr>
										<th class="cent">Descriptor Title</th>
										<th class="cent">Program Region</th>
										<th  class="cent">Program Level</th>
										<th  class="cent">Program Status</th>
										<th  class="cent">Added By</th>
										<th  class="cent">Date Added</th>
										<th  class="cent"></th>
										<th  class="cent"></th>
										<th  class="cent"></th>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(programs) gt 0}'>
                                  		<c:forEach items='${programs}' var='g'>
                                  			<tr class='guide-info'>
                                  				<td class="displayText1" >${g.descriptorTitle}</td>
                                  			  <td class="displayText1" >${g.pRegion.description}</td>
		                                      <td class="displayText1" >${g.pLevel.description}</td>
		                                      <td class="displayText1" >${g.programStatus}</td>
		                                      <td class="displayText1" >${g.addedBy}</td>
		                                      <td class="displayText1" >${g.dateAddedFormatted}</td>
		                                      <td class="displayText1" ><a href="addNewProgram.html">Add</a> | </td>
		                                      <td class="displayText1" ><a href="viewProgramDetails.html?id=${g.id}" target="_blank"> Edit </a> | </td>
		                                      <td class="displayText1" >
		                                      <a class="small" onclick="return confirm('Are you sure you want to DELETE this Program?');" href='deleteProgram.html?pid=${g.id}'>Delete</a>
		                
		                                      </td>
		                                      
		                                      
		                                      </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='12'>No Programs Found.</td></tr>
										</c:otherwise>
									</c:choose>
									<tr><td colspan="12"><hr size="1" /><br /><br /><br /></td></tr>

</table>
			<tr bgcolor="#000000">
				<td colspan="1"><div align="center" class="copyright">&copy; 2014 Newfoundland and Labrador English School District. All Rights Reserved.</div></td>
			</tr>
		</table><br/><a href='javascript:history.go(-1)' title="Back">Back</a><br/>
	</body>
</html>
