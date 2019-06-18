<%@ page language="java"
         session="true"
         isThreadSafe="false"
         import="com.awsd.security.*,
                 java.util.*,
                 java.io.*,
                 java.text.*"%>
                 
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>


<html>

	<head>
		<title>NLESD - District Survey System</title>
					

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
	   		
	   		<div class="col full_block topper" align="center">
	   		<script src="includes/js/date.js"></script>	   		
			</div>
			
			<div class="full_block center">
				<img src="includes/img/header.png" alt="" width="90%" border="0"><br/>				
			</div>
			<div class="col full_block content">
				<div class="bodyText">	
				
						<div style="font-size:16px;color:#007F01;padding-top:8px;" align="center">Thank You!</div>
				
					<%if(request.getAttribute("msg")!=null){%>
									<br>&nbsp;<br>
                	<span class="messageText" style="padding-top:4px;padding-bottom:4px;text-align:center;">
                  	<%=(String)request.getAttribute("msg")%>
                  </span>
                  
                  <br>&nbsp;<br>
                <%}else{%>
                	<br>&nbsp;<br>
                	<span class="messageText" style="padding-top:4px;padding-bottom:4px;text-align:center;">
                  	Please contact your administrator for a survey id and password.
                  </span>
                  <br>&nbsp;<br>
                <%}%>
						
				</div>
			</div>
		</div>

		<div class="section group">
			<div class="col full_block copyright">&copy; 2017 Newfoundland and Labrador English School District</div>
		</div>	
</div>
  

    
  </body>

</html>	