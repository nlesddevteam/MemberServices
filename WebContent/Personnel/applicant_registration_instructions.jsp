<%@ page language="java" isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<%

	//param type= T= Teacher S= for Support Staff
	//String ptype= "S";
	//ptype = request.getParameter("ptype").toString();

	
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display","none");
</script>
</head>
<body>
<br/>
  <div class="panel panel-info">
  <div class="panel-heading"><b>Applicant HR Registration Instructions</b>
  
  	<c:choose>
  		<c:when test="${param.ptype eq 'S' }">
  			<span style='vertical-align:top;'>for <b>Support Staff</b></span>
  		</c:when>
  	<c:otherwise>
  			<span style='vertical-align:top;'>for <b>Teachers/Educators</b></span>
  	</c:otherwise>
  	</c:choose>
  
  </div>
  	<div class="panel-body">	
	
		<b>To apply online using the electronic application process, you need to first create a profile as follows:</b>
		<br/><br/>
		<ol>
			<li>At Section 1 you should enter information as requested and
				proceed to Section 2. There is a Save/Update button on each
				page as needed. <br>
			<br>
			<li>When entering your profile, if you need to go back to a
				previous section, you can do so by selecting that particular section in
				the Menu at the top of your screen or through the profile page. <br>
			<br>
			<li>When entering information related to your education, work
				experience, or references (Sections 2 to 7), information entered
				incorrectly can be edited by using the "DEL" button and re-entering
				the information. <br>
			<br>
			
			<c:if test="${ param.ptype ne 'S' }">		
			
			<li>Newfoundland &amp; Labrador English School District experience means any experience with
				any former Boards that are now a part of Newfoundland &amp; Labrador English School District. <br>
			<br>
			<li>Replacement or substitute time and number of major or minor
				courses should be entered in digits only, (ie. 22, not twenty-two).
				<br>
			<br>
			<li>You are required to upload the appropriate documents as part
				of the application process. <br>
			<br>
			</c:if> 
			
			<li>When you have completed your profile you will be returned to
				the employment page where you click "view" on the position(s) for
				which you wish to apply. All information regarding the position is
				displayed here and you click "apply" to submit your application.
				Your application is automatically submitted. <br>
			<br>
			<li>It is your responsibility to ensure your profile information
				is correct and complete, and to update/edit your profile as needed
				when changes occur in experience or education.
			<br/><br/>
			<li><b>Make sure you create a security question in case your forget your password. Failure to do so and you will not be able to reset your password.</b>	
		</ol>
		
		<form>
		
		
		
		
		<div align="center" class="no-print">
		
		<a class="btn btn-xs btn-danger" href="javascript:history.go(-1);">Back</a> 
		
		<c:choose>
			<c:when test="${ param.ptype ne 'S' }">
				<input type="button" value="Continue" class="btn btn-xs btn-primary"
					onclick="document.location.href='applicantRegistration.html';">one
			</c:when>		
			<c:otherwise>
				<input type="button" value="Continue" class="btn btn-xs btn-primary"
					onclick="document.location.href='applicantRegistrationSS.html';">two
			</c:otherwise>
		</c:choose>
		
		</div>
		<br>
		<br>

	</form>
</div></div>
</body>
</html>
