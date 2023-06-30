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
	
	<style>
	.processTable {width:100%;max-width:1024px;font-size:12px;}
	.title {width:30%;font-weight:bold;text-transform:uppercase;}
	.result {width:70%;}	
	
  	input {border: 1px solid silver;}
		.btn-group {float:left;}	
  
  
	</style>
	</head>

	<body>
	<div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
<div class="siteHeaderGreen">Please confirm files for processing</div>

<jsp:include page="menu.jsp" />
	

		<form action="startProcessingNLESDPayrollDocument.html">	
		
					<c:if test="${payroll_file ne null}">					
					<div class="siteSubHeaderGreen">Payroll File</div>
							<input type='hidden' name='payroll_file' id='payroll_file' value='${payroll_file.filename}' />
							<input type='hidden' name='payroll_file_id' id='payroll_file_id' value='${payroll_file.documentId}' />
							<table class="processTable table table-sm table-border responsive table-striped">
	                			<tr><td class='title'>File Name:</td><td class='result'>${payroll_file.originalFileName}</td></tr>
	                			<tr><td class='title'>Number of Records:</td><td class='result'>${payrollrecords.payrollRecordsCount}</td></tr>
	                			<tr><td class='title'>Company: </td><td class='result'>${payrollrecords.company}</td></tr>
	                			<tr><td class='title'>Group Name: </td><td class='result'>${payrollrecords.payrollGroup}</td></tr>
	                			<tr><td class='title'>Pay Period Start Date: </td><td class='result'>${payrollrecords.payrollStartDate}</td></tr>
	                			<tr><td class='title'>Pay Period End Date: </td><td class='result'>${payrollrecords.payrollEndDate}</td></tr>
                			</table>
                	</c:if>
                		<c:if test="${mapping_file ne null}">                		
	                		<div class="siteSubHeaderGreen">Mapping File</div>                		               		
	                		<input type='hidden' name='mapping_file' id='mapping_file' value='${mapping_file.filename}' />
	                		<input type='hidden' name='mapping_file_id' id='mapping_file_id' value='${mapping_file.documentId}' />
		                		<table class="processTable table table-sm table-border responsive table-striped"> 
		                			<tr><td class='title'>File Name:</td><td class='result'>${mapping_file.originalFileName}</td></tr>
		                			<tr><td class='title'>Number of Mapping Records:</td><td class='result'> ${mappingcount}</td></tr>
		                		</table>
                		</c:if>
                		<c:if test="${other_file ne null}">
                		<div class="siteSubHeaderGreen">Substitute Work History File</div>  
                			
                			<input type='hidden' name='other_file' id='other_file' value='${other_file.filename}' />
                			<input type='hidden' name='other_file_id' id='other_file_id' value='${other_file.documentId}' />
                			<table class="processTable table table-sm table-border responsive table-striped"> 
	                			<tr><td class='title'>File Name:</td><td class='result'>${other_file.originalFileName}</td></tr>
	                			<tr><td class='title'>Number of Records: </td><td class='result'>${workhistoryrecords.workHistoryRecordsCount}</td></tr>
	                			<tr><td class='title'>Company: </td><td class='result'>${workhistoryrecords.company}</td></tr>
	                			<tr><td class='title'>Department: </td><td class='result'>ID: ${workhistoryrecords.deptId}</td></tr>
	                		</table>	
                		</c:if>
						
						<c:if test="${other_file eq null and mapping_file eq null and other_file eq null }">
                          				<div class="alert alert-danger" style="text-align:center;">Sorry, no files available to confirm for processing at this time.</div>
                          			</c:if>
						
                          	<%if(request.getAttribute("msg") == null){%>
                          	 		<c:if test="${other_file ne null or mapping_file ne null or other_file ne null }">
                          				<input type='submit' value='Start Processing File(s)' class="btn btn-sm btn-danger" onclick="loadingData();">
                          			</c:if>
                          <%}%>               		
					
					</form>

</div></div></div>

    
  </body>

</html>										
				