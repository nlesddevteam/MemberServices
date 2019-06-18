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
	SurveySectionBean[] sections = (SurveySectionBean[]) request.getAttribute("SURVEY_SECTION_BEANS");
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
					
					<div class="headerText">${SURVEY_BEAN.name} <br/> ${SURVEY_SECTION_BEAN.heading} - Question Listing</div>
					<p>
					
                	<c:if test="${msg ne null}">
                	<span class="messageText">${msg}</span><p>
                	</c:if>
                	
					
					<div class="survey-list">
					
					
					
					
					
					
									
									<c:choose>
										<c:when test="${fn:length(SURVEY_SECTION_QUESTION_BEANS) gt 0}">
										
										<div class='row'>
							
							<div class='column header surveyQuestionID'>ID</div>	
							<div class='column header surveyQuestionType'>Type</div>							
							<div class='column header surveyQuestionStem'>Stem</div>
							<div class='column header surveyQuestionM'>Manditory</div>							
							<div class='column header surveyQuestionAdmin'>Admin</div>
							
						</div>
										
										
											<c:forEach items="${SURVEY_SECTION_QUESTION_BEANS}" var="q">
												
											<div class='row'>
											<div class='column surveyQuestionID'><a href='editSectionQuestion.html?id=${q.questionId}'>${q.questionId }</a></div>
											<div class='column surveyQuestionType'>${q.questionType.description}</div>
											<div class='column surveyQuestionStem'>${q.questionBody}</div>
											<div class='column surveyQuestionM'>${q.manditory}</div>
											<div class='column surveyQuestionAdmin'>
														
														<a href='editSectionQuestion.html?id=${q.questionId}'><img src="../includes/img/edit-off.png" border=0 title="Edit Question" style="height:20px;"></a>
														<a href='viewQuestionHistogram.html?id=${q.questionId}'><img src="../includes/img/histogram-off.png" border=0 title="Histogram" style="height:20px;"></a>
														
														</div>											
												
											</div>	
											</c:forEach>
										</c:when>
										<c:otherwise>
											<div class='row'>
											<div class='column surveySpanAll'>This survey has no questions created.</div>
											</div>
										
										</c:otherwise>
									</c:choose>
									
									</div>
									<p>								
									<div align="center">
											<a href='createSectionQuestion.html?id=${SURVEY_BEAN.surveyId}&section_id=${SURVEY_SECTION_BEAN.sectionId}'><img src="../includes/img/create-questionlg-off.png" class="img-swap" border=0 title="Create Question" ></a>&nbsp;<a href="javascript:history.go(-1)"><img src="../includes/img/backa-off.png" class="img-swap" border=0></a>
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