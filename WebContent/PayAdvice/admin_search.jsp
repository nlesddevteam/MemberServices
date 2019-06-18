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
		
		<script type="text/javascript">
		$(document).ready(function() {
			setOptions(); // on load
		    $('#searchby').change(setOptions); // on change
		    function setOptions() {       
		        switch ($("#searchby").val()) {
		            case "SELECT" :
		                $("#divtext").hide();
		                $("#divselect").hide();
		                break;
		            case "SIN":
		                $("#divselect").hide();
		                $("#divtext").show();
		                break;
		            case "NAME":
		                $("#divtext").show();
		                $("#divselect").hide();
		                break;
		            case "SCHOOL":
		                $("#divselect").show();
		                $("#divtext").hide();
		                break;
		            }
		    } 
		});
		</script>
		<script type="text/javascript">

			function search()
			{
				cleartable();
				ajaxRequestInfo();
			}
			function cleartable()
			{
				$('#showlists td').parent().remove();
			}
			function ajaxRequestInfo()
			{
				var searchby = $.trim($('#searchby').val());
				var searchtext = $.trim($('#txtsearch').val());
				var searchschool = $.trim($('#school').val());
				
				var isvalid=false;
				$.ajax(
			 			{
			 				type: "POST",  
			 				url: "searchNLESDPayAdvice.html",
			 			
			 				data: {
			 					sby: searchby,
			 					stx: searchtext,
			 					ssc: searchschool
			 				}, 
			 				success: function(xml){
			 					var i=1;
			 					$(xml).find('EMPLOYEE').each(function(){
			 							
			 							if($(this).find("MESSAGE").text() == "LISTFOUND")
			 								{
			 								
			 									var color="FFFFFF";
			 									if(i % 2 == 0){
			 										color="#E0E0E0";
			 									}
												var newrow="<tr style='background-color:" + color + ";'>";
			                                    newrow += "<td class='displayText'>" + $(this).find("EMPNAME").text() + "</td>";
			                                    newrow += "<td class='displayText'>" + $(this).find("SCHOOL").text() + "</td>";
			                                    newrow += "<td class='displayText'>" + $(this).find("SIN").text() + "</td>";
			                                    newrow += "<td class='displayText'>" + $(this).find("ID").text() +"</td>";
												newrow += "<td class='displayText' align='center'>";										
												newrow += "<a title='View Employee' style='text-decoration:none;' "
												newrow += "href='viewNLESDPayAdviceTeacherListAdmin.html?empnumber=" ;
												newrow +=  $(this).find("ID").text() + "'><img src='includes/img/viewemp.png' border='0'></a></td>";
												newrow += "<td class='displayText' align='center'>";										
												newrow += "<a href='#' title='Reset Password' style='text-decoration:none;' ";
												newrow += " onclick='updatePassword(\"" + $(this).find("ID").text() + "\");'><img src='includes/img/resetpass.png' border='0'></a></td>";
												newrow += "<td class='displayText' align='center'>";										
												newrow += "<a href='#' title='Resend Password' style='text-decoration:none;' ";
												newrow += " onclick='resendPassword(\"" + $(this).find("ID").text() + "\");'><img src='includes/img/resendpass.png' border='0'></a></td></tr>";
												
												$('table#showlists tr:last').after(newrow);
												i=i+1;
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
			function updatePassword(testing)
			{
				var isvalid=false;
				$.ajax(
			 			{
			 				type: "POST",  
			 				url: "resetEmployeePassword.html",
			 			
			 				data: {
			 					pid: testing
			 				}, 
			 				success: function(xml){
			 					var i=1;
			 					$(xml).find('EMPLOYEE').each(function(){
			 							
			 							if($(this).find("MESSAGE").text() == "SUCCESS")
			 								{
			 								
			 									alert("Password has been reset and email sent");
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
			function resendPassword(testing)
			{
				var isvalid=false;
				$.ajax(
			 			{
			 				type: "POST",  
			 				url: "resendEmployeePasswordAdmin.html",
			 			
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
				<img src="includes/img/header.png" alt="" width="90%" border="0"><br/>				
			</div>
			<div class="col full_block content">
				<div class="bodyText">	
				<jsp:include page="menu.jsp" />
				<br/><div align="center"><img src="/MemberServices/includes/img/bar.png" width=99% height=1></div><br/>	
				<div class="pageHeader" align="center">Search Employees</div>
			
		
<p>


		
	
	
	
	<span class="messageText">
									<%if(request.getAttribute("msg")!=null){%>
										<%=(String)request.getAttribute("msg")%>
                             		 <%} %>   
									</span>
									
									
									
		<div class="pageSectionHeader siteSubHeaders">Search by:</div>
		<div class="pageBody">							
									
					 
										<select id="searchby">
										<option value="SELECT">Please Select Search By</option>
										<option value="NAME">Name</option>
										<option value="SCHOOL">School</option>
										<option value="SIN">SIN</option>
										</select>
					
										
										<div id="divtext" style="display:none">
										<p>For:<br/> 
										<input type="text" id="txtsearch"><br/>
										</div>
										<div id="divselect"  style="display:none">
										<p><select id="school">
										<option value="SELECT">Select School</option>
										<c:forEach var="test" items="${list}" >

										<option value="<c:out value='${test}'/>"><c:out value="${test}"/></option>
										</c:forEach>
										</select><br/>
										</div>
										
										<p><input type="button" value="Search Employees" onclick="search()"><p>
		</div>						
					<br>
					<table align="center" id="showlists" class="payrollSearchTable">
					<tr class="header">
					<th class="searchEmp">EMPLOYEE</th>
					<th class="searchSch">SCHOOL</th>
					<th class="searchSin">SIN</th>
					<th class="searchID">PAYROLL ID</th>
					<th class="options">View</th>
					<th class="options">Reset</th>
					<th class="options">Resend</th>
					</tr>
					</table>
					
					<br/><br/>
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
