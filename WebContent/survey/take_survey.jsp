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
		<title>District Survey System</title>
					

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
		<script type="text/javascript">
			function displaySaveMessage(){
				var save = document.getElementById('save');
				
				save.innerHTML = 'Saving Reponse, Please Wait...';
				save.className = 'messageText';
				
			}
			
			$(function(){
				$('.bullet-answer').keydown(function(event){
					
					if ( event.which == 13 ) {
						event.preventDefault();
					}
					else{
						var max = parseInt($(this).attr('bullet-length'));
						var len = parseInt($(this).val().length);
						
						if((event.which == 8) || (event.which == 46)) {
							len--;
							
							if(len < 0) len = 0;
						}
						else {
							len++;
						}
						
						if(len > max){
							event.preventDefault();
						}
						else {
							$('#' + $(this).attr('name') + '_chars').html((max - len) + ' characters remaining.');
						}
					}
					
				});
			})
		</script>	
			
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
				
				<% if(survey.isInternal()){ %>
				<esd:SecurityCheck />
				<% } %>
				
					<br/>
					
					<div class="headerText"><%=survey.getName()%></div>
					
					<p>
					
                	<c:if test="${msg ne null}">
                	<span class="messageText">${msg}</span><p>
                	</c:if>
                	
					
					
					<%
                	if(survey.hasIntroduction())
                		out.println(survey.getIntroduction());
                	%>
					
					<br/>
								
								<%if(!survey.getEndDate().before(Calendar.getInstance().getTime())){ %>
								<form id="frmTakeSurvey" action="takeSurvey.html" method="POST" onsubmit="displaySaveMessage();return true;">
									<input type="hidden" name="op" value="create" />
									<input type="hidden" name="survey_id" value="<%=survey.getSurveyId()%>"/>
									<survey:Preview survey='<%=survey%>' />
									<table align="center" width="100%" cellspacing="2" cellpadding="2" border="0">
										<tr>
                    	<td id='save' align="center"><input type="submit" value="Submit Survey Response" /></td>
                    </tr>
									</table>
								</form>
								<%}else{%>
									<P style='color:red; font-weight:bold;'>
										This survey is now closed. Thank you for participating!<br/>
										Newfoundland and Labrador English School District
									</P>
								<%}%>
					
					
					
					
								
										
										
											
									
                	
                	
                	
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