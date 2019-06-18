<%@ page language ="java" 
         session = "true"
         import = "java.util.*,            			    			                	
                 	java.io.*,
                 	java.text.*,
         			com.esdnl.survey.bean.*,         			
         			com.esdnl.util.*"
         isThreadSafe="false"%>  
         
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/survey.tld" prefix="survey" %>


<%
	SurveyBean survey = (SurveyBean) request.getAttribute("SURVEY_BEAN");
%>


<html>

	<head>
		<title>District Survey System/title>
					

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
	   		
	   		<div class="col full_block topper" align="center">
	   		<script src="includes/js/date.js"></script>	   		
			</div>
			
			<div class="full_block center">
				<img src="includes/img/header.png" alt="" width="90%" border="0"><br/>				
			</div>
			<div class="col full_block content">
				<div class="bodyText">	
						<br/>				
					<div class="headerText"><%=survey.getName() %></div>
					
					<p>
					
                	<c:if test="${msg ne null}">
                	<span class="messageText">${msg}</span><p>
                	</c:if>
                	
					<form id="frmSurveyPassword" action="surveyAuthenticate.html" method="post">
									<input type="hidden" name="survey_id" value='<%=survey.getSurveyId()%>' />
									
									<span class="requiredStar">*&nbsp;</span> Required fields.<p>
                   
										
                    
                    		<span class="fieldTitle">Password:</span> <span class="requiredStar">*&nbsp;</span><br/>
                    		<input type="password" name="survey_password" id="survey_password" style="width:200px;" class="requiredInputBox"/>
                    		&nbsp;&nbsp;&nbsp;<input type="submit" value='Go' />
                    	
					</form>
					
					
					
					
					
									
								
										
										
											
									
                	
                	
                	
                	<br/>&nbsp;<br/>
					
					
						
				</div>
			</div>
		</div>

		<div class="section group">
			<div class="col full_block copyright">&copy; 2017 Newfoundland and Labrador English School District</div>
		</div>	
</div>
  

    
  </body>

</html>	