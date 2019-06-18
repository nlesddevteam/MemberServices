<%@ page language="java"
	import="java.util.*,
                 java.text.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.awsd.security.crypto.*"
	isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
$("#loadingSpinner").css("display","none");
</script>
<style>
.tableTitle {font-weight:bold;width:20%;}
.tableResult {font-weight:normal;width:80%;}

.tableTitleL {font-weight:bold;width:15%;}
.tableResultL {font-weight:normal;width:35%;}
.tableTitleR {font-weight:bold;width:15%;}
.tableResultR {font-weight:normal;width:35%;}
input {border:1px solid silver;}

</style>

<script type="text/Javascript">  
function breakout()
    {
        if(opener)
        {
          opener.location.href="applicantlogin.html";
          self.close();
        }
        else if(self != top)
        {
          top.location.href="applicantlogin.html";
          self.close();
        }
    }
</script>

<script>
  $(document).ready(function(){
	$("#loginLink").click(function() {		

	//$('#selectBlock').hide();			
	//$('#processBlock').show();								

	 return true; 
	});
	
	$("#appLoginForm").submit(function(){
		if ($("#email").val() == '') {
			$('#msgerr').css('display','block').html("ERROR: Please enter a valid email address.").delay(5000).fadeOut();
			$('#email').focus();
		return false;	
		}
		if ($("#password").val() == '') {
			$('#msgerr').css('display','block').html("ERROR: Password cannot be empty.").delay(5000).fadeOut();
			$('#password').focus();
		return false;	
		}
		
		$('#selectBlock').hide();			
		$('#processBlock').show();	
		return true;
	});
	
	
	});
  
  </script>

</head>
<body>
	<script type='text/javascript'>breakout();</script>
	
		
	
	<%
		if (request.getAttribute("AUTHENTICATED") != null) {
			ApplicantProfileBean profile = (ApplicantProfileBean) session
					.getAttribute("APPLICANT");
			
			String surl="";
			if(profile.getProfileType().equals("T")){
			surl="https://www.nlesd.ca/employment/teachingpositions.jsp?uid=";
			}else{
			surl="https://www.nlesd.ca/employment/supportadminpositions.jsp?uid=";
			}
	%>
	<script type="text/javascript">
      document.location.href="<%=surl%><%=PasswordEncryption.encrypt(profile.getSIN())%>";
    </script>
	<%
		}else {
	%>
	<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-success">   
	               	<div class="panel-heading"><b>Applicant Login</b><br/>Please login or register to continue. Fields marked with * are required.</div>
      			 	<div class="panel-body"> 	
	
	
	<div class="alert alert-success" align="center" id="msgok" style="display:none;"><b>SUCCESS:</b> ${msg}</div>
	<div class="alert alert-danger" align="center" id="msgerr" style="display:none;"><b>ERROR:</b> ${errmsg}</div>  
	
	
	 			<div id="selectBlock" >
		  		
		  		<form action="applicantlogin.html" method="post" id="appLoginForm">
		  		
		  		<div class="input-group">
    			<span class="input-group-addon">EMAIL:</span> 
		  				<input type="text" name="email" id="email" onKeyPress="return submitenter(this,event);" class="form-control" data-toggle="tooltip" title="Enter your email address you used when you signed up for an Employment Services Account." placeholder="Enter your email address.">
		  		</div>
		  		<br/>
		  		<div class="input-group">
    			<span class="input-group-addon">PASSWORD:</span>
						<input class="form-control" type="password" name="password" id="password" data-toggle="tooltip" title="Enter your password" onKeyPress="return submitenter(this,event);" placeholder="Enter your password.">
				</div>	
									
			  <br/>
			  <div align="center"><input type="submit" value="Login" id="loginLink" class="btn btn-sm btn-success"> 			  	  	
			  <a class="btn btn-sm btn-warning" href="#inline1" title="Forget Password" onclick="OpenPopUp();">Forgot Password?</a>
			  <a href="/employment/teachingpositions.jsp?finished=true" class="btn btn-sm btn-danger">Cancel</a>
				</div>
				</form>
			</div>
			<div id="processBlock" style="display:none;" align="center">
										<img src="includes/img/processing.gif" alt="loading" id="loading_image"><br/>
										Logging in, please wait....
										<br/><br/>
			</div>		
	
	
	</div></div></div>
	
	
	
	
	
	

	<%}%>
	
	
	
	

<!-- Modal -->
<div id="step1" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Security Question</h4>
      </div>
      <div class="modal-body">
       Your Question
       <input type="text" name="securityquestion" id="securityquestion" class="form-control">
       Your Answer Was?
       <input type="password" name="securityanswer" id="securityanswer" class="form-control">
	   <input type="hidden" name="csecurityanswer" id="csecurityanswer">
	   
	   <div class="alert alert-danger" align="center" id="msgerrstep1" style="display:none;"></div> 
	   
      </div>
      <div class="modal-footer">
        <input type="button" value="Submit" onclick="checkanswer();" class="btn btn-sm btn-success"/> <button type="button" class="btn btn-sm btn-danger" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>
	
	
<div id="step2" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">New Password</h4>
      </div>
      <div class="modal-body">
       Password
       <input type="password" name="newpassword" id="newpassword" class="form-control">
       Confirm Password
       <input type="password" name="confirmpassword" id="confirmpassword" class="form-control">
       
       <div class="alert alert-danger" align="center" id="msgerrstep2" style="display:none;"></div> 
      </div>
      <div class="modal-footer">
        <input type="button" value="Change Password" onclick="checkpassword();" class="btn btn-sm btn-success"/> <button type="button" class="btn btn-sm btn-danger" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>	
	


<%if(request.getAttribute("msg")!=null){%>
	<script>$("#msgok").css("display","block").delay(5000).fadeOut();</script>							
<%}%>
<%if(request.getAttribute("errmsg")!=null){%>
	<script>$("#msgerr").css("display","block").delay(5000).fadeOut();</script>							
<%}%>



</body>
</html>
