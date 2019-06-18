<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*,
                 org.apache.commons.lang.*" 
         isThreadSafe="false"%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%
  ApplicantProfileBean profile = null;
  ApplicantEducationOtherBean edu_other = null;
%>

<%
  profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
  
  if(profile != null)
  {
    edu_other = ApplicantEducationOtherManager.getApplicantEducationOtherBean(profile.getSIN());
  }
  
  //System.out.println(edu_other.getNumberMusicCourses());
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
input {border:1px solid silver;}

input[type=checkbox] {
    vertical-align:middle;
    position: relative;
    bottom: 0px;
}

</style>
</head>
<body>
<div style="float:right;margin-top:-10px;font-size:72px;color:rgb(0, 128, 0,0.2);font-weight:bold;vertical-align:top;">6</div>
<div style="font-size:20px;padding-top:10px;color:rgb(0, 128, 0,0.8);font-weight:bold;text-align:left;">
SECTION 6: Editing your Teacher/Educator HR Application Profile
</div>

<br/>Please complete/update your profile below. Fields marked * are required. 

<div class="alert alert-success" align="center" id="msgok" style="display:none;"><b>SUCCESS:</b> ${msg}</div>
<div class="alert alert-danger" align="center" id="msgerr" style="display:none;"><b>ERROR:</b> ${errmsg}</div>


<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>6. EDUCATION CONTINUED</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive"> 

  					<form id="frmPostJob" action="applicantRegistration.html?step=6" method="post">
                                <table class="table table-striped table-condensed" style="font-size:11px;">							   
							    <tbody>
							    <tr>
							    <td class="tableTitle">Level of Professional Training*:</td>
				                <td class="tableResult" colspan=3><job:TrainingMethods id='trnmtds' cls='form-control' value='<%=(edu_other != null)?Integer.toString(edu_other.getProfessionalTrainingLevel().getValue()):""%>' /></td>						
							    </tr>							    
							    <tr>
							    <td class="tableTitleL"># Special Education Courses*:</td>
				                <td class="tableResultL"><input type="text" name="sped_courses" id="sped_courses"  class="form-control" value="<%=(edu_other != null)?Integer.toString(edu_other.getNumberSpecialEducationCourses()):"0"%>"></td>								
							    <td class="tableTitleR"># French Language Courses*:</td>
				                <td class="tableResultR"><input type="text" name="fr_crs" id="fr_crs" class="form-control" value="<%=(edu_other != null)?Integer.toString(edu_other.getNumberFrenchCourses()):"0"%>"></td>
								</tr>
                                <tr>
							    <td class="tableTitleL"># Math Courses*:</td>
				                <td class="tableResultL"><input type="text" name="math_crs" id="math_crs" class="form-control" value="<%=(edu_other != null)?Integer.toString(edu_other.getNumberMathCourses()):"0"%>"></td>								
							    <td class="tableTitleR"># English Courses*:</td>
				                <td class="tableResultR"><input type="text" name="english_crs" id="english_crs" class="form-control" value="<%=(edu_other != null)?Integer.toString(edu_other.getNumberEnglishCourses()):"0"%>"></td>
								</tr> 
                                <tr>
							    <td class="tableTitleL"># Music Courses*:</td>
				                <td class="tableResultL"><input type="text" name="music_crs" id="music_crs" class="form-control" value="<%=(edu_other != null)?Integer.toString(edu_other.getNumberMusicCourses()):"0"%>"></td>								
							    <td class="tableTitleR"># Technology Courses*:</td>
				                <td class="tableResultR"><input type="text" name="tech_crs" id="tech_crs" class="form-control" value="<%=(edu_other != null)?Integer.toString(edu_other.getNumberTechnologyCourses()):"0"%>"></td>
								</tr> 
                                <tr>
							    <td class="tableTitleL"># Science Courses*:</td>
				                <td class="tableResultL"><input type="text" name="science_crs" id="science_crs" class="form-control" value="<%=(edu_other != null)?Integer.toString(edu_other.getNumberScienceCourses()):"0"%>"></td>								
							    <td class="tableTitleR">Total # Courses Completed:</td>
				                <td class="tableResultR"><input type="text" name="total_crs" id="total_crs" class="form-control" value="<%=(edu_other != null)?Integer.toString(edu_other.getTotalCoursesCompleted()):"0"%>"></td>
								</tr>
								<tr>
							    <td class="tableTitleL">Present NL Teaching Certificate Level*:</td>
				                <td class="tableResultL"><input type="text" name="cert_lvl" id="cert_lvl" class="form-control" value="<%=((edu_other != null) && (StringUtils.isNotBlank(edu_other.getTeachingCertificateLevel()))) ? edu_other.getTeachingCertificateLevel():""%>"></td>								
							    <td class="tableTitleR">Date Certificate Issued (mm/yyyy)*:</td>
				                <td class="tableResultR"><input type="text" name="cert_date" id="cert_date" class="form-control" value="<%=((edu_other != null) && (edu_other.getTeachingCertificateIssuedDate() != null)) ? (new SimpleDateFormat("MM/yyyy")).format(edu_other.getTeachingCertificateIssuedDate()) : ""%>"></td>
								</tr> 
                                </tbody>
                                </table>
                                <div align="center"><a class="btn btn-xs btn-danger" href="view_applicant.jsp">Back to Profile</a>                                
                                <input type="submit" value="Save/Update" class="btn btn-xs btn-success"></div>
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
		$("#cert_date").datepicker({
	      	changeMonth: true,
	      	changeYear: true,
	      	dateFormat: "mm/yy"
	   });
 });
		
 
 </script>                  
</body>
</html>
