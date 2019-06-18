<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>
                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>

<esd:SecurityCheck />
<%!
  User usr = null; 
%>
<%
  User usr = (User) session.getAttribute("usr");
%>
<html>

	<head>
		<title>NLESD - Senior Educating Team Resources</title>
					

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

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	</head>

  <body><br/>
  <div class="mainContainer">

  	   	<div class="section group">
	   		
	   		<div class="col full_block topper">
	   		<div class="toppertextleft">Welcome <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></div>
	   		<div class="toppertextright"><script src="includes/js/date.js"></script></div>	   		
			</div>
			
			<div class="full_block center">
				<img src="includes/img/setheader.png" alt="" width="90%" border="0"><br/>				
			</div>
			<div class="col full_block content">
				<div class="bodyText">	
					Below are a selection of district board presentations and resources available to you. 
				<br/><br/>
				Not all presentations and resources are available to all members. 
				<br/><br/>
			
					
					
				
				<div align="center"><img src="includes/images/bar.png" width=99% height=1></div>
					<div class="header">Presentation Resource Archive</div>
									
					<div class="mainbody"> 	
						<ul>						
					<esd:SecurityAccessRequired roles="ADMINISTRATOR,TRUSTEE,EXECUTIVE ASSISTANT,DIRECTOR,ASSISTANT DIRECTORS,SENIOR EDUCATION OFFICIER,SENIOR ADMINISTRATIVE OFFICER,PRINCIPAL,VICE PRINCIPAL">	
					<li><a href="leadershippresentation-central-mar2015.jsp">Director's Leadership Update Presentation - Central Region  (March 2015)</a>				
					<li><a href="leadershippresentation-eastern-mar2015.jsp">Director's Leadership Update Presentation - Eastern Region  (March 2015)</a>
					<li><a href="leadershippresentation-labrador-feb2015.jsp">Director's Leadership Update Presentation - Labrador Region  (February 2015)</a>
					<li><a href="leadershippresentation-western-feb2015.jsp">Director's Leadership Update Presentation - Western Region  (February 2015)</a>
					
					
					</esd:SecurityAccessRequired>
						<li><a href="publicpresentation-jan2015.jsp">Director's Update - Public Board Meeting (January 2015) - Darrin Pike</a>
						<li><a href="publicpresentation-nov2014.jsp">Director's Update - Public Board Meeting (November 2014) - Darrin Pike</a>
					
					<esd:SecurityAccessRequired roles="ADMINISTRATOR,TRUSTEE,EXECUTIVE ASSISTANT,DIRECTOR,ASSISTANT DIRECTORS,SENIOR EDUCATION OFFICIER,SENIOR ADMINISTRATIVE OFFICER">	
						<li><a href="intelivote-mar2016.jsp">Intelivote Systems - March 5, 2016</a>
						<li><a href="seopresentation-dec2014.jsp">Director Presentation - SEO Meetings (December 2014) - Darrin Pike</a>
						
			</esd:SecurityAccessRequired>
						
						
						</ul>
						</div>
		
						
	</div>
</div>
			</div>

<div style="float:right;padding-right:3px;width:25%;text-align:right;"><a href="../navigate.jsp" title="Back to MemberServices Main Menu"><img src="includes/img/ms-footerlogo.png" border=0></a></div>
		<div class="section group">
			<div class="col full_block copyright">&copy; 2016 Newfoundland and Labrador English School District</div>
		</div>	
</div>
  

    
  </body>

</html>	