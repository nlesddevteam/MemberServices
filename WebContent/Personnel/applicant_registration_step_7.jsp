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

<job:ApplicantLoggedOn/>

<%
  ApplicantProfileBean profile = null;
  ApplicantOtherInformationBean other_info = null;
%>

<%
  profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
  
  if(profile != null)
  {
    other_info = ApplicantOtherInfoManager.getApplicantOtherInformationBean(profile.getSIN());
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

.tableTitleL {font-weight:bold;width:15%;}
.tableResultL {font-weight:normal;width:35%;}
.tableTitleR {font-weight:bold;width:15%;}
.tableResultR {font-weight:normal;width:35%;}
input {border:1px solid silver;}

</style>

  
</head>
<body>

<div style="float:right;margin-top:-10px;font-size:72px;color:rgb(0, 128, 0,0.2);font-weight:bold;vertical-align:top;">7</div>
<div style="font-size:20px;padding-top:10px;color:rgb(0, 128, 0,0.8);font-weight:bold;text-align:left;">
	SECTION 7: Editing your Teacher/Educator HR Application Profile 
</div>
<br/>
Please enter any other information you would like to include in your profile. You are limited to 4000 characters (approx 500 words).
<br/>

<div class="panel-group" style="padding-top:5px;">                               
	               	<div class="panel panel-info">   
	               	<div class="panel-heading"><b>7. OTHER INFORMATION</b></div>
      			 	<div class="panel-body"> 
      			 	
					<div class="table-responsive"> 
								<form id="frmPostJob" action="applicantRegistration.html?step=7" method="post">   
								                           
                                <textarea name="other_info" id="other_info" class="form-control" style="height:500px;"><%=(other_info != null)?other_info.getOtherInformation():""%></textarea>                                        
                                <br/>
                                <div align="center"><a class="btn btn-xs btn-danger" href="view_applicant.jsp">Back to Profile</a>
                                <input type="submit" class="btn btn-xs btn-success" value="Save/Update"/></div>                                     
                                </form>
 
</div></div></div></div>

<%if(request.getAttribute("msg")!=null){%>
	<script>$(".msgok").css("display","block").delay(5000).fadeOut();</script>							
<%}%>
<%if(request.getAttribute("errmsg")!=null){%>
	<script>$(".msgerr").css("display","block").delay(5000).fadeOut();</script>							
<%}%>
<script>
//Configure char count for just this page. Default max is 2460. 4000 for this page.
var pageWordCountConf = {
	    showParagraphs: true,
	    showWordCount: true,
	    showCharCount: true,
	    countSpacesAsChars: true,
	    countHTML: true,
	    maxWordCount: -1,
	    maxCharCount: 4000,
	}
    CKEDITOR.replace('other_info', {wordcount: pageWordCountConf,height:350});     
</script>                  



                 
</body>
</html>
