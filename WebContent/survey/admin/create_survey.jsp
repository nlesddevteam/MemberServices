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
   <style type="text/css">
.ui-datepicker {
    background: #FFFFFF;
    border: 1px solid #000000;
    color: #EEE;
}
</style>
	<script src="../includes/ckeditor/ckeditor.js"></script>
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
					
					<div class="headerText">${SURVEY_BEAN ne null ? "Update" : "Create"} Survey</div>
					<p>
					
                	
                	<form id="frmCreateSurvey" action="createSurvey.html" method="post">
									
									<c:choose>
										<c:when test="${SURVEY_BEAN ne null}">
											<input type="hidden" name='survey_id' value='${SURVEY_BEAN.surveyId}' />
											<input type="hidden" name="op" value="update" />
										</c:when>
										<c:otherwise>
											<input type="hidden" name="op" value="create" />		
										</c:otherwise>
									</c:choose>
									
									<span class="requiredStar">*&nbsp;</span> Required fields.<p>
									
                   
                    <c:if test="${msg ne null}">
                    	<span class="messageText">${msg}</span><p>
                    </c:if>
                    
                   
                   			<span class="fieldTitle">Title:</span><span class="requiredStar">&nbsp;*</span><br/>                   
                    		<input type="text" name="survey_name" id="survey_name" style="width:90%;" class="requiredInputBox" value='${(SURVEY_BEAN ne null) ? SURVEY_BEAN.name : ""}' />
                   			<p>			 
                   			<span class="fieldTitle">Password:</span><br/>                
                    		<input type="password" name="survey_password" id="survey_password" style="width:200px;" class="inputBox" value='${(SURVEY_BEAN ne null) ? SURVEY_BEAN.password : ""}' />                    
                    		<p>	
                   			<span class="fieldTitle">Confirm Password:</span><br/>                 	
                    		<input type="password" name="survey_password_confirm" id="survey_password_confirm" style="width:200px;" class="inputBox" value='${(SURVEY_BEAN ne null) ? SURVEY_BEAN.password : ""}' />
                            <p>	            
                   			<span class="fieldTitle">Start Date:</span><span class="requiredStar">&nbsp;*</span><br/>                   
                      		<input class="requiredInputBox" type="text" name="survey_start_date" id="survey_start_date" style="width:100px;" value='${(SURVEY_BEAN ne null) ? SURVEY_BEAN.startDateFormatted : ""}' readonly/>
                            <p>	             
                   			<span class="fieldTitle">End Date:</span><br/> 
                      		<input class="inputBox" type="text" name="survey_end_date" id="survey_end_date" style="width:100px;" value='${((SURVEY_BEAN ne null) && (SURVEY_BEAN.endDate ne null)) ? SURVEY_BEAN.endDateFormatted : ""}' readonly/>
                            <p>	            
                    		<span class="fieldTitle">Introduction:</span><br/> 
                    		<textarea id="survey_introduction" name="survey_introduction" class="inputBox" style="width:90%;height:100px;">${(SURVEY_BEAN ne null) ? SURVEY_BEAN.introduction : ""}</textarea>
    						<p>			
                   			<span class="fieldTitle">Thank You Message:</span><br/> 
                    		<textarea id="survey_thankyou" name="survey_thankyou" class="inputBox" style="width:90%;height:100px;">${(SURVEY_BEAN ne null) ? SURVEY_BEAN.thankYouMessage : ""}</textarea>
                 			<p>	
                    		<span class="fieldTitle">Internal Only?</span><span class="requiredStar">&nbsp;*</span><br/>                	
                    		<input id="survey_internal" name="survey_internal" type="checkbox" class="requiredInputBox" ${SURVEY_BEAN ne null && SURVEY_BEAN.internal ? "CHECKED" : "" } />
              				<p>	
                    		<input type="submit" value='${(SURVEY_BEAN ne null) ? "Update" : "Create"}' />
								</form>
                	
                	<script>
				    CKEDITOR.replace( 'survey_introduction' );
				    CKEDITOR.replace( 'survey_thankyou' );
				    </script>     
                	
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