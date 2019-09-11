<%@ page language="java"
         import="java.util.*,
                 java.text.*,
                 com.esdnl.personnel.jobs.bean.*,
                 com.esdnl.personnel.jobs.dao.*" %>

<%@ taglib uri="/WEB-INF/memberservices.tld" prefix="esd" %>
<%@ taglib uri="/WEB-INF/personnel_jobs.tld" prefix="job" %>

<job:ApplicantLoggedOn/>

	<%
		ApplicantProfileBean profile = (ApplicantProfileBean)session.getAttribute("APPLICANT");
	
		profile.modified();
		
		//session.setAttribute("APPLICANT", ApplicantProfileManager.getApplicantProfileBean(profile.getUID()));
	%>

<html>
<head>
<title>Newfoundland &amp; Labrador English School District - Applicant Registration</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">@import 'includes/home.css';</style>

  <script type="text/javascript">
    function toggleRow(rid, state)
    {
      var row = document.getElementById(rid);
      row.style.display=state;
    }
  </script>
</head>
<body>


  <script type="text/javascript">
    document.location.href="/employment/index.jsp?finished=true";
  </script>
<table cellpadding="2" cellspacing="2" width="920" align="left"><tr><td colspan="2"><span class="applicantTitle">Applicant Registration<br>Step 10: Documents - Criminal Offence Declaration Form</span></td></tr>
<tr valign="top"><td>&nbsp;</td><td align='center'>Fields marked <span class="requiredStar">*</span> are required.</td></tr>
<tr valign="top"><td width="185"><jsp:include page="includes/jsp/applicant_registration_menu.jsp" flush="true" />
</td><td align='center'>
                              <job:ApplicantProfile /> 
</td></tr></table>
</body>
</html>
