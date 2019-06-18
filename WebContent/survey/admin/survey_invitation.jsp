<%@ page language ="java" 
         session = "true"
         import = "java.util.*,
         			com.awsd.security.*,                 	
                 	java.io.*,
                 	java.text.*,
         			com.esdnl.survey.bean.*,
         			com.esdnl.survey.dao.*,
         			com.esdnl.util.*"
         isThreadSafe="false"%>  
         
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/survey.tld" prefix="survey" %>

<esd:SecurityCheck permissions="SURVEY-ADMIN-VIEW" />

<%
	SurveyBean survey = (SurveyBean) request.getAttribute("SURVEY_BEAN");
%>

<%!
  User usr = null; 
%>
<%
  User usr = (User) session.getAttribute("usr");
%>
<html>

	<head>
		<title>District Survey System Admin</title>
					

  	<meta name="viewport" content="width=device-width, initial-scale=1.0">  
    <meta charset="utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		 <link rel="stylesheet" href="/MemberServices/includes/css/jquery-ui-1.10.3.custom.css" >
			<link href="../includes/css/ms.css" rel="stylesheet" type="text/css">	
			<script src="/MemberServices/includes/js/jquery-1.9.1.js"></script>
			<script src="/MemberServices/includes/js/jquery-ui-1.10.3.custom.js"></script>
			<script type="text/javascript" src="../includes/js/common.js"></script>						
			<script src="../includes/js/nlesd.js"></script>
			<script src="/MemberServices/includes/js/backgroundchange.js"></script>		
			<script type="text/javascript">
			$('document').ready(function(){
					$('#survey_start_date').datepicker({dateFormat: 'dd/mm/yy'});
					$('#survey_end_date').datepicker({dateFormat: 'dd/mm/yy'});
			});
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
	   		
	   		<div class="col full_block topper">
	   		<div class="toppertextleft">Welcome <%=usr.getPersonnel().getFirstName()%> <%=usr.getPersonnel().getLastName()%></div>
	   		<div class="toppertextright"><script src="../includes/js/date.js"></script></div>	   		
			</div>
			
			<div class="full_block center">
				<img src="../includes/img/header.png" alt="" width="90%" border="0"><br/>				
			</div>
			<div class="col full_block content">
				<div class="bodyText">	
					<div align="center" style="padding-bottom:10px;padding-top:5px;">
						<a href='index.jsp'><img src="../includes/img/survey-list-off.png" class="img-swap" border=0 title="Survey Listings"></a>
						<a href='createSurvey.html'><img src="../includes/img/create-new-off.png" class="img-swap" border=0 title="Create Survey"></a>
						<a href="../../navigate.jsp"><img src="../includes/img/back-off.png" class="img-swap" border=0 title="Back to MemberServices Main Menu"></a>
					</div>	
					
					<div class="headerText"><%=survey.getName()%> - Send Invitation(s)</div>
					<p>
					
                	<%if(request.getAttribute("msg")!=null){%>
                	<span class="messageText"><%=(String)request.getAttribute("msg")%></span>
                	<%}%>
					<form id="frmSendSurveyInvitation" action="sendSurveyInvitation.html" method="post" ENCTYPE="multipart/form-data">
									<input type="hidden" name="op" value="send" />
									<input type="hidden" name="survey_id" value="<%=survey.getSurveyId()%>" />
									
									<span class="requiredStar">*&nbsp;</span> Required fields.<p>
										
                     
                    <span class="fieldTitle">Participants List File:</span><span class="requiredStar">&nbsp;*</span><br/>  
                    	<input type="file" id='participant_list' name='participant_list' style="width:250px;" class="requiredInputBox" />
                    	<p>
                    	 <span class="fieldTitle">From:</span><br/>  
                    	<input type="text" name="from" id="from" style="width:200px;" class="requiredInputBox"/>
                    	<p>
                    	<span class="fieldTitle">Invitation Subject:</span><span class="requiredStar">&nbsp;*</span><br/>  
                    	<input type="text" name="invitation_subject" id="invitation_subject" style="width:250px;" class="requiredInputBox"/>
                    	<p>
                    	<span class="fieldTitle">Invitation Content:</span><br/> 
                    	<input type="file" id='invitation_template' name='invitation_template' style="width:250px;" class="requiredInputBox" />
                    	<p>
                    	                   	
                    	<input type="submit" value="Send Invitation(s)" />
                                    
									
								</form>
                	
                	
                	<p>                	
                	<div align="center"><a href="javascript:history.go(-1)"><img src="../includes/img/backa-off.png" class="img-swap" border=0></a></div>
                	<br/>&nbsp;<br/>
					
					
						
				</div>
			</div>
		</div>

<div style="float:right;padding-right:3px;text-align:right;"><a href="../../navigate.jsp" title="Back to MemberServices Main Menu"><img src="../includes/img/ms-footerlogo.png" border=0 style="width:100%;"></a></div>
		<div class="section group">
			<div class="col full_block copyright">&copy; 2017 Newfoundland and Labrador English School District</div>
		</div>	
</div>
  

    
  </body>

</html>	