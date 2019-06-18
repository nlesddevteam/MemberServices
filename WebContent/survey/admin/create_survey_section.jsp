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
			
			
		<script>
		jQuery(function(){
	     $(".img-swap").hover(
	          function(){this.src = this.src.replace("-off","-on");},
	          function(){this.src = this.src.replace("-on","-off");});
		});
	 
		
		
		</script>
   
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
					
					<div class="headerText">${SURVEY_BEAN.name} - ${section ne null ? "Update" : "Create"} Section</div>
					<p>
					
                	
                	<form id="frmCreateSurveySection" action="createSurveySection.html" method="post">
									
									<c:choose>
										<c:when test="${section ne null}">
											<input type="hidden" name="op" value="update" />
											<input type="hidden" name="section_id" value="${section.sectionId}" />
										</c:when>
										<c:otherwise>
											<input type="hidden" name="op" value="create" />
											<input type="hidden" name="survey_id" value="${SURVEY_BEAN.surveyId}" />
										</c:otherwise>
									</c:choose>
									
									<table align="center" width="100%" cellspacing="2" cellpadding="2" border="0">
										<tr>
                    	<td colspan="2" align="center" valign="middle"><span class="requiredStar">*&nbsp;</span> Required fields.</td>
                    </tr>
                    
                    <c:if test="${msg ne null}">
                    	<tr class="messageText">
                        <td colspan="2" align="center">${msg}</td>
                      </tr>
                    </c:if>
                    
                    <tr>
                    	<td class="label">Heading:<span class="requiredStar">*&nbsp;</span><br/>
                    		<input type="text" 
                    					 name="section_heading" 
                    					 id="section_heading" 
                    					 style="width:100%;" 
                    					 class="requiredInputBox"
                    					 value='${section ne null ? section.heading : "" }' />
                    	</td>
                    </tr>
                    
                    <tr>
                    	<td class="label">Is Heading Displayed?
                    		<input type="checkbox" 
                    					 name="survey_heading_displayed" 
                    					 id="survey_heading_displayed" 
                    					 class="requiredInputBox" 
                    					 ${((section ne null) && section.headerDisplayed) ? "CHECKED" : ""} />
                    	</td>
                    </tr>
                    
                    <tr>
                    	<td class="label" valign="top">Introduction:<br/>
                    		<textarea 
                    			id="section_introduction" 
                    			name="section_introduction" 
                    			class="inputBox" 
                    			style="height:100px;">${section ne null ? section.introduction : ""}</textarea>
                    	</td>
                    </tr>
                    
                    <tr>
                    	<td class="label" valign="top">Instructions:<br/>
                    		<textarea 
                    			id="section_instructions" 
                    			name="section_instructions" 
                    			class="inputBox" 
                    			style="height:100px;">${section ne null ? section.instructions : ""}</textarea>
                    	</td>
                    </tr>
                    
                    <tr>
                    	<td align="center">
                    		<input type="submit" value='${section ne null ? "Update Section" : "Create Section"}' />
                    	</td>
                    </tr>
                                    
									</table>
								</form>
                	<script>
				    CKEDITOR.replace( 'section_introduction' );
				    CKEDITOR.replace( 'section_instructions' );
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