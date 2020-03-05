<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>

		<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
		<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn'%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
		<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
		<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%
	ApplicantProfileBean profile = null;
	profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
	ApplicantEducationOtherSSBean other_info = null;
	if(profile != null)
	{
	    other_info = ApplicantEducationOtherSSManager.getApplicantEducationOtherSSBean(profile.getSIN());
	}
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
  
</head>
<body>
<div style="float:right;margin-top:-10px;font-size:72px;color:rgb(0, 128, 0,0.2);font-weight:bold;vertical-align:top;">5</div>
<div style="font-size:20px;padding-top:10px;color:rgb(0, 128, 0,0.8);font-weight:bold;text-align:left;">
	SECTION5: Editing your Support Staff/Management HR Application Profile 
</div>
<br/><br/>
<div class="alert alert-success" align="center" id="msgok" style="display:none;"><b>SUCCESS:</b> ${msg}</div>
<div class="alert alert-danger" align="center" id="msgerr" style="display:none;"><b>ERROR:</b> ${errmsg}</div>

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>5. OTHER INFORMATION</b></div>
      			 	<div class="panel-body"> 
					<div class="table-responsive">
					Please add any other information you would like to state in your profile. 
 						<form id="frmPostJob" action="applicantRegistrationSS.html?step=6" method="post">
                        <textarea name="other_info" id="other_info" class="form-control" style="height:250px;"><%=(other_info != null)?other_info.getOtherInformation():""%></textarea>                                        
                        <br/>
                        <div align="center"><a class="btn btn-xs btn-danger" href="view_applicant_ss.jsp">Back to Profile</a> <input type="submit" class="btn btn-success btn-xs" value="Save/Update"></div>
                        </form>
</div></div></div></div>

<%if(request.getAttribute("msg")!=null){%>
	<script>$("#msgok").css("display","block").delay(5000).fadeOut();</script>							
<%}%>
<%if(request.getAttribute("errmsg")!=null){%>
	<script>$("#msgerr").css("display","block").delay(5000).fadeOut();</script>							
<%}%>
<script>
//Configure char count for just this page. Default max is 2460. 2000 for this page.
var pageWordCountConf = {
	    showParagraphs: true,
	    showWordCount: true,
	    showCharCount: true,
	    countSpacesAsChars: true,
	    countHTML: true,
	    maxWordCount: -1,
	    maxCharCount: 2000,
	}
    CKEDITOR.replace('other_info', {wordcount: pageWordCountConf,height:250});     
</script>            

                     
</body>
</html>