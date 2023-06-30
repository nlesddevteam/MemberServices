<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
         com.esdnl.payadvice.bean.*,com.esdnl.util.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>

                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<esd:SecurityCheck permissions="PAY-ADVICE-ADMIN" />
<%!
  User usr = null; 
%>
<%
  User usr = (User) session.getAttribute("usr");
%>
<html>

	<head>
		<title>NLESD - Teacher Pay Advice Manager</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
   	
	</head>

	<body>
	<div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
<jsp:include page="menu.jsp" />
<div class="siteHeaderGreen">View Processing Errors</div>
	
	
								<table class="payrollPeriodsTableDetails table table-sm table-striped">
									<tr>
										<td class='label' width="15%">Pay Begin Date:<input type='hidden' id='pgid' value='${pgbean.id}'></td>
										<td class='ppresult'>${pgbean.payBgDt}</td>
										<td class='label' width="15%">Pay End Date:</td>
										<td class='ppresult'>${pgbean.payEndDt}</td>
										<td class='label' width="15%">Check Date:</td>
										<td class='ppresult'>${pgbean.checkDt}</td>
									</tr>
									<tr>

										
										<td class='label' width="15%">Check Number:</td>
										<td class='ppresult'>${pgbean.checkNum}</td>
										<td class='label' width="15%">Pay Group:</td>
										<td colspan='3' class='ppresult'>${pgbean.payGp}</td>
										</tr>
									<tr>
										<td class='label'>Emails Attempted:</td><td class='ppresult'><span id='emailssent'>${ppbean.totalPayStubs}</span></td>
										<td class='label'>Emails Sent:</td><td class='ppresult' style="color:Green;"><span id='emailssuccess'>${ppbean.totalPayStubsSent}</span></td>
										<td class='label'>Emails Failed:</td><td class='ppresult' style="color:Red;"><span id='emailsfailed'>${ppbean.totalPayStubsNotSent}</span></td>
									</tr>
									<tr>
										<td class='ppresult' colspan='6'>
										<div align="center">
										<a class="btn btn-sm btn-info" href='exportProcessErrors.html?payid=${ppbean.payGroupId}' target='_blank'>Export Process Errors To CSV</a>
										</div>
										</td>
									</tr>									
								</table>
	
	<br/>
	
					<table class="payrollProcessTable table table-sm table-bordered">
							<thead class="thead-dark">
									<tr>
										<th class="employee">EMPLOYEE NAME</th>
										<th class="school">SCHOOL / LOCATION</th>
										<th class="error">ERROR</th>
									</tr>
							</thead>
							<tbody>		
									<c:choose>
	                                  	<c:when test='${fn:length(paygroups) gt 0}'>
                                  		<c:forEach items='${paygroups}' var='g'>
                                  				<tr>
                                  				<td>${g.value.empName}</td>
		                                      	<td>${g.value.locnCode}</td>
		                                      	<td>${g.value.error}</td>
		                                      	</tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='3' style="text-align:center;">No Processing Errors.</td></tr>
										</c:otherwise>
									</c:choose>
							</tbody>		
								</table>
					

  </div></div></div>  
  </body>

</html>									
			