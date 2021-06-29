<%@ page language="java"
         import="java.util.*,
                  java.text.*" isThreadSafe="false"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<job:ApplicantLoggedOn/>

<html>
<head>
<title>MyHRP Applicant Profiling System</title>
<script>
	$("#loadingSpinner").css("display","none");
</script>
<style>
.tableTitle {font-weight:bold;width:20%;}
.tableResult {font-weight:normal;width:80%;}

.tableTitleL {font-weight:bold;width:20%;}
.tableResultL {font-weight:normal;width:30%;}
.tableTitleR {font-weight:bold;width:20%;}
.tableResultR {font-weight:normal;width:30%;}
input {    
    border:1px solid silver;
}

</style>

</head>
<body><div style="float:right;margin-top:-10px;font-size:72px;color:rgb(0, 128, 0,0.2);font-weight:bold;vertical-align:top;">2c</div>
<div style="font-size:20px;padding-top:10px;color:rgb(0, 128, 0,0.8);font-weight:bold;text-align:left;">
	SECTION 2c: Editing your Teacher/Educator HR Application Profile 
	</div>

<br/>Please complete/update your profile below. Fields marked * are required. 

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>2c. NLESD REPLACEMENT CONTRACT EXPERIENCE</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 


 
                  <form id="AddReplExpForm" name="AddReplExpForm" action="applicantRegistration.html?step=2B" method="post" onsubmit="return AddReplExpForm_Validator(this)">                  
                  
                   <table class="table table-striped table-condensed" style="font-size:11px;">							   
							    <tbody>
							    <tr>
							    <td class="tableTitleL">From(mm/yyyy)*:</td>
				                <td class="tableResultL"><input type="text" name="from_date" id="from_date" class="form-control" readonly autocomplete="off"></td>								
							    <td class="tableTitleR">To(mm/yyyy)*:</td>
				                <td class="tableResultR"><input type="text" name="to_date" id="to_date" class="form-control" readonly autocomplete="off"></td>
								</tr>
                                <tr>
							    <td class="tableTitle">School*:</td>
				                <td class="tableResult" colspan=3><job:JobLocation id="school_id" cls="form-control" /></td></td>								
							    </tr>
								<tr>
							    <td class="tableTitle">Grades/Subjects Taught*:</td>
				                <td class="tableResult" colspan=3><input type="text" name="grds_subs" id="grds_subs" class="form-control"></td>
								</tr>
                                </tbody>
                                </table>
                  
                  				<div align="center">
                  				<a class="btn btn-sm btn-danger" href="view_applicant.jsp">Back to Profile</a>
                  				<input class="btn btn-sm btn-success" type="submit" value="Add Experience"> 
                  				<!-- <input type="button" value="Save" class="btn btn-sm btn-success" onclick="document.location.href='applicant_registration_step_3.jsp'">--></div>
                                  
                                <br/>   
                               <!-- List Current Experience, if any. If not, do not show (Built into the class) -->
                               <job:ApplicantEsdReplacementExperience />
                  
                                                     
                   </form>
 
 </div></div></div></div>
 
<%if(request.getAttribute("msg")!=null){%>
	<script>$(".msgok").css("display","block").delay(5000).fadeOut();</script>							
<%}%>
<%if(request.getAttribute("errmsg")!=null){%>
	<script>$(".msgerr").css("display","block").delay(5000).fadeOut();</script>							
<%}%>
 
 <script>
 $('document').ready(function(){
		$("#from_date,#to_date").datepicker({
	      	changeMonth: true,
	      	changeYear: true,
	      	dateFormat: "mm/yy",
	      	yearRange: "-75:+0"
	   });
 });
 </script>                       
</body>
</html>
