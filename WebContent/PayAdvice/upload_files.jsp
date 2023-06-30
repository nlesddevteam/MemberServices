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
 	
		
		<script>
		

					
			function checkfiles()
			{

				
				var test=true;
				var test1 =$("#payroll-file").val();
				if (test1.length == 0)
				{
					$(".msgERR").css("display","block").append("Please Select Payroll File to Upload");					
					test=false;
					
				}
				var test2 =$("#mapping-file").val();
				if (test2.length == 0)
				{
					$(".msgERR").css("display","block").append("Please Select Mapping File to Upload");					
					test=false;
					
				}
				var test3 =$("#other-file").val();
				if (test3.length == 0)
				{
					$(".msgERR").css("display","block").append("Please Select History File to Upload");					
					test=false;
					
				}
				return test;
			}
		</script>

	
	</head>

  <body>
  
  <div class="row pageBottomSpace">
<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
<div class="siteBodyTextBlack">
<jsp:include page="menu.jsp" />
<div class="siteHeaderGreen">Upload Pay Advice Files</div>


  

<form action="uploadNLESDPayrollDocuments.html" method="post" ENCTYPE="multipart/form-data" onSubmit="return checkfiles();" class="was-validated">
		
		<input type='hidden' name='op' value='confirm' />


 
	<div class="miniBlock">	
	<div class="siteSubHeaderGreen">Payroll Data</div>
	
		<b>Data File:</b><br/>
		<input class="form-control" type='file' name='payroll-file' id='payroll-file' required/><br/>
	<b>Notes:</b><br/>
		<textarea name='payroll-notes' class="form-control" maxlength="1000"  style="height:150px;"></textarea>
	</div>				
	
	<div class="miniBlock">
	<div class="siteSubHeaderGreen">Employee Mapping</div>

		<b>Mapping File:</b><br/>
		<input type='file' class="form-control" name='mapping-file' id='mapping-file' required/><br/>
		<b>Notes:</b><br/>
		<textarea name='mapping-notes' class="form-control" maxlength="1000"  style="height:150px;"></textarea>
	</div>
	
	<div class="miniBlock">		
	<div class="siteSubHeaderGreen">Work History</div>
									
		History File:<br/>
		<input type='file' name='other-file' id='other-file' class="form-control" required/><br/>
								
		<b>Notes:</b><br/>
		<textarea name='other-notes' class="form-control" maxlength="1000" style="height:150px;"></textarea>
	</div>
		<br/><br/>	
		<div align="center">		
		<input class="btn btn-sm btn-primary" id='btnSubmit' type='submit' value='Upload File(s)'/>
		</div>		

										
	</form>
		
								
								
	</div>
	</div>
	</div>

    
  </body>

</html>										