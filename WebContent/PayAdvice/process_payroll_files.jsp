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
				<div class="pageHeader" align="center">Please confirm files for processing</div>
		<p>			

		<form onsubmit="checkfiles();" action="startProcessingNLESDPayrollDocument.html">	
		
					<table class="payrollProcessTable">						
						<c:if test="${payroll_file ne null}">
							<tr><td colspan='2'><div class="pageSectionHeader siteSubHeaders">Payroll File</div>
							<input type='hidden' name='payroll_file' id='payroll_file' value='${payroll_file.filename}' />
							<input type='hidden' name='payroll_file_id' id='payroll_file_id' value='${payroll_file.documentId}' />
							</td></tr>
                			<tr><td class='title'>File Name:</td><td class='result'>${payroll_file.originalFileName}</td></tr>
                			<tr><td class='title'>Number of Records:</td><td class='result'>${payrollrecords.payrollRecordsCount}</td></tr>
                			<tr><td class='title'>Company: </td><td class='result'>${payrollrecords.company}</td></tr>
                			<tr><td class='title'>Group Name: </td><td class='result'>${payrollrecords.payrollGroup}</td></tr>
                			<tr><td class='title'>Pay Period Start Date: </td><td class='result'>${payrollrecords.payrollStartDate}</td></tr>
                			<tr><td class='title'>Pay Period End Date: </td><td class='result'>${payrollrecords.payrollEndDate}</td></tr>
                		</c:if>
                		<c:if test="${mapping_file ne null}">
                		<tr><td colspan='2'><div class="pageSectionHeader siteSubHeaders">Mapping File</div>
                		<input type='hidden' name='mapping_file' id='mapping_file' value='${mapping_file.filename}' />
                		<input type='hidden' name='mapping_file_id' id='mapping_file_id' value='${mapping_file.documentId}' />
                		</td></tr>
                			<tr><td class='title'>File Name:</td><td class='result'>${mapping_file.originalFileName}</td></tr>
                			<tr><td class='title'>Number of Mapping Records:</td><td class='result'> ${mappingcount}</td></tr>
                		</c:if>
                		<c:if test="${other_file ne null}">
                			<tr><td colspan='2'><div class="pageSectionHeader siteSubHeaders">Substitute Work History File</div> 
                			<input type='hidden' name='other_file' id='other_file' value='${other_file.filename}' />
                			<input type='hidden' name='other_file_id' id='other_file_id' value='${other_file.documentId}' />
                			</td></tr>
                			<tr><td class='title'>File Name:</td><td class='result'>${other_file.originalFileName}</td></tr>
                			<tr><td class='title'>Number of Records: </td><td class='result'>${workhistoryrecords.workHistoryRecordsCount}</td></tr>
                			<tr><td class='title'>Company: </td><td class='result'>${workhistoryrecords.company}</td></tr>
                			<tr><td class='title'>Department: </td><td class='result'>ID: ${workhistoryrecords.deptId}</td></tr>
                		</c:if>
						<%if(request.getAttribute("msg")!=null){%>
                         	<tr class="messageText">
                            <td colspan="2" align="center">
                             <%=(String)request.getAttribute("msg")%>
                             	</td>
                              	</tr>
                          	<%}else {%> 
                          		<tr><td colspan='2' align='center'><input type='submit' value='Start Processing File(s)'></tr>
                          <%} %>               		
					</table>
					</form>
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
				