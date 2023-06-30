<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>
                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<esd:SecurityCheck />
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
   </head>

  <body>
  <div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
  <jsp:include page="menu.jsp" />
<div class="siteHeaderGreen">PayAdvice Manager</div>
 Welcome to the PayAdvice system. please read the processing steps below if you are unfamiliar with the system.
 
<br/><br/>	
  
  <div align="center" style="margin:0 auto;width:80%;border:1px solid silver;border-radius:2px;">
  <div style="text-align:left;font-size:14px;padding:10px;">
  <div class="siteSubHeaderBlue">Payroll Processing Steps</div>
		
		<ol>
		<li>Upload Payroll Data, Employee Mapping and Work History files using the <A href="upload_files.jsp">Upload File</A> menu item.
		<p><li>Once all files have uploaded successfully, click the  <A href="viewUnprocessedNLESDPayrollDocuments.html">Unprocessed Files</A> menu item and select	the files to be imported and processed.  Then click the Process Files button.
		<p><li>After clicking the Process Files button, confirm the the correct files (Payroll, Mapping and History) have been selected.
		<p><li>Next click the Start Processing Files button and monitor the progress of the data import using the <A href="viewNLESDPayAdviceImportJobs.html">View Import Jobs</A> menu item.
		<p><li>Upon completion of the data import, use the <A href="viewNLESDPayAdvicePayPeriodsListAdmin.html">View Pay Periods</A> menu item to find and select the Pay Period you are looking for and view the details. 
		<p><li>If all information looks correct then click the Start Pay Stubs Creation\Email button.  Monitor the the creation\email process using the <A href="viewNLESDPayAdvicePayPeriodsListAdmin.html">View Pay Periods</A> or wait for the process completion email.
		<p><li>When processing has completed, view any processing errors by clicking the View Process Errors link on the View Pay Period Details page.
		<p><li>Finally, if all stubs have been generated and emailed successfully then close the pay period using the Close Pay Period button on the View Pay Period Details page.									
		</ol>	
		
	</div>					
	</div>
			 
		 
			 
</div>
</div>
</div>

    
  </body>

</html>								