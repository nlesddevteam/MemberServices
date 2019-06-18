<%@ page language="java"
         import="java.util.*,
                  com.esdnl.personnel.jobs.bean.*,
                  com.esdnl.personnel.jobs.dao.*" 
         isThreadSafe="false"%>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<%
ApplicantProfileBean profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
JobOpportunityBean[] jobs = null;
if(profile == null)
  request.getRequestDispatcher("applicant_login.jsp").forward(request, response);

jobs = JobOpportunityManager.getApplicantOpenJobOpportunityBeans(profile.getSIN());
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Newfoundland &amp; Labrador English School District - Member Services - Personnel-Package</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">@import 'includes/home.css';</style>
<script language="JavaScript" src="js/CalendarPopup.js"></script>
<link rel="stylesheet" href="includes/jquery-ui-1.12.0/jquery-ui.min.css">
<link rel="stylesheet" href="includes/bootstrap.min.css">
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/jquery-ui-1.12.0.min.js type="text/javascript"></script>
<script type="text/javascript" src="js/applicant_validations.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</head>
<body>
<table cellpadding="2" cellspacing="2" width="920" align="left"><tr><td colspan="2"><span class="applicantTitle">Applicant Position Application(s)</span></td></tr>
<tr valign="top"><td>&nbsp;</td><td align='center'>Fields marked <span class="requiredStar">*</span> are required.</td></tr>
<tr valign="top"><td width="185"><jsp:include page="includes/jsp/applicant_registration_menu_ss.jsp" flush="true" />
</td><td align='center'>
                                <table width="100%" cellpadding="0" cellspacing="0" border="0">
                                  <tr>
                                  	<td width="100%">
                                  		<table cellpadding="3" cellspacing="3" border="0" style='border:solid 1px #333333;' align="center" width="90%">
						                            <tr>
						                              <td colspan='4' class="displayPageTitle" style='border:none;background-color:#D8E7FC;' valign='middle' align="center" >Current Job Competition Application(s)</td>
						                            </tr>
						                            <tr>
			                                    <td style="border: solid 1px #e4e4e4;" class="displayHeaderTitle" width="13%">Comp.</td>
			                                    <td style="border: solid 1px #e4e4e4;" class="displayHeaderTitle" width="47%" style="padding-right:3px;">Title</td>
			                                    <td style="border: solid 1px #e4e4e4;" class="displayHeaderTitle" width="35%">Closing Date</td>
			                                    <td style="border: solid 1px #e4e4e4;" width="*">&nbsp;</td>
			                                  </tr>
			                                  <%if(jobs.length > 0){
			                                    for(int i=0; i < jobs.length; i++){%>
			                                    <tr style="background-color:<%=(i%2==0)?"#FFFFFF":"#F0F0F0"%>;">
			                                      <td style="border: solid 1px #e4e4e4;" class="displayText" valign="top"><%=jobs[i].getCompetitionNumber()%></td>
			                                      <td style="border: solid 1px #e4e4e4;" class="displayText" valign="top"><%=jobs[i].getPositionTitle()%></td>
			                                      <td style="border: solid 1px #e4e4e4;" class="displayText" valign="top"><%=jobs[i].getFormatedCompetitionEndDate()%></td>
			                                      <td style="border: solid 1px #e4e4e4;" class='displayText' valign="top"><a href="#" onclick="openapply('<%=jobs[i].getCompetitionNumber()%>')" style="color:#FF0000;font-weight:bold;text-decoration:none;">VIEW</a></td>	
			                                    </tr>
			                                 <% }
			                                  }else{
			                                    out.println("<tr style='padding-bottom:5px;'><td colspan='4' class='displayText' style='font-size:9;padding-bottom:5px;'>No open job applications on file.</td></tr>");
			                                  }%>
						                          </table>
                                  	</td>
                                  </tr>
                                  

                                </table>
                              </td></tr></table>
<!-- Modal -->
<div id="jobModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title"><span id="spantitle">View Job Posting</span></h4>
      </div>
      <div class="modal-body">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>	
</body>
</html>
