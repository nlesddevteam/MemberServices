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

<br/>Please complete/update your profile below. Fields marked * are required. Use whole numbers (# 0, 1, 2 etc ) where required and if no major, minor, or degree, select Not Applicable.

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
				                <td class="tableResultL"><input type="text" name="from_date" id="from_date" class="form-control" readonly autocomplete="off"></td>
							    <td class="tableTitleR">To(mm/yyyy)*:</td>
				                <td class="tableResultR"><input type="text" name="to_date" id="to_date" class="form-control" readonly autocomplete="off"></td>
								</tr>
								<tr>
							    <td class="tableTitleL"># Major Courses*:</td>
				                <td class="tableResultL"><div class="majorTest"><input type="text" name="major_courses" id="major_courses" class="form-control" placeholder="Enter a # 0,1,2,3 etc"></div>
				                <div class="majorErrorNote alert alert-danger" style="display:none;"><b>INVALID ENTRY:</b> Enter a number ONLY (total number minor courses)</div>
				                </td>
							    <td class="tableTitleR"># Minor Courses*:</td>
				                <td class="tableResultR"><div class="minorTest"><input type="text" name="minor_courses" id="minor_courses" class="form-control" placeholder="Enter a # 0,1,2,3 etc">
				                 <div class="minorErrorNote alert alert-danger" style="display:none;"><b>INVALID ENTRY:</b> Enter a number ONLY (total number minor courses)</div>
				                </div>
				                
				                </td>
								</tr>
								<tr>
								<td colspan=4>
										<div class="panel-group" id="accordion">
										  <div class="panel panel-warning">
										    <div class="panel-heading">
										      <h4 class="panel-title">
										        <a data-toggle="collapse" data-parent="#accordion" href="#collapse1">
										        SELECT MAJOR(s)* (Click to Open)</a>
										      </h4>
										    </div>
										    <div id="collapse1" class="panel-collapse collapse">
										      <div class="panel-body">										      
										      Please select up to a maximum of two (2) majors from the list below. <b>If no Major, select Not Applicable.</b><br/>
										      <div class="alert alert-danger majorError" style="display:none;">ERROR: Sorry, you can only select a maximum of two (2) majors.</div>
										      <job:MajorMinor id="major" cls="form-control"/></div>
										      <div class="alert alert-danger majorError" style="display:none;">ERROR: Sorry, you can only select a maximum of two (2) majors.</div>
										    </div>
										  </div>
										  <div class="panel panel-warning">
										    <div class="panel-heading">
										      <h4 class="panel-title">
										        <a data-toggle="collapse" data-parent="#accordion" href="#collapse2">
										        SELECT MINOR* (Click to Open) </a>
										      </h4>
										    </div>
										    <div id="collapse2" class="panel-collapse collapse">
										      <div class="panel-body">
										       Please select up to one (1) minor from the list below. You CANNOT select a minor IF you have two (2) majors selected in previous step. <b>If no Minor, select Not Applicable.</b><br/>
										       <div class="alert alert-danger minorError" style="display:none;">ERROR: Sorry, you cannot select a minor with two (2) majors.</div>										        
										      <job:MajorMinor id="minor" cls="form-control"/>
										      <div class="alert alert-danger minorError" style="display:none;">ERROR: Sorry, you cannot select a minor with 2 majors.</div>										     
										      </div>
										    </div>
										  </div>
										  <div class="panel panel-warning">
										    <div class="panel-heading">
										      <h4 class="panel-title">
										        <a data-toggle="collapse" data-parent="#accordion" href="#collapse3">
										        SELECT DEGREE CONFERRED* (Click to Open)</a>
										      </h4>
										    </div>
										    <div id="collapse3" class="panel-collapse collapse">
										      <div class="panel-body">
										      Please select up to one (1) degree from the list below. <b>If no Degree, select Not Applicable.</b><br/>
										      <job:Degrees id="degree" cls="form-control"/></div>
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
	      	yearRange: "-75:+5"
	   });
 });
 </script>
 <script>
 //Only allow one checked item per category.
$(document).ready(function(){
	
	//Check minor N/A
	
	$('input[name="minor"][value="-1"]').prop("checked",true);
	
	var majorLimit=2;	
	var minorLimit=1;	
	
    $('.major').click(function() {
    	if ($('.major:checked').length >majorLimit) {
            this.checked = false;
            
           $(".majorError").css("display","block").delay(5000).fadeOut();
        } 
    	//if two elected, tick no minor, else untick.
    	if ($('.major:checked').length ==2) {
    		$('input[name="minor"]').prop('checked', false);
    		$('input[name="minor"][value="-1"]').prop("checked",true);
    		
    	} else {
    		$('input[name="minor"][value="-1"]').prop("checked",false);
    	}
    	
	});
    
    //
    $('.minor').click(function() {
    	
    	if ($('.major:checked').length ==2) {
    		 $(".minorError").css("display","block").delay(5000).fadeOut();
    		 $('input[name="minor"]').prop('checked', false);
    	$('input[name="minor"][value="-1"]').prop("checked",true);
    	} else {    			
    	        $('.minor').not(this).prop('checked', false);
    	}
    });
    $('.degree').click(function() {
        $('.degree').not(this).prop('checked', false);
    });

    
    
    
    $('#major_courses').change(function(){    
    	  var inputVal = $("#major_courses").val();     	  
    	  if(!$.isNumeric(inputVal)) {    		  
    		  $(".majorTest").addClass("has-error").addClass("has-feedback").removeClass("has-success");  	
    		  $(".majorErrorNote").css("display","block").delay(5000).fadeOut();
    	  } else {    		  
    		  $(".majorTest").addClass("has-success").addClass("has-feedback").removeClass("has-error"); 
    	  }         
    	
    });
    
    $('#minor_courses').change(function(){    
  	  var inputVal = $("#minor_courses").val(); 
  	  if(!$.isNumeric(inputVal)) {    		  
		  $(".minorTest").addClass("has-error").addClass("has-feedback").removeClass("has-success");  	
		  $(".minorErrorNote").css("display","block").delay(5000).fadeOut();
	  } else {    		  
		  $(".minorTest").addClass("has-success").addClass("has-feedback").removeClass("has-error"); 
	  }         
  	
  });
    
    
    
    
});
</script>

</body>
</html>
