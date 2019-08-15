<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.esdnl.personnel.jobs.bean.*,com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>
         
<!-- LOAD JAVA TAG LIBRARIES -->
		
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%@ taglib uri="/WEB-INF/personnel_v2.tld" prefix="personnel" %>

<job:ApplicantLoggedOn/>

<%
  	ApplicantSecurityBean security_question = null;
	ApplicantProfileBean profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
  
  	if(profile != null)
  	{
    	//create function to get values
	  security_question = ApplicantSecurityManager.getApplicantSecurityBean(profile.getSIN());
  	}
%>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display","none");
</script>
<script>
  $(document).ready(function(){
	
	$("#ApplicantSecurity").submit(function(){
		if ($("#security_question").val() == '') {
			$('#msgerr').css('display','block').html("ERROR: Your Security Question cannot be empty.").delay(5000).fadeOut();
			$('#security_question').focus();
		return false;	
		}
		if ($("#security_answer").val() == '') {
			$('#msgerr').css('display','block').html("ERROR: Your Answer cannot be empty.").delay(5000).fadeOut();
			$('#security_answer').focus();
		return false;	
		}
				
		return true;
	});
	
	});
  
  </script>

</head>
<body>
<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Applicant Security Password Recovery</b></div>
      			 	<div class="panel-body"> 	
	Please enter a question and answer that you will remember in case you need to reset your password. If you fail to complete this section, you will not be able to reset your password and will need to contact support if your login fails.
	<br/><br/>
	<div class="alert alert-success" align="center" id="msgok" style="display:none;"><b>SUCCESS:</b> ${msg}</div>
	<div class="alert alert-danger" align="center" id="msgerr" style="display:none;"><b>ERROR:</b> ${errmsg}</div> 

			
        		<form id="ApplicantSecurity" action="/MemberServices/Personnel/applicantsecurity.html" method="post">
     			<%if(profile != null){%>
     				<input type="hidden" name="sin" value="<%=profile.getSIN()%>" >
                    <input type="hidden" name="op" value="edit" >
                <%}%> 
                
                <div class="input-group">
    			<span class="input-group-addon">Security Question</span>
                  <input type="text" name="security_question" id="security_question"  class="form-control" value="<%=(security_question!=null)?security_question.getSecurity_question():""%>">
                </div>
                <div class="input-group">
    			<span class="input-group-addon">Security Answer</span>
                  <input type="text" name="security_answer" id="security_answer" class="form-control"  value="<%=(security_question!=null)?security_question.getSecurity_answer():""%>">
                </div>
                <br/>  
                <div align="center">                  
                  <input type="submit" class="btn btn-sm btn-success" value="Save/Update" /> <a href="view_applicant.jsp" class="btn btn-sm btn-danger">Cancel</a>
                 </div>                    
                                                
				</form>
  </div></div></div>   
  
 
<%if(request.getAttribute("msg")!=null){%>
	<script>$("#msgok").css("display","block").delay(5000).fadeOut();</script>							
<%}%>
<%if(request.getAttribute("errmsg")!=null){%>
	<script>$("#msgerr").css("display","block").delay(5000).fadeOut();</script>							
<%}%> 
               
</body>
</html>
