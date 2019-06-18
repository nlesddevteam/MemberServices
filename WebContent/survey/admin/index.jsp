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
	SurveyBean[] surveys = SurveyManager.getSuveryBeans();
%>

<c:set var='surveys' value='<%=surveys%>' />
<c:set var='now' value='<%=Calendar.getInstance().getTime()%>' />
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
					
					<div class="headerText">Survey Listing</div>
					<p>
					<c:if test="${msg ne null}">
                	<span style="padding-top:8px;padding-bottom:8px;text-align:center;color:Red;font-size:11px;">${msg}</span>
                	</c:if>
                	
                	
                	<div class='survey-list'>
						<div class='row'>
							
							<div class='column header surveyID'>ID</div>	
							<div class='column header surveyTitle'>Title</div>							
							<div class='column header surveyStart'>Start</div>
							<div class='column header surveyEnd'>End</div>
							<div class='column header surveyResponses'>Resp</div>
							<div class='column header surveyAdmin'>Admin</div>
							
						</div>
						
						<c:choose>
										<c:when test='${ fn:length(surveys) gt 0 }'>
											<c:forEach items="${surveys}" var="survey">
						
						
					<div class='row'>
							<div class='column surveyID'><a href='editSurvey.html?id=${survey.surveyId}'>${survey.surveyId}</a></div>
							<div class='column surveyTitle'><a href='editSurvey.html?id=${survey.surveyId}'>${survey.name}</a> 							
							<c:if test="${survey.password != null}">
								<span style='color:Red;'>( ${survey.password} )</span>
							</c:if>
							</div>
							
							<div class='column surveyStart'>${survey.startDateFormatted}</div>
							<div class='column surveyEnd'>${(survey.endDate ne null)? survey.endDateFormatted : "&nbsp;"}</div>
							<div class='column surveyResponses'>${survey.responseCount}</div>
							<div class='column surveyAdmin'>
											<a href='createSurveySection.html?id=${survey.surveyId}' class='menu'><img src="../includes/img/create-section-off.png" border=0 title="Create Section" style="height:20px;"></a>
											<a href='viewSurveySections.html?id=${survey.surveyId}' class='menu'><img src="../includes/img/view-section-off.png" border=0 title="View Sections" style="height:20px;"></a>
											<a href='createSectionQuestion.html?id=${survey.surveyId}' class='menu'><img src="../includes/img/create-question-off.png" border=0 title="Create Question" style="height:20px;"></a>
											<a href='previewSurvey.html?id=${survey.surveyId}' class='menu'><img src="../includes/img/preview-survey-off.png" border=0 title="Preview Survey" style="height:20px;"></a>
											<a href='editSurvey.html?id=${survey.surveyId}'><img src="../includes/img/edit-off.png" border=0 title="Edit Survey" style="height:20px;"></a>
										<c:if test="${survey.endDate == null || survey.endDate ge now}">
											<a href='sendSurveyInvitation.html?id=${survey.surveyId}' class='menu'><img src="../includes/img/send-invite-off.png" border=0 title="Send Invitations" style="height:20px;"></a>
											<a href='/MemberServices/survey/takeSurvey.html?id=${survey.surveyId}' style='color:#FF0000;' class='menu'><img src="../includes/img/take-survey-off.png" border=0 title="Take Survey" style="height:20px;"></a>
										</c:if>
										<a href='exportSurveyResponses.html?id=${survey.surveyId}' class='menu' target='_blank'>&middot; Export Responses</a><br>													
							</div>
					</div>	
					</c:forEach>
										</c:when>
										<c:otherwise>										
										<div class='row'>
										<div class='column'>No surveys available.</div>
										</div>
										</c:otherwise>
									</c:choose>					
						
						</div>
                	
                	
                	
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