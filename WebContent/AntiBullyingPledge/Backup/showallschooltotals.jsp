<%@ page language ="java" 
         session = "true"
         import = "java.util.*,java.text.DateFormat,
         					 com.nlesd.antibullyingpledge.bean.*,
         					 com.nlesd.antibullyingpledge.dao.*,com.esdnl.util.*"
         isThreadSafe="false"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/taglib/memberservices.tld" prefix="esd" %>
<%
	List<AntiBullyingPledgeSchoolTotalsBean> pledges = AntiBullyingPledgeManager.getAllSchoolTotals();
	Integer totalpledges = AntiBullyingPledgeManager.getTotalPledges();
	Integer totalpledgesconfirmed = AntiBullyingPledgeManager.getTotalPledgesConfirmed();
%>
<c:set var='pledges' value='<%=pledges%>' />
<c:set var='testvar' value='1' />
<c:set var='totalpledges' value='<%=totalpledges%>' />
<c:set var='totalpledgesconfirmed' value='<%=totalpledgesconfirmed%>' />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>NLESD - Bullying Pledge Admin</title>
		<link href="css/pledges.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<table align="center" width="100%" cellspacing="1" style="font-size: 11px;" cellpadding="1" border="0" id="showpledges">
			<tr><td><h1>Total Pledges:${totalpledges} Total Pledges Confirmed: ${totalpledgesconfirmed}</h1></td></tr>

						<c:forEach items="${pledges}" var="pledge" varStatus="loopCounter">
               							<c:choose>   
                   							<c:when test="${testvar == 1}">   
                         								<tr>
       												<td>
       												<table>
       																									<tr><td align="center"><h1>${pledge.schoolName}</h1></td></tr>
								<tr><td align="center"><img src='includes/img/${pledge.schoolPicture}.jpg' width="82" height="82" onError="this.onerror=null;this.src='includes/img/default.jpg';"></td></tr>
								<tr><td align="center"><h1>${pledge.totalPledges}</h1></td></tr>
       												</td>
       												</tr>
       												</table>
       												</td>
                         								 <c:set var="testvar"  value="${testvar + 1}"/>
                   									</c:when>
                   							<c:when test="${testvar > 1 && testvar < 5}">    
                   									<td>
       												<table>
       																									<tr><td align="center"><h1>${pledge.schoolName}</h1></td></tr>
								<tr><td align="center"><img src='includes/img/${pledge.schoolPicture}.jpg' width="82" height="82" onError="this.onerror=null;this.src='includes/img/default.jpg';"></td></tr>
								<tr><td align="center"><h1>${pledge.totalPledges}</h1></td></tr>
       												</td>
       												</tr>
       												</table>
       												</td>
                   									<c:set var="testvar"  value="${testvar + 1}"/> 
                   							</c:when>
                   							<c:otherwise>
                   									<td>
       												<table>
       																									<tr><td align="center"><h1>${pledge.schoolName}</h1></td></tr>
								<tr><td align="center"><img src='includes/img/${pledge.schoolPicture}.jpg' width="82" height="82" onError="this.onerror=null;this.src='includes/img/default.jpg';"></td></tr>
								<tr><td align="center"><h1>${pledge.totalPledges}</h1></td></tr>
       												</td>
       												</tr>
       												</table>
       												</td></tr>
                   									<c:set var="testvar"  value="1"/>
                   									</c:otherwise>   
               								</c:choose> 
						</c:forEach>
				</table>
		</body>
</html>