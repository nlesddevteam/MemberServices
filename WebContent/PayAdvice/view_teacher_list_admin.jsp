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
			<script type="text/javascript" src="/MemberServices/includes/js/common.js"></script>		
		
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
	<!-- Add mousewheel plugin (this is optional) -->
	<script type="text/javascript" src="includes/fancybox/jquery.mousewheel-3.0.6.pack.js"></script>
	<!-- Add fancyBox main JS and CSS files -->
	<script type="text/javascript" src="includes/fancybox/jquery.fancybox.js?v=2.1.5"></script>
	<link rel="stylesheet" type="text/css" href="includes/fancybox/jquery.fancybox.css?v=2.1.5" media="screen" />
	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="includes/fancybox/helpers/jquery.fancybox-buttons.css?v=1.0.5" />
	<script type="text/javascript" src="includes/fancybox/helpers/jquery.fancybox-buttons.js?v=1.0.5"></script>
	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="includes/fancybox/helpers/jquery.fancybox-thumbs.css?v=1.0.7" />
	<script type="text/javascript" src="includes/fancybox/helpers/jquery.fancybox-thumbs.js?v=1.0.7"></script>
	<!-- Add Media helper (this is optional) -->
	<script type="text/javascript" src="includes/fancybox/helpers/jquery.fancybox-media.js?v=1.0.6"></script>
	<script type="text/javascript" src="includes/js/changepopup.js"></script>
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
				<div class="pageHeader" align="center">View Employee Information</div>
			<p>	
				




<span class="messageText">
									<%if(request.getAttribute("msg")!=null){%>
										<%=(String)request.getAttribute("msg")%>
                             		 <%} %>   
</span>


					<table class="payrollTeacherTable">
									<tr class="header">
										<th class="ppname">NAME</th>
										<th class="ppstart">PAY BEGIN DATE</th>
										<th class="ppend">PAY END DATE</th>
										<th class="ppcheck">CHECK DATE</th>
										<th class="ppdetail">Details</th>
										<th class="pphistory">History</th>
										<th class="ppemail">Email</th>
										
									</tr>
										<c:choose>
	                                  	<c:when test='${fn:length(employees) gt 0}'>
                                  		<c:forEach items='${employees}' var='g'>
                                  			<tr class='datalist'>
                                  			<td align='left'>${g.empName}</td>
		                                      <td align='center'>${g.payBgDt}</td>
		                                      <td align='center'>${g.payEndDt}</td>
		                                      <td align='center'>${g.checkDt}</td>
		                                      <td><a title="View Details" href="viewNLESDPayAdviceTestStub.html?id=${g.id}&emp=${g.empNumber}" target="_blank"><img src="includes/img/details.png" border=0></a></td>
		                                      
		                                      <c:choose>
		                                      <c:when test='${g.hisCount gt 0}'>
		                                      	<td><a title="View History" href="viewNLESDPayAdviceWorkHistory.html?id=${g.id}&emp=${g.empNumber}" target="_blank"><img src="includes/img/history.png" border=0></a></td>
		                                       </c:when>
												<c:otherwise>
												<td>N/A</td>
		                                       </c:otherwise>
												</c:choose>
												<td>
												
												<a class="fancybox" href="#inline1" title="Email Employee Pay Advice Information" onclick="OpenPopUp(${g.id},'${g.empNumber}');"><img src="includes/img/resendpass.png" border=0></a>
												</td>
		                                      </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='7' align='center'>No information found for employee</td></tr>
										</c:otherwise>
									</c:choose>
								</table>
					
			<br/><br/>
				
				
			<div id="inline1" style="width:400px;display: none;">
		
			<span class="headertitle">Email Employee Pay Advice Information</span>
			<table width="300px;" cellpadding="0" cellspacing="3" align="center" border="0" style="padding: 5px;">
				<tr>
					<td class="subheader" valign="middle" width='125px'>
						Employee:
					</td>
					<td>
						<span id="empname"></span>
					</td>
				</tr>
				<tr>
					<td class="subheader" valign="middle" width='125px'>
						Email:
					</td>
					<td>
						<span id="empemail"></span>
					</td>
				</tr>
				<tr>
					<td class="subheader" valign="middle" width='125px'>
						Pay Period:
					</td>
					<td>
						<span id="payperiod"></span>
					</td>
				</tr>				

								<tr>
					<td class="subheader" valign="middle" width='125px'>
						
					</td>
					<td>
						
						<input type="hidden" id='hidPID'>
						<input type="hidden" id='hidEID'>
						<input type="hidden" id='hidEMAIL'>
					</td>
				</tr>				
				<tr>
					<td colspan="2" valign="middle" align="center">
						<input type="button" value="Email" onclick="sendinfo();"/>
						<input type="button" value="Cancel" onclick="closewindow();"/>

					</td>
				</tr>
		</table>
	</div>
	
</div>
</div>
			</div>

<div style="float:right;padding-right:3px;width:25%;text-align:right;"><a href="../navigate.jsp" title="Back to MemberServices Main Menu"><img src="/MemberServices/includes/img/ms-footerlogo.png" border=0></a></div>
		<div class="section group">
			<div class="col full_block copyright">&copy; 2016 Newfoundland and Labrador English School District</div>
		</div>	
</div>
  

    
  </body>

</html>					