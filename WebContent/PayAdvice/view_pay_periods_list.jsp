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
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		 <link rel="stylesheet" href="/MemberServices/includes/css/jquery-ui-1.10.3.custom.css" >
		<link href="includes/css/ms.css" rel="stylesheet" type="text/css">				
			<script src="/MemberServices/includes/js/jquery-1.9.1.js"></script>
			<script src="/MemberServices/includes/js/jquery-ui-1.10.3.custom.js"></script>
			<script type="text/javascript" src="includes/js/common.js"></script>
			<script src="includes/js/nlesd.js"></script>
			<script src="/MemberServices/includes/js/backgroundchange.js"></script>
		
		<script>
		jQuery(function(){
	     $(".img-swap").hover(
	          function(){this.src = this.src.replace("-off","-on");},
	          function(){this.src = this.src.replace("-on","-off");});
		});
			
		
	</script>
<script type="text/javascript">
			$('document').ready(function(){
				$('tr.datalist:odd').css('background-color', '#E0E0E0');
			});
		</script>
	
	</head>

	<body>
	<br/>
  <div class="mainContainer">

  	   	<div class="section group">
	   		
	   		<div class="col full_block topper">
	   		<div class="toppertextleft">Logged in as <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></div>
	   		<div class="toppertextright"><script src="/MemberServices/includes/js/date.js"></script></div>	   		
			</div>
			
			<div class="full_block center">
				<img src="includes/img/header.png" alt="" width="90%" border="0"><br/>				
			</div>
			<div class="col full_block content">
				<div class="bodyText">	
			<jsp:include page="menu.jsp" />
				<br/><div align="center"><img src="/MemberServices/includes/img/bar.png" width=99% height=1></div><br/>	
				<div class="pageHeader" align="center">View Pay Periods</div>
			<p>	
			

<span class="messageText">
									<%if(request.getAttribute("msg")!=null){%>
										<%=(String)request.getAttribute("msg")%>
                             		 <%} %>   
									</span>
									
									
									
<table align="center" class="payrollPeriodsTable">
									<tr class="header ">
										<th class="startP">Start</th>
										<th class="endP">End</th>
										<th class="statImp">Import Status</th>
										<th class="stubStat">Stub &amp; Email Status</th>
										<th class="perStat">Period Status</th>
										<th class="perDetails">View</th>
									</tr>
																		<c:choose>
	                                  	<c:when test='${fn:length(periods) gt 0}'>
                                  		<c:forEach items='${periods}' var='g'>
                                  			<tr class='datalist'>
                                  			
                                  			<td>${g.bgDate}</td>
		                                      <td>${g.endDate}</td>
		                                      <td>${g.importStatus}</td>
		                                      <td>${g.stubCreationStatus}</td>
		                                      <td>${g.closedStatus}</td>
		                                      
		                                      <td>
		                                      <a title="View Details" class="small" href='viewNLESDPayAdvicePayPeriodDetails.html?id=${g.payGroupId}'><img src='includes/img/details.png' border='0'></a>
		                                      </td>
		                                      </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='6'>No Pay Periods found.</td></tr>
										</c:otherwise>
									</c:choose>
								</table>
					
<br/>
					
	</div>
</div>
			</div>

<div style="float:right;padding-right:3px;width:25%;text-align:right;"><a href="../navigate.jsp" title="Back to MemberServices Main Menu"><img src="/MemberServices/includes/img/ms-footerlogo.png" border=0></a></div>
		<div class="section group">
			<div class="col full_block copyright">&copy; 2016 Newfoundland and Labrador English School District</div>
		</div>	
</div>
  
<br/>
    
  </body>

</html>												
					
			