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


	
	</head>

  <body><br/>
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
				<div class="pageHeader" align="center">Teacher PayAdvice Manager</div>
			
			<p>	
		<div class="pageSectionHeader siteSubHeaders">Payroll Processing Steps</div>
		<div class="pageBody">
		<ol>
		<li>Upload Payroll Data, Employee Mapping and Work History files using the <A href="upload_files.jsp" style="color: #CC0000">Upload File</A> menu item.
		<p><li>Once all files have uploaded successfully, click the  <A href="viewUnprocessedNLESDPayrollDocuments.html" style="color: #CC0000">Unprocessed Files</A> menu item and select	the files to be imported and processed.  Then click the Process Files button.
		<p><li>After clicking the Process Files button, confirm the the correct files (Payroll, Mapping and History) have been selected.
		<p><li>Next click the Start Processing Files button and monitor the progress of the data import using the <A href="viewNLESDPayAdviceImportJobs.html" style="color: #CC0000">View Import Jobs</A> menu item.
		<p><li>Upon completion of the data import, use the <A href="viewNLESDPayAdvicePayPeriodsListAdmin.html" style="color: #CC0000">View Pay Periods</A> menu item to find and select the Pay Period you are looking for and view the details. 
		<p><li>If all information looks correct then click the Start Pay Stubs Creation\Email button.  Monitor the the creation\email process using the <A href="viewNLESDPayAdvicePayPeriodsListAdmin.html" style="color: #CC0000">View Pay Periods</A> or wait for the process completion email.
		<p><li>When processing has completed, view any processing errors by clicking the View Process Errors link on the View Pay Period Details page.
		<p><li>Finally, if all stubs have been generated and emailed successfully then close the pay period using the Close Pay Period button on the View Pay Period Details page.									
		</ol>	
		
						
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