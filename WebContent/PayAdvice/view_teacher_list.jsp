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
<esd:SecurityCheck permissions="PAY-ADVICE-NORMAL" />
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
	
	<script>
		function openHistorical()
		{
			var pp = $("#payperiod").val();
			var en = $("#empnumber").val();
			var choice = $("#viewtype").val();
			if(pp > 0)
			{
			if(choice == "vd")
			{
				var url="viewNLESDPayAdviceTestStub.html?id=" + pp + "&emp=" + en;
			}else{
				var url="viewNLESDPayAdviceWorkHistory.html?id=" + pp + "&emp=" + en;
			}
			window.open(url,"_blank");
			}else{
				alert("Please Select Pay Period");
			}
		}
		function resendPassword(testing)
		{
			var isvalid=false;
			$.ajax(
		 			{
		 				type: "POST",  
		 				url: "resendEmployeePasswordTeacher.html",
		 			
		 				data: {
		 					pid: testing
		 				}, 
		 				success: function(xml){
		 					var i=1;
		 					$(xml).find('EMPLOYEE').each(function(){
		 							
		 							if($(this).find("MESSAGE").text() == "SUCCESS")
		 								{
		 								
		 									alert("Password has been resent");
											isvalid=true;
			                   				
		 								}else{
		 									alert($(this).find("MESSAGE").text());
		 									
		 								}
								});
							},
		 				  error: function(xhr, textStatus, error){
		 				      alert(xhr.statusText);
		 				      alert(textStatus);
		 				      alert(error);
		 				  },
		 				dataType: "text",
		 				async: false
		 			}
		 		);
			return isvalid;

		}		
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
				<img src="includes/img/headert.png" alt="" width="90%" border="0"><br/>				
			</div>
			<div class="col full_block content">
				<div class="bodyText">	
			<p><div align="center">
			<div class="menuIconImage"><a href='../navigate.jsp'><img src="includes/img/home-off.png" border=0 class="menuImage img-swap"></a></div>		
		<div class="menuIconImage"><a class="fancybox" href="#inline1" title="Change My Password" onclick="OpenPopUpPassword('${empnum}');"><img src="includes/img/changepass-off.png" class="menuImage img-swap" border=0></a></div>
		<div class="menuIconImage"><a href='#' style='text-decoration:none;' title="Resend My Password" onclick="resendPassword('${empnum}');"><img src="includes/img/resend-off.png" class="menuImage img-swap" border=0></a></div>
			</div>
				<br/><div align="center"><img src="/MemberServices/includes/img/bar.png" width=99% height=1></div><br/>	
				<div class="pageHeader" align="center">View My Pay Advices</div>
			<p>	
				


	
												
	<span class="messageText" style="text-align:center;">
									<%if(request.getAttribute("msg")!=null){%>
										<%=(String)request.getAttribute("msg")%>
                             		 <%} %>   
	</span>
									
	<p>								
<table class="payrollTeacherTable" >

									<tr class="header">
										<th class="ppbegint">Pay Begin Date</th>
										<th class="ppendt">Pay End Date</th>
										<th class="ppcheckt">Check Date</th>
										<th  class="ppdetail">Details<input type="hidden" id="empnumber" value="${empnum}"></th>
										<th  class="pphistory">History</th>
									</tr>
									<c:choose>
	                                  	<c:when test='${fn:length(paygroups) gt 0}'>
                                  		<c:forEach items='${paygroups}' var='g' end='5'>
                                  			<tr class='datalist'>
                                  			<td>${g.payBgDt}</td>
		                                      <td>${g.payEndDt}</td>
		                                      <td>${g.checkDt}</td>
		                                      <td><a title="View Details" href="viewNLESDPayAdviceTestStub.html?id=${g.id}&emp=${g.empNumber}" target="_blank"><img src="includes/img/details.png" border=0></a></td>
		                                      
		                                      <c:choose>
		                                      <c:when test='${g.hisCount gt 0}'>
		                                      	<td><a title="View History" href="viewNLESDPayAdviceWorkHistory.html?id=${g.id}&emp=${g.empNumber}" target="_blank"><img src="includes/img/history.png" border=0></a></td>
		                                       </c:when>
												<c:otherwise>
												<td>N/A</td>
		                                       </c:otherwise>
												</c:choose>
		                                      
		                                      
		                                      </tr>
                                  		</c:forEach>
                                  		</c:when>
										<c:otherwise>
											<tr><td colspan='5'>No payroll information found.</td></tr>
										</c:otherwise>
									</c:choose>
									<tr><td colspan="5"><hr size="1" /><br /></td></tr>
									<tr><th class="header" colspan="5" >View Other Pay Period(s)</th></tr> 
									</table>
									<p>
												Please select:&nbsp;
												<select id="payperiod">
			<option value='-1'>Please Select</option>
			<c:choose>
				<c:when test='${fn:length(paygroups) gt 0}'>
                	<c:forEach items='${paygroups}' var='g'>
                		<option value="${g.id}">${g.payBgDt}-${g.payEndDt}</option>
					</c:forEach>
                </c:when>
            </c:choose>
			</select>
			
			<select id="viewtype">
			<option value="vd">Details</option>
			<option value="vh">History</option>
			</select>
			<input type="button" value="View" onClick="return openHistorical();" />
			
				
				<p>	
				
		      <div id="inline1" style="width:400px;display: none;">
		
			<span class="headertitle">Change My Password</span>
			<table width="300px;" cellpadding="0" cellspacing="3" align="center" border="0" style="padding: 5px;">

				<tr>
					<td class="subheader" valign="middle" width='125px'>
						Current Password:
					</td>
					<td>
						<input type="text" class="requiredinput" id="current_password"  name="current_password" style="width:250px;" >
					</td>
				</tr>
								<tr>
					<td class="subheader" valign="middle" width='125px'>
						New Password:
					</td>
					<td>
						<input type="text" class="requiredinput" id="new_password"  name="new_password" style="width:250px;" >
					</td>
				</tr>
								<tr>
					<td class="subheader" valign="middle" width='125px'>
						Confirm New Password:
					</td>
					<td>
						<input type="text" class="requiredinput" id="confirm_password"  name="confirm_password" style="width:250px;" >
					</td>
				</tr>
			
				
				<tr>
					<td colspan="2" valign="middle" align="center">
						<input type="button" value="Change Password" onclick="updatepassword();"/>
						<input type="button" value="Cancel" onclick="closepasswordwindow();"/>
						<input type="hidden" id="hidPID" name="hidPID">
						<input type="hidden" id="hidPASSWORD" name="hidPASSWORD">
					</td>
				</tr>
								<tr>
					<td colspan="2" valign="middle" align="center">
							<ul>
							<li>Password must be at least six characters</li>
							<li>Password must not contain special characters(!@#$%^&*)</li>
							</ul>
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