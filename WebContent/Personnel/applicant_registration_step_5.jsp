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
	input { border:1px solid silver;}
</style>

</head>
<body>
<div style="float:right;margin-top:-10px;font-size:72px;color:rgb(0, 128, 0,0.2);font-weight:bold;vertical-align:top;">5</div>
<div style="font-size:20px;padding-top:10px;color:rgb(0, 128, 0,0.8);font-weight:bold;text-align:left;">
	SECTION 5: Editing your Teacher/Educator HR Application Profile
</div>

<br/>Please complete/update your profile below. Fields marked * are required.

<div class="alert alert-success" align="center" id="msgok" style="display:none;"><b>SUCCESS:</b> ${msg}</div>
<div class="alert alert-danger" align="center" id="msgerr" style="display:none;"><b>ERROR:</b> ${errmsg}</div>

<div class="panel-group" style="padding-top:5px;">
	               	<div class="panel panel-info">
	               	<div class="panel-heading"><b>5. UNIVERSITY/COLLEGE EDUCATION</b></div>
      			 	<div class="panel-body">
					<div class="table-responsive">
                    <form id="frmPostJob" action="applicantRegistration.html?step=5" method="post">
                           <table class="table table-striped table-condensed" style="font-size:11px;">
							    <tbody>
                                <tr>
							    <td class="tableTitleL">Name of Institution*:</td>
				                <td class="tableResultL"><input type="text" name="institution" id="institution" class="form-control"></td>
				                <td class="tableTitleR">Program/Faculty*:</td>
				                <td class="tableResultR"><input type="text" name="program" id="program" class="form-control"></td>
								</tr>
                                <tr>
							    <td class="tableTitleL">From(mm/yyyy)*:</td>
				                <td class="tableResultL"><input type="text" name="from_date" id="from_date" class="form-control" autocomplete="off"></td>
							    <td class="tableTitleR">To(mm/yyyy)*:</td>
				                <td class="tableResultR"><input type="text" name="to_date" id="to_date" class="form-control" autocomplete="off"></td>
								</tr>
								<tr>
							    <td class="tableTitleL"># Major Courses*:</td>
				                <td class="tableResultL"><input type="text" name="major_courses" id="major_courses" class="form-control"></td>
							    <td class="tableTitleR"># Minor Courses*:</td>
				                <td class="tableResultR"><input type="text" name="minor_courses" id="minor_courses" class="form-control"></td>
								</tr>
								<tr>
								<td colspan=4>
										<div class="panel-group" id="accordion">
										  <div class="panel panel-warning">
										    <div class="panel-heading">
										      <h4 class="panel-title">
										        <a data-toggle="collapse" data-parent="#accordion" href="#collapse1">
										        SELECT MAJOR (Click to Open)</a>
										      </h4>
										    </div>
										    <div id="collapse1" class="panel-collapse collapse">
										      <div class="panel-body"><job:MajorMinor id="major" cls="form-control"/></div>
										    </div>
										  </div>
										  <div class="panel panel-warning">
										    <div class="panel-heading">
										      <h4 class="panel-title">
										        <a data-toggle="collapse" data-parent="#accordion" href="#collapse2">
										        SELECT MINOR (Click to Open)</a>
										      </h4>
										    </div>
										    <div id="collapse2" class="panel-collapse collapse">
										      <div class="panel-body"><job:MajorMinor id="minor" cls="form-control"/></div>
										    </div>
										  </div>
										  <div class="panel panel-warning">
										    <div class="panel-heading">
										      <h4 class="panel-title">
										        <a data-toggle="collapse" data-parent="#accordion" href="#collapse3">
										        SELECT DEGREE CONFERRED (Click to Open)</a>
										      </h4>
										    </div>
										    <div id="collapse3" class="panel-collapse collapse">
										      <div class="panel-body"><job:Degrees id="degree" cls="form-control"/></div>
										    </div>
										  </div>
										</div>
								</td>
								</tr>

								</tbody>
                                </table>





 								<div align="center">
 								<a class="btn btn-sm btn-danger" href="view_applicant.jsp">Back to Profile</a>
 								<input class="btn btn-sm btn-success" type="submit" value="Add Education">
 								<!-- <input type="button" value="Save" class="btn btn-sm btn-success" onclick="document.location.href='view_applicant.jsp'">-->
 								</div>
                               <br/>

                               <!-- List Current Experience, if any. If not, do not show (Built into the class) -->
								 <job:ApplicantEducation />


                                </form>
</div></div></div></div>

<%if(request.getAttribute("msg")!=null){%>
	<script>$("#msgok").css("display","block").delay(5000).fadeOut();</script>
<%}%>
<%if(request.getAttribute("errmsg")!=null){%>
	<script>$("#msgerr").css("display","block").delay(5000).fadeOut();</script>
<%}%>

 <script>
 $('document').ready(function(){
		$("#from_date,#to_date").datepicker({
	      	changeMonth: true,
	      	changeYear: true,
	      	dateFormat: "mm/yy",
	      	yearRange: "-75:+5"
	   });
 });
 </script>
 <script>
 //Only allow one checked item per category.
$(document).ready(function(){
    $('.major').click(function() {
        $('.major').not(this).prop('checked', false);
    });
    $('.minor').click(function() {
        $('.minor').not(this).prop('checked', false);
    });
    $('.degree').click(function() {
        $('.degree').not(this).prop('checked', false);
    });

});
</script>

</body>
</html>
