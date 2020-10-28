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
			
			function checkfiles()
			{

				
				var test=true;
				var test1 =$("#payroll-file").val();
				if (test1.length == 0)
				{
					
					alert("Please Select Payroll File to Upload");
					test=false;
					
				}
				var test2 =$("#mapping-file").val();
				if (test2.length == 0)
				{
					alert("Please Select Mapping File to Upload");
					test=false;
					
				}
				var test3 =$("#other-file").val();
				if (test3.length == 0)
				{
					alert("Please Select History File to Upload");
					test=false;
					
				}
				return test;
			}
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
				<div class="pageHeader" align="center">Upload Pay Advice Files</div>
			<p>
			
		<form action="uploadNLESDPayrollDocuments.html" method="post" ENCTYPE="multipart/form-data" onSubmit="return checkfiles();">
								<input type='hidden' name='op' value='confirm' />
		
		<div class="pageSectionHeader siteSubHeaders">Payroll Data</div>
		<div class="pageBody">
		Data File:<br/>
		<input type='file' name='payroll-file' id='payroll-file' style="width:40%;min-width:250px;"/><p>
		Notes:<br/>
		<textarea name='payroll-notes' style="width:60%;height:75px;min-width:300px;"></textarea><p>
		</div>					
		<br/>								
		<div class="pageSectionHeader siteSubHeaders">Employee Mapping</div>
		<div class="pageBody">
		Mapping File:<br/>
		<input type='file' name='mapping-file' id='mapping-file' style="width:40%;min-width:250px;"/><p>
		Notes:<br/>
		<textarea name='mapping-notes'  style="width:60%;height:75px;min-width:300px;"></textarea><p>
		</div>								
		<br/>								
		<div class="pageSectionHeader siteSubHeaders">Work History</div>
		<div class="pageBody">								
		History File:<br/>
		<input type='file' name='other-file' id='other-file' style="width:40%;min-width:250px;"/><p>
								
		Notes:<br/>
		<textarea name='other-notes' style="width:60%;height:75px;min-width:300px;"></textarea><p>
							
		<input id='btnSubmit' type='submit' value='Upload File(s)' /><p>
										
		<c:if test="${msg ne null}">
            <p class="messageText" style="padding-top:5px;padding-bottom:5px;text-align:center;">${msg}</p>
        </c:if>
		</div>									
							</form>
		
								
								
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