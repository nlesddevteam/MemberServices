<%@ page language="java"
         import="java.util.*,
                  java.text.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*" isThreadSafe="false"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>
<%
	ApplicantProfileBean profile = null;
	profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
	
%>

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
	input { border:1px solid silver;}
</style>
<script src="js/applicant_validations.js"></script>
<script>
			$('document').ready(function(){
				var pickerOpts={dateFormat:"mm/dd/yy",changeMonth:true,changeYear:true,yearRange: "-75:+0"};
			    $( "#fromdate" ).datepicker(pickerOpts);
			    $( "#todate" ).datepicker(pickerOpts);
			    
			    $("#reason").change(function(){
				   if($("#reason").val() == "6"){
					   	$("#otherreason").show();
				   }else{
					   $("#otherreason").hide();
				   }
				});
			});
</script>

<script type="text/javascript">
    function toggleRow(rid, state)
    {
      var row = document.getElementById(rid);
      row.style.display=state;
    }
  </script>
</head>
<body>
<div style="float:right;margin-top:-10px;font-size:72px;color:rgb(0, 128, 0,0.2);font-weight:bold;vertical-align:top;">3</div>
<div style="font-size:20px;padding-top:10px;color:rgb(0, 128, 0,0.8);font-weight:bold;text-align:left;">
	SECTION 3: Editing your Support Staff/Management HR Application Profile 
</div>

<br/>Please complete/update your profile below. Fields marked * are required. 

<div class="alert alert-success" align="center" id="msgok" style="display:none;"><b>SUCCESS:</b> ${msg}</div>
<div class="alert alert-danger" align="center" id="msgerr" style="display:none;"><b>ERROR:</b> ${errmsg}</div>

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>3. EMPLOYMENT HISTORY</b></div>
      			 	<div class="panel-body">
      			 	<div class="alert alert-danger" align="center" id="divmsg" style="display:none;"><span id="spanmsg" name="spanmsg"></span></div> 
					<div class="table-responsive"> 
  
                                <form id="frmPostJob" action="applicantRegistrationSS.html?step=3" method="post" onsubmit="return checknewemployment()">
                                <table class="table table-striped table-condensed" style="font-size:11px;">							   
							    <tbody>
							    <tr> 
                                <td class="tableTitleL">Company*:</td>
                                <td class="tableResultL" id="companyW"><input type="text" name="company" id="company" class="form-control" placeholder="Company/Institution you worked for?"></td>
                                <td class="tableTitleR">Address*:</td>
                                <td class="tableResultR" id="addressW"><input type="text" name="address" id="address" class="form-control" placeholder="Company Address"></td>
                                </tr>
                                <tr> 
                                <td class="tableTitleL">Phone Number*:</td>
                                <td class="tableResultL" id="phonenumberW"><input type="text" name="phonenumber" id="phonenumber" class="form-control" placeholder="Contact Number"></td>
                                <td class="tableTitleR">Supervisor*:</td>
                                <td class="tableResultR" id="supervisorW"><input type="text" name="supervisor" id="supervisor" class="form-control" placeholder="Your boss/supervisor?"></td>
                                </tr>  
                                <tr> 
                                <td class="tableTitleL">Job Title*:</td>
                                <td class="tableResultL" id="jobtitleW"><input type="text" name="jobtitle" id="jobtitle" class="form-control" placeholder="Your job position/title?"></td>
                                <td class="tableTitleR">Duties*:</td>
                                <td class="tableResultR" id="dutiesW"><input type="text" name="duties" id="duties" class="form-control" max-length="250" placeholder="Your job duties."></td>
                                </tr>
                                <tr> 
                                <td class="tableTitleL">From*:</td>
                                <td class="tableResultL" id="fromdateW"><input type="text" name="fromdate" id="fromdate" readonly class="form-control" placeholder="In position from?"></td>
                                <td class="tableTitleR">To:</td>
                                <td class="tableResultR" id="todateW"><input type="text" name="todate" id="todate" readonly class="form-control" placeholder="In position until?"></td>
                                </tr>
                                <tr> 
                                <td class="tableTitleL">Reason For Leaving*(Select Current Position if still employed):</td>
                                <td class="tableResultL">
	                                <select name="reason" id="reason" class="form-control">
	                                      		<option value="-1">--- Please select ---</option>
	                                      		<option value="0">Current Posiiton</option>
	                                      		<option value="1">Layoff</option>
	                                      		<option value="2">Resignation</option>
	                                      		<option value="3">Retirement</option>
	                                      		<option value="4">Temporary Position</option>
	                                      		<option value="5">Contract Ended</option>
	                                      		<option value="6">Other</option>
	                                </select>
	                                <div id="otherreason" name="otherreason" style="display:none;">
	                                      		<input type="text" name="reasonforleaving" id="reasonforleaving" class="form-control" placeholder="Reason for leaving your job with this company?">
	                                </div>
                                </td>
                                <td class="tableTitleR"> May we contact this<br/>supervisor for a reference?</td>
                                <td class="tableResultR">
                                		<select id="contact" name="contact" class="form-control">                                		
                                      		<option value="Y">Yes</option>
                                      		<option value="N">No</option>
                                      	</select>
                                </td>
                                </tr>
                                </tbody>
                                </table>                                   	
                                <div align="center"><input type="submit" class="btn btn-xs btn-primary" value="Add Employment Position"></div>
                                
                                <hr>      
                                <job:ApplicantEmploymentSS showdelete="edit"/>
                                <hr>      
                                <div align="center"><a class="btn btn-xs btn-danger" href="view_applicant_ss.jsp">Back to Profile</a> 
</div>
                                </form>
                                
                        <%if(request.getAttribute("msg")!=null){%>
							<script>$("#msgok").css("display","block").delay(5000).fadeOut();</script>							
						<%}%>
						<%if(request.getAttribute("errmsg")!=null){%>
							<script>$("#msgerr").css("display","block").delay(5000).fadeOut();</script>							
						<%}%>
 
   <script>
 $('document').ready(function(){
		$("#fromdate,#todate").datepicker({
	      	changeMonth: true,
	      	changeYear: true,
	      	dateFormat: "mm/yy",
	      	yearRange: "-75:+0"
	   });
 });
 </script> 
                         
                    
</body>
</html>
